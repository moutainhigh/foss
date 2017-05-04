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
 *  
 *  PROJECT NAME  : tfr-partialline-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 落地配外发管理
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/api/shared/dto/LdpExternalBillTrackDto.java
 * 
 *  FILE NAME     :LdpExternalBillTrackDto.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.partialline.api.shared.dto;

import java.util.Date;

import com.deppon.foss.module.transfer.partialline.api.shared.domain.LdpExternalBillTrackEntity;

/**
 * 落地配扩展对象，用于查询返回或者查询参数
 * 
 * @author liuzhaowei
 * @date 2013-07-29 上午9:18:36
 */
public class LdpExternalBillTrackDto extends LdpExternalBillTrackEntity{

	private static final long serialVersionUID = -5368891834441086975L;

	/** 查询起始时间 */
	private Date startTime;
	/** 查询截止时间 */
	private Date endTime;
	/**有效状态*/
	private String active;
	
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
}