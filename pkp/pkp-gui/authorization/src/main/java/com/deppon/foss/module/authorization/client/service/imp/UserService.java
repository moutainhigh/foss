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
 * FILE PATH        	: authorization/src/main/java/com/deppon/foss/module/authorization/client/service/imp/UserService.java
 * 
 * FILE NAME        	: UserService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.authorization.client.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mybatis.guice.transactional.Transactional;

import com.deppon.foss.framework.client.component.dataaccess.annotation.Transaction;
import com.deppon.foss.module.authorization.client.dao.IUserDao;
import com.deppon.foss.module.authorization.client.dao.IUserFunctionDao;
import com.deppon.foss.module.authorization.client.domain.UserFunction;
import com.deppon.foss.module.authorization.client.service.IUserService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.login.shared.domain.LoginInfo;
import com.google.inject.Inject;


/**
 * 用户服务
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:38:33,content:TODO </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午9:38:33
 * @since
 * @version
 */
public class UserService implements IUserService {

	@Inject
	IUserDao userDao;

	@Inject
	IUserFunctionDao userFunctionDao;

	/**
	 * 功能：insertUser
	 * 
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Transactional
	@Override
	public void insertUser(UserEntity user) {
		// 保存用户信息
		userDao.insert(user);

	}

	/**
	 * 功能：updateUser
	 * 
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Transaction
	public void updateUser(UserEntity user) {
		userDao.update(user);
	}

	/**
	 * 
	 * 功能：insertOrUpdateUser
	 * 
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Transaction
	public void insertOrUpdateUser(UserEntity user, Set<String> functionCodes) {

		UserEntity iuser = userDao.getById(user.getId());

/*		if (iuser != null) {
			userDao.update(user);
		} else {
			userDao.insert(user);
		}*/
		
		List<UserFunction> userFunctions = new ArrayList<UserFunction>();
		if(functionCodes!=null && !functionCodes.isEmpty()){
			UserFunction userFunction;
			for (String functionCode : functionCodes) {
				userFunction = new UserFunction();
				userFunction.setUserId(iuser.getId());
				userFunction.setFunctionCode(functionCode);
				userFunctions.add(userFunction);
	
			}
		}
		// 跟新用户权限
		updateUserFunction(user, userFunctions);
	}

	/**
	 * 
	 * 功能：updateUserFunction
	 * 
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	private void updateUserFunction(UserEntity user, List<UserFunction> userFunctions) {
		// 删除用户权限
		userFunctionDao.deleteUserfunctionByUserID(user.getId());
		// 保存用户权限
		for (UserFunction userFunction : userFunctions) {
			userFunctionDao.insertUserFunction(userFunction);
		}
	}

	/**
	 * 功能：queryUserByName
	 * 
	 * @param:
	 * @return User
	 * @since:1.6
	 */
	@Transactional
	public LoginInfo queryUserByName(String userName) {
		LoginInfo loginInfo = new LoginInfo();
		UserEntity user = userDao.getByLoginName(userName);
		loginInfo.setUser(user);

		/*
		Map<String, String> set = new HashMap<String, String>();
		List<UserFunction> userFunctions = userFunctionDao
				.queryUserfunctionByUserID(user.getId());
		for (UserFunction userFunction : userFunctions) {
			set.put(userFunction.getFunctionCode(), userFunction.getFunctionCode());
		}

		loginInfo.setUserResourcesCodes(set);
		*/

		return loginInfo;
	}

 
}