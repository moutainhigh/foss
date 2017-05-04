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

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.esb.inteface.domain.crm.AsynOmsGoodsBillReceiveRequest;
import com.deppon.esb.pojo.domain.crm2foss.UpdateEOrderRequest;
import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.esb.crm.server.order.CancelOrderRequest;
import com.deppon.foss.esb.crm.server.order.CancelOrderResponse;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.server.web.message.IMessageBundle;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOwnDriverDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonExpressVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnDriverEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressVehicleDto;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
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
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderActionHistoryEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderChangeEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchVehicleRecordEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IEWaybillOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IModifEwaybillOrderRecordDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IPreHandEWaybillOrderDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IRPSDispatchOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.ITruckResourceDao;
import com.deppon.foss.module.pickup.order.api.server.service.IAppOrderJMSService;
import com.deppon.foss.module.pickup.order.api.server.service.IGPSOrderTaskService;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderAutoExceptionLogService;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderBusinessLockService;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderTaskHandleService;
import com.deppon.foss.module.pickup.order.api.server.service.ISpecialPickupAddressService;
import com.deppon.foss.module.pickup.order.api.server.service.IVehicleManageService;
import com.deppon.foss.module.pickup.order.api.shared.define.ActionMessageType;
import com.deppon.foss.module.pickup.order.api.shared.define.DispatchOrderStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.define.OrderMutexElementConstants;
import com.deppon.foss.module.pickup.order.api.shared.define.RejectReasonConstants;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderActionHistoryEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderChangeEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchVehicleRecordEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.EWaybillOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.ModifyEwaybillOrderLogEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.OrderAutoExceptionLogEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.PreHandEWaybillOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.RPSOrderJobEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.AppOrderDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderVehicleDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.FailOrderDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderHandleDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderMutexElement;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.TransferOrderDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.VehicleActualSituationDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.VehicleInfoDto;
import com.deppon.foss.module.pickup.order.api.shared.exception.DispatchException;
import com.deppon.foss.module.pickup.order.api.shared.exception.SmsException;
import com.deppon.foss.module.pickup.order.api.shared.util.SettlementReportNumber;
import com.deppon.foss.module.pickup.order.server.hdrule.HdRuleNotifyBase;
import com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.ExceptionProcessException;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.NotifyCustomerException;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderJMSService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IEWaybillService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRelateDetailEntityService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmModifyInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderDetailDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmRefundTypeEnum;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmReturnBillTypeEnum;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IOrderVehicleService;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.ows.inteface.domain.orderstate.OrderStateRequest;

/**
 * 订单处理的service
 * 
 * @author 038590-foss-wanghui
 * @date 2012-10-23 上午9:49:49
 */
public class OrderTaskHandleService implements IOrderTaskHandleService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderTaskHandleService.class);

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
	 * 订单变更表DAO
	 */
	private IDispatchOrderChangeEntityDao dispatchOrderChangeEntityDao;
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
	 * 营业部服务
	 */
	private ISaleDepartmentService saleDepartmentService;
	/**
	 * 车辆服务
	 */
	private ITruckResourceDao truckResourceDao;
	
	private ICommonExpressVehicleService commonExpressVehicleService;
	
	/**
	 * GPS服务
	 */
	private IGPSOrderTaskService GPSOrderTaskService;
	/** 
	 * 业务互斥锁服务
	 */
	private IBusinessLockService businessLockService;
	//"公司司机"DAO
    private IOwnDriverDao ownDriverDao;
	
	private IAppOrderJMSService appOrderJMSService;
	
	private IConfigurationParamsService configurationParamsService;
	
	private IOrderAutoExceptionLogService orderAutoExceptionLogService;
	
	private ICustomerService customerService;
	
	private IWaybillExpressService waybillExpressService;
	
	private IOrderBusinessLockService orderBusinessLockService;
	/** 
	 * 零
	 */
	private static final int ZERO = 0;
	
	/**
	 * 订单来源 大客户发件系统
	 */
	private static final String ORDER_RESOURCE_VAS = "VAS";
	/**
	 * 电子运单服务；
	 */
	private IEWaybillOrderEntityDao ewaybillOrderEntityDao;
	/**
	 * 特殊地址服务
	 */
	private ISpecialPickupAddressService specialPickupAddressService;
	
	private IEWaybillService ewaybillService;
	
	/**
	 * 电子运单预处理数据dao层
	 */
	private IPreHandEWaybillOrderDao preHandEWaybillOrderDao;
	
	/**
	 * 子母件服务接口层
	 */
	private IWaybillRelateDetailEntityService waybillRelateDetailEntityService;
	
	/**
	 * 修改电子运单订单信息日志 
	 */
	private IModifEwaybillOrderRecordDao modifEwaybillOrderRecordDao;
	
	private ICustomerDao customerDao;
	
	//综合的接口，排除外请车
	private ILeasedVehicleService leasedVehicleService;
		
	//综合的接口,非本公司的车
	private static final String ORDER_RESOURCE_LEASEDVENHICLE = "leasedVehicle";
	
	/**
	 * RPS调度订单DAO
	 */
	private IRPSDispatchOrderEntityDao rpsdispatchOrderEntityDao;
	
	/**
	 * RPS调度订单DAO
	 * set method
	 */
	public void setRpsdispatchOrderEntityDao(
			IRPSDispatchOrderEntityDao rpsdispatchOrderEntityDao) {
		this.rpsdispatchOrderEntityDao = rpsdispatchOrderEntityDao;
	}

	public void setPreHandEWaybillOrderDao(IPreHandEWaybillOrderDao preHandEWaybillOrderDao) {
		this.preHandEWaybillOrderDao = preHandEWaybillOrderDao;
	}
	
	
	public void setEwaybillOrderEntityDao(
			IEWaybillOrderEntityDao ewaybillOrderEntityDao) {
		this.ewaybillOrderEntityDao = ewaybillOrderEntityDao;
	}

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
	 */
	private List<FailOrderDto> processOrderHandleList(List<OrderHandleDto> orderHandleDtos) {
		// 失败订单集合
		List<FailOrderDto> failOrderDtos = new ArrayList<FailOrderDto>();
		//获取订单业务锁自动释放时间
		String orderLockTtl = configurationParamsService.queryConfValueByCode(DispatchOrderStatusConstants.ORDER_LOCK_TTL);
		
		for (OrderHandleDto orderHandleDto : orderHandleDtos) {
			LOGGER.info("人工受理订单"+orderHandleDto.getOrderNo()+"开始");
			OrderMutexElement mutexElement = null;
			if (StringUtils.isBlank(orderLockTtl)) {
				mutexElement = new OrderMutexElement(orderHandleDto.getOrderNo(), "订单更新_手动调度", 
						OrderMutexElementConstants.DISPATCHORDERNO_LOCK);
				LOGGER.info("人工受理订单"+orderHandleDto.getOrderNo()+"时订单业务锁自动释放时间采用默认值");
			} else {
				mutexElement = new OrderMutexElement(orderHandleDto.getOrderNo(), "订单更新_手动调度", 
						OrderMutexElementConstants.DISPATCHORDERNO_LOCK,Integer.valueOf(orderLockTtl));
			}
			
			//MutexElement mutexElement = new MutexElement(orderHandleDto.getOrderNo(), "订单受理", MutexElementType.DISPATCHORDER_NO);
			try {
				//互斥锁定
				boolean isLocked = orderBusinessLockService.lock(mutexElement, DispatchOrderStatusConstants.ZERO);
				if(!isLocked){
		     		FailOrderDto failOrder = new FailOrderDto(orderHandleDto.getOrderNo(), 
							DispatchOrderStatusConstants.FAILURE_LOCK, 
							messageBundle.getMessage(UserContext.getUserLocale(),
									ActionMessageType.ORDER_LOCK, new Object[0]));
		     		failOrderDtos.add(failOrder);
		     		LOGGER.info("人工受理订单"+orderHandleDto.getOrderNo()+"时订单已锁定");
					continue;
				}
				/*boolean isLocked = businessLockService.lock(mutexElement, ZERO);
				//如果没有上锁
				if(!isLocked){
					// 添加失败详细信息
					FailOrderDto failOrder = new FailOrderDto(orderHandleDto.getOrderNo(), "已知异常", "订单已锁定");
					failOrderDtos.add(failOrder);
					LOGGER.info("人工受理订单"+orderHandleDto.getOrderNo()+"时订单已锁定");
					continue;
				}*/
				try {
					//判断接货订单在CRM中是否存在
					/*LOGGER.info("人工受理订单"+orderHandleDto.getOrderNo()+"判断在CRM中是否存在");
					if (DispatchOrderStatusConstants.TYPE_PICKUP_ORDER.equals(orderHandleDto.getOrderType())
							&& crmOrderService.searchOrder(orderHandleDto.getOrderNo()) == false) {
						LOGGER.info("人工受理订单"+orderHandleDto.getOrderNo()+"时在CRM中不存在");
						throw new DispatchException("订单在CRM中不存在");
					}*/
					// 设置状态为已派车
					orderHandleDto.setOrderStatus(DispatchOrderStatusConstants.STATUS_DISPATCH_VEHICLE);
					// 发送中
					orderHandleDto.setOrderSendStatus(DispatchOrderStatusConstants.ORDER_SENDING);
					//14.7.30 gcl AUTO-212 设置订单受理方式为人工
					orderHandleDto.setAcceptStatus(DispatchOrderStatusConstants.ORDER_ACCEPT_STATUS_HANDLE);
					//14.8.22 gcl DEFECT-3958
					if(StringUtils.isEmpty(orderHandleDto.getDriverMobile())){
						OwnDriverEntity ownDriver=new OwnDriverEntity();
						ownDriver.setEmpCode(orderHandleDto.getDriverCode());
						List<OwnDriverEntity> dlist=ownDriverDao.queryOwnDriverListBySelectiveCondition(ownDriver, NumberConstants.NUMERAL_ZORE, NumberConstants.NUMERAL_ONE);
						if(dlist!=null&&dlist.size()>0){
							orderHandleDto.setDriverMobile(dlist.get(0).getEmpPhone());
						}
					}
					//如果工号为空  说明是外请车  设置默认工号000000 add by 329757
					if("".equals(orderHandleDto.getDriverCode()) || orderHandleDto.getDriverCode()==null){
						orderHandleDto.setDriverCode("000000");
					}
					// 初始化订单的操作人、操作部门、状态
					initOrderHandle(orderHandleDto);
					int count = 0;
					//14.7.29 gcl AUTO-212 人工受理时 不更新预处理小区 只更新实际接货小区
					OrderHandleDto orderhandledto=new OrderHandleDto();
					BeanUtils.copyProperties(orderhandledto, orderHandleDto);
					orderhandledto.setPickupRegionCode("");
					orderhandledto.setPickupRegionName("");
					count = dispatchOrderEntityDao.updateByOrderHandle(orderhandledto);
					if (count == 1) {
						// 添加订单操作历史
						addDispatchOrderActionHistory(orderHandleDto);
						// 添加派车记录
						addDispatchVehicleRecord(orderHandleDto);
						if (!ProductEntityConstants.EXPRESS_PRODUCT_CODE_LIST.contains(orderHandleDto.getProductCode())) {
							addVehicleWV(orderHandleDto);
						}
						//如果为零担电子运单转货订单，调用新的短信模板
						if(DispatchOrderStatusConstants.TYPE_EWAYBILL_TRANSFER_ORDER.equals(orderHandleDto.getOrderType())){
							LOGGER.info("人工受理零担电子订单"+orderHandleDto.getOrderNo()+"发短信开始");
							try {
								sendNetworkWaybillDispatchSms(orderHandleDto);
							} catch (Exception e) {
								LOGGER.info("人工受理零担电子订单"+orderHandleDto.getOrderNo()+"时短信发送失败");
								throw new DispatchException("零担电子订单短信发送失败");
							}
							LOGGER.info("人工受理零担电子订单"+orderHandleDto.getOrderNo()+"发短信开始");
						} else {
							// 获得订单对应的运输产品类型
							DispatchOrderConditionDto dto = new DispatchOrderConditionDto();
							dto.setOrderNo(orderHandleDto.getOrderNo());	
							DispatchOrderEntity dispatchOrder = dispatchOrderEntityDao.queryOrdersByOrderNo(dto);
							if(StringUtils.isNotEmpty(dispatchOrder.getProductCode())){
								orderHandleDto.setProductCode(dispatchOrder.getProductCode());
							}
							
							 // 发送短信
							LOGGER.info("人工受理订单"+orderHandleDto.getOrderNo()+"发短信开始");
							if(FossConstants.ACTIVE.equals(
								this.configurationParamsService.queryConfValueByCode(
										ConfigurationParamsConstants.STL_ORDER_MESSAGE_SWITCH))){
								try {
									sendSmsByHandle(failOrderDtos, orderHandleDto);
								} catch (Exception e) {
									e.printStackTrace();
									LOGGER.info("人工受理订单"+orderHandleDto.getOrderNo()+"时短信发送失败");
									throw new DispatchException("短信发送失败");
								}
							}else {
								LOGGER.info("人工受理订单"+orderHandleDto.getOrderNo()+"时短信功能关闭");
							}
							LOGGER.info("人工受理订单"+orderHandleDto.getOrderNo()+"发短信结束");
							

							//判断车辆是否是公司的车辆
							String queryVehicleType = leasedVehicleService.queryVehicleType(orderHandleDto.getVehicleNo());
							// 同步约车信息给GPS
							if(queryVehicleType != null && !ORDER_RESOURCE_LEASEDVENHICLE.equals(queryVehicleType)) {
								// 是公司的车辆就走下面的逻辑(外请车不需要同步约车信息给GPS)
								try{
									LOGGER.info("人工受理订单"+orderHandleDto.getOrderNo()+"同步约车信息给GPS开始");
									sendToGPS(orderHandleDto,1);
								}catch(Exception e){
									e.printStackTrace();
									LOGGER.info("人工受理订单"+orderHandleDto.getOrderNo()+"时同步约车信息给GPS失败");
									throw new DispatchException("同步约车信息给GPS失败");
								}
								LOGGER.info("人工受理订单"+orderHandleDto.getOrderNo()+"同步约车信息给GPS结束");
							}
							
							//推送订单信息给APP
							try{
								LOGGER.info("人工受理订单"+orderHandleDto.getOrderNo()+"推送订单信息给APP开始");
								sendEmpCodeToApp(orderHandleDto);
							}catch(Exception e){
								e.printStackTrace();
								LOGGER.info("人工受理订单"+orderHandleDto.getOrderNo()+"时推送订单信息给APP失败");
								throw new DispatchException("推送订单信息给APP失败");
							}
							LOGGER.info("人工受理订单"+orderHandleDto.getOrderNo()+"推送订单信息给APP结束");
							
							//推送已派车状态给官网
							try{
								LOGGER.info("人工受理订单"+orderHandleDto.getOrderNo()+"推送已派车状态给官网开始");
								sendOrderStateToApp(orderHandleDto);
							}catch(Exception e){
								e.printStackTrace();
								LOGGER.info("人工受理订单"+orderHandleDto.getOrderNo()+"时推送订单信息给微信失败");
								throw new DispatchException("推送订单信息给微信失败");
							}
							LOGGER.info("人工受理订单"+orderHandleDto.getOrderNo()+"推送已派车状态给官网结束");
						}
						
						// 更新外部系统状态
						try{
							LOGGER.info("人工受理订单"+orderHandleDto.getOrderNo()+"更新外部系统开始");
							updateExternalSystem(orderHandleDto, null);
						}catch(Exception e){
							LOGGER.info("人工受理订单"+orderHandleDto.getOrderNo()+"时更新外部系统失败");
							throw new DispatchException("更新外部系统失败");
						}
						LOGGER.info("人工受理订单"+orderHandleDto.getOrderNo()+"更新外部系统结束");
						
						try {
							// 发送在线通知给营业部
							LOGGER.info("人工受理订单"+orderHandleDto.getOrderNo()+"在线通知营业部开始");
							addNotice(orderHandleDto.getSalesDepartmentCode(), getNotice(orderHandleDto));
						} catch (BusinessException e) {
							LOGGER.info("人工受理订单"+orderHandleDto.getOrderNo()+"时在线通知营业部失败");
							throw new DispatchException("在线通知营业部失败");
						}
						LOGGER.info("人工受理订单"+orderHandleDto.getOrderNo()+"在线通知营业部结束");
						
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
						LOGGER.info("人工受理订单"+orderHandleDto.getOrderNo()+"时更新调度订单失败");
						throw new DispatchException("更新调度订单失败");
					}
				}catch (DispatchException ex) {
					ex.printStackTrace();
					FailOrderDto failOrder = new FailOrderDto(orderHandleDto.getOrderNo(), "已知异常", ex.getErrorCode());
					failOrderDtos.add(failOrder);
				}
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.error(e.getMessage(), e);
				FailOrderDto failOrder = new FailOrderDto(orderHandleDto.getOrderNo(), "未知异常", "未知异常");
				failOrderDtos.add(failOrder);
			} finally {
				//解锁
				orderBusinessLockService.unlock(mutexElement);
				//businessLockService.unlock(mutexElement);
				LOGGER.info("人工受理订单"+orderHandleDto.getOrderNo()+"结束");
			}
		}
		return failOrderDtos;
	}
	
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
		//add by 329757 增加车辆车牌号属性
		appOrderDto.setVehicleNo(orderHandleDto.getVehicleNo());
		appOrderJMSService.sendOrderState(appOrderDto);
	}
	
	/**
	 * 零担&人工调度 发送订单状态（已派车）到app DMANA-8883
	 * 
	 * @author 219396-foss-daolin
	 * @date 2014-12-05 上午11:10:49
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
		// 司机名称
		request.setDriverName(orderHandleDto.getDriverName());
		// 司机电话
		request.setDriverTel(orderHandleDto.getDriverMobile());
		// 车牌号
		request.setCarNumber(orderHandleDto.getVehicleNo());
		// 出发城市
		request.setLeaveCity(orderHandleDto.getPickupCity());
		// 接单时间
		String owsPickupTime = orderHandleDto.getOwsPickupTime();
		if(owsPickupTime.startsWith("1970")) {
			owsPickupTime = owsPickupTime.substring(SettlementReportNumber.SEVENTEEN);
		}
		request.setOrderTime(owsPickupTime);
		
		appOrderJMSService.sendOrderStateToApp(request);
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
				// 接货和转货订单短信模板不同
				if (DispatchOrderStatusConstants.TYPE_PICKUP_ORDER.equals(orderHandleDto.getOrderType())) {
					DispatchOrderEntity dispatchOrder = dispatchOrderEntityDao
							.queryAllInfoByOrderNo(orderHandleDto.getOrderNo());
					String smsCodeForDriver = DispatchOrderStatusConstants.TEMPLATE_PICKUP_ORDER_TO_DRIVER_SMS;
					if(StringUtils.isNotBlank(dispatchOrder.getDeliveryCustomerCode())){
						CustomerEntity customer=new CustomerEntity();
						customer.setCusCode(dispatchOrder.getDeliveryCustomerCode());
						CustomerEntity toCustomer=customerService.queryCustInfoByCustomerEntity(customer);
						if(toCustomer!=null&&FossConstants.CUSTOMER_CONTRABAND.equals(toCustomer.getBlackListCategory())) {
							smsCodeForDriver = DispatchOrderStatusConstants.LESS_CARLOAD_FREIGHT_BLACKLIST_SMS;
						}
					}
					sendSms(smsCodeForDriver, orderHandleDto, true, NotifyCustomerConstants.BS_TYPE_PKP_ORDER);
				} else {
					sendSms(DispatchOrderStatusConstants.TEMPLATE_TRANSFER_ORDER_TO_DRIVER_SMS, orderHandleDto, true,NotifyCustomerConstants.BS_TYPE_PKP_ORDER);
				}
			}
		} 
		catch (NotifyCustomerException e) {
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
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 添加失败信息
			FailOrderDto failOrder = new FailOrderDto(orderHandleDto.getOrderNo(), DispatchOrderStatusConstants.FAILURE_NOTICE, messageBundle.getMessage(UserContext.getUserLocale(),
					ActionMessageType.DRIVER_SMS_SENDFAILURE, new Object[0]));
			failOrderDtos.add(failOrder);
			flag = false;
			throw new DispatchException("发送短信异常");
		}
		try {
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
				// 零担
				sendSms(DispatchOrderStatusConstants.TEMPLATE_ORDER_TO_CUSTOMER_SMS, orderHandleDto, false,NotifyCustomerConstants.BS_TYPE_PKP_ORDER);
			}
		} catch (NotifyCustomerException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (SMSSendLogException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return flag;
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
		if (DispatchOrderStatusConstants.TYPE_PICKUP_ORDER.equals(orderHandleDto.getOrderType())
				|| DispatchOrderStatusConstants.TYPE_EWAYBILL_TRANSFER_ORDER.equals(orderHandleDto.getOrderType())) {
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
		crmModifyInfoDto.setDriverName(orderHandleDto.getDriverName());
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
		if (dispatchOrderEntity != null && 
				ProductEntityConstants.EXPRESS_PRODUCT_CODE_LIST.contains(dispatchOrderEntity.getProductCode())) {
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
		}
		//重量体积
		crmModifyInfoDto.setWeight(orderHandleDto.getWeight()==null ? 0 : orderHandleDto.getWeight().doubleValue());
		crmModifyInfoDto.setVolume(orderHandleDto.getVolume()==null ? 0 : orderHandleDto.getVolume().doubleValue());
		//运单号
		crmModifyInfoDto.setWaybillNumber(orderHandleDto.getWaybillNo());
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
	 * @date 2012-11-30 上午10:52:01 231440
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
		// 司机手机
		vehicleRecordEntity.setDriverMobile(orderHandleDto.getDriverMobile());
		// 派车时间
		vehicleRecordEntity.setDeliverTime(orderHandleDto.getOperateTime());
		// 是否PDA
		vehicleRecordEntity.setPdaStatus(FossConstants.YES.equals(orderHandleDto.getIsPda()) ? DispatchOrderStatusConstants.ISPDA_NORMAL : DispatchOrderStatusConstants.ISPDA_NO);
		// 定人定区Code
		vehicleRecordEntity.setPickupRegionCode(orderHandleDto.getPickupRegionCode());
		// 定人定区名称
		vehicleRecordEntity.setPickupRegionName(orderHandleDto.getPickupRegionName());
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
			// 约车订单受理之后，将约车发送GPS，并设置操作状态为删除
			sendToGPS(orderHandleDto,SettlementReportNumber.THREE);
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
	 * PDA订单开单更新状态
	 * @param orderHandleDto
	 * @param dto
	 */
	@Override
	public void pdaOrderWaybill(OrderHandleDto orderHandleDto,CrmModifyInfoDto dto){
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
			// 更新CRM订单状态
			crmOrderJMSService.sendModifyOrder(dto);
		}
	}

	/**
	 * 图片开单订单开单更新.
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
	 * 
	 * @date 2013-2-2 上午11:22:48
	 */
	@Override
	public void orderPictureWaybill(OrderHandleDto orderHandleDto) {
		if (orderHandleDto == null || StringUtils.isEmpty(orderHandleDto.getOrderNo())) {
			return;
		}
		// 根据订单号查询对应的订单
		/**
		 * @author Foss-278328-hujinyang 20160418 优化
		 */
		if(orderHandleDto.getId()==null || "".equals(orderHandleDto.getId())){
			DispatchOrderEntity dispatchOrder = dispatchOrderEntityDao.queryOrderByOrderNo(orderHandleDto.getOrderNo());
			if (dispatchOrder == null) {
				return;
			}
			orderHandleDto.setId(dispatchOrder.getId());
		}
		
		if(orderHandleDto.getOperator() == null || "".equals(orderHandleDto.getOperator())){
			EmployeeEntity employeeEntity = employeeService.queryEmployeeByEmpCode(orderHandleDto.getOperatorCode());
			if (employeeEntity != null) {
				orderHandleDto.setOperator(employeeEntity.getEmpName());
			}
		}
		
		/**
		 * @author Foss-278328-hujinyang 20160418 优化
		 */
		if(orderHandleDto.getOperateOrgName() ==null || "".equals(orderHandleDto.getOperateOrgName())){
			OrgAdministrativeInfoEntity administrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(orderHandleDto.getOperateOrgCode());
			if (administrativeInfoEntity != null) {
				orderHandleDto.setOperateOrgName(administrativeInfoEntity.getName());
			}
		}
		
		int count = dispatchOrderEntityDao.updateByOrderHandle(orderHandleDto);
		if (count == 1) {
			// 更新派车记录
			updateDispatchVehicleRecord(orderHandleDto);
			// 添加订单操作历史
			addDispatchOrderActionHistory(orderHandleDto);
			// 更新外部系统状态
			//updateExternalSystem(orderHandleDto, null);
		}
	}
	
	
	
	@Override
	public boolean addOmsPickupOrder(AsynOmsGoodsBillReceiveRequest payload) {
		LOGGER.info("addOmsPickupOrder start : " + ReflectionToStringBuilder.toString(payload));
		DispatchOrderEntity dispatchOrderEntity = new DispatchOrderEntity();
		String id = UUIDUtils.getUUID();
		SaleDepartmentEntity saleDepartment = null;
		Date createTime = new Date();
		if (!ProductEntityConstants.EXPRESS_PRODUCT_CODE_LIST.contains(payload.getProductCode())) {
			//零担
			// 根据code查询营业部
			saleDepartment = saleDepartmentService.querySaleDepartmentByCode(payload.getSalesDepartmentCode() != null ? payload.getSalesDepartmentCode() : null);
			if (saleDepartment == null) {
				throw new DispatchException("查询不到对应的营业部，编码：" + payload.getSalesDepartmentCode());
			}
			// 订单所属营业部
			dispatchOrderEntity.setSalesDepartmentCode(saleDepartment.getCode());
			// 订单所属营业部名称
			dispatchOrderEntity.setSalesDepartmentName(saleDepartment.getName());
			
			// 派车车队
			OrgAdministrativeInfoEntity fleet = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(payload.getFleetCode());
			if(fleet == null){
				throw new DispatchException("查询不到对应的车队，编码：" + payload.getFleetCode());
			}
			// 派车车队编码
			dispatchOrderEntity.setFleetCode(fleet.getCode());
			
			OrgAdministrativeInfoEntity orderVehicleOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(payload.getOrderVehicleOrgCode());
			// 约车部门
			dispatchOrderEntity.setOrderVehicleOrgCode(orderVehicleOrg != null ? orderVehicleOrg.getCode() : "");
			// 约车部门名称
			dispatchOrderEntity.setOrderVehicleOrgName(orderVehicleOrg != null ? orderVehicleOrg.getName() : "");
		} else {
			// 订单所属营业部
			dispatchOrderEntity.setSalesDepartmentCode(DispatchOrderStatusConstants.DB_DEFAULT_STRING);
			// 订单所属营业部名称
			dispatchOrderEntity.setSalesDepartmentName(DispatchOrderStatusConstants.DB_DEFAULT_STRING);
		}
		
		dispatchOrderEntity.setId(id);
		dispatchOrderEntity.setOrderNo(payload.getOrderNo());
		// 提货网点
		dispatchOrderEntity.setCustomerPickupOrgCode(payload.getCustomerPickupOrgCode());
		dispatchOrderEntity.setOrderType(payload.getOrderType());
		dispatchOrderEntity.setVehicleType(payload.getVehicleType());
		dispatchOrderEntity.setWeight(BigDecimal.valueOf(payload.getWeight()));
		dispatchOrderEntity.setVolume(BigDecimal.valueOf(payload.getVolume()));
		dispatchOrderEntity.setPickupProvince(payload.getPickupProvince());
		dispatchOrderEntity.setPickupCity(payload.getPickupCity());
		dispatchOrderEntity.setPickupCounty(payload.getPickupCounty());
		dispatchOrderEntity.setPickupElseAddress(payload.getPickupElseAddress());
		dispatchOrderEntity.setLatestPickupTime(payload.getLatestPickupTime());
		dispatchOrderEntity.setCustomerName(payload.getCustomerName());
		dispatchOrderEntity.setTel(payload.getTel());
		dispatchOrderEntity.setMobile(payload.getMobile());
		dispatchOrderEntity.setGoodsQty(payload.getGoodsQty());
		dispatchOrderEntity.setOrderNotes(payload.getOrderNotes());
		dispatchOrderEntity.setDriverName(payload.getDriverName());
		dispatchOrderEntity.setDriverCode(payload.getDriverCode());
		dispatchOrderEntity.setVehicleNo(payload.getVehicleNo());
		dispatchOrderEntity.setDeviceNo(payload.getDeviceNo());
		dispatchOrderEntity.setOrderStatus(payload.getOrderStatus());
		dispatchOrderEntity.setOperateNotes(payload.getOperateNotes());
		dispatchOrderEntity.setOperator(payload.getOperator());
		dispatchOrderEntity.setOperatorCode(payload.getOperatorCode());
		dispatchOrderEntity.setOperateOrgName(payload.getOperateOrgName());
		dispatchOrderEntity.setOperateOrgCode(payload.getOperateOrgCode());
		dispatchOrderEntity.setOperateTime(payload.getOperateTime());
		dispatchOrderEntity.setPreprocessId(payload.getPreprocessId());
		dispatchOrderEntity.setVehicleNoSuggested(payload.getVehicleNoSuggested());
		dispatchOrderEntity.setDriverCodeSuggested(payload.getDriverCodeSuggested());
		dispatchOrderEntity.setProductCode(payload.getProductCode());
		dispatchOrderEntity.setIsPda(payload.getIsPda());
		dispatchOrderEntity.setOrderSendStatus(payload.getOrderSendStatus());
		//约车时间赋值foss当前系统时间
		dispatchOrderEntity.setOrderVehicleTime(new Date());
		dispatchOrderEntity.setEarliestPickupTime(payload.getEarliestPickupTime());
		dispatchOrderEntity.setGoodsName(payload.getGoodsName());
		dispatchOrderEntity.setGoodsType(payload.getGoodsType());
		dispatchOrderEntity.setGoodsPackage(payload.getGoodsPackage());
		dispatchOrderEntity.setIsSms(payload.getIsSms());
		dispatchOrderEntity.setIsCustomer(payload.getIsCustomer());
		dispatchOrderEntity.setOrderTime(payload.getOrderTime());
		// 创建人工号
		dispatchOrderEntity.setCreateUserCode(payload.getCreateUserCode());
		EmployeeEntity creator = employeeService.queryEmployeeByEmpCode(payload.getCreateUserCode());
		// 创建人名称
		dispatchOrderEntity.setCreateUserName(creator != null ? creator.getEmpName() : "");
		
		dispatchOrderEntity.setConsigneeAddress(payload.getConsigneeAddress());

		/**
		 * 要求把OMS提货方式转成foss中的
		 */
		if("SELF_PICKUP".equals(payload.getReceiveMethod()))
		{
			payload.setReceiveMethod("SELF_PICKUP_AIR");
		}else if("FREE_PICKUP".equals(payload.getReceiveMethod())){
			payload.setReceiveMethod("SELF_PICKUP_FREE_AIR");
		}else if("FREE_DELIVERY".equals(payload.getReceiveMethod())){
			payload.setReceiveMethod("DELIVER");
		}else if("DELIVERY_STOCK".equals(payload.getReceiveMethod())){
			payload.setReceiveMethod("DELIVER_INGA");
		}else if("DELIVERY_HOME".equals(payload.getReceiveMethod())){
			payload.setReceiveMethod("DELIVER_UP");
		}else if("DELIVERY".equals(payload.getReceiveMethod())){
			payload.setReceiveMethod("DELIVER_NOUP");
		}else if("PICKUP".equals(payload.getReceiveMethod())){
			payload.setReceiveMethod("SELF_PICKUP");
		}else if("".equals(payload.getReceiveMethod()) || payload.getReceiveMethod() == null){
			payload.setReceiveMethod("");
		}
		dispatchOrderEntity.setReceiveMethod(payload.getReceiveMethod());
		
		dispatchOrderEntity.setOrderSource(payload.getOrderSource());
		dispatchOrderEntity.setPickupProvinceCode(payload.getPickupProvinceCode());
		dispatchOrderEntity.setPickupCityCode(payload.getPickupCityCode());
		dispatchOrderEntity.setPickupCountyCode(payload.getPickupCountyCode());
		dispatchOrderEntity.setWaybillNo(payload.getWaybillNo());
		dispatchOrderEntity.setReceiveOrgCode(payload.getReceiveOrgCode());
		dispatchOrderEntity.setReceiveOrgName(payload.getReceiveOrgName());
		dispatchOrderEntity.setPaidMethod(payload.getPaidMethod());
		dispatchOrderEntity.setSmallZoneCodeSuggested(payload.getSmallZoneCodeSuggested());
		dispatchOrderEntity.setSmallZoneNameSuggested(payload.getSmallZoneNameSuggested());
		dispatchOrderEntity.setSmallZoneCodeActual(payload.getSmallZoneCodeActual());
		dispatchOrderEntity.setIsCollect(payload.getIsCollect());
		//代收货款退款类型，CRM传递明文，需要转化为FOSS的编码
		if(CrmRefundTypeEnum.NORMAL.getCrmCode().equals(payload.getReciveLoanType())){
			dispatchOrderEntity.setReciveLoanType(WaybillConstants.REFUND_TYPE_THREE_DAY);
		}else if(CrmRefundTypeEnum.INTRADAY.getCrmCode().equals(payload.getReciveLoanType())){
			dispatchOrderEntity.setReciveLoanType(WaybillConstants.REFUND_TYPE_ONE_DAY);
		}else{
			dispatchOrderEntity.setReciveLoanType(null);
		}
		dispatchOrderEntity.setReviceMoneyAmount(payload.getReviceMoneyAmount());
		dispatchOrderEntity.setInsuredAmount(payload.getInsuredAmount());
		dispatchOrderEntity.setCouponNumber(payload.getCouponNumber());
		dispatchOrderEntity.setAcceptStatus(payload.getAcceptStatus());
		dispatchOrderEntity.setWaybillType(payload.getWaybillType());
		dispatchOrderEntity.setDeliveryCustomerCode(payload.getDeliveryCustomerCode());
		//是否是电子运单大客户
		dispatchOrderEntity.setIsEWaybillBigCustomer(WaybillConstants.YES.equals(payload.getIsEWaybillBigCustomer()) ? "Y":"N" );
		dispatchOrderEntity.setCodRefundAccount(payload.getCodRefundAccount());
		dispatchOrderEntity.setCodRefundAccountName(payload.getCodRefundAccountName());
		dispatchOrderEntity.setGoodsSize(payload.getGoodsSize());
		dispatchOrderEntity.setReceiveCustomerAddressNote(payload.getReceiveCustomerAddressNote());
		dispatchOrderEntity.setDeliveryCustomerAddressNote(payload.getDeliveryCustomerAddressNote());
		dispatchOrderEntity.setConsigneeProvince(payload.getConsigneeProvince());
		dispatchOrderEntity.setConsigneeCity(payload.getConsigneeCity());
		dispatchOrderEntity.setConsigneeCounty(payload.getConsigneeCounty());
		dispatchOrderEntity.setConsigneeDetailAddress(payload.getConsigneeDetailAddress());
		dispatchOrderEntity.setTeanLimit(payload.getTeanLimit());
		dispatchOrderEntity.setProcurementNumber(payload.getProcurementNumber());
		dispatchOrderEntity.setChannelNumber(payload.getChannelNumber());
		dispatchOrderEntity.setSenderCode(payload.getSenderCode());
		dispatchOrderEntity.setPaymentOrgCode(payload.getPaymentOrgCode());
		dispatchOrderEntity.setReceiverCustName(payload.getReceiverCustName());
		dispatchOrderEntity.setReceiverCustPhone(payload.getReceiverCustPhone());
		dispatchOrderEntity.setReceiverCustMobile(payload.getReceiverCustMobile());
		//客户分群
		if(StringUtil.isNotBlank(dispatchOrderEntity.getDeliveryCustomerCode())) {
			CustomerEntity customerEntity = customerDao.queryCustStateByCode(dispatchOrderEntity.getDeliveryCustomerCode());
			if(customerEntity!=null&&StringUtil.isNotBlank(customerEntity.getFlabelleavemonth())) {
				dispatchOrderEntity.setCustomerGroup(DictUtil.rendererSubmitToDisplay(customerEntity.getFlabelleavemonth(), DictionaryConstants.CRM_CUSTOMER_GROUP));
			}
		}
		dispatchOrderEntity.setConsigneeVillageCode(payload.getConsigneeVillageCode());
		dispatchOrderEntity.setConsigneeVillage(payload.getConsigneeVillage());
		dispatchOrderEntity.setOtherFee(payload.getOtherFee());
		dispatchOrderEntity.setIsPicPackage(payload.getIsPicPackage());
		dispatchOrderEntity.setOriginalNumber(payload.getOriginalNumber());
		dispatchOrderEntity.setServiceType(payload.getServiceType());
		dispatchOrderEntity.setGgOrderType(payload.getGgOrderType());
		dispatchOrderEntity.setGotInTime(payload.getGotInTime());
		dispatchOrderEntity.setInflowTime(payload.getInflowTime());
		dispatchOrderEntity.setPickupManId(payload.getPickupManId());
		dispatchOrderEntity.setRefundMethod(payload.getRefundMethod());
		dispatchOrderEntity.setCnWd(payload.getCnWd());
		dispatchOrderEntity.setServiceFlag(payload.getServiceFlag());
		dispatchOrderEntity.setScheduleType(payload.getScheduleType());
		dispatchOrderEntity.setAppointTime(payload.getAppointTime());
		dispatchOrderEntity.setDriverPhone(payload.getDriverPhone());
		dispatchOrderEntity.setAppointType(payload.getAppointType());
		
		//foss灰度流量到RPS 2017年3月16日14:51:45 qzq
		dispatchOrderEntity=this.fossHdRps(dispatchOrderEntity,createTime);
		
		// 按照订单号进行查询
		DispatchOrderConditionDto dispatchOrderCondition = new DispatchOrderConditionDto();
		dispatchOrderCondition.setOrderNo(payload.getOrderNo());
		DispatchOrderEntity dispatchOrder = dispatchOrderEntityDao.queryOrdersByOrderNo(dispatchOrderCondition);
		if (dispatchOrder != null) {
			// 如果存在，现有逻辑是直接删除FOSS订单
			dispatchOrderEntityDao.deleteDispatchOrderForNo(dispatchOrderCondition);
			LOGGER.info(dispatchOrderCondition.getOrderNo() + "删除成功。"); 
			dispatchOrderEntityDao.insertSelective(dispatchOrderEntity);
			// 将修改前后字段值插入数据库(更新日志)
			ModifyEwaybillOrderLogEntity logEntity = new ModifyEwaybillOrderLogEntity();
			logEntity.setId(UUIDUtils.getUUID());
			logEntity.setOrderNo(dispatchOrder.getOrderNo());
			logEntity.setWaybillNo(dispatchOrder.getWaybillNo());
			logEntity.setOperateType(FossConstants.OPERATE_DELETE);
			logEntity.setIsSuccess(FossConstants.YES);
			logEntity.setOperateRecord("OMS约车时，订单已存在，进行删除"+dispatchOrder.toString());
			logEntity.setCreateTime(createTime);
			modifEwaybillOrderRecordDao.insertSelective(logEntity);		
			}else {
				dispatchOrderEntityDao.insertSelective(dispatchOrderEntity);
			}
		
		//如果为零担电子运单转货订单，不走自动调度，不走预处理
		if(DispatchOrderStatusConstants.TYPE_EWAYBILL_TRANSFER_ORDER.equals(dispatchOrderEntity.getOrderType())){
			return true;
		}
		//零担
		if (!ProductEntityConstants.EXPRESS_PRODUCT_CODE_LIST.contains(payload.getProductCode())) {

			/**
			 * 构造UserEntity
			 */
			UserEntity user = new UserEntity();
			// 设置编码
			user.setEmpCode(payload.getCreateUserCode());
			// 设置雇员名称
			user.setEmpName(creator != null ? creator.getEmpName() : "");
			// 设置用户名称
			user.setUserName(creator != null ? creator.getEmpName() : "");
			/**
			 * 构造EmployeeEntity
			 */
			EmployeeEntity employeeEntity = new EmployeeEntity();
			// 设置雇员名称
			employeeEntity.setEmpCode(payload.getCreateUserCode());
			// 设置用户名称
			employeeEntity.setEmpName(creator != null ? creator.getEmpName() : "");
			user.setEmployee(employeeEntity);
			/**
			 * 构造部门实体
			 */
			OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
			
			if (saleDepartment != null) {
				// 设置编码
				dept.setCode(saleDepartment.getCode());
				// 设置名称
				dept.setName(saleDepartment.getName());
				// 判断营业部是否集中接送货部门,集中接送货部门才进行预处理
				if (FossConstants.YES.equals(saleDepartment.getInCentralizedShuttle())) {
					LOGGER.info("===========符合预处理条件==================");
					// 变更表记录
					DispatchOrderChangeEntity dispatchOrderChangeEntity = new DispatchOrderChangeEntity();
					// id
					dispatchOrderChangeEntity.setId(UUIDUtils.getUUID());
					//219396-20150202 将运输性质为DTD、YTY的订单JOB_ID设为“B”，不走自动调度、直接预处理
					if (StringUtils
							.isNotBlank(dispatchOrderEntity.getProductCode())
							&& (dispatchOrderEntity
									.getProductCode()
									.equals(ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR) || dispatchOrderEntity
									.getProductCode()
									.equals(ProductEntityConstants.PRICING_PRODUCT_YARD_TO_YARD))) {
						dispatchOrderChangeEntity.setJobId("B");
					}else {
						dispatchOrderChangeEntity.setJobId(DispatchOrderStatusConstants.CHANGE_ORDER_NO_QUERY);
					}
					// 变更id
					dispatchOrderChangeEntity.setChangeId(id);
					// 变更时间
					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd"); 
					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String todayString = sdf1.format(new Date());
					String pickupTimeString = sdf1.format(dispatchOrderEntity.getEarliestPickupTime());
					if(todayString.equals(pickupTimeString)) {
						dispatchOrderChangeEntity.setChangeTime(createTime);
					}else {
						//确保非当日订单在9点后自动受理
						try {
							dispatchOrderChangeEntity.setChangeTime(sdf2.parse(pickupTimeString+" 08:03:00"));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					// 设置接货城市
					dispatchOrderChangeEntity.setCityCode(dispatchOrderEntity.getPickupCityCode());
					// 设置产品类型
					dispatchOrderChangeEntity.setProductCode(dispatchOrderEntity.getProductCode());
					//设置最早接货时间
					dispatchOrderChangeEntity.setEarliestPickupTime(dispatchOrderEntity.getEarliestPickupTime()==null?new Date():dispatchOrderEntity.getEarliestPickupTime());
					//判断订单是否由foss处理  ----自动调度  2017年3月16日15:03:46 qzq
					if(DispatchOrderStatusConstants.HD_RULE_FOSS.equals(dispatchOrderEntity.getSysGray())){
						dispatchOrderChangeEntityDao.insertChange(dispatchOrderChangeEntity);
					}
					// 订单分配总量
					businessMonitorService.counter(BusinessMonitorIndicator.ORDER_AUTO_ASSIGN_COUNT, new CurrentInfo(user, dept));
				}
				// 订单总量
				businessMonitorService.counter(BusinessMonitorIndicator.ORDER_TOTAL_COUNT, new CurrentInfo(user, dept));
			}
		} else {
			if(WaybillConstants.EWAYBILL.equals(dispatchOrderEntity.getWaybillType())){
				//进行电子运单预处理数据的处理
				PreHandEWaybillOrderEntity record = new PreHandEWaybillOrderEntity();
				record.setId(UUIDUtils.getUUID());
				record.setCreateTime(new Date());
				record.setModifyTime(new Date());
				record.setOrderNo(dispatchOrderEntity.getOrderNo());
				record.setWaybillNo(dispatchOrderEntity.getWaybillNo());
				record.setJobId(WaybillConstants.UNKNOWN);
				record.setExceptionMsg(FossConstants.NO);
				record.setWaybillType(WaybillConstants.EWAYBILL);
				preHandEWaybillOrderDao.insertPreEWaybillOrderSelective(record);
				}
		}
		return true;
	}
	
	/**
	 * 2017年3月17日15:32:52 qzq
	 * foss灰度流量到RPS 
	 * @param dispatchOrderEntity 
	 * @param createTime 创建时间
	 * @return
	 */
	private DispatchOrderEntity fossHdRps(DispatchOrderEntity dispatchOrderEntity,Date createTime){
		String orderStatus=dispatchOrderEntity.getOrderStatus();
		boolean IS_TRANSFER_ORDER=DispatchOrderStatusConstants.TYPE_TRANSFER_ORDER.equals(dispatchOrderEntity.getOrderType()) ;// 转货订单
		boolean IS_EWAYBILL_ORDER= DispatchOrderStatusConstants.TYPE_EWAYBILL_TRANSFER_ORDER.equals(dispatchOrderEntity.getOrderType());//零担电子运单转货运单
		boolean IS_DOOR_TO_DOOR=ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR.equals(dispatchOrderEntity.getProductCode());//三级产品 门到门
		boolean IS_YARD_TO_YARD=ProductEntityConstants.PRICING_PRODUCT_YARD_TO_YARD.equals(dispatchOrderEntity.getProductCode());//三级产品 场到场
		if (IS_TRANSFER_ORDER || IS_EWAYBILL_ORDER  ||IS_DOOR_TO_DOOR ||IS_YARD_TO_YARD) {
			//如果是转货订单、零担电子运单转货运单、三级产品 门到门、三级产品 场到场；则直接由foss处理
			LOGGER.info("是转货订单、零担电子运单转货运单、三级产品 门到门、三级产品 场到场；则直接由foss处理"+dispatchOrderEntity.getOrderNo());
			dispatchOrderEntity.setSysGray(DispatchOrderStatusConstants.HD_RULE_FOSS);
		}else{
			
			//查询RPS订单表,按照订单号进行查询
			DispatchOrderConditionDto dispatchOrderCondition_RPS = new DispatchOrderConditionDto();
			dispatchOrderCondition_RPS.setOrderNo(dispatchOrderEntity.getOrderNo());
			DispatchOrderEntity dispatchOrder_RPS = rpsdispatchOrderEntityDao.queryOrdersByOrderNo(dispatchOrderCondition_RPS); 
			if (dispatchOrder_RPS != null) {//如果存在
				LOGGER.info("RPS系统中存在订单则发送给RPS系统"+dispatchOrderEntity.getOrderNo());
				// 如果存在，现有逻辑是直接删除RPS订单
				rpsdispatchOrderEntityDao.deleteDispatchOrderForNo(dispatchOrderCondition_RPS);
				LOGGER.info("RPS中存在订单号："+dispatchOrderCondition_RPS.getOrderNo() + "，删除成功。"); 
				dispatchOrderEntity.setSysGray(DispatchOrderStatusConstants.HD_RULE_RPS);
				dispatchOrderEntity.setOrderStatus(DispatchOrderStatusConstants.RPS_WAIT_DISPATCH);
				rpsdispatchOrderEntityDao.insertSelective(dispatchOrderEntity);//插入RPS订单表
				rpsdispatchOrderEntityDao.addRpsOrderJob(this.packOrderJobEntity(dispatchOrderEntity, createTime)); //插入RPS订单job调度表
			}else{ //根据灰度规则判断是否要走rps
				if( HdRuleNotifyBase.isSendToRPSByTime() || HdRuleNotifyBase.isSendToRPSByPer(dispatchOrderEntity.getOrderNo())){
					LOGGER.info("符合灰度规则订单发送给RPS系统"+dispatchOrderEntity.getOrderNo());
					//将数据保存到rps
					dispatchOrderEntity.setSysGray(DispatchOrderStatusConstants.HD_RULE_RPS);
					dispatchOrderEntity.setOrderStatus(DispatchOrderStatusConstants.RPS_WAIT_DISPATCH);
					rpsdispatchOrderEntityDao.insertSelective(dispatchOrderEntity);
					rpsdispatchOrderEntityDao.addRpsOrderJob(this.packOrderJobEntity(dispatchOrderEntity, createTime)); //插入RPS订单job调度表
				}else{
					LOGGER.info("不符合灰度规则订单发送给RPS系统"+dispatchOrderEntity.getOrderNo());
					dispatchOrderEntity.setSysGray(DispatchOrderStatusConstants.HD_RULE_FOSS);
				}
			}
		}
		dispatchOrderEntity.setOrderStatus(orderStatus);//改回原来的orderStatus
		return dispatchOrderEntity;
	}
	
	/**
	 * 2017年3月24日09:59:20 qzq
	 * 封装rps订单job表
	 * @param dispatchOrderEntity
	 * @param createTime 创建时间
	 * @return
	 */
	private RPSOrderJobEntity packOrderJobEntity(DispatchOrderEntity dispatchOrderEntity,Date createTime){
		// 变更表记录
		RPSOrderJobEntity orderJob = new RPSOrderJobEntity(); 
		// id
		orderJob.setId(UUIDUtils.getUUID());
		// 订单号
		orderJob.setOrderNo(dispatchOrderEntity.getOrderNo());
		//创建时间
		orderJob.setCreateTime(createTime);
		// 设置接货城市
		orderJob.setCityCode(dispatchOrderEntity.getPickupCityCode());
		// 设置产品类型
		orderJob.setProductCode(dispatchOrderEntity.getProductCode());
		//设置最早接货时间
		orderJob.setEarlistPickupTime(dispatchOrderEntity.getEarliestPickupTime()==null?new Date():dispatchOrderEntity.getEarliestPickupTime());
		//设置变更状态
		orderJob.setChangeStatus(DispatchOrderStatusConstants.RPS_CHANGE_STATUS); 
		//jobid
//	    orderJob.setJobId(DispatchOrderStatusConstants.CHANGE_ORDER_NO_QUERY);
		// 变更时间
//		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd"); 
//		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String todayString = sdf1.format(new Date());
//		String pickupTimeString = sdf1.format(dispatchOrderEntity.getEarliestPickupTime());
//		if(todayString.equals(pickupTimeString)) {
//			orderJob.setChangeTime(createTime);
//		}else {
//			//确保非当日订单在9点后自动受理
//			try {
//				orderJob.setChangeTime(sdf2.parse(pickupTimeString+" 08:03:00"));
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//		}
       
	    return orderJob;
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
		//获取订单业务锁自动释放时间
		String orderLockTtl = configurationParamsService.queryConfValueByCode(DispatchOrderStatusConstants.ORDER_LOCK_TTL);
		OrderMutexElement mutexElement = null;		
		if (StringUtils.isBlank(orderLockTtl)) {
			mutexElement = new OrderMutexElement(dto.getOrderNo(), "订单更新_派车记录", OrderMutexElementConstants.DISPATCHORDERNO_LOCK);
			LOGGER.info("调度派车记录更新订单"+dto.getOrderNo()+"时订单业务锁自动释放时间采用默认值");
		} else {
			mutexElement = new OrderMutexElement(dto.getOrderNo(), "订单更新_派车记录", 
					OrderMutexElementConstants.DISPATCHORDERNO_LOCK,Integer.valueOf(orderLockTtl));
		}
		//互斥锁定
		boolean isLocked = orderBusinessLockService.lock(mutexElement, DispatchOrderStatusConstants.ZERO);
		if(!isLocked){
		//if(true){
     		LOGGER.info("调度派车记录更新订单"+dto.getOrderNo()+"时订单已锁定");
     		throw new DispatchException("订单正在操作中，请稍后再试！");
		}
		try {
			OrderHandleDto orderHandleDto = new OrderHandleDto();
			// 订单号
			orderHandleDto.setOrderNo(dto.getOrderNo());
			// 订单类型
			orderHandleDto.setOrderType(dto.getOrderType());
			// 订单状态
			orderHandleDto.setOrderStatus(dto.getOrderStatus());
			// 订单id
			orderHandleDto.setId(dto.getOrderId());
			
			DispatchOrderEntity order = dispatchOrderEntityDao.queryOrderByOrderNo(dto.getOrderNo());
			if (order != null && ProductEntityConstants.EXPRESS_PRODUCT_CODE_LIST.contains(order.getProductCode())) {
				// 小件订单 变更订单状态时，如果是更新为PDA接受 or 已开单是，更新发送状态为已发送
				if (StringUtils.equals(dto.getOrderStatus(), DispatchOrderStatusConstants.STATUS_PDA_ACCEPT) || 
						StringUtils.equals(dto.getOrderStatus(), DispatchOrderStatusConstants.STATUS_WAYBILL)) {
					dto.setOrderSendStatus(DispatchOrderStatusConstants.ORDER_SENDSUCCESS);
				}
			}
			// 初始化orderHandle中的操作人等
			initOrderHandle(orderHandleDto);
			// 更新外部系统的订单
			updateExternalSystem(orderHandleDto, null);
			// 更新派车记录
			updateDispatchVehicleRecord(orderHandleDto);
			// 添加订单操作历史
			addDispatchOrderActionHistory(orderHandleDto);
			// 判断订单是否为未处理、待改接、已退回、揽货失败、已作废时，需要将GPS存在轨迹的数据进行删除
			if(StringUtils.equals(dto.getOrderStatus(), DispatchOrderStatusConstants.STATUS_NONE_HANDLE)
					|| StringUtils.equals(dto.getOrderStatus(), DispatchOrderStatusConstants.STATUS_AGAIN_PICKUP)
					|| StringUtils.equals(dto.getOrderStatus(), DispatchOrderStatusConstants.STATUS_RETURN)
					|| StringUtils.equals(dto.getOrderStatus(), DispatchOrderStatusConstants.STATUS_PICKUP_FAILURE)
					|| StringUtils.equals(dto.getOrderStatus(), DispatchOrderStatusConstants.STATUS_CANCEL)){
				//删除GPS对应轨迹
				sendToGPS(orderHandleDto,SettlementReportNumber.THREE);
			}
			//DEFECT-2767 更改状态时 修改操作时间
			dto.setOperateTime(new Date());
			return this.dispatchOrderEntityDao.updateDispatchOrderStatusById(dto);
		} catch (Exception e) {
			throw new DispatchException("操作失败！");
		}finally {
			//解锁
			orderBusinessLockService.unlock(mutexElement);
			LOGGER.info("人工受理订单"+dto.getOrderNo()+"结束");
		}
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
			/**
			 * DEFECT-2767 更改状态时 修改操作时间
			 */
			dto.setOperateTime(new Date());
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
	 * 回滚订单状态
	 * @author 043258-foss-zhaobin
	 * @date 2014-12-26 下午3:49:26
	 */
	@Override
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
	 * Sets the vehicleManageService.
	 * 
	 * @param vehicleManageService the vehicleManageService to set
	 */
	public void setVehicleManageService(IVehicleManageService vehicleManageService) {
		this.vehicleManageService = vehicleManageService;
	}

	/**
	 * Sets the dispatchOrderChangeEntityDao.
	 * 
	 * @param dispatchOrderChangeEntityDao the dispatchOrderChangeEntityDao to
	 *            set
	 */
	public void setDispatchOrderChangeEntityDao(IDispatchOrderChangeEntityDao dispatchOrderChangeEntityDao) {
		this.dispatchOrderChangeEntityDao = dispatchOrderChangeEntityDao;
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
	 * 增加转货订单（集中区域）-- 中转接口.
	 * 
	 * @param transferOrderDto 转货订单dto
	 * 			orderNo
	 * 				约车号
	 * 			vehicleType
	 * 				车型
	 * 			weight
	 * 				重量
	 * 			volume
	 * 				体积
	 * 			pickupElseAddress
	 * 				接货地址
	 * 			customerName
	 * 				客户名称
	 * 			tel
	 * 				电话
	 * 			mobile
	 * 				手机
	 * 			orderNotes
	 * 				备注
	 * 			salesDepartmentName
	 * 				用车部门名称
	 * 			salesDepartmentCode
	 * 				用车部门编码
	 * 			latestPickupTime
	 * 				预计用车时间
	 * 			goodsName
	 * 				货物名称
	 * 			goodsType
	 * 				货物类型
	 * 			goodsQty
	 * 				货物件数
	 * 			orderVehicleOrgCode
	 * 				申请部门编码
	 * 			orderVehicleOrgName
	 * 				申请部门名称
	 * 			orderVehicleTime
	 * 				申请时间
	 * 			createUserName
	 * 				申请人
	 * 			createUserCode
	 * 				申请人编码
	 * 			fleetCode
	 * 				派车车队编码
	 * @return true, if successful
	 * @author 038590-foss-wanghui
	 * @date 2012-11-29 上午11:47:04
	 */
	@Override
	public boolean addTransferOrder(TransferOrderDto transferOrderDto) {
		// 传入参数空校验
		if (transferOrderDto == null) {
			return false;
		}
		DispatchOrderEntity oldOrder = dispatchOrderEntityDao.queryOrderByOrderNo(transferOrderDto.getOrderNo());
		DispatchOrderEntity dispatchOrderEntity = new DispatchOrderEntity();
		try {
			BeanUtils.copyProperties(dispatchOrderEntity, transferOrderDto);
		} catch (IllegalAccessException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			LOGGER.error(e.getMessage(), e);
		}
		// 不存在则插入，存在则更新
		if(oldOrder == null){
				// ID
				dispatchOrderEntity.setId(UUIDUtils.getUUID());
				// 转货订单
				dispatchOrderEntity.setOrderType(DispatchOrderStatusConstants.TYPE_TRANSFER_ORDER);
				// 未处理
				dispatchOrderEntity.setOrderStatus(DispatchOrderStatusConstants.STATUS_NONE_HANDLE);
				//增加最早接货时间
				dispatchOrderEntity.setEarliestPickupTime(new Date());
			// 调用DAO插入数据
			dispatchOrderEntityDao.insertSelective(dispatchOrderEntity);
		} else {
			dispatchOrderEntity.setId(oldOrder.getId());
			dispatchOrderEntityDao.updateByPrimaryKeySelective(dispatchOrderEntity);
		}
		return true;
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
	 * 对同步的订单进行重复性验证
	 * 
	 * @param dispatchOrderEntity
	 */
	private void checkOrderExist(DispatchOrderEntity dispatchOrderEntity) {
		if (dispatchOrderEntity == null || StringUtils.isEmpty(dispatchOrderEntity.getOrderNo())) {
			return;
		}
		// 查询foss是否有相同单号的订单
		DispatchOrderConditionDto dto = new DispatchOrderConditionDto();
		dto.setOrderNo(dispatchOrderEntity.getOrderNo());
		// 按照订单号进行查询
		DispatchOrderEntity originalOrder = dispatchOrderEntityDao.queryOrdersByOrderNo(dto);
		if (originalOrder == null) {
			// 如果在FOSS中不存在该单号订单
			return;
		}
		// 如果存在，现有逻辑是直接删除FOSS订单
		dispatchOrderEntityDao.deleteDispatchOrderForNo(dto);
		LOGGER.info(dto.getOrderNo() + "删除成功。");
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
		if (!ProductEntityConstants.EXPRESS_PRODUCT_CODE_LIST.contains(dispatchOrderEntity.getProductCode())) {
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
	
	//向电子运单订单表插入数据
	private void insertEWaybillOrder(EWaybillOrderEntity eWaybillOrderEntity){
		if(eWaybillOrderEntity != null){
			ewaybillOrderEntityDao.insertEWaybillOrder(eWaybillOrderEntity);
			LOGGER.info("插入订单成功！");
		}
	}

	/*
	 * 受理订单时向将约车信息插入数据库
	 * @author Foss-136334-Bailei
	 * @date 2014-09-23
	 */
	public void addEWaybilllOrder(String orderNo){
		if(StringUtils.isEmpty(orderNo)){
			LOGGER.info("传入的订单号为空");
			return;
		}
		DispatchOrderEntity dispatchOrderEntity = dispatchOrderEntityDao.queryAllInfoByOrderNo(orderNo);
		if(dispatchOrderEntity == null || StringUtils.isEmpty(dispatchOrderEntity.getOrderNo())
				|| !WaybillConstants.EWAYBILL.equals(dispatchOrderEntity.getWaybillType())){
			LOGGER.info("订单数据不存在或者不是电子运单");
			return;
		}
		boolean isCreate = false;
		EWaybillOrderEntity eWaybillOrderEntity = ewaybillOrderEntityDao.queryEWaybillByOrderNo(orderNo);
		//如果EwaybillOrder 表中存在，则更新ewaybillOrder
		if(eWaybillOrderEntity != null){
			isCreate = true;
		}else{
			eWaybillOrderEntity = new EWaybillOrderEntity();
		}
		//通过查询订单接口查询订单信息
		CrmOrderDetailDto crmOrderDetailDto = null;
		try {
			crmOrderDetailDto = crmOrderService.importOrder(orderNo);
		} catch (Exception e) {
			LOGGER.info("查询CRM订单出现问题,异常信息为:");
			e.printStackTrace();
			//如果eWaybillOrderEntity数据存在，可以进行一次数据的重置
			try {
				if(StringUtils.isNotEmpty(eWaybillOrderEntity.getID())){
					EWaybillOrderEntity eWaybillOrderEntityTemp = new EWaybillOrderEntity();
					eWaybillOrderEntityTemp.setID(eWaybillOrderEntity.getID());
					eWaybillOrderEntityTemp.setJobID(WaybillConstants.UNKNOWN);
					eWaybillOrderEntityTemp.setFailReason(FossConstants.NO);
					eWaybillOrderEntityTemp.setModifyTime(new Date());	
					ewaybillOrderEntityDao.updateEWaybillOrderEntitySelective(eWaybillOrderEntityTemp);
					return;
				}
			} catch (Exception e1) {
				// 查询不到订单数据进行数据一次重置,保证数据的健壮性
				LOGGER.info("查询不到订单数据进行数据一次重置,用于保证数据的健壮性,EWaybillOrderEntity对应ID为:"+eWaybillOrderEntity.getID());
				e1.printStackTrace();
			}
		}
		if(crmOrderDetailDto == null){
			return;
		}
		eWaybillOrderEntity.setOrderNO(orderNo);
		eWaybillOrderEntity.setReceiveCustomerID(crmOrderDetailDto.getReceiverCustId());
		eWaybillOrderEntity.setReceiveCustomerCode(crmOrderDetailDto.getReceiverCustNumber());
		eWaybillOrderEntity.setReceiveCustomerName(crmOrderDetailDto.getReceiverCustName());
		eWaybillOrderEntity.setReceiveCustomerContact(crmOrderDetailDto.getReceiverCustName());
		eWaybillOrderEntity.setReceiveCustomerMobilephone(crmOrderDetailDto.getReceiverCustMobile());
		eWaybillOrderEntity.setReceiveCustomerPhone(crmOrderDetailDto.getReceiverCustPhone());
		eWaybillOrderEntity.setDeliveryCustomerContactId(crmOrderDetailDto.getContactManId());
		eWaybillOrderEntity.setReceiveCustomerNationCode(null);
		eWaybillOrderEntity.setReceiveCustomerProvCode(crmOrderDetailDto.getReceiverCustProvinceCode());
		eWaybillOrderEntity.setReceiveCustomerCityCode(crmOrderDetailDto.getReceiverCustCityCode());
		eWaybillOrderEntity.setReceiveCustomerDistCode(crmOrderDetailDto.getReceiverCustAreaCode());
		eWaybillOrderEntity.setReceiveCustomerAddress(crmOrderDetailDto.getReceiverCustAddress());
		eWaybillOrderEntity.setDeliveryCustomerContact(crmOrderDetailDto.getContactName());
		eWaybillOrderEntity.setChannelCustID(crmOrderDetailDto.getChannelCustId());
		// 签收单
		String crmReturnBillType = crmOrderDetailDto.getReturnBillType();
		for (CrmReturnBillTypeEnum crm : CrmReturnBillTypeEnum.values()) {
			// 订单返单方式
			if (crm.getCrmCode().equals(crmReturnBillType)) {
				eWaybillOrderEntity.setReturnBillType(crm.getFossCode());
			}
		}
		if (StringUtils.isEmpty(crmReturnBillType)) {
			eWaybillOrderEntity.setReturnBillType(WaybillConstants.NOT_RETURN_BILL);
		}
		eWaybillOrderEntity.setJobID(WaybillConstants.UNKNOWN);
		eWaybillOrderEntity.setFailReason(FossConstants.NO);
		if(isCreate){
			eWaybillOrderEntity.setModifyTime(new Date());	
			ewaybillOrderEntityDao.updateEWaybillOrderEntitySelective(eWaybillOrderEntity);
		}else{
			eWaybillOrderEntity.setCreateTime(new Date());
			eWaybillOrderEntity.setModifyTime(new Date());	
			insertEWaybillOrder(eWaybillOrderEntity);
		}
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
	 * 修改电子运单订单信息
	 * @author 219396-daolin
	 * @date 2015-06-09 上午9:49:49 
	 */
	@Override
	@Transactional
	public void modifyEwaybillOrder(UpdateEOrderRequest payload) {
		StringBuffer log = new StringBuffer("");
		if(StringUtils.isBlank(payload.getOrderNumber())||StringUtils.isBlank(payload.getWaybillNumber())) {
			throw new DispatchException("参数错误", "请传入订单号和运单号");
		}
		// 判断操作类型是否为空
		if (StringUtils.isBlank(payload.getModifyType())) {
			throw new DispatchException("参数错误", "未指定修改类型");
		}
		//如果订单来源--大客户发件系统则不验证渠道客户Id
		//判断客户编码是否为空
		if (StringUtils.isNotBlank(payload.getResource())) {
			if(!ORDER_RESOURCE_VAS.equals(payload.getResource())){
				if(StringUtils.isBlank(payload.getChannelCustId())) {
					throw new DispatchException("参数错误", "客户编码不能为空");
				}
			}
		}
		
		//根据订单号查询FOSS是否存在该订单信息 
		DispatchOrderEntity oldOrderEntity = dispatchOrderEntityDao
				.queryAllInfoByOrderNo(payload.getOrderNumber());
		if (oldOrderEntity == null) {
			throw new DispatchException("拒绝更改", "该订单在FOSS不存在");
		}
		//若订单状态为已开单，不允许更改
		if (DispatchOrderStatusConstants.STATUS_WAYBILL.equals(oldOrderEntity.getOrderStatus()))
			throw new DispatchException("拒绝更改", "该订单已开单,不允许更改");
		
		Date createTime = new Date();
		
		if (DispatchOrderStatusConstants.OPERATE_MODIFIY.equals(payload
				.getModifyType())) {
			/********************************* 更新订单信息begin *********************************/
			DispatchOrderEntity newOrderEntity = new DispatchOrderEntity();
			newOrderEntity.setId(oldOrderEntity.getId());
			//将订单状态设置为未处理，相当于CRM重新约车
			newOrderEntity.setOrderStatus(DispatchOrderStatusConstants.STATUS_NONE_HANDLE);
			
			// 查询提货网点
			OrgAdministrativeInfoEntity customerPickupOrg = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByUnifiedCode(payload
							.getReceivingToPoint());
			if (customerPickupOrg != null) {
				// 提货网点
				newOrderEntity.setCustomerPickupOrgCode(customerPickupOrg
						.getCode());
			} else {
				// 提货网点
				newOrderEntity.setCustomerPickupOrgCode(payload
						.getReceivingToPoint());
			}
			if(!StringUtils.equals(oldOrderEntity.getCustomerPickupOrgCode(), newOrderEntity.getCustomerPickupOrgCode())) {
				log.append(getLog("customerPickupOrgCode", oldOrderEntity.getCustomerPickupOrgCode(), newOrderEntity.getCustomerPickupOrgCode()));
			}
			// 接货省份
			newOrderEntity.setPickupProvince(payload.getContactProvince());
			if(!StringUtils.equals(oldOrderEntity.getPickupProvince(), newOrderEntity.getPickupProvince())) {
				log.append(getLog("pickupProvince", oldOrderEntity.getPickupProvince(), newOrderEntity.getPickupProvince()));
			}
			newOrderEntity.setPickupProvinceCode(payload.getReceiveProvinceCode());
			if(!StringUtils.equals(oldOrderEntity.getPickupProvinceCode(), newOrderEntity.getPickupProvinceCode())) {
				log.append(getLog("pickupProvinceCode", oldOrderEntity.getPickupProvinceCode(), newOrderEntity.getPickupProvinceCode()));
			}
			// 接货城市
			newOrderEntity.setPickupCity(payload.getContactCity());
			if(!StringUtils.equals(oldOrderEntity.getPickupCity(), newOrderEntity.getPickupCity())) {
				log.append(getLog("pickupCity", oldOrderEntity.getPickupCity(), newOrderEntity.getPickupCity()));
			}
			newOrderEntity.setPickupCityCode(payload.getReceiveCityCode());
			if(!StringUtils.equals(oldOrderEntity.getPickupCityCode(), newOrderEntity.getPickupCityCode())) {
				log.append(getLog("pickupCityCode", oldOrderEntity.getPickupCityCode(), newOrderEntity.getPickupCityCode()));
			}
			// 接货区/县
			newOrderEntity.setPickupCounty(payload.getContactArea());
			if(!StringUtils.equals(oldOrderEntity.getPickupCounty(), newOrderEntity.getPickupCounty())) {
				log.append(getLog("pickupCounty", oldOrderEntity.getPickupCounty(), newOrderEntity.getPickupCounty()));
			}
			newOrderEntity.setPickupCountyCode(payload.getReceiveCountyCode());
			if(!StringUtils.equals(oldOrderEntity.getPickupCountyCode(), newOrderEntity.getPickupCountyCode())) {
				log.append(getLog("pickupCountyCode", oldOrderEntity.getPickupCountyCode(), newOrderEntity.getPickupCountyCode()));
			}
			// 接货具体地址
			newOrderEntity.setPickupElseAddress(payload.getContactAddress());
			if(!StringUtils.equals(oldOrderEntity.getPickupElseAddress(), newOrderEntity.getPickupElseAddress())) {
				log.append(getLog("pickupElseAddress", oldOrderEntity.getPickupElseAddress(), newOrderEntity.getPickupElseAddress()));
			}
			//收货联系人姓名
			newOrderEntity.setReceiverCustName(payload.getReceiverCustName());
			if(!StringUtils.equals(oldOrderEntity.getReceiverCustName(), newOrderEntity.getReceiverCustName())) {
				log.append(getLog("receiverCustName", oldOrderEntity.getReceiverCustName(), newOrderEntity.getReceiverCustName()));
			}
			//收货联系人电话
			newOrderEntity.setReceiverCustPhone(payload.getReceiverCustPhone());
			if(!StringUtils.equals(oldOrderEntity.getReceiverCustPhone(), newOrderEntity.getReceiverCustPhone())) {
				log.append(getLog("receiverCustPhone", oldOrderEntity.getReceiverCustPhone(), newOrderEntity.getReceiverCustPhone()));
			}
			//收货联系人手机
			newOrderEntity.setReceiverCustMobile(payload.getReceiverCustMobile());
			if(!StringUtils.equals(oldOrderEntity.getReceiverCustMobile(), newOrderEntity.getReceiverCustMobile())) {
				log.append(getLog("receiverCustMobile", oldOrderEntity.getReceiverCustMobile(), newOrderEntity.getReceiverCustMobile()));
			}
			// 接货具体地址备注
			newOrderEntity.setDeliveryCustomerAddressNote(payload.getContactAddrRemark());
			if(!StringUtils.equals(oldOrderEntity.getDeliveryCustomerAddressNote(), newOrderEntity.getDeliveryCustomerAddressNote())) {
				log.append(getLog("deliveryCustomerAddressNote", oldOrderEntity.getDeliveryCustomerAddressNote(), newOrderEntity.getDeliveryCustomerAddressNote()));
			}
			// 收货具体地址备注
			newOrderEntity.setReceiveCustomerAddressNote(payload.getReceiverCustAddrRemark());
			if(!StringUtils.equals(oldOrderEntity.getReceiveCustomerAddressNote(), newOrderEntity.getReceiveCustomerAddressNote())) {
				log.append(getLog("receiveCustomerAddressNote", oldOrderEntity.getReceiveCustomerAddressNote(), newOrderEntity.getReceiveCustomerAddressNote()));
			}
			// 客户名称
			newOrderEntity.setCustomerName(payload.getContactName());
			if(!StringUtils.equals(oldOrderEntity.getCustomerName(), newOrderEntity.getCustomerName())) {
				log.append(getLog("customerName", oldOrderEntity.getCustomerName(), newOrderEntity.getCustomerName()));
			}
			// 客户地址
			StringBuffer receiverAddress = new StringBuffer("");
			if(StringUtils.isNotBlank(payload.getReceiverCustProvince()))
				receiverAddress.append(payload.getReceiverCustProvince());
			if(StringUtils.isNotBlank(payload.getReceiverCustCity()))
				receiverAddress.append(payload.getReceiverCustCity());
			if(StringUtils.isNotBlank(payload.getReceiverCustArea()))
				receiverAddress.append(payload.getReceiverCustArea());
			if(StringUtils.isNotBlank(payload.getReceiverCustAddress()))
				receiverAddress.append(payload.getReceiverCustAddress());	
			newOrderEntity.setConsigneeAddress(receiverAddress.toString());
			if(!StringUtils.equals(oldOrderEntity.getConsigneeAddress(), newOrderEntity.getConsigneeAddress())) {
				log.append(getLog("consigneeAddress", oldOrderEntity.getConsigneeAddress(), newOrderEntity.getConsigneeAddress()));
			}
			// 货物名称
			newOrderEntity.setGoodsName(payload.getGoodsName());
			if(!StringUtils.equals(oldOrderEntity.getGoodsName(), newOrderEntity.getGoodsName())) {
				log.append(getLog("goodsName", oldOrderEntity.getGoodsName(), newOrderEntity.getGoodsName()));
			}
			// 包装
			newOrderEntity.setGoodsPackage(payload.getPacking());
			if(!StringUtils.equals(oldOrderEntity.getGoodsPackage(), newOrderEntity.getGoodsPackage())) {
				log.append(getLog("goodsPackage", oldOrderEntity.getGoodsPackage(), newOrderEntity.getGoodsPackage()));
			}
			// 重量
			if(payload.getTotalWeight()==null) {
				newOrderEntity.setWeight(new BigDecimal(0));
			}else {
				if(payload.getTotalWeight()<SettlementReportNumber.NUMBER_0001) {
					newOrderEntity.setWeight(new BigDecimal(0));
				}else {
					newOrderEntity.setWeight(BigDecimal.valueOf(payload.getTotalWeight()));
				}
			}
			if(!bigDecimalEquals(oldOrderEntity.getWeight(), newOrderEntity.getWeight())) {
				String oldWeight = null;
				String newWeight = null;
				if(oldOrderEntity.getWeight()!=null)
					oldWeight = String.valueOf(oldOrderEntity.getWeight().doubleValue());
				if(newOrderEntity.getWeight()!=null)
					newWeight = String.valueOf(newOrderEntity.getWeight().doubleValue());
				log.append(getLog("weight", oldWeight, newWeight));
			}
			// 体积
			if(payload.getTotalVolume()==null) {
				newOrderEntity.setVolume(new BigDecimal(0));
			}else {
				if(payload.getTotalVolume()<SettlementReportNumber.NUMBER_0001) {
					newOrderEntity.setVolume(new BigDecimal(0));
				}else {
					newOrderEntity.setVolume(BigDecimal.valueOf(payload.getTotalVolume()));
				}
			}
			if(!bigDecimalEquals(oldOrderEntity.getVolume(), newOrderEntity.getVolume())) {
				String oldVolume = null;
				String newVolume = null;
				if(oldOrderEntity.getVolume()!=null)
					oldVolume = String.valueOf(oldOrderEntity.getVolume().doubleValue());
				if(newOrderEntity.getVolume()!=null)
					newVolume = String.valueOf(newOrderEntity.getVolume().doubleValue());
				log.append(getLog("volume", oldVolume, newVolume));
			}
			// 件数
			newOrderEntity.setGoodsQty(payload.getGoodsNumber());
			if(!StringUtils.equals(String.valueOf(oldOrderEntity.getGoodsQty()), String.valueOf(newOrderEntity.getGoodsQty()))) {
				log.append(getLog("goodsQty", String.valueOf(oldOrderEntity.getGoodsQty()), String.valueOf(newOrderEntity.getGoodsQty())));
			}
			// 货物类型
			newOrderEntity.setGoodsType(payload.getGoodsType());
			if(!StringUtils.equals(oldOrderEntity.getGoodsType(), newOrderEntity.getGoodsType())) {
				log.append(getLog("goodsType", oldOrderEntity.getGoodsType(), newOrderEntity.getGoodsType()));
			}
			// 发货人手机
			newOrderEntity.setMobile(payload.getContactMobile());
			if(!StringUtils.equals(oldOrderEntity.getMobile(), newOrderEntity.getMobile())) {
				log.append(getLog("mobile", oldOrderEntity.getMobile(), newOrderEntity.getMobile()));
			}
			// 联系电话
			newOrderEntity.setTel(payload.getContactPhone());
			if(!StringUtils.equals(oldOrderEntity.getTel(), newOrderEntity.getTel())) {
				log.append(getLog("Tel", oldOrderEntity.getTel(), newOrderEntity.getTel()));
			}
			// 付款方式
			newOrderEntity.setPaidMethod(payload.getPaymentType());
			if(!StringUtils.equals(oldOrderEntity.getPaidMethod(), newOrderEntity.getPaidMethod())) {
				log.append(getLog("paidMethod", oldOrderEntity.getPaidMethod(), newOrderEntity.getPaidMethod()));
			}
			
			/**
			 * 2013-08-09 CRM要求把CRM提货方式转成foss中的
			 */
			if ("SELF_PICKUP".equals(payload.getDeliveryMode())) {
				payload.setDeliveryMode("SELF_PICKUP_AIR");
			} else if ("FREE_PICKUP".equals(payload.getDeliveryMode())) {
				payload.setDeliveryMode("SELF_PICKUP_FREE_AIR");
			} else if ("FREE_DELIVERY".equals(payload.getDeliveryMode())) {
				payload.setDeliveryMode("DELIVER");
			} else if ("DELIVERY_STOCK".equals(payload.getDeliveryMode())) {
				payload.setDeliveryMode("DELIVER_INGA");
			} else if ("DELIVERY_HOME".equals(payload.getDeliveryMode())) {
				payload.setDeliveryMode("DELIVER_UP");
			} else if ("DELIVERY".equals(payload.getDeliveryMode())) {
				payload.setDeliveryMode("DELIVER_NOUP");
			} else if ("PICKUP".equals(payload.getDeliveryMode())) {
				payload.setDeliveryMode("SELF_PICKUP");
			} else if ("".equals(payload.getDeliveryMode())
					|| payload.getDeliveryMode() == null) {
				payload.setDeliveryMode("");
			}

			// 提货方式
			newOrderEntity.setReceiveMethod(payload.getDeliveryMode());
			if(!StringUtils.equals(oldOrderEntity.getReceiveMethod(), newOrderEntity.getReceiveMethod())) {
				log.append(getLog("receiveMethod", oldOrderEntity.getReceiveMethod(), newOrderEntity.getReceiveMethod()));
			}
			// 代收货款退款类型，CRM传递明文，需要转化为FOSS的编码
			if (CrmRefundTypeEnum.NORMAL.getCrmCode().equals(
					payload.getReciveLoanType())) {
				newOrderEntity
						.setReciveLoanType(WaybillConstants.REFUND_TYPE_THREE_DAY);
			} else if (CrmRefundTypeEnum.INTRADAY.getCrmCode().equals(
					payload.getReciveLoanType())) {
				newOrderEntity
						.setReciveLoanType(WaybillConstants.REFUND_TYPE_ONE_DAY);
			} else {
				newOrderEntity.setReciveLoanType(null);
			}
			if(!StringUtils.equals(oldOrderEntity.getReciveLoanType(), newOrderEntity.getReciveLoanType())) {
				log.append(getLog("reciveLoanType", oldOrderEntity.getReciveLoanType(), newOrderEntity.getReciveLoanType()));
			}
			// 保价申明价值,接口String转化为BigDecimal
			newOrderEntity.setInsuredAmount(new BigDecimal(payload.getInsuredAmount()==null?0:payload.getInsuredAmount()));
			if(!bigDecimalEquals(oldOrderEntity.getInsuredAmount(), newOrderEntity.getInsuredAmount())) {
				String oldInsuredAmount = null;
				String newInsuredAmount = null;
				if(oldOrderEntity.getInsuredAmount()!=null)
					oldInsuredAmount = String.valueOf(oldOrderEntity.getInsuredAmount().doubleValue());
				if(newOrderEntity.getInsuredAmount()!=null)
					newInsuredAmount = String.valueOf(newOrderEntity.getInsuredAmount().doubleValue());
				log.append(getLog("insuredAmount", oldInsuredAmount, newInsuredAmount));
			}
			// 优惠券编码
			newOrderEntity.setCouponNumber(payload.getCouponNumber());
			if(!StringUtils.equals(oldOrderEntity.getCouponNumber(), newOrderEntity.getCouponNumber())) {
				log.append(getLog("couponNumber", oldOrderEntity.getCouponNumber(), newOrderEntity.getCouponNumber()));
			}
			// 发货客户CRM客户编码
			newOrderEntity.setDeliveryCustomerCode(payload.getShipperNumber());
			if(!StringUtils.equals(oldOrderEntity.getDeliveryCustomerCode(), newOrderEntity.getDeliveryCustomerCode())) {
				log.append(getLog("deliveryCustomerCode", oldOrderEntity.getDeliveryCustomerCode(), newOrderEntity.getDeliveryCustomerCode()));
			}
			// 代收货款帐号codRefundAccount
			newOrderEntity.setCodRefundAccount(payload.getReciveLoanAccount());
			if(!StringUtils.equals(oldOrderEntity.getCodRefundAccount(), newOrderEntity.getCodRefundAccount())) {
				log.append(getLog("codRefundAccount", oldOrderEntity.getCodRefundAccount(), newOrderEntity.getCodRefundAccount()));
			}
			// 代收货款帐号开户名codRefundAccountName
			newOrderEntity.setCodRefundAccountName(payload.getAccountName());
			if(!StringUtils.equals(oldOrderEntity.getCodRefundAccountName(), newOrderEntity.getCodRefundAccountName())) {
				log.append(getLog("codRefundAccountName", oldOrderEntity.getCodRefundAccountName(), newOrderEntity.getCodRefundAccountName()));
			}
			// 收货省
			newOrderEntity.setConsigneeProvince(payload.getReceiverCustProvince());
			if(!StringUtils.equals(oldOrderEntity.getConsigneeProvince(), newOrderEntity.getConsigneeProvince())) {
				log.append(getLog("consigneeProvince", oldOrderEntity.getConsigneeProvince(), newOrderEntity.getConsigneeProvince()));
			}
			// 收货市
			newOrderEntity.setConsigneeCity(payload.getReceiverCustCity());
			if(!StringUtils.equals(oldOrderEntity.getConsigneeCity(), newOrderEntity.getConsigneeCity())) {
				log.append(getLog("consigneeCity", oldOrderEntity.getConsigneeCity(), newOrderEntity.getConsigneeCity()));
			}
			// 收货区
			newOrderEntity.setConsigneeCounty(payload.getReceiverCustArea());
			if(!StringUtils.equals(oldOrderEntity.getConsigneeCounty(), newOrderEntity.getConsigneeCounty())) {
				log.append(getLog("consigneeCounty", oldOrderEntity.getConsigneeCounty(), newOrderEntity.getConsigneeCounty()));
			}
			// 收货详细地址
			newOrderEntity.setConsigneeDetailAddress(payload.getReceiverCustAddress());
			if(!StringUtils.equals(oldOrderEntity.getConsigneeDetailAddress(), newOrderEntity.getConsigneeDetailAddress())) {
				log.append(getLog("consigneeDetailAddress", oldOrderEntity.getConsigneeDetailAddress(), newOrderEntity.getConsigneeDetailAddress()));
			}
			// 采购单号
			newOrderEntity.setProcurementNumber(payload.getProcurementNumber());
			if(!StringUtils.equals(oldOrderEntity.getProcurementNumber(), newOrderEntity.getProcurementNumber())) {
				log.append(getLog("procurementNumber", oldOrderEntity.getProcurementNumber(), newOrderEntity.getProcurementNumber()));
			}
			//是否字母件
			newOrderEntity.setIsPicPackage(payload.getIsPicPackage());
			if(!StringUtils.equals(oldOrderEntity.getIsPicPackage(), newOrderEntity.getIsPicPackage())) {
				log.append(getLog("isPicPackage", oldOrderEntity.getIsPicPackage(), newOrderEntity.getIsPicPackage()));
			}
			//原始单号
			newOrderEntity.setOriginalNumber(payload.getOriginalNumber());
			if(!StringUtils.equals(oldOrderEntity.getOriginalNumber(), newOrderEntity.getOriginalNumber())) {
				log.append(getLog("originalNumber", oldOrderEntity.getOriginalNumber(), newOrderEntity.getOriginalNumber()));
			}
			//设置代收货款
			newOrderEntity.setReviceMoneyAmount(new BigDecimal( payload.getReviceMoneyAmount() == null ? 0 : payload.getReviceMoneyAmount() ));
			String oldReviceMoneyAmount = ""; 
			String newReviceMoneyAmount = "";
			if(oldOrderEntity.getReviceMoneyAmount()!=null){
				oldReviceMoneyAmount = String.valueOf(oldOrderEntity.getReviceMoneyAmount().doubleValue())+"";
			}
			if(newOrderEntity.getReviceMoneyAmount()!=null){
				newReviceMoneyAmount = String.valueOf(newOrderEntity.getReviceMoneyAmount().doubleValue())+"";
			}
			log.append(getLog("reviceMoneyAmount", oldReviceMoneyAmount, newReviceMoneyAmount));
			
			dispatchOrderEntityDao.updateEwaybillOrderInfo(newOrderEntity);
			
			//将订单回滚到未处理状态
			ewaybillService.singleDeleteEcommerceOrder(payload); 
			PreHandEWaybillOrderEntity record = new PreHandEWaybillOrderEntity();
			record.setId(UUIDUtils.getUUID());
			record.setCreateTime(new Date());
			record.setModifyTime(new Date());
			record.setOrderNo(payload.getOrderNumber());
			record.setWaybillNo(payload.getWaybillNumber());
			record.setJobId(WaybillConstants.UNKNOWN);
			record.setExceptionMsg(FossConstants.NO);
			record.setWaybillType(WaybillConstants.EWAYBILL);
			preHandEWaybillOrderDao.insertPreEWaybillOrderSelective(record);	
						
			/*************************************更新运单信息start********************************/
			StringBuffer allLog = new StringBuffer();
			allLog.append("[更改字段]\r\n");
			allLog.append(log.toString()+"\r\n");
			allLog.append("[请求实体]\r\n");
			allLog.append(payload.toString());
			// 将修改前后字段值插入数据库(更新日志)
			ModifyEwaybillOrderLogEntity logEntity = new ModifyEwaybillOrderLogEntity();
			logEntity.setId(UUIDUtils.getUUID());
			logEntity.setOrderNo(payload.getOrderNumber());
			logEntity.setWaybillNo(payload.getWaybillNumber());
			logEntity.setOperateType(FossConstants.OPERATE_MODIFY);
			logEntity.setIsSuccess(FossConstants.YES);
			logEntity.setOperateRecord(allLog.toString());
			logEntity.setCreateTime(createTime);
			modifEwaybillOrderRecordDao.insertSelective(logEntity);			
		} else if(DispatchOrderStatusConstants.OPERATE_DELETE.equals(payload
				.getModifyType())){
			DispatchOrderEntity cancelEntity = new DispatchOrderEntity();
			cancelEntity.setId(oldOrderEntity.getId());
			//将订单状态设置为已取消
			cancelEntity.setOrderStatus(DispatchOrderStatusConstants.STATUS_CANCEL);
			dispatchOrderEntityDao.updateByPrimaryKeySelective(cancelEntity);
			ewaybillService.singleDeleteEcommerceOrder(payload); 
			// 将修改前后字段值插入数据库
			ModifyEwaybillOrderLogEntity logEntity = new ModifyEwaybillOrderLogEntity();
			logEntity.setId(UUIDUtils.getUUID());
			logEntity.setOrderNo(payload.getOrderNumber());
			logEntity.setWaybillNo(payload.getWaybillNumber());
			logEntity.setOperateType(FossConstants.OPERATE_DELETE);
			logEntity.setIsSuccess(FossConstants.YES);
			logEntity.setOperateRecord("[请求实体]\r\n"+payload.toString());
			logEntity.setCreateTime(createTime);
			modifEwaybillOrderRecordDao.insertSelective(logEntity);
			
			//更新CRM中状态为已拒绝/已退回
			CrmModifyInfoDto crmModifyInfoDto = new CrmModifyInfoDto();
			//如果为电子运单
			if (StringUtils.isNotBlank(oldOrderEntity.getWaybillType()) 
					&& "EWAYBILL".equals(oldOrderEntity.getWaybillType())) {
				crmModifyInfoDto.setBackInfo("客户主动发起订单取消，请与客户核实");
				//将FOSS的已拒绝转换为CRM的已退回
				crmModifyInfoDto.setGoodsStatus("GOBACK");
			}else{
				crmModifyInfoDto.setBackInfo("更新电子运单订单接口返回已拒绝状态");
				//将FOSS的已拒绝转换为CRM的已拒绝
				crmModifyInfoDto.setGoodsStatus("REJECT");
			}
			
			crmModifyInfoDto.setOrderNumber(payload.getOrderNumber());
			crmModifyInfoDto.setWaybillNumber(payload.getWaybillNumber());
			crmModifyInfoDto.setOprateDeptCode("DP0000");
			crmModifyInfoDto.setOprateUserNum("000000");
			crmOrderJMSService.sendModifyOrder(crmModifyInfoDto);
		}else {
			throw new DispatchException("无该操作", "该操作类型无效");
		}
	}
	
	private String getLog(String head, String old, String now) {
		return head+":["+old+"=>"+now+"]\r\n";
	}
	
	private boolean bigDecimalEquals(BigDecimal b1, BigDecimal b2) {
		if(b1==null&&b2==null)
			return true;
		if(b1==null&&b2!=null)
			return false;
		if(b1!=null&&b2==null)
			return false;
		if(b1!=null&&b2!=null)
			return b1.doubleValue()==b2.doubleValue();
		return false;
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
	
	public void setCommonExpressVehicleService(ICommonExpressVehicleService commonExpressVehicleService) {
		this.commonExpressVehicleService = commonExpressVehicleService;
	}

	public void setGPSOrderTaskService(IGPSOrderTaskService gPSOrderTaskService) {
		GPSOrderTaskService = gPSOrderTaskService;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	public void setOwnDriverDao(IOwnDriverDao ownDriverDao) {
		this.ownDriverDao = ownDriverDao;
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

	/**
	 * Sets the saleDepartmentService.
	 * 
	 * @param saleDepartmentService the saleDepartmentService to set
	 */
	public void setSaleDepartmentService(ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public void setTruckResourceDao(ITruckResourceDao truckResourceDao) {
		this.truckResourceDao = truckResourceDao;
	}

	public void setModifEwaybillOrderRecordDao(
			IModifEwaybillOrderRecordDao modifEwaybillOrderRecordDao) {
		this.modifEwaybillOrderRecordDao = modifEwaybillOrderRecordDao;
	}

	public void setEwaybillService(IEWaybillService ewaybillService) {
		this.ewaybillService = ewaybillService;
	}

	public void setSpecialPickupAddressService(
			ISpecialPickupAddressService specialPickupAddressService) {
		this.specialPickupAddressService = specialPickupAddressService;
	}
	public void setCustomerDao(ICustomerDao customerDao) {
		this.customerDao = customerDao;
	}
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}


	public void setWaybillRelateDetailEntityService(
			IWaybillRelateDetailEntityService waybillRelateDetailEntityService) {
		this.waybillRelateDetailEntityService = waybillRelateDetailEntityService;
	}


	public void setOrderBusinessLockService(
			IOrderBusinessLockService orderBusinessLockService) {
		this.orderBusinessLockService = orderBusinessLockService;
	}


	public void setLeasedVehicleService(ILeasedVehicleService leasedVehicleService) {
		this.leasedVehicleService = leasedVehicleService;
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
	 * 
	 *           goodsName
	 * 				货物名称
	 * @return the smsParam
	 * @author 340411-foss-wanghui
	 * @date 2016-11-30 上午10:50:53
	 */
	private Map<String, String> getSmsParamByNetwork(OrderHandleDto orderHandleDto) {
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
		
		// 货物名称
		paramMap.put("goodsName", orderHandleDto.getGoodsName());

		// 司机姓名
		paramMap.put("driverName", orderHandleDto.getDriverName());
		// 司机手机
		paramMap.put("driverMobile", orderHandleDto.getDriverMobile());
		// 包装
		paramMap.put("goodsPackage", orderHandleDto.getGoodsPackage());
		return paramMap;
	}
	 /* @return the smsParam
			 * @author 340411-foss-wanghui
			 * @date 2016-11-30 上午10:50:53
			 */
	public String getSmsContentByNetwork(String smsCode, OrderHandleDto orderHandleDto) {
		String sms = "";
		// 模版参数
		SmsParamDto smsParamDto = new SmsParamDto();
		// 短信编码
		smsParamDto.setSmsCode(smsCode);
		// 部门编码
		smsParamDto.setOrgCode(FossUserContextHelper.getOrgCode());
		// 参数Map
		smsParamDto.setMap(this.getSmsParamByNetwork(orderHandleDto));
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
	 * 发送电子运单调度短信
	 * 
	 * @author 340411-foss-wanghui
	 * @date 2016-6-3 下午6:44:22
	 */
	@Override
	public void sendNetworkWaybillDispatchSms(OrderHandleDto orderHandleDto) {
		// TODO Auto-generated method stub
		try {
			// 约车smsCode
			String	smsCode =  DispatchOrderStatusConstants.TEMPLATE_NETWORKWAYBILLDISPATCH_SMS;
			sendSms(smsCode, orderHandleDto, true,NotifyCustomerConstants.BS_TYPE_PKP_ORDER);

			//LOGGER.info("sendSms start : order " + orderHandleDto + " isDriver " + isDriver);
			NotificationEntity notificationEntity = new NotificationEntity();
			// 设置订单号
			notificationEntity.setWaybillNo(orderHandleDto.getOrderNo());
			// 设置通知模块名  NotifyCustomerConstants.BS_TYPE_PKP_ORDER
			notificationEntity.setModuleName(NotifyCustomerConstants.BS_TYPE_PKP_ORDER);
			// 设置通知类型
			notificationEntity.setNoticeType(NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE);
			// 设置通知内容
			notificationEntity.setNoticeContent(this.getSmsContentByNetwork(smsCode, orderHandleDto));
			// 设置手机号

			notificationEntity.setMobile(orderHandleDto.getDriverMobile());
			notificationEntity.setConsignee(orderHandleDto.getDriverName());

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

		} catch (NotifyCustomerException e) {
			LOGGER.info("司机短信发送失败");
			LOGGER.error(e.getMessage(), e);	
			throw new DispatchException("发送短信异常");
		} catch (SMSSendLogException e) {
			LOGGER.info("司机短信发送失败");
			LOGGER.error(e.getMessage(), e);
			throw new DispatchException("发送短信异常");
		} catch (BusinessException e) {
			LOGGER.info("短信模板获取失败");
			LOGGER.error(e.getMessage(), e);
			throw new DispatchException("发送短信异常");
		} catch (Exception e){
			LOGGER.info("司机短信发送失败");
			LOGGER.error(e.getMessage(), e);
			throw new DispatchException("发送短信异常");
		}
	}
	
}