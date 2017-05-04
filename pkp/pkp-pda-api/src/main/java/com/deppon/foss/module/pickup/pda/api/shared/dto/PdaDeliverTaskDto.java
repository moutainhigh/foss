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
 * PROJECT NAME	: pkp-pda-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pda/api/shared/dto/PdaDeliverTaskDto.java
 * 
 * FILE NAME        	: PdaDeliverTaskDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pda.api.shared.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Pda 查询送货任务DTO
 * @author 097972-foss-dengtingting
 * @date 2012-12-22 下午6:36:01
 */
public class PdaDeliverTaskDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 派送单详细
	 */
	private List<PdaDeliverTaskDetailsDto> deliverTaskDetailsDtos;

	/**
	 * 派送单号
	 */
	private String deliverbillNo;

	/**
	 * Gets the 派送单详细.
	 *
	 * @return the 派送单详细
	 */
	public List<PdaDeliverTaskDetailsDto> getDeliverTaskDetailsDtos() {
		return deliverTaskDetailsDtos;
	}

	/**
	 * Sets the 派送单详细.
	 *
	 * @param deliverTaskDetailsDtos the new 派送单详细
	 */
	public void setDeliverTaskDetailsDtos(List<PdaDeliverTaskDetailsDto> deliverTaskDetailsDtos) {
		this.deliverTaskDetailsDtos = deliverTaskDetailsDtos;
	}

	/**
	 * Gets the 派送单号.
	 *
	 * @return the 派送单号
	 */
	public String getDeliverbillNo() {
		return deliverbillNo;
	}

	/**
	 * Sets the 派送单号.
	 *
	 * @param deliverbillNo the new 派送单号
	 */
	public void setDeliverbillNo(String deliverbillNo) {
		this.deliverbillNo = deliverbillNo;
	}

}