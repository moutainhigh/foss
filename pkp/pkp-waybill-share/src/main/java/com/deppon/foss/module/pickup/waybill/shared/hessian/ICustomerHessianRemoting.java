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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/hessian/ICustomerHessianRemoting.java
 * 
 * FILE NAME        	: ICustomerHessianRemoting.java
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
package com.deppon.foss.module.pickup.waybill.shared.hessian;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IHessianService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusContractTaxEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerContactDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerPaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.CustomerCouponEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.HisDeliveryCusEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.HisReceiveCusEntity;

/**
 * 
 * 
 ******************************************* 
 * <b style="font-family:微软雅黑"><small>Description:客户信息远程服务接口</small></b> </br>
 * <b style="font-family:微软雅黑"><small>HISTORY</small></b></br> <b
 * style="font-family:微软雅黑"><small> ID DATE PERSON REASON</small></b><br>
 ******************************************** 
 * <div style="font-family:微软雅黑,font-size:70%"> 2012-10-10 sunrui 新增 </div>
 ******************************************** 
 */
public interface ICustomerHessianRemoting extends IHessianService {


    /**
     * 根据dto封装的查询条件查询客户信息
     * 
     * @author 026123-foss-lifengteng
     * @date 2012-10-24 下午7:13:55
     */
    List<CustomerContactDto> queryCustomerInfo(CustomerQueryConditionDto condition);

    
    /**
     * 通过传入手机号、部门Code，返回部门的客户信息
     * @author 026123-foss-lifengteng
     * @date 2012-11-2 上午11:47:18
     */
    CustomerDto queryCustInfoByMobile(String mobilePhone,String deptCode);

    /**
     * 通过传入一组电话号码、部门Code返回部门的客户信息列表
     * @author 026123-foss-lifengteng
     * @date 2012-11-2 上午11:48:08
     */
    List<CustomerDto> queryCustInfoByPhone(List<String> phoneList,String deptCode);

    /**
     * 根据手机或电话号码查询历史发货客户信息
     * @author 026123-foss-lifengteng
     * @date 2012-11-21 下午7:21:01
     */
    List<HisDeliveryCusEntity> queryHisDeliveryCusByPhone(CustomerQueryConditionDto conditionDto);
    
    /**
     * 根据手机或电话号码查询历史收货客户信息
     * @author 026123-foss-lifengteng
     * @date 2012-11-21 下午7:21:01
     */
    List<HisReceiveCusEntity> queryHisReceiveCusByPhone(CustomerQueryConditionDto conditionDto);
    
    /**
     * 根据客户编码查询客户信息
     * @author 026123-foss-lifengteng
     * @date 2012-11-26 上午10:45:29
     */
    CustomerDto queryCustInfoByCode(String custCode);

    /**
     * 根据客户名称精确查询全公司的客户信息
     * @author 026123-foss-lifengteng
     * @date 2013-1-6 下午6:30:43
     */
	List<CustomerDto> queryCustInfoByName(String custName);

	/**
	 * 根据手机号码集合与部门编码查询客户信息，部门编码为空时查询全国
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午12:50:25
	 */
	List<CustomerDto> queryCustInfoByMobileList(List<String> mobiles, String deptCode);

	/**
	 * 根据多个手机号码查询最近三个月的历史发货客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
	List<HisDeliveryCusEntity> queryHisDeliveryCusByMobileList(List<String> mobileList);

	/**
	 * 根据多个电话号码查询最近三个月的历史发货客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
	List<HisDeliveryCusEntity> queryHisDeliveryCusByPhoneList(List<String> phoneList);
	
	/**
	 * 根据多个手机号码查询最近三个月的历史收货客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
	List<HisReceiveCusEntity> queryHisReceiveCusByMobileList(List<String> mobileList);

	/**
	 * 根据多个电话号码查询最近三个月的历史收货客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
	List<HisReceiveCusEntity> queryHisReceiveCusByPhoneList(List<String> phoneList);

    /**
     * 根据dto封装的查询条件查询客户信息
     * @author 026123-foss-lifengteng
     * @date 2012-10-25 上午11:07:57
     * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting#queryCustomerInfo(com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto)
     */
	List<CustomerQueryConditionDto> queryCustomerByCondition(CustomerQueryConditionDto condition);
	
	
	   /**
     * 根据dto封装的查询条件查询客户信息
     * @author 026123-foss-lifengteng
     * @date 2012-10-25 上午11:07:57
     * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting#queryCustomerInfo(com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto)
     */
	CustomerPaginationDto queryCustomerByConditionByPage(CustomerPaginationDto condition);
	
	/**
	 * 根据查询条件的对象集合对象客户综合信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-14 下午9:30:33
	 */
	List<CustomerQueryConditionDto> queryCustomerByConditionList(List<CustomerQueryConditionDto> conditions);

	/**
	 * 根据客户查询临欠、散客的银行帐号信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-21 下午1:58:55
	 */
	List<NonfixedCusAccountEntity> queryBankAccountByCode(String custCode);

	/**
	 * 根据工号集合查询对应的员工对象集合
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-26 下午8:50:46
	 */
	List<EmployeeEntity> queryEmployeeByCodeList(List<String> codes);

	 /**
	 * 根据客户编码查询客户合同信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-21 下午3:09:27
	 */
	CusBargainEntity queryCusBargainByCustCode(String custCode);
	
	/**
	 * 内存溢出，性能优化
	 * @return
	 * add WangQianJin by 2010-10-17
	 */
	List<EmployeeEntity> queryEmployeeNameAndCodeByCodeList(List<String> codes);

	/**
	 * 根据编码查询员工信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-11-1 下午8:53:17
	 */
	EmployeeEntity queryEmployeeByEmpCode(String code);

	/**
	 * 根据客户编码,客户联系方式查询客户当前可以使用的发票标记信息
	 * @param condition
	 * @param date
	 * @return
	 */
	CusContractTaxEntity queryCurrentUseInvoiceMark(
			CustomerQueryConditionDto condition, Date date);


	boolean cheCustomerPhoneExistCustomerMobilePhone(CustomerQueryConditionDto customerQueryConditionDto);

	 /**
     * 
    * @Description: 查询客户与优惠券关系
    * @author hbhk 
    * @date 2015-6-9 下午2:26:47 
      @param entity
      @param start
      @param size
      @return
     */
    List<CustomerCouponEntity> queryCustomerCouponList(CustomerCouponEntity entity);
    
    /**
     * 
    * @Description: 客户使用优惠券
    * @author hebo 
    * @date 2015-6-9 下午2:27:25 
      @param customerCode
      @param couponCode
      @return
     */
    void useCustomerCoupon(String customerCode,String couponCode);

    //根据客户编码查询客户信息
	CustomerEntity queryCustomerInfoByCusCode(String code);

	//根据客户编码查询是否一票多件
	int queryOneticketornotBycode(String code);


	/**
	 * <p>根据客户编码查询发票标记，不查询客户其他信息</p> 
	 * @author Foss-151211-yangtaohong 
	 * @date 2016-5-12 下午9:49:23
	 * @param custCode
	 * @return
	 * @see
	 */
	CustomerDto queryInvoiceTypeByCusCode(String custCode);
}