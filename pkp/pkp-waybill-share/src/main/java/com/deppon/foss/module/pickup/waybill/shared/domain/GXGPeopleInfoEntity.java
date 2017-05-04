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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/domain/WaybillEntity.java
 * 
 * FILE NAME        	: WaybillEntity.java
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
package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.io.Serializable;

/**
 * 运单entity
 * @author dop-266396
 * @date 2015-09-14 上午10:36:10
 */
public class GXGPeopleInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
		// 公司
		private String companyName;
		// 名称
		private String name;
		// 邮编
		private String postCode;
		// 电话
		private String phone;
		// 手机
		private String mobile;
		// 省份
		private String province;
		// 城市
		private String city;
		// 县/区
		private String county;
		// 街道镇（五级地址菜鸟项目使用）
		private String street;
		// 原始地址
		private String originalAddress;
		// 详细地址（详细地址不做校验，因为不是所有的渠道都有详细地址）
		private String address;
		public String getCompanyName() {
			return companyName;
		}
		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPostCode() {
			return postCode;
		}
		public void setPostCode(String postCode) {
			this.postCode = postCode;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public String getProvince() {
			return province;
		}
		public void setProvince(String province) {
			this.province = province;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getCounty() {
			return county;
		}
		public void setCounty(String county) {
			this.county = county;
		}
		public String getStreet() {
			return street;
		}
		public void setStreet(String street) {
			this.street = street;
		}
		public String getOriginalAddress() {
			return originalAddress;
		}
		public void setOriginalAddress(String originalAddress) {
			this.originalAddress = originalAddress;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
}
