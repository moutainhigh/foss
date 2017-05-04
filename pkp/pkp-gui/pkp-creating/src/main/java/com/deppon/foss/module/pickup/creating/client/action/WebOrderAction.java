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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/WebOrderAction.java
 * 
 * FILE NAME        	: WebOrderAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.pickup.common.client.utils.ModalFrameUtil;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.creating.client.ui.order.WebOrderDialog;

/**
 * 
 * 网上订单Action
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-11-13 上午9:35:42
 */
public class WebOrderAction implements IButtonActionListener<WaybillEditUI> {

	private static final int NUM_1126 = 1126;
	// 主界面
	WaybillEditUI ui;
	/**
	 * 国际化
	 */
	private II18n i18n = I18nManager.getI18n(WebOrderAction.class);	
	@Override
	public void actionPerformed(ActionEvent e) {
		WaybillPanelVo panelVo = ui.getBindersMap().get("waybillBinder").getBean();
		if(panelVo.getPickupCentralized()){
			//营业部可以在自己部门集中开单
			if(panelVo.getReceiveOrgCode()!=null){
				/*WebOrderDialog dialog = new WebOrderDialog(ui);
				WindowUtil.centerAndShow(dialog);*/
				WebOrderDialog dialog = ui.getWebOrderDialog();
				
				
				/**
				 * 优化性能 CalculateCostsDialog初始化放到点击按钮事件中
				 * @author Foss-278328-hujinyang
				 * @date 2015-12-15 09:34:45
				 */
				dialog = new WebOrderDialog(ui);
				
				dialog.setSize(NUM_1126, NumberConstants.NUMBER_600);
				ModalFrameUtil.getInstance().showAsModal(dialog, ApplicationContext.getApplication().getWorkbench().getFrame());
			}else{
				MsgBox.showInfo(i18n.get("foss.gui.creating.webOrderAction.msgBox.nullReceiveOrgCode"));
			}
		}else{
			/*WebOrderDialog dialog = new WebOrderDialog(ui);
			WindowUtil.centerAndShow(dialog);*/
			WebOrderDialog dialog = ui.getWebOrderDialog();
			/**
			 * 优化性能 CalculateCostsDialog初始化放到点击按钮事件中
			 * @author Foss-278328-hujinyang
			 * @date 2015-12-15 09:34:45
			 */
			dialog = new WebOrderDialog(ui);
			
			dialog.setSize(NUM_1126, NumberConstants.NUMBER_600);
			ModalFrameUtil.getInstance().showAsModal(dialog, ApplicationContext.getApplication().getWorkbench().getFrame());
		}
	}

	@Override
	public void setInjectUI(WaybillEditUI ui) {
		this.ui = ui;
	}
}