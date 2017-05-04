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
 * FILE PATH        	: login/src/main/java/com/deppon/foss/module/login/client/ui/AppLoginFrame.java
 * 
 * FILE NAME        	: AppLoginFrame.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.login.client.ui;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.util.ImageUtil;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.module.boot.client.util.FossAppPathUtil;
import com.deppon.foss.module.login.shared.domain.LoginInfo;


/**
 * GUI客户端登录窗口界面
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午11:45:15, </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午11:45:15
 * @since
 * @version
 */
@SuppressWarnings("serial")
public class AppLoginFrame extends JFrame {
	private static final Log log = LogFactory.getLog(AppLoginFrame.class);

	public static final int width = 400;
	public static final int height = width * 3 / 4;
	
	private JPanel panelLogo;      // 标题区域
	private JPanel panelInput;     // 输入区域
	private JPanel panelTip;       // 提示区域
	private JPanel panelButton;    // 按钮区域
	
	private JLabel labelUsername = new JLabel(ImageUtil.getImageIcon(this.getClass(), "na.png"));
	private JLabel labelPassword = new JLabel(ImageUtil.getImageIcon(this.getClass(), "pw.png"));
	private JTextField textUsername;
	private JPasswordField textPassword;
	private JLabel labelTipInfo = new JLabel();    // 输入验证及登录进度信息
	private CardLayout layout = new CardLayout();

	private JButton btnOnLineLogin;
	private JButton btnOffLineLogin;
	
	/**
	 * 登录界面面板默认构造器
	 */
	public AppLoginFrame() {
		this.setResizable(false);
		this.setBackground(Color.white);
		
		// 设置窗口大小及位置,长宽比例 4:3
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - width) / 2;
		int y = (screen.height - height) / 2;
		this.setBounds(x, y, width, height);
		
		this.drawContentPanel();
		//制动调整界面内部的大小
		pack();
		log.info("登录窗口初始化完成");
		
		this.addWindowListener(new WindowAdapter() 
		{ 
			public void windowClosing(WindowEvent e) 
			{ 
				System.exit(0); 
			} 
		}); 
		textPassword.grabFocus();
	}
	
	
	
	/**
	 * 设置面板的总体布局，使用网格包布局管理器，
	 * 由上到下分为四个区域: 标题及标志区域, 用户输入区域,
	 * 错误提示区域, 按钮区域
	 */
	private void drawContentPanel() {
		// 设置布局管理器
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		
		// 设置标题及标志区域的布局
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 2;
		constraints.ipady = 0;
		this.add(this.drawLogoPanel(), constraints);
		
		// 设置用户输入区域的布局
		constraints.gridx = 0;
		constraints.gridy = NumberConstants.NUMBER_5;
		constraints.gridwidth = 1;
		constraints.gridheight = 2;
		constraints.ipady = NumberConstants.NUMBER_10;
		this.add(this.drawInputPanel(), constraints);		
	
		// 设置错误提示区域的布局
		constraints.gridx = 0;
		constraints.gridy = NumberConstants.NUMBER_8;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.ipady = NumberConstants.NUMBER_20;
		this.add(this.drawTipPanel(), constraints);		
		
		// 设置按钮区域的布局
		constraints.gridx = 0;
		constraints.gridy = NumberConstants.NUMBER_10;
		constraints.gridwidth = 1;
		constraints.gridheight = 2;
		constraints.ipady = NumberConstants.NUMBER_30;    // ipady 值 要适当的大，用以撑满主面板，不在主面板上下留有间距
		this.add(this.drawButtonPanel(), constraints);
		
		this.setVisible(true);
	}
	
	/**
	 * 创建标题及标志区域
	 * 
	 * @return
	 */
	private JPanel drawLogoPanel() {
		if(panelLogo == null) {
			panelLogo = new JPanel();
		}
		panelLogo.setBackground(new Color(NumberConstants.NUMBER_221, NumberConstants.NUMBER_221, NumberConstants.NUMBER_221)); //zxy 20131017 KDTE-1293
 
		JLabel logoIcon = new JLabel(ImageUtil.getImageIcon(this.getClass(), "monologo.png"));
		JLabel fossIcon = new JLabel(ImageUtil.getImageIcon(this.getClass(), "foss.png"));
		panelLogo.add(logoIcon, BorderLayout.WEST);
		panelLogo.add(fossIcon, BorderLayout.EAST);
		return panelLogo;
	}
	
	/**
	 * 创建输入区域
	 * 
	 * @return
	 */
	private JPanel drawInputPanel() {
		if(panelInput == null) {
			panelInput = new JPanel();
		}
		panelInput.setBackground(new Color(NumberConstants.NUMBER_255, NumberConstants.NUMBER_255, NumberConstants.NUMBER_255)); //zxy 20131017 KDTE-1293
		panelInput.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		// 设置第一行
		constraints.gridy = 0;
		
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.ipadx = NumberConstants.NUMBER_5;
		
		constraints.gridx = 0;
		panelInput.add(labelUsername, constraints);
		constraints.gridx = 2;
		textUsername = new JTextField(NumberConstants.NUMBER_25);
		//有登录过显示上次登录ID
		LoginInfo loginInfo = (LoginInfo)SessionContext.get("login_info");
		if(loginInfo != null){
			textUsername.setText(loginInfo.getUser().getUserName());
		}else{
			String path = FossAppPathUtil.getAppLocalPathForLogin();
			//获取本地用户目录下的所有用户的文件夹
			File dir = new File(path);
			
			if(dir!=null){
				File[] files = dir.listFiles();
				
				if(files==null|| files.length == 0){
					textUsername.setText("000000");
				}else{
					//获取最近修改的文件，即为最近登录人
					int tem = 0;
					for (int i = 0; i < files.length; i++) {
			               if(i < files.length-1){
			            	   if(files[i].lastModified()>files[i+1].lastModified()){
				            	   tem = i;
				               }else{
				            	   tem = i+1;
				               }
			               }
			          }
					textUsername.setText(files[tem].getName());
				}
			}
		}
		panelInput.add(textUsername, constraints);
		
		// 设置第二行, 用于设置上下两行的间距
		constraints.gridy = 2;
		constraints.ipady = NumberConstants.NUMBER_5;
		panelInput.add(new JLabel(), constraints);
		
		// 设置第三行
		constraints.gridx = 0;
		constraints.gridy = NumberConstants.NUMBER_3;
		constraints.ipady = 0;
		panelInput.add(labelPassword, constraints);
		constraints.gridx = 2;
		textPassword = new JPasswordField(NumberConstants.NUMBER_25);
		textPassword.setText("");
		panelInput.add(textPassword, constraints);
		panelInput.setVisible(true);
		return panelInput;
	}
	
	/**
	 * 创建进度及错误提示区域
	 * @return
	 */
	private JPanel drawTipPanel() {
		if(panelTip == null) {
			panelTip = new JPanel();
		}
		panelTip.setBackground(new Color(NumberConstants.NUMBER_255, NumberConstants.NUMBER_255, NumberConstants.NUMBER_255)); //zxy 20131017 KDTE-1293
		panelTip.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.insets = new Insets(NumberConstants.NUMBER_10, NumberConstants.NUMBER_50, NumberConstants.NUMBER_10, NumberConstants.NUMBER_20);
		
		labelTipInfo.setText(" \n \n \n");  // 预定义三个空行
		panelTip.add(labelTipInfo, constraints);
		
		return panelTip;
	}
	
	   /**
		 * 设置进度及错误提示信息内容
		 * @param text 提示信息
		 */
		public void setTipInfo(String text) {		
			if(labelTipInfo != null && text != null) {
				labelTipInfo.setText(text);
				labelTipInfo.setForeground(Color.red);
			}
		}
		 
	
	/**
	 * 创建按钮区域
	 * @return
	 */
	private JPanel drawButtonPanel() {
		if(panelButton == null) {
			panelButton = new JPanel();
		}
        panelButton.setLayout(layout);
		JPanel panelButton2 = new JPanel();
        panelButton2.setBackground(new Color(NumberConstants.NUMBER_221,NumberConstants.NUMBER_221,NumberConstants.NUMBER_221)); //zxy 20131017 KDTE-1293
		panelButton2.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		Icon icon = ImageUtil.getImageIcon(this.getClass(), "btn.png");
		btnOnLineLogin = new JButton(icon);
		btnOnLineLogin.setPreferredSize(new Dimension(icon.getIconWidth(),icon.getIconHeight()));
		btnOnLineLogin.setIcon(icon);
		panelButton2.add(btnOnLineLogin,constraints);
		panelButton.add(panelButton2, "visible");
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(NumberConstants.NUMBER_221,NumberConstants.NUMBER_221,NumberConstants.NUMBER_221)); //zxy 20131017 KDTE-1293
		panelButton.add(panel, "invisible");
        layout.show(panelButton, "visible");
 
		return panelButton;
	}
	
	public JButton getBtnOnLineLogin() {
		return btnOnLineLogin;
	}

	public void setBtnOnLineLogin(JButton btnOnLineLogin) {
		this.btnOnLineLogin = btnOnLineLogin;
	}
	
	public JButton getBtnOffLineLogin() {
		return btnOffLineLogin;
	}

	public void setBtnOffLineLogin(JButton btnOffLineLogin) {
		this.btnOffLineLogin = btnOffLineLogin;
	}
	
	public JLabel getLabelTipInfo() {
		return labelTipInfo;
	}

	public void setLabelTipInfo(JLabel labelTipInfo) {
		this.labelTipInfo = labelTipInfo;
	}
	
	public JTextField getTextUsername() {
		return textUsername;
	}

	public void setTextUsername(JTextField textUsername) {
		this.textUsername = textUsername;
	}

	public JPasswordField getTextPassword() {
		return textPassword;
	}

	public void setTextPassword(JPasswordField textPassword) {
		this.textPassword = textPassword;
	}


	/**
	 * 
	 * 隐藏登陆按钮
	 * @author 102246-foss-shaohongliang
	 * @date 2013-3-18 下午2:29:29
	 */
	public void hideLoginButton() {
		layout.show(panelButton, "invisible");
	}
	
	/**
	 * 
	 * 显示登陆按钮
	 * @author 102246-foss-shaohongliang
	 * @date 2013-3-18 下午2:29:29
	 */
	public void showLoginButton() {
		layout.show(panelButton, "visible");
	}
}