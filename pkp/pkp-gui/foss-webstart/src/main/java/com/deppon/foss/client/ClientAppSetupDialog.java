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
 * FILE PATH        	: foss-webstart/src/main/java/com/deppon/foss/client/ClientAppSetupDialog.java
 * 
 * FILE NAME        	: ClientAppSetupDialog.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class ClientAppSetupDialog extends JDialog {
//	private static final Log log = LogFactory.getLog(ClientAppSetupDialog.class);
	
	private static final int width=400;
	private static final int height=250;

	private static final long serialVersionUID = -5845024483017058566L;

	private static final int TEN = 10;
	private static ClientAppSetupDialog instance;
	private ClientAppSetupDialog() throws Exception {
		initUI();
	}
	
	public static ClientAppSetupDialog getInstance(ClientApp pParent) throws Exception {
		if(instance==null){
			instance = new ClientAppSetupDialog();
		}
//		instance.parent = pParent;
		return instance;
	}
	
	private JTextField txtFtpServer = null;
	private JTextField txtFtpPort = null;
	private JTextField txtFtpUserName = null;
	private JPasswordField txtFtpPassword = null;
	
	private JTextField txtHessianServerPort = null;
	private JTextField txtHessianServicePath = null;
	private JTextField txtHessianServiceHost = null;
	private JTextField txtHessianServiceWaittimeout = null;
	private JTextField txtHessianConnectionWaittimeout = null;
	
	private JTextField txtDownloadServer = null;
	private JRadioButton radHttp = null;
	private JRadioButton radFtp = null;
	private JRadioButton radFtpActive = null;
	private JRadioButton radFtpPassive = null;
	private static final int NUM_5 = 5 ;
	private static final int NUM_3 = 3 ;
	private static final int NUM_7 = 7 ;
	private static final int NUM_9 = 9 ;
	private static final int NUM_13 = 13 ;
	private static final int NUM_15 = 15 ;
	private static final int NUM_10 = 10 ;
	private static final int NUM_11 = 11 ;
	private static final int NUM_17 = 17 ;
	private static final int NUM_19 = 19 ;
	private static final int NUM_21 = 21 ;
	private static final int NUM_4 = 4;
//	private ClientApp parent = null;
	
	private void initparametersetupui(){
		
		JPanel mainpanel = new JPanel(new BorderLayout());
		mainpanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		FormLayout layout = new FormLayout(
                "right:60dlu, 6dlu, 50dlu, 4dlu, center:50dlu", // columns  
                "pref, 3dlu, pref, 3dlu, pref, 3dlu, pref, 3dlu, pref, 3dlu, pref, 3dlu, pref, 3dlu, pref, 3dlu, pref, 3dlu, pref, 3dlu, pref"); // rows  
		
		JPanel inputpanel = new JPanel(layout);
        CellConstraints cc = new CellConstraints();
        //ftp设置
        inputpanel.add(new JLabel(ClientAppMessages.getString(ClientAppConstants.key_ui_setup_lbl_ftp_setup)), cc.xywh(1, 1, NUM_5, 1));
        inputpanel.add(new JLabel(ClientAppMessages.getString(ClientAppConstants.key_ui_setup_lbl_ftp_server)), cc.xy(1, NUM_3));
        JTextField textField = new JTextField();
        txtFtpServer = textField ;
        inputpanel.add(txtFtpServer, cc.xywh(NUM_3, NUM_3, NUM_3, 1));
        txtFtpServer.setEnabled(false);
        
        inputpanel.add(new JLabel(ClientAppMessages.getString(ClientAppConstants.key_ui_setup_lbl_ftp_port)), cc.xy(1, NUM_5));  
        JTextField textField2 = new JTextField();
        txtFtpPort = textField2 ;
        inputpanel.add(txtFtpPort , cc.xy(NUM_3, NUM_5));
        txtFtpPort.setEnabled(false);
        
        inputpanel.add(new JLabel(ClientAppMessages.getString(ClientAppConstants.key_ui_setup_lbl_ftp_username)), cc.xy(1, NUM_7));  
        JTextField textField3 = new JTextField();
        txtFtpUserName = textField3 ;
        inputpanel.add(txtFtpUserName , cc.xy(NUM_3, NUM_7));
        txtFtpUserName.setEnabled(false);
        
        inputpanel.add(new JLabel(ClientAppMessages.getString(ClientAppConstants.key_ui_setup_lbl_ftp_password)), cc.xy(1, NUM_9));  
        JPasswordField passwordField = new JPasswordField();
        txtFtpPassword = passwordField ;
        inputpanel.add(txtFtpPassword , cc.xy(NUM_3, NUM_9));
        txtFtpPassword.setEnabled(false);
        
        inputpanel.add(new JLabel("---------------------------------------------------------------------------------"), cc.xywh(1, NUM_10, NUM_5, 1));
        //hessian配置
        inputpanel.add(new JLabel(ClientAppMessages.getString(ClientAppConstants.key_ui_setup_lbl_hessian_setup)), cc.xywh(1, NUM_11, NUM_5, 1));
        inputpanel.add(new JLabel(ClientAppMessages.getString(ClientAppConstants.key_ui_setup_lbl_hessian_service_path)), cc.xy(1, NUM_13));
        JTextField textField4 = new JTextField();
        txtHessianServicePath = textField4 ;
        inputpanel.add(txtHessianServicePath , cc.xywh(NUM_3, NUM_13, NUM_3, 1));
        txtHessianServicePath.setEnabled(false);
        
        inputpanel.add(new JLabel(ClientAppMessages.getString(ClientAppConstants.key_ui_setup_lbl_hessian_service_host)), cc.xy(1, NUM_15));  
        JTextField textField5 = new JTextField();
        txtHessianServiceHost = textField5 ;
        inputpanel.add(txtHessianServiceHost, cc.xywh(NUM_3, NUM_15, NUM_3, 1));
        txtHessianServiceHost.setEnabled(false);
        
        inputpanel.add(new JLabel(ClientAppMessages.getString(ClientAppConstants.key_ui_setup_lbl_hessian_server_port)), cc.xy(1, NUM_17));  
        JTextField textField6 = new JTextField();
        txtHessianServerPort = textField6 ;
        inputpanel.add(txtHessianServerPort , cc.xy(NUM_3, NUM_17));
        txtHessianServerPort.setEnabled(false);
        
        inputpanel.add(new JLabel(ClientAppMessages.getString(ClientAppConstants.key_ui_setup_lbl_hessian_service_waittimeout)), cc.xy(1, NUM_19));  
        JTextField textField7 = new JTextField();
        txtHessianServiceWaittimeout = textField7 ;
        inputpanel.add(txtHessianServiceWaittimeout , cc.xy(NUM_3, NUM_19));
        txtHessianServiceWaittimeout.setEnabled(false);
        
        inputpanel.add(new JLabel(ClientAppMessages.getString(ClientAppConstants.key_ui_setup_lbl_hessian_connection_waittimeout)), cc.xy(1, NUM_21));  
        JTextField textField8 = new JTextField();
        txtHessianConnectionWaittimeout = textField8 ;
        inputpanel.add(txtHessianConnectionWaittimeout , cc.xy(NUM_3, NUM_21));
        txtHessianConnectionWaittimeout.setEnabled(false);
  
        inputpanel.setBorder(BorderFactory.createEmptyBorder(TEN, TEN, TEN, TEN));
        mainpanel.add(inputpanel,BorderLayout.CENTER);
	}
	
	public void initUI(){
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setTitle(ClientAppMessages.getString(ClientAppConstants.key_ui_setup_title));

		JPanel mainpanel = new JPanel(new BorderLayout());
		mainpanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		FormLayout layout = new FormLayout(
                "right:60dlu, 6dlu, 50dlu, 6dlu, 50dlu, 4dlu ", // columns  
                "pref, 3dlu, pref, 3dlu, pref, 3dlu, pref, 3dlu, pref, 3dlu, pref, 3dlu, pref, 3dlu, pref"); // rows  
		
		JPanel inputpanel = new JPanel(layout);
        CellConstraints cc = new CellConstraints();
        
        inputpanel.add(new JLabel(ClientAppMessages.getString(ClientAppConstants.key_ui_setup_lbl_download_setup)), cc.xywh(2, NUM_3, NUM_4, 1));
        inputpanel.add(new JLabel(ClientAppMessages.getString(ClientAppConstants.key_ui_setup_lbl_download_server)), cc.xy(1, NUM_5));
        inputpanel.add(txtDownloadServer = new JTextField(), cc.xywh(NUM_3, NUM_5, NUM_3 , 1));
        txtDownloadServer.setEnabled(false);
        
        inputpanel.add(new JLabel(ClientAppMessages.getString(ClientAppConstants.key_ui_setup_lbl_download_type)), cc.xy(1, NUM_7));  
        radHttp = new JRadioButton(ClientAppMessages.getString(ClientAppConstants.key_ui_setup_lbl_download_type_http));
        radHttp.addActionListener(new RadioTypeClickActionListner());
        radFtp = new JRadioButton(ClientAppMessages.getString(ClientAppConstants.key_ui_setup_lbl_download_type_ftp));
        radFtp.addActionListener(new RadioTypeClickActionListner());
        ButtonGroup radGrouptype = new ButtonGroup();
        radGrouptype.add(radFtp);
        radGrouptype.add(radHttp);
        inputpanel.add(radHttp, cc.xy(NUM_3, NUM_7));
        inputpanel.add(radFtp, cc.xy(NUM_5, NUM_7));
        radFtp.setEnabled(false);
        radHttp.setEnabled(false);
        
        inputpanel.add(new JLabel(ClientAppMessages.getString(ClientAppConstants.key_ui_setup_lbl_download_ftp_mode)), cc.xy(1, NUM_9));  
        radFtpActive = new JRadioButton(ClientAppMessages.getString(ClientAppConstants.key_ui_setup_lbl_download_ftp_mode_active));
        radFtpPassive = new JRadioButton(ClientAppMessages.getString(ClientAppConstants.key_ui_setup_lbl_download_ftp_mode_passive));
        ButtonGroup radGroupmode = new ButtonGroup();
        radGroupmode.add(radFtpActive);
        radGroupmode.add(radFtpPassive);
        inputpanel.add(radFtpPassive, cc.xy(NUM_3, NUM_9));
        inputpanel.add(radFtpActive, cc.xy(NUM_5, NUM_9));
        radFtpPassive.setEnabled(false);
        radFtpActive.setEnabled(false);
        
        inputpanel.add(new JLabel(ClientAppMessages.getString(ClientAppConstants.key_ui_setup_lbl_ftp_port)), cc.xy(1, NUM_11));  
        JTextField field = new JTextField();
        txtFtpPort = field ;
        inputpanel.add(txtFtpPort , cc.xy(NUM_3, NUM_11));
        txtFtpPort.setEnabled(false);
        
        inputpanel.add(new JLabel(ClientAppMessages.getString(ClientAppConstants.key_ui_setup_lbl_ftp_username)), cc.xy(1, NUM_13));  
        JTextField field2 = new JTextField();
        txtFtpUserName = field2 ;
        inputpanel.add(txtFtpUserName , cc.xy(NUM_3, NUM_13));
        txtFtpUserName.setEnabled(false);
        
        inputpanel.add(new JLabel(ClientAppMessages.getString(ClientAppConstants.key_ui_setup_lbl_ftp_password)), cc.xy(1, NUM_15));  
        JPasswordField passField = new JPasswordField();
        txtFtpPassword = passField ;
        inputpanel.add(txtFtpPassword , cc.xywh(NUM_3, NUM_15, 2, 1));
        txtFtpPassword.setEnabled(false);
        
        mainpanel.add(inputpanel,BorderLayout.CENTER);
 
       
        
        JButton closebtn = new JButton(ClientAppMessages.getString(ClientAppConstants.key_ui_btn_close));
        closebtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hideSetup();
			}
		});
        
        JPanel btnpanel = new JPanel(new FlowLayout());
      //  btnpanel.add(clearbtn);
        btnpanel.add(closebtn);
        
        mainpanel.add(btnpanel, BorderLayout.SOUTH);
		getContentPane().add(mainpanel);
	}
	
	class RadioTypeClickActionListner implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(radFtp.isSelected()){
				txtFtpPort.setEnabled(true);
				txtFtpUserName.setEnabled(true);
				txtFtpPassword.setEnabled(true);
				radFtpActive.setEnabled(true);
				radFtpPassive.setEnabled(true);
			}
			if(radHttp.isSelected()){
				txtFtpPort.setEnabled(false);
				txtFtpUserName.setEnabled(false);
				txtFtpPassword.setEnabled(false);
				radFtpActive.setEnabled(false);
				radFtpPassive.setEnabled(false);
			}
		}
	}
	
	public void resetDefaultSetup(String server, String type, String ftpport,
			String ftpmode, String ftpusername, String ftppassword)
			throws Exception {
		//loadFtpDefaultSetup();
		//loadHessianDefaultSetup();
		if(txtDownloadServer!=null){
			txtDownloadServer.setText(server);
		}
		if(ftpport!=null){
			txtFtpPort.setText(ftpport);
		}
		if(radFtp!=null && ClientAppConstants.key_download_type_ftp.equals(type)){
			radFtp.setSelected(true);
			txtFtpPort.setEnabled(false);
			txtFtpUserName.setEnabled(false);
			txtFtpPassword.setEnabled(false);
			radFtpActive.setEnabled(false);
			radFtpPassive.setEnabled(false);
		}
		if(radHttp!=null && ClientAppConstants.key_download_type_http.equals(type)){
			radHttp.setSelected(true);
			txtFtpPort.setEnabled(false);
			txtFtpUserName.setEnabled(false);
			txtFtpPassword.setEnabled(false);
			radFtpActive.setEnabled(false);
			radFtpPassive.setEnabled(false);
		}
		
		if(radFtpActive!=null && ClientAppConstants.key_ftp_mode_Active.equals(ftpmode)){
			radFtpActive.setSelected(true);
		}
		if(radFtpPassive!=null && ClientAppConstants.key_ftp_mode_Passive.equals(ftpmode)){
			radFtpPassive.setSelected(true);
		}
		
		if(ftpusername!=null){
			txtFtpUserName.setText(ftpusername);
		}
		if(ftppassword!=null){
			txtFtpPassword.setText(ftppassword);
		}
		
	}
	
	
	
	
	
	
	
	public void pop(){
		setSize(new Dimension(width,height));
		double width2 = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height2 = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width2 - width) / 2,
				(int) (height2 - height) / 2);
		
		this.setModal(true);
		setResizable(false);
		this.setVisible(true);
	}
	
	public void hideSetup(){
		this.setVisible(false);
	}
}