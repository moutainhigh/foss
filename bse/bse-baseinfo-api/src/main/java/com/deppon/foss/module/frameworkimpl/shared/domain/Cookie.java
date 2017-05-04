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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/frameworkimpl/shared/domain/Cookie.java
 * 
 * FILE NAME        	: Cookie.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.frameworkimpl.shared.domain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.deppon.foss.framework.cache.CacheManager;
import com.deppon.foss.framework.cache.ICache;
import com.deppon.foss.framework.entity.IUser;
import com.deppon.foss.framework.exception.security.UserNotLoginException;
import com.deppon.foss.framework.server.Definitions;
import com.deppon.foss.framework.server.context.SessionContext;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.server.sso.SSOConstant;
import com.deppon.foss.module.frameworkimpl.server.util.TokenMarshal;

/**
 * Cookie操作类 主要功能： 1.生成Cookie {@link #saveCookie()} 2.Cookie数据到Session
 * {@link #cookieToSession()}
 * 
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:ningyu,date:2012-11-29 下午8:04:32
 * </p>
 * 
 * @author ningyu
 * @date 2012-11-29 下午8:04:32
 * @since
 * @version
 */
public final class Cookie {

	private Cookie() {
	}

	private static Cookie cookie;

	public static Cookie getInstance() {
		if (cookie == null) {
			cookie = new Cookie();
		}
		return cookie;
	}

	/**
	 * 获取Token字符串
	 * 
	 * @author ningyu
	 * @date 2012-12-20 下午3:37:53
	 * @return
	 * @see
	 */
	public static String getTokenParam() {
		String userId = (String) SessionContext.getSession().getObject(
				Definitions.KEY_USER);
		String empCode = (String) SessionContext.getSession().getObject(
				"FRAMEWORK_KEY_EMPCODE");
		String currentDeptCode = (String) SessionContext.getSession()
				.getObject("FOSS_KEY_CURRENT_DEPTCODE");
		String currentDeptName = (String) SessionContext.getSession()
				.getObject("FOSS_KEY_CURRENT_DEPTNAME");
		Token token = new Token(SessionContext.getSession().getId(), userId,
				empCode, currentDeptCode,currentDeptName, SessionContext.getSession()
						.getMaxInactiveInterval());
		return TokenMarshal.marshal(token);
	}

	/**
	 * 保存cookie 主要功能： 1.根据session重新生成cookie 2.修改cookie的时间戳
	 * 
	 * @author ningyu
	 * @date 2012-11-28 下午8:57:44
	 * @param token
	 * @param response
	 * @see
	 */
	public static void saveCookie() {
		HttpServletResponse response = ServletActionContext.getResponse();

		String tokenParam = getTokenParam();
		javax.servlet.http.Cookie cookie = getCookie();
		if (cookie != null) {
			// 修改cookie时间戳
			cookie.setValue(tokenParam);
		} else {
			// 重新new一个Cookie
			cookie = new javax.servlet.http.Cookie(SSOConstant.TOKEN_NAME,
					tokenParam);
		}
		cookie.setPath("/");// 同一个域名所有url cookie共享
		// cookie.setMaxAge(5*60);不写入磁盘，只写入内存，只有在当前页面有用,浏览器关闭立即失效
		response.addCookie(cookie);
	}

	/**
	 * 失效Cookie
	 * 
	 * @author ningyu
	 * @date 2012-11-30 上午10:12:24
	 * @see
	 */
	public static void invalidateCookie() {
		HttpServletResponse response = ServletActionContext.getResponse();
		// 失效掉token的cookie
		javax.servlet.http.Cookie cookieToken = getCookie();
		if (cookieToken != null) {
			cookieToken.setMaxAge(0);// 设置为0立即删除
			response.addCookie(cookieToken);
		}
		javax.servlet.http.Cookie cookieJsession = getCookie(SSOConstant.JSESSIONID);
		if (cookieJsession != null) {
			cookieJsession.setMaxAge(0);// 设置为0立即删除
			response.addCookie(cookieJsession);
		}
	}

	/**
	 * 获取HttpCookie对象,token对应的cookie
	 * 
	 * @author ningyu
	 * @date 2012-12-6 上午9:49:55
	 * @return
	 * @see
	 */
	public static javax.servlet.http.Cookie getCookie() {
		return getCookie(SSOConstant.TOKEN_NAME);
	}

	/**
	 * 获取HttpCookie对象,根据传入的cookie的name值获取, 参数可以通过
	 * {@link com.deppon.foss.framework.server.sso.SSOConstant}获取
	 * 
	 * @author ningyu
	 * @date 2012-12-13 上午11:54:31
	 * @param name
	 * @return
	 * @see
	 */
	public static javax.servlet.http.Cookie getCookie(String name) {
		if (StringUtils.isBlank(name)) {
			return null;
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		javax.servlet.http.Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				if (name.equals(cookies[i].getName())) {
					return cookies[i];
				}
			}
		}
		return null;
	}

	/**
	 * Cookie数据到Session 主要功能： 1.Cookie不存在，Throw UserNotLoginException异常
	 * 2.Cookie存在，赋值到Session
	 * 
	 * @author ningyu
	 * @date 2012-11-30 上午8:31:15
	 * @see
	 */
	public static void cookieToSession() {
		javax.servlet.http.Cookie cookie = getCookie();
		if (cookie != null) {
			String paramToken = cookie.getValue();
			if (StringUtils.isBlank(paramToken)) {
				throw new UserNotLoginException();// 用户未登录
			} else {
				Token token = TokenMarshal.unMarshal(paramToken);
				if (token != null && !token.expired()) {
					Cookie.getInstance().tokenToSession(token);
				} else {
					throw new UserNotLoginException();// 用户未登录
				}
			}
		} else {
			throw new UserNotLoginException();// 用户未登录
		}
	}

	/**
	 * token的内容复制到session中
	 * 
	 * @author ningyu
	 * @date 2012-11-29 上午9:12:20
	 * @param token
	 * @see
	 */
	@SuppressWarnings("unchecked")
	private void tokenToSession(Token token) {
		SessionContext.getSession().setObject("FRAMEWORK_KEY_EMPCODE",
				token.getEmpCode());
		SessionContext.getSession().setObject(Definitions.KEY_USER,
				token.getUserId());
		SessionContext.getSession().setObject("FOSS_KEY_CURRENT_DEPTCODE",
				token.getCurrentDeptCode());
		SessionContext.getSession().setObject("FOSS_KEY_CURRENT_DEPTNAME",
				token.getCurrentDeptName());
		ICache<String, IUser> userCache = CacheManager.getInstance().getCache(
				IUser.class.getName());
		UserContext.setCurrentUser(userCache.get(token.getUserId()));
	}

}
