package com.deppon.foss.module.pickup.predeliver.cas;

/**
 * PROJECT NAME: wfs
 * PACKAGE NAME: com.deppon.wfs.main.server.sso
 * FILE    NAME: DwfsSSO.java
 * COPYRIGHT: Copyright(c) 2013 Deppon All Rights Reserved.
 */
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deppon.foss.framework.server.Definitions;
import com.deppon.foss.framework.server.context.SessionContext;
import com.deppon.foss.framework.server.context.WebApplicationContextHolder;
import com.deppon.foss.framework.server.sso.SSOConstant;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IEmployeeDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.CookieToken;
import com.deppon.foss.module.pickup.predeliver.api.shared.util.TokenMarshal;

import edu.yale.its.tp.cas.client.IContextInit;

/**
 * @title: DwfsSSO
 * @description：DwfsSSO,初始化类
 * @author： chenmingyan
 * @date： 2013-9-18 下午2:36:16
 */
public class DwfsSSO implements IContextInit {

	private String userName;

	@Override
	public String getTranslatorUser(String userName) {
		this.userName = userName;
		return userName;
	}

	@Override
	public void initContext(ServletRequest request, ServletResponse response,
			FilterChain arg2, String userName) {
		
		/**
		 * 判断是否是http请求
		 */
		if (!(request instanceof HttpServletRequest)
				|| !(response instanceof HttpServletResponse)) {
			try {
				throw new ServletException(
						"CasFileter is protected http resources");
			} catch (ServletException e) {
				e.printStackTrace();
			}
		}
		// 转换成HttpServletRequest
		HttpServletRequest req = (HttpServletRequest) request;
		// 转换成HttpServletRequest
		HttpServletResponse res = (HttpServletResponse) response;
		// 拿到HttpSession
		HttpSession session = req.getSession();
		// 拿到userService
		IUserService userService = (IUserService) WebApplicationContextHolder
				.getWebApplicationContext().getBean("userService");
		UserEntity userEntity = userService.findByLoginName(this.userName);

		if (null == userEntity) {
			try {
				throw new ServletException("该用户不存在!");
			} catch (ServletException e) {
				e.printStackTrace();
				return;
			}
		}
	

		IEmployeeDao employeeDao = (IEmployeeDao) WebApplicationContextHolder
				.getWebApplicationContext().getBean("employeeDao");
		// 拿到部门
		EmployeeEntity emp = employeeDao.queryEmployeeByEmpCode(userEntity.getUserName());
		

		// 初始化sessionContext
		SessionContext.setSession(session);
		// 把登录用户ID、工号与默认部门编码存入session中
		// 存入用户ID
		SessionContext.setCurrentUser(userEntity.getId());
		// 存入用户工号
		SessionContext.getSession().setObject("FRAMEWORK_KEY_EMPCODE",
				userEntity.getUserName());
		// 存入默认部门编码
		SessionContext.getSession().setObject("FOSS_KEY_CURRENT_DEPTCODE",
				emp.getOrgCode());

		
		// 重新生成cookie或修改cookie中时间戳
		String tokenParam = getTokenParam();
		// 重新new一个Cookie
		Cookie tokenParamCookie = new Cookie(SSOConstant.TOKEN_NAME, tokenParam);
		tokenParamCookie.setPath("/");// 同一个域名所有url cookie共享
		res.addCookie(tokenParamCookie);
		
		String casToken = (String) req.getParameter("cookie");
		
		try {
			casToken = URLEncoder.encode(casToken, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
		}
		SessionContext.getSession().setObject("CAS_TGC",
				casToken);
		Cookie casTokenCookie = new Cookie("CAS_TGC",
				casToken);
		casTokenCookie.setPath("/");// 同一个域名所有url cookie共享
		res.addCookie(casTokenCookie);
		/**
		 * 跳转成功log
		 */
		
	}
	
	
	public static String getTokenParam() {
		String userId = (String) SessionContext.getSession().getObject(
				Definitions.KEY_USER);
		String empCode = (String) SessionContext.getSession().getObject(
				"FRAMEWORK_KEY_EMPCODE");
		String currentDeptCode = (String) SessionContext.getSession()
				.getObject("FOSS_KEY_CURRENT_DEPTCODE");
		CookieToken token = new CookieToken(SessionContext.getSession().getId(), userId,
				empCode, currentDeptCode, SessionContext.getSession()
						.getMaxInactiveInterval());
		return TokenMarshal.marshal(token);
	}
}
