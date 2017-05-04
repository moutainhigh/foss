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
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-departure-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/server/dao/IArtificialDepartDao.java
 *  
 *  FILE NAME          :IArtificialDepartDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.QueryDepartEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.RelationInfoEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckDepartEntity;
/**
 * 人工放行业务
 * @author 038300-foss-pengzhen
 * @date 2013-03-07 下午7:56:03
 */
public interface IArtificialDepartDao{

	/**
	 * 查询临时任务,分页
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 下午5:22:40
	 */
	List<TruckDepartEntity> queryTemporaryAssignments(QueryDepartEntity queryEntity,
			int limit, int start);

	/**
	 * 查询临时任务,不分页
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 下午5:22:40
	 */
	List<LmsTruckDepartPlanEntity> queryTemporaryAssignments(QueryDepartEntity queryEntity);
	
	/**
	 * 通过车牌号查找公司司机的信息（放行计划）
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 下午5:22:40
	 */
	List<RelationInfoEntity> queryDriverInfoByVehicleNoFormDepartPlan(QueryDepartEntity queryEntity);
	/**
	 * 通过车牌号查找接送货司机的信息
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 下午5:22:40
	 */
	List<RelationInfoEntity> queryDriverInfoByVehicleNoFormBSE(QueryDepartEntity queryEntity);
	
	/**
	 * 通过车牌号查找接任务车辆司机的信息
	 * @author foss-zhangyixin
	 * @date 2012-10-29 下午5:22:40
	 */
	List<RelationInfoEntity> queryDriverInfoByTruckTaskDetailId(QueryDepartEntity queryEntity);
	
	/**
	 * 
	 * 查询总记录数
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-30 下午6:32:58
	 */
	Long getCount(QueryDepartEntity queryEntity);
	
	/**
	 * LMS放行，更新LMS表
	 * @author dp-wangqiang
	 * @date 2012-10-17 下午2:09:50
	 */
	void updateLMS(LmsTruckDepartPlanEntity lmsTruckDepartPlanEntity);
	
}