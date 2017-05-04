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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/ui/PendingButtonColumn.java
 * 
 * FILE NAME        	: PendingButtonColumn.java
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
package com.deppon.foss.module.pickup.creatingexp.client.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.MessageI18nUtil;
import com.deppon.foss.module.pickup.creating.client.action.ImportWaybillEditUIAction;
import com.deppon.foss.module.pickup.creating.client.service.ISalesDeptWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpImportWaybillEditUIAction;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 待处理运单补录按钮
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:16:43,
 * </p>
 * @author 105089-FOSS-yangtong
 * @date 2012-10-17 上午11:16:43
 * @since
 * @version
 */
@SuppressWarnings("unused")
public class ExpPendingButtonColumn extends AbstractCellEditor implements  TableCellEditor, TableCellRenderer ,
   ActionListener { 
	//日志
	public static final Logger LOGGER = LoggerFactory.getLogger(ExpPendingButtonColumn.class);
  
    /**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(ExpPendingButtonColumn.class); 
	
	//log
	private static Log log = LogFactory
			.getLog(ExpPendingButtonColumn.class);

	/**
	 * salesDeptWaybillService
	 */
	private transient ISalesDeptWaybillService salesDeptWaybillService;
	
	//初始化运单服务对象
	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	
	/**
	 * 运单状态列表
	 */
	private List<DataDictionaryValueEntity> list;
	
	/**
	 * ui
	 */
	private ExpSalesDeptWaybillUI ui;
	
    /**
     * LinkTableMode
     */
    private ExpLinkTableMode dtm;  
    
    /**
     * 行
     */
    private int row;  
    
    /**
     * table mode
     */
    private ExpLinkTableMode tableModel; 
    
    /**
     * 不需要点击的按钮的状态中文字
     */
    private List<String> ignoreChieseName = new ArrayList<String>();
      
    /**
     * 构造方法
     * @param tc
     * @param ui
     */
    public ExpPendingButtonColumn(TableColumn tc, ExpSalesDeptWaybillUI ui , 
    		ExpLinkTableMode tableModel)   {  
        this.tableModel = tableModel;
        tc.setCellEditor(this);  
        tc.setCellRenderer(this);  
        this.ui = ui;
        salesDeptWaybillService = WaybillServiceFactory.getSalesDeptWaybillService();
        list = salesDeptWaybillService.queryPendingType();
        //作废
        String obsolete = CommonUtils.getNameFromDict(WaybillRfcConstants.INVALID,DictionaryConstants.WAYBILL_RFC_TYPE_CUSTOMER);
        //中止
        String abort = CommonUtils.getNameFromDict(WaybillRfcConstants.ABORT,DictionaryConstants.WAYBILL_RFC_TYPE_INSIDE);
        ignoreChieseName.add(obsolete);
        ignoreChieseName.add(abort);
        for(DataDictionaryValueEntity data : list){
        	if(WaybillConstants.WAYBILL_STATUS_PC_ACTIVE.equals(data.getValueCode())){
        		String gnoreChieseNameStr = data.getValueName();
        		ignoreChieseName.add(gnoreChieseNameStr);
        	}else if(WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE.equals(data.getValueCode())){
        		String gnoreChieseNameStr = data.getValueName();
        		ignoreChieseName.add(gnoreChieseNameStr);
        	}
    	}
    }  
      
    /**
     * 输入panel
     */
    public Component getTableCellEditorComponent(JTable table,  
            Object value, boolean isSelected, int viewRow, int column) {  
    	int row = table.convertRowIndexToModel(viewRow);
        //  当单元格处于编辑状态时   
        if(tableModel!=null){
    		String statusChinese  =(String) tableModel.getValueAt(row, 2);
    		if(ignoreChieseName.contains(statusChinese)){
    			JLabel editorButton = new JLabel();
    			return editorButton; 
    		}else{
    			JButton editorButton = new JButton();
    			editorButton.setFocusable(false);  
    		    editorButton.addActionListener(this); 
    		    editorButton.setText(i18n.get("foss.gui.creating.pendingButtonColumn.button.label"));  
    	        dtm = (ExpLinkTableMode)table.getModel();  
    	        this.row = row;  
    	        return editorButton;  
    		}
    		
    		
    	}else{
    		JButton editorButton = new JButton();
			editorButton.setFocusable(false);  
		    editorButton.addActionListener(this); 
		    editorButton.setText(i18n.get("foss.gui.creating.pendingButtonColumn.button.label"));  
	        dtm = (ExpLinkTableMode)table.getModel();  
	        this.row = row;  
	        return editorButton;   
    	}
    }  
  
    /**
     * 结果列表
     */
    @Override  
    public Object getCellEditorValue() {  
        //  当单元格的编辑状态结束后调用此方法内的代码   
        return null;  
    }  
  
    /**
     * is selected 
     */
    @Override  
    public boolean shouldSelectCell(EventObject arg0) {  
    	log.debug(arg0);   
        return super.shouldSelectCell(arg0);  
    }  
    
    /**
     * 结果列表 Component
     */
    @Override  
    public Component getTableCellRendererComponent(JTable table,  
            Object value, boolean isSelected, boolean hasFocus, int viewRow,  
            int column) {  

    	int row = table.convertRowIndexToModel(viewRow);
    	
        //  当单元格处于展示状态时
    	if(tableModel!=null){
    		String statusChinese  =(String) tableModel.getValueAt(row, 2);
    		if(ignoreChieseName.contains(statusChinese)){
    			JLabel editorButton = new JLabel();
    			return editorButton; 
    			
    		}else{
    			JButton renderButton = new JButton();
    			String isBig13  =(String) tableModel.getValueAt(row, NumberConstants.NUMBER_24);
    			if(FossConstants.NO.equals(isBig13)){
    				renderButton.setText("当日补录"); 
    			}else{
    				renderButton.setText("次日补录"); 
    			}
    			 
    			return renderButton;  
    		}
    		
    		
    	}else{
    		JButton renderButton = new JButton();
    		renderButton.setText(i18n.get("foss.gui.creating.pendingButtonColumn.button.label"));  
    		return renderButton;  
    	}
    
    	
    } 
    
    /**
	 * 补录按钮事件处理
	 */
    @Override  
    public void actionPerformed(ActionEvent arg0) {  
    	try {
			//立即终止此单元格的编辑状态，使表格模型可以自动更新   
			String id = dtm.getValueAt(this.row, NumberConstants.NUMBER_23).toString();
			//管理部出发运单业务操作服务
			salesDeptWaybillService = WaybillServiceFactory.getSalesDeptWaybillService();
			//获得待处理运单基本信息对象 
			WaybillPendingEntity waybillPending = salesDeptWaybillService.queryPendingById(id);
			String isBig13  =(String) tableModel.getValueAt(row, NumberConstants.NUMBER_24);			
			if(waybillPending!=null){		
				waybillPending.setIsBig13(isBig13);
				// 校验运单号是否已存在
				validateWaybillNo(waybillPending.getPendingType(),waybillPending.getWaybillNo());
			}
			
			
			if(waybillPending==null || !CommonUtils.directDetermineIsExpressByProductCode(waybillPending.getProductCode())){
				ImportWaybillEditUIAction uiAction = new ImportWaybillEditUIAction();
				uiAction.importWaybillEditUI(waybillPending);
			}else{
				ExpImportWaybillEditUIAction uiAction = new ExpImportWaybillEditUIAction();
				uiAction.importWaybillEditUI(waybillPending);
			}
			
			
		} catch (BusinessException ex) {
			LOGGER.error("导入异常", ex);
			//MsgBox.showError(i18n.get("foss.gui.creating.pendingButtonColumn.exception.label")+"："+ex.getMessage());
			if (StringUtils.isNotEmpty(ex.getMessage())) {
				MsgBox.showInfo(i18n.get("foss.gui.creating.pendingButtonColumn.exception.label")+MessageI18nUtil.getMessage(ex, i18n));
			}else if(StringUtils.isNotEmpty(ex.getErrorCode())){
			    MsgBox.showInfo(i18n.get("foss.gui.creating.pendingButtonColumn.exception.label")+ex.getErrorCode());
			}
		}
		 	
    }
    
    // 校验运单号是否已存在
    private void validateWaybillNo(String pendingType,String waybillNo){
    	if(WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(pendingType)){
			//PDA补录
			if (waybillService.checkWaybillNoExceptPDAPending(waybillNo) &&  waybillService.checkEWaybillNoExceptPDAPending(waybillNo)) {		    			
    			throw new WaybillValidateException(i18n.get("foss.pkp.waybill.waybillManagerService.exception.wayBillExsits",new Object[]{waybillNo}));
    		}
		}else{
			//非PDA补录
    		if (waybillService.checkWaybillNo(waybillNo)) {		    			
    			throw new WaybillValidateException(i18n.get("foss.pkp.waybill.waybillManagerService.exception.wayBillExsits",new Object[]{waybillNo}));
    		}
		}
    }

    /**
     * getSalesDeptWaybillService
     * @return ISalesDeptWaybillService
     */
	public ISalesDeptWaybillService getSalesDeptWaybillService() {
		return salesDeptWaybillService;
	}

	/**
	 * setSalesDeptWaybillService
	 * @param salesDeptWaybillService ISalesDeptWaybillService
	 */
	public void setSalesDeptWaybillService(
			ISalesDeptWaybillService salesDeptWaybillService) {
		this.salesDeptWaybillService = salesDeptWaybillService;
	}
 
}