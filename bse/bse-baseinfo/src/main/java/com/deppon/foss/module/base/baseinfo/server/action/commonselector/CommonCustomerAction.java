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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonCustomerAction.java
 * 
 * FILE NAME        	: CommonCustomerAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.action
 * FILE    NAME: CommonCustomerAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonCustomerService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CusAccountDto;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CustomerVo;
 
/**
 * 公共选择器--客户查询Action.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-4 下午4:26:21
 */
public class CommonCustomerAction extends AbstractAction implements IQueryAction {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1505536975718526244L;
	
	/**
	 * The common customer service.
	 */
	private ICommonCustomerService commonCustomerService;
	
	/**
	 * 客户信息Vo
	 */
	private CustomerVo customerVo=new CustomerVo();
	
	/**
	 * 客户查询方法.
	 *
	 * @return the string
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-4 下午4:26:56
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@JSON
	public String queryCustomerInfo() {
		long totalCount=commonCustomerService.queryRecordCount(customerVo.getCustomerEntity());
		if(totalCount > 0){
			List<CustomerEntity> customerList=commonCustomerService.queryCustomers(customerVo.getCustomerEntity(),  limit,  start);
			customerVo.setCustomerList(customerList);
		} 
		setTotalCount(totalCount);
		return returnSuccess();
	}
	
	/**
	 * 客户和银行账户查询方法.
	 *
	 * @return the string
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-4 下午4:26:56
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@JSON
	public String queryCusAccountInfo() {
		List<CusAccountDto> customerList = commonCustomerService.queryCustAccountsInfo(customerVo.getCustomerCondDto());
		customerVo.setCusAccountList(customerList);
		return returnSuccess();
	}   
	
	
	/**
	 * <p>客户表与客户账户表关联后的信息查询，用于开户人姓名选择器</p> 
	 * @author 232607 
	 * @date 2015-11-4 上午10:56:51
	 * @return
	 * @see
	 */
	@JSON
	public String queryCusAccountJoinCus() {
		List<CusAccountDto> list = commonCustomerService.queryCusAccountJoinCus(customerVo.getCusAccountDto(),  limit,  start);
		customerVo.setCusAccountList(list);
		long totalCount=commonCustomerService.queryCusAccountJoinCusCount(customerVo.getCusAccountDto());
		this.setTotalCount(totalCount);
		return returnSuccess();
	}
	
	/**
	 * setter.
	 *
	 * @param commonCustomerService the new common customer service
	 */
	public void setCommonCustomerService(
			ICommonCustomerService commonCustomerService) {
		this.commonCustomerService = commonCustomerService;
	}

	
	public CustomerVo getCustomerVo() {
		return customerVo;
	}

	
	public void setCustomerVo(CustomerVo customerVo) {
		this.customerVo = customerVo;
	}

	@Override
	public String query() {
		return null;
	}

 

}
