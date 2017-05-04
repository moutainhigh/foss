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
 * FILE PATH        	: foss-webstart/src/main/java/com/deppon/foss/ftp/FossGuiFTPServer.java
 * 
 * FILE NAME        	: FossGuiFTPServer.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.ssl.SslConfigurationFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;

import com.deppon.foss.util.ClassPathResourceUtil;

public class FossGuiFTPServer {

	private static FossGuiFTPServer instance;
	FtpServer server;
	
	private FossGuiFTPServer() throws Exception{ 
		
		initFtpServerConfig(); 
		
	}

	public static FossGuiFTPServer getInstance() throws Exception{
		if(instance==null){
			instance = new FossGuiFTPServer();
		}
		return instance;
	}
	
	private static final int DEFAULT_FTP_PORT = 2121;
	public void initFtpServerConfig() throws Exception {
		ClassPathResourceUtil util = new ClassPathResourceUtil();
    	InputStream in = util.getInputStream("com/deppon/foss/ftp/server.properties");
    	
    	Properties p = new Properties();
    	p.load(in);
    	
    	String ftpKeystoreFile = (String)p.get("ftp.keystore.file");//ftpserver.jks
    	String ftpKeystoreFilePassword = (String)p.get("ftp.keystore.file.password"); //password
    	
    	String ftpListenerType= (String)p.get("ftp.listener.type"); //default 
    	int ftpListenerPort = DEFAULT_FTP_PORT;
		try {
			ftpListenerPort = Integer.parseInt((String) p.get("ftp.listener.port"));
		} catch (Exception e) {
			ftpListenerPort = DEFAULT_FTP_PORT;
		}
    	String ftpUserManagerFile=(String)p.get("ftp.user.manager.file"); 
    	
    	FtpServerFactory serverFactory = new FtpServerFactory();
    	ListenerFactory factory = new ListenerFactory();
    	factory.setPort(ftpListenerPort);

    	SslConfigurationFactory ssl = new SslConfigurationFactory();
    	
    	InputStream keystroein = util.getInputStream("com/deppon/foss/ftp/"+ftpKeystoreFile);
    	ssl.setKeystoreFile(getTmpConfigFile(ftpKeystoreFile,keystroein));
    	ssl.setKeystorePassword(ftpKeystoreFilePassword);
    	factory.setSslConfiguration(ssl.createSslConfiguration());
    	
    	serverFactory.addListener(ftpListenerType, factory.createListener());
    	PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
    	InputStream usermanagein = util.getInputStream("com/deppon/foss/ftp/"+ftpUserManagerFile);
    	userManagerFactory.setFile(getTmpConfigFile(ftpUserManagerFile,usermanagein) );
    	serverFactory.setUserManager(userManagerFactory.createUserManager());

    	server = serverFactory.createServer();
	}
	
	private static final int BUFFER_SIZE = 1024;
	private File getTmpConfigFile (String fname,InputStream in) throws Exception {
		File f = new File(fname);
		if(f.exists()){
			f.delete();
		}
		
		f = new File(fname);
    	FileOutputStream fo = new FileOutputStream(f);
    	byte[] buffer = new byte[BUFFER_SIZE];
    	int pos = 0;
    	while((pos=in.read(buffer))!=-1){
    		fo.write(buffer, 0, pos);
    	}
    	fo.close();
    	return f;
	}
	
	public void start() throws Exception {
		// start the server
    	if(server!=null){
    		server.start();
    	}
	}
	
	public void stop(){
		if(server!=null){
			server.stop();
		}
	}
	
	public void suspend(){
		if(server!=null){
			server.suspend();
		}
	}
	
	public boolean isStopped(){
		if(server!=null){
			return server.isStopped();
		}
		else {
			return false;
		}
	}
	
	public boolean isSuspended(){
		if(server!=null){
			return server.isSuspended();
		}
		else {
			return false;
		}
	}
}