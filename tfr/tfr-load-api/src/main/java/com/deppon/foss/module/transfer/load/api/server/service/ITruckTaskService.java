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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/server/service/ITruckTaskService.java
 *  
 *  FILE NAME          :ITruckTaskService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-load-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.server.service
 * FILE    NAME: ITaskTruckService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.TruckTaskHandOverDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.VehiclEmptyBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.WayBillHandOverDto;

/**
 * 任务车辆Service
 * @author dp-duyi
 * @date 2012-11-7 上午10:19:04
 */
public interface ITruckTaskService {
	//根据空驶单创建任务车辆
	public int createTruckTaskByVehiclEmDto(VehiclEmptyBillDto vehiclEmDto);
	//根据交接单创建任务车辆
	public int createTruckTask(TruckTaskHandOverDto handOverDto);
	//根据交接单批量生成任务车辆
	public int createTruckTaskByHandOverBill(String handOverBillNo);
	//根据交接单批量生成任务车辆
	public int batchCreateTruckTaskByHandOverBill(Date bizJobStartTime,	Date bizJobEndTime,int threadNo, int threadCount);
	//根据配载单更新任务车辆明细
	public int updateTruckTaskByAssembleBill(TruckTaskHandOverDto handOverDto);
	//根据配载单批量更新任务车辆明细
	public int batchUpdateTruckTaskByAssembleBill(Date bizJobStartTime,	Date bizJobEndTime,int threadNo, int threadCount);
	//根据配载单批量更新任务车辆明细
	public int createTruckTaskByAssembleBill(String assembleBillNo);
	//批量创建任务车辆
	public int batchCreateTruckTask(Date bizJobStartTime,Date bizJobEndTime,int threadNo, int threadCount);
	//根据交接单删除任务车辆
	public int deleteTruckTaskByHandOverBill(String handOverBillNo);
	//根据配载单删除任务车辆
	public int deleteTruckTaskbyAssembelBill(String assembleBillNo);
	//交接单更新车牌号时调用
	public int handOverBillUpdateVehicleNo(String billNo);
	//配载单更新车牌号时调用
	public int assembleBillUpdateVehicleNo(String billNo);
	//配载单中插入交接单，修改交接单单据级别为0
	public int updateBillLevelToUnValid(String handOverBillNo);
	//配载单中删除交接单，修改交接单单据级别为1
	public int updateBillLevelToValid(String handOverBillNo);
	//推送到营业部的交接单信息
	public String queryHandOverBillMsg(String handOverBillNo);
	//接送货接口：根据运单号、流水号查询在途交接单出发部门编码，到达部门编码
	public HandOverBillEntity queryOnTheWayHandOverBillBySerialNo(String wayBillNo,String serialNo);
	//接送货接口：根据运单号查询运单交接信息
	public List<WayBillHandOverDto> queryWayBillHandOverInfo(String wayBillNo);
	//交接单新增时，更新走货路径状态
	public Date updateTransportPath(Date bizJobStartTime,Date bizJobEndTime,int threadNo, int threadCount);
	//交接单新增时，更新CRM货物状态
	//public void updateCRMGoodsState(List<HandOverBillDetailDto> handOverDetails);
	//批量同步未成功同步至gps系统的待跟踪车辆
	//public void batchFailedSynTruckGpsTask(int queryCount);
	public VehicleAssociationDto getVehicle(String vehicleNo);
	public int updateHandOverBeUpdateTransportPath(String handOverNo);
	public void updateHandOverBillBeUpdateCRM(String handBillNos);
	/**
	 * 查询未出发的任务车辆明细记录
	 * @author 045923-foss-shiwei
	 * @date 2013-5-15 上午9:53:41
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao#queryUndepartRecByVehicleNo(com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity)
	 */
	List<String> queryUndepartRecByVehicleNo(TruckTaskDetailEntity queryCon);
	//查询未成功同步至gps系统的待跟踪车辆中交接单信息-短途
	public List<TruckTaskHandOverDto> queryhandOverBillBytruckDetailId(String truckDetailId);
	/**
	 *查询车辆任务明细信息，通过车次号/交接单号
	 *@author foss-heyongdong
	 *@date 2014年4月21日 14:06:18
	 *@param billNo 
	 * */
	List<TruckTaskDetailEntity> queryTruckTaskDetail(String billNo);
	/**
	 * 修改车辆任务 ，用于提前装车
	 * @author 105869
	 * @date 2014年9月1日 10:28:17
	 * @param vehicleNo,trailerHandOverbills
	 * */
	public int modifyTruckTaskByTrailerHandOverBill(
			String vehilceNo, List<String> trailerHandOverbills);
	/**
	 * 跟新车辆任务明细
	 * @author 105869
	 * @date 2014年9月2日 16:01:48
	 * @param handOverDto
	 * */
	int updateTruckTaskDetail(TruckTaskHandOverDto handOverDto);
	/**
	 *查询车辆任务明细信息，通过车牌号、目的站、状态
	 *@author foss-heyongdong
	 *@date 2014年9月11日 09:12:11
	 *@param TruckTaskDetailEntity 
	 * */
	public List<String> queryTruckTaskDetailAndBill(TruckTaskDetailEntity qeuryParam);
	/**
	 * 根据配载单号和中途到达部门生成车辆任务
	 * @author 105869
	 * @date 2014年11月25日 16:28:25
	 * */
	
	public int createTruckTaskForMidLoad(String vehicleAssembleNo, String orgcode,String destcode );
	/**
	 * 根据配载单号、出发部门 、中途到达部门删除车辆任务
	 * @author 105869
	 * @date 2014年11月25日 16:28:25
	 * */
	public void deleteTruckTaskForMidLoad(String origOrgCode,String onTheWayDestOrgCode, String vehicleNo);
}