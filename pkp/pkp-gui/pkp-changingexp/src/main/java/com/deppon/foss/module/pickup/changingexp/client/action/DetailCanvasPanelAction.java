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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/action/DetailCanvasPanelAction.java
 * 
 * FILE NAME        	: DetailCanvasPanelAction.java
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

import javax.swing.JButton;

import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changingexp.client.ui.internal.CanvasPanel;

/**
 * 
 * 详细计价信息按钮Action类
 * @author 102246-foss-shaohongliang
 * @date 2012-11-3 上午8:49:27
 */
public class DetailCanvasPanelAction implements
		IButtonActionListener<WaybillRFCUI> {

	private WaybillRFCUI waybillRFCUI;

	/**
	 * 
	 * 详细计价画布显示
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午4:50:32
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		CanvasPanel canvasPanel = waybillRFCUI.canvasPanel;
		//保存在更改单主页面上
		//主页面滚动时，需要画布跟随滚动
		if(canvasPanel == null){
			//初始化Canva
			canvasPanel = new CanvasPanel((JButton) e.getSource(), waybillRFCUI.getDetailCanvasPanel()
					.getExtendPanel());
		}
		//显示就隐藏
		if(canvasPanel.isShowing()){
			canvasPanel.setVisible(false);
		}else{
			canvasPanel.showCanvas();
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
		waybillRFCUI = ui;
	}

}