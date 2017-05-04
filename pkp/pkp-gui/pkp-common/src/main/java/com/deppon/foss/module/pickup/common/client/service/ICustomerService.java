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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/ICustomerService.java
 * 
 * FILE NAME        	: ICustomerService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-common
 * PACKAGE NAME: com.deppon.foss.module.pickup.common.client.service
 * FILE    NAME: ICustomerService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.common.client.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusContractTaxEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerContactDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerPaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.pickup.common.client.vo.QueryMemberDialogVo;



/**
 * 查询客户信息的服务接口
 * @author 026123-foss-lifengteng
 * @date 2012-11-21 下午4:29:30
 */
public interface ICustomerService extends IService {
	
    /**
     * 根据dto封装的查询条件查询客户信息
     * 
     * @author 026123-foss-lifengteng
     * @date 2012-10-24 下午7:13:55
     */
    List<CustomerContactDto> queryCustomerInfo(CustomerQueryConditionDto condition);

    /**
     * 通过传入手机电话号码、部门Code，返回部门的客户信息
     * 
     * @author 026123-foss-lifengteng
     * @date 2012-11-15 下午9:02:26
     */
    List<CustomerContactDto> queryCustInfoByPhone(CustomerQueryConditionDto conditionDto);
    
    /**
     * 根据手机或电话号码查询历史发货客户信息
     * @author 026123-foss-lifengteng
     * @date 2012-11-21 下午7:21:01
     */
    List<CustomerContactDto> queryHisDeliveryCusByPhone(CustomerQueryConditionDto conditionDto);
    
    /**
     * 根据手机或电话号码查询历史收货客户信息
     * @author 026123-foss-lifengteng
     * @date 2012-11-21 下午7:21:01
     */
    List<CustomerContactDto> queryHisReceiveCusByPhone(CustomerQueryConditionDto conditionDto);

	/**
	 * 根据客户编码查询客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-28 下午10:25:07
	 */
	List<QueryMemberDialogVo> queryCustInfoByCode(String deptCode);
	
	/**
	 * 根据客户编码查询客户银行信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-28 下午10:25:07
	 */
	List<CusAccountEntity> queryBankAccountByCode(String customerCode);
	
    /**
     * 根据客户名称精确查询全公司的客户信息
     * @author 026123-foss-lifengteng
     * @date 2013-1-6 下午6:30:43
     */
	List<CustomerContactDto> queryCustInfoByName(String custName);
	
	/**
	 * 根据多个手机号码查询最近三个月的历史发货客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
	List<CustomerQueryConditionDto> queryHisDeliveryCusByMobileList(List<String> mobileList);

	/**
	 * 根据多个电话号码查询最近三个月的历史发货客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
	List<CustomerQueryConditionDto> queryHisDeliveryCusByPhoneList(List<String> phoneList);

	/**
	 * 根据多个手机号码查询最近三个月的历史收货客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
	List<CustomerQueryConditionDto> queryHisReceiveCusByMobileList(List<String> mobileList);

	/**
	 * 根据多个电话号码查询最近三个月的历史收货客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
	List<CustomerQueryConditionDto> queryHisReceiveCusByPhoneList(List<String> phoneList);

	/**
	 * 根据dto封装的查询条件查询客户信息 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-11 下午3:57:33
	 */
	List<CustomerQueryConditionDto> queryCustomerByCondition(CustomerQueryConditionDto condition);

	/**
	 * 根据查询条件的对象集合对象客户综合信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-14 下午9:30:33
	 */
	List<CustomerQueryConditionDto> queryCustomerByConditionList(List<CustomerQueryConditionDto> conditions);
	
	/**
	 * 根据工号集合查询对应的员工对象集合
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-26 下午8:50:46
	 */
	List<EmployeeEntity> queryEmployeeByCodeList(List<String> codes);

	/**
	 * 分页查询客户信息
	 * @param gainQueryCondition
	 * @return
	 */
	CustomerPaginationDto queryCustomerByConditionByPage(
			CustomerPaginationDto gainQueryCondition);

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
	
	/**
	 * @author foss-liyongfei
	 * @param custCode 客户编码
	 * @return 客户精确代收的属性
	 */
	String queryAccurateCollectionByCustCode(String custCode);
	
	/**
	 * 根据Dmana-10888增加方法，查询出发票标记和客户编码信息
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-03-19下午15:03
	 */
	String queryInvoiceTypeByCode(String custCode);
	/**
	 * <p>根据客户编码查询发票标记不关联其他客户信息</p> 
	 * @author Foss-151211-yangtaohong 
	 * @date 2016-5-12 下午9:37:28
	 * @param custCode
	 * @return
	 * @see
	 */
	String queryInvoiceTypeByCusCode(String custCode);

	boolean cheCustomerPhoneExistCustomerMobilePhone(CustomerQueryConditionDto customerQueryConditionDto);

	//根据客户编码查询客户信息
	CustomerEntity queryCustomerInfoByCusCode(String code);
	//根据客户编码查询是否一票多件
	int queryOneticketornotBycode(String code);
}