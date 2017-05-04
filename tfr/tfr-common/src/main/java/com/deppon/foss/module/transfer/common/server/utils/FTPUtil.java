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
 * PROJECT NAME: tfr-common
 * PACKAGE NAME: com.deppon.foss.module.transfer.common.server.utils
 * FILE    NAME: FTPUtil.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.common.server.utils;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.shared.util.string.StringUtil;

/**
 * FTP 相关操作工具类
 * @author 104306-foss-wangLong
 * @date 2012-12-27 下午2:15:39
 * @see FTPUtil#initialize(String, int, String, String)
 * @see FTPUtil#uoload(String, String, InputStream)
 * 
 * 
 * 
 * 
 */
public class FTPUtil {
	/**
	 * 记录日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FTPUtil.class);
	/**
	 * 缓冲块大小,默认为1M
	 */
	public static final int DEFAULT_BUFFER_SIZE = 1024 * 1024;
	/**
	 * 主机名或地址
	 */
	private final String host;
	/**
	 * 端口
	 */
	private final int port;
	/**
	 * 登录用户名
	 */
	private final String user;
	/**
	 * 登录用户密码
	 */
	private final String passWord;
	
	/**
	 * 获取一个初始化FTPUtil对象
	 * @author 038300-foss-pengzhen
	 * @date 2013-1-16 下午3:26:36
	 */
	public static FTPUtil initialize(String host, int port, String user, String passWord) {
		return new FTPUtil(host, port, user, passWord);
	}
	
	/**
	 * 私有构造器
	 * @author 104306-foss-wangLong
	 * @date 2012-12-27 下午5:08:21
	 */
	private FTPUtil(String host, int port, String user, String passWord) {
		super();
		this.host = host;
		this.port = port;
		this.user = user;
		this.passWord = passWord;
	}

	/**
	 * Connect to the FTP server 连接服务器
	 * @author 104306-foss-wangLong
	 * @date 2012-12-27 下午5:08:21
	 * @return An FTPClient instance
	 * @throws IOException
	 */
	private FTPClient connect() throws IOException {
		//构造ftp客户端
		FTPClient client = new FTPClient();
		//通过地址+端口连接服务器
		client.connect(host, port);
		int reply = client.getReplyCode();
		//判断reply >= 200 && reply < 300 ，如果非以2开头的返回值，说明连接不成功
		if (!FTPReply.isPositiveCompletion(reply)) {
			//记录错误日志
			LOGGER.error("Server - " + host + " refused connection on port - " + port);
			throw new IOException("Server - " + host + " refused connection on port - " + port);
		//登录
		} else if (client.login(user, passWord)) {
			//设置文件传输类型：二进制
			client.setFileType(FTP.BINARY_FILE_TYPE);
			//设置缓冲大小
			client.setBufferSize(DEFAULT_BUFFER_SIZE);
		} else {
			//记录错误日志
			LOGGER.error("Login failed on server - " + host + ", port - " + port + ", user - " + user +", passWord - " + passWord);
			throw new IOException("Login failed on server - " + host + ", port - " + port);
		}
		return client;
	}
	
	/**
	 * 上传操作
	 * @author 038300-foss-pengzhen
	 * @param directory 上传目录
	 * @param fileName 上传文件名
	 * @param inputStream 上传IO流
	 * @date 2013-1-16 下午3:49:59
	 */
	public boolean upload(String directory, String fileName, InputStream inputStream) throws IOException {
		boolean result = false;
		//获取连接
		FTPClient ftpClient = connect();
        try {
        	//每次数据连接之前，client通知server开通一个端口来传输数据
        	ftpClient.enterLocalPassiveMode();
            // make directory , 执行ftp cwd命令
            ftpClient.cwd("/");
            //
            if (directory != null && !"".equals(directory.trim())) {
                String[] pathes = directory.split("/");
                for (String onepath : pathes) {
                	if (StringUtil.isEmpty(onepath)) {
						continue;
					}
                	//将指定目录段用iso-8859-1解码经过UTF-8编码后的字节数组，得到unicode码的字符串
                    onepath = new String(onepath.getBytes("GBK"),"iso-8859-1");        
                    //如果不能切换到该目录，则创建
                    if (!ftpClient.changeWorkingDirectory(onepath)) {
                        ftpClient.makeDirectory(onepath);
                        ftpClient.changeWorkingDirectory(onepath);
                    }
                }
            }
            fileName = new String(fileName.getBytes("GBK"),"iso-8859-1");  
            //保存上传文件
            result = ftpClient.storeFile(fileName, inputStream);
        } catch (IOException e) {
        	LOGGER.info("上传出错 ，错误信息:{}", e.getMessage());
            e.printStackTrace();
        } finally {
        	//关闭ftpClient
        	disconnect(ftpClient);
        }
        return result;
	}
	
	/**
	 * Logout and disconnect the given FTPClient.
	 * @param client
	 * @throws IOException
	 */
	private void disconnect(FTPClient client) throws IOException {
		//判断非空
		if (client != null) {
			//如果没有连接，抛出异常
			if (!client.isConnected()) {
				throw new IOException("Client not connected");
			}
			//登出注销
			boolean logoutSuccess = client.logout();
			client.disconnect();
			if (!logoutSuccess) {
				LOGGER.warn("Logout failed while disconnecting, error code - " + client.getReplyCode());
			}
		}
	}
}