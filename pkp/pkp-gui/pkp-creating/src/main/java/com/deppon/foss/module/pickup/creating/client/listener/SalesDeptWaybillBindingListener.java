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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/listener/SalesDeptWaybillBindingListener.java
 * 
 * FILE NAME        	: SalesDeptWaybillBindingListener.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.listener;

import java.util.List;

import com.deppon.foss.framework.client.core.binding.BindingEvent;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.binding.IBindingListener;
import com.deppon.foss.module.pickup.creating.client.ui.SalesDeptWaybillUI;
import com.deppon.foss.module.pickup.creating.client.vo.SalesDeptWaybillVo;


/**
 * 
 * 运单validationListner
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:16:43,
 * </p>
 * @author 105089-FOSS-yangtong
 * @date 2012-10-17 上午11:16:43
 * @since
 * @version
 */
public class SalesDeptWaybillBindingListener implements IBindingListener {
	
	SalesDeptWaybillUI ui;
	
	IBinder<SalesDeptWaybillVo> salesWaybillBinder;
	
	public SalesDeptWaybillBindingListener(SalesDeptWaybillUI ui) {
		this.ui = ui;
	}
	
	@Override
	public void bindingTriggered(List<BindingEvent> events) {		
	}

	/**
	 * 
	 * 是否vo.set***也触发该事件类
	 * @author 025000-FOSS-helong
	 * @date 2013-1-10 上午09:05:30
	 * @return
	 */
	public boolean isFromVoTargetEnable(){
		return false;
	}
}