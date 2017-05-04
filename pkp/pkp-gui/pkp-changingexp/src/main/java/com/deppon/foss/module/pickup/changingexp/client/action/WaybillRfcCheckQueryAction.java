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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/action/WaybillRfcCheckQueryAction.java
 * 
 * FILE NAME        	: WaybillRfcCheckQueryAction.java
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
import com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCCheckUI;
import com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCCheckUI.ApplyChangeVoModel;
import com.deppon.foss.module.pickup.changingexp.client.vo.CheckStatusVo;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.NumberValidate;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcCondition;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcChangeDto;

/**
 * 
 * <p>
 * 更改单审核查询<br />
 * </p>
 * @title WaybillRfcCheckQueryAction.java
 * @package com.deppon.foss.module.pickup.changingexp.client.action 
 * @author suyujun
 * @version 0.1 2012-12-18
 */
public class WaybillRfcCheckQueryAction implements
		IButtonActionListener<WaybillRFCCheckUI> {
	
	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(WaybillRfcCheckQueryAction.class);
	/**
	 * 9
	 */
	private static final int LENGTHEND = 10;
	
	/**
	 * 8
	 */
	private static final int LENGTHSTART = 10;

	/**
	 * log
	 */
	private static final Logger LOG = Logger
			.getLogger(WaybillRfcCheckQueryAction.class);

	/**
	 * service
	 */
	private IWaybillRfcVarificationService waybillRfcVarificationService = WaybillRfcServiceFactory
			.getWaybillRfcVarificationService();

	/**
	 * ui object
	 */
	private WaybillRFCCheckUI waybillRFCCheckUI;

	/**
	 * 
	 * 审核查询
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午5:27:21
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {

		List<WaybillRfcChangeDto> waybillRfcChangeDtoList = new ArrayList<WaybillRfcChangeDto>();
		WaybillRfcCondition waybillRfcCondition = new WaybillRfcCondition();

		waybillRfcCondition.setDeptCode(waybillRFCCheckUI
				.getApproveDepartmentText().getText());

		waybillRfcCondition.setDealStatus(((CheckStatusVo) waybillRFCCheckUI
				.getStatus().getSelectedItem()).getCheckStatusCode());
		waybillRfcCondition.setStartDate(waybillRFCCheckUI.getFossdBeginDate()
				.getDate());
		Date endDate = waybillRFCCheckUI.getFossdEndDate()
				.getDate();
		//设置时分秒
		endDate.setHours(WaybillRfcConstants.TIME_TWENTY_THREE);
		endDate.setMinutes(WaybillRfcConstants.TIME_FIFTY_NINE);
		endDate.setSeconds(WaybillRfcConstants.TIME_FIFTY_NINE);
		waybillRfcCondition.setEndDate(endDate);
//		waybillRfcCondition.setIsExpress("Y");
		waybillRfcCondition.setWaybillNumber(waybillRFCCheckUI
				.getWaybillNoTxt().getText());

		waybillRfcCondition.setDealType(WaybillRfcConstants.WAYBILL_RFC_CHECK);
		try {
			if(StringUtils.isNotEmpty(waybillRfcCondition.getWaybillNumber())){
				boolean b = NumberValidate.checkBetweenLength(waybillRfcCondition.getWaybillNumber(), LENGTHSTART, LENGTHEND);
				if(!b){
					MsgBox.showInfo(i18n.get("foss.gui.changingexp.waybillRfcCheckAndDealQueryAction.msgBox.wrongWaybillNumber"));
					return ;
				}
			}
			waybillRfcChangeDtoList = waybillRfcVarificationService
					.queryWaybillRfcVarificationDto(waybillRfcCondition);
			if(waybillRfcChangeDtoList!=null && !waybillRfcChangeDtoList.isEmpty()){
				/**
				 * 由于T_SRV_WAYBILLRFC表中未存放运输性质字段,无法根据根据运输类型为"经济快递"的方式过滤非快递审核运单
				 * 故只能根据运单号为10位且以5、6、7、8、9开头的字符来判断了
				 */
				List<WaybillRfcChangeDto> newList = new ArrayList<WaybillRfcChangeDto>();
				for (WaybillRfcChangeDto dto : waybillRfcChangeDtoList) {
					//是否为经济快递运单
					if(CommonUtils.isExpressWaybill(dto.getWaybillNumber().trim())){
						newList.add(dto);
					}
				}
				waybillRfcChangeDtoList = newList;
			}else{
				MsgBox.showInfo(i18n.get("foss.gui.changingexp.waybillRfcCheckAndDealQueryAction.msgBox.nullRfcChangeDtoList"));
			}
			ApplyChangeVoModel tableModel = (ApplyChangeVoModel) waybillRFCCheckUI.getTable().getModel();
			tableModel.setRowCount(0);// 清除原有行
			tableModel.setData(waybillRfcChangeDtoList);
			tableModel.fireTableDataChanged();				
		} catch (Exception exp) {
			LOG.error(exp.getMessage(),exp);
			JOptionPane.showMessageDialog(null, exp.getMessage(), i18n.get("foss.gui.changingexp.waybillRFCUI.common.prompt"),
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
	public void setInjectUI(WaybillRFCCheckUI ui) {
		waybillRFCCheckUI = ui;
	}

}