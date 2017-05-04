package com.deppon.foss.module.pickup.creating.client.ui.ewaybill;

import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import org.apache.commons.lang.ObjectUtils.Null;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.module.pickup.waybill.shared.dto.LTLEWaybillChangeWeightDto;

/** 
 * 零担电子面单批量导入重量体积的tableModel
 * 配置表格展示的表格头信息
 * @author 305082
 *
 */
public class LTLEWaybillTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 7803887726781613781L;

	/**
	 *  日志
	 */
	public static final Logger LOGGER = LoggerFactory.getLogger(LTLEWaybillTableModel.class);

	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(LTLEWaybillTableModel.class);

	 
	private Vector rowData;
	private Vector columnData;

	//常量配置区，设置表格头
	private String[] tableHeader = {
				i18n.get("foss.gui.creating.linkTableMode.column.zero"),//选择
				i18n.get("foss.gui.creating.linkTableMode.column.one"),//操作列
				i18n.get("foss.gui.creating.LTLEWaybillChangeWeightUI.comboWaybillStatusModel.changeStatus"),//更改状态
				i18n.get("foss.gui.creating.numberPanel.waybillNo.label"),//运单号
				i18n.get("foss.gui.creating.salesDeptWaybillUI.labelOrder"),//订单号
				i18n.get("foss.gui.creating.waybillPendingCompleteAction.MsgBox.createOrgCode"),//开单部门
				i18n.get("foss.gui.creating.linkTableMode.column.eleven"),//重量
				i18n.get("foss.gui.creating.linkTableMode.column.twelve"),//体积
				i18n.get("foss.gui.creating.linkTableMode.column.thirteen"),//件数
				i18n.get("foss.gui.creating.LTLEWaybillChangeWeightUI.comboWaybillStatusModel.totalFee"),//费用信息
				i18n.get("foss.gui.creating.linkTableMode.column.TransportType"),//运输类型
				i18n.get("foss.gui.creating.LTLEWaybillChangeWeightUI.comboWaybillStatusModel.exception")//异常原因
		};

	
	public LTLEWaybillTableModel(List<LTLEWaybillChangeWeightDto> data) {
		rowData=listToVector(data);
		columnData=toVector(tableHeader);
	}
	@Override
	public int getRowCount() {
		return rowData==null?0:rowData.size();
	}

	@Override
	public int getColumnCount() {
		return columnData==null?0:columnData.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return ((Vector) rowData.elementAt(rowIndex)).elementAt(columnIndex);
	}

	@Override
	public String getColumnName(int column) {
		return (String) this.columnData.get(column);
	}
	
	/**
	 * 默认情况下这个方法不用重新实现的，但是这样就会造成如果这个列式boolean的类型，就当做string来处理了
	 * 如果是boolean的类型那么用checkbox来显示
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if(rowData.size()>0){
		Object o = getValueAt(0, columnIndex);
		if (o != null) {
			return o.getClass(); 
		} else { 
			return Null.class; 
		}
		}
		return Null.class; 
	}
	
	//CheckBox状态改变后，设置单元格数据模型的值，并更新单元格视图。
    public void setValueAt(Object value, int row, int col) {
        ((Vector) rowData.get(row)).set(col, value);
        this.fireTableCellUpdated(row, col);
    }

	/**
	 * 来判断当前选中的单元格是够可以被编辑，因为我是从第二列需要可以编辑的，也就是复选框的列可以编辑的，故
	 * 我有个逻辑判断的哈
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex == 0) {
			return true;
		}
		return false;
	}


	  //一维数组转一维向量  
    public Vector<String> toVector(String[] arrObj){  
        Vector<String> vecObj=new Vector<String>();  
        for(String obj:arrObj){  
            vecObj.add(obj);  
        }  
        return vecObj;  
    }   
	//表格展示的具体数据，此处的字段要跟常量配置区的字段对应
    private Vector listToVector(List<LTLEWaybillChangeWeightDto> list) {
		Vector rowData = new Vector();
		int i=1;
		if(list!=null){
		for (LTLEWaybillChangeWeightDto ebcw : list) {
			Vector row = new Vector();
			row.add(false);//选择
			row.add((i++)+"");//操作列
			row.add(getCNValue(ebcw.getChangeStatus()));//更改状态
			row.add(ebcw.getWaybillNo());//运单号
			row.add(ebcw.getOrderNo());//定单号
			row.add(ebcw.getCreateOrgName());//开单部门
			row.add(ebcw.getWeightChanged());//重量
			row.add(ebcw.getVolumeChanged());//体积
			row.add(ebcw.getGoodsQtyTotal());//件数
			row.add(ebcw.getTotalFee());//费用信息
			row.add(ebcw.getProductCode());//运输类型
			row.add(i18n.get(ebcw.getFailReason()));//异常原因
			rowData.add(row);
		}
		}
		return rowData;
	}
	private Object getCNValue(String changeStatus) {
		if("RFC_SUCCESS".equals(changeStatus)){
			changeStatus = i18n.get("foss.gui.creating.LTLEWaybillChangeWeightUI.comboWaybillStatusModel.changeSuccess");
		}else if("RFC_FAIL".equals(changeStatus)){
			changeStatus = i18n.get("foss.gui.creating.LTLEWaybillChangeWeightUI.comboWaybillStatusModel.changeFail");
		}else if("NOT_IMPORT".equals(changeStatus)){
			changeStatus = i18n.get("foss.gui.creating.LTLEWaybillChangeWeightUI.comboWaybillStatusModel.notImport");
		}
		return changeStatus;
	}
	
}

