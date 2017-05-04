package com.deppon.foss.module.frameworkimpl.server.sso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.define.LocaleConst;
import com.deppon.foss.framework.server.sso.ISSOUserStore;
import com.deppon.foss.framework.server.sso.SSOConstant;
import com.deppon.foss.framework.server.sso.SSOManager;
import com.deppon.foss.framework.server.sso.config.SSOApplicationConfig;
import com.deppon.foss.framework.server.sso.config.SSOConfig;
import com.deppon.foss.framework.server.sso.config.SSOXmlConfigLoader;
import com.deppon.foss.framework.server.sso.domain.Token;
import com.deppon.foss.framework.server.sso.util.TokenMarshal;
import com.deppon.foss.framework.server.sso.util.URLUtils;
import com.deppon.foss.base.util.define.NumberConstants;

/**
 * 
 * @ClassName: SSORequestServlet
 * @Description: <b style="font-family:微软雅黑"> <small> 处理sso登录请求 验证目的服务器是否可信
 *               如果可信向目的服务器发送验证请求 如果验证通过，获得目的服务器返回的token，附加到url上，重定向到目的服务器
 *               请求参数格式：app=destServer&url=serverURI </small> </b>
 * @author Glen Zhang yigang.zhang@achievo.com
 * @date 2011-6-13 下午05:55:27
 * 
 */
public final class SSORequestServlet extends HttpServlet {

	private static final long serialVersionUID = 3413183552969805066L;

	// Log4j
	private Logger log = LogManager.getLogger(getClass());

	/**
	 * @Title:sendValidate
	 * @Description:向目的服务器发送验证请求
	 * @param validateUrl
	 *            请求目标的URL
	 * @param requestToken
	 *            请求校验的token
	 * @param sourceAppId
	 *            请求验证的源App
	 * @return
	 * @throws ServletException
	 */
	private String sendValidate(String validateUrl) throws ServletException {
		HttpURLConnection con = null;
		BufferedReader br = null;
		InputStream is = null;
		try {
			// XXXXXX/tokenGenerate
			URL url = new URL(validateUrl);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setConnectTimeout(NumberConstants.NUMBER_5000);
			con.setUseCaches(false);
			con.addRequestProperty("HD", "HDVALUE");

			con.connect();
			// 一个token一般不会超过70个字符
			StringBuffer tokenBuf = new StringBuffer(NumberConstants.NUMBER_70);
			int responseCode = con.getResponseCode();
			if (responseCode == HttpServletResponse.SC_OK) {
				is = con.getInputStream();
				br = new BufferedReader(new InputStreamReader(is));
				String line = "";
				while ((line = br.readLine()) != null) {
					tokenBuf.append(line);
				}
				return tokenBuf.toString();
			}
		} catch (Exception e) {
			log.error("send tokenGenerate servlet check failure!", e);
			throw new ServletException(e);
		} finally {
			if (con != null) {
				con.disconnect();
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e1) {
					log.error(e1);
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
		return null;
	}

	/**
	 * 处理sso登录请求 验证目的服务器是否可信 如果可信向目的服务器发送验证请求
	 * 如果验证通过，获得目的服务器返回的token，附加到url上，重定向到目的服务器
	 * 请求参数格式：app=destServer&url=serverURI
	 * 
	 */
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		HttpSession session = request.getSession();
		String currentApp = this.getServletContext().getInitParameter(SSOConstant.CTX_PM_SSO_APP_ID);
		SSOManager ssoManager = SSOManager.getInstance(currentApp);
		ISSOUserStore userStore = ssoManager.getSSOUserStore();
		String userID = userStore.getLoginUserId(session);
		if (userID == null) {
			log.error("uuid is null，user not logon or not get logon user info ，please check getSessionUuid() method!");
			throw new ServletException("The user info for id  must not be null. ");
		}

		// 目的服务器ID
		String destAppID = request.getParameter("app");
		// 验证通过后，返回的token标志
		String tokenValue = "";
		SSOApplicationConfig destApp = null;
		Token token = null;
		if (log.isInfoEnabled()){
			log.debug("接收到SSO跳转校验请求,目标应用：" + destAppID + " 跳转地址：" + request.getParameter("url") + " 时间："
					+ System.currentTimeMillis());
		}
		try {
			// 加载sso.xml文件，根据目的服务器验证是否可信，存在即表示可信
			SSOXmlConfigLoader resource = SSOXmlConfigLoader.getInstance();
			SSOConfig sso = resource.getSso();
			if (sso == null) {
				log.error("can't find sso.xml file in classpath!");
				throw new ServletException("can't find sso.xml file in classpath!");
			}
			destApp = sso.getApplication(destAppID);
			/*
			 * if (destApp == null) { //多余的检查 log.error("The application " +
			 * destAppID + " is forbiddened. "); throw new
			 * ServletException("This application " + destAppID +
			 * " is forbiddened"); }
			 */
			// 根据目的服务器的验证URL，到目的服务器验证用户是否可以登录目的服务器
			// session.getId()当前SSO跳转请求的sessionID，非目标应用产生的sessionID
			token = new Token(userID, session.getId(), currentApp, Token.genUUID());

			String requestToken = TokenMarshal.marshal(token);
			// 将当前用户的Token信息保存到当前应用的TokenStore中，以备返回校验
			ssoManager.store(token.getTokenKey(), requestToken);

			requestToken = URLEncoder.encode(requestToken, "UTF-8");

			String validateURL = URLUtils.concat(destApp.getUrl(), "/tokenGenerate?") + SSOConstant.TOKEN_NAME + "="
					+ requestToken;
			tokenValue = sendValidate(validateURL);

			tokenValue = URLEncoder.encode(tokenValue, "UTF-8");

			/*
			 * if (tokenValue == null) { //多余的非空检查
			 * log.error("This SSO login for " + destAppID + " is failure.");
			 * throw new ServletException("The SSO login for " + destAppID +
			 * " is failure."); }
			 */
			
			
			/**
			 * 验证通过后，重定向到目的服务器 如果url没有值，直接重定向到homePage
			 */
			String url = request.getParameter("url");
			if (url == null || "".equals(url)) {
				url = destApp.getHomePage();
			}/* else {
				url = request.getQueryString();
				int index = url.lastIndexOf("&url=");
				if (index != -1) {
					url = url.substring(index + 5);
				}
			}*/
			StringBuffer redirect = new StringBuffer().append("<script>window.location.href='").append(destApp.getUrl())
					.append(url);
			//url不包含？
			if (url != null && url.indexOf('?')==-1) {
				redirect.append("?");
			}
			//redirect中包含？且最后字符不是？
			if(redirect.charAt(redirect.length()-1) != '?'){
				redirect.append("&");
			}
			Locale locale = Locale.CHINA;
			redirect.append(SSOConstant.TOKEN_NAME).append("=").append(tokenValue).append("&forward=true&")
					.append(LocaleConst.KEY_LOCALE_LANGUAGE).append("=").append(locale.getLanguage()).append("&")
					.append(LocaleConst.KEY_LOCALE_COUNTRY).append("=").append(locale.getCountry());
			
			redirect.append("';</script>");
			response.setHeader("Content-Type", "text/html");
			response.getOutputStream().write(redirect.toString().getBytes());
			if (log.isInfoEnabled()){
				log.info("校验完成，开始跳转: " + redirect.toString() + " 时间：" + System.currentTimeMillis());
			}
		} catch (ServletException e) {
			log.error("", e);
			throw e;
		} finally {
			// 不管校验成功与否，都确保清除不再使用的token
			if (token != null){
				ssoManager.remove(token);
			}
		}

		
		// response.getWriter().write(redirect.toString());
		// response.sendRedirect(redirect.toString());

	}
}