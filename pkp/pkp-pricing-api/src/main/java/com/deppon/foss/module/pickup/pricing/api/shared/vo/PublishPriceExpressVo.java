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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/vo/PublishPriceVo.java
 * 
 * FILE NAME        	: PublishPriceVo.java
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

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PublishPriceExpressEntity;

/**
 * (公布价VO)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:dp-张斌,date:2012-10-23 上午10:13:10
 * </p>
 * 
 * @author dp-张斌
 * @date 2012-10-23 上午10:13:10
 * @since
 * @version
 */
public class PublishPriceExpressVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2981743427191134913L;
	/**
	 * 公布价 查询条件
	 */
	private PublishPriceExpressEntity publishPriceExpressEntity;
	/** 
	 * 公布价返回结果集
	 */
	private List<PublishPriceExpressEntity> publishPriceExpressEntityList;
	
	/**
	 * 获取 公布价 查询条件.
	 *
	 * @return the 公布价 查询条件
	 */
	public PublishPriceExpressEntity getPublishPriceExpressEntity() {
		return publishPriceExpressEntity;
	}

	/**
	 * 设置 公布价 查询条件.
	 *
	 * @param publishPriceEntity the new 公布价 查询条件
	 */
	public void setPublishPriceExpressEntity(
			PublishPriceExpressEntity publishPriceExpressEntity) {
		this.publishPriceExpressEntity = publishPriceExpressEntity;
	}
	
	/**
	 * 获取 公布价返回结果集.
	 *
	 * @return the 公布价返回结果集
	 */
	public List<PublishPriceExpressEntity> getPublishPriceExpressEntityList() {
		return publishPriceExpressEntityList;
	}

	/**
	 * 设置 公布价返回结果集.
	 *
	 * @param publishPriceEntityList the new 公布价返回结果集
	 */
	public void setPublishPriceExpressEntityList(
			List<PublishPriceExpressEntity> publishPriceExpressEntityList) {
		this.publishPriceExpressEntityList = publishPriceExpressEntityList;
	}
}