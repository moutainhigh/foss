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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/action/ShowChangeGoodsQtyDialogAction.java
 * 
 * FILE NAME        	: ShowChangeGoodsQtyDialogAction.java
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

import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changingexp.client.ui.dialog.ChangeGoodsQtyDialog;
import com.deppon.foss.module.pickup.changingexp.client.vo.WaybillInfoVo;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class ShowChangeGoodsQtyDialogAction implements IButtonActionListener<WaybillRFCUI> {

	/**
	 * ui object
	 */
	private WaybillRFCUI ui;
	
	/**
	 * 
	 * 打木架对话框显示
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午5:25:06
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		WaybillInfoVo bean = ui.getBinderWaybill();
		//打开修改件数dialog
		ui.getOriginWaybill();
		// 创建弹出窗口
		ChangeGoodsQtyDialog dialog = new ChangeGoodsQtyDialog(bean, ui );
		// 剧中显示弹出窗口
		WindowUtil.centerAndShow(dialog);
	}

	/**
	 * 
	 * UI注入
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午4:48:26
	 * @see com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener#setInjectUI(java.awt.Container)
	 */
	public void setInjectUI(WaybillRFCUI ui) {
		this.ui = ui;
	}

}