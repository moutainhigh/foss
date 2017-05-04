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
 * PROJECT NAME	: bse-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/dao/impl/WinFormSettingDao.java
 * 
 * FILE NAME        	: WinFormSettingDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.server.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.common.api.server.dao.IWinFormSettingDao;
import com.deppon.foss.module.base.common.api.shared.domain.WinFormSettingEntity;

public class WinFormSettingDao extends iBatis3DaoImpl implements IWinFormSettingDao {
	
	private static final String NAMESPACE = "foss.bse.bse-common.winFormSettingDao.";
	
	/**
	 * 查询当前用户弹出间隔设置
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2012-12-24 上午9:08:18
	 * @return 
	 */
	@Override
	public WinFormSettingEntity getUserSettingByUserCode(String userCode) {
		return (WinFormSettingEntity) getSqlSession().selectOne(NAMESPACE+"selectWinFormSettingByUserCode", userCode);
	}
	/**
	 * 新增当前用户设置
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2012-12-24 上午9:08:21
	 * @return 
	 */
	@Override
	public int insertWinFormSetting(WinFormSettingEntity entity) {
		return getSqlSession().insert(NAMESPACE+"insertWinFormSetting", entity);
	}
	/**
	 * 根据Id更新当前用户设置
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2012-12-24 上午9:08:23
	 * @return 
	 */
	@Override
	public int uptWinFormSettingById(WinFormSettingEntity entity) {
		return getSqlSession().update(NAMESPACE+"uptWinFormSettingById", entity);
	}
	/**
	 *根据当前用户编码更新当前用户设置
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2012-12-24 上午9:08:23
	 * @return 
	 */
	@Override
	public int uptWinFormSettingByUserCode(String userCode, String autoAlertFlag) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("userCode",userCode);
		map.put("autoAlertFlag",autoAlertFlag);
		return getSqlSession().update(NAMESPACE+"uptWinFormSettingByUserCode", map);
	}
	
}
