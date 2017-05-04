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
 * PROJECT NAME	: pkp-changingexp
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/action/QueryBankAccountAction.java
 * 
 * FILE NAME        	: QueryBankAccountAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-creating
 * PACKAGE NAME: com.deppon.foss.module.pickup.creating.client.action
 * FILE    NAME: WaybillSaveAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.changingexp.client.action;

import java.awt.event.ActionEvent;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.pickup.changingexp.client.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.changingexp.client.service.WaybillRfcServiceFactory;
import com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changingexp.client.utils.Common;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;

/**
 * （查询发货客户银行账号信息）
 * 
 * @author 025000-foss-helong
 * @date 2012-11-1 下午7:59:52
 */
public class QueryBankAccountAction implements IButtonActionListener<WaybillRFCUI> {

	
	IWaybillRfcService waybillService = WaybillRfcServiceFactory.getWaybillRfcService();
	
	// 主界面
	WaybillRFCUI ui;
	
	// 国际化
	private static final II18n i18n = I18nManager.getI18n(QueryBankAccountAction.class);

	/**
	 * 
	 * （方法详细描述说明、方法参数的具体涵义）
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午4:51:25
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		WaybillPanelVo bean = ui.getBinderWaybill();
		
		try
		{
			validate(bean);
			//设置银行信息
			Common.setBankInfo(bean,ui,waybillService);
		} catch (WaybillValidateException w) {
			MsgBox.showInfo(w.getMessage());
		}
	}

	/**
	 * 
	 * 验证是否有银行账号信息
	 * @author 025000-FOSS-helong
	 * @date 2012-12-7 下午05:36:20
	 */
	private void validate(WaybillPanelVo bean)
	{
		if(bean.getDeliveryCustomerCode() == null || "".equals(bean.getDeliveryCustomerCode()))
		{
			throw new WaybillValidateException(i18n.get("foss.gui.creating.queryBankAccountAction.exception.nullDeliveryCustomerCode"));
		}
	}
	
	/**
	 * 
	 * UI注入
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午4:48:26
	 * @see com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener#setInjectUI(java.awt.Container)
	 */
	@Override
	public void setInjectUI(WaybillRFCUI ui) {
		this.ui = ui;
	}
	
}