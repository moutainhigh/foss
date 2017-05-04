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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/action/GuiEnvCollectAction.java
 * 
 * FILE NAME        	: GuiEnvCollectAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.action;

import java.io.File;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.file.FileInfo;
import com.deppon.foss.framework.server.components.file.FileManager;
import com.deppon.foss.framework.server.components.security.SecurityNonCheckRequired;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.frameworkimpl.server.interceptor.CookieNonCheckRequired;
import com.deppon.foss.module.pickup.waybill.shared.dto.GuiEnvCollectVo;
import com.deppon.foss.util.UUIDUtils;

/**
 * GUI客户端采集
 * 
 * @author 038590-foss-wanghui
 * @date 2013-2-23 下午2:58:21
 */
public class GuiEnvCollectAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(GuiEnvCollectAction.class);

	private GuiEnvCollectVo guiEnvCollectVo;

	private File file;

	// 文件管理器
	private FileManager fileManager;

	/**
	 * 上传GUI客户端信息
	 * 
	 * @return the string
	 * @author 038590-foss-wanghui
	 * @date 2013-2-25 上午11:16:06
	 */
	@CookieNonCheckRequired
	@SecurityNonCheckRequired
	public String uploadEnvParams() {
		// 设置id
		if (guiEnvCollectVo != null && guiEnvCollectVo.getGuiEnvCollectEntity() != null) {
			LOGGER.info("上传GUI客户端环境采集");
			guiEnvCollectVo.getGuiEnvCollectEntity().setId(UUIDUtils.getUUID());
			guiEnvCollectVo.getGuiEnvCollectEntity().setCreateTime(new Date());
			//guiEnvCollectService.uploadEnvParam(guiEnvCollectVo.getGuiEnvCollectEntity());
			return returnSuccess();
		} else {
			LOGGER.info("上传失败，内容为空。");
			return returnError("pkp.waybill.uploadFailure");
		}
	}

	/**
	 * 上传日志
	 * 
	 * @return the string
	 * @author 038590-foss-wanghui
	 * @date 2013-2-27 上午9:57:41
	 */
	@CookieNonCheckRequired
	@SecurityNonCheckRequired
	public String uploadLog() {
		try {
			FileInfo fileInfo = fileManager.create(file);
			fileInfo.getRelativePath();
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	public GuiEnvCollectVo getGuiEnvCollectVo() {
		return guiEnvCollectVo;
	}

	public void setGuiEnvCollectVo(GuiEnvCollectVo guiEnvCollectVo) {
		this.guiEnvCollectVo = guiEnvCollectVo;
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @param fileManager the fileManager to set
	 */
	public void setFileManager(FileManager fileManager) {
		this.fileManager = fileManager;
	}

}