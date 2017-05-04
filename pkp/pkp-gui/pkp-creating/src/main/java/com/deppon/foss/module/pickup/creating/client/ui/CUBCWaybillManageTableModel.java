package com.deppon.foss.module.pickup.creating.client.ui;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.commons.lang.ObjectUtils.Null;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;

/**
 * CUBC推送日志状态tableModel
 * 配置数据模型的表格头
 *
 */
public class CUBCWaybillManageTableModel extends AbstractTableModel {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1016386589304301730L;
	
	private static final int FIFTY = 50;
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(CUBCWaybillManageTableModel.class);

	//log object日志
	private static Log log = LogFactory.getLog(CUBCWaybillManageTableModel.class);

	// 常量配置区
	private String[] tableHeader = {
			i18n.get("foss.gui.creating.linkTableMode.column.zero"),//选择
			"ID",//id号
			i18n.get("foss.gui.creating.linkTableMode.column.cubc.waybillno"),//运单号
			i18n.get("foss.gui.creating.linkTableMode.column.cubc.requestContent"),//请求消息
			i18n.get("foss.gui.creating.linkTableMode.column.cubc.responseContent"),//响应消息
			i18n.get("foss.gui.creating.linkTableMode.column.cubc.versionNo"),//版本号
			i18n.get("foss.gui.creating.linkTableMode.column.cubc.codeOrUrl"),//服务编码或请求路径
			i18n.get("foss.gui.creating.linkTableMode.column.cubc.createTime"),//创建时间
			i18n.get("foss.gui.creating.linkTableMode.column.cubc.statu"),//状态响应码
			i18n.get("foss.gui.creating.linkTableMode.column.cubc.errorMsg"),//异常信息
			i18n.get("foss.gui.creating.linkTableMode.column.cubc.desc1"),//接口描述
			i18n.get("foss.gui.creating.linkTableMode.column.cubc.desc2")//其他信息
	};
	
	private Object[][] data;
	
	public CUBCWaybillManageTableModel(Object[][] data){
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
		if(columnIndex == 0 ) {
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
			
			column.setPreferredWidth(FIFTY);
		}
	}

	public Object[][] getData() {
		return data;
	}

	public void setData(Object[][] data) {
		this.data = data;
	}
	
}
