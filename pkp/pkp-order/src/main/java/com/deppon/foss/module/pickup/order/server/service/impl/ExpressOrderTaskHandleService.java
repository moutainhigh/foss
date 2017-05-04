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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/server/service/impl/OrderTaskHandleService.java
 * 
 * FILE NAME        	: OrderTaskHandleService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.deppon.gis.inteface.domain.CollectAddressResponse;
import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.esb.crm.server.order.CancelOrderRequest;
import com.deppon.foss.esb.crm.server.order.CancelOrderResponse;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.server.web.message.IMessageBundle;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressVehiclesDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgAdministrativeInfoDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonExpressVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressVehicleDto;
import com.deppon.foss.module.base.common.api.server.service.IBusinessMonitorService;
import com.deppon.foss.module.base.common.api.server.service.IMessageService;
import com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService;
import com.deppon.foss.module.base.common.api.shared.define.BusinessMonitorIndicator;
import com.deppon.foss.module.base.common.api.shared.domain.InstationJobMsgEntity;
import com.deppon.foss.module.base.common.api.shared.dto.SmsParamDto;
import com.deppon.foss.module.base.common.api.shared.exception.MessageException;
import com.deppon.foss.module.base.common.api.shared.exception.SMSSendLogException;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderActionHistoryEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchVehicleRecordEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.ITruckResourceDao;
import com.deppon.foss.module.pickup.order.api.server.service.IAppOrderJMSService;
import com.deppon.foss.module.pickup.order.api.server.service.IExpressOrderTaskHandleService;
import com.deppon.foss.module.pickup.order.api.server.service.IGPSOrderTaskService;
import com.deppon.foss.module.pickup.order.api.server.service.IGisAddressCollectionService;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderAutoExceptionLogService;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderBusinessLockService;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderReportService;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderTaskHandleService;
import com.deppon.foss.module.pickup.order.api.server.service.IRegionCourierReportService;
import com.deppon.foss.module.pickup.order.api.server.service.IVehicleManageService;
import com.deppon.foss.module.pickup.order.api.shared.define.ActionMessageType;
import com.deppon.foss.module.pickup.order.api.shared.define.DispatchOrderStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.define.OrderMutexElementConstants;
import com.deppon.foss.module.pickup.order.api.shared.define.RejectReasonConstants;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderActionHistoryEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchVehicleRecordEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.OrderAutoExceptionLogEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.OrderReportEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.RegionCourierReportEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.AppOrderDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderVehicleDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.FailOrderDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderHandleDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderMutexElement;
import com.deppon.foss.module.pickup.order.api.shared.dto.TransferOrderDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.VehicleActualSituationDto;
import com.deppon.foss.module.pickup.order.api.shared.exception.DispatchException;
import com.deppon.foss.module.pickup.order.api.shared.exception.SmsException;
import com.deppon.foss.module.pickup.order.api.shared.util.SettlementReportNumber;
import com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.ExceptionProcessException;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.NotifyCustomerException;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderJMSService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderService;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmModifyInfoDto;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IOrderVehicleService;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.ProductCodeUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.gis.gisservice.CommonException;
import com.deppon.ows.inteface.domain.orderstate.OrderStateRequest;

/**
 * 订单处理的service
 * 
 * @author 038590-foss-wanghui
 * @date 2012-10-23 上午9:49:49
 */
public class ExpressOrderTaskHandleService implements IExpressOrderTaskHandleService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExpressOrderTaskHandleService.class);


	/**
	 * 客户相关
	 */
	private ICustomerDao customerDao;
	/**
	 * 调度订单DAO
	 */
	private IDispatchOrderEntityDao dispatchOrderEntityDao;
	/**
	 * 派车记录DAO
	 */
	private IDispatchVehicleRecordEntityDao dispatchVehicleRecordEntityDao;
	/**
	 * 订单操作历史DAO
	 */
	private IDispatchOrderActionHistoryEntityDao dispatchOrderActionHistoryEntityDao;
	/**
	 * 车辆管理Service
	 */
	private IVehicleManageService vehicleManageService;
	/**
	 * 综合的营业部服务Service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * CRM订单更新服务
	 */
	private ICrmOrderJMSService crmOrderJMSService;
	/**
	 * CRM订单查询服务
	 */
	private ICrmOrderService crmOrderService;
	/**
	 * 短信模板服务
	 */
	private ISMSTempleteService sMSTempleteService;
	/**
	 * 短信通知服务
	 */
	private INotifyCustomerService notifyCustomerService;
	/**
	 * 人员服务
	 */
	private IEmployeeService employeeService;
	/**
	 * 中转约车服务
	 */
	private IOrderVehicleService orderVehicleService;
	/**
	 * 消息服务
	 */
	private IMessageService messageService;
	/**
	 * 资源加载
	 */
	private IMessageBundle messageBundle;
	/**
	 * 应用监控
	 */
	private IBusinessMonitorService businessMonitorService;
	/**
	 * 车辆服务
	 */
	private ITruckResourceDao truckResourceDao;
	
	private ICommonExpressVehicleService commonExpressVehicleService;
	private IOrderTaskHandleService orderTaskHandleService;
	/**
	 * 每日报表服务
	 */
	private IOrderReportService orderReportService;
	private IGisAddressCollectionService gisAddressCollectionService;
	private IRegionCourierReportService regionCourierReportService;
	/**
	 * GPS服务
	 */
	private IGPSOrderTaskService GPSOrderTaskService;
	
	private IAppOrderJMSService appOrderJMSService;
	
	private IConfigurationParamsService configurationParamsService;
	
	private IOrderAutoExceptionLogService orderAutoExceptionLogService;
	
	private IExpressVehiclesDao expressVehiclesDao;
	
	private IOrgAdministrativeInfoDao orgAdministrativeInfoDao;
	
	private ICustomerService customerService;
	
	private IOrderBusinessLockService orderBusinessLockService;
	
	
	/**
	 * 待处理电子运单数据持久层
	 */
	/**
	 * 受理订单.
	 * 
	 * @param 
	 * 			id
	 * 				订单ID
	 * 			orderNo
	 * 				订单号
	 * 			orderType
	 * 				订单类型 -- 接货or转货
	 * 			rejectReason
	 * 				拒绝原因
	 * 			desc
	 * 				其他原因描述
	 * 			orderStatus
	 * 				订单待改状态
	 * 			originOrderStatus
	 * 				订单原始状态
	 * 			driverName
	 * 				司机姓名
	 * 			driverCode
	 * 				司机编码
	 * 			vehicleNo
	 * 				车牌号
	 * 			deviceNo
	 * 				设备号
	 * 			operateNotes
	 * 				订单操作备注
	 * 			operator
	 * 				操作人
	 * 			operatorCode
	 * 				操作人编码
	 * 			operateOrgName
	 * 				操作部门
	 * 			operateOrgCode
	 * 				操作部门编码
	 * 			operateTime
	 * 				操作时间
	 * 			isPda
	 * 				是否PDA
	 * 			isSms
	 * 				是否短信
	 * 			isCustomer
	 * 				是否发送客户
	 * 			pickupRegionCode
	 * 				接货小区Code
	 * 			pickupRegionName
	 * 				接货小区名称
	 * 			weight
	 * 				重量
	 * 			volume
	 * 				体积
	 * 			goodsQty
	 * 				件数
	 * 			driverMobile
	 * 				司机手机
	 * 			customerMobile
	 * 				客户手机
	 * 			pickupAddress
	 * 				接货地址
	 * 			salesDepartmentName
	 * 				营业部名称
	 * 			salesDepartmentCode
	 * 				营业部编码
	 * 			orderNotes
	 * 				备注
	 * 			pickupTime
	 * 				接货时间
	 * 			customerName
	 * 				客户姓名
	 * 			tel
	 * 				客户电话
	 * 			orderSendStatus
	 * 				订单发送状态
	 * @return the list
	 * @author 038590-foss-wanghui
	 * @date 2012-10-23 上午9:50:09
	 * @see com.deppon.foss.module.pickup.order.api.server.service.IOrderTaskHandleService#acceptOrder(java.util.List)
	 */
	@Transactional
	@Override
	public List<FailOrderDto> acceptOrder(List<OrderHandleDto> orderHandleDtoList) {
		LOGGER.info("acceptOrder start");
		// 订单集合为空，抛异常
		if (orderHandleDtoList == null || orderHandleDtoList.size() == 0) {
			throw new DispatchException(ActionMessageType.NONE_ORDER);
		}
		// 受理订单
		List<FailOrderDto> failureDtos = processOrderHandleList(orderHandleDtoList);
		LOGGER.info("acceptOrder end");
		return failureDtos;
	}

	/**
	 * 添加订单操作历史.
	 * 
	 * @param 
	 * 			id
	 * 				订单ID
	 * 			orderNo
	 * 				订单号
	 * 			orderType
	 * 				订单类型 -- 接货or转货
	 * 			rejectReason
	 * 				拒绝原因
	 * 			desc
	 * 				其他原因描述
	 * 			orderStatus
	 * 				订单待改状态
	 * 			originOrderStatus
	 * 				订单原始状态
	 * 			driverName
	 * 				司机姓名
	 * 			driverCode
	 * 				司机编码
	 * 			vehicleNo
	 * 				车牌号
	 * 			deviceNo
	 * 				设备号
	 * 			operateNotes
	 * 				订单操作备注
	 * 			operator
	 * 				操作人
	 * 			operatorCode
	 * 				操作人编码
	 * 			operateOrgName
	 * 				操作部门
	 * 			operateOrgCode
	 * 				操作部门编码
	 * 			operateTime
	 * 				操作时间
	 * 			isPda
	 * 				是否PDA
	 * 			isSms
	 * 				是否短信
	 * 			isCustomer
	 * 				是否发送客户
	 * 			pickupRegionCode
	 * 				接货小区Code
	 * 			pickupRegionName
	 * 				接货小区名称
	 * 			weight
	 * 				重量
	 * 			volume
	 * 				体积
	 * 			goodsQty
	 * 				件数
	 * 			driverMobile
	 * 				司机手机
	 * 			customerMobile
	 * 				客户手机
	 * 			pickupAddress
	 * 				接货地址
	 * 			salesDepartmentName
	 * 				营业部名称
	 * 			salesDepartmentCode
	 * 				营业部编码
	 * 			orderNotes
	 * 				备注
	 * 			pickupTime
	 * 				接货时间
	 * 			customerName
	 * 				客户姓名
	 * 			tel
	 * 				客户电话
	 * 			orderSendStatus
	 * 				订单发送状态
	 * @author 038590-foss-wanghui
	 * @date 2012-11-7 上午11:14:52
	 */
	@Override
	public void addDispatchOrderActionHistory(OrderHandleDto orderHandleDto) {
		DispatchOrderActionHistoryEntity dispatchOrderActionHistoryEntity = new DispatchOrderActionHistoryEntity();
		// 主键id
		dispatchOrderActionHistoryEntity.setId(UUIDUtils.getUUID());
		// 订单操作备注
		dispatchOrderActionHistoryEntity.setNotes(orderHandleDto.getOperateNotes());
		// 订单操作人
		dispatchOrderActionHistoryEntity.setOperator(orderHandleDto.getOperator());
		// 订单操作人code
		dispatchOrderActionHistoryEntity.setOperatorCode(orderHandleDto.getOperatorCode());
		// 调度订单id
		dispatchOrderActionHistoryEntity.settSrvDispatchOrderId(orderHandleDto.getId());
		// 调度订单状态
		dispatchOrderActionHistoryEntity.setOrderStatus(orderHandleDto.getOrderStatus());
		// 操作时间
		dispatchOrderActionHistoryEntity.setOperateTime(orderHandleDto.getOperateTime());
		// 插入订单操作历史
		dispatchOrderActionHistoryEntityDao.addDispatchOrderActionHistory(dispatchOrderActionHistoryEntity);
	}

	/**
	 * 初始化订单操作dto集合.
	 * 
	 * @param 
	 * 			id
	 * 				订单ID
	 * 			orderNo
	 * 				订单号
	 * 			orderType
	 * 				订单类型 -- 接货or转货
	 * 			rejectReason
	 * 				拒绝原因
	 * 			desc
	 * 				其他原因描述
	 * 			orderStatus
	 * 				订单待改状态
	 * 			originOrderStatus
	 * 				订单原始状态
	 * 			driverName
	 * 				司机姓名
	 * 			driverCode
	 * 				司机编码
	 * 			vehicleNo
	 * 				车牌号
	 * 			deviceNo
	 * 				设备号
	 * 			operateNotes
	 * 				订单操作备注
	 * 			operator
	 * 				操作人
	 * 			operatorCode
	 * 				操作人编码
	 * 			operateOrgName
	 * 				操作部门
	 * 			operateOrgCode
	 * 				操作部门编码
	 * 			operateTime
	 * 				操作时间
	 * 			isPda
	 * 				是否PDA
	 * 			isSms
	 * 				是否短信
	 * 			isCustomer
	 * 				是否发送客户
	 * 			pickupRegionCode
	 * 				接货小区Code
	 * 			pickupRegionName
	 * 				接货小区名称
	 * 			weight
	 * 				重量
	 * 			volume
	 * 				体积
	 * 			goodsQty
	 * 				件数
	 * 			driverMobile
	 * 				司机手机
	 * 			customerMobile
	 * 				客户手机
	 * 			pickupAddress
	 * 				接货地址
	 * 			salesDepartmentName
	 * 				营业部名称
	 * 			salesDepartmentCode
	 * 				营业部编码
	 * 			orderNotes
	 * 				备注
	 * 			pickupTime
	 * 				接货时间
	 * 			customerName
	 * 				客户姓名
	 * 			tel
	 * 				客户电话
	 * 			orderSendStatus
	 * 				订单发送状态
	 * @return the list
	 * @author 038590-foss-wanghui
	 * @date 2012-11-9 上午9:01:33
	 * @data 2014年7月10日 下午4:30:16
	 * 添加状态码的判断：业务需求：分成三种状态 1.发件人短信批量发送 2.发件人短信停发 3.收件人短信停发  
	 * 0表示的是未选中   1表示的是选中
	 * 发件人短信批量发送	当CRM勾选发件人短信批量发送时，停发快递订单调度受理短信、快递签收收货人短信、签收单返单短信，次日向客户发送批量打包短信
	 * 发件人短信停发	当CRM勾选发件人短信停发时，停发快递订单调度受理短信、快递签收发件人短信。
     * 收件人短信停发	当CRM勾选收件人短信停发时，停止发送快递派送收货人短信、快递开单收货人短信、快递签收收货人短信
	 * 快递订单调度受理短信
	 */
	private List<FailOrderDto> processOrderHandleList(List<OrderHandleDto> orderHandleDtos) {
		// 失败订单集合
		List<FailOrderDto> failOrderDtos = new ArrayList<FailOrderDto>();
		//获取订单业务锁自动释放时间
		String orderLockTtl = configurationParamsService.queryConfValueByCode(DispatchOrderStatusConstants.ORDER_LOCK_TTL);
		for (OrderHandleDto orderHandleDto : orderHandleDtos) {
			OrderMutexElement mutexElement = null;
			if (StringUtils.isBlank(orderLockTtl)) {
				mutexElement = new OrderMutexElement(orderHandleDto.getOrderNo(), "订单更新_快递手动调度", 
						OrderMutexElementConstants.DISPATCHORDERNO_LOCK);
				LOGGER.info("人工受理快递订单"+orderHandleDto.getOrderNo()+"时订单业务锁自动释放时间采用默认值");
			}else {
				mutexElement = new OrderMutexElement(orderHandleDto.getOrderNo(), "订单更新_快递手动调度", 
						OrderMutexElementConstants.DISPATCHORDERNO_LOCK,Integer.valueOf(orderLockTtl));
			}
			
			//互斥锁定
	     	boolean isLocked = orderBusinessLockService.lock(mutexElement, DispatchOrderStatusConstants.ZERO);	
	     	if(!isLocked){
	     		FailOrderDto failOrder = new FailOrderDto(orderHandleDto.getOrderNo(), 
						DispatchOrderStatusConstants.FAILURE_LOCK, 
						messageBundle.getMessage(UserContext.getUserLocale(),
								ActionMessageType.ORDER_LOCK, new Object[0]));
	     		failOrderDtos.add(failOrder);
	     		LOGGER.info("人工受理快递订单"+orderHandleDto.getOrderNo()+"时订单已锁定");
				continue;
			}
			try {
				//判断接货订单在CRM中是否存在
			/*if (DispatchOrderStatusConstants.TYPE_PICKUP_ORDER.equals(orderHandleDto.getOrderType())
						&& crmOrderService.searchOrder(orderHandleDto.getOrderNo()) == false) {
					FailOrderDto failOrder = new FailOrderDto(orderHandleDto.getOrderNo(), 
							DispatchOrderStatusConstants.FAILURE_NOT_CRM, 
							messageBundle.getMessage(UserContext.getUserLocale(),
									ActionMessageType.NOT_CRM_FAILURE, new Object[0]));
					failOrderDtos.add(failOrder);
					continue;
				}*/
				
				// 设置状态为已派车
				orderHandleDto.setOrderStatus(DispatchOrderStatusConstants.STATUS_DISPATCH_VEHICLE);
				// 发送中
				orderHandleDto.setOrderSendStatus(DispatchOrderStatusConstants.ORDER_SENDING);
				//14.7.30 gcl AUTO-212 设置订单受理方式为人工
				orderHandleDto.setAcceptStatus(DispatchOrderStatusConstants.ORDER_ACCEPT_STATUS_HANDLE);
				//14.8.22 gcl DEFECT-3958
				if(StringUtils.isEmpty(orderHandleDto.getDriverMobile())){
					EmployeeEntity emp = employeeService.queryEmployeeByEmpCodeNoCache(orderHandleDto.getDriverCode());
					if(emp!=null){
						orderHandleDto.setDriverMobile(emp.getMobilePhone());
					}
				}
				// 初始化订单的操作人、操作部门、状态
				initOrderHandle(orderHandleDto);
				LOGGER.info("受理订单: " + orderHandleDto.toString());
				int count = 0;
				
				//14.8.11 gcl AUTO-212 人工受理时 不更新预处理小区 只更新实际接货小区
				OrderHandleDto orderhandledto=new OrderHandleDto();
				BeanUtils.copyProperties(orderhandledto, orderHandleDto);
				orderhandledto.setSmallZoneCodeSuggested("");
				orderhandledto.setSmallZoneNameSuggested("");
				count = dispatchOrderEntityDao.updateByExpressOrderHandle(orderhandledto);
				//测试 count=1
				if (count == 1) {
					// 添加订单操作历史
					addDispatchOrderActionHistory(orderHandleDto);
					// 添加派车记录
					addDispatchVehicleRecord(orderHandleDto);
					// 获得订单对应的运输产品类型
					DispatchOrderConditionDto dto = new DispatchOrderConditionDto();
					dto.setOrderNo(orderHandleDto.getOrderNo());	
					DispatchOrderEntity dispatchOrder = dispatchOrderEntityDao.queryOrdersByOrderNo(dto);
					if(StringUtils.isNotEmpty(dispatchOrder.getProductCode())){
						orderHandleDto.setProductCode(dispatchOrder.getProductCode());
					}
					
					 // 发送短信
					if(FossConstants.ACTIVE.equals(
						this.configurationParamsService.queryConfValueByCode(
								ConfigurationParamsConstants.STL_ORDER_MESSAGE_SWITCH))){
						try {
							LOGGER.info("短信发送开始！订单号"+orderHandleDto.getOrderNo());
							//设置客户编码和客户手机号，判断是否违禁品黑名单
							DispatchOrderEntity entity = dispatchOrderEntityDao.queryAllInfoByOrderNo(orderHandleDto.getOrderNo());
							if(entity!=null) {
								orderHandleDto.setDeliveryCustomerCode(entity.getDeliveryCustomerCode());
								orderHandleDto.setDeliveryCustomerMobile(entity.getMobile());
							}
							sendSmsByHandle(failOrderDtos, orderHandleDto);
							LOGGER.info("短信发送结束！订单号"+orderHandleDto.getOrderNo());
						} catch (Exception e) {
							String exceptionType = "SMS_SEND_ERROR";
							String reason = "发送短信异常";	
							addExceptionLog(orderHandleDto, exceptionType, reason);
							backOrderHandle(orderHandleDto);
							throw new DispatchException("发送短信异常，无法继续操作！");
						}
					}else {
						String exceptionType = "SMS_SEND_ERROR";
						String reason = "短信功能关闭，短信发送失败！";	
						addExceptionLog(orderHandleDto, exceptionType, reason);
					}

					// 更新外部系统状态
					try{
						LOGGER.info("更新外部系统开始！订单号"+orderHandleDto.getOrderNo());
						updateExternalSystem(orderHandleDto, null);
						LOGGER.info("更新外部系统结束！订单号"+orderHandleDto.getOrderNo());
					}catch(Exception e){
						String exceptionType = "UPDATE_CRM_ERROR";
						String reason = "更新外部系统报错";	
						addExceptionLog(orderHandleDto,exceptionType,reason);	
					}
					
					// 同步约车信息给GPS
					try{
						LOGGER.info("同步约车信息给GPS开始！订单号"+orderHandleDto.getOrderNo());
						sendToGPS(orderHandleDto,1);
						LOGGER.info("同步约车信息给GPS结束！订单号"+orderHandleDto.getOrderNo());
					}catch(Exception e){
						String exceptionType = "UPDATE_GPS_ERROR";
						String reason = "同步约车信息报错";	
						addExceptionLog(orderHandleDto,exceptionType,reason);	
					}
					
					//推送订单信息给APP
					try{
						LOGGER.info("推送订单信息给APP开始！订单号"+orderHandleDto.getOrderNo());
						sendEmpCodeToApp(orderHandleDto);
						LOGGER.info("推送订单信息给APP结束！订单号"+orderHandleDto.getOrderNo());
					}catch(Exception e){
						String exceptionType = "UPDATE_APP_ERROR";
						String reason = "推送订单信息给APP报错";	
						addExceptionLog(orderHandleDto,exceptionType,reason);	
					}
					
					//推送订单信息给微信
					try{
						LOGGER.info("推送订单信息给微信开始！订单号"+orderHandleDto.getOrderNo());
						sendOrderStateToApp(orderHandleDto);
						LOGGER.info("推送订单信息给微信结束！订单号"+orderHandleDto.getOrderNo());
					}catch(Exception e){
						String exceptionType = "UPDATE_WEIXIN_ERROR";
						String reason = "推送订单信息给微信报错";	
						LOGGER.info(orderHandleDto.getOrderNo()+":"+e.getMessage());
						addExceptionLog(orderHandleDto,exceptionType,reason);	
					}
					
					try {
						LOGGER.info("电子运单添加数据开始！订单号"+orderHandleDto.getOrderNo());
						orderTaskHandleService.addEWaybilllOrder(orderHandleDto.getOrderNo());
						LOGGER.info("电子运单添加/更新结束！订单号"+orderHandleDto.getOrderNo());
					} catch (Exception e) {
						LOGGER.error(e.getMessage(), e);
					}
					// 新增每日报表记录
					addOrderReport(orderHandleDto);
					addRegionCourierReport(orderHandleDto);//14.7.31
					try {
						// 发送在线通知给营业部
						addNotice(orderHandleDto.getSalesDepartmentCode(), getNotice(orderHandleDto));
					} catch (BusinessException e) {
						LOGGER.error(e.getMessage(), e);
						FailOrderDto failOrder = new FailOrderDto(orderHandleDto.getOrderNo(), DispatchOrderStatusConstants.FAILURE_NOTICE, messageBundle.getMessage(UserContext.getUserLocale(),
								ActionMessageType.NOTICE_FAILURE, new Object[0]));
						failOrderDtos.add(failOrder);
					}
					
					//14.7.23 gcl AUTO-195
					if(orderHandleDto.getDelGPS()!=null&&"YES".equals(orderHandleDto.getDelGPS())&&dispatchOrder!=null){
						try {
							CollectAddressResponse  response =gisAddressCollectionService.delGisAddressInfo(dispatchOrder);
//						System.out.println(response.isIsSuccess()+"--------------00000000000000000000");
						} catch (CommonException e) {
							LOGGER.error(e.getMessage(), e);
						}
					}
					// 应用监控--订单分派票数
					if (DispatchOrderStatusConstants.STATUS_AGAIN_PICKUP.equals(orderHandleDto.getOriginOrderStatus())) {
						// 应用监控--订单改派票数
						businessMonitorService.counter(BusinessMonitorIndicator.ORDER_REASSIGN_COUNT, FossUserContext.getCurrentInfo());
					}
					// 应用监控--订单分派票数
					businessMonitorService.counter(BusinessMonitorIndicator.ORDER_ASSIGN_COUNT, FossUserContext.getCurrentInfo());
					// 应用监控--订单处理票数
					businessMonitorService.counter(BusinessMonitorIndicator.ORDER_PROCESSED_COUNT, FossUserContext.getCurrentInfo());
				} else {
					// 更新调度订单失败
					// 添加失败详细信息
					FailOrderDto failOrder = new FailOrderDto(orderHandleDto.getOrderNo(), DispatchOrderStatusConstants.FAILURE_UPDATE, messageBundle.getMessage(UserContext.getUserLocale(),
							ActionMessageType.ACCEPT_FAILURE, new Object[0]));
					failOrderDtos.add(failOrder);
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				FailOrderDto failOrder = new FailOrderDto(orderHandleDto.getOrderNo(), DispatchOrderStatusConstants.FAILURE_NOTICE, messageBundle.getMessage(UserContext.getUserLocale(),
						ActionMessageType.ACCEPT_FAILURE, new Object[0]));
				failOrderDtos.add(failOrder);
			}finally{
				orderBusinessLockService.unlock(mutexElement);
				LOGGER.info("人工受理订单"+orderHandleDto.getOrderNo()+"结束");
			}
		}
		LOGGER.debug("快递人工处理完成");
		return failOrderDtos;
	}

	/**
	 * 
	 * 发送短信
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-7-12 上午11:13:15
	 */
	private boolean sendSmsByHandle(List<FailOrderDto> failOrderDtos, OrderHandleDto orderHandleDto) {
		//处理成功标准为
		boolean flag = true;
		try {
			// 发送短信给司机
			if (FossConstants.YES.equals(orderHandleDto.getIsSms())) {
				   // 小件
				String smsCode = getSmsCodeForCourier(orderHandleDto.getDeliveryCustomerCode());
				sendSms(smsCode, orderHandleDto, true,NotifyCustomerConstants.BS_TYPE_PKP_ORDER);
			}
		} catch (NotifyCustomerException e) {
			LOGGER.error(e.getMessage(), e);
			// 添加失败信息
			FailOrderDto failOrder = new FailOrderDto(orderHandleDto.getOrderNo(), DispatchOrderStatusConstants.FAILURE_NOTICE, messageBundle.getMessage(UserContext.getUserLocale(),
					ActionMessageType.DRIVER_SMS_SENDFAILURE, new Object[0]));
			failOrderDtos.add(failOrder);
			flag = false;
			throw new DispatchException("发送短信异常");
		} catch (SMSSendLogException e) {
			LOGGER.error(e.getMessage(), e);
			// 添加失败信息
			FailOrderDto failOrder = new FailOrderDto(orderHandleDto.getOrderNo(), DispatchOrderStatusConstants.FAILURE_NOTICE, messageBundle.getMessage(UserContext.getUserLocale(),
					ActionMessageType.DRIVER_SMS_SENDFAILURE, new Object[0]));
			failOrderDtos.add(failOrder);
			flag = false;
			throw new DispatchException("发送短信异常");
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			// 添加失败信息
			FailOrderDto failOrder = new FailOrderDto(orderHandleDto.getOrderNo(), DispatchOrderStatusConstants.FAILURE_NOTICE, messageBundle.getMessage(UserContext.getUserLocale(),
					ActionMessageType.SMS_NOTFOUND, new Object[0]));
			failOrderDtos.add(failOrder);
			flag = false;
			throw new DispatchException("发送短信异常");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 添加失败信息
			FailOrderDto failOrder = new FailOrderDto(orderHandleDto.getOrderNo(), DispatchOrderStatusConstants.FAILURE_NOTICE, messageBundle.getMessage(UserContext.getUserLocale(),
					ActionMessageType.DRIVER_SMS_SENDFAILURE, new Object[0]));
			failOrderDtos.add(failOrder);
			flag = false;
			throw new DispatchException("发送短信异常");
		}
		//发送短信给客户时间节点改到PDA上点击受理按钮时发送
		/*try {
			// 发送短信给客户
			if (FossConstants.YES.equals(orderHandleDto.getIsCustomer())) {
				OwnTruckConditionDto conditionDto = new OwnTruckConditionDto();
				// 车牌号
				conditionDto.setVehicleNo(orderHandleDto.getVehicleNo());
				// 有效
				conditionDto.setActive(FossConstants.ACTIVE);
				// 获取车型
				VehicleInfoDto vehicleInfoDto = truckResourceDao.queryVehicleTypeByVehicleNo(conditionDto);
				// 设置车型
				orderHandleDto.setVehicleLengthName(vehicleInfoDto.getVehicleLengthName());
				// 小件
//					sendSms(DispatchOrderStatusConstants.TEMPLATE_EXPRESS_ORDER_TO_CUSTOMER_SMS, orderHandleDto, false);
				// 小件，快递发短信 MANA-581
				//是否发送快递信息的标记
				boolean isSendpackageFlag=false;
				String customerMobile = orderHandleDto.getCustomerMobile();
				if(StringUtils.isNotEmpty(customerMobile)){
					String code=customerDao.queryCustCodeByCustMobile(customerMobile);
					if(StringUtils.isNotEmpty(code)){
						CustomerEntity customerEntity = customerDao.queryCustStateByCode(code);
						if(customerEntity!=null){
							String shipperSms = customerEntity.getShipperSms();
							if(StringUtils.isNotEmpty(shipperSms)&&(shipperSms.equals(OrderConstant.STOP_MESSAGE_FOR_DELIVER)||shipperSms.equals(OrderConstant.BATCH_MESSAGE_FOR_DELIVER))){
								isSendpackageFlag=false;//停发短信
							}else{
								isSendpackageFlag=true;//发送短信
							}
						}else{
							isSendpackageFlag=true;//发送短信
						}
					}else{
						isSendpackageFlag=true;//发送短信
					}
				}else{
					isSendpackageFlag=true;//发送短信
				}
				*//**###########更改  如果是快递的话 需要根据状态判断是否需要发送  临单不需要判断 都发送  begin############**//*  
				if(isSendpackageFlag){
					// 发送短信
					sendSms(DispatchOrderStatusConstants.EXPRESS_ORDER_ACCEPT_SMS, orderHandleDto, false,DispatchOrderStatusConstants.EXPRESS_ORDER_MODULE);
				}
			}
		} catch (NotifyCustomerException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (SMSSendLogException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}*/
		return flag;
	}
	
	/**
	 * 约车信息受理时同步快递员信息给APP
	 * @param orderHandleDto
	 */

	/**
	 * 
	 * 约车信息受理时同步快递员信息给APP
	 * @author 043258-foss-zhaobin
	 * @date 2014-10-29 下午4:54:49
	 */
	private void sendEmpCodeToApp(OrderHandleDto orderHandleDto){
		AppOrderDto appOrderDto = new AppOrderDto();
		appOrderDto.setOrderNo(orderHandleDto.getOrderNo());
		appOrderDto.setDriverCode(orderHandleDto.getDriverCode());
		appOrderDto.setPosterMobilePhone(orderHandleDto.getCustomerMobile());
		appOrderDto.setProductCode(orderHandleDto.getProductCode());
		appOrderDto.setStatus(orderHandleDto.getOrderStatus());
		appOrderJMSService.sendOrderState(appOrderDto);
	}
	
	/**
	 * DMANA-8883 微信订单状态推送需求 将FOSS中的“已派车”状态推送至APP
	 */
	private void sendOrderStateToApp(OrderHandleDto orderHandleDto) {
		OrderStateRequest request = new OrderStateRequest();
		// 订单状态
		request.setOrderSource(DispatchOrderStatusConstants.STATUS_DISPATCH_VEHICLE);
		// 渠道来源
		request.setChannel(orderHandleDto.getOrderSource());
		// 订单号
		request.setOrderNo(orderHandleDto.getOrderNo());
		// 运输性质
		request.setTransportProperties(orderHandleDto.getProductCode());
		// 发货人
		request.setSender(orderHandleDto.getCustomerName());
		// 发货人手机
		request.setSenderTel(orderHandleDto.getCustomerMobile());
		// 出发城市
		request.setLeaveCity(orderHandleDto.getPickupCity());
		// 快递员工号
		request.setCourierNumber(orderHandleDto.getDriverCode());
		// 快递员姓名
		request.setCourierName(orderHandleDto.getDriverName());
		// 快递员电话
		request.setCourierTel(orderHandleDto.getDriverMobile());
		//接货时间
		String owsPickupTime = orderHandleDto.getOwsPickupTime();
		if(owsPickupTime.startsWith("1970")) {
			owsPickupTime = owsPickupTime.substring(SettlementReportNumber.SEVENTEEN);
		}
		request.setOrderTime(owsPickupTime);
		
//		LOGGER.info("========================发送至APP的信息begin==========================" );
//		LOGGER.info("订单状态="+DispatchOrderStatusConstants.STATUS_DISPATCH_VEHICLE+" 渠道来源="+ orderHandleDto.getOrderSource()+" 订单号="+orderHandleDto.getOrderNo()+
//				"  运输性质="+orderHandleDto.getProductCode()+" 发货人="+orderHandleDto.getCustomerName()+" 发货人手机="+orderHandleDto.getCustomerMobile()+" 出发城市="+orderHandleDto.getPickupCity()+
//				" 接单时间="+request.getOrderTime()+" 快递员工号="+orderHandleDto.getDriverCode()+" 快递员姓名="+orderHandleDto.getDriverName()+" 快递员电话="+orderHandleDto.getDriverMobile());
//		LOGGER.info("========================发送至APP的信息end==========================" );
		
		appOrderJMSService.sendOrderStateToApp(request);
	}
	
	/**
	 * 
	 * 获取在线通知内容
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-4-10 下午4:39:32
	 */
	private String getNotice(OrderHandleDto orderHandleDto){
		SmsParamDto smsParam = new SmsParamDto();
		// code
		smsParam.setSmsCode("PKP_ORDER_NOTICE");
		// 参数初始化
		smsParam.setMap(initParams(orderHandleDto));
		// 获取通知内容
		return sMSTempleteService.querySmsByParam(smsParam);
	}
	
	/**
	 * 初始化在线通知参数.
	 * 
	 * @param 
	 * 			id
	 * 				订单ID
	 * 			orderNo
	 * 				订单号
	 * 			orderType
	 * 				订单类型 -- 接货or转货
	 * 			rejectReason
	 * 				拒绝原因
	 * 			desc
	 * 				其他原因描述
	 * 			orderStatus
	 * 				订单待改状态
	 * 			originOrderStatus
	 * 				订单原始状态
	 * 			driverName
	 * 				司机姓名
	 * 			driverCode
	 * 				司机编码
	 * 			vehicleNo
	 * 				车牌号
	 * 			deviceNo
	 * 				设备号
	 * 			operateNotes
	 * 				订单操作备注
	 * 			operator
	 * 				操作人
	 * 			operatorCode
	 * 				操作人编码
	 * 			operateOrgName
	 * 				操作部门
	 * 			operateOrgCode
	 * 				操作部门编码
	 * 			operateTime
	 * 				操作时间
	 * 			isPda
	 * 				是否PDA
	 * 			isSms
	 * 				是否短信
	 * 			isCustomer
	 * 				是否发送客户
	 * 			pickupRegionCode
	 * 				接货小区Code
	 * 			pickupRegionName
	 * 				接货小区名称
	 * 			weight
	 * 				重量
	 * 			volume
	 * 				体积
	 * 			goodsQty
	 * 				件数
	 * 			driverMobile
	 * 				司机手机
	 * 			customerMobile
	 * 				客户手机
	 * 			pickupAddress
	 * 				接货地址
	 * 			salesDepartmentName
	 * 				营业部名称
	 * 			salesDepartmentCode
	 * 				营业部编码
	 * 			orderNotes
	 * 				备注
	 * 			pickupTime
	 * 				接货时间
	 * 			customerName
	 * 				客户姓名
	 * 			tel
	 * 				客户电话
	 * 			orderSendStatus
	 * 				订单发送状态
	 * @return the map
	 * @author 038590-foss-wanghui
	 * @date 2012-12-21 下午6:11:21
	 */
	private Map<String, String> initParams(OrderHandleDto orderHandleDto) {
		Map<String, String> map = new HashMap<String, String>();
		// 营业部名称
		map.put("salesDepartment", orderHandleDto.getSalesDepartmentName());
		// 订单号
		map.put("orderNo", orderHandleDto.getOrderNo());
		// 司机姓名
		map.put("driverName", orderHandleDto.getDriverName());
		// 车牌号
		map.put("vehicleNo", orderHandleDto.getVehicleNo());
		// 操作时间
		map.put("operateTime", DateUtils.convert(orderHandleDto.getOperateTime(), null));
		// 操作人
		map.put("operator", orderHandleDto.getOperator());
		// 受理部门
		map.put("dept", orderHandleDto.getOperateOrgName());
		return map;
	}

	/**
	 * 判断发货人是否为违禁品黑名单，选择不同的短信模板 
	 * @author 219396 FOSS-CHENGDAOLIN  15-07-2
	 */
	private String getSmsCodeForCourier(String cusCode) {
		if(StringUtils.isNotBlank(cusCode)) {
			//按发货人客户编码查询
			CustomerEntity customerEntity = new CustomerEntity();
			customerEntity.setActive(FossConstants.YES);
			customerEntity.setCusCode(cusCode);
			CustomerEntity ce = customerService.queryCustInfoByCustomerEntity(customerEntity);
			if(ce!=null&&FossConstants.CUSTOMER_CONTRABAND.equals(ce.getBlackListCategory()))
				return DispatchOrderStatusConstants.TEMPLATE_PICKUP_ORDER_TO_COURIER_SMS_CONTRABAND;
		}
		return DispatchOrderStatusConstants.TEMPLATE_PICKUP_ORDER_TO_COURIER_SMS;
	}
	
	/**
	 * 发送在线通知.
	 * 
	 * @param receiveOrgCode the receiveOrgCode
	 * @param noticeContent the noticeContent
	 * @throws ExceptionProcessException the exception process exception
	 * @author 038590-foss-wanghui
	 * @date 2012-12-21 下午5:19:55
	 */
	private void addNotice(String receiveOrgCode, String noticeContent) throws ExceptionProcessException {
		try {
			// 如果接受组织code、通知内容不为空
			if (StringUtils.isNotEmpty(receiveOrgCode) && StringUtils.isNotEmpty(noticeContent)) {
				InstationJobMsgEntity entity = new InstationJobMsgEntity();
				// UUID
				entity.setId(UUIDUtils.getUUID());
				// 发送方编码
				entity.setSenderCode(FossUserContextHelper.getUserCode());
				// 发送人名称
				entity.setSenderName(FossUserContextHelper.getUserName());
				// 发送方组织编码
				entity.setSenderOrgCode(FossUserContextHelper.getOrgCode());
				// 发送方组织名称
				entity.setSenderOrgName(FossUserContextHelper.getOrgName());
				// 接收方组织编码
				entity.setReceiveOrgCode(receiveOrgCode);
				// 接收方类型
				entity.setReceiveType(MessageConstants.MSG__RECEIVE_TYPE__ORG);
				// 消息内容
				entity.setContext(noticeContent);
				// 站内消息类型
				entity.setMsgType(DictionaryValueConstants.MSG_TYPE__NORMAL);
				// 发送时间
				entity.setPostDate(new Date());
				// 消息状态
				entity.setStatus(MessageConstants.MSG__STATUS__PROCESSING);
				messageService.createBatchInstationMsg(entity);
			}
		} catch (MessageException e) {
			LOGGER.error(e.getMessage(), e);
			throw new DispatchException(e.getErrorCode(), e);
		}
	}

	/**
	 * 更新外部系统状态.
	 * 
	 * @param 
	 * 			id
	 * 				订单ID
	 * 			orderNo
	 * 				订单号
	 * 			orderType
	 * 				订单类型 -- 接货or转货
	 * 			rejectReason
	 * 				拒绝原因
	 * 			desc
	 * 				其他原因描述
	 * 			orderStatus
	 * 				订单待改状态
	 * 			originOrderStatus
	 * 				订单原始状态
	 * 			driverName
	 * 				司机姓名
	 * 			driverCode
	 * 				司机编码
	 * 			vehicleNo
	 * 				车牌号
	 * 			deviceNo
	 * 				设备号
	 * 			operateNotes
	 * 				订单操作备注
	 * 			operator
	 * 				操作人
	 * 			operatorCode
	 * 				操作人编码
	 * 			operateOrgName
	 * 				操作部门
	 * 			operateOrgCode
	 * 				操作部门编码
	 * 			operateTime
	 * 				操作时间
	 * 			isPda
	 * 				是否PDA
	 * 			isSms
	 * 				是否短信
	 * 			isCustomer
	 * 				是否发送客户
	 * 			pickupRegionCode
	 * 				接货小区Code
	 * 			pickupRegionName
	 * 				接货小区名称
	 * 			weight
	 * 				重量
	 * 			volume
	 * 				体积
	 * 			goodsQty
	 * 				件数
	 * 			driverMobile
	 * 				司机手机
	 * 			customerMobile
	 * 				客户手机
	 * 			pickupAddress
	 * 				接货地址
	 * 			salesDepartmentName
	 * 				营业部名称
	 * 			salesDepartmentCode
	 * 				营业部编码
	 * 			orderNotes
	 * 				备注
	 * 			pickupTime
	 * 				接货时间
	 * 			customerName
	 * 				客户姓名
	 * 			tel
	 * 				客户电话
	 * 			orderSendStatus
	 * 				订单发送状态
	 * @param reason 原因
	 * @author 038590-foss-wanghui
	 * @date 2012-12-6 上午10:21:50
	 */
	@Override
	public void updateExternalSystem(OrderHandleDto orderHandleDto, String reason) {
		LOGGER.info("updateExternalSystem start" + orderHandleDto + "-" + orderHandleDto.getOrderStatus() + "-" + reason);
		if(StringUtils.isBlank(orderHandleDto.getOrderNo()) || StringUtils.isBlank(orderHandleDto.getOrderType()) || StringUtils.isBlank(orderHandleDto.getOrderStatus()))
		{
			throw new DispatchException("订单信息缺失，无法继续操作！");
		}
		if (DispatchOrderStatusConstants.TYPE_PICKUP_ORDER.equals(orderHandleDto.getOrderType())) {
			// 更新CRM订单状态
			updateCrmOrder(orderHandleDto);
		} else {
			// 调用中转接口修改转货订单的接口
			orderVehicleService.updateOrderVehicleApplyStatusByOrderNo(orderHandleDto.getOrderNo(), orderHandleDto.getOrderStatus(), reason);
		}
	}

	/**
	 * 调用CRM修改订单接口更新订单状态.
	 * 
	 * @param 
	 * 			id
	 * 				订单ID
	 * 			orderNo
	 * 				订单号
	 * 			orderType
	 * 				订单类型 -- 接货or转货
	 * 			rejectReason
	 * 				拒绝原因
	 * 			desc
	 * 				其他原因描述
	 * 			orderStatus
	 * 				订单待改状态
	 * 			originOrderStatus
	 * 				订单原始状态
	 * 			driverName
	 * 				司机姓名
	 * 			driverCode
	 * 				司机编码
	 * 			vehicleNo
	 * 				车牌号
	 * 			deviceNo
	 * 				设备号
	 * 			operateNotes
	 * 				订单操作备注
	 * 			operator
	 * 				操作人
	 * 			operatorCode
	 * 				操作人编码
	 * 			operateOrgName
	 * 				操作部门
	 * 			operateOrgCode
	 * 				操作部门编码
	 * 			operateTime
	 * 				操作时间
	 * 			isPda
	 * 				是否PDA
	 * 			isSms
	 * 				是否短信
	 * 			isCustomer
	 * 				是否发送客户
	 * 			pickupRegionCode
	 * 				接货小区Code
	 * 			pickupRegionName
	 * 				接货小区名称
	 * 			weight
	 * 				重量
	 * 			volume
	 * 				体积
	 * 			goodsQty
	 * 				件数
	 * 			driverMobile
	 * 				司机手机
	 * 			customerMobile
	 * 				客户手机
	 * 			pickupAddress
	 * 				接货地址
	 * 			salesDepartmentName
	 * 				营业部名称
	 * 			salesDepartmentCode
	 * 				营业部编码
	 * 			orderNotes
	 * 				备注
	 * 			pickupTime
	 * 				接货时间
	 * 			customerName
	 * 				客户姓名
	 * 			tel
	 * 				客户电话
	 * 			orderSendStatus
	 * 				订单发送状态
	 * @author 038590-foss-wanghui
	 * @date 2012-11-27 下午2:05:27
	 */
	private void updateCrmOrder(OrderHandleDto orderHandleDto) {
		LOGGER.info("更新CRM订单开始：" + orderHandleDto + "-" + orderHandleDto.getOrderStatus());
		// 获取CRM的映射订单状态
		String crmOrderStatus = DispatchOrderStatusConstants.CRM_ORDER_STATUS_MAPPING.get(orderHandleDto.getOrderStatus());
		// 为空则表明不需要将状态反馈给CRM
		if (StringUtils.isEmpty(crmOrderStatus)) {
			return;
		}
		// 更新CRM订单
		CrmModifyInfoDto crmModifyInfoDto = new CrmModifyInfoDto();
		// 订单号
		crmModifyInfoDto.setOrderNumber(orderHandleDto.getOrderNo());
		// 司机姓名
		//为配合裹裹回传司机工号，故拼接司机工号
		crmModifyInfoDto.setDriverName(orderHandleDto.getDriverName() + "-" + orderHandleDto.getDriverCode());
		// 司机手机号
		crmModifyInfoDto.setDriverMobile(orderHandleDto.getDriverMobile());
		// 操作人编码
		crmModifyInfoDto.setOprateUserNum(orderHandleDto.getOperatorCode());
		// 获取操作部门
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(orderHandleDto.getOperateOrgCode());
		// 操作部门code
		crmModifyInfoDto.setOprateDeptCode(org != null ? org.getUnifiedCode() : "");
		// 订单状态
		crmModifyInfoDto.setGoodsStatus(crmOrderStatus);
		// 操作备注
		crmModifyInfoDto.setBackInfo(orderHandleDto.getOperateNotes());
		// 设置收入部门
		DispatchOrderConditionDto dto = new DispatchOrderConditionDto();
		dto.setOrderNo(orderHandleDto.getOrderNo());
		DispatchOrderEntity dispatchOrderEntity = dispatchOrderEntityDao.queryOrdersByOrderNo(dto);
		
		// 小件订单
		// 收入部门标杆编码
		OrgAdministrativeInfoEntity receiveOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(dispatchOrderEntity.getReceiveOrgCode());
		crmModifyInfoDto.setEarningDeptStandardCode(receiveOrg != null ? receiveOrg.getUnifiedCode() : "");
		crmModifyInfoDto.setEarningDeptStandardName(receiveOrg != null ? receiveOrg.getName() : "");
		if (StringUtils.isEmpty(orderHandleDto.getDriverMobile()) && StringUtils.isNotEmpty(orderHandleDto.getDriverCode())) {
			// 如果是小件派送单,并且司机工号或者司机车牌号不为空
			ExpressVehicleDto expressVeh = new ExpressVehicleDto();
			// 司机工号
			expressVeh.setEmpCode(orderHandleDto.getDriverCode());
			// 车牌号
			expressVeh.setVehicleNo(orderHandleDto.getVehicleNo());
			List<ExpressVehicleDto> expressVehicleDtos = commonExpressVehicleService.queryExpressVehicleNoListBySelectiveCondition(expressVeh, NumberConstants.NUMBER_10, NumberConstants.ZERO);
			if (CollectionUtils.isNotEmpty(expressVehicleDtos)) {
				// 司机电话
				crmModifyInfoDto.setDriverMobile(expressVehicleDtos.get(0).getMobilePhone());
			}
		}
		//根据快递员工号查询快递车辆信息
		ExpressVehiclesEntity expressVehiclesEntity = new ExpressVehiclesEntity();
		expressVehiclesEntity.setActive(FossConstants.ACTIVE);
		expressVehiclesEntity.setEmpCode(orderHandleDto.getDriverCode());
		List<ExpressVehiclesEntity> expressVehiclesResult = expressVehiclesDao.queryInfos(expressVehiclesEntity, 1, 0);
		if(CollectionUtils.isNotEmpty(expressVehiclesResult)) {
			String billingOrgCode = expressVehiclesResult.get(0).getOrgCode();
			if(StringUtils.isNotBlank(billingOrgCode)) {
				//设置开单部门编码
				crmModifyInfoDto.setBillingOrgCode(expressVehiclesResult.get(0).getOrgCode());
				OrgAdministrativeInfoEntity  orgInfoEntity = orgAdministrativeInfoDao.queryOrgAdministrativeInfoByCode(billingOrgCode);
				if(orgInfoEntity!=null) {
					//设置开单部门电话
					crmModifyInfoDto.setBillingOrgPhone(orgInfoEntity.getDepTelephone());
				}
			}
		}
		// 调用CRM订单修改接口
		crmOrderJMSService.sendModifyOrder(crmModifyInfoDto);
		
		LOGGER.info("更新CRM订单成功");
	}

	/**
	 * 发送短信.
	 * 
	 * @param smsCode the smsCode
	 * @param orderHandleDto the orderHandleDto
	 * @param isDriver the isDriver
	 * @author 038590-foss-wanghui
	 * @date 2012-11-30 上午11:24:15
	 */
	private void sendSms(String smsCode, OrderHandleDto orderHandleDto, boolean isDriver,String moduleName) {
		LOGGER.info("sendSms start : order " + orderHandleDto + " isDriver " + isDriver);
		NotificationEntity notificationEntity = new NotificationEntity();
		// 设置订单号
		notificationEntity.setWaybillNo(orderHandleDto.getOrderNo());
		// 设置通知模块名  NotifyCustomerConstants.BS_TYPE_PKP_ORDER
		notificationEntity.setModuleName(moduleName);
		// 设置通知类型
		notificationEntity.setNoticeType(NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE);
		// 设置通知内容
		notificationEntity.setNoticeContent(this.getSmsContent(smsCode, orderHandleDto));
		// 设置手机号
		if (isDriver) {
			notificationEntity.setMobile(orderHandleDto.getDriverMobile());
			notificationEntity.setConsignee(orderHandleDto.getDriverName());
		} else {
			notificationEntity.setMobile(orderHandleDto.getCustomerMobile());
			notificationEntity.setConsignee(orderHandleDto.getCustomerName());
		}
		// 设置发送对象
		// 设置操作人
		notificationEntity.setOperator(FossUserContextHelper.getUserName());
		// 设置操作人编号
		notificationEntity.setOperatorNo(FossUserContextHelper.getUserCode());
		// 设置操作部门名称
		notificationEntity.setOperateOrgName(FossUserContextHelper.getOrgName());
		// 设置操作部门编码
		notificationEntity.setOperateOrgCode(FossUserContextHelper.getOrgCode());
		// 设置操作时间
		notificationEntity.setOperateTime(new Date());
		notifyCustomerService.sendMessage(notificationEntity);
	}

	/**
	 * 获取短信信息.
	 * 
	 * @param smsCode the smsCode
	 * @param 
	 * 			id
	 * 				订单ID
	 * 			orderNo
	 * 				订单号
	 * 			orderType
	 * 				订单类型 -- 接货or转货
	 * 			rejectReason
	 * 				拒绝原因
	 * 			desc
	 * 				其他原因描述
	 * 			orderStatus
	 * 				订单待改状态
	 * 			originOrderStatus
	 * 				订单原始状态
	 * 			driverName
	 * 				司机姓名
	 * 			driverCode
	 * 				司机编码
	 * 			vehicleNo
	 * 				车牌号
	 * 			deviceNo
	 * 				设备号
	 * 			operateNotes
	 * 				订单操作备注
	 * 			operator
	 * 				操作人
	 * 			operatorCode
	 * 				操作人编码
	 * 			operateOrgName
	 * 				操作部门
	 * 			operateOrgCode
	 * 				操作部门编码
	 * 			operateTime
	 * 				操作时间
	 * 			isPda
	 * 				是否PDA
	 * 			isSms
	 * 				是否短信
	 * 			isCustomer
	 * 				是否发送客户
	 * 			pickupRegionCode
	 * 				接货小区Code
	 * 			pickupRegionName
	 * 				接货小区名称
	 * 			weight
	 * 				重量
	 * 			volume
	 * 				体积
	 * 			goodsQty
	 * 				件数
	 * 			driverMobile
	 * 				司机手机
	 * 			customerMobile
	 * 				客户手机
	 * 			pickupAddress
	 * 				接货地址
	 * 			salesDepartmentName
	 * 				营业部名称
	 * 			salesDepartmentCode
	 * 				营业部编码
	 * 			orderNotes
	 * 				备注
	 * 			pickupTime
	 * 				接货时间
	 * 			customerName
	 * 				客户姓名
	 * 			tel
	 * 				客户电话
	 * 			orderSendStatus
	 * 				订单发送状态
	 * @return the smsContent
	 * @author 038590-foss-wanghui
	 * @date 2012-11-30 上午10:52:01
	 */
	public String getSmsContent(String smsCode, OrderHandleDto orderHandleDto) {
		String sms = "";
		// 模版参数
		SmsParamDto smsParamDto = new SmsParamDto();
		// 短信编码
		smsParamDto.setSmsCode(smsCode);
		// 部门编码
		smsParamDto.setOrgCode(FossUserContextHelper.getOrgCode());
		// 参数Map
		smsParamDto.setMap(this.getSmsParam(orderHandleDto));
		try {
			sms = sMSTempleteService.querySmsByParam(smsParamDto);
		} catch (Exception e) {
			LOGGER.error("error", e);
		}
		if (sms == null) {
			throw new SmsException(ActionMessageType.SMS_NOTFOUND);
		}
		return sms;
	}

	/**
	 * 设置短信模版内容的参数.
	 * 
	 * @param 
	 * 			id
	 * 				订单ID
	 * 			orderNo
	 * 				订单号
	 * 			orderType
	 * 				订单类型 -- 接货or转货
	 * 			rejectReason
	 * 				拒绝原因
	 * 			desc
	 * 				其他原因描述
	 * 			orderStatus
	 * 				订单待改状态
	 * 			originOrderStatus
	 * 				订单原始状态
	 * 			driverName
	 * 				司机姓名
	 * 			driverCode
	 * 				司机编码
	 * 			vehicleNo
	 * 				车牌号
	 * 			deviceNo
	 * 				设备号
	 * 			operateNotes
	 * 				订单操作备注
	 * 			operator
	 * 				操作人
	 * 			operatorCode
	 * 				操作人编码
	 * 			operateOrgName
	 * 				操作部门
	 * 			operateOrgCode
	 * 				操作部门编码
	 * 			operateTime
	 * 				操作时间
	 * 			isPda
	 * 				是否PDA
	 * 			isSms
	 * 				是否短信
	 * 			isCustomer
	 * 				是否发送客户
	 * 			pickupRegionCode
	 * 				接货小区Code
	 * 			pickupRegionName
	 * 				接货小区名称
	 * 			weight
	 * 				重量
	 * 			volume
	 * 				体积
	 * 			goodsQty
	 * 				件数
	 * 			driverMobile
	 * 				司机手机
	 * 			customerMobile
	 * 				客户手机
	 * 			pickupAddress
	 * 				接货地址
	 * 			salesDepartmentName
	 * 				营业部名称
	 * 			salesDepartmentCode
	 * 				营业部编码
	 * 			orderNotes
	 * 				备注
	 * 			pickupTime
	 * 				接货时间
	 * 			customerName
	 * 				客户姓名
	 * 			tel
	 * 				客户电话
	 * 			orderSendStatus
	 * 				订单发送状态
	 * @return the smsParam
	 * @author 038590-foss-wanghui
	 * @date 2012-11-30 上午10:50:53
	 */
	private Map<String, String> getSmsParam(OrderHandleDto orderHandleDto) {
		Map<String, String> paramMap = new HashMap<String, String>();
		// 订单号
		paramMap.put("orderNo", orderHandleDto.getOrderNo());
		// 营业部名称
		paramMap.put("salesDepartmentName", orderHandleDto.getSalesDepartmentName());
		// 客户姓名
		paramMap.put("customerName", orderHandleDto.getCustomerName());
		// 电话
		paramMap.put("tel", orderHandleDto.getTel());
		// 手机号码
		paramMap.put("mobile", orderHandleDto.getCustomerMobile());
		// 接货地址
		paramMap.put("pickupAddress", orderHandleDto.getPickupAddress());
		// 件数
		paramMap.put("goodsQty", String.valueOf(orderHandleDto.getGoodsQty()));
		// 重量
		paramMap.put("weight", String.valueOf(orderHandleDto.getWeight()));
		// 体积
		paramMap.put("volume", String.valueOf(orderHandleDto.getVolume()));
		// 接货时间
		paramMap.put("pickupTime", orderHandleDto.getPickupTime());
		// 订单备注
		paramMap.put("orderNotes", orderHandleDto.getOrderNotes());
		// 车型
		paramMap.put("vehicleType", orderHandleDto.getVehicleLengthName());
		// 车牌号
		paramMap.put("vehicleNo", orderHandleDto.getVehicleNo());
		// 司机姓名
		paramMap.put("driverName", orderHandleDto.getDriverName());
		// 司机手机
		paramMap.put("driverMobile", orderHandleDto.getDriverMobile());
		// 包装
		paramMap.put("goodsPackage", orderHandleDto.getGoodsPackage());
		return paramMap;
	}

	/**
	 * 增加未接票数.
	 * 
	 * @param 
	 * 			id
	 * 				订单ID
	 * 			orderNo
	 * 				订单号
	 * 			orderType
	 * 				订单类型 -- 接货or转货
	 * 			rejectReason
	 * 				拒绝原因
	 * 			desc
	 * 				其他原因描述
	 * 			orderStatus
	 * 				订单待改状态
	 * 			originOrderStatus
	 * 				订单原始状态
	 * 			driverName
	 * 				司机姓名
	 * 			driverCode
	 * 				司机编码
	 * 			vehicleNo
	 * 				车牌号
	 * 			deviceNo
	 * 				设备号
	 * 			operateNotes
	 * 				订单操作备注
	 * 			operator
	 * 				操作人
	 * 			operatorCode
	 * 				操作人编码
	 * 			operateOrgName
	 * 				操作部门
	 * 			operateOrgCode
	 * 				操作部门编码
	 * 			operateTime
	 * 				操作时间
	 * 			isPda
	 * 				是否PDA
	 * 			isSms
	 * 				是否短信
	 * 			isCustomer
	 * 				是否发送客户
	 * 			pickupRegionCode
	 * 				接货小区Code
	 * 			pickupRegionName
	 * 				接货小区名称
	 * 			weight
	 * 				重量
	 * 			volume
	 * 				体积
	 * 			goodsQty
	 * 				件数
	 * 			driverMobile
	 * 				司机手机
	 * 			customerMobile
	 * 				客户手机
	 * 			pickupAddress
	 * 				接货地址
	 * 			salesDepartmentName
	 * 				营业部名称
	 * 			salesDepartmentCode
	 * 				营业部编码
	 * 			orderNotes
	 * 				备注
	 * 			pickupTime
	 * 				接货时间
	 * 			customerName
	 * 				客户姓名
	 * 			tel
	 * 				客户电话
	 * 			orderSendStatus
	 * 				订单发送状态
	 * @author 038590-foss-wanghui
	 * @date 2012-11-19 下午7:28:35
	 */
	private void addVehicleWV(OrderHandleDto orderHandleDto) {
		VehicleActualSituationDto vehicleActualSituationDto = new VehicleActualSituationDto();
		// 车牌号
		vehicleActualSituationDto.setVehicleNo(orderHandleDto.getVehicleNo());
		// 未接票数
		vehicleActualSituationDto.setNonePickupGoodsQty(1);
		// 修改车载信息
		vehicleManageService.addVehicleWVByVehicleNo(vehicleActualSituationDto);
	}

	/**
	 * 添加派车记录.
	 * 
	 * @param 
	 * 			id
	 * 				订单ID
	 * 			orderNo
	 * 				订单号
	 * 			orderType
	 * 				订单类型 -- 接货or转货
	 * 			rejectReason
	 * 				拒绝原因
	 * 			desc
	 * 				其他原因描述
	 * 			orderStatus
	 * 				订单待改状态
	 * 			originOrderStatus
	 * 				订单原始状态
	 * 			driverName
	 * 				司机姓名
	 * 			driverCode
	 * 				司机编码
	 * 			vehicleNo
	 * 				车牌号
	 * 			deviceNo
	 * 				设备号
	 * 			operateNotes
	 * 				订单操作备注
	 * 			operator
	 * 				操作人
	 * 			operatorCode
	 * 				操作人编码
	 * 			operateOrgName
	 * 				操作部门
	 * 			operateOrgCode
	 * 				操作部门编码
	 * 			operateTime
	 * 				操作时间
	 * 			isPda
	 * 				是否PDA
	 * 			isSms
	 * 				是否短信
	 * 			isCustomer
	 * 				是否发送客户
	 * 			pickupRegionCode
	 * 				接货小区Code
	 * 			pickupRegionName
	 * 				接货小区名称
	 * 			weight
	 * 				重量
	 * 			volume
	 * 				体积
	 * 			goodsQty
	 * 				件数
	 * 			driverMobile
	 * 				司机手机
	 * 			customerMobile
	 * 				客户手机
	 * 			pickupAddress
	 * 				接货地址
	 * 			salesDepartmentName
	 * 				营业部名称
	 * 			salesDepartmentCode
	 * 				营业部编码
	 * 			orderNotes
	 * 				备注
	 * 			pickupTime
	 * 				接货时间
	 * 			customerName
	 * 				客户姓名
	 * 			tel
	 * 				客户电话
	 * 			orderSendStatus
	 * 				订单发送状态
	 * @author 038590-foss-wanghui
	 * @date 2012-11-16 上午10:25:22
	 */
	private void addDispatchVehicleRecord(OrderHandleDto orderHandleDto) {
		DispatchVehicleRecordEntity vehicleRecordEntity = new DispatchVehicleRecordEntity();
		// 调度订单id
		vehicleRecordEntity.settSrvDispatchOrderId(orderHandleDto.getId());
		// 车牌号
		vehicleRecordEntity.setVehicleNo(orderHandleDto.getVehicleNo());
		// id
		vehicleRecordEntity.setId(UUIDUtils.getUUID());
		// 司机姓名
		vehicleRecordEntity.setDriverName(orderHandleDto.getDriverName());
		// 司机code
		vehicleRecordEntity.setDriverCode(orderHandleDto.getDriverCode());
		// 派车时间
		vehicleRecordEntity.setDeliverTime(orderHandleDto.getOperateTime());
		// 是否PDA
		vehicleRecordEntity.setPdaStatus(FossConstants.YES.equals(orderHandleDto.getIsPda()) ? DispatchOrderStatusConstants.ISPDA_NORMAL : DispatchOrderStatusConstants.ISPDA_NO);
		// 定人定区Code
		vehicleRecordEntity.setPickupRegionCode(orderHandleDto.getSmallZoneCodeSuggested());//14.7.22 gcl AUTO-194
		// 定人定区名称
		vehicleRecordEntity.setPickupRegionName(orderHandleDto.getSmallZoneNameSuggested());
		// 订单状态
		vehicleRecordEntity.setOrderStatus(orderHandleDto.getOrderStatus());
		if(DispatchOrderStatusConstants.STATUS_DISPATCH_VEHICLE.equals(orderHandleDto.getOrderStatus())){
			// 受理状态
			vehicleRecordEntity.setProcessStatus(DispatchOrderStatusConstants.STATUS_NONE_HANDLE.equals(orderHandleDto.getOriginOrderStatus()) ? DispatchOrderStatusConstants.STATUS_PROCESS_ACCEPT : DispatchOrderStatusConstants.STATUS_PROCESS_AGAIN);
		} else {
			// 退回状态
			vehicleRecordEntity.setProcessStatus(DispatchOrderStatusConstants.STATUS_PROCESS_RETURN);
		}
		dispatchVehicleRecordEntityDao.addDispatchVehicleRecord(vehicleRecordEntity);
	}

	/**
	 * 初始化订单操作dto.
	 * 
	 * @param 
	 * 			id
	 * 				订单ID
	 * 			orderNo
	 * 				订单号
	 * 			orderType
	 * 				订单类型 -- 接货or转货
	 * 			rejectReason
	 * 				拒绝原因
	 * 			desc
	 * 				其他原因描述
	 * 			orderStatus
	 * 				订单待改状态
	 * 			originOrderStatus
	 * 				订单原始状态
	 * 			driverName
	 * 				司机姓名
	 * 			driverCode
	 * 				司机编码
	 * 			vehicleNo
	 * 				车牌号
	 * 			deviceNo
	 * 				设备号
	 * 			operateNotes
	 * 				订单操作备注
	 * 			operator
	 * 				操作人
	 * 			operatorCode
	 * 				操作人编码
	 * 			operateOrgName
	 * 				操作部门
	 * 			operateOrgCode
	 * 				操作部门编码
	 * 			operateTime
	 * 				操作时间
	 * 			isPda
	 * 				是否PDA
	 * 			isSms
	 * 				是否短信
	 * 			isCustomer
	 * 				是否发送客户
	 * 			pickupRegionCode
	 * 				接货小区Code
	 * 			pickupRegionName
	 * 				接货小区名称
	 * 			weight
	 * 				重量
	 * 			volume
	 * 				体积
	 * 			goodsQty
	 * 				件数
	 * 			driverMobile
	 * 				司机手机
	 * 			customerMobile
	 * 				客户手机
	 * 			pickupAddress
	 * 				接货地址
	 * 			salesDepartmentName
	 * 				营业部名称
	 * 			salesDepartmentCode
	 * 				营业部编码
	 * 			orderNotes
	 * 				备注
	 * 			pickupTime
	 * 				接货时间
	 * 			customerName
	 * 				客户姓名
	 * 			tel
	 * 				客户电话
	 * 			orderSendStatus
	 * 				订单发送状态
	 * @author 038590-foss-wanghui
	 * @date 2012-11-9 上午9:01:49
	 */
	private void initOrderHandle(OrderHandleDto orderHandleDto) {
		// 操作时间
		orderHandleDto.setOperateTime(new Date());
		// 操作人
		orderHandleDto.setOperator(FossUserContextHelper.getUserName());
		// 操作人编码
		orderHandleDto.setOperatorCode(FossUserContextHelper.getUserCode());
		// 操作部门
		orderHandleDto.setOperateOrgName(FossUserContextHelper.getOrgName());
		// 操作部门编码
		orderHandleDto.setOperateOrgCode(FossUserContextHelper.getOrgCode());
	}

	/**
	 * 拒绝订单.
	 * 
	 * @param 
	 * 			id
	 * 				订单ID
	 * 			orderNo
	 * 				订单号
	 * 			orderType
	 * 				订单类型 -- 接货or转货
	 * 			rejectReason
	 * 				拒绝原因
	 * 			desc
	 * 				其他原因描述
	 * 			orderStatus
	 * 				订单待改状态
	 * 			originOrderStatus
	 * 				订单原始状态
	 * 			driverName
	 * 				司机姓名
	 * 			driverCode
	 * 				司机编码
	 * 			vehicleNo
	 * 				车牌号
	 * 			deviceNo
	 * 				设备号
	 * 			operateNotes
	 * 				订单操作备注
	 * 			operator
	 * 				操作人
	 * 			operatorCode
	 * 				操作人编码
	 * 			operateOrgName
	 * 				操作部门
	 * 			operateOrgCode
	 * 				操作部门编码
	 * 			operateTime
	 * 				操作时间
	 * 			isPda
	 * 				是否PDA
	 * 			isSms
	 * 				是否短信
	 * 			isCustomer
	 * 				是否发送客户
	 * 			pickupRegionCode
	 * 				接货小区Code
	 * 			pickupRegionName
	 * 				接货小区名称
	 * 			weight
	 * 				重量
	 * 			volume
	 * 				体积
	 * 			goodsQty
	 * 				件数
	 * 			driverMobile
	 * 				司机手机
	 * 			customerMobile
	 * 				客户手机
	 * 			pickupAddress
	 * 				接货地址
	 * 			salesDepartmentName
	 * 				营业部名称
	 * 			salesDepartmentCode
	 * 				营业部编码
	 * 			orderNotes
	 * 				备注
	 * 			pickupTime
	 * 				接货时间
	 * 			customerName
	 * 				客户姓名
	 * 			tel
	 * 				客户电话
	 * 			orderSendStatus
	 * 				订单发送状态
	 * @return true, if successful
	 * @author 038590-foss-wanghui
	 * @date 2012-10-23 上午9:50:13
	 * @see com.deppon.foss.module.pickup.order.api.server.service.IOrderTaskHandleService#rejectOrder(com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity)
	 */
	@Transactional
	@Override
	public boolean rejectOrder(OrderHandleDto orderHandleDto) {
		// 订单为空
		if (orderHandleDto == null) {
			throw new DispatchException(ActionMessageType.NONE_ORDER);
		}
		String reason = RejectReasonConstants.UNKNOW_ADDRESS.equals(orderHandleDto.getRejectReason()) ? orderHandleDto.getRejectReason() : orderHandleDto.getDesc();
		// 设置操作备注
		orderHandleDto.setOperateNotes(reason);
		// 拒绝订单状态
		orderHandleDto.setOrderStatus(DispatchOrderStatusConstants.STATUS_RETURN);
		initOrderHandle(orderHandleDto);
		LOGGER.info("rejectOrder start : " + orderHandleDto);
		// 更新订单状态
		int count = dispatchOrderEntityDao.updateByOrderHandle(orderHandleDto);
		if (count == 1) {
			/*
			 * 订单操作历史
			 */
			addDispatchOrderActionHistory(orderHandleDto);
			// 添加派车记录
			addDispatchVehicleRecord(orderHandleDto);
			// 修改外部系统状态
			updateExternalSystem(orderHandleDto, reason);
			// 更新派车记录
			updateDispatchVehicleRecord(orderHandleDto);
			// 应用监控--订单分派票数
//			businessMonitorService.counter(BusinessMonitorIndicator.ORDER_ASSIGN_COUNT, FossUserContext.getCurrentInfo());
			// 应用监控--订单处理票数
			businessMonitorService.counter(BusinessMonitorIndicator.ORDER_PROCESSED_COUNT, FossUserContext.getCurrentInfo());
			// 应用监控--已取消订单量
			businessMonitorService.counter(BusinessMonitorIndicator.ORDER_CANCELED_COUNT, FossUserContext.getCurrentInfo());
			LOGGER.info("rejectOrder end");
			return true;
		} else {
			throw new DispatchException(ActionMessageType.REJECT_FAILURE);
		}
	}

	/**
	 * 更新派车记录.
	 * 
	 * @param 
	 * 			id
	 * 				订单ID
	 * 			orderNo
	 * 				订单号
	 * 			orderType
	 * 				订单类型 -- 接货or转货
	 * 			rejectReason
	 * 				拒绝原因
	 * 			desc
	 * 				其他原因描述
	 * 			orderStatus
	 * 				订单待改状态
	 * 			originOrderStatus
	 * 				订单原始状态
	 * 			driverName
	 * 				司机姓名
	 * 			driverCode
	 * 				司机编码
	 * 			vehicleNo
	 * 				车牌号
	 * 			deviceNo
	 * 				设备号
	 * 			operateNotes
	 * 				订单操作备注
	 * 			operator
	 * 				操作人
	 * 			operatorCode
	 * 				操作人编码
	 * 			operateOrgName
	 * 				操作部门
	 * 			operateOrgCode
	 * 				操作部门编码
	 * 			operateTime
	 * 				操作时间
	 * 			isPda
	 * 				是否PDA
	 * 			isSms
	 * 				是否短信
	 * 			isCustomer
	 * 				是否发送客户
	 * 			pickupRegionCode
	 * 				接货小区Code
	 * 			pickupRegionName
	 * 				接货小区名称
	 * 			weight
	 * 				重量
	 * 			volume
	 * 				体积
	 * 			goodsQty
	 * 				件数
	 * 			driverMobile
	 * 				司机手机
	 * 			customerMobile
	 * 				客户手机
	 * 			pickupAddress
	 * 				接货地址
	 * 			salesDepartmentName
	 * 				营业部名称
	 * 			salesDepartmentCode
	 * 				营业部编码
	 * 			orderNotes
	 * 				备注
	 * 			pickupTime
	 * 				接货时间
	 * 			customerName
	 * 				客户姓名
	 * 			tel
	 * 				客户电话
	 * 			orderSendStatus
	 * 				订单发送状态
	 * @author 038590-foss-wanghui
	 * @date 2013-2-3 下午3:07:02
	 */
	public void updateDispatchVehicleRecord(OrderHandleDto orderHandleDto) {
		// 参数构造
		DispatchOrderVehicleDto dispatchOrderVehicleDto = new DispatchOrderVehicleDto();
		dispatchOrderVehicleDto.setOrderId(orderHandleDto.getId());
		dispatchOrderVehicleDto.setOrderStatus(orderHandleDto.getOrderStatus());
		// 更新派车记录
		dispatchVehicleRecordEntityDao.updateVehicleRecordByOrderId(dispatchOrderVehicleDto);
	}

	/**
	 * 订单开单更新.
	 * 
	 * @param 
	 * 			id
	 * 				订单ID
	 * 			orderNo
	 * 				订单号
	 * 			orderType
	 * 				订单类型 -- 接货or转货
	 * 			rejectReason
	 * 				拒绝原因
	 * 			desc
	 * 				其他原因描述
	 * 			orderStatus
	 * 				订单待改状态
	 * 			originOrderStatus
	 * 				订单原始状态
	 * 			driverName
	 * 				司机姓名
	 * 			driverCode
	 * 				司机编码
	 * 			vehicleNo
	 * 				车牌号
	 * 			deviceNo
	 * 				设备号
	 * 			operateNotes
	 * 				订单操作备注
	 * 			operator
	 * 				操作人
	 * 			operatorCode
	 * 				操作人编码
	 * 			operateOrgName
	 * 				操作部门
	 * 			operateOrgCode
	 * 				操作部门编码
	 * 			operateTime
	 * 				操作时间
	 * 			isPda
	 * 				是否PDA
	 * 			isSms
	 * 				是否短信
	 * 			isCustomer
	 * 				是否发送客户
	 * 			pickupRegionCode
	 * 				接货小区Code
	 * 			pickupRegionName
	 * 				接货小区名称
	 * 			weight
	 * 				重量
	 * 			volume
	 * 				体积
	 * 			goodsQty
	 * 				件数
	 * 			driverMobile
	 * 				司机手机
	 * 			customerMobile
	 * 				客户手机
	 * 			pickupAddress
	 * 				接货地址
	 * 			salesDepartmentName
	 * 				营业部名称
	 * 			salesDepartmentCode
	 * 				营业部编码
	 * 			orderNotes
	 * 				备注
	 * 			pickupTime
	 * 				接货时间
	 * 			customerName
	 * 				客户姓名
	 * 			tel
	 * 				客户电话
	 * 			orderSendStatus
	 * 				订单发送状态
	 * @author 038590-foss-wanghui
	 * @date 2013-2-2 上午11:22:48
	 */
	@Override
	public void orderWaybill(OrderHandleDto orderHandleDto) {
		if (orderHandleDto == null || StringUtils.isEmpty(orderHandleDto.getOrderNo())) {
			return;
		}
		// 根据订单号查询对应的订单
		DispatchOrderEntity dispatchOrder = dispatchOrderEntityDao.queryOrderByOrderNo(orderHandleDto.getOrderNo());
		if (dispatchOrder == null) {
			return;
		}
		orderHandleDto.setId(dispatchOrder.getId());
		EmployeeEntity employeeEntity = employeeService.queryEmployeeByEmpCode(orderHandleDto.getOperatorCode());
		if (employeeEntity != null) {
			orderHandleDto.setOperator(employeeEntity.getEmpName());
		}
		OrgAdministrativeInfoEntity administrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(orderHandleDto.getOperateOrgCode());
		if (administrativeInfoEntity != null) {
			orderHandleDto.setOperateOrgName(administrativeInfoEntity.getName());
		}
		int count = dispatchOrderEntityDao.updateByOrderHandle(orderHandleDto);
		if (count == 1) {
			// 更新派车记录
			updateDispatchVehicleRecord(orderHandleDto);
			// 添加订单操作历史
			addDispatchOrderActionHistory(orderHandleDto);
			// 更新外部系统状态
			updateExternalSystem(orderHandleDto, null);
		}
	}


	/**
	 * Sets the dispatchOrderEntityDao.
	 * 
	 * @param dispatchOrderEntityDao the dispatchOrderEntityDao to set
	 */
	public void setDispatchOrderEntityDao(IDispatchOrderEntityDao dispatchOrderEntityDao) {
		this.dispatchOrderEntityDao = dispatchOrderEntityDao;
	}

	/**
	 * Sets the dispatchVehicleRecordEntityDao.
	 * 
	 * @param dispatchVehicleRecordEntityDao the dispatchVehicleRecordEntityDao
	 *            to set
	 */
	public void setDispatchVehicleRecordEntityDao(IDispatchVehicleRecordEntityDao dispatchVehicleRecordEntityDao) {
		this.dispatchVehicleRecordEntityDao = dispatchVehicleRecordEntityDao;
	}

	/**
	 * Sets the dispatchOrderActionHistoryEntityDao.
	 * 
	 * @param dispatchOrderActionHistoryEntityDao the
	 *            dispatchOrderActionHistoryEntityDao to set
	 */
	public void setDispatchOrderActionHistoryEntityDao(IDispatchOrderActionHistoryEntityDao dispatchOrderActionHistoryEntityDao) {
		this.dispatchOrderActionHistoryEntityDao = dispatchOrderActionHistoryEntityDao;
	}

	/**
	 * 根据订单ID更改调度订单完成状态.
	 * 
	 * @param dto
	 * 			id
	 * 				派车记录Id
	 * 			orderId
	 * 				订单ID
	 * 			orderVehicleOrgName
	 * 				约车部门名称
	 * 			orderNo
	 * 				订单号
	 * 			deliverBeginTime
	 * 				派车时间 ==处理订单时间开始时间
	 * 			deliverEndTime
	 * 				派车时间 ==处理订单时间结束时间
	 * 			earliestPickupTime
	 * 				接货最早时间 == 用车时间
	 * 			latestPickupTime
	 * 				接货最晚时间
	 * 			orderVehicleTime
	 * 				约车时间
	 * 			driverName
	 * 				司机姓名
	 * 			vehicleNo
	 * 				车牌号
	 * 			orderNotes
	 * 				备注 ==订单备注
	 * 			pickupRegionCode
	 * 				定人定区
	 * 			orderSendStatus
	 * 				订单任务发送状态
	 * 			orderStatus
	 * 				订单任务完成状态 == 订单状态
	 * 			orderType
	 * 				订单类型
	 * 			usecarTime
	 * 				用车时间
	 * 			pdaStatus
	 * 				PDA使用状态
	 * 			deliverTime
	 * 				派车时间
	 * @return the integer
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-8 上午10:47:25
	 */
	@Override
	public Integer updateDispatchOrderStatusById(DispatchOrderVehicleDto dto) {
		OrderHandleDto orderHandleDto = new OrderHandleDto();
		// 订单号
		orderHandleDto.setOrderNo(dto.getOrderNo());
		// 订单类型
		orderHandleDto.setOrderType(dto.getOrderType());
		// 订单状态
		orderHandleDto.setOrderStatus(dto.getOrderStatus());
		// 订单id
		orderHandleDto.setId(dto.getOrderId());
		orderHandleDto.setOperateTime(new Date());
		DispatchOrderEntity order = dispatchOrderEntityDao.queryOrderByOrderNo(dto.getOrderNo());
		
		// 小件订单 变更订单状态时，如果是更新为PDA接受 or 已开单是，更新发送状态为已发送
		if (StringUtils.equals(dto.getOrderStatus(), DispatchOrderStatusConstants.STATUS_PDA_ACCEPT) || 
				StringUtils.equals(dto.getOrderStatus(), DispatchOrderStatusConstants.STATUS_WAYBILL)) {
				dto.setOrderSendStatus(DispatchOrderStatusConstants.ORDER_SENDSUCCESS);
		}
		// 初始化orderHandle中的操作人等
		initOrderHandle(orderHandleDto);
		// 更新外部系统的订单
		updateExternalSystem(orderHandleDto, null);
		// 更新派车记录
		updateDispatchVehicleRecord(orderHandleDto);
		// 添加订单操作历史
		addDispatchOrderActionHistory(orderHandleDto);
		return this.dispatchOrderEntityDao.updateDispatchOrderStatusById(dto);
	}

	/**
	 * PDA异常，根据派车记录ID修改是否PDA为异常 且更新订单状态为：“待改接”.
	 * 
	 * @param dto
	 * 			id
	 * 				派车记录Id
	 * 			orderId
	 * 				订单ID
	 * 			orderVehicleOrgName
	 * 				约车部门名称
	 * 			orderNo
	 * 				订单号
	 * 			deliverBeginTime
	 * 				派车时间 ==处理订单时间开始时间
	 * 			deliverEndTime
	 * 				派车时间 ==处理订单时间结束时间
	 * 			earliestPickupTime
	 * 				接货最早时间 == 用车时间
	 * 			latestPickupTime
	 * 				接货最晚时间
	 * 			orderVehicleTime
	 * 				约车时间
	 * 			driverName
	 * 				司机姓名
	 * 			vehicleNo
	 * 				车牌号
	 * 			orderNotes
	 * 				备注 ==订单备注
	 * 			pickupRegionCode
	 * 				定人定区
	 * 			orderSendStatus
	 * 				订单任务发送状态
	 * 			orderStatus
	 * 				订单任务完成状态 == 订单状态
	 * 			orderType
	 * 				订单类型
	 * 			usecarTime
	 * 				用车时间
	 * 			pdaStatus
	 * 				PDA使用状态
	 * 			deliverTime
	 * 				派车时间
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-9 下午3:13:35
	 */
	@Transactional
	@Override
	public void updateIspdaByVehicleId(List<DispatchOrderVehicleDto> dtos) {
		for (DispatchOrderVehicleDto dto : dtos) {
			// 根据派车记录ID将派车记录PdaStatus 改为异常
			DispatchVehicleRecordEntity entity = new DispatchVehicleRecordEntity();
			entity.setId(dto.getId());
			entity.setPdaStatus(DispatchOrderStatusConstants.ISPDA_EXCEPTION);
			dispatchVehicleRecordEntityDao.updateIspdaByVehicleId(entity);
			// 将订单状态改为“待改接”
			dto.setOrderStatus(DispatchOrderStatusConstants.STATUS_AGAIN_PICKUP);
			dispatchOrderEntityDao.updateDispatchOrderStatusById(dto);
			DispatchOrderEntity dispatchOrder = dispatchOrderEntityDao.queryOrderById(dto.getOrderId());
			OrderHandleDto orderHandleDto = new OrderHandleDto();
			// 订单id
			orderHandleDto.setId(dto.getOrderId());
			// 订单号
			orderHandleDto.setOrderNo(dispatchOrder.getOrderNo());
			// 订单类型
			orderHandleDto.setOrderType(dispatchOrder.getOrderType());
			// 订单状态
			orderHandleDto.setOrderStatus(dto.getOrderStatus());
			// 初始化orderHandle中的操作人等
			initOrderHandle(orderHandleDto);
			// 更新外部系统的订单
			updateExternalSystem(orderHandleDto, null);
			// 更新派车记录
			updateDispatchVehicleRecord(orderHandleDto);
			// 添加订单操作历史
			addDispatchOrderActionHistory(orderHandleDto);
		}

	}
	/**
	 * 
	 * @Title: sendToGPS
	 * @Description: 将约车订单信息发送给GPS
	 * @param @param optState 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void sendToGPS(OrderHandleDto orderHandleDto, int optState) {
		boolean success = GPSOrderTaskService.putOrderTaskToGPS(orderHandleDto,
				optState);
		// 如果发送成功
		if (success) {
			LOGGER.info("订单发送GPS成功！");
		} else {
			LOGGER.error("订单发送GPS失败！");
			throw new BusinessException("订单发送GPS失败！");
		}
	}
	
	/**
	 * 
	 * 抛出异常之后 回滚订单状态
	 * @author 043258-foss-zhaobin
	 * @date 2014-12-26 下午3:49:26
	 */
	public void backOrderHandle(OrderHandleDto orderHandleDto){
		orderHandleDto.setSmallZoneCodeActual("");
		orderHandleDto.setDriverCode("");
		orderHandleDto.setDriverName("");
		orderHandleDto.setDeviceNo("");
		orderHandleDto.setIsPda("");
		orderHandleDto.setIsSms("");
		orderHandleDto.setIsCustomer("");
		orderHandleDto.setOperator("");
		orderHandleDto.setOperatorCode("");
		orderHandleDto.setOperateOrgCode("");
		orderHandleDto.setOperateOrgName("");
		// 设置状态为已派车
		orderHandleDto.setOrderStatus(DispatchOrderStatusConstants.STATUS_NONE_HANDLE);
		// 发送状态制空
		orderHandleDto.setOrderSendStatus("");
		// 设置订单受理方式为空
		orderHandleDto.setAcceptStatus("");
		orderHandleDto.setDriverMobile("");
		orderHandleDto.setOriginOrderStatus(null);
		dispatchOrderEntityDao.updateByOrderHandle(orderHandleDto);
	}
	
	/**
	 * 
	 * @Title: addExceptionLog 
	 * @Description: 自动处理异常日志新增
	 * @param @param dispatchOrderEntity
	 * @param @param excptionType
	 * @param @param reason    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public void addExceptionLog(OrderHandleDto orderHandleDto,
		String excptionType,String reason){
		// 如果待分配的快递员为空，则新增日常日志，同时转为人工处理
		OrderAutoExceptionLogEntity orderAutoExceptionLogEntity = new OrderAutoExceptionLogEntity();
		orderAutoExceptionLogEntity.setId(UUIDUtils
				.getUUID());
		orderAutoExceptionLogEntity
				.setDispatchOrderId(orderHandleDto.getId());
		orderAutoExceptionLogEntity
				.setOrderNo(orderHandleDto
						.getOrderNo());
		orderAutoExceptionLogEntity
				.setExceptionType(excptionType);
		orderAutoExceptionLogEntity
				.setExceptionReason(reason);
		orderAutoExceptionLogEntity
				.setCreateTime(new Date());
		orderAutoExceptionLogService
				.insertAutoOrderExceptionLog(orderAutoExceptionLogEntity);		
	}
	
	/**
	 * Sets the vehicleManageService.
	 * 
	 * @param vehicleManageService the vehicleManageService to set
	 */
	public void setVehicleManageService(IVehicleManageService vehicleManageService) {
		this.vehicleManageService = vehicleManageService;
	}


	/**
	 * Sets the orgAdministrativeInfoService.
	 * 
	 * @param orgAdministrativeInfoService the orgAdministrativeInfoService to
	 *            set
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * Sets the crmOrderJMSService.
	 * 
	 * @param crmOrderJMSService the crmOrderJMSService to set
	 */
	public void setCrmOrderJMSService(ICrmOrderJMSService crmOrderJMSService) {
		this.crmOrderJMSService = crmOrderJMSService;
	}

	public void setCrmOrderService(ICrmOrderService crmOrderService) {
		this.crmOrderService = crmOrderService;
	}

	/**
	 * Sets the sMSTempleteService.
	 * 
	 * @param sMSTempleteService the sMSTempleteService to set
	 */
	public void setsMSTempleteService(ISMSTempleteService sMSTempleteService) {
		this.sMSTempleteService = sMSTempleteService;
	}

	/**
	 * Sets the notifyCustomerService.
	 * 
	 * @param notifyCustomerService the notifyCustomerService to set
	 */
	public void setNotifyCustomerService(INotifyCustomerService notifyCustomerService) {
		this.notifyCustomerService = notifyCustomerService;
	}

	/**
	 * Sets the employeeService.
	 * 
	 * @param employeeService the employeeService to set
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	/**
	 * Sets the orderVehicleService.
	 * 
	 * @param orderVehicleService the orderVehicleService to set
	 */
	public void setOrderVehicleService(IOrderVehicleService orderVehicleService) {
		this.orderVehicleService = orderVehicleService;
	}

	/**
	 * Sets the messageService.
	 * 
	 * @param messageService the messageService to set
	 */
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	/**
	 * Sets the messageBundle.
	 * 
	 * @param messageBundle the messageBundle to set
	 */
	public void setMessageBundle(IMessageBundle messageBundle) {
		this.messageBundle = messageBundle;
	}

	/**
	 * Sets the businessMonitorService.
	 * 
	 * @param businessMonitorService the businessMonitorService to set
	 */
	public void setBusinessMonitorService(IBusinessMonitorService businessMonitorService) {
		this.businessMonitorService = businessMonitorService;
	}

	public void setTruckResourceDao(ITruckResourceDao truckResourceDao) {
		this.truckResourceDao = truckResourceDao;
	}

	/** 
	 * 撤销订单
	 * @author 038590-foss-wanghui
	 * @date 2013-8-20 下午4:32:33
	 * @see com.deppon.foss.module.pickup.order.api.server.service.IOrderTaskHandleService#revokeTransferOrder(com.deppon.foss.module.pickup.order.api.shared.dto.TransferOrderDto)
	 */
	@Override
	public boolean revokeTransferOrder(TransferOrderDto dto) {
		// 传入参数空校验
		if (dto == null || StringUtils.isBlank(dto.getOrderNo())) {
			throw new DispatchException("订单号为空，不允许撤销！");
		}
		// 查询订单
		DispatchOrderEntity oldOrder = dispatchOrderEntityDao.queryOrderByOrderNo(dto.getOrderNo());
		DispatchOrderEntity dispatchOrderEntity = new DispatchOrderEntity();
		// 判断是否已操作过，未操作过，允许撤销，否则抛异常
		if(DispatchOrderStatusConstants.STATUS_NONE_HANDLE.equals(oldOrder.getOrderStatus())){
			dispatchOrderEntity.setId(oldOrder.getId());
			dispatchOrderEntity.setOrderStatus(DispatchOrderStatusConstants.STATUS_REVOCATION);
			dispatchOrderEntityDao.updateByPrimaryKeySelective(dispatchOrderEntity);
		} else {
			throw new DispatchException("当前订单已被操作，不允许撤销！");
		}
		return true;
	}
	
	
	/**
	 * 取消订单
	 * @param payload
	 * @param response
	 */
	@Override
	public void cancelOrder(CancelOrderRequest payload, CancelOrderResponse response) {
		// 空则返回
		if(payload == null || StringUtils.isBlank(payload.getOrderNumber())){
			// 传入的约车订单号码为空
			response.setResult(NumberConstants.ZERO);
			response.setReason("传入的约车订单号码为空");
			return;
		}
		DispatchOrderConditionDto dto = new DispatchOrderConditionDto();
		dto.setOrderNo(payload.getOrderNumber());
		DispatchOrderEntity dispatchOrderEntity = dispatchOrderEntityDao.queryOrdersByOrderNo(dto);
		if (dispatchOrderEntity == null ) {
			// 传入的约车订单号码在FOSS中不存在。
			response.setResult(NumberConstants.ZERO);
			response.setReason("传入的约车订单号码在FOSS中不存在。");
			return;
		}
		if (!ProductCodeUtils.isExpress(dispatchOrderEntity.getProductCode())) {
			// 如果不是快递订单
			response.setResult(NumberConstants.ZERO);
			response.setReason("传入的约车订单号码不是快递订单。");
			return;
		}
		if (StringUtils.equals(DispatchOrderStatusConstants.STATUS_CANCEL, dispatchOrderEntity.getOrderStatus())) {
			// 已取消
			response.setResult(NumberConstants.ONE);
			response.setReason("已经取消");
			return;
		}
		OrderHandleDto record = new OrderHandleDto();
		record.setOrderNo(payload.getOrderNumber());
		record.setOrderStatus(DispatchOrderStatusConstants.STATUS_CANCEL);
		List<String> orderStatusList = new ArrayList<String>();
		orderStatusList.add(DispatchOrderStatusConstants.STATUS_NONE_HANDLE);
		orderStatusList.add(DispatchOrderStatusConstants.STATUS_DISPATCH_VEHICLE);
		record.setOrderStatusList(orderStatusList);
		int i = dispatchOrderEntityDao.updateByExpressOrderHandle(record);
		if (i > 0) {
			// 取消成功
			response.setResult(NumberConstants.ONE);
			return;
		} else {
			// 取消成功
			response.setResult(NumberConstants.ZERO);
			response.setReason("订单处于接货中或者以后的状态都不能取消");
			return;
		}
	}
	
	/**
	 * 
	 * @Title: addOrderReport 
	 * @Description: 增加快递每日报表记录 
	 * @param @param orderHandleDto    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	private void addOrderReport(OrderHandleDto orderHandleDto){
		OrderReportEntity orderReportEntity = new OrderReportEntity();
		orderReportEntity.setId(UUIDUtils.getUUID());
		orderReportEntity.setDispatchOrderId(orderHandleDto.getId());
		orderReportEntity.setOrderNo(orderHandleDto.getOrderNo());
		orderReportEntity.setPickupAddress(orderHandleDto.getPickupAddress());
		orderReportEntity.setExpressDriverCode(orderHandleDto.getDriverCode());
		orderReportEntity.setExpressDriverName(orderHandleDto.getDriverName());
		orderReportEntity.setExpressDriverPhone(orderHandleDto.getDriverMobile());
		orderReportEntity.setVehicleNo(orderHandleDto.getVehicleNo());
		orderReportEntity.setPickupRegionCode(orderHandleDto.getSmallZoneCodeSuggested());//14.7.22 gcl AUTO-194
		orderReportEntity.setPickupRegionName(orderHandleDto.getSmallZoneNameSuggested());
		orderReportService.insertOrderReport(orderReportEntity);
	};
	/**
	 * 
	 * @Title: addRegionCourierReport 
	 * @Description: 增加快递每日报表记录 
	 * @param @param orderHandleDto    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	private void addRegionCourierReport(OrderHandleDto orderHandleDto){
		RegionCourierReportEntity regionCourierReportEntity = new RegionCourierReportEntity();
		regionCourierReportEntity.setCourierCode(orderHandleDto.getDriverCode());
		regionCourierReportEntity.setRegionCode(orderHandleDto.getPickupRegionCode());
		regionCourierReportEntity.setRecieveOrders(new BigDecimal(1));
		regionCourierReportService.insert(regionCourierReportEntity);
	}
	
	public void setCommonExpressVehicleService(ICommonExpressVehicleService commonExpressVehicleService) {
		this.commonExpressVehicleService = commonExpressVehicleService;
	}
	
	public void setOrderReportService(IOrderReportService orderReportService) {
		this.orderReportService = orderReportService;
	}
	
	public void setCustomerDao(ICustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public void setRegionCourierReportService(
			IRegionCourierReportService regionCourierReportService) {
		this.regionCourierReportService = regionCourierReportService;
	}

	public void setGisAddressCollectionService(
			IGisAddressCollectionService gisAddressCollectionService) {
		this.gisAddressCollectionService = gisAddressCollectionService;
	}

	public void setGPSOrderTaskService(IGPSOrderTaskService gPSOrderTaskService) {
		GPSOrderTaskService = gPSOrderTaskService;
	}
	public void setOrderTaskHandleService(
			IOrderTaskHandleService orderTaskHandleService) {
		this.orderTaskHandleService = orderTaskHandleService;
	}

	public void setAppOrderJMSService(IAppOrderJMSService appOrderJMSService) {
		this.appOrderJMSService = appOrderJMSService;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setOrderAutoExceptionLogService(
			IOrderAutoExceptionLogService orderAutoExceptionLogService) {
		this.orderAutoExceptionLogService = orderAutoExceptionLogService;
	}

	/**
	 * @param expressVehiclesDao the expressVehiclesDao to set
	 */
	public void setExpressVehiclesDao(IExpressVehiclesDao expressVehiclesDao) {
		this.expressVehiclesDao = expressVehiclesDao;
	}

	/**
	 * @param orgAdministrativeInfoDao the orgAdministrativeInfoDao to set
	 */
	public void setOrgAdministrativeInfoDao(
			IOrgAdministrativeInfoDao orgAdministrativeInfoDao) {
		this.orgAdministrativeInfoDao = orgAdministrativeInfoDao;
	}

	/**
	 * @param customerService the customerService to set
	 */
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	public void setOrderBusinessLockService(
			IOrderBusinessLockService orderBusinessLockService) {
		this.orderBusinessLockService = orderBusinessLockService;
	}
		
}