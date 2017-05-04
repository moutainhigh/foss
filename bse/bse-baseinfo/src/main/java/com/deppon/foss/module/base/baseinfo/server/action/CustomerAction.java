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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/CustomerAction.java
 * 
 * FILE NAME        	: CustomerAction.java
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
package com.deppon.foss.module.base.baseinfo.server.action;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CustomerVo;
import com.deppon.foss.util.define.FossConstants;

/**
 * 客户信息action
 * 
 * @author 073586-FOSS-LIXUEXING
 * @date 2012-12-03 08:10:10
 * @since
 * @version 0.01
 */
public class CustomerAction extends AbstractAction {

    /**
     * 下面是声明的属性
     */
    private static final long serialVersionUID = -3714478860779651461L;

    // service接口
    private ICustomerService customerService;

    // VO信息实体
    private CustomerVo customerVo = new CustomerVo();

    /**
     * <p>
     * 查询客户信息
     * </p>
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-03 09:10:10
     * @return String
     */
    public String queryCustomers() {
	try {
	    CustomerEntity entity = customerVo.getCustomerEntity();
	    // 查询有效的数据
	    entity.setActive(FossConstants.ACTIVE);
	    customerVo.setCustomerList(customerService.queryCustomers(entity,
		    limit, start));
	    totalCount = customerService.queryRecordCount(entity);
	    
	    return returnSuccess();
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }

    /**
     * 通过传入一个客户编码查询出对应的客户信息
     * <p>
     * 包括：客户基本信息、联系人信息集合、客户银行账户信息集合
     * </p>
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-03 09:10:10
     * @param custCode
     *            客户编码
     * @return 存在：CustomerDto 不存在：null
     * @see
     */
    public String queryCustInfoByCode() {
	customerVo.setCustomerDto(customerService
		.queryCustInfoByCode(customerVo.getCustomerEntity()
			.getCusCode()));
	return returnSuccess();
    }

    /*
     * =================================================================
     * 下面是get,set方法：
     */
    public CustomerVo getCustomerVo() {
	return customerVo;
    }

    public void setCustomerVo(CustomerVo customerVo) {
	this.customerVo = customerVo;
    }

    public void setCustomerService(ICustomerService customerService) {
	this.customerService = customerService;
    }

}
