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
 *  PROJECT NAME  : tfr-unload-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/dto/LoadAndUnloadStandardDto.java
 *  
 *  FILE NAME          :LoadAndUnloadStandardDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-unload-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.api.shared.dto
 * FILE    NAME: LoadAndUnloadStandardDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.math.BigDecimal;

/**
 * 部门装卸车标准
 * @author 046130-foss-xuduowei
 * @date 2012-12-18 下午9:13:46
 */
public class LoadAndUnloadStandardDto {
	/**
	 * 卸车重量标准
	 */
	private BigDecimal unloadWeightStd;
	/**
	 * 卸车体积标准
	 */
	private BigDecimal unloadVolumeStd;
	
	/**
	 * 获取 卸车重量标准.
	 *
	 * @return the 卸车重量标准
	 */
	public BigDecimal getUnloadWeightStd() {
		return unloadWeightStd;
	}
	
	/**
	 * 设置 卸车重量标准.
	 *
	 * @param unloadWeightStd the new 卸车重量标准
	 */
	public void setUnloadWeightStd(BigDecimal unloadWeightStd) {
		this.unloadWeightStd = unloadWeightStd;
	}
	
	/**
	 * 获取 卸车体积标准.
	 *
	 * @return the 卸车体积标准
	 */
	public BigDecimal getUnloadVolumeStd() {
		return unloadVolumeStd;
	}
	
	/**
	 * 设置 卸车体积标准.
	 *
	 * @param unloadVolumeStd the new 卸车体积标准
	 */
	public void setUnloadVolumeStd(BigDecimal unloadVolumeStd) {
		this.unloadVolumeStd = unloadVolumeStd;
	}
	
	
}