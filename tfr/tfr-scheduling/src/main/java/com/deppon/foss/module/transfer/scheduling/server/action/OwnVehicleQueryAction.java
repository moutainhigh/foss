/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 *  PROJECT NAME  : tfr-scheduling
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/action/InviteVehicleAction.java
 * 
 *  FILE NAME     :InviteVehicleAction.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.scheduling.server.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IPassOrderApplyService;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.OrderVehicleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.OrderVehicleVo;

public class OwnVehicleQueryAction extends AbstractAction{

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(InviteVehicleQueryAction.class);
	
	private OrderVehicleVo orderVehicleVo = new OrderVehicleVo();
	
	private IPassOrderApplyService passOrderApplyService;
	
	/**
	 * 按照查询条件查询外请车信息
	 * @return: 
	 *
	 * @author foss-wuyingjie
	 * @date 2013-3-29 下午5:28:52
	 */
	@JSON
	public String queryOrderVehicleInfoList(){
		LOGGER.trace("into action");
		
		OrderVehicleDto orderVehicleDto = passOrderApplyService.queryCompanyVehicleAndBorrowVehicleByPage(orderVehicleVo.getVehicleDriverWithDto(), this.getStart(), this.getLimit());
		
		orderVehicleVo.setVehicleDriverWithList(orderVehicleDto.getVehicleDriverWithList());
		
		this.setTotalCount(orderVehicleDto.getTotalCount());
		
		return super.returnSuccess();
		
	}

	public OrderVehicleVo getOrderVehicleVo() {
		return orderVehicleVo;
	}

	public void setOrderVehicleVo(OrderVehicleVo orderVehicleVo) {
		this.orderVehicleVo = orderVehicleVo;
	}

	public void setPassOrderApplyService(IPassOrderApplyService passOrderApplyService) {
		this.passOrderApplyService = passOrderApplyService;
	}

}
