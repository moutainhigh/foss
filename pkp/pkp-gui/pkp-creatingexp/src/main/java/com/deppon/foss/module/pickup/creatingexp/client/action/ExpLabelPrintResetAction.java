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
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpLinkTableModePrint;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpPrintSignUI;

/**
 * 重置按钮
 *@author 280747-FOSS-祝学
 * @date 2015-12-17 上午11:16:43 
 * @param e
 */
public class ExpLabelPrintResetAction extends AbstractButtonActionListener<ExpPrintSignUI> {
	private ExpPrintSignUI ui;
	
	@Override
	public void setIInjectUI(ExpPrintSignUI ui) {
		this.ui = ui;
	}
	
	/**
	 * 按钮功能:批量打印要实现的功能
	 * @author 220125   
	 * @date 2015-11-11 11:11:11 Yangxiaolong 
	 * @param e
	 */
	@SuppressWarnings("static-access")
	public void actionPerformed(ActionEvent e) {
		JXTable table = ui.getTable();
		table.setModel(new ExpLinkTableModePrint(null));	
		ui.refreshTable(table);
		
		//制单时间
		JXDateTimePicker startTimePicker = ui.getZdStartDate();
		Calendar cal1   =   Calendar.getInstance();
		cal1.add(Calendar.DATE,0);
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
		//开单人
		ui.getCreateUserCode().setText("");
		//全选按钮
		ui.getAllSelectCheckBox().setSelected(false);
		
	}

	
}