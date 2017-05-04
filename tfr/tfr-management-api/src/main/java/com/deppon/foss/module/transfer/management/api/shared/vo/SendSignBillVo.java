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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/vo/SendSignBillVo.java
 *  
 *  FILE NAME          :SendSignBillVo.java
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

import com.deppon.foss.module.transfer.management.api.shared.domain.SendSignBillEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.SendSignBillDto;
/**
 * 派送签单Vo
 * @author 099197-foss-liming
 * @date 2012-11-29 上午11:49:47
 */
public class SendSignBillVo  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -716660955360756805L;	
	
	/**
	 * 派送签单集合
	 */
	private List<SendSignBillEntity> sendSignBillList;
	
	/**
	 * 派送签单entity
	 */
	private SendSignBillEntity sendSignBillEntity;
	
	/**
	 * 派送签单dto
	 */
	private SendSignBillDto sendSignBillDto;

	/**
	 * 获取 派送签单集合.
	 *
	 * @return the 派送签单集合
	 */
	public List<SendSignBillEntity> getSendSignBillList() {
		return sendSignBillList;
	}

	/**
	 * 设置 派送签单集合.
	 *
	 * @param sendSignBillList the new 派送签单集合
	 */
	public void setSendSignBillList(List<SendSignBillEntity> sendSignBillList) {
		this.sendSignBillList = sendSignBillList;
	}

	/**
	 * 获取 派送签单entity.
	 *
	 * @return the 派送签单entity
	 */
	public SendSignBillEntity getSendSignBillEntity() {
		return sendSignBillEntity;
	}

	/**
	 * 设置 派送签单entity.
	 *
	 * @param sendSignBillEntity the new 派送签单entity
	 */
	public void setSendSignBillEntity(SendSignBillEntity sendSignBillEntity) {
		this.sendSignBillEntity = sendSignBillEntity;
	}

	/**
	 * 获取 派送签单dto.
	 *
	 * @return the 派送签单dto
	 */
	public SendSignBillDto getSendSignBillDto() {
		return sendSignBillDto;
	}

	/**
	 * 设置 派送签单dto.
	 *
	 * @param sendSignBillDto the new 派送签单dto
	 */
	public void setSendSignBillDto(SendSignBillDto sendSignBillDto) {
		this.sendSignBillDto = sendSignBillDto;
	}

	
	

}