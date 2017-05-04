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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/vo/WoodYokeVo.java
 * 
 * FILE NAME        	: WoodYokeVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.common.client.vo;

import java.math.BigDecimal;

/**
 * 
 * 打木架VO
 * @author 025000-FOSS-helong
 * @date 2012-12-24 下午08:54:46
 */
public class WoodYokeVo {

	// 代打木架部门
	private String packageOrgCode;

	// 打木架货物件数
	private Integer standGoodsNum;

	// 代打木架要求
	private String standRequirement;

	// 打木架货物尺寸
	private String standGoodsSize;

	// 打木架货物体积
	private BigDecimal standGoodsVolume;

	// 打木箱货物件数
	private Integer boxGoodsNum;

	// 代打木箱要求
	private String boxRequirement;

	// 打木箱货物尺寸
	private String boxGoodsSize;

	// 打木箱货物体积
	private BigDecimal boxGoodsVolume;

	
	/**
	 * 代打木架部门
	 */
	public String getPackageOrgCode() {
		return packageOrgCode;
	}

	/**
	 * 代打木架部门
	 */
	public void setPackageOrgCode(String packageOrgCode) {
		this.packageOrgCode = packageOrgCode;
	}

	/**
	 * 打木架货物件数
	 */
	public Integer getStandGoodsNum() {
		return standGoodsNum;
	}

	/**
	 * 打木架货物件数
	 */
	public void setStandGoodsNum(Integer standGoodsNum) {
		this.standGoodsNum = standGoodsNum;
	}

	/**
	 * 代打木架要求
	 */
	public String getStandRequirement() {
		return standRequirement;
	}

	/**
	 * 代打木架要求
	 */
	public void setStandRequirement(String standRequirement) {
		this.standRequirement = standRequirement;
	}

	/**
	 * 打木架货物尺寸
	 */
	public String getStandGoodsSize() {
		return standGoodsSize;
	}

	/**
	 * 打木架货物尺寸
	 */
	public void setStandGoodsSize(String standGoodsSize) {
		this.standGoodsSize = standGoodsSize;
	}

	/**
	 * 打木架货物体积
	 */
	public BigDecimal getStandGoodsVolume() {
		return standGoodsVolume;
	}

	/**
	 * 打木架货物体积
	 */
	public void setStandGoodsVolume(BigDecimal standGoodsVolume) {
		this.standGoodsVolume = standGoodsVolume;
	}

	/**
	 * 打木箱货物件数
	 */
	public Integer getBoxGoodsNum() {
		return boxGoodsNum;
	}

	/**
	 * 打木箱货物件数
	 */
	public void setBoxGoodsNum(Integer boxGoodsNum) {
		this.boxGoodsNum = boxGoodsNum;
	}

	/**
	 * 代打木箱要求
	 */
	public String getBoxRequirement() {
		return boxRequirement;
	}

	/**
	 * 代打木箱要求
	 */
	public void setBoxRequirement(String boxRequirement) {
		this.boxRequirement = boxRequirement;
	}

	/**
	 * 打木箱货物尺寸
	 */
	public String getBoxGoodsSize() {
		return boxGoodsSize;
	}

	/**
	 * 打木箱货物尺寸
	 */
	public void setBoxGoodsSize(String boxGoodsSize) {
		this.boxGoodsSize = boxGoodsSize;
	}

	/**
	 * 打木箱货物体积
	 */
	public BigDecimal getBoxGoodsVolume() {
		return boxGoodsVolume;
	}

	/**
	 * 打木箱货物体积
	 */
	public void setBoxGoodsVolume(BigDecimal boxGoodsVolume) {
		this.boxGoodsVolume = boxGoodsVolume;
	}

}