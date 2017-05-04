package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.TableModel;

import org.jdesktop.swingx.JXTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.LTLEWaybillManageTableModel;
import com.deppon.foss.module.pickup.creating.client.ui.LTLEWaybillManageUI;
import com.google.inject.Inject;

/**
 * 电子面单重推标签按钮
 * 
 */
public class LTLEWaybillPushLabelAction extends AbstractButtonActionListener<LTLEWaybillManageUI> {
	public static final Logger LOGGER = LoggerFactory.getLogger(LTLEWaybillPushLabelAction.class);
	// 国际化
	private static final II18n i18n = I18nManager.getI18n(LTLEWaybillPushLabelAction.class);

	/**
	 * 定义常量数字
	 */
	private static final Integer ZERO = 0;
	
	private static final Integer ONE = 1;
	
	private static final Integer TWO = 2;
	
	private static final Integer THREE = 3;
	
	private LTLEWaybillManageUI ui;

	@Inject
	private IWaybillService wayBillService;
	
	@Override
	public void setIInjectUI(LTLEWaybillManageUI ui) {
		this.ui = ui;
	}

	/**
	 * 重推标签按钮事件
	 */
	public void actionPerformed(ActionEvent e) {
		//获取远程调用服务
		wayBillService = WaybillServiceFactory.getWaybillService();
		//得到Jtablemodel对象
		LTLEWaybillManageTableModel model=(LTLEWaybillManageTableModel) ui.getTable().getModel();
		int count = model.getRowCount();
		if (count <= 0) {
			return;
		}
		List<String> waybillNoList=new ArrayList<String>();
 		//获取选中数据行数索引
		boolean flag = true;
		for(int i=0;i<count;i++){
			String waybillNo = (String)model.getData()[i][THREE];
			String pushLabelStatus = (String)model.getData()[i][TWO];
			//判断是否选择
		   if(ui.getAllSelectCheckBox().isSelected()){
			   if (null == pushLabelStatus) {
				   if (flag) {
					   MsgBox.showInfo("生成待补录失败的运单不允许推送标签！");
					   flag = false;
				   }
			   }else {
				   waybillNoList.add(waybillNo);
			   }
		   }else{
			   Object isCheckboxStr=model.getValueAt(i, ZERO);
			   if(isCheckboxStr==null){
				   if (null == pushLabelStatus) {
					   if (flag) {
						   MsgBox.showInfo("生成待补录失败的运单不允许推送标签！");
						   flag = false;
					   }
				   }else {
					   waybillNoList.add(waybillNo);
				   }
			   }
		   }		
       	
		}
		//根据rowIndexs来判断是否有数据
		if(waybillNoList.size()>ZERO){
			try {
				wayBillService.pushLabelStatus(waybillNoList);
				MsgBox.showInfo("可推送数据标签状态已推送，稍候请输入单号查询推送结果!");
				final JXTable table = ui.getTable();
				//设置全选按钮
				ui.getAllSelectCheckBox().setSelected(false);
				// 刷新表格,重推标签后清空表格
				LTLEWaybillManageTableModel tableModel = new LTLEWaybillManageTableModel(null);
				table.setModel(tableModel);
				ui.refreshTable(table);
			} catch (Exception e2) {
				MsgBox.showInfo(e2.getMessage());
			}
		}else{
			if (flag) {
				MsgBox.showInfo(i18n.get("foss.gui.creating.ExpWaybillBatchChangeWeightUI.BatchChangeEWaybilInfoDialog.notSelect"));
				return;
			}
		}
		
	}
	
	//获取table列名称
	public String[] getColumnNames(TableModel model){
		String[] columnNames=new String[model.getColumnCount()]; 
		for(int i=ONE;i<model.getColumnCount();i++){
			columnNames[i-ONE]=model.getColumnName(i).toString();
		}
		return columnNames;
	}
	
	//获取jtble数据
	public List<Object> getRowData(TableModel model,List<Integer> rowIndexs){
		List<Object> rowList=new ArrayList<Object>();
	    for(int i:rowIndexs) {
	    	String[] rowData=new String[model.getColumnCount()];
	    	for(int j=ONE;j<model.getColumnCount();j++){
	    	rowData[j-ONE]=model.getValueAt(i, j)+"";
	    	}
	    	rowList.add(rowData);
	    }
	    return rowList;
	}

	
	
}
