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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/CreatePrintTimesDialogAction.java
 * 
 * FILE NAME        	: CreatePrintTimesDialogAction.java
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
package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.creating.client.ui.print.BarcodePrintDialog;
import com.deppon.foss.module.pickup.creating.client.ui.print.PrintTimesDialog;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;

/**
 * (触发打印次数对话框)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:jiangfei,date:2012-10-16 上午11:43:08,content:
 * </p>
 * 
 * @author jiangfei
 * @date 2012-10-16 上午11:43:08
 * @since
 * @version
 */
public class CreatePrintTimesDialogAction implements
		IButtonActionListener<WaybillEditUI> {

	WaybillEditUI ui;

	// 通过工厂类获得运单服务类
	private IWaybillService waybillService = WaybillServiceFactory.getWaybillService();


/**
	 * 国际化
	 */
private static II18n i18n = I18nManager.getI18n(CreatePrintTimesDialogAction.class);
	/**
	 * <p>
	 * ( 触发 打印预览 或者 运单打印对话框)
	 * </p>
	 * 
	 * @author jiangfei
	 * @date 2012-10-17 下午9:30:23
	 * @param arg0
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent event) {
		if (i18n.get("foss.gui.creating.buttonPanel.preview.label").equals(event.getActionCommand())) {
			new PrintTimesDialog(false, ui);
		} else if (i18n.get("foss.gui.creating.buttonPanel.print.label").equals(event.getActionCommand())) {
			new PrintTimesDialog(true, ui);
		} else if (i18n.get("foss.gui.creating.buttonPanel.printLabel.label").equals(event.getActionCommand())) {
			WaybillPanelVo waybillPanelVo = ui.getBindersMap().get("waybillBinder").getBean();
			/**
			 * 获取运单状态
			 */
			String waybillStatus=waybillPanelVo.getWaybillstatus();
			if(waybillPanelVo.getWaybillNo()!=null && !"".equals(waybillPanelVo.getWaybillNo())){
				WaybillPendingDto dto=waybillService.queryPendingWaybill(waybillPanelVo.getWaybillNo());
				if(waybillStatus==null || "".equals(waybillStatus)){
					if(dto!=null && dto.getWaybillPending()!=null){
						waybillStatus=dto.getWaybillPending().getPendingType();
					}
				}
			}			
			new BarcodePrintDialog(waybillPanelVo.getWaybillNo(),waybillStatus);
		}
	}

	@Override
	public void setInjectUI(WaybillEditUI ui) {
		this.ui = ui;
	}

}