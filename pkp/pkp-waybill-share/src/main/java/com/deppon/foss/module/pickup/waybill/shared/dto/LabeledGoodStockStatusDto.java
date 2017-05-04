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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/LabeledGoodStockStatusDto.java
 * 
 * FILE NAME        	: LabeledGoodStockStatusDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;

import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;

/**
 * 
 * 货签库存状态
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-12-7 下午2:05:58
 */
public class LabeledGoodStockStatusDto implements Serializable {

	private static final long serialVersionUID = -1515259751745244833L;

	/**
	 * 当前库存部门编码
	 */
	private String currentStockOrgCode;
	
	/**
	 * 是否在库
	 */
	private boolean isInStock;
	
	/**
	 * 对应货签实体
	 */
	private LabeledGoodEntity labeledGoodEntity;
	
	
	/**
	 * @return the labeledGoodEntity
	 */
	public LabeledGoodEntity getLabeledGoodEntity() {
		return labeledGoodEntity;
	}


	
	/**
	 * @param labeledGoodEntity the labeledGoodEntity to set
	 */
	public void setLabeledGoodEntity(LabeledGoodEntity labeledGoodEntity) {
		this.labeledGoodEntity = labeledGoodEntity;
	}


	/**
	 * @return the isInStock
	 */
	public boolean isInStock() {
		return isInStock;
	}

	
	/**
	 * @param isInStock the isInStock to set
	 */
	public void setInStock(boolean isInStock) {
		this.isInStock = isInStock;
	}

	/**
	 * @return the currentStockOrgCode
	 */
	public String getCurrentStockOrgCode() {
		return currentStockOrgCode;
	}

	/**
	 * @param currentStockOrgCode
	 *            the currentStockOrgCode to set
	 */
	public void setCurrentStockOrgCode(String currentStockOrgCode) {
		this.currentStockOrgCode = currentStockOrgCode;
	}

}