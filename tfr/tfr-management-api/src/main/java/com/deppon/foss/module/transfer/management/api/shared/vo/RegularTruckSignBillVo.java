/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-management-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/vo/RegularTruckSignBillVo.java
 *  
 *  FILE NAME          :RegularTruckSignBillVo.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.management.api.shared.domain.RegularTruckSignBillEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.RegularTruckSignBillDto;



/**
 *  专线对发签单Vo
 * 
 * @author dp-liming
 * @date 2012-12-19 上午11:30:21
 */
public class RegularTruckSignBillVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1687935791314766199L;

	/**
	 * 专线对发签单集合
	 */
	private List<RegularTruckSignBillEntity> regularTruckSignBillList;
	
	/**
	 * 专线对发签单entity
	 */
	private RegularTruckSignBillEntity regularTruckSignBillEntity;
	
	/**
	 * 专线对发签单Dto
	 */
	private RegularTruckSignBillDto	regularTruckSignBillDto;
	
	/**
	 * Id
	 */
	private String id;

	/**
	 * 获取 专线对发签单集合.
	 *
	 * @return the 专线对发签单集合
	 */
	public List<RegularTruckSignBillEntity> getRegularTruckSignBillList() {
		return regularTruckSignBillList;
	}

	/**
	 * 设置 专线对发签单集合.
	 *
	 * @param regularTruckSignBillList the new 专线对发签单集合
	 */
	public void setRegularTruckSignBillList(
			List<RegularTruckSignBillEntity> regularTruckSignBillList) {
		this.regularTruckSignBillList = regularTruckSignBillList;
	}

	/**
	 * 获取 专线对发签单entity.
	 *
	 * @return the 专线对发签单entity
	 */
	public RegularTruckSignBillEntity getRegularTruckSignBillEntity() {
		return regularTruckSignBillEntity;
	}

	/**
	 * 设置 专线对发签单entity.
	 *
	 * @param regularTruckSignBillEntity the new 专线对发签单entity
	 */
	public void setRegularTruckSignBillEntity(
			RegularTruckSignBillEntity regularTruckSignBillEntity) {
		this.regularTruckSignBillEntity = regularTruckSignBillEntity;
	}

	/**
	 * 获取 专线对发签单Dto.
	 *
	 * @return the 专线对发签单Dto
	 */
	public RegularTruckSignBillDto getRegularTruckSignBillDto() {
		return regularTruckSignBillDto;
	}

	/**
	 * 设置 专线对发签单Dto.
	 *
	 * @param regularTruckSignBillDto the new 专线对发签单Dto
	 */
	public void setRegularTruckSignBillDto(
			RegularTruckSignBillDto regularTruckSignBillDto) {
		this.regularTruckSignBillDto = regularTruckSignBillDto;
	}

	/**
	 * 获取 id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置 id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	
	
	
}