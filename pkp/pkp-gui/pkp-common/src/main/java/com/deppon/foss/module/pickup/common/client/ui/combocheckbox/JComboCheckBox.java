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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/combocheckbox/JComboCheckBox.java
 * 
 * FILE NAME        	: JComboCheckBox.java
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
package com.deppon.foss.module.pickup.common.client.ui.combocheckbox;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.plaf.ComponentUI;

import com.deppon.foss.base.util.define.NumberConstants;

/**
 * JComboBox 实现多选
 * @author shixw
 *
 */
public class JComboCheckBox extends JComboBox {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * max width
	 */
	private int maxWidth = NumberConstants.NUMBER_300;
	
	/**
	 * ui
	 */
	private ComponentUI ui;
	
	/**
	 * render
	 */
	private ComboCheckBoxRenderer render ;

	/**
	 * 构造方法
	 */
	public JComboCheckBox() {
		super();
		render=new ComboCheckBoxRenderer();
		setRenderer(render);
	}

	/**
	 * 构造方法
	 * @param items
	 */
	public JComboCheckBox(String[] items) {
		super();
		render=new ComboCheckBoxRenderer();
		setRenderer(render);
		addItems(items);
		updateUI();
	}

	/**
	 * 构造方法
	 * @param items
	 */
	public JComboCheckBox(Vector<String> items) {
		super();
		render=new ComboCheckBoxRenderer();
		setRenderer(render);
		addItems(items.toArray(new String[0]));
		updateUI();
	}

	/**
	 * 构造方法
	 * @param maxWidth
	 */
	public JComboCheckBox(int maxWidth) {
		super();
		this.maxWidth = maxWidth;
		render=new ComboCheckBoxRenderer();
		setRenderer(render);
		updateUI();
	}

	/**
	 * 构造方法
	 * @param items
	 * @param maxWidth
	 */
	public JComboCheckBox(String[] items, int maxWidth) {
		super();
		this.maxWidth = maxWidth;
		render=new ComboCheckBoxRenderer();
		setRenderer(render);
		addItems(items);
		updateUI();
	}

	/**
	 * 构造方法
	 * @param items
	 * @param maxWidth
	 */
	public JComboCheckBox(Vector<String> items, int maxWidth) {
		super();
		this.maxWidth = maxWidth;
		render=new ComboCheckBoxRenderer();
		setRenderer(render);
		addItems(items.toArray(new String[0]));
		updateUI();
	}

	/**
	 * 加入item
	 * @param items
	 */
	public void addItems(String[] items) {
		for (int i = 0; i < items.length; i++) {
			String string = items[i];
			this.addItem(new ComboCheckBoxEntry(String.valueOf(i + 1), string));
		}
	}

	/**
	 * 加入item
	 * @param item
	 */
	public void addItem(ComboCheckBoxEntry item) {
		super.addItem(item);
	}

	/**
	 * 加入item
	 * @param checked
	 * @param state
	 * @param id
	 * @param value
	 */
	public void addItem(boolean checked, boolean state, String id, String value) {
		super.addItem(new ComboCheckBoxEntry(checked, state, id, value));
	}

	/**
	 * get code from items list
	 * @return
	 */
	public String[] getCheckedCodes() {
		List<String> values = new ArrayList<String>();
		DefaultComboBoxModel model = (DefaultComboBoxModel) getModel();
		for (int i = 0; i < model.getSize(); i++) {
			ComboCheckBoxEntry item = (ComboCheckBoxEntry) model
					.getElementAt(i);
			boolean checked = item.getChecked();
			if (checked) {
				values.add(item.getUniqueCode());
			}
		}
		String[] retVal = new String[values.size()];
		String[] array = values.toArray(retVal);
		return array;
	}

	/**
	 * get code values from items list
	 * @return
	 */
	public String[] getCheckedValues() {
		List<String> values = new ArrayList<String>();
		DefaultComboBoxModel model = (DefaultComboBoxModel) getModel();
		for (int i = 0; i < model.getSize(); i++) {
			ComboCheckBoxEntry item = (ComboCheckBoxEntry) model
					.getElementAt(i);
			boolean checked = item.getChecked();
			if (checked) {
				values.add(item.getValue());
			}
		}
		String[] retVal = new String[values.size()];
		String[] array = values.toArray(retVal);
		return array;
		
	}

	/**
	 * update ui
	 */
	public void updateUI() {
		ui = new ComboCheckBoxUI(maxWidth);
	
		setUI(ui);
	}

	/**
	 * @return the ui
	 */
	public ComponentUI getUi() {
		return ui;
	}

	/**
	 * @param ui the ui to set
	 */
	public void setUi(ComponentUI ui) {
		this.ui = ui;
	}

	/**
	 * @return the render
	 */
	public ComboCheckBoxRenderer getRender() {
		return render;
	}

	/**
	 * @param render the render to set
	 */
	public void setRender(ComboCheckBoxRenderer render) {
		this.render = render;
	}

	
	
	
	
}