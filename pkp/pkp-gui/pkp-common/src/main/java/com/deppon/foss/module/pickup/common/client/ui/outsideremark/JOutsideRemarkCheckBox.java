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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/outsideremark/JOutsideRemarkCheckBox.java
 * 
 * FILE NAME        	: JOutsideRemarkCheckBox.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.common.client.ui.outsideremark;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComboBox;


public class JOutsideRemarkCheckBox extends JComboBox{
    /**
	 * 序列化
	 */
	private static final long serialVersionUID = -3390680658928639157L;

	//鼠标事件监听对象
	private transient final MouseAdapter defaultMouseAdapter = new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
	    	showPopup();
	    	super.mouseClicked(e);
	    }
	};
	
	/**
	 * 构造方法  初始化监听器
	 */
    public JOutsideRemarkCheckBox() {
        initListener();
    }
    
    /**
     * 初始化监听器
     */
    private void initListener() {
    	this.addMouseListener(defaultMouseAdapter);
    }

  
}