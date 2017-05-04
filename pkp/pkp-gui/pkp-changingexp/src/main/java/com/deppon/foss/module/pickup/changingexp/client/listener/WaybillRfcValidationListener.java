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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/listener/WaybillRfcValidationListener.java
 * 
 * FILE NAME        	: WaybillRfcValidationListener.java
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
package com.deppon.foss.module.pickup.changingexp.client.listener;

import java.awt.Component;

import com.deppon.foss.framework.client.commons.validation.IValidationListener;
import com.deppon.foss.framework.client.commons.validation.ValidationError;
import com.deppon.foss.framework.client.commons.validation.ValidationErrorEvent;
import com.deppon.foss.framework.client.core.binding.BindingAssociation;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.client.widget.validatewidget.IBallValidateWidget;

/**
 * 
 * 运单变更画面校验处理类
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-29 上午9:32:33
 */
public class WaybillRfcValidationListener implements IValidationListener {

	@Override
	public void validationError(ValidationErrorEvent e) {
		//遍历错误信息
		for (ValidationError error : e.getErrors()) {
			//得到错误提示的控件
			Component jComponent = ((BindingAssociation) error.getKey()).getComponent();
			//如果错误框支持小组件方式提示
			if (jComponent instanceof IBallValidateWidget) {
				// 实现了IBallValidateWidget的控件用气泡方式提示错误
				IBallValidateWidget field = (IBallValidateWidget) jComponent;
				//提示错误
				field.verifyWrong(error.getMessage());
			} else {
				// 否则弹框提示
				MsgBox.showError(error.getMessage());
			}
		}
	}

}