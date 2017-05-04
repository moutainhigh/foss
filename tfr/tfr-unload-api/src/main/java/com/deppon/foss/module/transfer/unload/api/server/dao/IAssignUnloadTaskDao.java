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
 *  PROJECT NAME  : tfr-unload-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/server/dao/IAssignUnloadTaskDao.java
 *  
 *  FILE NAME          :IAssignUnloadTaskDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.unload.api.server.dao;

import java.util.List;
import java.util.Map;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ArriveBillDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskTotalDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.LoaderDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.TruckTaskDetailDto;

public interface IAssignUnloadTaskDao {
	//查询到达车辆
	public List<AssignUnloadTaskTotalDto> queryArriveTransferVehicle(AssignUnloadTaskTotalDto vehicle);
	//查询到达车辆
	public List<AssignUnloadTaskTotalDto> queryArrivePickUpVehicle(AssignUnloadTaskTotalDto vehicle);
	//查询到达车辆
	public List<AssignUnloadTaskTotalDto> queryArriveTransferVehicle(AssignUnloadTaskTotalDto vehicle, int limit, int start);
	//查询到达车辆
	public List<AssignUnloadTaskTotalDto> queryArrivePickUpVehicle(AssignUnloadTaskTotalDto vehicle, int limit, int start);
	//查询到达快递车辆chenmingyan
	public List<AssignUnloadTaskTotalDto> queryArriveExpressVehicle(AssignUnloadTaskTotalDto vehicle);
	public List<AssignUnloadTaskTotalDto> queryArriveExpressVehicle(AssignUnloadTaskTotalDto vehicle, int limit, int start);
	public List<AssignUnloadTaskTotalDto> queryElecTransportVehicle(AssignUnloadTaskTotalDto vehicle, int limit, int start);
	//查询到达单据
	public List<ArriveBillDto> queryArriveTransderBill(ArriveBillDto bill);
	public List<ArriveBillDto> queryArrivePickUpBill(ArriveBillDto bill);
	public List<ArriveBillDto> queryArriveExpressBill(ArriveBillDto bill);
	public List<ArriveBillDto> queryElecWayBill(ArriveBillDto bill);
	//查询理货员
	public List<LoaderDto> queryLoaderState(List<LoaderDto> loaderCodes);
	public List<LoaderDto> queryLoaderFinishedWorkLoad(Map<String,Object> condition);
	public List<LoaderDto> queryLoaderUnFinishedWorkLoad(List<LoaderDto> loaderCodes);
	//插入分配记录
	public int insert(List<AssignUnloadTaskDto> tasks);
	//查询到达单据状态
	public List<ArriveBillDto> queryTransferBillState(List<ArriveBillDto> bills);
	public List<ArriveBillDto> queryPickUpBillState(List<ArriveBillDto> bills);
	//快递集中交接单chenmingyan
	public List<ArriveBillDto> queryExpressBillState(List<ArriveBillDto> bills);
	//更新到达单据状态
	public int updateArriveTransferBillState(List<ArriveBillDto> bills);
	public int updateArrivePickUpBillState(List<ArriveBillDto> bills);
	public int updateArriveTransferBillByState(String billNo, String billLevel, String beforeBillState, String afterBillState);
	public int updateArrivePickUpBillByState(String billNo, String beforeBillState, String afterBillState);
	//快递集中交接单chenmingyan
	public int updateArriveExpressBillByState(String billNo, String beforeBillState, String afterBillState);
	public int updateArriveExpressBillState(List<ArriveBillDto> bills);
	public int updateElecWayBillState(List<ArriveBillDto> bills);
	//刷新
	public List<AssignUnloadTaskTotalDto> queryUnStartTask(AssignUnloadTaskDto taskState,int limit,int start);
	public Long queryUnStartTaskCount(AssignUnloadTaskDto taskState);
	//取消分配
	public int cancelAssignUnloadTask(AssignUnloadTaskDto tasks);
	//取消分配前查询需要取消的到达单据
	public List<AssignUnloadTaskDto> queryTaskByVehicle(AssignUnloadTaskDto assignMsg);
	
	//查询界面：查询
	public List<AssignUnloadTaskTotalDto> queryAssignUnloadTask(AssignUnloadTaskDto task,int limit,int start);
	public Long queryAssignUnloadTaskCount(AssignUnloadTaskDto task);
	//查询明细
	public List<ArriveBillDto> queryAssignTransferUnloadTaskDetail(AssignUnloadTaskDto task);
	public List<ArriveBillDto> queryAssignPickUpUnloadTaskDetail(AssignUnloadTaskDto task);
	public List<ArriveBillDto> queryAssignExpressUnloadTaskDetail(AssignUnloadTaskDto task);
	//查询未完成任务
	public List<String> queryUnfinishedTask(String loaderCode,String vehicleNo);
	public int queryTransferArrivedCount(List<String> orgCodes,String vehicleType);
	public int queryPickUpArrivedCount(List<String> orgCodes);
	public List<AssignUnloadTaskTotalDto> queryAssignUnloadedTask(AssignUnloadTaskDto task);
	//根据车牌号查询任务车辆id
	public String queryTruckTaskIdByVehicleNo(String vechicleNo);
	//根据数据字典valueCode查询valueName
	public String queryDictionaryValueNameByValueCode(String valueCode);
	//根据单据编号，车牌号查询分配卸车任务 by wqh
	public AssignUnloadTaskTotalDto queryAssUnloadTaskByBillNo(String vechicleNo,String billNo);
	//根据 到达部门、单据编号，车牌号查询部分任务车辆信息by wqh
	public TruckTaskDetailDto queryTruckTaskDetailByBillNoAndVechicleNo(String vechicleNo,String billNo,String destOrgCode);
	//根据卸车任务id查询所有的交接单
	public List<String> queryHandNosByTruckTaskId(String truckTaskId);
	/**
	 * 修改空运正单交接单卸车状态
	 * @Author 263072
	 * 2015-9-19 14:16:10
	 * @return
	 */
	public int updateAirHandOverBillState(List<ArriveBillDto> bills);
	//新增和取消分配时修改商务专递单据状态和卸车状态272681
	int updateArriveBusinessAirBillState(List<ArriveBillDto> bills);
	//新增前查询到达商务专递卸车单据状态 272681
	List<ArriveBillDto> queryAirBusinessBillState(List<ArriveBillDto> bills);
	//新增前查询电子面单卸车单据状态 322610
	List<ArriveBillDto> queryElecTransportBillState(List<ArriveBillDto> bills);
	/**
	 * @desc:分页查询商务专递
	 * @author chenlei 272681
	 * @date:2015-8-14
	 */
	List<AssignUnloadTaskTotalDto> queryArriveBusinessVehicle(
			AssignUnloadTaskTotalDto vehicle, int limit, int start);
	
	/**
	 * @desc:查询商务专递
	 * @author chenlei 272681
	 * @date:2015-8-14
	 */
	List<AssignUnloadTaskTotalDto> queryArriveBusinessVehicle(
			AssignUnloadTaskTotalDto vehicle);
	
	/**
	 * @desc:查询零担电子面单
	 * @author songjl 322610
	 * @date:2016-8-9
	 */
	List<AssignUnloadTaskTotalDto> queryElecTransportVehicle(
			AssignUnloadTaskTotalDto vehicle);
	
	/**
	 * 
	 * <p>查询商务专递卸车单据</p> 
	 * @author chenlei 272681
	 * @date 2015-8-20 
	 */
	List<ArriveBillDto> queryArriveBusinessBill(ArriveBillDto bill);
	
	/**
	 * 
	 * <p>根据状态修改商务专递交接单单据状态</p> 
	 * @author chenlei 272681
	 * @param billNo
	 * @param beforeBillState
	 * @param afterBillState
	 */
	int updateArriveBusinessAirBillByState(String billNo,
			String beforeBillState, String afterBillState);
	/**
	 * 
	 * <p>根据状态修改电子运单交接单单据状态</p> 
	 * @author 322610
	 * @param billNo
	 * @param beforeBillState
	 * @param afterBillState
	 */
	int updateElecTransportBillByState(String billNo,
			String beforeBillState, String afterBillState);
	
	/**
	 * 
	 * <p>查询已分配任务取消任务时修改商务专递单据状态</p> 
	 * @author chenlei 272681
	 * @date 2015/8/26
	 * @param bills
	 */
	int updateBusinessAirBillState(List<ArriveBillDto> bills);
	
	/***
	 * 
	 * <p>查询商务专递卸车分配记录明细</p> 
	 * @author chenlei 272681
	 * @date 2015/8/26
	 * @param task
	 */
	List<ArriveBillDto> queryAssignBusinessUnloadTaskDetail(
			AssignUnloadTaskDto task);
	
	/***
	 * 
	 * <p>查询零担电子运单卸车分配记录明细</p> 
	 * @author 322610
	 * @date 2016-7-26
	 * @param task
	 */
	List<ArriveBillDto> queryElecWayUnloadTaskDetail(
			AssignUnloadTaskDto task);
	
	/**
	* @description 查询是否是已分配过的卸车任务，用于判断是否二次分配
	* @param billNo
	* @return
	* @version 1.0
	* @author 276198-foss-duhao
	* @update 2015-10-24 下午3:50:28
	*/
	public int isExistAllotedUnload(String billNo);
	
	/**
	* @description 批量更新快递交接单接口
	* @param newTasks
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年6月15日 下午7:17:23
	 */
	public int update(List<AssignUnloadTaskDto> newTasks);
	/**
	* @description 营业部中转到达车辆(不包含快递)
	* @param vehicle
	* @param limit
	* @param start
	* @return
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年6月25日 下午2:54:46
	 */
	List<AssignUnloadTaskTotalDto> querySalesDeptArriveTransferVehicle(AssignUnloadTaskTotalDto vehicle, int limit,
			int start);
	/**
	* @description 营业部商务专递(不包含快递)
	* @param vehicle
	* @param limit
	* @param start
	* @return
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年6月25日 下午2:54:46
	 */
	List<AssignUnloadTaskTotalDto> querySalesDeptArriveBusinessVehicle(AssignUnloadTaskTotalDto vehicle, int limit,
			int start);
	/**
	* @description 营业部中转到达车辆(不包含快递)
	* @param vehicle
	* @return
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年6月25日 下午3:53:58
	 */
	List<AssignUnloadTaskTotalDto> querySalesDeptArriveTransferVehicle(AssignUnloadTaskTotalDto vehicle);
	
	/**
	* @description 营业部商务专递(不包含快递)
	* @param vehicle
	* @param limit
	* @param start
	* @return
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年6月25日 下午2:54:46
	 */
	List<AssignUnloadTaskTotalDto> querySalesDeptArriveBusinessVehicle(AssignUnloadTaskTotalDto vehicle);
	
}