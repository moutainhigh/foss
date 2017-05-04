/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-package-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/packaging/api/shared/domain/QueryUnpackDetailsEntity.java
 *  
 *  FILE NAME          :QueryUnpackDetailsEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/**
 * 
 */
package com.deppon.foss.module.transfer.packaging.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * TODO 查询营业部代打包装的代打明细实体
 * @author 046130-foss-xuduowei
 * @date 2012-10-12 下午6:19:47
 */
public class QueryUnpackDetailsEntity extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5072578338329357866L;
	/**
	 * 流水号
	 */
	private String serialNum;
	/**
	 * 是否需要包装
	 */
	private String isNeedPack;
	/**
	 * 是否已打木架
	 */
	private String isPacked;
	/**
	 * 是否已交接
	 */
	private String handoverNo;
	/**
	 * 货物状态
	 */
	private String goodsStatus;
	/**
	 * 货区
	 */
	private String goodsAreaCode;
	/**
	 * 是否木架区库存
	 */
	private String isStockInYoke;
	/**
	 * 包装类型
	 */
	private String packageType;
	
	/**
	 * 是否已打木托
	 */
	private String isWoodCare;
	
	/**
	 * 获取 流水号.
	 *
	 * @return the 流水号
	 */
	public String getSerialNum() {
		return serialNum;
	}
	
	/**
	 * 设置 流水号.
	 *
	 * @param serialNum the new 流水号
	 */
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	
	/**
	 * 获取 是否需要包装.
	 *
	 * @return the 是否需要包装
	 */
	public String getIsNeedPack() {
		return isNeedPack;
	}
	
	/**
	 * 设置 是否需要包装.
	 *
	 * @param isNeedPack the new 是否需要包装
	 */
	public void setIsNeedPack(String isNeedPack) {
		this.isNeedPack = isNeedPack;
	}
	
	/**
	 * 获取 是否已包装.
	 *
	 * @return the 是否已包装
	 */
	public String getIsPacked() {
		return isPacked;
	}
	
	/**
	 * 设置 是否已包装.
	 *
	 * @param isPacked the new 是否已包装
	 */
	public void setIsPacked(String isPacked) {
		this.isPacked = isPacked;
	}
	
	/**
	 * 获取 货物状态.
	 *
	 * @return the 货物状态
	 */
	public String getGoodsStatus() {
		return goodsStatus;
	}
	
	/**
	 * 设置 货物状态.
	 *
	 * @param goodsStatus the new 货物状态
	 */
	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}
	
	/**
	 * 获取 是否木架区库存.
	 *
	 * @return the 是否木架区库存
	 */
	public String getIsStockInYoke() {
		return isStockInYoke;
	}
	
	/**
	 * 设置 是否木架区库存.
	 *
	 * @param isStockInYoke the new 是否木架区库存
	 */
	public void setIsStockInYoke(String isStockInYoke) {
		this.isStockInYoke = isStockInYoke;
	}
	
	/**
	 * 获取 是否已交接.
	 *
	 * @return the 是否已交接
	 */
	public String getHandoverNo() {
		return handoverNo;
	}
	
	/**
	 * 设置 是否已交接.
	 *
	 * @param handoverNo the new 是否已交接
	 */
	public void setHandoverNo(String handoverNo) {
		this.handoverNo = handoverNo;
	}
	
	/**
	 * 获取 货区.
	 *
	 * @return the 货区
	 */
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}
	
	/**
	 * 设置 货区.
	 *
	 * @param goodsAreaCode the new 货区
	 */
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}

	public String getPackageType() {
		return packageType;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	public String getIsWoodCare() {
		return isWoodCare;
	}

	public void setIsWoodCare(String isWoodCare) {
		this.isWoodCare = isWoodCare;
	}
	
	
}