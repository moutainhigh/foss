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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonCustomerService.java
 * 
 * FILE NAME        	: CommonCustomerService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector
 * FILE    NAME: CommonCustomerService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAreaAddressService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonCustomerService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CusAccountDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.util.CollectionUtils;

/**
 * 公共选择器--客户信息查询service实现.
 * 
 * @author 078823-foss-panGuangJun
 * @date 2012-12-4 下午3:49:18
 */
@Transactional
public class CommonCustomerService implements ICommonCustomerService {

	/** The customer dao. */
	private ICustomerDao customerDao;
	private IAreaAddressService areaAddressService;
	private IDataDictionaryValueService dataDictionaryValueService;
	/**
	 * 组织信息 Service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * 查询客户基本信息.
	 * 
	 * @param entity
	 *            the entity
	 * @param start
	 *            the start
	 * @param limit
	 *            the limit
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-4 下午3:49:18
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonCustomerService#queryCustomers(com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity,
	 *      int, int)
	 */
	@Override
	public List<CustomerEntity> queryCustomers(CustomerEntity entity,int limit, int start) {
		List<CustomerEntity> customerEntitys = new ArrayList<CustomerEntity>();
			if(("true").equals(entity.getAll())){
				entity.setActive(null);
			if (StringUtils.isNotBlank(entity.getUnifiedCode())) {
				String orgCode = entity.getUnifiedCode();
				OrgAdministrativeInfoEntity orgInfo = getOrgInfo(orgCode);
				entity.setUnifiedCode(orgInfo.getUnifiedCode());
			}
			// 313353 sonar优化
			if(StringUtils.isNotBlank(entity.getSinglePeopleFlag()) ||
					(StringUtils.isNotBlank(entity.getContcatFlag()) && StringUtils.isNotBlank(entity.getSinglePeopleFlag()))){
				//即客户信息可按手机号查询又要查散客信息
				customerEntitys= customerDao.queryExtCustomersWithSinglePeopleInfo(entity, limit, start);
			}else if (StringUtils.isNotBlank(entity.getContcatFlag())) {
				//公司客户信息可按手机号查询
				customerEntitys= customerDao.queryExtCustomers(entity, limit, start);
			// 313353 sonar优化
//			}else if (StringUtils.isNotBlank(entity.getSinglePeopleFlag())) {
//				//查询公司客户和散客信息
//				customerEntitys= customerDao.queryExtCustomersWithSinglePeopleInfo(entity, limit, start);
			} else {
				//查询公司客户信息
				customerEntitys= customerDao.queryCustomers(entity, limit, start);
			}
		}else{
			if (StringUtils.isNotBlank(entity.getUnifiedCode())) {
				String orgCode = entity.getUnifiedCode();
				OrgAdministrativeInfoEntity orgInfo = getOrgInfo(orgCode);
				entity.setUnifiedCode(orgInfo.getUnifiedCode());
			}
			//313353 sonar优化
			if(StringUtils.isNotBlank(entity.getSinglePeopleFlag()) ||
					(StringUtils.isNotBlank(entity.getContcatFlag()) && StringUtils.isNotBlank(entity.getSinglePeopleFlag()))){
				//即客户信息可按手机号查询又要查散客信息
				customerEntitys= customerDao.queryExtCustomersWithSinglePeopleInfo(entity, limit, start);
			}else if (StringUtils.isNotBlank(entity.getContcatFlag())) {
				//公司客户信息可按手机号查询
				customerEntitys= customerDao.queryExtCustomers(entity, limit, start);
			//313353 sonar优化
//			}else if (StringUtils.isNotBlank(entity.getSinglePeopleFlag())) {
//				//查询公司客户和散客信息
//				customerEntitys= customerDao.queryExtCustomersWithSinglePeopleInfo(entity, limit, start);
			} else {
				//查询公司客户信息
				customerEntitys= customerDao.queryCustomers(entity, limit, start);
			}
			
		}
			return customerEntitys;
	}
	/**
	 * 查询客户基本信息的总条数.
	 * 
	 * @param entity
	 *            the entity
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-4 下午3:49:18
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonCustomerService#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity)
	 */
	@Override
	public Long queryRecordCount(CustomerEntity entity) {
		if(("true").equals(entity.getAll())){
			entity.setActive(null);
			if (StringUtils.isNotBlank(entity.getUnifiedCode())) {
				String orgCode = entity.getUnifiedCode();
				OrgAdministrativeInfoEntity orgInfo = getOrgInfo(orgCode);
				entity.setUnifiedCode(orgInfo.getUnifiedCode());
			}
			//313353 sonar优化
			if(StringUtils.isNotBlank(entity.getSinglePeopleFlag()) ||
					(StringUtils.isNotBlank(entity.getContcatFlag()) && StringUtils.isNotBlank(entity.getSinglePeopleFlag()))){
				//即客户信息可按手机号查询又要查散客信息
				return customerDao.queryExtWithSinglePeopleRecordCount(entity);
			}else if (StringUtils.isNotBlank(entity.getContcatFlag())) {
				//公司客户信息可按手机号查询
				return customerDao.queryExtRecordCount(entity);
			//313353 sonar优化
//			}else if (StringUtils.isNotBlank(entity.getSinglePeopleFlag())) {
//				//查询公司客户和散客信息
//				return customerDao.queryExtWithSinglePeopleRecordCount(entity);
			} else {
				//查询公司客户信息
				return customerDao.queryRecordCount(entity);
			}
		}else{
			if (StringUtils.isNotBlank(entity.getUnifiedCode())) {
				String orgCode = entity.getUnifiedCode();
				OrgAdministrativeInfoEntity orgInfo = getOrgInfo(orgCode);
				entity.setUnifiedCode(orgInfo.getUnifiedCode());
			}
			//313353 sonar优化
			if(StringUtils.isNotBlank(entity.getSinglePeopleFlag()) ||
					(StringUtils.isNotBlank(entity.getContcatFlag()) && StringUtils.isNotBlank(entity.getSinglePeopleFlag()))){
				//即客户信息可按手机号查询又要查散客信息
				return customerDao.queryExtWithSinglePeopleRecordCount(entity);
			}else if (StringUtils.isNotBlank(entity.getContcatFlag())) {
				//公司客户信息可按手机号查询
				return customerDao.queryExtRecordCount(entity);
			//313353 sonar优化
//			}else if (StringUtils.isNotBlank(entity.getSinglePeopleFlag())) {
//				//查询公司客户和散客信息
//				return customerDao.queryExtWithSinglePeopleRecordCount(entity);
			} else {
				//查询公司客户信息
				return customerDao.queryRecordCount(entity);
			}			
		}
	}

	/**
	 * 根据部门编码获取部门标杆编码
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @param
	 * @date 2013-1-11 上午11:18:36
	 * @return
	 */
	private OrgAdministrativeInfoEntity getOrgInfo(String deptCode) {
		return orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(deptCode);
	}

	/**
	 * 查询包含客户账号信息的集合.
	 * 
	 * @param condition
	 *            the condition
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-4 下午3:49:18
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonCustomerService#queryCustomerInfo(com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto)
	 */
	@Override
	public List<CusAccountDto> queryCustomerAndAccountsInfo(			
			CustomerQueryConditionDto condition) {
		List<CustomerDto> customerDtos = customerDao
				.queryCustAndAccountsInfoByCondition(condition);
		List<CusAccountDto> cusAccountList = new ArrayList<CusAccountDto>();
		CusAccountDto cusAccountDto = null;
		if (CollectionUtils.isNotEmpty(customerDtos)) {
			for (int i = 0; i < customerDtos.size(); i++) {
				if (CollectionUtils.isNotEmpty(customerDtos.get(i)
						.getBankAccountList())) {
					for (int j = 0; j < customerDtos.get(i)
							.getBankAccountList().size(); j++) {
						cusAccountDto = new CusAccountDto();
						BeanUtils.copyProperties(customerDtos.get(i),
								cusAccountDto);
						BeanUtils.copyProperties(customerDtos.get(i)
								.getBankAccountList().get(j), cusAccountDto);
						// 设置省名称
						AdministrativeRegionsEntity province = areaAddressService
								.queryRegionByCode(cusAccountDto.getProvCode());
						if (null != province) {
							cusAccountDto.setProvinceName(province.getName());
						}
						// 设置市名称
						AdministrativeRegionsEntity city = areaAddressService
								.queryRegionByCode(cusAccountDto.getCityCode());
						if (null != city) {
							cusAccountDto.setCityName(city.getName());
						}
						// 设置账户属性
						DataDictionaryValueEntity dicValue = dataDictionaryValueService
								.queryDataDictionaryValueByTermsCodeValueCode(
										DictionaryConstants.CRM_ACCOUNT_NATURE,
										cusAccountDto.getAccountNature());

						if (null != dicValue) {
							cusAccountDto.setAccountNatureName(dicValue
									.getValueName());
						}
						cusAccountList.add(cusAccountDto);
					}
				}
			}
		}
		return cusAccountList;
	}

	/**
	 * 查询包含客户或散户的账号信息集合.
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2013-3-13 上午9:52:12
	 */
	@Override
	public List<CusAccountDto> queryCustAccountsInfo(CustomerQueryConditionDto condition) {
		if(StringUtils.isBlank(condition.getCustCode())){
			return null;
		}
		List<CusAccountDto> cusAccountList = customerDao.queryCustAccountsInfoByCondition(condition);
//		if(CollectionUtils.isNotEmpty(cusAccountList)){
//			for (CusAccountDto cusAccountDto : cusAccountList) {
//				// 设置省名称
//				AdministrativeRegionsEntity province = areaAddressService
//						.queryRegionByCode(cusAccountDto.getProvCode());
//				if (null != province) {
//					cusAccountDto.setProvinceName(province.getName());
//				}
//				// 设置市名称
//				AdministrativeRegionsEntity city = areaAddressService
//						.queryRegionByCode(cusAccountDto.getCityCode());
//				if (null != city) {
//					cusAccountDto.setCityName(city.getName());
//				}
//				// 设置账户属性
//				DataDictionaryValueEntity dicValue = dataDictionaryValueService
//						.queryDataDictionaryValueByTermsCodeValueCode(
//								DictionaryConstants.CRM_ACCOUNT_NATURE,
//								cusAccountDto.getAccountNature());
//
//				if (null != dicValue) {
//					cusAccountDto.setAccountNatureName(dicValue.getValueName());
//				}
//			}
//		}
		
		return cusAccountList;
	}

	/** 
	 * <p>客户表与客户账户表关联后的信息查询，用于开户人姓名选择器</p> 
	 * @author 232607 
	 * @date 2015-11-4 上午11:29:31
	 * @param customerCondDto
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonCustomerService#queryCusAccountJoinCus(com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto)
	 */
	@Override
	public List<CusAccountDto> queryCusAccountJoinCus(CusAccountDto customerCondDto, int limit, int start) {
		List<CusAccountDto> list=customerDao.queryCusAccountJoinCus(customerCondDto, limit, start);
		return list;
	}
	/** 
	 * <p>客户表与客户账户表关联后的信息查询，用于开户人姓名选择器</p> 
	 * @author 232607 
	 * @date 2015-11-4 上午11:29:34
	 * @param customerCondDto
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonCustomerService#queryCusAccountJoinCusCount(com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto)
	 */
	@Override
	public long queryCusAccountJoinCusCount(CusAccountDto customerCondDto) {
		long total=customerDao.queryCusAccountJoinCusCount(customerCondDto);
		return total;
	}
	
	/**
	 * setter.
	 * 
	 * @param customerDao
	 *            the new customer dao
	 */
	public void setCustomerDao(ICustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	/**
	 * setter
	 */
	public void setAreaAddressService(IAreaAddressService areaAddressService) {
		this.areaAddressService = areaAddressService;
	}

	/**
	 * setter
	 */
	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}


}
