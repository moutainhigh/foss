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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/ui/controller/state/InitState.java
 * 
 * FILE NAME        	: InitState.java
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

import javax.swing.JComponent;

import com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changingexp.client.ui.controller.AbstractState;
import com.deppon.foss.module.pickup.changingexp.client.ui.controller.WaybillRfcStateContext;
import com.deppon.foss.module.pickup.waybill.shared.dto.UploadVoucherDto;


/**
 * 
 * 初始状态
 * @author 102246-foss-shaohongliang
 * @date 2012-11-22 上午9:37:18
 */
public class InitState extends AbstractState {
	
	public InitState(WaybillRfcStateContext context) {
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
		rfcUI.getImportPanel().getRdoCustomerRequire().setEnabled(false);
		rfcUI.getImportPanel().getRdoInsideRequire().setEnabled(false);
		rfcUI.getImportPanel().getTxtRfcReason().setEnabled(false);
		rfcUI.getImportPanel().getCboRfcType().setEnabled(false);
		rfcUI.getImportPanel().getCboRfcType().setSelectedIndex(-1);
		rfcUI.getWaybillInfoPanel().getIncrementPanel().getTable().setEnabled(false);
		rfcUI.getWaybillInfoPanel().getIncrementPanel().getTxtServiceCharge().setEnabled(false);
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
	protected JComponent getTransferInjectPanel() {
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
	protected JComponent getMessageInjectPanel() {
		return getWaybillRfcUI().getMessagePanel().getChangedInfoPanel();
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
		return new ArrayList<UploadVoucherDto>();
	}
}