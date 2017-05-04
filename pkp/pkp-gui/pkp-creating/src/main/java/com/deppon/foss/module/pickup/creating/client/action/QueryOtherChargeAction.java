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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/QueryOtherChargeAction.java
 * 
 * FILE NAME        	: QueryOtherChargeAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.util.HashMap;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.creating.client.ui.customer.QueryOtherChargePanel;

/**
 * 
 * 运单QueryOtherChargeAction
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:16:43,
 * </p>
 * @author 105089-FOSS-yangtong
 * @date 2012-10-17 上午11:16:43
 * @since
 * @version
 */
public class QueryOtherChargeAction extends
		AbstractButtonActionListener<WaybillEditUI> {

	WaybillEditUI ui;
	
	// 国际化
	private static final II18n i18n = I18nManager.getI18n(QueryOtherChargeAction.class);
	
	/**
	 * 430 
	 */
	private static final int HEIGHT = 430;  
	/**
	 *1126
	 */
	private static final int WIDTH = 1126;  

	/**
	 * 
	 * <p>
	 * (计算总费用)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 下午02:46:47
	 * @param e
	 * @see com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		HashMap<String, IBinder<WaybillPanelVo>> map = ui.getBindersMap();
		IBinder<WaybillPanelVo> waybillBinder = map.get("waybillBinder");
		WaybillPanelVo bean = waybillBinder.getBean();
		
		if(bean.getCustomerPickupOrgCode() == null)
		{
			MsgBox.showInfo(i18n.get("foss.gui.creating.queryOtherChargeAction.MsgBox.nullCustomerPickupOrgCode"));
		}else
		{
			showOtherChargeDialog();
		}
	}
	
	
	/**
	 * 
	 * 计算计费重量
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-31 下午02:14:05
	 */
	private void showOtherChargeDialog() {
		QueryOtherChargePanel dialog = new QueryOtherChargePanel(ui);
		dialog.setSize(WIDTH, HEIGHT);
		// 剧中显示弹出窗口
		WindowUtil.centerAndShow(dialog);
	}

	@Override
	public void setIInjectUI(WaybillEditUI ui) {

		this.ui = ui;

	}

}