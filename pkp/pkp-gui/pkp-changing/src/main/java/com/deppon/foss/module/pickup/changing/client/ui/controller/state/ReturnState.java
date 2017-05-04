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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/ui/controller/state/ReturnState.java
 * 
 * FILE NAME        	: ReturnState.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.changing.client.ui.controller.state;

import javax.swing.JComponent;

import com.deppon.foss.module.pickup.changing.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changing.client.ui.controller.WaybillRfcStateContext;
import com.deppon.foss.module.pickup.changing.client.vo.WaybillInfoVo;
import com.deppon.foss.module.pickup.common.client.utils.BZPartnersJudge;
import com.deppon.foss.util.define.FossConstants;


/**
 * 
 * 返货状态
 * @author 102246-foss-shaohongliang
 * @date 2012-11-22 上午9:37:18
 */
public class ReturnState extends CustomerChangeState {
	
	public ReturnState(WaybillRfcStateContext context) {
		super(context);
	}

	/**
	 * 
	 * 更新组件状态
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:57:24
	 * @see com.deppon.foss.module.pickup.changing.client.ui.controller.AbstractState#updateComponents(com.deppon.foss.module.pickup.changing.client.ui.WaybillRFCUI)
	 */
	@Override
	protected void updateComponents(WaybillRFCUI rfcUI) {
		super.updateComponents(rfcUI);
		// 客户变更、转运、返货
		disableUI(transferPanel);
		transferPanel.getTransportInfoPanel().getCombReturnBillType().setEnabled(true); // 返单类别
		transferPanel.getTransportInfoPanel().getCombOutSideRemark().setEnabled(true); // 对外备注
		transferPanel.getTransportInfoPanel().getTxtInsideRemark().setEnabled(true); // 对内备注
		
		transferPanel.getReturnTabPanel().getReturnInfoPanel().getCombReturnProductType().setEnabled(true);//运输性质
		transferPanel.getReturnTabPanel().getReturnInfoPanel().getCombReturnAllocationType().setEnabled(true);//转运提货方式
		
		transferPanel.getReturnTabPanel().getReturnInfoPanel().getTxtReturnDestination().setEnabled(false);//转运目的站
		transferPanel.getReturnTabPanel().getReturnInfoPanel().getBtnQueryBranch().setEnabled(true);//查询目的站放大镜
		//如果返货面板查询目的站按钮可编辑，则设置查询网点可编辑，否则设置为不可编辑
		if(transferPanel.getReturnTabPanel().getReturnInfoPanel().getBtnQueryBranch().isEnabled()){
			rfcUI.getButtonPanel().getBtnSearchBranch().setEnabled(true);
		}else{
			rfcUI.getButtonPanel().getBtnSearchBranch().setEnabled(false);
		}
		WaybillInfoVo waybillVo = getWaybillRfcUI().getBinderWaybill();
		/**
		 * 设置自提件是否可编辑
		 */
		if(FossConstants.YES.equals(waybillVo.getFlagEconomyEdit())){
			getWaybillRfcUI().getWaybillInfoPanel().getBasicPanel().getChbEconomyGoods().setEnabled(true);
		}else{
			getWaybillRfcUI().getWaybillInfoPanel().getBasicPanel().getChbEconomyGoods().setEnabled(false);
		}		
		//设置自提件相关控件是否可编辑
		if(waybillVo.getIsEconomyGoods()!=null && waybillVo.getIsEconomyGoods()){
			//上门接货不能编辑
			getWaybillRfcUI().getWaybillInfoPanel().getBasicPanel().getCboReceiveModel().setEnabled(false);
			//提货方式不可编辑
			getWaybillRfcUI().getWaybillInfoPanel().getTransferPanel().getReturnTabPanel().getReturnInfoPanel().getCombReturnAllocationType().setEnabled(false);
		}
		//大票貨
		rfcUI.getWaybillInfoPanel().getGoodsPanel().getChbBigTicket().setEnabled(false);

		if(BZPartnersJudge.IS_PARTENER){
			goodsPanel.getTxtWeight().setEnabled(false); // 重量
			goodsPanel.getTxtSize().setEnabled(false);//尺寸
			goodsPanel.getTxtVolume().setEnabled(false);//体积
		}
		
	}
	
	/**
	 * 
	 * 运输面板
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:57:35
	 * @see com.deppon.foss.module.pickup.changing.client.ui.controller.AbstractState#getTransferInjectPanel()
	 */
	@Override
	protected JComponent getTransferInjectPanel() {
		return getWaybillRfcUI().getWaybillInfoPanel().getTransferPanel().getReturnTabPanel();
	}	

}