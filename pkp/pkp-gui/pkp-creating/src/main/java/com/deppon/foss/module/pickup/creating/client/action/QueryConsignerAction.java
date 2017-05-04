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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/QueryConsignerAction.java
 * 
 * FILE NAME        	: QueryConsignerAction.java
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
package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.creating.client.common.Common;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;

/**
 * 
 * 运单QueryConsignerAction
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:16:43,
 * </p>
 * 
 * @author 105089-FOSS-yangtong
 * @date 2012-10-17 上午11:16:43
 * @since
 * @version
 */
public class QueryConsignerAction extends AbstractButtonActionListener<WaybillEditUI> {

	WaybillEditUI ui;
	/**
	 * 
	 * <p>
	 * 查询发货客户
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 下午02:46:47
	 * @param e
	 * @see com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if(ui.getPictureWaybillType() != null && WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType().trim())){
			String weight = ui.pictureCargoInfoPanel.getTxtWeight().getText();
			String volume = ui.pictureCargoInfoPanel.getTxtVolume().getText();
			if(StringUtils.isNotBlank(weight) && new BigDecimal(weight).compareTo(BigDecimal.ZERO) > 0 
					&& StringUtils.isNotBlank(volume) && new BigDecimal(volume).compareTo(BigDecimal.ZERO) > 0){
				ui.incrementPanel.getBtnCalculate().setEnabled(true);
		ui.billingPayPanel.getBtnSubmit().setEnabled(false);// 提交为不可编辑
				ui.billingPayPanel.getBtnSubmitAndNextSingle().setEnabled(false);
				ui.incrementPanel.getJlable().setVisible(false);
				ui.incrementPanel.getCombServiceRate().setVisible(false);
			}else{
				ui.incrementPanel.getBtnCalculate().setEnabled(false);
				ui.billingPayPanel.getBtnSubmit().setEnabled(true);// 提交为不可编辑
				ui.billingPayPanel.getBtnSubmitAndNextSingle().setEnabled(true);
				ui.incrementPanel.getJlable().setVisible(true);
				ui.incrementPanel.getCombServiceRate().setVisible(true);
			}
		}else{
			ui.billingPayPanel.getBtnSubmit().setEnabled(false);// 提交为不可编辑
		ui.buttonPanel.getBtnSubmit().setEnabled(false);// 提交为不可编辑
		}
		//设置发货客户信息
		Common.setQueryDeliveryCustomer(ui);
	}

	@Override
	public void setIInjectUI(WaybillEditUI ui) {

		this.ui = ui;

	}

}