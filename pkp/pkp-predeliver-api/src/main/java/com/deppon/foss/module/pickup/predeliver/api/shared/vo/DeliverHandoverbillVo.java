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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/vo/DeliverbillVo.java
 * 
 * FILE NAME        	: DeliverbillVo.java
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

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverHandoverbillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverHandoverbillQueryDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PreDeliverHandoverbillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PreDeliverHandoverbillQueryDto;


/**
 * 派送交单VO.
 * @author 159231 meiying
 * 2015-4-20  下午5:07:45
 */
public class DeliverHandoverbillVo implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7293716261043287112L;
	/**
	 * 网上支付未支付完运单
	 */
	private List<String> notPayByOLWaybillNos;
	/**
	 * 待派送交单查询DTO
	 */
	private PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto;
	/**
	 * 待派送交单查询结果DTOs
	 */
	private List<PreDeliverHandoverbillDto>  preDeliverHandoverbillDtos;
	private PreDeliverHandoverbillDto preDeliverHandoverbillDto;
	/**
	 * 派送交单查询DTO
	 */
	private DeliverHandoverbillQueryDto deliverHandoverbillQueryDto;
	/**
	 * 通知信息
	 */
	private NotificationEntity notificationEntity;
	/**
	 * 已派送交单查询结果DTOs
	 */
	private List<DeliverHandoverbillDto>  deliverHandoverbillDtos;
	/**
	 * 获取preDeliverHandoverbillQueryDto  
	 * @return preDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto
	 */
	public PreDeliverHandoverbillQueryDto getPreDeliverHandoverbillQueryDto() {
		return preDeliverHandoverbillQueryDto;
	}
	/**
	 * 设置preDeliverHandoverbillQueryDto  
	 * @param preDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto 
	 */
	public void setPreDeliverHandoverbillQueryDto(
			PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto) {
		this.preDeliverHandoverbillQueryDto = preDeliverHandoverbillQueryDto;
	}
	/**
	 * 获取preDeliverHandoverbillDtos  
	 * @return preDeliverHandoverbillDtos preDeliverHandoverbillDtos
	 */
	public List<PreDeliverHandoverbillDto> getPreDeliverHandoverbillDtos() {
		return preDeliverHandoverbillDtos;
	}
	/**
	 * 设置preDeliverHandoverbillDtos  
	 * @param preDeliverHandoverbillDtos preDeliverHandoverbillDtos 
	 */
	public void setPreDeliverHandoverbillDtos(
			List<PreDeliverHandoverbillDto> preDeliverHandoverbillDtos) {
		this.preDeliverHandoverbillDtos = preDeliverHandoverbillDtos;
	}
	/**
	 * 获取deliverHandoverbillQueryDto  
	 * @return deliverHandoverbillQueryDto deliverHandoverbillQueryDto
	 */
	public DeliverHandoverbillQueryDto getDeliverHandoverbillQueryDto() {
		return deliverHandoverbillQueryDto;
	}
	/**
	 * 设置deliverHandoverbillQueryDto  
	 * @param deliverHandoverbillQueryDto deliverHandoverbillQueryDto 
	 */
	public void setDeliverHandoverbillQueryDto(
			DeliverHandoverbillQueryDto deliverHandoverbillQueryDto) {
		this.deliverHandoverbillQueryDto = deliverHandoverbillQueryDto;
	}
	/**
	 * 获取deliverHandoverbillDtos  
	 * @return deliverHandoverbillDtos deliverHandoverbillDtos
	 */
	public List<DeliverHandoverbillDto> getDeliverHandoverbillDtos() {
		return deliverHandoverbillDtos;
	}
	/**
	 * 设置deliverHandoverbillDtos  
	 * @param deliverHandoverbillDtos deliverHandoverbillDtos 
	 */
	public void setDeliverHandoverbillDtos(
			List<DeliverHandoverbillDto> deliverHandoverbillDtos) {
		this.deliverHandoverbillDtos = deliverHandoverbillDtos;
	}
	/**
	 * 获取notificationEntity  
	 * @return notificationEntity notificationEntity
	 */
	public NotificationEntity getNotificationEntity() {
		return notificationEntity;
	}
	/**
	 * 设置notificationEntity  
	 * @param notificationEntity notificationEntity 
	 */
	public void setNotificationEntity(NotificationEntity notificationEntity) {
		this.notificationEntity = notificationEntity;
	}
	/**
	 * 获取preDeliverHandoverbillDto  
	 * @return preDeliverHandoverbillDto preDeliverHandoverbillDto
	 */
	public PreDeliverHandoverbillDto getPreDeliverHandoverbillDto() {
		return preDeliverHandoverbillDto;
	}
	/**
	 * 设置preDeliverHandoverbillDto  
	 * @param preDeliverHandoverbillDto preDeliverHandoverbillDto 
	 */
	public void setPreDeliverHandoverbillDto(
			PreDeliverHandoverbillDto preDeliverHandoverbillDto) {
		this.preDeliverHandoverbillDto = preDeliverHandoverbillDto;
	}
	/**
	 * 获取notPayByOLWaybillNos  
	 * @return notPayByOLWaybillNos notPayByOLWaybillNos
	 */
	public List<String> getNotPayByOLWaybillNos() {
		return notPayByOLWaybillNos;
	}
	/**
	 * 设置notPayByOLWaybillNos  
	 * @param notPayByOLWaybillNos notPayByOLWaybillNos 
	 */
	public void setNotPayByOLWaybillNos(List<String> notPayByOLWaybillNos) {
		this.notPayByOLWaybillNos = notPayByOLWaybillNos;
	}
	
}