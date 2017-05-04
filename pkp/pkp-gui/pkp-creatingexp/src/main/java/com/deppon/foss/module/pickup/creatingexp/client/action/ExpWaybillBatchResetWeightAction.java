package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.widget.calender.JXDateTimePicker;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillBatchChangeWeightUI;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ewaybill.BatchChangeInfoTableModel;

/**
 * 
 * 批量更改重量
 * 
 * @author 136334-foss-bailei
 * @date 2015-1-29 下午1:54:16
 */
public class ExpWaybillBatchResetWeightAction implements
		IButtonActionListener<ExpWaybillBatchChangeWeightUI> {
	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager
			.getI18n(ExpWaybillBatchResetWeightAction.class);

	private static final Logger LOG = Logger
			.getLogger(ExpWaybillBatchResetWeightAction.class);

	private ExpWaybillBatchChangeWeightUI ui;

	/**
	 * 执行更改
	 * 
	 * @author 136334-foss-bailei
	 * @date 2015-1-24 下午5:27:34
	 */
	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent e) {
		JXTable table = ui.getTable();
		BatchChangeInfoTableModel model = ui.getTableModel();
		// model.setData(null);
		table.setModel(model);

		ui.refreshTable(table);
		// FOSS提交时间
		JXDateTimePicker fossStartTimePicker = ui.getImportStartTime();
		Calendar cal3 = Calendar.getInstance();
		cal3.add(Calendar.DATE, -NumberConstants.NUMBER_6);
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
		cal4.set(Calendar.HOUR_OF_DAY, NumberConstants.NUMBER_23); // 23点
		cal4.set(Calendar.MINUTE, NumberConstants.NUMBER_59);// 59分
		cal4.set(Calendar.SECOND, NumberConstants.NUMBER_59);// 59秒
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
	}

	@Override
	public void setInjectUI(ExpWaybillBatchChangeWeightUI ui) {
		this.ui = ui;
	}
}