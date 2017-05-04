/**
 * Copyright 2016 STL TEAM
 */
/*******************************************************************************
 * Copyright 2016 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-consumer-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/api/shared/dto/BillPayblePtpDto.java
 * 
 * FILE NAME        	: BillPayblePtpDto.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2016  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;

/**
 * 合伙人应付单Dto
 * @author foss-Jiang Xun
 * @date 2016-01-21 下午4:55:00
 */
public class BillPayblePtpDto implements Serializable {

	/**
	 * 实体序列号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 应付单entity
	 */
	private BillPayableEntity billPayableEntity;
	
	/**
	 * 用户信息
	 */
	private CurrentInfo currentInfo;
	
	/**
	 * 应付单明细列表
	 */
	private List<BillPayableDEntity> billPayableDList;

	public BillPayableEntity getBillPayableEntity() {
		return billPayableEntity;
	}

	public void setBillPayableEntity(BillPayableEntity billPayableEntity) {
		this.billPayableEntity = billPayableEntity;
	}

	public CurrentInfo getCurrentInfo() {
		return currentInfo;
	}

	public void setCurrentInfo(CurrentInfo currentInfo) {
		this.currentInfo = currentInfo;
	}

	public List<BillPayableDEntity> getBillPayableDList() {
		return billPayableDList;
	}

	public void setBillPayableDList(List<BillPayableDEntity> billPayableDList) {
		this.billPayableDList = billPayableDList;
	}
	
}
