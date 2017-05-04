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
 * FILE PATH        	: information/src/main/java/com/deppon/foss/module/information/client/message/ToDoMsgDialog.java
 * 
 * FILE NAME        	: ToDoMsgDialog.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.information.client.message;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;
import com.deppon.foss.util.define.FossConstants;

/**
 * 锁屏窗口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:31:14, </p>
 * @author niujian
 * @date 2012-12-18
 * @since
 * @version
 */
public class OrderUnLockDialog extends JDialog {
	private JButton btnUnlock;
	private JLabel lbContext;
	private static OrderUnLockDialog instance;
	private boolean status=false;//0为解锁，1为锁屏
	
	private static final II18n i18n = I18nManager.getI18n(OrderUnLockDialog.class);
	
	public OrderUnLockDialog() {
		
		
		
	}
	
	
    /**
	 * 获取系统参数
	 * 
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
		
		return waybillHessianRemoting.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP, type, FossConstants.ROOT_ORG_CODE);

	}
	
    public OrderUnLockDialog(Frame owner, boolean modal,long n,final String orgCode) {
    	
     	super(ApplicationContext.getApplication().getWorkbench().getFrame(), true);
     	JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		btnUnlock = new JButton("确定");
		panel.add(btnUnlock);
		JPanel panel1 = new JPanel();
		getContentPane().add(panel1, BorderLayout.CENTER);
		
		lbContext = new JLabel(i18n.get("com.deppon.foss.module.information.client.message.unlock.message", new Object[]{n}));
		lbContext.setFont(new Font("宋体", Font.BOLD, NumberConstants.NUMBER_12));
		panel1.add(lbContext);
	    /**
       	 * 设置对话框不能改变大小
       	 */
        this.setResizable(false);
        /**
       	 * 调用pack()方法封装
       	 */
        pack();
     
        lbContext.setText(i18n.get("com.deppon.foss.module.information.client.message.unlock.message", new Object[]{n}));
   
        
		btnUnlock.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				shutDownPopInfo();
				
			}
		});
		
    }
    
	public JButton getBtnUnlock() {
		return btnUnlock;
	}
	public JLabel getLbContext() {
		return lbContext;
	}
	
	public static OrderUnLockDialog getInstance(Frame owner, boolean modal,long n,String orgCode){
		if(instance==null){
			instance = new OrderUnLockDialog(owner,modal,n,orgCode);
			
		}else {
			instance.refreshToDoMsgList(n);
			
		}
		instance.setStatus(false);
		return instance;
	}


	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * 
	 * 更新待办
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	public void refreshToDoMsgList(long n){	
		lbContext.setText(i18n.get("com.deppon.foss.module.information.client.message.unlock.message", new Object[]{n}));
		
	}
	
	
	
	public void shutDownPopInfo() {
		dispose();
	}

}