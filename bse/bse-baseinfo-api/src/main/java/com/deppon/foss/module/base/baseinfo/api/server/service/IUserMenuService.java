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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IUserMenuService.java
 * 
 * FILE NAME        	: IUserMenuService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserMenuEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ResourceException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.UserException;

/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:用户菜单业务访问接口</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
* 1 2012-11-6 钟庭杰    新增
* </div>  
********************************************
 */
public interface IUserMenuService extends IService {
    
	/**
	 * 保存用户常用菜单
	 * saveUserMenu
	 * @param userCode
	 * @param resouceCodes
	 * @return
	 * @return void
	 * @since:
	 */
	void saveUserMenu(String userCode, List<String> resouceCodes)
			throws UserException, ResourceException;
	
	/**
	 * 通过用户编码查询用户常用菜单
	 * queryUserMenuByUserCode
	 * @param userCode
	 * @return
	 * @return List<UserMenuEntity>
	 * @since:
	 */
	List<UserMenuEntity> queryUserMenuByUserCode(String userCode)
			throws UserException;
	
	
}
