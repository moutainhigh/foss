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
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/action/WaybillRfcAccountAction.java
 * 
 * FILE NAME        	: WaybillRfcAccountAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.action;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.api.shared.vo.WaybillRfcAccountVo;

/**
 * 
 * 更改单申请查询
 * 
 * @author ibm-wangfei
 * @date Jan 29, 2013 11:18:52 AM
 */
public class WaybillRfcAccountAction  extends AbstractAction {
	
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 更改单服务接口
	 */
	private IWaybillRfcService waybillRfcService;
	
	/**
	 * 执行计划vo
	 */
	private WaybillRfcAccountVo vo =  new WaybillRfcAccountVo();

	/**
	 * 设置 更改单服务接口.
	 *
	 * @param waybillRfcService the new 更改单服务接口
	 */
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}
 
	/**
	 * 
	 * 更改单申请查询
	 * 
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 29, 2013 11:41:26 AM
	 */
	@JSON 
	public String queryChangeOrderList(){
		try {
			// 查询符合条件的记录数
			Long totalCount = this.waybillRfcService.queryChangeOrderCount(vo.getWaybillRfcForAccountServiceCondition());
			if (totalCount != null && totalCount.intValue() > 0) {
				//根据查询条件返回
				vo.setWaybillRfcForAccountServiceDto(waybillRfcService.queryChangeOrderList(vo.getWaybillRfcForAccountServiceCondition(), this.getStart(), this.getLimit()));
			} else {
				vo.setWaybillRfcForAccountServiceDto(null);
			}
			this.setTotalCount(totalCount);
			
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 更新执行节点.
	 *
	 * @return the string
	 * @author 105089-foss-yangtong
	 * @date 2012-11-20 下午4:31:29
	 */
	@JSON
	public String updateWaybillRfcIds(){
		if(vo != null){
			this.waybillRfcService.updateWaybillRfcIds(vo.getIds());
		}
		return returnSuccess();
	}

	public WaybillRfcAccountVo getVo() {
		return vo;
	}

	public void setVo(WaybillRfcAccountVo vo) {
		this.vo = vo;
	}
}