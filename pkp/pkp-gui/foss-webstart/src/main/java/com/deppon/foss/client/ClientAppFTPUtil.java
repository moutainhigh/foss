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
 * FILE PATH        	: foss-webstart/src/main/java/com/deppon/foss/client/ClientAppFTPUtil.java
 * 
 * FILE NAME        	: ClientAppFTPUtil.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.client;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ConnectException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.deppon.foss.util.ClassPathResourceUtil;

public class ClientAppFTPUtil implements IClientAppDownload {
	private static final Log logger = LogFactory.getLog(ClientAppFTPUtil.class);

	public static final String FTP_HOME = "/"; 

	public String inputftpaddr = null;
	public String FTP_SERVER = "127.0.0.1";

	private static final int NUM_21 = 21;
	public int FTP_PORT = NUM_21 ;
	public String FTP_USERNAME = "a";
	public String FTP_PASSWORD = "a";
	public String FTP_MODE = ClientAppConstants.key_ftp_mode_Active;

	public static final String FTP_CONF_PATH = "com/deppon/foss/client/default/ftp.properties";
	public static final String FTP_CONF_SERVER = "ftp.server";
	public static final String FTP_CONF_PORT = "ftp.port";
	public static final String FTP_CONF_USERNAME = "ftp.username";
	public static final String FTP_CONF_PASSWORD = "ftp.password";
	public static final String FTP_CONF_MODE = "ftp.mode";

	public FTPClient ftpClient;

	private static final int NUM_300000 = 300000 ;

	private ClientAppFTPUtil() { this.ftpClient = new FTPClient(); }

	static class FTPUtilHolder {
		static ClientAppFTPUtil ftpUtil = new ClientAppFTPUtil();
	}

	public static ClientAppFTPUtil getInstance() {
		return FTPUtilHolder.ftpUtil;
	}
	
	@Override
	public void resetConnectionConfig() throws Exception {
		if(ftpClient!=null && ftpClient.isConnected()){
			disconnect();
		}
		
		logger.info("获取客户端默认的ftp配置");
		try {
			ClassPathResourceUtil util = new ClassPathResourceUtil();
			InputStream in = util.getInputStream(FTP_CONF_PATH);
			Properties prop = new Properties();
			prop.load(in);

			FTP_SERVER = prop.getProperty(FTP_CONF_SERVER, FTP_SERVER);
			FTP_PORT = Integer.valueOf(prop.getProperty(FTP_CONF_PORT));
			FTP_USERNAME = prop.getProperty(FTP_CONF_USERNAME, FTP_USERNAME);
			FTP_PASSWORD = prop.getProperty(FTP_CONF_PASSWORD, FTP_PASSWORD);
			FTP_MODE = prop.getProperty(FTP_CONF_MODE, FTP_MODE);

		} catch (IOException exp) {
			logger.error("获取默认FTP配置出错",exp);
			throw new Exception("加载FTP配置信息错误", exp);
		}
		
		
	}
	
	@Override
	public void updateDownloadServer(String serverurl,String ftpport,String ftpmode, String ftpusername, String ftppassword) throws Exception {
		if(serverurl!=null){
			this.FTP_SERVER = serverurl;
		}
		
		if(ftpmode!=null){
			this.FTP_MODE = ftpmode;
		}
		
		if(ftpusername!=null){
			this.FTP_USERNAME = ftpusername;
		}
		
		if(ftppassword!=null){
			this.FTP_PASSWORD = ftppassword;
		}
		
		try{
			if(ftpport!=null){
				this.FTP_PORT = Integer.parseInt(ftpport);
			}
		}catch (Exception e) {
			logger.error("修正ftp下载配置端口错误", e);
			throw new Exception("修正ftp下载配置端口错误", e);
		}
	}

	/**
	 * 连接到FTP服务器
	 * @param hostname  主机名
	 * @param port      端口
	 * @param username  用户名
	 * @param password  密码
	 * @return 是否连接成功
	 * @throws IOException
	 */
	@Override
	public boolean connect() throws Exception {
		logger.info("连接FTP站点, server:"+FTP_SERVER+", port:"+FTP_PORT);
		StringWriter sw = new StringWriter();
		try{
			// 设置将过程中使用到的命令输出到控制台
			ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(sw)));
			logger.info(sw.getBuffer());
	
			ftpClient.connect(FTP_SERVER, FTP_PORT);
			ftpClient.setControlEncoding("GBK");
	
			ftpClient.setConnectTimeout(NUM_300000);
			ftpClient.setSoTimeout(NUM_300000);
			
			if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				if (ftpClient.login(FTP_USERNAME, FTP_PASSWORD)) {
					return true;
				}
			}
		}catch(Exception e){
			disconnect();
		}
		return false;
	}

    public void download2(String remote, String local, long remoteLastTime) throws Exception {
        retry++;
        download(remote, local, 0 );
    }

	public static int retry = 0;

	private static final int NUM_8096 = 8096 ;
	/**
	 * 从FTP服务器上下载文件
	 * @param remote 远程文件路径
	 * @param local  本地文件路径
	 * @param 服务器文件的时间戳
	 * @throws IOException
	 */
	public void download(String remote, String local, long remoteLastTime)
			throws Exception {
		
		// 设置被动模式(P) 主动模式(A)
		if(ClientAppConstants.key_ftp_mode_Active.equals(FTP_MODE)){
			ftpClient.enterLocalActiveMode();
		}else if(ClientAppConstants.key_ftp_mode_Passive.equals(FTP_MODE)){
			ftpClient.enterLocalPassiveMode();
		}
		
		// 设置以二进制方式传输
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		
		remote = FTP_HOME + remote;
		// 检查远程文件是否存在

		String remoteFile = new String(remote.getBytes("GBK"), "ISO-8859-1");
		/*
		 * FTPFile[] files = ftpClient.listFiles(remoteFile); if (files.length
		 * != 1) { throw new IOException("can not found [" + remote + "]"); }
		 */

		File destFile = new File(local);
		File dir = destFile.getParentFile();
		if (!dir.exists())
			dir.mkdirs();

		InputStream ioi = null;
		OutputStream ioo = null;

		try {
			ioo = new BufferedOutputStream(new FileOutputStream(destFile));
			ioi = ftpClient.retrieveFileStream(remoteFile);
			byte[] abyte = new byte[NUM_8096 ];
			int size = -1;
			
			if(ioi!=null){
				while ((size = ioi.read(abyte)) != -1){
					ioo.write(abyte, 0, size);
				}
				ioo.flush();
				if (ftpClient.completePendingCommand()){
					logger.info("Download file [" + FTP_SERVER+remote + "] success");
				}else{
					// 删除本地文件
					if(destFile.exists()){
						destFile.delete();
					}
				}
				retry = 0;
			}else{
				throw new Exception("Download file [" + FTP_SERVER+remote + "] faild, null file Stream Get. ");
			}
            
		} catch (Exception e) {
			logger.info("Download file retry "+ retry+"s [" + FTP_SERVER+remote + "] faild",e);
			if(e instanceof ConnectException && retry<2){
				String msg = e.getMessage();
				if(msg.indexOf("Connection timed out")!=-1){
					download2(remote, local, 0 );
				}
			}else {
				retry = 0;
				throw new Exception("Download file [" + FTP_SERVER+remote + "] faild", e);
			}
			
		} finally {
			if (null != ioi){
				ioi.close();
			}
			if (null != ioo){
				ioo.close();
			}
		}

		if (remoteLastTime > 0){
			destFile.setLastModified(remoteLastTime);
		}
	}
	
	@Override
	public void download(String remote, String local)
			throws Exception {
		download(remote,local,0);
	}

	/**
	 * 断开与远程服务器的连接
	 * @throws IOException
	 */
	@Override
	public void disconnect() throws Exception  {
		if (null != ftpClient && ftpClient.isConnected()) {
			ftpClient.disconnect();
			logger.info("断开FTP站点连接, server:"+FTP_SERVER+", port:"+FTP_PORT);
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