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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/ui/dialog/ChangeModeCheckBoxColumn.java
 * 
 * FILE NAME        	: ChangeModeCheckBoxColumn.java
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

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.module.pickup.changing.client.vo.WaybillInfoVo;
import com.deppon.foss.module.pickup.waybill.shared.define.LabeledGoodChangeHistoryConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodChangeHistoryDto;

/**
 * 删除导出列
 * 
 * @author shixw
 * 
 */
public class ChangeModeCheckBoxColumn extends AbstractCellEditor implements
		TableCellEditor, TableCellRenderer {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;

	// log
	private static Log LOG = LogFactory.getLog(ChangeModeCheckBoxColumn.class);
	
	private static final int THREE = 3;
	
	/**
	 * ui
	 */
	private ChangeGoodsQtyDialog ui;
	
	/**
	 * 删除 check box  列表
	 */
	private List<JCheckBox> renderButtonList = new ArrayList<JCheckBox>();  
	
	/**
     * LinkTableMode
     */
    private ChangeGoodQtyMode dtm;  
    
    /**
     * 行
     */
    private int row;  
    
    /**
     * 最新的界面信息
     */
	private WaybillInfoVo currentWaybillInfoVo;
    
    /**
     * 构造方法
     * @param tc
     * @param ui
     */
    public ChangeModeCheckBoxColumn(TableColumn tc, ChangeGoodsQtyDialog ui,
    		WaybillInfoVo currentWaybillInfoVo) {  
      
        tc.setCellEditor(this);  
        tc.setCellRenderer(this);  
        this.ui = ui;
        this.currentWaybillInfoVo = currentWaybillInfoVo;
        if(LOG.isDebugEnabled()){
        	LOG.debug(this.ui);
        	LOG.debug(this.currentWaybillInfoVo);
        }
      
        
    }

   
    
    /**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}



	/**
	 * @param row the row to set
	 */
	public void setRow(int row) {
		this.row = row;
	}
	
	/**
     * 输入panel
     */
    public Component getTableCellEditorComponent(JTable table,  
            Object value, boolean isSelected, int row, int column) {  
        //  当单元格处于编辑状态时  
    	if(dtm==null){
    		dtm = (ChangeGoodQtyMode)table.getModel(); 
    	}
        this.row = row;  
        if(renderButtonList.size()<row+1){
        	JCheckBox renderButton = new JCheckBox(); 

        	renderButton.setHorizontalAlignment(JLabel.CENTER);
        	SelectedListener l  = new SelectedListener();
        	renderButton.addItemListener(l);  

            renderButtonList.add(renderButton);
            return renderButton;  
        }else{
        	JCheckBox renderButton = renderButtonList.get(row); 
            
        	renderButton.setHorizontalAlignment(JLabel.CENTER);
            return renderButton;  
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
    	LOG.debug(arg0);   
        return super.shouldSelectCell(arg0);  
    }  
    
    /**
     * 结果列表 Component
     */
    @Override  
    public Component getTableCellRendererComponent(JTable table,  
            Object value, boolean isSelected, boolean hasFocus, int row,  
            int column) {  
    	if(dtm==null){
    		dtm = (ChangeGoodQtyMode)table.getModel(); 
    	}
    	
        //  当单元格处于展示状态时   
    	 if(renderButtonList.size()<row+1 ){
         	 JCheckBox renderButton = new JCheckBox(); 

         	 renderButton.setHorizontalAlignment(JLabel.CENTER);         	 
         	 LabeledGoodChangeHistoryDto dto = (LabeledGoodChangeHistoryDto)dtm.getValueAt(row, THREE);
         	 if(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_DELETE.equals(dto.getChangeType())){
         		renderButton.setSelected(true);
         	 }else{
         		renderButton.setSelected(false);
         	 }
         	 SelectedListener l  = new SelectedListener();
         	 renderButton.addItemListener(l);  

             renderButtonList.add(renderButton);
             return renderButton;  
         }else{
         	 JCheckBox renderButton = renderButtonList.get(row); 
         	 LabeledGoodChangeHistoryDto dto = (LabeledGoodChangeHistoryDto)dtm.getValueAt(row, THREE);
         	 if(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_DELETE.equals(dto.getChangeType())){
         		renderButton.setSelected(true);
         	 }else{
         		renderButton.setSelected(false);
         	 }
         	 renderButton.setHorizontalAlignment(JLabel.CENTER);
             return renderButton;  
         }
    } 
  
    /**
	* 选
	* @author shixw
	*
	*/
	class SelectedListener  implements ItemListener  {
		
		/**
		 * 补录按钮事件处理
		 */
	    public void itemStateChanged(ItemEvent e) { 
	    	JCheckBox renderButton = renderButtonList.get(row); 
			Object obj = e.getSource();
			
			if(obj!=null && obj.equals(renderButton)) {
				//从0开始 最后一个是对象本身
				LabeledGoodChangeHistoryDto dto = (LabeledGoodChangeHistoryDto)dtm.getValueAt(row, THREE);
				
				//删除checkbox框被选中
				if(renderButton.isSelected()) {
					dto.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_DELETE);
					
				}else{	//删除check box框取消选中	
					//这一条就是增加
					if(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_DELETE.equals(dto.getChangeType())){
						dto.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_NEW);
					}else{
						//其他状态 我们要保持原样 防止 打木架信息丢失
					}
				}
			}
	    }
	}
	
	/**
	 * @return the renderButtonList
	 */
	public List<JCheckBox> getRenderButtonList() {
		return renderButtonList;
	}
 
}