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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/vo/AbandonedGoodsSearchVo.java
 * 
 * FILE NAME        	: AbandonedGoodsSearchVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveDeliverDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveDeliverQueryDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveDeliverTotalDto;
/**
 * 到达派送VO
 * @author foss-meiying
 * @date 2013-06-24 上午11:24:09
 */
public class ArriveDeliverVo implements Serializable {

	// 序列化版本号
	private static final long serialVersionUID = 1L;
	/**
	 * 到达派送集合
	 */
	private List<ArriveDeliverDto> arriveDeliverDtoList;
	/**
	 * 综合派送查询条件DTO
	 */
	private ArriveDeliverQueryDto arriveDeliverQueryDto;
	/**
	 *  汇总查询出来清单的数据DTO
	 */
	private ArriveDeliverTotalDto arriveDeliverTotalDto;
	public List<ArriveDeliverDto> getArriveDeliverDtoList() {
		return arriveDeliverDtoList;
	}
	public void setArriveDeliverDtoList(List<ArriveDeliverDto> arriveDeliverDtoList) {
		this.arriveDeliverDtoList = arriveDeliverDtoList;
	}
	public ArriveDeliverQueryDto getArriveDeliverQueryDto() {
		return arriveDeliverQueryDto;
	}
	public void setArriveDeliverQueryDto(ArriveDeliverQueryDto arriveDeliverQueryDto) {
		this.arriveDeliverQueryDto = arriveDeliverQueryDto;
	}
	public ArriveDeliverTotalDto getArriveDeliverTotalDto() {
		return arriveDeliverTotalDto;
	}
	public void setArriveDeliverTotalDto(ArriveDeliverTotalDto arriveDeliverTotalDto) {
		this.arriveDeliverTotalDto = arriveDeliverTotalDto;
	}
	
}