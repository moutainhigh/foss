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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/QueryBankAccountAction.java
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
package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.util.HashMap;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.pickup.creating.client.common.Common;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;

/**
 * （查询发货客户银行账号信息）
 * 
 * @author 025000-foss-helong
 * @date 2012-11-1 下午7:59:52
 */
public class QueryBankAccountAction implements IButtonActionListener<WaybillEditUI> {

	
	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	
	// 主界面
	WaybillEditUI ui;
	
	// 国际化
	private static final II18n i18n = I18nManager.getI18n(QueryBankAccountAction.class);

	@Override
	public void actionPerformed(ActionEvent e) {

		HashMap<String, IBinder<WaybillPanelVo>> map = ui.getBindersMap();
		IBinder<WaybillPanelVo> waybillBinder = map.get("waybillBinder");
		WaybillPanelVo bean = waybillBinder.getBean();
		
		try
		{
			validate(bean);
			//设置银行信息
			Common.setBankInfo(bean,ui,waybillService);
			//DP-FOSS zhaoyiqing 20161026 开单配合CUBC校验银行信息
			Common.validateBankInfoCUBC(bean);
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

	@Override
	public void setInjectUI(WaybillEditUI ui) {
		this.ui = ui;
	}
	
}