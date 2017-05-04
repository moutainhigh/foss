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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/SalesDeptAccountantEntity.java
 * 
 * FILE NAME        	: SalesDeptAccountantEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 记录会计所负责的营业部
 * @author 027443-foss-zhaopeng
 * @date 2012-10-30 下午3:59:15
 */
public class SalesDeptAccountantEntity extends BaseEntity {
	
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -3966031350437995984L;
    
    /**
     * 会计姓名
     */
    private String accountantName;
    
    /**
     * 会计工号
     */
    private String accountantNO;
    
    /**
     * 会计所属部门
     */
    private String accountantDept;
    
    /**
     * 所辖营业部
     */
    private String salesDepartment;
    
    /**
     * 虚拟code
     */
    private String virtualCode;
    
    /**
     * 是否启用
     */
    private String active;

	/**
	 * @return accountantName
	 */
	public String getAccountantName() {
		return accountantName;
	}

	/**
	 * @param  accountantName  
	 */
	public void setAccountantName(String accountantName) {
		this.accountantName = accountantName;
	}

	/**
	 * @return accountantNO
	 */
	public String getAccountantNO() {
		return accountantNO;
	}

	/**
	 * @param  accountantNO  
	 */
	public void setAccountantNO(String accountantNO) {
		this.accountantNO = accountantNO;
	}

	/**
	 * @return accountantDept
	 */
	public String getAccountantDept() {
		return accountantDept;
	}

	/**
	 * @param  accountantDept  
	 */
	public void setAccountantDept(String accountantDept) {
		this.accountantDept = accountantDept;
	}

	/**
	 * @return salesDepartment
	 */
	public String getSalesDepartment() {
		return salesDepartment;
	}

	/**
	 * @param  salesDepartment  
	 */
	public void setSalesDepartment(String salesDepartment) {
		this.salesDepartment = salesDepartment;
	}

	/**
	 * @return virtualCode
	 */
	public String getVirtualCode() {
		return virtualCode;
	}

	/**
	 * @param  virtualCode  
	 */
	public void setVirtualCode(String virtualCode) {
		this.virtualCode = virtualCode;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param  active  
	 */
	public void setActive(String active) {
		this.active = active;
	}
}