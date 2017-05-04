/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/hessian/FossSSOLoginRemoting.java
 * 
 * FILE NAME        	: FossSSOLoginRemoting.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.hessian;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.deppon.foss.framework.server.components.security.SecurityNonCheckRequired;
import com.deppon.foss.framework.server.context.SessionContext;
import com.deppon.foss.framework.server.deploy.hessian.annotation.Remote;
import com.deppon.foss.framework.server.sso.SSOConstant;
import com.deppon.foss.framework.server.sso.SSOManager;
import com.deppon.foss.framework.server.sso.config.SSOApplicationConfig;
import com.deppon.foss.framework.server.sso.config.SSOConfig;
import com.deppon.foss.framework.server.sso.config.SSOInfoConfig;
import com.deppon.foss.framework.server.sso.config.SSOXmlConfigLoader;
import com.deppon.foss.framework.server.sso.domain.Token;
import com.deppon.foss.framework.server.sso.util.TokenMarshal;
import com.deppon.foss.framework.server.web.session.ISession;
import com.deppon.foss.module.pickup.waybill.shared.exception.FossSSORemotingException;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IFossSSOLoginRemoting;

/**
 * 
 * 单点登录生成Token Remoting
 * @author 102246-foss-shaohongliang
 * @date 2012-12-1 上午10:54:09
 */
@SecurityNonCheckRequired
@Remote
public class FossSSOLoginRemoting implements IFossSSOLoginRemoting {

	/**
	 * CONNECTIONTIMEOUT
	 */
	private static final int CONNECTIONTIMEOUT = 5000;
	/**
	 * 日志类
	 */
	private static final Logger LOG = LoggerFactory.getLogger(FossSSOLoginRemoting.class);
	
	/**
	 * 
	 * @Title:sendValidate
	 * @Description:向目的服务器发送验证请求
	 * @param @param validateUrl
	 * @param @return
	 * @return String 校验码，重定位到目的服务器时带过去
	 * @throws
	 */
	private TokenGenerateResponse sendValidate(String validateUrl) {
		HttpURLConnection con = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		//单点登陆验证结果
		TokenGenerateResponse tokenGenerateResponse = new TokenGenerateResponse();
		try {
			URL url = new URL(validateUrl);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setConnectTimeout(CONNECTIONTIMEOUT);
			con.setUseCaches(false);
			con.setRequestProperty("content-type", "text/html;charset=UTF-8");
			con.connect();
			StringBuffer token = new StringBuffer();
			int responseCode = con.getResponseCode();
			LOG.info("sso sendValidate responseCode :" + responseCode);
			tokenGenerateResponse.setResponseCode(responseCode);
			if (responseCode == HttpStatus.OK.value()) {
				InputStream is = con.getInputStream();
				isr = new InputStreamReader(is);
				br = new BufferedReader(isr);
				String line = "";
				while ((line = br.readLine()) != null) {
					token.append(line);
				}
				tokenGenerateResponse.setToken(token.toString());
			}else{
				tokenGenerateResponse.setReason(String.valueOf(responseCode));
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			tokenGenerateResponse.setReason(e.getMessage());
		} finally {
			if (con != null) {
				con.disconnect();
			}
			if(isr != null){
				try {
					isr.close();
				} catch (IOException e) {
					LOG.error(e.getMessage(), e);
				}
			}
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					LOG.error(e.getMessage(), e);
				}
			}
		}
		return tokenGenerateResponse;
	}


	/**
	 * 通过目标应用服务的ID与待访问的页面信息，得到一个单点登录的URL，
	 * getUrl
	 * @param destAppID 目标应用服务的ID
	 * @param homePage  待访问的页面信息
	 * @return 一个单点登录的URL
	 * @return String
	 * @since: JDK 1.6
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String getUrl(String destAppID, String homePage) {
		//当前应用ID
		String currentAppId = SSOInfoConfig.getInstance().getSsoProperty(SSOConstant.CTX_PM_SSO_APP_ID);
		//根据ID读取sso-common.xml下application
		SSOManager ssoManager = SSOManager.getInstance(currentAppId);
		//获取当前登录用户的工号
		ISession session = SessionContext.getSession();
		String userID = (String) session.getObject("FRAMEWORK_KEY_EMPCODE");
		if (userID == null) {
			LOG.error("The user info for FRAMEWORK_KEY_EMPCODE  must not be null. ");
			throw new FossSSORemotingException("The user info for FRAMEWORK_KEY_EMPCODE  must not be null. currentAppId is "+currentAppId);
		}

		// 验证通过后，返回的token标志
		String tokenValue = "";
		SSOApplicationConfig destApp = null;
		try {
			// 加载sso.xml文件，根据目的服务器验证是否可信，存在即表示可信
			SSOXmlConfigLoader resource = SSOXmlConfigLoader.getInstance();
			SSOConfig sso = resource.getSso();
			if (sso == null) {
				LOG.error("can't find sso.xml file in classpath!");
				throw new FossSSORemotingException("can't find sso.xml file in classpath!");
			}
			destApp = sso.getApplication(destAppID);
			if (destApp == null) {
				LOG.error("The application " + destAppID + " is forbiddened. ");
				throw new FossSSORemotingException("This application " + destAppID + " is forbiddened");
			}
			// 根据目的服务器的验证URL，到目的服务器验证用户是否可以登录目的服务器
			Token token = new Token(userID, (String) SessionContext.getSession().getId(), currentAppId, Token.genUUID());
			String requestToken = TokenMarshal.marshal(token);
			ssoManager.store(token.getTokenKey(), requestToken);
			requestToken = URLEncoder.encode(requestToken, "UTF-8");
			String validateURL = destApp.getUrl() + "tokenGenerate?app=" + currentAppId + "&" + SSOConstant.TOKEN_NAME
					+ "=" + requestToken;
			LOG.info("sso validateURL:" + validateURL);
			TokenGenerateResponse tokenGenerateResponse = sendValidate(validateURL);
			//验证出错
			if(tokenGenerateResponse.getResponseCode()!= HttpStatus.OK.value()){
				String error = "The SSO login for " + destAppID + " is failure.\nreason = " + 
							tokenGenerateResponse.getReason() +"\n"+validateURL;
				LOG.error(error);
				throw new FossSSORemotingException(error);
			}else{
				tokenValue = tokenGenerateResponse.getToken();
			}
		} catch (Exception e) {
			LOG.error("",e);
			throw new FossSSORemotingException(e);
		}
		StringBuffer url = new StringBuffer();
		url.append(destApp.getUrl());
		if (homePage == null) {
			homePage = destApp.getHomePage();
		}
		url.append(homePage);
		url.append("?").append(SSOConstant.TOKEN_NAME).append("=").append(tokenValue).append("&forward=true");
		return url.toString();
	}
	
	private class TokenGenerateResponse{
		
		private String token;
		
		private int responseCode;
		
		private String reason;
		
		/**
		 * @return the reason
		 */
		public String getReason() {
			return reason;
		}
		/**
		 * @param reason the reason to set
		 */
		public void setReason(String reason) {
			this.reason = reason;
		}
		/**
		 * @return the token
		 */
		public String getToken() {
			return token;
		}
		/**
		 * @param token the token to set
		 */
		public void setToken(String token) {
			this.token = token;
		}
		/**
		 * @return the responseCode
		 */
		public int getResponseCode() {
			return responseCode;
		}
		/**
		 * @param responseCode the responseCode to set
		 */
		public void setResponseCode(int responseCode) {
			this.responseCode = responseCode;
		}
		
		
	}
}