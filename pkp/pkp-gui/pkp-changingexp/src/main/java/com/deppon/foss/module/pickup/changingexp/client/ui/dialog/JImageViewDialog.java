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
 * PROJECT NAME	: pkp-changingexp
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/ui/dialog/JImageViewDialog.java
 * 
 * FILE NAME        	: JImageViewDialog.java
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
package com.deppon.foss.module.pickup.changingexp.client.ui.dialog;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JDialog;
import javax.swing.JPanel;

import com.deppon.foss.framework.client.core.context.ApplicationContext;

/**
 * 
 * 图片预览对话框
 * @author 102246-foss-shaohongliang
 * @date 2012-12-18 下午8:00:27
 */
public class JImageViewDialog extends JDialog {

	/**
	 * 2
	 */
	private static final int TWO = 2;

	/**
	 * 40
	 */
	private static final int FORTY = 40;

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -5326264652918463823L;

	/**
	 * 图片对象
	 */
	private BufferedImage image;

	public JImageViewDialog(BufferedImage image) {
		super(ApplicationContext.getApplication().getWorkbench().getFrame());
		this.image = image;
		init();
	}

	/**
	 * 
	 * 初始化布局
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午9:18:23
	 */
	private void init() {
		JPanel imagePanel = new ZPanel();
		this.add(imagePanel);
		setSize(image.getWidth(this) + TWO, image.getHeight(this) + FORTY);
		setModal(true);
	}
	
	/**
	 * 
	 * 图片面板
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午10:08:50
	 */
	private final class ZPanel extends javax.swing.JPanel {

		private static final long serialVersionUID = 1L;

		@Override
		public void paintComponent(Graphics g1) {
			int x = 0;
			int y = 0;
			Graphics g = (Graphics) g1;
			if (null == image) {
				return;
			}
			//绘制图片
			g.drawImage(image, x, y, image.getWidth(this), image.getHeight(this), this);
		}
	}

}