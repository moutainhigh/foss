package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.jdesktop.swingx.JXTable;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.widget.calender.JXDateTimePicker;
import com.deppon.foss.module.pickup.creating.client.ui.LTLEWaybillImportWeightUI;
import com.deppon.foss.module.pickup.creating.client.ui.ewaybill.LTLEWaybillTableModel;

/**
 * 重置按钮
 * 批量导入页面查询的所有条件重置为初始状态
 * @author 305082
 *
 */
public class LTLEWaybillResetWeightAction implements
		IButtonActionListener<LTLEWaybillImportWeightUI> {

	private LTLEWaybillImportWeightUI ui;
	
	private static final Integer SIX = 6;
	
	private static final Integer TWENTY_THREE = 23;
	
	private static final Integer FIFTY_NINE = 59;
	

	/**
	 * 重置按钮的具体操作
	 */
	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent e) {
		JXTable table = ui.getTable();
		LTLEWaybillTableModel model = ui.getTableModel();
		// model.setData(null);
		table.setModel(model);

		ui.refreshTable(table);
		// FOSS提交时间
		JXDateTimePicker fossStartTimePicker = ui.getImportStartTime();
		Calendar cal3 = Calendar.getInstance();
		cal3.add(Calendar.DATE, -SIX);
		cal3.set(Calendar.HOUR_OF_DAY, 0); // 0点
		cal3.set(Calendar.MINUTE, 0);// 0分
		cal3.set(Calendar.SECOND, 0);// 0秒
		fossStartTimePicker.setFormats(new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss"));
		fossStartTimePicker.setTimeFormat(DateFormat
				.getTimeInstance(DateFormat.MEDIUM));
		fossStartTimePicker.setDate(cal3.getTime());

		JXDateTimePicker fossEndTimePicker = ui.getImportEndTime();
		Calendar cal4 = Calendar.getInstance();
		cal4.add(Calendar.DATE, 0);
		cal4.set(Calendar.HOUR_OF_DAY, TWENTY_THREE); // 23点
		cal4.set(Calendar.MINUTE, FIFTY_NINE);// 59分
		cal4.set(Calendar.SECOND, FIFTY_NINE);// 59秒
		fossEndTimePicker
				.setFormats(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		fossEndTimePicker.setTimeFormat(DateFormat
				.getTimeInstance(DateFormat.MEDIUM));
		fossEndTimePicker.setDate(cal4.getTime());
		// 运单号
		ui.getTxtMixNo().setText("");
		// 发货客户编码
		ui.getTxtCustomerCode().setText("");
		// 运单状态
		ui.getCombRfcStatus().setSelectedIndex(0);
		//收货部门
		ui.getCombRfcTaskDept().setSelectedIndex(0);
	}

	@Override
	public void setInjectUI(LTLEWaybillImportWeightUI ui) {
		this.ui = ui;
	}
}