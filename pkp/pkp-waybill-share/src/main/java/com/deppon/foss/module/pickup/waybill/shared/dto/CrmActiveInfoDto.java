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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/ResultDto.java
 * 
 * FILE NAME        	: ResultDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.List;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MarkActivitiesQueryConditionDto;

/**
 * CRM营销活动DTO
 * @创建时间 2014-4-16 下午7:16:56   
 * @创建人： WangQianJin
 */
public class CrmActiveInfoDto implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    //营销活动列表
    private List<MarkActivitiesQueryConditionDto> activeList;

	public List<MarkActivitiesQueryConditionDto> getActiveList() {
		return activeList;
	}

	public void setActiveList(List<MarkActivitiesQueryConditionDto> activeList) {
		this.activeList = activeList;
	}
    
}