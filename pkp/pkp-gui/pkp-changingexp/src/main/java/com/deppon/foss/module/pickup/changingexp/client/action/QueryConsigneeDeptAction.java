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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/action/QueryConsigneeDeptAction.java
 * 
 * FILE NAME        	: QueryConsigneeDeptAction.java
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

import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changingexp.client.ui.dialog.InnerPickupSalesDepartmentDialog;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;

/**
 * （查询收货部门）
 * 
 * @author 025000-foss-helong
 * @date 2012-11-1 下午7:59:52
 */
public class QueryConsigneeDeptAction implements IButtonActionListener<WaybillRFCUI> {

	// 主界面
	WaybillRFCUI ui;

	@Override
	public void actionPerformed(ActionEvent e) {

		WaybillPanelVo bean = ui.getBinderWaybill();

		try {
			InnerPickupSalesDepartmentDialog dialog = new InnerPickupSalesDepartmentDialog();
			// 剧中显示弹出窗口
			WindowUtil.centerAndShow(dialog);

//			SaleDepartmentEntity entity = dialog.getSaleDepartmentEntity();
//			if (entity != null) {
//				//收货部门名称
//				bean.setReceiveCustomerContact(entity.getName());
//			}
			OrgAdministrativeInfoEntity entity = dialog.getOrgAdministrativeInfoEntity();
			if (entity != null) {
				//收货部门名称
				bean.setReceiveCustomerContact(entity.getName());
				//部门CODE
				bean.setReceiveCustomerContactId(entity.getCode());
			}
		} catch (WaybillValidateException w) {
			MsgBox.showInfo(w.getMessage());
		}
	}

	@Override
	public void setInjectUI(WaybillRFCUI ui) {
		this.ui = ui;
	}

}