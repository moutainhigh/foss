package com.deppon.foss.module.frameworkimpl.sso.server;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.entity.IUser;
import com.deppon.foss.framework.server.context.SessionContext;
import com.deppon.foss.framework.server.context.WebApplicationContextHolder;
import com.deppon.foss.framework.server.sso.SSOConstant;
import com.deppon.foss.framework.server.sso.domain.Token;
import com.deppon.foss.framework.server.sso.impl.SSOUserSessionStore;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;


  

public class FossSSOUserSessionStore extends SSOUserSessionStore {

	private Logger log = Logger.getLogger(FossSSOUserSessionStore.class);
	 
	@Override
	public void saveLoginInfo(HttpServletRequest request,HttpServletResponse response, Token t) {
		super.saveLoginInfo(request, response, t);
		/**
		 * 跳转开始log
		 */
		log.info("CC单点登录开始----------");

		IUserService userService = (IUserService) WebApplicationContextHolder.getWebApplicationContext().getBean("userService");
		IUser user = userService.findByLoginName(t.getUserId());
		System.out.println(t.getUserId());
		System.out.println(user.getId());
		//SsoHandler ssoHandler = (SsoHandler) WebApplicationContextHolder.getWebApplicationContext().getBean("ssoHandler");
//		UserEntity userEntity = userService.findByUserId(t.getUserId());
		UserEntity userEntity = userService.queryEmpByUserCode(user.getId());
		
		log.info("单点登录用户----------"+userEntity.getUserName());
		
		//初始化sessionContext
		SessionContext.setSession(request.getSession());
		// 把登录用户ID、工号与默认部门编码存入session中
		// 存入用户ID
		SessionContext.setCurrentUser(userEntity.getId());
		//
//		SessionContext.getSession().setObject("FRAMEWORK__KEY_USER__","");
		// 存入用户工号
		SessionContext.getSession().setObject("FRAMEWORK_KEY_EMPCODE",userEntity.getUserName());
		// 存入默认部门编码
		SessionContext.getSession().setObject("FOSS_KEY_CURRENT_DEPTCODE",userEntity.getEmployee().getOrgCode());
		
		SessionContext.getSession().setObject("FOSS_KEY_CURRENT_DEPTNAME",userEntity.getEmployee().getOrgCode());
//		Token tokenValue=new Token(SessionContext.getSession().getId(), userEntity.getId(),
//				userEntity.getUserName(), userEntity.getEmployee().getOrgCode(),userEntity.getEmployee().getOrgCode(),
//				SessionContext.getSession()
//				.getMaxInactiveInterval());
//		String tokenParam =new String(Base64.encodeBase64String(tokenValue.toBytes()));
		FossCcToken tokenValue=new FossCcToken(SessionContext.getSession().getId(), userEntity.getId(),
				userEntity.getUserName(), userEntity.getEmployee().getOrgCode(),userEntity.getEmployee().getOrgCode(),
				SessionContext.getSession()
				.getMaxInactiveInterval());
		String tokenParam =new String(Base64.encodeBase64String(tokenValue.toBytes()));
		// 重新生成cookie或修改cookie中时间戳
//		String tokenParam = DpapCookie.getTokenParam();
		// 重新new一个Cookie
		Cookie tokenParamCookie = new Cookie(SSOConstant.TOKEN_NAME, tokenParam);
		tokenParamCookie.setPath("/");// 同一个域名所有url cookie共享
		// cookie.setMaxAge(5*60);不写入磁盘，只写入内存，只有在当前页面有用,浏览器关闭立即失效
		response.addCookie(tokenParamCookie);
		
		/**
		 * 跳转成功log
		 */
		log.info("CC单点登录结束----------");
		
	}
}
