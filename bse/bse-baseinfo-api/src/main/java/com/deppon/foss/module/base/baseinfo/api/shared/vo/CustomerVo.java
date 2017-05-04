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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/CustomerVo.java
 * 
 * FILE NAME        	: CustomerVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CusAccountDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;

public class CustomerVo implements Serializable {
 
	private static final long serialVersionUID = -5458363938947393945L;
	
	// 客户信息 集合
	private List<CustomerEntity> customerList;
	//客户信息实体
	private CustomerEntity customerEntity = new CustomerEntity();
	//DTO信息实体
	private CustomerDto customerDto;
	//公共查询选择器 客户与账户信息实体
	private CusAccountDto cusAccountDto;
	//公共查询选择器 客户与账户信息实体列表
	private List<CusAccountDto> cusAccountList;
	//客户与账户信息
	private CustomerQueryConditionDto customerCondDto;
	// 返回前台的INT类型属性
	private int returnInt;
	/**
	 * 下面是get,set方法
	 * 
	 * @author 073586-FOSS-LIXUEXING
	 * @date 2012-11-27 下午19:40:42
	 */

	public List<CustomerEntity> getCustomerList() {
		return customerList;
	}
	
	public List<CusAccountDto> getCusAccountList() {
		return cusAccountList;
	}
	
	public void setCusAccountList(List<CusAccountDto> cusAccountList) {
		this.cusAccountList = cusAccountList;
	}
	public void setCustomerList(List<CustomerEntity> customerList) {
		this.customerList = customerList;
	}
	public CustomerEntity getCustomerEntity() {
		return customerEntity;
	}
	public void setCustomerEntity(CustomerEntity customerEntity) {
		this.customerEntity = customerEntity;
	}
	public int getReturnInt() {
		return returnInt;
	}
	public void setReturnInt(int returnInt) {
		this.returnInt = returnInt;
	}
	public CustomerDto getCustomerDto() {
		return customerDto;
	}
	public void setCustomerDto(CustomerDto customerDto) {
		this.customerDto = customerDto;
	}
	
	public CustomerQueryConditionDto getCustomerCondDto() {
		return customerCondDto;
	}
	
	public void setCustomerCondDto(CustomerQueryConditionDto customerCondDto) {
		this.customerCondDto = customerCondDto;
	}
	
	public CusAccountDto getCusAccountDto() {
		return cusAccountDto;
	}
	
	public void setCusAccountDto(CusAccountDto cusAccountDto) {
		this.cusAccountDto = cusAccountDto;
	}
	
}
