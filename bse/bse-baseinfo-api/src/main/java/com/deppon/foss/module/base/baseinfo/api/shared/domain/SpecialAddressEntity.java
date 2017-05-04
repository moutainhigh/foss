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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/SpecialAddressEntity.java
 * 
 * FILE NAME        	: SpecialAddressEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 特殊地址实体类.
 *
 * @author 何波
 * @date 2013-2-20 下午2:24:59
 * @since
 * @version
 */
public class SpecialAddressEntity extends BaseEntity {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -6216784102822021979L;

    /**
     * 地址类别.
     */
    private String type;

    /**
     * 国家代码.
     */
    private String nationCode;

    /**
     * 省份代码.
     */
    private String provinceCode;

    /**
     * 城市代码.
     */
    private String cityCode;

    /**
     * 区县代码.
     */
    private String countyCode;

    /**
     * 详细地址.
     */
    private String address;

    /**
     * 电话号码.
     */
    private String phoneNO;

    /**
     * 是否作废标志.
     */
    private String active;

    /**
     * 描述.
     */
    private String description;
    
    /**
     * 描述扩展.
     */
    private String descNote;
    
    /**
     * 获取 描述扩展.
     *
     * @return  the descNote
     */
    public String getDescNote() {
        return descNote;
    }

    
    /**
     * 设置 描述扩展.
     *
     * @param descNote the descNote to set
     */
    public void setDescNote(String descNote) {
        this.descNote = descNote;
        if(StringUtils.isNotBlank(descNote)){
            this.description = descNote;
        }
        
    }

    /**
     * 获取 描述.
     *
     * @return the description
     */
    public String getDescription() {
	return description;
    }

    /**
     * 设置 描述.
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
	this.description = description;
	if(StringUtils.isNotBlank(description)){
	    this.descNote = description;
	}
	
    }

    /**
     * 获取 地址类别.
     *
     * @return type
     */
    public String getType() {
	return type;
    }

    /**
     * 设置 地址类别.
     *
     * @param type the new 地址类别
     */
    public void setType(String type) {
	this.type = type;
    }

    /**
     * 获取 国家代码.
     *
     * @return nationCode
     */
    public String getNationCode() {
	return nationCode;
    }

    /**
     * 设置 国家代码.
     *
     * @param nationCode the new 国家代码
     */
    public void setNationCode(String nationCode) {
	this.nationCode = nationCode;
    }

    /**
     * 获取 省份代码.
     *
     * @return provinceCode
     */
    public String getProvinceCode() {
	return provinceCode;
    }

    /**
     * 设置 省份代码.
     *
     * @param provinceCode the new 省份代码
     */
    public void setProvinceCode(String provinceCode) {
	this.provinceCode = provinceCode;
    }

    /**
     * 获取 城市代码.
     *
     * @return cityCode
     */
    public String getCityCode() {
	return cityCode;
    }

    /**
     * 设置 城市代码.
     *
     * @param cityCode the new 城市代码
     */
    public void setCityCode(String cityCode) {
	this.cityCode = cityCode;
    }

    /**
     * 获取 区县代码.
     *
     * @return countyCode
     */
    public String getCountyCode() {
	return countyCode;
    }

    /**
     * 设置 区县代码.
     *
     * @param countyCode the new 区县代码
     */
    public void setCountyCode(String countyCode) {
	this.countyCode = countyCode;
    }

    /**
     * 获取 详细地址.
     *
     * @return address
     */
    public String getAddress() {
	return address;
    }

    /**
     * 设置 详细地址.
     *
     * @param address the new 详细地址
     */
    public void setAddress(String address) {
	this.address = address;
    }

    /**
     * 获取 电话号码.
     *
     * @return phoneNO
     */
    public String getPhoneNO() {
	return phoneNO;
    }

    /**
     * 设置 电话号码.
     *
     * @param phoneNO the new 电话号码
     */
    public void setPhoneNO(String phoneNO) {
	this.phoneNO = phoneNO;
    }

    /**
     * 获取 是否作废标志.
     *
     * @return active
     */
    public String getActive() {
	return active;
    }

    /**
     * 设置 是否作废标志.
     *
     * @param active the new 是否作废标志
     */
    public void setActive(String active) {
	this.active = active;
    }

}