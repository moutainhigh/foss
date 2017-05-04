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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/server/service/IAssignUnloadTaskService.java
 *  
 *  FILE NAME          :IAssignUnloadTaskService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.unload.api.server.service;



import java.io.InputStream;
import java.util.List;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ArriveBillDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskTotalDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ExpressCancelAssignUnloadInstructDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.LoaderDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.TruckTaskDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.vo.AssignUnloadTaskVo;

public interface IAssignUnloadTaskService {
	public List<AssignUnloadTaskTotalDto> queryArriveVehicle(AssignUnloadTaskTotalDto vehicle);
	public List<AssignUnloadTaskTotalDto> queryArriveVehicle(AssignUnloadTaskTotalDto vehicle,int limit,int start);
	public InputStream exportAssignunloadtask(AssignUnloadTaskTotalDto vehicle);
	public InputStream exportAssignunloadedtask(AssignUnloadTaskDto task);
	public String queryArriveBillCount();
	public List<ArriveBillDto> queryArriveBill(ArriveBillDto bill);
	public List<LoaderDto> queryLoader(LoaderDto loader,int limit,int start);
	public Long getLoaderCount(LoaderDto loader);
	public int insertAssignUnloadTask(LoaderDto loader,AssignUnloadTaskTotalDto vehicle,List<ArriveBillDto> bills);
	public List<AssignUnloadTaskTotalDto> queryUnStartTask(int limit,int start);
	public Long queryUnStartTaskCount();
	public int cancelAssignUnloadTask(AssignUnloadTaskDto assignMsg);
	
	public List<AssignUnloadTaskTotalDto> queryAssignUnloadTask(AssignUnloadTaskDto task,int limit,int start);
	public Long queryAssignUnloadTaskCount(AssignUnloadTaskDto task);
	public List<ArriveBillDto> queryAssignUnloadTaskDetail(AssignUnloadTaskDto task);
	public AssignUnloadTaskDto refreshAssignedTaskDetail(AssignUnloadTaskDto assignMsg);
	public List<AssignUnloadTaskTotalDto> queryAssignUnloadedTask(AssignUnloadTaskDto task);
	//根据车牌号查询任务车辆id
	public String queryTruckTaskIdByVehicleNo(String vechicleNo);
	//根据数据字典valueCode查询valueName
	public String queryDictionaryValueNameByValueCode(String valueCode);
	//根据车牌号查询查询预分配月台号
	public String queryPrePlatformNo(String vechicleNo,String billNo);
	//根据单据编号，车牌号查询分配卸车任务 by wqh
	public AssignUnloadTaskTotalDto queryAssUnloadTaskByBillNo(String vechicleNo,String billNo);
	//根据 到达部门、单据编号，车牌号查询部分任务车辆信息by wqh
	public TruckTaskDetailDto queryTruckTaskDetailByBillNoAndVechicleNo(String vechicleNo,String billNo,String destOrgCode);
	//根据卸车任务id查询所有的交接单
	public List<String> queryHandNosByTruckTaskId(String truckTaskId);
	//FOSS同步取消分配卸车任务给悟空
	public void cancelAssignedUnloadTask(ExpressCancelAssignUnloadInstructDto cancelAssignUnloadInstructDto) throws Exception;
	//FOSS同步分配车辆任务给悟空
	public void assignUnloadTask(AssignUnloadTaskVo vo) throws Exception;
}