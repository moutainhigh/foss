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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/action/QuerySalesDepartmentAction.java
 * 
 * FILE NAME        	: QuerySalesDepartmentAction.java
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
package com.deppon.foss.module.pickup.changing.client.action;

import java.awt.event.ActionEvent;

import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.changing.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changing.client.utils.Common;
import com.deppon.foss.module.pickup.changing.client.vo.WaybillInfoVo;
import com.deppon.foss.module.pickup.common.client.ui.dialog.SalesDepartmentDialog;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;

/**
 * （查询收货部门）
 * 
 * @author 025000-foss-helong
 * @date 2012-11-1 下午7:59:52
 */
public class QuerySalesDepartmentAction implements IButtonActionListener<WaybillRFCUI> {

	// 主界面
	private WaybillRFCUI ui;

	/**
	 * 
	 * 查询收货部门
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午5:24:31
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		WaybillInfoVo bean = ui.getBinderWaybill();

		try {
			//弹出查询收货部门对话框
//			SalesDepartmentDialog dialog = new SalesDepartmentDialog();
			SalesDepartmentDialog dialog = new SalesDepartmentDialog(ui.getImportPanel().getTxtWaybillNo().getText());
			// 剧中显示弹出窗口
			WindowUtil.centerAndShow(dialog);
			//获得收获部门信息
			SaleDepartmentEntity entity = dialog.getSaleDepartmentEntity();
			//设置收获部门信息
			Common.setSalesDepartmentForCentrial(entity, bean, ui);
			
			if(entity!=null){
				if(entity.getCode()!=null && !entity.getCode().equals(bean.getReceiveOrgCode())){
					//修改收货部门需要重新计算总运费
					ui.getButtonPanel().getBtnSubmit().setEnabled(false);
				}
			}
		} catch (WaybillValidateException w) {
			MsgBox.showInfo(w.getMessage());
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