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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/impl/SalesDepartmentService.java
 * 
 * FILE NAME        	: SalesDepartmentService.java
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
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.mybatis.guice.transactional.Transactional;

import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.module.base.baseinfo.server.service.impl.ExpressCityService;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressCityService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesBillingGroupEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressCityResultDto;
import com.deppon.foss.module.pickup.common.client.dao.ISalesDepartmentDao;
import com.deppon.foss.module.pickup.common.client.dto.QueryPickupPointDto;
import com.deppon.foss.module.pickup.common.client.service.IBaseDataService;
import com.deppon.foss.module.pickup.common.client.service.ISalesDepartmentService;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.vo.BranchQueryVo;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.SalesDepartmentCityDto;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 查询提货网点时候用到
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-jiangfei,date:2012-10-31 下午4:19:59,
 * </p>
 * 
 * @author foss-jiangfei
 * @date 2012-10-31 下午4:19:59
 * @since
 * @version
 */
public class SalesDepartmentService implements ISalesDepartmentService {

	/**
	 * 注入项
	 */
	private ISalesDepartmentDao salesDepartmentDao;

	@Inject
	public void setSalesDepartmentDao(ISalesDepartmentDao salesDepartmentDao) {
		this.salesDepartmentDao = salesDepartmentDao;
	}
	
	/**
	 * 注入基础资料DAO
	 */
	@Inject
	private IBaseDataService baseDataService;
	
	//远程访问Service
	IWaybillHessianRemoting waybillRemotingService = DefaultRemoteServiceFactory.getService(IWaybillHessianRemoting.class);

	/**
	 * <p>
	 * (根据营业部名字等信息集合查询提货网点)
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-31 下午4:20:24
	 * @param code
	 * @param name
	 * @param isqueryFuzzy
	 * @return
	 * @see com.deppon.foss.module.pickup.common.client.service.ISalesDepartmentService#querySalesDepartmentInfo(java.lang.String,
	 *      java.lang.String, java.lang.Boolean)
	 */
	@Override
	public List<SaleDepartmentEntity> querySalesDepartmentInfo(
			QueryPickupPointDto dto) {
		
		if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())) {
//			IWaybillHessianRemoting waybillRemotingService  
//					= DefaultRemoteServiceFactory
//					.getService(IWaybillHessianRemoting.class);
			com.deppon.foss.module.pickup.waybill.shared.dto.QueryPickupPointDto 
			dto2 = new com.deppon.foss.module.pickup.waybill.shared.dto.QueryPickupPointDto();
			
			try{
				org.apache.commons.beanutils.PropertyUtils.copyProperties(dto2, dto);
			}catch(Exception e){
				
				//小件代码有添加 
			}
			return waybillRemotingService.queryByDepartmentInfo(dto2);
		}else{
			if(FossConstants.YES.equals(dto.getPickUpSelf())){
				return salesDepartmentDao.queryByDepartmentInfo(dto);
			}else{
				if(CommonUtils.directDetermineIsExpressByProductCode(dto.getTransType())){
					//这段代码新加的 小件  查询虚拟部门
					return salesDepartmentDao. queryByDepartmentInfoVirtual( dto);
				}else{
					return salesDepartmentDao.queryByDepartmentInfo(dto);
				}
			}
		}
	}
	
	
	

	/**
	 * 根据营业部编码得到营业部的试点城城名称 code 类型等详细信息
	 * TODO 这个方法调用乔历峰接口
	 * @param dto
	 * @return
	 */
	public  SalesDepartmentCityDto querySalesDepartmentCityInfo(SalesDepartmentCityDto dto){
		SalesDepartmentCityDto result  = new SalesDepartmentCityDto();
		result.setSalesDepartmentCode(dto.getSalesDepartmentCode());
//		result.setCityCode("111");
//		result.setCityName("北京");
//		result.setCityType(WaybillConstants.CITY_TYPE_SHIDIAN);
		
		
		if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())) {
			IWaybillHessianRemoting waybillRemotingService  = DefaultRemoteServiceFactory.getService(IWaybillHessianRemoting.class);
			ExpressCityResultDto expressdto = waybillRemotingService.queryExpressCityTypeByOrgCode(dto.getSalesDepartmentCode());
			if(expressdto!=null){
				result.setCityCode(expressdto.getCityCode());
				result.setCityName(expressdto.getCityName());
				result.setCityType(expressdto.getType());
				result.setProvCode(expressdto.getProvCode());
				result.setProvName(expressdto.getProvName());
			}
		
		}else{
			IExpressCityService expressCityService =GuiceContextFactroy.getInjector().getInstance(ExpressCityService.class);
			ExpressCityResultDto expressdto =  expressCityService.queryExpressCityTypeByOrgCode(dto.getSalesDepartmentCode());
			if(expressdto!=null){
				result.setCityCode(expressdto.getCityCode());
				result.setCityName(expressdto.getCityName());
				result.setCityType(expressdto.getType());
				result.setProvCode(expressdto.getProvCode());
				result.setProvName(expressdto.getProvName());
			}
		}
		return result;
	}

	/**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	@Transactional
	@Override
	public void addSalesDepartment(SaleDepartmentEntity saleDepartment) {
		 
		salesDepartmentDao.insertSelective(saleDepartment);
	}
	
	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Transactional
	@Override
	public void updateSalesDepartment(SaleDepartmentEntity saleDepartment) {
		salesDepartmentDao.updateByPrimaryKeySelective(saleDepartment);
		
	}
	
	/**
	 * 
	 * 功能：新增或更新记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Override
	public void saveOrUpdate(SaleDepartmentEntity saleDepartment) {
		if(!salesDepartmentDao.insertSelective(saleDepartment)){
			salesDepartmentDao.updateByPrimaryKeySelective(saleDepartment);

		}
		
	}

	/**
	 * 
	 * 根据部门名称查询
	 * @author 025000-FOSS-helong
	 * @date 2012-12-17 上午10:57:29
	 */
	@Override
	public List<SaleDepartmentEntity> querySaleDepartmentByName(String name) {
		return salesDepartmentDao.querySaleDepartmentByName(name);
	}
	
	/**
	 * 根据名称查询营业部，若isArrived为true则只查询可做到达的营业部
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-3 下午3:53:32
	 */
	@Override
	public List<SaleDepartmentEntity> querySaleDeptByNameOnline(String name,boolean isArrived) {
		//判断是否为空
		if(StringUtil.isEmpty(name)){
			return null;
		}
		
		//封装查询条件
		SaleDepartmentEntity entity = new SaleDepartmentEntity();
		entity.setName(name);
		entity.setActive(FossConstants.ACTIVE);
		
		//是否只查询可做到达的营业部
		if(isArrived){
			entity.setArrive(FossConstants.YES);
		}
		
		return baseDataService.querySaleDepartmentByEntityOnline(entity);
	}
	
	/**
	 * 
	 * 根据部门名称查询
	 * @author 025000-FOSS-helong
	 * @date 2012-12-17 上午10:57:29
	 */
	@Override
	public List<SaleDepartmentEntity> querySaleDepartmentByNameForCentralized(String name,String billingGroup) {
		//根据开单组编码查询出所涵盖的营业部
		List<SaleDepartmentEntity> deptList = baseDataService.querySalesListByBillingGroupCode(billingGroup);
		//符合条件的集合
		List<SaleDepartmentEntity> depts = null;
		//集中非空判断
		if(CollectionUtils.isNotEmpty(deptList)){
			//判断传入的名称是否为空
			if(StringUtil.isNotEmpty(name)){
				depts = new ArrayList<SaleDepartmentEntity>();
				//遍体集合
    			for (SaleDepartmentEntity dept : deptList) {
    				//判断集中是否有该名称的部门
    				if(StringUtil.defaultIfNull(dept.getName()).indexOf(name.trim())>=0){
    					depts.add(dept);
    				}
    			}
			}else{
				//为空则返回全部查询出来的集合
				deptList = depts;
			}
		}
		return depts;
	}
	
    /**
     * 
     * 通过 标识编码查询
     * @author 026113-foss-linwensheng
     * @date 2012-11-28 上午10:30:33
     */
    public SaleDepartmentEntity querySaleDepartmentByCode(String code){
    	return salesDepartmentDao.querySaleDepartmentByCode(code);
    }
    
	/**
	 * 根据历史时间和营业部编码查询营业部信息（查询历史营业部信息）
	 * 
	 * 若时间为空，则只根据营业部编码查询营业部信息
	 * 否则将根据时间，查询在creatTime和modifyTime时间段的营业部
	 * 不根据Active='Y'来查询
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-17 下午6:02:26
	 */
	@Override
    public SaleDepartmentEntity querySaleDepartmentByCode(String code,Date billTime){
    	return baseDataService.querySaleDepartmentByCode(code,billTime);
    }
	
	public void delete(SaleDepartmentEntity saleDepartment){
		salesDepartmentDao.delete(saleDepartment);
	}

	@Override
	public List<BranchQueryVo> queryListByDepartment(SaleDepartmentEntity entity) {
		return salesDepartmentDao.queryListByDepartment(entity);
	}

	/**
	 * 
	 * 内部带货查询部门
	 * @author 025000-FOSS-helong
	 * @date 2013-4-16 下午08:41:41
	 * @param name
	 * @return
	 */
	@Override
	public List<SaleDepartmentEntity> querySaleDepartmentInner(String name) {
		return salesDepartmentDao.querySaleDepartmentInner(name);
	}
	
	/**
	 * 校验运输性质和提货网点是否匹配
	 * @author WangQianJin
	 * @date 2013-7-19 上午10:58:12
	 */
	public String checkProductAndTarget(QueryPickupPointDto dto){
		SaleDepartmentEntity sale = null;
		if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())) {							
			com.deppon.foss.module.pickup.waybill.shared.dto.QueryPickupPointDto dto2 =
			 new com.deppon.foss.module.pickup.waybill.shared.dto.QueryPickupPointDto();			
			try{
				org.apache.commons.beanutils.PropertyUtils.copyProperties(dto2, dto);
			}catch(Exception e){
				System.out.println("checkProductAndTarget异常==================="+e.getMessage());
			}
			sale = waybillRemotingService.queryDepartmentInfoByDto(dto2);			
		}else{
			sale = salesDepartmentDao.queryDepartmentInfoByDto(dto);
		}
		/**
		 * 如果有营业部信息，则表示此提货网点可以选择这种运输性质，不需要清空提货网点
		 */
		if(sale!=null){
			return FossConstants.NO;
		}else{
			return FossConstants.YES;
		}
	}
	
	/**
 	 * 
 	 * 根据部门Code和所属集中开单组查询
 	 * 
 	 * @author WangQianJin
 	 * @date 2013-08-02
 	 */
 	public List<SalesBillingGroupEntity> querySalesBillGroupByCodeAndBillCode(String code,String billingGroup){
 		return salesDepartmentDao.querySalesBillGroupByCodeAndBillCode(code, billingGroup);
 	}

 	@Override
 	public List<SaleDepartmentEntity> querySaleDepartmentByNameForCentralizedExp(String name,String billingGroup,String waybillNo){
		return waybillRemotingService.querySaleDepartmentByNameForCentralizedExp(name, billingGroup,waybillNo);
	}

}