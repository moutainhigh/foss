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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/ui/decorate/BorderDecorate.java
 * 
 * FILE NAME        	: BorderDecorate.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-creating
 * PACKAGE NAME: com.deppon.foss.module.pickup.creating.client.ui.decorate
 * FILE    NAME: BorderDecorate.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.creating.client.ui.decorate;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;

import com.deppon.foss.framework.client.core.binding.BindingAssociation;

/**
 * 
 * 
 ******************************************* 
 * <b style="font-family:微软雅黑"><small>Description:装饰边框颜色</small></b> </br> <b
 * style="font-family:微软雅黑"><small>HISTORY</small></b></br> <b
 * style="font-family:微软雅黑"><small> ID DATE PERSON REASON</small></b><br>
 ******************************************** 
 * <div style="font-family:微软雅黑,font-size:70%"> 2012-4-1 linws 新增 </div>
 ******************************************** 
 */
public class BorderDecorate {

	/**
	 * old border
	 */
	private Border oldBorder;

	/**
	 * component
	 */
	private JComponent component;

	/**
	 * 构造方法
	 * @param association
	 */
	public BorderDecorate(BindingAssociation association) {
		component = (JComponent) association.getComponent();
		oldBorder = component.getBorder();
	}

	/**
	 * setBorder
	 * @param color
	 */
	public void setBorder(Color color) {
		Border border = BorderFactory.createMatteBorder(1, 1, 1, 1, color);
		component.setBorder(border);
	}

	/**
	 * reset border
	 */
	public void resetBorder() {
		component.setBorder(oldBorder);

	}
}