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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/ui/OfflineButtonColumn.java
 * 
 * FILE NAME        	: OfflineButtonColumn.java
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
package com.deppon.foss.module.pickup.creating.client.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.creating.client.action.ImportWaybillEditUIAction;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;


/**
 * 
 * 离线运单提交按钮
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:16:43,
 * </p>
 * @author 105089-FOSS-yangtong
 * @date 2012-10-17 上午11:16:43
 * @since
 * @version
 */
public class OfflineButtonColumn extends AbstractCellEditor implements  TableCellEditor, TableCellRenderer ,
   ActionListener {  
  
    /**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(OfflineButtonColumn.class); 
	
	//log object
	private Log LOG = LogFactory.getLog(OfflineButtonColumn.class);

	private JButton renderButton;  
    private JButton editorButton;  
    private LinkTableMode dtm;  
    private int row;  
    /**
     * ui
     */
  	SalesDeptWaybillUI ui;
  	
    /**
     * 构造方法  
     * @param tc
     * @param ui
     */
    public OfflineButtonColumn(TableColumn tc, SalesDeptWaybillUI ui)  
    {  
        renderButton = new JButton();  
        editorButton = new JButton();  
        editorButton.setFocusable(false);  
        editorButton.addActionListener(this);  
        tc.setCellEditor(this);  
        tc.setCellRenderer(this);  
        this.ui = ui;
    }  
      
    /**
     * 当单元格处于编辑状态时   
     */
    public Component getTableCellEditorComponent(JTable table,  
            Object value, boolean isSelected, int row, int column) {  
        //  当单元格处于编辑状态时   
        editorButton.setText(i18n.get("foss.gui.creating.buttonPanel.submit.label"));  
        dtm = (LinkTableMode)table.getModel();  
        this.row = row;  
        return editorButton;  
    }  
  
    /**
     * 当单元格的编辑状态结束后调用此方法内的代码   
     */
    @Override  
    public Object getCellEditorValue() {  
        //  当单元格的编辑状态结束后调用此方法内的代码   
        return null;  
    }
  
    /**
     * getTableCellRendererComponent
     */
    @Override  
    public Component getTableCellRendererComponent(JTable table,  
            Object value, boolean isSelected, boolean hasFocus, int row,  
            int column) {  
        //  当单元格处于展示状态时   
        renderButton.setText(i18n.get("foss.gui.creating.buttonPanel.submit.label"));  
        return renderButton;  
    } 
    
    /**
	 * 提交按钮事件处理
	 */
    @Override  
    public void actionPerformed(ActionEvent arg0) {  
    	//立即终止此单元格的编辑状态，使表格模型可以自动更新   
		String id;
		try {
			id = dtm.getValueAt(this.row, NumberConstants.NUMBER_22).toString();
			WaybillPendingEntity waybillPending = WaybillServiceFactory.getSalesDeptWaybillService().queryWaybillOfflineByPrimaryKey(id);
			ImportWaybillEditUIAction uiAction = new ImportWaybillEditUIAction();
			uiAction.importOFFLINEWaybillEditUI(waybillPending);
			LOG.debug(id);
		} catch (BusinessException e) {
			MsgBox.showError(e.getMessage()+"\n"+e.getErrorCode());
		}

    }

}