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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/vo/NotifyCustomerVo.java
 * 
 * FILE NAME        	: NotifyCustomerVo.java
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

import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyMultibillDto;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;

/**
 * 客户通知VO
 * @author ibm-wangfei
 * @date Oct 15, 2012 2:22:43 PM
 */
public class NotifyCustomerVo implements Serializable {
	private static final long serialVersionUID = 5286228891514984949L;

	/**
	 * 运单查询及页面交互dto
	 */
	private NotifyCustomerConditionDto conditionDto;

	/**
	 * 运单通知列表页面
	 */
	private List<NotifyCustomerDto> dtoList;

	/**
	 * 仓库免费保管天数
	 */
	private String warehouseFreeSafeDataNum;

	/**
	 * 通知信息发送时间范围
	 */
	private String informationReceiveTimeRange;

	/**
	 * 产品列表
	 */
	private List<ProductEntity> productEntitys;

	/**
	 * 多票信息列表
	 */
	private List<NotifyMultibillDto> multibillList;
	
	/**
	 * 获取 多票信息列表dto.
	 *
	 * @return the 多票信息列表dto
	 */
	public List<NotifyMultibillDto> getMultibillList() {
		return multibillList;
	}

	/**
	 * 设置 多票信息列表dto.
	 *
	 * @return the 多票信息列表dto
	 */
	public void setMultibillList(List<NotifyMultibillDto> multibillList) {
		this.multibillList = multibillList;
	}

	/**
	 * 获取 运单查询及页面交互dto.
	 *
	 * @return the 运单查询及页面交互dto
	 */
	public NotifyCustomerConditionDto getConditionDto() {
		return conditionDto;
	}

	/**
	 * 设置 运单查询及页面交互dto.
	 *
	 * @param conditionDto the new 运单查询及页面交互dto
	 */
	public void setConditionDto(NotifyCustomerConditionDto conditionDto) {
		this.conditionDto = conditionDto;
	}

	/**
	 * 获取 运单通知列表页面.
	 *
	 * @return the 运单通知列表页面
	 */
	public List<NotifyCustomerDto> getDtoList() {
		return dtoList;
	}

	/**
	 * 设置 运单通知列表页面.
	 *
	 * @param dtoList the new 运单通知列表页面
	 */
	public void setDtoList(List<NotifyCustomerDto> dtoList) {
		this.dtoList = dtoList;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 获取 仓库免费保管天数.
	 *
	 * @return the 仓库免费保管天数
	 */
	public String getWarehouseFreeSafeDataNum() {
		return warehouseFreeSafeDataNum;
	}

	/**
	 * 设置 仓库免费保管天数.
	 *
	 * @param warehouseFreeSafeDataNum the new 仓库免费保管天数
	 */
	public void setWarehouseFreeSafeDataNum(String warehouseFreeSafeDataNum) {
		this.warehouseFreeSafeDataNum = warehouseFreeSafeDataNum;
	}

	/**
	 * 获取 通知信息发送时间范围.
	 *
	 * @return the 通知信息发送时间范围
	 */
	public String getInformationReceiveTimeRange() {
		return informationReceiveTimeRange;
	}

	/**
	 * 设置 通知信息发送时间范围.
	 *
	 * @param informationReceiveTimeRange the new 通知信息发送时间范围
	 */
	public void setInformationReceiveTimeRange(String informationReceiveTimeRange) {
		this.informationReceiveTimeRange = informationReceiveTimeRange;
	}

	/**
	 * 获取 产品列表.
	 *
	 * @return the 产品列表
	 */
	public List<ProductEntity> getProductEntitys() {
		return productEntitys;
	}

	/**
	 * 设置 产品列表.
	 *
	 * @param productEntitys the new 产品列表
	 */
	public void setProductEntitys(List<ProductEntity> productEntitys) {
		this.productEntitys = productEntitys;
	}

}