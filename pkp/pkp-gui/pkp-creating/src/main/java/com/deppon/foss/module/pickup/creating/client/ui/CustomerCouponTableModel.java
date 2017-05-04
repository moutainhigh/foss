package com.deppon.foss.module.pickup.creating.client.ui;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.commons.lang.ObjectUtils.Null;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;

/**
 * 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author hbhk 
* @date 2015-6-15 上午11:30:41
 */
public class CustomerCouponTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1016386589304301730L;
		
		// 常量配置区
		private String[] tableHeader = {
				"编号",
				"优惠券编码",
				"面额",
				"有效期"
		};
		
		private Object[][] data;
		
		public CustomerCouponTableModel(Object[][] data){
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
			return false;
		}
		/**
		 * 如果这个列可以被编辑的话，但是没有这个方法，当回车后是恢复之前的内容的，只有在这个地方通过对新值的
		 * 获取然后再设置进去才会被更改的。
		 */
		@Override
		public void setValueAt(Object value, int rowIndex, int columnIndex) {
			data[rowIndex][columnIndex] = value;
		}
		
		public Object[][] getData() {
			return data;
		}

		public void setData(Object[][] data) {
			this.data = data;
		}
		
		public static void adjustColumnPreferredWidths(JXTable table) {
			// strategy - get max width for cells in column and
			// make that the preferred width
			TableColumnModel columnModel = table.getColumnModel();
			for (int col = 0; col < table.getColumnCount(); col++) {
				
				TableColumn column = columnModel.getColumn(col);
				if (col == 0) {
					column.setPreferredWidth(NumberConstants.NUMBER_200);
				} else if (col == 1) {
					column.setPreferredWidth(NumberConstants.NUMBER_50);
					column.setMaxWidth(NumberConstants.NUMBER_50);
				} 
			} // for col
		}
		

	}