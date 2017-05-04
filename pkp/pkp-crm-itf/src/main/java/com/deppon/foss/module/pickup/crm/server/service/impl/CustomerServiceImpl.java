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
 * PROJECT NAME	: pkp-crm-itf
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/crm/server/service/impl/CustomerServiceImpl.java
 * 
 * FILE NAME        	: CustomerServiceImpl.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.crm.server.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebParam.Mode;
import javax.jws.WebResult;
import javax.xml.ws.Holder;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.header.ESBHeader;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.customerservice.CommonException;
import com.deppon.foss.customerservice.CustomerService;
import com.deppon.foss.esb.crm.server.order.AmountInfo;
import com.deppon.foss.esb.crm.server.order.BindOrderRequest;
import com.deppon.foss.esb.crm.server.order.BindOrderResponse;
import com.deppon.foss.esb.crm.server.order.CancelOrderRequest;
import com.deppon.foss.esb.crm.server.order.CancelOrderResponse;
import com.deppon.foss.esb.crm.server.order.ClaimsPayBillGenerateRequest;
import com.deppon.foss.esb.crm.server.order.ClaimsPayBillGenerateResponse;
import com.deppon.foss.esb.crm.server.order.FossQueryAcctinfoRequest;
import com.deppon.foss.esb.crm.server.order.FossQueryAcctinfoResponse;
import com.deppon.foss.esb.crm.server.order.GoodsBillReceiveRequest;
import com.deppon.foss.esb.crm.server.order.GoodsBillReceiveResponse;
import com.deppon.foss.esb.crm.server.order.IsCustomerBlankOutRequest;
import com.deppon.foss.esb.crm.server.order.IsCustomerBlankOutResponse;
import com.deppon.foss.esb.crm.server.order.QueryMoneyRequest;
import com.deppon.foss.esb.crm.server.order.QueryMoneyResponse;
import com.deppon.foss.esb.crm.server.order.ResultDetal;
import com.deppon.foss.esb.crm.server.order.ServiceChargeStatusUpdateRequest;
import com.deppon.foss.esb.crm.server.order.ServiceChargeStatusUpdateResponse;
import com.deppon.foss.esb.crm.server.order.SyncOrderLockInfo;
import com.deppon.foss.esb.crm.server.order.SyncOrderLockInfoRequest;
import com.deppon.foss.esb.crm.server.order.SyncOrderLockInfoResponse;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderTaskHandleService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.QueryMoneyConditionDto;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.QueryMoneyDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.OrderLockEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.OrderLockResult;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * CRM webservice服务
 * 
 * @author 038590-foss-wanghui
 * @date 2012-12-18 下午10:08:46
 */
public class CustomerServiceImpl implements CustomerService {
	/**
	 * Logger日志
	 */
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(CustomerServiceImpl.class);
	/**
	 * 订单处理service
	 */
	private IOrderTaskHandleService orderTaskHandleService;
	/**
	 * 运单管理service
	 */
	private IWaybillManagerService waybillManagerService;

	// 注册BigDecimal转换器，否则Bigdecimal转换报错
	static {
		BigDecimalConverter bigDecimalConverter = new BigDecimalConverter(null);
		ConvertUtils.register(bigDecimalConverter, BigDecimal.class);
	}

	/**
	 * 查询发到货金额.
	 * 
	 * @param esbHeader
	 *            the esbHeader
	 * @param payload
	 *            mobile 手机号 name 姓名 telephone 电话
	 * @return the query money response
	 * @throws CommonException
	 *             the common exception
	 * @author 038590-foss-wanghui
	 * @date 2012-12-25 上午9:39:32
	 * @see com.deppon.foss.customerservice.CustomerService#queryMoney(javax.xml.ws.Holder,
	 *      com.deppon.esb.inteface.domain.crm.QueryMoneyRequest)
	 */
	@Override
	public QueryMoneyResponse queryMoney(Holder<ESBHeader> esbHeader,
			QueryMoneyRequest payload) throws CommonException {
		// esb规范，设置头
		esbHeader.value.setResponseId(payload.getName());
		esbHeader.value.setResultCode(1);
		// 手机号码或固话及姓名必须二选一
		if (StringUtils.isNotEmpty(payload.getMobile())
				|| (StringUtils.isNotEmpty(payload.getTelephone()) && StringUtils
						.isNotEmpty(payload.getName()))
				|| StringUtils.isNotEmpty(payload.getCustomerCode())) {
			// 拷贝对象
			QueryMoneyConditionDto queryMoneyConditionDto = new QueryMoneyConditionDto();
			try {
				BeanUtils.copyProperties(queryMoneyConditionDto, payload);
			} catch (IllegalAccessException e) {
				LOGGER.error(e.getMessage(), e);
			} catch (InvocationTargetException e) {
				LOGGER.error(e.getMessage(), e);
			}
			// 设置有效标志
			queryMoneyConditionDto.setActive(FossConstants.ACTIVE);
			// 获得对应收货客户的金额信息
			List<QueryMoneyDto> recieveMoneyList = waybillManagerService
					.queryRecieveMoney(queryMoneyConditionDto);
			// 获得对应发货客户的金额信息
			List<QueryMoneyDto> deliveryMoneyList = waybillManagerService
					.queryDeliveryMoney(queryMoneyConditionDto);
			// 构建response对象
			QueryMoneyResponse queryMoneyResponse = new QueryMoneyResponse();
			// 循环收货客户的金额信息集合设置到response的List
			for (QueryMoneyDto queryMoneyDto : recieveMoneyList) {
				if (queryMoneyDto != null) {
					AmountInfo amountInfo = new AmountInfo();
					try {
						// 拷贝对象
						BeanUtils.copyProperties(amountInfo, queryMoneyDto);
					} catch (IllegalAccessException e) {
						LOGGER.error(e.getMessage(), e);
					} catch (InvocationTargetException e) {
						LOGGER.error(e.getMessage(), e);
					}
					// 添加返回的收货金额对象
					queryMoneyResponse.getAmountInfo().add(amountInfo);
				}
			}
			// 循环发货客户的金额信息集合设置到response的List
			for (QueryMoneyDto queryMoneyDto : deliveryMoneyList) {
				if (queryMoneyDto != null) {
					AmountInfo amountInfo = new AmountInfo();
					try {
						// 拷贝对象
						BeanUtils.copyProperties(amountInfo, queryMoneyDto);
					} catch (IllegalAccessException e) {
						LOGGER.error(e.getMessage(), e);
					} catch (InvocationTargetException e) {
						LOGGER.error(e.getMessage(), e);
					}
					// 添加返回的发货金额对象
					queryMoneyResponse.getAmountInfo().add(amountInfo);
				}
			}
			return queryMoneyResponse;
		} else {
			throw new CommonException("参数不合法，手机号码或固话及姓名必须二选一");
		}
	}

	/**
	 * 运单绑定解绑接口.
	 * 
	 * @param esbHeader
	 *            the esbHeader
	 * @param payload
	 *            .oldWayBillNumber 老运单号 payload.newWayBillNumber 新运单号
	 *            payload.orderNo 订单号
	 * @return the bind order response
	 * @throws CommonException
	 *             the common exception
	 * @author foss-sunrui
	 * @date 2012-12-18 下午10:09:11
	 * @see com.deppon.foss.customerservice.CustomerService#bindOrder(javax.xml.ws.Holder,
	 *      com.deppon.esb.inteface.domain.crm.BindOrderRequest)
	 */
	@Override
	public BindOrderResponse bindOrder(Holder<ESBHeader> esbHeader,
			BindOrderRequest payload) throws CommonException {
		// esb规范，设置头
		esbHeader.value.setResponseId(payload != null ? payload.getOrderNo() : "");
		esbHeader.value.setResultCode(1);
		BindOrderResponse response = new BindOrderResponse();
		response.setResult(true);
		// 空则返回
		if (payload == null) {
			response.setResult(false);
		} else {
			// 绑定解绑service调用
			ResultDto res = waybillManagerService.reBindOrder(
					payload.getOldWayBillNumber(),
					payload.getNewWayBillNumber(), payload.getOrderNo());
			// 判断是否为0
			// 为0则失败
			if ("0".equals(res.getCode())) {
				response.setResult(false);
			}
		}
		return response;
	}

	/**
	 * 约车订单接口.
	 * 
	 * @param esbHeader
	 *            the esbHeader
	 * @param payload
	 *            custName 客户姓名 custMobile 客户手机号 custTel 客户电话 receiveProvince
	 *            接货省份 receiveCity 接货城市 receiveCounty 接货市 receiveDetailAddress
	 *            接货地址 consigneeAddress 收货地址 goodsName 货物名称 weight 重量 cubage 体积
	 *            packing 包装 pieces 件数 size 尺寸 desc 描述 transProperty 运输性质
	 *            deliverMode 提货方式 ladingStationId 提货网点 goodsType 货物类型
	 *            usingVehicleDeptNum 用车部门编码 usingVehicleDeptId 用车部门id
	 *            firstPickTime 最早接货时间 lastPickTime 最迟接货时间 vehDeptNum 部门编码
	 *            vehDeptId 部门id orderNumber 订单号 orderedTime 下单时间 orderType 订单来源
	 *            orderOwnDeptId 订单所属部门 creatorId 创建人id carType 车型
	 * @return the goods bill receive response
	 * @throws CommonException
	 *             the common exception
	 * @author 038590-foss-wanghui
	 * @date 2012-12-18 下午10:09:15
	 * @see com.deppon.foss.customerservice.CustomerService#sendGoodsBillReceive(javax.xml.ws.Holder,
	 *      com.deppon.esb.inteface.domain.crm.GoodsBillReceiveRequest)
	 */
	/*@Override
	public GoodsBillReceiveResponse sendGoodsBillReceive(
			Holder<ESBHeader> esbHeader, AsynGoodsBillReceiveRequest payload)
			throws CommonException {
		LOGGER.info("sendGoodsBillReceive start : " + ReflectionToStringBuilder.toString(payload));
		// esb规范，设置头
		esbHeader.value.setResponseId(payload.getOrderNumber());
		esbHeader.value.setResultCode(1);
		GoodsBillReceiveResponse response = new GoodsBillReceiveResponse();
		// 空则返回
		if (payload == null) {
			response.setResult(false);
			response.setReason("约车订单为空！");
		} else {
			try {
				// 调用添加接货订单接口
				orderTaskHandleService.addPickupOrder(payload);
				// 成功标志
				response.setResult(true);
			} catch (BusinessException e) {
				LOGGER.error(e.getMessage(), e);
				// 设置失败返回结果
				response.setResult(false);
				response.setReason(e.getErrorCode());
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				// 设置失败返回结果
				response.setResult(false);
				response.setReason(e.getMessage());
			}
		}
		LOGGER.info("sendGoodsBillReceive end : " + ReflectionToStringBuilder.toString(response));
		return response;
	}*/

	/**
	 * @param orderTaskHandleService
	 *            the orderTaskHandleService to set
	 */
	public void setOrderTaskHandleService(
			IOrderTaskHandleService orderTaskHandleService) {
		this.orderTaskHandleService = orderTaskHandleService;
	}

	/**
	 * @param waybillManagerService
	 *            the waybillManagerService to set
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	/**
	 * 非接送货需实现接口
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2012-12-25 下午4:33:02
	 * @see com.deppon.foss.customerservice.CustomerService#updateServiceChargeStatus(javax.xml.ws.Holder,
	 *      com.deppon.esb.inteface.domain.crm.ServiceChargeStatusUpdateRequest)
	 */
	@Override
	public ServiceChargeStatusUpdateResponse updateServiceChargeStatus(
			Holder<ESBHeader> esbHeader,
			ServiceChargeStatusUpdateRequest payload) throws CommonException {
		return null;
	}

	@Override
	public IsCustomerBlankOutResponse qureyIsCustomeBlankOut(
			IsCustomerBlankOutRequest request, Holder<ESBHeader> esbHeader)
			throws CommonException {
		return null;
	}

	@Override
	public ClaimsPayBillGenerateResponse claimsPayBillGenerate(
			Holder<ESBHeader> esbHeader, ClaimsPayBillGenerateRequest payload)
			throws CommonException {
		return null;
	}
	/**
 	 * 
 	 * 更新屏信息
 	 * 
 	 * @author panguoyang
 	 * @date 2013-08-02
 	 */
	@Override
	@WebResult(name = "SyncOrderLockInfoResponse", targetNamespace = "http://www.deppon.com/esb/inteface/domain/crm", partName = "response")
	@WebMethod(action = "http://www.deppon.com/foss/customerService/syncOrderLockInfo")
	public SyncOrderLockInfoResponse syncOrderLockInfo(
			@WebParam(partName = "request", name = "SyncOrderLockInfoRequest", targetNamespace = "http://www.deppon.com/esb/inteface/domain/crm") SyncOrderLockInfoRequest request,
			@WebParam(partName = "esbHeader", mode = Mode.INOUT, name = "esbHeader", targetNamespace = "http://www.deppon.com/esb/header", header = true) Holder<ESBHeader> esbHeader)
			throws CommonException {
		// esb规范，设置头
		esbHeader.value.setResponseId(UUID.randomUUID().toString());
		esbHeader.value.setResultCode(1);
		SyncOrderLockInfoResponse response = new SyncOrderLockInfoResponse();
		// 请求
		List<SyncOrderLockInfo> syncOrderLockInfos = request
				.getSyncOrderLockInfo();
		// 响应
		
		List<OrderLockEntity> records = new ArrayList<OrderLockEntity>();
		for (SyncOrderLockInfo syncOrderLockInfo : syncOrderLockInfos) {		
			OrderLockEntity orderLockEntity = new OrderLockEntity();
			orderLockEntity.setDeptCode(syncOrderLockInfo.getDeptCode());
			orderLockEntity.setOrderCountOverdue((long) syncOrderLockInfo
					.getLockCount());
			orderLockEntity.setOrderCountUnoverdue((long) syncOrderLockInfo
					.getPromptCount());
			records.add(orderLockEntity);
		}
		List<OrderLockResult> orderLockResults = waybillManagerService
				.crmsyncorderLock(records);
		for(OrderLockResult orderLockResult:orderLockResults){
			ResultDetal resultDetal = new ResultDetal();
			resultDetal.setDeptCode(orderLockResult.getDeptCode());
			resultDetal.setReason(orderLockResult.getFailCause());
			resultDetal.setResultCode(orderLockResult.getResultState());
			response.getResultInfo().add(resultDetal);
		}
		return response;
	}
	

	/**
	 * 取消约车订单
	 */
	@Override
	public CancelOrderResponse cancelOrder(Holder<ESBHeader> esbHeader, CancelOrderRequest payload) throws CommonException {
		LOGGER.info("取消约车订单输入参数：" + ReflectionToStringBuilder.toString(esbHeader));
		LOGGER.info("取消约车订单输入参数：" + ReflectionToStringBuilder.toString(payload));
		// esb规范，设置头
		esbHeader.value.setResponseId(payload.getOrderNumber());
		esbHeader.value.setResultCode(1);
		CancelOrderResponse response = new CancelOrderResponse();
		try {
			// 调用添加接货订单接口
			orderTaskHandleService.cancelOrder(payload, response);
		}catch (BusinessException e) {
			LOGGER.error(e.getMessage(),e);
			// 设置失败返回结果
			response.setResult(NumberConstants.ZERO);
			response.setReason(e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			// 设置失败返回结果
			response.setResult(NumberConstants.ZERO);
			response.setReason(e.getMessage());
		}
		LOGGER.info("取消约车订单返回参数：" + ReflectionToStringBuilder.toString(response));
		return response;
	}
	
	/**
	 * 根据客户编码和时间获取运单信息
	 * @创建时间 2014-4-12 下午6:09:16   
	 * @创建人： WangQianJin
	 */
	@Override
	public FossQueryAcctinfoResponse fossQueryAcctinfo(Holder<ESBHeader> esbHeader,
			FossQueryAcctinfoRequest payload) throws CommonException {
		LOGGER.info("根据客户编码和时间获取运单信息参数：" + ReflectionToStringBuilder.toString(esbHeader));
		LOGGER.info("根据客户编码和时间获取运单信息参数：" + ReflectionToStringBuilder.toString(payload));
		//验证请求信息是否为空
		validationData(payload);
		// esb规范，设置头
		esbHeader.value.setResponseId(payload.getDeliveryCustomerCode());
		esbHeader.value.setResultCode(1);
		esbHeader.value.setBackServiceCode("FOSS_ESB2FOSS_QUERY_ACCT_INFO");
		FossQueryAcctinfoResponse response=new FossQueryAcctinfoResponse();
		try {
			// 根据客户编码和时间获取运单信息
			response=waybillManagerService.fossQueryAcctinfo(payload);
		}catch (BusinessException e) {
			LOGGER.error(e.getMessage(),e);			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);			
		}
		LOGGER.info("根据客户编码和时间获取运单信息返回参数：" + ReflectionToStringBuilder.toString(response));
		return response;
	}
	
	/**
	 * 判断时间是否为空，如果为空判断异常
	 * @param argsMap
	 * @throws CommonException
	 */
	private void validationData(FossQueryAcctinfoRequest payload) throws CommonException{
		if(payload==null){
			throw new CommonException("请求信息不能为空！");
		}
		if(payload.getDeliveryCustomerCode()==null){
			throw new CommonException("客户编码不能为空！");
		}
		if(payload.getPageSize()==0){
			throw new CommonException("分页条数不能为空！");
		}
		if(payload.getCurrentPage()==0){
			throw new CommonException("页号不能为空！");
		}
		if(payload.getStartDate()==null){
			throw new CommonException("开始时间不能为空！");
		}
		if(payload.getEndDate()==null){
			throw new CommonException("结束时间不能为空！");
		}
		Date startDate=payload.getStartDate();
		Date endDate=payload.getEndDate();
		Long diffDate= DateUtils.getTimeDiff(startDate, endDate);
		if(diffDate>NumberConstants.NUMBER_10){
			throw new CommonException("查询时间范围超过10天，请调整时间！");
		}		
	}

	@Override
	public GoodsBillReceiveResponse sendGoodsBillReceive(
			Holder<ESBHeader> esbHeader, GoodsBillReceiveRequest payload)
			throws CommonException {
		LOGGER.info("改接口已废弃，已改为JMS");
		return null;
	}
}