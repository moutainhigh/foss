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
 * PROJECT NAME	: pkp-order
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/server/action/SignInLogoutAction.java
 * 
 * FILE NAME        	: SignInLogoutAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.server.action;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.order.api.server.service.ISignInAndLogOutSalesPartmentService;
import com.deppon.foss.module.pickup.order.api.shared.define.ActionMessageType;
import com.deppon.foss.module.pickup.order.api.shared.define.PdaSignStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.dto.PdaSignDto;
import com.deppon.foss.module.pickup.order.api.shared.exception.DispatchException;
import com.deppon.foss.module.pickup.order.api.shared.vo.SignInAndLogOutVo;

/**
 * 调度解除POS签到
 * @author 273279-foss-liding
 * @date 2016-03-02 下午2:33:28
 */
public class SalesPartmentSignInLogoutAction extends AbstractAction{
	// 序列id
	private static final long serialVersionUID = 1L;
	// 签到注销Vo
	private SignInAndLogOutVo vo;
	// 签到注销服务
	private ISignInAndLogOutSalesPartmentService signInAndLogOutSalesPartmentService;
	
	
	/**
	 * 根据条件查询PDA签到信息.
	 * 
	 * @return the string
	 * @author 273279-foss-liding
	 * @date 2012-11-19 下午5:34:15
	 */
	@JSON
	public String querySalesPartmentSignedInfo(){
		try {
			if(null==vo||null==vo.getDto()){
				throw new DispatchException("根据条件查询PDA签到信息，传入参数不能为空！");
			}
				PdaSignDto dto = vo.getDto();
				Long count = signInAndLogOutSalesPartmentService.querySalesPartmentSignedInfoCount(dto);
				if(count > 0){
					// 查询签到信息
					this.setTotalCount(count);
					vo.setPdasignList(signInAndLogOutSalesPartmentService.querySalesPartmentSignedInfoByPage(dto, this.getStart(), this.getLimit()));
				}
		} catch (DispatchException e) {
			// 返回失败信息
			return returnError(e);
		}
		// 成功
		return returnSuccess();
	}
	
	/**
	 * 根据ID解除司机签到信息.
	 * 
	 * @return the string
	 * @author 273279-foss-liding
	 * @date 2012-11-19 下午7:01:17
	 */
	@JSON
	public String handResolveSalesPartmentBind(){
		try {
			// 解除绑定状态
			vo.getPdaEntity().setStatus(PdaSignStatusConstants.UNBUNDLE);
			// 解除绑定
			signInAndLogOutSalesPartmentService.handResolveBind(vo.getPdaEntity());
		} catch (DispatchException e) {
			// 返回失败信息
			return returnError(e);
		}
		// 成功
		return returnSuccess(ActionMessageType.UNBUNDLE_SUCCESS);
	}

	/**
	 * Gets the vo.
	 * 
	 * @return the vo
	 */
	public SignInAndLogOutVo getVo() {
		return vo;
	}

	/**
	 * Sets the vo.
	 * 
	 * @param vo the vo to see
	 */
	public void setVo(SignInAndLogOutVo vo) {
		this.vo = vo;
	}
	
	public void setSignInAndLogOutSalesPartmentService(
			ISignInAndLogOutSalesPartmentService signInAndLogOutSalesPartmentService) {
		this.signInAndLogOutSalesPartmentService = signInAndLogOutSalesPartmentService;
	}
	
}