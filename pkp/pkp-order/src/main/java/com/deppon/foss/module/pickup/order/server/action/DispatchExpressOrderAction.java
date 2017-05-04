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
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.server.service.IExpressOrderTaskHandleService;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderExpressService;
import com.deppon.foss.module.pickup.order.api.shared.define.ActionMessageType;
import com.deppon.foss.module.pickup.order.api.shared.define.DispatchOrderStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderVehicleDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.FailOrderDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderQueryHandleDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckDto;
import com.deppon.foss.module.pickup.order.api.shared.exception.DispatchException;
import com.deppon.foss.module.pickup.order.api.shared.vo.DispatchOrderConditionVo;
import com.deppon.foss.module.pickup.order.api.shared.vo.DispatchOrderVo;
import com.deppon.foss.module.pickup.order.api.shared.vo.OrderHandleVo;
import com.deppon.foss.module.pickup.order.api.shared.vo.VehicleVo;
import com.deppon.foss.util.define.FossConstants;

/**
 * 调度订单Action
 * 
 * @author 038590-foss-wanghui
 * @date 2012-10-22 上午8:39:26
 */
public class DispatchExpressOrderAction extends AbstractAction {
	// 序列id
	private static final long serialVersionUID = -1304707379397579594L;

	// 查询条件VO
	private DispatchOrderConditionVo dispatchOrderConditionVo;

	// 调度订单结果VO
	private DispatchOrderVo dispatchOrderVo = new DispatchOrderVo();

	// 拒绝订单VO
	private OrderHandleVo orderHandleVo;
	
	private VehicleVo vehicleVo = new VehicleVo();
	
	// 当前登录人所属快递大区下的行政区域编码列表（以逗号隔开）
	private String countyCodes;
	
	private IDispatchOrderEntityDao dispatchOrderEntityDao;
	
	private ICustomerService customerService;

	public VehicleVo getVehicleVo() {
		return vehicleVo;
	}


	public void setVehicleVo(VehicleVo vehicleVo) {
		this.vehicleVo = vehicleVo;
	}

	// 短信内容
	private String smsContent;

	// 订单service
	private IOrderExpressService orderExpressService;

	// 订单处理Service
	private IExpressOrderTaskHandleService expressOrderTaskHandleService;

	/**
	 * 受理订单.
	 * 
	 * @return the string
	 * @author 038590-foss-wanghui
	 * @date 2012-12-18 下午7:59:45
	 */
	@JSON
	public String acceptDispatchExpressOrder() {
		try {
			// 判空，返回订单为空
			if (orderHandleVo == null) {
				returnError(ActionMessageType.NONE_ORDER);
			}
			// 调用受理订单
			List<FailOrderDto> failOrderList = expressOrderTaskHandleService.acceptOrder(orderHandleVo.getOrderHandleDtoList());
			// 设置受理失败的订单集合
			dispatchOrderVo.setFailOrderList(failOrderList);
			// 返回成功
			return returnSuccess(ActionMessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			// 失败
			return returnError(e);
		}
	}


	public void setOrderExpressService(IOrderExpressService orderExpressService) {
		this.orderExpressService = orderExpressService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 根据条件查询调度订单.
	 * 
	 * @return the string
	 * @author 038590-foss-wanghui
	 * @date 2012-10-22 上午8:40:25
	 */
	@JSON
	public String queryExpressOrder() {
		try {
			if(dispatchOrderConditionVo!=null) {
				// 查询订单服务，分页处理
				DispatchOrderDto dispatchOrderDto = orderExpressService.queryExpressDispatchOrders(dispatchOrderConditionVo.getDispatchOrderConditionDto(), this.getStart(), this.getLimit());
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
	public String rejectDispatchExpressOrder() {
		try {
			// 判空
			if (orderHandleVo == null) {
				// 为空，则直接返回退回订单失败
				throw new DispatchException(ActionMessageType.REJECT_ORDER_FAILURE);
			}
			// 退回订单服务
			expressOrderTaskHandleService.rejectOrder(orderHandleVo.getOrderHandleDto());
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
	public String queryExpressVehicleRecord() {
		try {
			// 返回符合条件的派车记录总数
			Long totalCount = orderExpressService.getVehicleRecordByCount(dispatchOrderConditionVo.getDispatchOrderVehicleDto());
			if (totalCount > 0) {
				// 返回符合条件的派车记录
				List<DispatchOrderVehicleDto> dtos = orderExpressService.queryVehicleRecordBy(dispatchOrderConditionVo.getDispatchOrderVehicleDto(), this.getStart(), this.getLimit());
				// 设置派车记录
				dispatchOrderConditionVo.setVehicleDtos(dtos);
			} else {
				// 设置派车记录
				dispatchOrderConditionVo.setVehicleDtos(null);
				this.setTotalCount(Long.valueOf(0));
			}
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
	public String updateExpressDispatchOrderStatusById() {
		try {
			// 根据订单id更新订单状态
			expressOrderTaskHandleService.updateDispatchOrderStatusById(dispatchOrderConditionVo.getDispatchOrderVehicleDto());
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
	public String updateExpressIspdaByVehicleId() {
		try {
			// PDA异常，更新订单状态和派车记录
			expressOrderTaskHandleService.updateIspdaByVehicleId(dispatchOrderConditionVo.getOrderIdsAndVehicleIds());
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
	public String queryExpressSmsContent() {
		String smsCode = DispatchOrderStatusConstants.TEMPLATE_PICKUP_ORDER_TO_COURIER_SMS;
		try {
			if (orderHandleVo != null) {
				smsCode = validaSmsCodeExtracted(smsCode);
				smsContent = expressOrderTaskHandleService.getSmsContent(smsCode, orderHandleVo.getOrderHandleDto());
			}
			return returnSuccess();
		} catch (BusinessException e) {
			// 返回异常信息
			return returnError(e);
		}
	}


	private String validaSmsCodeExtracted(String smsCode) {
		if(null!=orderHandleVo.getOrderHandleDto()) {
			if(StringUtils.isNotBlank(orderHandleVo.getOrderHandleDto().getOrderNo())) {
				DispatchOrderEntity order = dispatchOrderEntityDao.queryOrderByOrderNo(orderHandleVo.getOrderHandleDto().getOrderNo());
				if(StringUtils.isNotBlank(order.getDeliveryCustomerCode())) {
					//按发货人客户编码查询
					CustomerEntity customerEntity = new CustomerEntity();
					customerEntity.setActive(FossConstants.YES);
					customerEntity.setCusCode(order.getDeliveryCustomerCode());
					CustomerEntity ce = customerService.queryCustInfoByCustomerEntity(customerEntity);
					if(ce!=null&&FossConstants.CUSTOMER_CONTRABAND.equals(ce.getBlackListCategory())) {
						smsCode = DispatchOrderStatusConstants.TEMPLATE_PICKUP_ORDER_TO_COURIER_SMS_CONTRABAND;
					}
				}
			}
		}
		return smsCode;
	}
	
	/**
	 * 查询可用快递员
	 * @return
	 */
	@JSON
	public String queryExpressUsedUser() {
		try {
			List<OwnTruckDto> ownTruckDtos = orderExpressService.queryUsedUser(vehicleVo.getOwnTruckConditionDto());
			vehicleVo.setUsedVehicleDtos(ownTruckDtos);
			return returnSuccess();
		} catch (BusinessException e) {
			// 返回异常信息
			return returnError(e);
		}
	}
	
	/**
	 * 查询全部快递员
	 * @return
	 */
	@JSON
	public String queryExpressAllUser() {
		try {
			Long count = orderExpressService.queryAllUserCount(vehicleVo.getOwnTruckConditionDto());
			if (count > 0) {
				List<OwnTruckDto> ownTruckDtos = orderExpressService.queryAllUser(vehicleVo.getOwnTruckConditionDto(), this.getStart(), this.getLimit());
				vehicleVo.setUsedVehicleDtos(ownTruckDtos);
			} else {
				vehicleVo.setUsedVehicleDtos(null);
				this.setTotalCount(Long.valueOf(0));
			}
			this.setTotalCount(count);
			return returnSuccess();
		} catch (BusinessException e) {
			// 返回异常信息
			return returnError(e);
		}
	}
	

	/**
	 * 查询当前登录人所属快递大区下的行政区域编码列表（以逗号隔开）
	 * @return
	 */
	@JSON
	public String queryCountyCodes() {
		try {
			OrderQueryHandleDto orderQueryHandleDto = orderExpressService.setOrderQueryHandleDto(null);
			this.countyCodes = orderQueryHandleDto.getCountyCodes();
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
	 * Sets the expressOrderTaskHandleService.
	 * 
	 * @param expressOrderTaskHandleService the expressOrderTaskHandleService to see
	 */
	

	public String getSmsContent() {
		return smsContent;
	}

	public void setExpressOrderTaskHandleService(
			IExpressOrderTaskHandleService expressOrderTaskHandleService) {
		this.expressOrderTaskHandleService = expressOrderTaskHandleService;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}


	public String getCountyCodes() {
		return countyCodes;
	}


	public void setCountyCodes(String countyCodes) {
		this.countyCodes = countyCodes;
	}


	/**
	 * @param dispatchOrderEntityDao the dispatchOrderEntityDao to set
	 */
	public void setDispatchOrderEntityDao(
			IDispatchOrderEntityDao dispatchOrderEntityDao) {
		this.dispatchOrderEntityDao = dispatchOrderEntityDao;
	}


	/**
	 * @param customerService the customerService to set
	 */
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
	
	
}