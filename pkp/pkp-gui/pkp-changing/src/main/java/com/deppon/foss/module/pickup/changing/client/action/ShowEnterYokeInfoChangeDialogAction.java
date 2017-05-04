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
 * PROJECT NAME	: pkp-changing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/action/ShowEnterYokeInfoChangeDialogAction.java
 * 
 * FILE NAME        	: ShowEnterYokeInfoChangeDialogAction.java
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
package com.deppon.foss.module.pickup.changing.client.action;

import java.awt.event.ActionEvent;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.pickup.changing.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changing.client.ui.dialog.EnterYokeInfoChangeDialog;
import com.deppon.foss.module.pickup.changing.client.vo.WaybillInfoVo;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 查看代打木架信息Action
 * @author 102246-foss-shaohongliang
 * @date 2012-12-12 下午2:21:22
 */
public class ShowEnterYokeInfoChangeDialogAction implements IButtonActionListener<WaybillRFCUI> {
	
	/**
	 * ui object
	 */
	private WaybillRFCUI ui;
	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(ShowEnterYokeInfoChangeDialogAction.class);
	/**
	 * 
	 * 打木架对话框显示
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午5:25:06
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		WaybillInfoVo bean = ui.getBinderWaybill();
		if(FossConstants.YES.equals(bean.getDoPacking())){
			//代打木架弹出框
			EnterYokeInfoChangeDialog dialog = new EnterYokeInfoChangeDialog(ui);
			WindowUtil.centerAndShow(dialog);
			
			//zxy 20140106 DEFECT-1373 start 新增:弹出打木架框后,提交按钮不可用，必须重新计算总运费
			//提交按钮不可用
			ui.getButtonPanel().getBtnSubmit().setEnabled(false);
			//计算总运费按钮可用
			ui.getWaybillInfoPanel().getBillingPayPanel().getBtnCalculate().setEnabled(true);
			//zxy 20140106 DEFECT-1373 end 新增:弹出打木架框后,提交按钮不可用，必须重新计算总运费
		}else{
			MsgBox.showError(i18n.get("foss.gui.changing.showEnterYokeInfoChangeDialogAction.msgBox.doNotPacking"));
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
	public void setInjectUI(WaybillRFCUI ui) {
		this.ui = ui;
	}

}