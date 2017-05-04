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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pickup/server/service/impl/PdaDispatchOrderService.java
 * 
 * FILE NAME        	: PdaDispatchOrderService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pickup.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAcceptPointSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAccessPointService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPrintStarService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IReturnGoodsRequestEntityService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AccessPointEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressPrintStarEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ReturnGoodsRequestEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.FreightRouteLineDto;
import com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService;
import com.deppon.foss.module.base.common.api.shared.dto.SmsParamDto;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IPdaSignEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.ITruckResourceDao;
import com.deppon.foss.module.pickup.order.api.shared.define.DispatchOrderStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.define.PdaSignStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.PdaSignEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderHandleDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.VehicleInfoDto;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaDispatchOrderService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDispatchOrderDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaForwardDiverDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaOrderDto;
import com.deppon.foss.module.pickup.pda.api.shared.exception.PdaProcessException;
import com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IOnlineAddressDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillCHDtlPendingDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.define.OrderConstant;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ReturnBillingEWaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillCHDtlPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.DispatchEWaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillCustomerDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpressFeederPickupDetailDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpressFeederPickupQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.FreightRouteDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.QmsYchExceptionReportEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 查询未接订单接口
 * 
 * @author ibm-wangfei
 * @date Dec 5, 2012 8:10:54 PM
 */
public class PdaDispatchOrderService implements IPdaDispatchOrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PdaDispatchOrderService.class);
	
	private static final int NUMBER_1000 = 1000;
	// PDA签到DAO
	private IPdaSignEntityDao pdaSignEntityDao;
	// 调度订单DAO
	private IDispatchOrderEntityDao dispatchOrderEntityDao;
	// 组织信息 Service接口
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	//运单待补录表
	private IWaybillPendingDao waybillPendingDao;
	
	//员工服务类
	private IEmployeeService employeeService;
	
	private IWaybillManagerService waybillManagerService;
	
	private IReturnGoodsRequestEntityService returnGoodsRequestEntityService;
	
	/**
	 * 区域服务类
	 */
	private IAdministrativeRegionsService administrativeRegionsService;
	
	/**
	 * 产品类型服务类
	 */
	private IProductService productService;
	
	/**
	 * 综合 库区服务类
	 */
	private IGoodsAreaService goodsAreaService;
	
	/**
	 * (实现ExpressPrintStar的Service接口)
	 */
	 private IExpressPrintStarService expressPrintStarService;
	 
	 /**
	 * 综合 外场service接口
	 */
	private IOutfieldService outfieldService;
	
	/**
	 * 待处理运单费用
	 */
	private IWaybillCHDtlPendingDao waybillCHDtlPendingDao;
	
	/**
	 * 数据字典服务
	 */
	private IDataDictionaryValueService dataDictionaryValueService;
	
	/**
	 * 接驳点与营业部映射接口类
	 */
	private IAcceptPointSalesDeptService acceptPointSalesDeptService;
	
	private IStockService stockService;
	
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	/**
	 * 接驳点Service
	 */
	private IAccessPointService accessPointService;
	
	/**
	 * 通知客户Service
	 */
	private INotifyCustomerService notifyCustomerService;
	
	/**
	 * 短信模板Service
	 */
	private ISMSTempleteService sMSTempleteService;
	
	private IWaybillExpressService waybillExpressService;
	
	private ITruckResourceDao truckResourceDao;
	
	private ICustomerDao customerDao;
	
	private IConfigurationParamsService configurationParamsService;

	public void setAccessPointService(IAccessPointService accessPointService) {
		this.accessPointService = accessPointService;
	}
	
	public void setAcceptPointSalesDeptService(IAcceptPointSalesDeptService acceptPointSalesDeptService) {
		this.acceptPointSalesDeptService = acceptPointSalesDeptService;
	}
	
	public void setDataDictionaryValueService(IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}
	
	public void setWaybillCHDtlPendingDao(IWaybillCHDtlPendingDao waybillCHDtlPendingDao) {
		this.waybillCHDtlPendingDao = waybillCHDtlPendingDao;
	}
	
	public void setOutfieldService(IOutfieldService outfieldService) {
		this.outfieldService = outfieldService;
	}
	 
	 public void setExpressPrintStarService(IExpressPrintStarService expressPrintStarService) {
		this.expressPrintStarService = expressPrintStarService;
	}
	

	public void setReturnGoodsRequestEntityService(
			IReturnGoodsRequestEntityService returnGoodsRequestEntityService) {
		this.returnGoodsRequestEntityService = returnGoodsRequestEntityService;
	}

	/**
	 * 根据司机工号、车牌号未接订单接口接口.
	 * 
	 * @param driverCode the driver code
	 * @param vehicleNo the vehicle no
	 * @return the list
	 * @throws PdaProcessException the pda process exception
	 * @author ibm-wangfei
	 * @date Dec 18, 2012 7:11:31 PM
	 * @see com.deppon.foss.module.pickup.pda.api.server.service.IPdaDispatchOrderService#queryDispatchOrderByVehicle(java.lang.String,
	 *      java.lang.String)
	 */
	@Transactional
	@Override
	public List<PdaDispatchOrderDto> queryDispatchOrderByVehicle(String driverCode, String vehicleNo) throws PdaProcessException {
		if (StringUtil.isBlank(driverCode) && StringUtil.isBlank(vehicleNo)) {
			LOGGER.error("司机号码和车牌号不能全部为空。");
			throw new PdaProcessException("司机号码和车牌号不能全部为空。");
		}
		// 校验PDA是否绑定
		int rowNum = pdaSignEntityDao.querySignCountByDV(new PdaSignEntity(null, driverCode, vehicleNo, PdaSignStatusConstants.BUNDLE));
		if (rowNum <= 0) {
			throw new PdaProcessException("PDA没有绑定。");
		}

		// 校验是否有已派车的任务
		List<String> defaultOrderStatus = new ArrayList<String>();
		defaultOrderStatus.add(DispatchOrderStatusConstants.STATUS_DISPATCH_VEHICLE);
		DispatchOrderConditionDto dto = new DispatchOrderConditionDto();
		dto.setDriverCode(driverCode);
		dto.setVehicleNo(vehicleNo);
		dto.setDefaultOrderStatus(defaultOrderStatus);
		List<String> defaultOrderType = new ArrayList<String>();
		defaultOrderType.add(DispatchOrderStatusConstants.TYPE_PICKUP_ORDER);
		defaultOrderType.add(DispatchOrderStatusConstants.TYPE_TRANSFER_ORDER);
		dto.setDefaultOrderType(defaultOrderType);
		
//		dto.setIsPda(FossConstants.YES);
		// 查询符合条件的订单
		List<DispatchOrderEntity> dispatchOrderEntitys = dispatchOrderEntityDao.queryOrdersByPda(dto);
		List<PdaDispatchOrderDto> padDispatchOrderDtos = null;
		PdaDispatchOrderDto padDispatchOrderDto = null;
		if (CollectionUtils.isNotEmpty(dispatchOrderEntitys)) {
			padDispatchOrderDtos = new ArrayList<PdaDispatchOrderDto>();
			for (DispatchOrderEntity dispatchOrderEntity : dispatchOrderEntitys) {
				//14.8.5 gcl AUTO-218
				if(DispatchOrderStatusConstants.ORDER_ACCEPT_STATUS_HANDLE.equals(dispatchOrderEntity.getAcceptStatus())){
					dispatchOrderEntity.setForwardDriverCode("");
					dispatchOrderEntity.setForwardDriverName("");
				}
				//地址备注
				if(StringUtils.isNotEmpty(dispatchOrderEntity.getDeliveryCustomerAddressNote())){
					dispatchOrderEntity.setPickupElseAddress(dispatchOrderEntity.getPickupElseAddress()+"("+dispatchOrderEntity.getDeliveryCustomerAddressNote()+")");
				}
				if(StringUtils.isNotEmpty(dispatchOrderEntity.getReceiveCustomerAddressNote()))	{
					dispatchOrderEntity.setConsigneeAddress(dispatchOrderEntity.getConsigneeAddress()+"("+dispatchOrderEntity.getReceiveCustomerAddressNote()+")");
				}
				padDispatchOrderDto = new PdaDispatchOrderDto();
				setProperties(dispatchOrderEntity, padDispatchOrderDto);
				//收货联系人手机号不为空时传给PDA，为空时将收货联系人电话传给PDA
				if(StringUtil.isNotBlank(dispatchOrderEntity.getReceiverCustMobile())) {
					padDispatchOrderDto.setReceiverCustContactInfo(dispatchOrderEntity.getReceiverCustMobile());
				}else {
					padDispatchOrderDto.setReceiverCustContactInfo(dispatchOrderEntity.getReceiverCustPhone());
				}
				padDispatchOrderDtos.add(padDispatchOrderDto);
			}
			StringBuffer result = new StringBuffer();
			if(CollectionUtils.isNotEmpty(padDispatchOrderDtos)) {
				for(PdaDispatchOrderDto one : padDispatchOrderDtos) {
					result.append(ReflectionToStringBuilder.toString(one)).append("; ");
				}
			}
			LOGGER.error("PDA下拉结果["+driverCode+"-"+vehicleNo+"]:"+result.toString());
			return padDispatchOrderDtos;
		} else {
			return null;
		}
	}
	
	/**
	 * 根据客户编码进行电子运单进行数据的查询
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-7-16 18:53:44
	 */
	@Override
	public List<DispatchEWaybillDto> queryEWaybillOrderByCust(EWaybillConditionDto eWaybillConditionDto) {
		//进行数据的校验
		if(eWaybillConditionDto == null || StringUtils.isEmpty(eWaybillConditionDto.getDriverCode()) 
				|| StringUtils.isEmpty(eWaybillConditionDto.getVehicleNo())){
			throw new PdaProcessException("传入的参数为空，请稍候重试");
		}
		//设置对应的客户编码集合
		List<String> defaultOrderStatus = new ArrayList<String>();
		//订单状态：:1、NONE_HANDLE 未处理2、DISPATCH_VEHICLE 已派车3、PDA_ACCEPT PDA接收
		//4、PICKUPING 接货中5、AGAIN_PICKUP 待改接6、RETURN 已退回7、PICKUP_FAILURE 揽货失败
		//8、WAYBILL 已开单9、REVOCATION 已撤销10、CANCEL 已作废
		defaultOrderStatus.add(DispatchOrderStatusConstants.STATUS_DISPATCH_VEHICLE);
//		defaultOrderStatus.add(DispatchOrderStatusConstants.STATUS_AGAIN_PICKUP);
		eWaybillConditionDto.setOrderStatusList(defaultOrderStatus);
		//由于PDA传过来的手机号可能是固话 也可能是 手机号/固话，这边做下处理
		String mobilePhone = eWaybillConditionDto.getMobilePhone();
		if(mobilePhone!=null && !"".equals(mobilePhone)){
			String[] arrays = mobilePhone.split("/");
			if(arrays.length==2){
				eWaybillConditionDto.setMobilePhone(arrays[0].trim());
				eWaybillConditionDto.setOfficePhone(arrays[1].trim());
			}
		}
		List<DispatchEWaybillDto> orderDtoListTemp = waybillPendingDao.queryEWaybillByCust(eWaybillConditionDto);
		//PDA处理会更好一点
		//		List<String> orderList = new ArrayList<String>();
//		if(orderDtoListTemp!=null){
//			for(DispatchEWaybillDto vo : orderDtoListTemp){
//				if(vo.getOrderCode()!=null && !"".equals(vo.getOrderCode())){
//					orderList.add(vo.getOrderCode());
//				}
//			}
//		}
		//将下拉下来的订单状态改为PDA接收
//		dispatchOrderEntityDao.updateStatusByOrderNoList(orderList, DispatchOrderStatusConstants.STATUS_PDA_ACCEPT);
//		return revertBaseData(orderDtoListTemp);
		if(orderDtoListTemp!=null && orderDtoListTemp.size()>0){
			int size = orderDtoListTemp.size();
			int num =(size/NUMBER_1000)+1;
			List<String> orderList =null;
			for(int i=0;i<num;i++){
				orderList =new ArrayList<String>();
				int max = (i+1)*NUMBER_1000;
				if(max > size){
					max=size;
				}
				for(int j = i*NUMBER_1000;j<max;j++){
					DispatchEWaybillDto dto = orderDtoListTemp.get(j);
					if(dto!=null && !"".equals(dto.getOrderCode())){
						orderList.add(dto.getOrderCode());
					}
				}
				if(orderList!=null){
					dispatchOrderEntityDao.updateSendStatus(orderList, DispatchOrderStatusConstants.ORDER_SENDSUCCESS);
				}
			}
		}
		return orderDtoListTemp;
	}

	/**
	 * 根据司机和车牌号进行数据的查询
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-7-18 09:31:20
	 */
	

	/**
	 * 独立出一个方法，进行相关数据的封装，之所以不走一个方法进行查询出来，是怕以后执行计划发生变化，所以进行后台数据的封装，况且这些都是走缓存查询的，不存在多少延迟
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-7-25 08:34:02
	 * @param orderDtoListTemp
	 * @return
	 */
	private List<DispatchEWaybillDto> revertBaseData(List<DispatchEWaybillDto> orderDtoList) {
		if(CollectionUtils.isNotEmpty(orderDtoList)){
			List<DispatchEWaybillDto> orderDtoListTemp = new ArrayList<DispatchEWaybillDto>();
			DispatchEWaybillDto eWaybillOrderDto = null;
			for(int i=0;i<orderDtoList.size();i++){
				eWaybillOrderDto = orderDtoList.get(i);
				// 出发部门名称
				OrgAdministrativeInfoEntity orgAdministrativeInfoStart = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(eWaybillOrderDto.getOriginDeptCode());
				if(orgAdministrativeInfoStart != null){
					eWaybillOrderDto.setOriginDeptName(orgAdministrativeInfoStart.getName());
				}
				//到达部门
				OrgAdministrativeInfoEntity orgAdministrativeInfoEnd = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(eWaybillOrderDto.getDestDeptCode());
				if(orgAdministrativeInfoEnd != null){
					eWaybillOrderDto.setDestDeptName(orgAdministrativeInfoEnd.getName());
					//是否打印@
					if(StringUtils.isNotEmpty(orgAdministrativeInfoEnd.getName()) && orgAdministrativeInfoEnd.getName().indexOf(FossConstants.IS_START_EXPRESS) >= 0){
						eWaybillOrderDto.setIsPrintAt(FossConstants.YES);
					}else{
						eWaybillOrderDto.setIsPrintAt(FossConstants.NO);
					}
				}
				//收货人电话
				StringBuffer receiverPhone = new StringBuffer();
				if(StringUtils.isNotEmpty(eWaybillOrderDto.getReceivePhone())){
					if(StringUtils.isNotEmpty(eWaybillOrderDto.getReceiveMobilePhone())){
						receiverPhone.append(eWaybillOrderDto.getReceiveMobilePhone()+"/"+eWaybillOrderDto.getReceivePhone());
					}else{
						receiverPhone.append(eWaybillOrderDto.getReceivePhone());
					}
				}else{
					receiverPhone.append(eWaybillOrderDto.getReceiveMobilePhone());
				}
				//提货人电话
				eWaybillOrderDto.setAddresseeTel(receiverPhone.toString());
				StringBuffer sbreceiver = new StringBuffer();
				//收货客户地址
				//省份
				if(StringUtils.isNotEmpty(eWaybillOrderDto.getReceiveProCode())){
					AdministrativeRegionsEntity provEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(eWaybillOrderDto.getReceiveProCode());
		    		if(provEntity != null){
						sbreceiver.append(StringUtil.defaultIfNull(provEntity.getName()));
		    		}
				}//城市
				if(StringUtils.isNotEmpty(eWaybillOrderDto.getReceiveCityCode())){
					AdministrativeRegionsEntity cityEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(eWaybillOrderDto.getReceiveCityCode());
		    		if(cityEntity != null){
						sbreceiver.append(StringUtil.defaultIfNull(cityEntity.getName()));
		    		}
				}//地区
				if(StringUtils.isNotEmpty(eWaybillOrderDto.getReceiveDistCode())){
					AdministrativeRegionsEntity distEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(eWaybillOrderDto.getReceiveDistCode());
		    		if(distEntity != null){
						sbreceiver.append(StringUtil.defaultIfNull(distEntity.getName()));
		    		}
				}
				sbreceiver.append(StringUtil.defaultIfNull(eWaybillOrderDto.getReceiveAddress()));
				eWaybillOrderDto.setAddresseeAddr(sbreceiver.toString());
				
				//发货客户地址
				StringBuffer sbDeliver = new StringBuffer();
				//省份
				if(StringUtils.isNotEmpty(eWaybillOrderDto.getDeliveryProCode())){
					AdministrativeRegionsEntity provEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(eWaybillOrderDto.getDeliveryProCode());
		    		if(provEntity != null){
		    			sbDeliver.append(StringUtil.defaultIfNull(provEntity.getName()));
		    		}
				}//城市
				if(StringUtils.isNotEmpty(eWaybillOrderDto.getDeliveryCityCode())){
					AdministrativeRegionsEntity cityEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(eWaybillOrderDto.getDeliveryCityCode());
		    		if(cityEntity != null){
		    			sbDeliver.append(StringUtil.defaultIfNull(cityEntity.getName()));
		    		}
				}//地区
				if(StringUtils.isNotEmpty(eWaybillOrderDto.getDeliveryDistCode())){
					AdministrativeRegionsEntity distEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(eWaybillOrderDto.getDeliveryDistCode());
		    		if(distEntity != null){
		    			sbDeliver.append(StringUtil.defaultIfNull(distEntity.getName()));
		    		}
				}
				sbDeliver.append(StringUtil.defaultIfNull(eWaybillOrderDto.getDeliveryAddress()));
				eWaybillOrderDto.setSenderAddr(sbDeliver.toString());
				//发货人电话
				StringBuffer senderPhone = new StringBuffer();
				if(StringUtils.isNotEmpty(eWaybillOrderDto.getDeliveryPhone())){
					if(StringUtils.isNotEmpty(eWaybillOrderDto.getDeliveryMobilePhone())){
						senderPhone.append(eWaybillOrderDto.getDeliveryPhone()+"/"+eWaybillOrderDto.getDeliveryMobilePhone());
					}else{
						senderPhone.append(eWaybillOrderDto.getDeliveryPhone());
					}
				}else{
					senderPhone.append(eWaybillOrderDto.getDeliveryMobilePhone());
				}
				eWaybillOrderDto.setSenderTel(senderPhone.toString());
				//设置优惠活动信息
				List<WaybillCHDtlPendingEntity> list = waybillCHDtlPendingDao.queryCHDtlPendingByNo(eWaybillOrderDto.getWaybillCode());
				if(list != null && list.size()>0){
					for(int j=0;j<list.size();j++){
						if(j == list.size() - 1){
							eWaybillOrderDto.setMarketingInfo(list.get(i).getActiveCode()+":"+list.get(i).getActiveName());
						}else{
							eWaybillOrderDto.setMarketingInfo(list.get(i).getActiveCode()+":"+list.get(i).getActiveName()+"/");
						}
					}
				}
				//付款方式：经过沟通，PDA已经进行转换，我们这边无需进行转换
				/*if(StringUtils.isNotEmpty(eWaybillOrderDto.getPayType())){
					DataDictionaryValueEntity data = dataDictionaryValueService
							.queryDataDictionaryValueByTermsCodeValueCode(WaybillConstants.PAYMENT_MODE, eWaybillOrderDto.getPayType());
					if(data != null){
						//付款方式
						eWaybillOrderDto.setPayType(StringUtil.defaultIfNull(data.getValueName()));
					}
				}*/
				//受否签收回单
				if(StringUtils.isNotEmpty(eWaybillOrderDto.getReturnBillingType())){
					DataDictionaryValueEntity data = dataDictionaryValueService
							.queryDataDictionaryValueByTermsCodeValueCode(WaybillConstants.RETURN_BILL_TYPE, eWaybillOrderDto.getReturnBillingType());
					if(data != null){
						eWaybillOrderDto.setReturnBillingType(StringUtil.defaultIfNull(data.getValueName()));
					}
				}
				//设置第二城市已经第二外场
				setRouteInfoAndSecondOutField(eWaybillOrderDto);
				orderDtoListTemp.add(eWaybillOrderDto);
			}
			return orderDtoListTemp;
		}
		return null;
	}

	/**
	 * 设置返回dto信息.
	 * 
	 * @param dispatchOrderEntity the dispatch order entity
	 * @param padDispatchOrderDto the pad dispatch order dto
	 * @author ibm-wangfei
	 * @date Dec 6, 2012 11:38:46 AM
	 */
	private void setProperties(DispatchOrderEntity dispatchOrderEntity, PdaDispatchOrderDto padDispatchOrderDto) {
		// 拷贝订单entity到订单dto
		BeanUtils.copyProperties(dispatchOrderEntity, padDispatchOrderDto);
		try {
			// 营业部联系电话
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(dispatchOrderEntity.getSalesDepartmentCode());
			padDispatchOrderDto.setSalesDepartmentTel(orgAdministrativeInfoEntity == null ? "" : orgAdministrativeInfoEntity.getDepTelephone());
		} catch (Exception e) {
			LOGGER.error("error", e);
		}
	}
	
	/**
	 * 获取走货线路上第二城市外场部门简称（即出发营业部对应外场所在城市的一下城市对应的第一个外场）：
	 * 1）若为同城（即全部外场在同一城市，或只有一个外场），则返回提货网点对应的部门名称（包快递代理网点名称）
	 * 2）若非同城（即至少有2个外场对应的城市不一样）
	 * @author 026123-foss-lifengteng
	 * @date 2013-9-29 下午5:31:05
	 */
	private void setRouteInfoAndSecondOutField(DispatchEWaybillDto eWaybillOrderDto){
		//获取走货线路
		FreightRouteDto freightRouteDto = waybillManagerService.queryFreightRouteBySourceTarget(eWaybillOrderDto.getOriginDeptCode(), eWaybillOrderDto.getDestDeptCode(), eWaybillOrderDto.getTransType(), new Date());
		//判断是否为空
		if (CollectionUtils.isNotEmpty(freightRouteDto.getFreightRouteLinelist())) {
			List<FreightRouteLineDto> freightRouteLinelist = freightRouteDto.getFreightRouteLinelist();//获得走货路径list
			//获取最终配载部门编码
			String lastChangeCenterOrgCode = freightRouteLinelist.get(freightRouteLinelist.size() - 1).getSourceCode();
			//进行数据的赋值
			if(StringUtils.isNotEmpty(lastChangeCenterOrgCode)){
				OrgAdministrativeInfoEntity lastChangeCenterOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(lastChangeCenterOrgCode);
				if(lastChangeCenterOrg != null){
					eWaybillOrderDto.setFinalField(lastChangeCenterOrg.getName());
				}
			}
			// 得到途径始发营业部编码和外场编码 , 到达营业部 编码集合LIST A-C C-D D-B 得到这种格式
			List<String> addressInfoList = new ArrayList<String>();
			//拼接走货路径
			for (FreightRouteLineDto f : freightRouteLinelist) {
				addressInfoList.add(f.getSourceCode() + "-" + f.getTargetCode());
			}
			
			// 根据始发外场code 和外场集合 删除重复的外场 得到A C D B 格式的外场集合同时包含出发部门到达部门
			List<String> departmentInfoList = removeDuplicateRoute(addressInfoList);
			String firstCityName = null;
			//判断是否有外场
			if(departmentInfoList.size() >= 2){
				//根据组织编码查询组织信息
				OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(departmentInfoList.get(1));
				//出发外场编码对应的城市名称
				if(null != orgInfo){
					firstCityName = orgInfo.getCityName();
					eWaybillOrderDto.setLeaveCity(orgInfo.getCityName());
					eWaybillOrderDto.setDepartCityName(orgInfo.getCityName());
				}
				
				//若只有2个，则一定为同城
				if(departmentInfoList.size() == 2){
					//若为同城则设置第二城市外场简称为目的场
					eWaybillOrderDto.setSecField(eWaybillOrderDto.getDestDeptName());
					return;
				}
				// 判断外场个数（去除出发部门）
				else{
					//外场所在城市名称
					String loadCityName = "";
					//遍历集合（从第2个开始遍历），查找不同城市的外场编码
					for (int i=1; i<=(departmentInfoList.size()-1); i++) {
						OrgAdministrativeInfoEntity loadOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(departmentInfoList.get(i));
						if(null != loadOrg){
							//城市名称是否为空
							if(StringUtils.isEmpty(loadOrg.getCityName())){
								AdministrativeRegionsEntity entityInfo = administrativeRegionsService.queryAdministrativeRegionsByCodeNotCache(loadOrg.getCityCode());
								if(null != entityInfo){
									loadCityName = entityInfo.getName();
								}
							}else{
								//获得外场城市名
								loadCityName = StringUtil.defaultIfNull(loadOrg.getCityName()).trim();
							}
						}
						
						//外场所在城市编码与出发外场所在城市不一样
						if(loadOrg != null && StringUtil.isNotEmpty(firstCityName) && StringUtil.isNotEmpty(loadCityName) && !firstCityName.equals(loadCityName) ){
							//找到则设置第二城市外场简称,直接退出
							eWaybillOrderDto.setSecField(loadOrg.getOrgSimpleName());
							return;
						}
					}
					
					//若为同城则设置第二城市外场简称为目的场
					eWaybillOrderDto.setSecField(eWaybillOrderDto.getDestDeptName());
				}
			}else{
				throw new WaybillValidateException("该走货线路错误：只有出发部门和到达部门，没有外场！");
			}
		}
		//暂时注释掉查询外场走货外场，后期需要可以打开注释进行查询
		/*if(CollectionUtils.isNotEmpty(freightRouteDto.getFreightRouteLinelist())){
			List<String> addressInfoList = new ArrayList<String>();
			List<String> goodsAreaCodeList = new ArrayList<String>();
			List<FreightRouteLineDto> freightRouteLineList = freightRouteDto.getFreightRouteLinelist();//获得走货路径list
			String lastChangeCenterOrgCode = freightRouteLineList.get(freightRouteLineList.size() - 1).getSourceCode();
			//判定是否外发营业部这种情况
			String isNoStopDeptCode = freightRouteLineList.get(freightRouteLineList.size() - 1).getTargetCode();
			OrgAdministrativeInfoEntity noStopDept = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(isNoStopDeptCode, new Date());
			if(noStopDept != null){
				if(StringUtils.isNotEmpty(noStopDept.getName()) && noStopDept.getName().startsWith("出发快递营业部")){
					//是否打印@
					eWaybillOrderDto.setIsPrintAt(FossConstants.YES);
				}
			}
			
			// 最终外场编码 == 最终外场编码等于 走货路径的最后一条的 出发部门
			// lastChangeCenterOrgCode =
			// lastCenterOrgCode.substring(0,lastCenterOrgCode.indexOf(SLASH));
			addressInfoList.add(freightRouteLineList.get(0).getTargetCode());
			// 空运、偏线查货区
			if (ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(eWaybillOrderDto.getTransType()) || ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(eWaybillOrderDto.getTransType())) {
				for (int i = 1; i < freightRouteLineList.size(); i++) {
					addressInfoList.add(freightRouteLineList.get(i).getTargetCode());
					LOGGER.info("===除出发营业部以外走货路径===" + freightRouteLineList.get(i).getTargetCode());
					String goodsAreaCode = null;
					// 根据走货路径获得货区号
					if (i == freightRouteLineList.size() - 1) {
						List<GoodsAreaEntity> goodsAreaEntities = new ArrayList<GoodsAreaEntity>();
						// 空运货区
						if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(eWaybillOrderDto.getTransType())) {
							goodsAreaEntities = goodsAreaService.queryGoodsAreaListByType(freightRouteLineList.get(i).getSourceCode(), DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
						} else {
							// 偏线货区
							goodsAreaEntities = goodsAreaService.queryGoodsAreaListByType(freightRouteLineList.get(i).getSourceCode(), DictionaryValueConstants.BSE_GOODSAREA_TYPE_OTHER);
						}
						if (goodsAreaEntities.size() > 0) {
							goodsAreaCode = goodsAreaEntities.get(0).getGoodsAreaCode();
						}
					} else {
						goodsAreaCode = goodsAreaService.queryCodeByArriveRegionCode(freightRouteLineList.get(i).getSourceCode(), freightRouteLineList.get(i).getTargetCode(), eWaybillOrderDto.getTransType());
						*//**** yhj at 2013-05-09 add start *****//*
						//	是否追加★符号，不是最后一个记录且还没有打过标记
				    	if(!StringUtils.equalsIgnoreCase(eWaybillOrderDto.getIsStarFlag(),FossConstants.YES)){
				    		isPrintBseAsteriskGoodsArea(freightRouteLineList.get(i), eWaybillOrderDto);
				    	}
					}
					goodsAreaCodeList.add(goodsAreaCode);
				}
			} else {
				// 专线查货区--------快递库区编码查询3357
				for (int i = 1; i < freightRouteLineList.size(); i++) {
					addressInfoList.add(freightRouteLineList.get(i).getTargetCode());
					LOGGER.info("===除出发营业部以外走货路径===" + freightRouteLineList.get(i).getTargetCode());
					String goodsAreaCode = null;
					if(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE.equals(eWaybillOrderDto.getTransType()) 
							||ProductEntityConstants.PRICING_PRODUCT_ROUND_COUPON_PACKAGE.equals(eWaybillOrderDto.getTransType())){
						goodsAreaCode = expressPrintStarService.queryCodeByArriveRegionCode(freightRouteLineList.get(i).getSourceCode(), freightRouteLineList.get(i).getTargetCode(), eWaybillOrderDto.getTransType());
						if(StringUtils.isEmpty(goodsAreaCode)){
							goodsAreaCode = goodsAreaService.queryCodeByArriveRegionCode(freightRouteLineList.get(i).getSourceCode(), freightRouteLineList.get(i).getTargetCode(), eWaybillOrderDto.getTransType());
						}
					}else{
						goodsAreaCode = goodsAreaService.queryCodeByArriveRegionCode(freightRouteLineList.get(i).getSourceCode(), freightRouteLineList.get(i).getTargetCode(), eWaybillOrderDto.getTransType());
					}
					//是否追加★符号,不看最后一个且星标记没有被标记
					if(!StringUtils.equalsIgnoreCase(eWaybillOrderDto.getIsStarFlag(),FossConstants.YES)){
					    isPrintBseAsteriskGoodsArea(freightRouteLineList.get(i), eWaybillOrderDto);
					}
					goodsAreaCodeList.add(goodsAreaCode);
				}
			}
		
		}*/
	}
	
	/**
	 * 查询外场名称
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-8-27 20:34:59
	 * @param pOrgCode
	 * @return
	 */
	private String queryOutfieldName(String pOrgCode) {
		OutfieldEntity outfieldEntity = null;
		// 判断 是否为空运总调
		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(pOrgCode);
		if (orgEntity != null) {
			if (FossConstants.YES.equals(orgEntity.getAirDispatch())) {
				// 空运查外场
				outfieldEntity = outfieldService.queryOutfieldEntityByAirDispatchCode(pOrgCode);
			} else {
				// 偏线、专线查外场
				outfieldEntity = outfieldService.queryOutfieldByOrgCode(pOrgCode);
			}
		}
		return outfieldEntity == null ? null : outfieldEntity.getName();
	}
	
	private void isPrintBseAsteriskGoodsArea(FreightRouteLineDto currentRouteLineDto, DispatchEWaybillDto eWaybillOrderDto){
	//如果是经济快递运单,判断是否需要打星号标记
	if(productService.onlineDetermineIsExpressByProductCode(eWaybillOrderDto.getTransType(), new Date())){
		ExpressPrintStarEntity expressPrintStarEntity = expressPrintStarService.queryExpressPrintStarByArriveRegionCode(currentRouteLineDto.getSourceCode(), currentRouteLineDto.getTargetCode());;
		if(null!=expressPrintStarEntity){
    	    if(StringUtils.equalsIgnoreCase(expressPrintStarEntity.getAsteriskCode(), DictionaryValueConstants.ASTERISK_TYPE_LINE1)){
    	    	eWaybillOrderDto.setIsStarFlag("true");
    	    }
		}		
	}else{
    	GoodsAreaEntity goodsAreaEntity = null;
		//获得下一个到达站点对应的库区实体。判断库区实体是否需要打星号标记
    	goodsAreaEntity = goodsAreaService.queryGoodsAreaByArriveRegionCode(currentRouteLineDto.getSourceCode(), currentRouteLineDto.getTargetCode(), eWaybillOrderDto.getTransType());
    	if(null!=goodsAreaEntity){
    	    if(StringUtils.equalsIgnoreCase(goodsAreaEntity.getAsteriskCode(), DictionaryValueConstants.ASTERISK_TYPE_LINE1)){
    	    	eWaybillOrderDto.setIsStarFlag("true");
    	    }
		}
    }
}

	/**
	 * 根据第一个外场 从外场集合串 取出单个外场列表 删除重复的
	 * @author 026123-foss-lifengteng
	 * @date 2013-9-29 下午6:25:50
	 */
	private List<String> removeDuplicateRoute(List<String> routeList) {
		List<String> temp = new ArrayList<String>();
		for (int i = 0; i < routeList.size(); i++) {
			temp.add(routeList.get(i).substring(0, routeList.get(i).indexOf("-")));
		}
		return temp;
	}

	/**
	 * 
	 * @param dispatchOrderEntityDao
	 */
	public void setDispatchOrderEntityDao(IDispatchOrderEntityDao dispatchOrderEntityDao) {
		this.dispatchOrderEntityDao = dispatchOrderEntityDao;
	}

	/**
	 * 
	 * @param pdaSignEntityDao
	 */
	public void setPdaSignEntityDao(IPdaSignEntityDao pdaSignEntityDao) {
		this.pdaSignEntityDao = pdaSignEntityDao;
	}

	/**
	 * 
	 * @param orgAdministrativeInfoService
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	
	public void setWaybillPendingDao(IWaybillPendingDao waybillPendingDao) {
		this.waybillPendingDao = waybillPendingDao;
	}
	
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	
	public void setAdministrativeRegionsService(IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	public void setGoodsAreaService(IGoodsAreaService goodsAreaService) {
		this.goodsAreaService = goodsAreaService;
	}
		
	/**
	 * 
	 * @Title: queryForwardListByDriverCode 
	 * @Description: 根据当前登陆快递员工号查询对应区域下所有快递员集合
	 * @param @param driverCode
	 * @param @return    设定文件 
	 * @return List<PdaForwardDiverDto>    返回类型 
	 * @throws
	 */
	@Override
	public List<PdaForwardDiverDto> queryForwardListByDriverCode(
			String driverCode) {
		LOGGER.error("查询可转发快递人员开始......");
		if (StringUtil.isBlank(driverCode)) {
			LOGGER.error("快递员工号不能为空");
			throw new PdaProcessException("快递员工号不能为空");
		}
		try{
			List<OwnTruckDto> ownDtoList = pdaSignEntityDao.queryExpressDriverByDistirct(driverCode);
			List<PdaForwardDiverDto> pdaDriverList = null;
			if(!CollectionUtils.isEmpty(ownDtoList)
					&& ownDtoList.size() > 0){
				pdaDriverList = new ArrayList<PdaForwardDiverDto>();
				for(OwnTruckDto ownTruckDto : ownDtoList){
					PdaForwardDiverDto pdaDriver = new PdaForwardDiverDto();
					pdaDriver.setDriverCode(ownTruckDto.getDriverCode());
					pdaDriver.setDriverName(ownTruckDto.getDriverName());
					pdaDriverList.add(pdaDriver);
				}
			}
			LOGGER.error("查询可转发快递人员结束......");
			return pdaDriverList;
		}catch(Exception e){
			LOGGER.error("查询同区域下快递员集合异常");
			throw new PdaProcessException("查询同区域下快递员集合异常");
		}
		
	}


	@Override
	public List<DispatchEWaybillDto> queryIndividualCustEwaybill(EWaybillConditionDto eWaybillConditionDto) {
		if (StringUtil.isBlank(eWaybillConditionDto.getDriverCode()) && StringUtil.isBlank(eWaybillConditionDto.getVehicleNo())) {
			LOGGER.error("司机号码和车牌号不能全部为空。");
			throw new PdaProcessException("司机号码和车牌号不能全部为空。");
		}
		// 校验PDA是否绑定
		/*int rowNum = pdaSignEntityDao.querySignCountByDV(new PdaSignEntity(null, driverCode, vehicleNo, PdaSignStatusConstants.BUNDLE));
		if (rowNum <= 0) {
			throw new PdaProcessException("PDA没有绑定。");
		}*/
		List<String> defaultOrderStatus = new ArrayList<String>();
		//订单状态：:1、NONE_HANDLE 未处理2、DISPATCH_VEHICLE 已派车3、PDA_ACCEPT PDA接收
		//4、PICKUPING 接货中5、AGAIN_PICKUP 待改接6、RETURN 已退回7、PICKUP_FAILURE 揽货失败
		//8、WAYBILL 已开单9、REVOCATION 已撤销10、CANCEL 已作废
		defaultOrderStatus.add(DispatchOrderStatusConstants.STATUS_DISPATCH_VEHICLE);
		defaultOrderStatus.add(DispatchOrderStatusConstants.STATUS_AGAIN_PICKUP);
		eWaybillConditionDto.setOrderStatusList(defaultOrderStatus);
		//后期变化查询条件，只需要进行相对应的查询条件eWaybillConditionDto的改变即可
		List<DispatchEWaybillDto> orderDtoList = waybillPendingDao.queryIndividualCustEwaybill(eWaybillConditionDto);
		return revertBaseData(orderDtoList);
	}

	/**
	 * 根据大客户编码查询客户信息
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-7-16 14:02:39
	 */
	@Override
	public List<EWaybillCustomerDto> queryEWaybillOrderBigCust(EWaybillConditionDto eWaybillConditionDto) {
		if (StringUtil.isBlank(eWaybillConditionDto.getDriverCode()) && StringUtil.isBlank(eWaybillConditionDto.getVehicleNo())) {
			LOGGER.error("司机号码和车牌号不能全部为空。");
			throw new PdaProcessException("司机号码和车牌号不能全部为空。");
		}
		List<String> defaultOrderStatus = new ArrayList<String>();
		//订单状态：:1、NONE_HANDLE 未处理2、DISPATCH_VEHICLE 已派车3、PDA_ACCEPT PDA接收
		//4、PICKUPING 接货中5、AGAIN_PICKUP 待改接6、RETURN 已退回7、PICKUP_FAILURE 揽货失败
		//8、WAYBILL 已开单9、REVOCATION 已撤销10、CANCEL 已作废
		defaultOrderStatus.add(DispatchOrderStatusConstants.STATUS_DISPATCH_VEHICLE);
//		defaultOrderStatus.add(DispatchOrderStatusConstants.STATUS_AGAIN_PICKUP);
//		defaultOrderStatus.add(DispatchOrderStatusConstants.STATUS_PDA_ACCEPT);
		eWaybillConditionDto.setOrderStatusList(defaultOrderStatus);
		//开始进入相关数据的 查询
		//首先查询出对应联系人所对应的电子运单总数
		List<EWaybillCustomerDto> eWaybillCustomerDtoTemList = waybillPendingDao.queryEWaybillBigCust(eWaybillConditionDto);
		
		//根据客户联系人编码查询对应的详细信息
		if(CollectionUtils.isNotEmpty(eWaybillCustomerDtoTemList)){
			//由于查询出来的数据同一个大客户，会对应多条数据(已采集的，未采集的)，因此用此map进行合并,map 的key 是用 发货联系人ID和发货地址,发货联系人手机和固定电话联合主键作为唯一标示
			//也就是同一个发货联系人A 发货地址A 的单子合并到一起，发货联系人A 发货地址B的单子合并到一起
			Map<String,EWaybillCustomerDto> result = new HashMap<String, EWaybillCustomerDto>();
			List<EWaybillCustomerDto> eWaybillCustomerDtoList = new ArrayList<EWaybillCustomerDto>();
			for(EWaybillCustomerDto eWaybillCustomerDto : eWaybillCustomerDtoTemList){
				
				if(StringUtils.isNotEmpty(eWaybillCustomerDto.getContactCode())){
					EWaybillCustomerDto dataObject = result.get(eWaybillCustomerDto.getContactCode()+"_"+eWaybillCustomerDto.getAddress()+"_"+eWaybillCustomerDto.getMobilePhone()+"_"+eWaybillCustomerDto.getOfficePhone());
					if(dataObject==null){
						dataObject = eWaybillCustomerDto;
					}else{
						int newNumber = eWaybillCustomerDto.geteWaybillTotal();
						String isCollectGps = dataObject.getIsCollectGps();
						int oldNumber = dataObject.geteWaybillTotal();
						dataObject.seteWaybillTotal(newNumber+oldNumber);
						if(!FossConstants.YES.equals(isCollectGps)){
							dataObject.setIsCollectGps(eWaybillCustomerDto.getIsCollectGps());
						}
					}
					result.put(eWaybillCustomerDto.getContactCode()+"_"+eWaybillCustomerDto.getAddress()+"_"+eWaybillCustomerDto.getMobilePhone()+"_"+eWaybillCustomerDto.getOfficePhone(), dataObject);
				}else{
					eWaybillCustomerDtoList.add(eWaybillCustomerDto);
				}
			}
			//遍历合并后的Map，返回信息
			Iterator<Entry<String, EWaybillCustomerDto>> iter = result.entrySet().iterator();
			while(iter.hasNext()){
				Entry<String,EWaybillCustomerDto> entry =  iter.next();
				EWaybillCustomerDto eWaybillCustomerDto = entry.getValue();
				eWaybillCustomerDtoList.add(eWaybillCustomerDto);
			}
			return eWaybillCustomerDtoList;
		}
		return null;
	}
	@Resource
	private IOnlineAddressDao onlineAddressDao;
	//--收货人信息，保价金额，代收货款，签收单，包装费，运费，到付款合计
   @Override
   public ReturnBillingEWaybillEntity getReturnWaybillEntity(String originalWaybillNo,String changCode){
	   ReturnGoodsRequestEntity condition = new ReturnGoodsRequestEntity();
	   condition.setOriWaybill(originalWaybillNo);
	   //增加校验逻辑，判定原单是否存在于到达部门库存
	   boolean isInStock = stockService.queryStockByWaybillNo(originalWaybillNo);
	   //是否为子母件
	   TwoInOneWaybillDto isTowInOne=waybillManagerService.queryWaybillRelateByWaybillOrOrderNo(originalWaybillNo);
	   if(isInStock && FossConstants.YES.equals(isTowInOne.getIsTwoInOne())){
		   throw new PdaProcessException("此票货物不在营业部库存中，不允许返货开单");
	   }
		List<ReturnGoodsRequestEntity> list = returnGoodsRequestEntityService
				.queryReturnGoodsRequestEntityByCondition(condition);
		ReturnGoodsRequestEntity re = null;
		if(CollectionUtils.isNotEmpty(list)||(null!=changCode&&"PRINT".equals(changCode))){
			if(list.size()==1){
				 re = list.get(0);
			}else if(list.size()>1){
				for(ReturnGoodsRequestEntity g : list){
					if(null==re){
						re = g;
					}else{
						if(g.getTimeReport().after(re.getTimeReport())){
							re = g;
						}
					}
				}
			}
			
			ReturnBillingEWaybillEntity en =new ReturnBillingEWaybillEntity();
			WaybillEntity wayen = waybillManagerService.queryWaybillBasicByNo(originalWaybillNo);
			if(WaybillConstants.RETURNTYPE_OTHER_CITY.equals(re.getReturnType()) ||  
					WaybillConstants.RETURNTYPE_CUSTORMER_REFUSE.equals(re.getReturnType())||
					WaybillConstants.RETURNTYPE_SAME_CITY.equals(re.getReturnType())||
					WaybillConstants.RETURN_BACK.equals(re.getReturnType())||(null!=changCode&&"PRINT".equals(changCode))){
			

			if(null != wayen){
				//en.setDestination(wayen.getTargetOrgCode());
				en.setPieces(wayen.getGoodsQtyTotal().toString());
				ProductEntity product = productService.getLevel3ProductInfo(wayen.getProductCode());
				en.setTransportType(product == null ? wayen.getProductCode() : product.getName());
				en.setCutsetrPickuRgname(wayen.getCustomerPickupOrgName());
				en.setDestination(wayen.getCustomerPickupOrgCode());
				String entityName=	orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(wayen.getCustomerPickupOrgCode());
				en.setTransportCityName(entityName);
			}
			//受理状态
			if((null!=changCode&&"PRINT".equals(changCode))){
				en.setReturnStatus("");
			}else{
			en.setReturnStatus(re.getReturnStatus());
			}
			/*if(null!=re){
//				en.setReturnGoodsInfo("代收:"+re.getReturnMoneyReceive()+" 保价:"+re.getReturnMoneyInsured()+" 收货人姓名:"+re.getReturnManReceive()+" 收货人手机/收货人电话:"+re.getReturnPhoneReceive()+"/"+re.getReturnTelReceive()+" 收货人地址:"+re.getReturnProvince()+re.getReturnCity()+re.getReturnArea()+re.getReturnDetailaddress());
				en.setReturnGoodsInfo("收货人姓名:"+re.getReturnManReceive()+" 收货人手机/收货人电话:"+re.getReturnPhoneReceive()+"/"+re.getReturnTelReceive()+" 收货人地址:"+re.getReturnProvince()+re.getReturnCity()+re.getReturnArea()+re.getReturnDetailaddress());
			}*/	
			String proviceName = onlineAddressDao.queryProviceByCode(wayen.getReceiveCustomerProvCode());
			String cityName = onlineAddressDao.queryProviceByCode(wayen.getReceiveCustomerCityCode());
			String countyName = onlineAddressDao.queryProviceByCode(wayen.getReceiveCustomerDistCode());
			 
			String address = "";
			if(StringUtils.isNotEmpty(wayen.getReceiveCustomerName())){
				address=address+wayen.getReceiveCustomerName()+",";
			}
			//
			if(StringUtils.isNotEmpty(wayen.getReceiveCustomerMobilephone())&&
					StringUtils.isNotEmpty(wayen.getReceiveCustomerPhone())){
				address=address+wayen.getReceiveCustomerMobilephone()+"/"+wayen.getReceiveCustomerPhone()+",";
			}else if(StringUtils.isNotEmpty(wayen.getReceiveCustomerMobilephone())){
				address=address+wayen.getReceiveCustomerMobilephone()+",";
			}else if(StringUtils.isNotEmpty(wayen.getReceiveCustomerPhone())){
				address=address+wayen.getReceiveCustomerPhone()+",";
			}
			//
			if(StringUtils.isNotEmpty(wayen.getReceiveCustomerAddress())){
				address=address+proviceName+cityName+countyName+wayen.getReceiveCustomerAddress();
			}else{
				address=address+proviceName+cityName+countyName;
			}
			en.setReturnGoodsInfo(address);
			/*en.setReturnGoodsInfo("收货人姓名:"+wayen.getReceiveCustomerName()+
					" 收货人手机/收货人电话:"+wayen.getReceiveCustomerMobilephone()+
					"/"+wayen.getReceiveCustomerPhone()+
					" 收货人地址:"+proviceName+cityName+countyName+wayen.getReceiveCustomerAddress());*/
			/*String address = "";
			if(StringUtils.isNotEmpty(wayen.getDeliveryCustomerName())){
				address=address+wayen.getDeliveryCustomerName()+",";
			}
			//
			if(StringUtils.isNotEmpty(wayen.getDeliveryCustomerMobilephone())&&
					StringUtils.isNotEmpty(wayen.getDeliveryCustomerPhone())){
				address=address+wayen.getDeliveryCustomerMobilephone()+"/"+wayen.getDeliveryCustomerPhone()+",";
			}else if(StringUtils.isNotEmpty(wayen.getDeliveryCustomerMobilephone())){
				address=address+wayen.getDeliveryCustomerMobilephone()+",";
			}else if(StringUtils.isNotEmpty(wayen.getDeliveryCustomerPhone())){
				address=address+wayen.getDeliveryCustomerPhone()+",";
			}
			//
			if(StringUtils.isNotEmpty(wayen.getDeliveryCustomerAddress())){
				address=address+proviceName+cityName+countyName+wayen.getDeliveryCustomerAddress();
			}else{
				address=address+proviceName+cityName+countyName;
			}*/
		  }		
			//将返货方式传给PDA
			if(null!=changCode&&"PRINT".equals(changCode)){
				en.setReturnMode("");
			}else{
			if(StringUtils.isNotEmpty(re.getReturnMode())){
				en.setReturnMode(re.getReturnMode());
			}}
			String orgCode=wayen.getCustomerPickupOrgCode();
			if(StringUtils.isNotEmpty(orgCode)){
				// 查询营业部的部门信息
				SaleDepartmentEntity dept = waybillManagerService.querySaleDepartmentByCode(orgCode);
				if(dept != null && FossConstants.ACTIVE.equals(dept.getStation())){
					// 驻地营业部对应外场
					orgCode=dept.getTransferCenter();
				}
			}
			//字母件新加功能 PDA下拉明细时，将母件单号传给PDA
			if(StringUtils.isNotEmpty(originalWaybillNo)){
				TwoInOneWaybillDto dto=waybillManagerService.queryWaybillRelateByWaybillOrOrderNo(originalWaybillNo);
				if(dto !=null &&FossConstants.YES.equals(dto.getIsTwoInOne())){
					en.setParentWaybillNo(dto.getMainWaybillNo());
					if(StringUtils.isNotEmpty(en.getReturnMode())){
						List<String>  waybillNos=new ArrayList<String>();
						waybillNos.addAll(dto.getWaybillNoList());
						waybillNos.add(en.getParentWaybillNo());
						//按票返货
						if(WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(en.getReturnMode())){
								for (int i = 0; i <waybillNos.size(); i++) {
									QmsYchExceptionReportEntity  entity=waybillManagerService.isLoseGroup(waybillNos.get(i));
									if(entity !=null && WaybillConstants.YES.equals(entity.getIsInLoseGroup()) && entity.getIsSuccess()==1){
										waybillNos.remove(i);
									}	
								}	
								en.setPieces(String.valueOf(waybillNos.size()));
						}else{
							List<String> newWaybillNos=new ArrayList<String>();
							for (int i = 0; i < waybillNos.size(); i++) {
								StockEntity  entity=waybillManagerService.queryStockEntityByNos(orgCode,waybillNos.get(i) ,WaybillConstants.SERIAL_NUMBER,null);
								if(entity !=null){
									newWaybillNos.add(waybillNos.get(i));
								}
							}
							en.setPieces(String.valueOf(newWaybillNos.size()));
						}
					}
				}
			}

//			件数，收货人信息，保价金额，代收货款，签收单，包装费，运费，到付款合计
//			wayen
			
			en.setInsuranceAmount(wayen.getInsuranceAmount().toString());
			en.setCodAmount(wayen.getCodAmount().toString());
			en.setReturnBillType(wayen.getReturnBillType());
			en.setPackageFee(wayen.getPackageFee().toString());
			en.setTransportFee(wayen.getTransportFee().toString());
			en.setToPayAmount(wayen.getToPayAmount().toString());
			//返货类型
			if(null!=changCode&&"PRINT".equals(changCode)){
				en.setReturnType("");
			}else{
			en.setReturnType(re.getReturnType());
			}
		return en;
		}
		return null;
		
	   
   }

	/**
	 * 根据上传的对应登陆的司机工号信息与填写的对应的快递员信息下拉对应快递员所有所需接货的数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-4-23 14:56:49
	 * @return
	 */
	@Override
	public List<ExpressFeederPickupDetailDto> getExpressFeederPickupDetail(ExpressFeederPickupQueryDto queryDto) {
		StringBuffer sb = new StringBuffer();
		if(queryDto == null){
			sb.append("传入的查询Dto为null;");
			throw new PdaProcessException(sb.toString());
		}
		if(StringUtils.isEmpty(queryDto.getDriverCode())){
			sb.append("传入的司机工号;");
		}
		if(StringUtils.isEmpty(queryDto.getExpressEmpCode())){
			sb.append("传入的对应接驳点快递员工号;");
		}
		if(StringUtils.isEmpty(queryDto.getTaskNo())){
			sb.append("传入的接货任务编码;");
		}
		if(StringUtils.isNotEmpty(sb.toString())){
			sb.append("为空");
			throw new PdaProcessException(sb.toString());
		}
		LOGGER.info("司机下拉快递接货详情接口传入参数 " + ReflectionToStringBuilder.toString(queryDto));//记录日志
		//进行司机和快递员的校验，主要判定对应的快递员是否在对应司机的接驳点下
		//设置是否完成接货为N
		queryDto.setHandOverStatus(FossConstants.NO);
		//校验快递员与司机关系
		validateExpressFeederUnderOutField(queryDto);
		return waybillPendingDao.getExpressFeederPickupDetail(queryDto);
	}
	
	/**
	 * 二程接驳-司机下拉明细后需要更新对应waybill_pending表中的是否完成接货状态
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-5-7 10:12:35
	 */
	@Override
	public ResultDto updatePickupDoneExpressFeederPickupDetail(List<String> waybillNoList) {
		if(CollectionUtils.isEmpty(waybillNoList)){
			throw new PdaProcessException("传入的运单集合数据为空");
		}
		LOGGER.info("司机更新快递接货完成接货接口传入参数 " + ReflectionToStringBuilder.toString(waybillNoList));//记录日志
		ResultDto resultDto = new ResultDto();
		try {
			//进行数据的更新，需要判定运单总个数，大于1000条是需要进行数据的分部处理
			ExpressFeederPickupQueryDto queryDto = new ExpressFeederPickupQueryDto();
			if(waybillNoList.size() < FossConstants.ORACLE_MAX_IN_SIZE){
				queryDto.setWaybillNoList(waybillNoList);
				queryDto.setHandOverStatus(FossConstants.YES);
				waybillPendingDao.updateWaybillHandOverStatus(queryDto);
			}else{
				List<List<String>> idsLists = com.deppon.foss.util.CollectionUtils.splitListBySize(waybillNoList, FossConstants.ORACLE_MAX_IN_SIZE);
				for(List<String> lists : idsLists){
					queryDto.setWaybillNoList(lists);
					queryDto.setHandOverStatus(FossConstants.YES);
					waybillPendingDao.updateWaybillHandOverStatus(queryDto);
				}
			}
			resultDto.setCode(ResultDto.SUCCESS);
			resultDto.setMsg("");
		} catch (Exception e) {
			resultDto.setCode(ResultDto.FAIL);
			resultDto.setMsg("异常信息:"+e);
		}
		return resultDto;
	}

	
	  @Override
	   public ReturnBillingEWaybillEntity getReturnWaybillEntity(String originalWaybillNo){
		   ReturnGoodsRequestEntity condition = new ReturnGoodsRequestEntity();
		   condition.setOriWaybill(originalWaybillNo);
		   //增加校验逻辑，判定原单是否存在于到达部门库存
		   boolean isInStock = stockService.queryStockByWaybillNo(originalWaybillNo);
		   //是否为子母件
		   TwoInOneWaybillDto isTowInOne=waybillManagerService.queryWaybillRelateByWaybillOrOrderNo(originalWaybillNo);
		   if(isInStock && FossConstants.YES.equals(isTowInOne.getIsTwoInOne())){
			   throw new PdaProcessException("此票货物不在营业部库存中，不允许返货开单");
		   }
			List<ReturnGoodsRequestEntity> list = returnGoodsRequestEntityService
					.queryReturnGoodsRequestEntityByCondition(condition);
			ReturnGoodsRequestEntity re = null;
			if(CollectionUtils.isNotEmpty(list)){
				if(list.size()==1){
					 re = list.get(0);
				}else if(list.size()>1){
					for(ReturnGoodsRequestEntity g : list){
						if(null==re){
							re = g;
						}else{
							if(g.getTimeReport().after(re.getTimeReport())){
								re = g;
							}
						}
					}
				}
				
				ReturnBillingEWaybillEntity en =new ReturnBillingEWaybillEntity();
				WaybillEntity wayen = waybillManagerService.queryWaybillBasicByNo(originalWaybillNo);
				if(WaybillConstants.RETURNTYPE_OTHER_CITY.equals(re.getReturnType()) ||  
						WaybillConstants.RETURNTYPE_CUSTORMER_REFUSE.equals(re.getReturnType())||
						WaybillConstants.RETURNTYPE_SAME_CITY.equals(re.getReturnType())||
						WaybillConstants.RETURN_BACK.equals(re.getReturnType())){
				

				if(null != wayen){
					//en.setDestination(wayen.getTargetOrgCode());
					en.setPieces(wayen.getGoodsQtyTotal().toString());
					ProductEntity product = productService.getLevel3ProductInfo(wayen.getProductCode());
					en.setTransportType(product == null ? wayen.getProductCode() : product.getName());
					en.setDestination(wayen.getTargetOrgCode());
				}
				//受理状态
				en.setReturnStatus(re.getReturnStatus());
				
				/*if(null!=re){
//					en.setReturnGoodsInfo("代收:"+re.getReturnMoneyReceive()+" 保价:"+re.getReturnMoneyInsured()+" 收货人姓名:"+re.getReturnManReceive()+" 收货人手机/收货人电话:"+re.getReturnPhoneReceive()+"/"+re.getReturnTelReceive()+" 收货人地址:"+re.getReturnProvince()+re.getReturnCity()+re.getReturnArea()+re.getReturnDetailaddress());
					en.setReturnGoodsInfo("收货人姓名:"+re.getReturnManReceive()+" 收货人手机/收货人电话:"+re.getReturnPhoneReceive()+"/"+re.getReturnTelReceive()+" 收货人地址:"+re.getReturnProvince()+re.getReturnCity()+re.getReturnArea()+re.getReturnDetailaddress());
				}*/	
				String proviceName = onlineAddressDao.queryProviceByCode(wayen.getReceiveCustomerProvCode());
				String cityName = onlineAddressDao.queryProviceByCode(wayen.getReceiveCustomerCityCode());
				String countyName = onlineAddressDao.queryProviceByCode(wayen.getReceiveCustomerDistCode());
				 
				String address = "";
				if(StringUtils.isNotEmpty(wayen.getReceiveCustomerName())){
					address=address+wayen.getReceiveCustomerName()+",";
				}
				//
				if(StringUtils.isNotEmpty(wayen.getReceiveCustomerMobilephone())&&
						StringUtils.isNotEmpty(wayen.getReceiveCustomerPhone())){
					address=address+wayen.getReceiveCustomerMobilephone()+"/"+wayen.getReceiveCustomerPhone()+",";
				}else if(StringUtils.isNotEmpty(wayen.getReceiveCustomerMobilephone())){
					address=address+wayen.getReceiveCustomerMobilephone()+",";
				}else if(StringUtils.isNotEmpty(wayen.getReceiveCustomerPhone())){
					address=address+wayen.getReceiveCustomerPhone()+",";
				}
				//
				if(StringUtils.isNotEmpty(wayen.getReceiveCustomerAddress())){
					address=address+proviceName+cityName+countyName+wayen.getReceiveCustomerAddress();
				}else{
					address=address+proviceName+cityName+countyName;
				}
				en.setReturnGoodsInfo(address);
				/*en.setReturnGoodsInfo("收货人姓名:"+wayen.getReceiveCustomerName()+
						" 收货人手机/收货人电话:"+wayen.getReceiveCustomerMobilephone()+
						"/"+wayen.getReceiveCustomerPhone()+
						" 收货人地址:"+proviceName+cityName+countyName+wayen.getReceiveCustomerAddress());*/
				/*String address = "";
				if(StringUtils.isNotEmpty(wayen.getDeliveryCustomerName())){
					address=address+wayen.getDeliveryCustomerName()+",";
				}
				//
				if(StringUtils.isNotEmpty(wayen.getDeliveryCustomerMobilephone())&&
						StringUtils.isNotEmpty(wayen.getDeliveryCustomerPhone())){
					address=address+wayen.getDeliveryCustomerMobilephone()+"/"+wayen.getDeliveryCustomerPhone()+",";
				}else if(StringUtils.isNotEmpty(wayen.getDeliveryCustomerMobilephone())){
					address=address+wayen.getDeliveryCustomerMobilephone()+",";
				}else if(StringUtils.isNotEmpty(wayen.getDeliveryCustomerPhone())){
					address=address+wayen.getDeliveryCustomerPhone()+",";
				}
				//
				if(StringUtils.isNotEmpty(wayen.getDeliveryCustomerAddress())){
					address=address+proviceName+cityName+countyName+wayen.getDeliveryCustomerAddress();
				}else{
					address=address+proviceName+cityName+countyName;
				}*/
			  }		
				//将返货方式传给PDA
				
				if(StringUtils.isNotEmpty(re.getReturnMode())){
					en.setReturnMode(re.getReturnMode());
				}
				String orgCode=wayen.getCustomerPickupOrgCode();
				if(StringUtils.isNotEmpty(orgCode)){
					// 查询营业部的部门信息
					SaleDepartmentEntity dept = waybillManagerService.querySaleDepartmentByCode(orgCode);
					if(dept != null && FossConstants.ACTIVE.equals(dept.getStation())){
						// 驻地营业部对应外场
						orgCode=dept.getTransferCenter();
					}
				}
				//字母件新加功能 PDA下拉明细时，将母件单号传给PDA
				if(StringUtils.isNotEmpty(originalWaybillNo)){
					TwoInOneWaybillDto dto=waybillManagerService.queryWaybillRelateByWaybillOrOrderNo(originalWaybillNo);
					if(dto !=null &&FossConstants.YES.equals(dto.getIsTwoInOne())){
						en.setParentWaybillNo(dto.getMainWaybillNo());
						if(StringUtils.isNotEmpty(en.getReturnMode())){
							List<String>  waybillNos=new ArrayList<String>();
							waybillNos.addAll(dto.getWaybillNoList());
							waybillNos.add(en.getParentWaybillNo());
							//按票返货
							if(WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(en.getReturnMode())){
									for (int i = 0; i <waybillNos.size(); i++) {
										QmsYchExceptionReportEntity  entity=waybillManagerService.isLoseGroup(waybillNos.get(i));
										if(entity !=null && WaybillConstants.YES.equals(entity.getIsInLoseGroup()) && entity.getIsSuccess()==1){
											waybillNos.remove(i);
										}	
									}	
									en.setPieces(String.valueOf(waybillNos.size()));
							}else{
								List<String> newWaybillNos=new ArrayList<String>();
								for (int i = 0; i < waybillNos.size(); i++) {
									StockEntity  entity=waybillManagerService.queryStockEntityByNos(orgCode,waybillNos.get(i) ,WaybillConstants.SERIAL_NUMBER,null);
									if(entity !=null){
										newWaybillNos.add(waybillNos.get(i));
									}
								}
								en.setPieces(String.valueOf(newWaybillNos.size()));
							}
						}
					}
				}
				
//				收货人信息，保价金额，代收货款，签收单，包装费，运费，到付款合计
//				wayen
				en.setInsuranceAmount(wayen.getInsuranceAmount().toString());
				en.setCodAmount(wayen.getCodAmount().toString());
				en.setReturnBillType(wayen.getReturnBillType());
				en.setPackageFee(wayen.getPackageFee().toString());
				en.setTransportFee(wayen.getTransportFee().toString());
				en.setToPayAmount(wayen.getToPayAmount().toString());
				//返货类型
				
				en.setReturnType(re.getReturnType());
				
			return en;
			}
			return null;
			
		   
	   }
	/**
	 * 二程接驳-校验司机所接快递员所在接驳点所属司机对应外场所管辖的接驳点下
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-5-26 17:23:57
	 * @param queryDto
	 * @return
	 */
	@Override
	public void validateExpressFeederUnderOutField(ExpressFeederPickupQueryDto queryDto) {
		//根据快递员查询对应的接驳点数据
		List<String> acceptPointList = acceptPointSalesDeptService.queryExpressAcceptPointByEmployeeCode(queryDto.getExpressEmpCode());
		if(CollectionUtils.isEmpty(acceptPointList)){
			throw new PdaProcessException("根据快递员工号:"+queryDto.getExpressEmpCode()+"查询出来对应的接驳点为空");
		}
		
		//根据司机查询对应的外场
		String transferCenterCode = accessPointService.queryTransCenterByEmpCode(queryDto.getDriverCode());
		//对应的外场关系
		if(StringUtils.isEmpty(transferCenterCode)){
			throw new PdaProcessException("根据司机工号:"+queryDto.getExpressEmpCode()+"查询出来对应的外场为空");
		}
		
		AccessPointEntity entity = null;
		boolean isQuery =false;
		for(String acceptPointCode : acceptPointList){
			if(StringUtils.isNotEmpty(acceptPointCode)){
				entity = new AccessPointEntity();
				entity.setTransferCode(transferCenterCode);
				entity.setCode(acceptPointCode);
				List<AccessPointEntity> accessList = accessPointService.queryAccessPointByCommon(entity);
				if(CollectionUtils.isNotEmpty(accessList)){
					isQuery = true;
					break;
				}
			}
		}
		if(!isQuery){
			throw new PdaProcessException("根据快递员:"+queryDto.getExpressEmpCode()+"司机工号:"+queryDto.getDriverCode()+"查询不到对应接驳点与外场关系");
		}
	}

	@Override
	public Long isExistPickUpDoneByWaybillNoList(ExpressFeederPickupQueryDto queryDto) {
		if(queryDto == null || CollectionUtils.isEmpty(queryDto.getWaybillNoList())){
			throw new PdaProcessException("传入的查询参数或者运单号集合为空");
		}
		Long count = 0l;
		//进行数据的更新，需要判定运单总个数，大于1000条是需要进行数据的分部处理
		if(queryDto.getWaybillNoList().size() < FossConstants.ORACLE_MAX_IN_SIZE){
			queryDto.setWaybillNoList(queryDto.getWaybillNoList());
			queryDto.setHandOverStatus(FossConstants.YES);
			count = waybillPendingDao.isExistPickUpDoneByWaybillNoList(queryDto);
		}else{
			List<List<String>> idsLists = com.deppon.foss.util.CollectionUtils.splitListBySize(queryDto.getWaybillNoList(), FossConstants.ORACLE_MAX_IN_SIZE);
			for(List<String> lists : idsLists){
				queryDto.setWaybillNoList(lists);
				queryDto.setHandOverStatus(FossConstants.YES);
				count = waybillPendingDao.isExistPickUpDoneByWaybillNoList(queryDto);
				if(count > 0){
					break;
				}
			}
		}
		return count;
	}

	@Override
	public boolean acceptOrder(PdaOrderDto pdaOrderDto) {
		try {
			LOGGER.info("PDA受理订单"+pdaOrderDto.getOrderNo()+"开始");
			if(StringUtils.isBlank(pdaOrderDto.getOrderNo())) {
				throw new PdaProcessException("订单号不能为空");
			}
			LOGGER.info("PDA受理订单"+pdaOrderDto.getOrderNo()+"查询订单信息");
			DispatchOrderConditionDto orderQueryDto = new DispatchOrderConditionDto();
			orderQueryDto.setOrderNo(pdaOrderDto.getOrderNo());
			DispatchOrderEntity dispatchOrderEntity = dispatchOrderEntityDao.queryOrdersByOrderNo(orderQueryDto);
			if(dispatchOrderEntity==null) {
				throw new PdaProcessException("根据订单号查询不到订单信息["+pdaOrderDto.getOrderNo()+"]");
			}
			LOGGER.info("PDA受理订单"+pdaOrderDto.getOrderNo()+"判断是否快递");
			boolean isExpress = waybillExpressService.onlineDetermineIsExpressByProductCode(dispatchOrderEntity.getProductCode(), new Date());
			//是否发送短信标志位
			boolean isSendpackageFlag=true;
			String customerMobile = dispatchOrderEntity.getMobile();
			if(StringUtils.isNotEmpty(customerMobile)){
				String code=customerDao.queryCustCodeByCustMobile(customerMobile);
				if(StringUtils.isNotEmpty(code)){
					CustomerEntity customerEntity = customerDao.queryCustStateByCode(code);
					if(customerEntity!=null){
						String shipperSms = customerEntity.getShipperSms();
						if(StringUtils.isNotEmpty(shipperSms)&&(shipperSms.equals(OrderConstant.STOP_MESSAGE_FOR_DELIVER)||shipperSms.equals(OrderConstant.BATCH_MESSAGE_FOR_DELIVER))){
							isSendpackageFlag=false;//停发短信
						}
					}
				}
			}
			if(isSendpackageFlag==true) {
				LOGGER.info("PDA受理订单"+pdaOrderDto.getOrderNo()+"查询短信开关");
				if(FossConstants.ACTIVE.equals(this.configurationParamsService.queryConfValueByCode(ConfigurationParamsConstants.STL_ORDER_MESSAGE_SWITCH))) { 
					OrderHandleDto orderHandleDto = new OrderHandleDto();
					// 设置出发城市
					orderHandleDto.setPickupCity(dispatchOrderEntity.getPickupCity());
					//设置运输性质
					orderHandleDto.setProductCode(dispatchOrderEntity.getProductCode());
					// 设置订单来源
					orderHandleDto.setOrderSource(dispatchOrderEntity.getOrderSource());
					// 车牌
					orderHandleDto.setVehicleNo(dispatchOrderEntity.getVehicleNo());
					// 司机code
					orderHandleDto.setDriverCode(dispatchOrderEntity.getDriverCode());
					// 司机姓名
					orderHandleDto.setDriverName(dispatchOrderEntity.getDriverName());
					// 14.8.13 gcl AUTO-224 设置司机手机号
					LOGGER.info("PDA受理订单"+pdaOrderDto.getOrderNo()+"查询快递员手机号");
					orderHandleDto.setDriverMobile(pdaSignEntityDao.queryPhoneByDriverCode(dispatchOrderEntity.getDriverCode()));
					orderHandleDto.setCustomerName(dispatchOrderEntity.getCustomerName());
					orderHandleDto.setCustomerMobile(dispatchOrderEntity.getMobile());
					// 操作时间
					orderHandleDto.setOperateTime(new Date());
					// 操作人
					orderHandleDto.setOperator("System");
					// 操作人编码
					orderHandleDto.setOperatorCode("000000");
					// 操作部门编码
					orderHandleDto.setOperateOrgCode(dispatchOrderEntity.getFleetCode());
					// zxy 20140710 AUTO-134 新增:设置字段内容
					// 设置订单号
					orderHandleDto.setOrderNo(dispatchOrderEntity.getOrderNo());
					// 设置订单类型
					orderHandleDto.setOrderType(dispatchOrderEntity.getOrderType());
					// 14.8.12 gcl AUTO-224
					orderHandleDto.setIsCustomer("Y");
					orderHandleDto.setIsSms("Y");
					// 14.7.18 gcl AUTO-182 设置预处理建议小区和实际接货小区为预处理小区
					orderHandleDto.setPickupRegionCode(dispatchOrderEntity
							.getSmallZoneCodeSuggested());
					orderHandleDto.setPickupRegionName(dispatchOrderEntity
							.getSmallZoneNameSuggested());
					orderHandleDto.setSmallZoneCodeActual(dispatchOrderEntity
							.getSmallZoneCodeSuggested());
					OwnTruckConditionDto conditionDto = new OwnTruckConditionDto();
					// 车牌号
					conditionDto.setVehicleNo(orderHandleDto.getVehicleNo());
					// 有效
					conditionDto.setActive(FossConstants.ACTIVE);
					// 获取车型
					LOGGER.info("PDA受理订单"+pdaOrderDto.getOrderNo()+"获取车型");
					VehicleInfoDto vehicleInfoDto = truckResourceDao.queryVehicleTypeByVehicleNo(conditionDto);
					// 设置车型
					orderHandleDto.setVehicleLengthName(vehicleInfoDto.getVehicleLengthName());
					
					NotificationEntity notificationEntity = new NotificationEntity();
					// 设置订单号
					notificationEntity.setWaybillNo(pdaOrderDto.getOrderNo());
					// 设置通知模块名 
					if(isExpress) {
						notificationEntity.setModuleName(DispatchOrderStatusConstants.EXPRESS_ORDER_MODULE);
					}else {
						notificationEntity.setModuleName(NotifyCustomerConstants.BS_TYPE_PKP_ORDER);
					}
					// 设置通知类型
					notificationEntity.setNoticeType(NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE);
					// 设置通知内容
					notificationEntity.setNoticeContent(this.getSmsContent(orderHandleDto));
					// 设置手机号
					notificationEntity.setMobile(dispatchOrderEntity.getMobile());
					// 设置发送对象  
					notificationEntity.setConsignee(dispatchOrderEntity.getCustomerName());
					//设置操作人
					notificationEntity.setOperator("system");
					// 设置操作人编号
					notificationEntity.setOperatorNo("000000");
					// 设置操作部门名称
					notificationEntity.setOperateOrgName("system");
					// 设置操作部门(将承担短信费用)
					if(isExpress) {
						if(StringUtils.isNotEmpty(orderHandleDto.getDriverCode())){
							LOGGER.info("PDA受理订单"+pdaOrderDto.getOrderNo()+"查询操作部门");
							EmployeeEntity employeeEntity = employeeService.queryEmployeeByEmpCodeNoCache(orderHandleDto.getDriverCode());
							// 设置操作部门编码
							if(employeeEntity!=null) {
								notificationEntity.setOperateOrgCode(employeeEntity.getOrgCode());
							}
						}else{
							// 设置操作部门编码
							notificationEntity.setOperateOrgCode("W01");
						}
					}else {
						// 操作部门编码
						orderHandleDto.setOperateOrgCode(dispatchOrderEntity.getFleetCode());
					}
					// 设置操作时间
					notificationEntity.setOperateTime(new Date());
					LOGGER.info("PDA受理订单"+pdaOrderDto.getOrderNo()+"发送短信开始");
					notifyCustomerService.sendMessage(notificationEntity);
				}else {
					LOGGER.info("PDA受理订单"+pdaOrderDto.getOrderNo()+"时,短信功能关闭");
				}
			}else {
				LOGGER.info("PDA受理订单"+pdaOrderDto.getOrderNo()+"时,客户短信停发");
			}
			LOGGER.info("PDA受理订单"+pdaOrderDto.getOrderNo()+"结束");
		}catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("PDA受理订单"+pdaOrderDto.getOrderNo()+"时,发送短信失败");
			return false;
		}
		return true;
	}
		
	private String getSmsContent(OrderHandleDto orderHandleDto ) {
		String sms=null;
		// 模版参数
		SmsParamDto smsParamDto = new SmsParamDto();
		// 短信编码
			smsParamDto.setSmsCode(DispatchOrderStatusConstants.EXPRESS_ORDER_ACCEPT_SMS);
		// 部门编码   
		smsParamDto.setOrgCode("");
		// 参数Map
		Map<String, String> map = new HashMap<String, String>();
		map.put("driverName", orderHandleDto.getDriverName());
		try {
			OwnTruckDto ownTruckDto = pdaSignEntityDao.queryExpressVehicleNoByCourier(orderHandleDto.getDriverCode());
			if(ownTruckDto!=null) {
				map.put("driverMobile", ownTruckDto.getDriverMobile());
			}
		}catch(Exception e) {
			LOGGER.info("获取快递员["+orderHandleDto.getDriverCode()+"]手机号失败");
			LOGGER.error("error", e);
		}
		map.put("vehicleNo", orderHandleDto.getVehicleNo());
		map.put("vehicleType", orderHandleDto.getVehicleLengthName());
		smsParamDto.setMap(map);
		try {
			sms = sMSTempleteService.querySmsByParam(smsParamDto);
		} catch (Exception e) {
			LOGGER.error("error", e);
		}
		return sms;
	}

	/**
	 * @param notifyCustomerService the notifyCustomerService to set
	 */
	public void setNotifyCustomerService(
			INotifyCustomerService notifyCustomerService) {
		this.notifyCustomerService = notifyCustomerService;
	}

	/**
	 * @param sMSTempleteService the sMSTempleteService to set
	 */
	public void setsMSTempleteService(ISMSTempleteService sMSTempleteService) {
		this.sMSTempleteService = sMSTempleteService;
	}

	/**
	 * @param waybillExpressService the waybillExpressService to set
	 */
	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}

	/**
	 * @param truckResourceDao the truckResourceDao to set
	 */
	public void setTruckResourceDao(ITruckResourceDao truckResourceDao) {
		this.truckResourceDao = truckResourceDao;
	}

	/**
	 * @param customerDao the customerDao to set
	 */
	public void setCustomerDao(ICustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	/**
	 * @param configurationParamsService the configurationParamsService to set
	 */
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	
	
}