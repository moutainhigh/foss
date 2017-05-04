/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/SalesDeptResetAction.java
 * 
 * FILE NAME        	: SalesDeptResetAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.widget.calender.JXDateTimePicker;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpLinkTableMode;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpSalesDeptWaybillUI;

/**
 *  营业部出发运单重置按钮
 * 
 * @author 105089-foss-yangtong
 * @date 2012-11-1 下午7:59:52
 */
public class ExpSalesDeptResetAction extends AbstractButtonActionListener<ExpSalesDeptWaybillUI> {

	private ExpSalesDeptWaybillUI ui;
	
	@Override
	public void setIInjectUI(ExpSalesDeptWaybillUI ui) {
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
		table.setModel(new ExpLinkTableMode(null));
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
		
		//FOSS提交时间
		JXDateTimePicker fossStartTimePicker = ui.getFossStartDate();
		Calendar cal3   =   Calendar.getInstance();
		cal3.add(Calendar.DATE,   -1);
		cal3.set(Calendar.HOUR_OF_DAY, 0); //0点 
		cal3.set(Calendar.MINUTE, 0);//0分
		cal3.set(Calendar.SECOND, 0);//0秒
		fossStartTimePicker.setFormats( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		fossStartTimePicker.setTimeFormat( DateFormat.getTimeInstance( DateFormat.MEDIUM ) );
		fossStartTimePicker.setDate(cal3.getTime());
		
		JXDateTimePicker fossEndTimePicker = ui.getFossdEndDate();
		Calendar cal4   =   Calendar.getInstance();
		cal4.add(Calendar.DATE, 0);
		cal4.set(Calendar.HOUR_OF_DAY, NumberConstants.NUMBER_23); //23点 
		cal4.set(Calendar.MINUTE, NumberConstants.NUMBER_59);//59分
		cal4.set(Calendar.SECOND, NumberConstants.NUMBER_59);//59秒
		fossEndTimePicker.setFormats( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		fossEndTimePicker.setTimeFormat( DateFormat.getTimeInstance( DateFormat.MEDIUM ) );
		fossEndTimePicker.setDate(cal4.getTime());
		ui.repaint();
		
		//运单号、订单号
		ui.getTxtMixNo().setText("");
		//初始化下拉框
		ui.initComboBox();
		//开单人
		ui.getCreateUserCode().setText("");
		//离线运单待提交
		ui.getCheckBox().setSelected(false);
		//全选按钮
		ui.getAllSelectCheckBox().setSelected(false);
		
	}

	
}