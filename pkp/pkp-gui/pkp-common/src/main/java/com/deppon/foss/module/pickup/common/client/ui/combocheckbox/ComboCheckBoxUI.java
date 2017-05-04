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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/combocheckbox/ComboCheckBoxUI.java
 * 
 * FILE NAME        	: ComboCheckBoxUI.java
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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

import com.deppon.foss.base.util.define.NumberConstants;
import com.jgoodies.looks.windows.WindowsComboBoxUI;


/**
 * JComboBox 实现多选ui
 * @author shixw
 *
 */
public class ComboCheckBoxUI extends WindowsComboBoxUI implements Serializable{
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 是否为多选
	 */
    private boolean isMultiSel = true;
    
    /**
     * max width
     */
    public int maxWidth = NumberConstants.NUMBER_300;
 
    /**
     * 构造方法
     */
    public ComboCheckBoxUI() {
    }
 
    /**
     * 构造方法
     * @param maxWidth
     */
    public ComboCheckBoxUI(int maxWidth) {
        this.maxWidth = maxWidth;
    }
 
    /**
     * create ui
     * @param c
     * @return
     */
    public static ComponentUI createUI(JComponent c) {
        return new ComboCheckBoxUI();
    }
 
    /**
     * create popup
     */
    @Override
    protected ComboPopup createPopup() {
        ComboCheckPopUp popUp = new ComboCheckPopUp(comboBox, maxWidth);
        popUp.getAccessibleContext().setAccessibleParent(comboBox);
        
        return popUp;
    }
 
    /**
     * pop up class for combo check box
     * @author shixw
     *
     */
    public class ComboCheckPopUp extends BasicComboPopup {
 
        /**
		 * 序列化版本号
		 */
		private static final long serialVersionUID = -8469064534089714993L;
		
		/**
		 * default width
		 */
		private int width = -1;
		
		/**
		 * max width
		 */
        private int maxWidth = NumberConstants.NUMBER_300;
 
        /**
         * pop up window
         * @param cBox
         * @param maxWidth
         */
        public ComboCheckPopUp(JComboBox cBox, int maxWidth) {
            super(cBox);
            this.maxWidth = maxWidth;
        }
 
        /**
         * createScroller
         */
        protected JScrollPane createScroller() {
            return new JScrollPane(
                    list,
                    ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        }
 
        /**
         * createListMouseListener
         */
        protected MouseListener createListMouseListener() {
            return new CheckBoxListMouseHandler();
        }
 
        /**
         * createKeyListener
         */
        protected KeyListener createKeyListener() {
            return new CheckBoxKeyHandler();
        }
 
        /**
         * show
         */
        public void show() {
 
            Dimension popupSize = comboBox.getSize();
            Insets insets = getInsets();
            popupSize.setSize(popupSize.width - (insets.right + insets.left),
                    getPopupHeightForRowCount(comboBox.getMaximumRowCount()));
 
            int maxWidthOfCell = calcPreferredWidth();
            width = maxWidthOfCell;
 
            if (comboBox.getItemCount() > comboBox.getMaximumRowCount()) {
                width += scroller.getVerticalScrollBar().getPreferredSize().width;
            }
 
            if (width > this.maxWidth) {
                width = this.maxWidth;
            }
 
            if (width < this.comboBox.getWidth()) {
                width = this.comboBox.getWidth();
            }
 
            if (maxWidthOfCell > width) {
                popupSize.height += scroller.getHorizontalScrollBar().getPreferredSize().height;
            }
 
            Rectangle popupBounds = computePopupBounds(0, comboBox.getBounds().height, width, popupSize.height);
            scroller.setMaximumSize(popupBounds.getSize());
            scroller.setPreferredSize(popupBounds.getSize());
            scroller.setMinimumSize(popupBounds.getSize());
            list.invalidate();
            syncListSelectionWithComboBoxSelection();
            list.ensureIndexIsVisible(list.getSelectedIndex());
            setLightWeightPopupEnabled(comboBox.isLightWeightPopupEnabled());
            show(comboBox, popupBounds.x, popupBounds.y);
        }
 
        /**
         * calcPreferredWidth
         * @return
         */
        private int calcPreferredWidth() {
            int prefferedWidth = 0;
            ListCellRenderer renderer = list.getCellRenderer();
            for (int index = 0, count = list.getModel().getSize(); index < count; index++) {
                Object element = list.getModel().getElementAt(index);
                Component comp = renderer.getListCellRendererComponent(list, element, index, false,
                        false);
                Dimension dim = comp.getPreferredSize();
                if (dim.width > prefferedWidth) {
                    prefferedWidth = dim.width;
                }
            }
            return prefferedWidth;
        }
 
        /**
         * syncListSelectionWithComboBoxSelection
         */
        void syncListSelectionWithComboBoxSelection() {
            int selectedIndex = comboBox.getSelectedIndex();
            if (selectedIndex == -1) {
                list.clearSelection();
            } else {
                list.setSelectedIndex(selectedIndex);
            }
        }
 
        /**
         * setPopupWidth
         * @param width
         */
        public void setPopupWidth(int width) {
            this.width = width;
        }
 
        /**
         * CheckBoxKeyHandler
         * @author shixw
         *
         */
        protected class CheckBoxKeyHandler extends KeyAdapter {
        }
 
        /**
         * CheckBoxListMouseHandler
         * @author shixw
         *
         */
        protected class CheckBoxListMouseHandler extends MouseAdapter {
 
        	/**
        	 * mousePressed
        	 */
            public void mousePressed(MouseEvent anEvent) {
                int index = list.getSelectedIndex();
                ComboCheckBoxEntry item = (ComboCheckBoxEntry) list.getModel().getElementAt(index);
                boolean checked = !item.getChecked();
                int size = list.getModel().getSize();
 
                if (isMultiSel) {
                    item.setChecked(checked);
                } else {
                    for (int i = 0; i < size; i++) {
                        ComboCheckBoxEntry ccbe = (ComboCheckBoxEntry) list.getModel().getElementAt(i);
                        ccbe.setChecked(false);
                    }
                    item.setChecked(true);
                }
                updateListBoxSelectionForEvent(anEvent, false);
                Rectangle rect = list.getCellBounds(0, size - 1);
                list.repaint(rect);
            }
 
            /**
             * mouseReleased
             */
            public void mouseReleased(MouseEvent anEvent) {
                if (!isMultiSel) {
                    ComboCheckBoxEntry item = (ComboCheckBoxEntry) list.getSelectedValue();
                    if (item.checked) {
                        comboBox.setSelectedIndex(list.getSelectedIndex());
                        comboBox.setPopupVisible(false);
                    }
                }
            }
        }
    }
}