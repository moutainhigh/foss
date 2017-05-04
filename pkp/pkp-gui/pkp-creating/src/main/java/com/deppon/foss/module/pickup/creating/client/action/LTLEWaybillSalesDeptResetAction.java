package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.widget.calender.JXDateTimePicker;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.creating.client.ui.LTLEWaybillManageTableModel;
import com.deppon.foss.module.pickup.creating.client.ui.LTLEWaybillManageUI;

/**
 * 零担电子面单管理页面的查询条件重置按钮
 * @author 305082
 *
 */
public class LTLEWaybillSalesDeptResetAction extends AbstractButtonActionListener<LTLEWaybillManageUI> {
	// 国际化
	private static final II18n i18n = I18nManager.getI18n(LTLEWaybillSalesDeptResetAction.class);
		
	private LTLEWaybillManageUI ui;
	
	private static final Integer TWENTY_THREE = 23;
	
	private static final Integer FIFTY_NINE = 59;
	
	@Override
	public void setIInjectUI(LTLEWaybillManageUI ui) {
		this.ui = ui;
	}
	
	/**
	 * 按钮功能
	 * @param e
	 */
	@SuppressWarnings("static-access")
	public void actionPerformed(ActionEvent e) {
		JXTable table = ui.getTable();
		table.setModel(new LTLEWaybillManageTableModel(null));
		ui.refreshTable(table);
		
		//下单时间
		JXDateTimePicker startTimePicker = ui.getZdStartDate();
		Calendar cal1 = Calendar.getInstance();
		cal1.add(Calendar.DATE, 0);
		cal1.set(Calendar.HOUR_OF_DAY, 0); //0点 
		cal1.set(Calendar.MINUTE, 0);//0分
		cal1.set(Calendar.SECOND, 0);//0秒
		startTimePicker.setFormats(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		startTimePicker.setTimeFormat( DateFormat.getTimeInstance(DateFormat.MEDIUM));
		startTimePicker.setDate(cal1.getTime());
		
		JXDateTimePicker endTimePicker = ui.getZdEndDate();
		Calendar cal2 = Calendar.getInstance();
		cal2.add(Calendar.DATE, 0);
		cal2.set(Calendar.HOUR_OF_DAY, TWENTY_THREE); //23点 
		cal2.set(Calendar.MINUTE, FIFTY_NINE);//59分
		cal2.set(Calendar.SECOND, FIFTY_NINE);//59秒
		endTimePicker.setFormats(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		endTimePicker.setTimeFormat(DateFormat.getTimeInstance( DateFormat.MEDIUM));
		endTimePicker.setDate(cal2.getTime());
		
		ui.repaint();
		
		//运单号
		ui.getTxtMixNo().setText("");
		//订单号
		ui.getTxtOrder().setText("");
		//发货客户编码
		ui.getTxtCustomerCode().setText("");

		// 运单状态
		ui.getCombWaybillStatus().setSelectedIndex(0);
		//设置导出数据默认无法导出
		ui.getExportButton().setEnabled(false);
		//设置标签状态默认无法推送
		ui.getPushLabelButton().setEnabled(false);
		//标签推送状态
		ui.getLabelStatus().setSelectedIndex(0);
		//全选按钮
		ui.getAllSelectCheckBox().setSelected(false);
		//设置异常数据为0
		ui.getLblExceptMsg().setText(i18n.get("foss.gui.creating.salesDeptEWaybillUI.result.exceptResult"+0));
	}
}
