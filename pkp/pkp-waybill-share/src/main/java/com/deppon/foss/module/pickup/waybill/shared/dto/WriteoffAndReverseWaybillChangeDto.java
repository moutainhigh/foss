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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/WriteoffAndReverseWaybillChangeDto.java
 * 
 * FILE NAME        	: WriteoffAndReverseWaybillChangeDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.util.Date;
import java.util.List;

/**
 * 
 * 待核销的更改单信息
 * @author 102246-foss-shaohongliang
 * @date 2012-10-30 下午1:34:28
 */
public class WriteoffAndReverseWaybillChangeDto {

    /**
     *  更改受理开始时间
     */
    private Date operateTimeBegin;
    /**
     *  更改受理结束时间
     */
    private Date operateTimeEnd;
    /**
     *  变更起草部门
     */
    private String darftOrgName;
    /**
     *  运单单号
     */
    private String waybillNumber;
	/**
	 *  更改单List
	 */
    private List<WaybillChangeDto> waybillChangeDtos;
	/**
	 * @return operateTimeBegin : set the property operateTimeBegin.
	 */
	public Date getOperateTimeBegin() {
		return operateTimeBegin;
	}
	/**
	 * @param operateTimeBegin : return the property operateTimeBegin.
	 */
	public void setOperateTimeBegin(Date operateTimeBegin) {
		this.operateTimeBegin = operateTimeBegin;
	}
	/**
	 * @return operateTimeEnd : set the property operateTimeEnd.
	 */
	public Date getOperateTimeEnd() {
		return operateTimeEnd;
	}
	/**
	 * @param operateTimeEnd : return the property operateTimeEnd.
	 */
	public void setOperateTimeEnd(Date operateTimeEnd) {
		this.operateTimeEnd = operateTimeEnd;
	}
	/**
	 * @return darftOrgName : set the property darftOrgName.
	 */
	public String getDarftOrgName() {
		return darftOrgName;
	}
	/**
	 * @param darftOrgName : return the property darftOrgName.
	 */
	public void setDarftOrgName(String darftOrgName) {
		this.darftOrgName = darftOrgName;
	}
	/**
	 * @return waybillNumber : set the property waybillNumber.
	 */
	public String getWaybillNumber() {
		return waybillNumber;
	}
	/**
	 * @param waybillNumber : return the property waybillNumber.
	 */
	public void setWaybillNumber(String waybillNumber) {
		this.waybillNumber = waybillNumber;
	}
	/**
	 * @return waybillChangeDtos : set the property waybillChangeDtos.
	 */
	public List<WaybillChangeDto> getWaybillChangeDtos() {
		return waybillChangeDtos;
	}
	/**
	 * @param waybillChangeDtos : return the property waybillChangeDtos.
	 */
	public void setWaybillChangeDtos(List<WaybillChangeDto> waybillChangeDtos) {
		this.waybillChangeDtos = waybillChangeDtos;
	}
}