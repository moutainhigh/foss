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
 * FILE PATH        	: authorization/src/main/java/com/deppon/foss/module/authorization/client/service/IUserService.java
 * 
 * FILE NAME        	: IUserService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.authorization.client.service;

import java.util.Set;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.login.shared.domain.LoginInfo;


/**
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:40:13,content:TODO </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午9:40:13
 * @since
 * @version
 */
public interface IUserService {
	/**
	 * 
	 * 功能：insertUser
	 * 
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	public void insertUser(UserEntity user);

	/**
	 * 功能：updateUser
	 * 
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	public void updateUser(UserEntity user);

	/**
	 * 
	 * 功能：insertOrUpdateUser
	 * 
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	public void insertOrUpdateUser(UserEntity user,Set<String> functionCodes);

	/**
	 * 功能：queryUserByName
	 * 
	 * @param:
	 * @return User
	 * @since:1.6
	 */
	public LoginInfo queryUserByName(String userName);

}