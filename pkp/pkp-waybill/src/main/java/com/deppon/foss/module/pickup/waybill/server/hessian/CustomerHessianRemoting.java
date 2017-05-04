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
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/hessian/CustomerHessianRemoting.java
 * 
 * FILE NAME        	: CustomerHessianRemoting.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.server.hessian
 * FILE    NAME: CustomerHessianRemoting.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.waybill.server.hessian;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.framework.server.deploy.hessian.annotation.Remote;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusContractTaxEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerContactDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerPaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICustomerCouponService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IQueryCustomerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.CustomerCouponEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.HisDeliveryCusEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.HisReceiveCusEntity;
import com.deppon.foss.module.pickup.waybill.shared.hessian.ICustomerHessianRemoting;

/**
 * 客户远程服务实现类
 * 
 * @author 026123-foss-lifengteng
 * @date 2012-11-21 下午4:40:14
 */
@Remote
public class CustomerHessianRemoting implements ICustomerHessianRemoting {

    // 查询客户信息服务
    IQueryCustomerService queryCustomerService;
    
    @Autowired
    ICustomerCouponService  customerCouponService;

    @Resource
    public void setQueryCustomerService(IQueryCustomerService queryCustomerService) {
	this.queryCustomerService = queryCustomerService;
    }

    /**
     * 根据dto封装的查询条件查询客户信息
     * 
     * @author 026123-foss-lifengteng
     * @date 2012-10-25 上午11:07:57
     * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting#queryCustomerInfo(com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto)
     */
    @Override
    public List<CustomerContactDto> queryCustomerInfo(CustomerQueryConditionDto condition) {
	return queryCustomerService.queryCustomerInfo(condition);
    }

    /**
     * 通过传入手机号、部门Code，返回部门的客户信息
     * 
     * @author 026123-foss-lifengteng
     * @date 2012-11-2 下午2:54:26
     * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting#queryCustInfoByMobile(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public CustomerDto queryCustInfoByMobile(String mobilePhone,String deptCode) {
    //String deptCode = FossUserContext.getCurrentDeptCode();
	return queryCustomerService.queryCustInfoByMobile(mobilePhone, deptCode);
    }

    /**
     * 通过传入一组电话号码、部门Code返回部门的客户信息列表
     * 
     * @author 026123-foss-lifengteng
     * @date 2012-11-2 下午2:54:38
     * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting#queryCustInfoByPhone(java.util.List,
     *      java.lang.String)
     */
    @Override
    public List<CustomerDto> queryCustInfoByPhone(List<String> phoneList,String deptCode) {
	//String deptCode = FossUserContext.getCurrentDeptCode();
	return queryCustomerService.queryCustInfoByPhone(phoneList, deptCode);
    }
    
    /**
     * 根据客户名称精确查询全公司的客户信息
     * @author 026123-foss-lifengteng
     * @date 2013-1-6 下午6:30:43
     */
    @Override
    public List<CustomerDto> queryCustInfoByName(String custName){
    	return queryCustomerService.queryCustInfoByName(custName);
    }

    /**
     * 根据手机或电话号码查询历史发货客户信息
     * @author 026123-foss-lifengteng
     * @date 2012-11-21 下午7:21:01
     */
    @Override
    public List<HisDeliveryCusEntity> queryHisDeliveryCusByPhone(CustomerQueryConditionDto conditionDto) {
	return queryCustomerService.queryHisDeliveryCusByPhone(conditionDto);
    }

    /**
     * 根据手机或电话号码查询历史收货客户信息
     * @author 026123-foss-lifengteng
     * @date 2012-11-21 下午7:21:01
     */
    @Override
    public List<HisReceiveCusEntity> queryHisReceiveCusByPhone(CustomerQueryConditionDto conditionDto) {
	List<HisReceiveCusEntity> list = queryCustomerService.queryHisReceiveCusByPhone(conditionDto);
	return list;
    }

    /**
     * 根据客户编码查询客户信息
     * @author 026123-foss-lifengteng
     * @date 2012-11-26 上午10:45:29
     */
    @Override
    public CustomerDto queryCustInfoByCode(String custCode) {
	return queryCustomerService.queryCustInfoByCode(custCode);
    }
    
    
    /** 
     * <p>根据客户编码只查询发票标记不查询其他客户信息</p> 
     * @author Foss-151211-yangtaohong 
     * @date 2016-5-12 下午9:52:19
     * @param custCode
     * @return 
     * @see com.deppon.foss.module.pickup.waybill.shared.hessian.ICustomerHessianRemoting#queryInvoiceTypeByCusCode(java.lang.String)
     */
    @Override
    public CustomerDto queryInvoiceTypeByCusCode(String custCode) {
	return queryCustomerService.queryCustInvoiceTypeByCode(custCode);
    }
    
	/**
	 * 根据手机号码集合与部门编码查询客户信息，部门编码为空时查询全国
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午12:50:25
	 */
    @Override
    public List<CustomerDto> queryCustInfoByMobileList(List<String> mobiles,String deptCode){
    	return queryCustomerService.queryCustInfoByMobileList(mobiles, deptCode);
    }
    
    
    
	/**
	 * 根据多个手机号码查询最近三个月的历史发货客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
    @Override
    public List<HisDeliveryCusEntity> queryHisDeliveryCusByMobileList(List<String> mobileList){
    	return queryCustomerService.queryHisDeliveryCusByMobileList(mobileList);
    }
    
	/**
	 * 根据多个电话号码查询最近三个月的历史发货客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
    @Override
    public List<HisDeliveryCusEntity> queryHisDeliveryCusByPhoneList(List<String> phoneList){
    	return queryCustomerService.queryHisDeliveryCusByPhoneList(phoneList);
    }
    
	/**
	 * 根据多个手机号码查询最近三个月的历史发货客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
    @Override
    public List<HisReceiveCusEntity> queryHisReceiveCusByMobileList(List<String> mobileList){
    	return queryCustomerService.queryHisReceiveCusByMobileList(mobileList);
    }
    
	/**
	 * 根据多个电话号码查询最近三个月的历史发货客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
    @Override
    public List<HisReceiveCusEntity> queryHisReceiveCusByPhoneList(List<String> phoneList){
    	return queryCustomerService.queryHisReceiveCusByPhoneList(phoneList);
    }
    
    /**
     * 根据dto封装的查询条件查询客户信息
     * @author 026123-foss-lifengteng
     * @date 2012-10-25 上午11:07:57
     * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting#queryCustomerInfo(com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto)
     */
	@Override
	public List<CustomerQueryConditionDto> queryCustomerByCondition(CustomerQueryConditionDto condition) {
		return queryCustomerService.queryCustomerByCondition(condition);
	}
	
	  /**
     * 根据dto封装的查询条件查询客户信息
     * @author 026123-foss-lifengteng
     * @date 2012-10-25 上午11:07:57
     * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting#queryCustomerInfo(com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto)
     */
	public 	CustomerPaginationDto queryCustomerByConditionByPage(	CustomerPaginationDto condition) {
		return  queryCustomerService.queryCustomerByConditionByPage(condition);
	}

	/**
	 * 根据查询条件的对象集合对象客户综合信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-14 下午9:30:33
	 */
	@Override
	public List<CustomerQueryConditionDto> queryCustomerByConditionList(List<CustomerQueryConditionDto> conditions){
		return queryCustomerService.queryCustomerByConditionList(conditions);
	}
	
	/**
	 * 根据客户查询临欠、散客的银行帐号信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-21 下午1:58:55
	 */
	@Override
	public List<NonfixedCusAccountEntity> queryBankAccountByCode(String custCode){
		return queryCustomerService.queryBankAccountByCode(custCode);
	}
	
	/**
	 * 根据工号集合查询对应的员工对象集合
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-26 下午8:50:46
	 */
	@Override
	public List<EmployeeEntity> queryEmployeeByCodeList(List<String> codes){
		return queryCustomerService.queryEmployeeByCodeList(codes);
	}
	
	 /**
	 * 根据客户编码查询客户合同信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-21 下午3:09:27
	 */
	@Override
	public CusBargainEntity queryCusBargainByCustCode(String custCode){
		return queryCustomerService.queryCusBargainByCustCode(custCode);
	}
	
	/**
	 * 内存溢出，性能优化
	 * @return
	 * add WangQianJin by 2010-10-17
	 */
	@Override
	public List<EmployeeEntity> queryEmployeeNameAndCodeByCodeList(List<String> codes){
		return queryCustomerService.queryEmployeeNameAndCodeByCodeList(codes);
	}
	
	/**
	 * 根据编码查询员工信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-11-1 下午8:53:17
	 */
	@Override
	public EmployeeEntity queryEmployeeByEmpCode(String code){
		return queryCustomerService.queryEmployeeByEmpCode(code);
	}
	
	/**
	 * 根据客户编码,客户联系方式查询客户当前可以使用的发票标记信息
	 * @param condition
	 * @param date
	 * @return
	 */
	@Override
	public CusContractTaxEntity queryCurrentUseInvoiceMark(CustomerQueryConditionDto condition, Date date){
		return queryCustomerService.queryCurrentUseInvoiceMark(condition,date);
	}

	@Override
	public boolean cheCustomerPhoneExistCustomerMobilePhone(CustomerQueryConditionDto customerQueryConditionDto) {
		return queryCustomerService.cheCustomerPhoneExistCustomerMobilePhone(customerQueryConditionDto);
	}

	@Override
	public List<CustomerCouponEntity> queryCustomerCouponList(
			CustomerCouponEntity entity) {
		entity.setActiveDate(new Date());
		return customerCouponService.queryCustomerCouponList(entity, 0, Integer.MAX_VALUE);
	}

	@Override
	public void useCustomerCoupon(String customerCode, String couponCode) {
		 customerCouponService.useCustomerCoupon(customerCode, couponCode);
	}
	@Override
	public  CustomerEntity queryCustomerInfoByCusCode(String code){
		return queryCustomerService.queryCustomerInfoByCusCode(code);
	}

	@Override
	public int queryOneticketornotBycode(String code) {
		return queryCustomerService.queryOneticketornotBycode(code);
	}
	
}