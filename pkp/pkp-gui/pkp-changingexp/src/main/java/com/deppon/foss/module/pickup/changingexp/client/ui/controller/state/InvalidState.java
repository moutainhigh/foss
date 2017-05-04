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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/ui/controller/state/InvalidState.java
 * 
 * FILE NAME        	: InvalidState.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.changingexp.client.ui.controller.state;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changingexp.client.ui.controller.AbstractState;
import com.deppon.foss.module.pickup.changingexp.client.ui.controller.WaybillRfcStateContext;
import com.deppon.foss.module.pickup.waybill.shared.dto.UploadVoucherDto;


/**
 * 
 * 作废状态
 * @author 102246-foss-shaohongliang
 * @date 2012-11-22 上午9:37:18
 */
public class InvalidState extends AbstractState {

	/**
	 *  国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(WaybillRFCUI.class);
	
	public InvalidState(WaybillRfcStateContext context) {
		super(context);
	}

	/**
	 * 
	 * 更新组件状态
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:57:24
	 * @see com.deppon.foss.module.pickup.changingexp.client.ui.controller.AbstractState#updateComponents(com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCUI)
	 */
	@Override
	protected void updateComponents(WaybillRFCUI rfcUI) {
		rfcUI.getImportPanel().getRdoCustomerRequire().setEnabled(true);
		rfcUI.getImportPanel().getRdoInsideRequire().setEnabled(true);
		rfcUI.getImportPanel().getTxtRfcReason().setEnabled(true);
		rfcUI.getImportPanel().getCboRfcType().setEnabled(true);
		//提交、打印按钮
		getWaybillRfcUI().getButtonPanel().getBtnSubmit().setEnabled(true);
		//上传凭证按钮
		getWaybillRfcUI().getUploadVoucherPanel().getBtnAdd().setEnabled(true);
		getWaybillRfcUI().getUploadVoucherPanel().getBtnDelete().setEnabled(true);
		rfcUI.getUploadVoucherPanel().getTable().setEnabled(true);
		rfcUI.getUploadVoucherPanel().getTable().updateUI();
		//设置查询网点不可编辑
		rfcUI.getButtonPanel().getBtnSearchBranch().setEnabled(false);
	}
	
	/**
	 * 
	 * 运输面板
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:57:35
	 * @see com.deppon.foss.module.pickup.changingexp.client.ui.controller.AbstractState#getTransferInjectPanel()
	 */
	@Override
	protected JPanel getTransferInjectPanel() {
		return getWaybillRfcUI().getWaybillInfoPanel().getTransferPanel().getTransferSinglePanel();
	}	

	/**
	 * 
	 * 变更明细
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:57:47
	 * @see com.deppon.foss.module.pickup.changingexp.client.ui.controller.AbstractState#getMessageInjectPanel()
	 */
	@Override
	protected JPanel getMessageInjectPanel() {
		return getWaybillRfcUI().getMessagePanel().getInvalidInfoPanel();
	}
	
	/**
	 * 
	 * 上传凭证
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午8:58:01
	 * @see com.deppon.foss.module.pickup.changingexp.client.ui.controller.AbstractState#getUploadVoucherDtos()
	 */
	@Override
	protected List<UploadVoucherDto> getUploadVoucherDtos() {
		List<UploadVoucherDto> uploadVoucherList = new ArrayList<UploadVoucherDto>();
		UploadVoucherDto vo = new UploadVoucherDto();
		vo.setProofName(i18n.get("pickup.changingexp.invalidProof"));
		vo.setIsDefault(true);
		uploadVoucherList.add(vo);
		vo = new UploadVoucherDto();
		vo.setProofName(i18n.get("pickup.changingexp.waybillCustomerBill"));
		vo.setIsDefault(true);
		uploadVoucherList.add(vo);
		vo = new UploadVoucherDto();
		vo.setProofName(i18n.get("pickup.changingexp.idCardCopy"));
		vo.setIsDefault(true);
		uploadVoucherList.add(vo);
		return uploadVoucherList;
	}

}