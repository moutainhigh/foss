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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/action/ChangeGoodsQtyConfimrAction.java
 * 
 * FILE NAME        	: ChangeGoodsQtyConfimrAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/**
 * 
 */
package com.deppon.foss.module.pickup.changing.client.action;

import java.awt.event.ActionEvent;
import java.util.List;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.pickup.changing.client.ui.dialog.ChangeGoodsQtyDialog;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.waybill.shared.define.LabeledGoodChangeHistoryConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodChangeHistoryDto;

/**
 * 修改件数确认按钮
 * @author 026123-foss-lifengteng
 *
 */
public class ChangeGoodsQtyConfimrAction  extends AbstractButtonActionListener<ChangeGoodsQtyDialog>{

	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(ChangeGoodsQtyConfimrAction.class);
	/**
	 * 更改单主UI
	 */
	private ChangeGoodsQtyDialog ui;
	
	
	/**
	 * 
	 * 计算总费用
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午4:15:18
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		int totalQty = ui.getCurrentWaybillInfoVo().getGoodsQtyTotal();
		
		List<LabeledGoodChangeHistoryDto> list 
			= ui.getCurrentWaybillInfoVo().getLabeledGoodChangeHistoryDtoList();
		
		int totalQtyInList = 0;
		
		for(int i =0 ;i <list.size();i++){
			LabeledGoodChangeHistoryDto dtp = list.get(i);
			if(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_DELETE.equals(dtp.getChangeType())){
				continue;
			}
			totalQtyInList ++;
		}
		
		if(totalQtyInList!=totalQty){
			MsgBox.showError(i18n.get("foss.gui.changing.changeGoodsQtyConfimrAction.msgBox.inconsistentQtyInList"));
		}else{
			ui.dispose();
		}
		
	}


	/** 
	 * 
	 * 注入ui信息
	 * */
	@Override
	public void setIInjectUI(ChangeGoodsQtyDialog ui) {
		this.ui=ui;
		
	}
	
	
}