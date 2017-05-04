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
 * PROJECT NAME	: pkp-gui
 * 
 * FILE PATH        	: foss-webstart/src/main/java/com/deppon/foss/client/ClientAppDownload.java
 * 
 * FILE NAME        	: ClientAppDownload.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.client;

public class ClientAppDownload {

	public static IClientAppDownload getAppDownload(String vtype) throws Exception {
		if(ClientAppConstants.key_download_type_http.equals(vtype)){
			return ClientAppHttpUtil.getInstance();
		}
		else if(ClientAppConstants.key_download_type_ftp.equals(vtype)){
			return ClientAppFTPUtil.getInstance();
		}
		throw new Exception("没有设定客户端程序下载方式");
	}
}