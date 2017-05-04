package com.deppon.foss.module.pickup.changing.client.ui.controller;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;
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



import com.deppon.foss.module.pickup.changing.client.ui.controller.QueryInstallPanel.ChangeInfoDetailTableModel;
import com.deppon.foss.util.CollectionUtils;

public class InstallCheckBoxColumn extends AbstractCellEditor implements  TableCellEditor, TableCellRenderer {
	

    /**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	
	//log
	private Log LOG = LogFactory.getLog(InstallCheckBoxColumn.class);
	
	
	/**
	 * ui
	 */
	private QueryInstallPanel ui;
	
	/**
	 * 选择
	 */
	private List<JCheckBox> renderButtonList = new ArrayList<JCheckBox>();  
	
	/**
	 * 选中的导出运单号
	 */
	private List<String> selectExportWaybillNoList = new ArrayList<String>();
    /**
     * LinkTableMode
     */
    private ChangeInfoDetailTableModel dtm;  
    
    /**
     * 行
     */
    private int row;      
    List<Integer> rows = new ArrayList<Integer>(); 
    /**
     * 构造方法
     * @param tc
     * @param ui
     */
    public InstallCheckBoxColumn(TableColumn tc,QueryInstallPanel ui)  
    {  
      
        tc.setCellEditor(this);  
        tc.setCellRenderer(this);  
        this.ui=ui;
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
    		dtm = (ChangeInfoDetailTableModel)table.getModel(); 
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
    		dtm = (ChangeInfoDetailTableModel)table.getModel(); 
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
		 * 事件处理
		 */		
	    public void itemStateChanged(ItemEvent e) { 
	    	JCheckBox renderButton = renderButtonList.get(row); 
			Object obj = e.getSource();
			//List<String> waybillNoList = ui.getSelectExportWaybillNoList();
			

			if(obj!=null && obj.equals(renderButton)) {
				if(renderButton.isSelected()) {
					/*String waybillNo = dtm.getValueAt(row, 4).toString();
					String pendingtype =  dtm.getValueAt(row, 25).toString();*/
					/*if(StringUtils.isNotEmpty(waybillNo) 
							&&
		(WaybillConstants.WAYBILL_STATUS_PC_PENDING.equals(pendingtype)||WaybillConstants.WAYBILL_PICTURE_TYPE_WAYBILL_EXCEPTION.equals(pendingtype)
						  )){
						if(waybillNoList!=null && !waybillNoList.contains(waybillNo)){
							waybillNoList.add(waybillNo);
						}
					}*/
					rows.add(row);
					setCheckBoxAllSelected(ui);
					setMultipleRow(ui,row,rows);					
				}else {
					if(rows.contains(new Integer(row))) {
						rows.remove(new Integer(row));
					}else {
						rows.add(row);
					}
					setCheckBoxPartSelected(ui,renderButton);
					setMultipleRow(ui,row,rows);					
						
					/*String waybillNo = dtm.getValueAt(row, 4).toString();
					
					if(StringUtils.isNotEmpty(waybillNo)){
						if(waybillNoList!=null && waybillNoList.contains(waybillNo)){
							waybillNoList.remove(waybillNo);
						}
					}*/
					
				}             
			}          
		}
	    
	    
	}
	
	/**
	 * 设置表格中的某行记录为不选中状态
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-11 下午2:35:05
	 */
	public void setCheckBoxPartSelected(QueryInstallPanel  ui,JCheckBox checkBox){
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
	public void setCheckBoxAllSelected(QueryInstallPanel  ui){
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
	 * dp-foss-dongjialing
	 * 225131
	 * @param ui
	 * @param row
	 * @param rows
	 */
	public void setMultipleRow(QueryInstallPanel  ui,int row,List<Integer> rows) {
		JCheckBox renderButton = null;
		if(CollectionUtils.isNotEmpty(rows)) {
			Integer i = Collections.max(rows);
			if(i>=row) {
				ui.getTable().setRowSelectionInterval(0, i);
				for (int j = 0; j <= i; j++) {
					renderButton= ui.getList().get(j);
					if(renderButton != null && !renderButton.isSelected()) {
						ui.getTable().removeRowSelectionInterval(j, j);
					}
				}
			} else {
				ui.getTable().setRowSelectionInterval(0, row);
				for (int j = 0; j <= row; j++) { 
					renderButton= ui.getList().get(j);
					if(renderButton != null && !renderButton.isSelected()) {
						ui.getTable().removeRowSelectionInterval(j, j);
					}
				}
			}
		} else {
			ui.getTable().setRowSelectionInterval(0, ui.getList().size()-1);
			for (int j = 0; j < ui.getList().size(); j++) {
				renderButton= ui.getList().get(j);
				if(renderButton != null && !renderButton.isSelected()) {
					ui.getTable().removeRowSelectionInterval(j, j);
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



	public List<String> getSelectExportWaybillNoList() {
		return selectExportWaybillNoList;
	}



	public void setSelectExportWaybillNoList(List<String> selectExportWaybillNoList) {
		this.selectExportWaybillNoList = selectExportWaybillNoList;
	}
	

}
