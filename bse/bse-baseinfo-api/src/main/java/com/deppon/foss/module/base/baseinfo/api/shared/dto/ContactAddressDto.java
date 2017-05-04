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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/ContactAddressDto.java
 * 
 * FILE NAME        	: ContactAddressDto.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactEntity;

/**
 * 客户联系人-偏好地址-客户接送货地址DTO
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-12-4 下午8:41:07 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-12-4 下午8:41:07
 * @since
 * @version
 */
public class ContactAddressDto extends ContactEntity {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 292146883474801343L;
    
    /**
     * 接送货地址.
     */
    private String contactAddress;
    
    /**
     * 详细地址（接送街道）.
     */
    private String address;
    
    /**
     * 邮编.
     */
    private String zipCode;
    
    /**
     * 省份.
     */
    private String provinceName;
    
    /**
     * 城市.
     */
    private String cityCode;
    
    /**
     * 区县.
     */
    private String countyCode;
    
    /**
     * 地址类型.
     */
    private String addressType;
    
    /**
     * 与客户信息是多对一关系.
     */
    private CustomerDto customerDto;
    
    /**
     * 获取 接送货地址.
     *
     * @return  the contactAddress
     */
    public String getContactAddress() {
        return contactAddress;
    }
    
    /**
     * 设置 接送货地址.
     *
     * @param contactAddress the contactAddress to set
     */
    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }
    
    /**
     * 获取 详细地址（接送街道）.
     *
     * @return  the address
     */
    public String getAddress() {
        return address;
    }
    
    /**
     * 设置 详细地址（接送街道）.
     *
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
    /**
     * 获取 邮编.
     *
     * @return  the zipCode
     */
    public String getZipCode() {
        return zipCode;
    }
    
    /**
     * 设置 邮编.
     *
     * @param zipCode the zipCode to set
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    /**
     * 获取 省份.
     *
     * @return  the provinceName
     */
    public String getProvinceName() {
        return provinceName;
    }
    
    /**
     * 设置 省份.
     *
     * @param provinceName the provinceName to set
     */
    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
    
    /**
     * 获取 城市.
     *
     * @return  the cityCode
     */
    public String getCityCode() {
        return cityCode;
    }
    
    /**
     * 设置 城市.
     *
     * @param cityCode the cityCode to set
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    
    /**
     * 获取 区县.
     *
     * @return  the countyCode
     */
    public String getCountyCode() {
        return countyCode;
    }
    
    /**
     * 设置 区县.
     *
     * @param countyCode the countyCode to set
     */
    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }
    
    /**
     * 获取 地址类型.
     *
     * @return  the addressType
     */
    public String getAddressType() {
        return addressType;
    }
    
    /**
     * 设置 地址类型.
     *
     * @param addressType the addressType to set
     */
    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }
    
    /**
     * 获取 与客户信息是多对一关系.
     *
     * @return  the customerDto
     */
    public CustomerDto getCustomerDto() {
        return customerDto;
    }
    
    /**
     * 设置 与客户信息是多对一关系.
     *
     * @param customerDto the customerDto to set
     */
    public void setCustomerDto(CustomerDto customerDto) {
        this.customerDto = customerDto;
    }
    

}
