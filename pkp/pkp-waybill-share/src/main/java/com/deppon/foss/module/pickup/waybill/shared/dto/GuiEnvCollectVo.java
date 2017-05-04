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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/GuiEnvCollectVo.java
 * 
 * FILE NAME        	: GuiEnvCollectVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import com.deppon.foss.module.pickup.waybill.shared.domain.GuiEnvCollectEntity;


/**
 * Gui环境采集dto
 * @author 038590-foss-wanghui
 * @date 2013-2-23 下午4:18:45
 */
public class GuiEnvCollectVo {

	/**
	 * GUI环境采集实体
	 */
	private GuiEnvCollectEntity guiEnvCollectEntity;

	public GuiEnvCollectEntity getGuiEnvCollectEntity() {
		return guiEnvCollectEntity;
	}

	public void setGuiEnvCollectEntity(GuiEnvCollectEntity guiEnvCollectEntity) {
		this.guiEnvCollectEntity = guiEnvCollectEntity;
	}
	
	
}