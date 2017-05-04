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
 * FILE PATH        	: login/src/main/java/com/deppon/foss/module/login/client/action/LoginProcesser.java
 * 
 * FILE NAME        	: LoginProcesser.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.login.client.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.exceptions.TooManyResultsException;

import com.deppon.foss.base.util.SqlUtil;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.DateTimeSynchronizeService;
import com.deppon.foss.framework.client.commons.util.ImageUtil;
import com.deppon.foss.framework.client.component.networkstatus.NetworkMonitor;
import com.deppon.foss.framework.client.component.networkstatus.NetworkStatus;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.component.remote.IRemoteServer;
import com.deppon.foss.framework.client.component.transport.exception.NetworkNotAllowException;
import com.deppon.foss.framework.client.component.transport.exception.RemoteServiceNotFoundException;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.Definitions;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.boot.client.app.Application;
import com.deppon.foss.module.boot.client.util.FossAppPathUtil;
import com.deppon.foss.module.frameworkimpl.server.util.CryptoUtil;
import com.deppon.foss.module.login.client.Login;
import com.deppon.foss.module.login.client.ui.AppLoginFrame;
import com.deppon.foss.module.login.client.util.BeanToJsonUtil;
import com.deppon.foss.module.login.shared.domain.LoginForGUIInfo;
import com.deppon.foss.module.login.shared.domain.LoginInfo;
import com.deppon.foss.module.login.shared.domain.TokenDto;
import com.deppon.foss.module.login.shared.exception.LoginException;
import com.deppon.foss.module.login.shared.hessian.ILoginHessianRemoting;
import com.deppon.foss.module.pickup.common.client.service.IConfigService;
import com.deppon.foss.module.pickup.common.client.service.SysConfigServiceFactory;
import com.deppon.foss.module.pickup.common.client.utils.BZPartnersJudge;
import com.deppon.foss.module.pickup.common.client.utils.MessageI18nUtil;
import com.deppon.foss.module.pickup.waybill.shared.util.PropertiesUtil;
import com.deppon.foss.util.define.FossConstants;

/**
 * GUI执行登陆功能统一处理类 
 * @author: niujian
 */
public class LoginProcesser {
	
	private II18n i18n = I18nManager.getI18n(LoginProcesser.class);
	private AppLoginFrame loginFrame;
	private LoginType loginType;
	private Login login;
	private ILoginHessianRemoting service;
	private static final Log log = LogFactory.getLog(LoginProcesser.class);
	private int ret;
	//private String applicationRoot;
	private String hostName;
	private String macAddress;
	private String ipAddress;
	
	public LoginProcesser(LoginType loginType, AppLoginFrame loginFrame,
			Login login) {
		//该目录是读取启动器传入参数的目录
		//System.getProperty("org.java.plugin.boot.applicationPlugin");//, configuration.getProperty(PARAM_APPLICATION_PLUGIN));
		/*applicationRoot  = null;
		try{
			applicationRoot = new File(".").getCanonicalPath();
		}catch(Exception e){
			log.error("get Applcation root Exception", e);
		}
		log.info(" applicationRoot : " + applicationRoot);
		
		if(applicationRoot != null){
			String pcInfoFilePath =  applicationRoot+"/../../PCInfo.ini";
			try{
				File pcInfoFile = new File(pcInfoFilePath);
				if(pcInfoFile.exists()){
					readFile(pcInfoFile);	
				}else{
					log.warn(pcInfoFilePath + " not exist ");
				}
			}catch(Exception e){
				log.error("get pcInfoFile", e);
			}
		}
		*/
		this.login = login;
		this.loginType = loginType;
		this.loginFrame = loginFrame;
		service = DefaultRemoteServiceFactory
				.getService(ILoginHessianRemoting.class);
		SqlUtil.loadCache = false;
	}

	/**
	 * @param pcInfoFile
	 * 151020号被去掉
	 */
	private void readFile(File pcInfoFile) {
		
		BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(pcInfoFile));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                log.info("line " + line + ": " + tempString);
                
                if(tempString.contains("=")){
                	String[] array = StringUtils.split(tempString, "=", 2);
                	if(array.length>=2){
                		String name = array[0];
                		String value = array[1];
                		if("HostName".equalsIgnoreCase(name)){
                			hostName = value;
                			log.info(" hostName load from file : " + hostName);
                		}else if("IPAddr".equalsIgnoreCase(name)){
                			ipAddress = value;
                			log.info(" IPAddr load from file : " + ipAddress);
                		}else if("MacAddr".equalsIgnoreCase(name)){
                			macAddress = value;
                			log.info(" MacAddr load from file : " + macAddress);
                		}
                	}
                }
                
                line++;
            }
        } catch (Exception e) {
        	log.error("get pcInfoFile read File", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                	//to do nothing
                }
            }
        }
	}

	private boolean isNetworkOk(){
		try{
			IRemoteServer remoteServer = DefaultRemoteServiceFactory.getInstance().getRemoteServer();
			NetworkMonitor networkMonitor = remoteServer.getTransport().getNetworkMonitor();
			
			if(NetworkStatus.ONLINE == networkMonitor.getStatus()){
				return true;
			}
		}catch(Exception e){
			log.error("[ isNetworkOk error : "+e.toString() + " ]",e);
		}
		return false;
	}
	
	public boolean isOverMaxOfflineDays(LoginInfo loginfo) throws Exception {
		try{
			Date today = new Date();
			UserEntity user = loginfo.getUser();
			Date lastlogindate = user.getLastLogin();
			long diff = Math.abs((today.getTime()-lastlogindate.getTime())/(NumberConstants.NUMBER_24 * NumberConstants.NUMBER_3600 * NumberConstants.NUMBER_1000));
			if(diff>getMaxOfflineDays()){					
				return true;
			}
			return false;
		}catch(Exception e) {
			log.error("[ isOverMaxOfflineDays error : "+e.toString() + " ]",e); 
			throw e;
		}
	}
	
	public int getMaxOfflineDays(){
		IConfigService configService = SysConfigServiceFactory.getService();
		try{
			ConfigurationParamsEntity entity = configService.queryConfig(ConfigurationParamsConstants.MAX_OFFLINE_DAYS_CONFIG_CODE);
			return Integer.parseInt(entity.getConfValue());
		}catch(Exception e){
			log.error("[ getMaxOfflineDays error : "+e.toString() 
					+ ", return default constant max offline days:"+ConfigurationParamsConstants.MAX_OFFLINE_DAYS+"  ]",e); 
			return ConfigurationParamsConstants.MAX_OFFLINE_DAYS;
		}
	}
	
	private void saveLoginInfoToLocalFile(LoginInfo logininfo,String username) throws Exception {
		FileOutputStream out = null;
		try {
			String loginpath = FossAppPathUtil.getAppLocalPathForLogin();
			File loginfolder = new File(loginpath);
			if(!loginfolder.exists()){
				boolean flag = loginfolder.mkdirs();
				if(!flag) {
					log.error("创建文件夹失败");
				}
			}
					
			String loginfilepath = loginpath + File.separator + username;
					
			File loginfile = new File(loginfilepath);
			if (loginfile.exists()) {
				boolean flag = loginfile.delete();
				if(!flag){
					log.error("删除文件失败");
				}
			}
			out = new FileOutputStream(new File(loginfilepath));
			BeanToJsonUtil.beanToJson(out, logininfo);
					
		} catch (Exception e) {
			log.error("[ saveLoginInfoToLocalFile error : "+e.toString() + " ]",e); 
			throw e;
		}finally{
			if(out!=null){
				out.close();
			}
		}
	}

	/**
	 * 
	 * 重本地文件中获取登录对象
	 * @param username
	 * @return
	 * @author niujian
	 * @date 2013-3-21 下午2:01:32
	 */
	private LoginInfo loadLoginInfoFromLocalFile(String username) {
		FileInputStream in = null;
		try {
			String loginpath = FossAppPathUtil.getAppLocalPathForLogin()
					+ File.separator + username;
			File f = new File(loginpath);
			if (f.exists()) {
				in = new FileInputStream(f);
				Object obj = BeanToJsonUtil.jsonToBean(in, LoginInfo.class);
				
				return (LoginInfo) obj;
			}
		} catch (Exception e) {
			log.error("[ loadLoginInfoFromLocalFile error : "+e.toString() + " ]",e); 
		}finally{
			if(in!=null){
				try{
					in.close();
				}catch (Exception e) {
					log.error("[ loadLoginInfoFromLocalFile error : "+e.toString() + " ]",e); 
				}
			}
		}
		return null;
	}
	
	public void doLoginProcess(){
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				//登陆时禁用 用户名、密码 输入框
				loginFrame.getTextUsername().setEnabled(false);
				loginFrame.getTextPassword().setEnabled(false);
				loginFrame.hideLoginButton(); 
			}
		});
		loginFrame.setTipInfo("");
		if (loginType.count > NumberConstants.NUMBER_4) {
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					@Override
					public void run() {
						JOptionPane.showMessageDialog(null, i18n.get("login.out.5time"));
						loginFrame.dispose();
						login.getLoginReturn().set(false);
						login.getLoginOk().set(true);
						System.exit(0);
					}
				});
			} catch (InterruptedException e) {
				log.info("[ SwingUtilities.invokeAndWait:"+e.toString()+" ]", e);
			} catch (InvocationTargetException e) {
				log.info("[ SwingUtilities.invokeAndWait:"+e.toString()+" ]", e);
			}
		}

		String name = loginFrame.getTextUsername().getText().trim();
		String password = new String(loginFrame.getTextPassword().getPassword());
		
		UserEntity user = null;
		LoginInfo loginInfo = null;
		String userStatus = null;

		try {
			
			if (!isNetworkOk()) {//离线
				
				log.info("[ network status is OFFLINE do offline login ]");
				try {
					SwingUtilities.invokeAndWait(new Runnable() {
						@Override
						public void run() {
							//网络离线状态
							ret = JOptionPane.showConfirmDialog(null,
									i18n.get("login.error.network.block.login.offline"),
									"", JOptionPane.OK_CANCEL_OPTION);
						}
					});
				} catch (InterruptedException e) {
					log.info("[ SwingUtilities.invokeAndWait:"+e.toString()+" ]", e);
				} catch (InvocationTargetException e) {
					log.info("[ SwingUtilities.invokeAndWait:"+e.toString()+" ]", e);
				}
				
				if(ret == JOptionPane.OK_OPTION){
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							loginFrame.getLabelTipInfo().setIcon(ImageUtil.getImageIcon(LoginProcesser.class, "login.gif"));
						}
					});
					
					//查找本地最后一次登陆信息，尝试离线登陆
					loginInfo = loadLoginInfoFromLocalFile(name);
					if (loginInfo == null) {
						log.info("[ offline login failure no profile for user:"+name+" ]");
						loginFrame.setTipInfo(i18n.get("login.error.cannot.login.offline.noprofile"));
					} else {
						user = loginInfo.getUser();
						if(isOverMaxOfflineDays(loginInfo)){
							log.info("[ offline login failure user:"+name+" offline use too long time ]");
							Date today = new Date();
							Date lastlogindate = user.getLastLogin();
							long extendsdays = Math.abs((today.getTime()-lastlogindate.getTime())/(NumberConstants.NUMBER_24*NumberConstants.NUMBER_3600*NumberConstants.NUMBER_1000));
							loginFrame.setTipInfo(i18n.get("login.error.offline.use.some.days",extendsdays));
							user = null;
						}else {
							String pwd = CryptoUtil.digestByMD5(password);
							if(!pwd.equals(user.getPassword())){
								log.info("[ offline login failure user:"+name+" input password wrong ]");
								loginFrame.setTipInfo(i18n.get("login.error.offline.login.password.wrong"));
								user = null;
							}else {
								//离线登录成功，关闭所有的远程服务
								DefaultRemoteServiceFactory.destroy();
								loginType = LoginType.OFFLINE_LOGIN;
								userStatus = i18n.get("user.status.offline");
								SessionContext.set("user_loginType", "OFFLINE_LOGIN");
								log.info("[ offline login success user:"+name+" ]");
							}
						}
					}
				}
			}else {
				
				//判断客户端是否需要强制升级
				getTokenAndClientNoSync(0,"");
				log.info("[ network status is ONLINE do online login ]");
				//网络在线状态
				try{
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							loginFrame.getLabelTipInfo().setIcon(ImageUtil.getImageIcon(LoginProcesser.class, "login.gif"));
						}
					});
					
				   if(StringUtil.isEmpty(name)) {
					   throw new LoginException("login.out.name.null");
				   } else if(StringUtil.isEmpty(password)){
					   throw new LoginException("login.out.password.null");
				   }
				   
				   String hostNameExp = getHostName();//获取host名称
				   String macAddressExp = getMacAddress();//获取mac地址
				   String ipAddressExp = getIpAddress();//获取IP地址
				   String clientVersion = PropertiesUtil.getClientVersionKeyValue("clientVersion");
				   LoginForGUIInfo loginForGUIInfo=new LoginForGUIInfo();
				   loginForGUIInfo.setUsername(name);//设置用户名
				   loginForGUIInfo.setPwd(password);//设置密码
				   loginForGUIInfo.setHostName(hostNameExp);//设置host
				   loginForGUIInfo.setMacAddress(macAddressExp);//设置MAC地址
				   loginForGUIInfo.setIpAddress(ipAddressExp);//设置IP地址
				   loginForGUIInfo.setClientVersion(clientVersion);//客户端版本号
				   //取pcinfo文件的内容，
				   loginForGUIInfo.setPc_mac(macAddress);
				   loginForGUIInfo.setPc_ip(ipAddress);
				   loginForGUIInfo.setPc_hostName(hostName);
				   loginInfo = service.userLogin(loginForGUIInfo);
				   
				   user = loginInfo.getUser();
				   Date serverdate = loginInfo.getDate();
				   if(user != null){
					   userStatus = i18n.get("user.status.online");
					   SessionContext.set("user_loginType", "ONLINE_LOGIN");
					   // 登录成功,同步服务器时间
					   if(serverdate!=null){
						   DateTimeSynchronizeService.getInstance().dateTimeInitSynchronize(serverdate);
					   }else{
						   DateTimeSynchronizeService.getInstance().dateTimeInitSynchronize(new Date());
					   }
					   user.setLastLogin(new Date());
					   
					   if(user.getEmployee()!=null){
						   
						   if(user.getEmployee().getEmpCode()==null||"".equals(user.getEmployee().getEmpCode()))
						   {
							   throw new BusinessException("登陆时，用户编码返回为空，请联系管理员，重新编辑用户信息后保存！");
						   }
					   }
					   
					   
					   
					   // 记录登陆成功的Logininfo 到本地文件
					   saveLoginInfoToLocalFile(loginInfo,name);
					   loginType = LoginType.ONLINE_LOGIN;
					   log.info("[ online login success user:"+name+" ]");
					   
					   //记录单点登录Token
					   SessionContext.set("_TOKEN",loginInfo.getToken());
					   
					   SessionContext.set("_TOKEN_UUID", loginInfo.getUuid());
					   
					   //在线登陆以后吗，需要添加定时访问服务器 防止session失效的功能
					   Thread t  = new KeepSesionThread("KeepSesion");
					   Application.getExecutorService().submit(t);
					   
				   }
					
				}catch(Exception t){
					user = null;
					if(t instanceof BusinessException){
						BusinessException logexp = (BusinessException)t;
						String errmsg = MessageI18nUtil.getMessage(logexp, i18n);
						loginFrame.setTipInfo(errmsg);
						log.error("[ online login failure user:"+name+",err_msg:"+errmsg+" ]");
					}else {
						String errorMessage=t.getMessage();
						log.error("[ online login failure width exception:"+errorMessage+", try offline login ]",t);
						String resultErrorMessage=processException(t);
						if(StringUtils.isNotEmpty(resultErrorMessage)){
							errorMessage="["+resultErrorMessage+"]";
						}
					
						int ret = JOptionPane.showConfirmDialog(null,
								i18n.get("login.error.cannot.online.login.offline",new Object[]{errorMessage}),
								"", JOptionPane.OK_CANCEL_OPTION);
						if(ret == JOptionPane.OK_OPTION){
							//查找本地最后一次登陆信息，尝试离线登陆
							loginInfo =  loadLoginInfoFromLocalFile(name);
							if (loginInfo == null) {
								log.info("[ offline login failure no profile for user:"+name+" ]");
								loginFrame.setTipInfo(i18n.get("login.error.cannot.login.offline.noprofile"));
							} else {
								user = loginInfo.getUser();
								if(isOverMaxOfflineDays(loginInfo)){
									log.info("[ offline login failure user:"+name+" offline use too long time ]");
									Date today = new Date();
									Date lastlogindate = user.getLastLogin();
									long extendsdays = Math.abs((today.getTime()-lastlogindate.getTime())/(NumberConstants.NUMBER_24*NumberConstants.NUMBER_3600*NumberConstants.NUMBER_1000));
									loginFrame.setTipInfo(i18n.get("login.error.offline.use.some.days",extendsdays));
									user = null;
								}else {
									String pwd = CryptoUtil.digestByMD5(password);
									if(!pwd.equals(user.getPassword())){
										log.info("[ offline login failure user:"+name+" input password wrong ]");
										loginFrame.setTipInfo(i18n.get("login.error.offline.login.password.wrong"));
										user = null;
									}else {
										
										if (!isNetworkOk()) {
											//离线登录，关闭所有的远程服务
											DefaultRemoteServiceFactory.destroy();
										}
										loginType = LoginType.OFFLINE_LOGIN;
										userStatus = i18n.get("user.status.offline");
										SessionContext.set("user_loginType", "OFFLINE_LOGIN");
										log.info("[ offline login success user:"+name+" ]");
									}
								}
							}
						}
					}
				}
			}
			
			// 登录成功
			if (user != null) {
				if (user.getId() != null && !"".equals(user.getId())) {

					/*
					Set<String> functionCodes = loginInfo.getUserResourcesCodes();
					if (functionCodes != null) {
						SessionContext.setCurrentFunctionCodes(functionCodes);
					}*/
					
					SessionContext.set(SessionContext.KEY_USER, user);
					SessionContext.set(Definitions.KEY_USER, user.getId());
					SessionContext.set("user_status", userStatus);
					SessionContext.set("login_info",loginInfo);
					if(user.getEmployee()!=null && user.getEmployee().getDepartment()!=null ){
						SessionContext.set("FOSS_KEY_CURRENT_DEPTCODE",user.getEmployee().getDepartment().getCode());
					}						
					if(user.getEmployee()!=null ){
						SessionContext.set("FRAMEWORK_KEY_EMPCODE",user.getEmployee().getEmpCode());
					}
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							loginFrame.dispose();
							login.getLoginReturn().set(true);
							login.getLoginOk().set(true);
							login.getLoginSignal().countDown();
						}
					});
					 //初始化合伙人权限
					 BZPartnersJudge.initBZPartnerPrower();
				}
			} else{
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						//未找到用户时
						//启用 用户名、密码 输入框
						loginFrame.getTextUsername().setEnabled(true);
						loginFrame.getTextPassword().setEnabled(true);
						loginFrame.showLoginButton(); 
						loginFrame.getLabelTipInfo().setIcon(null);
					}
				});
			}
		} catch (Exception e1) {
			log.error("[ Loginaction actionPerformed error : "+e1.toString() + " ]",e1); 
			MsgBox.showInfo("登陆异常："+ e1.getMessage());
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					//未找到用户时
					//启用 用户名、密码 输入框
					loginFrame.getTextUsername().setEnabled(true);
					loginFrame.getTextPassword().setEnabled(true);
					loginFrame.showLoginButton(); 
					loginFrame.getLabelTipInfo().setIcon(null);
				}
			});
			return;
		}
		loginType.count++;
	}
	
	
	private String  processException(java.lang.Throwable t){
		String errorMessage="";
		if(t!=null){
			if(t instanceof NullPointerException){
				errorMessage=i18n.get("user.exception.connet");
			}
			if(t instanceof NetworkNotAllowException){
				errorMessage=i18n.get("user.exception.connetWrong");
				return errorMessage;
			}
			
			if(t instanceof RemoteServiceNotFoundException){
				errorMessage=i18n.get("user.exception.remoteSeriviceNotFound");
				return errorMessage;
			}
			
			if(t instanceof TooManyResultsException){
				errorMessage=i18n.get("user.exception.TooManyResultsException");
				return errorMessage;
			}
			if(t instanceof ConnectException){
				errorMessage=i18n.get("user.exception.timeOut");
				return errorMessage;
			}
			if(StringUtils.isEmpty(errorMessage)){
				errorMessage=processException(t.getCause());
				if(StringUtils.isNotEmpty(errorMessage)){
					return errorMessage;
				}
			}
		}
		return null;
	}
	
	private int getTokenAndClientNoSync(int countTime, String token) {
		TokenDto dto = service.keepSession(token);
		
		if(dto==null){
			return countTime;
		}
		
//		String tokenNew = dto.getTokenParam(); 
		String serverVersion = dto.getClientVersion();
		
		String controlByVersion = System.getProperty("controlByVersion");
		
		//String[] array = StringUtils.split(tokenNewAndVersion, " ");
		
//		if(StringUtils.isNotEmpty(tokenNew)){
//			SessionContext.set("_TOKEN", tokenNew);
//		}
		
		if(!FossConstants.NO.equals(controlByVersion) && countTime% NumberConstants.NUMBER_10 == 0){
			countTime = 1;
			String clientVersion  = PropertiesUtil.getClientVersionKeyValue("clientVersion");
			if(serverVersion!=null && !serverVersion.equals(clientVersion)){
				//
				JOptionPane.showMessageDialog(null,i18n.get("login.error.foss.login.mustupdateclient"));
				loginFrame.dispose();
				login.getLoginReturn().set(false);
				login.getLoginOk().set(true);
				System.exit(0);	
			}
		}
		return countTime;
	}
	
	/**
	 * @param countTime
	 * @param token
	 * @return
	 */
	private int getTokenAndClientNo(int countTime, String token) {
		
		String uuid = (String)SessionContext.get("_TOKEN_UUID");
		TokenDto dto = service.keepSessionUuid(token,uuid);
		
		if(dto==null){
			return countTime;
		}
		
		String serverVersion = dto.getClientVersion();		
		String controlByVersion = System.getProperty("controlByVersion");
		//String[] array = StringUtils.split(tokenNewAndVersion, " ");
		
//		if(StringUtils.isNotEmpty(tokenNew)){
//			SessionContext.set("_TOKEN", tokenNew);
//		}
		
		if(!FossConstants.NO.equals(controlByVersion) && countTime%NumberConstants.NUMBER_5==0){
			countTime = 1;
			String clientVersion = PropertiesUtil.getClientVersionKeyValue("clientVersion");
			if(serverVersion!=null && !serverVersion.equals(clientVersion)){
				
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						//
						JOptionPane.showMessageDialog(null,i18n.get("login.error.foss.login.mustupdateclient"));
						loginFrame.dispose();
						login.getLoginReturn().set(false);
						login.getLoginOk().set(true);
						System.exit(0);
					}
				});
			}
		}
		return countTime;
	}
	
	/**
	 * 定时循环线程 15分钟访问一次服务器 防止session超时
	 * @author 026123-foss-lifengteng
	 *
	 */
	class KeepSesionThread extends Thread{
		
		public KeepSesionThread() {
			super();
		}

		public KeepSesionThread(String name) {
			super(name);
		}

		public void run(){

			try{
				int countTime = 0;
	
				//登录后才刷新
				while (SessionContext.getCurrentUser()!=null){
					countTime ++;
					if(log.isDebugEnabled()){
						log.debug("keep session");
					}
					String token = (String)SessionContext.get("_TOKEN");
					countTime = getTokenAndClientNo(countTime, token);
					Thread.sleep(NumberConstants.NUMBER_15 * NumberConstants.NUMBER_60 * NumberConstants.NUMBER_1000l );
				}
				
			}catch(Exception e){
				log.error(e.getMessage(),e);
			}
		
		}

			
	}
	
	/**
	 * 
	 * @Description: 
	 * @author FOSSDP-sz
	 * @date 2013-4-24 上午10:14:39
	 * @return
	 * @version V1.0
	 */
	private String getHostName() {
		/*if(StringUtils.isNotEmpty(hostName)){
			return hostName;
		}*/
		
		InetAddress localHost = null;
		try {
			localHost = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			log.error("get hostname exception",e);
		}
		if(localHost != null){
			return localHost.getHostName();
		}
		return null;
	}
	
	private String getOs() {
		return System.getProperty("os.name").toLowerCase();
	}
	
	private String getUnixMac() {
		String mac = null;     
        BufferedReader bufferedReader = null;     
        Process process = null;     
        try {     
              /**  
               *  Unix下的命令，一般取eth0作为本地主网卡 显示信息中包含有mac地址信息    
               */  
            process = Runtime.getRuntime().exec("ifconfig eth0");   
            bufferedReader = new BufferedReader(new InputStreamReader(process     
                    .getInputStream()));     
            String line = null;     
            int index = -1;     
            while ((line = bufferedReader.readLine()) != null) {     
            	/**  
            	 *  寻找标示字符串[hwaddr]   
            	 */  
            	index = line.toLowerCase().indexOf("hwaddr");    
            	/**  
            	 * 找到了  
            	 */  
            	if (index != -1) {     
            		/**  
            		 *   取出mac地址并去除2边空格    
            		 */  
            		mac = line.substring(index +"hwaddr".length()+ 1).trim();   
            		break;     
            	}     
            }     
        } catch (IOException e) {     
        	log.error("IOException",e);
        } finally {     
            try {     
                if (bufferedReader != null) {     
                    bufferedReader.close();     
                }     
            } catch (IOException e1) {     
              log.error("exception",e1);     
           }     
            bufferedReader = null;     
            process = null;     
        }     
     
        return mac;    
	}
	
	private String getWindowsMac() {
		String macAddr = null;
		try {
			Process process = Runtime.getRuntime().exec("ipconfig /all");
			InputStreamReader ir = new InputStreamReader(process.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			String line;
			while ((line = input.readLine()) != null) {
				if (line.indexOf("Physical Address") > 0) {
					macAddr = line.substring(line.indexOf("-") - 2);
					break;
				}
			}
		} catch (Exception e) {
			log.error("get mac exception",e);
		}
		return macAddr;
	}
	
	private String getWindows7Mac() {
		try {
			String[] macs = getNonVirtualMacAddr();
			if(macs!=null && macs.length>0){
				return macs[0].toUpperCase();
			}
		} catch (Exception e) {
			log.error("exception" , e);
		}
		return null;
	}
	
	
	public static String[] getNonVirtualMacAddr() {
        List<String> addrList = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            while (nis.hasMoreElements()) {
                NetworkInterface ni = nis.nextElement();
                byte[] macBytes = ni.getHardwareAddress();
                if (!ni.isLoopback() && !ni.isVirtual() && !ni.isPointToPoint() && null != macBytes) {
                    addrList.add(toMACString(ni.getHardwareAddress()));
                }
            }
        } catch (SocketException se) {
        	//to do nothing
        }
        String[] array = new String[addrList.size()];
        return addrList.toArray(array);
    }

    
	 static String toMACString(byte[] data) {
	        StringBuilder sb = new StringBuilder(NumberConstants.NUMBER_18);
	        for (byte b : data) {
	            if (sb.length() > 0) {
	                sb.append('-');
	            }
	            sb.append(String.format("%02x", b));
	        }
	        return sb.toString();
	    }

	
	private String getMacAddress() {
		/*if(StringUtils.isNotEmpty(macAddress)){
			return macAddress;
		}*/
		String macAddr = null;
		String os = getOs();
		if(os.indexOf("windows 7") > -1) {
			macAddr = getWindows7Mac();
		}else if(os.indexOf("windows") > -1){
			macAddr = getWindowsMac();
		}else {
			macAddr = getUnixMac();
		}
		if(macAddr!=null){
			macAddr = StringUtils.replace(macAddr, "-", ":");
		}
		return macAddr;
	}
	
	private String getIpAddress() {
		/*if(StringUtils.isNotEmpty(ipAddress)){
			return ipAddress;
		}*/
		
		String ipAddressTmp = null;
		boolean flag = true;
		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements() && flag) {
				NetworkInterface ni = netInterfaces.nextElement();
				Enumeration<InetAddress> address = ni.getInetAddresses();
				while (address.hasMoreElements()) {
					InetAddress inetAddress = (InetAddress) address.nextElement();
					if(inetAddress.isSiteLocalAddress()) {
						ipAddressTmp = inetAddress.getHostAddress();
						flag = false;
						break;
					}
				}
			}
		} catch (SocketException e) {
			log.error("get ip exception",e);
		}
		return ipAddressTmp;
	}
	
	
}