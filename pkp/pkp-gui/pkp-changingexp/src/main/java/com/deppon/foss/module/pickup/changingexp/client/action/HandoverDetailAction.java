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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/action/HandoverDetailAction.java
 * 
 * FILE NAME        	: HandoverDetailAction.java
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

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.util.List;

import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.module.pickup.changingexp.client.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.changingexp.client.service.WaybillRfcServiceFactory;
import com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changingexp.client.ui.dialog.HandoverDetailDialog;
import com.deppon.foss.module.pickup.changingexp.client.vo.WaybillInfoVo;
import com.deppon.foss.module.transfer.unload.api.shared.dto.HandOverAndUnloadDto;

/**
 * 
 * 交接明细Action
 * @author 102246-foss-shaohongliang
 * @date 2012-11-9 下午3:58:48
 */
public class HandoverDetailAction implements IButtonActionListener<Container>{
	
	private WaybillRFCUI ui;
	
	private IWaybillRfcService rfcService = WaybillRfcServiceFactory.getWaybillRfcService();

	/**
	 * 
	 * 显示交接明细
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午4:50:54
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		WaybillInfoVo waybillInfoVo = ui.getBinderWaybill();
		
		String waybillNo = waybillInfoVo.getWaybillNo();
		
		//查询交接明细
		List<HandOverAndUnloadDto> handoverDetailDto = rfcService.queryHandoverDetail(waybillNo);
		
		//交接明细对话框
		HandoverDetailDialog dialog = new HandoverDetailDialog(waybillInfoVo, handoverDetailDto);
		WindowUtil.centerAndShow(dialog);
	}

	/**
	 * 
	 * UI注入
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午4:48:26
	 * @see com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener#setInjectUI(java.awt.Container)
	 */
	@Override
	public void setInjectUI(Container ui) {
		if(ui instanceof WaybillRFCUI){
			this.ui = (WaybillRFCUI)ui;	
		}
	}

}