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
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-load
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/action/DeliverLoadGapReportAction.java
 *  
 *  FILE NAME          :DeliverLoadGapReportAction.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.server.action;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.transfer.load.api.server.service.IDeliverLoadTaskService;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadWaybillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SerialNoStockEntity;
import com.deppon.foss.module.transfer.load.api.shared.vo.DeliverLoadTaskVo;

/** 
 * @className: DeliverLoadTaskAction
 * @author: ShiWei shiwei@outlook.com
 * @description: 手工完成派送装车任务
 * @date: 2013-4-13 下午4:21:51
 * 
 */
public class DeliverLoadTaskAction extends AbstractAction {

	private static final long serialVersionUID = -3237394326931552695L;
	
	/**
	 * 本模块vo
	 */
	private DeliverLoadTaskVo deliverLoadTaskVo = new DeliverLoadTaskVo();
	
	/**
	 * 本模块service
	 */
	private IDeliverLoadTaskService deliverLoadTaskService;

	public void setDeliverLoadTaskService(
			IDeliverLoadTaskService deliverLoadTaskService) {
		this.deliverLoadTaskService = deliverLoadTaskService;
	}

	public DeliverLoadTaskVo getDeliverLoadTaskVo() {
		return deliverLoadTaskVo;
	}

	public void setDeliverLoadTaskVo(DeliverLoadTaskVo deliverLoadTaskVo) {
		this.deliverLoadTaskVo = deliverLoadTaskVo;
	}
	
	/**
	 * 手工确认派送装车任务时，加载派送单数据
	 * @author 045923-foss-shiwei
	 * @date 2013-4-15 下午4:23:16
	 */
	@JSON
	public String loadDeliverBillAndDetailListByNo(){
		String billNo = deliverLoadTaskVo.getBillNo();
		try{
			//派送单基本信息
			deliverLoadTaskVo.setBillInfo(deliverLoadTaskService.queryDeliverBillByNo(billNo));
			//派送单详细信息
			deliverLoadTaskVo.setBillDetailList(deliverLoadTaskService.queryDeliverBillDetailListByNo(billNo));
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 根据运单号获取流水号库存
	 * @author 045923-foss-shiwei
	 * @date 2013-4-15 下午8:34:40
	 */
	@JSON
	public String querySerialNoListForDeliverBill(){
		String waybillNo = deliverLoadTaskVo.getWaybillNo();
		try{
			//派送单基本信息
			deliverLoadTaskVo.setSerialNoList(deliverLoadTaskService.querySerialNoStockList(waybillNo));
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 确认派送装车结束
	 * @author 045923-foss-shiwei
	 * @date 2013-4-16 下午6:51:43
	 */
	@JSON
	public String confirmDeliverLoadTask(){
		//派送单号
		String billNo = deliverLoadTaskVo.getBillNo();
		//运单明细
		List<LoadWaybillDetailEntity> waybillList = deliverLoadTaskVo.getBillDetailList();
		//流水号明细
		List<SerialNoStockEntity> serialNoList = deliverLoadTaskVo.getSerialNoList();
		//派送单最后修改时间
		Date operateTime = deliverLoadTaskVo.getOperateTime();
		try{
			deliverLoadTaskService.confirmDeliverLoadTaskByHand(billNo, operateTime, waybillList, serialNoList);
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
}
