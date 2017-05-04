/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/frameworkimpl/server/interceptor/ValidatorCookieInterceptor.java
 * 
 * FILE NAME        	: ValidatorCookieInterceptor.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.frameworkimpl.server.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.util.ReflectionUtils;

import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.server.context.RequestContext;
import com.deppon.foss.framework.server.context.SessionContext;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.framework.server.sso.SSOConstant;
import com.deppon.foss.framework.server.web.interceptor.AbstractInterceptor;
import com.deppon.foss.module.frameworkimpl.server.util.TokenMarshal;
import com.deppon.foss.module.frameworkimpl.shared.domain.Cookie;
import com.deppon.foss.module.frameworkimpl.shared.domain.Token;
import com.deppon.foss.util.define.FossConstants;
import com.opensymphony.xwork2.ActionInvocation;

/**
 * 验证Cookie
 * 
 * 1.没有session 1.1 cookie赋值给session 1.2 没有cookie，throw UserNotLoginException异常
 * 2.有session 2.1 没有cookie，重新生成cookie 2.2 有cookie，更新cookie中的时间戳
 * 
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:ningyu,date:2012-11-28 下午6:28:07
 * </p>
 * 
 * @author ningyu
 * @date 2012-11-28 下午6:28:07
 * @since
 * @version
 */
public class ValidatorCookieInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = -6961254917655976427L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		final String url = RequestContext.getCurrentContext()
				.getRemoteRequestURL();
		// 跳过登录和主页面加载的请求
		if (!isChecked(url)) {
			return invocation.invoke();
		}
		Object target = invocation.getAction();
		String methodName = invocation.getProxy().getMethod();
		Method method = ReflectionUtils.findMethod(target.getClass(),
				methodName);
		if (method.isAnnotationPresent(CookieNonCheckRequired.class)) {
			return invocation.invoke();
		}
		//如果请求是SSO登录，则无需进行Cookie to session的操作
		HttpServletRequest request = ServletActionContext.getRequest();
		String dpapSsoToken = request.getParameter(SSOConstant.TOKEN_NAME);
		String casSsoToken = request.getParameter(FossConstants.CAS_SSO_TOKEN_KEY);
		if(StringUtils.isBlank(dpapSsoToken)&&StringUtils.isBlank(casSsoToken)){
			// 当前服务session中没有user对象,从cookie中初始化user到session中
			Cookie.cookieToSession();			
		}

		// 重新生成cookie或修改cookie中时间戳
		Cookie.saveCookie();
		javax.servlet.http.Cookie cookie = Cookie.getCookie(FossConstants.SSO_COOKIE_KEY);
		if (cookie != null) {
			SessionContext.getSession().setObject(FossConstants.SSO_COOKIE_KEY,
					cookie.getValue());
		}
		FossUserContext.getCurrentUser().loadAccess();
		return invocation.invoke();
	}

	/**
	 * 判断是否是受控的url
	 * 
	 * @author ningyu
	 * @date 2012-11-28 下午8:58:42
	 * @param actionUrl
	 * @return
	 * @see
	 */
	private boolean isChecked(String actionUrl) {
		String url;
		if (actionUrl.indexOf(SymbolConstants.EN_QUESTION) != -1) {
			url = actionUrl.substring(0, actionUrl.indexOf(SymbolConstants.EN_QUESTION));
		} else if (actionUrl.indexOf(SymbolConstants.EN_SEMICOLON) != -1) {
			url = actionUrl.substring(0, actionUrl.indexOf(SymbolConstants.EN_SEMICOLON));
		} else {
			url = actionUrl;
		}
		// TODO 不受控页面应可配置
		if (url.endsWith("index.action")) {
			// 当遇到需要跳过的url，检查是否存在cookie
			javax.servlet.http.Cookie cookie = Cookie.getCookie();
			if (Cookie.getCookie() != null) {
				String paramToken = cookie.getValue();
				Token token = TokenMarshal.unMarshal(paramToken);
				if (token.expired()) {
					return false;
				}
				// 如果cookie存在，验证cookie后再往下走
				return true;
			} else {
				
				if (UserContext.getCurrentUser() != null) {
					UserContext.setCurrentUser(null);
				}
				return false;
			}
		}
		return true;
	}
}
