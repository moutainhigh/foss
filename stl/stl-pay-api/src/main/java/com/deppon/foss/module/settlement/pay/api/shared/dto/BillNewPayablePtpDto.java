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
 * PROJECT NAME	: stl-pay-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/pay/api/shared/dto/BillNewPayablePtpDto.java
 * 
 * FILE NAME        	: BillNewPayablePtpDto.java
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
import com.deppon.foss.module.settlement.pay.api.shared.domain.NewBillPayablePtpEntity;

/**
 * 合伙人应付单生成Dto
 * @author foss-Jiang Xun
 * @date 2016-01-23 下午05:05:00
 */
public class BillNewPayablePtpDto implements Serializable {

	/**
	 * 实体序列号
	 */
	private static final long serialVersionUID = -2881469360848720512L;
	
	/**
	 * 应付信息entity（包括应付单和应付费用明细）
	 */
	private List<NewBillPayablePtpEntity> newPayablePtpEntityList;
	
	/**
	 * 用户信息
	 */
	private CurrentInfo currentInfo;

	public List<NewBillPayablePtpEntity> getNewPayablePtpEntityList() {
		return newPayablePtpEntityList;
	}

	public void setNewPayablePtpEntityList(
			List<NewBillPayablePtpEntity> newPayablePtpEntityList) {
		this.newPayablePtpEntityList = newPayablePtpEntityList;
	}

	public CurrentInfo getCurrentInfo() {
		return currentInfo;
	}

	public void setCurrentInfo(CurrentInfo currentInfo) {
		this.currentInfo = currentInfo;
	}
	

}
