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
 * FILE PATH        	: foss-webstart/src/main/java/com/deppon/foss/webstart/FTPUtil.java
 * 
 * FILE NAME        	: FTPUtil.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.webstart;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FTPUtil {

	// opt/apache-tomcat-6.0.29/webapps/webstart/";
	public static final String FTP_HOME = "/";

	private static final long NUM_1000 = 1000 ; 

	public String FTP_SERVER = "127.0.0.1";

	private static final int NUM_21 = 21 ;
	public int FTP_PORT = NUM_21 ;
	public String FTP_USERNAME = "a";
	public String FTP_PASSWORD = "a";

	public static final String FTP_CONF_PATH = "config/ftp.properties";
	public static final String FTP_CONF_SERVER = "server";
	public static final String FTP_CONF_PORT = "port";
	public static final String FTP_CONF_USERNAME = "username";
	public static final String FTP_CONF_PASSWORD = "password";

	public FTPClient ftpClient;

	private static final int NUM_8096 = 8096;

	private static final int NUM_300000 = 300000 ;

	private FTPUtil() {
		this.ftpClient = new FTPClient();

		// 设置将过程中使用到的命令输出到控制台
		// this.ftpClient.addProtocolCommandListener(new PrintCommandListener(
		// new PrintWriter(System.out)));
		InputStream in = null;
		try {
			in = getClass().getResourceAsStream(FTP_CONF_PATH);
			Properties prop = new Properties();
			prop.load(in);

			FTP_SERVER = prop.getProperty(FTP_CONF_SERVER, FTP_SERVER);
			FTP_PORT = Integer.valueOf(prop.getProperty(FTP_CONF_PORT));
			FTP_USERNAME = prop.getProperty(FTP_CONF_USERNAME, FTP_USERNAME);
			FTP_PASSWORD = prop.getProperty(FTP_CONF_PASSWORD, FTP_PASSWORD);

		} catch (IOException e) {
			throw new RuntimeException("加载FTP配置信息错误", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	static class FTPUtilHolder {
		static FTPUtil ftpUtil = new FTPUtil();
	}

	public static FTPUtil getInstance() {
		return FTPUtilHolder.ftpUtil;
		
	}

	/** */
	/**
	 * 连接到FTP服务器
	 * 
	 * @param hostname
	 *            主机名
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return 是否连接成功
	 * @throws IOException
	 */
	public boolean connect() throws IOException {
		ftpClient.connect(FTP_SERVER, FTP_PORT);
		ftpClient.setControlEncoding("GBK");

		ftpClient.setConnectTimeout(NUM_300000);
		ftpClient.setSoTimeout(NUM_300000);

		if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
			if (ftpClient.login(FTP_USERNAME, FTP_PASSWORD)) {
				return true;
			}
		}

		disconnect();
		return false;
	}

	/** */
	/**
	 * 从FTP服务器上下载文件
	 * 
	 * @param remote
	 *            远程文件路径
	 * @param local
	 *            本地文件路径
	 * @param 服务器文件的时间戳
	 * @throws IOException
	 */
	public void download(String remote, String local, long remoteLastTime)
			throws Exception {
		
		// 设置被动模式
		ftpClient.enterLocalPassiveMode();
		// ftpClient.enterRemotePassiveMode();
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
			byte[] abyte = new byte[NUM_8096];
			int size = -1;
			
			if(ioi!=null){
				while ((size = ioi.read(abyte)) != -1)
					ioo.write(abyte, 0, size);
			
				if (ftpClient.completePendingCommand())
					System.out.println("Download file [" + remote + "] success");
				else{
					// 删除本地文件
					if(destFile.exists())
						destFile.delete();
					
					throw new IOException("Download file [" + remote + "] faild");
				}
			
			}else{
				throw new IOException("Download file [" + remote + "] faild");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;

		} finally {
			if (null != ioi)
				ioi.close();
			if (null != ioo)
				ioo.close();
		}

		if (remoteLastTime > 0)
			destFile.setLastModified(remoteLastTime);

	}

	/** */
	/**
	 * 断开与远程服务器的连接
	 * 
	 * @throws IOException
	 */
	public void disconnect() throws IOException {
		if (null != ftpClient && ftpClient.isConnected()) {
			ftpClient.disconnect();
		}
	}

	public static void downtest() throws Exception {
		try {

			// com.ibm.icu_4.0.1.v20090822.jar 5M
			// org.eclipse.swt.win32.win32.x86_3.5.2.v3557f.jar 2M
			// org.eclipse.ui.workbench_3.5.2.M20100113-0800.jar 3M
			// com.ibm.gbs.ai.portal.app.client.common_1.0.0.jar 1M

			FTPUtil.getInstance().connect();

			long t = System.currentTimeMillis();
			FTPUtil
					.getInstance()
					.download(
							"appHome/conf/hessianConfig.ini",
							"c:/hessianConfig.ini",
							0L);
			System.out.println("耗时:" + (System.currentTimeMillis() - t) / NUM_1000
					+ "秒");

			FTPUtil.getInstance().disconnect();

		} catch (IOException e) {
			System.out.println("连接FTP出错：" + e.getMessage());
		}
	}

	public static void main(String[] args) throws Exception {
		downtest();
	}
}