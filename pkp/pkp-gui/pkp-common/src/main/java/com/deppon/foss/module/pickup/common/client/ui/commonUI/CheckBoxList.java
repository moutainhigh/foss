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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/commonUI/CheckBoxList.java
 * 
 * FILE NAME        	: CheckBoxList.java
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

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.basic.BasicListUI;

/**
 * 
 * 带复选框List
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-12-12 下午5:02:20
 */
public class CheckBoxList extends JList {

	private static final long serialVersionUID = 7401568961910691452L;

	public CheckBoxList() {
		super();
		initCheckBox();
	}

	public CheckBoxList(final Vector<?> listData) {
		super(listData);
		initCheckBox();
	}

	public CheckBoxList(final Object[] listData) {
		super(listData);
		initCheckBox();
	}

	public CheckBoxList(ListModel dataModel) {
		super(dataModel);
		initCheckBox();
	}

	private void initCheckBox() {
		this.setCellRenderer(new CheckBoxRenderer());
		this.setUI(new CheckBoxListUI());
	}

	static class CheckBoxRenderer extends JCheckBox implements ListCellRenderer {

		private static final long serialVersionUID = -5482557127707629678L;

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {

			this.setSelected(isSelected);
			this.setText(value.toString());
			return this;
		}
	}

	class CheckBoxListUI extends BasicListUI implements MouseInputListener {

		protected MouseInputListener createMouseInputListener() {
			return this;
		}

		public void mouseClicked(MouseEvent e) {

		}

		public void mousePressed(MouseEvent e) {
			int row = CheckBoxList.this.locationToIndex(e.getPoint());

			boolean temp = CheckBoxList.this.getSelectionModel()
					.isSelectedIndex(row);

			if (!temp) {
				CheckBoxList.this.addSelectionInterval(row, row);
			} else {
				CheckBoxList.this.removeSelectionInterval(row, row);
			}

		}

		public void mouseReleased(MouseEvent e) {

		}

		public void mouseEntered(MouseEvent e) {

		}

		public void mouseExited(MouseEvent e) {

		}

		public void mouseDragged(MouseEvent e) {

		}

		public void mouseMoved(MouseEvent e) {

		}
	}

}