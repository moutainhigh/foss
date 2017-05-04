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
 *  PROJECT NAME  : tfr-common
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/server/utils/FTPUtil.java
 *  
 *  FILE NAME          :FTPUtil.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-commen 
 * PACKAGE NAME: com.deppon.foss.module.transfer.commen.server.util
 * FILE    NAME: UploadDlpFileUtil.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.common.server.utils;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esafenet.dll.NativeClientInterface;

/**
 * @author 038300-foss-pengzhen
 */
public class UploadDlpFileUtil {
	public static final String DEPPON_DLP_KEY = "GCUI0106CUIY0722";
	public static final String FILE_UNLOCK = "/untar_";
	private static final Logger LOGGER = LoggerFactory.getLogger(UploadDlpFileUtil.class);

	public static File decryptFile(File uploadFile) {
		File tmpFile = uploadFile;
		try {
			boolean isEncryptFile = NativeClientInterface.isDynamicDecrypt(uploadFile.getPath());
			if (isEncryptFile) {
				String decryFilePath = uploadFile.getParent() + FILE_UNLOCK + uploadFile.getName();
				// 解密文件
				boolean decryptFile = NativeClientInterface.DecryptFile(uploadFile.getPath(), decryFilePath,
						DEPPON_DLP_KEY);
				if (decryptFile) {
					tmpFile = new File(decryFilePath);
					LOGGER.info("解密成功");
				} else {
					LOGGER.info("解密失败");
				}

			}
		} catch (Exception e) {
			LOGGER.error("解密工具存在异常:" + e.getStackTrace());
		}
		return tmpFile;
	}
}
