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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/TransferCenterEntity.java
 * 
 * FILE NAME        	: TransferCenterEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 德邦的中转部门，主要职责是装卸货， 外场是自关联的类，下属有外场装车组，外场卸车组，配载查询组等
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2013-1-30 下午2:19:30
 */
public class TransferCenterEntity extends BaseEntity {

	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = -4659589241537188455L;

	/**
	 * 部门编码
	 */
	private String orgCode;

	/**
	 * 外场编码
	 */
	private String code;

	/**
	 * 外场名称
	 */
	private String name;

	/**
	 * 拼音
	 */
	private String pinYin;

	/**
	 * 外场简码
	 */
	private String simpleCode;

	/**
	 * 可汽运配载
	 */
	private String vehicleAssemble;

	/**
	 * 可外发配载
	 */
	private String outAssemble;

	/**
	 * 可打木架
	 */
	private String packingWood;

	/**
	 * 可中转
	 */
	private String transfer;

	/**
	 * 是否有自动分拣机
	 */
	private String sortingMachine;

	/**
	 * 货区面积,单位为平方米
	 */
	private String goodsArea;

	/**
	 * 货台面积,单位为平方米
	 */
	private String platArea;

	/**
	 * 库型
	 */
	private String platType;

	/**
	 * 所属外场
	 */
	private String parentOrgId;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date modifyTime;

	/**
	 * 是否启用
	 */
	private String active;

	/**
	 * 创建人
	 */
	private String createUserCode;

	/**
	 * 更新人
	 */
	private String modifyUserCode;

	/**
	 * 版本号
	 */
	private String versionNo;
	
	/**
	 * 是否待叉区
	 */
	private String isHaveWaitforarea;
	
	

	/**
	 * @return orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param  orgCode  
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param  code  
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param  name  
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return pinYin
	 */
	public String getPinYin() {
		return pinYin;
	}

	/**
	 * @param  pinYin  
	 */
	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
	}

	/**
	 * @return simpleCode
	 */
	public String getSimpleCode() {
		return simpleCode;
	}

	/**
	 * @param  simpleCode  
	 */
	public void setSimpleCode(String simpleCode) {
		this.simpleCode = simpleCode;
	}

	/**
	 * @return vehicleAssemble
	 */
	public String getVehicleAssemble() {
		return vehicleAssemble;
	}

	/**
	 * @param  vehicleAssemble  
	 */
	public void setVehicleAssemble(String vehicleAssemble) {
		this.vehicleAssemble = vehicleAssemble;
	}

	/**
	 * @return outAssemble
	 */
	public String getOutAssemble() {
		return outAssemble;
	}

	/**
	 * @param  outAssemble  
	 */
	public void setOutAssemble(String outAssemble) {
		this.outAssemble = outAssemble;
	}

	/**
	 * @return packingWood
	 */
	public String getPackingWood() {
		return packingWood;
	}

	/**
	 * @param  packingWood  
	 */
	public void setPackingWood(String packingWood) {
		this.packingWood = packingWood;
	}

	/**
	 * @return transfer
	 */
	public String getTransfer() {
		return transfer;
	}

	/**
	 * @param  transfer  
	 */
	public void setTransfer(String transfer) {
		this.transfer = transfer;
	}

	/**
	 * @return sortingMachine
	 */
	public String getSortingMachine() {
		return sortingMachine;
	}

	/**
	 * @param  sortingMachine  
	 */
	public void setSortingMachine(String sortingMachine) {
		this.sortingMachine = sortingMachine;
	}

	/**
	 * @return goodsArea
	 */
	public String getGoodsArea() {
		return goodsArea;
	}

	/**
	 * @param  goodsArea  
	 */
	public void setGoodsArea(String goodsArea) {
		this.goodsArea = goodsArea;
	}

	/**
	 * @return platArea
	 */
	public String getPlatArea() {
		return platArea;
	}

	/**
	 * @param  platArea  
	 */
	public void setPlatArea(String platArea) {
		this.platArea = platArea;
	}

	/**
	 * @return platType
	 */
	public String getPlatType() {
		return platType;
	}

	/**
	 * @param  platType  
	 */
	public void setPlatType(String platType) {
		this.platType = platType;
	}

	/**
	 * @return parentOrgId
	 */
	public String getParentOrgId() {
		return parentOrgId;
	}

	/**
	 * @param  parentOrgId  
	 */
	public void setParentOrgId(String parentOrgId) {
		this.parentOrgId = parentOrgId;
	}

	/**
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param  createTime  
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param  modifyTime  
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param  active  
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return createUserCode
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * @param  createUserCode  
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * @return modifyUserCode
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}

	/**
	 * @param  modifyUserCode  
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	/**
	 * @return versionNo
	 */
	public String getVersionNo() {
		return versionNo;
	}

	/**
	 * @param  versionNo  
	 */
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public String getIsHaveWaitforarea() {
		return isHaveWaitforarea;
	}

	public void setIsHaveWaitforarea(String isHaveWaitforarea) {
		this.isHaveWaitforarea = isHaveWaitforarea;
	}
	
	
}