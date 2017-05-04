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
 * PROJECT NAME	: pkp-pickup
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pickup/server/service/impl/PdaOrderTaskHandleService.java
 * 
 * FILE NAME        	: PdaOrderTaskHandleService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pickup.server.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnDriverService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverAssociationDto;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderChangeEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.server.service.IAutoExpressAcceptService;
import com.deppon.foss.module.pickup.order.api.server.service.IAutoPdaReturnRecordService;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderTaskHandleService;
import com.deppon.foss.module.pickup.order.api.shared.define.DispatchOrderStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderChangeEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderHandleDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.PdaReturnDto;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaOrderTaskHandleService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaOrderDto;
import com.deppon.foss.module.pickup.pda.api.shared.exception.PdaProcessException;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.util.UUIDUtils;

/**
 * 转货完成 AND 更新调度订单接口
 * 
 * @author ibm-wangfei
 * @date Dec 11, 2012 3:36:09 PM
 */
public class PdaOrderTaskHandleService implements IPdaOrderTaskHandleService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PdaOrderTaskHandleService.class);
	// 调度订单DAO
	private IDispatchOrderEntityDao dispatchOrderEntityDao;
	// 人员 Service接口
	private IEmployeeService employeeService;
	// 组织信息 Service接口
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	// 订单处理的service
	private IOrderTaskHandleService orderTaskHandleService;
	/** 
	 * “公司司机”的数据库对应数据访问Service接口
	 */
	private IOwnDriverService ownDriverService;
	
	// 快递订单退回/转发service
	private IAutoExpressAcceptService autoExpressAcceptService;
	// 零担退回
	private IAutoPdaReturnRecordService autoPdaReturnRecordService;
	//变更表操作
	private IDispatchOrderChangeEntityDao dispatchOrderChangeEntityDao;
	
	/**
	 * 快递运单服务类
	 */
	private IWaybillExpressService waybillExpressService;
	
	// 查询外请车号码DAO
	private ILeasedVehicleDao leasedVehicleDao;
	/**
	 * 转货完成 更新调度订单接口.
	 *
	 * @param pdaOrderDto the pda order dto
	 * @return the string
	 * @throws PdaProcessException the pda process exception
	 * @author ibm-wangfei
	 * @date Dec 18, 2012 7:11:02 PM
	 * @see com.deppon.foss.module.pickup.pda.api.server.service.IPdaOrderTaskHandleService#updateOrder(com.deppon.foss.module.pickup.pda.api.shared.dto.PdaOrderDto)
	 */
	@Transactional
	@Override
	public String udpateOrder(PdaOrderDto pdaOrderDto)
			throws PdaProcessException {
		if(pdaOrderDto != null){
			LOGGER.info("PDA更新调度订单接口["+pdaOrderDto.getOrderNo()+"]:"+ReflectionToStringBuilder.toString(pdaOrderDto));
		}
		// 校验订单号
		if (pdaOrderDto == null || StringUtil.isEmpty(pdaOrderDto.getOrderNo())) {
			LOGGER.error("请传入订单号。");
			throw new PdaProcessException("请传入订单号。");
		}
		DispatchOrderConditionDto dispatchOrderConditionDto = new DispatchOrderConditionDto();
		dispatchOrderConditionDto.setOrderNo(pdaOrderDto.getOrderNo());
		List<String> orderStatusList = new ArrayList<String>();
		orderStatusList.add(DispatchOrderStatusConstants.STATUS_RETURN);
		orderStatusList.add(DispatchOrderStatusConstants.STATUS_PICKUP_FAILURE);
		dispatchOrderConditionDto.setOrderStatusList(orderStatusList);
		// 校验订单是否存在
		DispatchOrderEntity oldEntity = dispatchOrderEntityDao
				.queryOrdersByOrderNo(dispatchOrderConditionDto);
		if (oldEntity == null) {
			LOGGER.error("订单号不存在！");
			throw new PdaProcessException("订单号不存在！");
		}
		// 转货完成接口--转货订单,返回结果PDA未收到，PDA再次发送请求，若转货信息已完成，则直接返回结果信息“转货完成成功！”
		if (DispatchOrderStatusConstants.TYPE_TRANSFER_ORDER.equals(oldEntity
				.getOrderType())) {//转货订单
			if (DispatchOrderStatusConstants.STATUS_WAYBILL.equals(oldEntity
					.getOrderStatus())) {//已开单
				return "转货完成成功！";
			}
		}
		//DMANA-6208 快递员PDA退单退回流向
		if(DispatchOrderStatusConstants.PDA_RETURN_ORDER.equals(pdaOrderDto.getOptState())
				&& waybillExpressService.onlineDetermineIsExpressByProductCode(oldEntity.getProductCode(), new Date())){
				pdaOrderDto.setOrderStatus(DispatchOrderStatusConstants.STATUS_AGAIN_PICKUP);			
		}
		// 零担为“联系不上客户”和“地址不详/不准” 取消重复订单、违禁品、要求延迟接货 退回调度
		if(StringUtils.isNotEmpty(oldEntity.getProductCode())&&! waybillExpressService.onlineDetermineIsExpressByProductCode(oldEntity.getProductCode(), new Date())
				&&DispatchOrderStatusConstants.TYPE_PICKUP_ORDER.equals(oldEntity.getOrderType())){
			String retReason=pdaOrderDto.getReturnReason();
			if(DispatchOrderStatusConstants.REJECT_CONTACT_NO_CUSTOMER.equals(retReason)
					||DispatchOrderStatusConstants.REJECT_PICKUP_ADDRESS_ERROR.equals(retReason)
					||DispatchOrderStatusConstants.REJECT_CONTRABAND.equals(retReason)
					||DispatchOrderStatusConstants.REJECT_CANCEL_REPEAT_ORDERS.equals(retReason)
					||DispatchOrderStatusConstants.REJECT_REQUIRE_DELAY.equals(retReason)){
				pdaOrderDto.setOrderStatus(DispatchOrderStatusConstants.STATUS_AGAIN_PICKUP);
			}
		}
		//14.7.10 gcl AUTO-118 退回id设置为订单id
		pdaOrderDto.setId(oldEntity.getId());
		// 系统更新转货订单状态“已开单”
		DispatchOrderEntity newEntity = new DispatchOrderEntity();
		// 设置操作备注
		if(DispatchOrderStatusConstants.REJECT_ELSE_REASON.equals(pdaOrderDto.getReturnReason())){
			newEntity.setOperateNotes("其他-"+(pdaOrderDto.getRemark()==null?"":pdaOrderDto.getRemark()));
		}else {
			newEntity.setOperateNotes(pdaOrderDto.getReturnReason());
		}
		// 订单状态
		newEntity.setOrderStatus(pdaOrderDto.getOrderStatus());
		// 订单id
		newEntity.setId(oldEntity.getId());
		// 设置订单发送状态为发送成功
		newEntity
				.setOrderSendStatus(DispatchOrderStatusConstants.ORDER_SENDSUCCESS);
		dispatchOrderEntityDao.updateByPrimaryKeySelective(newEntity);
		// 系统调用中转接口更新约车状态“确认车辆到达”
		// 调用中转接口修改转货订单的接口
		OrderHandleDto orderHandleDto = getOrderHandleDto(oldEntity, newEntity,
				pdaOrderDto);
		OrderHandleDto crmOrderHandleDto = new OrderHandleDto();
		try {
			BeanUtils.copyProperties(orderHandleDto,crmOrderHandleDto);
			// 根据传入退回原因判断是否是REJECT_TFR_NONE_ORDER_CREATE_ORDER  已转无订单开单
			if(StringUtils.equals(DispatchOrderStatusConstants.REJECT_TFR_NONE_ORDER_CREATE_ORDER, pdaOrderDto.getReturnReason())){
				//设置成电子运单揽活失败，将CRM订单状态更新为REJECT【拒绝】
				crmOrderHandleDto.setOrderStatus(DispatchOrderStatusConstants.UNACTIVE_EWAYBILL_GENERATE_FAILURE);
			}
			// 更新派车记录状态
			orderTaskHandleService.updateDispatchVehicleRecord(orderHandleDto);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		// 新增退回操作功能
		try {
			if(StringUtils.isNotEmpty(oldEntity.getProductCode())) {
				String productCode = oldEntity.getProductCode();
				String optState = pdaOrderDto.getOptState();
				//如果是快递订单
				if( waybillExpressService.onlineDetermineIsExpressByProductCode(productCode, new Date())) {
					if(StringUtils.isNotEmpty(optState)//快递退回实现
							&&DispatchOrderStatusConstants.PDA_RETURN_ORDER.equals(pdaOrderDto.getOptState())) {
							PdaReturnDto pdaReturnDto = new PdaReturnDto();
							BeanUtils.copyProperties(pdaOrderDto,pdaReturnDto);
							autoExpressAcceptService.pdaReturnProcess(pdaReturnDto);
					}else if(StringUtils.isNotEmpty(optState)//快递转发实现
							&&DispatchOrderStatusConstants.PDA_FORWARD_ORDER.equals(optState)){
						PdaReturnDto pdaReturnDto = new PdaReturnDto();
						BeanUtils.copyProperties(pdaOrderDto,pdaReturnDto);
						autoExpressAcceptService.pdaForwardProcess(pdaReturnDto);
					}
				}else {//如果是零担订单
					if(StringUtils.isNotEmpty(pdaOrderDto.getReturnReason())&&DispatchOrderStatusConstants.TYPE_PICKUP_ORDER.equals(oldEntity.getOrderType())){
						String orderNo = oldEntity.getOrderNo();
						String returnType = pdaOrderDto.getReturnReason(); 
						if(DispatchOrderStatusConstants.REJECT_NOT_TODAY.equals(returnType)) {
							if(pdaOrderDto.getEarliestPickupTime()==null || pdaOrderDto.getLatestPickupTime()==null)
								throw new BusinessException("接货时间不能为空");
							//备注客户要求的接货时间，方便CRM跟踪订单情况
							String head = "客户要求非当日接货";
							StringBuffer pickupTime = new StringBuffer();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
							pickupTime.append("["+sdf.format(pdaOrderDto.getEarliestPickupTime())+"至");
							pickupTime.append(sdf.format(pdaOrderDto.getLatestPickupTime())+"]");
							crmOrderHandleDto.setOperateNotes(head+pickupTime.toString()+(StringUtils.isNotBlank(pdaOrderDto.getRemark())?pdaOrderDto.getRemark():""));
							//手动将状态置为RETURN，以便推送状态给CRM
							crmOrderHandleDto.setOrderStatus(DispatchOrderStatusConstants.STATUS_RETURN);
							orderHandleDto.setOperateNotes(head+pickupTime.toString());
							//回滚订单到未处理
							OrderHandleDto dto = new OrderHandleDto();
							dto.setId(oldEntity.getId());
							dto.setEarliestPickupTime(pdaOrderDto.getEarliestPickupTime());
							dto.setLatestPickupTime(pdaOrderDto.getLatestPickupTime());
							orderTaskHandleService.backOrderHandle(dto);
							 
							//在变更表中新增一天记录，到用车当日系统自动处理
							DispatchOrderChangeEntity dispatchOrderChangeEntity = new DispatchOrderChangeEntity();
							//id
							dispatchOrderChangeEntity.setId(UUIDUtils.getUUID());
							//changeId对应订单id
							dispatchOrderChangeEntity.setChangeId(oldEntity.getId());
							//jobid(N/A未处理)
							if(ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR.equals(productCode)
									||ProductEntityConstants.PRICING_PRODUCT_YARD_TO_YARD.equals(productCode)) {
								dispatchOrderChangeEntity.setJobId("B");
							}else {
								dispatchOrderChangeEntity.setJobId(DispatchOrderStatusConstants.CHANGE_ORDER_NO_QUERY);
							}
							// 变更时间
							SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd"); 
							SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String todayString = sdf1.format(new Date());
							String pickupTimeString = sdf1.format(pdaOrderDto.getEarliestPickupTime());
							if(todayString.equals(pickupTimeString)) {
								dispatchOrderChangeEntity.setChangeTime(new Date());
							}else {
								//确保非当日订单在9点后自动受理
								try {
									dispatchOrderChangeEntity.setChangeTime(sdf2.parse(pickupTimeString+" 08:03:00"));
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							// 设置产品类型
							dispatchOrderChangeEntity.setProductCode(oldEntity.getProductCode());
							// 设置接货城市
							dispatchOrderChangeEntity.setCityCode(oldEntity.getPickupCityCode());
							//设置最早接货时间
							dispatchOrderChangeEntity.setEarliestPickupTime(pdaOrderDto.getEarliestPickupTime());
							
							dispatchOrderChangeEntityDao.insertChange(dispatchOrderChangeEntity);
						}
						PdaReturnDto pdaReturnDto = new PdaReturnDto();
						BeanUtils.copyProperties(pdaOrderDto,pdaReturnDto);
						autoPdaReturnRecordService.pdaReturnProcess(orderNo,returnType,pdaReturnDto);
						//更新Oms订单状态为接货中；客户要求非当日接货还更新OMS状态给RETURN
						if(!DispatchOrderStatusConstants.REJECT_NOT_TODAY.equals(pdaOrderDto.getReturnReason())){
							crmOrderHandleDto.setOrderStatus(DispatchOrderStatusConstants.STATUS_PICKUPING);
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			//更新Oms订单状态为接货中；客户要求非当日接货还更新OMS状态给RETURN
			if(!DispatchOrderStatusConstants.REJECT_NOT_TODAY.equals(pdaOrderDto.getReturnReason())){
				crmOrderHandleDto.setOrderStatus(DispatchOrderStatusConstants.STATUS_PICKUPING);
			}
		}
		try {
			// 添加订单操作历史
			orderTaskHandleService.addDispatchOrderActionHistory(orderHandleDto);
			// 更新外部系统状态
			orderTaskHandleService.updateExternalSystem(crmOrderHandleDto,
						newEntity.getOperateNotes());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		LOGGER.info("udpateOrder end : "
				+ ReflectionToStringBuilder.toString(orderHandleDto));
		return "操作完成。";
	}

	/**
	 *
	 * @param dispatchOrderEntityDao the dispatchOrderEntityDao to see
	 */
	public void setDispatchOrderEntityDao(IDispatchOrderEntityDao dispatchOrderEntityDao) {
		this.dispatchOrderEntityDao = dispatchOrderEntityDao;
	}

	/**
	 *
	 * @param orderTaskHandleService the orderTaskHandleService to see
	 */
	public void setOrderTaskHandleService(IOrderTaskHandleService orderTaskHandleService) {
		this.orderTaskHandleService = orderTaskHandleService;
	}

	/**
	 * 设置传入接口参数.
	 *
	 * @param oldEntity the old entity
	 * @param newEntity the new entity
	 * @param pdaOrderDto the pda order dto
	 * @return the orderHandleDto
	 * @author ibm-wangfei
	 * @date Dec 12, 2012 3:47:23 PM
	 */
	private OrderHandleDto getOrderHandleDto(DispatchOrderEntity oldEntity, DispatchOrderEntity newEntity, PdaOrderDto pdaOrderDto) {
		OrderHandleDto orderHandleDto = new OrderHandleDto();
		// 设置ID
		orderHandleDto.setId(oldEntity.getId());
		// 订单类型
		orderHandleDto.setOrderType(oldEntity.getOrderType());
		// 订单号
		orderHandleDto.setOrderNo(oldEntity.getOrderNo());
		// 司机姓名
		orderHandleDto.setDriverName(oldEntity.getDriverName());
		if (!waybillExpressService.onlineDetermineIsExpressByProductCode(oldEntity.getProductCode(), new Date())) {
			//拿到司机的code
			String driverCode = oldEntity.getDriverCode();
			DriverAssociationDto driverAssociationDto = null;
			//add by 329757 判断司机是否是外请车
			if(!"000000".equals(driverCode)){
				// 内部司机根据工号查询相关信息
				driverAssociationDto = ownDriverService.queryOwnDriverAssociationDtoByDriverCode(driverCode);
				// 司机手机号
				orderHandleDto.setDriverMobile(driverAssociationDto.getDriverPhone());
			}else{
				//说明是外请车
				 String driverHhone = leasedVehicleDao.queryLeasedVehicleDriverByVehicleNo(oldEntity.getVehicleNo());
				orderHandleDto.setDriverMobile(driverHhone);
			}
		}
		// 根据传入的司机code设置操作人编码、操作人组织code
		if (StringUtil.isNotBlank(pdaOrderDto.getDriverCode())) {
			// 操作人编码
			orderHandleDto.setOperatorCode(pdaOrderDto.getDriverCode());
			// 操作人组织code
			// 根据人员编码，获取人员entity
			EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(pdaOrderDto.getDriverCode());
			if (emp != null) {
				// 根据人员标杆编码，获取组织编码
				OrgAdministrativeInfoEntity orgAdministrativeInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCode(emp.getUnifieldCode());
				orderHandleDto.setOperateOrgCode(orgAdministrativeInfo == null ? oldEntity.getOperateOrgCode() : orgAdministrativeInfo.getCode());
			} else {
				orderHandleDto.setOperateOrgCode(oldEntity.getOperateOrgCode());
			}
		} else {
			// 操作人编码
			orderHandleDto.setOperatorCode(oldEntity.getOperatorCode());
			// 操作人组织code
			orderHandleDto.setOperateOrgCode(oldEntity.getOperateOrgCode());
		}
		// 订单状态
		orderHandleDto.setOrderStatus(newEntity.getOrderStatus());
		// 操作备注
		orderHandleDto.setOperateNotes(newEntity.getOperateNotes());
		// 操作时间
		orderHandleDto.setOperateTime(new Date());
		return orderHandleDto;
	}

	/**
	 *
	 * @param employeeService the employeeService to see
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	/**
	 *
	 * @param orgAdministrativeInfoService the orgAdministrativeInfoService to see
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setOwnDriverService(IOwnDriverService ownDriverService) {
		this.ownDriverService = ownDriverService;
	}

	public void setAutoExpressAcceptService(
			IAutoExpressAcceptService autoExpressAcceptService) {
		this.autoExpressAcceptService = autoExpressAcceptService;
	}

	public void setAutoPdaReturnRecordService(
			IAutoPdaReturnRecordService autoPdaReturnRecordService) {
		this.autoPdaReturnRecordService = autoPdaReturnRecordService;
	}

	public void setDispatchOrderChangeEntityDao(
			IDispatchOrderChangeEntityDao dispatchOrderChangeEntityDao) {
		this.dispatchOrderChangeEntityDao = dispatchOrderChangeEntityDao;
	}
	
	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}

	public void setLeasedVehicleDao(ILeasedVehicleDao leasedVehicleDao) {
		this.leasedVehicleDao = leasedVehicleDao;
	}



}