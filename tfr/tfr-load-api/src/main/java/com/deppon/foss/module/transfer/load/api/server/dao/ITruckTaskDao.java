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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/server/dao/ITruckTaskDao.java
 *  
 *  FILE NAME          :ITruckTaskDao.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.server.dao
 * FILE    NAME: ITaskTruckDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckGPSTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.HandOverBillDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.HandOverMsgDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.TruckTaskHandOverDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.WayBillHandOverDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.TaskVehicleDto;
/**
 * 任务车辆dao
 * @author dp-duyi
 * @date 2012-11-7 上午10:16:57
 */
public interface ITruckTaskDao {
	public List<TruckTaskHandOverDto> queryUnCreateTaskTruckHandOver(Date bizJobStartTime,	Date bizJobEndTime,int threadNo, int threadCount,String handOverBillNo);
	public List<TruckTaskHandOverDto> queryUnCreateTaskTruckHandOverForWk(String handOverBillNo);
	public String queryBeCreateTruckTask(TruckTaskHandOverDto truckTaskHandOverDto);
	public int insertTruckTask(TruckTaskEntity truckTaskEntity);
	public TruckTaskDetailEntity queryBeCreateTruckTaskDetail(TruckTaskHandOverDto truckTaskHandOverDto);
	public int insertTruckTaskDetail(TruckTaskDetailEntity truckTaskDetailEntity);
	public int insertTruckTaskBill(TruckTaskBillEntity truckTaskBillEntity);
	public int insertTruckGPSTask(TruckGPSTaskEntity truckGPSTaskEntity);
	public List<TruckTaskHandOverDto> queryVehicleSeal(TruckTaskDetailEntity truckTaskDetailEntity);
	public int updateVehicleSeal(TruckTaskDetailEntity truckTaskDetailEntity);
	public int updateCertificateBag(TruckTaskDetailEntity truckTaskDetailEntity);
	public int updateHandOverBillState(Map<String,String> handOverBill);
	public int updateHandOverBillStateWk(Map<String,String> handOverBill);
	public int updateTruckTaskState(TruckTaskEntity truckTaskEntity);
	//配载单部分
	public List<TruckTaskHandOverDto> queryUnCreateTruckTaskAssembleBill(Date bizJobStartTime,	Date bizJobEndTime,int threadNo, int threadCount,String assemleBillNo);
	public int updateTruckTaskBillStateByAssembleBill(Map<String,String> condition);
	public int updateAssembleBillState(Map<String,String> assembleBill);
	public int updateChargingAssembleNo(TruckTaskEntity truckTask);
	//删除交接单部分
	public TruckTaskHandOverDto queryTruckTaskIdByHandOverBill(String handOverBill);
	public void selectBillForUpdateByTruckTaskId(String truckTaskId);
	public int queryBillCountByTruckTask(String truckTaskId);
	public int queryBillCountByTruckTaskDetail(String truckTaskDetailId);
	public int deleteTruckTask(String truckTaskId);
	public int deleteTruckTaskDetail(String truckTaskDetailId);
	public int deleteTruckTaskGPSDetail(String truckTaskDetailId);
	public int deleteTruckTaskBill(String billNo);
	//更新待同步gps车辆表
	public int updateTruckGPSTask(TruckGPSTaskEntity truckGpsTask);
	public int updateTruckGPSTaskTimes(String id);
	//更新待同步gps车辆表-短途
	public int updateTruckShortGPSTask(TruckGPSTaskEntity truckGpsTask);
	public int updateTruckShortGPSTaskTimes(String id);
	//根据配载单查询任务车辆
	public List<TruckTaskEntity> queryTruckTaskByAssembleNo(String billNo);
	//配载单中插入、删除交接单
	public int updateTruckTaskBillStateByHandOverBill(Map<String,String> condition);
	//推送到营业部交接单信息
	public HandOverMsgDto queryHandOverBillMsg(String handOverBillNo);
	
	//交接单新增后查询未修改走货路径交接单
	public List<HandOverBillDetailDto> queryUnUpdateTransportPathHandOverBill(Date bizJobStartTime,	Date bizJobEndTime,int threadNo, int threadCount);
	
	//接送货接口：根据运单号、流水号查询在途交接单出发部门编码，到达部门编码
	public HandOverBillEntity queryOnTheWayHandOverBillBySerialNo(Map<String,Object> condition);
	//接送货接口：根据运单号查询运单交接信息
	public List<WayBillHandOverDto> queryWayBillHandOverInfo(String wayBillNo);
	
	//查询未成功同步至gps系统的待跟踪车辆
	public List<TaskVehicleDto> queryFailedTruckGPSTasks(int queryCount);
	//查询未成功同步至gps系统的待跟踪车辆-短途
	public List<TaskVehicleDto> queryFailedShortTruckGPSTasks(int queryCount);
	//查询未成功同步至gps系统的待跟踪车辆中交接单信息-短途
	public List<TruckTaskHandOverDto> queryhandOverBillBytruckDetailId(String truckDetailId);
	
	/**
	 * 查询未出发的任务车辆明细记录
	 * @author 045923-foss-shiwei
	 * @date 2013-5-15 上午9:53:57
	 */
	List<String> queryUndepartRecByVehicleNo(TruckTaskDetailEntity queryCon);
	public int updateHandOverBeUpdateTransportPath(String handOverNo);
	public String queryLastedCreateTruckTaskBill(String taskId,String origCode,String destCode);
	/**
	 * 查询车辆任务明细，通过交接单、配载单
	 * @author foss-heyongdong
	 * @date 2014年4月21日 14:10:17
	 * @param billNo
	 * @return List<TruckTaskDetailEntity>
	 */
	public List<TruckTaskDetailEntity> queryTruckTaskDetail(String billNo);
	/**
	 * 根据车辆任务Id查询封签信息
	 * @author heyongdong
	 * @date 2014年9月1日 14:28:06
	 * @param qeurySeal
	 * @return List<TruckTaskHandOverDto>
	 * */
	public List<TruckTaskHandOverDto> queryVehicleSealByTaskId(TruckTaskDetailEntity qeurySeal);
	/**
	 * 更新车辆任务明细
	 * @author 105869
	 * @date 2014年9月2日 16:22:17
	 * @param truckTaskDetail
	 * */
	public int updateTruckTaskDetail(TruckTaskDetailEntity truckTaskDetail);
	
	/**
	 * 通过交接单查询交接单信息 ，发车计划信息，卸车信息
	 * @author heyongdong
	 * @date 2014年9月4日 16:57:21
	 * @param handOverBillNo
	 * */
	public List<TruckTaskHandOverDto> queryTaskTruckHandOverBill(String handOverBillNo);
	/**
	 *查询车辆任务明细信息，通过车牌号、目的站、状态
	 *@author foss-heyongdong
	 *@date 2014年9月11日 09:12:11
	 *@param TruckTaskDetailEntity 
	 *
	 * */
	public List<String> queryTruckTaskDetailAndBill(TruckTaskDetailEntity qeuryParam);
	/**
	 * 通过配载单号查询 新增任务车辆需要的基本信息（用于只装不卸的业务）
	 * @author 105869
	 * @date 2014年9月16日 11:37:48
	 * @param vehicleAssembleNo
	 * @return
	 */
	public List<TruckTaskHandOverDto> queryUncreateTaskVehicleAssem(String vehicleAssembleNo);
	
	/**
	 * 通过出发站、目的站、车牌号查询在途或者未出发的车辆任务
	 * @author 105869
	 * @date 2014年9月17日 16:55:25
	 * @param queryTask
	 * @return
	 */
	public TruckTaskHandOverDto queryTruckTaskAndDetail(TruckTaskDetailEntity queryTask);
	
	
	
	/**
	* @description 根据主键查询单据信息
	* @param truckTaskBillId
	* @return
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年4月27日 下午8:15:59
	*/
	public TruckTaskBillEntity queryTruckTaskBillById(String truckTaskBillId);
	
	/**
	* @description 根据主键查询车辆任务明细信息
	* @param truckTaskDetailId
	* @return
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年4月27日 下午8:16:28
	*/
	public TruckTaskDetailEntity queryTruckTaskDetailById(
			String truckTaskDetailId);
	
	/**
	* @description 根据主键查询车辆任务主表信息
	* @param truckTaskId
	* @return
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年4月27日 下午8:16:47
	*/
	public TruckTaskEntity queryTruckTaskById(String truckTaskId);
	
	
	/**
	* @description 根据单据编号查询单据信息
	* @param truckTaskBillId
	* @return
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年4月27日 下午8:15:59
	*/
	public List<TruckTaskBillEntity> queryTruckTaskBillByBillNo(String billNo);
	
	/**
	* @description 根据车辆任务明细编号 查询单据信息
	* @param truckTaskBillId
	* @return
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年4月27日 下午8:15:59
	*/
	public List<TruckTaskBillEntity> queryTruckTaskBillByDetailNo(String parentId);
	
	
	
}