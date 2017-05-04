/**
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
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
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 * 
 *  you may not use this file except in compliance with the License.
 *  
 *  You may obtain a copy of the License at
 *  
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  
 *  Unless required by applicable law or agreed to in writing, software
 * 
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  
 *  See the License for the specific language governing permissions and
 *  
 *  limitations under the License.
 *  
 *  Contributors:
 *  
 *  
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-load
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/service/impl/PDACommonService.java
 *  
 *  FILE NAME          :PDACommonService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/**
 * PROJECT NAME: tfr-load
 * 
 * 
 * 
 * 
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server.service.impl
 * 
 * 
 * FILE    NAME: PDACommonService.java
 * 
 * 
 * 
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 * 
 * 
 * PDA共通类
 * 
 * PDA添加/删除理货员	【装车理货员列表】	调用FOSS接口，将理货员加入/离开信息同步至FOSS
 * 
 * 
 * 
 * 
 * 
 * 业务规则：
 * 
 * 
 * 
 * 
 * SR-1	通过理货员账号查询对应的派送单信息，只能查询分配给本账号的派送单信息
 *
 *
 *
 * SR-2	返回的任务列表按派送单生成时间+序号升序排序
 *
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
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
package com.deppon.foss.module.transfer.load.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILoadAndUnloadSquadService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPorterService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadSquadEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PorterEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao;
import com.deppon.foss.module.transfer.load.api.server.service.IPDACommonService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.MakeUpWaybillEntity;
import com.deppon.foss.module.transfer.load.server.dao.impl.PDALoadDao;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoaderDto;
import com.deppon.foss.module.transfer.pda.api.shared.exception.TransferPDAExceptionCode;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IPlatformDispatchService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PlatformDistributeEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 装卸车PDA共通service.
 *
 * @author dp-duyi
 * @date 2013-1-14 上午9:49:19
 */
public class PDACommonService implements IPDACommonService{
	static final Logger LOGGER = LoggerFactory.getLogger(PDACommonService.class);
	/**
	 *  pda装车dao. 
	 */
	private IPDALoadDao pdaLoadDao;
	private ISaleDepartmentService saleDepartmentService;
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	private IPorterService porterService;
	private ILoadAndUnloadSquadService loadAndUnloadSquadService;
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	private IEmployeeService employeeService;
	private IPlatformDispatchService platformDispatchService;
	
	
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	/**
	 * Sets the pda load dao.
	 *
	 * @param pdaLoadDao the new pda load dao
	 */
	public void setPdaLoadDao(PDALoadDao pdaLoadDao) {
		this.pdaLoadDao = pdaLoadDao;
	}
	public IPDACommonService pdaCommonService;
	
	public void setPdaCommonService(IPDACommonService pdaCommonService) {
		this.pdaCommonService = pdaCommonService;
	}
	/** The porter service. */
	
	
	/**
	 * Sets the porter service.
	 *
	 * @param porterService the new porter service
	 */
	public void setPorterService(IPorterService porterService) {
		this.porterService = porterService;
	}
	
	/** 
	 * The load and unload squad service. 
	 */
	
	
	/**
	 * Sets the load and unload squad service.
	 *
	 * @param loadAndUnloadSquadService the new load and unload squad service
	 */
	public void setLoadAndUnloadSquadService(
			ILoadAndUnloadSquadService loadAndUnloadSquadService) {
		this.loadAndUnloadSquadService = loadAndUnloadSquadService;
	}
	
	/** 
	 * The org administrative info complex service. 
	 */
	
	/**
	 * Sets the org administrative info complex service.
	 *
	 * @param orgAdministrativeInfoComplexService the new org administrative info complex service
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	
	/** 
	 * The employee service. 
	 */
	
	
	/**
	 * Sets the employee service.
	 *
	 * @param employeeService the new employee service
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	/**
	 * 月台Service接口
	 * 
	 * 
	 */
	/**
	 * 设置月台服务
	 *
	 *
	 * @param platformDispatchService 
	 */
	public void setPlatformDispatchService(IPlatformDispatchService platformDispatchService) {
		this.platformDispatchService = platformDispatchService;
	}
	/**
	 * 中途添加/删除理货员
	 * 
	 * @param taskId the task id
	 * 
	 * @param loaderTaskType the loader task type
	 * 
	 * @param loaderCodes the loader codes
	 * 
	 * @param operateTime the operate time
	 * 
	 * @param loaderState the loader state
	 * 
	 * @return the int
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2013-1-14 上午9:59:43
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IPDACommonService#modifyLoader(java.lang.String, java.util.List, java.util.Date, java.lang.String)
	 */
	@Override
	public int modifyLoader(String taskId,String loaderTaskType,List<LoaderDto> loaderCodes,Date operateTime,String loaderState) {
		try{
			//插入列表
			List<LoaderParticipationEntity> insertLoaders = new ArrayList<LoaderParticipationEntity>();
			//更新列表
			List<LoaderParticipationEntity> updateLoaders = new ArrayList<LoaderParticipationEntity>();
			LoaderParticipationEntity loader;
			List<LoaderParticipationEntity> loaderParticipations;
			PorterEntity porter;
			if(CollectionUtils.isNotEmpty(loaderCodes)){
				//遍历
				for(LoaderDto loadertemp : loaderCodes){
					loader = new LoaderParticipationEntity();
					//查询理货员
					loaderParticipations = pdaLoadDao.queryLoaderParticipation(taskId, loadertemp.getLoaderCode(),loadertemp.getFlag());
					if(CollectionUtils.isNotEmpty(loaderParticipations)){
						loader = loaderParticipations.get(0);
						//增加理货员
						if(TransferPDADictConstants.ADD_LOADR.equals(loaderState)){
							//加入时间
							loader.setJoinTime(operateTime);
						}else{
							//离开时间
							loader.setLeaveTime(operateTime);
						}
						updateLoaders.add(loader);
					}else{
						//设置 理货员工号
						loader.setLoaderCode(loadertemp.getLoaderCode());
						//设置 任务ID
						loader.setTaskId(taskId);
						//设置id 
						loader.setId(UUIDUtils.getUUID());
						if(TransferPDADictConstants.ADD_LOADR.equals(loaderState)){
							//加入时间
							loader.setJoinTime(operateTime);
						}else{
							//离开时间
							loader.setLeaveTime(operateTime);
						}
						//设置 类型
						loader.setTaskType(loaderTaskType);
						//Sets the 标识
						loader.setFlag(loadertemp.getFlag());
						//设置 是否建立任务理货员
						loader.setBeCreator(FossConstants.NO);
						//根据编码查询
						porter = porterService.queryPorterByEmpCode(loader.getLoaderCode());
						//调用综合接口查询理货员名称、理货员所属装卸车队
						if(porter != null){
							loader.setLoaderName(porter.getEmpName());
							if(StringUtils.isNotBlank(porter.getParentOrgCode())){
								loader.setLoadOrgCode(porter.getParentOrgCode());
								LoadAndUnloadSquadEntity team = loadAndUnloadSquadService.queryLoadAndUnloadSquadByCode(porter.getParentOrgCode());
								if(team != null){
									loader.setLoadOrgName(team.getName());
								}
							}
						}else{
							EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(loader.getLoaderCode());
							if(emp != null){
								loader.setLoaderName(emp.getEmpName());
							}else{
								throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_LOADER_MESSAGECODE);
							}
						}
						insertLoaders.add(loader);
					}
				}
			}
			if(CollectionUtils.isNotEmpty(insertLoaders)){
				pdaLoadDao.insertTransferLoaderParticipation(insertLoaders);
			}
			if(CollectionUtils.isNotEmpty(updateLoaders)){
				pdaLoadDao.updateLoaderParticipationByLoader(updateLoaders);
			}
			int addCount = 0;
			if(TransferPDADictConstants.ADD_LOADR.equals(loaderState)){
				addCount = loaderCodes.size();
			}
			return addCount;
		}catch(Exception e){
			LOGGER.error("任务类型:"+loaderTaskType+" 任务ID:"+taskId+" "+loaderState+"理货员失败",e);
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_PROCEDURE_ERROR_MESSAGECODE);
		}
	}
	
	/**
	 * 获取部门所属外场编码.
	 *
	 *
	 * @param orgCode the org code
	 * 
	 * 
	 * @return the current outfield code
	 * 
	 * @author 042795-foss-duyi
	 * 
	 * @date 2012-10-15 下午8:12:53
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IAssignLoadTaskService#queryDeliverBill(com.deppon.foss.module.transfer.load.api.shared.dto.DeliverBillQueryConditionDto, int, int)
	 */
	@Override
	public OrgAdministrativeInfoEntity getCurrentOutfieldCode(String orgCode){
				List<String> bizTypes = new ArrayList<String>();
				bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
				try{
					OrgAdministrativeInfoEntity org = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(orgCode,bizTypes);
					if(org != null){
						if(StringUtils.isNotBlank(org.getCode())){
							return org;
						}else{
							return null;
						}
					}else{
						return null;//部门信息为空
					}
				}catch(BusinessException e){
					return null;
				}		
	}

	/** 
	 * 新增任务时更新月台状态
	 * @author dp-duyi
	 * @date 2013-4-22 下午6:43:08
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IPDACommonService#updatePlatformStateByCreateTask(com.deppon.foss.module.transfer.scheduling.api.shared.domain.PlatformDistributeEntity)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void updatePlatformStateByCreateTask(
			String platformNo,String vehiclNo,String taskNo,String orgCode,String type,String useType,Date starTime,Date perEndTime) {
			PlatformDistributeEntity distributeEntity = new PlatformDistributeEntity();
			//月台虚拟code
			distributeEntity.setPlatformNo(platformNo);
			//车牌号
			distributeEntity.setVehicleNo(vehiclNo);
			//卸车任务编号
			distributeEntity.setLoadTaskNo(taskNo);
			//开始卸车时间
			distributeEntity.setUseStartTime(starTime);
			//结束卸车时间
			distributeEntity.setUseEndTime(perEndTime);
			//卸车部门
			distributeEntity.setTransferCenterNo(orgCode);
			//占用类型
			distributeEntity.setType(type);
			distributeEntity.setScheduleSource(useType);
			try{
				platformDispatchService.updatePlatformStatusForUsing(distributeEntity);
			}catch(Exception e){
				LOGGER.error("更新月台状态失败",e);
			}
	}

	/** 
	 * 完成卸车任务时更新月台状态
	 * @author dp-duyi
	 * @date 2013-4-22 下午6:43:08
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IPDACommonService#updatePlatformStateByFinishTask(java.lang.String, java.util.Date)
	 */
	@Override
	//@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void updatePlatformStateByFinishTask(String taskNo, Date useEndTime) {
		try{
			platformDispatchService.updatePlatformStatusForEnd(taskNo, useEndTime);
		}catch(Exception e){
			LOGGER.error("更新月台状态失败",e);
		}
	}
	/** 
	 * 获得营业部或外场
	 * @author dp-duyi
	 * @date 2013-7-31 上午9:40:27
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IPDACommonService#getTopCurrentOutfieldOrSalesDept(java.lang.String)
	 */
	@Override
	public OrgAdministrativeInfoEntity getTopCurrentOutfieldOrSalesDept(
			String orgCode) {
		OrgAdministrativeInfoEntity origOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(orgCode);
		if(origOrg != null){
			//如果是营业部或派送部
			if(FossConstants.YES.equals(origOrg.getSalesDepartment())){
				SaleDepartmentEntity saleDetp = saleDepartmentService.querySaleDepartmentByCode(origOrg.getCode());
				if(saleDetp != null && FossConstants.YES.equals(saleDetp.getStation())){
					origOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(saleDetp.getTransferCenter());
				}
			}else{
				//查找对应外场
				origOrg = this.getCurrentOutfieldCode(origOrg.getCode());
			}
		}
		return origOrg;
	}
	/**
	 * 
	 * 运单补录-更新装卸车任务重量体积 
	 * @author alfred
	 * @date 2014-2-17 上午10:27:29
	 * @param makeUpWaybillEntity 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IPDACommonService#makeUpPDAloadAndUnload(com.deppon.foss.module.transfer.load.api.shared.domain.MakeUpWaybillEntity)
	 */
	@Override
	public void makeUpPDAloadAndUnload(MakeUpWaybillEntity makeUpWaybillEntity) {
		BigDecimal averageVolume = BigDecimal.ZERO;
		BigDecimal averageWeight = BigDecimal.ZERO;
		//取运单平均每一件的重量和体积
		try {
			averageVolume = makeUpWaybillEntity.getVolume().divide(makeUpWaybillEntity.getQuantity(),LoadConstants.SONAR_NUMBER_3,BigDecimal.ROUND_HALF_UP);
			averageWeight = makeUpWaybillEntity.getWeight().divide(makeUpWaybillEntity.getQuantity(),LoadConstants.SONAR_NUMBER_3,BigDecimal.ROUND_HALF_UP);
		} catch (Exception e) {
			 e.printStackTrace();
			 averageVolume = BigDecimal.ZERO;
			 averageWeight = BigDecimal.ZERO;
		}
		
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("wayBillNo", makeUpWaybillEntity.getWaybillNo());
		condition.put("averageVolume",averageVolume);
		condition.put("averageWeight",averageWeight);
		pdaLoadDao.updateMakeUpPDAloadAndUnload(condition);
		
	}
	/**
	 * 
	 * <p>根据接驳点编码查询接驳点名称</p> 
	 * @author alfred
	 * @date 2015-9-7 下午7:32:52
	 * @param pointCode
	 * @return 
	 */
	@Override
	public String queryAccessPointName(String pointCode) {
		return pdaLoadDao.queryAccessPointName(pointCode);
	}
	
	
}