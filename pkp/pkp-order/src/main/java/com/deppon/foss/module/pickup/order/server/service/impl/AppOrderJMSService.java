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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/service/impl/CrmOrderJMSService.java
 * 
 * FILE NAME        	: CrmOrderJMSService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.server.service.impl;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.inteface.domain.app.StatusChangedRequest;
import com.deppon.foss.module.pickup.order.api.server.service.IAppOrderJMSService;
import com.deppon.foss.module.pickup.order.api.shared.dto.AppOrderDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.ows.inteface.domain.orderstate.OrderStateRequest;

/**
 * 
 * 将司机/快递员信息传给APP
 * @author 043258-foss-zhaobin
 * @date 2014-10-29 上午9:52:41
 */
public class AppOrderJMSService implements IAppOrderJMSService {

	/**
	 * 日志
	 */
	protected final static Logger logger = LoggerFactory
			.getLogger(AppOrderJMSService.class.getName());

/**
 * 
 * APP订单推送
 * @author 043258-foss-zhaobin
 * @date 2014-10-29 上午10:10:40
 * @see com.deppon.foss.module.pickup.order.api.server.service.IAppOrderJMSService#sendOrderState(com.deppon.foss.module.pickup.order.api.shared.dto.AppOrderDto)
 */
	@Override
	public ResultDto sendOrderState(AppOrderDto request) {
		logger.info("APP订单数据JMS服务 sendOrderState start : " + ReflectionToStringBuilder.toString(request));
		// 标识更新状态为成功
		String code = "1";
		// 初始化消息
		String msg = "";
		//判断是否为空
		if (request != null) {
			// 准备消息头信息
			AccessHeader header = new AccessHeader();
			header.setEsbServiceCode("ESB_FOSS2ESB_UPDATE_ORDER_STATE_FOSS");
			header.setVersion("0.1");
			header.setBusinessId(request.getOrderNo());

			StatusChangedRequest req = new StatusChangedRequest();
			req.setOrderNo(request.getOrderNo());
			req.setShippingNo(request.getShippingNo());
			req.setPosterMobilePhone(request.getPosterMobilePhone());
			req.setReceiverMobilePhone(request.getReceiverMobilePhone());
			req.setDriverCode(request.getDriverCode());
			req.setProductCode(request.getProductCode());
			req.setStatus(request.getStatus());
			//add by 329757 增加车辆车牌号属性 
			req.setVehicleNo(request.getVehicleNo());
			logger.info("APP订单数据JMS服务 sendOrderState  ing : " + ReflectionToStringBuilder.toString(req));
			// 发送请求
			try {
				ESBJMSAccessor.asynReqeust(header, req);
			} catch (Exception e) {
				// 对异常进行处理
				logger.error("订单状态修改失败：", e);
				code = "0";
				msg = "JMS请求发送失败：" + e.getMessage();
			}
		} else {
			//写异常信息到日志中
			logger.error("订单状态修改失败：传入的实体为空");
			code = "0";
			msg = "JMS请求发送失败：传入的实体为空";
		}

		//封装返回的结果信息
		ResultDto res = new ResultDto();
		res.setCode(code);
		res.setMsg(msg);
		logger.info("APP订单数据JMS服务 sendOrderState end : " + ReflectionToStringBuilder.toString(res));
		return res;
	}
	
	/** 
	 * DMANA-8883 FOSS中订单状态变为已派车时推送至移动APP
	 * @author 219396-foss-daolin
	 * @date 2014-12-9 下午15:25:40
	 *  
	 */
	@Override
	public ResultDto sendOrderStateToApp(OrderStateRequest request) {

		logger.info("APP已派车状态 sendOrderState start : " + ReflectionToStringBuilder.toString(request));
		// 标识更新状态为成功
		String code = "1";
		// 初始化消息
		String msg = "";
		//判断是否为空
		if (request != null) {
			// 准备消息头信息
			AccessHeader header = new AccessHeader();
			header.setEsbServiceCode("ESB_FOSS2ESB_ORDER_STATE_PUSH");
			header.setVersion("0.1");
			header.setBusinessId(request.getOrderNo());

			logger.info("FOSS向APP推送【已派车】订单状态开始 ,订单号为【 "+request.getOrderNo()+"】" + ReflectionToStringBuilder.toString(request));
			// 发送请求
			try {
				ESBJMSAccessor.asynReqeust(header, request);
			} catch (Exception e) {
				// 对异常进行处理
				logger.error("FOSS向APP推送【已派车】订单状态失败,订单号"+"【"+request.getOrderNo()+"】", e);
				code = "0";
				msg = "JMS请求发送失败：" + e.getMessage();
			}
		} else {
			//写异常信息到日志中
			logger.error("FOSS向APP推送【已派车】订单状态失败：传入的实体为空");
			code = "0";
			msg = "JMS请求发送失败：传入的实体为空";
		}

		//封装返回的结果信息
		ResultDto res = new ResultDto();
		res.setCode(code);
		res.setMsg(msg);
		logger.info("FOSS向APP推送【已派车】订单状态结束 : " + ReflectionToStringBuilder.toString(res));
		return res;
	}
}