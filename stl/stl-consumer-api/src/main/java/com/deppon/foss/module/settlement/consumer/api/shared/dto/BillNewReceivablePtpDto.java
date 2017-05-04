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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/api/shared/dto/BillNewReceivablePtpDto.java
 * 
 * FILE NAME        	: BillNewReceivablePtpDto.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2016  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.NewReceivablePtpEntity;

/**
 * 合伙人应收单生成Dto
 * @author foss-Jiang Xun
 * @date 2016-01-08 上午10:36:13
 */
public class BillNewReceivablePtpDto implements Serializable {

	/**
	 * 实体序列号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 应收信息entity（包括应收单和应收费用明细）
	 */
	private List<NewReceivablePtpEntity> newReceivablePtpEntityList;
	
	/**
	 * 用户信息
	 */
	private CurrentInfo currentInfo;
	
	public List<NewReceivablePtpEntity> getNewReceivablePtpEntityList() {
		return newReceivablePtpEntityList;
	}

	public void setNewReceivablePtpEntityList(
			List<NewReceivablePtpEntity> newReceivablePtpEntityList) {
		this.newReceivablePtpEntityList = newReceivablePtpEntityList;
	}

	public CurrentInfo getCurrentInfo() {
		return currentInfo;
	}

	public void setCurrentInfo(CurrentInfo currentInfo) {
		this.currentInfo = currentInfo;
	}

}
