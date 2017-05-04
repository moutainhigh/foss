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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/WoodYokeResetAction.java
 * 
 * FILE NAME        	: WoodYokeResetAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.common.Common;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.creating.client.ui.customer.EnterYokeInfoDialog;
import com.deppon.foss.module.pickup.creating.client.vo.WoodYokeVo;
/**
 * 
 * 运单WoodYokeResetAction
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:16:43,
 * </p>
 * @author 105089-FOSS-yangtong
 * @date 2012-10-17 上午11:16:43
 * @since
 * @version
 */
public class WoodYokeResetAction extends
		AbstractButtonActionListener<EnterYokeInfoDialog> {

	EnterYokeInfoDialog yoke;

	IBinder<WoodYokeVo> woodYokeBinder;

	/**
	 * 
	 * <p>
	 * (录入打木架信息)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 下午02:46:47
	 * @param e
	 * @see com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		WaybillEditUI waybillEditUI = yoke.getWaybillEditUI();
		HashMap<String, IBinder<WaybillPanelVo>> map = waybillEditUI.getBindersMap();
		IBinder<WaybillPanelVo> waybillBinder = map.get("waybillBinder");
		WaybillPanelVo bean = waybillBinder.getBean();
		
		Common.unsetWaybillPanelVoForWoodenPack(bean,waybillEditUI);
		Common.unsetStorageMatterForWoodenPack(bean,waybillEditUI);
		Common.unsetWoodenPackFee(bean);
	}

	@Override
	public void setIInjectUI(EnterYokeInfoDialog yoke) {
		this.yoke = yoke;
	}
}