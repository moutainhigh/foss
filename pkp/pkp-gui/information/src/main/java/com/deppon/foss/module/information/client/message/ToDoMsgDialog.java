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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 * 定时提醒详细提醒条目弹出窗口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:31:14, </p>
 * @author niujian
 * @date 2012-12-18
 * @since
 * @version
 */
public class ToDoMsgDialog extends JDialog {
	
	private static ToDoMsgDialog instance;
	
	private static final long serialVersionUID = -5937986867765489798L;
	private static final int MAINFORM_WIDTH = 800;
	private static final int MAINFORM_HEIGHT = 600;
	private ToDoMsgListPanel listpanel;
	/**
	 * 
	 * 构建一个待办框
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	private ToDoMsgDialog() {
		
		JPanel mainpanel = new JPanel();
		mainpanel.setLayout(new BorderLayout());
		listpanel = new ToDoMsgListPanel(this);
		mainpanel.add(listpanel,BorderLayout.CENTER);
		
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JButton btnClose = new JButton("关闭");
		btnClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				shutDownPopInfo();
			}
		});
		btnPanel.add(btnClose);
		mainpanel.add(btnPanel,BorderLayout.SOUTH);
		
		getContentPane().add(mainpanel);
	}
	
	public static ToDoMsgDialog getInstance(){
		if(instance==null){
			instance = new ToDoMsgDialog();
				
		}else {
			instance.refreshToDoMsgList();
				
		}
		return instance;
	}
	/**
	 * 
	 * 更新待办
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	public void refreshToDoMsgList(){
		if(listpanel!=null){
			listpanel.forceRefreshToDoMsgListUI();
		}
	}
	//根据sonar(方法名应该以小写字母开头)修改
	/**
	 * 
	 * 泡泡框到代表
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	public void popDialogHasToDoMsg(){
		if(listpanel.countToDoMsg()>0){
			showPopInfo();
		}
	}
	/**
	 * 
	 * show出一个泡泡框
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	public void showPopInfo() {
		setSize(MAINFORM_WIDTH, MAINFORM_HEIGHT);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension window = getSize();
		if (window.height > screen.height) {
			window.height = screen.height;
		}
		if (window.width > screen.width) {
			window.width = screen.width;
		}
		int xCoord = (screen.width / 2 - window.width / 2);
		int yCoord = (screen.height / 2 - window.height / 2);
		setLocation(xCoord, yCoord);
		setModal(true);
		setVisible(true);
	}

	public void shutDownPopInfo() {
		dispose();
	}

}