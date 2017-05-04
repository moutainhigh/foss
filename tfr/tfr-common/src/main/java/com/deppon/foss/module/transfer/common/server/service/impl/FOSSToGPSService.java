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
 *  PROJECT NAME  : tfr-common
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/server/service/impl/FOSSToGPSService.java
 *  
 *  FILE NAME          :FOSSToGPSService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-gps-itf
 * PACKAGE NAME: com.deppon.foss.module.transfer.gps.server.service.impl
 * FILE    NAME: FOSSToGPSServiceImpl.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.common.server.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.xml.ws.Holder;

import org.apache.commons.lang.StringUtils;
import org.example.deppon_gps_service.DepponGpsService;
import org.example.deppon_gps_service.domain.QueryTransmitVehicleRequest;
import org.example.deppon_gps_service.domain.QueryTransmitVehicleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.header.ESBHeader;
import com.deppon.foss.framework.server.context.WebApplicationContextHolder;
import com.deppon.foss.inteface.domain.vehlinetask.ExBill;
import com.deppon.foss.inteface.domain.vehlinetask.SyncVehLineTaskRequest;
import com.deppon.foss.inteface.domain.vehlinetask.VehLineTask;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToGPSService;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.dto.TaskVehicleDto;
import com.deppon.foss.module.transfer.common.server.utils.UnifiedCodeConvertUtils;
import com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService;
import com.deppon.foss.module.transfer.load.api.shared.dto.TruckTaskHandOverDto;


/**
 * 上传任务车辆信息实现类
 * 
 * @author 046130-foss-xuduowei
 * @date 2012-11-16 下午6:00:09
 */
public class FOSSToGPSService implements IFOSSToGPSService {
	/**
	 * GPS接口
	 */
	private DepponGpsService tfrDepponGpsService;
	/**
	 * 编码转换工具
	 */
	private UnifiedCodeConvertUtils unifiedCodeConvertUtils;

	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FOSSToGPSService.class);

	/**
	 * 消息格式
	 */
	private static final String MESSAGE_FORMAT = "SOAP";

	/**
	 * 源系统
	 */
	private static final String SOURCE_SYSTEM = "FOSS";

	private ITruckTaskService truckTaskService;
	
	public void setTruckTaskService(ITruckTaskService truckTaskService) {
		this.truckTaskService = truckTaskService;
	}

	public ITruckTaskService getTruckTaskService() {
		return truckTaskService;
	}

	/**
	 * 上传任务车辆信息--长途车辆任务
	 * 
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-16 下午6:00:09
	 * @see com.deppon.foss.module.transfer.gps.server.service.
	 *      IFOSSToGPSService#notifyTaskVehicleInfo(com.deppon.foss.module.
	 *      transfer.common.api.shared.dto.gps.server.dto.TaskVehicleDto)
	 */
	@Override
	public boolean notifyTaskVehicleInfo(TaskVehicleDto taskVehicleDto) {
		// 判断任务是否为空
		if (taskVehicleDto == null) {
			return false;
		}
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		QueryTransmitVehicleRequest queryTransmitVehicleRequest = new QueryTransmitVehicleRequest();	
		// 到达部门
		queryTransmitVehicleRequest.setArrivalDept(unifiedCodeConvertUtils.convertDeptCodeToUnifiedCode(taskVehicleDto.getArrivalDept()));
		// 是否删除
		queryTransmitVehicleRequest.setIsDeleted(taskVehicleDto.getIsDeleted());
		// 出发部门
		queryTransmitVehicleRequest.setStartDept(unifiedCodeConvertUtils.convertDeptCodeToUnifiedCode(taskVehicleDto.getStartDept()));
		// 出发时间
		if(StringUtils.isEmpty(taskVehicleDto.getStartTime())){
			queryTransmitVehicleRequest.setStartTime(df.format(new Date()));
		}else{
			queryTransmitVehicleRequest.setStartTime(taskVehicleDto.getStartTime());
		}
		// 唯一识别号
		queryTransmitVehicleRequest.setVehicleId(taskVehicleDto.getVehicleId());
		// 车牌号
		queryTransmitVehicleRequest.setVehicleNo(taskVehicleDto.getVehicleNo());
		String isDelete = taskVehicleDto.getIsDeleted() + "";
		//新增
		if(StringUtils.equals(isDelete, "0")){
			// 重量
			queryTransmitVehicleRequest.setWeight(taskVehicleDto.getWeight());
			// 体积
			queryTransmitVehicleRequest.setCubage(taskVehicleDto.getCubage());
		}else{
			//如果是删除，由于采用外关联方式，此时重量和体积为空，则为重量和体积设置特定值
			// 重量
			queryTransmitVehicleRequest.setWeight(1);
			// 体积
			queryTransmitVehicleRequest.setCubage(1);
		}
		
		// 设置头信息
		ESBHeader esbHeader = new ESBHeader();
		//服务编码
		esbHeader.setEsbServiceCode(TransferConstants.GPS_TASK_VEHICLE_SERVICE_CODE);
		//头版本
		esbHeader.setVersion(TransferConstants.ESB_ESBHEADER_VERSION);
		//交换模式
		esbHeader.setExchangePattern(TransferConstants.ESB_EXCHANGEPATTERN);
		//消息格式
		esbHeader.setMessageFormat(MESSAGE_FORMAT);
		esbHeader.setRequestId(UUID.randomUUID().toString());
		//源系统
		esbHeader.setSourceSystem(SOURCE_SYSTEM);
		//业务属性id
		esbHeader.setBusinessId(taskVehicleDto.getVehicleNo());
		//扩展1
		esbHeader.setBusinessDesc1(taskVehicleDto.getStartTime());
		Holder<ESBHeader> holder = new Holder<ESBHeader>();
		holder.value = esbHeader;
		try {
			//记录日志
			LOGGER.info("开始调用长途GPS接口，传入车牌号：" + queryTransmitVehicleRequest.getVehicleNo()+
					",出发部门:"+queryTransmitVehicleRequest.getStartDept()+",到达部门:"+queryTransmitVehicleRequest.getArrivalDept());
			// 调用GPS接口
			QueryTransmitVehicleResponse response = tfrDepponGpsService.queryTransmitVehicle(
					queryTransmitVehicleRequest, holder);
			LOGGER.info("调用长途GPS接口结束");
			return response.isResult();
		} catch (Exception e) {
			LOGGER.error("notifyTaskVehicleInfo", e);
			return false;
		}
	}
	
	/**
	 * 
	 * 同步短途发车任务给GPS
	 * @author alfred
	 * @date 2014-3-7 上午11:10:04
	 * @param taskVehicleDto
	 * @return 
	 * @see com.deppon.foss.module.transfer.common.api.server.service.IFOSSToGPSService#synchronousTaskVehicleInfo(com.deppon.foss.module.transfer.common.api.shared.dto.TaskVehicleDto)
	 */
	@Override
	public boolean synchronousTaskVehicleInfo(TaskVehicleDto taskVehicleDto) {
		// 判断任务是否为空
		if (taskVehicleDto == null) {
			return false;
		}
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		VehLineTask vehLineTask = new VehLineTask();
		SyncVehLineTaskRequest syncVehLineTaskRequest = new SyncVehLineTaskRequest();
		ExBill exBill = new ExBill();
		List<ExBill> exBills = new  ArrayList<ExBill>();
		//gps任务id
		vehLineTask.setTaskNum(taskVehicleDto.getVehicleId());
		//车牌号
		vehLineTask.setVehicleId(taskVehicleDto.getVehicleNo());
		//司机工号
		vehLineTask.setDriverCode(taskVehicleDto.getDriverCode());
		//司机姓名
		vehLineTask.setDriverName(taskVehicleDto.getDirverName());
		//到达部门标杆编码
		vehLineTask.setDestDeptCode(unifiedCodeConvertUtils.convertDeptCodeToUnifiedCode(taskVehicleDto.getArrivalDept()));
		//出发部门标杆编码
		vehLineTask.setOrigDeptCode(unifiedCodeConvertUtils.convertDeptCodeToUnifiedCode(taskVehicleDto.getStartDept()));
		//预计发车时间
		if(StringUtils.isNotEmpty(taskVehicleDto.getStartTime())){
			try {
				vehLineTask.setPlanDepartTime(df.parse(taskVehicleDto.getStartTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else{
			vehLineTask.setPlanDepartTime(new Date());
		}
		//虚拟路线编码
		vehLineTask.setLineCode(taskVehicleDto.getLineCode());
		//操作标识，1-更新(如果没有则新增，否则修改), 3-删除
		vehLineTask.setOperationFlag(1);
		//查询车辆任务对应交接单
		ITruckTaskService truckTaskService = (ITruckTaskService) WebApplicationContextHolder.getWebApplicationContext().getBean("truckTaskService");
		List<TruckTaskHandOverDto> handOverBills = truckTaskService.queryhandOverBillBytruckDetailId(taskVehicleDto.getTruckTaskId());
		for(TruckTaskHandOverDto handOverBill:handOverBills){
			//交接单号
			exBill.setDeliveryReceitpNo(handOverBill.getHandOverBillNo());
			//总件数
			exBill.setPackageCount(handOverBill.getGoodsQtyTotal());
			//总票数
			exBill.setWaybillCount(handOverBill.getWayBillQtyTotal());
			//总重量
			exBill.setWeight(handOverBill.getWeightTotal());
			//总体积
			exBill.setVolume(handOverBill.getVolumeTotal());
			exBills.add(exBill);
		}
		//交接单集合
		vehLineTask.getExBills().addAll(exBills);
		syncVehLineTaskRequest.getVehLineTasks().add(vehLineTask);
		
		// 设置头信息
		AccessHeader header = new AccessHeader();
		//服务编码
		header.setEsbServiceCode(TransferConstants.GPS_SYNC_VEHTASK_CODE);
		//版本
		header.setVersion(TransferConstants.ESB_ESBHEADER_VERSION);
		header.setBusinessId(taskVehicleDto.getVehicleNo());
		try {
			//记录日志
			LOGGER.error("开始调用短途GPS接口，传入车牌号：" + taskVehicleDto.getVehicleNo()+
					",出发部门:"+taskVehicleDto.getStartDept()+",到达部门:"+taskVehicleDto.getStartDept());
			// 调用GPS接口
			ESBJMSAccessor.asynReqeust(header, syncVehLineTaskRequest);
			LOGGER.info("调用短途GPS接口结束");
			return true;
		} catch (Exception e) {
			LOGGER.error("synchronousTaskVehicleInfo", e);
			return false;
		}
	}
	
	/**
	 * 获取 gPS接口.
	 * 
	 * @return the gPS接口
	 */
	public DepponGpsService getTfrDepponGpsService() {
		return tfrDepponGpsService;
	}

	/**
	 * 设置 gPS接口.
	 * 
	 * @param tfrDepponGpsService
	 *            the new gPS接口
	 */
	public void setTfrDepponGpsService(DepponGpsService tfrDepponGpsService) {
		this.tfrDepponGpsService = tfrDepponGpsService;
	}

	public void setUnifiedCodeConvertUtils(
			UnifiedCodeConvertUtils unifiedCodeConvertUtils) {
		this.unifiedCodeConvertUtils = unifiedCodeConvertUtils;
	}
    

}