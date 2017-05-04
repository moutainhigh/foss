package com.deppon.foss.module.pickup.creatingexp.client.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creatingexp.client.ui.commonUI.ExpReturnedGoodsWorkOrderDialog;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmReturnedGoodsDto;

public class ExpReturnedGoodsWorkOrder extends AbstractCellEditor implements  TableCellEditor, TableCellRenderer ,ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(ExpReturnedGoodsWaybillUI.class);

	//连接文字
	private String text = "";
	//当前行
	private int row;
	//tableModel
	private LinkTableMode tableModel;
	
	//初始化运单服务对象
	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();

	public ExpReturnedGoodsWorkOrder(TableColumn tc,LinkTableMode tableModel){
		this.tableModel = tableModel;
		tc.setCellEditor(this);  
        tc.setCellRenderer(this);
	}
	
	@Override
	public Object getCellEditorValue() {
		return text;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		String str = (value == null) ? "" : value.toString();
		JLabel label = new JLabel("<HTML><U>"+str+"</U></HTML>");
		label.setForeground(Color.blue);
		label.setHorizontalAlignment(JLabel.CENTER);
		return label;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		int rows = table.convertRowIndexToModel(row);
		text = (value == null) ? "" : value.toString();
		
		JButton butten = new JButton(text);
		butten.setHorizontalAlignment(JLabel.CENTER);
		butten.addActionListener(this);
		this.row = rows;
		return butten;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String workOrder = tableModel.getValueAt(this.row, NumberConstants.NUMBER_4).toString();
		if(workOrder != null && !"".equals(workOrder.trim())){
			CrmReturnedGoodsDto dto = new CrmReturnedGoodsDto();
			dto.setDealnumber(workOrder);
			List<CrmReturnedGoodsDto> dtos = waybillService.queryReturnGoodsWorkOrder(dto);
			if(dtos != null && dtos.size() > 0){
				CrmReturnedGoodsDto crmDto = dtos.get(0);
				
				ExpReturnedGoodsWorkOrderDialog dialog = new ExpReturnedGoodsWorkOrderDialog(crmDto);
				// 剧中显示弹出窗口
				WindowUtil.centerAndShow(dialog);
			}else{
				MsgBox.showInfo(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.error"));
			}
		}
	}

}
