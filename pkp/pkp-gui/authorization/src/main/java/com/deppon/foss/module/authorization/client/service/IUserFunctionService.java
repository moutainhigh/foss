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
 * FILE PATH        	: authorization/src/main/java/com/deppon/foss/module/authorization/client/service/IUserFunctionService.java
 * 
 * FILE NAME        	: IUserFunctionService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.authorization.client.service;

import java.util.List;

import com.deppon.foss.module.authorization.client.domain.UserFunction;


/**
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:39:55,content:TODO </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午9:39:55
 * @since
 * @version
 */
public interface IUserFunctionService {
	/**
	 * 
	 * 功能：save UserFunction List
	 * 
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	public void save(List<UserFunction> userFunctions);

	/**
	 * 
	 * 功能：query ByUser ID
	 * 
	 * @param:
	 * @return List<UserFunction>
	 * @since:1.6
	 */
	public List<UserFunction> queryByUserID(String userId);

	/**
	 * 
	 * 功能：deleteByUserID
	 * 
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	public void deleteByUserID(String userId);

}