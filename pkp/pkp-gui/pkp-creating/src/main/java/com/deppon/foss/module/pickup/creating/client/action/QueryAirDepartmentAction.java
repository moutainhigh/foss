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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/QueryAirDepartmentAction.java
 * 
 * FILE NAME        	: QueryAirDepartmentAction.java
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
import java.util.List;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.common.client.ui.commonUI.AirSalesDepartmentDialog;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;

/**
 * （查询发货客户银行账号信息）
 * 
 * @author 025000-foss-helong
 * @date 2012-11-1 下午7:59:52
 */
public class QueryAirDepartmentAction implements IButtonActionListener<WaybillEditUI> {

	
	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	
	// 主界面
	WaybillEditUI ui;

	// 国际化
	private static final II18n i18n = I18nManager.getI18n(QueryAirDepartmentAction.class);
	
	@Override
	public void actionPerformed(ActionEvent e) {
		HashMap<String, IBinder<WaybillPanelVo>> map = ui.getBindersMap();
		IBinder<WaybillPanelVo> waybillBinder = map.get("waybillBinder");
		WaybillPanelVo bean = waybillBinder.getBean();

		List<OrgAdministrativeInfoEntity> airDept = waybillService.queryOrgAirDepartment();
		if(airDept != null && !airDept.isEmpty())
		{
			AirSalesDepartmentDialog panel = new AirSalesDepartmentDialog(airDept);
			// 居中显示弹出窗口
			WindowUtil.centerAndShow(panel);
			
			OrgAdministrativeInfoEntity entity = panel.getAirDepartmentEntity();
			//设置空运配载部门
			setAirDepartment(entity,bean);
		}else
		{
			MsgBox.showInfo(i18n.get("foss.gui.creating.queryAirDepartmentAction.MsgBox.nullAirDept"));
		}
	}
	
	
	/**
	 * 
	 * 设置空运配载部门
	 * @author 025000-FOSS-helong
	 * @date 2013-1-22 上午08:14:21
	 */
	private void setAirDepartment(OrgAdministrativeInfoEntity entity,WaybillPanelVo bean)
	{
		if(entity != null)
		{
			//组织名称
			bean.setLastLoadOrgCode(entity.getCode());
			//组织编码
			bean.setLastLoadOrgName(entity.getName());
		}
	}

	@Override
	public void setInjectUI(WaybillEditUI ui) {
		this.ui = ui;
	}
	
}