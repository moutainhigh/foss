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
 * FILE PATH        	: foss-webstart/src/main/java/com/deppon/foss/client/IClientAppDownload.java
 * 
 * FILE NAME        	: IClientAppDownload.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.client;

public interface IClientAppDownload {

	public void resetConnectionConfig() throws Exception;
	
	public void updateDownloadServer(String serverurl,String ftpport, String ftpmode, String ftpusername, String ftppassword ) throws Exception;
	
	public void download(String remote, String local) throws Exception;
	
	public boolean connect() throws Exception;
	
	public void disconnect() throws Exception;
	
	public String getRemoteAppHomeRootPath(String inputPath);
}