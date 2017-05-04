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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/dao/impl/VehicleActualSituationEntityDao.java
 * 
 * FILE NAME        	: VehicleActualSituationEntityDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IVehicleActualSituationEntityDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.VehicleActualSituationEntity;



/**
 * 车辆车载信息DAO
 * @author 038590-foss-wanghui
 * @date 2012-11-3 上午11:37:51
 */
public class VehicleActualSituationEntityDao extends iBatis3DaoImpl implements IVehicleActualSituationEntityDao {

	//车辆车载信息name space
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.predeliver.api.shared.domain.VehicleActualSituationEntity.";
	
	 /**
     * 
     * 添加车辆实况记录
     * @param 
     * @author 038590-foss-wanghui
     * @date 2012-12-6 上午11:05:08
     */
	@Override
	public int addVehicleSituation(VehicleActualSituationEntity record) {
		return getSqlSession().insert(NAMESPACE + "addVehicleSituation", record);
	}

	/**
	 * 
	 * 根据车牌号查询车载信息
	 * @author ibm-wangfei
	 * @date Nov 30, 2012 4:08:47 PM
	 */
	@Override
	public VehicleActualSituationEntity queryByVehicleNo(String vehicleNo) {
		return (VehicleActualSituationEntity) getSqlSession().selectOne(NAMESPACE + "queryByVehicleNo", vehicleNo);
	}

	/**
     * 
     * 根据车牌号修改车辆剩余重量和剩余体积
     * @param 
     * @author 038590-foss-wanghui
     * @date 2012-11-3 下午1:48:46
     */
	@Override
	public int updateWVByVehicleNo(VehicleActualSituationEntity record) {
		return getSqlSession().update(NAMESPACE + "updateWVByVehicleNo", record);
	}

	/**
	 * 
	 * 按分页查询车辆实况表
	 * @param 
	 * @author 038590-foss-wanghui
	 * @date 2012-11-17 下午6:10:31
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<VehicleActualSituationEntity> queryByPage(int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAMESPACE + "queryByPage", null, rowBounds);
	}

	/**
	 * 
	 * 根据车牌号清空载重和载空
	 * @param 
	 * @author 038590-foss-wanghui
	 * @date 2012-11-19 上午8:41:31
	 */
	@Override
	public int updateWV2EmptyByVehicleNo(
			VehicleActualSituationEntity actualSituationEntity) {
		return getSqlSession().update(NAMESPACE + "updateWV2EmptyByVehicleNo", actualSituationEntity);
	}

	/**
	 * 
	 * 根据车牌号增加车载信息（载重、载空和接送货票数）
	 * @param 
	 * @author 038590-foss-wanghui
	 * @date 2012-11-19 下午5:21:35
	 */
	@Override
	public int addWVByVehicleNo(VehicleActualSituationEntity vehicleActualSituationEntity) {
		return getSqlSession().update(NAMESPACE + "addWVByVehicleNo", vehicleActualSituationEntity);
	}

	/**
	 * 
	 * 删除车辆实况
	 * @param vehicleNo 车牌号
	 * @author 038590-foss-wanghui
	 * @date 2012-12-27 下午3:17:02
	 */
	@Override
	public int deleteVehicleSituation(String vehicleNo) {
		return getSqlSession().delete(NAMESPACE + "deleteVehicleSituation", vehicleNo);
	}
	
}