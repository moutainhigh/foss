package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
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
import com.deppon.foss.module.pickup.creating.client.ui.CUBCWaybillManageTableModel;
import com.deppon.foss.module.pickup.creating.client.ui.CUBCWaybillManagerUI;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillLogEntity;
import com.deppon.foss.util.CollectionUtils;
import com.google.inject.Inject;

/**
 * 运单重新推送至CUBC按钮
 * 
 */
public class CUBCWaybillPushAction extends AbstractButtonActionListener<CUBCWaybillManagerUI> {
	public static final Logger LOGGER = LoggerFactory.getLogger(CUBCWaybillPushAction.class);
	// 国际化
	private static final II18n i18n = I18nManager.getI18n(CUBCWaybillPushAction.class);

	/**
	 * 定义常量数字
	 */
	private static final Integer ZERO = 0;
	
	private static final Integer ONE = 1;
	
	private static final Integer TWO = 2;
	
	private static final Integer THREE = 3;
	
	private static final Integer FOUR = 4;
	
	private static final Integer FIVE = 5;
	
	private static final Integer SIX = 6;
	
	private static final Integer SEVEN = 7;
	
	private static final Integer EIGHT = 8;
	
	private static final Integer NINE = 9;
	
	private static final Integer TEN = 10;
	
	private static final Integer ELEVEN = 11;
	
	private CUBCWaybillManagerUI ui;

	@Inject
	private IWaybillService wayBillService;
	
	@Override
	public void setIInjectUI(CUBCWaybillManagerUI ui) {
		this.ui = ui;
	}

	/**
	 * 重推标签按钮事件
	 */
	public void actionPerformed(ActionEvent e) {
		//获取远程调用服务
		wayBillService = WaybillServiceFactory.getWaybillService();
		//得到Jtablemodel对象
		CUBCWaybillManageTableModel model=(CUBCWaybillManageTableModel) ui.getTable().getModel();
		int count = model.getRowCount();
		if (count <= 0) {
			return;
		}
		
		//获取选中的运单Id
		List<String> selectExportWaybillNoList = ui.getSelectExportWaybillNoList();
		if(CollectionUtils.isEmpty(selectExportWaybillNoList)){
			MsgBox.showInfo(i18n.get("foss.gui.creating.ExpWaybillBatchChangeWeightUI.BatchChangeEWaybilInfoDialog.notSelect"));
			return;
		}
		//待推送的日志实体
		List<WaybillLogEntity> cubcLogEntitys=new ArrayList<WaybillLogEntity>();
		//取值并封装
		Object[][] data = model.getData();
		for (int i = 0; i < data.length; i++) {
			String waybillId =(String)data[i][ONE];
			if(selectExportWaybillNoList.contains(waybillId)){
				getSubCubcLogEntity(cubcLogEntitys, data, i, waybillId);
			}
		}
		
		
		//根据rowIndexs来判断是否有数据
		if(cubcLogEntitys.size()>ZERO){
			try {
				wayBillService.pushWaybillToCUBC(cubcLogEntitys);
				MsgBox.showInfo("已完成运单推送，稍候请输入单号查询推送结果!");
				final JXTable table = ui.getTable();
				//设置全选按钮
				ui.getAllSelectCheckBox().setSelected(false);
				// 刷新表格,重推标签后清空表格
				CUBCWaybillManageTableModel tableModel = new CUBCWaybillManageTableModel(null);
				table.setModel(tableModel);
				//隐藏相关的列
			    TableColumnModel tableColumnModel = table.getColumnModel();
			    //其实没有移除，仅仅隐藏而已  
			    TableColumn tc = tableColumnModel.getColumn(1);  
			    tableColumnModel.removeColumn(tc);
				ui.refreshTable(table);
			} catch (Exception e2) {
				MsgBox.showInfo(e2.getMessage());
			}
		}else{
			MsgBox.showInfo(i18n.get("foss.gui.creating.ExpWaybillBatchChangeWeightUI.BatchChangeEWaybilInfoDialog.notSelect"));
			return;
		}
		
	}
	
	/**
	 * 获取前台传递的运单日志实体集合
	 * @param cubcLogEntitys
	 * @param data
	 * @param i
	 * @param waybillId
	 */
	private void getSubCubcLogEntity(List<WaybillLogEntity> cubcLogEntitys,
			Object[][] data, int i, String waybillId) {
		WaybillLogEntity logEntity=new WaybillLogEntity();
		logEntity.setId(waybillId);
		logEntity.setWaybillNo((String)data[i][TWO]);
		logEntity.setRequestContent((String)data[i][THREE]);
		logEntity.setResponseContent((String)data[i][FOUR]);
		logEntity.setVersionNo((Long)data[i][FIVE]);
		String changing=i18n.get("foss.gui.creating.CUBCWaybillManagerUI.waybillStatus.changing");
		String creating=i18n.get("foss.gui.creating.CUBCWaybillManagerUI.waybillStatus.creating");
		if(creating.equals((String)data[i][SIX])){
			logEntity.setCode("ESB_FOSS2ESB_SYN_BILL_INFO");
		}else if(changing.equals((String)data[i][SIX])){
			logEntity.setCode("ESB_FOSS2ESB_FOSS_CUBC_MODIFY_SYN");
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String parseDate = (String)data[i][SEVEN];
		Date createTime=null;
		try {
			createTime = dateFormat.parse(parseDate);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		logEntity.setCreateTime(createTime);
		String status = (String) data[i][EIGHT];
		if(i18n.get("foss.gui.creating.waibillImporter.messageDialog.failed").equals(status)){
			status=WaybillConstants.FAIL;
		}else if(i18n.get("foss.gui.creating.CUBCWaybillManagerUI.waybillStatus.syncPending").equals(status)){
			status=WaybillConstants.SYNC_PENDING;
		}
		logEntity.setStatu(status);
		logEntity.setErrorMsg((String)data[i][NINE]);
		logEntity.setDesc1((String)data[i][TEN]);
		logEntity.setDesc2((String)data[i][ELEVEN]);
		cubcLogEntitys.add(logEntity);
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
