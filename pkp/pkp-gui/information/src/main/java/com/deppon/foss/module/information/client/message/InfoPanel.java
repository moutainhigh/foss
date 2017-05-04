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
 * FILE PATH        	: information/src/main/java/com/deppon/foss/module/information/client/message/InfoPanel.java
 * 
 * FILE NAME        	: InfoPanel.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.information.client.message;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.java.plugin.Plugin;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.ImageUtil;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.application.IApplicationAware;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.core.workbench.IPluginAware;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.boot.client.app.Application;
import com.deppon.foss.module.boot.client.util.FossAppPathUtil;
import com.deppon.foss.module.pickup.waybill.shared.define.ToDoMsgConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.OrderLockEntity;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IOrderLockHessianRemoting;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;
import com.deppon.foss.util.define.FossConstants;

/**
 * 定时提醒主显示面板
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:Joe,date:2012-10-11 下午12:25:19,
 * </p>
 * 
 * @author Joe
 * @date 2012-10-11 下午12:25:19
 * @since
 * @version
 */
public class InfoPanel extends JPanel implements IPluginAware, IApplicationAware {

	private static final int CONSTANT_ONE_MINUTE = 60*1000;
	private static final long serialVersionUID = 4060558251378456708L;
	private static final Log log = LogFactory.getLog(InfoPanel.class);
	private static final II18n i18n = I18nManager.getI18n(InfoPanel.class);
	/**
	 * 110 size
	 */
//	private static final int HUNDREDTEN = 110;
	/**
	 * 300 size
	 */
//	private static final int THREEHUNDRED = 300;
	
	private IApplication application;
	@SuppressWarnings("unused")
	private Plugin plugin;
	
	private ToDoMsgListPanel toDoMsgListPanel;
	private JPanel setupPanel;
	private JTextField txtRemindMinutes;
	private JCheckBox chkRemindSet;
	private JButton btnOk;

	private static final int msg_refresh_minutes_min = 1;
	private static final int msg_refresh_minutes_max = 10;
	
	
	
	public InfoPanel() {
		//由于是异步打开窗口  所以需要放在swing专用图形线程中，否则界面会出错
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				initUI();
			}});
	}
	/**
	 * 
	 * 初始化UI
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	public void initUI() {
		setLayout(new BorderLayout());
		
		// new to do message list panel 
		toDoMsgListPanel = new ToDoMsgListPanel();
		add(toDoMsgListPanel,BorderLayout.CENTER);
		
		// new setup panel  
		txtRemindMinutes = new JTextField();
		chkRemindSet = new JCheckBox("下次不要提示");
		btnOk = new JButton("确认", ImageUtil.getImageIcon(this.getClass(),"clock16.gif"));
		btnOk.addActionListener(new OkButtonActionListener());
		setRemindSettingByLocal();
		
		setupPanel = new JPanel();
		txtRemindMinutes.setColumns(2);
		setupPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		setupPanel.add(new JLabel("<html><font color=red>提示:</font>通知时间间隔最小"
				+ msg_refresh_minutes_min + "分钟,最大" + msg_refresh_minutes_max
				+ "分钟</html>"));
		setupPanel.add(txtRemindMinutes);
		setupPanel.add(new JLabel("分钟后再通知"));
		setupPanel.add(chkRemindSet);
		setupPanel.add(btnOk);
		add(setupPanel,BorderLayout.SOUTH);
	}
	/**
	 * 
	 * 初始化UI
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	public void setRemindSettingByLocal(){
		Properties properties = new Properties();
		//根据sonar(不良实践 - 方法可能在关闭流时因为异常而失败)修改
		FileInputStream fi =  null;
		try{
			String confFileName = FossAppPathUtil.getRemindSetFileName();
			fi = new FileInputStream(confFileName);
			properties.load(fi);			
		}catch(Exception e){ 
			log.error("setRemindSettingByLocal error load local config file error",e);
		}finally{
			if(fi!=null){
				try{
					fi.close();
				}catch(Exception e){ 
					log.error(e.getMessage());
				}
			}
		}
		
		String minute = properties.getProperty(ToDoMsgConstants.KEY_TODO_MSG_AUTO_REFRESH_MINUTES);
		try{
			int n = Integer.parseInt(minute);
			txtRemindMinutes.setText(String.valueOf(n));
		}catch(Exception e){
			txtRemindMinutes.setText(getToDoMsgRefreshMinutes());
		}
		
		try{
			chkRemindSet.setSelected(!Boolean.valueOf(properties.getProperty(ToDoMsgConstants.KEY_TODO_MSG_NEED_REMIND)));
		}catch(Exception e){
			chkRemindSet.setSelected(Boolean.FALSE);
		}
	}
	/**
	 * 
	 * OK按钮事件
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	class OkButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(saveConfigurationToLocalFile()){
				JOptionPane.showMessageDialog(null, (Object)i18n.get(ToDoMsgConstants.KEY_TODO_MSG_SAVE_CONFIG_SUCCESS));
			}
			else {
				setRemindSettingByLocal();
			}
		}
	}
	/**
	 * 
	 * 保存configuration文件到本地
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	@SuppressWarnings("deprecation")
	private boolean saveConfigurationToLocalFile(){
		try{
			String minute = txtRemindMinutes.getText();
			boolean isRemind = !chkRemindSet.isSelected();
			
			Properties properties = new Properties();
			
			try{
				int intMinute = Integer.parseInt(minute);
				if(intMinute>=msg_refresh_minutes_min && intMinute<=msg_refresh_minutes_max){
					properties.setProperty(ToDoMsgConstants.KEY_TODO_MSG_AUTO_REFRESH_MINUTES, minute);
				}
				else {
					JOptionPane.showMessageDialog(null, (Object)i18n.get(ToDoMsgConstants.KEY_TODO_MSG_SET_CONFIG_ERROR_REFRESH_MINUTES));
					return false;
				}
			}catch(Exception e){
				//_Properties.setProperty(ToDoMsgConstants.KEY_TODO_MSG_AUTO_REFRESH_MINUTES, ""+ ToDoMsgConstants.VALUE_TODO_MSG_AUTO_REFRESH_MINUTES);
				JOptionPane.showMessageDialog(null, (Object)i18n.get(ToDoMsgConstants.KEY_TODO_MSG_SET_CONFIG_ERROR_REFRESH_MINUTES));
				return false;
			}
			
			properties.setProperty(ToDoMsgConstants.KEY_TODO_MSG_NEED_REMIND, (Boolean.valueOf(isRemind)).toString());
			
			File dir = new File(FossAppPathUtil.getAppLocalPath());
			if(!dir.exists()){
				dir.mkdirs();
			}
			
			String confFileName = FossAppPathUtil.getRemindSetFileName();
			FileOutputStream fo = new FileOutputStream(confFileName);
			properties.save(fo, "#foss gui to do message remind setting ");
			fo.close();
			
		}catch(Exception e){
			log.error("saveConfigurationToLocalFile error ",e);
		}
		return true;
	}
	/**
	 * 
	 * 加载configuration文件
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	public Properties loadConfigAsProperties(){
		Properties properties = new Properties();
		//根据sonar(不良实践 - 方法可能在关闭流时因为异常而失败)修改
		FileInputStream fi=null;
		try{
			String confFileName = FossAppPathUtil.getRemindSetFileName();
			fi = new FileInputStream(confFileName);
			properties.load(fi);
			return properties;
		}catch(Exception e){
			log.error(e);
		}finally{
			if(fi!=null){
				try{
					fi.close();
				}catch(Exception e){ 
					log.error(e.getMessage());
				}
			}
		}
		return null;
	}
	
	/**
	 * 从配置文件读取刷新时间间隔
	 * @return
	 */
	public String getToDoMsgRefreshMinutes(){
		Properties properties = loadConfigAsProperties();
		String minute = null;
		if(properties!=null){
			minute = properties.getProperty(ToDoMsgConstants.KEY_TODO_MSG_AUTO_REFRESH_MINUTES);
		}
		if(minute!=null){
			return minute;
		}
		else {
			return ""+ ToDoMsgConstants.VALUE_TODO_MSG_AUTO_REFRESH_MINUTES;	
		}
	}
	/**
	 * 
	 * 初始化UI
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	public boolean isNeedRemind(){
		Properties properties = loadConfigAsProperties();
		if(properties!=null){
			try{
				Boolean needMinute = Boolean.valueOf(properties.getProperty(ToDoMsgConstants.KEY_TODO_MSG_NEED_REMIND));
				return needMinute.booleanValue();
			}catch(Exception e){
				log.error(e);
			}
		}
		return Boolean.TRUE;
	}

	/**
	 * 启动定时刷新代办panel条目数量线程
	 */
	public void startToDoMsgAutoRefreshThread(){
		
		Thread t = new Thread("RefreshToDoMsg"){
			@SuppressWarnings("static-access")
			public void run() {
				try{
					long separate = Long.parseLong(getToDoMsgRefreshMinutes());
					log.debug("[ auto refresh To Do Message list by "+separate+" minutes ]");	
					this.sleep(separate*CONSTANT_ONE_MINUTE);
					//登录后才刷新
					while (SessionContext.getCurrentUser()!=null){					
						//  sonar高危 - 整形乘法的结果转换为long型
						SwingUtilities.invokeLater(new Runnable(){
							public void run() {
								
								if(toDoMsgListPanel!=null){
									toDoMsgListPanel.forceRefreshToDoMsgListUI();
								}
						}});
						this.sleep(separate*CONSTANT_ONE_MINUTE);
					}
				}catch(Exception e){
					//根据sonar(不良实践 - 方法可能忽略异常)修改
					log.error(e.getMessage());
				}
			}
		};
		
		Application.getExecutorService().submit(t);
	}

	
	
	
	
	
	/**
	 * 启动定时刷新跳出代办提醒线程
	 */
	public void startToDoMsgAutoPopThread(){
		Thread t = new Thread("PopToDoMsg"){
			@SuppressWarnings("static-access")
			public void run() {
				try{
					long separate = Long.parseLong(getToDoMsgRefreshMinutes());
					log.debug("[ auto refresh To Do Message list by "+separate+" minutes for pop dialog ]");						
					// sonar高危 - 整形乘法的结果转换为long型
					this.sleep(separate*CONSTANT_ONE_MINUTE);
					while (SessionContext.getCurrentUser()!=null){
						if(isNeedRemind()){
							SwingUtilities.invokeLater(new Runnable(){
								public void run() {
									ToDoMsgDialog dialog = ToDoMsgDialog.getInstance();
									if(!dialog.isVisible()){
										dialog.popDialogHasToDoMsg();
									}
									else {
										dialog.refreshToDoMsgList();
									}
							}});
							// sonar高危 - 整形乘法的结果转换为long型
							this.sleep(separate*CONSTANT_ONE_MINUTE);
						}
					}
				}catch(Exception e){ 
					log.error(e.getMessage());
				}
			}
		};
		Application.getExecutorService().submit(t);
	}
	
	
	/**
	 * 启动超时订单处理锁屏线程
	 */
	public void startOrderLockThread(){
		Thread t = new Thread("OrderLock"){
			@SuppressWarnings({ "static-access"})
			public void run() {

				long lockInit=2;
				int lockRefresh= NumberConstants.NUMBER_180 ;
				String isLock="Y";
				
				
				try{
					 ConfigurationParamsEntity lockInitCF= getConfigurationParamsEntity(ConfigurationParamsConstants.PKP_FOSS_LOCK_INIT);//初始化时间
					 if(lockInitCF!=null&&StringUtils.isNotEmpty(lockInitCF.getConfValue()))
					 {
						 lockInit = Integer.parseInt(lockInitCF.getConfValue());
					 }
					 
					
					}catch(Exception e)
					{
						// do Nothing
					}
				//刷新时间
				
				try{
					 ConfigurationParamsEntity lockRefereshCF= getConfigurationParamsEntity(ConfigurationParamsConstants.PKP_FOSS_LOCK_REFRESH);//刷新时间
			      if(lockRefereshCF!=null&&StringUtils.isNotEmpty(lockRefereshCF.getConfValue()))
					 {
			    	  lockRefresh = Integer.parseInt(lockRefereshCF.getConfValue());
					 }
					 
					
					}catch(Exception e)
					{
						// do Nothing
					}
				
				
				//锁开关
				try{
					 ConfigurationParamsEntity isLockCF= getConfigurationParamsEntity(ConfigurationParamsConstants.PKP_FOSS_IS_LOCK);//锁开关
			         if(isLockCF!=null&&StringUtils.isNotEmpty(isLockCF.getConfValue()))
					 {
			    	  isLock = isLockCF.getConfValue();
					 }
					}catch(Exception e)
					{
						// do Nothing
					}
				 
				
				
					
				try{

					UserEntity userEntity=	(UserEntity) SessionContext.getCurrentUser();
					final String deptCode = userEntity.getEmployee().getOrgCode(); 
					IOrderLockHessianRemoting orderLockHessianRemoting	=DefaultRemoteServiceFactory.getService(IOrderLockHessianRemoting.class);
					
					final	String unifiedCode =orderLockHessianRemoting.queryUnifiedCode(deptCode);
					
					// sonar高危 - 整形乘法的结果转换为long型
					this.sleep(lockInit*NumberConstants.NUMERAL_10000);
					while (SessionContext.getCurrentUser()!=null){						
    					
						
						
    					
    					final String iisLock=isLock;
    				
    					orderLockServer(deptCode, unifiedCode, iisLock);    						    
    					// sonar高危 - 整形乘法的结果转换为long型
    					this.sleep(NumberConstants.NUMBER_1000l* lockRefresh);
    					
						
					}
					
				}catch(Exception e){ 
					log.error(e.getMessage());
				}
			}
		};
		Application.getExecutorService().submit(t);
	}	
	
	//锁屏
    private static OrderLockDialog showOverdueMsg(long n,final String orgCode) {    
		final OrderLockDialog overdueMsgDialog = OrderLockDialog.getInstance(ApplicationContext.getApplication().getWorkbench().getFrame(), true,n,orgCode);
		if(!overdueMsgDialog.isVisible()){
			overdueMsgDialog.setVisible(true);
		}else{
			overdueMsgDialog.refreshToDoMsgList(n);
		}
		/*// 设置pwdDialog对象的大小
		overdueMsgDialog.setSize(THREEHUNDRED, HUNDREDTEN);
		// 将pwdDialog置于屏幕中间
		WindowUtil.center(overdueMsgDialog);*/
		return overdueMsgDialog;
		
	}
	
    /**
	 * 获取系统参数
	 * String orgCode,String config,String model
	 * @param type
	 * @return
	 */
	private ConfigurationParamsEntity getConfigurationParamsEntity(String type) {
		/**
		 * 根据组织的配置参数查询系统参数实体
		 * 
		 *  组织配置参数-接送货模块使用：DictionaryConstants.SYSTEM_CONFIG_PARM__PKP
		 *  异常转弃货JOB扫描天数：FossConstants.ROOT_ORG_CODE
		 */
		IWaybillHessianRemoting waybillHessianRemoting=DefaultRemoteServiceFactory.getService(IWaybillHessianRemoting.class);
		
		return waybillHessianRemoting.queryConfigurationParamsByOrgCode(FossConstants.ROOT_ORG_CODE, type, DictionaryConstants.SYSTEM_CONFIG_PARM__PKP);

	}
    
  //锁屏
    private static OrderUnLockDialog showUnOverdueMsg(long n,String orgCode) {    
    	final OrderUnLockDialog overdueMsgDialog = OrderUnLockDialog.getInstance(ApplicationContext.getApplication().getWorkbench().getFrame(), true,n,orgCode);
		if(!overdueMsgDialog.isVisible()){
			overdueMsgDialog.setVisible(true);
		}else{
			overdueMsgDialog.refreshToDoMsgList(n);
		}
		/*// 设置pwdDialog对象的大小
		overdueMsgDialog.setSize(THREEHUNDRED, HUNDREDTEN);
		// 将pwdDialog置于屏幕中间
		WindowUtil.center(overdueMsgDialog);*/
		return overdueMsgDialog;
		

	}
    
    
    
    
	
	public JPanel pushMessage() {
		// start todomsg auto refresh thread
		startToDoMsgAutoRefreshThread();
		
		startToDoMsgAutoPopThread();
		
		if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())) {
            String isLock="Y";
			//锁开关
			try{
				 ConfigurationParamsEntity isLockCF= getConfigurationParamsEntity(ConfigurationParamsConstants.PKP_FOSS_IS_LOCK);//锁开关
		         if(isLockCF!=null&&StringUtils.isNotEmpty(isLockCF.getConfValue()))
				 {
		    	  isLock = isLockCF.getConfValue();
				 }
				}catch(Exception e)
				{
					// do Nothing
				}
			if(FossConstants.ACTIVE.equals(isLock)){
		          startOrderLockThread();
				
			}
		}
		return this;
	}

	@Override
	public void setApplication(IApplication application) {
		this.application = application;
	}
	public IApplication getApplication(){
		return this.application;
	}

	@Override
	public void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}
	private void orderLockServer(final String deptCode,
			final String unifiedCode, final String iisLock) {
		try{
			SwingUtilities.invokeLater(new Runnable(){
				public void run() {
			if(FossConstants.ACTIVE.equals(iisLock)){
					IOrderLockHessianRemoting orderLockHessianRemoting	=DefaultRemoteServiceFactory.getService(IOrderLockHessianRemoting.class);
					
					OrderLockEntity orderLockEntity =orderLockHessianRemoting.queryOrderLockByDeptCode(unifiedCode);
				
					
					if(orderLockEntity==null||orderLockEntity.getOrderCountOverdue()==0){
						
						
						OrderLockDialog.getInstance(ApplicationContext.getApplication().getWorkbench().getFrame(), true,0,deptCode).shutDownPopInfo();
					
					
				}
				
				
				if(orderLockEntity==null||orderLockEntity.getOrderCountUnoverdue()==0||orderLockEntity.getOrderCountOverdue()>0)
				{
					   OrderUnLockDialog.getInstance(ApplicationContext.getApplication().getWorkbench().getFrame(), true,0,deptCode).shutDownPopInfo();
				}
					
					
					if(orderLockEntity!=null&&orderLockEntity.getOrderCountOverdue()>0)//锁屏
					{							
						  showOverdueMsg(orderLockEntity.getOrderCountOverdue(),deptCode);
						//提醒
					}else if(orderLockEntity!=null&&orderLockEntity.getOrderCountUnoverdue()>0){
						showUnOverdueMsg(orderLockEntity.getOrderCountUnoverdue(),deptCode);
					
					
					}
					
					
					
 		
						}		
			}});
		}catch(Throwable  e){
			// to do nothing
		}
	}

}