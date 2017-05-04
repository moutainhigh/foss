/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/impl/VehicleAgencyDeptService.java
 * 
 * FILE NAME        	: VehicleAgencyDeptService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.common.client.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.server.service.impl.OrgAdministrativeInfoService;
import com.deppon.foss.module.pickup.common.client.dao.IVehicleAgencyDeptDao;
import com.deppon.foss.module.pickup.common.client.dto.QueryPickupPointDto;
import com.deppon.foss.module.pickup.common.client.service.IVehicleAgencyDeptService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.ExpressPriceRegionService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 偏线代理网点service 接口实现：对偏线代理网点信息的增删改查的基本操作
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-10-15
 * 下午2:22:04,
 * </p>
 * 
 * @author foss-jiangfei
 * @date 2012-10-15 下午2:22:04
 * @since
 * @version
 */
public class VehicleAgencyDeptService implements IVehicleAgencyDeptService {

	private IVehicleAgencyDeptDao vehicleAgencyDeptDao;

	private ExpressPriceRegionService expressPriceRegionService;
/*	@Inject
	public void setExpressPriceRegionService(
			ExpressPriceRegionService expressPriceRegionService) {
		this.expressPriceRegionService = expressPriceRegionService;
	}*/
	/*@Inject
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}*/
	IWaybillHessianRemoting waybillRemotingService  = DefaultRemoteServiceFactory.getService(IWaybillHessianRemoting.class);

	public ExpressPriceRegionService getExpressPriceRegionService() {
		return expressPriceRegionService;
	}
	public void setExpressPriceRegionService(
			ExpressPriceRegionService expressPriceRegionService) {
		this.expressPriceRegionService = expressPriceRegionService;
	}
	@Inject
	public void setVehicleAgencyDeptDao(
			IVehicleAgencyDeptDao vehicleAgencyDeptDao) {
		this.vehicleAgencyDeptDao = vehicleAgencyDeptDao;
	}
	/**
	 * 对外接口实现 根据传入参数查询代理网点（空运代理网点和偏线代理网点）
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2012-11-1 下午3:43:18
	 * @param dto
	 *            参数封装DTO（包括：目的站、代理网点名称、代理网点类型、用于版本控制时间）
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService#queryOuterBranchs(com.deppon.foss.module.base.baseinfo.api.shared.dto.OuterBranchParamsDto)
	 */
	@Override
	public List<OuterBranchEntity> queryOuterBranchs(QueryPickupPointDto dto) {

		if (null == dto) {
			return null;
		}
		
		
		if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())) {
//			IWaybillHessianRemoting waybillRemotingService  
//					= DefaultRemoteServiceFactory
//					.getService(IWaybillHessianRemoting.class);
			com.deppon.foss.module.pickup.waybill.shared.dto.QueryPickupPointDto 
			dto2 = new com.deppon.foss.module.pickup.waybill.shared.dto.QueryPickupPointDto();
			
			try{
				org.apache.commons.beanutils.PropertyUtils.copyProperties(dto2, dto);
			}catch(Exception e){
				//to do nothing
			}
			return waybillRemotingService.queryOuterBranchs(dto2);
		}else{
			return vehicleAgencyDeptDao.queryOuterBranchs(dto);
		}
		
		
	}
	
	/**
     * 
     * @author WangQianJin
     * @date 2013-7-19 下午2:14:33
     */
	@Override
    public String checkProductAndTarget(QueryPickupPointDto dto){
		OuterBranchEntity outer=null;
		if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())) {			
			com.deppon.foss.module.pickup.waybill.shared.dto.QueryPickupPointDto 
			dto2 = new com.deppon.foss.module.pickup.waybill.shared.dto.QueryPickupPointDto();			
			try{
				org.apache.commons.beanutils.PropertyUtils.copyProperties(dto2, dto);
			}catch(Exception e){
				//to do nothing
			}
			outer=waybillRemotingService.queryOuterBranchByDto(dto2);
		}else{
			outer=vehicleAgencyDeptDao.queryOuterBranchByDto(dto);
		}
		/**
		 * 如果有偏线信息，则表示此提货网点可以选择这种运输性质，不需要清空提货网点
		 */
		if(outer!=null){
			return FossConstants.NO;
		}else{
			return FossConstants.YES;
		}
    }
	/**
	 * 获取区域的网点信息
	 * @param cuntyCode
	 * @return
	 */
	@Override
	public List<SaleDepartmentEntity> getOrgAdminInfoEntityList(String countyId) {
		List<SaleDepartmentEntity> outerBranchEntityList=new ArrayList<SaleDepartmentEntity>();
		 List<OrgAdministrativeInfoEntity> orgList	=waybillRemotingService.queryOrgAdministrativeInfoEntity(countyId);
		 /*
		 waybillRemotingService*/
		 for(OrgAdministrativeInfoEntity orgAdministrativeInfoEntity:orgList)
		 {
//			 OuterBranchEntity outerBranchEntity=new OuterBranchEntity();
			 if(orgAdministrativeInfoEntity!=null)
			 {
				 //营业部的code不能为空，判断是否是营业部
				 if((orgAdministrativeInfoEntity.getCode()!=null&&!"".equals(orgAdministrativeInfoEntity.getCode()))&&"Y".equals(orgAdministrativeInfoEntity.getSalesDepartment()))
				 {
					 outerBranchEntityList = getOuterBranchEntityList(outerBranchEntityList,orgAdministrativeInfoEntity);
				 }
			 }
		 }
   	 return outerBranchEntityList;
	}
	
	private List<SaleDepartmentEntity> getOuterBranchEntityList(
			List<SaleDepartmentEntity> outerBranchEntityList,
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity) {
		SaleDepartmentEntity saleDepartmentEntity= waybillRemotingService.querySaleDepartmentByCode(orgAdministrativeInfoEntity.getCode());
		 if(saleDepartmentEntity!=null)
		 {
			 if(saleDepartmentEntity.getCanHomeImproSend()!=null&&!"".equals(saleDepartmentEntity.getCanHomeImproSend()))
			 {
			 if("Y".endsWith(saleDepartmentEntity.getCanHomeImproSend()))
			 {
				/* outerBranchEntity.setAgentDeptCode(saleDepartmentEntity.getCode());
				 outerBranchEntity.setAgentDeptName(saleDepartmentEntity.getName());
				 outerBranchEntity.setPickupSelf(saleDepartmentEntity.getPickupSelf());
				 outerBranchEntity.setPickupToDoor(saleDepartmentEntity.getDelivery());*/
				 outerBranchEntityList.add(saleDepartmentEntity);
			 }
			 }
			 
		 }
		return outerBranchEntityList;
	}

}