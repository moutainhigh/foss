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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/domain/CountyEntity.java
 * 
 * FILE NAME        	: CountyEntity.java
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
package com.deppon.foss.module.pickup.common.client.domain;

/**
 * 
 * 区域实体类
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:102246-foss-shaohongliang,date:2012-10-24
 * 下午6:43:20,content
 * </p>
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-24 下午6:43:20
 * @since
 * @version
 */
public class CountyEntity {

	/**
	 * 区域ID
	 */
	private String id;

	/**
	 * 区域名称
	 */
	private String county;

	/**
	 * 城市ID
	 */
	private String father;

	/**
	 * 全拼
	 */
	private String pinyin;

	/**
	 * 简拼
	 */
	private String shortPinyin;

	/**
	 * 是否显示
	 */
	private int isShowWithCity;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the county
	 */
	public String getCounty() {
		return county;
	}

	/**
	 * @param county
	 *            the county to set
	 */
	public void setCounty(String county) {
		this.county = county;
	}

	/**
	 * @return the father
	 */
	public String getFather() {
		return father;
	}

	/**
	 * @param father
	 *            the father to set
	 */
	public void setFather(String father) {
		this.father = father;
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
	 * @return the isShowWithCity
	 */
	public int getIsShowWithCity() {
		return isShowWithCity;
	}

	/**
	 * @param isShowWithCity
	 *            the isShowWithCity to set
	 */
	public void setIsShowWithCity(int isShowWithCity) {
		this.isShowWithCity = isShowWithCity;
	}

}