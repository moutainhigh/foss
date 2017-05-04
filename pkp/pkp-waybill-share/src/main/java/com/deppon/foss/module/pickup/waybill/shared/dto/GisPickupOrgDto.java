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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/GisPickupOrgDto.java
 * 
 * FILE NAME        	: GisPickupOrgDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill-share
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.shared.dto
 * FILE    NAME: GisInputDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;


/**
 * 封装GIS到达网点查询数据的DTO
 * @author 026123-foss-lifengteng
 * @date 2012-12-26 上午11:44:29
 */
public class GisPickupOrgDto implements Serializable {

	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = -2117231999306716265L;

	/**
	 * 运单号
	 */
	private String appNum;
	
	/**
	 * 客户省份
	 */
	private String province;
	
	/**
	 * 客户城市
	 */
	private String city;
	
	/**
	 * 客户区县
	 */
	private String county;
	
	/**
	 * 地址
	 */
	private String otherAddress;
	
	/**
	 * 手机号码
	 */
	private String phone;
	
	/**
	 * 电话号码
	 */
	private String tel;
	
	/**
	 * 运输类型
	 */
	private String transportway;
	
	/**
	 * 提货方式
	 */
	private String matchtypes;

	
	/**
	 * 获取 运单号.
	 *
	 * @return the 运单号
	 */
	public String getAppNum() {
		return appNum;
	}

	
	/**
	 * 设置 运单号.
	 *
	 * @param appNum the new 运单号
	 */
	public void setAppNum(String appNum) {
		this.appNum = appNum;
	}

	
	/**
	 * 获取 客户省份.
	 *
	 * @return the 客户省份
	 */
	public String getProvince() {
		return province;
	}

	
	/**
	 * 设置 客户省份.
	 *
	 * @param province the new 客户省份
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	
	/**
	 * 获取 客户城市.
	 *
	 * @return the 客户城市
	 */
	public String getCity() {
		return city;
	}

	
	/**
	 * 设置 客户城市.
	 *
	 * @param city the new 客户城市
	 */
	public void setCity(String city) {
		this.city = city;
	}

	
	/**
	 * 获取 客户区县.
	 *
	 * @return the 客户区县
	 */
	public String getCounty() {
		return county;
	}

	
	/**
	 * 设置 客户区县.
	 *
	 * @param county the new 客户区县
	 */
	public void setCounty(String county) {
		this.county = county;
	}

	
	/**
	 * 获取 地址.
	 *
	 * @return the 地址
	 */
	public String getOtherAddress() {
		return otherAddress;
	}

	
	/**
	 * 设置 地址.
	 *
	 * @param otherAddress the new 地址
	 */
	public void setOtherAddress(String otherAddress) {
		this.otherAddress = otherAddress;
	}

	
	/**
	 * 获取 手机号码.
	 *
	 * @return the 手机号码
	 */
	public String getPhone() {
		return phone;
	}

	
	/**
	 * 设置 手机号码.
	 *
	 * @param phone the new 手机号码
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	
	/**
	 * 获取 电话号码.
	 *
	 * @return the 电话号码
	 */
	public String getTel() {
		return tel;
	}

	
	/**
	 * 设置 电话号码.
	 *
	 * @param tel the new 电话号码
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	
	/**
	 * 获取 运输类型.
	 *
	 * @return the 运输类型
	 */
	public String getTransportway() {
		return transportway;
	}

	
	/**
	 * 设置 运输类型.
	 *
	 * @param transportWay the new 运输类型
	 */
	public void setTransportway(String transportway) {
		this.transportway = transportway;
	}


	
	/**
	 * 获取 提货方式.
	 *
	 * @return the 提货方式
	 */
	public String getMatchtypes() {
		return matchtypes;
	}


	
	/**
	 * 设置 提货方式.
	 *
	 * @param matchType the new 提货方式
	 */
	public void setMatchtypes(String matchtypes) {
		this.matchtypes = matchtypes;
	}

	
}