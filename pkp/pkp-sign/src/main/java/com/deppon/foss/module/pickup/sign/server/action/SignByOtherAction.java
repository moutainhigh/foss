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
 * PROJECT NAME	: pkp-sign
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/action/SignByOtherAction.java
 * 
 * FILE NAME        	: SignByOtherAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.action;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignByOtherService;
import com.deppon.foss.module.pickup.sign.api.shared.vo.SignByOtherVo;

/***
 * 处理他人收件 Action
 * 客户自提时，若原收货人名字错误或代提人无法提供开单收货人有效证件，
 * 营业员通过该用例可录入授权密码查询到发货人联系方式，
 * 并要求发货人在原单上修改收货人名字后传至收到达部门
 * @date 2012-11-26 上午10:36:08
 */
public class SignByOtherAction extends AbstractAction {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 视图对象
	 */
	private SignByOtherVo vo = new SignByOtherVo();
	
	/**
	 * 处理他人收件 Service
	 */
	private ISignByOtherService signByOtherService;

	
	/***
	 * 营业员通过输入运单号，经部门经理授权后查询发货客户联系方式
	 * @date 2012-11-26 上午10:41:13
	 */
	@JSON
	public String queryWaybillReceiverInfo() {
		try {
			//营业员通过输入运单号，经部门经理授权后查询发货客户联系方式
			vo.setRtSearchWaybillSignByOtherDto(
				signByOtherService.queryWaybillReceiverInfo(
					vo.getSearchWaybillSignByOtherDto()));
		} catch (BusinessException e) {
			//异常
			return returnError(e);
			// 返回error
		}
		return returnSuccess();
		// 返回success
	}
	
	/***
	 * 发货客户更改收货人的电子凭证
	 * @date 2012-11-26 上午12:41:13
	 */
	@JSON
	public String updateWaybillReceiverInfo() {
		try {
			//发货客户更改收货人的电子凭证
			vo.setRtSearchWaybillSignByOtherDto(
				signByOtherService.updateWaybillReceiverInfo(
					vo.getSearchWaybillSignByOtherDto()));
		} catch (BusinessException e) {
			//异常
			return returnError(e);
			// 返回error
		}
		return returnSuccess();
		// 返回success
	}
	
	/**
	 *  get对象
	 */
	public SignByOtherVo getVo() {
		return vo;
	}

	/**
	 *  set对象
	 */
	public void setVo(SignByOtherVo sVo) {
		this.vo = sVo;
	}

	/**
	 *  set对象
	 */
	public void setSignByOtherService(ISignByOtherService signByOtherService) {
		this.signByOtherService = signByOtherService;
	}
	
	
	
}