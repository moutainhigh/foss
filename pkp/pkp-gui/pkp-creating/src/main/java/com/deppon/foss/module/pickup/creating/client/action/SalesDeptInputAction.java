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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/SalesDeptInputAction.java
 * 
 * FILE NAME        	: SalesDeptInputAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JOptionPane;

import org.jdesktop.swingx.JXTable;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.creating.client.action.io.WaibillImporter;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillOffLinePendingService;
import com.deppon.foss.module.pickup.creating.client.service.impl.WaybillOffLinePendingService;
import com.deppon.foss.module.pickup.creating.client.ui.LinkTableMode;
import com.deppon.foss.module.pickup.creating.client.ui.OfflineButtonColumn;
import com.deppon.foss.module.pickup.creating.client.ui.SalesDeptWaybillUI;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.util.UUIDUtils;
import com.google.inject.Inject;

/**
 *  营业部出发运单导入
 * 
 * @author 105089-foss-yangtong
 * @date 2012-11-1 下午7:59:52
 */
public class SalesDeptInputAction extends AbstractButtonActionListener<SalesDeptWaybillUI> {
	
	// 国际化
	private static final II18n i18n = I18nManager.getI18n(SalesDeptInputAction.class);
	
	@Inject
	IWaybillOffLinePendingService offLinePendingService;
	
	private SalesDeptWaybillUI ui;
	
	@Override
	public void setIInjectUI(SalesDeptWaybillUI ui) {
		this.ui = ui;
	}
	
	/**
	 * 导入按钮功能
	 * @author 105089-FOSS-yangtong
	 * @date 2012-10-16 下午02:46:47
	 * @param e
	 */
	@SuppressWarnings({ "rawtypes", "static-access" })
	public void actionPerformed(ActionEvent e) {
		LinkTableMode tableModel = null;
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
		boolean flag = ui.getCheckBox().isSelected();
		if(flag){
			WaibillImporter importer = new WaibillImporter();
			List list = importer.importFile(ui, dept.getCode());
			boolean canImport = true;
			if(list != null && list.size() > 0){
				WaybillPendingEntity pending1 =(WaybillPendingEntity) list.get(0);
				if(pending1!=null){
					//离线运单与当前登录人不是同一部门,不能导入
					String importOrgCode = pending1.getCreateOrgCode();
					String currentOrgCode = dept.getCode();//离线运单与当前登录人不是同一部门,不能导入
					if(currentOrgCode!=null && !currentOrgCode.equals(importOrgCode)){
						MsgBox.showError(i18n.get("foss.gui.creating.salesDeptInputAction.MsgBox.orgCodeError"));
						canImport = false;//离线运单与当前登录人不是同一部门,不能导入
					}
				}
				
				if(canImport){//可以导入
					tableModel = new LinkTableMode(ui.getArray(list,1));
					StringBuffer buffer = new StringBuffer();
					offLinePendingService =  GuiceContextFactroy.getInjector().
							getInstance(WaybillOffLinePendingService.class);
					for(int i=0; i<list.size(); i++){
						WaybillPendingEntity pending = (WaybillPendingEntity)list.get(i);
						
	
						if(!offLinePendingService.isWayBillExsits(pending.getWaybillNo())){
							// 插入数据之前，需要重新生成id，否则会与之前可能存在的状态为N的废记录id冲突
							pending.setId(UUIDUtils.getUUID());
							offLinePendingService.insert(pending);
						}else{
							buffer.append(pending.getWaybillNo());
							buffer.append(",");
						}	
					}
					if(buffer.toString() != null && !"".equals(buffer.toString())){
						MsgBox.showError(i18n.get("foss.gui.creating.salesDeptInputAction.MsgBox.offLineWaybillNo.one") + buffer.toString() + i18n.get("foss.gui.creating.salesDeptInputAction.MsgBox.offLineWaybillNo.two")); 
					}
				}
			}
			if(tableModel != null){
				JXTable table = ui.getTable();
				table.setModel(tableModel);
				new OfflineButtonColumn(table.getColumn(i18n.get("foss.gui.creating.salesDeptInputAction.MsgBox.oprateColumn")), ui);
				ui.refreshTable(table);
		        JOptionPane.showMessageDialog(ui, i18n.get("foss.gui.creating.salesDeptInputAction.MessageDialog.successImportTable"), i18n.get("foss.gui.creating.waybillEditUI.common.success"),
					    JOptionPane.INFORMATION_MESSAGE);
			}
		}else{
			MsgBox.showError(i18n.get("foss.gui.creating.salesDeptExportAction.MsgBox.notOffLineWaybill")); 
		}
		
		
	}

}