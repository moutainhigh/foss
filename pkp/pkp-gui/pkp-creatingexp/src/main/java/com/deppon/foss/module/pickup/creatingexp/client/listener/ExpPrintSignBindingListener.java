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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/listener/PrintSignBindingListener.java
 * 
 * FILE NAME        	: PrintSignBindingListener.java
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
package com.deppon.foss.module.pickup.creatingexp.client.listener;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.binding.BindingEvent;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.binding.IBindingListener;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.pickup.common.client.service.IOuterBranchService;
import com.deppon.foss.module.pickup.common.client.service.impl.OuterBranchService;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.vo.StartSignLabelVo;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpPrintSignUI;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.google.inject.Injector;

/**
 * 
 * 打印(联动监听)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 下午03:01:15,content:
 * </p>
   @author 105089-foss-yangtong
 * @date 2012-10-17 下午03:01:15
 * @since
 * @version
 */
public class ExpPrintSignBindingListener implements IBindingListener {
	
	ExpPrintSignUI ui;
	
	IBinder<StartSignLabelVo> printBinder;
	
	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	
	Injector injector = GuiceContextFactroy.getInjector();
	//空运、偏线
	IOuterBranchService outerBranchService = injector.getInstance(OuterBranchService.class);
	
	private static Log log = LogFactory.getLog(ExpPrintSignBindingListener.class);
	
	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(ExpPrintSignBindingListener.class); 
	
	public ExpPrintSignBindingListener(ExpPrintSignUI ui) {
		this.ui = ui;
	}
	
	@Override
	public void bindingTriggered(List<BindingEvent> events) {	
		HashMap<String, IBinder<StartSignLabelVo>> map = ui.getBindersMap();
		printBinder = map.get("printBinder");
		StartSignLabelVo bean = printBinder.getBean();
		try {
			for (BindingEvent bindingEvent : events) {
				if ("waybillNo".equals(bindingEvent.getPropertyName())) {// 运单号
					waybillNoListener(bean);
				}
			} 
		}catch (Exception w) {
			log.error("WaybillValidateException", w);
			MsgBox.showInfo(i18n.get("foss.gui.creating.listener.printSign.dept"));
		}
	}
	
	/**
	 * 
	 * 运单号事件监听
	 * 
	 * @author 105089-FOSS-yangtong
	 * @date 2012-11-3 下午02:54:46
	 */
	private void waybillNoListener(StartSignLabelVo vo) {
		WaybillDto dto = waybillService.queryWaybillByNo(vo.getWaybillNo());
		if (dto != null) {
			OrgAdministrativeInfoEntity orgInfo;
			OuterBranchEntity branch;
			WaybillEntity waybill = dto.getWaybillEntity();
			if (waybill != null) {
//				//是否为经济快递的运单号
//				if(!ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE.equals(waybill.getProductCode())){
//					MsgBox.showInfo(i18n.get("foss.gui.creatingexp.expPrintSignAction.MsgBox.notExpressProduct"));
//					return;
//				}
				
				String receiveOrgCode = dto.getWaybillEntity().getReceiveOrgCode();
				orgInfo = waybillService.queryByCode(StringUtil.defaultIfNull(receiveOrgCode));
				ui.getTxtStartOrg().setText(orgInfo.getName());
				String customerPickupOrgCode = waybill.getCustomerPickupOrgCode();
				orgInfo = waybillService.queryByCode(StringUtil.defaultIfNull(customerPickupOrgCode));
				// 查询不到再到空运或者偏线查询
				if (orgInfo == null) {
					branch = outerBranchService.queryOuterBranchByCode(customerPickupOrgCode, dto.getWaybillEntity().getBillTime());
					if (branch == null) {
						MsgBox.showInfo(i18n.get("foss.gui.creating.printSignAction.MsgBox.nullWaybillEntity"));
						return;
					}
					ui.getTxtReachOrg().setText(branch.getSimplename());
				} else {
					ui.getTxtReachOrg().setText(orgInfo.getName());
				}
				String targetOrgCode = waybill.getTargetOrgCode();
				ui.getTxtDestnationOrg().setText(targetOrgCode);
			}
		}
	}

	/**
	 * 
	 * 是否vo.set***也触发该事件类
	 * @author 025000-FOSS-helong
	 * @date 2013-1-10 上午09:05:30
	 * @return
	 */
	@Override
	public boolean isFromVoTargetEnable(){
		return false;
	}
	

}