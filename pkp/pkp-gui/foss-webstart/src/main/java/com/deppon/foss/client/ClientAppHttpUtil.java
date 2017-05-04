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
 * FILE PATH        	: foss-webstart/src/main/java/com/deppon/foss/client/ClientAppHttpUtil.java
 * 
 * FILE NAME        	: ClientAppHttpUtil.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ClientAppHttpUtil implements IClientAppDownload {
	
	private static final Log logger = LogFactory.getLog(ClientAppHttpUtil.class);
	private static ClientAppHttpUtil instance;
	private static final int NUM_1024 = 1024 ;
	private ClientAppHttpUtil() {
		
	}
	public static ClientAppHttpUtil getInstance(){
		if(instance==null){
			instance = new ClientAppHttpUtil();
		}
		return instance;
	}
	
	private static String downloadserver = "";
	
	private static  String downloadPort="80";
	@Override
	public void updateDownloadServer(String serverurl,String ftport, String ftpmode, String ftpusername, String ftppassword){
		downloadserver = serverurl;
		downloadPort = ftport;
	}

    public void download2(String remote, String plocal) throws Exception {
        retry++;
        download(remote,plocal);
    }

	public static int retry = 0;
	@Override
	public void download(String remote, String plocal) throws Exception {
		
		if(downloadserver==null || "".equals(downloadserver ) || "null".equals(downloadserver)){
			throw new Exception("下载服务器地址错误，不能进行下载");
		}
		
		String vurl = "http://"+downloadserver+":"+downloadPort+"/"+remote;
		try {
			URL url = new URL(vurl);
			// 打开连接
			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
			logger.info("http 下载文件"+vurl);
			//httpConnection.setRequestProperty("User-Agent", "Internet Explorer");
			httpConnection.setConnectTimeout(0);
			httpConnection.setReadTimeout(0);
			
			InputStream input = httpConnection.getInputStream();
			byte[] b = new byte[NUM_1024];
			// 读取网络文件,写入指定的文件中
			int pos = 0;
			
			File destFile = new File(plocal);
			File dir = destFile.getParentFile();
			if (!dir.exists())
				dir.mkdirs();
			
			FileOutputStream out = new FileOutputStream(plocal);
			while ((pos = input.read(b)) != -1) {
				out.write(b, 0, pos);
			}
			out.flush();
			out.close();
			httpConnection.disconnect();
            retry = 0;
		} catch (Exception e) {
			logger.info("Download file [" + vurl + "] faild",e);
			if(e instanceof ConnectException && retry<2){
				String msg = e.getMessage();
				if(msg.indexOf("Connection timed out")!=-1){
                    download2(remote, plocal);
				}
			}
			else {
				retry = 0;
				throw new Exception("retry Download file "+retry+" times [" + vurl + "] faild", e);
			}
		}

	}
	
	@Override
	public boolean connect() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void resetConnectionConfig() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void disconnect() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args){
		try{
			ClientAppHttpUtil util = ClientAppHttpUtil.getInstance();
			//util.download("http://fosslx.deppon.com/fossgui/apphome/","d:\\foss-exe4j.zip");
			util.updateDownloadServer(null, null, null, null, null);
			util.download("test.txt","d:\\foss-exe4j.zip");
		}catch (Exception e) {
			//to do nothing
		}
	}
	
	@Override
	public String getRemoteAppHomeRootPath(String inputPath) {
		if(inputPath==null){
			return "appHome/";	
		}
		else {
			return inputPath;
		}
	}
}