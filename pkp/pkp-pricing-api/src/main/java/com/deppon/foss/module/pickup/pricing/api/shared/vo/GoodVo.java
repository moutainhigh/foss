/**
 *  initial comments.
 */
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
 * PROJECT NAME	: pkp-pricing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/vo/GoodVo.java
 * 
 * FILE NAME        	: GoodVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;

/**
 * (货物类型VO)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:dp-张斌,date:2012-12-18 上午10:13:10
 * </p>
 * 
 * @author dp-张斌
 * @date 2012-12-18 上午10:13:10
 * @since
 * @version
 */
public class GoodVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4480452754031715805L;
	/**
	 * 货物类型
	 */
	private GoodsTypeEntity goodsTypeEntity;
	/**
	 * 货物类型LIST
	 */
    private List<GoodsTypeEntity> goodsTypeEntityList;
    /**
     * 激活/删除IDS
     */
    private List<String> goodIds;
    
	/**
	 * 获取 激活/删除IDS.
	 *
	 * @return the 激活/删除IDS
	 */
	public List<String> getGoodIds() {
		return goodIds;
	}
	
	/**
	 * 设置 激活/删除IDS.
	 *
	 * @param goodIds the new 激活/删除IDS
	 */
	public void setGoodIds(List<String> goodIds) {
		this.goodIds = goodIds;
	}
	
	/**
	 * 获取 货物类型.
	 *
	 * @return the 货物类型
	 */
	public GoodsTypeEntity getGoodsTypeEntity() {
		return goodsTypeEntity;
	}
	
	/**
	 * 设置 货物类型.
	 *
	 * @param goodsTypeEntity the new 货物类型
	 */
	public void setGoodsTypeEntity(GoodsTypeEntity goodsTypeEntity) {
		this.goodsTypeEntity = goodsTypeEntity;
	}
	
	/**
	 * 获取 货物类型LIST.
	 *
	 * @return the 货物类型LIST
	 */
	public List<GoodsTypeEntity> getGoodsTypeEntityList() {
		return goodsTypeEntityList;
	}
	
	/**
	 * 设置 货物类型LIST.
	 *
	 * @param goodsTypeEntityList the new 货物类型LIST
	 */
	public void setGoodsTypeEntityList(List<GoodsTypeEntity> goodsTypeEntityList) {
		this.goodsTypeEntityList = goodsTypeEntityList;
	}
    
}