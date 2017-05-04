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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/ui/controller/WaybillRfcStateContext.java
 * 
 * FILE NAME        	: WaybillRfcStateContext.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.changing.client.ui.controller;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.changing.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changing.client.ui.controller.state.AbortState;
import com.deppon.foss.module.pickup.changing.client.ui.controller.state.CommitedState;
import com.deppon.foss.module.pickup.changing.client.ui.controller.state.CustomerChangeState;
import com.deppon.foss.module.pickup.changing.client.ui.controller.state.ImportedState;
import com.deppon.foss.module.pickup.changing.client.ui.controller.state.InitState;
import com.deppon.foss.module.pickup.changing.client.ui.controller.state.InsideChangeState;
import com.deppon.foss.module.pickup.changing.client.ui.controller.state.InvalidState;
import com.deppon.foss.module.pickup.changing.client.ui.controller.state.ReturnState;
import com.deppon.foss.module.pickup.changing.client.ui.controller.state.TransferState;
import com.deppon.foss.module.pickup.changing.client.vo.WaybillInfoVo;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;

/**
 * 
 * 更改单页面状态Context
 * @author 102246-foss-shaohongliang
 * @date 2012-12-24 下午9:09:43
 */
public class WaybillRfcStateContext {

	/**
	 * 中止状态
	 */
	private IWaybillRfcState abortState;

	/**
	 * 返货状态
	 */
	private IWaybillRfcState returnState;

	/**
	 * 转运状态
	 */
	private IWaybillRfcState transferState;

	/**
	 * 作废状态
	 */
	private IWaybillRfcState invalidState;

	/**
	 * 客户变更状态
	 */
	private IWaybillRfcState customerChangeState;

	/**
	 * 内部变更状态
	 */
	private IWaybillRfcState insideChangeState;

	/**
	 * 提交状态
	 */
	private IWaybillRfcState commitedState;

	/**
	 * 初始状态 
	 */
	private IWaybillRfcState initState;

	/**
	 * 导入状态
	 */
	private IWaybillRfcState importedState;
	
	/**
	 * 当前状态
	 */
	private IWaybillRfcState currentState;

	/**
	 * 更改单UI
	 */
	private WaybillRFCUI waybillRFCUI;

	public WaybillRfcStateContext(WaybillRFCUI waybillRFCUI) {
		this.waybillRFCUI = waybillRFCUI;
		buildStates();
	}

	/**
	 * 
	 * 构建状态类
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午9:12:00
	 */
	private void buildStates() {
		abortState = new AbortState(this);
		commitedState = new CommitedState(this);
		initState = new InitState(this);
		returnState = new ReturnState(this);
		transferState = new TransferState(this);
		invalidState = new InvalidState(this);
		customerChangeState = new CustomerChangeState(this);
		insideChangeState = new InsideChangeState(this);
		importedState = new ImportedState(this);
	}

	public WaybillRFCUI getWaybillRFCUI() {
		return waybillRFCUI;
	}

	public void setWaybillRFCUI(WaybillRFCUI waybillRFCUI) {
		this.waybillRFCUI = waybillRFCUI;
	}

	/**
	 * 
	 * 获取当前页面状态
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午9:13:06
	 */
	public IWaybillRfcState getCurrentRfcState() {
		//绑定VO
		WaybillInfoVo binderWaybill = waybillRFCUI.getBinderWaybill();

		// 提交后不允许再修改，只能打印
		// if(currentState == commitedState)
		// return currentState;

		if (binderWaybill == null) {
			//返货初始状态
			currentState = initState;
		} else {
			//变更类型
			DataDictionaryValueVo rfcType = binderWaybill.getRfcType();
			if (StringUtil.isEmpty(binderWaybill.getWaybillNo())) {
				//导入错误的运单号恢复为初始状态
				currentState = initState;
			} else if (rfcType == null) {
				//变更类型重置后回到导入状态
				currentState = importedState;
			} else if (WaybillRfcConstants.CUSTOMER_CHANGE.equals(rfcType
					.getValueCode())) {
				currentState = customerChangeState;

			} else if (WaybillRfcConstants.TRANSFER.equals(rfcType
					.getValueCode())) {
				currentState = transferState;

			} else if (WaybillRfcConstants.RETURN
					.equals(rfcType.getValueCode())) {
				currentState = returnState;

			} else if (WaybillRfcConstants.INVALID.equals(rfcType
					.getValueCode())) {
				currentState = invalidState;

			} else if (WaybillRfcConstants.INSIDE_CHANGE.equals(rfcType
					.getValueCode())) {
				currentState = insideChangeState;

			} else if (WaybillRfcConstants.ABORT.equals(rfcType.getValueCode())) {
				currentState = abortState;

			}

		}
		return currentState;

	}

	/**
	 * 
	 * 提交运单后更新状态
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午9:15:26
	 */
	public void commit() {
		currentState = commitedState;
		currentState.performComponentsState();
	}

}