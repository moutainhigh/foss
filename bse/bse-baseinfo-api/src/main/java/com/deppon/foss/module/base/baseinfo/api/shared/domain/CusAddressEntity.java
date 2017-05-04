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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/CusAddressEntity.java
 * 
 * FILE NAME        	: CusAddressEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 客户接送货地址实体类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-16 下午4:35:41 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-16 下午4:35:41
 * @since
 * @version
 */
public class CusAddressEntity extends BaseEntity{
   
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -8665172476807284344L;
    
    /**
     * 所属客户ＩＤ.
     */
    private BigDecimal customerCode;
    
    /**
     * 详细地址.
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
     * 状态.
     */
    private String status;
    
    /**
     * 是否启用.
     */
    private String active;
    
    /**
     * 虚拟编码.
     */
    private String virtualCode;
    
    /**
     * 在CRM中fid.
     */
    private BigDecimal crmId;
    
    //078816_wp_2014-03-20
    /**
     * 所属客户的fossId
     */
    private String ownCustId;
    
    /**
     * 客户性质
     */
    private String customserNature;
    
    /**
     * 客户接送货地址备注信息
     */
    private String addressRemark;
    
    /**
     * 获取 所属客户ＩＤ.
     *
     * @return  the customerCode
     */
    public BigDecimal getCustomerCode() {
        return customerCode;
    }
    
    /**
     * 设置 所属客户ＩＤ.
     *
     * @param customerCode the customerCode to set
     */
    public void setCustomerCode(BigDecimal customerCode) {
        this.customerCode = customerCode;
    }
    
    /**
     * 获取 详细地址.
     *
     * @return  the address
     */
    public String getAddress() {
        return address;
    }
    
    /**
     * 设置 详细地址.
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
     * 获取 状态.
     *
     * @return  the status
     */
    public String getStatus() {
        return status;
    }
    
    /**
     * 设置 状态.
     *
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * 获取 是否启用.
     *
     * @return  the active
     */
    public String getActive() {
        return active;
    }
    
    /**
     * 设置 是否启用.
     *
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }
    
    /**
     * 获取 虚拟编码.
     *
     * @return  the virtualCode
     */
    public String getVirtualCode() {
        return virtualCode;
    }
    
    /**
     * 设置 虚拟编码.
     *
     * @param virtualCode the virtualCode to set
     */
    public void setVirtualCode(String virtualCode) {
        this.virtualCode = virtualCode;
    }
    
    /**
     * 获取 在CRM中fid.
     *
     * @return  the crmId
     */
    public BigDecimal getCrmId() {
        return crmId;
    }
    
    /**
     * 设置 在CRM中fid.
     *
     * @param crmId the crmId to set
     */
    public void setCrmId(BigDecimal crmId) {
        this.crmId = crmId;
    }

	public String getCustomserNature() {
		return customserNature;
	}

	public void setCustomserNature(String customserNature) {
		this.customserNature = customserNature;
	}

	/**
	 * @return the ownCustId
	 */
	public String getOwnCustId() {
		return ownCustId;
	}

	/**
	 * @param ownCustId the ownCustId to set
	 */
	public void setOwnCustId(String ownCustId) {
		this.ownCustId = ownCustId;
	}

	public String getAddressRemark() {
		return addressRemark;
	}

	public void setAddressRemark(String addressRemark) {
		this.addressRemark = addressRemark;
	} 
    
    

}
