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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/CustomerDto.java
 * 
 * FILE NAME        	: CustomerDto.java
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
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;


/**
 * 用来存储客户相关信息的DTO
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-20 上午11:02:58 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-20 上午11:02:58
 * @since
 * @version
 */
public class CustomerDto extends CustomerEntity {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 4191999282548463574L;
    
    /**
     * 部门名称.
     */
    private String deptname;
    
    /**
     * 联系人集合.
     */
    List<ContactEntity> contactList ;
    
    /**
     * 客户开户银行账户集合.
     */
    List<CusAccountEntity> bankAccountList;
    
    /**
     * 客户合同信息集合.
     */
    List<CusBargainEntity> bargainList;
    
    /**
     * 客户合同-优惠信息集合（封装）.
     */
    List<BargainPreferDto> bargainPreferList;
    
    /**
     * 客户联系人-偏好地址-客户接送货地址.
     */
    List<ContactAddressDto> contactAddressList;
    
    private String CustomserNature;
    
    private String isLargeCustomers;
    /**
     * 获取 部门名称.
     *
     * @return  the deptname
     */
    public String getDeptname() {
        return deptname;
    }
    
    /**
     * 设置 部门名称.
     *
     * @param deptname the deptname to set
     */
    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }
    
    /**
     * 获取 联系人集合.
     *
     * @return  the contactList
     */
    public List<ContactEntity> getContactList() {
        return contactList;
    }
    
    /**
     * 设置 联系人集合.
     *
     * @param contactList the contactList to set
     */
    public void setContactList(List<ContactEntity> contactList) {
        this.contactList = contactList;
    }
    
    /**
     * 获取 客户开户银行账户集合.
     *
     * @return  the bankAccountList
     */
    public List<CusAccountEntity> getBankAccountList() {
        return bankAccountList;
    }
    
    /**
     * 设置 客户开户银行账户集合.
     *
     * @param bankAccountList the bankAccountList to set
     */
    public void setBankAccountList(List<CusAccountEntity> bankAccountList) {
        this.bankAccountList = bankAccountList;
    }
    
    /**
     * 获取 客户合同信息集合.
     *
     * @return  the bargainList
     */
    public List<CusBargainEntity> getBargainList() {
        return bargainList;
    }
    
    /**
     * 设置 客户合同信息集合.
     *
     * @param bargainList the bargainList to set
     */
    public void setBargainList(List<CusBargainEntity> bargainList) {
        this.bargainList = bargainList;
    }
    
    /**
     * 获取 客户合同-优惠信息集合（封装）.
     *
     * @return  the bargainPreferList
     */
    public List<BargainPreferDto> getBargainPreferList() {
        return bargainPreferList;
    }
    
    /**
     * 设置 客户合同-优惠信息集合（封装）.
     *
     * @param bargainPreferList the bargainPreferList to set
     */
    public void setBargainPreferList(List<BargainPreferDto> bargainPreferList) {
        this.bargainPreferList = bargainPreferList;
    }
    
    /**
     * 获取 客户联系人-偏好地址-客户接送货地址.
     *
     * @return  the contactAddressList
     */
    public List<ContactAddressDto> getContactAddressList() {
        return contactAddressList;
    }
    
    /**
     * 设置 客户联系人-偏好地址-客户接送货地址.
     *
     * @param contactAddressList the contactAddressList to set
     */
    public void setContactAddressList(List<ContactAddressDto> contactAddressList) {
        this.contactAddressList = contactAddressList;
    }

	public String getCustomserNature() {
		return CustomserNature;
	}

	public void setCustomserNature(String customserNature) {
		CustomserNature = customserNature;
	}

	public String getIsLargeCustomers() {
		return isLargeCustomers;
	}

	public void setIsLargeCustomers(String isLargeCustomers) {
		this.isLargeCustomers = isLargeCustomers;
	}
    

}
