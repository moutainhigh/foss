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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/action/WaybillRfcCheckAndDealQueryAction.java
 * 
 * FILE NAME        	: WaybillRfcCheckAndDealQueryAction.java
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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.pickup.changingexp.client.service.IWaybillRfcVarificationService;
import com.deppon.foss.module.pickup.changingexp.client.service.WaybillRfcServiceFactory;
import com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCQueryCheckAndDealUI;
import com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCQueryCheckAndDealUI.ApplyChangeVoModel;
import com.deppon.foss.module.pickup.changingexp.client.vo.CheckStatusVo;
import com.deppon.foss.module.pickup.common.client.utils.NumberValidate;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcCondition;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcChangeDto;

/**
 * 
 * <p>
 * 更改单审核受理查询
 * </p>
 * @title WaybillRfcCheckAndDealQueryAction.java
 * @package com.deppon.foss.module.pickup.changingexp.client.action 
 * @author suyujun
 * @version 0.1 2012-12-18
 */
public class WaybillRfcCheckAndDealQueryAction implements
	IButtonActionListener<WaybillRFCQueryCheckAndDealUI> {
	
	/**
	 * 9
	 */
	private static final int LENGTHEND = 10;
	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(WaybillRfcCheckAndDealQueryAction.class);
	/**
	 * 8
	 */
	private static final int LENGTHSTART = 8;
	
	/**
	 * log
	 */
	private static final Logger LOG = Logger
			.getLogger(WaybillRfcCheckAndDealQueryAction.class);

    private IWaybillRfcVarificationService waybillRfcVarificationService = WaybillRfcServiceFactory
	    .getWaybillRfcVarificationService();

    private WaybillRFCQueryCheckAndDealUI waybillRFCQueryCheckAndDealUI;

    /**
     * 
     * 审核受理查询
     * @author 102246-foss-shaohongliang
     * @date 2012-12-24 下午5:26:49
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @SuppressWarnings("deprecation")
	@Override
    public void actionPerformed(ActionEvent e) {
		List<WaybillRfcChangeDto> waybillRfcChangeDtoList = new ArrayList<WaybillRfcChangeDto>();
		WaybillRfcCondition waybillRfcCondition = new WaybillRfcCondition();
		waybillRfcCondition.setDeptCode(waybillRFCQueryCheckAndDealUI
			.getApproveDepartmentText().getText());
	
		waybillRfcCondition
			.setDealStatus(((CheckStatusVo) waybillRFCQueryCheckAndDealUI
				.getDealStatus().getSelectedItem())
				.getCheckStatusCode());
		waybillRfcCondition
			.setCheckStatus(((CheckStatusVo) waybillRFCQueryCheckAndDealUI
				.getCheckStatus().getSelectedItem())
				.getCheckStatusCode());
		waybillRfcCondition.setStartDate(waybillRFCQueryCheckAndDealUI.getFossdBeginDate()
				.getDate());
		Date endDate = waybillRFCQueryCheckAndDealUI.getFossdEndDate()
				.getDate();
		//设置时分秒
		endDate.setHours(WaybillRfcConstants.TIME_TWENTY_THREE);
		endDate.setMinutes(WaybillRfcConstants.TIME_FIFTY_NINE);
		endDate.setSeconds(WaybillRfcConstants.TIME_FIFTY_NINE);
		waybillRfcCondition.setEndDate(endDate);
		waybillRfcCondition.setWaybillNumber(waybillRFCQueryCheckAndDealUI
			.getWaybillNoTxt().getText());
	
		try {
	
			if(StringUtils.isNotEmpty(waybillRfcCondition.getWaybillNumber())){
				boolean b = NumberValidate.checkBetweenLength(waybillRfcCondition.getWaybillNumber(), LENGTHSTART, LENGTHEND);
				if(!b){
					MsgBox.showInfo(i18n.get("foss.gui.changingexp.waybillRfcCheckAndDealQueryAction.msgBox.wrongWaybillNumber"));
					return ;
				}
			}
			waybillRfcChangeDtoList = waybillRfcVarificationService.queryWaybillRfcChkAndPro(waybillRfcCondition);
			if(waybillRfcChangeDtoList!=null && !waybillRfcChangeDtoList.isEmpty()){
				 LOG.debug(waybillRfcChangeDtoList.size());
			}else{
				MsgBox.showInfo(i18n.get("foss.gui.changingexp.waybillRfcCheckAndDealQueryAction.msgBox.nullRfcChangeDtoList"));
			}
	
		    ApplyChangeVoModel tableModel = (ApplyChangeVoModel) waybillRFCQueryCheckAndDealUI.getTable().getModel();
		    tableModel.setRowCount(0);// 清除原有行
		    tableModel.setData(waybillRfcChangeDtoList);
		    tableModel.fireTableDataChanged();
		   
		} catch (Exception exp) {
			LOG.error("exception", exp);
		    JOptionPane.showMessageDialog(null, exp.getMessage(), i18n.get("foss.gui.creating.waybillEditUI.common.error"),
			    JOptionPane.WARNING_MESSAGE);
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
    public void setInjectUI(WaybillRFCQueryCheckAndDealUI ui) {
    	waybillRFCQueryCheckAndDealUI = ui;
    }

}