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
 * FILE PATH        	: authorization/src/main/java/com/deppon/foss/module/authorization/client/service/imp/UserFunctionService.java
 * 
 * FILE NAME        	: UserFunctionService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.authorization.client.service.imp;

import java.util.List;

import org.mybatis.guice.transactional.Transactional;

import com.deppon.foss.module.authorization.client.dao.IUserFunctionDao;
import com.deppon.foss.module.authorization.client.domain.UserFunction;
import com.deppon.foss.module.authorization.client.service.IUserFunctionService;
import com.google.inject.Inject;


/**
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:38:24,content:TODO </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午9:38:24
 * @since
 * @version
 */
public class UserFunctionService implements IUserFunctionService {

	@Inject
	IUserFunctionDao userFunctionDao;

	@Transactional
	@Override
	public void save(List<UserFunction> userFunctions) {
		// TODO Auto-generated method stub

		for (UserFunction userFunction : userFunctions) {
			userFunctionDao.insertUserFunction(userFunction);
		}

	}
	@Transactional
	@Override
	public List<UserFunction> queryByUserID(String userId) {
		// TODO Auto-generated method stub
		return userFunctionDao.queryUserfunctionByUserID(userId);
	}
	@Transactional
	@Override
	public void deleteByUserID(String userId) {
		// TODO Auto-generated method stub
		userFunctionDao.deleteUserfunctionByUserID(userId);
	}

}