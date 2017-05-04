/*
 * PROJECT NAME: tfr-scheduling-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.shared.util
 * FILE    NAME: UploadFileUtil.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.shared.util;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esafenet.dll.NativeClientInterface;

/**
 * 上传解密文件
 * 
 * @author 096598-foss-zhongyubing
 * @date 2013-4-3 上午10:02:48
 */
public class UploadFileUtil {
	public static final String DEPPON_DLP_KEY = "GCUI0106CUIY0722";
	public static final String FILE_UNLOCK = "/untar_";
	private static final Logger LOGGER = LoggerFactory.getLogger(UploadFileUtil.class);

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
			LOGGER.info("解密工具存在异常:" + e.getStackTrace());
		}
		return tmpFile;
	}
}
