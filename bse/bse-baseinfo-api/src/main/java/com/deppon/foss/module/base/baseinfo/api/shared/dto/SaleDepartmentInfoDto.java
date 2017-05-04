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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/SaleDepartmentInfoDto.java
 * 
 * FILE NAME        	: SaleDepartmentInfoDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * @Description: 营业部信息
 * SaleDepartmentInfoDto.java Create on 2013-1-22 下午8:03:47
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class SaleDepartmentInfoDto  extends BaseEntity {
    private static final long serialVersionUID = -3967231352083822843L;

    /**
    *部门编码
    */	
    private String code;
    /**
    *部门名称
    */	
    private String name;
	/**
	 * 可自提
	 */
	private String pickupSelf;
	/**
	 * 可派送
	 */
	private String delivery;
	/**
	 * 营业部类型
	 */
	private String orgType;
	/**
	 * 简称
	 */
	private String simpleName;
	/**
	 * 营业部地址
	 */
	private String address;
	/**
	 * 营业部电话
	 */
	private String telephone;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPickupSelf() {
		return pickupSelf;
	}
	public void setPickupSelf(String pickupSelf) {
		this.pickupSelf = pickupSelf;
	}
	public String getDelivery() {
		return delivery;
	}
	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	public String getSimpleName() {
		return simpleName;
	}
	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
}
