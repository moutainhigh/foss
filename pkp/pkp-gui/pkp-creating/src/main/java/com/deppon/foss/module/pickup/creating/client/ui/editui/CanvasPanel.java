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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/ui/editui/CanvasPanel.java
 * 
 * FILE NAME        	: CanvasPanel.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.creating.client.ui.editui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.util.ImageUtil;

/**
 * 
 * (画布)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:16:22,content:
 * </p>
 * 
 * @author 025000-FOSS-helong
 * @date 2012-10-17 上午11:16:22
 * @since
 * @version
 */
public class CanvasPanel extends JPopupMenu{

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  设置窗口宽度
	 */
	private static final Integer windowWidth = 550; 
	
	/**
	 *  设置窗口高度
	 */
	private static final Integer windowHeight = 700; 


	/**
	 * 屏幕宽度
	 */
	private String title = "<>";

	/**
	 * 主面板
	 */
	private JPanel mainPanel; 

	/**
	 * 标题栏标签
	 */
	private JLabel titleLabel; 

	/**
	 *  标题栏面板
	 */
	private JComponent titleComponent; 

	/**
	 * 内容面板
	 */
	private JPanel contentPanel; 

	/**
	 * 构造方法
	 * @param parent
	 * @param parentPanel
	 * @param contentPanel
	 * @param titleComponent
	 * @param windowWidth
	 */
	public CanvasPanel(JPanel contentPanel, JComponent titleComponent) {
		this.contentPanel = contentPanel;
		this.titleComponent = titleComponent;
		this.init();

	}

	/**
	 * 
	 * 显示画布
	 * @author 102246-foss-shaohongliang
	 * @date 2013-3-11 下午3:27:37
	 */
	public void showCanvas() {
		//指定显示位置
		this.show(titleComponent, -windowWidth - NumberConstants.NUMBER_5, -(windowHeight - titleComponent.getHeight())/2);
	}

	@Override
	public void setVisible(boolean b) {
		if(titleComponent instanceof JButton){
			JButton parentButton = (JButton)titleComponent;
			String imgSrc = b?"buttonOpen.png":"buttonClose.png";
			parentButton.setIcon(ImageUtil.getImageIcon(
					this.getClass().getClassLoader(),imgSrc));
		}
		super.setVisible(b);
	}
	
	/**
	 * 初始化界面信息
	 */
	private void init() {

		mainPanel = new JPanel(new BorderLayout());
		titleLabel = new JLabel(title);
		titleLabel.setForeground(Color.WHITE);
		mainPanel.add(contentPanel, BorderLayout.CENTER);
		this.add(mainPanel);
		this.setPreferredSize(new Dimension(windowWidth, windowHeight));
		
		//F12 hide window
		this.registerKeyboardAction(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		},
		KeyStroke.getKeyStroke("F12"),
		JComponent.WHEN_IN_FOCUSED_WINDOW);

	}


}