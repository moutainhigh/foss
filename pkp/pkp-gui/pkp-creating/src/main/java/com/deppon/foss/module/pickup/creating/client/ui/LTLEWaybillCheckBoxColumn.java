package com.deppon.foss.module.pickup.creating.client.ui;

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
import com.deppon.foss.module.pickup.creating.client.service.ISalesDeptWaybillService;
import com.deppon.foss.util.CollectionUtils;

/**
 * 选中导出列
 */
public class LTLEWaybillCheckBoxColumn  extends AbstractCellEditor implements  TableCellEditor, TableCellRenderer  {  
	private static final long serialVersionUID = 1L;
	
	//log
	private Log LOG = LogFactory.getLog(LTLEWaybillCheckBoxColumn.class);
	
	//salesDeptWaybillService
	private transient ISalesDeptWaybillService salesDeptWaybillService;
	
	//UI
	private LTLEWaybillManageUI ui;
	
	//补录 button
	private List<JCheckBox> renderButtonList = new ArrayList<JCheckBox>(); 
    
    //TableModel
    private LTLEWaybillManageTableModel dtm; 
    
    private static final int FOUR = 4;
    
    //行
    private int row;
      
    public LTLEWaybillCheckBoxColumn(TableColumn tc, LTLEWaybillManageUI ui){  
        tc.setCellEditor(this);  
        tc.setCellRenderer(this);  
        this.ui = ui;
    }

	/**
     * 输入panel
     */
    public Component getTableCellEditorComponent(JTable table,  
            Object value, boolean isSelected, int viewRow, int column) {  
    	int row = table.convertRowIndexToModel(viewRow);
        //  当单元格处于编辑状态时
    	if(dtm==null){
    		dtm = (LTLEWaybillManageTableModel)table.getModel();
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
     * 结果列表 Component
     */
    @Override  
    public Component getTableCellRendererComponent(JTable table,  
            Object value, boolean isSelected, boolean hasFocus, int viewRow,  
            int column) {  
    	if(dtm==null){
    		dtm = (LTLEWaybillManageTableModel)table.getModel(); 
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
	*/
	class SelectedListener  implements ItemListener  {
		//补录按钮事件处理
	    public void itemStateChanged(ItemEvent e) { 
	    	JCheckBox renderButton = renderButtonList.get(row); 
			Object obj = e.getSource();
			List<String> waybillNoList = ui.getSelectExportWaybillNoList();
			if(obj!=null && obj.equals(renderButton)) {
				if(renderButton.isSelected()) {
					String waybillNo = null;
					//获取对应的运单需要判定对应的数据是否为空，否则会报空指针异常
					Object object = dtm.getValueAt(row, FOUR);
					if(object == null){
						return;
					}else{
						waybillNo = object.toString();
					}
					if(StringUtils.isNotEmpty(waybillNo)){
						if(waybillNoList!=null && !waybillNoList.contains(waybillNo)){
							waybillNoList.add(waybillNo);
						}
					}
					setCheckBoxAllSelected(ui);
				}else {  
					setCheckBoxPartSelected(ui,renderButton);
					String waybillNo = null;
					//获取对应的运单需要判定对应的数据是否为空，否则会报空指针异常
					Object object = dtm.getValueAt(row, FOUR);
					if(object == null){
						return;
					}else{
						waybillNo = object.toString();
					}
					if(StringUtils.isNotEmpty(waybillNo)){
						if(waybillNoList!=null && waybillNoList.contains(waybillNo)){
							waybillNoList.remove(waybillNo);
						}
					}
				}             
			}           
		}
	}
	
	/**
	 * 设置表格中的某行记录为不选中状态
	 * @author fei 305082
	 * @date 2016-6-16下午3:46:37
	 */
	public void setCheckBoxPartSelected(LTLEWaybillManageUI ui,JCheckBox checkBox){
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
	 * @author fei 305082
	 * @date 2016-6-16下午3:46:51
	 */
	public void setCheckBoxAllSelected(LTLEWaybillManageUI ui){
		long count = ui.getTable().getModel().getColumnCount();
		//获得对象集合
		List<JCheckBox> list = ui.getList();
		//判断集合是否为空
		if(count == 0 || CollectionUtils.isEmpty(list)){
			return ;
		}
		//保存所有记录的选中状态
		List<JCheckBox> selectList = new ArrayList<JCheckBox>();
		for(int i=0;i<count;i++){
			for (JCheckBox chk : list) {
				if(chk.isSelected()){
					selectList.add(chk);
				}
			}
		}
		//比较选中行的记录数与总记录数个数
		if(selectList.size() == list.size()){
			ui.getAllSelectCheckBox().setSelected(true);
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
		
	public ISalesDeptWaybillService getSalesDeptWaybillService() {
		return salesDeptWaybillService;
	}
	
	public void setSalesDeptWaybillService(
			ISalesDeptWaybillService salesDeptWaybillService) {
		this.salesDeptWaybillService = salesDeptWaybillService;
	}
	
	public List<JCheckBox> getRenderButtonList() {
		return renderButtonList;
	}
	
	public int getRow() {
		return row;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
}