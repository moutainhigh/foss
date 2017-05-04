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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/BusinessPartnerEntity.java
 * 
 * FILE NAME        	: BusinessPartnerEntity.java
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
 * 用来存储交互“空运代理公司、偏线代理公司”的数据库对应实体：SUC-786、SUC-754
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-11 下午5:33:51</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-11 下午5:33:51
 * @since
 * @version
 */
public class BusinessPartnerEntity extends BaseEntity {
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -5465914922540102105L;

    /**
     * 代理公司编码.
     */
    private String agentCompanyCode;

    /**
     * 管理部门.
     */
    private String management;
    
    /**
     * 管理部门名称（扩展）.
     */
    private String managementName;

    /**
     * 代理公司名称.
     */
    private String agentCompanyName;

    /**
     * 代理简称.
     */
    private String simplename;

    /**
     * 省份.
     */
    private String provCode;
    
    /**
     * 省份名称（扩展）.
     */
    private String provName;

    /**
     * 城市.
     */
    private String cityCode;
    
    /**
     * 城市名称（扩展）.
     */
    private String cityName;

    /**
     * 联系地址.
     */
    private String contactAddress;

    /**
     * 联系电话.
     */
    private String contactPhone;

    /**
     * 联系人.
     */
    private String contact;

    /**
     * 联系人电话.
     */
    private String mobilePhone;

    /**
     * 代理公司类别.
     */
    private String agentCompanySort;

    /**
     * 备注.
     */
    private String notes;

    /**
     * 是否启用.
     */
    private String active;
    
    /**
     * 虚拟编码.
     */
    private String virtualCode;
    
    /**
     * 版本号.
     */
    private Long versionNo;
    /**
	 * 查询全部（包含无效的）
	 */   
	private String all;
    /**
     * 服务编码
     */
    private String interFaceServiceCode;
    /**
	 * 核销方式
	 */   
	private String verificationMethods;
	
	
	
	
	
	public String getAll() {
		return all;
	}
	public void setAll(String all) {
		this.all = all;
	}
    /**
     * 获取 版本号.
     *
     * @return the 版本号
     */
    public Long getVersionNo() {
        return versionNo;
    }

    
    /**
     * 设置 版本号.
     *
     * @param versionNo the new 版本号
     */
    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }

    /**
     * 获取 虚拟编码.
     *
     * @return the 虚拟编码
     */
    public String getVirtualCode() {
        return virtualCode;
    }

    /**
     * 设置 虚拟编码.
     *
     * @param virtualCode the new 虚拟编码
     */
    public void setVirtualCode(String virtualCode) {
        this.virtualCode = virtualCode;
    }

    /**
     * 获取 代理公司编码.
     *
     * @return  the agentCompanyCode
     */
    public String getAgentCompanyCode() {
        return agentCompanyCode;
    }

    /**
     * 设置 代理公司编码.
     *
     * @param agentCompanyCode the agentCompanyCode to set
     */
    public void setAgentCompanyCode(String agentCompanyCode) {
        this.agentCompanyCode = agentCompanyCode;
    }
    
    /**
     * 获取 管理部门.
     *
     * @return  the management
     */
    public String getManagement() {
        return management;
    }
    
    /**
     * 设置 管理部门.
     *
     * @param management the management to set
     */
    public void setManagement(String management) {
        this.management = management;
    }

    /**
     * 获取 代理公司名称.
     *
     * @return  the agentCompanyName
     */
    public String getAgentCompanyName() {
        return agentCompanyName;
    }
    
    /**
     * 设置 代理公司名称.
     *
     * @param agentCompanyName the agentCompanyName to set
     */
    public void setAgentCompanyName(String agentCompanyName) {
        this.agentCompanyName = agentCompanyName;
    }

    /**
     * 获取 代理简称.
     *
     * @return  the simplename
     */
    public String getSimplename() {
        return simplename;
    }

    /**
     * 设置 代理简称.
     *
     * @param simplename the simplename to set
     */
    public void setSimplename(String simplename) {
        this.simplename = simplename;
    }

    /**
     * 获取 省份.
     *
     * @return  the provCode
     */
    public String getProvCode() {
        return provCode;
    }
    
    /**
     * 设置 省份.
     *
     * @param provCode the provCode to set
     */
    public void setProvCode(String provCode) {
        this.provCode = provCode;
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
     * 获取 联系地址.
     *
     * @return  the contactAddress
     */
    public String getContactAddress() {
        return contactAddress;
    }
    
    /**
     * 设置 联系地址.
     *
     * @param contactAddress the contactAddress to set
     */
    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }
    
    /**
     * 获取 联系电话.
     *
     * @return  the contactPhone
     */
    public String getContactPhone() {
        return contactPhone;
    }
    
    /**
     * 设置 联系电话.
     *
     * @param contactPhone the contactPhone to set
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
    
    /**
     * 获取 联系人.
     *
     * @return  the contact
     */
    public String getContact() {
        return contact;
    }
    
    /**
     * 设置 联系人.
     *
     * @param contact the contact to set
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * 获取 联系人电话.
     *
     * @return  the mobilePhone
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * 设置 联系人电话.
     *
     * @param mobilePhone the mobilePhone to set
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    
    /**
     * 获取 代理公司类别.
     *
     * @return  the agentCompanySort
     */
    public String getAgentCompanySort() {
        return agentCompanySort;
    }
    
    /**
     * 设置 代理公司类别.
     *
     * @param agentCompanySort the agentCompanySort to set
     */
    public void setAgentCompanySort(String agentCompanySort) {
        this.agentCompanySort = agentCompanySort;
    }

    /**
     * 获取 备注.
     *
     * @return  the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * 设置 备注.
     *
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
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
     * 获取 管理部门名称（扩展）.
     *
     * @return the 管理部门名称（扩展）
     */
    public String getManagementName() {
        return managementName;
    }

    
    /**
     * 设置 管理部门名称（扩展）.
     *
     * @param managementName the new 管理部门名称（扩展）
     */
    public void setManagementName(String managementName) {
        this.managementName = managementName;
    }

    
    /**
     * 获取 省份名称（扩展）.
     *
     * @return the 省份名称（扩展）
     */
    public String getProvName() {
        return provName;
    }

    
    /**
     * 设置 省份名称（扩展）.
     *
     * @param provName the new 省份名称（扩展）
     */
    public void setProvName(String provName) {
        this.provName = provName;
    }

    
    /**
     * 获取 城市名称（扩展）.
     *
     * @return the 城市名称（扩展）
     */
    public String getCityName() {
        return cityName;
    }

    
    /**
     * 设置 城市名称（扩展）.
     *
     * @param cityName the new 城市名称（扩展）
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    
	public String getInterFaceServiceCode() {
		return interFaceServiceCode;
	}
	public void setInterFaceServiceCode(String interFaceServiceCode) {
		this.interFaceServiceCode = interFaceServiceCode;
	}
	public String getVerificationMethods() {
		return verificationMethods;
	}
	public void setVerificationMethods(String verificationMethods) {
		this.verificationMethods = verificationMethods;
	}
    
}
