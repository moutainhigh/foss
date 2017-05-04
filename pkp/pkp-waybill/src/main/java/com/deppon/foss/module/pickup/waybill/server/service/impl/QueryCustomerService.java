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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/service/impl/QueryCustomerService.java
 * 
 * FILE NAME        	: QueryCustomerService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.server.service.impl
 * FILE    NAME: QueryCustomerService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusBargainService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusContractTaxService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.INonfixedCusAccountService;
import com.deppon.foss.module.base.baseinfo.api.server.service.INonfixedCustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusContractTaxEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OneticketornotEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerContactDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerPaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IHisDeliveryCusDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IHisReceiveCusDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IQueryCustomerService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.HisDeliveryCusEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.HisReceiveCusEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.HisCustomerDto;

/**
 * 查询会员、客户信息服务接口实现类
 * 
 * @author 026123-foss-lifengteng
 * @date 2012-10-23 下午7:42:26
 */
public class QueryCustomerService implements IQueryCustomerService {

	/**
	 * 客户服务
	 */
	private ICustomerService customerService;
	
	private static final int NUMBER_90 = 90;
	
	private ICustomerDao customerDao;
	/**
	 *  客户合同信息Service接口
	 */
	private ICusBargainService cusBargainService;
	
	/**
	 * 散客、临客服务
	 */
	private INonfixedCustomerService nonfixedCustomerService;
	
	/**
	 * 散客、临客银行帐号服务
	 */
	private INonfixedCusAccountService nonfixedCusAccountService;

	/**
	 * 历史发货客户服务
	 */
	private IHisDeliveryCusDao hisDeliveryCusDao;

	/**
	 * 历史收货客户服务
	 */
	private IHisReceiveCusDao hisReceiveCusDao;
	
	/**
	 * 员工信息
	 */
	private IEmployeeService employeeService;
	
	/**
	 * 组织服务
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 客户发票标记信息Service接口
	 */
	ICusContractTaxService cusContractTaxService;
	
	
	public void setCusContractTaxService(
			ICusContractTaxService cusContractTaxService) {
		this.cusContractTaxService = cusContractTaxService;
	}

	/**
	 * 设置组织服务
	 * @author 026123-foss-lifengteng
	 * @date 2013-7-1 下午5:19:43
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * 员工信息的set方法
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-1 上午8:11:23
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	/**
	 * @return the customerService .
	 */
	public ICustomerService getCustomerService() {
		return customerService;
	}

	/**
	 * @param customerService
	 *            the customerService to set.
	 */
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	/**
	 * @return the hisDeliveryCusDao .
	 */
	public IHisDeliveryCusDao getHisDeliveryCusDao() {
		return hisDeliveryCusDao;
	}

	/**
	 * @param hisDeliveryCusDao
	 *            the hisDeliveryCusDao to set.
	 */
	public void setHisDeliveryCusDao(IHisDeliveryCusDao hisDeliveryCusDao) {
		this.hisDeliveryCusDao = hisDeliveryCusDao;
	}

	/**
	 * @return the hisReceiveCusDao .
	 */
	public IHisReceiveCusDao getHisReceiveCusDao() {
		return hisReceiveCusDao;
	}

	/**
	 * @param hisReceiveCusDao
	 *            the hisReceiveCusDao to set.
	 */
	public void setHisReceiveCusDao(IHisReceiveCusDao hisReceiveCusDao) {
		this.hisReceiveCusDao = hisReceiveCusDao;
	}
	


	
	/**
	 * @return the nonfixedCustomerService .
	 */
	public INonfixedCustomerService getNonfixedCustomerService() {
		return nonfixedCustomerService;
	}

	
	/**
	 *@param nonfixedCustomerService the nonfixedCustomerService to set.
	 */
	public void setNonfixedCustomerService(INonfixedCustomerService nonfixedCustomerService) {
		this.nonfixedCustomerService = nonfixedCustomerService;
	}
	
	/**
	 * @return the nonfixedCusAccountService .
	 */
	public INonfixedCusAccountService getNonfixedCusAccountService() {
		return nonfixedCusAccountService;
	}

	
	/**
	 *@param nonfixedCusAccountService the nonfixedCusAccountService to set.
	 */
	public void setNonfixedCusAccountService(INonfixedCusAccountService nonfixedCusAccountService) {
		this.nonfixedCusAccountService = nonfixedCusAccountService;
	}

	
	public void setCusBargainService(ICusBargainService cusBargainService) {
		this.cusBargainService = cusBargainService;
	}

	
	
	public ICustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(ICustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	/**
	 * 根据名称查询客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-24 下午2:27:56
	 * @see com.deppon.foss.module.pickup.waybill.server.service.IQueryCustomerService#queryCustInfoByName(java.lang.String)
	 */
	@Override
	public List<CustomerDto> queryCustInfoByName(String custName) {
		return customerService.queryCustInfoByName(custName);
	}

	/**
	 * 根据客户编码查询出对应的客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-24 下午2:27:58
	 * @see com.deppon.foss.module.pickup.waybill.server.service.IQueryCustomerService#queryCustInfoByCode(java.lang.String)
	 */
	@Override
	public CustomerDto queryCustInfoByCode(String custCode) {
		if (StringUtil.isBlank(custCode)) {
		    return null;
		}else{
			return customerService.queryCustInfoByCode(custCode);	
		}		
	}
	/**
	 * <p>根据客户编码只查询客户发票标记,不查询客户其他信息</p> 
	 * @author Foss-151211-yangtaohong 
	 * @date 2016-5-12 下午9:27:54
	 * @param custCode
	 * @return
	 * @see
	 */
	@Override
	public CustomerDto queryCustInvoiceTypeByCode(String custCode) {
		if (StringUtil.isBlank(custCode)) {
		    return null;
		}else{
			return customerService.queryCustInvoiceTypeByCode(custCode);	
		}		
	}
	/**
	 * 根据dto封装的查询条件查询客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-24 下午2:28:02
	 * @see com.deppon.foss.module.pickup.waybill.server.service.IQueryCustomerService#queryCustomerInfo(com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto)
	 */
	@Override
	public List<CustomerContactDto> queryCustomerInfo(CustomerQueryConditionDto condition) {
		return customerService.queryCustomerInfo(condition);
	}

	/**
	 * 通过传入一组电话号码、部门Code返回部门的客户信息列表
	 * PS:部门为空时，查询全国的客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-25 上午10:49:54
	 */
	@Override
	public List<CustomerDto> queryCustInfoByPhone(List<String> phoneList, String deptCode) {
		return customerService.queryCustInfoByPhone(phoneList, deptCode);
	}

	/**
	 * 根据手机号码查询客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-25 上午9:57:13
	 * @see com.deppon.foss.module.pickup.waybill.server.service.IQueryCustomerService#queryCustInfoByMobile(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public CustomerDto queryCustInfoByMobile(String mobilePhone, String deptCode) {
		return customerService.queryCustInfoByMobile(mobilePhone, deptCode);
	}
	
	/**
	 * 根据手机号码集合与部门编码查询客户信息，部门编码为空时查询全国
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午12:50:25
	 */
	@Override
	public List<CustomerDto> queryCustInfoByMobileList(List<String> mobiles,String deptCode){
		if(CollectionUtils.isNotEmpty(mobiles)){
			List<CustomerDto> custList = new ArrayList<CustomerDto>();
			CustomerDto cust = null;
			for (String m : mobiles) {
				cust = customerService.queryCustInfoByMobile(m, deptCode);
				if(null != cust){
					custList.add(cust);
				}
			}
			return custList;
		}
		
		return null;
	}

	/**
	 * @author ibm-foss-sunrui
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-31 下午3:26:34
	 */
	private Date genLastThreeMonthDate() {
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		now.add(Calendar.DAY_OF_MONTH, - NUMBER_90);
		return now.getTime();
	}

	/**
	 * 通过手机、电话号码查询历史发货客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-31 下午5:30:42
	 */
	@Override
	public List<HisDeliveryCusEntity> queryHisDeliveryCusByPhone(CustomerQueryConditionDto conditionDto) {
		HisCustomerDto hisCustomer = new HisCustomerDto();
		hisCustomer.setContact(StringUtil.defaultIfNull(conditionDto.getContactName()));
		hisCustomer.setCreateTime(genLastThreeMonthDate());
		hisCustomer.setMobilePhone(StringUtil.defaultIfNull(conditionDto.getMobilePhone()));
		hisCustomer.setPhone(StringUtil.defaultIfNull(conditionDto.getContactPhone()));
		return hisDeliveryCusDao.queryByCondition(hisCustomer);
	}

	/**
	 * 通过手机、电话号码查询历史收货客户信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-31 下午5:29:19
	 */
	@Override
	public List<HisReceiveCusEntity> queryHisReceiveCusByPhone(CustomerQueryConditionDto conditionDto) {
		HisCustomerDto hisCustomer = new HisCustomerDto();
		hisCustomer.setContact(StringUtil.defaultIfNull(conditionDto.getContactName()));
		hisCustomer.setCreateTime(genLastThreeMonthDate());
		hisCustomer.setMobilePhone(StringUtil.defaultIfNull(conditionDto.getMobilePhone()));
		hisCustomer.setPhone(StringUtil.defaultIfNull(conditionDto.getContactPhone()));

		List<HisReceiveCusEntity> list = hisReceiveCusDao.queryByCondition(hisCustomer);
		return list;
	}

	/**
	 * 新增历史发货客户
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-22 下午4:45:48
	 */
	@Override
	public int addHisDeliveryCustomer(HisDeliveryCusEntity custEntity) {
		return hisDeliveryCusDao.insertSelective(custEntity);
	}

	/**
	 * 新装置历史收货客户
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-22 下午4:45:50
	 */
	@Override
	public int addHisReceiveCustomer(HisReceiveCusEntity custEntity) {
		return hisReceiveCusDao.insertSelective(custEntity);
	}
	
	/**
	 * 根据多个手机号码查询最近三个月的历史发货客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
	@Override
	public List<HisDeliveryCusEntity> queryHisDeliveryCusByMobileList(List<String> mobileList){
		return hisDeliveryCusDao.queryDeliveryCustByMobileList(mobileList,genLastThreeMonthDate());
	}

	/**
	 * 根据多个电话号码查询最近三个月的历史发货客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
	@Override
	public List<HisDeliveryCusEntity> queryHisDeliveryCusByPhoneList(List<String> phoneList){
		return hisDeliveryCusDao.queryDeliveryCustByPhoneList(phoneList,genLastThreeMonthDate());
	}

	/**
	 * 根据多个手机号码查询最近三个月的历史收货客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
	@Override
	public List<HisReceiveCusEntity> queryHisReceiveCusByMobileList(List<String> mobileList){
		return hisReceiveCusDao.queryReceiveCustByMobileList(mobileList,genLastThreeMonthDate());
	}

	/**
	 * 根据多个电话号码查询最近三个月的历史收货客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-7 下午2:19:19
	 */
	@Override
	public List<HisReceiveCusEntity> queryHisReceiveCusByPhoneList(List<String> phoneList){
		return hisReceiveCusDao.queryReceiveCustByPhoneList(phoneList,genLastThreeMonthDate());
	}
	
	/**
	 * 根据客户查询临欠、散客的银行帐号信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-21 下午1:58:55
	 */
	@Override
	public List<NonfixedCusAccountEntity> queryBankAccountByCode(String custCode){
		return nonfixedCusAccountService.queryCusAccountByCustCode(custCode);
	}
	
	/**
	 * 设置查询出的客户类型
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-26 下午7:41:38
	 */
	private List<CustomerQueryConditionDto> addQueryCustomerType(List<CustomerQueryConditionDto> custList,String type){
		//判断集合是否为空
		if(CollectionUtils.isNotEmpty(custList)){
			//遍历集合设置客户类型
			for (CustomerQueryConditionDto dto : custList) {
				dto.setCustomerType(type);
				//根据标杆编码查询部门名称
				String name = orgAdministrativeInfoService.queryDeptNameByUnifiedCode(StringUtil.defaultIfNull(dto.getDeptCode()));
				dto.setDeptName(name);
			}
		}
		return custList;
	}
	
	/**
	 * 根据dto封装的查询条件查询客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-24 下午2:28:02
	 * @see com.deppon.foss.module.pickup.waybill.server.service.IQueryCustomerService#queryCustomerInfo(com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto)
	 */
	@Override
	public List<CustomerQueryConditionDto> queryCustomerByCondition(CustomerQueryConditionDto condition) {
		/**
		 * 定义临时存储查询条件的对象 防止查询正式CRM客户未果时，修改了查询条件， 导致查询临欠散客时出现查询不正确的问题
		 */
		CustomerQueryConditionDto tempConditionDto = new CustomerQueryConditionDto();
		// 将传来的值赋值给临时存储查询条件的对象
		try {
			// 使用拷贝的方式，防止出现值无法拷贝的情况
			PropertyUtils.copyProperties(tempConditionDto, condition);
		} catch (Exception e) {
			return null;
		}
		CustomerPaginationDto tempConditionDto2 = new CustomerPaginationDto();
		// 将传来的值赋值给临时存储查询条件的对象
		try {
			// 使用拷贝的方式，防止出现值无法拷贝的情况
			PropertyUtils.copyProperties(tempConditionDto2, condition);
		} catch (Exception e) {
			return null;
		}

		// 联系人编码
		String linkmanCode = StringUtil.defaultIfNull(tempConditionDto.getLinkmanCode());
		// 身份证号码
		String idCard = StringUtil.defaultIfNull(tempConditionDto.getIdCard());

		// 存储CRM正式客户、临客、散客的集合信息
		List<CustomerQueryConditionDto> allList = new ArrayList<CustomerQueryConditionDto>();
		List<CustomerQueryConditionDto> tempList = new ArrayList<CustomerQueryConditionDto>();

		tempConditionDto2.setPageNum(1);
		tempConditionDto2.setPageSize(WaybillConstants.CUSTOMER_COUNT + NumberConstants.NUMBER_10);
		tempConditionDto2.setInvoiceDate(condition.getInvoiceDate());
		//部门编码（因为在查询正式客户时，会根据部门编码查询出标杆编码，并设置给dto进行后续查询条件进行查询，因此需要在变更前先存储起来）
		String deptCode = tempConditionDto2.getDeptCode();
		// 这种情况下只要查询第一张表
		// 存储CRM正式客户、临客、散客的集合信息
		tempConditionDto2.setInvoiceDate(condition.getInvoiceDate());
		allList = customerService.queryCustomerByConditionByPage(tempConditionDto2);
		// 设置集合中对象为CRM正式客户
		allList = addQueryCustomerType(allList, WaybillConstants.CUSTOMER_TYPE_FORMAL);
		//固定客户与散客合并为一张表，因此如果固定客户可以获取信息，则不再查询散客。
		if (CollectionUtils.isEmpty(allList)) {
			/**
			 * 由于临欠散客没有联系人编码、身份证号， 因此，若该2项不为空，则直接返回空
			 */
			// 这种情况要查询第二张表
			if ("".equals(linkmanCode) && "".equals(idCard)) {
				tempConditionDto2.setPageSize(WaybillConstants.CUSTOMER_COUNT);
				//设置最原始的部门编码
				tempConditionDto2.setDeptCode(deptCode);
				// 散客和临客
				tempList = nonfixedCustomerService.queryCustomerByConditionByPage(tempConditionDto2);
				// 设置集合中对象为CRM临欠散客
				tempList = addQueryCustomerType(tempList, WaybillConstants.CUSTOMER_TYPE_TEMP);
			}
		}

		// 判断是否为空
		if (CollectionUtils.isNotEmpty(allList)) {
			if (CollectionUtils.isNotEmpty(tempList)) {
				// 设置为临欠或散客
				allList.addAll(tempList);
			}
		} else {
			// 集合非空判断
			if (CollectionUtils.isNotEmpty(tempList)) {
				allList = tempList;
			}
		}

		return allList;

	}
	
	
	/**
	 * 根据dto封装的查询条件查询客户信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-24 下午2:28:02
	 * @see com.deppon.foss.module.pickup.waybill.server.service.IQueryCustomerService#queryCustomerInfo(com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto)
	 */
	@Override
	public CustomerPaginationDto queryCustomerByConditionByPage(CustomerPaginationDto condition) {
		/**
		 * 定义临时存储查询条件的对象
		 * 防止查询正式CRM客户未果时，修改了查询条件，
		 * 导致查询临欠散客时出现查询不正确的问题
		 */
		CustomerPaginationDto tempConditionDto = new CustomerPaginationDto();
		//将传来的值赋值给临时存储查询条件的对象
		try {
			//使用拷贝的方式，防止出现值无法拷贝的情况
			PropertyUtils.copyProperties(tempConditionDto, condition);
		} catch (Exception e) {
			return null;
		}
		
//		int total1 = customerService.countCustomerByCondition( tempConditionDto);
//		
//		int total2 = 0;
		//联系人编码
		String linkmanCode = StringUtil.defaultIfNull(tempConditionDto.getLinkmanCode());
		//身份证号码
		String idCard = StringUtil.defaultIfNull(tempConditionDto.getIdCard());
		
		//判断是否全为空
//		if("".equals(linkmanCode) && "".equals(idCard)){
//			total2 = nonfixedCustomerService.countCustomerByCondition(tempConditionDto);
//		}
		
//		int total = total1 + total2;
		
//		condition.setTotalCount(total);
//		tempConditionDto.setTotalCount(total);
		
		List<CustomerQueryConditionDto>  allList = new ArrayList<CustomerQueryConditionDto>();
		List<CustomerQueryConditionDto> tempList = new ArrayList<CustomerQueryConditionDto>();
		
		condition.setPageNum(1);
		condition.setPageSize(WaybillConstants.CUSTOMER_COUNT+NumberConstants.NUMBER_10);
		
		//第一张表和第二张表做联合 
		/**
		 * 由于临欠散客没有联系人编码、身份证号，
		 * 因此，若该2项不为空，则直接返回空
		 */
		//定义临欠散客集合对象
		//这种情况下只要查询第一张表
		//存储CRM正式客户、临客、散客的集合信息
		allList = customerService.queryCustomerByConditionByPage(condition);
		//设置集合中对象为CRM正式客户
		allList = addQueryCustomerType(allList,WaybillConstants.CUSTOMER_TYPE_FORMAL);
		
		//判断是否全为空
		if("".equals(linkmanCode) && "".equals(idCard)){
			//散客和临客
			tempConditionDto.setPageNum(1);
			tempList = nonfixedCustomerService.queryCustomerByConditionByPage(tempConditionDto);
			//设置集合中对象为CRM临欠散客
			tempList = addQueryCustomerType(tempList,WaybillConstants.CUSTOMER_TYPE_TEMP);
		}
		
		
		//判断是否为空
		if(CollectionUtils.isNotEmpty(allList)){
			if(CollectionUtils.isNotEmpty(tempList)){
				//设置为临欠或散客
				allList.addAll(tempList);
			}
		}else{
			//集合非空判断
			if(CollectionUtils.isNotEmpty(tempList)){
				allList = tempList;
			}
		}
		
		condition.setDtoList(allList);
		return condition;
	}
	

	
	/**
	 * 根据查询条件的对象集合对象客户综合信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-14 下午9:30:33
	 */
	@Override
	public List<CustomerQueryConditionDto> queryCustomerByConditionList(List<CustomerQueryConditionDto> conditions){
		//存储CRM正式客户、临客、散客的集合信息
		List<CustomerQueryConditionDto> allList = customerService.queryCustomerByConditionList(conditions);
		//设置集合中对象为CRM正式客户
		allList = addQueryCustomerType(allList,WaybillConstants.CUSTOMER_TYPE_FORMAL);
		
		//散客和临客
		List<CustomerQueryConditionDto> tempList = null;
		//遍历临客、散客查询条件
		for (CustomerQueryConditionDto dto : conditions) {
			List<CustomerQueryConditionDto> list = nonfixedCustomerService.queryCustomerByCondition(dto);
			//设置集合中对象为CRM临欠散客
			list = addQueryCustomerType(list,WaybillConstants.CUSTOMER_TYPE_TEMP);
			
			if(CollectionUtils.isNotEmpty(tempList)){
				tempList.addAll(list);
			}else{
				tempList = list;
			}
			
		}
		
		/***********CRM二期统一客户模型**********/
		//组装集合对象
		if(CollectionUtils.isEmpty(allList)){
			if(CollectionUtils.isNotEmpty(tempList)){
				allList = tempList;
			}
		}
		
		return allList;
	}
	
	/**
	 * 根据工号集合查询对应的员工对象集合
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-26 下午8:50:46
	 */
	@Override
	public List<EmployeeEntity> queryEmployeeByCodeList(List<String> codes) {
		//集合非空判断
		if(CollectionUtils.isEmpty(codes)){
			return null;
		}
		
		//定义对象集合
		List<EmployeeEntity> employeeList = new ArrayList<EmployeeEntity>();
		//循环查询员工对象
		for (String code : codes) {
			//根据员工工号查询
			EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(code);
			//非空判断
			if(null != emp){
				//加入集合
				employeeList.add(emp);
			}
		}
		return employeeList;
	}
	
	 /**
	 * 根据客户编码查询客户合同信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-21 下午3:09:27
	 */
	@Override
	public CusBargainEntity queryCusBargainByCustCode(String custCode) {
		 return cusBargainService.queryCusBargainByCustCode(custCode);
	 }
	
	/**
	 * 内存溢出，性能优化
	 * @return
	 * add WangQianJin by 2010-10-17
	 */
	@Override
	public List<EmployeeEntity> queryEmployeeNameAndCodeByCodeList(List<String> codes){
		return employeeService.queryEmployeeNameAndCodeByEmpCode(codes);
	}
	
	/**
	 * 根据编码查询员工信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-11-1 下午8:51:31
	 */
	@Override
	public EmployeeEntity queryEmployeeByEmpCode(String code){
		return employeeService.queryEmployeeByEmpCode(code);
	}
	
	/**
	 * 查询简单的员工信息
	 * @autor Foss-278328-hujinyang
	 * @time 2016-4-22
	 * @param code
	 * @return
	 */
	@Override
	public  EmployeeEntity querySimpleEmployeeByEmpCode(String code){
		return employeeService.querySimpleEmployeeByEmpCode(code);
	}
	/**
	 * 根据客户编码,客户联系方式查询客户当前可以使用的发票标记信息
	 * @param condition
	 * @param date
	 * @return
	 */
	@Override
	public CusContractTaxEntity queryCurrentUseInvoiceMark(CustomerQueryConditionDto condition, Date date){
		return cusContractTaxService.queryCurrentUseInvoiceMark(condition, date);
	}
	
	/**
	 * 根据查询条件获取客户信息是否存在
	 * @创建时间 2014-5-27 下午6:57:11   
	 * @创建人： WangQianJin
	 */
	public int queryCustomerExistByCondition(CustomerQueryConditionDto condition){
		return nonfixedCustomerService.queryCustomerExistByCondition(condition);
	}
	
	/**
	 * 根据crmCusId获取客户信息
	 * @param crmCusId
	 * @return
	 */
	public CustomerEntity queryCustomerByCrmCusId(BigDecimal crmCusId,String fossId){
		List<CustomerEntity> list=customerService.queryCusInfosByCrmCusId(crmCusId, fossId);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;	
		}		 
	}
	/***
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
	public String queryComtomerInvoiceType(Date date ,BigDecimal crmId){
		if(date == null){
			return customerDao.queryComtomerInvoiceType(new Date(),crmId);
		}else{
			return customerDao.queryComtomerInvoiceType(date,crmId);
		}
	}

	@Override
	public boolean cheCustomerPhoneExistCustomerMobilePhone(CustomerQueryConditionDto customerQueryConditionDto) {
		List<CustomerQueryConditionDto> conditions = new ArrayList<CustomerQueryConditionDto>();
		conditions.add(customerQueryConditionDto);
		//存储CRM正式客户、临客、散客的集合信息
		List<CustomerQueryConditionDto> allList = customerService.queryCustomerByConditionList(conditions);
		if(CollectionUtils.isNotEmpty(allList)){
			return true;
		}
		
		//遍历临客、散客查询条件
		for (CustomerQueryConditionDto dto : conditions) {
			List<CustomerQueryConditionDto> list = nonfixedCustomerService.queryCustomerByCondition(dto);
			if(CollectionUtils.isNotEmpty(list)){
				return true;
			}
		}
		return false;
	}
	@Override
	public  CustomerEntity queryCustomerInfoByCusCode(String code){
		return customerService.queryCustomerInfoByCusCode(code);
	}

	@Override
	public int queryOneticketornotBycode(String code) {
		int isone = 0 ;
		OneticketornotEntity oneticketornotEntity  = customerService.queryOneticketornotBycode(code);
		if(null!=oneticketornotEntity
				&&null!=oneticketornotEntity.getIsoneticketornot()
				&&"1".equals(oneticketornotEntity.getIsoneticketornot())){
			isone = 1;
		}
		return isone;
	}
}