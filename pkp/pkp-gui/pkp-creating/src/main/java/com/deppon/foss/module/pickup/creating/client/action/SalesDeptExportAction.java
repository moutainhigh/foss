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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/SalesDeptExportAction.java
 * 
 * FILE NAME        	: SalesDeptExportAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.creating.client.action.io.WaybillExporter;
import com.deppon.foss.module.pickup.creating.client.service.ISalesDeptWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillOffLinePendingService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.service.impl.WaybillOffLinePendingService;
import com.deppon.foss.module.pickup.creating.client.ui.SalesDeptWaybillUI;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;
import com.google.inject.Inject;

/**
 * 营业部出发运单导出
 * 
 * @author 105089-foss-yangtong
 * @date 2012-11-1 下午7:59:52
 */
@SuppressWarnings({ "unused", "rawtypes" })
public class SalesDeptExportAction extends
		AbstractButtonActionListener<SalesDeptWaybillUI> {
	
	// 国际化
	private static final II18n i18n = I18nManager.getI18n(SalesDeptExportAction.class);

	@Inject
	private ISalesDeptWaybillService salesDeptWaybillService;
	@Inject
	private IWaybillOffLinePendingService waybillOffLinePendingService;

	private SalesDeptWaybillUI ui;

	private List<WaybillPendingEntity> pendingList;

	@Override
	public void setIInjectUI(SalesDeptWaybillUI ui) {
		this.ui = ui;
	}

	/**
	 * 导出按钮功能
	 * 
	 * @author 105089-FOSS-yangtong
	 * @date 2012-10-16 下午02:46:47
	 * @param e
	 * @see com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		salesDeptWaybillService = WaybillServiceFactory
				.getSalesDeptWaybillService();
		waybillOffLinePendingService =  GuiceContextFactroy.getInjector().
				getInstance(WaybillOffLinePendingService.class);
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
		boolean flag = ui.getCheckBox().isSelected();
		if (flag) {
			pendingList = new ArrayList<WaybillPendingEntity>(); 
			WaybillPendingDto waybillPendingDto = new WaybillPendingDto();
			waybillPendingDto.setBillStartTime(ui.getZdStartDate().getDate());
			waybillPendingDto.setBillEndTime(ui.getZdEndDate().getDate());

			List<String> list= ui.getSelectExportWaybillNoList();
			
			for(String waybillNo : list ){
				WaybillPendingDto dto = waybillOffLinePendingService.queryPendingWaybillByNo(waybillNo);
				pendingList.add(dto.getWaybillPending());
			}
			WaybillExporter exporter = new WaybillExporter();
			exporter.exportFile(ui, pendingList, user.getId(),
					dept.getCode());
		} else {
			/*
			 * WaybillPendingEntity pendingEntity = new WaybillPendingEntity();
			 * pendingEntity.setBillStartTime(ui.getZdStartDate().getDate());
			 * pendingEntity.setBillEndTime(ui.getZdEndDate().getDate());
			 * 
			 * pendingList =
			 * salesDeptWaybillService.queryPending(pendingEntity);
			 * WaybillExporter.exportFile(ui, pendingList, user.getId(),
			 * dept.getCode(), WaybillPendingEntity.class);
			 */
			MsgBox.showError(i18n.get("foss.gui.creating.salesDeptExportAction.MsgBox.notOffLineWaybill"));
		}

	}

}