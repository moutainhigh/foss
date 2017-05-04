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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/QuerySalesDepartmentAction.java
 * 
 * FILE NAME        	: QuerySalesDepartmentAction.java
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
import java.math.BigDecimal;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.creating.client.common.Common;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.creating.client.ui.popupdialog.SalesDepartmentDialog;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;

/**
 * （查询收货部门）
 * 
 * @author 025000-foss-helong
 * @date 2012-11-1 下午7:59:52
 */
public class QuerySalesDepartmentAction implements IButtonActionListener<WaybillEditUI> {

	// 主界面
	WaybillEditUI ui;

	@Override
	public void actionPerformed(ActionEvent e) {

		HashMap<String, IBinder<WaybillPanelVo>> map = ui.getBindersMap();
		IBinder<WaybillPanelVo> waybillBinder = map.get("waybillBinder");
		WaybillPanelVo bean = waybillBinder.getBean();

		try {
			//弹出查询收货部门对话框
			int n = 0 ;//n=0:非图片开单,n=1:图片开单
			String waybillNo = null ;
			if(ui.getPictureWaybillType()!=null && WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType()) ){
				waybillNo = ui.basicPanel.getTxtWaybillNO().getText();
				n = 1 ;
			}
			SalesDepartmentDialog dialog = new SalesDepartmentDialog(n,waybillNo);
//			SalesDepartmentDialog dialog = new SalesDepartmentDialog();
			// 剧中显示弹出窗口
			WindowUtil.centerAndShow(dialog);
			//获得收获部门信息
			SaleDepartmentEntity entity = dialog.getSaleDepartmentEntity();
			//设置收获部门信息
			Common.setSalesDepartmentForCentrial(entity, bean, ui);
			bean.setIsBigGoods(false);
			/**
			 * 当导入的PDA运单没有收货部门时，需要手工设置收货部门
			 * 当手动设置收货部门时，系统初始化产品类型，
			 * 设置产品类型时，同时需要自动设置提货方式
			 */
			// 根据运输性质的改变，改变提货方式
			Common.changePickUpMode(bean, ui);
			
			if(entity!=null && entity.getCode()!=null && !entity.getCode().equals(bean.getReceiveOrgCode())){
				// 提交为不可编辑
				if(ui.getPictureWaybillType() != null && WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType().trim())){
					String weight = ui.pictureCargoInfoPanel.getTxtWeight().getText();
					String volume = ui.pictureCargoInfoPanel.getTxtVolume().getText();
					if(StringUtils.isNotBlank(weight) && new BigDecimal(weight).compareTo(BigDecimal.ZERO) > 0 
							&& StringUtils.isNotBlank(volume) && new BigDecimal(volume).compareTo(BigDecimal.ZERO) > 0){
						ui.incrementPanel.getBtnCalculate().setEnabled(true);
				ui.billingPayPanel.getBtnSubmit().setEnabled(false);
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
				ui.buttonPanel.getBtnSubmit().setEnabled(false);
				}
			}
			
			/**
			 * 集中开单查询走货线路时，出发部门不是收货部门，是(集中开单查开单组所属中转场的默认出发部门)
			 */
			//当收货部门 不为空，且走货线路不为空时，需要重新根据新的出发部门和到达部门设置走货线路
//			if(entity != null && StringUtils.isNotEmpty(bean.getLoadLineName())){
//				// 整车的提货网点不需要设置线路和空运配载及时效
//				if (!ui.basicPanel.getChbWholeVehicle().isSelected()) {
//					// 提货网点
//					ShowPickupStationDialogAction pickupStationAction = new ShowPickupStationDialogAction();
//					pickupStationAction.setInjectUI(ui);
//					// 设置线路
//					pickupStationAction.setLoadLine(bean);
//					// 设置空运配载
//					pickupStationAction.setAirDeptEnabled(bean);
//				}
//			}
		} catch (WaybillValidateException w) {
			MsgBox.showInfo(w.getMessage());
		} catch (BusinessException ex) {
			if (!"".equals(ex.getMessage())) {
				MsgBox.showInfo(ex.getMessage());
			}
		}
	}

	@Override
	public void setInjectUI(WaybillEditUI ui) {
		this.ui = ui;
	}

}