/**   
* @Title: BatchChangeInfoTableModel.java 
* @Package com.deppon.foss.module.pickup.creatingexp.client.ui.ewaybill 
* @Description: 大客户电子更改单table模版
* @author 270293   
* @date 2015-7-16 上午9:11:39 
* @version V2.0   
*/
package com.deppon.foss.module.pickup.creatingexp.client.ui.ewaybill;

import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import org.apache.commons.lang.ObjectUtils.Null;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpBatchChangeWeightDto;

/** 
 * @ClassName: BatchChangeInfoTableModel 
 * @Description: 大客户电子更改单table模版
 * @author 270293-foss-zhangfeng 
 * @date 2015-7-16 上午9:11:39 
 *  
 */
public class BatchChangeInfoTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 5883365603131625962L;
	/**
	 *  日志
	 */
	public static final Logger LOGGER = LoggerFactory.getLogger(BatchChangeInfoTableModel.class);

	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(BatchChangeInfoTableModel.class);

	 
	private Vector rowData;
	private Vector columnData;

	private String[] tableHeader = {
				i18n.get("foss.gui.creating.expEWaybillTableMode.column.zero"),//选择
				i18n.get("foss.gui.creating.expEWaybillTableMode.column.one"),//操作列
				i18n.get("foss.gui.creating.ExpWaybillBatchChangeWeightUI.comboWaybillStatusModel.changeStatus"),//单号
				i18n.get("foss.gui.creating.salesDeptWaybillUI.labelOrder"),//定单号
				i18n.get("foss.gui.creating.numberPanel.waybillNo.label"),//运单号
				i18n.get("foss.gui.creating.linkTableMode.column.eleven"),//重量
				i18n.get("foss.gui.creating.linkTableMode.column.twelve"),//体积
				//start --   auto：  zhoupengyu   265041
				//i18n.get("foss.gui.creating.waybillEditUI.ServiceFee.label"),//装卸费
				// -- end
				i18n.get("foss.gui.creating.ExpWaybillBatchChangeWeightUI.comboWaybillStatusModel.totalFee"),//费用信息
				i18n.get("foss.gui.creating.ExpWaybillBatchChangeWeightUI.comboWaybillStatusModel.product"),//产品性质
				i18n.get("foss.gui.creating.ui.print.ChoosePrintTypeDialog.chinese.remark"),//更改备注
				i18n.get("foss.gui.creating.ExpWaybillBatchChangeWeightUI.comboWaybillStatusModel.failReason")//发货联系人
		};

	
	public BatchChangeInfoTableModel(List<ExpBatchChangeWeightDto> data) {
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
    public Vector<String> toVector(String[] arr1_obj){  
        Vector<String> vec1_obj=new Vector<String>();  
        for(String obj:arr1_obj){  
            vec1_obj.add(obj);  
        }  
        return vec1_obj;  
    }   
	
    private Vector listToVector(List<ExpBatchChangeWeightDto> list) {
		Vector rowData = new Vector();
		int i=1;
		if(list!=null){
		for (ExpBatchChangeWeightDto ebcw : list) {
			Vector row = new Vector();
			row.add(false);
			row.add((i++)+"");
			row.add(getCNValue(ebcw.getChangeStatus()));
			row.add(ebcw.getOrderNo());
			row.add(ebcw.getWaybillNo());
			row.add(ebcw.getWeightChanged());
			row.add(ebcw.getVolumeChanged());
			//row.add(ebcw.getServicefee());
			row.add(ebcw.getTransportFeeChanged());
			row.add(ebcw.getProductCodeChanged());
			row.add(ebcw.getChangeNote());
			row.add(i18n.get(ebcw.getFailReason()));                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
			rowData.add(row);
		}
		}
		return rowData;
	}
	private Object getCNValue(String changeStatus) {
		if("RFC_SUCCESS".equals(changeStatus)){
			changeStatus = i18n.get("foss.gui.creating.ExpWaybillBatchChangeWeightUI.comboWaybillStatusModel.changeSuccess");
		}else if("RFC_FAIL".equals(changeStatus)){
			changeStatus = i18n.get("foss.gui.creating.ExpWaybillBatchChangeWeightUI.comboWaybillStatusModel.changeFail");
		}else if("NOT_IMPORT".equals(changeStatus)){
			changeStatus = i18n.get("foss.gui.creating.ExpWaybillBatchChangeWeightUI.comboWaybillStatusModel.notImport");
		}
		return changeStatus;
	}
	
}

