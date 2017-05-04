/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/ICustomerService.java
 * 
 * FILE NAME        	: ICustomerService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
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
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactAddressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OneticketornotEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerContactDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerPaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.CustomerException;

/**
 * 客户信息Service接口
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-10-20 上午10:17:54
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-10-20 上午10:17:54
 * @since
 * @version
 */
public interface ICustomerService extends IService {

	 /**
	  * 新增散客
	  *
	  * auther:wangpeng_078816
	  * date:2014-4-22
	  *
	  */
    int addNonfixedCustomer(CustomerEntity entity);
    
    /**
     * 新增客户
     * 
     * @author dp-xieyantao
     * @date 2012-10-20 上午10:06:59
     * @param entity
     * @return 1：成功；0：失败
     * @see
     */
    int addCustomer(CustomerEntity entity);

    /**
     * 根据code作废客户
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-10-20 下午2:28:27
     * @param crmCusId
     *            CRM客户ＩＤ
     * @return
     * @see
     */
    int deleteCustomerByCode(BigDecimal crmCusId, String modifyUser);

    /**
     * 修改客户 对于有版本控制的编辑，先逻辑删除该记录，再新增一条记录
     * 
     * @author dp-xieyantao
     * @date 2012-10-20 上午10:06:59
     * @param entity
     * @return
     * @see
     */
    int updateCustomer(CustomerEntity entity);

    /**
     * <p>
     * 根据crmId,最后一次修改时间查询客户是否存在
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-11-26 上午10:15:29
     * @param crmId
     * @param lastupdatetime
     * @return
     * @see
     */
    boolean queryCustomerByCrmId(BigDecimal crmId, Date lastupdatetime);

    /**
     * <p>
     * 根据客户CRMId验证客户是否存在
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-1-5 上午11:26:53
     * @param crmId
     * @return
     * @see
     */
    boolean queryCustomerByCrmId(BigDecimal crmId);

    /**
     * 根据传入对象查询符合条件所有客户信息
     * 
     * @author dp-xieyantao
     * @date 2012-10-20 上午10:06:59
     * @param entity
     * @param limit
     * @param start
     * @return
     * @see
     */
    List<CustomerEntity> queryCustomers(CustomerEntity entity, int limit,
	    int start);

    /**
     * 统计总记录数
     * 
     * @author dp-xieyantao
     * @date 2012-10-20 上午10:06:59
     * @param entity
     * @return
     * @see
     */
    Long queryRecordCount(CustomerEntity entity);

    /**
     * --------------------下面为对外提供的接口----------------------------------
     */

    /**
     * 通过传入查询条件对象dto，返回客户（会员）信息的对象集合list
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-10-20 上午11:42:44
     * @param condition
     * @return
     * @see
     */
    List<CustomerContactDto> queryCustomerInfo(
	    CustomerQueryConditionDto condition);

    /**
     * 通过传入一个客户编码查询出对应的客户信息
     * <p>
     * 包括：客户基本信息、联系人信息集合、客户银行账户、客户合同信息集合
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-10-20 上午11:48:46
     * @param custCode
     *            客户编码
     * @return 存在：CustomerDto 不存在：null
     * @see
     */
    CustomerDto queryCustInfoByCode(String custCode);

    /**
     * 通过传入一个客户编码查询出财务未作废客户信息
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-10-20 上午11:48:46
     * @param custCode
     *            客户编码
     * @return
     * @see
     */
    CustomerEntity queryNoDeletedCustInfoByCode(String custCode);
    
    /**
     * 通过传入一个客户实体类来查询客户信息
     * 
     * @author 261997-foss-css
     * @date 2015-6-15 下午13:55:46
     * @param customerEntity
     *            客户实体类
     * @return
     * @see
     */
    CustomerEntity queryCustInfoByCustomerEntity(CustomerEntity customerEntity);    


    /**
     * 通过传入一个客户名称查询出对应的客户信息
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-10-20 上午11:53:55
     * @param custName
     *            客户名称
     * @return
     * @see
     */
    List<CustomerDto> queryCustInfoByName(String custName);

    /**
     * <p>
     * 根据客户编码查询客户主数据(先从缓存里面取，缓存没有再从数据库里面取)
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-4-3 上午10:49:57
     * @param custCode
     * @return
     * @see
     */
    CustomerDto queryCustomerDtoByCustCode(String custCode);

    /**
     * 通过传入一组电话号码、部门Code返回部门的客户信息列表
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-10-25 上午8:32:01
     * @param phoneList
     *            电话号码列表
     * @param deptCode
     *            部门Code
     * @return 客户信息列表
     * @see
     */
    List<CustomerDto> queryCustInfoByPhone(List<String> phoneList,
	    String deptCode);

    /**
     * 通过传入手机号、部门Code，返回部门的客户信息
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-10-25 上午8:35:08
     * @param mobilePhone
     *            手机号码
     * @param deptCode
     *            部门code
     * @return 客户信息
     * @see
     */
    CustomerDto queryCustInfoByMobile(String mobilePhone, String deptCode);

    /**
     * 通过传入部门编码查询该部门下所有客户信息：客户编码、客户名称
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-10-26 上午10:15:00
     * @param deptCode
     *            部门编码
     * @return 客户信息列表
     * @see
     */
    List<CustomerEntity> queryCusInfosByDeptCode(String deptCode);

    /**
     * <p>
     * 根据客户联系人地址ID，查询出客户的收货习惯
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-11-5 上午11:26:34
     * @param contactAddressId
     *            联系人地址ID
     * @return 存在：联系人地址实体（偏好起始时间、偏好结束时间、是否送货上楼、其他要求）
     *         <P>
     *         不存在：null
     *         </p>
     * @see
     */
    ContactAddressEntity queryContactAddressById(String contactAddressId);

    /**
     * 通过传入查询条件对象dto，返回客户信息的对象集合list
     * 
     * @author 026123-foss-lifengteng
     * @date 2013-1-11 下午3:41:10
     */
    List<CustomerQueryConditionDto> queryCustomerByCondition(
	    CustomerQueryConditionDto condition);

    /**
     * 根据查询条件的对象集合对象客户综合信息
     * 
     * @author 026123-foss-lifengteng
     * @date 2013-1-14 下午9:30:33
     */
    List<CustomerQueryConditionDto> queryCustomerByConditionList(
	    List<CustomerQueryConditionDto> conditions);

    /**
     * @param condition
     * @return
     */
    int countCustomerByCondition(CustomerPaginationDto condition);

    /**
     * @param condition
     * @return
     */
    List<CustomerQueryConditionDto> queryCustomerByConditionByPage(
	    CustomerPaginationDto condition);
    
    /** 快递新增方法：快递开单查询查询客户及其合同等相关信息
     * @param condition
     * @return
     */
    List<CustomerQueryConditionDto> queryExpCustomerByConditionByPage(
	    CustomerPaginationDto condition);
    
    /**
     * 通过crm_cus_id查询客户信息
     * 
     * @author 132599-FOSS-ShenWeiHua
     * @date 2014-12-10 上午10:15:00
     * @param CRM_CUS_ID 客户信息在crm系统的id
     * @return 客户信息
     * @see
     */
    List<CustomerEntity> queryCusInfosByCrmCusId(BigDecimal crmCusId,String fossId);
    
    /**
     * 通过客户编码查询客户信息
     * 
     * @author 132599-FOSS-ShenWeiHua
     * @date 2015-03-27 上午10:15:00
     * @param code 
     * @return 联系人信息
     * @see
     */
    List<ContactEntity> queryContactInfoByCusCode(String code);
    /**
     * 通过客户编码查询客户信息
     * 
     * @author 261997
     * @date 2015-07-17 上午9:15:00
     * @param code 
     * @return 客户对象
     * @see
     */
    CustomerEntity queryCustomerInfoByCusCode(String code);
    /**
     * 通过客户编码查询客户信息
     * 
     * @author 273311
     * @date 2015-12-23 
     * @param code 
     * @return 对象
     * @see
     */
    OneticketornotEntity queryOneticketornotBycode(String code);
    
    /**
     * 验证散客是否存在
     * @param code
     * @return
     */
    boolean queryExistsCustInfoByCode(String code);

	/**
	 * <p>根据客户编码查询客户发票标记</p> 
	 * @author Foss-151211-yangtaohong 
	 * @date 2016-5-12 下午8:20:41
	 * @param custCode
	 * @return
	 * @throws CustomerException
	 * @see
	 */
	CustomerDto queryCustInvoiceTypeByCode(String custCode)
			throws CustomerException;
}
