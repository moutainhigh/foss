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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/DeleteOtherChargeAction.java
 * 
 * FILE NAME        	: DeleteOtherChargeAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.utils.CommoForFeeUtils;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.creating.client.ui.editui.IncrementPanel.WaybillOtherCharge;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;

/**
 * 
 * 运单DeleteOtherChargeAction
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:16:43,
 * </p>
 * @author 105089-FOSS-yangtong
 * @date 2012-10-17 上午11:16:43
 * @since
 * @version
 */
public class DeleteOtherChargeAction extends
		AbstractButtonActionListener<WaybillEditUI> {

	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();

	WaybillEditUI ui;

	IBinder<WaybillPanelVo> waybillBinder;
	
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
	public void actionPerformed(ActionEvent e) {
		HashMap<String, IBinder<WaybillPanelVo>> map = ui.getBindersMap();
		waybillBinder = map.get("waybillBinder");
		WaybillPanelVo bean = waybillBinder.getBean();

		deleteTableRow(bean);

	}

	/**
	 * 
	 * 删除选中的其他费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 下午04:48:28
	 */
	private void deleteTableRow(WaybillPanelVo bean) {
		JXTable table = ui.incrementPanel.getTblOther();
		int i = table.getSelectedRow();
		if (i >= 0) {
			WaybillOtherCharge model = (WaybillOtherCharge) table.getModel();
			List<OtherChargeVo> data = model.getData();

			// 其他费用合计
			OtherChargeVo vo = data.get(i);
			BigDecimal money = new BigDecimal(vo.getMoney());
			
			//是否可以删除
			Boolean isDelete = vo.getIsDelete();
			
			if(isDelete == null){
				MsgBox.showInfo(vo.getChargeName() + i18n.get("foss.gui.creating.deleteOtherChargeAction.MsgBox.notSetIsDelete"));
				return;
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
			 * 燃油附加费不能删除, 月结客户不可以删除综合服务费
			 */
			if (WaybillConstants.OTHERFEE_ZHXX.equals(vo.getCode()) && chargeMode) {
				MsgBox.showInfo(i18n.get("foss.gui.creating.deleteOtherChargeAction.MsgBox.month"));
				return;
			}
			if(PriceEntityConstants.QT_CODE_CZHCZFWF.equals(vo.getCode())){
				if(!isDelete){
					MsgBox.showInfo(i18n.get("foss.gui.creating.deleteOtherChargeAction.MsgBox.CZHCZFWFnotBeDelete"));
					return;
				}
			}else{
				if(!isDelete){
					MsgBox.showInfo(vo.getChargeName() + i18n.get("foss.gui.creating.deleteOtherChargeAction.MsgBox.notBeDelete"));
					return;
				}
			}
			
			
			//累计其他费用面板所有费用(因为当点击计算总运费以后，其他费用中已经去除各种特殊归集费用，要把特殊费用再次累加到其他费用，方便下一次归集)
			CommoForFeeUtils.otherPanelSumFee(data,bean);
			
			BigDecimal otherFee = bean.getOtherFee().subtract(money);
			bean.setOtherFee(otherFee);
			bean.setOtherFeeCanvas(bean.getOtherFee().toString());

			data.remove(i);
			ui.incrementPanel.setChangeDetail(data);
			if(ui.getPictureWaybillType() != null && WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType().trim())){
				String weight = ui.pictureCargoInfoPanel.getTxtWeight().getText();
				String volume = ui.pictureCargoInfoPanel.getTxtVolume().getText();
				if(StringUtils.isNotBlank(weight) && new BigDecimal(weight).compareTo(BigDecimal.ZERO) > 0 
						&& StringUtils.isNotBlank(volume) && new BigDecimal(volume).compareTo(BigDecimal.ZERO) > 0){
					ui.incrementPanel.getBtnCalculate().setEnabled(true);
					ui.billingPayPanel.getBtnSubmit().setEnabled(false);// 提交为不可编辑
					ui.billingPayPanel.getBtnSubmitAndNextSingle().setEnabled(false);
					ui.incrementPanel.getJlable().setVisible(false);
					ui.incrementPanel.getCombServiceRate().setVisible(false);
				}else{
					ui.incrementPanel.getBtnCalculate().setEnabled(false);
					ui.billingPayPanel.getBtnSubmit().setEnabled(true);// 提交为不可编辑
					ui.billingPayPanel.getBtnSubmitAndNextSingle().setEnabled(true);
					ui.incrementPanel.getJlable().setVisible(true);
					ui.incrementPanel.getCombServiceRate().setVisible(true);
				}

			}else{
				ui.billingPayPanel.getBtnSubmit().setEnabled(false);// 提交为不可编辑
				ui.buttonPanel.getBtnSubmit().setEnabled(false);// 提交为不可编辑
			}
		}

	}

	@Override
	public void setIInjectUI(WaybillEditUI ui) {

		this.ui = ui;

	}

}