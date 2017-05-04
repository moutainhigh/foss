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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/WaybillFRcQueryByWaybillNosDto.java
 * 
 * FILE NAME        	: WaybillFRcQueryByWaybillNosDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.util.List;

/**
 * 通过运单号（或者List）来查询未受理更改单
 * 
 * @author 026113-foss-linwensheng
 * 
 */
public class WaybillFRcQueryByWaybillNosDto {
	// 运单号集合
	List<String> waybillNos;

	// 更改单状态:待审核
	String preAudit;
	//更改单状态
	String preAccecpt;
	public List<String> getWaybillNos() {
		return waybillNos;
	}
	public void setWaybillNos(List<String> waybillNos) {
		this.waybillNos = waybillNos;
	}
	public String getPreAudit() {
		return preAudit;
	}
	public void setPreAudit(String preAudit) {
		this.preAudit = preAudit;
	}
	public String getPreAccecpt() {
		return preAccecpt;
	}
	public void setPreAccecpt(String preAccecpt) {
		this.preAccecpt = preAccecpt;
	}
	
	
	
}