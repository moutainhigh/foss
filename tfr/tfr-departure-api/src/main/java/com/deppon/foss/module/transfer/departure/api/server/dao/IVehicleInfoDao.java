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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/server/dao/IVehicleInfoDao.java
 *  
 *  FILE NAME          :IVehicleInfoDao.java
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

import com.deppon.foss.module.transfer.departure.api.shared.domain.ArrivalInfoEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.BusinessInfoEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.DepartInfoEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.OnTheWayInfoEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckTaskBillEntity;
import com.deppon.foss.module.transfer.departure.api.shared.dto.TruckTaskBillSummaryDto;
import com.deppon.foss.module.transfer.departure.api.shared.dto.VehicleDetailDTO;
/**
 * 
 * 车辆详细信息底层接口
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-25 上午8:59:54
 */
public interface IVehicleInfoDao{

	/**
	 * 通过车牌号交接单的列表
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-17 下午2:09:50
	 */
	List<TruckTaskBillEntity> queryTaskBill(VehicleDetailDTO dto);

	/**
	 * 通过条件找到出发到达信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-17 下午2:09:50
	 */
	ArrivalInfoEntity queryTaskDetail(VehicleDetailDTO dto);

	/**
	 * 通过条件找到在途信息
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-17 下午2:09:50
	 */
	OnTheWayInfoEntity queryOnTheWay(VehicleDetailDTO dto);

	/**
	 * 通过条件找到申请放行信息信息
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-17 下午2:09:50
	 */
	DepartInfoEntity queryDepart(VehicleDetailDTO dto);

	/**
	 * 通过条件找到业务信息
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-17 下午2:09:50
	 */
	BusinessInfoEntity queryBusiness(VehicleDetailDTO dto);
	
	/**
	 * 获取任务车辆类型,线路，运行时长
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-17 下午2:09:50
	 */
	ArrivalInfoEntity queryBusinessInfo(VehicleDetailDTO dto);

	/**
	 * 在途跟踪优化需求
	 * 查询车辆任务下运单的总票数, 总总量, 总体积;
	 * 根据运单运输性质分组
	 * (只查询运输性质为“精准卡航”、“精准城运”货物<业务部门要求>) 感觉好奇葩
	 * @author 163580
	 * @date 2014-5-7 下午2:14:24
	 * @param dto
	 * @return
	 * @see
	 */
	List<TruckTaskBillSummaryDto> queryTaskBillSummary(VehicleDetailDTO dto);
}