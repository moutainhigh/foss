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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/PrintSignAction.java
 * 
 * FILE NAME        	: PrintSignAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/**
 * 
 */
package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.util.Date;

import javax.print.PrintException;
import javax.swing.JOptionPane;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.common.client.service.IOuterBranchService;
import com.deppon.foss.module.pickup.common.client.service.impl.OuterBranchService;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.DateUtils;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.PrintSignUI;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.print.labelprint.LabelPrintContext;
import com.deppon.foss.print.labelprint.LabelPrintManager;
import com.deppon.foss.print.labelprint.service.LblPrtServiceConst;
import com.google.inject.Injector;

/**
 * 
 * 整车标签打印
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:16:43,
 * </p>
 * @author 105089-FOSS-yangtong
 * @date 2012-10-17 上午11:16:43
 * @since
 * @version
 */
public class PrintSignAction implements
	IButtonActionListener<PrintSignUI>  {
	
	// 日志
	private static final Log LOG = LogFactory.getLog(PrintSignAction.class);
	
	// 国际化
	private static final II18n i18n = I18nManager.getI18n(PrintSignAction.class);
	
	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	
	Injector injector = GuiceContextFactroy.getInjector();
	
	//空运、偏线
	IOuterBranchService outerBranchService = injector.getInstance(OuterBranchService.class);
			
	private PrintSignUI ui;
		
	/*  
	 *  打印
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String waybillNo = ui.getTxtWaybillNo().getText();
		if (StringUtils.isEmpty(waybillNo)) {
			MsgBox.showInfo(i18n.get("foss.gui.creating.printSignAction.MsgBox.nullWaybillNo"));
			return;
		}
		WaybillDto dto = waybillService.queryWaybillByNo(waybillNo);
		if (dto == null || dto.getWaybillEntity() == null) {
			MsgBox.showInfo(i18n.get("foss.gui.creating.printSignAction.MsgBox.nullWaybillEntity"));
			return;
		}
		WaybillEntity waybill = dto.getWaybillEntity();
		
		//是否为经济快递的运单号
		if(CommonUtils.directDetermineIsExpressByProductCode(waybill.getProductCode())){
			MsgBox.showInfo(i18n.get("foss.gui.creating.printSignUI.msgbox.error.expressNo"));
			return;
		}
		if (WaybillConstants.NOT_RETURN_BILL.equals(waybill.getReturnBillType()) 
				|| StringUtils.isEmpty(waybill.getReturnBillType())) {
			MsgBox.showInfo(i18n.get("foss.gui.creating.printSignAction.MsgBox.nullReturnBill"));
			return;
		}
		
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		/*if (!user.getEmployee().getOrgCode().equals(waybill.getReceiveOrgCode())) {
			MsgBox.showInfo(i18n.get("foss.gui.creating.printSignAction.MsgBox.notOrgWaybill"));
			return;
		}*/
		
		OrgAdministrativeInfoEntity orgInfo;
		OuterBranchEntity branch;
		LabelPrintContext ctx = new LabelPrintContext();
		 
			String receiveOrgCode = dto.getWaybillEntity()
					.getReceiveOrgCode();
			orgInfo = waybillService.queryByCode(StringUtil
					.defaultIfNull(receiveOrgCode));
			ui.getTxtStartOrg().setText(orgInfo.getName());
			String customerPickupOrgCode = waybill
					.getCustomerPickupOrgCode();
			//修改当单号为空运时候查询到达部门为网点信息
			orgInfo = waybillService.queryByCode(StringUtil
					.defaultIfNull(customerPickupOrgCode));
			//查询不到再到空运或者偏线查询
			if(orgInfo == null){
				branch = outerBranchService.queryOuterBranchByCode(customerPickupOrgCode, dto.getWaybillEntity().getBillTime());
				if(branch == null){
					MsgBox.showInfo(i18n.get("foss.gui.creating.printSignAction.MsgBox.nullWaybillEntity"));
					return;
				}
				ui.getTxtReachOrg().setText(branch.getSimplename());
			}else{
				ui.getTxtReachOrg().setText(orgInfo.getName());
			}
			String targetOrgCode = waybill.getTargetOrgCode();
			ui.getTxtDestnationOrg().setText(targetOrgCode);
		try {
			String inputValue = JOptionPane
					.showInputDialog(i18n.get("foss.gui.creating.printSignAction.MsgBox.inputPrintCounts"));
			//输入为空即为不输入
			if(inputValue == null){
				return;
			}else{//输入的话值肯定不为空
				try{
					int i = Integer.parseInt(inputValue);
					if(i <= 0){
						MsgBox.showInfo(i18n.get("foss.gui.creating.calculateAction.exception.notBelowZeroGoodsQtyTotal"));
						return;
					}
				}catch (Exception eee) {
					MsgBox.showInfo(i18n.get("foss.gui.creating.enterYokeInfoDialog.msgbox.inputNumber"));
					return;
				}				
			}
			ctx.put("prtpiece", inputValue);
			ctx.put("destination", ui.getTxtDestnationOrg()
					.getText());
			ctx.put("number", waybillNo);
			ctx.put("departure", ui.getTxtStartOrg().getText());
			ctx.put("arrival", ui.getTxtReachOrg().getText());
			//BUG-12956:GODEX打印机打印某运单31211211的签收单，打印出的签收单下面部门名称显示两遍，重复显示
//			ctx.put("username", user.getEmployee().getDepartment().getName()+ user.getEmpName());
			ctx.put("username", user.getEmpName());
			ctx.put("datetime",DateUtils.getDateString(new Date()));
			try {
				LabelPrintManager.doLabelPrintAction(LblPrtServiceConst.key_lblprt_program_WholeCarLabelPrintWorker,ctx);
				MsgBox.showInfo(i18n.get("foss.gui.creating.printSignUI.msgbox.printSuccess"));
			} catch (PrintException e2) {
				JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.printerNotConnected"), i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
				return;
			}
		} catch (Exception exp) {
			LOG.error("标签打印异常" + exp.toString(), exp);
			JOptionPane.showMessageDialog(null, exp.toString(),
					i18n.get("foss.gui.creating.waybillEditUI.common.error"), JOptionPane.WARNING_MESSAGE);
			return;
		}
	}
	 
	@Override
	public void setInjectUI(PrintSignUI ui) {
		this.ui = ui;
		
	}

}