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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/dao/impl/RealWeightAndVolumeDao.java
 * 
 *  FILE NAME     :RealWeightAndVolumeDao.java
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
package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.IRealWeightAndVolumeDao;
import com.deppon.foss.module.transfer.platform.api.shared.dto.RealWeightAndVolumeDto;


/**
 * 货量预测总表dao实现
 * @author huyue
 * @date 2012-10-11 下午9:13:22
 */
public class RealWeightAndVolumeDao extends iBatis3DaoImpl implements IRealWeightAndVolumeDao{

	private static final String NAMESPACE = "Foss.platform.realWeightAndVolume.";
	
	/**
	 * 查询真实出发货量
	 * @author huyue
	 * @date 2012-12-4 下午9:09:37
	 */
	public RealWeightAndVolumeDto queryRealDepart(Map<String, Object> map) {
		
		RealWeightAndVolumeDto realWeightAndVolumeDto = (RealWeightAndVolumeDto) this.getSqlSession().selectOne(NAMESPACE + "queryRealDepart", map);
		
		return realWeightAndVolumeDto;
	}
	
	/**
	 * 查询真实到达货量
	 * @author huyue
	 * @date 2012-12-4 下午9:13:27
	 */
	public RealWeightAndVolumeDto queryRealArrive(Map<String, Object> map) {
		
		RealWeightAndVolumeDto realWeightAndVolumeDto = (RealWeightAndVolumeDto) this.getSqlSession().selectOne(NAMESPACE + "queryRealArrive", map);
		
		return realWeightAndVolumeDto;
	}
	
	/**
	 * 获取时间范围内所有开单重量体积票数件数
	 * @author huyue
	 * @date 2012-12-6 上午11:38:38
	 */
	public RealWeightAndVolumeDto queryAverage(Map<String, Object> map) {
		
		RealWeightAndVolumeDto realWeightAndVolumeDto = (RealWeightAndVolumeDto) this.getSqlSession().selectOne(NAMESPACE + "queryAverage", map);
		
		return realWeightAndVolumeDto;
	}
	
}