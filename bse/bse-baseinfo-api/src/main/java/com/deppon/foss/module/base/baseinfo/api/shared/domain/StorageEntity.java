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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/StorageEntity.java
 * 
 * FILE NAME        	: StorageEntity.java
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
 * 库位
 * 
 * @author zhujunyong
 * Create on Oct 10, 2012
 */
public class StorageEntity extends BaseEntity {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 526917380305965624L;

	/**
	 * 组织
	 */
	private String organizationCode;
	
	/**
	 * 组织名称
	 */
	private String organizationName;

	/**
	 * 外场编码（扩展）
	 */
	private String transferCode;

	/**
	 * 库位编码
	 */
	private String storageCode;

	/**
	 * 虚拟编码
	 */
	private String virtualCode;
	
	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 备注
	 */
	private String notes;

//	/**
//	 * 到各个月台的距离列表
//	 */
//	private List<DistanceEntity> distanceList;
	
	/**
	 * 库区虚拟编码
	 */
	private String goodsAreaVirtualCode;
	/**
	 * 库区名称
	 */
	private String goodsAreaName;
	/**
	 * 库区code
	 */
	private String goodsAreaCode;
	/**
	 * 部门列表（数据权限用）
	 */
	private List<String> orgCodeList;
 	/**
 	 * 库位长度	
 	 */
	private String  storageLength;
	/**
	 * 库位宽度
	 */
	private String  storageWidth;
	/**
	 *  库位横坐标
	 */
	private String  storageAbscissa;
	/**
	 * 库位 纵坐标
	 */
	private String  storageOrdinate;
	/**
	 * 获取 组织
	 * @return  the organizationCode
	 */
	public String getOrganizationCode() {
	    return organizationCode;
	}
	
	/**
	 * 设置  组织
	 * @param organizationCode the organizationCode to set
	 */
	public void setOrganizationCode(String organizationCode) {
	    this.organizationCode = organizationCode;
	}
	
	/**
	 * 获取库位编码
	 * @return  the storageCode
	 */
	public String getStorageCode() {
	    return storageCode;
	}
	
	/**
	 * 设置 库位编码
	 * @param storageCode the storageCode to set
	 */
	public void setStorageCode(String storageCode) {
	    this.storageCode = storageCode;
	}
	
	/**
	 * 获取虚拟编码
	 * @return  the virtualCode
	 */
	public String getVirtualCode() {
	    return virtualCode;
	}
	
	/**
	 * 设置 虚拟编码
	 * @param virtualCode the virtualCode to set
	 */
	public void setVirtualCode(String virtualCode) {
	    this.virtualCode = virtualCode;
	}
	
	/**
	 * 是否有效
	 * @return  the active
	 */
	public String getActive() {
	    return active;
	}

	/**
	 *  设置是否有效
	 * @param active the active to set
	 */
	public void setActive(String active) {
	    this.active = active;
	}
	
	/**
	 * 获取 备注
	 * @return  the notes
	 */
	public String getNotes() {
	    return notes;
	}
	
	/**
	 * 设置备注
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
	    this.notes = notes;
	}
	
//	/**
//	 * 获取  到各个月台的距离列表
//	 * @return  the distanceList
//	 */
//	public List<DistanceEntity> getDistanceList() {
//	    return distanceList;
//	}
//
//	/**
//	 * 设置  到各个月台的距离列表
//	 * @param distanceList the distanceList to set
//	 */
//	public void setDistanceList(List<DistanceEntity> distanceList) {
//	    this.distanceList = distanceList;
//	}
		
	/**
	 * 获取 部门列表（数据权限用）
	 * @return  the orgCodeList
	 */
	public List<String> getOrgCodeList() {
	    return orgCodeList;
	}
	
	/**
	 * 设置 部门列表（数据权限用）
	 * @param orgCodeList the orgCodeList to set
	 */
	public void setOrgCodeList(List<String> orgCodeList) {
	    this.orgCodeList = orgCodeList;
	}
	
	/**
	 * 获取 外场编码
	 * @return  the transferCode
	 */
	public String getTransferCode() {
	    return transferCode;
	}
	
	/**
	 * 设置 外场编码
	 * @param transferCode the transferCode to set
	 */
	public void setTransferCode(String transferCode) {
	    this.transferCode = transferCode;
	}

	/**
	 * 获取 组织名称
	 * @return organizationName
	 */
	public String getOrganizationName() {
		return organizationName;
	}

	/**
	 * 设置组织名称
	 * @param  organizationName  
	 */
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	/**
	 * 获取 库区虚拟编码
	 * @return goodsAreaVirtualCode
	 */
	public String getGoodsAreaVirtualCode() {
		return goodsAreaVirtualCode;
	}

	/**
	 * 设置 库区虚拟编码
	 * @param  goodsAreaVirtualCode  
	 */
	public void setGoodsAreaVirtualCode(String goodsAreaVirtualCode) {
		this.goodsAreaVirtualCode = goodsAreaVirtualCode;
	}
	/**
	 * 获取 库区名称
	 * @param  goodsAreaName  
	 */
	public String getGoodsAreaName() {
		return goodsAreaName;
	}
	/**
	 * 设置 库区名称
	 * @param  goodsAreaName  
	 */
	public void setGoodsAreaName(String goodsAreaName) {
		this.goodsAreaName = goodsAreaName;
	}
	/**
	 *获取 库区编码code
	 * @param  goodsAreaCode  
	 */
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}
	/**
	 * 设置 库区编码
	 * @param  goodsAreaCode  
	 */
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}

	/** 
	 *获取 库位长度
	 * @return  storageLength  
	 */
	public String getStorageLength() {
		return storageLength;
	}

	/**
	 *设置  库位长度
	 *setStorageLength
	 * @param storageLength the storageLength to set
	 * @return the storageLength
	 */
	public void setStorageLength(String storageLength) {
		this.storageLength = storageLength;
	}

	/** 
	 *获取 库位 宽度
	 * @return  storageWidth  
	 */
	public String getStorageWidth() {
		return storageWidth;
	}

	/**
	 *设置 库位宽度
	 *setStorageWidth
	 * @param storageWidth the storageWidth to set
	 * @return the storageWidth
	 */
	public void setStorageWidth(String storageWidth) {
		this.storageWidth = storageWidth;
	}

	/** 
	 *获取  库位横坐标
	 * @return  storageAbscissa  
	 */
	public String getStorageAbscissa() {
		return storageAbscissa;
	}

	/**
	 *设置 库位横坐标
	 *setStorageAbscissa
	 * @param storageAbscissa the storageAbscissa to set
	 * @return the storageAbscissa
	 */
	public void setStorageAbscissa(String storageAbscissa) {
		this.storageAbscissa = storageAbscissa;
	}

	/** 
	 *获取 库位纵坐标
	 * @return  storageOrdinate  
	 */
	public String getStorageOrdinate() {
		return storageOrdinate;
	}

	/**
	 *设置 纵坐标
	 *setStorageOrdinate
	 * @param storageOrdinate the storageOrdinate to set
	 * @return the storageOrdinate
	 */
	public void setStorageOrdinate(String storageOrdinate) {
		this.storageOrdinate = storageOrdinate;
	}
	
	
}