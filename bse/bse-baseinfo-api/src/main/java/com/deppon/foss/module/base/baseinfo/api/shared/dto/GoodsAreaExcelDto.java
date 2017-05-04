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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/PlatformExcelDto.java
 * 
 * FILE NAME        	: PlatformExcelDto.java
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

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class GoodsAreaExcelDto implements Serializable {

	private static final long serialVersionUID = 1L;

	// 行号 (用来定位，比如用来标示哪一行的数据有问题不能导入)
    private Integer rowNo;

	// 外场编码
	private String transferCode;
	// 外场名称
	private String organizationName;
	// 库区编码
	private String goodsAreaCode;
	// 库区名称
	private String goodsAreaName;
	// 库区类型
	private String goodsAreaType;
	// 货物类型
	private String goodsType;
	// 目的站
	private String arriveRegionName;
	// 库区类别
	private String goodsAreaUsage;
	// 计票方式
	private String countingMode;
	// 星标编码
	private String asteriskCode;
	// 备注
	private String notes;
	
	private String goodsAreaWidth;
	
	private String goodsAreaLength;
	
	private String goodsAreaHeight;
	//横坐标
	private String abscissa;
	//纵坐标
	private String ordinate;
	//面积
	private String area;
	
	private String platformCode;
	
	private String distance;

	// 用户编辑的excel文件中可能前后会有多余空格
    public void trimAll() {
    	transferCode = transferCode != null ? transferCode.trim() : null;
    	organizationName = organizationName != null ? organizationName.trim() : null;
    	goodsAreaCode = goodsAreaCode != null ? goodsAreaCode.trim() : null;
    	goodsAreaName = goodsAreaName != null ? goodsAreaName.trim() : null;
    	goodsAreaType = goodsAreaType != null ? goodsAreaType.trim() : null;
    	goodsType = goodsType != null ? goodsType.trim() : null;
    	arriveRegionName = arriveRegionName != null ? arriveRegionName.trim() : null;
    	notes = notes != null ? notes.trim() : null;
    	//goodsAreaWidth = goodsAreaWidth != null ? goodsAreaWidth.trim() : null;
    	//goodsAreaLength = goodsAreaLength != null ? goodsAreaLength.trim() : null;
    	area = area != null ? area.trim() : null;
    	goodsAreaHeight = goodsAreaHeight != null ? goodsAreaHeight.trim() : null;
    	abscissa = abscissa != null ? abscissa.trim() : null;
    	ordinate = ordinate != null ? ordinate.trim() : null;
    	platformCode = platformCode != null ? platformCode.trim() : null;
    	distance = distance != null ? distance.trim() : null;
    }
    
    // 验证是否有必填栏位没填
    public boolean validate(){
		return StringUtils.isNotBlank(transferCode) 
			&& StringUtils.isNotBlank(organizationName)
			&& StringUtils.isNotBlank(goodsAreaCode)
			&& StringUtils.isNotBlank(goodsAreaName)
			&& StringUtils.isNotBlank(goodsAreaType)
			&& StringUtils.isNotBlank(goodsType)
//			&& StringUtils.isNotBlank(arriveRegionName)
//			&& StringUtils.isNotBlank(notes)
			//&& StringUtils.isNotBlank(goodsAreaWidth)
			//&& StringUtils.isNotBlank(goodsAreaLength)
			&& StringUtils.isNotBlank(area)
			&& StringUtils.isNotBlank(goodsAreaHeight)
			&& StringUtils.isNotBlank(abscissa)
			&& StringUtils.isNotBlank(ordinate)
			&& StringUtils.isNotBlank(platformCode)
			&& StringUtils.isNotBlank(distance);
    }
	
    public Integer getRowNo() {
		return rowNo;
	}
	public void setRowNo(Integer rowNo) {
		this.rowNo = rowNo;
	}

	public String getTransferCode() {
		return transferCode;
	}
	public void setTransferCode(String transferCode) {
		this.transferCode = transferCode;
	}

	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}

	public String getGoodsAreaName() {
		return goodsAreaName;
	}
	public void setGoodsAreaName(String goodsAreaName) {
		this.goodsAreaName = goodsAreaName;
	}

	public String getGoodsAreaType() {
		return goodsAreaType;
	}
	public void setGoodsAreaType(String goodsAreaType) {
		this.goodsAreaType = goodsAreaType;
	}

	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getArriveRegionName() {
		return arriveRegionName;
	}
	public void setArriveRegionName(String arriveRegionName) {
		this.arriveRegionName = arriveRegionName;
	}

	public String getGoodsAreaUsage() {
		return goodsAreaUsage;
	}
	public void setGoodsAreaUsage(String goodsAreaUsage) {
		this.goodsAreaUsage = goodsAreaUsage;
	}

	public String getCountingMode() {
		return countingMode;
	}
	public void setCountingMode(String countingMode) {
		this.countingMode = countingMode;
	}

	public String getAsteriskCode() {
		return asteriskCode;
	}
	public void setAsteriskCode(String asteriskCode) {
		this.asteriskCode = asteriskCode;
	}

	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getGoodsAreaWidth() {
		return goodsAreaWidth;
	}
	public void setGoodsAreaWidth(String goodsAreaWidth) {
		this.goodsAreaWidth = goodsAreaWidth;
	}

	public String getGoodsAreaLength() {
		return goodsAreaLength;
	}
	public void setGoodsAreaLength(String goodsAreaLength) {
		this.goodsAreaLength = goodsAreaLength;
	}

	public String getGoodsAreaHeight() {
		return goodsAreaHeight;
	}
	public void setGoodsAreaHeight(String goodsAreaHeight) {
		this.goodsAreaHeight = goodsAreaHeight;
	}

	public String getAbscissa() {
		return abscissa;
	}
	public void setAbscissa(String abscissa) {
		this.abscissa = abscissa;
	}

	public String getOrdinate() {
		return ordinate;
	}
	public void setOrdinate(String ordinate) {
		this.ordinate = ordinate;
	}

	public String getPlatformCode() {
		return platformCode;
	}
	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}

	/** 
	 *获取
	 * @return  area  
	 */
	public String getArea() {
		return area;
	}

	/**
	 *设置
	 *setArea
	 * @param area the area to set
	 * @return the area
	 */
	public void setArea(String area) {
		this.area = area;
	}
	
    
}
