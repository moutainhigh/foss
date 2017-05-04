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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/ui/dialog/JImageChooser.java
 * 
 * FILE NAME        	: JImageChooser.java
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
package com.deppon.foss.module.pickup.changing.client.ui.dialog;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;

/**
 * 
 * 图片选择器
 * @author 102246-foss-shaohongliang
 * @date 2012-10-30 下午2:48:41
 */
public class JImageChooser extends JFileChooser {
	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(JImageChooser.class); 

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1270263563182464659L;
	
	/**
	 * 构造方法
	 */
	public JImageChooser() {
		//初始目录设置到桌面
		super(FileSystemView.getFileSystemView().getHomeDirectory());
	}

	@Override
	public boolean accept(File file) {
		//文件夹可以显示
		if(file.isDirectory())
			return true;
		String fileName = file.getName().toUpperCase();
		//只显示图片文件
		return fileName.endsWith(".JPG") || fileName.endsWith(".GIF") || fileName.endsWith(".PNG") || fileName.endsWith(".BMP");
	}

	@Override
	public String getDescription(File f) {
		//图片文件
		return i18n.get("foss.gui.changing.imageViewCheckDialog.image.type");
	}
}