/**
 *  initial comments.
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
 *  
 *  
 *  
 *  
 *  
 *  
 *  
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
 *  PROJECT NAME  : tfr-load
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/service/impl/CreateLoaderWorkloadService.java
 *  
 *  FILE NAME          :CreateLoaderWorkloadService.java
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
 * PROJECT NAME: tfr-load
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server.service.impl
 * FILE    NAME: CreateLoaderWorkloadService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.server.service.IBusinessMonitorService;
import com.deppon.foss.module.base.common.api.shared.define.BusinessMonitorIndicator;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.ICreateLoaderWorkloadDao;
import com.deppon.foss.module.transfer.load.api.server.service.ICreateLoaderWorkloadService;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderWorkloadEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.BillAndPkgCountDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoaderParticipationDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoaderWorkloadDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 *生成装卸车工作量
 * @author dp-duyi
 * @date 2012-12-24 上午11:12:08
 */
public class CreateLoaderWorkloadService implements ICreateLoaderWorkloadService{
	private static final Logger LOGGER = LoggerFactory.getLogger(CreateLoaderWorkloadService.class);
	
	private ICreateLoaderWorkloadDao createLoaderWorkloadDao;
	public void setCreateLoaderWorkloadDao(
			ICreateLoaderWorkloadDao createLoaderWorkloadDao) {
		this.createLoaderWorkloadDao = createLoaderWorkloadDao;
	}
	private ICreateLoaderWorkloadService createLoaderWorkloadService;
	public void setCreateLoaderWorkloadService(
			ICreateLoaderWorkloadService createLoaderWorkloadService) {
		this.createLoaderWorkloadService = createLoaderWorkloadService;
	}
	private IEmployeeService employeeService;
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	private ITfrCommonService tfrCommonService;
	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}

	/**
	 * 应用监控服务
	 */
	private IBusinessMonitorService businessMonitorService;

	public void setBusinessMonitorService(
			IBusinessMonitorService businessMonitorService) {
		this.businessMonitorService = businessMonitorService;
	}
	
	/**
	 * 交接单Service
	 */
	private IHandOverBillService handOverBillService;
	
	/**   
	 * @param handOverBillService the handOverBillService to set
	 * Date:2013-7-18下午3:27:34
	 */
	public void setHandOverBillService(IHandOverBillService handOverBillService) {
		this.handOverBillService = handOverBillService;
	}
	
	/**
	 * 业务锁service
	 */
	private IBusinessLockService businessLockService;

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
	/** 
	 * 查询未生成工作量任务，生成装卸车工作量
	 * @author dp-duyi
	 * @date 2012-12-24 上午11:12:53
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ICreateLoaderWorkloadService#createLoaderWorkLoad()
	 */
	@Override
	//@Transactional
	public void createLoaderWorkLoad(Date bizJobStartTime,	Date bizJobEndTime,int threadNo, int threadCount) {
		while(true){
			List<LoaderWorkloadDetailDto> loadTasks = createLoaderWorkloadDao.queryUnCreateWorkLoadLoadTask(bizJobStartTime,bizJobEndTime, threadNo, threadCount);
			//sonar 为空判断 218427
			if(loadTasks==null){
				throw new TfrBusinessException("loadTasks为空");
			}
			int a=loadTasks.size();
			LOGGER.info("装卸车工作量: loadTasks.size="+loadTasks.size());
			List<LoaderWorkloadDetailDto> unloadTasks = createLoaderWorkloadDao.queryUnCreateWorkLoadUnloadTask(bizJobStartTime,bizJobEndTime, threadNo, threadCount);
			int b=unloadTasks.size();
		    int c=a+b;
			LOGGER.info("装卸车工作量: unloadTasks.size="+unloadTasks.size());
			List<LoaderWorkloadDetailDto> tasks = new ArrayList<LoaderWorkloadDetailDto>();
			if(c<=LoadConstants.SONAR_NUMBER_300){
			LOGGER.info("装卸车工作量: 小于三百条不启动job");
				return ;
			}
			LOGGER.info("装卸车工作量: 大于三百条启动开始job");
			if(CollectionUtils.isNotEmpty(loadTasks)){
				tasks.addAll(loadTasks);
			}
			if(CollectionUtils.isNotEmpty(unloadTasks)){
				tasks.addAll(unloadTasks);
			}
			for(LoaderWorkloadDetailDto task : tasks){
				try{
					MutexElement mutex = null;
					try {
						// 判空
						if (task!= null && task.getTaskNo() != null) {
							// 任务号
							String lockStr = task.getTaskNo();
							mutex = new MutexElement(lockStr, "装卸量统计", MutexElementType.TFR_LOADER_WORKLOAD_ADDNEW);
							// 锁定
							boolean flag = businessLockService.lock(mutex, 0);
							if (flag) {
								//统计工作量
								createLoaderWorkloadService.calculateWorkLoad(task);
								// 解锁
								businessLockService.unlock(mutex);
							} else {
								continue;
							}
						}
						// 成功
						
					} catch (TfrBusinessException e) {
						// 解锁
						businessLockService.unlock(mutex);
						// 返回错误信息
						LOGGER.error(e.toString());
						// 捕捉业务异常
					} catch (BusinessException e) {
						// 解锁
						businessLockService.unlock(mutex);
						// 返回错误信息
						// 返回错误信息
						LOGGER.error(e.getMessage());
					} // 捕捉异常
					catch (Exception e) {
						// 解锁
						businessLockService.unlock(mutex);
						// 返回错误信息
						// 返回错误信息
						LOGGER.error(e.toString(), "");
					}
					
					//监控月台吞吐量
					UserEntity user = null;
					OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
					dept.setCode(task.getOrgCode());
					CurrentInfo currentInfo = new CurrentInfo(user,dept);
					Map<BusinessMonitorIndicator, Number> map = new HashMap<BusinessMonitorIndicator, Number>();
					BusinessMonitorIndicator platforUseIndicator = BusinessMonitorIndicator.PLATFORM_USE_LABEL_COUNT;
					Number goodsQty = task.getTotGoodsQty();
					map.put(platforUseIndicator, goodsQty);
					businessMonitorService.counter(map, currentInfo);
				}catch(BusinessException e){
					LOGGER.error(e.getMessage());
					
					TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
					jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.LOADER_WORKLOAD.getBizName());
					jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.LOADER_WORKLOAD.getBizCode());
					jobProcessLogEntity.setExecBizId(task.getId());
	//				jobProcessLogEntity.setExecTableName("TFR.T_OPT_ST_RESULT_LIST");
					jobProcessLogEntity.setRemark("createLoaderWorkloadService.calculateWorkLoad计算每个任务工作量失败");
					jobProcessLogEntity.setExceptionInfo(e.getMessage());
					jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
					
					tfrCommonService.addJobProcessLog(jobProcessLogEntity);
				}
			}
		}
	}
	/** 
	 * 计算每个任务工作量
	 * @author dp-duyi
	 * @date 2012-12-24 上午11:12:53
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ICreateLoaderWorkloadService#createLoaderWorkLoad()
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int calculateWorkLoad(LoaderWorkloadDetailDto task){
		List<LoaderParticipationDto> loaderParticipations;
		List<BillAndPkgCountDto> bpList;
		String taskNo = task.getTaskNo();
		String handoverNo = "";
		String vehicleNo = "";
		if(LoadConstants.LOADER_WORKLOAD_HANDLE_TYPE_LOAD.equals(task.getHandleType())){
			loaderParticipations = createLoaderWorkloadDao.queryLoadParticipationDtoByTaskId(task.getId());
			if(loaderParticipations==null){
				//判断当前任务ID是否返回结果如果返回为空说明不符合业务规则更改其任务状态为W
				createLoaderWorkloadDao.updateunCreateLoadTask(task.getId());
				return 0;
			}
			//根据id获取理货员当前任务的所包含的包数,票数,件数
		    bpList =  createLoaderWorkloadDao.queryBillAndPkgCountByTaskID(task.getId());
			//根据装车任务编号获取交接单信息, 目前装车任务编号与交接单是一对一的关系
			// 272681  装车任务编号与交接单一对多
		 // 272681  装车任务编号与交接单一对多
			String handOverBills = handOverBillService.queryHandOverBillNoByLoadTaskNo(taskNo);
			vehicleNo = task.getVehicleNo();
			if(handOverBills == null) {
				handoverNo = "N/A";         
			} else {
				handoverNo = handOverBills;
			}
		}else{
			//卸车货物类型：AB货物
			String billNos = createLoaderWorkloadDao.queryBillNosByTaskNo(taskNo);
			vehicleNo = task.getVehicleNo();
			handoverNo = billNos;
			task.setGoodsType(this.getUnloadGoodsTypeByTaskId(task.getId()));
			loaderParticipations = createLoaderWorkloadDao.queryUnloadParticipationDtoByTaskId(task.getId());
			if(loaderParticipations==null){
				createLoaderWorkloadDao.updateunCreateUnLoadTask(task.getId());
				return 0;
			}
			//根据id获取理货员当前任务的所包含的包数和票数
			bpList =  createLoaderWorkloadDao.queryUnLoadBillAndPkgCountByTaskID(task.getId());
		}
		//272681 快递与零担
		List<LoaderWorkloadEntity> workloads = new ArrayList<LoaderWorkloadEntity>();
		LoaderWorkloadEntity expressWorkLoad;
		LoaderWorkloadEntity ldWorkLoad;
		String taskId = task.getId();
		String taskType = task.getTaskType();
		String handlerType = task.getHandleType();
		long participationTime;
		long participationTotalTime;
		EmployeeEntity employee;
		if(CollectionUtils.isEmpty(loaderParticipations)) {
			LOGGER.error("loaderParticipations is empty taskId " + task.getId());
		}
		for(LoaderParticipationDto loader : loaderParticipations){
			LOGGER.error(task.getId());
		
			if(loader == null) {
				continue;
			}
			//272681 快递货
			if(loader.getExpressOrLd().equals("Y")){
				
			if(loader.getLeaveTime() == null) {
				//BUG-53607 没有离开时间没法统计货量，没有离开时间设置离开时间 
				loader.setLeaveTime(DateUtils.getEndTimeAdd(loader.getJoinTime()));
				LOGGER.error("loader.getLeaveTime() is null taskId " + task.getId());
			}
			if(loader.getJoinTime() == null) {
				LOGGER.error("loader.getJoinTime() is null taskId " + task.getId());
			}
			participationTime = loader.getLeaveTime().getTime() - loader.getJoinTime().getTime();
			participationTotalTime = participationTime;
			expressWorkLoad = new LoaderWorkloadEntity();
			for(LoaderParticipationDto temploader : loaderParticipations){
				if(temploader.getExpressOrLd().equals("Y")){
				if(!(temploader.getLeaveTime().before(loader.getJoinTime()) ||temploader.getLeaveTime().equals(loader.getJoinTime())|| loader.getLeaveTime().before(temploader.getJoinTime())||loader.getLeaveTime().equals(temploader.getJoinTime()))){
					if(!loader.getLoaderCode().equals(temploader.getLoaderCode())){
						if(temploader.getJoinTime().before(loader.getJoinTime())){
							if(temploader.getLeaveTime().before(loader.getLeaveTime())){
								participationTotalTime = participationTotalTime + temploader.getLeaveTime().getTime() - loader.getJoinTime().getTime();
							}else{
								participationTotalTime = participationTotalTime + participationTime;
							}
						}else{
							if(temploader.getLeaveTime().before(loader.getLeaveTime())){
								participationTotalTime = participationTotalTime + temploader.getLeaveTime().getTime() - temploader.getJoinTime().getTime();
							}else{
								participationTotalTime = participationTotalTime + loader.getLeaveTime().getTime() - temploader.getJoinTime().getTime();
							}
						}
					}
				}
			}
			}
			for(BillAndPkgCountDto bpDto:bpList){
				if(bpDto.getPackageCount()>0){
						if(bpDto.getId().equals(loader.getId())){
							LOGGER.info("工作量="+loader.getExpressWaybillQty().intValue()+"票数="+bpDto.getWayBillCount()+"包数="+bpDto.getPackageCount());
							BigDecimal bigDp = new BigDecimal(loader.getExpressWaybillQty().intValue()-
									bpDto.getWayBillCount()+bpDto.getPackageCount());
							BigDecimal bigDp2 = new BigDecimal(loader.getExpressGoodsQty().intValue()-
									bpDto.getGoodsCount()+bpDto.getPackageCount());
							LOGGER.info("WaybillQty="+bigDp+"GoodsQty="+bigDp2);
							loader.setExpressWaybillQty(bigDp);
							loader.setExpressGoodsQty(bigDp2);
						}
				}
			}
			//272681 快递
			expressWorkLoad.setId(UUIDUtils.getUUID());
			expressWorkLoad.setHandleType(handlerType);
			expressWorkLoad.setJoinTime(loader.getJoinTime());
			expressWorkLoad.setLeaveTime(loader.getLeaveTime());
			expressWorkLoad.setLoaderCode(loader.getLoaderCode());
			expressWorkLoad.setLoaderName(loader.getLoaderName());
			expressWorkLoad.setLoadOrgCode(loader.getLoadOrgCode());
			expressWorkLoad.setLoadOrgName(loader.getLoadOrgName());
			expressWorkLoad.setOrgCode(loader.getOrgCode());
			expressWorkLoad.setOrgName(loader.getOrgName());
			expressWorkLoad.setTaskId(taskId);
			expressWorkLoad.setTaskNo(taskNo);
			expressWorkLoad.setTaskType(taskType);
			expressWorkLoad.setGoodsType(task.getGoodsType());
			expressWorkLoad.setHandoverNo(handoverNo);
			expressWorkLoad.setVehicleNo(vehicleNo);
			expressWorkLoad.setExpressOrLd(LoadConstants.EXPRESS);
		
			employee = employeeService.queryEmployeeByEmpCode(loader.getLoaderCode());
			//调用综合接口查询理货员名称、理货员所属装卸车队
			if(employee != null){
				loader.setLoaderName(employee.getEmpName());
				if(employee.getDepartment()!=null){
					expressWorkLoad.setLoaderOrgCode(employee.getDepartment().getCode());
					expressWorkLoad.setLoaderOrgName(employee.getDepartment().getName());
				}
			}
			BigDecimal partTime = BigDecimal.valueOf(participationTime);
			BigDecimal partTotalTime =  BigDecimal.valueOf(participationTotalTime);
			if(BigDecimal.ZERO.compareTo(partTotalTime)==-1){
				expressWorkLoad.setVolume((loader.getExpressVolume().multiply(partTime)).divide(partTotalTime,2,BigDecimal.ROUND_UP));
				expressWorkLoad.setWeight((loader.getExpressWeight().multiply(partTime)).divide(partTotalTime,2,BigDecimal.ROUND_UP));
				
				BigDecimal expressGoodsQty = (loader.getExpressGoodsQty().multiply(partTime)).divide(partTotalTime,2,BigDecimal.ROUND_UP);
				BigDecimal expressWayBillQty = (loader.getExpressWaybillQty().multiply(partTime).divide(partTotalTime,2,BigDecimal.ROUND_UP));

				
				expressWorkLoad.setGoodsQty(expressGoodsQty.intValue());
				expressWorkLoad.setWaybillQty(expressWayBillQty.intValue());

				}else{
	            expressWorkLoad.setVolume(BigDecimal.ZERO);
	            expressWorkLoad.setWeight(BigDecimal.ZERO);
	            expressWorkLoad.setGoodsQty(0);
	            expressWorkLoad.setWaybillQty(0);
			}
			expressWorkLoad.setCreateTime(new Date());
			workloads.add(expressWorkLoad);
			}else{
				//272681 零担货
				if(loader.getLeaveTime() == null) {
					//BUG-53607 没有离开时间没法统计货量，没有离开时间设置离开时间 
					loader.setLeaveTime(DateUtils.getEndTimeAdd(loader.getJoinTime()));
					LOGGER.error("loader.getLeaveTime() is null taskId " + task.getId());
				}
				if(loader.getJoinTime() == null) {
					LOGGER.error("loader.getJoinTime() is null taskId " + task.getId());
				}
				participationTime = loader.getLeaveTime().getTime() - loader.getJoinTime().getTime();
				participationTotalTime = participationTime;
				ldWorkLoad = new LoaderWorkloadEntity();
				for(LoaderParticipationDto temploader : loaderParticipations){
					if(temploader.getExpressOrLd().equals("N")){
					if(!(temploader.getLeaveTime().before(loader.getJoinTime()) ||temploader.getLeaveTime().equals(loader.getJoinTime())|| loader.getLeaveTime().before(temploader.getJoinTime())||loader.getLeaveTime().equals(temploader.getJoinTime()))){
						if(!loader.getLoaderCode().equals(temploader.getLoaderCode())){
							if(temploader.getJoinTime().before(loader.getJoinTime())){
								if(temploader.getLeaveTime().before(loader.getLeaveTime())){
									participationTotalTime = participationTotalTime + temploader.getLeaveTime().getTime() - loader.getJoinTime().getTime();
								}else{
									participationTotalTime = participationTotalTime + participationTime;
								}
							}else{
								if(temploader.getLeaveTime().before(loader.getLeaveTime())){
									participationTotalTime = participationTotalTime + temploader.getLeaveTime().getTime() - temploader.getJoinTime().getTime();
								}else{
									participationTotalTime = participationTotalTime + loader.getLeaveTime().getTime() - temploader.getJoinTime().getTime();
								}
							}
						}
					}
				}
				}
				//272681 零担
				ldWorkLoad.setId(UUIDUtils.getUUID());
				ldWorkLoad.setHandleType(handlerType);
				ldWorkLoad.setJoinTime(loader.getJoinTime());
				ldWorkLoad.setLeaveTime(loader.getLeaveTime());
				ldWorkLoad.setLoaderCode(loader.getLoaderCode());
				ldWorkLoad.setLoaderName(loader.getLoaderName());
				ldWorkLoad.setLoadOrgCode(loader.getLoadOrgCode());
				ldWorkLoad.setLoadOrgName(loader.getLoadOrgName());
				ldWorkLoad.setOrgCode(loader.getOrgCode());
				ldWorkLoad.setOrgName(loader.getOrgName());
				ldWorkLoad.setTaskId(taskId);
				ldWorkLoad.setTaskNo(taskNo);
				ldWorkLoad.setTaskType(taskType);
				ldWorkLoad.setGoodsType(task.getGoodsType());
				ldWorkLoad.setHandoverNo(handoverNo);
				ldWorkLoad.setVehicleNo(vehicleNo);
				ldWorkLoad.setExpressOrLd(LoadConstants.LD);
		
				employee = employeeService.queryEmployeeByEmpCode(loader.getLoaderCode());
				//调用综合接口查询理货员名称、理货员所属装卸车队
				if(employee != null){
					loader.setLoaderName(employee.getEmpName());
					if(employee.getDepartment()!=null){
						ldWorkLoad.setLoaderOrgName(employee.getDepartment().getName());
						ldWorkLoad.setLoaderOrgCode(employee.getDepartment().getCode());
					}
				}
				BigDecimal partTime = BigDecimal.valueOf(participationTime);
				BigDecimal partTotalTime =  BigDecimal.valueOf(participationTotalTime);
				if(BigDecimal.ZERO.compareTo(partTotalTime)==-1){
					ldWorkLoad.setVolume((loader.getLdVolume().multiply(partTime)).divide(partTotalTime,2,BigDecimal.ROUND_UP));
					ldWorkLoad.setWeight((loader.getLdWeight().multiply(partTime)).divide(partTotalTime,2,BigDecimal.ROUND_UP));
					
					BigDecimal ldGoodsQty = (loader.getLdGoodsQty().multiply(partTime)).divide(partTotalTime,2,BigDecimal.ROUND_UP);
					BigDecimal ldWayBillQty = (loader.getLdWaybillQty().multiply(partTime).divide(partTotalTime,2,BigDecimal.ROUND_UP));
				
					ldWorkLoad.setGoodsQty(ldGoodsQty.intValue());
					ldWorkLoad.setWaybillQty(ldWayBillQty.intValue());
					}else{
		            ldWorkLoad.setVolume(BigDecimal.ZERO);
		            ldWorkLoad.setWeight(BigDecimal.ZERO);
		            ldWorkLoad.setGoodsQty(0);
		            ldWorkLoad.setWaybillQty(0);
				}
				ldWorkLoad.setCreateTime(new Date());
			    workloads.add(ldWorkLoad);
			}	
		}
		if(CollectionUtils.isNotEmpty(workloads)){
			 createLoaderWorkloadDao.insertWorkLoad(workloads);
		}
		if(LoadConstants.LOADER_WORKLOAD_HANDLE_TYPE_LOAD.equals(task.getHandleType())){
			createLoaderWorkloadDao.updateLoadTaskBeCreateWorkLoad(task.getId(), FossConstants.YES);
		}else{
			createLoaderWorkloadDao.updateUnloadTaskBeCreateWorkLoad(task.getId(), FossConstants.YES);
		}
		return 0;
	}
	private String getUnloadGoodsTypeByTaskId(String unloadTaskId){
		List<String> unloadBillGoodTypes = createLoaderWorkloadDao.queryUnloadGoodsType(unloadTaskId);
		if(CollectionUtils.isNotEmpty(unloadBillGoodTypes)){
			String goodsType = unloadBillGoodTypes.get(0);
			if(StringUtils.isBlank(goodsType)){
				return TransferPDADictConstants.LOAD_GOODS_TYPE_ALL;
			}else{
				if(!TransferPDADictConstants.LOAD_GOODS_TYPE_ALL.equals(goodsType)){
					for(String billGoodsType : unloadBillGoodTypes){
						if(!goodsType.equals(billGoodsType)){
							return TransferPDADictConstants.LOAD_GOODS_TYPE_ALL;
						}
					}
				}
				return goodsType;
			}
		}else{
			return TransferPDADictConstants.LOAD_GOODS_TYPE_ALL;
		}
		
	}

}