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
 * PROJECT NAME	: pkp-changing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/listener/WaybillRfcAuthorizeComponentListener.java
 * 
 * FILE NAME        	: WaybillRfcAuthorizeComponentListener.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.changing.client.listener;

import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.util.Calendar;

import javax.swing.JComboBox;

import com.deppon.foss.module.pickup.changing.client.ui.WaybillRFCAuthorizeUI;

/**
 * 
 * 更改单数据初始化事件
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-31 下午6:07:05
 */
public class WaybillRfcAuthorizeComponentListener implements HierarchyListener {

	// 是否初始化完毕
	private boolean isInit;
	
	// UI
	private WaybillRFCAuthorizeUI waybillRFCAuthorizeUI;
	
	public WaybillRfcAuthorizeComponentListener(WaybillRFCAuthorizeUI waybillRFCAuthorizeUI) {
		this.waybillRFCAuthorizeUI = waybillRFCAuthorizeUI;
	}

	@Override
	public void hierarchyChanged(HierarchyEvent e) {
		if ((HierarchyEvent.SHOWING_CHANGED & e.getChangeFlags()) != 0 && e.getComponent().isShowing()) {
			//此方法会执行多次，需要判断
			if (!isInit) {
				//设置默认值
				setDefaultValue();
				// 初始化完毕
				isInit = true;
			}
		}
	}
	
	/**
	 * 
	 * 设置默认值
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-17 上午10:03:50
	 */
	public void setDefaultValue() {
		//代理人为空
		waybillRFCAuthorizeUI.getTxtUser().setEmployeeEntities(null);
		waybillRFCAuthorizeUI.getTxtUser().getTxtField().setText("");
	
		//生效类型默认为“全部”
		JComboBox comboBox = waybillRFCAuthorizeUI.getComboBoxType();
		if(comboBox.getItemCount()>0){
			comboBox.setSelectedIndex(0);
		}
		//生效开始时间和结束时间分别为当月第一天与当月最后一天，精确到日
		Calendar calendar = Calendar.getInstance();
		int end=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		int begin=calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		
		//设置开始时间
		calendar.set(Calendar.DAY_OF_MONTH, begin);
		waybillRFCAuthorizeUI.getDatePickerStart().setDate(calendar.getTime());
		
		//设置结束时间
		calendar.set(Calendar.DAY_OF_MONTH, end);
		waybillRFCAuthorizeUI.getDatePickerEnd().setDate(calendar.getTime());
		
	}

}