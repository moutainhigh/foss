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
 * PROJECT NAME	: pkp-changing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/vo/WaybillRfcPrintVo.java
 * 
 * FILE NAME        	: WaybillRfcPrintVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.changing.client.vo;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcChangeDetailDto;

/**
 * 
 * 更改单打印VO
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:102246-foss-shaohongliang,date:2012-10-23
 * 上午10:39:41,content:
 * </p>
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-23 上午10:39:41
 * @since
 * @version
 */
public class WaybillRfcPrintVo {

	/**
	 * 更改单VO
	 */
	private WaybillInfoVo waybillInfoVo;

	/**
	 * 变更明细DTO集合
	 */
	private List<WaybillRfcChangeDetailDto> WaybillRfcChangeDetailDtos;

	
	/**
	 * @return the waybillInfoVo
	 */
	public WaybillInfoVo getWaybillInfoVo() {
		return waybillInfoVo;
	}

	
	/**
	 * @param waybillInfoVo the waybillInfoVo to set
	 */
	public void setWaybillInfoVo(WaybillInfoVo waybillInfoVo) {
		this.waybillInfoVo = waybillInfoVo;
	}

	
	/**
	 * @return the waybillRfcChangeDetailDtos
	 */
	public List<WaybillRfcChangeDetailDto> getWaybillRfcChangeDetailDtos() {
		return WaybillRfcChangeDetailDtos;
	}

	
	/**
	 * @param waybillRfcChangeDetailDtos the waybillRfcChangeDetailDtos to set
	 */
	public void setWaybillRfcChangeDetailDtos(
			List<WaybillRfcChangeDetailDto> waybillRfcChangeDetailDtos) {
		WaybillRfcChangeDetailDtos = waybillRfcChangeDetailDtos;
	}


}