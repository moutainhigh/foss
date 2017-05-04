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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/listener/WebOrderComponentListener.java
 * 
 * FILE NAME        	: WebOrderComponentListener.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.listener;

import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.creating.client.action.io.WaybillExporter;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.order.WebOrderDialog;
import com.deppon.foss.module.pickup.creating.client.vo.WebOrderVo;

/**
 * 
 * 网上订单数据初始化
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-11-13 下午4:30:44
 */
public class WebOrderComponentListener implements HierarchyListener {

	//log object
	private static Log log = LogFactory.getLog(WaybillExporter.class);
	
	//waybill service
	private IWaybillService waybillService = WaybillServiceFactory
			.getWaybillService();
	//if this listenre is initialized
	private boolean isInit;

	private WebOrderDialog webOrderDialog;
	//vo
	private WebOrderVo webOrderVo;

	public WebOrderComponentListener(WebOrderDialog webOrderDialog) {
		this.webOrderDialog = webOrderDialog;
		webOrderVo = webOrderDialog.getBinder().getBean();

	}

	@Override
	public void hierarchyChanged(HierarchyEvent e) {
		if ((HierarchyEvent.SHOWING_CHANGED & e.getChangeFlags()) != 0
				&& e.getComponent().isShowing()) {
			if (!isInit) {
				initCombOrderStatus();// 受理状态
				initCombOrderType();// 订单类型
				resetToDefaultValue();
				isInit = true;
			}
		}
	}

	/**
	 * 
	 * 查询条件初始化
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-13 下午4:32:58
	 */
	@SuppressWarnings("deprecation")
	public void resetToDefaultValue() {
		webOrderDialog.getCombOrderType().setSelectedIndex(0);
		webOrderDialog.getCombOrderStatus().setSelectedIndex(0);
		webOrderVo.setOrderNo(null);
		webOrderVo.setDeliveryCustomerName(null);
		webOrderVo.setDeliveryCustomerContact(null);
		webOrderVo.setDeliveryCustomerMobilephone(null);
		webOrderVo.setDeliveryCustomerPhone(null);
		webOrderVo.setWaybillNumber(null);//重置运单号
		Date stratDate = new Date();
		Date endDate = new Date();
		stratDate.setDate(stratDate.getDate() -2);
		webOrderDialog.getDatePickerStart().setDate(stratDate);
		webOrderDialog.getDatePickerEnd().setDate(endDate);
	}

	/**
	 * 
	 * 订单类型数据初始化
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-13 上午10:30:08
	 */
	private void initCombOrderType() {
		List<DataDictionaryValueEntity> list = waybillService.queryOrderType();
		DefaultComboBoxModel combOrderTypeModel = webOrderDialog
				.getOrderTypeModel();
		// 全部
		DataDictionaryValueVo allOrderType = new DataDictionaryValueVo();
		//allOrderType.setValueCode("");
		allOrderType.setValueName("");
		combOrderTypeModel.addElement(allOrderType);
		for (DataDictionaryValueEntity dataDictionary : list) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			try {
				BeanUtils.copyProperties(vo, dataDictionary);
			} catch (IllegalAccessException e) {
				log.error("IllegalAccessException",e);
			} catch (InvocationTargetException e) {
				log.error("InvocationTargetException",e);
			}
			combOrderTypeModel.addElement(vo);
		}
	}

	/**
	 * 
	 * 受理状态数据初始化
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-13 上午10:30:23
	 */
	private void initCombOrderStatus() {
		List<DataDictionaryValueEntity> list = waybillService
				.queryOrderStatus();
		DefaultComboBoxModel combOrderStatusModel = webOrderDialog
				.getOrderStatusModel();
		// 全部
		DataDictionaryValueVo allOrderStatus2 = new DataDictionaryValueVo();
		//allOrderStatus2.setValueCode("ALL");
		allOrderStatus2.setValueName("");
		combOrderStatusModel.addElement(allOrderStatus2);
		for (DataDictionaryValueEntity dataDictionary : list) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			try {
				BeanUtils.copyProperties(vo, dataDictionary);
			} catch (IllegalAccessException e) {
				log.error("IllegalAccessException",e);
			} catch (InvocationTargetException e) {
				log.error("InvocationTargetException",e);
			}
			combOrderStatusModel.addElement(vo);
		}
	}

}