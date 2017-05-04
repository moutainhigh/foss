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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/dto/AirWaybillDto.java
 * 
 * FILE NAME        	: AirWaybillDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverHandoverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.VisibleHandBillReturnEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDto;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;

/**
 * 综合查询派送情况DTO
 * 
 * @author 038590-foss-meiying
 * @date 2013-07-01 下午9:10:22
 */
public class DeliverySituationDto implements Serializable {
	private static final long serialVersionUID = 6824773151338819096L;
	/**
	 * 签收情况信息
	 */
	private List<WaybillSignResultEntity> signSituationList;
	/**
	 * 派送情况信息
	 */
	private List<DeliverbillDto> deliverbilldtoList;
	/**
	 * 通知情况信息
	 */
	private List<NotificationEntity> notificationList;
	/**
	 * 交单情况信息
	 */
	private List<DeliverHandoverbillEntity> deliverHandoverbillList;
	/**
	 * 退单情况信息
	 */
	private List<VisibleHandBillReturnEntity> VisibleHandBillReturnList;
	public List<WaybillSignResultEntity> getSignSituationList() {
		return signSituationList;
	}
	public void setSignSituationList(List<WaybillSignResultEntity> signSituationList) {
		this.signSituationList = signSituationList;
	}
	public List<DeliverbillDto> getDeliverbilldtoList() {
		return deliverbilldtoList;
	}
	public void setDeliverbilldtoList(List<DeliverbillDto> deliverbilldtoList) {
		this.deliverbilldtoList = deliverbilldtoList;
	}
	public List<NotificationEntity> getNotificationList() {
		return notificationList;
	}
	public void setNotificationList(List<NotificationEntity> notificationList) {
		this.notificationList = notificationList;
	}
	public List<DeliverHandoverbillEntity> getDeliverHandoverbillList() {
		return deliverHandoverbillList;
	}
	public void setDeliverHandoverbillList(
			List<DeliverHandoverbillEntity> deliverHandoverbillList) {
		this.deliverHandoverbillList = deliverHandoverbillList;
	}
	public List<VisibleHandBillReturnEntity> getVisibleHandBillReturnList() {
		return VisibleHandBillReturnList;
	}
	public void setVisibleHandBillReturnList(
			List<VisibleHandBillReturnEntity> visibleHandBillReturnList) {
		VisibleHandBillReturnList = visibleHandBillReturnList;
	}
	
}