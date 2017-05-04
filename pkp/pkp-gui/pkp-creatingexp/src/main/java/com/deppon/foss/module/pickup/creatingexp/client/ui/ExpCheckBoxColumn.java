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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/ui/CheckBoxColumn.java
 * 
 * FILE NAME        	: CheckBoxColumn.java
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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.pickup.creating.client.service.ISalesDeptWaybillService;
import com.deppon.foss.util.CollectionUtils;

/**
 * 选中导出列
 * @author shixw
 *
 */
public class ExpCheckBoxColumn  extends AbstractCellEditor implements  TableCellEditor, TableCellRenderer  {  
  
    /**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	
	//log
	private Log LOG = LogFactory.getLog(ExpCheckBoxColumn.class);
	

	/**
	 * salesDeptWaybillService
	 */
	private transient ISalesDeptWaybillService salesDeptWaybillService;
	
	/**
	 * ui
	 */
	private ExpSalesDeptWaybillUI ui;
	
	/**
	 * 补录 button
	 */
	private List<JCheckBox> renderButtonList = new ArrayList<JCheckBox>();  
	
    
    /**
     * LinkTableMode
     */
    private ExpLinkTableMode dtm;  
    
    /**
     * 行
     */
    private int row;  
      
    /**
     * 构造方法
     * @param tc
     * @param ui
     */
    public ExpCheckBoxColumn(TableColumn tc, ExpSalesDeptWaybillUI ui)  
    {  
      
        tc.setCellEditor(this);  
        tc.setCellRenderer(this);  
       
        this.ui = ui;
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
            Object value, boolean isSelected, int viewRow, int column) {  

    	int row = table.convertRowIndexToModel(viewRow);
    	
        //  当单元格处于编辑状态时  
    	if(dtm==null){
    		dtm = (ExpLinkTableMode)table.getModel(); 
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
            Object value, boolean isSelected, boolean hasFocus, int viewRow,  
            int column) {  
    	if(dtm==null){
    		dtm = (ExpLinkTableMode)table.getModel(); 
    	}
    	
    	int row = table.convertRowIndexToModel(viewRow);
    	
        //  当单元格处于展示状态时   
    	 if(renderButtonList.size()<row+1 ){
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
			List<String> waybillNoList = ui.getSelectExportWaybillNoList();
			List<String> idList= ui.getSelectIdList();
			if(obj!=null && obj.equals(renderButton)) {
				if(renderButton.isSelected()) {
					String waybillNo = dtm.getValueAt(row, NumberConstants.NUMBER_3).toString();
					String id =  dtm.getValueAt(row, NumberConstants.NUMBER_22).toString();
					if(StringUtils.isNotEmpty(waybillNo)){
						if(waybillNoList!=null && !waybillNoList.contains(waybillNo)){
							waybillNoList.add(waybillNo);
						}
					}

					if(StringUtils.isNotEmpty(id)){
						if(idList!=null && !idList.contains(id)){
							idList.add(id);
						}
					}
					
					setCheckBoxAllSelected(ui);
				}   else {  
					setCheckBoxPartSelected(ui,renderButton);
					
					String waybillNo = dtm.getValueAt(row, NumberConstants.NUMBER_3).toString();
					
					String id =  dtm.getValueAt(row, NumberConstants.NUMBER_22).toString();
					
					if(StringUtils.isNotEmpty(waybillNo)){
						if(waybillNoList!=null && waybillNoList.contains(waybillNo)){
							waybillNoList.remove(waybillNo);
						}
					}
					
					if(StringUtils.isNotEmpty(id)){
						if(idList!=null && !idList.contains(id)){
							idList.remove(id);
						}
					}
				}             
			}           
		}
	    
	    
	};
	
	/**
	 * 设置表格中的某行记录为不选中状态
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-11 下午2:35:05
	 */
	public void setCheckBoxPartSelected(ExpSalesDeptWaybillUI ui,JCheckBox checkBox){
		//获得对象集合
		List<JCheckBox> list = ui.getList();
		//判断集合是否为空
		if(CollectionUtils.isEmpty(list)){
			return ;
		}
		
		//在将第row记录设置为false状态前，先保存所有记录的选中状态
		List<JCheckBox> selectList = new ArrayList<JCheckBox>();
		for (JCheckBox chk : list) {
			if(chk.isSelected() && !selectList.equals(checkBox)){
				selectList.add(chk);
			}
		}
		
		//未被选中时，设置全选按钮为未选中状态，其它已选中的不变
		ui.getAllSelectCheckBox().setSelected(false);
		//设置为未选中
		checkBox.setSelected(false);
		//设置全选框后，将单选框再设置回来
		for (JCheckBox chk : list) {
			for (JCheckBox selectCheckBox : selectList) {
				if(chk.equals(selectCheckBox)){
					chk.setSelected(true);
				}
			}
		}
	}
	
	/**
	 * 若逐条全选中全部记录，则全选按钮自动选中
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-11 下午3:55:34
	 */
	public void setCheckBoxAllSelected(ExpSalesDeptWaybillUI ui){
		//获得对象集合
		List<JCheckBox> list = ui.getList();
		//判断集合是否为空
		if(CollectionUtils.isEmpty(list)){
			return ;
		}
		
		//保存所有记录的选中状态
		List<JCheckBox> selectList = new ArrayList<JCheckBox>();
		for (JCheckBox chk : list) {
			if(chk.isSelected()){
				selectList.add(chk);
			}
		}
		
		//比较选中行的记录数与总记录数个数
		if(selectList.size() == list.size()){
			ui.getAllSelectCheckBox().setSelected(true);
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

	/**
	 * @return the renderButtonList
	 */
	public List<JCheckBox> getRenderButtonList() {
		return renderButtonList;
	}
 
	
	 
  
}