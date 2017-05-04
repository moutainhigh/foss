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
 * PROJECT NAME	: pkp-waybill-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/service/IQueryCustomerService.java
 * 
 * FILE NAME        	: IQueryCustomerService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.server.service
 * FILE    NAME: IQueryCustomerService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusContractTaxEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerContactDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerPaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.HisDeliveryCusEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.HisReceiveCusEntity;

/**
 * 查询会员、客户信息接口
 * 
 * @author 026123-foss-lifengteng
 * @date 2012-10-23 下午7:41:42
 */
public interface IQueryCustomerService {

    /**
     * 根据客户名称查询客户信息
     * 
     * @author 026123-foss-lifengteng
     * @date 2012-10-24 上午10:19:09
     */
    List<CustomerDto> queryCustInfoByName(String custName);

    /**
     * 根据客户编码查询出对应的客户信息
     * 
     * @author 026123-foss-lifengteng
     * @date 2012-10-24 上午10:19:59
     */
    CustomerDto queryCustInfoByCode(String custCode);

    /**
     * 根据dto封装的查询条件查询客户信息
     * 
     * @author 026123-foss-lifengteng
     * @date 2012-10-24 上午10:20:42
     */
    List<CustomerContactDto> queryCustomerInfo(CustomerQueryConditionDto condition);

    /**
     * 通过传入一组电话号码、部门Code返回部门的客户信息列表
     * 
     * @author 026123-foss-lifengteng
     * @date 2012-10-25 上午9:56:14
     */
    List<CustomerDto> queryCustInfoByPhone(List<String> phoneList, String deptCode);

    /**
     * 通过传入手机号、部门Code，返回部门的客户信息
     * 
     * @author 026123-foss-lifengteng
     * @date 2012-10-25 上午9:56:30
     */
    CustomerDto queryCustInfoByMobile(String mobilePhone, String deptCode);

    /**
     * 通过手机、电话号码查询历史发货客户信息
     * 
     * @author 026123-foss-lifengteng
     * @date 2012-10-31 下午5:30:42
     */
    List<HisDeliveryCusEntity> queryHisDeliveryCusByPhone(CustomerQueryConditionDto condition);

    /**
     * 通过手机、电话号码查询历史收货客户信息
     * 
     * @author 026123-foss-lifengteng
     * @date 2012-10-31 下午5:29:19
     */
    List<HisReceiveCusEntity> queryHisReceiveCusByPhone(CustomerQueryConditionDto condition);

    /**
     * 新增历史发货客户
     * 
     * @author 026123-foss-lifengteng
     * @date 2012-11-22 下午4:45:48
     */
    int addHisDeliveryCustomer(HisDeliveryCusEntity custEntity);

    /**
     * 新装置历史收货客户
     * 
     * @author 026123-foss-lifengteng
     * @date 2012-11-22 下午4:45:50
     */
    int addHisReceiveCustomer(HisReceiveCusEntity custEntity);

	/**
	 * 根据手机号码集合与部门编码查询客户信息，部门编码为空时查询全国
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午12:50:25
	 */
	List<CustomerDto> queryCustInfoByMobileList(List<String> mobiles, String deptCode);

	/**
	 * 根据多个手机号码查询历史发货客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
	List<HisDeliveryCusEntity> queryHisDeliveryCusByMobileList(List<String> mobileList);

	/**
	 * 根据多个电话号码查询历史发货客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
	List<HisDeliveryCusEntity> queryHisDeliveryCusByPhoneList(List<String> phoneList);
	/**
	 * 根据多个手机号码查询历史收货客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
	List<HisReceiveCusEntity> queryHisReceiveCusByMobileList(List<String> mobileList);

	/**
	 * 根据多个电话号码查询历史收货客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
	List<HisReceiveCusEntity> queryHisReceiveCusByPhoneList(List<String> phoneList);

	/**
	 * 根据dto封装的查询条件查询客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-24 下午2:28:02
	 * @see com.deppon.foss.module.pickup.waybill.server.service.IQueryCustomerService#queryCustomerInfo(com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto)
	 */
	List<CustomerQueryConditionDto> queryCustomerByCondition(CustomerQueryConditionDto condition);

	/**
	 * 根据dto封装的查询条件查询客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-24 下午2:28:02
	 * @see com.deppon.foss.module.pickup.waybill.server.service.IQueryCustomerService#queryCustomerInfo(com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto)
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
	 * @date 2013-11-1 下午8:51:31
	 */
	EmployeeEntity queryEmployeeByEmpCode(String code);
	
	
	/**
	 *	查询简单员工信息
	 * @autor Foss-278328-hujinyang
	 * @time 2016-4-22
	 * @param code
	 * @return
	 */
	public  EmployeeEntity querySimpleEmployeeByEmpCode(String code);

	/**
	 * 根据客户编码,客户联系方式查询客户当前可以使用的发票标记信息
	 * @param condition
	 * @param date
	 * @return
	 */
	CusContractTaxEntity queryCurrentUseInvoiceMark(
			CustomerQueryConditionDto condition, Date date);

	/**
	 * 根据查询条件获取客户信息是否存在
	 * @创建时间 2014-5-27 下午6:57:11   
	 * @创建人： WangQianJin
	 */
	int queryCustomerExistByCondition(CustomerQueryConditionDto condition);
	
	/**
	 * 根据crmCusId获取客户信息
	 * @param crmCusId
	 * @return
	 */
	CustomerEntity queryCustomerByCrmCusId(BigDecimal crmCusId,String fossId);
	/**
	 * 
	* @author: 200945 吴涛
	* @Title: queryComtomerInvoiceType 
	* @Description: TODO(根据CRMID 去查询发票标记) 
	* @param @param date
	* @param @param crmId
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String queryComtomerInvoiceType(Date date ,BigDecimal crmId);
	boolean cheCustomerPhoneExistCustomerMobilePhone(CustomerQueryConditionDto customerQueryConditionDto);
	//根据客户编码查询客户信息
	public  CustomerEntity queryCustomerInfoByCusCode(String code);
	//根据客户编码查询是否一票多件
	public  int queryOneticketornotBycode(String code);

	/**
	 * <p>根据客户编码查询客户发票标记</p> 
	 * @author Foss-151211-yangtaohong 
	 * @date 2016-5-12 下午9:30:52
	 * @param custCode
	 * @return
	 * @see
	 */
	CustomerDto queryCustInvoiceTypeByCode(String custCode);
}