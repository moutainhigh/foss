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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/CustomerDao.java
 * 
 * FILE NAME        	: CustomerDao.java
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
package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao;
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
import com.deppon.foss.util.define.FossConstants;

/**
 * 客户信息Dao接口实现：对客户信息增删改查操作
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-10-20 下午2:38:43
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-10-20 下午2:38:43
 * @since
 * @version
 */
public class CustomerDao extends SqlSessionDaoSupport implements ICustomerDao {

    private static final String NAMESPACE = "foss.bse.bse-baseinfo.customer.";

    /**
     * 新增客户
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-10-20 下午2:38:43
     * @param entity
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao#addCustomer(com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity)
     */
    @Override
    public int addCustomer(CustomerEntity entity) {

	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }

    /**
     * 根据code作废客户
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-10-20 下午2:28:27
     * @param crmCusId
     *            CRM客户ＩＤ
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao#deleteCustomerByCode(java.lang.String[])
     */
    @Override
    public int deleteCustomerByCode(BigDecimal crmCusId, String modifyUser) {

	Map<String, Object> map = new HashMap<String, Object>();
	map.put("crmCusId", crmCusId);
	map.put("active0", FossConstants.ACTIVE);
	map.put("active", FossConstants.INACTIVE);
	map.put("modifyUser", modifyUser);
	map.put("modifyDate", new Date());

	return this.getSqlSession().update(NAMESPACE + "deleteByCode", map);
    }

    /**
     * 修改客户
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-10-20 下午2:38:44
     * @param entity
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao#updateCustomer(com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity)
     */
    @Override
    public int updateCustomer(CustomerEntity entity) {

	return this.getSqlSession().update(NAMESPACE + "update", entity);
    }

    /**
     * 根据传入对象查询符合条件所有客户信息
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-10-20 下午2:38:44
     * @param entity
     * @param limit
     * @param start
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao#queryCustomers(com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity,
     *      int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<CustomerEntity> queryCustomers(CustomerEntity entity,
	    int limit, int start) {

	RowBounds rowBounds = new RowBounds(start, limit);

	return this.getSqlSession().selectList(NAMESPACE + "queryAllInfos",
		entity, rowBounds);
    }

    
    /** 
     * 根据传入对象查询符合条件所有客户信息
     * @author 101911-foss-zhouChunlai
     * @date 2013-3-12 上午11:18:26
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao#queryCustomers(com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<CustomerEntity> queryExtCustomers(CustomerEntity entity,
	    int limit, int start) {
	RowBounds rowBounds = new RowBounds(start, limit);
	return this.getSqlSession().selectList(NAMESPACE + "queryExtAllInfos",
		entity, rowBounds);
    }
    /**
     * 统计总记录数
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-10-20 下午2:38:44
     * @param entity
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity)
     */
    @Override
    public Long queryRecordCount(CustomerEntity entity) {
	return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryCount",
		entity);
    }
    
    
    /** 
     * 根据传入对象查询符合条件公司客户和散客信息
     * @author 101911-foss-zhouChunlai
     * @date 2013-3-21 下午3:32:57
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao#queryExtCustomersWithSinglePeopleInfo(com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<CustomerEntity> queryExtCustomersWithSinglePeopleInfo(CustomerEntity entity,int limit, int start) {
	RowBounds rowBounds = new RowBounds(start, limit);
	return this.getSqlSession().selectList(NAMESPACE + "queryExtCustWithSingleInfo",entity, rowBounds);
    }
  
    /** 
     * 统计公司客户和散客信息总记录数
     * @author 101911-foss-zhouChunlai
     * @date 2013-3-21 下午3:34:24
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity)
     */
    @Override
    public Long queryExtWithSinglePeopleRecordCount(CustomerEntity entity) {
	return (Long) this.getSqlSession().selectOne(NAMESPACE + "countExtCustWithSinglePeople",
		entity);
    }
    
    /** 
     * 统计关联客户信息总记录数 
     * @author 101911-foss-zhouChunlai
     * @date 2013-3-12 上午11:15:10
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao#queryExtRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity)
     */
    @Override
    public Long queryExtRecordCount(CustomerEntity entity) {
    	return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryExtCount",entity);
    }
    /**
     * 通过传入查询条件集合dto，返回会员信息的对象集合list
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-10-20 下午2:38:44
     * @param condition
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao#queryCustomerInfo(com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<CustomerContactDto> queryCustomerInfo(
	    CustomerQueryConditionDto condition) {

	Map<String, Object> map = new HashMap<String, Object>();
	map.put("condition", condition);
	map.put("active", FossConstants.ACTIVE);

	return this.getSqlSession().selectList(NAMESPACE + "queryCustomerInfo",
		map);
    }

    /**
     * 通过传入一个客户编码查询出对应的客户信息,但是查询客户合同适用部门不放在ibatis文件查询客户CustomerDto中，
     * 修改人：151211，杨套红，20150320，修复大客户绑定合同适用全国营业部开单反应慢问题
     * @author 094463-foss-xieyantao
     * @date 2012-10-20 下午2:38:44
     * @param custCode
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao#queryCustInfoByCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
	public CustomerDto queryCustInfoByCode(String custCode) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("active", FossConstants.ACTIVE);
		map.put("custCode", custCode);
		/*CN-282合同邦定部门过多导致开单卡顿问题：优化SQL
				 * List<CustomerDto> list中不再查询该客户合同绑定的适用部门List
				 * 客户合同适用部门改变接口增加部门参数
				 * 151211,杨套红
				 * 20150320
				 * */
		List<CustomerDto> list = this.getSqlSession().selectList(
				NAMESPACE + "queryCustInfoByCodeNew", map);
		CustomerDto custdto = null;
		if (CollectionUtils.isNotEmpty(list)) {
			if (list.size() > 1) {
				custdto = getCustomerDto(list, custdto);
			} else{
				custdto = list.get(0);
			}
			return custdto;
		} else {
			return null;
		}
	}

	private CustomerDto getCustomerDto(List<CustomerDto> list,
			CustomerDto custdto) {
		String fossId = "";
		for (CustomerDto dto : list) {
			if (null == dto) {
				continue;
			} else {
				BigDecimal crmCustid = dto.getCrmCusId();
				if (null == crmCustid) {
					if(StringUtils.isNotEmpty(fossId)){
						continue;
					}
					fossId = dto.getId();
				} else {
					if(null != custdto){
						continue;
					}
					custdto = dto;
				}
				if(StringUtils.isNotEmpty(fossId) && null != custdto){
					dto.setId(fossId);
					break;
				}
			}
		}
		return custdto;
	}
    
    
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
    @Override
	public CustomerEntity queryCustInfoByCustomerEntity(
			CustomerEntity customerEntity) {
    	
    	if(StringUtils.isBlank(customerEntity.getCusCode())){
    	     return null;
         }
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("active", FossConstants.ACTIVE);
    	//客户编码
    	map.put("cusCode", customerEntity.getCusCode());
    	//客户固定手机 fixedReceiveMobile
    	map.put("fixedReceiveMobile", customerEntity.getFixedReceiveMobile());
    	List<CustomerEntity> list = this.getSqlSession().selectList(
        			NAMESPACE + "queryCustInfoByCustomerEntity", map);
		if (CollectionUtils.isEmpty(list)) {
		    return null;
		} else {
		    return list.get(0);
		}
	}

	/**
     * 通过传入一个客户编码查询出财务未作废客户信息
     *  
     * @author 094463-foss-xieyantao
     * @date 2012-10-20 上午11:48:46
     * @param custCode 客户编码
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<CustomerEntity> queryNoDeletedCustInfoByCode(String custCode){
	Map<String, String> map = new HashMap<String, String>();
	map.put("isDelete", FossConstants.NO);
	map.put("custCode", custCode);

	return this.getSqlSession().selectList(
		NAMESPACE + "queryNoDeletedCustInfoByCode", map);
    }
    
    /**
     * 通过传入一个客户名称查询出对应的客户信息
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-10-20 下午2:38:44
     * @param custName
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao#queryCustInfoByName(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<CustomerDto> queryCustInfoByName(String custName) {

	Map<String, Object> map = new HashMap<String, Object>();
	map.put("custName", custName);
	map.put("active", FossConstants.ACTIVE);

	return this.getSqlSession().selectList(
		NAMESPACE + "queryCustInfoByName", map);
    }

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
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao#queryCustInfoByPhone(java.util.List,
     *      java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<CustomerContactDto> queryCustInfoByPhone(
	    List<String> phoneList, String deptCode) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("phoneList", phoneList);
	map.put("active", FossConstants.ACTIVE);
	map.put("deptCode", deptCode);

	return this.getSqlSession().selectList(
		NAMESPACE + "queryCustInfoByPhone", map);
    }

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
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao#queryCustInfoByMobile(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public CustomerContactDto queryCustInfoByMobile(String mobilePhone,
	    String deptCode) {

	Map<String, Object> map = new HashMap<String, Object>();
	map.put("mobilePhone", mobilePhone);
	map.put("deptCode", deptCode);
	map.put("active", FossConstants.ACTIVE);

	return (CustomerContactDto) this.getSqlSession().selectOne(
		NAMESPACE + "queryCustInfoByMobile", map);
    }

    /**
     * 通过传入部门标杆编码查询该部门下所有客户信息：客户编码、客户名称
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-10-26 上午10:15:00
     * @param unifiedCode
     *            部门标杆编码
     * @return 客户信息列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao#queryCusInfosByDeptCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<CustomerEntity> queryCusInfosByDeptCode(String unifiedCode) {

	Map<String, String> map = new HashMap<String, String>();
	map.put("unifiedCode", unifiedCode);
	map.put("active", FossConstants.ACTIVE);

	return this.getSqlSession().selectList(
		NAMESPACE + "queryCusInfosByDeptCode", map);
    }

    /**
     * <p>
     * 根据客户联系人地址ID，查询出客户的收货习惯
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-11-5 上午13:54:34
     * @param contactAddressId
     *            联系人地址ID
     * @return 存在：联系人实体（偏好起始时间、偏好结束时间、是否送货上楼、其他要求）
     *         <P>
     *         不存在：null
     *         </p>
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao#queryContactAddressById(java.lang.String)
     */
    @Override
    public ContactAddressEntity queryContactAddressById(String contactAddressId) {

	Map<String, String> map = new HashMap<String, String>();
	map.put("active", FossConstants.ACTIVE);
	map.put("contactAddressId", contactAddressId);
	return (ContactAddressEntity) this.getSqlSession().selectOne(
		NAMESPACE + "queryContactAddressById", map);
    }

    /**
     * <p>
     * 根据crmId,最后一次修改时间查询客户是否存在
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-11-26 下午3:11:21
     * @param crmId
     * @param lastupdatetime
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao#queryCustomerByCrmId(java.math.BigDecimal,
     *      java.util.Date)
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean queryCustomerByCrmId(BigDecimal crmId, Date lastupdatetime) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("modifyDate", lastupdatetime);
	map.put("crmId", crmId);
	map.put("active", null);

	List<CustomerEntity> list = this.getSqlSession().selectList(
		NAMESPACE + "queryCustomerByCrmId", map);
	return CollectionUtils.isNotEmpty(list);
    }
    
    /**
     * <p>根据crmId查询客户实体</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-23 上午9:30:28
     * @param crmId 客户CRM ID
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao#queryCustomerByCrmId(java.math.BigDecimal)
     */
    @SuppressWarnings("unchecked")
    @Override
    public CustomerEntity queryCustomerByCrmId(BigDecimal crmId) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("modifyDate", null);
	map.put("crmId", crmId);
	map.put("active", FossConstants.ACTIVE);

	List<CustomerEntity> list = this.getSqlSession().selectList(
		NAMESPACE + "queryCustomerByCrmId", map);
	if (CollectionUtils.isEmpty(list)) {
	    return null;
	} else {
	    return list.get(0);
	}
    }

    /**
     * 查询包含账号信息的客户客户信息
     * 
     * @author 078823-foss-panGuangJun
     * @date 2012-12-4 下午4:00:48
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao#queryCustAndAccountsInfoByCondition(com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<CustomerDto> queryCustAndAccountsInfoByCondition(
	    CustomerQueryConditionDto condition) {
	List<CustomerDto> list = this.getSqlSession().selectList(
		NAMESPACE + "queryCustAndAccountsInfoByCondition", condition);
	return list;
    }

    /** 
     * 查询包含散客或会员客户的账号信息
     * @author 101911-foss-zhouChunlai
     * @date 2013-3-13 上午9:45:50
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao#queryCustAndAccountsInfoByCondition(com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<CusAccountDto> queryCustAccountsInfoByCondition(
	    CustomerQueryConditionDto condition) {
	List<CusAccountDto> list = this.getSqlSession().selectList(
		NAMESPACE + "queryCustAccountsInfoByCondition", condition);
	return list;
    }

    
    /**
     * 查询包含账号信息的客户客户信息总条数
     * 
     * @author 078823-foss-panGuangJun
     * @date 2012-12-4 下午4:00:48
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao#queryCustAndAccountsInfoByCondition(com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto)
     */
    @Override
    public long countCustAndAccountsInfoByCondition(
	    CustomerQueryConditionDto condition) {
	Object obj = this.getSqlSession().selectOne(
		NAMESPACE + "countCustAndAccountsInfoByCondition", condition);
	return (Long) obj;
    }

    /**
     * <p>
     * 根据合同ID查询合同绑定部门
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-12-6 上午10:46:38
     * @param bargainId
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao#queryAppOrgEntitiesByBargainId(java.math.BigDecimal)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BargainAppOrgEntity> queryAppOrgEntitiesByBargainId(
	    BigDecimal bargainId) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("active", FossConstants.ACTIVE);
	map.put("bargainId", bargainId);

	return this.getSqlSession().selectList(
		NAMESPACE + "queryAppOrgEntitiesByBargainId", map);
    }

    /**
     * 通过传入查询条件集合dto，返回客户信息的对象集合list
     * 
     * @author 026123-foss-lifengteng
     * @date 2013-1-11 下午3:14:25
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao#queryCustomerInfo(com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<CustomerQueryConditionDto> queryCustomerByCondition(
	    CustomerQueryConditionDto condition) {
	Map<String, Object> map = new HashMap<String, Object>();
	//2015-09-23 DATE-6211模糊查询问题，在向接送货确认无影响后改为精确查询
	condition.setExactQuery(true);
	map.put("condition", condition);
	map.put("active", FossConstants.ACTIVE);
	return this.getSqlSession().selectList(
		NAMESPACE + "queryCustomerByCondition", map);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<CustomerQueryConditionDto> queryCustomerByConditionByPage(
    		CustomerPaginationDto condition) {
		Map<String, Object> map = new HashMap<String, Object>();
		//2015-09-23 DATE-6211模糊查询问题，在向接送货确认无影响后改为精确查询
		condition.setExactQuery(true);
		map.put("condition", condition);
		map.put("active", FossConstants.ACTIVE);
		RowBounds rowBounds = new RowBounds(condition.getPageNum()-1, condition.getPageSize());
		return this.getSqlSession().selectList(
			NAMESPACE + "queryCustomerByCondition", map, rowBounds);
    }
    
    
    /**
	 * 查询客户记录总数
	 * @param condition
	 * @return
	 */
    public int countCustomerByCondition(CustomerPaginationDto condition){
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("condition", condition);
    	map.put("active", FossConstants.ACTIVE);
    	return (Integer)this.getSqlSession().selectOne(
    		NAMESPACE + "countCustomerByCondition", map);
    }
    
	/**
	 * 通过传入查询条件集合dto，返回客户的合同信息对象
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-1 下午7:46:39
	 */
    @Override
	public CustomerQueryConditionDto queryBargainByCondition(CustomerQueryConditionDto condition) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("condition", condition);
		map.put("active", FossConstants.ACTIVE);
		return (CustomerQueryConditionDto) this.getSqlSession().selectOne(NAMESPACE + "queryBargainByCondition", map);
	}
    
   /**
    * 根据当前时间和合同crm_ID查询发票标记
    * @author 132599-foss-shenweihua
    * @date 2013-11-26 下午6:46:39
    */
	@Override
	public String queryComtomerInvoiceType(Date date, BigDecimal braginCrmId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("date", date);
		map.put("braginCrmId",braginCrmId);
		return (String)this.getSqlSession().selectOne(NAMESPACE + "queryComtomerInvoiceType", map);
	}
    /**
     * 根据合同crmId，查询是否存在有生效的，是否精准 包裹为 “Y” 的优惠信息
     * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
     * @author 268984 
     * @date 2016-7-22 下午2:29:01
     * @return
     * @see
     */
	@Override
	public long queryIscpackBybargainCrmId(BigDecimal braginCrmId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("braginCrmId",braginCrmId);
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryIscpackBybargainCrmId", map);
	}
	/**
     * 根据开单界面传入的客户查询条件查询客户信息（散客）
     * crm2
     * auther:wangpeng_078816
     * date:2014-4-10
     *
     */
    @SuppressWarnings("unchecked")
	@Override
    public List<CustomerQueryConditionDto> queryNonfixedCustomerByCondition(
		CustomerQueryConditionDto dto) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("condition", dto);
    	map.put("active", FossConstants.ACTIVE);
    	return (List<CustomerQueryConditionDto>)this.getSqlSession().selectList(
    		NAMESPACE + "queryNonfixedCustomerByCondition", map);
    }
    
    
    

	/***
	 * 通过条件查询符合条件的数据
	 * @author yuting@163.com
	 * @date 2014-07-12 下午6:46:39
	 */
	@SuppressWarnings("unchecked")
	public List<CustomerEntity> queryCustomersByCollection() {
		return this.getSqlSession().selectList(NAMESPACE+"queryCustomersByExpSmsLimit");
	}

	
	/**
	 * 通过custcode,customermobile 查询联系人的名称
	 * @param custCode  客户编码
	 * @param customerMobile  联系人手机号
	 * @author yuting
     * @data 2014年7月18日 下午4:30:16
	 */
	@Override
	public String queryContactNameByCustCodeAndcustMobile(String custCode,
			String customerMobile) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("custCode",  custCode);
		paraMap.put("customerMobile",  customerMobile);
		return (String) this.getSqlSession().selectOne(NAMESPACE+"queryContactNameByCustCodeAndcustMobile", paraMap);
	}

	/**
	 * 通过客户编码查询客户状态
	 * @param custCode  客户编码
	 * @author yuting
     * @data 2014年7月18日 下午4:30:16
	 */
	@Override
	public CustomerEntity queryCustStateByCode(String code) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("custCode",  code);
		return (CustomerEntity) this.getSqlSession().selectOne(NAMESPACE+"queryCustStateByCode", paraMap);
	}
	
	/**
	 * 通过客户编码查询客户状态
	 * @param unifiedCode  标杆编码
	 * @author yuting
     * @data 2014年7月22日 下午1:35:20
	 */
	@Override
	public String queryOrgCodeByUnifiedCode(String unifiedCode) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("unifiedCode",  unifiedCode);
		String orgCode = (String) this.getSqlSession().selectOne(NAMESPACE+"queryOrgCodeByUnifiedCode", paraMap);
		return orgCode;
	}
	/**
	 * 通过客户手机号查询客户编码
	 * @param customerMobile  客户手机
	 * @author yuting
     * @data 2014年7月23日 下午1:51:20
	 */
	@Override
	public String queryCustCodeByCustMobile(String customerMobile) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("customerMobile",  customerMobile);
		String code = (String) this.getSqlSession().selectOne(NAMESPACE+"queryCustCodeByCustMobile", paraMap);
		return code;
	}
	
	/**
     * 通过crm_cus_id查询客户信息
     * 
     * @author 132599-FOSS-ShenWeiHua
     * @date 2014-12-10 上午10:15:00
     * @param CRM_CUS_ID 客户信息在crm系统的id
     * @return 客户信息
     * @see
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerEntity> queryCusInfosByCrmCusId(BigDecimal crmCusId,String fossId) {
		Map<String, Object> map = new HashMap<String, Object>();
    	map.put("crmCusId", crmCusId);
    	map.put("fossId", fossId);
    	map.put("active", FossConstants.ACTIVE);
    	return (List<CustomerEntity>)this.getSqlSession().selectList(
    		NAMESPACE + "queryCusInfosByCrmCusId", map);
	}
	
	/** 	
		 * <p>根据合同ID查询合同适用部门名称</p> 	
		 * @author 151211 
		 * @date 2015-3-21 上午10:31:30
		 * @param bargainId
		 * @return 
		 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao#queryAppOrgNameByBargainId(java.math.BigDecimal)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List<String> queryAppOrgNameByBargainId(BigDecimal bargainId) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("active", FossConstants.ACTIVE);
			map.put("bargainId", bargainId);
			return this.getSqlSession().selectList(
				NAMESPACE + "queryAppOrgNameByBargainId", map);
		    }

		/**
	     * 通过客户编码查询客户信息
	     * 
	     * @author 132599-FOSS-ShenWeiHua
	     * @date 2015-03-27 上午10:15:00
	     * @param code 
	     * @return 联系人信息
	     * @see
	     */
		@SuppressWarnings("unchecked")
		@Override
		public List<ContactEntity> queryContactInfoByCusCode(String code) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("code", code);
			return this.getSqlSession().selectList(NAMESPACE + "queryContactInfoByCusCode",map);
		}

		/**
	     * 通过客户编码查询客户信息
	     * 
	     * @author 261997
	     * @date 2015-07-17 上午9:15:00
	     * @param code 
	     * @return 客户对象
	     * @see
	     */
		@Override
		public CustomerEntity queryCustomerInfoByCusCode(String code) {
			if(StringUtil.isBlank(code)){
				return null;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("code", code);
			return (CustomerEntity) this.getSqlSession().selectOne(NAMESPACE + "queryCustomerInfoByCusCode",map);
		}
		
		
		/**
		 * 
		 * 根据客户编码查询客户信息（是否大客户标记 客户分群）  
		 * @author 308861 
		 * @date 2017-1-11 下午4:40:32
		 * @param code
		 * @return 
		 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao#customerByCusCode(java.lang.String)
		 */
		@Override
		public CustomerNewEntity customerByCusCode(String code) {
			if(StringUtil.isBlank(code)){
				return null;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("code", code);
			return (CustomerNewEntity) this.getSqlSession().selectOne(NAMESPACE + "customerByCusCode",map);
		}
		
		/** 
		 * <p>客户表与客户账户表关联后的信息查询，用于开户人姓名选择器</p> 
		 * @author 232607 
		 * @date 2015-11-4 上午11:33:09
		 * @param customerCondDto
		 * @return 
		 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao#queryCusAccountJoinCus(com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List<CusAccountDto> queryCusAccountJoinCus(CusAccountDto customerCondDto, int limit, int start) {
			RowBounds rowBounds = new RowBounds(start, limit);
			return this.getSqlSession().selectList(NAMESPACE + "queryCusAccountJoinCus",customerCondDto, rowBounds);
		}

		/** 
		 * <p>客户表与客户账户表关联后的信息查询，用于开户人姓名选择器</p> 
		 * @author 232607 
		 * @date 2015-11-4 上午11:33:13
		 * @param customerCondDto
		 * @return 
		 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao#queryCusAccountJoinCusCount(com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto)
		 */
		@Override
		public long queryCusAccountJoinCusCount(CusAccountDto customerCondDto) {
			return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryCusAccountJoinCusCount",customerCondDto);
		}

		@Override
		public boolean queryExistsCustInfoByCode(String code) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("code", code);
			long count=(Long)this.getSqlSession().selectOne(NAMESPACE + "queryExistsCustInfoByCode",map);
			if(count>0){
				return true;
			}
			return false;
		}
		
		
}
