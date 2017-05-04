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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/UIUtils.java
 * 
 * FILE NAME        	: UIUtils.java
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
package com.deppon.foss.module.pickup.common.client.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.ItemSelectable;

import javax.swing.text.JTextComponent;
/**
 * ui控制工具类
 * 主要控制ui是否可以编辑
 * 
 * @author 026123-foss-lifengteng
 *
 */
public class UIUtils {

	/**
	 * 将ui设置为不可编辑
	 * @param comp
	 * @param clazz
	 */
	public static void disableUI(Component comp, Class<?>... clazz ) {
		setUIControllEnable(comp, false,clazz);
	}

	/**
	 * 将ui设置为可编辑
	 * @param comp
	 * @param clazz
	 */
	public static void enableUI(Component comp, Class<?>... clazz ) {
		setUIControllEnable(comp, true,clazz);
	}

	/**
	 * <p>
	 * 设置所有控件不可用
	 * </p>
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-17 下午4:21:27
	 * @see
	 */
	private static void setUIControllEnable(Component comp, boolean isEnable, Class<?>... clazz ) {
		//获得可变参数长度
		int length = clazz.length;
		//若面板组件类型与Component一致，则退出
		if(clazz.length > 0){
			for (int i =0 ;i<length ;i++){
				if(comp.getClass() == clazz[i]){
					return;
				}
			}
		}
		
		//若面板中还有面板，则遍历面板中的组件，并设置为不可编辑
		if (comp instanceof Container) {
			//获得Container面板中的组件数
			int childCount = ((Container) comp).getComponentCount();
			//
			for (int i = 0; i < childCount; i++) {
				setUIControllEnable(((Container) comp).getComponent(i), isEnable, clazz);
			}
		}
		
		//若面板为文件框组件，则设置为不可编辑状态
		if (comp instanceof JTextComponent || comp instanceof ItemSelectable) {
			comp.setEnabled(isEnable);
		}

	}
	
	
}