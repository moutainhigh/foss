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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/server/action/DispatchOrderAction.java
 * 
 * FILE NAME        	: DispatchOrderAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.server.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderTaskHandleService;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderTaskQueryService;
import com.deppon.foss.module.pickup.order.api.shared.define.ActionMessageType;
import com.deppon.foss.module.pickup.order.api.shared.define.DispatchOrderStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderVehicleDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.FailOrderDto;
import com.deppon.foss.module.pickup.order.api.shared.exception.DispatchException;
import com.deppon.foss.module.pickup.order.api.shared.vo.DispatchOrderConditionVo;
import com.deppon.foss.module.pickup.order.api.shared.vo.DispatchOrderVo;
import com.deppon.foss.module.pickup.order.api.shared.vo.OrderHandleVo;

/**
 * 调度订单Action
 * 
 * @author 038590-foss-wanghui
 * @date 2012-10-22 上午8:39:26
 */
public class DispatchOrderAction extends AbstractAction {
	// 序列id
	private static final long serialVersionUID = -1304707379397579594L;

	// 查询条件VO
	private DispatchOrderConditionVo dispatchOrderConditionVo;

	// 调度订单结果VO
	private DispatchOrderVo dispatchOrderVo = new DispatchOrderVo();

	// 拒绝订单VO
	private OrderHandleVo orderHandleVo;
	
	// 短信内容
	private String smsContent;

	// 订单查询service
	private IOrderTaskQueryService orderTaskQueryService;

	// 订单处理Service
	private IOrderTaskHandleService orderTaskHandleService;

	/**
	 * 受理订单.
	 * 
	 * @return the string
	 * @author 038590-foss-wanghui
	 * @date 2012-12-18 下午7:59:45
	 */
	@JSON
	public String acceptDispatchOrder() {
		try {
			// 判空，返回订单为空
			if (orderHandleVo == null) {
				returnError(ActionMessageType.NONE_ORDER);
			}
			// 调用受理订单
			List<FailOrderDto> failOrderList = orderTaskHandleService.acceptOrder(orderHandleVo.getOrderHandleDtoList());
			// 设置受理失败的订单集合
			dispatchOrderVo.setFailOrderList(failOrderList);
			// 返回成功
			return returnSuccess(ActionMessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			// 失败
			return returnError(e);
		}
	}

	/**
	 * 根据条件查询调度订单.
	 * 
	 * @return the string
	 * @author 038590-foss-wanghui
	 * @date 2012-10-22 上午8:40:25
	 */
	@JSON
	public String queryDispatchOrder() {
		try {
			if(dispatchOrderConditionVo!=null) {
				// 查询订单服务，分页处理
				DispatchOrderDto dispatchOrderDto = orderTaskQueryService.queryDispatchOrders(dispatchOrderConditionVo.getDispatchOrderConditionDto(), this.getStart(), this.getLimit());
				// result为空，则直接返回
				if (dispatchOrderDto != null) {
					// 设置符合查询条件的订单集合
					dispatchOrderVo.setOrderList(dispatchOrderDto.getDispatchOrderEntities());
					// 设置符合查询条件的总条数
					// 分页处理用
					this.setTotalCount(dispatchOrderDto.getCount());
				}
			}
			return returnSuccess();
		} catch (DispatchException e) {
			return returnError(e);
		}
	}

	/**
	 * 退回订单.
	 * 
	 * @return the string
	 * @author 038590-foss-wanghui
	 * @date 2012-10-23 上午9:50:32
	 */
	@JSON
	public String rejectDispatchOrder() {
		try {
			// 判空
			if (orderHandleVo == null) {
				// 为空，则直接返回退回订单失败
				throw new DispatchException(ActionMessageType.REJECT_ORDER_FAILURE);
			}
			// 退回订单服务
			orderTaskHandleService.rejectOrder(orderHandleVo.getOrderHandleDto());
			// 退回成功
			return returnSuccess(ActionMessageType.REJECT_SUCCESS);
		} catch (DispatchException e) {
			// 失败返回信息
			return returnError(e);
		}
	}

	/**
	 * 根据条件查询派车记录.
	 * 
	 * @return the string
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-7 上午10:10:58
	 */
	@JSON
	public String queryVehicleRecord() {
		try {
			// 返回符合条件的派车记录
			List<DispatchOrderVehicleDto> dtos = orderTaskQueryService.queryVehicleRecordBy(dispatchOrderConditionVo.getDispatchOrderVehicleDto(), this.getStart(), this.getLimit());
			for(int i=0; i<dtos.size();i++)
			{
				dtos.get(i).setWeightAndVolume(dtos.get(i).getWeight()+"KG/" + dtos.get(i).getVolume()+"方" );
				String notes = dtos.get(i).getOperateNotes();
				if(StringUtils.isNotBlank(notes)) {
					if(!notes.startsWith("其他")) {
						if(notes.endsWith("-")) {
							notes = notes.substring(0, notes.length()-1);
							dtos.get(i).setOperateNotes(notes);
						}
					}
				}
//				if(dtos.get(i).getPendingType()!=null){
//					//判断是PDA开单还是FOSS开单
//					if(dtos.get(i).getPendingType().equals("PC_ACTIVE")){
//						//PC端FOSS开单，存入FOSS开单时间
//						dtos.get(i).setBillTime(dtos.get(i).getFossBillTime());
//					}else if(dtos.get(i).getPendingType().equals("PDA_ACTIVE")
//							|| dtos.get(i).getPendingType().equals("PDA_PENDING")){
//						//PDA开单，存入打印标签时间
//						dtos.get(i).setBillTime(dtos.get(i).getPdaPrintTime());
//					}
//				}
			}
			// 设置派车记录
			dispatchOrderConditionVo.setVehicleDtos(dtos);
			// 返回符合条件的派车记录总数
			Long totalCount = orderTaskQueryService.getVehicleRecordByCount(dispatchOrderConditionVo.getDispatchOrderVehicleDto());
			// 设置派车记录总数
			this.setTotalCount(totalCount);
		} catch (DispatchException e) {
			// 返回失败信息
			return returnError(e);
		}
		// 成功
		return returnSuccess();
	}

	/**
	 * 根据订单ID 更改订单完成状态.
	 * 
	 * @return the string
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-8 上午10:43:52
	 */
	@JSON
	public String updateDispatchOrderStatusById() {
		try {
			// 根据订单id更新订单状态
			orderTaskHandleService.updateDispatchOrderStatusById(dispatchOrderConditionVo.getDispatchOrderVehicleDto());
		} catch (DispatchException e) {
			return returnError(e);
		}
		// 成功
		return returnSuccess(ActionMessageType.MODIFY_SUCCESS);
	}

	/**
	 * PDA异常，根据派车记录ID修改是否PDA为异常 且更新订单状态为：“待改接”.
	 * 
	 * @return the string
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-9 下午3:13:35
	 */
	@JSON
	public String updateIspdaByVehicleId() {
		try {
			// PDA异常，更新订单状态和派车记录
			orderTaskHandleService.updateIspdaByVehicleId(dispatchOrderConditionVo.getOrderIdsAndVehicleIds());
		} catch (DispatchException e) {
			// 返回异常信息
			return returnError(e);
		}
		// 返回成功信息
		return returnSuccess(ActionMessageType.MODIFY_SUCCESS);
	}
	
	
	/**
	 * 
	 * 根据短信模板获取短信内容
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-6-3 下午8:05:49
	 */
	@JSON
	public String querySmsContent(){
		String smsCode = "";
		try {
			if(orderHandleVo != null){
				if (DispatchOrderStatusConstants.TYPE_PICKUP_ORDER.equals(orderHandleVo.getOrderHandleDto().getOrderType())) {
					smsCode = DispatchOrderStatusConstants.TEMPLATE_PICKUP_ORDER_TO_DRIVER_SMS;
				} else {
					smsCode = DispatchOrderStatusConstants.TEMPLATE_TRANSFER_ORDER_TO_DRIVER_SMS;
				}
				smsContent = orderTaskHandleService.getSmsContent(smsCode, orderHandleVo.getOrderHandleDto());
			}
			return returnSuccess();
		} catch (BusinessException e) {
			// 返回异常信息
			return returnError(e);
		}
	}

	/**
	 * Gets the dispatchOrderVo.
	 * 
	 * @return the dispatchOrderVo
	 */
	public DispatchOrderVo getDispatchOrderVo() {
		return dispatchOrderVo;
	}

	
	/**
	 * Sets the dispatchOrderVo.
	 * 
	 * @param dispatchOrderVo the dispatchOrderVo to see
	 */
	public void setDispatchOrderVo(DispatchOrderVo dispatchOrderVo) {
		this.dispatchOrderVo = dispatchOrderVo;
	}

	/**
	 * Gets the dispatchOrderConditionVo.
	 * 
	 * @return the dispatchOrderConditionVo
	 */
	public DispatchOrderConditionVo getDispatchOrderConditionVo() {
		return dispatchOrderConditionVo;
	}

	/**
	 * Sets the dispatchOrderConditionVo.
	 * 
	 * @param dispatchOrderConditionVo the dispatchOrderConditionVo to see
	 */
	public void setDispatchOrderConditionVo(DispatchOrderConditionVo dispatchOrderConditionVo) {
		this.dispatchOrderConditionVo = dispatchOrderConditionVo;
	}

	/**
	 * Sets the orderTaskQueryService.
	 * 
	 * @param orderTaskQueryService the orderTaskQueryService to see
	 */
	public void setOrderTaskQueryService(IOrderTaskQueryService orderTaskQueryService) {
		this.orderTaskQueryService = orderTaskQueryService;
	}

	/**
	 * Gets the orderHandleVo.
	 * 
	 * @return the orderHandleVo
	 */
	public OrderHandleVo getOrderHandleVo() {
		return orderHandleVo;
	}

	/**
	 * Sets the orderHandleVo.
	 * 
	 * @param orderHandleVo the orderHandleVo to see
	 */
	public void setOrderHandleVo(OrderHandleVo orderHandleVo) {
		this.orderHandleVo = orderHandleVo;
	}

	/**
	 * Sets the orderTaskHandleService.
	 * 
	 * @param orderTaskHandleService the orderTaskHandleService to see
	 */
	public void setOrderTaskHandleService(IOrderTaskHandleService orderTaskHandleService) {
		this.orderTaskHandleService = orderTaskHandleService;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

}