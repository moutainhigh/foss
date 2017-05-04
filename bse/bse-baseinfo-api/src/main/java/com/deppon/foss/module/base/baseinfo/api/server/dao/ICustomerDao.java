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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/ICustomerDao.java
 * 
 * FILE NAME        	: ICustomerDao.java
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
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BargainAppOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactAddressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerNewEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CusAccountDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerContactDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerPaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;


/**
 * 客户信息Dao接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-20 上午10:06:59, </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-20 上午10:06:59
 * @since
 * @version
 */
public interface ICustomerDao {
    
    /**
     * 新增客户 
     * @author dp-xieyantao
     * @date 2012-10-20 上午10:06:59
     * @param entity
     * @return 1：成功；0：失败
     * @see
     */
    int addCustomer(CustomerEntity entity) ;
    
    /**
     * 根据code作废客户
     * @author 094463-foss-xieyantao
     * @date 2012-10-20 下午2:28:27
     * @param crmCusId CRM客户ＩＤ
     * @return
     * @see
     */
    int deleteCustomerByCode(BigDecimal crmCusId,String modifyUser);
    
    /**
     * 修改客户 
     * @author dp-xieyantao
     * @date 2012-10-20 上午10:06:59
     * @param entity
     * @return
     * @see
     */
    int updateCustomer(CustomerEntity entity);
    
    /**
     * 根据传入对象查询符合条件所有客户信息 
     * @author dp-xieyantao
     * @date 2012-10-20 上午10:06:59
     * @param entity
     * @param limit
     * @param start
     * @return
     * @see
     */
    List<CustomerEntity> queryCustomers(CustomerEntity entity,int limit,int start);
 
    /**
     * 根据传入对象查询符合条件所有客户信息 
     * @author 101911-foss-zhouChunlai
     * @date 2013-3-12 上午11:18:00
     */
    List<CustomerEntity> queryExtCustomers(CustomerEntity entity,int limit,int start);
    /**
     * 统计总记录数 
     * @author dp-xieyantao
     * @date 2012-10-20 上午10:06:59
     * @param entity
     * @return
     * @see
     */
    Long queryRecordCount(CustomerEntity entity);
    
    /** 
     * 根据传入对象查询符合条件公司客户和散客信息
     * @author 101911-foss-zhouChunlai
     * @date 2013-3-21 下午3:32:57
     */
    List<CustomerEntity> queryExtCustomersWithSinglePeopleInfo(CustomerEntity entity,int limit, int start) ;
    /** 
     * 统计公司客户和散客信息总记录数
     * @author 101911-foss-zhouChunlai
     * @date 2013-3-21 下午3:34:24
     */
    Long queryExtWithSinglePeopleRecordCount(CustomerEntity entity) ;
    
    
    /**
     * 统计关联客户信息总记录数 
     * @author 101911-foss-zhouChunlai
     * @date 2013-3-12 上午11:14:23
     */
    Long queryExtRecordCount(CustomerEntity entity);
    
    /**
     * 通过传入查询条件集合dto，返回会员信息的对象集合list 
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-10-20 上午11:42:44
     * @param condition
     * @return
     * @see
     */
    List<CustomerContactDto> queryCustomerInfo(CustomerQueryConditionDto condition);
    
    /**
     * 通过传入一个客户编码查询出对应的客户信息
     *  
     * @author 094463-foss-xieyantao
     * @date 2012-10-20 上午11:48:46
     * @param custCode 客户编码
     * @return
     * @see
     */
    CustomerDto queryCustInfoByCode(String custCode);
    
    /**
     * 通过传入一个客户实体类来查询客户信息
     * 
     * @author 261997-foss-css
     * @date 2015-6-15 下午14:20:46
     * @param customerEntity
     *            客户实体类
     * @return
     * @see
     */
    CustomerEntity queryCustInfoByCustomerEntity(CustomerEntity customerEntity);  
    
    
    
    /**
     * 通过传入一个客户编码查询出财务未作废客户信息
     *  
     * @author 094463-foss-xieyantao
     * @date 2012-10-20 上午11:48:46
     * @param custCode 客户编码
     * @return
     * @see
     */
    List<CustomerEntity> queryNoDeletedCustInfoByCode(String custCode);
    
    /**
     * 通过传入一个客户名称查询出对应的客户信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-20 上午11:53:55
     * @param custName 客户名称
     * @return 
     * @see
     */
    List<CustomerDto> queryCustInfoByName(String custName);
    
    /**
     * 通过传入一组电话号码、部门Code返回部门的客户信息列表 
     * @author 094463-foss-xieyantao
     * @date 2012-10-25 上午8:32:01
     * @param phoneList 电话号码列表
     * @param deptCode  部门Code
     * @return 客户信息列表
     * @see
     */
    List<CustomerContactDto> queryCustInfoByPhone(List<String> phoneList,String deptCode);
    
    /**
     * 通过传入手机号、部门Code，返回部门的客户信息
     * @author 094463-foss-xieyantao
     * @date 2012-10-25 上午8:35:08
     * @param mobilePhone 手机号码
     * @param deptCode 部门code
     * @return 客户以及客户联系人信息
     * @see
     */
    CustomerContactDto queryCustInfoByMobile(String mobilePhone,String deptCode);
    
    /**
     * 通过传入部门编码查询该部门下所有客户信息：客户编码、客户名称
     * @author 094463-foss-xieyantao
     * @date 2012-10-26 上午10:15:00
     * @param unifiedCode 部门标杆编码
     * @return 客户信息列表
     * @see
     */
    List<CustomerEntity> queryCusInfosByDeptCode(String unifiedCode);
    
    /**
     * <p>根据客户联系人地址ID，查询出客户的收货习惯</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-5 上午11:26:34
     * @param contactAddressId 联系人地址ID
     * @return 存在：联系人地址实体（偏好起始时间、偏好结束时间、是否送货上楼、其他要求）
     *        <P> 不存在：null</p>
     * @see
     */
    ContactAddressEntity queryContactAddressById(String contactAddressId);
    
    /**
     * <p>根据crmId,最后一次修改时间查询客户是否存在</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-26 上午10:15:29
     * @param crmId
     * @param lastupdatetime
     * @return
     * @see
     */
    boolean queryCustomerByCrmId(BigDecimal crmId,Date lastupdatetime);
    
    /**
     * <p>根据crmId查询客户实体</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-23 上午9:30:28
     * @param crmId 客户CRM ID
     * @return
     * @see
     */
    CustomerEntity queryCustomerByCrmId(BigDecimal crmId);
    
    /**
     * <p>根据合同ID查询合同绑定部门</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-12-6 上午10:45:00
     * @param bargainId
     * @return
     * @see
     */
    List<BargainAppOrgEntity> queryAppOrgEntitiesByBargainId(BigDecimal bargainId);
    
	/**
	 * 根据条件查询包含账号信息的客户信息
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-4 下午3:58:33
	* @return List<CustomerDto> :包含客户账号信息的客户信息
	* @param  condition:查询条件
	 */
	List<CustomerDto> queryCustAndAccountsInfoByCondition(
			CustomerQueryConditionDto condition);

	/**
	 * 根据条件查询包含账号信息的客户信息总条数
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-4 下午4:23:05
	* @return List<CustomerDto>
	* @param 
	 */
	public long countCustAndAccountsInfoByCondition(
			CustomerQueryConditionDto condition);

	/**
	 * 根据条件查询包含客户(会员客户和散户)账号信息
	 * @author 101911-foss-zhouChunlai
	 * @date 2013-3-13 上午10:01:38
	 */
	List<CusAccountDto> queryCustAccountsInfoByCondition(CustomerQueryConditionDto condition);

	  
    /** 
     * 通过传入查询条件集合dto，返回客户信息的对象集合list 
     * @author 026123-foss-lifengteng
     * @date 2013-1-11 下午3:14:25
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao#queryCustomerInfo(com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto)
     */
	List<CustomerQueryConditionDto> queryCustomerByCondition(CustomerQueryConditionDto condition);

	/**
	 * 通过传入查询条件集合dto，返回客户的合同信息对象
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-1 下午7:46:39
	 */
	CustomerQueryConditionDto queryBargainByCondition(CustomerQueryConditionDto condition);

	/**
	 * 查询客户记录总数
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
	
	/**
	 * 根据当前时间和合同的CRM_ID查询发票标记信息
	 * @param date
	 * @param braginCrmId
	 * @return
	 */
	String queryComtomerInvoiceType(Date date,BigDecimal braginCrmId);
	
    /**
     * 根据开单界面传入的客户查询条件查询客户信息（散客）
     * crm2
     * auther:wangpeng_078816
     * date:2014-4-10
     *
     */
	List<CustomerQueryConditionDto> queryNonfixedCustomerByCondition(CustomerQueryConditionDto dto);
	
	
	/**
	 * 通过custcode 模糊查询 符合条件的数据
	 * @param date
	 * @param collection
	 * @author yuting
     * @data 2014年7月9日 下午4:30:16
	 */
	public List<CustomerEntity> queryCustomersByCollection();

	
	/**
	 * 通过custcode,customermobile 查询联系人的名称
	 * @param custCode  客户编码
	 * @param customerMobile  联系人手机号
	 * @author yuting
     * @data 2014年7月18日 下午4:30:16
	 */
	String queryContactNameByCustCodeAndcustMobile(String custCode,
			String customerMobile);

	/**
	 * 通过客户编码查询客户状态
	 * @param code  客户编码
	 * @author yuting
     * @data 2014年7月18日 下午4:30:16
	 */
	CustomerEntity queryCustStateByCode(String code);

	
	/**
	 * 通过客户编码查询客户状态
	 * @param unifiedCode  标杆编码
	 * @author yuting
     * @data 2014年7月22日 下午1:35:20
	 */
	String queryOrgCodeByUnifiedCode(String unifiedCode);

	/**
	 * 通过客户手机号查询客户编码
	 * @param customerMobile  客户手机
	 * @author yuting
     * @data 2014年7月23日 下午1:51:20
	 */
	String queryCustCodeByCustMobile(String customerMobile);
	
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
	 * <p>根据合同ID查询合同适用部门名称</p> 
	 * @author 151211 
	 * @date 2015-3-23 上午8:14:18
	 * @param bargainId
	 * @return
	 * @see
	 */
	List<String> queryAppOrgNameByBargainId(BigDecimal bargainId);
	
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
     * 
     * 根据客户编码查询客户信息（是否大客户标记 客户分群） 
     * @author 308861 
     * @date 2017-1-11 下午4:39:54
     * @param code
     * @return
     * @see
     */
    CustomerNewEntity customerByCusCode(String code);
    
	/**
	 * <p>客户表与客户账户表关联后的信息查询，用于开户人姓名选择器</p> 
	 * @author 232607 
	 * @date 2015-11-4 上午11:31:45
	 * @param customerCondDto
	 * @param start 
	 * @param limit 
	 * @return
	 * @see
	 */
	List<CusAccountDto> queryCusAccountJoinCus(CusAccountDto customerCondDto, int limit, int start);

	/**
	 * <p>客户表与客户账户表关联后的信息查询，用于开户人姓名选择器</p> 
	 * @author 232607 
	 * @date 2015-11-4 上午11:31:48
	 * @param customerCondDto
	 * @return
	 * @see
	 */
	long queryCusAccountJoinCusCount(CusAccountDto customerCondDto);

    /**
     * 验证散客是否存在
     * @param code
     * @return
     */
	boolean queryExistsCustInfoByCode(String code);
	 /**
     * 根据合同crmId，查询是否存在有生效的，是否精准 包裹为 “Y” 的优惠信息
     * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
     * @author 268984 
     * @date 2016-7-22 下午2:29:01
     * @return
     * @see
     */
	long queryIscpackBybargainCrmId(BigDecimal braginCrmId);
}
