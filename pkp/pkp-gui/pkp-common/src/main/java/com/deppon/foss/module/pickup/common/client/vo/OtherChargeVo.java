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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/vo/OtherChargeVo.java
 * 
 * FILE NAME        	: OtherChargeVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.common.client.vo;

/**
 * 
 * 其他费用VO
 * 
 * @author 025000-FOSS-helong
 * @date 2012-12-24 下午08:53:52
 */
public class OtherChargeVo {

	private int i;
	// 主键ID
	String id;
	/**
	 * 货物类型name
	 */
	private String goodsTypeName;

	// 费用编码
	String code;
	// 名称
	String chargeName;
	// 归集类别
	String type;
	// 描述
	String descrition;
	// 金额
	String money;
	// 原始金额
	String oldMoney;
	// 上限
	String upperLimit;
	// 下限
	String lowerLimit;
	// 是否可修改
	Boolean isUpdate;
	// 是否可删除
	Boolean isDelete;
    //最高收费
	private String maxMoney="9999999";
	// 是否初始化值
	Boolean isInit;
	// 是否已被修改
	Boolean isEdit;
	// 安装件数
	private int num;
	// 搬运体积
	private String volume;
	// 超标楼层数
	private String excessiveFloorNum;
	/**
	 * @return the isEdit
	 */
	public Boolean getIsEdit() {
		return isEdit;
	}

	/**
	 * @param isEdit
	 *            the isEdit to set
	 */
	public void setIsEdit(Boolean isEdit) {
		this.isEdit = isEdit;
	}

	/**
	 * @return the oldMoney
	 */
	public String getOldMoney() {
		return oldMoney;
	}

	/**
	 * @param oldMoney
	 *            the oldMoney to set
	 */
	public void setOldMoney(String oldMoney) {
		this.oldMoney = oldMoney;
	}

	/**
	 * @return 是否初始化值
	 */
	public Boolean getIsInit() {
		return isInit;
	}

	/**
	 * @param 是否初始化值
	 */
	public void setIsInit(Boolean isInit) {
		this.isInit = isInit;
	}

	/**
	 * 主键ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * 主键ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 费用编码
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 费用编码
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 费用名称
	 */
	public String getChargeName() {
		return chargeName;
	}

	/**
	 * 费用名称
	 */
	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}

	/**
	 * 归集类别
	 */
	public String getType() {
		return type;
	}

	/**
	 * 归集类别
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 描述
	 */
	public String getDescrition() {
		return descrition;
	}

	/**
	 * 描述
	 */
	public void setDescrition(String descrition) {
		this.descrition = descrition;
	}

	/**
	 * 金额
	 */
	public String getMoney() {
		return money;
	}

	/**
	 * 金额
	 */
	public void setMoney(String money) {
		this.money = money;
	}

	/**
	 * 上限
	 */
	public String getUpperLimit() {
		return upperLimit;
	}

	/**
	 * 上限
	 */
	public void setUpperLimit(String upperLimit) {
		this.upperLimit = upperLimit;
	}

	/**
	 * 下限
	 */
	public String getLowerLimit() {
		return lowerLimit;
	}

	/**
	 * 下限
	 */
	public void setLowerLimit(String lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	/**
	 * 是否可修改
	 */
	public Boolean getIsUpdate() {
		return isUpdate;
	}

	/**
	 * 是否可修改
	 */
	public void setIsUpdate(Boolean isUpdate) {
		this.isUpdate = isUpdate;
	}

	/**
	 * 是否可删除
	 */
	public Boolean getIsDelete() {
		return isDelete;
	}

	/**
	 * 是否可删除
	 */
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	
	public String getGoodsTypeName() {
		return goodsTypeName;
	}

	public void setGoodsTypeName(String goodsTypeName) {
		this.goodsTypeName = goodsTypeName;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getExcessiveFloorNum() {
		return excessiveFloorNum;
	}

	public void setExcessiveFloorNum(String excessiveFloorNum) {
		this.excessiveFloorNum = excessiveFloorNum;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public String getMaxMoney() {
		return maxMoney;
	}

	public void setMaxMoney(String maxMoney) {
		this.maxMoney = maxMoney;
	}

	
}