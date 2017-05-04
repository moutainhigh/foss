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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/GoodsAreaEntity.java
 * 
 * FILE NAME        	: GoodsAreaEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/**
 * 
 */
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * @author zhujunyong
 * Create on Oct 10, 2012
 * 库区
 */
public class GoodsAreaEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7505227148208422826L;

	/**
	 *  组织
	 */
	private String organizationCode;
	
	/**
	 *  组织名称(冗余)
	 */
	private String organizationName;

	/**
	 *  外场编码（扩展）
	 */
	private String transferCode;
	
	/**
	 *  库区编码
	 */
	private String goodsAreaCode;
	
	/** 
	 * 虚拟编码
	 */
	private String virtualCode;

	/**
	 *  库区名称
	 */
	private String goodsAreaName;
	
	/**
	 * 名称加编码（供待叉区显示用）
	 */
	private String nameAndCode;
	
	/**
	 * 库区类型(卡货库区、普货库区、城际快车库区,混装库区和偏线库区等,贵重物品，待包装等)
	 */
	private String goodsAreaType;
	
	/**
	 *  货物类型（A货，B货）
	 */
	private String goodsType;
	
	/**
	 * 目的站
	 */
	private String arriveRegionCode;
	
	/**
	 *  目的站（冗余）
	 */
	private String arriveRegionName;
	
	/**
	 *  库区类别（长途，短途）
	 */
	private String goodsAreaUsage;
	
	/**
	 *  是否有效
	 */
	private String active;
	
	/**
	 * 备注
	 */
	private String notes;
	
	/**
	 *  所关联的库位列表
	 */
	private List<StorageEntity> storageList;
	
	/**
	 *  星标编码
	 */
	private String asteriskCode;
	
	/**
	 *  版本号
	 */
	private Long version;
	
	/**
	 * 部门列表（数据权限用）
	 */
	private List<String> orgCodeList;
	
	//Start...
	/**
	 *  计票方式
	 */
	private String countingMode;
	/**
	 *  计票方式(卡普)
	 */
	private String countingModeKp;
	/**
	 *  计票方式(卡普)
	 */
	private String countingModeAb;
	
	//新增成员变量
	/**
	 * 库区宽度
	 */
	private String goodsAreaWidth;
	/**
	 * 库区长度
	 */
	private String goodsAreaLength;
	/**
	 * 库区高度
	 */
	private String goodsAreaHeight;
	/**
	 * 库区体积
	 */
	private String volume;
	/**
	 * 横坐标
	 */
	private String abscissa;
	/**
	 * 纵坐标
	 */
	private String ordinate;
	
	/**
	 *  所关联的库位列表
	 */
	private List<PlatAreaDistanceEntity> distanceList;
	/**
	 * 库区 面积
	 */
	private String area;
	
	
	
	
	
	/** 
	 *获取 组织
	 * @return  organizationCode  
	 */
	public String getOrganizationCode() {
		return organizationCode;
	}
	/**
	 *设置 组织
	 *setOrganizationCode
	 * @param organizationCode the organizationCode to set
	 * @return the organizationCode
	 */
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}
	/** 
	 *获取 组织名称(冗余
	 * @return  organizationName  
	 */
	public String getOrganizationName() {
		return organizationName;
	}
	/**
	 *设置 组织名称(冗余
	 *setOrganizationName
	 * @param organizationName the organizationName to set
	 * @return the organizationName
	 */
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	/** 
	 *获取 外场编码（扩展
	 * @return  transferCode  
	 */
	public String getTransferCode() {
		return transferCode;
	}
	/**
	 *设置 外场编码（扩展
	 *setTransferCode
	 * @param transferCode the transferCode to set
	 * @return the transferCode
	 */
	public void setTransferCode(String transferCode) {
		this.transferCode = transferCode;
	}
	/** 
	 *获取 库区编码
	 * @return  goodsAreaCode  
	 */
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}
	/**
	 *设置 库区编码
	 *setGoodsAreaCode
	 * @param goodsAreaCode the goodsAreaCode to set
	 * @return the goodsAreaCode
	 */
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}
	/** 
	 *获取虚拟编码
	 * @return  virtualCode  
	 */
	public String getVirtualCode() {
		return virtualCode;
	}
	/**
	 *设置虚拟编码
	 *setVirtualCode
	 * @param virtualCode the virtualCode to set
	 * @return the virtualCode
	 */
	public void setVirtualCode(String virtualCode) {
		this.virtualCode = virtualCode;
	}
	/** 
	 *获取 库区名称
	 * @return  goodsAreaName  
	 */
	public String getGoodsAreaName() {
		return goodsAreaName;
	}
	/**
	 *设置 库区名称
	 *setGoodsAreaName
	 * @param goodsAreaName the goodsAreaName to set
	 * @return the goodsAreaName
	 */
	public void setGoodsAreaName(String goodsAreaName) {
		this.goodsAreaName = goodsAreaName;
	}
	/** 
	 *获取 名称加编码（供待叉区显示用
	 * @return  nameAndCode  
	 */
	public String getNameAndCode() {
		return nameAndCode;
	}
	/**
	 *设置 名称加编码（供待叉区显示用
	 *setNameAndCode
	 * @param nameAndCode the nameAndCode to set
	 * @return the nameAndCode
	 */
	public void setNameAndCode(String nameAndCode) {
		this.nameAndCode = nameAndCode;
	}
	/** 
	 *获取 库区类型(卡货库区、普货库区、城际快车库区,混装库区和偏线库区等,贵重物品，待包装等
	 * @return  goodsAreaType  
	 */
	public String getGoodsAreaType() {
		return goodsAreaType;
	}
	/**
	 *设置 库区类型(卡货库区、普货库区、城际快车库区,混装库区和偏线库区等,贵重物品，待包装等
	 *setGoodsAreaType
	 * @param goodsAreaType the goodsAreaType to set
	 * @return the goodsAreaType
	 */
	public void setGoodsAreaType(String goodsAreaType) {
		this.goodsAreaType = goodsAreaType;
	}
	/** 
	 *获取 货物类型（A货，B货
	 * @return  goodsType  
	 */
	public String getGoodsType() {
		return goodsType;
	}
	/**
	 *设置 货物类型（A货，B货
	 *setGoodsType
	 * @param goodsType the goodsType to set
	 * @return the goodsType
	 */
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	/** 
	 *获取 目的站
	 * @return  arriveRegionCode  
	 */
	public String getArriveRegionCode() {
		return arriveRegionCode;
	}
	/**
	 *设置 目的站
	 *setArriveRegionCode
	 * @param arriveRegionCode the arriveRegionCode to set
	 * @return the arriveRegionCode
	 */
	public void setArriveRegionCode(String arriveRegionCode) {
		this.arriveRegionCode = arriveRegionCode;
	}
	/** 
	 *获取 目的站（冗余） 
	 * @return  arriveRegionName  
	 */
	public String getArriveRegionName() {
		return arriveRegionName;
	}
	/**
	 *设置 目的站（冗余）
	 *setArriveRegionName
	 * @param arriveRegionName the arriveRegionName to set
	 * @return the arriveRegionName
	 */
	public void setArriveRegionName(String arriveRegionName) {
		this.arriveRegionName = arriveRegionName;
	}
	/** 
	 *获取  库区类别（长途，短途
	 * @return  goodsAreaUsage  
	 */
	public String getGoodsAreaUsage() {
		return goodsAreaUsage;
	}
	/**
	 *设置 库区类别（长途，短途
	 *setGoodsAreaUsage
	 * @param goodsAreaUsage the goodsAreaUsage to set
	 * @return the goodsAreaUsage
	 */
	public void setGoodsAreaUsage(String goodsAreaUsage) {
		this.goodsAreaUsage = goodsAreaUsage;
	}
	/** 
	 *获取
	 * @return  active  
	 */
	public String getActive() {
		return active;
	}
	/**
	 *设置
	 *setActive
	 * @param active the active to set
	 * @return the active
	 */
	public void setActive(String active) {
		this.active = active;
	}
	/** 
	 *获取 备注
	 * @return  notes  
	 */
	public String getNotes() {
		return notes;
	}
	/**
	 *设置 备注
	 *setNotes
	 * @param notes the notes to set
	 * @return the notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	/** 
	 *获取 所关联的库位列表
	 * @return  storageList  
	 */
	public List<StorageEntity> getStorageList() {
		return storageList;
	}
	/**
	 *设置 所关联的库位列表
	 *setStorageList
	 * @param storageList the storageList to set
	 * @return the storageList
	 */
	public void setStorageList(List<StorageEntity> storageList) {
		this.storageList = storageList;
	}
	/** 
	 *获取 星标编码
	 * @return  asteriskCode  
	 */
	public String getAsteriskCode() {
		return asteriskCode;
	}
	/**
	 *设置 星标编码
	 *setAsteriskCode
	 * @param asteriskCode the asteriskCode to set
	 * @return the asteriskCode
	 */
	public void setAsteriskCode(String asteriskCode) {
		this.asteriskCode = asteriskCode;
	}
	/** 
	 *获取 版本号
	 * @return  version  
	 */
	public Long getVersion() {
		return version;
	}
	/**
	 *设置 版本号
	 *setVersion
	 * @param version the version to set
	 * @return the version
	 */
	public void setVersion(Long version) {
		this.version = version;
	}
	/** 
	 *获取  部门列表（数据权限用
	 * @return  orgCodeList  
	 */
	public List<String> getOrgCodeList() {
		return orgCodeList;
	}
	/**
	 *设置 部门列表（数据权限用
	 *setOrgCodeList
	 * @param orgCodeList the orgCodeList to set
	 * @return the orgCodeList
	 */
	public void setOrgCodeList(List<String> orgCodeList) {
		this.orgCodeList = orgCodeList;
	}
	/** 
	 *获取 计票方式
	 * @return  countingMode  
	 */
	public String getCountingMode() {
		return countingMode;
	}
	/**
	 *设置 计票方式
	 *setCountingMode
	 * @param countingMode the countingMode to set
	 * @return the countingMode
	 */
	public void setCountingMode(String countingMode) {
		this.countingMode = countingMode;
	}
	/** 
	 *获取 计票方式卡普
	 * @return  countingModeKp  
	 */
	public String getCountingModeKp() {
		return countingModeKp;
	}
	/**
	 *设置 计票方式卡普 
	 *setCountingModeKp
	 * @param countingModeKp the countingModeKp to set
	 * @return the countingModeKp
	 */
	public void setCountingModeKp(String countingModeKp) {
		this.countingModeKp = countingModeKp;
	}
	/** 
	 *获取 计票方式
	 * @return  countingModeAb  
	 */
	public String getCountingModeAb() {
		return countingModeAb;
	}
	/**
	 *设置 计票方式
	 *setCountingModeAb
	 * @param countingModeAb the countingModeAb to set
	 * @return the countingModeAb
	 */
	public void setCountingModeAb(String countingModeAb) {
		this.countingModeAb = countingModeAb;
	}
	/** 
	 *获取 库区宽度
	 * @return  goodsAreaWidth  
	 */
	public String getGoodsAreaWidth() {
		return goodsAreaWidth;
	}
	/**
	 *设置 库区宽度
	 *setGoodsAreaWidth
	 * @param goodsAreaWidth the goodsAreaWidth to set
	 * @return the goodsAreaWidth
	 */
	public void setGoodsAreaWidth(String goodsAreaWidth) {
		this.goodsAreaWidth = goodsAreaWidth;
	}
	/** 
	 *获取 库区长度
	 * @return  goodsAreaLength  
	 */
	public String getGoodsAreaLength() {
		return goodsAreaLength;
	}
	/**
	 *设置 库区长度
	 *setGoodsAreaLength
	 * @param goodsAreaLength the goodsAreaLength to set
	 * @return the goodsAreaLength
	 */
	public void setGoodsAreaLength(String goodsAreaLength) {
		this.goodsAreaLength = goodsAreaLength;
	}
	/** 
	 *获取 库区高度
	 * @return  goodsAreaHeight  
	 */
	public String getGoodsAreaHeight() {
		return goodsAreaHeight;
	}
	/**
	 *设置 库区高度
	 *setGoodsAreaHeight
	 * @param goodsAreaHeight the goodsAreaHeight to set
	 * @return the goodsAreaHeight
	 */
	public void setGoodsAreaHeight(String goodsAreaHeight) {
		this.goodsAreaHeight = goodsAreaHeight;
	}
	/** 
	 *获取 体积
	 * @return  volume  
	 */
	public String getVolume() {
		return volume;
	}
	/**
	 *设置 体积
	 *setVolume
	 * @param volume the volume to set
	 * @return the volume
	 */
	public void setVolume(String volume) {
		this.volume = volume;
	}
	/** 
	 *获取 很坐标
	 * @return  abscissa  
	 */
	public String getAbscissa() {
		return abscissa;
	}
	/**
	 *设置 横坐标
	 *setAbscissa
	 * @param abscissa the abscissa to set
	 * @return the abscissa
	 */
	public void setAbscissa(String abscissa) {
		this.abscissa = abscissa;
	}
	/** 
	 *获取 纵坐标
	 * @return  ordinate  
	 */
	public String getOrdinate() {
		return ordinate;
	}
	/**
	 *设置 纵坐标
	 *setOrdinate
	 * @param ordinate the ordinate to set
	 * @return the ordinate
	 */
	public void setOrdinate(String ordinate) {
		this.ordinate = ordinate;
	}
	/** 
	 *获取   所关联的库位列表
	 * @return  distanceList  
	 */
	public List<PlatAreaDistanceEntity> getDistanceList() {
		return distanceList;
	}
	/**
	 *设置  所关联的库位列表
	 *setDistanceList
	 * @param distanceList the distanceList to set
	 * @return the distanceList
	 */
	public void setDistanceList(List<PlatAreaDistanceEntity> distanceList) {
		this.distanceList = distanceList;
	}
	/** 
	 *获取 库区面积
	 * @return  area  
	 */
	public String getArea() {
		return area;
	}
	/**
	 *设置  库区面积
	 *setArea
	 * @param area the area to set
	 * @return the area
	 */
	public void setArea(String area) {
		this.area = area;
	}
	
}
