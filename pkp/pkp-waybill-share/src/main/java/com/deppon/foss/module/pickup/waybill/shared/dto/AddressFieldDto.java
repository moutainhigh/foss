/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dto/AddressFieldDto.java
 * 
 * FILE NAME        	: AddressFieldDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
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
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;

/**
 * 
 * 地址栏绑定VO
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:102246-foss-shaohongliang,date:2012-10-24
 * 下午3:13:42,
 * </p>
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-24 下午3:13:42
 * @since
 * @version
 */
public class AddressFieldDto implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7816372977180961523L;

	/**
	 * 3
	 */
	private static final int THREE = 3;
	
	/**
	 * 4
	 */
	private static final int FOUR = 4;

	/**
	 * 区域ID
	 */
	private String countyId;

	/**
	 * 区域名称
	 */
	private String countyName;

	/**
	 * 城市ID
	 */
	private String cityId;

	/**
	 * 城市名称
	 */
	private String cityName;

	/**
	 * 省份ID
	 */
	private String provinceId;

	/**
	 * 省份名称
	 */
	private String provinceName;

	/**
	 * 国家ID
	 */
	private String nationId;
	
	/**
	 * 国家名称
	 */
	private String nationName;

	/**
	 * 全拼
	 */
	private String pinyin;

	/**
	 * 简拼
	 */
	private String shortPinyin;

	/**
	 * 层次深度 国家 = 0 省份 = 1 城市 = 2 区域 = 3  乡镇=4
	 */
	private int degree;
	
	/**
	 * 乡镇名称
	 */
	private String villageTownName;
	
	/**
	 * 乡镇ID
	 */
	private String villageTownId;
	

	/**
	 * 地址
	 * @return
	 */
	private String address;
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getVillageTownName() {
		return villageTownName;
	}

	public void setVillageTownName(String villageTownName) {
		this.villageTownName = villageTownName;
	}

	public String getVillageTownId() {
		return villageTownId;
	}

	public void setVillageTownId(String villageTownId) {
		this.villageTownId = villageTownId;
	}

	/**
	 * @return the countyId
	 */
	public String getCountyId() {
		return countyId;
	}

	/**
	 * @param countyId
	 *            the countyId to set
	 */
	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}

	/**
	 * @return the countyName
	 */
	public String getCountyName() {
		return countyName;
	}

	/**
	 * @param countyName
	 *            the countyName to set
	 */
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	/**
	 * @return the cityId
	 */
	public String getCityId() {
		return cityId;
	}

	/**
	 * @param cityId
	 *            the cityId to set
	 */
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName
	 *            the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return the provinceId
	 */
	public String getProvinceId() {
		return provinceId;
	}

	/**
	 * @param provinceId
	 *            the provinceId to set
	 */
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	/**
	 * @return the provinceName
	 */
	public String getProvinceName() {
		return provinceName;
	}

	/**
	 * @param provinceName
	 *            the provinceName to set
	 */
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	/**
	 * @return the pinyin
	 */
	public String getPinyin() {
		return pinyin;
	}

	/**
	 * @param pinyin
	 *            the pinyin to set
	 */
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	/**
	 * @return the shortPinyin
	 */
	public String getShortPinyin() {
		return shortPinyin;
	}

	/**
	 * @param shortPinyin
	 *            the shortPinyin to set
	 */
	public void setShortPinyin(String shortPinyin) {
		this.shortPinyin = shortPinyin;
	}

	/**
	 * @return the degree
	 */
	public int getDegree() {
		return degree;
	}

	/**
	 * @param degree
	 *            the degree to set
	 */
	public void setDegree(int degree) {
		this.degree = degree;
	}

	/**
	 * Get Nation ID
	 * @return nationId
	 */
	public String getNationId() {
		return nationId;
	}

	/**
	 * Set Nation ID
	 * @param nationId
	 */
	public void setNationId(String nationId) {
		this.nationId = nationId;
	}

	/**
	 * Get Nation Name
	 * @return nationName
	 */
	public String getNationName() {
		return nationName;
	}

	/**
	 * Set Nation Name
	 * @param nationName
	 */
	public void setNationName(String nationName) {
		this.nationName = nationName;
	}
	
	/**
	 * to string方法
	 */
	public String toString() {
		switch (degree) {
		case 0:
			return nationName;
		case 1:
			return provinceName;
		case 2:
			return cityName;
		case THREE:
			return countyName;
		case FOUR:
			return villageTownName;
		default:
			break;
		}
		return super.toString();
	}
	
}