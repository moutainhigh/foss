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
 * PROJECT NAME	: pkp-gui
 * 
 * FILE PATH        	: login/src/main/java/com/deppon/foss/module/login/client/service/UserService.java
 * 
 * FILE NAME        	: UserService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.login.client.service;

import java.util.Set;

import com.deppon.foss.module.authorization.client.service.UserServiceFactory;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.login.shared.domain.LoginInfo;


/**
 * 登录功能本地实现
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午11:44:58, </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午11:44:58
 * @since
 * @version
 */
public class UserService {

	public static void saveOrUpdateUser(UserEntity user, Set<String> functionCodes) {

		UserServiceFactory.getUserService().insertOrUpdateUser(user,
				functionCodes);

	}

	/**
	 * 
	 * 功能：login
	 * 
	 * @param:
	 * @return LoginInfo
	 * @since:1.6
	 */
	public static LoginInfo login(String loginName, String password) {

		LoginInfo loginInfo = UserServiceFactory.getUserService()
				.queryUserByName(loginName);

		if (loginInfo != null
				&& loginInfo.getUser().getPassword().equals(password)) {
			return loginInfo;

		}

		return null;

	}

}