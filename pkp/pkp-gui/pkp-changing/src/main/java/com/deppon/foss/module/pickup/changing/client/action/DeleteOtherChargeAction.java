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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/action/DeleteOtherChargeAction.java
 * 
 * FILE NAME        	: DeleteOtherChargeAction.java
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
package com.deppon.foss.module.pickup.changing.client.action;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.List;

import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.pickup.changing.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changing.client.ui.internal.IncrementPanel.WaybillOtherCharge;
import com.deppon.foss.module.pickup.changing.client.vo.WaybillInfoVo;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.utils.CommoForFeeUtils;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;

/**
 * 
 * 删除其他费用Action
 * @author 102246-foss-shaohongliang
 * @date 2012-12-24 下午5:32:03
 */
public class DeleteOtherChargeAction extends
		AbstractButtonActionListener<WaybillRFCUI> {

	private WaybillRFCUI ui;

	/**
	 * 国际化
	 */
	private static II18n i18n = I18nManager.getI18n(DeleteOtherChargeAction.class);

	/**
	 * 
	 * <p>
	 * 删除其他费用
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 下午02:46:47
	 * @param e
	 * @see com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		WaybillInfoVo bean = ui.getBinderWaybill();

		deleteTableRow(bean);

	}

	/**
	 * 
	 * 删除选中的其他费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 下午04:48:28
	 */
	private void deleteTableRow(WaybillInfoVo bean) {
		JXTable table = ui.incrementPanel.getTblOther();
		int i = table.getSelectedRow();
		if (i >= 0) {
			WaybillOtherCharge model = (WaybillOtherCharge) table.getModel();
			List<OtherChargeVo> data = model.getData();

			// 其他费用合计
			OtherChargeVo vo = data.get(i);
			bean.setHandDeliveryFee(BigDecimal.ZERO);
			BigDecimal money = new BigDecimal(vo.getMoney());
			
			//是否可以删除
			Boolean isDelete = vo.getIsDelete();
			
			if(isDelete == null){
				MsgBox.showInfo(vo.getChargeName() + i18n.get("foss.gui.creating.deleteOtherChargeAction.MsgBox.notSetIsDelete"));
				return;
			}
		   if(PriceEntityConstants.QT_CODE_CZHCZFWF.equals(vo.getCode())){
			    isDelete=false;
				// 总件数
				int zjs=bean.getGoodsQtyTotal();
				//总总量
				BigDecimal zzl=bean.getGoodsWeightTotal();
				//平均重量
				BigDecimal cz=zzl.divide(new BigDecimal(zjs),1,BigDecimal.ROUND_HALF_UP);
				if(cz.compareTo(new BigDecimal(NumberConstants.NUMBER_500))<=0){
					isDelete = true;
				}
		   }
		   if(PriceEntityConstants.QT_CODE_CZHCZFWF_SDTJ.equals(vo.getCode())){
			   isDelete=true;
		   }
		   
			/**
			 * 月结
			 */
			Boolean chargeMode = bean.getChargeMode();
			if(chargeMode == null){
				//没有填写的情况下 作为非月结处理
				chargeMode = Boolean.FALSE;
			}
			/**
			 * 燃油附加费不能删除, 月结客户可以删除综合服务费
			 */
			if(WaybillConstants.OTHERFEE_ZHXX.equals(vo.getCode()) 
					&& ! chargeMode  ){
				
				MsgBox.showInfo(i18n.get("foss.gui.creating.deleteOtherChargeAction.MsgBox.notMonthCustomer")
						+ vo.getChargeName() + i18n.get("foss.gui.creating.deleteOtherChargeAction.MsgBox.notBeDelete"));
				return;
				
			}else if(!isDelete){
				if(PriceEntityConstants.QT_CODE_CZHCZFWF.equals(vo.getCode())){
					MsgBox.showInfo(i18n.get("foss.gui.creating.deleteOtherChargeAction.MsgBox.CZHCZFWFnotBeDelete"));
					return;
					 
				}else{
					 MsgBox.showInfo(vo.getChargeName() + i18n.get("foss.gui.creating.deleteOtherChargeAction.MsgBox.notBeDelete"));
					return;
				}
			}
			
			//累计其他费用面板所有费用(因为当点击计算总运费以后，其他费用中已经去除各种特殊归集费用，要把特殊费用再次累加到其他费用，方便下一次归集)
			CommoForFeeUtils.otherPanelSumFee(data,bean);
			
			BigDecimal otherFee = bean.getOtherFee().subtract(money);
			bean.setOtherFee(otherFee);
			bean.setOtherFeeCanvas(bean.getOtherFee().toString());
			Object obj = data.get(i);
			if(obj!=null){
				List<OtherChargeVo> list = bean.getOtherChargeVos();
				list.remove(obj);
				data.remove(obj);
			}
			ui.incrementPanel.setChangeDetail(data);

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
	public void setIInjectUI(WaybillRFCUI ui) {

		this.ui = ui;

	}

}