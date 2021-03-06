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
 * PROJECT NAME	: bse-common-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/server/dao/IWinFormSettingDao.java
 * 
 * FILE NAME        	: IWinFormSettingDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.api.server.dao;

import com.deppon.foss.module.base.common.api.shared.domain.WinFormSettingEntity;

public interface IWinFormSettingDao {

	/**
	 * 查询当前用户弹出间隔设置
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2012-12-24 上午9:08:18
	 * @return 
	 */
	WinFormSettingEntity getUserSettingByUserCode(String userCode);

	/**
	 * 新增当前用户设置
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2012-12-24 上午9:08:21
	 * @return 
	 */
	int insertWinFormSetting(WinFormSettingEntity entity);

	/**
	 * 根据Id更新当前用户设置
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2012-12-24 上午9:08:23
	 * @return 
	 */
	int uptWinFormSettingById(WinFormSettingEntity entity);
	
	/**
	 *根据当前用户编码更新当前用户设置
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2012-12-24 上午9:08:23
	 * @return 
	 */
	int uptWinFormSettingByUserCode(String userCode,String autoAlertFlag);
}
