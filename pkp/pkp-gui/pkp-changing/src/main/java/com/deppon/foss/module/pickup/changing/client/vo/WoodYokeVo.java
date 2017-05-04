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
 * PROJECT NAME	: pkp-changing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/vo/WoodYokeVo.java
 * 
 * FILE NAME        	: WoodYokeVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.changing.client.vo;

import java.math.BigDecimal;

/**
 * 
 * 代打木架VO
 * @author 102246-foss-shaohongliang
 * @date 2012-12-18 下午7:58:49
 */
public class WoodYokeVo {

	/**
	 * 代打木架部门
	 */
	private String packageOrgCode;

	/**
	 * 打木架货物件数
	 */
	private Integer standGoodsNum;

	/**
	 * 代打木架要求
	 */
	private String standRequirement;

	/**
	 * 打木架货物尺寸
	 */
	private String standGoodsSize;

	/**
	 * 打木架货物体积
	 */
	private BigDecimal standGoodsVolume;

	/**
	 * 打木箱货物件数
	 */
	private Integer boxGoodsNum;

	/**
	 * 代打木箱要求
	 */
	private String boxRequirement;

	/**
	 * 打木箱货物尺寸
	 */
	private String boxGoodsSize;

	/**
	 * 打木箱货物体积
	 */
	private BigDecimal boxGoodsVolume;

	
	/**
	 * @return the packageOrgCode
	 */
	public String getPackageOrgCode() {
		return packageOrgCode;
	}

	
	/**
	 * @param packageOrgCode the packageOrgCode to set
	 */
	public void setPackageOrgCode(String packageOrgCode) {
		this.packageOrgCode = packageOrgCode;
	}

	
	/**
	 * @return the standGoodsNum
	 */
	public Integer getStandGoodsNum() {
		return standGoodsNum;
	}

	
	/**
	 * @param standGoodsNum the standGoodsNum to set
	 */
	public void setStandGoodsNum(Integer standGoodsNum) {
		this.standGoodsNum = standGoodsNum;
	}

	
	/**
	 * @return the standRequirement
	 */
	public String getStandRequirement() {
		return standRequirement;
	}

	
	/**
	 * @param standRequirement the standRequirement to set
	 */
	public void setStandRequirement(String standRequirement) {
		this.standRequirement = standRequirement;
	}

	
	/**
	 * @return the standGoodsSize
	 */
	public String getStandGoodsSize() {
		return standGoodsSize;
	}

	
	/**
	 * @param standGoodsSize the standGoodsSize to set
	 */
	public void setStandGoodsSize(String standGoodsSize) {
		this.standGoodsSize = standGoodsSize;
	}

	
	/**
	 * @return the standGoodsVolume
	 */
	public BigDecimal getStandGoodsVolume() {
		return standGoodsVolume;
	}

	
	/**
	 * @param standGoodsVolume the standGoodsVolume to set
	 */
	public void setStandGoodsVolume(BigDecimal standGoodsVolume) {
		this.standGoodsVolume = standGoodsVolume;
	}

	
	/**
	 * @return the boxGoodsNum
	 */
	public Integer getBoxGoodsNum() {
		return boxGoodsNum;
	}

	
	/**
	 * @param boxGoodsNum the boxGoodsNum to set
	 */
	public void setBoxGoodsNum(Integer boxGoodsNum) {
		this.boxGoodsNum = boxGoodsNum;
	}

	
	/**
	 * @return the boxRequirement
	 */
	public String getBoxRequirement() {
		return boxRequirement;
	}

	
	/**
	 * @param boxRequirement the boxRequirement to set
	 */
	public void setBoxRequirement(String boxRequirement) {
		this.boxRequirement = boxRequirement;
	}

	
	/**
	 * @return the boxGoodsSize
	 */
	public String getBoxGoodsSize() {
		return boxGoodsSize;
	}

	
	/**
	 * @param boxGoodsSize the boxGoodsSize to set
	 */
	public void setBoxGoodsSize(String boxGoodsSize) {
		this.boxGoodsSize = boxGoodsSize;
	}

	
	/**
	 * @return the boxGoodsVolume
	 */
	public BigDecimal getBoxGoodsVolume() {
		return boxGoodsVolume;
	}

	
	/**
	 * @param boxGoodsVolume the boxGoodsVolume to set
	 */
	public void setBoxGoodsVolume(BigDecimal boxGoodsVolume) {
		this.boxGoodsVolume = boxGoodsVolume;
	}


}