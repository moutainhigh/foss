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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/action/WaybillRfcDealQueryAction.java
 * 
 * FILE NAME        	: WaybillRfcDealQueryAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.changingexp.client.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.pickup.changingexp.client.service.IWaybillRfcVarificationService;
import com.deppon.foss.module.pickup.changingexp.client.service.WaybillRfcServiceFactory;
import com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCDealUI;
import com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCDealUI.ApplyChangeVoModel;
import com.deppon.foss.module.pickup.changingexp.client.vo.CheckStatusVo;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcCondition;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcChangeDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;

/**
 * 
 * <p>
 * 更改单受理查询 
 * </p>
 * @title WaybillRfcDealQueryAction.java
 * @package com.deppon.foss.module.pickup.changingexp.client.action 
 * @author suyujun
 * @version 0.1 2012-12-18
 */
public class WaybillRfcDealQueryAction implements
		IButtonActionListener<WaybillRFCDealUI> {
	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(WaybillRfcDealQueryAction.class);
	/**
	 * log
	 */
	private static final Logger LOG = Logger
			.getLogger(WaybillRfcDealQueryAction.class);
	
	/**
	 * SERVICE
	 */
	private IWaybillRfcVarificationService waybillRfcVarificationService = WaybillRfcServiceFactory
			.getWaybillRfcVarificationService();

	/**
	 * UI
	 */
	private WaybillRFCDealUI waybillRFCDealUI;

	/**
	 * 
	 * 受理查询
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午5:27:46
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		/**
		 * 查询日期范围不能超过10天！
		 */
		if(DateUtils.getTimeDiff(waybillRFCDealUI.getFossdBeginDate().getDate(), waybillRFCDealUI.getFossdEndDate().getDate()) > 9){
			MsgBox.showInfo(i18n.get("foss.gui.changingexp.waybillRfcDealQueryAction.msgBox.notPassTenDays"));			
		}else{
			List<WaybillRfcChangeDto> waybillRfcChangeDtoList = new ArrayList<WaybillRfcChangeDto>();
			WaybillRfcCondition waybillRfcCondition = new WaybillRfcCondition();

			waybillRfcCondition.setDeptCode(waybillRFCDealUI
					.getApproveDepartmentText().getText());

			waybillRfcCondition.setDealStatus(((CheckStatusVo) waybillRFCDealUI
					.getStatus().getSelectedItem()).getCheckStatusCode());
			waybillRfcCondition.setStartDate(waybillRFCDealUI.getFossdBeginDate()
					.getDate());
			Date endDate = waybillRFCDealUI.getFossdEndDate().getDate();
			endDate.setHours(WaybillRfcConstants.TIME_TWENTY_THREE);
			endDate.setMinutes(WaybillRfcConstants.TIME_FIFTY_NINE);
			endDate.setSeconds(WaybillRfcConstants.TIME_FIFTY_NINE);
			waybillRfcCondition.setEndDate(endDate);
			waybillRfcCondition.setIsExpress(1);
			waybillRfcCondition.setWaybillNumber(waybillRFCDealUI.getWaybillNoTxt()
					.getText());

			waybillRfcCondition
					.setDealType(WaybillRfcConstants.WAYBILL_RFC_PROCESS);
			try {
				waybillRfcChangeDtoList = waybillRfcVarificationService
						.queryWaybillRfcVarificationDto(waybillRfcCondition);
				if(CollectionUtils.isNotEmpty(waybillRfcChangeDtoList)){
					for(WaybillRfcChangeDto waybillRfcChangeDto:waybillRfcChangeDtoList)
					{
						if(waybillRfcChangeDto.getDealStauts() !=null &&(!WaybillRfcConstants.ACCECPT.equals(waybillRfcChangeDto.getDealStauts())&&!WaybillRfcConstants.ACCECPT_DENY.equals(waybillRfcChangeDto.getDealStauts())))
						{
							waybillRfcChangeDto.setHandleDeptName("");
							waybillRfcChangeDto.setHandlePerson("");
						}
					}
				}else{
					MsgBox.showInfo(i18n.get("foss.gui.changingexp.waybillRfcCheckAndDealQueryAction.msgBox.nullRfcChangeDtoList"));

				}
				
			
				ApplyChangeVoModel tableModel = (ApplyChangeVoModel) waybillRFCDealUI
						.getTable().getModel();
				tableModel.setRowCount(0);// 清除原有行
				tableModel.setData(waybillRfcChangeDtoList);
				tableModel.fireTableDataChanged();				

			} catch (Exception exp) {
				LOG.error(exp.getMessage(),exp);
				JOptionPane.showMessageDialog(null, exp.getMessage(), i18n.get("foss.gui.changingexp.waybillRFCUI.common.prompt"),
						JOptionPane.WARNING_MESSAGE);
			}
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
	public void setInjectUI(WaybillRFCDealUI ui) {
		waybillRFCDealUI = ui;
	}

}