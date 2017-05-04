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
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/service/GuiEnvCollectService.java
 * 
 * FILE NAME        	: GuiEnvCollectService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.service;

import com.deppon.foss.module.pickup.waybill.api.server.dao.IGuiEnvCollectDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IGuiEnvCollectService;
import com.deppon.foss.module.pickup.waybill.shared.domain.GuiEnvCollectEntity;

/**
 * GUI环境采集service
 * @author 038590-foss-wanghui
 * @date 2013-2-23 下午3:47:08
 */
public class GuiEnvCollectService implements IGuiEnvCollectService {
	
	/**
	 * gui环境信息采集dao
	 */
	private IGuiEnvCollectDao guiEnvCollectDao;
	/**
	 * 上传gui端环境采集信息
	 * @author 038590-foss-wanghui
	 * @date 2013-2-23 下午3:48:52
	 */
	@Override
	public void uploadEnvParam(GuiEnvCollectEntity guiEnvCollectEntity) {
		guiEnvCollectDao.insertSelective(guiEnvCollectEntity);
	}
	
	public void setGuiEnvCollectDao(IGuiEnvCollectDao guiEnvCollectDao) {
		this.guiEnvCollectDao = guiEnvCollectDao;
	}

}