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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/QueryWoodAction.java
 * 
 * FILE NAME        	: QueryWoodAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-creating
 * PACKAGE NAME: com.deppon.foss.module.pickup.creating.client.action
 * FILE    NAME: WaybillSaveAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.util.HashMap;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.common.Common;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.util.define.FossConstants;


/**
 * 
 * 查询打木架信息
 * @author 025000-FOSS-helong
 * @date 2013-3-6 下午07:14:06
 */
public class QueryWoodAction implements IButtonActionListener<WaybillEditUI> {

	// 国际化
//	private static final II18n i18n = I18nManager.getI18n(QueryWoodAction.class);
	
	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	
	// 主界面
	WaybillEditUI ui;

	@Override
	public void actionPerformed(ActionEvent e) {
//		HashMap<String, IBinder<WaybillPanelVo>> map = ui.getBindersMap();
//		IBinder<WaybillPanelVo> waybillBinder = map.get("waybillBinder");
//		WaybillPanelVo bean = waybillBinder.getBean();
		//zxy 20131118 ISSUE-4391 start 修改：托也能弹出打木架的对话框
		/*if (bean.getWood() != null || bean.getSalver() != null) {
			int wood = bean.getWood();
			int salver = bean.getSalver();
			if (wood > 0 || salver > 0) {
				if (FossConstants.YES.equals(bean.getDoPacking())) {
					if(ui.getPictureWaybillType() != null && WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType().trim())){
						if(bean.getGoodsName()==null||"".equals(bean.getGoodsName())){
							MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.woodListener.three"));
							bean.setWood(0);
							bean.setSalver(0);
						}else if(bean.getGoodsQtyTotal()==null){
							MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.woodListener.four"));
							bean.setWood(0);
							bean.setSalver(0);
						}else{
							ui.showWoodYokeDialog();
	    					Common.setSaveAndSubmitFalse(ui);
						}
					}else{
					if(bean.getGoodsName()==null||"".equals(bean.getGoodsName())){
						MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.woodListener.three"));
						bean.setWood(0);
						bean.setSalver(0);
					}else if(bean.getGoodsQtyTotal()==null){
						MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.woodListener.four"));
						bean.setWood(0);
						bean.setSalver(0);
					}else if(bean.getGoodsWeightTotal()==null){
						MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.woodListener.five"));
						bean.setWood(0);
						bean.setSalver(0);
					}else if(bean.getGoodsVolumeTotal()==null){
						MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.woodListener.six"));
						bean.setWood(0);
						bean.setSalver(0);
					}else{
						ui.showWoodYokeDialog();
    					Common.setSaveAndSubmitFalse(ui);
					}
					}
				} else {
					MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.woodListener.two"));
				}
			}
			//zxy 20131118 ISSUE-4391 end 修改：托也能弹出打木架的对话框
		} else {
			MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.woodListener.seven"));
			bean.setWood(Integer.valueOf(0));
		}*/
		ui.showWoodYokeDialog();
		Common.setSaveAndSubmitFalse(ui);
	}
	

	@Override
	public void setInjectUI(WaybillEditUI ui) {
		this.ui = ui;
	}
	
}