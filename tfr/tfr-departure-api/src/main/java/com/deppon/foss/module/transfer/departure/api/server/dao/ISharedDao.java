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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/server/dao/ISharedDao.java
 *  
 *  FILE NAME          :ISharedDao.java
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

import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckActionDetailEntity;
import com.deppon.foss.module.transfer.departure.api.shared.dto.AutoTaskDTO;
import com.deppon.foss.module.transfer.departure.api.shared.dto.HandoverRefershDTO;
import com.deppon.foss.module.transfer.departure.api.shared.dto.WayBillRefershDTO;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckGPSTaskEntity;
/**
 * 
 * 定时任务顶层接口
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-25 上午8:59:34
 */
public interface ISharedDao{

	/**
	 * 
	 * 查询未完成任务车辆绑定信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 下午5:20:17
	 */
	List<TruckActionDetailEntity> queryTruckActionDetail(
			TruckActionDetailEntity truckActionDetailEntity);
	
	/**
	 * 查询未完成状态绑定的，用于传到后台的数据
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 下午5:22:40
	 */
	List<WayBillRefershDTO> queryDataForWaybillRefresh(WayBillRefershDTO waybillDTO);
	
	/**
	 * 根据卸车任务ID获取运单的编号
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 下午5:22:40
	 */
	List<WayBillRefershDTO> queryUnloadInfoByUnloadId(String unloadId);
	
	/**
	 * 根据卸车任务ID获取运单的编号
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 下午5:22:40
	 */
	List<String> queryWaybillByUnloadId(String unloadId);
	/**
	 * 定时任务
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-17 下午2:09:50
	 */
	void autoCancle(int rule);
	
	/**
	 * 
	 * 更新车辆任务绑定表的状态
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-2 下午3:13:00
	 */
	void updateActionDetail(TruckActionDetailEntity truckActionDetailEntity);
	
	/**
	 * 
	 * 任务车辆明细获取交接单号
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-2 下午3:13:00
	 */
	List<HandoverRefershDTO> getHandoverByDetail(String truckTaskDetailId);
	
	/**
	 * 
	 * 任务车辆明细获取运单号
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-2 下午3:13:00
	 */
	List<String> getWaybillNoByDetail(String truckTaskDetailId);
	/**
	 * 
	 * 任务车辆明细获取配载单号
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-2 下午3:13:00
	 */
	List<String> getVehicleAssembByDetail(String truckTaskDetailId);
	
	/**
	 * 
	 * 到达或者撤销到达时往PKP的job表插一条记录
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-2 下午3:13:00
	 */
	void insertTempForPKP(AutoTaskDTO dto);
	/**
	 * 
	 * 通过交接单号更改发车计划的状态
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午11:06:24
	 */
	void updateTruckDepartPlanDetailStatus(HandoverRefershDTO dto);
	/**
	 * 
	 *  通过交接单号更改发车计划的状态 (取消)
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-25 上午11:06:24
	 */
	void updateTruckDepartPlanNotDetailStatus(HandoverRefershDTO dto);

	/**
	 * 
	 * 单件出入库插入pkp临时表记录
	 * 
	 * @param dto
	 * @author ibm-wangfei
	 * @date Feb 25, 2013 1:42:22 PM
	 */
	void insertIOTempForPKP(AutoTaskDTO dto);

	/**
	 * 
	 * 更改到达部门插入临时表表记录
	 * 
	 * @param dto
	 * @author ibm-wangfei
	 * @date Mar 5, 2013 10:42:56 AM
	 */
	void insertONTempForPKP(AutoTaskDTO dto);
	
	/**
	 * 
	 * 根据任务车辆明细关闭GPS任务
	 * 
	 * @param dto
	 * @author ibm-wangfei
	 * @date Mar 5, 2013 10:42:56 AM
	 */
	void updateGpsTask(TruckGPSTaskEntity truckGPSTaskEntity);
	
	
	/**
	 * 做更新前，先更改标识位，返回int类型
	 * @author 283244
	 * */
	int updateNotModify(String id);
	/**
	 * 更新结束，返回初始标识位.
	 * @author 283244
	 * */
	void  updateModify(String id);

	
	/**
	* @description 任务车辆明细获取快递交接单号
	* @param truckTaskDetailId
	* @return
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年6月1日 上午8:35:45
	*/
	List<HandoverRefershDTO> getWKHandoverByDetail(String truckTaskDetailId);
	
	/**
	 * 
	 * 任务车辆明细获取交接单号
	 * @author foss-ruilibao
	 * @date 2016-08-15 下午06:24:00
	 */
	List<String> queryEcsLongBill(String truckTaskDetailId);
	
//	/**
//	 * 
//	 * 通过任务车辆明细ID取得该外请车或者整车的未结清金额
//	 * @author foss-liubinbin(for IBM)
//	 * @date 2013-4-16 下午2:09:14
//	 */
//	BigDecimal getLeadOrLoadFeeByTruckDetailId(String truckTaskDetailId);

	/**
	 * 
	 * 查询整车入库信息
	 * 
	 * @author foss-wangruipeng
	 * @date 2016-09-08 下午02:42:17
	 */
	List<TruckActionDetailEntity> pushForWholeVehicle(
			TruckActionDetailEntity truckActionDetailEntity);
	
}