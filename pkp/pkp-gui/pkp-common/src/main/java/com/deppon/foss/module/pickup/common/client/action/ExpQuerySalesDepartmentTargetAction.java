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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/action/QuerySalesDepartmentTargetAction.java
 * 
 * FILE NAME        	: QuerySalesDepartmentTargetAction.java
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
package com.deppon.foss.module.pickup.common.client.action;

import java.awt.event.ActionEvent;
import java.util.Map;

import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.common.client.ui.ExpQueryPublishPriceUI;
import com.deppon.foss.module.pickup.common.client.ui.dialog.SalesDepartmentDialog;
import com.deppon.foss.module.pickup.common.client.vo.QueryPublishPriceVo;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;

/**
 * （查询收货部门）
 * 
 * @author 025000-foss-helong
 * @date 2012-11-1 下午7:59:52
 */
public class ExpQuerySalesDepartmentTargetAction implements IButtonActionListener<ExpQueryPublishPriceUI> {

	// 主界面
	private ExpQueryPublishPriceUI ui;

	@Override
	public void actionPerformed(ActionEvent e) {

		Map<String, IBinder<QueryPublishPriceVo>> map = ui.getBindersMap();
		
	
		IBinder<QueryPublishPriceVo> waybillBinder = map.get("queryPublishPriceBinder");
		QueryPublishPriceVo bean = waybillBinder.getBean();

		try {
			SalesDepartmentDialog dialog = new SalesDepartmentDialog(false, "all");
			// 剧中显示弹出窗口
			WindowUtil.centerAndShow(dialog);

			SaleDepartmentEntity entity = dialog.getSaleDepartmentEntity();
			if (entity != null) {
				//收货部门编号
				bean.setTargetOrgCode(entity.getCode());
				//收货部门名称
				bean.setTargetOrgName(entity.getName());
			}
		} catch (WaybillValidateException w) {
			MsgBox.showInfo(w.getMessage());
		}
	}
	
	

	@Override
	public void setInjectUI(ExpQueryPublishPriceUI ui) {
		this.ui = ui;
	}

}