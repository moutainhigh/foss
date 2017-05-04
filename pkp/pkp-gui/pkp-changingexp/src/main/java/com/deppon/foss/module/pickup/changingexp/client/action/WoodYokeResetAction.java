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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/action/WoodYokeResetAction.java
 * 
 * FILE NAME        	: WoodYokeResetAction.java
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

import com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changingexp.client.ui.dialog.EnterYokeInfoChangeDialog;
import com.deppon.foss.module.pickup.changingexp.client.vo.WaybillInfoVo;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;

/**
 * 
 * 代打木架信息重置
 * @author 102246-foss-shaohongliang
 * @date 2012-12-18 下午7:41:33
 */
public class WoodYokeResetAction extends
		AbstractButtonActionListener<EnterYokeInfoChangeDialog> {

	/**
	 * 代打木 dialog
	 */
	private EnterYokeInfoChangeDialog yoke;

	/**
	 * 
	 * 录入代打木架信息
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-18 下午7:41:54
	 * @see com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		WaybillRFCUI ui= yoke.getWaybillRFCUI();
		WaybillInfoVo bean = ui.getBinderWaybill();
		// 打木架货物件数
		yoke.txtYokeGoodsPieces.setText("");		
		// 代打木架要求
		yoke.txtYokeRequire.setText("");
		// 打木架货物尺寸
		yoke.txtYokeGoodsSize.setText("");
		// 打木架货物体积
		yoke.txtYokeGoodsVolume.setText("");
		// 打木箱货物件数
		yoke.txtBoxGoodsPieces.setText("");
		// 代打木箱要求
		yoke.txtBoxRequire.setText("");
		// 打木箱货物尺寸
		yoke.txtBoxGoodsSize.setText("");
		// 打木箱货物体积
		yoke.txtBoxGoodsVolume.setText("");
		// 流水号
		yoke.list.clearSelection();
		//储运事项清空代打木架
		String  brak =bean.getTransportationRemark();
		String str="";
		boolean ish	=false;
		if(brak!=null){
			String[] strs = brak.split(";");
			for(int	j=0;j<strs.length;j++){
				if(strs[j].contains("木架")){
					ish=true;
					continue;
				}
				str= str+strs[j]+";";
			}
			if(ish){
			bean.setTransportationRemark(str);
			}
			
		}
         
		
       
	}

	/**
	 * 
	 * UI注入
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午5:28:30
	 * @see com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener#setIInjectUI(java.awt.Container)
	 */
	@Override
	public void setIInjectUI(EnterYokeInfoChangeDialog yoke) {
		this.yoke = yoke;
	}

}