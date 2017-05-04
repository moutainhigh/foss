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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/SalesDeptDeleteAction.java
 * 
 * FILE NAME        	: SalesDeptDeleteAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.creating.client.service.ISalesDeptWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpSalesDepartEWaybillUI;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpSalesDeptWaybillUI;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillImportException;
import com.google.inject.Inject;

/**
 *  营业部出发运单删除
 * 
 * @author 105089-foss-yangtong
 * @date 2012-11-1 下午7:59:52
 */
@SuppressWarnings("unused")
public class ExpEWaybillSalesDeptDeleteAction extends AbstractButtonActionListener<ExpSalesDepartEWaybillUI> {
	
	// 国际化
	private static final II18n i18n = I18nManager.getI18n(ExpEWaybillSalesDeptDeleteAction.class);
	
	@Inject
	private ISalesDeptWaybillService salesDeptWaybillService = WaybillServiceFactory.getSalesDeptWaybillService();
	
	private ExpSalesDepartEWaybillUI ui;
	
	private IWaybillService wayBillService =  WaybillServiceFactory.getWaybillService();
	
	@Override
	public void setIInjectUI(ExpSalesDepartEWaybillUI ui) {
		this.ui = ui;
	}
	
	/**
	 * 删除按钮功能
	 * @author 105089-FOSS-yangtong
	 * @date 2012-10-16 下午02:46:47
	 * @param e
	 * @see 
	 */
	public void actionPerformed(ActionEvent e) {
		try {
			//运单号
			String mixNo = ui.getTxtMixNo().getText().trim();
			
			//数据字典
			DataDictionaryValueVo vo =null;
			
			//获得表格数据
			JXTable table = ui.getTable();
			
			ExpEWaybillSalesDeptSearchAction searchAction = new ExpEWaybillSalesDeptSearchAction();
			searchAction.setIInjectUI(ui);
			ActionEvent event = new ActionEvent(this, 1, "");
			searchAction.actionPerformed(event);
			
		} catch (BusinessException ee) {
			MsgBox.showError(i18n.get("foss.gui.creating.salesDeptDeleteAction.MsgBox.deleteErorr")+StringUtil.defaultIfNull(ee.getMessage()));
		}
	}
}