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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/WaybillLabeledGoodStockDto.java
 * 
 * FILE NAME        	: WaybillLabeledGoodStockDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill-share
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.shared.dto
 * FILE    NAME: WaybillStockStatusDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 运单货签库存状态
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-11-29 下午2:38:23
 */
public class WaybillLabeledGoodStockDto implements Serializable {

	private static final long serialVersionUID = 6683245916402044232L;

	/**
	 *  货签状态
	 */
	private List<LabeledGoodStockStatusDto> labeledGoodStockStatusDtos;

	
	/**
	 * @return the labeledGoodStockStatusDtos
	 */
	public List<LabeledGoodStockStatusDto> getLabeledGoodStockStatusDtos() {
		return labeledGoodStockStatusDtos;
	}

	
	/**
	 * @param labeledGoodStockStatusDtos the labeledGoodStockStatusDtos to set
	 */
	public void setLabeledGoodStockStatusDtos(
			List<LabeledGoodStockStatusDto> labeledGoodStockStatusDtos) {
		this.labeledGoodStockStatusDtos = labeledGoodStockStatusDtos;
	}


}