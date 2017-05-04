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
 * FILE PATH        	: authorization/src/main/java/com/deppon/foss/module/authorization/client/dao/IUserFunctionDao.java
 * 
 * FILE NAME        	: IUserFunctionDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.authorization.client.dao;

import java.util.List;

import com.deppon.foss.module.authorization.client.domain.UserFunction;
 

/**
 * 用户权限访问接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:35:58,content:TODO </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午9:35:58
 * @since
 * @version
 */
public interface IUserFunctionDao {
	/**
	 * 
	 * 功能：insertUserFunction
	 * 
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	public void insertUserFunction(UserFunction userFunction);

	/**
	 * 
	 * 功能：queryUserfunctionByUserID
	 * 
	 * @param:
	 * @return List<UserFunction>
	 * @since:1.6
	 */

	public List<UserFunction> queryUserfunctionByUserID(String userId);

	/**
	 * 
	 * 功能：deleteUserfunctionByUserID
	 * 
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	public void deleteUserfunctionByUserID(String userId);
}