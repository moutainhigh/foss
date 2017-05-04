package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.widget.calender.JXDateTimePicker;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpEWaybillTableMode;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpSalesDepartEWaybillUI;

/**
 *  营业部出发运单重置按钮
 * @author 105089-foss-yangtong
 * @date 2012-11-1 下午7:59:52
 */
public class ExpEWaybillSalesDeptResetAction extends AbstractButtonActionListener<ExpSalesDepartEWaybillUI> {
	// 国际化
	private static final II18n i18n = I18nManager.getI18n(ExpEWaybillSalesDeptResetAction.class);
		
	private ExpSalesDepartEWaybillUI ui;
	
	@Override
	public void setIInjectUI(ExpSalesDepartEWaybillUI ui) {
		this.ui = ui;
	}
	
	/**
	 * 按钮功能
	 * @author 105089-FOSS-yangtong
	 * @date 2012-10-16 下午02:46:47
	 * @param e
	 */
	@SuppressWarnings("static-access")
	public void actionPerformed(ActionEvent e) {
		JXTable table = ui.getTable();
		table.setModel(new ExpEWaybillTableMode(null));
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
		cal2.set(Calendar.HOUR_OF_DAY, NumberConstants.NUMBER_23); //23点 
		cal2.set(Calendar.MINUTE, NumberConstants.NUMBER_59);//59分
		cal2.set(Calendar.SECOND, NumberConstants.NUMBER_59);//59秒
		endTimePicker.setFormats(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		endTimePicker.setTimeFormat(DateFormat.getTimeInstance( DateFormat.MEDIUM));
		endTimePicker.setDate(cal2.getTime());
		
//		//FOSS提交时间
//		JXDateTimePicker fossStartTimePicker = ui.getFossStartDate();
//		Calendar cal3 = Calendar.getInstance();
//		cal3.add(Calendar.DATE, 0);
//		cal3.set(Calendar.HOUR_OF_DAY, 0); //0点 
//		cal3.set(Calendar.MINUTE, 0);//0分
//		cal3.set(Calendar.SECOND, 0);//0秒
//		fossStartTimePicker.setFormats(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
//		fossStartTimePicker.setTimeFormat(DateFormat.getTimeInstance( DateFormat.MEDIUM ) );
//		fossStartTimePicker.setDate(cal3.getTime());
//		
//		JXDateTimePicker fossEndTimePicker = ui.getFossdEndDate();
//		Calendar cal4 = Calendar.getInstance();
//		cal4.add(Calendar.DATE, 0);
//		cal4.set(Calendar.HOUR_OF_DAY, 23); //23点 
//		cal4.set(Calendar.MINUTE, 59);//59分
//		cal4.set(Calendar.SECOND, 59);//59秒
//		fossEndTimePicker.setFormats(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
//		fossEndTimePicker.setTimeFormat( DateFormat.getTimeInstance( DateFormat.MEDIUM ) );
//		fossEndTimePicker.setDate(cal4.getTime());
//		
//		//PDA扫描时间
//		JXDateTimePicker scanStartTimePicker = ui.getDateBeginScanTime();
//		Calendar cal5 = Calendar.getInstance();
//		cal5.add(Calendar.DATE,   0);
//		cal5.set(Calendar.HOUR_OF_DAY, 0); //0点 
//		cal5.set(Calendar.MINUTE, 0);//0分
//		cal5.set(Calendar.SECOND, 0);//0秒
//		scanStartTimePicker.setFormats(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
//		scanStartTimePicker.setTimeFormat( DateFormat.getTimeInstance( DateFormat.MEDIUM));
//		scanStartTimePicker.setDate(cal5.getTime());
//		
//		JXDateTimePicker scanEndTimePicker = ui.getDateEndScanTime();
//		Calendar cal6   =   Calendar.getInstance();
//		cal6.add(Calendar.DATE, 0);
//		cal6.set(Calendar.HOUR_OF_DAY, 23); //23点 
//		cal6.set(Calendar.MINUTE, 59);//59分
//		cal6.set(Calendar.SECOND, 59);//59秒
//		scanEndTimePicker.setFormats(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
//		scanEndTimePicker.setTimeFormat( DateFormat.getTimeInstance( DateFormat.MEDIUM));
//		scanEndTimePicker.setDate(cal6.getTime());
		ui.repaint();
		
		//运单号
		ui.getTxtMixNo().setText("");
		//订单号
//		ui.getTxtOrder().setText("");
		//发货客户编码
		ui.getTxtCustomerCode().setText("");
		//快递员工号
		ui.getTxtCreateUserCode().setText("");

		// 运单状态
		ui.getCombWaybillStatus().setSelectedIndex(0);
		//订单来源
//		ui.getComboOrderChannel().setSelectedIndex(0);
		//是否扫描
//		ui.getCombIsScan().setSelectedIndex(0);
		
		//设置是否查询异常数据为否
//		ui.getChkExceptMsg().setSelected(false);
		//全选按钮
		ui.getAllSelectCheckBox().setSelected(false);
		//设置异常数据为0
//		ui.getLblExceptMsg().setText(i18n.get("foss.gui.creating.salesDeptEWaybillUI.result.exceptResult"+0));
	}
}