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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/AdministrativeRegionsEntity.java
 * 
 * FILE NAME        	: AdministrativeRegionsEntity.java
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
 * 行政区域 entity.
 *
 * @author 087584-foss-lijun
 * @date 2012-10-20 下午2:35:52
 */
public class AdministrativeRegionsEntity  extends BaseEntity {
    
    /**
     * 
     */
    private static final long serialVersionUID = -3967231351672695546L;

    /**
     * 数据版本号.
     */	
    private Long versionNo;

    /**
     * 行政区域编码.
     */	
    private String code;

    /**
     * 区域全称.
     */	
    private String name;

    /**
     * 简称.
     */	
    private String simpleName;

    /**
     * 可用名称.
     */	
    private String availableName;

    /**
     * 上级行政区域编码.
     */	
    private String parentDistrictCode;

    /**
     * 上级行政区域名称.
     */	
    private String parentDistrictName;

    /**
     * 虚拟行政区域.
     */	
    private String virtualDistrictId;

    /**
     * 行政区域级别.
     */	
    private String degree;
    
    /**
     * 行政区域级别名称“扩展字段”.
     */	
     private String degreeName;

    /**
     * 是否启用.
     */	
    private String active;
    
    /**
     * 是否大客户全境
     */
    private String isBigCustomerOwnRegion;
    /**
     * 后缀.
     */	
    private String regionsuffix;

    /**
     * 拼音.
     */	
    private String pinYin;
    
    /**
     * 拼音简码.
     */	
    private String pinYinAbbr;
    
    /**
     * 是否热门城市.
     */
    private String isHotCity;
    
    
    /**
     * 获取 是否热门城市.
     *
     * @return  the isHotCity
     */
    public String getIsHotCity() {
        return isHotCity;
    }

    
    /**
     * 设置 是否热门城市.
     *
     * @param isHotCity the isHotCity to set
     */
    public void setIsHotCity(String isHotCity) {
        this.isHotCity = isHotCity;
    }

    /**
     * 获取 数据版本号.
     *
     * @return the 数据版本号
     */
    public Long getVersionNo() {
        return versionNo;
    }
    
    /**
     * 设置 数据版本号.
     *
     * @param versionNo the new 数据版本号
     */
    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }
    
    /**
     * 获取 行政区域编码.
     *
     * @return the 行政区域编码
     */
    public String getCode() {
		return code;
    }
    
    /**
     * 设置 行政区域编码.
     *
     * @param code the new 行政区域编码
     */
    public void setCode(String code) {
		this.code = code;
    }

    /**
     * 获取 区域全称.
     *
     * @return the 区域全称
     */
    public String getName() {
		return name;
    }
    
    /**
     * 设置 区域全称.
     *
     * @param name the new 区域全称
     */
    public void setName(String name) {
		this.name = name;
    }

    /**
     * 获取 简称.
     *
     * @return the 简称
     */
    public String getSimpleName() {
		return simpleName;
    }
    
    /**
     * 设置 简称.
     *
     * @param simpleName the new 简称
     */
    public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
    }

    /**
     * 获取 可用名称.
     *
     * @return the 可用名称
     */
    public String getAvailableName() {
		return availableName;
    }
    
    /**
     * 设置 可用名称.
     *
     * @param availableName the new 可用名称
     */
    public void setAvailableName(String availableName) {
		this.availableName = availableName;
    }

    /**
     * 获取 上级行政区域编码.
     *
     * @return the 上级行政区域编码
     */
    public String getParentDistrictCode() {
		return parentDistrictCode;
    }
    
    /**
     * 设置 上级行政区域编码.
     *
     * @param parentDistrictCode the new 上级行政区域编码
     */
    public void setParentDistrictCode(String parentDistrictCode) {
		this.parentDistrictCode = parentDistrictCode;
    }

    /**
     * 获取 上级行政区域名称.
     *
     * @return the 上级行政区域名称
     */
    public String getParentDistrictName() {
		return parentDistrictName;
    }
    
    /**
     * 设置 上级行政区域名称.
     *
     * @param parentDistrictName the new 上级行政区域名称
     */
    public void setParentDistrictName(String parentDistrictName) {
		this.parentDistrictName = parentDistrictName;
    }

    /**
     * 获取 虚拟行政区域.
     *
     * @return the 虚拟行政区域
     */
    public String getVirtualDistrictId() {
		return virtualDistrictId;
    }
    
    /**
     * 设置 虚拟行政区域.
     *
     * @param virtualDistrictId the new 虚拟行政区域
     */
    public void setVirtualDistrictId(String virtualDistrictId) {
		this.virtualDistrictId = virtualDistrictId;
    }

    /**
     * 获取 行政区域级别.
     *
     * @return the 行政区域级别
     */
    public String getDegree() {
		return degree;
    }
    
    /**
     * 设置 行政区域级别.
     *
     * @param degree the new 行政区域级别
     */
    public void setDegree(String degree) {
		this.degree = degree;
    }

    /**
     * 获取 是否启用.
     *
     * @return the 是否启用
     */
    public String getActive() {
		return active;
    }
    
    /**
     * 设置 是否启用.
     *
     * @param active the new 是否启用
     */
    public void setActive(String active) {
		this.active = active;
    }

    /**
     * 获取 后缀.
     *
     * @return the 后缀
     */
    public String getRegionsuffix() {
		return regionsuffix;
    }
    
    /**
     * 设置 后缀.
     *
     * @param regionsuffix the new 后缀
     */
    public void setRegionsuffix(String regionsuffix) {
		this.regionsuffix = regionsuffix;
    }

	
	/**
	 * 获取 行政区域级别名称“扩展字段”.
	 *
	 * @return the 行政区域级别名称“扩展字段”
	 */
	public String getDegreeName() {
		return degreeName;
	}

	
	/**
	 * 设置 行政区域级别名称“扩展字段”.
	 *
	 * @param degreeName the new 行政区域级别名称“扩展字段”
	 */
	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}

	/**
	 * 获取 拼音.
	 *
	 * @return pinYin
	 */
	public String getPinYin() {
		return pinYin;
	}

	/**
	 * 设置 拼音.
	 *
	 * @param pinYin the new 拼音
	 */
	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
	}

	/**
	 * 获取 拼音简码.
	 *
	 * @return pinYinAbbr
	 */
	public String getPinYinAbbr() {
		return pinYinAbbr;
	}

	/**
	 * 设置 拼音简码.
	 *
	 * @param pinYinAbbr the new 拼音简码
	 */
	public void setPinYinAbbr(String pinYinAbbr) {
		this.pinYinAbbr = pinYinAbbr;
	}


	public String getIsBigCustomerOwnRegion() {
		return isBigCustomerOwnRegion;
	}


	public void setIsBigCustomerOwnRegion(String isBigCustomerOwnRegion) {
		this.isBigCustomerOwnRegion = isBigCustomerOwnRegion;
	}
    
}
