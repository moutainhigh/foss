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
 * PROJECT NAME	: pkp-changing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/ui/internal/CanvasPanel.java
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
package com.deppon.foss.module.pickup.changing.client.ui.internal;

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

import com.deppon.foss.framework.client.commons.util.ImageUtil;

/**
 * 
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:dp-yangtong,date:2012-10-12 上午10:05:23,content:
 * </p>
 * 
 * @author dp-yangtong
 * @date 2012-10-12 上午10:05:23
 * @since
 * @version
 */
public class CanvasPanel extends JPopupMenu{

	private static final long serialVersionUID = -3564453685861233338L;


	private static final int TEN = 10;
	

	private static final int PANELHEIGHT = 520;
	
	private static final int PANELWIDTH = 180;

	
	private String title = "<>";

	/**
	 *  主面板
	 */
	private JPanel mainPanel;

	/**
	 * 标题栏标签
	 */
	private JLabel titleLabel; 

	/**
	 * 内容面板
	 */
	private JComponent contentPanel; 

	/**
	 * 父容器
	 */
	private JComponent parentPanel;

	public CanvasPanel(JComponent target, JComponent content) {
		this.contentPanel = content;
		this.parentPanel = target;
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
		this.show(parentPanel, -PANELWIDTH - TEN, 0);
	}
	
	@Override
	public void setVisible(boolean b) {
		if(parentPanel instanceof JButton){
			JButton parentButton = (JButton)parentPanel;
			String imgSrc = b?"buttonOpen.png":"buttonClose.png";
			parentButton.setIcon(ImageUtil.getImageIcon(
					this.getClass().getClassLoader(),imgSrc));
		}
		super.setVisible(b);
	}
	
	/**
	 * 
	 * 初始化布局
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午10:54:43
	 */
	private void init() {

		// setUndecorated(true);

		mainPanel = new JPanel(new BorderLayout());

		titleLabel = new JLabel(title);

		titleLabel.setForeground(Color.WHITE);

		mainPanel.add(contentPanel, BorderLayout.CENTER);
		
		mainPanel.setPreferredSize(new Dimension(PANELWIDTH, PANELHEIGHT));
		
		this.add(mainPanel);
		
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