package com.deppon.foss.module.pickup.creating.client.ui;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.commons.lang.ObjectUtils.Null;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;

public class BatchCreateWaybillTableModel extends AbstractTableModel  {

private static final long serialVersionUID = 1016386589304304321L;

	private static final int NUM_125 = 125;
	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(BatchCreateWaybillTableModel.class); 

	//log object
	private static Log log = LogFactory.getLog(BatchCreateWaybillTableModel.class);

	// 常量配置区
	private String[] tableHeader = {i18n.get("foss.gui.creating.BatchCreateWaybillTableModel.tableHeader.one"),
			i18n.get("foss.gui.creating.BatchCreateWaybillTableModel.tableHeader.two"),
			i18n.get("foss.gui.creating.BatchCreateWaybillTableModel.tableHeader.three"),
			i18n.get("foss.gui.creating.BatchCreateWaybillTableModel.tableHeader.four"),
			i18n.get("foss.gui.creating.BatchCreateWaybillTableModel.tableHeader.five"),
			i18n.get("foss.gui.creating.BatchCreateWaybillTableModel.tableHeader.six"),
			i18n.get("foss.gui.creating.BatchCreateWaybillTableModel.tableHeader.seven"),
			i18n.get("foss.gui.creating.BatchCreateWaybillTableModel.tableHeader.eight"),
			i18n.get("foss.gui.creating.BatchCreateWaybillTableModel.tableHeader.nine"),
			i18n.get("foss.gui.creating.BatchCreateWaybillTableModel.tableHeader.ten"),
			i18n.get("foss.gui.creating.BatchCreateWaybillTableModel.tableHeader.eleven"),
			i18n.get("foss.gui.creating.BatchCreateWaybillTableModel.tableHeader.twelve"),
			i18n.get("foss.gui.creating.BatchCreateWaybillTableModel.tableHeader.thirteen"),
			i18n.get("foss.gui.creating.BatchCreateWaybillTableModel.tableHeader.fourteen"),
			i18n.get("foss.gui.creating.BatchCreateWaybillTableModel.tableHeader.fifteen"),
			i18n.get("foss.gui.creating.BatchCreateWaybillTableModel.tableHeader.sixteen"),
			i18n.get("foss.gui.creating.BatchCreateWaybillTableModel.tableHeader.seventeen"),
			i18n.get("foss.gui.creating.BatchCreateWaybillTableModel.tableHeader.eighteen"),
			i18n.get("foss.gui.creating.BatchCreateWaybillTableModel.tableHeader.nineteen"),
			i18n.get("foss.gui.creating.BatchCreateWaybillTableModel.tableHeader.twenty"),
			i18n.get("foss.gui.creating.BatchCreateWaybillTableModel.tableHeader.twentyOne"),
			i18n.get("foss.gui.creating.BatchCreateWaybillTableModel.tableHeader.twentyTwo"),
			i18n.get("foss.gui.creating.BatchCreateWaybillTableModel.tableHeader.twentyThree"),
			i18n.get("foss.gui.creating.BatchCreateWaybillTableModel.tableHeader.twentyFour"),
			i18n.get("foss.gui.creating.BatchCreateWaybillTableModel.tableHeader.twentyFive")};
	private Object[][] data;
	
	public BatchCreateWaybillTableModel(Object[][] data){
		this.data = data;
	}
	
	@Override
	public int getColumnCount() {
		
		return tableHeader.length;
		
	}

	@Override
	public int getRowCount() {
		if(data != null){
			return data.length;
		}else	
		{
			return 0;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(data != null){
			try{
				return data[rowIndex][columnIndex];
			}catch(Exception e){
				return null;
			}
		}else{	
			return null;
		}
		 
	}

	@Override
	public String getColumnName(int column) {
		
		return tableHeader[column];
	}

	/**
	 * 默认情况下这个方法不用重新实现的，但是这样就会造成如果这个列式boolean的类型，就当做string来处理了
	 * 如果是boolean的类型那么用checkbox来显示
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		Object o = getValueAt(0, columnIndex);
		if (o != null) {
			return o.getClass(); 
		} else { 
			return Null.class; 
		}
		
	}

 
	/**
	 * 来判断当前选中的单元格是够可以被编辑，因为我是从第二列需要可以编辑的，也就是复选框的列可以编辑的，故
	 * 我有个逻辑判断的哈
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex == 0 || columnIndex ==2) {
			return true;
		}
		return false;
	}

	/**
	 * 如果这个列可以被编辑的话，但是没有这个方法，当回车后是恢复之前的内容的，只有在这个地方通过对新值的
	 * 获取然后再设置进去才会被更改的。
	 */
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		log.info("[row: "+ rowIndex + ", column: " + columnIndex + ", value: " + value + " ]");
		data[rowIndex][columnIndex] = value;
	}
	
	public static void adjustColumnPreferredWidths(JXTable table) {
		// strategy - get max width for cells in column and
		// make that the preferred width
		TableColumnModel columnModel = table.getColumnModel();
		for (int col = 0; col < table.getColumnCount(); col++) {
			
			TableColumn column = columnModel.getColumn(col);
			
			if(col==0 || col==1){
				column.setPreferredWidth(NumberConstants.NUMBER_40);
				column.setMaxWidth(NumberConstants.NUMBER_40);
			}
			if(col==2){
				column.setPreferredWidth(NUM_125);
			}else{
				column.setPreferredWidth(NumberConstants.NUMBER_80);
			}
			
		} // for col
	}

	public Object[][] getData() {
		return data;
	}

	public void setData(Object[][] data) {
		this.data = data;
	}
	
}
