package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.widget.calender.JXDateTimePicker;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpReturnedGoodsWaybillUI;
import com.deppon.foss.module.pickup.creatingexp.client.ui.LinkTableMode;

/**
 *  营业部出发运单重置按钮
 * 
 * @author 105089-foss-yangtong
 * @date 2012-11-1 下午7:59:52
 */
public class ExpReturnedGoodsResetAction extends AbstractButtonActionListener<ExpReturnedGoodsWaybillUI> {

	private ExpReturnedGoodsWaybillUI ui;
	
	@Override
	public void setIInjectUI(ExpReturnedGoodsWaybillUI ui) {
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
		table.setModel(new LinkTableMode(null));
		ui.refreshTable(table);
		
		//制单时间
		JXDateTimePicker startTimePicker = ui.getZdStartDate();
		Calendar cal1   =   Calendar.getInstance();
		cal1.add(Calendar.DATE,   -1);
		cal1.set(Calendar.HOUR_OF_DAY, 0); //0点 
		cal1.set(Calendar.MINUTE, 0);//0分
		cal1.set(Calendar.SECOND, 0);//0秒
		startTimePicker.setFormats( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		startTimePicker.setTimeFormat( DateFormat.getTimeInstance( DateFormat.MEDIUM ) );
		startTimePicker.setDate(cal1.getTime());
		
		JXDateTimePicker endTimePicker = ui.getZdEndDate();
		Calendar cal2   =   Calendar.getInstance();
		cal2.add(Calendar.DATE, 0);
		cal2.set(Calendar.HOUR_OF_DAY, NumberConstants.NUMBER_23); //23点 
		cal2.set(Calendar.MINUTE, NumberConstants.NUMBER_59);//59分
		cal2.set(Calendar.SECOND, NumberConstants.NUMBER_59);//59秒
		endTimePicker.setFormats( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		endTimePicker.setTimeFormat( DateFormat.getTimeInstance( DateFormat.MEDIUM ) );
		endTimePicker.setDate(cal2.getTime());
		
		ui.repaint();
		
		//原单号
		ui.getWaybillNo().setText("");
		//返单号
		ui.getReturnWaybillNo().setText("");
		//初始化下拉框
		ui.initComboBox();
		
	}

}