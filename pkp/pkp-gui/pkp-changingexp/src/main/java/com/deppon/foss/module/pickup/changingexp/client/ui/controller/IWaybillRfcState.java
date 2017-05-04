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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/ui/controller/IWaybillRfcState.java
 * 
 * FILE NAME        	: IWaybillRfcState.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.changingexp.client.ui.controller;

/**
 * 
 * 更改单UI状态
 * @author 102246-foss-shaohongliang
 * @date 2012-11-21 下午6:44:10
 */
public interface IWaybillRfcState {

	/**
	 * 
	 * 委托更新控件状态
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-21 下午6:45:00
	 */
	void performComponentsState();
	

	/**
	 * 
	 * 委托切换布局
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-21 下午6:45:00
	 */
	void performLayout();

}