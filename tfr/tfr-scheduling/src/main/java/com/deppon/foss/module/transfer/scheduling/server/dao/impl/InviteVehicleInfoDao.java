/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 *  PROJECT NAME  : tfr-scheduling
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/dao/impl/InviteVehicleDao.java
 * 
 *  FILE NAME     :InviteVehicleDao.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.scheduling.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IInviteVehicleInfoDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.InviteVehicleEditParmEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.InviteVehicleQueryParmEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.InviteVehicleInfoDto;

/**
 * 
* @description 获取外请约车信息和修改外请约车DAO
* @version 1.0
* @author 332209-foss-ruilibao
* @update 2016-5-6 下午2:37:01
 */
public class InviteVehicleInfoDao extends iBatis3DaoImpl implements IInviteVehicleInfoDao {
	
	private static final String NAMESPACE = "foss.scheduling.InviteVehicleInfo.";
	
	/**
	 * 
	* @description 根据车牌号和用车部门查询外请约车信息
	* @see com.deppon.foss.module.transfer.scheduling.api.server.dao.IInviteVehicleInfoDao#queryInviteVehicleInfo(com.deppon.foss.module.transfer.scheduling.api.shared.domain.InviteVehicleQueryParmEntity)
	* @author 332209-foss-ruilibao
	* @update 2016-5-6 下午2:37:50
	* @version V1.0
	 */
	@Override
	public InviteVehicleInfoDto queryInviteVehicleInfo(
			InviteVehicleQueryParmEntity entity) {
		return (InviteVehicleInfoDto) getSqlSession().selectOne(NAMESPACE + "queryInviteVehicleInfo", entity);
	}

	/**
	 * 
	* @description 根据车牌号和约车编号更新外请约车状态
	* @see com.deppon.foss.module.transfer.scheduling.api.server.dao.IInviteVehicleInfoDao#updateInviteVehicleStatus(com.deppon.foss.module.transfer.scheduling.api.shared.domain.InviteVehicleEditParmEntity)
	* @author 332209-foss-ruilibao
	* @update 2016-5-6 下午2:38:20
	* @version V1.0
	 */
	@Override
	public int updateInviteVehicleStatus(InviteVehicleEditParmEntity entity) {
		return getSqlSession().update( NAMESPACE + "editInviteVehicleInfo", entity);
	}
}