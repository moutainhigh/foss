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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/AirportEntity.java
 * 
 * FILE NAME        	: AirportEntity.java
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
 * 用来存储交互“机场信息”的数据库对应实体：SUC-52
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-11 下午5:24:54</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-11 下午5:24:54
 * @since
 * @version
 */
public class AirportEntity extends BaseEntity {
    
    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -517786922285451284L;

    /**
     * 机场名称.
     */
    private String airportName;

    /**
     * 机场三字码.
     */
    private String airportCode;
    
    /**
     * 所在省份.
     */
    private String provCode;
    
    /**
     * 扩展字段省份名称.
     */
    private String provName;
    
    /**
     * 机场所在城市.
     */
    private String cityCode;
    
    /**
     * 扩展字段城市名称.
     */
    private String cityName;
    
    /**
     * 所属区县.
     */
    private String countyCode;

    /**
     * 联系人.
     */
    private String contact;

    /**
     * 联系电话.
     */
    private String contactPhone;

    /**
     * 提货地址.
     */
    private String pickupAddress;

    /**
     * 机场描述信息.
     */
    private String notes;

    /**
     * 是否启用.
     */
    private String active;
    
    /**
     * 中文简称.
     */
    private String simplename;

   

    /**
     * 中文简称拼音.
     */
    private String pinyin;
    
    /**
     * 扩展字段查询参数.
     */
    private String queryParam;
    
    /**
     * 扩展字段查询城市参数.
     */
    private String queryDistrictParam;
    
    /**
     * 获取 机场名称.
     *
     * @return  the airportName
     */
    public String getAirportName() {
        return airportName;
    }
    
    /**
     * 获取 扩展字段查询城市参数.
     *
     * @return the 扩展字段查询城市参数
     */
    public String getQueryDistrictParam() {
		return queryDistrictParam;
	}

	/**
	 * 设置 扩展字段查询城市参数.
	 *
	 * @param queryDistrictParam the new 扩展字段查询城市参数
	 */
	public void setQueryDistrictParam(String queryDistrictParam) {
		this.queryDistrictParam = queryDistrictParam;
	}

	/**
	 * 获取 扩展字段查询参数.
	 *
	 * @return the 扩展字段查询参数
	 */
	public String getQueryParam() {
		return queryParam;
	}

	/**
	 * 设置 扩展字段查询参数.
	 *
	 * @param queryParam the new 扩展字段查询参数
	 */
	public void setQueryParam(String queryParam) {
		this.queryParam = queryParam;
	}

	/**
	 * 获取 扩展字段省份名称.
	 *
	 * @return the 扩展字段省份名称
	 */
	public String getProvName() {
		return provName;
	}

	/**
	 * 设置 扩展字段省份名称.
	 *
	 * @param provName the new 扩展字段省份名称
	 */
	public void setProvName(String provName) {
		this.provName = provName;
	}

	/**
	 * 获取 扩展字段城市名称.
	 *
	 * @return the 扩展字段城市名称
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * 设置 扩展字段城市名称.
	 *
	 * @param cityName the new 扩展字段城市名称
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * 设置 机场名称.
	 *
	 * @param airportName the airportName to set
	 */
    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }
    
    /**
     * 获取 机场三字码.
     *
     * @return  the airportCode
     */
    public String getAirportCode() {
        return airportCode;
    }
    
    /**
     * 设置 机场三字码.
     *
     * @param airportCode the airportCode to set
     */
    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }
    
    /**
     * 获取 机场所在城市.
     *
     * @return  the cityCode
     */
    public String getCityCode() {
        return cityCode;
    }
    
    /**
     * 设置 机场所在城市.
     *
     * @param cityCode the cityCode to set
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    
    /**
     * 获取 所属区县.
     *
     * @return  the countyCode
     */
    public String getCountyCode() {
        return countyCode;
    }
    
    /**
     * 设置 所属区县.
     *
     * @param countyCode the countyCode to set
     */
    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
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
     * 获取 提货地址.
     *
     * @return  the pickupAddress
     */
    public String getPickupAddress() {
        return pickupAddress;
    }
    
    /**
     * 设置 提货地址.
     *
     * @param pickupAddress the pickupAddress to set
     */
    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }
    
    /**
     * 获取 机场描述信息.
     *
     * @return  the notes
     */
    public String getNotes() {
        return notes;
    }
    
    /**
     * 设置 机场描述信息.
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
     * 获取 中文简称.
     *
     * @return  the simplename
     */
    public String getSimplename() {
        return simplename;
    }
    
    /**
     * 设置 中文简称.
     *
     * @param simplename the simplename to set
     */
    public void setSimplename(String simplename) {
        this.simplename = simplename;
    }
    
    /**
     * 获取 所在省份.
     *
     * @return  the provCode
     */
    public String getProvCode() {
        return provCode;
    }

    /**
     * 设置 所在省份.
     *
     * @param provCode the provCode to set
     */
    public void setProvCode(String provCode) {
        this.provCode = provCode;
    }

    /**
     * 获取 中文简称拼音.
     *
     * @return  the pinyin
     */
    public String getPinyin() {
        return pinyin;
    }

    /**
     * 设置 中文简称拼音.
     *
     * @param pinyin the pinyin to set
     */
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }    
}
