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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/commonUI/TextFieldSelector.java
 * 
 * FILE NAME        	: TextFieldSelector.java
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
package com.deppon.foss.module.pickup.common.client.ui.commonUI;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.deppon.foss.framework.client.commons.util.ImageUtil;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 带有选择放大镜的textField组合panel
 * @author shixw
 *
 */
public abstract class TextFieldSelector extends JPanel {
	
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -8053368907102997074L;


	private static final int TEN = 10;
	

	private int selectionMode = ListSelectionModel.SINGLE_SELECTION;
	protected JTextField txtField;
	
	protected JButton btnQuery;
	/**
	 * 构造方法
	 * @param model
	 */
	public TextFieldSelector(boolean editabled) {
		
		FormLayout formLayout = new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("default:grow"),
						FormFactory.DEFAULT_COLSPEC, },
				new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, });
		setLayout(formLayout);
		
		txtField = getTxtField();
		if(txtField==null){
			txtField = new JTextField();
			txtField.setEnabled(editabled);
			add(txtField, "1, 1, fill, default");
			txtField.setColumns(TEN);
		}
		btnQuery = getBtnQuery();
		if(btnQuery==null){
			btnQuery = new JButton();
			btnQuery.setFocusPainted(false);
			btnQuery.setBorderPainted(false);
			btnQuery.setIcon(ImageUtil.getImageIcon(this.getClass(),
					"query.png"));
			add(btnQuery, "2, 1");
		}
	
	}
	
	
	
	/**
	 * @return the txtField
	 */
	public abstract JTextField getTxtField();


	/**
	 * @return the btnQuery
	 */
	public abstract JButton getBtnQuery() ;

	

	/**
	 * @return the selectionMode
	 */
	public int getSelectionMode() {
		return selectionMode;
	}

	/**
	 * @param selectionMode the selectionMode to set
	 */
	public void setSelectionMode(int selectionMode) {
		this.selectionMode = selectionMode;
	}

}