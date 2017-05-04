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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/service/impl/NotifyCustomerService.java
 * 
 * FILE NAME        	: NotifyCustomerService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgGisUrlDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILclDeliveryToCashManagementUnloadingService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IlclDeliveryToCashManagementDeliveryService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactAddressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementDeliveryEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementUnloadingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgGisUrlEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DepartureStandardDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.CustomerException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.server.service.ISMSAdvertisingSloganService;
import com.deppon.foss.module.base.common.api.server.service.ISMSFailLogService;
import com.deppon.foss.module.base.common.api.server.service.ISMSSendLogService;
import com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.domain.SMSFailLogEntity;
import com.deppon.foss.module.base.common.api.shared.domain.SMSSendLogEntity;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.common.api.shared.dto.SmsParamDto;
import com.deppon.foss.module.base.common.api.shared.exception.SMSAdVertisingSloganException;
import com.deppon.foss.module.base.common.api.shared.exception.SMSFailLogException;
import com.deppon.foss.module.base.common.api.shared.exception.SMSSendLogException;
import com.deppon.foss.module.base.common.api.shared.exception.SMSTempleteException;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyCustomerDao;
import com.deppon.foss.module.pickup.predeliver.api.server.process.IHandleQueryOutfieldService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArrivalGoodsInformationDopService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.ICustomerReceiptAddressService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IExceptionProcessService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ExceptionProcessConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.CustomerReceiptAddressEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionProcessDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.InvoiceInfomationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArrivalGoodsResponseDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArrivalGoodsWaybillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionProcessConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionProcessDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.StorageJobDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.ExceptionProcessException;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.NotifyCustomerException;
import com.deppon.foss.module.pickup.predeliver.server.util.IntegerUtils;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.sign.api.server.service.ISendWaybillTrackService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillTrackDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.define.ExpWaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillQueryInfoDto;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.ICustomerBargainService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.DebitDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.WaybillReceivableDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.trackings.api.shared.define.WaybillTrackingsConstants;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PathDetailEntity;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 运单通知Service
 * 
 * @author ibm-wangfei
 * @date 2012-10-11 15:42:37
 */
public class NotifyCustomerService implements INotifyCustomerService {
	/**
	 * Logger实例
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(NotifyCustomerService.class);

	/**
	 * 注入通知客户DAO
	 */
	private INotifyCustomerDao notifyCustomerDao;

	/**
	 * 处理异常Service
	 */
	private IExceptionProcessService exceptionProcessService;

	/**
	 * 综合Service
	 */
	private ICustomerService customerService;
    
	/**
	 * 到达联Service
	 */
	private IArriveSheetManngerService arriveSheetManngerService;

	/**
	 * 客户信用额度管理服务
	 */
	private ICustomerBargainService customerBargainService;

	/**
	 * 短信模板service接口
	 */
	private ISMSTempleteService sMSTempleteService;

	/**
	 * 发送短信接口
	 */
	private ISMSSendLogService smsSendLogService;

	/**
	 * 短信发送日志接口
	 */
	private ISMSFailLogService smsFailLogService;

	/**
	 * 组织信息 Service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * 短信广告语service接口
	 */
	private ISMSAdvertisingSloganService sMSAdvertisingSloganService;

	/**
	 * 外场相关共通接口
	 */
	private IHandleQueryOutfieldService handleQueryOutfieldService;

	/**
	 * 应收单服务
	 */
	private IBillReceivableService billReceivableService;

	/**
	 * 系统配置参数 Service接口
	 */
	private IConfigurationParamsService configurationParamsService;

	private INotifyCustomerService notifyCustomerService;
	private IProductService productService;
	
	private ICustomerDao customerDao;
	
	/**
	 * 业务互斥锁服务
	 */
	private IBusinessLockService businessLockService;
	/**
	 * 运单管理服务
	 */
	private IWaybillManagerService waybillManagerService;
	
	/**
	 * 实际收货地址服务
	 */
	private ICustomerReceiptAddressService customerReceiptAddressService;
	
	/**
	 * 实际承运
	 */
	private IActualFreightDao actualFreightDao;
	
	
	/**
	 * 查询GIS短链接
	 */
	private IOrgGisUrlDao orgGisUrlDao;

	public void setOrgGisUrlDao(IOrgGisUrlDao orgGisUrlDao) {
		this.orgGisUrlDao = orgGisUrlDao;
	}
	/**
	 * 轨迹推送接口 
	 */
	private ISendWaybillTrackService sendWaybillTrackService;
	/**
	 * 员工
	 * service接口
	 */
	private IEmployeeService employeeService;
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	/**
	 * @param sendWaybillTrackService the sendWaybillTrackService to set
	 */
	public void setSendWaybillTrackService(
			ISendWaybillTrackService sendWaybillTrackService) {
		this.sendWaybillTrackService = sendWaybillTrackService;
	}

	/**
	 * 到货信息推送接口
	 */
	private IArrivalGoodsInformationDopService arrivalGoodsInformationDopService;
	
	public void setArrivalGoodsInformationDopService(
			IArrivalGoodsInformationDopService arrivalGoodsInformationDopService) {
		this.arrivalGoodsInformationDopService = arrivalGoodsInformationDopService;
	}
	/*
	 * 接送货接口
	 */
	private ICalculateTransportPathService calculateTransportPathService;
	/*
	 * 基础信息查询走货路径时间和规定卸出时间接口
	 */
	private ILclDeliveryToCashManagementUnloadingService lclDeliveryToCashManagementUnloadingService;
	private IlclDeliveryToCashManagementDeliveryService lclDeliveryToCashManagementDeliveryService;
	/*
	 * 营业部接口
	 */
	private ISaleDepartmentService 	saleDepartmentService; 
	/*
	 * 线路接口（查始发和到达线路）
	 */
	
	private ILineService lineService;

	/**
	 * 客户通知运单查询.
	 * 
	 * @param conditionDto the condition dto
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author ibm-wangfei
	 * @date Oct 24, 2012 4:36:58 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService#queryWaybillInfoList(com.deppon.foss.module.pickup.predeliver.api.shared.vo.NotifyCustomerVo)
	 */
	@Override
	public List<NotifyCustomerDto> queryWaybillInfoList(NotifyCustomerConditionDto conditionDto, int start, int limit) {
		// 判断页面传入dot
		if (conditionDto == null) {
			// 为null时，返回null值
			return null;
		}
		// 打印页面传入参数
		LOGGER.info("客户通知页面查询参数：" + ReflectionToStringBuilder.toString(conditionDto));
		// 查询列表.
		List<NotifyCustomerDto> dtos = getNotifyCustomerDto(conditionDto, start, limit);
		// 判断查询结果是否存在
		if (CollectionUtils.isEmpty(dtos)) {
			// 不存在，返回null值
			return null;
		}
		//过滤相同的运单号集合   243921
		List<Map<String,Object>> waybillNOs = new ArrayList<Map<String,Object>>();
		// 对查询结果进行循环
		for (NotifyCustomerDto dto : dtos) {
			// 设置dto的查询属性
			dto.setSelectType(conditionDto.getSelectType());

			// 送货ID 设置收货习惯
			dto.setReceivingHabits(getReceivingHabits(dto.getContactAddressId()));

			// 是否为网上支付
			if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(dto.getPaidMethod())) {
				// 根据运单号，查询开单付款方式为网上支付的应收单的未核销金额
				WaybillReceivableDto receivableDto = billReceivableService.queryReceivableAmountByWaybillNO(dto.getWaybillNo());
				// 没有数据的话，返回为空,没有未结清欠款,
				if (receivableDto == null) {
					// no为未结清货款
					dto.setIsPay(FossConstants.YES);
				} else {
					// yes为结清货款
					dto.setIsPay(FossConstants.NO);
				}
			}
			// 设置送货地址
			dto.setReceiveCustomerAddress(handleQueryOutfieldService.getCompleteAddressAttachAddrNote(dto.getReceiveCustomerProvCode(), dto.getReceiveCustomerCityCode(), dto.getReceiveCustomerDistCode(), dto.getReceiveCustomerAddress(), dto.getReceiveCustomerAddressNote()));

			// 按照运单号查询，添加以下条件
			if (StringUtil.isNotBlank(conditionDto.getWaybillNo())) {
				// 根据库存件数判断是否到达
				if (dto.getStockQty() == null || dto.getStockQty().intValue() == 0) {
					// 如果没有库存，则设置车辆任务为在途
					dto.setTaskStatus(TaskTruckConstant.TASK_TRUCK_STATE_ONTHEWAY);
				}
			}
			// 按库存方式查询时，不修改到达件数
			if (String.valueOf(NotifyCustomerConstants.SELECT_TYPE_STOCK).equals(conditionDto.getSelectType())) {
				// 退出本次循环
				continue;
			}
			// 按交接单查询时，交接单到达或者已卸车，则列表中“到达件数”等于“交接单件数”，交接单未到达，则列表中到达件数等于0
			if (TaskTruckConstant.TASK_TRUCK_STATE_ARRIVED.equals(dto.getTaskStatus()) || TaskTruckConstant.TASK_TRUCK_STATE_UNLOADED.equals(dto.getTaskStatus())) {
				// 判断交接单已到达
				dto.setArriveGoodsQty(dto.getHandoverGoodsQty());
			} else {
				// 设置到达件数为0
				dto.setArriveGoodsQty(0);
			}
			//到达件数更新后，与开单件数进行比较 判断是否分批配载未到齐
			if(dto.getArriveGoodsQty() < dto.getGoodsQtyTotal()){
				dto.setBatchStowageNOtHere(FossConstants.YES);
			}else{
				dto.setBatchStowageNOtHere(FossConstants.NO);
			}
			//相同运单号的到达件数进行累加    243921
			Map<String,Object> map = new HashMap<String, Object>();
			if(CollectionUtils.isEmpty(waybillNOs)){
				map.put("waybilNo", dto.getWaybillNo());
				map.put("arriveGoodsQty", dto.getArriveGoodsQty());
			}else{
				Map<String,Object> map1 = this.getWaybillDetail(waybillNOs, dto.getWaybillNo());
				if(null == map1){ //运单号不存在list中
					map.put("waybilNo", dto.getWaybillNo());
					map.put("arriveGoodsQty", dto.getArriveGoodsQty());
				}else{//存在list中 ，对相同的运单号 的到达件数进行累加
					map.put("waybilNo", dto.getWaybillNo());
					map.put("arriveGoodsQty", Integer.parseInt(map1.get("arriveGoodsQty").toString()) + dto.getArriveGoodsQty());
					waybillNOs.remove(map1);
				}
			}
			waybillNOs.add(map);
		}
		//对查询结果循环      243921
		for (NotifyCustomerDto dto : dtos) {
			// 按库存方式查询时
			if (String.valueOf(NotifyCustomerConstants.SELECT_TYPE_STOCK).equals(conditionDto.getSelectType())) {
				// 退出本次循环
				continue;
			}
			// 按交接单查询时， 相同运单号的到达件数累加后 ，与开单件数进行比较 判断是否分批配载未到齐
			Map<String,Object> map = this.getWaybillDetail(waybillNOs, dto.getWaybillNo());
			if(null != map){
				if(Integer.parseInt(map.get("arriveGoodsQty").toString()) < dto.getGoodsQtyTotal()){
					dto.setBatchStowageNOtHere(FossConstants.YES);
				}else{
					dto.setBatchStowageNOtHere(FossConstants.NO);
				}
			}
		}		
		// 返回查询结果
		return dtos;
	}

	/**
	 * 判断List集合中存在某个运单号 返回
	 * @author 243921-foss-zhangtingting
	 * @date 2015-06-05 下午1:43:25
	 * @param list 存放运单号与到达件数的list集合
	 * @param waybillNo 运单号
	 * @return
	 */
	private Map<String,Object> getWaybillDetail(List<Map<String,Object>> list,String waybillNo){
		for(Map<String,Object> map : list){
			if(waybillNo.equals(String.valueOf(map.get("waybilNo")))){
				return map;
			}
		}
		return null;
	}
	
	/**
	 * 查询记录count.
	 * 
	 * @param conditionDto the condition dto
	 * @return the long
	 * @author ibm-wangfei
	 * @date Nov 9, 2012 10:08:54 AM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service
	 *      .INotifyCustomerService#queryWaybillInfoCount(com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto)
	 */
	@Override
	public NotifyCustomerConditionDto queryWaybillInfoCount(NotifyCustomerConditionDto conditionDto) {
		// 判断页面传入dot
		if (conditionDto == null) {
			// 为null时，返回null值
			return null;
		}
		// 打印页面传入参数
		LOGGER.info("客户通知页面查询COUNT参数：" + ReflectionToStringBuilder.toString(conditionDto));
		// 设置默认查询条件.
		initNotifyCustomerQuery(conditionDto);
		// 判断页面查询类型.
		int queryType = getQueryType(conditionDto);

		// 打印设置默认值后的查询参数
		LOGGER.info(ReflectionToStringBuilder.toString(conditionDto));

		//查询条件中：派送方式选择“家装送装”，同时“提货方式”下拉框选择任意提货方式，无查询结果      -add by 243921
		if(StringUtils.isNotBlank(conditionDto.getReceiveMethod()) && StringUtils.isNotBlank(conditionDto.getReceiveMethodCon()) 
				&& NotifyCustomerConstants.DELIVER_EQUIP.equals(conditionDto.getReceiveMethod())){
			conditionDto.setTotalVotes(0l);
			return conditionDto;
		}
		
		// 对页面查询类型进行switch判断
		switch (queryType) {
		case NotifyCustomerConstants.SELECT_TYPE_STOCK:
			// 0. 默认查询到达本地以及本地库存的运单
			// 按运单号、库存数量查询
			return this.notifyCustomerDao.queryNotifyCustomerCountForStock(conditionDto);
		case NotifyCustomerConstants.SELECT_TYPE_HANDOVER:
			// 1.页面录入交接单号
			// 按交接单号查询
			return this.notifyCustomerDao.queryNotifyCustomerCountForHandoverNo(conditionDto);
		case NotifyCustomerConstants.SELECT_TYPE_VEHICLEASSEMBLENO:
			// 2.页面录入车次、预计到达时间
			// 按车次、预计到达时间查询
			return this.notifyCustomerDao.queryNotifyCustomerCountForVehicleAssembleNo(conditionDto);
		case NotifyCustomerConstants.SELECT_TYPE_STOCK_ERROR:
			// 3.页面应该录入运单号，但没有录入，直接返回null
			return null;
		default:
			// 默认为库存查询
			return this.notifyCustomerDao.queryNotifyCustomerCountForStock(conditionDto);
		}
	}

	/**
	 * 通过运单编号查询运单通知记录.
	 * 
	 * @param conditionDto the condition dto
	 * @return the notify customer condition dto
	 * @author ibm-wangfei
	 * @date Oct 24, 2012 4:36:38 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService#queryWaybillInfo(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public NotifyCustomerConditionDto queryWaybillInfo(NotifyCustomerConditionDto conditionDto) {
		if(null == conditionDto || null == conditionDto.getNotifyCustomerDto()){
			throw new NotifyCustomerException("通知客户通过运单编号查询运单通知记录查询条件为空！");
		}
		// 打印查询参数
		LOGGER.info("通过运单编号查询运单通知记录" + ReflectionToStringBuilder.toString(conditionDto));
		// 获取运单明细信息
		NotifyCustomerDto notifyCustomerDto = this.queryNotificationByWaybillNo(conditionDto);
		if(null == notifyCustomerDto){
			throw new NotifyCustomerException("查询客户通知运单明细信息为空！");
		}
		// 将列表中的值直接设置到查询出来出来的dto上
		// 已减少数据库多重关联查询.
		notifyCustomerDtoRelate(conditionDto.getNotifyCustomerDto(), notifyCustomerDto);
		// 根据编码查询组织信息
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(notifyCustomerDto.getReceiveOrgCode());
		// 设置始发部门名称
		notifyCustomerDto.setReceiveOrgName(orgAdministrativeInfoEntity == null ? "" : orgAdministrativeInfoEntity.getName());
		// 获取客户相关信息
		setCustomerInfo(notifyCustomerDto);
		
		//用于通知客户显示实际收货地址信息;
		String acutalAdressVirtual = notifyCustomerDto.getReceiveCustomerAddress();
		
		// 设置送货地址
		notifyCustomerDto.setReceiveCustomerAddress(handleQueryOutfieldService.getCompleteAddressAttachAddrNote(notifyCustomerDto.getReceiveCustomerProvCode(), notifyCustomerDto.getReceiveCustomerCityCode(), notifyCustomerDto.getReceiveCustomerDistCode(),
				notifyCustomerDto.getReceiveCustomerVillageCode(), notifyCustomerDto.getReceiveCustomerAddress(), notifyCustomerDto.getReceiveCustomerAddressNote()));
		
		//取最新一条通知记录的实际收货地址，如果通知记录没有则读取开单地址
		NotificationEntity notificationEntity = new NotificationEntity();
		notificationEntity.setWaybillNo(conditionDto.getWaybillNo());
		NotificationEntity notify1 =notifyCustomerService.queryLastNotifyByWaybillNo(notificationEntity);
		
		//设置根据运单带出显示在通知界面的实际收货地址省-市-区， 街道(备注)， 详细地址
		NotificationEntity notification = new NotificationEntity();
		if (null != notify1) {
			if(StringUtils.isNotBlank(notify1.getActualProvCode())||
					StringUtils.isNotBlank(notify1.getActualCityCode())||
					StringUtils.isNotBlank(notify1.getActualDistrictCode())||
					StringUtil.isNotBlank(notify1.getActualAddressDetail())||
					StringUtil.isNotBlank(notify1.getActualStreetn())){
				//转换为页面用dto
				notifyCustomerDto.setReceiveCustomerProvCode(notify1.getActualProvCode()==null?null:notify1.getActualProvCode());
				notifyCustomerDto.setReceiveCustomerCityCode(notify1.getActualCityCode()==null?null:notify1.getActualCityCode());
				notifyCustomerDto.setReceiveCustomerDistCode(notify1.getActualDistrictCode()==null?null:notify1.getActualDistrictCode());
				
				String actualProvN = handleQueryOutfieldService.getCompleteAddress(notify1.getActualProvCode(), null, null, "");
				notification.setActualProvN(actualProvN);
				String actualCityN = handleQueryOutfieldService.getCompleteAddress(null, notify1.getActualCityCode(), null, "");
				notification.setActualCityN(actualCityN);
				String actualDistN = handleQueryOutfieldService.getCompleteAddress(null, null, notify1.getActualDistrictCode(), "");
				notification.setActualDistrictN(actualDistN);
				
				notification.setActualStreetn(notify1.getActualStreetn()==null?null:notify1.getActualStreetn());
				notification.setActualAddressDetail(notify1.getActualAddressDetail()==null?null:notify1.getActualAddressDetail());
			} else {
				String actualProvN = handleQueryOutfieldService.getCompleteAddress(notifyCustomerDto.getReceiveCustomerProvCode(), null, null, "");
				notification.setActualProvN(actualProvN);
				String actualCityN = handleQueryOutfieldService.getCompleteAddress(null, notifyCustomerDto.getReceiveCustomerCityCode(), null, "");
				notification.setActualCityN(actualCityN);
				String actualDistN = handleQueryOutfieldService.getCompleteAddress(null, null, notifyCustomerDto.getReceiveCustomerDistCode(), "");
				notification.setActualDistrictN(actualDistN);
				
				//设置收货地址详细信息
				notification.setActualStreetn(notifyCustomerDto.getReceiveCustomerAddressNote());
				////设置收货地址
				notification.setActualAddressDetail(acutalAdressVirtual);		
			}
			//设置会展货
			notification.setIsExhibition(notify1.getIsExhibition());
			//设置空车出
			notification.setIsEmptyCar(notify1.getIsEmptyCar());
			//设置送货时间
			notification.setDeliverDate(notify1.getDeliverDate());
		} else {
			String actualProvN = handleQueryOutfieldService.getCompleteAddress(notifyCustomerDto.getReceiveCustomerProvCode(), null, null, "");
			notification.setActualProvN(actualProvN);
			String actualCityN = handleQueryOutfieldService.getCompleteAddress(null, notifyCustomerDto.getReceiveCustomerCityCode(), null, "");
			notification.setActualCityN(actualCityN);
			String actualDistN = handleQueryOutfieldService.getCompleteAddress(null, null, notifyCustomerDto.getReceiveCustomerDistCode(), "");
			notification.setActualDistrictN(actualDistN);
			
			//设置收货地址详细信息
			notification.setActualStreetn(notifyCustomerDto.getReceiveCustomerAddressNote());
			////设置收货地址
			notification.setActualAddressDetail(acutalAdressVirtual);			
		}
		conditionDto.setNotificationEntity(notification);
		
		// 根据运单号查询客户的应收单到付金额 --结算接口
		//存放应收单查询过滤条件 dto
		//BillReceivableConditionDto billReceiveable = new BillReceivableConditionDto(conditionDto.getWaybillNo());
		// 根据运单号查询客户的应收单到付金额和应收代收货款金额
		// BUG-37198
		// 通知客户点击通知信息界面的到付总金额没有加上代收货款，给业务操作造成了不便和困扰！修改
		/*
		 * List<BillReceivableEntity> billReceivableEntities =
		 * billReceivableService
		 * .queryReceivableAmountByCondition(billReceiveable); for
		 * (BillReceivableEntity billReceivableEntity : billReceivableEntities)
		 * { // 到达应收单 if (SettlementDictionaryConstants.
		 * BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE
		 * .equals(billReceivableEntity.getBillType())) { // 应收到付款
		 * notifyCustomerDto
		 * .setToPayAmount(billReceivableEntity.getUnverifyAmount()); break; }
		 * else { continue; } }
		 */
		if (notifyCustomerDto.getStorageCharge() != null && notifyCustomerDto.getStorageCharge().compareTo(BigDecimal.ZERO) > 0) {
			// 设置保管费计算的共通参数
			String[] codes = { NotifyCustomerConstants.STORAGE_CHARGE_MIN_DESC };// 最小仓储费
			// 系统配置参数获取
			StorageJobDto storageJobDto = getConfigurationParams(codes);
			// 设置仓储费最小金额
			if (storageJobDto.getStorageChangeMin().compareTo(notifyCustomerDto.getStorageCharge()) > 0) {
				notifyCustomerDto.setStorageCharge(storageJobDto.getStorageChangeMin());
			}else{
				//对保管费取整数
				notifyCustomerDto.setStorageCharge(notifyCustomerDto.getStorageCharge().setScale(0,BigDecimal.ROUND_HALF_UP));	//DEFECT-5186 
			}
		}
		// 设置 运单通知信息.
		conditionDto.setNotifyCustomerDto(notifyCustomerDto);
		
		notification.setWaybillNo(conditionDto.getWaybillNo());
		notification.setOrder("DESC");
		notification.setOperateOrgCode(FossUserContextHelper.getOrgCode());//操作部门编码
		notification.setModuleName(NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY);

		//获取运单信息中的特殊增值服务类型字段,供给供应商推送该类运单的到货信息  -- add by 243921
		notification.setSpecialValueAddedService(notifyCustomerDto.getSpecialValueAddedService());

		// 运单通知列表信息
		List<NotificationEntity> list = notifyCustomerDao.queryNotificationsByParam(notification);
		// 设置 运单通知历史记录/批量通知.
		conditionDto.setNotificationEntityList(list);
		if(notification.getDeliverDate() == null || "".equals(notification.getDeliverDate())) {
			// 设置默认的要求送货日期 送货时间默认当前时间；16：00以通知的，默认第二天早上凌晨0点 BUG-31972
			setDefaultDeliverDate(conditionDto);
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				conditionDto.getNotifyCustomerDto().setDeliverDate(sdf.parse(notification.getDeliverDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		// 返回查询结果dto
		return conditionDto;
	}

	/**
	 * 
	 * 设置默认的送货日期
	 * 
	 * @param conditionDto
	 * @author ibm-wangfei
	 * @date Jun 19, 2013 7:14:11 PM
	 */
	private void setDefaultDeliverDate(NotifyCustomerConditionDto conditionDto) {
		Date currDate = new Date();
		// 设置默认的要求送货日期 送货时间默认当天；若是16：00以后的通知，默认第二天
		if (currDate.getHours() > NumberConstants.NUMBER_15) {
			conditionDto.getNotifyCustomerDto().setDeliverDate(DateUtils.getStartDatetime(currDate, 1));
		} else {
			conditionDto.getNotifyCustomerDto().setDeliverDate(currDate);
		}
	}

	/**
	 * 新增通知相关信息.
	 * 
	 * @param conditionDto the condition dto
	 * @throws NotifyCustomerException the notify customer exception
	 * @author ibm-wangfei
	 * @date Oct 22, 2012 11:42:31 AM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService#addNotificationInfo
	 *      (com.deppon.foss.module.pickup.predeliver.api.shared.vo.NotifyCustomerVo)
	 */
	@Override
	public void addNotificationInfo(NotifyCustomerConditionDto conditionDto) {
		// 判断传入参数
		if (conditionDto == null) {
			// 打印异常信息
			LOGGER.error("通知信息异常，传入参数为NULL");
			// 抛出异常
			throw new NotifyCustomerException("通知信息异常，传入参数为NULL");
		}
		//根据是否是固定电话 判断 是否给收货人发送短息 
		NotificationEntity notificationEntity  = conditionDto.getNotificationEntity();
		if(notificationEntity!=null){
			isSendSMSToReceiver(notificationEntity);
		}
		//如果当前通知部门是语音通知
		if(conditionDto.getNotificationEntity() != null&&NotifyCustomerConstants.NOTIFY_TYPE_VOICE_NOTICE.equals(conditionDto.getNotificationEntity().getNoticeType())){
			if(checkIsPilotOrgCode(FossUserContextHelper.getOrgCode())){
				throw new NotifyCustomerException("当前部门为试点部门 ，不能语音通知");//当前部门为试点部门 ，不能语音通知
			}
		}
		
		//保存页面实际收货地址到数据库实际收货地址表
		CustomerReceiptAddressEntity addressEntity = new CustomerReceiptAddressEntity();
		if (null != conditionDto.getNotificationEntity() && null != conditionDto.getNotifyCustomerDto()) {
			if (StringUtil.isNotBlank(conditionDto.getNotificationEntity().getReceiveCustomerCode()) 
					&& StringUtil.isNotBlank(conditionDto.getNotifyCustomerDto().getReceiveCustomerName())) {
				addressEntity.setCustomerCode(conditionDto.getNotificationEntity().getReceiveCustomerCode());
				addressEntity.setCustomerName(conditionDto.getNotifyCustomerDto().getReceiveCustomerName());
				addressEntity.setCustomerContactName(conditionDto.getNotificationEntity().getReceiveCustomerContact());
				addressEntity.setReceiveProvCode(conditionDto.getNotificationEntity().getActualProvCode());
				addressEntity.setReceiveCityCode(conditionDto.getNotificationEntity().getActualCityCode());
				addressEntity.setReceiveDistCode(conditionDto.getNotificationEntity().getActualDistrictCode());
				String addressNotes = (conditionDto.getNotificationEntity().getActualStreetn() == null ? "" : conditionDto.getNotificationEntity().getActualStreetn());
				addressEntity.setReceiveStreet(addressNotes);
				addressEntity.setReceiveAddressDetails(conditionDto.getNotificationEntity().getActualAddressDetail() );
				addressEntity.setCustomerMobilePhone(conditionDto.getNotificationEntity().getReceiveCustomerMobilephone());
				addressEntity.setCustomerPhone(conditionDto.getNotifyCustomerDto().getReceiveCustomerPhone());
				addressEntity.setModifyUserCode(FossUserContext.getCurrentUser().getCreateUser());
				addressEntity.setModifyUserName(FossUserContext.getCurrentUser().getEmpName());
				addressEntity.setModifyDate(new Date());
				addressEntity.setCreaterCode(FossUserContext.getCurrentUser().getCreateUser());
				addressEntity.setCreaterName(FossUserContext.getCurrentUser().getEmpName());
				addressEntity.setCreateDate(new Date());
				addressEntity.setCreateOrgCode(FossUserContext.getCurrentDeptCode());
				addressEntity.setCreateOrgName(FossUserContext.getCurrentDeptName());
				int result = customerReceiptAddressService.insertReceiptAddress(addressEntity);
				
				conditionDto.setNotifyCustomerDto(null);
			}
		}
		
		//运单当前页面的实际收货地址(可能是通知记录最新的实际收货地址， 也可能是开单地址) 
		//如果当前页面的实际送货地址和开单地址一样，则通知保存实际送货地址为null； 否则保存新的实际收货地址(包括null)
		WaybillEntity  waybill =waybillManagerService.queryWaybillBasicByNo(conditionDto.getNotificationEntity().getWaybillNo());
		if (null != waybill) {
			boolean flag = compareWayBill2ActualAddress(waybill, conditionDto.getNotificationEntity());
			if (flag) {
				modifyActualAddress2Null(conditionDto.getNotificationEntity());
			} 
		}
		
		// 打印传入参数
		LOGGER.info("新增通知:" + ReflectionToStringBuilder.toString(conditionDto));
		List<String> waybillParams = new ArrayList<String>(); 
		waybillParams.add(conditionDto.getNotificationEntity().getWaybillNo());
		this.checkWaybillIsMakeUp(waybillParams);

		// 添加运单通知信息
		this.notifyCustomerService.addNotifyCustomer(conditionDto.getNotificationEntity(), conditionDto.getInvoiceInfomationEntity());
	}
	/**
	 * 根据是否是固定电话 判断 是否给收货人发送短息 
	 * @param waybilNo
	 * @date:2015-2-10 09:42:32
	 * @author yuting
	 */
	private void isSendSMSToReceiver(NotificationEntity notificationEntity) {
		//根据是否是固定电话 判断 是否给收货人发送短息  快递派送处理,快递自提签收,pda快递派送签收,pda签收出库 update
		LOGGER.info("--------是否给收货人发送短信--------");
		if(StringUtils.isNotEmpty(notificationEntity.getWaybillNo())){
			WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(notificationEntity.getWaybillNo());
			if(waybill!=null){
				//判断运输性质
				String productCode  = waybill.getProductCode();
				if(StringUtils.isNotEmpty(productCode) && (ExpWaybillConstants.directDetermineIsExpressByProductCode(productCode))){
					String deliveryCustomerCode = waybill.getDeliveryCustomerCode();
					if(StringUtils.isNotEmpty(deliveryCustomerCode)){
						CustomerEntity customerEntity = customerDao.queryCustStateByCode(deliveryCustomerCode);
						if(null != customerEntity){
							String receiverFixedPhone = customerEntity.getFixedReceiveMobile();
							if(StringUtils.isNotEmpty(receiverFixedPhone)){
								String receiveCustomerMobilephone = waybill.getReceiveCustomerMobilephone();
								//取消发送短信
								if(StringUtils.isNotEmpty(receiveCustomerMobilephone)&&receiveCustomerMobilephone.equals(receiverFixedPhone)){
									if(StringUtils.isNotEmpty(notificationEntity.getNoticeType())&&notificationEntity.getNoticeType().equals(NotifyCustomerConstants.NOTIFY_TYPE_TEL_NOTICE)){
										throw new NotifyCustomerException("不能电话通知该收货人！", "不可发送通知短信");
									}else{
										//抛出异常
										throw new NotifyCustomerException("不能为该收货人发送短信！", "不可发送通知短信");
									}
								}
							}
						}						
					}
				}
			}
		}
		LOGGER.info("--------是否给收货人发送短信--------");
	}

	
	/**
	 * 批量通知.
	 * 
	 * @param conditionDto the condition dto
	 * @author ibm-wangfei
	 * @date Nov 2, 2012 4:59:44 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService#batchNotify
	 *      (com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto)
	 */
	@Override
	public void batchNotify(NotifyCustomerConditionDto conditionDto) {
		// 判断传入参数
		if (conditionDto == null || CollectionUtils.isEmpty(conditionDto.getNotificationEntityList())) {
			// 打印异常信息
			LOGGER.info("传入参数为NULL");
			// 返回
			return;
		}
		NotificationEntity notification =conditionDto.getNotificationEntityList().get(0);
		//如果当前通知部门是语音通知
		if(notification != null&&NotifyCustomerConstants.NOTIFY_TYPE_VOICE_NOTICE.equals(notification.getNoticeType())){
			if(checkIsPilotOrgCode(FossUserContextHelper.getOrgCode())){
				throw new NotifyCustomerException("当前部门为试点部门 ，不能语音通知");//当前部门为试点部门 ，不能语音通知
			}
		}
		// 打印批量通知的传入参数
		LOGGER.info("批量通知:" + ReflectionToStringBuilder.toString(conditionDto));
		List<String> waybillParams = new ArrayList<String>(); 
		for (NotificationEntity notificationEntity : conditionDto.getNotificationEntityList()) {
			waybillParams.add(notificationEntity.getWaybillNo());//添加运单号
			//根据是否是固定电话 判断 是否给收货人发送短息 
			if(StringUtils.isNotEmpty(notificationEntity.getNoticeType())&&notificationEntity.getNoticeType().equals(NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE)){
				isSendSMSToReceiver(notificationEntity);
			}
		}
		this.checkWaybillIsMakeUp(waybillParams);
		// 对传入的运单列表进行批量通知
		for (NotificationEntity notificationEntity : conditionDto.getNotificationEntityList()) {
		
			//运单当前页面的实际收货地址(可能是通知记录最新的实际收货地址， 也可能是开单地址)
			//如果当前页面的实际送货地址和开单地址一样，则通知保存实际送货地址为null； 否则保存新的实际收货地址(包括null)
			//这里批量通知没法修改运单的实际收货地址，故通知的时候通知记录实际收货地址都设置为null
			modifyActualAddress2Null(notificationEntity);

			// 添加运单通知信息.
			this.notifyCustomerService.addNotifyCustomer(notificationEntity, null);
		}
	}
	/**
	 * 检验是否试点部门 
	 *  @author 159231-foss-meiying
	 * @date 2014-4-4 上午9:42:14
	 */
	@Override
	public boolean checkIsPilotOrgCode(String orgCode){
		boolean isPilot=false;
		if(StringUtils.isEmpty(orgCode)){
			return isPilot;
		}
		//查询配置参数是否有配置试点部门
		String confValue = configurationParamsService.queryConfValueByCode(NotifyCustomerConstants.NOTICE_CUSTOMER_ORG_CODE);
		if(StringUtils.isNotBlank(confValue)){
			String[] orgCodes =confValue.split(",");
			if(orgCodes!= null && orgCodes.length>0){
				for (String string : orgCodes) {//循环遍历，若传入的部门与试点部门任何一个相等，返回true
					if(NotifyCustomerConstants.NOTICE_CUSTOMER_ORG_DIP.equals(string)){
						isPilot=true;
						break;
					}
					if(string.equals(orgCode)){
						isPilot=true;
						break;
					}
				}
			}
		}/*else {//如果配置参数没有配，后台默认的试点部门（W011305080404,W0113080403,W03050339）
			String[] orgCodes =NotifyCustomerConstants.NOTICE_CUSTOMER_ORG_CODES.split(",");
			if(orgCodes!= null && orgCodes.length>0){
				for (String string : orgCodes) {//循环遍历，若传入的部门与试点部门任何一个相等，返回true
					if(string.equals(orgCode)){
						isPilot=true;
						break;
					}
				}
			}
		}*/
		return isPilot;
	}
	/**
	 * 验证是否存在未补录的运单
	 */
	private void checkWaybillIsMakeUp(List<String> waybillParams){
		WaybillQueryInfoDto queryDto = new WaybillQueryInfoDto();
		List<String> pendingType = new ArrayList<String>();
		pendingType.add(WaybillConstants.WAYBILL_STATUS_PC_ACTIVE);
		pendingType.add(WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE);
		pendingType.add(WaybillConstants.WAYBILL_STATUS_EWAYBILL_ACTIVE);
		queryDto.setPendingTypes(pendingType);
		queryDto.setActive(FossConstants.ACTIVE);//有效
		queryDto.setWaybillList(waybillParams);//运单号
		List<String> waybills = waybillManagerService.queryWaybillNoMakeupByWaybillNo(queryDto);
		if(CollectionUtils.isNotEmpty(waybills)){
			StringBuffer waybillNos = new StringBuffer();
			for (String w : waybills) {
				//运单号
				waybillNos.append(w);
				//常量","
				waybillNos.append(SignConstants.SPLIT_CHAR);
			}
			
			//抛出异常 参数错误
			throw new NotifyCustomerException(NotifyCustomerException.WAYBILL_NOT_MAKEUP,new Object[]{waybillNos.substring(0, waybillNos.length()-1)});
		}
	}
	/**
	 * 根据运单号列表，查询运单相关信息.
	 * 
	 * @param waybillNos the waybill nos
	 * @param notificationEntity the notification entity
	 * @param notificationEntitys the notification entitys
	 * @return the list
	 * @author ibm-wangfei
	 * @date Nov 2, 2012 10:55:13 AM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService#queryWaybillsByNos(java.lang.String[])
	 */
	@Override
	@Transactional(readOnly = true)
	public List<NotifyCustomerDto> queryWaybillsByNos(String waybillNos, NotificationEntity notificationEntity, List<NotificationEntity> notificationEntitys) {
		// 打印传入参数
		LOGGER.info("根据运单号查询运单：", waybillNos);

		if (StringUtil.isEmpty(waybillNos)) {
			// 打印异常信息
			LOGGER.info("运单号为空，退出查询");
			// 运单号为空，退出查询
			return null;
		}
		// 根据运单号列表，查询运单相关信息
		List<NotifyCustomerDto> dtos = this.notifyCustomerDao.queryWaybillsByNos(waybillNos.split(NotifyCustomerConstants.SPLIT_CHAR));
		// 运单相关信息列表判断
		if (CollectionUtils.isEmpty(dtos)) {
			// 运单相关信息列表不存在的时候，返回null
			return null;
		}
		// 设置保管费计算的共通参数
		String[] codes = { NotifyCustomerConstants.STORAGE_CHARGE_MIN_DESC };// 最小仓储费
		// 系统配置参数获取
		StorageJobDto storageJobDto = getConfigurationParams(codes);
		// 打印通知信息
		LOGGER.info(notificationEntity.toString());
		// 根据运单号，转换notificationEntitys为map
		Map<String, Integer> map = new HashMap<String, Integer>();
		// 判断传入的notificationEntitys列表
		if (CollectionUtils.isNotEmpty(notificationEntitys)) {
			// 对列表进行循环
			for (NotificationEntity entity : notificationEntitys) {
				// 运单号-到达数量
				map.put(entity.getWaybillNo(), entity.getArriveGoodsQty());
				// 打印map的信息
				LOGGER.info(entity.getWaybillNo(), entity.getArriveGoodsQty());
			}
		}
		// 运单相关信息设置
		for (NotifyCustomerDto dto : dtos) {
			// 判断页面传入的选择数据运单号和到达数量
			if (!map.isEmpty()) {
				// 设置通知信息的到达数量
				notificationEntity.setArriveGoodsQty(map.get(dto.getWaybillNo()));
				// 设置dto的到达数量
				dto.setArriveGoodsQty(map.get(dto.getWaybillNo()));
			}
			if (dto.getStorageCharge() != null && dto.getStorageCharge().compareTo(BigDecimal.ZERO) > 0) {
				// 设置仓储费最小金额
				if (storageJobDto.getStorageChangeMin().compareTo(dto.getStorageCharge()) > 0) {
					dto.setStorageCharge(storageJobDto.getStorageChangeMin());
				}else{
					//对保管费取整数
					dto.setStorageCharge(dto.getStorageCharge().setScale(0,BigDecimal.ROUND_HALF_UP));	//DEFECT-5186 
				}
			}
			// 设置通知信息
			String[] noticeContents = queryNoticeContent(notificationEntity, dto);
			dto.setNoticeContent(noticeContents[0]);
			dto.setNoticeContentVoice(noticeContents[1]);

			//获取运单信息中的特殊增值服务类型字段,供给供应商推送该类运单的到货信息  -- add by 243921
			notificationEntity.setSpecialValueAddedService(dto.getSpecialValueAddedService());
		}
		// 返回dto列表
		return dtos;
	}

	/**
	 * 查询保管费.
	 * 
	 * @param waybillNos the waybill nos
	 * @return the list
	 * @author ibm-wangfei
	 * @date Nov 5, 2012 11:44:42 AM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService#queryStorageList(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<NotifyCustomerDto> queryStorageList(String waybillNos) {
		// 打印输入参数
		LOGGER.info("查询保管费:", waybillNos);
		List<NotifyCustomerDto> dtos = null;
		// 判断传入的运单号列表是否为空
		if (StringUtil.isEmpty(waybillNos)) {
			// 打印输出
			LOGGER.info("not select waybill，return query StorageList");
			// 返回努力值
			return null;
		}
		// 根据运单号列表，查询运单相关信息
		dtos = this.notifyCustomerDao.queryWaybillsByNos(waybillNos.split(NotifyCustomerConstants.SPLIT_CHAR));
		// 判断查询结果
		if (dtos == null || dtos.size() == 0) {
			// 返回null
			return null;
		}
		// 设置保管费计算的共通参数
		String[] codes = { NotifyCustomerConstants.STORAGE_CHARGE_MIN_DESC };// 最小仓储费
		// 系统配置参数获取
		StorageJobDto storageJobDto = getConfigurationParams(codes);
		ExceptionProcessConditionDto exceptionProcessConditionDto = null;
		List<ExceptionProcessDto> exceptionProcessDtos = null;

		// 对结果循环，设置属性
		for (NotifyCustomerDto dto : dtos) {
			// 设置保管费的异常信息
			exceptionProcessConditionDto = new ExceptionProcessConditionDto();
			// 设置运单号
			exceptionProcessConditionDto.setWaybillNo(dto.getWaybillNo());
			// 设置异常类型
			exceptionProcessConditionDto.setStatus(ExceptionProcessConstants.HANDLING);
			exceptionProcessConditionDto.setActive(FossConstants.ACTIVE);
			// 查询是否存在相同异常
			if (dto.getStorageCharge() != null && dto.getStorageCharge().compareTo(BigDecimal.ZERO) > 0) {
				// 设置仓储费最小金额
				if (storageJobDto.getStorageChangeMin().compareTo(dto.getStorageCharge()) > 0) {
					dto.setStorageCharge(storageJobDto.getStorageChangeMin());
				}else{
					//对保管费取整数
					dto.setStorageCharge(dto.getStorageCharge().setScale(0,BigDecimal.ROUND_HALF_UP));	//DEFECT-5186 
				}
			}
			exceptionProcessDtos = exceptionProcessService.queryExceptionProcess(exceptionProcessConditionDto);
			// 判断查询结果
			if (exceptionProcessDtos == null || exceptionProcessDtos.size() == 0) {
				// 不存在，退出本次循环
				continue;
			}
			// 定义异常信息变量
			StringBuffer exceptionLinks = new StringBuffer();
			// 定义备注变量
			StringBuffer notes = new StringBuffer();
			// 对异常结果进行循环
			for (ExceptionProcessDto edto : exceptionProcessDtos) {
				// 设置异常类型
				exceptionLinks.append(getExceptionValue(edto.getExceptionLink())).append(" ");
				// 设置异常信息
				notes.append(edto.getNotes() == null ? "" : edto.getNotes()).append(" ");
			}
			// 设置异常信息
			dto.setExceptionType(exceptionLinks.toString());
			// 设置异常备注
			dto.setExceptionNotes(notes.toString());
		}
		// 返回dtos
		return dtos;
	}

	/**
	 * 系统参数--获取免费保存天数.
	 * 
	 * @return the warehouseFreeSafeDataNum
	 * @author ibm-wangfei
	 * @date Nov 12, 2012 8:18:47 PM
	 */
	@Override
	public int getWarehouseFreeSafeDataNum() {
		// 设置保管费计算的共通参数
		String[] codes = { NotifyCustomerConstants.WAREHOUSE_FREESAFE_DATA_NUM_DESC };// 默认的免费库存天数
		// 系统配置参数获取
		StorageJobDto storageJobDto = getConfigurationParams(codes);
		// 返回免费保管天数
		return storageJobDto.getWarehouseFreeSafeDataNum();
	}

	/**
	 * 获取可发送短信的时间间隔.
	 * 
	 * @return the informationReceiveTimeRange
	 * @author ibm-wangfei
	 * @date Nov 21, 2012 4:01:01 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService#getInformationReceiveTimeRange()
	 */
	@Override
	public String getInformationReceiveTimeRange() {
		// 返回可发送短信的时间间隔.
		return "8:00-21:00";
	}

	/**
	 * 
	 * 发送短信
	 * 
	 * @param estimatedPickupTime
	 * @author ibm-wangfei
	 * @date Jun 24, 2013 2:24:39 PM
	 */
	@Override
	@Transactional
	public void autoSendMessageDetail(String estimatedPickupTime, NotifyCustomerDto dto,boolean isPilotOrgCode ) {
		// 发送短信
		NotificationEntity entity = new NotificationEntity();
		setAutoNotificationInfo(entity, dto, estimatedPickupTime,isPilotOrgCode);
		// 获取短信内容
		String[] noticeContents = queryNoticeContent(entity, dto);
		if (noticeContents != null && noticeContents.length == 2) {
			entity.setNoticeContent(noticeContents[0]);
			entity.setNoticeContentVoice(noticeContents[1]);
		} else {
			return;
		}
		// 发送短信
		try {
			sendMessage(entity);
			// 更新运单关联表通知状态、最后通知时间、送货日期、付款方式
			updateActualFreightEntityByWaybillNo(entity);
		} catch (NotifyCustomerException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * 设置实体信息
	 * 
	 * @param entity
	 * @param dto
	 * @param estimatedPickupTime
	 * @author ibm-wangfei
	 * @date Jun 24, 2013 2:24:21 PM
	 */
	private void setAutoNotificationInfo(NotificationEntity entity, NotifyCustomerDto dto, String estimatedPickupTime,boolean isPilotOrgCode) {
		//校验当前部门是否为试点部门
		if(isPilotOrgCode){
			// 设置通知内容为短信
			entity.setNoticeType(NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE);
		}else {
			// 设置通知内容为语音
			entity.setNoticeType(NotifyCustomerConstants.NOTIFY_TYPE_VOICE_NOTICE);
		}
		// 预计提货时间
		entity.setEstimatedPickupTime(estimatedPickupTime);
		// 运单号
		entity.setWaybillNo(dto.getWaybillNo());
		//发送部门编码-- 提货网点
		entity.setOperateOrgCode(dto.getCustomerPickupOrgCode());
		//发送人员编码
		entity.setOperatorNo("000000");
		// 电话
		entity.setMobile(dto.getReceiveCustomerMobilephone());
		// 发送人
		entity.setOperator("--");
		// 业务类型
		entity.setModuleName(NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY);
		// 数量
		entity.setArriveGoodsQty(dto.getGoodsQtyTotal());
		// 接收人
		entity.setConsignee(dto.getReceiveCustomerContact());
		// 操作时间
		entity.setOperateTime(new Date());
		// 币种
		entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
	}

	/**
	 * 发送短信.
	 * 
	 * @param notificationEntity the notification entity
	 * @throws NotifyCustomerException the notify customer exception
	 * @author ibm-wangfei
	 * @date Dec 18, 2012 7:01:58 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService#sendMessage
	 *      (com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity)
	 */
	@Override
	public void sendMessage(NotificationEntity notificationEntity) {
		// 必输字段验证
		validateNotificationEntity(notificationEntity);
		//		if (NotifyCustomerConstants.NOTIFY_TYPE_VOICE_NOTICE.equals(notificationEntity.getNoticeType())) {
		//			// 通知方式_语音
		//			notificationEntity.setNoticeResult(NotifyCustomerConstants.VOICE_NOTICING);
		//		} else if (NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE.equals(notificationEntity.getNoticeType())) {
		//			// 通知方式_短信
		//			notificationEntity.setNoticeResult(NotifyCustomerConstants.SMS_NOTICING);
		//		}
		// 通知成功
		notificationEntity.setNoticeResult(NotifyCustomerConstants.SUCCESS);
		if (StringUtil.equals(NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY, notificationEntity.getModuleName()) || StringUtil.equals(NotifyCustomerConstants.BS_TYPE_PKP_SIGNRETURNPROCESS, notificationEntity.getModuleName())) {
			// 通知类型为客户通知或者签收单返单的时候，才插入notificationEntity 
			// 新增运单通知明细信息
			addNotificationEntity(notificationEntity);
		}
		if(NotifyCustomerConstants.SMS_PKP_NOTIFY_EXP.equals(notificationEntity.getModuleName())) {
			notificationEntity.setStopSmsToReciever(ifStopSmsToReciever(notificationEntity.getWaybillNo()));
		}
		//家装项目二期需求：家装类运单短信通知默认为通知成功，不给客户发送短信，但仍发送到货通知到dop  243921
		if(StringUtil.equals(NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY, notificationEntity.getModuleName()) && 
				StringUtils.isNotEmpty(notificationEntity.getSpecialValueAddedService()) &&
				notificationEntity.getSpecialValueAddedService().contains(NotifyCustomerConstants.DELIVER_EQUIP)){
			LOGGER.info("家装运单短信通知默认为通知成功，不给客户发送短信，但仍发送到货通知到dop。");
			this.arrivalGoodsToDop(notificationEntity.getWaybillNo(), notificationEntity.getSpecialValueAddedService());
		}else{
			// 发送短信
			sendSms(notificationEntity);
		}
		
		//add by 231438，快递100，轨迹推送需求RFOSS2015031706；自动通知调用此方法，无法直接获取当前登陆信息，需自己取值封装
		if(StringUtil.equals(NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY, notificationEntity.getModuleName())){
			//推送轨迹,短信通知
			WaybillTrackDto trackDto = new WaybillTrackDto(); // 货物轨迹推送Dto
			//操作部门
			String deptCode = StringUtil.isEmpty(notificationEntity.getOperateOrgCode()) ? FossUserContextHelper.getOrgCode() : notificationEntity.getOperateOrgCode();
			//自动通知取通知时赋值的 发送人员编码："000000";
			String operatorCode = notificationEntity.getOperatorNo();
			//当前用户登录信息
			CurrentInfo currentInfo = this.getCurrentInfo(operatorCode, deptCode);
			//运单号
			trackDto.setWaybillNo(notificationEntity.getWaybillNo());
			//登录信息
			trackDto.setCurrentInfo(currentInfo);
			//操作类型
			trackDto.setOperateType(WaybillTrackingsConstants.OPERATE_TYPE_NOTIFY);
			//通知客户，操作结果描述：客户通知,短信通知成功
			trackDto.setOperateDesc("客户通知，短信通知成功");
			//通知方式_短信：模块_通知 调用轨迹接口，推送轨迹  --add by 231438
			//调用轨迹接口，推送轨迹
			sendWaybillTrackService.sendTrackings(trackDto); 
			//推送菜鸟轨迹--add by 339073
			sendWaybillTrackService.rookieTrackingByNotification(notificationEntity);
		} //--add by 231438
	}
	/**
	 * @author 231438，借鉴159231的pda调用接口封装登录用户信息
	 * 封装登录用户信息，自动通知用
	 */
	private CurrentInfo getCurrentInfo(String operatorCode,String deptCode){
		//通过录入员编码查询姓名
		EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(operatorCode);
		UserEntity user = new UserEntity();
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		// 获取操作部门
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(deptCode);
		if(emp != null){//如果不为空
			user.setEmployee(emp);
		}else {
			emp = new EmployeeEntity();
			emp.setEmpName("SYSTEM");//默认值 给"SYSTEM"
			emp.setEmpCode(operatorCode);//员工编码
			user.setEmployee(emp);//用户信息
		}
		if(org != null){
			dept.setName(org.getName());// 得到部门名称
			dept.setUnifiedCode(org.getUnifiedCode());//标杆编码
		}else {
			dept.setName("SYSTEM");//默认值 给"SYSTEM"
		}
		//部门编码
		dept.setCode(deptCode);
		return new CurrentInfo(user, dept);//返回对象
	}

	/**
	 * 同步更新短信、语音发送状态.
	 * 
	 * @author ibm-wangfei
	 * @date Dec 18, 2012 7:02:12 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService#syncSmsStatus()
	 */
	@Override
	public void syncSmsStatus() {
		/**
		 * // 获取当前时间 Date currDate = new Date(); // 打印参数
		 * LOGGER.info("同步语音、短信发送状态，当前时间：{}", DateUtils.convert(currDate)); //
		 * 获取所有状态为通知未果的短信、语音记录 NotificationEntity notification = new
		 * NotificationEntity(); // 通知方式_电话
		 * notification.setNoticeType(NotifyCustomerConstants
		 * .NOTIFY_TYPE_TEL_NOTICE); // 定义通知结果数组 String notifyResults[] = {
		 * NotifyCustomerConstants.VOICE_NOTICING,
		 * NotifyCustomerConstants.SMS_NOTICING,
		 * NotifyCustomerConstants.NOTICING_UNSUCCESSFUL }; // 设置通知结果
		 * notification.setNotifyResults(notifyResults); // 查询一天内的通知信息 Date
		 * beforeDate = DateUtils.addDayToDate(currDate,
		 * NotifyCustomerConstants.SYNC_SMS_MIN_DAY * -1); // 设置操作时间
		 * notification.setOperateTime(beforeDate); // 设置排序状态
		 * notification.setOrder("ASC"); // 通过运单通知查询运单通知记录
		 * List<NotificationEntity> notifications =
		 * this.notifyCustomerDao.queryNotificationsByParam(notification); //
		 * 判断查询结果list if (CollectionUtils.isNotEmpty(notifications)) { //
		 * 执行短信状态同步操作 exeSmsStatus(notifications, currDate); } else { //
		 * 没有符合条件的记录 LOGGER.info("没有符合条件的记录，更新当期时间一天前的通知未果的短信为通知失败"); } //
		 * 更新之前的短信、运单的通知状态为失败 notifyCustomerService.batchUpdateInfo(beforeDate,
		 * notifyResults);
		 */
	}

	/**
	 * 
	 * 更新之前的短信、运单的通知状态为失败
	 * 
	 * @param beforeDate
	 * @param notifyResults
	 * @author ibmbatchUpdateInfo-wangfei
	 * @date Apr 19, 2013 10:25:14 AM
	 */
	@Transactional
	@Override
	public void batchUpdateInfo(Date beforeDate, String notifyResults[]) {
		// 批量设置通知表和运单表的通知状态
		NotificationEntity entity = new NotificationEntity();
		// 设置操作时间
		entity.setOperateTime(beforeDate);
		// 设置通知结果
		entity.setNotifyResults(notifyResults);
		// 设置失败信息
		entity.setExceptionNotes("短信/语音已发送到短信平台一天，平台未发送，设置为通知失败。");
		// 设置通知状态
		entity.setNewNoticeResult(NotifyCustomerConstants.FAILURE);
		// 更新一天前所有通知中、通知未果的运单为通知失败
		int i = this.notifyCustomerDao.batchUpdateActualFreightEntity(entity);
		// 打印信息
		LOGGER.info("更新当期时间一天前的通知中、通知未果的运单为通知失败记录数为：{}", i);
		// 更新一天前的所有通知中、通知未果的短信为通知失败
		i = this.notifyCustomerDao.batchUpdateNotificationEntity(entity);
		// 打印信息
		LOGGER.info("更新当期时间一天前的通知中、通知未果的短信为通知失败记录数为：{}", i);
	}

	/**
	 * 
	 * 执行短信状态同步操作
	 * 
	 * @param notifications
	 * @param currDate
	 * @author ibm-wangfei
	 * @date Mar 12, 2013 10:26:26 AM
	 */
	private void exeSmsStatus(List<NotificationEntity> notifications, Date currDate) {
		// 打印出记录条数
		LOGGER.info("符合条件的记录数：{}", notifications.size());
		// 短信发送日志entity
		//		SMSSendLogEntity smsSendLog;
		for (NotificationEntity entity : notifications) {
			// 更新通知结果状态
			//			smsSendLog = new SMSSendLogEntity();
			//			smsSendLog.setUnionId(entity.getId());
			//			smsSendLog = smsSendLogService.querySMSSendLog(smsSendLog);
			//			if (smsSendLog == null) {
			//				// 打印continue信息
			//				LOGGER.info("没有短信信息的实体,执行下一条通知");
			//				continue;
			//			}
			notifyCustomerService.excuteInfo(entity, currDate);
		}
	}

	/**
	 * 
	 * 执行具体操作
	 * 
	 * @param entity
	 * @param currDate
	 * @param smsSendLog
	 * @author ibm-wangfei
	 * @date Apr 23, 2013 1:42:06 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService#excuteInfo(com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity,
	 *      java.util.Date,
	 *      com.deppon.foss.module.base.common.api.shared.domain.SMSSendLogEntity)
	 */
	@Transactional
	@Override
	public void excuteInfo(NotificationEntity entity, Date currDate) {
		try {
			// 客户通知信息entity
			NotificationEntity newEntity = new NotificationEntity();
			newEntity.setId(entity.getId());
			//			if (FossConstants.NO.equals(smsSendLog.getIsValidated())) {
			//				// 验证失败
			//				newEntity.setNoticeResult(NotifyCustomerConstants.FAILURE);
			//				newEntity.setExceptionNotes(smsSendLog.getReason());
			//			} else {
			// 短信发送结果entity
			SMSFailLogEntity smsFailLog = new SMSFailLogEntity();
			smsFailLog.setFailIdentity(entity.getId());
			smsFailLog = smsFailLogService.querySMSFailLog(smsFailLog);
			if (smsFailLog == null) {
				// 判断短信3分钟、语音5分钟有无结果返回
				if (NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE.equals(entity.getNoticeType())) {
					// 短信判断
					if (DateUtils.getMinuteDiff(entity.getOperateTime(), currDate) > NotifyCustomerConstants.SMS_NOTICE_MAX_MINUTE) {
						newEntity.setNoticeResult(NotifyCustomerConstants.NOTICING_UNSUCCESSFUL);
						LOGGER.info("短信三分钟后没有结果返回，设置为短信通知未果");
					} else {
						LOGGER.info("短信三分钟内没有结果返回，continue");
						return;
					}
				} else if (NotifyCustomerConstants.NOTIFY_TYPE_VOICE_NOTICE.equals(entity.getNoticeType())) {
					// 语音判断
					if (DateUtils.getMinuteDiff(entity.getOperateTime(), currDate) > NotifyCustomerConstants.VOICE_NOTICE_MAX_MINUTE) {
						newEntity.setNoticeResult(NotifyCustomerConstants.NOTICING_UNSUCCESSFUL);
						LOGGER.info("语音五分钟后没有结果返回，设置为语音通知未果");
					} else {
						LOGGER.info("语音五分钟内没有结果返回，continue");
						return;
					}
				} else {
					// 不做处理
					return;
				}
			} else {
				if (FossConstants.NO.equals(smsFailLog.getIsSuccessed())) {
					// 发送失败
					newEntity.setNoticeResult(NotifyCustomerConstants.FAILURE);
					newEntity.setExceptionNotes(smsFailLog.getFailReason());
				} else {
					// 发送成功
					newEntity.setNoticeResult(NotifyCustomerConstants.SUCCESS);
				}
			}
			//			}
			if (newEntity != null && StringUtil.isNotEmpty(newEntity.getNoticeResult())) {
				// 更新通知状态及错误信息
				this.notifyCustomerDao.updateNotificationEntity(newEntity);
				// 客户通知时，更新运单通知状态
				if (StringUtil.equals(NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY, entity.getModuleName())) {
					// 运单实际货物entity
					ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
					// 运单号
					actualFreightEntity.setWaybillNo(entity.getWaybillNo());
					// 通知状态
					actualFreightEntity.setNotificationResult(newEntity.getNoticeResult());
					this.updateActualFreightEntityByWaybillNo(actualFreightEntity);
				}
			}
		} catch (SMSSendLogException se) {
			LOGGER.error(NotifyCustomerException.ERROR, se);
		} catch (SMSFailLogException fe) {
			LOGGER.error(NotifyCustomerException.ERROR, fe);
		}
	}

	/**
	 * 运单仓储异常JOB.
	 * 
	 * @author ibm-wangfei
	 * @date Dec 18, 2012 7:02:28 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService#processWarehousingTimeout()
	 */
	@Transactional
	@Override
	public void processWarehousingTimeout() {
		// 查询出符合条件的运单列表
		String productCodes[] = { ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT, ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE };

		// 设置保管费计算的共通参数
		String[] codes = { NotifyCustomerConstants.WAREHOUSE_TIMEOUT_DATA_DESC };// 默认的免费库存天数
		StorageJobDto storageJobDto = getConfigurationParams(codes);
		List<NotifyCustomerDto> dtos = notifyCustomerDao.queryWaybillsWithWarehousingTimeout(productCodes, storageJobDto.getWarehouseTimeoutData());
		if (CollectionUtils.isNotEmpty(dtos)) {
			LOGGER.info("待处理运单列表：{}", dtos.size());
			// 部门
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity;
			String lastLoadOrgName;
			for (NotifyCustomerDto dto : dtos) {
				orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(dto.getLastLoadOrgCode());
				if (orgAdministrativeInfoEntity == null) {
					lastLoadOrgName = dto.getLastLoadOrgCode();
				} else {
					lastLoadOrgName = orgAdministrativeInfoEntity.getName();
				}
				ExceptionEntity exceptionEntity = initExceptionEntity(dto.getWaybillNo(), ExceptionProcessConstants.WAYBILL_EXCEPTION, ExceptionProcessConstants.CUSTOMER_PICKUP, dto.getLastLoadOrgCode(), lastLoadOrgName,
						MessageConstants.MSG__SYS_USER_CODE, MessageConstants.MSG__SYS_USER_CODE);
				// 删除原有的仓储异常
				exceptionProcessService.deleteExceptionProcessInfo2Pickup(exceptionEntity);
				// 新增仓储异常
				exceptionProcessService.addExceptionProcessInfo(exceptionEntity);
				exceptionProcessService.addExceptionProcessDetail(initExceptionProcessDetailEntity(exceptionEntity, "仓储超时异常", dto.getLastLoadOrgCode(), lastLoadOrgName, MessageConstants.MSG__SYS_USER_CODE, MessageConstants.MSG__SYS_USER_CODE));
			}
		} else {
			// 这里不做处理
			LOGGER.info("没有记录，退出仓储异常执行");
		}
	}

	/**
	 * 根据运单编号，更新运单附属表的通知信息.
	 * 
	 * @param actualFreightEntity the actual freight entity
	 *            actualFreightEntity.waybillNo 运单号
	 *            actualFreightEntity.notificationTime 通知时间
	 *            actualFreightEntity.deliverDate 送货日期
	 *            actualFreightEntity.notificationResult 通知结果
	 *            actualFreightEntity.paymentType 付款方式
	 * @return the int
	 * @author ibm-wangfei
	 * @date Dec 18, 2012 7:02:41 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService#updateActualFreightEntityByWaybillNo(com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity)
	 */
	@Override
	public int updateActualFreightEntityByWaybillNo(ActualFreightEntity actualFreightEntity) {
		// 打印日志
		LOGGER.info("待更新的actualFreightEntity：" + ReflectionToStringBuilder.toString(actualFreightEntity));
		return notifyCustomerDao.updateActualFreightEntityByWaybillNo(actualFreightEntity);
	}

	/**
	 * 获取客户通知短信、语音内容.
	 * 
	 * @param entity the entity
	 * @return the string
	 * @author ibm-wangfei
	 * @date Dec 18, 2012 7:02:52 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService#queryNoticeContent
	 *      (com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity)
	 */
	@Override
	public String[] queryNoticeContent(NotificationEntity entity) {
		String[] noticeContents = { "", "" };
		if (entity == null || StringUtil.isEmpty(entity.getWaybillNo())) {
			return noticeContents;
		} else {
			// 继续处理
			LOGGER.info("继续处理");
		}
		String[] waybills = { entity.getWaybillNo() };
		List<NotifyCustomerDto> dtos = this.notifyCustomerDao.queryWaybillsByNos(waybills);
		
		if (CollectionUtils.isEmpty(dtos)) {
			return noticeContents;
		} else {
			NotifyCustomerDto dto = dtos.get(0);
			if (dto.getStorageCharge() != null && dto.getStorageCharge().compareTo(BigDecimal.ZERO) > 0) {
				// 设置保管费计算的共通参数
				String[] codes = { NotifyCustomerConstants.STORAGE_CHARGE_MIN_DESC };// 最小仓储费
				// 系统配置参数获取
				StorageJobDto storageJobDto = getConfigurationParams(codes);
				// 设置仓储费最小金额
				if (storageJobDto.getStorageChangeMin().compareTo(dto.getStorageCharge()) > 0) {
					dto.setStorageCharge(storageJobDto.getStorageChangeMin());
				}else{
					//对保管费取整数
					dto.setStorageCharge(dto.getStorageCharge().setScale(0,BigDecimal.ROUND_HALF_UP));	//DEFECT-5186 
				}
			}
			// 获取短信信息文本
			return queryNoticeContent(entity, dto);
		}
	}

	/**
	 * 通知内容验证.
	 * 
	 * @param entity the entity
	 * @throws NotifyCustomerException the notify customer exception
	 * @author ibm-wangfei
	 * @date Dec 24, 2012 12:37:43 PM
	 */
	private void validateNotificationEntity(NotificationEntity entity) {
		if (entity == null) {
			LOGGER.info("短信entity不能为null");
			throw new NotifyCustomerException("短信entity不能为null");
		}
		// 验证电话号码
		if (StringUtil.isBlank(entity.getNoticeType())) {
			LOGGER.info("通知类型不能为空");
			throw new NotifyCustomerException("通知类型不能为空");
		}
		// 通知类型为电话时，直接返回
		if (NotifyCustomerConstants.NOTIFY_TYPE_TEL_NOTICE.equals(entity.getNoticeType())) {
			LOGGER.info("通知类型通知类型为电话时，不执行以下验证");
			return;
		}
		// 验证电话号码
		if (StringUtil.isBlank(entity.getMobile())) {
			LOGGER.info("手机号码不能为空");
			throw new NotifyCustomerException("手机号码不能为空");
		}
		// 验证通知内容
		if (StringUtil.isBlank(entity.getNoticeContent())) {
			LOGGER.info("短信/语音内容不能为空");
			throw new NotifyCustomerException("短信/语音内容不能为空");
		}
		// 验证接收人姓名
		if (StringUtil.isBlank(entity.getConsignee())) {
			LOGGER.info("接收人姓名不能为空");
			throw new NotifyCustomerException("接收人姓名不能为空");
		}

		// 运单号
		if (StringUtil.isBlank(entity.getWaybillNo())) {
			LOGGER.info("运单号不能为空");
			throw new NotifyCustomerException("运单号不能为空");
		}

		// 模块名称
		if (StringUtil.isBlank(entity.getModuleName())) {
			LOGGER.info("模块名称不能为空");
			throw new NotifyCustomerException("模块名称不能为空");
		}
	}

	/**
	 * 添加运单通知信息.
	 * 
	 * @param notificationEntity the notification entity
	 * @param invoiceInfomationEntity the invoice infomation entity
	 * @throws NotifyCustomerException the notify customer exception
	 * @author ibm-wangfei
	 * @date Dec 24, 2012 12:52:57 PM
	 */
	@Transactional
	@Override
	public void addNotifyCustomer(NotificationEntity notificationEntity, InvoiceInfomationEntity invoiceInfomationEntity) {
		
		if (notificationEntity == null) {
			throw new NotifyCustomerException("通知信息异常");
		}
		// 非电话通知，通知成功不需要再次通知
		if (!NotifyCustomerConstants.NOTIFY_TYPE_TEL_NOTICE.equals(notificationEntity.getNoticeType())) {
			// 通知成功的，当天不用再次通知
			boolean isNeed = validate(notificationEntity);
			if (!isNeed) {
				//抛出派送单异常
				throw new NotifyCustomerException(NotifyCustomerException.WAYBILL_IS_SEND_MESS);   //
			}
		}
		MutexElement mutexElement = new MutexElement(notificationEntity.getWaybillNo(), "运单号", MutexElementType.CUSTOMER_NOTIFY);
		//互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement, NumberConstants.ZERO);
		//若未上锁
		if (!isLocked) {
			//抛出派送单异常
			throw new NotifyCustomerException(NotifyCustomerException.NOTIFY_UNDERWAY);
		}
		try {
			// 对传入的通知信息实体进行实例化处理
			initNotificationEntity(notificationEntity);
			//家装项目二期需求：家装类运单语音/短信通知默认为通知成功，不给客户发送短信，但仍发送到货通知到dop 243921
			if(StringUtils.isNotEmpty(notificationEntity.getSpecialValueAddedService()) &&
					notificationEntity.getSpecialValueAddedService().contains(NotifyCustomerConstants.DELIVER_EQUIP)){
				notificationEntity.setNoticeResult(NotifyCustomerConstants.SUCCESS);
			}
			if (NotifyCustomerConstants.NOTIFY_TYPE_VOICE_NOTICE.equals(notificationEntity.getNoticeType())) {
				// 通知类型为语音
				// 新增短信实体
				//			notificationEntity.setNoticeType(NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE);
				// 新增运单通知明细信息
				//			addNotificationEntity(notificationEntity);
				if (StringUtil.isNotBlank(notificationEntity.getNoticeContentVoice())) {
					String smsNotict = notificationEntity.getNoticeContent();
					//				String smsId = notificationEntity.getId();
					// 新增语音实体
					notificationEntity.setNoticeType(NotifyCustomerConstants.NOTIFY_TYPE_VOICE_NOTICE);
					notificationEntity.setNoticeContent(notificationEntity.getNoticeContentVoice());
					// 新增运单通知明细信息
					addNotificationEntity(notificationEntity);
					notificationEntity.setNoticeContent(smsNotict);
					//				notificationEntity.setNoticeContentVoiceID(notificationEntity.getId());
					//				notificationEntity.setId(smsId);
				}
			} else {
				// 新增运单通知明细信息
				addNotificationEntity(notificationEntity);
			}
			//推送到货信息  add by 243921  语音/短信/电话  都需推送到货信息给dop
			this.arrivalGoodsToDop(notificationEntity.getWaybillNo(),notificationEntity.getSpecialValueAddedService());
			
			if (NotifyCustomerConstants.NOTIFY_TYPE_TEL_NOTICE.equals(notificationEntity.getNoticeType())) {
				// 电话通知

				//推送轨迹 --add by 231438，快递100，轨迹推送需求RFOSS2015031706 
				WaybillTrackDto trackDto = new WaybillTrackDto(); // 货物轨迹推送Dto
				//当前用户登录信息
				CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
				if(null == currentInfo){ //验证登录信息
					throw new NotifyCustomerException("登录信息为空！");
				}
				//运单号
				trackDto.setWaybillNo(notificationEntity.getWaybillNo());
				//登录信息
				trackDto.setCurrentInfo(currentInfo);
				//操作类型
				trackDto.setOperateType(WaybillTrackingsConstants.OPERATE_TYPE_NOTIFY);
				
				// 通知成功
				if (NotifyCustomerConstants.SUCCESS.equals(notificationEntity.getNoticeResult())) {
					// 判断能否月结/欠款
					checkIsBeBebt(notificationEntity);

					// 电话通知并且通知成功时,新增到达联信息
					ArriveSheetEntity entity = initArriveSheetEntity(notificationEntity);
					// 根据运单号校验生成到达联
					arriveSheetManngerService.checkGenerateArriveSheet(entity);
					// 更新到达联的通知客户录入信息
					arriveSheetManngerService.updateArriveSheetEntityByWaybillNo(entity);

					//电话通知成功,操作结果描述
					trackDto.setOperateDesc("客户通知，电话通知成功");
					
				}else{//电话通知失败 ，推送轨迹 --add by 231438
					//通知客户，操作结果描述
					trackDto.setOperateDesc("客户通知，电话通知失败");
				}
				//调用轨迹接口，推送轨迹
				sendWaybillTrackService.sendTrackings(trackDto); //--add by 231438
				// 添加通知发票信息
				if (invoiceInfomationEntity != null && FossConstants.YES.equals(notificationEntity.getIsNeedInvoice())) {
					invoiceInfomationEntity.setNotificationId(notificationEntity.getId());
					addInvoiceInfomationEntity(invoiceInfomationEntity);
				}
				// 根据通知结果，进行异常流程处理
				updateExceptionProcess(notificationEntity);
			} else {
				// 必输字段验证
				validateNotificationEntity(notificationEntity);
				//家装项目二期需求：家装类运单语音/短信通知默认为通知成功，不给客户发送短信，但仍发送到货通知到dop  243921
				if(StringUtils.isNotEmpty(notificationEntity.getSpecialValueAddedService()) &&
						notificationEntity.getSpecialValueAddedService().contains(NotifyCustomerConstants.DELIVER_EQUIP)){

				}else{
					// 语音、短信通知
					sendSms(notificationEntity);
				}
				
				//推送轨迹,短信通知 --add by 231438，快递100，轨迹推送需求RFOSS2015031706
				WaybillTrackDto trackDto = new WaybillTrackDto(); // 货物轨迹推送Dto
				if(null == FossUserContext.getCurrentInfo()){ //验证登录信息
					throw new NotifyCustomerException("快递100-轨迹推送，登录信息为空！");
				}
				//当前用户登录信息
				CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
				//运单号
				trackDto.setWaybillNo(notificationEntity.getWaybillNo());
				//登录信息
				trackDto.setCurrentInfo(currentInfo);
				//操作类型
				trackDto.setOperateType(WaybillTrackingsConstants.OPERATE_TYPE_NOTIFY);
				//通知客户，操作结果描述：客户通知,短信通知成功
				trackDto.setOperateDesc("客户通知，短信通知成功");
				//通知方式_短信：模块_通知 调用轨迹接口，推送轨迹  --add by 231438
				if(StringUtil.equals(NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY, notificationEntity.getModuleName())){
					//调用轨迹接口，推送轨迹
					sendWaybillTrackService.sendTrackings(trackDto); //--add by 231438
				}
				//通知模块-短信通知：调用轨迹接口，推送轨迹  --add by 231438
				sendWaybillTrackService.sendTrackings(trackDto);
			}
			//推送菜鸟轨迹
			sendWaybillTrackService.rookieTrackingByNotification(notificationEntity);
			//DP-FOSS-零担-派送兑现时效显示功能优化需求DN201606230002  by370196
			notificationEntity.setCashTime(this.executeCashTime(notificationEntity.getWaybillNo()));
			// 更新运单关联表通知状态、最后通知时间、送货日期、付款方式
			updateActualFreightEntityByWaybillNo(notificationEntity);
		} catch (NotifyCustomerException e) {
			businessLockService.unlock(mutexElement);
			throw new NotifyCustomerException(e.getErrorCode());
		} 
		businessLockService.unlock(mutexElement);
	}
	
	
	/**
	 * @param notificationEntity
	 * @throws ParseException
	 * 计算规定兑现时间
	 */ 
	@Override
	public Date executeCashTime(String waybillNo){
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
		//根据运单的运输性质判断,如果是精准城运和精准卡航有规定兑现时间
		if(waybillEntity.getProductCode()!=null&&
				(waybillEntity.getProductCode().equals("FLF")||
					waybillEntity.getProductCode().equals("FSF"))) {
			//根据运单号查询走货路径详情
			List<PathDetailEntity> pathDetailList = calculateTransportPathService.queryPathDetailByNos(waybillNo,null);
			if(pathDetailList==null||pathDetailList.size()==0) {
				return null;
			}
			//查出运单的起始时间
			Date waybillBeginTime = waybillEntity.getBillTime();
			//使用Calender类进行计算
			Calendar waybillBeginCalendar = Calendar.getInstance();
			waybillBeginCalendar.setTime(waybillBeginTime);
			//保存最后部门code
			String lastOrgCode = null ;
			try {
				for(int i=0;i<pathDetailList.size();i++) {
					/*
					 * 是中间站：false，
					 * 最后一站不是外场（非驻地部门）：true，无卸货时间，直接设最后到达时间
					 * 最后一站为外场（驻地部门）：不求最后一段时间，
					 */
					byte lineType =0;
					if(i==0) {
						//始发线路
						if(!addTransportTime( waybillBeginCalendar, pathDetailList.get(i),lineType)){
							return null;
						}
					}else if(i==pathDetailList.size()-1) {
						//到达线路
						lineType = 2;
						//最后的营业部是不是驻地部门（外场）
						lastOrgCode = pathDetailList.get(i).getObjectiveOrgCode();
						SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(lastOrgCode);
						if(saleDepartmentEntity==null) {
							return null;
						}
						//如果该营业部不是驻地部门（外场）则要计算此段路线时间，是外场则不计算
						if(saleDepartmentEntity.getStation()==null||!saleDepartmentEntity.getStation().equals("Y")) {
							//根据出发部门和目的部门查询运输时间、卸货时间,如果没有查询到则不设规定兑现时间
							if(!addTransportTime( waybillBeginCalendar, pathDetailList.get(i),lineType)){
								return null;
							}
						}
					}else {
						//外场-外场
						lineType = 1;
						//根据出发部门和目的部门查询运输时间、卸货时间,如果没有查询到则不设规定兑现时间
						if(!addTransportTime( waybillBeginCalendar, pathDetailList.get(i),lineType)){
							return null;
						}
					}
				}
				//根据目的部门查询相应的兑现时间段
				List<LclDeliveryToCashManagementDeliveryEntity> lclDeliveryToCashManagementDeliveryList = 
						lclDeliveryToCashManagementDeliveryService.queryLclDeliveryToCashManagementDeliveryByOrgCode(lastOrgCode);
				if(lclDeliveryToCashManagementDeliveryList==null||lclDeliveryToCashManagementDeliveryList.size()==0) {
					return null;
				}
				Calendar endCalendar = Calendar.getInstance();
				for(int i = 0;i<lclDeliveryToCashManagementDeliveryList.size();i++) {
					Date beginDate =  lclDeliveryToCashManagementDeliveryList.get(i).getStartDate();
					Date endDate = lclDeliveryToCashManagementDeliveryList.get(i).getEndDate();
					Date waybillBeginDate = waybillBeginCalendar.getTime();
					endCalendar.setTime(lclDeliveryToCashManagementDeliveryList.get(i).getEndDate());
					int endHour =endCalendar.get(Calendar.HOUR_OF_DAY);
					int endMinute = endCalendar.get(Calendar.MINUTE);
					/*
					 * 如果：结束时间为00:00其实为24:00则证明已经是一天的最后一段时间
					 * 如果：算出来的最后的时间能够匹配上对应的时间段则取对应的规定兑现时间(包前不包后)
					 */
					if((endHour==0 && endMinute ==0)||
							getDayMinutes(beginDate)<=getDayMinutes(waybillBeginDate)&&
							getDayMinutes(waybillBeginDate)<getDayMinutes(endDate)) {
						Calendar targetTimeCalendar = Calendar.getInstance();
						SimpleDateFormat targetSdf = new SimpleDateFormat("HH:mm");
						Date targetDate = targetSdf.parse(lclDeliveryToCashManagementDeliveryList.get(i).getDeliverOnTime());
						targetTimeCalendar.setTime(targetDate);
						waybillBeginCalendar.set(Calendar.HOUR_OF_DAY,targetTimeCalendar.get(Calendar.HOUR_OF_DAY));
						waybillBeginCalendar.set(Calendar.MINUTE,targetTimeCalendar.get(Calendar.MINUTE));
						waybillBeginCalendar.add(Calendar.DATE, Integer.parseInt(lclDeliveryToCashManagementDeliveryList.get(i).getDeliverOnDay()));
						break;
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
			Date cashTime = waybillBeginCalendar.getTime();
			LOGGER.info("规定兑现时间和运单号：" + cashTime+":"+waybillNo);
			return cashTime;
		}else {
			return null;
		}
	}
	
	
	/**
	 * @param waybillBeginCalendar
	 * @param pathDetailEntity
	 * @param lineType:0、出发线路，1、外场到外场，2、到达线路
	 * @throws ParseException
	 * 计算走货路径时间
	 */
	private boolean addTransportTime(Calendar waybillBeginCalendar, 
			PathDetailEntity pathDetailEntity, byte lineType) throws ParseException {
		SimpleDateFormat HHmmSdf1 = new SimpleDateFormat("HHmm");
		SimpleDateFormat HHmmSdf2 = new SimpleDateFormat("HH:mm");
		if(lineType==0||lineType==2){
			//出发线路和到达线路调同一接口lineType:0、出发线路，1、外场到外场，2、到达线路
			List<DepartureStandardDto> departureStandardDtoList= 
					lineService.queryDepartureStandardListBySourceTarget(
							pathDetailEntity.getOrigOrgCode(),pathDetailEntity.getObjectiveOrgCode());
			if(departureStandardDtoList.size()<=0){
				return false;
			}
			Calendar arriveCalendar = Calendar.getInstance(); 
			arriveCalendar.setTime(HHmmSdf1.parse(departureStandardDtoList.get(departureStandardDtoList.size()-1).getArriveTime()));
			Date leaveDate = HHmmSdf1.parse(departureStandardDtoList.get(departureStandardDtoList.size()-1).getLeaveTime());
			//如果是始发路线，计算的时间迟于出发时间则隔天
			if(getDayMinutes(waybillBeginCalendar.getTime())>getDayMinutes(leaveDate)){
				waybillBeginCalendar.add(Calendar.DAY_OF_MONTH, 1);
			}
	//		加上中间运行的天数
			waybillBeginCalendar.add(Calendar.DAY_OF_MONTH, departureStandardDtoList.get(departureStandardDtoList.size()-1).getArriveDay());
	//		直接设置到达时间
			waybillBeginCalendar.set(Calendar.HOUR_OF_DAY,arriveCalendar.get(Calendar.HOUR_OF_DAY));
			waybillBeginCalendar.set(Calendar.MINUTE,arriveCalendar.get(Calendar.MINUTE));
		}else{
			//查询外场到外场所需运行时间和卸货时间
			List<LclDeliveryToCashManagementUnloadingEntity> lclList=
					lclDeliveryToCashManagementUnloadingService.queryLclDeliveryToCashManagementUnloadingEntitytByCode(
					pathDetailEntity.getOrigOrgCode(),pathDetailEntity.getObjectiveOrgCode());
			if(lclList==null) {
				return false;
			}
			Date scheduleTime = HHmmSdf2.parse(lclList.get(lclList.size()-1).getScheduleTime());
//			如果计算的时间迟于出发时间则证明会隔天			
			if(getDayMinutes(waybillBeginCalendar.getTime())>getDayMinutes(scheduleTime)){
				waybillBeginCalendar.add(Calendar.DAY_OF_MONTH, 1);
			}
			//在计算的时间上增加运行时间（接口已计算总和，格式为小时分：HH:mm + 天数） ，运行时间：时+分
			waybillBeginCalendar.add(Calendar.HOUR_OF_DAY,Integer.parseInt(lclList.get(lclList.size()-1).getScheduleHours()));
			waybillBeginCalendar.add(Calendar.MINUTE,Integer.parseInt(lclList.get(lclList.size()-1).getScheduleMins()));
			//加上卸出时间unloadingTime(HH:mm);
			Date unloadingTime  = HHmmSdf2.parse(lclList.get(lclList.size()-1).getUnloadingTimeOut());
			Calendar unloadingTimeCalendar = Calendar.getInstance();
			unloadingTimeCalendar.setTime(unloadingTime);
			waybillBeginCalendar.add(Calendar.HOUR_OF_DAY,unloadingTimeCalendar.get(Calendar.HOUR_OF_DAY));
			waybillBeginCalendar.add(Calendar.MINUTE,unloadingTimeCalendar.get(Calendar.MINUTE));
		}
		return true;
	}

	/**
	 * @param date
	 * 获取当天经过的分钟
	 * @return
	 */
	private int getDayMinutes(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int calendarHour =calendar.get(Calendar.HOUR_OF_DAY);
		int calendarMinute = calendar.get(Calendar.MINUTE);
		int totalcalendarMinute = calendarHour*60 + calendarMinute;
		return totalcalendarMinute;
	}
	
	/**
	 * 
	 * 通知成功的，当天不用再次通知
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-6-12 下午5:22:27
	 */
	private boolean validate(NotificationEntity notificationEntity) {
		NotificationEntity notification= new NotificationEntity();
		notification.setWaybillNo(notificationEntity.getWaybillNo());
		notification.setOperateOrgCode(FossUserContextHelper.getOrgCode());
		int count = 0;
		if (NotifyCustomerConstants.NOTIFY_TYPE_VOICE_NOTICE.equals(notificationEntity.getNoticeType())) {
			count = notifyCustomerDao.queryVoiceNoticeSuccessByNo(notification);
		} else if (NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE.equals(notificationEntity.getNoticeType())) {
			count = notifyCustomerDao.querySmsNoticeSuccessByNo(notification);
		}
		// 当天已经通知过，返回false
		if (count != 0) {
			return false;
		}
		return true;
	}

	/**
	 * 电话通知并且成功时、判断能否月结/欠款.
	 * 
	 * @param isTelAndSuccess the is tel and success
	 * @param notificationEntity the notification entity
	 * @throws NotifyCustomerException the notify customer exception
	 * @author ibm-wangfei
	 * @date Nov 14, 2012 2:57:31 PM
	 */
	private void checkIsBeBebt(NotificationEntity notificationEntity) {
		try {
			if (StringUtil.isEmpty(notificationEntity.getPaymentType())) {
				return;
			}
			if (!SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH.equals(notificationEntity.getPaymentType())) {
				// 如果不是现金
				DebitDto debitDto = customerBargainService.isBeBebt(notificationEntity.getReceiveCustomerCode(), notificationEntity.getOperateOrgCode(), notificationEntity.getPaymentType(), notificationEntity.getToPayAmount());
				if (!debitDto.isBeBebt()) {
					throw new NotifyCustomerException(debitDto.getMessage());
				}
			}
		} catch (SettlementException se) {
			LOGGER.error(NotifyCustomerException.ERROR, se);
			throw new NotifyCustomerException(se.getErrorCode(), se);
		}
	}

	/**
	 * 新增运单通知明细信息.
	 * 
	 * @param notificationEntity the notification entity
	 * @author ibm-wangfei
	 * @date Oct 26, 2012 4:38:35 PM
	 */
	private void addNotificationEntity(NotificationEntity notificationEntity) {
		notifyCustomerDao.addNotificationEntity(notificationEntity);
	}

	/**
	 * 新增运单发票信息.
	 * 
	 * @param invoiceInfomationEntity the invoice infomation entity
	 * @author ibm-wangfei
	 * @date Oct 26, 2012 4:38:17 PM
	 */
	private void addInvoiceInfomationEntity(InvoiceInfomationEntity invoiceInfomationEntity) {
		if (StringUtil.isNotBlank(invoiceInfomationEntity.getTaxNo()) || 
				StringUtil.isNotBlank(invoiceInfomationEntity.getCompanyName()) || 
				StringUtil.isNotBlank(invoiceInfomationEntity.getTel()) || 
				StringUtil.isNotBlank(invoiceInfomationEntity.getAccount()) || 
				StringUtil.isNotBlank(invoiceInfomationEntity.getBank()) || 
				StringUtil.isNotBlank(invoiceInfomationEntity.getAddress())) {
			initInvoiceInfomationEntity(invoiceInfomationEntity);
			notifyCustomerDao.addInvoiceInfomationEntity(invoiceInfomationEntity);
		} else {
			return;
		}
	}

	/**
	 * 更新运单关联表通知状态、最后通知时间、送货日期、付款方式.
	 * 
	 * @param notificationEntity the notification entity
	 *            notificationEntity.waybillNo 运单号
	 *            notificationEntity.operateTime 通知时间
	 *            notificationEntity.deliverDate 送货日期
	 *            notificationEntity.notificationResult 通知结果
	 *            notificationEntity.paymentType 付款方式
	 * @author ibm-wangfei
	 * @date Nov 5, 2012 5:22:57 PM
	 */
	private void updateActualFreightEntityByWaybillNo(NotificationEntity notificationEntity) {
		ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
		// 运单号
		actualFreightEntity.setWaybillNo(notificationEntity.getWaybillNo());
		// 通知日期
		actualFreightEntity.setNotificationTime(notificationEntity.getOperateTime());
		// 要求送货日期
		actualFreightEntity.setDeliverDate(DateUtils.convert(notificationEntity.getDeliverDate(), DateUtils.DATE_FORMAT));

		// 通知结果
		actualFreightEntity.setNotificationResult(notificationEntity.getNoticeResult());
		// 付款方式
		actualFreightEntity.setPaymentType(notificationEntity.getPaymentType());
		
		//送货时间段，送货时间范围，发票类型
		actualFreightEntity.setDeliveryTimeInterval(notificationEntity.getDeliveryTimeInterval());
		actualFreightEntity.setDeliveryTimeStart(notificationEntity.getDeliveryTimeStart());
		actualFreightEntity.setDeliveryTimeOver(notificationEntity.getDeliveryTimeOver());
		actualFreightEntity.setInvoiceType(notificationEntity.getInvoiceType());
		
		
		// 如果是电话通知且通知成功，则设置曾经电话通知成功过
		if (NotifyCustomerConstants.NOTIFY_TYPE_TEL_NOTICE.equals(notificationEntity.getNoticeType()) && NotifyCustomerConstants.SUCCESS.equals(notificationEntity.getNoticeResult())) {
			actualFreightEntity.setEverTelnoticeSuccess(FossConstants.YES);
		}
		// 设置最后通知部门code
		actualFreightEntity.setNotificationOrgCode(notificationEntity.getOperateOrgCode());
		// 设置规定兑现时间
		actualFreightEntity.setCashTime(notificationEntity.getCashTime());
		LOGGER.info(actualFreightEntity.getCashTime()+"");
		// 根据运单编号，更新运单附属表的通知信息.
		this.updateActualFreightEntityByWaybillNo(actualFreightEntity);
	}

	/**
	 * 根据通知结果，进行异常流程处理.
	 * 
	 * @param notificationEntity the notification entity
	 *            notificationEntity.waybillNo 运单号
	 * @author ibm-wangfei
	 * @date Nov 5, 2012 5:25:43 PM
	 */
	private void updateExceptionProcess(NotificationEntity notificationEntity) {
		try {
			// 1:判断是否插入异常信息
			ExceptionProcessConditionDto exceptionProcessConditionDto = new ExceptionProcessConditionDto();
			// 运单号
			exceptionProcessConditionDto.setWaybillNo(notificationEntity.getWaybillNo());
			// 异常类型
			exceptionProcessConditionDto.setExceptionType(ExceptionProcessConstants.WAYBILL_EXCEPTION);
			// 异常信息
			exceptionProcessConditionDto.setExceptionLink(ExceptionProcessConstants.CUSTOMER_NOTICE);
			// 异常状态
			exceptionProcessConditionDto.setStatus(ExceptionProcessConstants.HANDLING);
			//通知内容
			exceptionProcessConditionDto.setNoticeContext(notificationEntity.getNoticeContent());
			//设置运单状态
			exceptionProcessConditionDto.setActive(FossConstants.YES);
			
			//通知成功，且勾选异常的
			if ("Y".equals(notificationEntity.getIsException()) && NotifyCustomerConstants.SUCCESS.equals(notificationEntity.getNoticeResult())) {
				exceptionProcessConditionDto.setExceptionOperate(ExceptionProcessConstants.PKP_EXCEPTION_INFORM_SUCCESS);
			}
			if (NotifyCustomerConstants.FAILURE.equals(notificationEntity.getNoticeResult())) {
				exceptionProcessConditionDto.setExceptionOperate(ExceptionProcessConstants.PKP_EXCEPTION_INFORM_FAIL);
			}
			
			List<ExceptionProcessDto> exceptionProcessDtos = exceptionProcessService.queryExceptionProcess(exceptionProcessConditionDto);
			if (exceptionProcessDtos == null || exceptionProcessDtos.size() == 0) {
				//新增通知成功且异常的也插入记录
				if ((NotifyCustomerConstants.FAILURE.equals(notificationEntity.getNoticeResult())) || (
						NotifyCustomerConstants.SUCCESS.equals(notificationEntity.getNoticeResult()) && "Y".equals(notificationEntity.getIsException()))) {
					// 如果通知失败，新增异常主子表
					ExceptionEntity exceptionEntity = initExceptionEntity(notificationEntity.getWaybillNo(), ExceptionProcessConstants.WAYBILL_EXCEPTION, ExceptionProcessConstants.CUSTOMER_NOTICE, null, null, null, null);
					//新增异常原因、异常操作、通知内容
					exceptionEntity.setExceptiOperate(exceptionProcessConditionDto.getExceptionOperate());
					exceptionEntity.setExceptionReason(DictUtil.rendererSubmitToDisplay(notificationEntity.getExceptionReason(), DictionaryConstants.PKP_NOTICE_EXCEPTION_REASON));
					exceptionEntity.setNoticeContext(exceptionProcessConditionDto.getNoticeContext());
					exceptionProcessService.addExceptionProcessInfo(exceptionEntity);
					
					if (StringUtil.isNotEmpty(exceptionEntity.getId())) {
						
						//通知成功且异常
						if ((NotifyCustomerConstants.SUCCESS.equals(notificationEntity.getNoticeResult())  && FossConstants.YES.equals(notificationEntity.getIsException()))) {
							String exceptionSeason = DictUtil.rendererSubmitToDisplay(notificationEntity.getExceptionReason(), DictionaryConstants.PKP_NOTICE_EXCEPTION_REASON);
							if (StringUtil.isNotEmpty(exceptionSeason)) {
								notificationEntity.setExceptionNotes(exceptionSeason);
							} else {
								notificationEntity.setExceptionNotes("通知成功且异常");
							}
						}
						
						exceptionProcessService.addExceptionProcessDetail(initExceptionProcessDetailEntity(exceptionEntity, notificationEntity.getExceptionNotes(), null, null, null, null));
					} else {
						LOGGER.error("异常信息插入失败。");
					}
				}
			} else if (exceptionProcessDtos.size() == 1) {
				ExceptionEntity exceptionEntity = new ExceptionEntity();
				exceptionEntity.setId(exceptionProcessDtos.get(0).getExceptionProcessId());
				if (NotifyCustomerConstants.SUCCESS.equals(notificationEntity.getNoticeResult()) && FossConstants.NO.equals(notificationEntity.getIsException())) {
					// 如果通知成功,更新异常主表状态为已处理
					exceptionEntity.setStatus(ExceptionProcessConstants.HANDLED);
					exceptionEntity.setExceptionTime(new Date());
					exceptionProcessService.updateExceptionProcessInfo(exceptionEntity);
				} else if (NotifyCustomerConstants.FAILURE.equals(notificationEntity.getNoticeResult()) 
						|| (NotifyCustomerConstants.SUCCESS.equals(notificationEntity.getNoticeResult())  && FossConstants.YES.equals(notificationEntity.getIsException()) ) ) {
					
					String exceptionSeason = "";
					//通知成功且异常-异常详细里的异常原因下拉框异常原因
					if ((NotifyCustomerConstants.SUCCESS.equals(notificationEntity.getNoticeResult())  && FossConstants.YES.equals(notificationEntity.getIsException()))) {
						exceptionSeason = DictUtil.rendererSubmitToDisplay(notificationEntity.getExceptionReason(), DictionaryConstants.PKP_NOTICE_EXCEPTION_REASON);
						if (StringUtil.isNotEmpty(exceptionSeason)) {
							notificationEntity.setExceptionNotes(exceptionSeason);
						} else {
							notificationEntity.setExceptionNotes("通知成功且异常原因空");
						}
					}
					
					//更新异常主表
					exceptionEntity.setExceptionTime(new Date());
					exceptionEntity.setExceptionReason(exceptionSeason);
					
					LOGGER.info("通知客户--通知内容--更新"+notificationEntity.getNoticeContent());
					//add by foss-sunyanfei 2015-7-30
					exceptionEntity.setNoticeContext(notificationEntity.getNoticeContent());//通知内容
					//add by foss-sunyanfei 2015-7-30
					exceptionProcessService.updateExceptionProcessInfo(exceptionEntity);
					exceptionEntity.setExceptionLink(ExceptionProcessConstants.CUSTOMER_NOTICE);
					
					// 插入异常明细信息
					exceptionProcessService.addExceptionProcessDetail(initExceptionProcessDetailEntity(exceptionEntity, notificationEntity.getExceptionNotes(), null, null, null, null));
				}
			} else if (exceptionProcessDtos.size() == 2) {// 通知成功 (1)把只通知异常的待处理，(2)通知成功且异常的待处理； 同时更新成已处理
				for(ExceptionProcessDto eDto : exceptionProcessDtos) {
					ExceptionEntity exceptionEntity = new ExceptionEntity();
					exceptionEntity.setId(eDto.getExceptionProcessId());
					if (NotifyCustomerConstants.SUCCESS.equals(notificationEntity.getNoticeResult()) && FossConstants.NO.equals(notificationEntity.getIsException())) {
						// 如果通知成功,更新异常主表状态为已处理
						exceptionEntity.setStatus(ExceptionProcessConstants.HANDLED);
						exceptionEntity.setExceptionTime(new Date());
						exceptionProcessService.updateExceptionProcessInfo(exceptionEntity);
					} 
				}
			} else {
				// 这里不做处理
				LOGGER.info("不做处理");
			}
		} catch (ExceptionProcessException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * 查询客户通知运单明细信息.
	 * 
	 * @param conditionDto the condition dto
	 * @return the notify customer dto
	 * @author ibm-wangfei
	 * @date Oct 24, 2012 4:35:50 PM
	 */
	@Override
	public NotifyCustomerDto queryNotificationByWaybillNo(NotifyCustomerConditionDto conditionDto) {
		// 设置active状态
		conditionDto.setActive(FossConstants.ACTIVE);
		// 查询运单通知明细
		return notifyCustomerDao.queryNotifyCustomer(conditionDto);
	}

	/**
	 * 查询客户通知历史记录列表.
	 * 
	 * @param waybillNo the waybill no
	 * @return the list
	 * @author ibm-wangfei
	 * @date Oct 24, 2012 4:35:50 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService#queryNotificationByWaybillNo(java.lang.String)
	 */
	@Override
	public List<NotificationEntity> queryNotificationByWaybillNo(String waybillNo) {
		NotificationEntity notification = new NotificationEntity();
		notification.setWaybillNo(waybillNo);
		notification.setOrder("DESC");
		notification.setModuleName(NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY);
		return notifyCustomerDao.queryNotificationsByParam(notification);
	}
	
	/**
	 * 
	 * 根据运单号，判断运单有没有通知成功过。
	 * 
	 * @param waybillNo
	 * @return
	 * @author ibm-wangfei
	 * @date Jul 4, 2013 10:10:05 AM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService#isNotificationSuccessByWaybillNo(java.lang.String)
	 */
	@Override
	public boolean isNotificationSuccessByWaybillNo(String waybillNo) {
		if (StringUtil.isBlank(waybillNo)) {
			return false;
		}
		NotificationEntity notification = new NotificationEntity();
		notification.setWaybillNo(waybillNo);
		notification.setModuleName(NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY);
		notification.setNoticeResult(NotifyCustomerConstants.SUCCESS);
		List<NotificationEntity> entitys = notifyCustomerDao.queryNotificationsByParam1(notification);
		return CollectionUtils.isEmpty(entitys) ? false : true;
	}
	
	/**
	  * 根据运单号，判断运单是否通知成功过   提供给中转的接口
	  * @author 243921 - foss - zhangtingting
	  * @date 2015-08-18 下午2:17:45
	  * @param waybillNo
	  * @return
	  * @see com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService#isNotificationByWaybillNoForTfr(java.lang.String)
	  */
	@Override
	public boolean isNotificationByWaybillNoForTfr(String waybillNo){
		if (StringUtil.isBlank(waybillNo)) {
			return false;
		}
		NotificationEntity notification = new NotificationEntity();
		notification.setWaybillNo(waybillNo);
		notification.setModuleName(NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY);
		List<NotificationEntity> notificationList = notifyCustomerDao.queryNotificationsByParam1(notification);
		if(CollectionUtils.isNotEmpty(notificationList)){
			//获取最新的通知记录
			NotificationEntity entity = notificationList.get(0);
			if(NotifyCustomerConstants.SUCCESS.equals(entity.getNoticeResult())){//最新通知记录为通知成功
				return true;
			}
		} 
		return false;//无通知记录 或者最新的通知记录为未通知成功
	}

	/**
	 * 设置到达联通知信息
	 * 
	 * @author ibm-wangfei
	 * @date Oct 24, 2012 4:35:50 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService#queryNotificationByWaybillNo(java.lang.String)
	 */
	@Override
	public void setArriveSheetNotifyInfo(ArriveSheetEntity arriveSheetEntity) {
		if (arriveSheetEntity == null || StringUtil.isEmpty(arriveSheetEntity.getWaybillNo())) {
			return;
		}
		NotificationEntity notification = new NotificationEntity();
		notification.setWaybillNo(arriveSheetEntity.getWaybillNo());
		notification.setNoticeType(NotifyCustomerConstants.NOTIFY_TYPE_TEL_NOTICE);
		notification.setModuleName(NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY);
		notification.setNoticeResult(NotifyCustomerConstants.SUCCESS);
		List<NotificationEntity> entitys = notifyCustomerDao.queryNotificationsByParam1(notification);
		if (CollectionUtils.isEmpty(entitys)) {
			return;
		}
		NotificationEntity notificationEntity = (NotificationEntity) entitys.get(0);
		// 是否必送货
		arriveSheetEntity.setIsSentRequired(notificationEntity.getIsSentRequired());
		// 是否需要发票
		arriveSheetEntity.setIsNeedInvoice(notificationEntity.getIsNeedInvoice());
		// 提前通知内容
		arriveSheetEntity.setPreNoticeContent(notificationEntity.getNoticeContent());
		// 送货要求
		arriveSheetEntity.setDeliverRequire(notificationEntity.getDeliverRequire());
		// 要求送货日期
		arriveSheetEntity.setDeliverDate(DateUtils.convert(notificationEntity.getDeliverDate()));
	}

	/**
	 * 查询客户通知历史记录列表.
	 * 
	 * @param waybillNo the waybill no
	 * @return the list
	 * @author ibm-wangfei
	 * @date Oct 24, 2012 4:35:50 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService#queryNotificationByWaybillNo(java.lang.String)
	 */
	@Override
	public List<NotificationEntity> queryNotificationByWaybillNoAsc(String waybillNo) {
		NotificationEntity notification = new NotificationEntity();
		// 运单号
		notification.setWaybillNo(waybillNo);
		// 排序方式
		notification.setOrder("ASC");
		// 通知信息模块
		notification.setModuleName(NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY);
		// 通过运单通知查询运单通知记录
		return notifyCustomerDao.queryNotificationsByParam(notification);
	}

	/**
	 * 
	 * 系统配置参数获取
	 * 
	 * @param configurationCodes []
	 *            NotifyCustomerConstants.MAX_WAREHOUSE_FREE_SAFE_DATA_DESC
	 *            仓储费计费最大天数 NotifyCustomerConstants.STORAGE_CHARGE_MAX_DESC
	 *            仓储费计费最大天数 NotifyCustomerConstants.STORAGE_CHARGE_MIN_DESC
	 *            最小仓储费金额 NotifyCustomerConstants.STORAGE_CHARGE_UNIT_DESC
	 *            仓储费收取标准
	 *            NotifyCustomerConstants.WAREHOUSE_FREESAFE_DATA_NUM_DESC
	 *            默认的免费库存天数 NotifyCustomerConstants.IS_STORAGE_CHARGE_INIT_DESC
	 *            是否仓储费初始化 NotifyCustomerConstants.IS_STORAGE_CHARGE_INIT_DESC
	 *            是否仓储费初始化 NotifyCustomerConstants.WAREHOUSE_TIMEOUT_DATA_DESC
	 *            默认的免费库存天数
	 * @author ibm-wangfei
	 * @date Mar 4, 2013 7:45:03 PM
	 */
	@Override
	public StorageJobDto getConfigurationParams(String[] configurationCodes) {
		StorageJobDto storageJobDto = new StorageJobDto();
		if (configurationCodes != null && configurationCodes.length > 0) {
			for (String configurationCode : configurationCodes) {
				String confValue = configurationParamsService.queryConfValueByCode(configurationCode);
				if (StringUtil.isNotBlank(confValue)) {
					// 保管费计费最大天数
					if (NotifyCustomerConstants.MAX_WAREHOUSE_FREE_SAFE_DATA_DESC.equals(configurationCode)) {
						storageJobDto.setMaxWarehouseFreeSafeData(IntegerUtils.toInteger(confValue));
						continue;
					}
					// 最大保管费金额
					if (NotifyCustomerConstants.STORAGE_CHARGE_MAX_DESC.equals(configurationCode)) {
						storageJobDto.setStorageChangeMax(IntegerUtils.toBigDecimal(confValue));
						continue;
					}
					// 最小保管费金额
					if (NotifyCustomerConstants.STORAGE_CHARGE_MIN_DESC.equals(configurationCode)) {
						storageJobDto.setStorageChangeMin(IntegerUtils.toBigDecimal(confValue));
						continue;
					}
					// 保管费收取标准
					if (NotifyCustomerConstants.STORAGE_CHARGE_UNIT_DESC.equals(configurationCode)) {
						storageJobDto.setStorageChargeUnit(IntegerUtils.toBigDecimal(confValue));
						continue;
					}
					// 默认的免费库存天数
					if (NotifyCustomerConstants.WAREHOUSE_FREESAFE_DATA_NUM_DESC.equals(configurationCode)) {
						storageJobDto.setWarehouseFreeSafeDataNum(IntegerUtils.toInteger(confValue));
						continue;
					}
					// 是否保管费初始化
					if (NotifyCustomerConstants.IS_STORAGE_CHARGE_INIT_DESC.equals(configurationCode)) {
						storageJobDto.setIsStorageChargeInit(confValue);
						continue;
					}
					// 超过X天，算仓储异常
					if (NotifyCustomerConstants.WAREHOUSE_TIMEOUT_DATA_DESC.equals(configurationCode)) {
						storageJobDto.setWarehouseTimeoutData(IntegerUtils.toInteger(confValue));
						continue;
					}
				}
			}
		}
		// 保管费计费最大天数
		storageJobDto.setMaxWarehouseFreeSafeData(storageJobDto.getMaxWarehouseFreeSafeData() == null ? NotifyCustomerConstants.MAX_WAREHOUSE_FREE_SAFE_DATA : storageJobDto.getMaxWarehouseFreeSafeData());
		// 保管费计费最大天数
		storageJobDto.setStorageChangeMax(storageJobDto.getStorageChangeMax() == null ? NotifyCustomerConstants.STORAGE_CHARGE_MAX : storageJobDto.getStorageChangeMax());
		// 最小保管费金额
		storageJobDto.setStorageChangeMin(storageJobDto.getStorageChangeMin() == null ? NotifyCustomerConstants.STORAGE_CHARGE_MIN : storageJobDto.getStorageChangeMin());
		// 保管费收取标准
		storageJobDto.setStorageChargeUnit(storageJobDto.getStorageChargeUnit() == null ? NotifyCustomerConstants.STORAGE_CHARGE_UNIT : storageJobDto.getStorageChargeUnit());
		// 默认的免费库存天数
		if (storageJobDto.getWarehouseFreeSafeDataNum() == null || storageJobDto.getWarehouseFreeSafeDataNum().intValue() < 0) {
			storageJobDto.setWarehouseFreeSafeDataNum(NotifyCustomerConstants.WAREHOUSE_FREESAFE_DATA_NUM);
		}
		// 是否保管费初始化
		storageJobDto.setIsStorageChargeInit(storageJobDto.getIsStorageChargeInit() == null ? FossConstants.NO : storageJobDto.getIsStorageChargeInit());
		// 超过X天，算仓储异常
		storageJobDto.setWarehouseTimeoutData(storageJobDto.getWarehouseTimeoutData() == null ? NotifyCustomerConstants.WAREHOUSE_TIMEOUT_DATA : storageJobDto.getWarehouseTimeoutData());
		return storageJobDto;
	}

	/**
	 * 获取客户相关信息.
	 * 
	 * @param notifyCustomerDto the notify customer dto waybillNo 运单号 orderNo
	 *            订单号 orderChannel 订单来源 orderPaidMethod 订单付款方式
	 *            deliveryCustomerId 发货客户ID deliveryCustomerCode 发货客户编码
	 *            deliveryCustomerName 发货客户名称 deliveryCustomerMobilephone 发货客户手机
	 *            deliveryCustomerPhone 发货客户电话 deliveryCustomerContact 发货客户联系人
	 *            deliveryCustomerNationCode 发货国家 deliveryCustomerProvCode 发货省份
	 *            deliveryCustomerCityCode 发货市 deliveryCustomerDistCode 发货区
	 *            deliveryCustomerAddress 发货具体地址 receiveCustomerId 收货客户ID
	 *            receiveCustomerCode 收货客户编码 receiveCustomerName 收货客户名称
	 *            receiveCustomerMobilephone 收货客户手机 receiveCustomerPhone 收货客户电话
	 *            receiveCustomerContact 收货客户联系人 receiveCustomerNationCode 收货国家
	 *            receiveCustomerProvCode 收货省份 receiveCustomerCityCode 收货市
	 *            receiveCustomerDistCode 收货区 receiveCustomerAddress 收货具体地址
	 *            receiveOrgCode 收货部门(出发部门) productId 产品ID productCode 运输性质
	 *            receiveMethod 提货方式 customerPickupOrgCode 提货网点 loadMethod 配载类型
	 *            targetOrgCode 目的站 pickupToDoor 是否上门接货 driverCode 司机
	 *            pickupCentralized 是否集中接货 loadLineCode 配载线路 loadOrgCode 配载部门
	 *            lastLoadOrgCode 最终配载部门 preDepartureTime 预计出发时间
	 *            preCustomerPickupTime 预计派送/提货时间 carDirectDelivery 是否大车直送
	 *            goodsName 货物名称 goodsQtyTotal 货物总件数 goodsWeightTotal 货物总重量
	 *            goodsVolumeTotal 货物总体积 goodsSize 货物尺寸 goodsTypeCode 货物类型
	 *            preciousGoods 是否贵重物品 specialShapedGoods 是否异形物品 outerNotes 对外备注
	 *            innerNotes 对内备注 goodsPackage 货物包装 insuranceAmount 保价声明价值
	 *            insuranceRate 保价费率 insuranceFee 保价费 codAmount 代收货款 codRate
	 *            代收费率 codFee 代收货款手续费 refundType 退款类型 returnBillType 返单类别
	 *            secretPrepaid 预付费保密 toPayAmount 到付金额 prePayAmount 预付金额
	 *            deliveryGoodsFee 送货费 otherFee 其他费用 packageFee 包装手续费
	 *            promotionsFee 优惠费用 billingType 运费计费类型 unitPrice 运费计费费率
	 *            transportFee 公布价运费 valueAddFee 增值费用 paidMethod 开单付款方式
	 *            arriveType 到达类型 forbiddenLine 禁行 freightMethod 合票方式
	 *            flightShift 航班时间 totalFee 总费用 promotionsCode 优惠编码 createTime
	 *            创建时间 modifyTime 更新时间（业务时间） billTime 开单时间 createUserCode 开单人
	 *            modifyUserCode 更新人 createOrgCode 开单组织 modifyOrgCode 更新组织 refId
	 *            原运单ID refCode 原运单号 currencyCode 币种 isWholeVehicle 是否整车运单
	 *            wholeVehicleAppfee 整车约车报价 wholeVehicleActualfee 整车开单报价
	 *            accountName 返款帐户开户名称 accountCode 返款帐户开户账户 accountBank 返款帐户开户银行
	 *            billWeight 计费重量 pickupFee 接货费 serviceFee 装卸费 preArriveTime
	 *            预计到达时间 transportType 运输类型 printTimes 打印次数 addTime 新增时间
	 *            contactAddressId 联系人地址ID flightNumberType 航班类型
	 *            collectionDepartment 收款部门 transportationRemark 储运事项 active
	 *            运单状态 isPassOwnDepartment 是否经过营业部 otherPackage 其他包装 paperNum
	 *            纸包装 woodNum 木包装 fibreNum 纤包装 salverNum 托包装 membraneNum 膜包装
	 *            deliverCustContactId 发货联系人Id receiverCustContactId 收获联系人ID
	 *            pendingType 处理类型:"PDA_ACTIVE"--PDA已补录，"PC_ACTIVE"--暂存已开单
	 *            licensePlateNum 车牌号 orderVehicleNum 约车编号 stockStatus 库存状态
	 *            arriveGoodsQty 到达件数 stockQty 库存件数 storageDay 在库天数 wayFee 运费
	 *            storageCharge 仓储费 departureTime 出发日期 arriveTime 到达日期
	 *            lastNotifiTime 上次通知日期 handoverNo 交接单号 noticeResult 通知情况
	 *            inStockTime 入库时间 planArriveTime 预计到达时间 handoverGoodsQty 交接件数
	 *            receivingHabits 送货习惯 selectType 查询方式 notificationTimeSpace
	 *            最后通知日期与当前日期的间隔天数 notificationTime 最后通知日期 overdueDay 逾期天数
	 *            exceptionType 异常类型 exceptionNotes 异常信息 noticeContent 通知信息
	 *            taskStatus 车辆到达状态 customerQulification 客户资质（结算方式） creditAmount
	 *            信用额度 lastInStockTime 最后入库时间 receiveOrgName 始发部门名称 isBackFlg
	 *            客户是否有银行账户列表 isPay 是否为网上支付 totalToPayAmount 到付费用合计（到付金额+保管费金额）
	 * @author * ibm-wangfei
	 * @date * Nov 9, 2012 11:29:38 AM
	 */
	private void setCustomerInfo(NotifyCustomerDto notifyCustomerDto) {
		if (StringUtil.isEmpty(notifyCustomerDto.getReceiveCustomerCode())) {
			// 设置客户的银行列表属性为N
			notifyCustomerDto.setIsBackFlg(FossConstants.NO);
			return;
		}
		try {
			CustomerDto customerDto = customerService.queryCustInfoByCode(notifyCustomerDto.getReceiveCustomerCode());
			if (customerDto != null) {
				// 客户资质（结算方式）
				notifyCustomerDto.setCustomerQulification(customerDto.getChargeMode());
				// 信用额度
				notifyCustomerDto.setCreditAmount(customerDto.getCreditAmount());
				if (CollectionUtils.isEmpty(customerDto.getBankAccountList())) {
					// 设置客户的银行列表属性为N
					notifyCustomerDto.setIsBackFlg(FossConstants.NO);
				} else {
					// 设置客户的银行列表属性为Y
					notifyCustomerDto.setIsBackFlg(FossConstants.YES);
				}
			} else {
				// 设置客户的银行列表属性为N
				notifyCustomerDto.setIsBackFlg(FossConstants.NO);
			}
		} catch (CustomerException e) {
			LOGGER.error(NotifyCustomerException.ERROR, e);
			// 设置客户的银行列表属性为N
			notifyCustomerDto.setIsBackFlg(FossConstants.NO);
		}
	}

	/**
	 * 对传入的通知信息实体进行实例化处理.
	 * 
	 * @param inputEntity the input entity
	 * @author ibm-wangfei
	 * @date Oct 22, 2012 11:25:02 AM
	 */
	private void initNotificationEntity(NotificationEntity inputEntity) {
		// 是否征收保管费
		if (StringUtil.isEmpty(inputEntity.getIsStorageCharge())) {
			inputEntity.setIsStorageCharge(FossConstants.NO);
		} else {
			inputEntity.setIsStorageCharge(FossConstants.YES);
		}
		//是否异常
		if (StringUtil.isEmpty(inputEntity.getIsException())) {
			inputEntity.setIsException(FossConstants.NO);
		} else {
			inputEntity.setIsException(FossConstants.YES);
		}
		//是否会展货
		if (StringUtil.isEmpty(inputEntity.getIsExhibition())) {
			inputEntity.setIsExhibition(FossConstants.NO);
		} else {
			inputEntity.setIsExhibition(FossConstants.YES);
		}
		// 付款方式
		if (inputEntity.getPaymentType() == null) {
			inputEntity.setPaymentType("");
		}

		// 收货人
		inputEntity.setConsignee(inputEntity.getReceiveCustomerContact());
		// 联系方式
		inputEntity.setMobile(inputEntity.getReceiveCustomerMobilephone());
		if (NotifyCustomerConstants.FAILURE.equals(inputEntity.getNoticeResult())) {
			// 错误信息
			inputEntity.setExceptionNotes(inputEntity.getNoticeContent());
			inputEntity.setNoticeContent(inputEntity.getNoticeContent());
			// 通知失败时，付款方式设置为空，即数据库不更新
			inputEntity.setPaymentType("");
		} else if (NotifyCustomerConstants.SUCCESS.equals(inputEntity.getNoticeResult()) && FossConstants.YES.equals(inputEntity.getIsException())) {
			// 错误信息
			inputEntity.setExceptionNotes(NotifyCustomerConstants.NOTICE_CONTENT_SUCCESS);
			inputEntity.setNoticeContent(inputEntity.getNoticeContent());
		} else {
			inputEntity.setExceptionNotes("");
		}

		// 提货方式
		inputEntity.setDeliverType(inputEntity.getReceiveMethod());

		if (NotifyCustomerConstants.NOTIFY_TYPE_VOICE_NOTICE.equals(inputEntity.getNoticeType()) || NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE.equals(inputEntity.getNoticeType())) {
			// 语音、短信通知时，设置页面的隐藏区域值为空
			inputEntity.setDeliverDate("");
			inputEntity.setDeliverRequire("");
			inputEntity.setSmallTicketFee("");
			inputEntity.setPaymentType("");
			inputEntity.setIsNeedInvoice("");
			inputEntity.setIsSentRequired("");
			// 语音、短信通知后默认的状态为“语音通知中”、“短信通知中”
			//			if (NotifyCustomerConstants.NOTIFY_TYPE_VOICE_NOTICE.equals(inputEntity.getNoticeType())) {
			//				inputEntity.setNoticeResult(NotifyCustomerConstants.VOICE_NOTICING);
			//			} else if (NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE.equals(inputEntity.getNoticeType())) {
			//				inputEntity.setNoticeResult(NotifyCustomerConstants.SMS_NOTICING);
			//			}
			// 默认通知成功 -- 2013-06-12
			inputEntity.setNoticeResult(NotifyCustomerConstants.SUCCESS);
		}

		if (StringUtil.isEmpty(inputEntity.getIsNeedInvoice())) {
			// 默认为不需要发票
			inputEntity.setIsNeedInvoice(FossConstants.NO);
		}

		if (StringUtil.isEmpty(inputEntity.getIsSentRequired())) {
			// 默认为不需要送货
			inputEntity.setIsSentRequired(FossConstants.NO);
		}

		// 操作时间
		inputEntity.setOperateTime(new Date());
		// 操作人
		inputEntity.setOperator(FossUserContextHelper.getUserName());
		// 操作人编码
		inputEntity.setOperatorNo(FossUserContextHelper.getUserCode());
		// 操作部门
		inputEntity.setOperateOrgName(FossUserContextHelper.getOrgName());
		// 操作部门编码
		inputEntity.setOperateOrgCode(FossUserContextHelper.getOrgCode());
		// 币种
		inputEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		// 短信业务编码
		inputEntity.setModuleName(NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY);
		// 是否提前通知
		if (StringUtil.equals(inputEntity.getTaskStatus(), TaskTruckConstant.TASK_TRUCK_STATE_UNDEPART) || 
				StringUtil.equals(inputEntity.getTaskStatus(), TaskTruckConstant.TASK_TRUCK_STATE_ONTHEWAY)) {
			inputEntity.setIsPreNotify(FossConstants.YES);
		}
	}

	/**
	 * 对传入的通知客户发票信息进行实例化处理.
	 * 
	 * @param invoiceInfomationEntity the invoice infomation entity
	 * @author ibm-wangfei
	 * @date Oct 22, 2012 11:28:23 AM
	 */
	private void initInvoiceInfomationEntity(InvoiceInfomationEntity invoiceInfomationEntity) {
		// 操作时间
		invoiceInfomationEntity.setOperateTime(new Date());
		// 操作人
		invoiceInfomationEntity.setOperator(FossUserContextHelper.getUserName());
		// 操作人编码
		invoiceInfomationEntity.setOperatorCode(FossUserContextHelper.getUserCode());
		// 操作部门
		invoiceInfomationEntity.setOperateOrgName(FossUserContextHelper.getOrgName());
		// 操作部门编码
		invoiceInfomationEntity.setOperateOrgCode(FossUserContextHelper.getOrgCode());
	}

	/**
	 * 初始化异常信息.
	 * 
	 * @param waybillNo the waybill no
	 * @param exceptionType the exception type
	 * @param exceptionLink the exception link
	 * @param lastLoadOrgCode the last load org code
	 * @param lastLoadOrgName the last load org name
	 * @param createUserCode the create user code
	 * @param createUserName the create user name
	 * @return the exception entity
	 * @author ibm-wangfei
	 * @date Nov 5, 2012 6:49:14 PM
	 */
	private ExceptionEntity initExceptionEntity(String waybillNo, String exceptionType, String exceptionLink, String lastLoadOrgCode, String lastLoadOrgName, String createUserCode, String createUserName) {
		ExceptionEntity exceptionEntity = new ExceptionEntity();
		// 运单号
		exceptionEntity.setWaybillNo(waybillNo);
		// 异常类型
		exceptionEntity.setExceptionType(exceptionType);
		// 异常环节
		exceptionEntity.setExceptionLink(exceptionLink);
		// 异常状态
		exceptionEntity.setStatus(ExceptionProcessConstants.HANDLING);
		// 异常时间
		exceptionEntity.setExceptionTime(new Date());
		if (ExceptionProcessConstants.CUSTOMER_NOTICE.equals(exceptionLink)) {
			// 创建人
			exceptionEntity.setCreateUserName(FossUserContextHelper.getUserName());
			// 创建人code
			exceptionEntity.setCreateUserCode(FossUserContextHelper.getUserCode());
			// 操作部门
			exceptionEntity.setCreateOrgName(FossUserContextHelper.getOrgName());
			// 操作部门code
			exceptionEntity.setCreateOrgCode(FossUserContextHelper.getOrgCode());
		} else {
			// 设置定时任务操作人，操作部门
			// 创建人
			exceptionEntity.setCreateUserName(createUserName);
			// 创建人code
			exceptionEntity.setCreateUserCode(createUserCode);
			// 操作部门
			exceptionEntity.setCreateOrgName(lastLoadOrgName);
			// 操作部门code
			exceptionEntity.setCreateOrgCode(lastLoadOrgCode);
		}
		return exceptionEntity;
	}

	/**
	 * 到达联信息实例化.
	 * 
	 * @param notificationEntity the notification entity
	 * @return the arrive sheet entity
	 * @author ibm-wangfei
	 * @date Nov 13, 2012 7:38:08 PM
	 */
	private ArriveSheetEntity initArriveSheetEntity(NotificationEntity notificationEntity) {
		ArriveSheetEntity entity = new ArriveSheetEntity();
		// 运单号
		entity.setWaybillNo(notificationEntity.getWaybillNo());
		// 是否打印
		entity.setIsPrinted(FossConstants.NO);
		// 打印次数
		entity.setPrinttimes(0);
		// 是否有效
		entity.setActive(FossConstants.YES);
		// 创建时间
		entity.setCreateDate(new Date());
		// 创建人
		entity.setCreateUserName(FossUserContextHelper.getUserName());
		//创建人code 
		entity.setCreateUserCode(FossUserContextHelper.getUserCode());
		// 创建部门
		entity.setCreateOrgName(FossUserContextHelper.getOrgName());
		// 创建部门code
		entity.setCreateOrgCode(FossUserContextHelper.getOrgCode());
		// 是否必送货
		entity.setIsSentRequired(notificationEntity.getIsSentRequired());
		// 是否需要发票
		entity.setIsNeedInvoice(notificationEntity.getIsNeedInvoice());
		// 提前通知内容
		entity.setPreNoticeContent(notificationEntity.getNoticeContent());
		// 送货要求
		entity.setDeliverRequire(notificationEntity.getDeliverRequire());
		// 要求送货日期
		entity.setDeliverDate(DateUtils.convert(notificationEntity.getDeliverDate()));
		return entity;
	}

	/**
	 * 初始化异常分录表.
	 * 
	 * @param exceptionEntity the exception entity
	 * @param notes the notes
	 * @param lastLoadOrgCode the last load org code
	 * @param lastLoadOrgName the last load org name
	 * @param createUserCode the create user code
	 * @param createUserName the create user name
	 * @return the exception process detail entity
	 * @author ibm-wangfei
	 * @date Nov 5, 2012 6:55:52 PM
	 */
	private ExceptionProcessDetailEntity initExceptionProcessDetailEntity(ExceptionEntity exceptionEntity, String notes, String lastLoadOrgCode, String lastLoadOrgName, String createUserCode, String createUserName) {
		ExceptionProcessDetailEntity exceptionProcessDetailEntity = new ExceptionProcessDetailEntity();
		// 异常主信息ID
		exceptionProcessDetailEntity.settSrvExceptionId(exceptionEntity.getId());
		// 异常信息
		exceptionProcessDetailEntity.setNotes(notes);
		// 操作时间
		exceptionProcessDetailEntity.setOperateTime(new Date());

		if (ExceptionProcessConstants.CUSTOMER_NOTICE.equals(exceptionEntity.getExceptionLink())) {
			// 页面操作调用
			// 创建人
			exceptionProcessDetailEntity.setOperator(FossUserContextHelper.getUserName());
			// 创建人code
			exceptionProcessDetailEntity.setOperatorCode(FossUserContextHelper.getUserCode());
			// 创建部门
			exceptionProcessDetailEntity.setOperateOrgName(FossUserContextHelper.getOrgName());
			// 创建部门code
			exceptionProcessDetailEntity.setOperateOrgCode(FossUserContextHelper.getOrgCode());
		} else {
			// 设置定时任务操作人，操作部门
			// 创建人
			exceptionEntity.setCreateUserName(createUserName);
			// 创建人code
			exceptionEntity.setCreateUserCode(createUserCode);
			// 创建部门
			exceptionEntity.setCreateOrgName(lastLoadOrgName);
			// 创建部门code
			exceptionEntity.setCreateOrgCode(lastLoadOrgCode);
		}
		return exceptionProcessDetailEntity;
	}

	/**
	 * 查询列表.
	 * 
	 * @param conditionDto the condition dto
	 * @param start the start
	 * @param limit the limit
	 * @return the notifyCustomerDto waybillNo 运单号 orderNo 订单号 orderChannel 订单来源
	 *         orderPaidMethod 订单付款方式 deliveryCustomerId 发货客户ID
	 *         deliveryCustomerCode 发货客户编码 deliveryCustomerName 发货客户名称
	 *         deliveryCustomerMobilephone 发货客户手机 deliveryCustomerPhone 发货客户电话
	 *         deliveryCustomerContact 发货客户联系人 deliveryCustomerNationCode 发货国家
	 *         deliveryCustomerProvCode 发货省份 deliveryCustomerCityCode 发货市
	 *         deliveryCustomerDistCode 发货区 deliveryCustomerAddress 发货具体地址
	 *         receiveCustomerId 收货客户ID receiveCustomerCode 收货客户编码
	 *         receiveCustomerName 收货客户名称 receiveCustomerMobilephone 收货客户手机
	 *         receiveCustomerPhone 收货客户电话 receiveCustomerContact 收货客户联系人
	 *         receiveCustomerNationCode 收货国家 receiveCustomerProvCode 收货省份
	 *         receiveCustomerCityCode 收货市 receiveCustomerDistCode 收货区
	 *         receiveCustomerAddress 收货具体地址 receiveOrgCode 收货部门(出发部门) productId
	 *         产品ID productCode 运输性质 receiveMethod 提货方式 customerPickupOrgCode
	 *         提货网点 loadMethod 配载类型 targetOrgCode 目的站 pickupToDoor 是否上门接货
	 *         driverCode 司机 pickupCentralized 是否集中接货 loadLineCode 配载线路
	 *         loadOrgCode 配载部门 lastLoadOrgCode 最终配载部门 preDepartureTime 预计出发时间
	 *         preCustomerPickupTime 预计派送/提货时间 carDirectDelivery 是否大车直送
	 *         goodsName 货物名称 goodsQtyTotal 货物总件数 goodsWeightTotal 货物总重量
	 *         goodsVolumeTotal 货物总体积 goodsSize 货物尺寸 goodsTypeCode 货物类型
	 *         preciousGoods 是否贵重物品 specialShapedGoods 是否异形物品 outerNotes 对外备注
	 *         innerNotes 对内备注 goodsPackage 货物包装 insuranceAmount 保价声明价值
	 *         insuranceRate 保价费率 insuranceFee 保价费 codAmount 代收货款 codRate 代收费率
	 *         codFee 代收货款手续费 refundType 退款类型 returnBillType 返单类别 secretPrepaid
	 *         预付费保密 toPayAmount 到付金额 prePayAmount 预付金额 deliveryGoodsFee 送货费
	 *         otherFee 其他费用 packageFee 包装手续费 promotionsFee 优惠费用 billingType
	 *         运费计费类型 unitPrice 运费计费费率 transportFee 公布价运费 valueAddFee 增值费用
	 *         paidMethod 开单付款方式 arriveType 到达类型 forbiddenLine 禁行 freightMethod
	 *         合票方式 flightShift 航班时间 totalFee 总费用 promotionsCode 优惠编码 createTime
	 *         创建时间 modifyTime 更新时间（业务时间） billTime 开单时间 createUserCode 开单人
	 *         modifyUserCode 更新人 createOrgCode 开单组织 modifyOrgCode 更新组织 refId
	 *         原运单ID refCode 原运单号 currencyCode 币种 isWholeVehicle 是否整车运单
	 *         wholeVehicleAppfee 整车约车报价 wholeVehicleActualfee 整车开单报价
	 *         accountName 返款帐户开户名称 accountCode 返款帐户开户账户 accountBank 返款帐户开户银行
	 *         billWeight 计费重量 pickupFee 接货费 serviceFee 装卸费 preArriveTime 预计到达时间
	 *         transportType 运输类型 printTimes 打印次数 addTime 新增时间 contactAddressId
	 *         联系人地址ID flightNumberType 航班类型 collectionDepartment 收款部门
	 *         transportationRemark 储运事项 active 运单状态 isPassOwnDepartment 是否经过营业部
	 *         otherPackage 其他包装 paperNum 纸包装 woodNum 木包装 fibreNum 纤包装 salverNum
	 *         托包装 membraneNum 膜包装 deliverCustContactId 发货联系人Id
	 *         receiverCustContactId 收获联系人ID pendingType
	 *         处理类型:"PDA_ACTIVE"--PDA已补录，"PC_ACTIVE"--暂存已开单 licensePlateNum 车牌号
	 *         orderVehicleNum 约车编号 stockStatus 库存状态 arriveGoodsQty 到达件数
	 *         stockQty 库存件数 storageDay 在库天数 wayFee 运费 storageCharge 仓储费
	 *         departureTime 出发日期 arriveTime 到达日期 lastNotifiTime 上次通知日期
	 *         handoverNo 交接单号 noticeResult 通知情况 inStockTime 入库时间 planArriveTime
	 *         预计到达时间 handoverGoodsQty 交接件数 receivingHabits 送货习惯 selectType 查询方式
	 *         notificationTimeSpace 最后通知日期与当前日期的间隔天数 notificationTime 最后通知日期
	 *         overdueDay 逾期天数 exceptionType 异常类型 exceptionNotes 异常信息
	 *         noticeContent 通知信息 taskStatus 车辆到达状态 customerQulification
	 *         客户资质（结算方式） creditAmount 信用额度 lastInStockTime 最后入库时间
	 *         receiveOrgName 始发部门名称 isBackFlg 客户是否有银行账户列表 isPay 是否为网上支付
	 *         totalToPayAmount 到付费用合计（到付金额+保管费金额）
	 * @author ibm-wangfei
	 * @date Nov 7, 2012 8:52:32 PM
	 */
	private List<NotifyCustomerDto> getNotifyCustomerDto(NotifyCustomerConditionDto conditionDto, int start, int limit) {
		// 判断页面查询类型.
		int queryType = getQueryType(conditionDto);
		//update 
		conditionDto=getNotifyCustomerConditionDto(conditionDto);
		switch (queryType) {
		case NotifyCustomerConstants.SELECT_TYPE_STOCK:
			// 0. 默认查询到达本地以及本地库存的运单
			return (List<NotifyCustomerDto>) this.notifyCustomerDao.queryNotifyCustomerListForStock(conditionDto, start, limit);
		case NotifyCustomerConstants.SELECT_TYPE_HANDOVER:
			// 1.按交接单号查询
			return (List<NotifyCustomerDto>) this.notifyCustomerDao.queryNotifyCustomerListForHandoverNo(conditionDto, start, limit);
		case NotifyCustomerConstants.SELECT_TYPE_VEHICLEASSEMBLENO:
			// 2.按车次、预计到达时间查询
			return (List<NotifyCustomerDto>) this.notifyCustomerDao.queryNotifyCustomerListForVehicleAssembleNo(conditionDto, start, limit);
		default:
			return (List<NotifyCustomerDto>) this.notifyCustomerDao.queryNotifyCustomerListForStock(conditionDto, start, limit);
		}
	}

	/**
	 * 将列表中的值直接设置到查询出来出来的dto上，已减少数据库多重关联查询.
	 * 
	 * @param pageDto the page dto waybillNo 运单号 orderNo 订单号 orderChannel 订单来源
	 *            orderPaidMethod 订单付款方式 deliveryCustomerId 发货客户ID
	 *            deliveryCustomerCode 发货客户编码 deliveryCustomerName 发货客户名称
	 *            deliveryCustomerMobilephone 发货客户手机 deliveryCustomerPhone
	 *            发货客户电话 deliveryCustomerContact 发货客户联系人
	 *            deliveryCustomerNationCode 发货国家 deliveryCustomerProvCode 发货省份
	 *            deliveryCustomerCityCode 发货市 deliveryCustomerDistCode 发货区
	 *            deliveryCustomerAddress 发货具体地址 receiveCustomerId 收货客户ID
	 *            receiveCustomerCode 收货客户编码 receiveCustomerName 收货客户名称
	 *            receiveCustomerMobilephone 收货客户手机 receiveCustomerPhone 收货客户电话
	 *            receiveCustomerContact 收货客户联系人 receiveCustomerNationCode 收货国家
	 *            receiveCustomerProvCode 收货省份 receiveCustomerCityCode 收货市
	 *            receiveCustomerDistCode 收货区 receiveCustomerAddress 收货具体地址
	 *            receiveOrgCode 收货部门(出发部门) productId 产品ID productCode 运输性质
	 *            receiveMethod 提货方式 customerPickupOrgCode 提货网点 loadMethod 配载类型
	 *            targetOrgCode 目的站 pickupToDoor 是否上门接货 driverCode 司机
	 *            pickupCentralized 是否集中接货 loadLineCode 配载线路 loadOrgCode 配载部门
	 *            lastLoadOrgCode 最终配载部门 preDepartureTime 预计出发时间
	 *            preCustomerPickupTime 预计派送/提货时间 carDirectDelivery 是否大车直送
	 *            goodsName 货物名称 goodsQtyTotal 货物总件数 goodsWeightTotal 货物总重量
	 *            goodsVolumeTotal 货物总体积 goodsSize 货物尺寸 goodsTypeCode 货物类型
	 *            preciousGoods 是否贵重物品 specialShapedGoods 是否异形物品 outerNotes 对外备注
	 *            innerNotes 对内备注 goodsPackage 货物包装 insuranceAmount 保价声明价值
	 *            insuranceRate 保价费率 insuranceFee 保价费 codAmount 代收货款 codRate
	 *            代收费率 codFee 代收货款手续费 refundType 退款类型 returnBillType 返单类别
	 *            secretPrepaid 预付费保密 toPayAmount 到付金额 prePayAmount 预付金额
	 *            deliveryGoodsFee 送货费 otherFee 其他费用 packageFee 包装手续费
	 *            promotionsFee 优惠费用 billingType 运费计费类型 unitPrice 运费计费费率
	 *            transportFee 公布价运费 valueAddFee 增值费用 paidMethod 开单付款方式
	 *            arriveType 到达类型 forbiddenLine 禁行 freightMethod 合票方式
	 *            flightShift 航班时间 totalFee 总费用 promotionsCode 优惠编码 createTime
	 *            创建时间 modifyTime 更新时间（业务时间） billTime 开单时间 createUserCode 开单人
	 *            modifyUserCode 更新人 createOrgCode 开单组织 modifyOrgCode 更新组织 refId
	 *            原运单ID refCode 原运单号 currencyCode 币种 isWholeVehicle 是否整车运单
	 *            wholeVehicleAppfee 整车约车报价 wholeVehicleActualfee 整车开单报价
	 *            accountName 返款帐户开户名称 accountCode 返款帐户开户账户 accountBank 返款帐户开户银行
	 *            billWeight 计费重量 pickupFee 接货费 serviceFee 装卸费 preArriveTime
	 *            预计到达时间 transportType 运输类型 printTimes 打印次数 addTime 新增时间
	 *            contactAddressId 联系人地址ID flightNumberType 航班类型
	 *            collectionDepartment 收款部门 transportationRemark 储运事项 active
	 *            运单状态 isPassOwnDepartment 是否经过营业部 otherPackage 其他包装 paperNum
	 *            纸包装 woodNum 木包装 fibreNum 纤包装 salverNum 托包装 membraneNum 膜包装
	 *            deliverCustContactId 发货联系人Id receiverCustContactId 收获联系人ID
	 *            pendingType 处理类型:"PDA_ACTIVE"--PDA已补录，"PC_ACTIVE"--暂存已开单
	 *            licensePlateNum 车牌号 orderVehicleNum 约车编号 stockStatus 库存状态
	 *            arriveGoodsQty 到达件数 stockQty 库存件数 storageDay 在库天数 wayFee 运费
	 *            storageCharge 仓储费 departureTime 出发日期 arriveTime 到达日期
	 *            lastNotifiTime 上次通知日期 handoverNo 交接单号 noticeResult 通知情况
	 *            inStockTime 入库时间 planArriveTime 预计到达时间 handoverGoodsQty 交接件数
	 *            receivingHabits 送货习惯 selectType 查询方式 notificationTimeSpace
	 *            最后通知日期与当前日期的间隔天数 notificationTime 最后通知日期 overdueDay 逾期天数
	 *            exceptionType 异常类型 exceptionNotes 异常信息 noticeContent 通知信息
	 *            taskStatus 车辆到达状态 customerQulification 客户资质（结算方式） creditAmount
	 *            信用额度 lastInStockTime 最后入库时间 receiveOrgName 始发部门名称 isBackFlg
	 *            客户是否有银行账户列表 isPay 是否为网上支付 totalToPayAmount 到付费用合计（到付金额+保管费金额）
	 * @param dbDto the db dto waybillNo 运单号 orderNo 订单号 orderChannel 订单来源
	 *            orderPaidMethod 订单付款方式 deliveryCustomerId 发货客户ID
	 *            deliveryCustomerCode 发货客户编码 deliveryCustomerName 发货客户名称
	 *            deliveryCustomerMobilephone 发货客户手机 deliveryCustomerPhone
	 *            发货客户电话 deliveryCustomerContact 发货客户联系人
	 *            deliveryCustomerNationCode 发货国家 deliveryCustomerProvCode 发货省份
	 *            deliveryCustomerCityCode 发货市 deliveryCustomerDistCode 发货区
	 *            deliveryCustomerAddress 发货具体地址 receiveCustomerId 收货客户ID
	 *            receiveCustomerCode 收货客户编码 receiveCustomerName 收货客户名称
	 *            receiveCustomerMobilephone 收货客户手机 receiveCustomerPhone 收货客户电话
	 *            receiveCustomerContact 收货客户联系人 receiveCustomerNationCode 收货国家
	 *            receiveCustomerProvCode 收货省份 receiveCustomerCityCode 收货市
	 *            receiveCustomerDistCode 收货区 receiveCustomerAddress 收货具体地址
	 *            receiveOrgCode 收货部门(出发部门) productId 产品ID productCode 运输性质
	 *            receiveMethod 提货方式 customerPickupOrgCode 提货网点 loadMethod 配载类型
	 *            targetOrgCode 目的站 pickupToDoor 是否上门接货 driverCode 司机
	 *            pickupCentralized 是否集中接货 loadLineCode 配载线路 loadOrgCode 配载部门
	 *            lastLoadOrgCode 最终配载部门 preDepartureTime 预计出发时间
	 *            preCustomerPickupTime 预计派送/提货时间 carDirectDelivery 是否大车直送
	 *            goodsName 货物名称 goodsQtyTotal 货物总件数 goodsWeightTotal 货物总重量
	 *            goodsVolumeTotal 货物总体积 goodsSize 货物尺寸 goodsTypeCode 货物类型
	 *            preciousGoods 是否贵重物品 specialShapedGoods 是否异形物品 outerNotes 对外备注
	 *            innerNotes 对内备注 goodsPackage 货物包装 insuranceAmount 保价声明价值
	 *            insuranceRate 保价费率 insuranceFee 保价费 codAmount 代收货款 codRate
	 *            代收费率 codFee 代收货款手续费 refundType 退款类型 returnBillType 返单类别
	 *            secretPrepaid 预付费保密 toPayAmount 到付金额 prePayAmount 预付金额
	 *            deliveryGoodsFee 送货费 otherFee 其他费用 packageFee 包装手续费
	 *            promotionsFee 优惠费用 billingType 运费计费类型 unitPrice 运费计费费率
	 *            transportFee 公布价运费 valueAddFee 增值费用 paidMethod 开单付款方式
	 *            arriveType 到达类型 forbiddenLine 禁行 freightMethod 合票方式
	 *            flightShift 航班时间 totalFee 总费用 promotionsCode 优惠编码 createTime
	 *            创建时间 modifyTime 更新时间（业务时间） billTime 开单时间 createUserCode 开单人
	 *            modifyUserCode 更新人 createOrgCode 开单组织 modifyOrgCode 更新组织 refId
	 *            原运单ID refCode 原运单号 currencyCode 币种 isWholeVehicle 是否整车运单
	 *            wholeVehicleAppfee 整车约车报价 wholeVehicleActualfee 整车开单报价
	 *            accountName 返款帐户开户名称 accountCode 返款帐户开户账户 accountBank 返款帐户开户银行
	 *            billWeight 计费重量 pickupFee 接货费 serviceFee 装卸费 preArriveTime
	 *            预计到达时间 transportType 运输类型 printTimes 打印次数 addTime 新增时间
	 *            contactAddressId 联系人地址ID flightNumberType 航班类型
	 *            collectionDepartment 收款部门 transportationRemark 储运事项 active
	 *            运单状态 isPassOwnDepartment 是否经过营业部 otherPackage 其他包装 paperNum
	 *            纸包装 woodNum 木包装 fibreNum 纤包装 salverNum 托包装 membraneNum 膜包装
	 *            deliverCustContactId 发货联系人Id receiverCustContactId 收获联系人ID
	 *            pendingType 处理类型:"PDA_ACTIVE"--PDA已补录，"PC_ACTIVE"--暂存已开单
	 *            licensePlateNum 车牌号 orderVehicleNum 约车编号 stockStatus 库存状态
	 *            arriveGoodsQty 到达件数 stockQty 库存件数 storageDay 在库天数 wayFee 运费
	 *            storageCharge 仓储费 departureTime 出发日期 arriveTime 到达日期
	 *            lastNotifiTime 上次通知日期 handoverNo 交接单号 noticeResult 通知情况
	 *            inStockTime 入库时间 planArriveTime 预计到达时间 handoverGoodsQty 交接件数
	 *            receivingHabits 送货习惯 selectType 查询方式 notificationTimeSpace
	 *            最后通知日期与当前日期的间隔天数 notificationTime 最后通知日期 overdueDay 逾期天数
	 *            exceptionType 异常类型 exceptionNotes 异常信息 noticeContent 通知信息
	 *            taskStatus 车辆到达状态 customerQulification 客户资质（结算方式） creditAmount
	 *            信用额度 lastInStockTime 最后入库时间 receiveOrgName 始发部门名称 isBackFlg
	 *            客户是否有银行账户列表 isPay 是否为网上支付 totalToPayAmount 到付费用合计（到付金额+保管费金额）
	 * @author ibm-wangfei
	 * @date Oct 26, 2012 5:18:38 PM
	 */
	private void notifyCustomerDtoRelate(NotifyCustomerDto pageDto, NotifyCustomerDto dbDto) {
		if (pageDto != null && dbDto != null) {
			// 到达件数
			dbDto.setArriveGoodsQty(pageDto.getArriveGoodsQty());
			// 库存件数
			dbDto.setStockQty(pageDto.getStockQty());
			// 交接单件数
			dbDto.setHandoverGoodsQty(pageDto.getHandoverGoodsQty());
			// 页面传入的查询方式
			dbDto.setSelectType(pageDto.getSelectType());
			// 车辆状态
			dbDto.setTaskStatus(pageDto.getTaskStatus());
		}
	}

	/**
	 * 设置默认查询条件.
	 * 
	 * @param notifyCustomerConditionDto the notify customer condition dto
	 * @author ibm-wangfei
	 * @date Oct 25, 2012 10:53:18 AM
	 */
	private void initNotifyCustomerQuery(NotifyCustomerConditionDto notifyCustomerConditionDto) {
		String currOrgCode = FossUserContextHelper.getOrgCode();
		// 登录人员所在部门
		notifyCustomerConditionDto.setLastLoadOrgCode(currOrgCode);
		// 默认查询中派送方式--派送
		//notifyCustomerConditionDto.setReceiveMethodTmp(WaybillConstants.DELIVER_FREE);
		// 默认查询中通知状态--成功
		notifyCustomerConditionDto.setNotificationTypeTmp(NotifyCustomerConstants.SUCCESS);
		// 当前日期
		notifyCustomerConditionDto.setDeliverDateTmp(new Date());
		// 默认日期
		notifyCustomerConditionDto.setDeliverDateDef(DateUtils.getEndDatetime(new Date()));
		// 默认查询中付款方式--非临时欠款等收款放货--现没有此付款方式，取消设置SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT
		// 默认查询中变更状态 -- 已受理
		List<String> wbrStatus = new ArrayList<String>();
		// 已同意
		wbrStatus.add(WaybillRfcConstants.ACCECPT);
		// 审核拒绝
		wbrStatus.add(WaybillRfcConstants.AUDIT_DENY);
		// 受理拒绝
		wbrStatus.add(WaybillRfcConstants.ACCECPT_DENY);
		notifyCustomerConditionDto.setWbrStatus(wbrStatus);
		// 异常状态-运单异常
		notifyCustomerConditionDto.setExceptionType(ExceptionProcessConstants.WAYBILL_EXCEPTION);
		// 异常类型-客户通知、客户自提
		notifyCustomerConditionDto.setExceptionLink1(ExceptionProcessConstants.CUSTOMER_NOTICE);
		notifyCustomerConditionDto.setExceptionLink2(ExceptionProcessConstants.CUSTOMER_PICKUP);
		// 异常状态-处理中、已转弃货
		String[] exceptionStatus = { ExceptionProcessConstants.HANDLING, ExceptionProcessConstants.ALREADY_ABANDON_GOODS };
		notifyCustomerConditionDto.setExceptionStatus(exceptionStatus);
		// 车辆状态-已废弃
		notifyCustomerConditionDto.setTaskStatus(TaskTruckConstant.TASK_TRUCK_STATE_CANCLED);
		// 默认运单不等于空运、偏线
		String productCodes[] = { ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT, ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE };
		notifyCustomerConditionDto.setProductCodes(productCodes);
		// 默认运单版本
		notifyCustomerConditionDto.setActive(FossConstants.ACTIVE);

		// ibm-wangfei 2013-2-22 15:48:48 添加库存外场、库区默认查询条件/DMANA-9499update in 2015-03-07
		List<String> list = handleQueryOutfieldService.getEndStockCodeAndExpressAreaCode(currOrgCode);
		if (CollectionUtils.isNotEmpty(list)) {
			List<String> ld=new ArrayList<String>();
			ld.add(list.get(1));
			ld.add(list.get(2));
			notifyCustomerConditionDto.setEndStockOrgCode(list.get(0));
			notifyCustomerConditionDto.setGoodsAreaCodes(ld);
		}
		
		//new add 自动派单优化-行政区域多选
		if (StringUtil.isNotBlank(notifyCustomerConditionDto.getDeliverDistCode())) {
			notifyCustomerConditionDto.setDeliverDistCodes(notifyCustomerConditionDto.getDeliverDistCode().replace(" ", "").split(","));
		}
		//new add 自动派单优化-提货方式多选
		if (StringUtil.isNotBlank(notifyCustomerConditionDto.getReceiveMethodCon())) {
			notifyCustomerConditionDto.setReceiveMethodCons(notifyCustomerConditionDto.getReceiveMethodCon().replace(" ", "").split(","));
		}
		//new add 自动派单优化-运输性质多选
		if (StringUtil.isNotBlank(notifyCustomerConditionDto.getProductCode())) {
			notifyCustomerConditionDto.setProductCodeCons(notifyCustomerConditionDto.getProductCode().replace(" ", "").split(","));
		}
		//new add 自动派单优化-交接单号多个
		if (StringUtil.isNotBlank(notifyCustomerConditionDto.getVehicleAssembleNo())) {
			notifyCustomerConditionDto.setVehicleAssembleNos(notifyCustomerConditionDto.getVehicleAssembleNo().replace(" ", "").split("\\n"));
		}
		
		LOGGER.info("默认查询条件为：" + ReflectionToStringBuilder.toString(notifyCustomerConditionDto));
	}

	/**
	 * 判断页面查询类型.
	 * 
	 * @param conditionDto the condition dto
	 * @return the queryType
	 * @author ibm-wangfei
	 * @date Nov 9, 2012 10:09:30 AM
	 */
	private int getQueryType(NotifyCustomerConditionDto conditionDto) {
		// 查询方式 1. 默认查询到达本地以及本地库存的运单 - 2. 安装车次号、交接单、预计到达时间查询
		if (StringUtil.isNotBlank(conditionDto.getWaybillNo())) {
			// 根据运单编号查询 忽略其他全部
			conditionDto.setReceiveMethod(null);
			conditionDto.setProductCode(null);
			conditionDto.setNoticeResult(null);
			conditionDto.setStorageDay(null);
			conditionDto.setStorageDayStatus(NumberConstants.NUMERAL_ZORE);
			conditionDto.setInStockTimeFrom(null);
			conditionDto.setInStockTimeTo(null);
			conditionDto.setSelectType(String.valueOf(NotifyCustomerConstants.SELECT_TYPE_STOCK));
			//new add
			conditionDto.setDeliverProv(null);  //省
			conditionDto.setDeliverCity(null);  //市
			conditionDto.setDeliverDistCodes(null);  //性质区域列表
			conditionDto.setReceiveMethodCons(null); //提货方式
			conditionDto.setProductCodeCons(null); //运输性质
			
			// 解析运单号为列表
			conditionDto.setArrayWaybillNos(conditionDto.getWaybillNo().split("\\n"));
			if (conditionDto.getArrayWaybillNos().length == 0) {
				return NotifyCustomerConstants.SELECT_TYPE_STOCK_ERROR;
			}
			conditionDto.setIsQueryDeliveyDate(FossConstants.YES);
			return NotifyCustomerConstants.SELECT_TYPE_STOCK;
		}
		if (StringUtil.isNotBlank(conditionDto.getHandoverNo())) {
			// 根据交接单编号查询 ，则忽略“车次”、“在库天数”和“入库时间”查询条件；
			conditionDto.setSelectType(String.valueOf(NotifyCustomerConstants.SELECT_TYPE_HANDOVER));
			conditionDto.setPlanArriveTimeFrom(null);
			conditionDto.setPlanArriveTimeTo(null);
			conditionDto.setIsQueryDeliveyDate(FossConstants.YES);
			return NotifyCustomerConstants.SELECT_TYPE_HANDOVER;
		}
		if (StringUtil.isNotBlank(conditionDto.getVehicleAssembleNo())) {
			// 根据车次号查询 则忽略“在库天数”和“入库时间”查询条件；
			conditionDto.setSelectType(String.valueOf(NotifyCustomerConstants.SELECT_TYPE_HANDOVER));
			conditionDto.setIsQueryDeliveyDate(FossConstants.YES);
			return NotifyCustomerConstants.SELECT_TYPE_VEHICLEASSEMBLENO;
		}
		if (StringUtil.isNotBlank(conditionDto.getStorageDay()) || StringUtil.isNotEmpty(conditionDto.getInStockTimeFrom()) || StringUtil.isNotEmpty(conditionDto.getInStockTimeTo())) {
			// 根据“入库时间”、“入库天数”查询条件，则忽略“预计到达时间”查询条件；
			conditionDto.setSelectType(String.valueOf(NotifyCustomerConstants.SELECT_TYPE_STOCK));
			// 入库天数的特殊情况（X天以上）
			if (StringUtil.isNotBlank(conditionDto.getStorageDay()) && IntegerUtils.toInteger(conditionDto.getStorageDay()) < 0) {
				conditionDto.setStorageDayStatus(NumberConstants.NUMERAL_ONE);
				conditionDto.setStorageDay(String.valueOf(IntegerUtils.toInteger(conditionDto.getStorageDay()) * -1));
			}
			return NotifyCustomerConstants.SELECT_TYPE_STOCK;
		}
		if (StringUtil.isNotBlank(conditionDto.getPlanArriveTimeFrom()) || StringUtil.isNotEmpty(conditionDto.getPlanArriveTimeTo()) ||
				StringUtil.isNotBlank(conditionDto.getArriveTimeFrom()) || StringUtil.isNotEmpty(conditionDto.getArriveTimeTo())) {
			// 预计到达时间查询，同交接单查询
			conditionDto.setSelectType(String.valueOf(NotifyCustomerConstants.SELECT_TYPE_HANDOVER));
			return NotifyCustomerConstants.SELECT_TYPE_HANDOVER;
		}
		// 录入派送方式、通知状态、运输性质 or 不录入任何值时
		conditionDto.setSelectType(String.valueOf(NotifyCustomerConstants.SELECT_TYPE_STOCK));
		return NotifyCustomerConstants.SELECT_TYPE_STOCK;
	}

	/**
	 * 获取送货习惯.
	 * 
	 * @param contactAddressId the contact address id
	 * @return the receivingHabits
	 * @author ibm-wangfei
	 * @date Nov 8, 2012 2:25:55 PM
	 */
	private String getReceivingHabits(String contactAddressId) {
		ContactAddressEntity contactAddressEntity = null;
		// 判断联系人地址ID
		if (StringUtil.isBlank(contactAddressId)) {
			LOGGER.info("联系人地址ID为空，退出查询客户的收货习惯。");
			return "";
		}
		try {
			// 根据客户联系人地址ID，查询出客户的收货习惯
			contactAddressEntity = customerService.queryContactAddressById(contactAddressId);
		} catch (CustomerException e) {
			LOGGER.error(e.getMessage(), e);
			return "";
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return "";
		}

		StringBuffer receivingHabits = new StringBuffer();
		if (contactAddressEntity == null) {
			return receivingHabits.toString();
		}
		// 偏好起始时间
		if (contactAddressEntity.getBeginTime() != null) {
			receivingHabits.append(DateUtils.convert(contactAddressEntity.getBeginTime(), NotifyCustomerConstants.DATE_TIME_FORMAT_YYYYMMDD_HHMM));
		}

		// 偏好结束时间
		if (contactAddressEntity.getEndTime() != null) {
			if (StringUtil.isNotEmpty(receivingHabits.toString())) {
				receivingHabits.append("至");
			}
			receivingHabits.append(DateUtils.convert(contactAddressEntity.getEndTime(), NotifyCustomerConstants.DATE_TIME_FORMAT_YYYYMMDD_HHMM));
		}

		// 是否送货上楼 : Y or N
		if (StringUtil.equals(FossConstants.YES, contactAddressEntity.getDeliveryUpstairs())) {
			if (StringUtil.isNotEmpty(receivingHabits.toString())) {
				receivingHabits.append(NotifyCustomerConstants.SPLIT_CHAR);
			}
			receivingHabits.append("需要送货上楼");
		}
		// 其他要求
		if (StringUtil.isNotEmpty(contactAddressEntity.getOtherDemand())) {
			if (StringUtil.isNotEmpty(receivingHabits.toString())) {
				receivingHabits.append(NotifyCustomerConstants.SPLIT_CHAR);
			}
			receivingHabits.append(contactAddressEntity.getOtherDemand());
		}
		return receivingHabits.toString();
	}

	/**
	 * 根据传入的异常类型，返回异常名称.
	 * 
	 * @param exceptionType the exception type
	 * @return the exceptionValue
	 * @author ibm-wangfei
	 * @date Nov 13, 2012 4:54:23 PM
	 */
	private String getExceptionValue(String exceptionType) {
		String exceptionValue = "";
		if (StringUtil.isEmpty(exceptionType)) {
			return exceptionValue;
		}
		if (ExceptionProcessConstants.CUSTOMER_NOTICE.equals(exceptionType)) {
			exceptionValue = "送货通知";
		} else if (ExceptionProcessConstants.CUSTOMER_PICKUP.equals(exceptionType)) {
			exceptionValue = "客户自提";
		} else if (ExceptionProcessConstants.DELIVER.equals(exceptionType)) {
			exceptionValue = "司机送货";
		} else {
			// 这里不做处理
			LOGGER.info("不做处理");
		}
		return exceptionValue;
	}

	/**
	 * update:
	 * @author yuting
	 * 获取短信信息.
	 * 获取短信信息.
	 * 
	 * @param entity the entity
	 * @param dto the dto waybillNo 运单号 orderNo 订单号 orderChannel 订单来源
	 *            orderPaidMethod 订单付款方式 deliveryCustomerId 发货客户ID
	 *            deliveryCustomerCode 发货客户编码 deliveryCustomerName 发货客户名称
	 *            deliveryCustomerMobilephone 发货客户手机 deliveryCustomerPhone
	 *            发货客户电话 deliveryCustomerContact 发货客户联系人
	 *            deliveryCustomerNationCode 发货国家 deliveryCustomerProvCode 发货省份
	 *            deliveryCustomerCityCode 发货市 deliveryCustomerDistCode 发货区
	 *            deliveryCustomerAddress 发货具体地址 receiveCustomerId 收货客户ID
	 *            receiveCustomerCode 收货客户编码 receiveCustomerName 收货客户名称
	 *            receiveCustomerMobilephone 收货客户手机 receiveCustomerPhone 收货客户电话
	 *            receiveCustomerContact 收货客户联系人 receiveCustomerNationCode 收货国家
	 *            receiveCustomerProvCode 收货省份 receiveCustomerCityCode 收货市
	 *            receiveCustomerDistCode 收货区 receiveCustomerAddress 收货具体地址
	 *            receiveOrgCode 收货部门(出发部门) productId 产品ID productCode 运输性质
	 *            receiveMethod 提货方式 customerPickupOrgCode 提货网点 loadMethod 配载类型
	 *            targetOrgCode 目的站 pickupToDoor 是否上门接货 driverCode 司机
	 *            pickupCentralized 是否集中接货 loadLineCode 配载线路 loadOrgCode 配载部门
	 *            lastLoadOrgCode 最终配载部门 preDepartureTime 预计出发时间
	 *            preCustomerPickupTime 预计派送/提货时间 carDirectDelivery 是否大车直送
	 *            goodsName 货物名称 goodsQtyTotal 货物总件数 goodsWeightTotal 货物总重量
	 *            goodsVolumeTotal 货物总体积 goodsSize 货物尺寸 goodsTypeCode 货物类型
	 *            preciousGoods 是否贵重物品 specialShapedGoods 是否异形物品 outerNotes 对外备注
	 *            innerNotes 对内备注 goodsPackage 货物包装 insuranceAmount 保价声明价值
	 *            insuranceRate 保价费率 insuranceFee 保价费 codAmount 代收货款 codRate
	 *            代收费率 codFee 代收货款手续费 refundType 退款类型 returnBillType 返单类别
	 *            secretPrepaid 预付费保密 toPayAmount 到付金额 prePayAmount 预付金额
	 *            deliveryGoodsFee 送货费 otherFee 其他费用 packageFee 包装手续费
	 *            promotionsFee 优惠费用 billingType 运费计费类型 unitPrice 运费计费费率
	 *            transportFee 公布价运费 valueAddFee 增值费用 paidMethod 开单付款方式
	 *            arriveType 到达类型 forbiddenLine 禁行 freightMethod 合票方式
	 *            flightShift 航班时间 totalFee 总费用 promotionsCode 优惠编码 createTime
	 *            创建时间 modifyTime 更新时间（业务时间） billTime 开单时间 createUserCode 开单人
	 *            modifyUserCode 更新人 createOrgCode 开单组织 modifyOrgCode 更新组织 refId
	 *            原运单ID refCode 原运单号 currencyCode 币种 isWholeVehicle 是否整车运单
	 *            wholeVehicleAppfee 整车约车报价 wholeVehicleActualfee 整车开单报价
	 *            accountName 返款帐户开户名称 accountCode 返款帐户开户账户 accountBank 返款帐户开户银行
	 *            billWeight 计费重量 pickupFee 接货费 serviceFee 装卸费 preArriveTime
	 *            预计到达时间 transportType 运输类型 printTimes 打印次数 addTime 新增时间
	 *            contactAddressId 联系人地址ID flightNumberType 航班类型
	 *            collectionDepartment 收款部门 transportationRemark 储运事项 active
	 *            运单状态 isPassOwnDepartment 是否经过营业部 otherPackage 其他包装 paperNum
	 *            纸包装 woodNum 木包装 fibreNum 纤包装 salverNum 托包装 membraneNum 膜包装
	 *            deliverCustContactId 发货联系人Id receiverCustContactId 收获联系人ID
	 *            pendingType 处理类型:"PDA_ACTIVE"--PDA已补录，"PC_ACTIVE"--暂存已开单
	 *            licensePlateNum 车牌号 orderVehicleNum 约车编号 stockStatus 库存状态
	 *            arriveGoodsQty 到达件数 stockQty 库存件数 storageDay 在库天数 wayFee 运费
	 *            storageCharge 仓储费 departureTime 出发日期 arriveTime 到达日期
	 *            lastNotifiTime 上次通知日期 handoverNo 交接单号 noticeResult 通知情况
	 *            inStockTime 入库时间 planArriveTime 预计到达时间 handoverGoodsQty 交接件数
	 *            receivingHabits 送货习惯 selectType 查询方式 notificationTimeSpace
	 *            最后通知日期与当前日期的间隔天数 notificationTime 最后通知日期 overdueDay 逾期天数
	 *            exceptionType 异常类型 exceptionNotes 异常信息 noticeContent 通知信息
	 *            taskStatus 车辆到达状态 customerQulification 客户资质（结算方式） creditAmount
	 *            信用额度 lastInStockTime 最后入库时间 receiveOrgName 始发部门名称 isBackFlg
	 *            客户是否有银行账户列表 isPay 是否为网上支付 totalToPayAmount 到付费用合计（到付金额+保管费金额）
	 * @return the string
	 * @author ibm-wangfei
	 * @date Nov 21, 2012 4:02:40 PM
	 */
	@Override
	public String[] queryNoticeContent(NotificationEntity entity, NotifyCustomerDto dto) {
		String[] noticeContents = { "", "" };
		if (entity == null || dto == null) {
			return noticeContents;
		}
		// 判断运单号是否为空
		if (StringUtil.isEmpty(dto.getWaybillNo())) {
			return noticeContents;
		}
		// 根据运单号取得运单信息
		WaybillEntity waybill = waybillManagerService
				.queryWaybillBasicByNo(dto.getWaybillNo());
		// 取得产品类型
		String productCode = waybill.getProductCode();
		if(waybill.getGoodsVolumeTotal()!=null){
			dto.setGoodsVolumeTotal(waybill.getGoodsVolumeTotal());
		}
		if(waybill.getGoodsWeightTotal()!=null){
			dto.setGoodsWeightTotal(waybill.getGoodsWeightTotal());
		}
		// 短信模版CODE
		String smsTemplateCode = "";
		// 判断是否是经济快递
		if(WaybillConstants.directDetermineIsExpressByProductCode(productCode)){
		//为快递，则保持原来不变
			smsTemplateCode = this.getSmsCode(entity, dto);
		}else {
		//为零担，则更改为最新模板MANA-1232
			smsTemplateCode = this.getSelfSmsCode(entity, dto);
	    }
		// 短信内容
		String smsConten = "";
		// 语音模版CODE
		String voiceTemplateCode = "";
		String voiceConten = "";
		if (StringUtil.isEmpty(smsTemplateCode)) {
			return noticeContents;
		}
		if (StringUtil.equals(entity.getNoticeType(), NotifyCustomerConstants.NOTIFY_TYPE_VOICE_NOTICE)) {
			if(NotifyCustomerConstants.PICKUP_NO_FC_FORECAST_NOT_SC_WV.equals(smsTemplateCode)||NotifyCustomerConstants.PICKUP_FC_FORECAST_NOT_SC_WV.equals(smsTemplateCode)){
				if(NotifyCustomerConstants.PICKUP_NO_FC_FORECAST_NOT_SC_WV.equals(smsTemplateCode)){
					// 通知方式为语音时，选择语音模版
					voiceTemplateCode = NotifyCustomerConstants.SMS_CODE_PICKUP_NO_FC_FORECAST_NOT_SC + NotifyCustomerConstants.SMS_CODE_VOICE_SUFFIX;
				}else {
					// 通知方式为语音时，选择语音模版
					voiceTemplateCode = NotifyCustomerConstants.SMS_CODE_PICKUP_FC_FORECAST_NOT_SC + NotifyCustomerConstants.SMS_CODE_VOICE_SUFFIX;
				}
			}else {
				// 通知方式为语音时，选择语音模版
				voiceTemplateCode = smsTemplateCode + NotifyCustomerConstants.SMS_CODE_VOICE_SUFFIX;
			}
			// 模版参数
			SmsParamDto smsParamDto = new SmsParamDto();
			// 语音编码
			smsParamDto.setSmsCode(voiceTemplateCode);
			// 部门编码
			smsParamDto.setOrgCode(StringUtil.isEmpty(entity.getOperateOrgCode()) ? FossUserContextHelper.getOrgCode() : entity.getOperateOrgCode());
			// 参数Map
			smsParamDto.setMap(this.getSmsParam(entity, dto));
			try {
				voiceConten = sMSTempleteService.querySmsByParam(smsParamDto);
			} catch (SMSTempleteException e) {
				LOGGER.error(e.getMessage(), e);
				throw new NotifyCustomerException(NotifyCustomerException.MESSAGE_EMPTY, e);
			}
			LOGGER.info("语音信内容：{}", smsConten);
			LOGGER.info("语音模版CODE:{}", voiceTemplateCode);
		}
		LOGGER.info("短信模版CODE:{}", smsTemplateCode);
		//测试
		//PICKUP_FC_FREE_NOT_SC_WV  有到付
		//PICKUP_NO_FC_FEE_NOT_SC_WV无到付
		//smsTemplateCode="PICKUP_FC_FREE_NOT_SC_WV";
		//测试
		// 模版参数
		SmsParamDto smsParamDto = new SmsParamDto();
		// 短信编码
		smsParamDto.setSmsCode(smsTemplateCode);
		// 部门编码
		smsParamDto.setOrgCode(StringUtil.isEmpty(entity.getOperateOrgCode()) ? FossUserContextHelper.getOrgCode() : entity.getOperateOrgCode());
		// 参数Map
		smsParamDto.setMap(this.getSmsParam(entity, dto));
		try {
			smsConten = sMSTempleteService.querySmsByParam(smsParamDto);
		} catch (SMSTempleteException e) {
			LOGGER.error(e.getMessage(), e);
			throw new NotifyCustomerException(NotifyCustomerException.MESSAGE_EMPTY, e);
		}
		LOGGER.info("短信内容：{}", smsConten);

		if (StringUtil.isBlank(smsConten)) {
			throw new NotifyCustomerException(NotifyCustomerException.TEMPLATE_EMPTY);
		}
		// 获取客户通知的广告语信息 为批量通知减少通知信息广告的查询次数，添加字段
		String smsSloganValue = "";
		if (dto.isQuerySlogan() == true) {
			smsSloganValue = dto.getSmsSloganValue();
		} else {
			smsSloganValue = getSmsSloganValue(smsParamDto.getOrgCode());
		}
		LOGGER.info("广告语信息：{}", smsSloganValue);
		if (StringUtil.isBlank(smsSloganValue)) {
			smsSloganValue = "";
		}
		// 设置短信内容
		noticeContents[0] = smsConten + smsSloganValue;
		// 设置语音内容
		noticeContents[1] = voiceConten;

		return noticeContents;
	}

	/**
	 * 
	 * 获取客户通知的广告语信息
	 * 
	 * @param orgCode
	 * @return String
	 * @author ibm-wangfei
	 * @date Jan 11, 2013 11:33:04 AM
	 */
	private String getSmsSloganValue(String orgCode) {
		String smsSloganValue = "";
		try {
			smsSloganValue = sMSAdvertisingSloganService.querySmsSloganContent(NotifyCustomerConstants.PKP_NOTIFY_CUSTOMER_AD, orgCode);
		} catch (SMSAdVertisingSloganException se) {
			LOGGER.error(NotifyCustomerException.ERROR, se);
		}
		return smsSloganValue;
	}

	/**
	 * 获取短信模版编码.
	 * 
	 * @param entity the entity
	 * @param dto the dto waybillNo 运单号 orderNo 订单号 orderChannel 订单来源
	 *            orderPaidMethod 订单付款方式 deliveryCustomerId 发货客户ID
	 *            deliveryCustomerCode 发货客户编码 deliveryCustomerName 发货客户名称
	 *            deliveryCustomerMobilephone 发货客户手机 deliveryCustomerPhone
	 *            发货客户电话 deliveryCustomerContact 发货客户联系人
	 *            deliveryCustomerNationCode 发货国家 deliveryCustomerProvCode 发货省份
	 *            deliveryCustomerCityCode 发货市 deliveryCustomerDistCode 发货区
	 *            deliveryCustomerAddress 发货具体地址 receiveCustomerId 收货客户ID
	 *            receiveCustomerCode 收货客户编码 receiveCustomerName 收货客户名称
	 *            receiveCustomerMobilephone 收货客户手机 receiveCustomerPhone 收货客户电话
	 *            receiveCustomerContact 收货客户联系人 receiveCustomerNationCode 收货国家
	 *            receiveCustomerProvCode 收货省份 receiveCustomerCityCode 收货市
	 *            receiveCustomerDistCode 收货区 receiveCustomerAddress 收货具体地址
	 *            receiveOrgCode 收货部门(出发部门) productId 产品ID productCode 运输性质
	 *            receiveMethod 提货方式 customerPickupOrgCode 提货网点 loadMethod 配载类型
	 *            targetOrgCode 目的站 pickupToDoor 是否上门接货 driverCode 司机
	 *            pickupCentralized 是否集中接货 loadLineCode 配载线路 loadOrgCode 配载部门
	 *            lastLoadOrgCode 最终配载部门 preDepartureTime 预计出发时间
	 *            preCustomerPickupTime 预计派送/提货时间 carDirectDelivery 是否大车直送
	 *            goodsName 货物名称 goodsQtyTotal 货物总件数 goodsWeightTotal 货物总重量
	 *            goodsVolumeTotal 货物总体积 goodsSize 货物尺寸 goodsTypeCode 货物类型
	 *            preciousGoods 是否贵重物品 specialShapedGoods 是否异形物品 outerNotes 对外备注
	 *            innerNotes 对内备注 goodsPackage 货物包装 insuranceAmount 保价声明价值
	 *            insuranceRate 保价费率 insuranceFee 保价费 codAmount 代收货款 codRate
	 *            代收费率 codFee 代收货款手续费 refundType 退款类型 returnBillType 返单类别
	 *            secretPrepaid 预付费保密 toPayAmount 到付金额 prePayAmount 预付金额
	 *            deliveryGoodsFee 送货费 otherFee 其他费用 packageFee 包装手续费
	 *            promotionsFee 优惠费用 billingType 运费计费类型 unitPrice 运费计费费率
	 *            transportFee 公布价运费 valueAddFee 增值费用 paidMethod 开单付款方式
	 *            arriveType 到达类型 forbiddenLine 禁行 freightMethod 合票方式
	 *            flightShift 航班时间 totalFee 总费用 promotionsCode 优惠编码 createTime
	 *            创建时间 modifyTime 更新时间（业务时间） billTime 开单时间 createUserCode 开单人
	 *            modifyUserCode 更新人 createOrgCode 开单组织 modifyOrgCode 更新组织 refId
	 *            原运单ID refCode 原运单号 currencyCode 币种 isWholeVehicle 是否整车运单
	 *            wholeVehicleAppfee 整车约车报价 wholeVehicleActualfee 整车开单报价
	 *            accountName 返款帐户开户名称 accountCode 返款帐户开户账户 accountBank 返款帐户开户银行
	 *            billWeight 计费重量 pickupFee 接货费 serviceFee 装卸费 preArriveTime
	 *            预计到达时间 transportType 运输类型 printTimes 打印次数 addTime 新增时间
	 *            contactAddressId 联系人地址ID flightNumberType 航班类型
	 *            collectionDepartment 收款部门 transportationRemark 储运事项 active
	 *            运单状态 isPassOwnDepartment 是否经过营业部 otherPackage 其他包装 paperNum
	 *            纸包装 woodNum 木包装 fibreNum 纤包装 salverNum 托包装 membraneNum 膜包装
	 *            deliverCustContactId 发货联系人Id receiverCustContactId 收获联系人ID
	 *            pendingType 处理类型:"PDA_ACTIVE"--PDA已补录，"PC_ACTIVE"--暂存已开单
	 *            licensePlateNum 车牌号 orderVehicleNum 约车编号 stockStatus 库存状态
	 *            arriveGoodsQty 到达件数 stockQty 库存件数 storageDay 在库天数 wayFee 运费
	 *            storageCharge 仓储费 departureTime 出发日期 arriveTime 到达日期
	 *            lastNotifiTime 上次通知日期 handoverNo 交接单号 noticeResult 通知情况
	 *            inStockTime 入库时间 planArriveTime 预计到达时间 handoverGoodsQty 交接件数
	 *            receivingHabits 送货习惯 selectType 查询方式 notificationTimeSpace
	 *            最后通知日期与当前日期的间隔天数 notificationTime 最后通知日期 overdueDay 逾期天数
	 *            exceptionType 异常类型 exceptionNotes 异常信息 noticeContent 通知信息
	 *            taskStatus 车辆到达状态 customerQulification 客户资质（结算方式） creditAmount
	 *            信用额度 lastInStockTime 最后入库时间 receiveOrgName 始发部门名称 isBackFlg
	 *            客户是否有银行账户列表 isPay 是否为网上支付 totalToPayAmount 到付费用合计（到付金额+保管费金额）
	 * @return the smsCode
	 * @author ibm-wangfei
	 * @date Nov 21, 2012 4:38:16 PM
	 */
	private String getSmsCode(NotificationEntity entity, NotifyCustomerDto dto) {
		if (StringUtil.isNotEmpty(dto.getReceiveMethod()) && dto.getReceiveMethod().contains(WaybillConstants.DELIVER_FREE)) {
			// 送货
			return NotifyCustomerConstants.SMS_CODE_DELIVER;
		}
		// 自提
		if (dto.getToPayAmount() != null && dto.getToPayAmount().compareTo(BigDecimal.valueOf(0)) > 0) { // 有到付款
			if (StringUtil.equals(FossConstants.YES, entity.getIsStorageCharge())) {
				// 征收保管费
				// 已产生保管费提货模板
				if (dto.getStorageCharge() != null && dto.getStorageCharge().compareTo(BigDecimal.valueOf(0)) > 0) {
					// 对有到付并征收保管费的进行到付金额累计
					dto.setTotalToPayAmount(dto.getToPayAmount().add(dto.getStorageCharge()));
					return NotifyCustomerConstants.SMS_CODE_PICKUP_FC_FEE;
				}
				// 预计提货模板
				if (StringUtil.isNotEmpty(entity.getEstimatedPickupTime())) {
					return NotifyCustomerConstants.SMS_CODE_PICKUP_FC_FORECAST;
				}
				// 自提-有到付-未产生保管费-征收
//				if (dto.getStorageDay() != null && dto.getStorageDay() >= 0) {
					// 对有到付并征收保管费的进行到付金额累计
				dto.setTotalToPayAmount(dto.getToPayAmount().add(dto.getStorageCharge()==null ? BigDecimal.ZERO:dto.getStorageCharge()));
				return NotifyCustomerConstants.SMS_CODE_PICKUP_FC_FREE;
//				}
			} else {
				// 不征收保管费
				// 预计提货模板
				if (StringUtil.isNotEmpty(entity.getEstimatedPickupTime())) {
					return NotifyCustomerConstants.SMS_CODE_PICKUP_FC_FORECAST_NOT_SC;
				}
				// 提货模板
				return NotifyCustomerConstants.SMS_CODE_PICKUP_FC_FREE_NOT_SC;
			}
		} else { // 如果到付款为null或者等于0， 为无到付款
			if (StringUtil.equals(FossConstants.YES, entity.getIsStorageCharge())) {
				// 已产生保管费提货模板
				if (dto.getStorageCharge() != null && dto.getStorageCharge().compareTo(BigDecimal.valueOf(0)) > 0) {
					return NotifyCustomerConstants.SMS_CODE_PICKUP_NO_FC_FEE;
				}
				// 预计提货模板
				if (StringUtil.isNotEmpty(entity.getEstimatedPickupTime())) {
					return NotifyCustomerConstants.SMS_CODE_PICKUP_NO_FC_FORECAST;
				}
				// 三天内提货模板
				if (dto.getStorageDay() != null && dto.getStorageDay() >= 0) {
					return NotifyCustomerConstants.SMS_CODE_PICKUP_NO_FC_FREE;
				}
			} else {
				// 不征收保管费
				// 预计提货模板
				if (StringUtil.isNotEmpty(entity.getEstimatedPickupTime())) {
					return NotifyCustomerConstants.SMS_CODE_PICKUP_NO_FC_FORECAST_NOT_SC;
				}
				// 提货模板
				return NotifyCustomerConstants.SMS_CODE_PICKUP_NO_FC_FREE_NOT_SC;
			}
		}

		// 默认返回三天内提货模版
		return NotifyCustomerConstants.SMS_CODE_PICKUP_NO_FC_FREE;
	}

	/**
	 * 设置短信模版内容的参数.
	 * 
	 * @param entity the entity
	 * @param dto the dto waybillNo 运单号 orderNo 订单号 orderChannel 订单来源
	 *            orderPaidMethod 订单付款方式 deliveryCustomerId 发货客户ID
	 *            deliveryCustomerCode 发货客户编码 deliveryCustomerName 发货客户名称
	 *            deliveryCustomerMobilephone 发货客户手机 deliveryCustomerPhone
	 *            发货客户电话 deliveryCustomerContact 发货客户联系人
	 *            deliveryCustomerNationCode 发货国家 deliveryCustomerProvCode 发货省份
	 *            deliveryCustomerCityCode 发货市 deliveryCustomerDistCode 发货区
	 *            deliveryCustomerAddress 发货具体地址 receiveCustomerId 收货客户ID
	 *            receiveCustomerCode 收货客户编码 receiveCustomerName 收货客户名称
	 *            receiveCustomerMobilephone 收货客户手机 receiveCustomerPhone 收货客户电话
	 *            receiveCustomerContact 收货客户联系人 receiveCustomerNationCode 收货国家
	 *            receiveCustomerProvCode 收货省份 receiveCustomerCityCode 收货市
	 *            receiveCustomerDistCode 收货区 receiveCustomerAddress 收货具体地址
	 *            receiveOrgCode 收货部门(出发部门) productId 产品ID productCode 运输性质
	 *            receiveMethod 提货方式 customerPickupOrgCode 提货网点 loadMethod 配载类型
	 *            targetOrgCode 目的站 pickupToDoor 是否上门接货 driverCode 司机
	 *            pickupCentralized 是否集中接货 loadLineCode 配载线路 loadOrgCode 配载部门
	 *            lastLoadOrgCode 最终配载部门 preDepartureTime 预计出发时间
	 *            preCustomerPickupTime 预计派送/提货时间 carDirectDelivery 是否大车直送
	 *            goodsName 货物名称 goodsQtyTotal 货物总件数 goodsWeightTotal 货物总重量
	 *            goodsVolumeTotal 货物总体积 goodsSize 货物尺寸 goodsTypeCode 货物类型
	 *            preciousGoods 是否贵重物品 specialShapedGoods 是否异形物品 outerNotes 对外备注
	 *            innerNotes 对内备注 goodsPackage 货物包装 insuranceAmount 保价声明价值
	 *            insuranceRate 保价费率 insuranceFee 保价费 codAmount 代收货款 codRate
	 *            代收费率 codFee 代收货款手续费 refundType 退款类型 returnBillType 返单类别
	 *            secretPrepaid 预付费保密 toPayAmount 到付金额 prePayAmount 预付金额
	 *            deliveryGoodsFee 送货费 otherFee 其他费用 packageFee 包装手续费
	 *            promotionsFee 优惠费用 billingType 运费计费类型 unitPrice 运费计费费率
	 *            transportFee 公布价运费 valueAddFee 增值费用 paidMethod 开单付款方式
	 *            arriveType 到达类型 forbiddenLine 禁行 freightMethod 合票方式
	 *            flightShift 航班时间 totalFee 总费用 promotionsCode 优惠编码 createTime
	 *            创建时间 modifyTime 更新时间（业务时间） billTime 开单时间 createUserCode 开单人
	 *            modifyUserCode 更新人 createOrgCode 开单组织 modifyOrgCode 更新组织 refId
	 *            原运单ID refCode 原运单号 currencyCode 币种 isWholeVehicle 是否整车运单
	 *            wholeVehicleAppfee 整车约车报价 wholeVehicleActualfee 整车开单报价
	 *            accountName 返款帐户开户名称 accountCode 返款帐户开户账户 accountBank 返款帐户开户银行
	 *            billWeight 计费重量 pickupFee 接货费 serviceFee 装卸费 preArriveTime
	 *            预计到达时间 transportType 运输类型 printTimes 打印次数 addTime 新增时间
	 *            contactAddressId 联系人地址ID flightNumberType 航班类型
	 *            collectionDepartment 收款部门 transportationRemark 储运事项 active
	 *            运单状态 isPassOwnDepartment 是否经过营业部 otherPackage 其他包装 paperNum
	 *            纸包装 woodNum 木包装 fibreNum 纤包装 salverNum 托包装 membraneNum 膜包装
	 *            deliverCustContactId 发货联系人Id receiverCustContactId 收获联系人ID
	 *            pendingType 处理类型:"PDA_ACTIVE"--PDA已补录，"PC_ACTIVE"--暂存已开单
	 *            licensePlateNum 车牌号 orderVehicleNum 约车编号 stockStatus 库存状态
	 *            arriveGoodsQty 到达件数 stockQty 库存件数 storageDay 在库天数 wayFee 运费
	 *            storageCharge 仓储费 departureTime 出发日期 arriveTime 到达日期
	 *            lastNotifiTime 上次通知日期 handoverNo 交接单号 noticeResult 通知情况
	 *            inStockTime 入库时间 planArriveTime 预计到达时间 handoverGoodsQty 交接件数
	 *            receivingHabits 送货习惯 selectType 查询方式 notificationTimeSpace
	 *            最后通知日期与当前日期的间隔天数 notificationTime 最后通知日期 overdueDay 逾期天数
	 *            exceptionType 异常类型 exceptionNotes 异常信息 noticeContent 通知信息
	 *            taskStatus 车辆到达状态 customerQulification 客户资质（结算方式） creditAmount
	 *            信用额度 lastInStockTime 最后入库时间 receiveOrgName 始发部门名称 isBackFlg
	 *            客户是否有银行账户列表 isPay 是否为网上支付 totalToPayAmount 到付费用合计（到付金额+保管费金额）
	 * @return the smsParam
	 * @author ibm-wangfei
	 * @date Nov 21, 2012 5:00:34 PM
	 */
	private Map<String, String> getSmsParam(NotificationEntity entity, NotifyCustomerDto dto) {
		Map<String, String> paramMap = new HashMap<String, String>();
		// 提货人姓名
		if (StringUtil.isNotEmpty(dto.getReceiveCustomerContact())) {
			paramMap.put("receiveCustomerContact", dto.getReceiveCustomerContact());
		} else {
			paramMap.put("receiveCustomerContact", "");
		}
		// 预计小时数
		if (StringUtil.isNotEmpty(entity.getEstimatedPickupTime())) {
			paramMap.put("estimatedPickupTime", entity.getEstimatedPickupTime());
		} else {
			paramMap.put("estimatedPickupTime", "");
		}
		
		//update：begin   短信模板中添加  重量  和  体积参数
		
		if(dto.getGoodsWeightTotal()!=null){
			paramMap.put("totalWeight", dto.getGoodsWeightTotal().toString());
		}else{
			paramMap.put("totalWeight", "");
		}
		
		if(dto.getGoodsWeightTotal()!=null){
			paramMap.put("totalVolume",dto.getGoodsVolumeTotal().toString());
		}else {
			paramMap.put("totalVolume","");
		}
		
		//end
		
		// 发货人
		if (StringUtil.isNotEmpty(dto.getDeliveryCustomerContact())) {
			paramMap.put("deliveryCustomerContact", dto.getDeliveryCustomerContact());
		} else {
			paramMap.put("deliveryCustomerContact", "");
		}
		// 运单号
		if (StringUtil.isNotEmpty(dto.getWaybillNo())) {
			paramMap.put("waybillNo", dto.getWaybillNo());
		} else {
			paramMap.put("waybillNo", "");
		}

		// 通知件数
		if (entity.getArriveGoodsQty() != null) {
			paramMap.put("arriveGoodsQty", entity.getArriveGoodsQty().toString());
		} else {
			paramMap.put("arriveGoodsQty", "");
		}

		// 免费存储天数
		int warehouseFreeSafeDataNum = this.getWarehouseFreeSafeDataNum();
		if (warehouseFreeSafeDataNum > 0) {
			paramMap.put("warehouseFreeSafeDataNum", String.valueOf(warehouseFreeSafeDataNum));
		} else {
			paramMap.put("warehouseFreeSafeDataNum", "");
		}

		// 根据登录的部门code获取
		OrgAdministrativeInfoEntity orgAdministrativeInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(StringUtil.isEmpty(entity.getOperateOrgCode()) ? FossUserContextHelper.getOrgCode() : entity.getOperateOrgCode());
		if (orgAdministrativeInfo != null) {
			// 联系电话
			paramMap.put("customerPickupOrgTel", StringUtil.isEmpty(orgAdministrativeInfo.getDepTelephone()) ? "" : orgAdministrativeInfo.getDepTelephone());
			// 部门传真
			paramMap.put("customerPickupOrgFax", StringUtil.isEmpty(orgAdministrativeInfo.getDepFax()) ? "" : orgAdministrativeInfo.getDepFax());
			// 联系地址
			paramMap.put("customerPickupOrgAdd", StringUtil.isEmpty(orgAdministrativeInfo.getAddress()) ? "" : orgAdministrativeInfo.getAddress());
			// 发货部门行政区域（市）
			paramMap.put("city", orgAdministrativeInfo.getCityName() == null ? "" : orgAdministrativeInfo.getCityName());
			// 发货部门行政区域（区县）
			paramMap.put("county", orgAdministrativeInfo.getCountyName() == null ? "" : orgAdministrativeInfo.getCountyName());
			// 提货网点名称
			paramMap.put("customerPickupOrgName", orgAdministrativeInfo.getName());
			entity.setOperateOrgName(orgAdministrativeInfo.getName());
			if(StringUtils.isNotBlank(orgAdministrativeInfo.getDepCoordinate())){
				// 查询GIS短链接
				OrgGisUrlEntity orgGisUrlEntity = orgGisUrlDao.queryGisUrlInfoByOrgCode(orgAdministrativeInfo.getCode());
				if(orgGisUrlEntity!=null){
					if(StringUtil.isBlank(orgGisUrlEntity.getShortUrl())){
						paramMap.put("gisShortUrl", "");
					}else{
						//查询配置参数是否有配置试点部门
						String confValue = configurationParamsService.queryConfValueByCode(NotifyCustomerConstants.GIS_SHORTURL_HTTP);
						if(StringUtils.isNotBlank(confValue)){
							paramMap.put("gisShortUrl",confValue+orgGisUrlEntity.getShortUrl());
							
						}else{
							paramMap.put("gisShortUrl",NotifyCustomerConstants.GIS_SHORTURL_HTTP_DEFAULT +orgGisUrlEntity.getShortUrl());
							
						}
					}
				}else{
					paramMap.put("gisShortUrl", "");
				}
			}else{
				paramMap.put("gisShortUrl", "");
			}
		} else {
			// 设置为空值
			paramMap.put("customerPickupOrgTel", "");
			paramMap.put("customerPickupOrgFax", "");
			paramMap.put("customerPickupOrgAdd", "");
			paramMap.put("city", "");
			paramMap.put("county", "");
			paramMap.put("customerPickupOrgName", "");
			paramMap.put("gisShortUrl", "");
		}

		// 逾期天数
		if (dto.getStorageDay() != null) {
			paramMap.put("overdueDay", String.valueOf(dto.getStorageDay()));
		} else {
			paramMap.put("overdueDay", "0");
		}
		// 保管费金额
		if (dto.getStorageCharge() != null) {
			paramMap.put("storageCharge", String.valueOf(dto.getStorageCharge()));
		} else {
			paramMap.put("storageCharge", "");
		}
		// 到付款金额
		if (dto.getToPayAmount() != null) {
			paramMap.put("toPayAmount", String.valueOf(dto.getToPayAmount()));
		} else {
			paramMap.put("toPayAmount", "");
		}
		// 有到付且征收保管费 到付费用合计（到付金额 + 保管费金额）.
		if (dto.getTotalToPayAmount() != null) {
			paramMap.put("totalToPayAmount", String.valueOf(dto.getTotalToPayAmount()));
		} else if (dto.getToPayAmount() != null) {
			paramMap.put("totalToPayAmount", String.valueOf(dto.getToPayAmount()));
		} else {
			paramMap.put("totalToPayAmount", "");
		}
		return paramMap;
	}

	/**
	 * 发送短信、语音.
	 * 
	 * @param notificationEntity the notification entity
	 * @throws NotifyCustomerException the notify customer exception
	 * @author ibm-wangfei
	 * @date Nov 27, 2012 5:12:24 PM
	 */
	private void sendSms(NotificationEntity notificationEntity) {
		SMSSendLogEntity smsSendLog = new SMSSendLogEntity();
		try {
			//发送部门编码
			smsSendLog.setSenddeptCode(notificationEntity.getOperateOrgCode());
			//发送人员编码
			smsSendLog.setSenderCode(notificationEntity.getOperatorNo());
			// 电话
			smsSendLog.setMobile(notificationEntity.getMobile());
			// 短信内容
			smsSendLog.setContent(notificationEntity.getNoticeContent());
			// 发送部门
			smsSendLog.setSenddept(notificationEntity.getOperateOrgName());
			// 发送人
			smsSendLog.setSender(notificationEntity.getOperator());
			// 业务类型
			smsSendLog.setMsgtype(notificationEntity.getModuleName());
			// 短信来源
			smsSendLog.setMsgsource(NotifyCustomerConstants.SYS_SOURCE);
			// 唯一标识
			if (StringUtil.isBlank(notificationEntity.getId())) {
				smsSendLog.setUnionId(UUIDUtils.getUUID());
			} else {
				smsSendLog.setUnionId(notificationEntity.getId());
			}
			// 运单号
			smsSendLog.setWaybillNo(notificationEntity.getWaybillNo());
			// 发送时间
			smsSendLog.setSendTime(new Date());
			// 服务类型 （1:短信、2:语音、3:短信语音）
			if (StringUtil.equals(NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE, notificationEntity.getNoticeType())) {
				if(!notificationEntity.isStopSmsToReciever()) {
					// 服务类型（1:短信）
					smsSendLog.setServiceType(NumberConstants.NUMERAL_S_ONE);
					LOGGER.info("短信内容：" + ReflectionToStringBuilder.toString(smsSendLog));
					// 发送短信内容
					smsSendLogService.sendSMS(smsSendLog);
				}
			} else if (StringUtil.equals(NotifyCustomerConstants.NOTIFY_TYPE_VOICE_NOTICE, notificationEntity.getNoticeType())) {
				// 服务类型（2:语音）
				if (StringUtil.equals(NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY, notificationEntity.getModuleName())) {
					// 业务类型是客户通知 and 实体内的语言内容不为空，同时发送短信和语音
					if (StringUtil.isNotBlank(notificationEntity.getNoticeContent())) {
						// 发送短信（1:短信）
						smsSendLog.setUnionId(UUIDUtils.getUUID());
						smsSendLog.setServiceType(NumberConstants.NUMERAL_S_ONE);
						LOGGER.info("短信内容：" + ReflectionToStringBuilder.toString(smsSendLog));
						// 发送短信内容
						smsSendLogService.sendSMS(smsSendLog);
					}
					if (StringUtil.isNotBlank(notificationEntity.getNoticeContentVoice())) {
						// 发送语音（2:语音）
						// 发送时间
						smsSendLog.setSendTime(new Date());
						smsSendLog.setUnionId(notificationEntity.getId());
						// 通知类型
						smsSendLog.setServiceType(NumberConstants.NUMERAL_S_TWO);
						// 通知内容
						smsSendLog.setContent(notificationEntity.getNoticeContentVoice());
						LOGGER.info("短信内容：" + ReflectionToStringBuilder.toString(smsSendLog));
						// 发送语音内容
						smsSendLogService.sendSMS(smsSendLog);
					}
				} else {
					// 其他业务，只发送语音
					smsSendLog.setServiceType(NumberConstants.NUMERAL_S_TWO);
					LOGGER.info("语音内容：" + ReflectionToStringBuilder.toString(smsSendLog));
					// 发送短信内容
					smsSendLogService.sendSMS(smsSendLog);
				}
			} else {
				LOGGER.error("非短信语音类型");
			}
		} catch (SMSSendLogException se) {
			LOGGER.error(NotifyCustomerException.ERROR, se);
			throw new NotifyCustomerException(se.getMessage(), se);
		}
	}

	/**
	 * @param customerService the customerService to see
	 */
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	/**
	 * @param exceptionProcessService the exceptionProcessService to see
	 */
	public void setExceptionProcessService(IExceptionProcessService exceptionProcessService) {
		this.exceptionProcessService = exceptionProcessService;
	}

	/**
	 * @param notifyCustomerDao the notifyCustomerDao to see
	 */
	public void setNotifyCustomerDao(INotifyCustomerDao notifyCustomerDao) {
		this.notifyCustomerDao = notifyCustomerDao;
	}

	/**
	 * @param arriveSheetManngerService the arriveSheetManngerService to see
	 */
	public void setArriveSheetManngerService(IArriveSheetManngerService arriveSheetManngerService) {
		this.arriveSheetManngerService = arriveSheetManngerService;
	}

	/**
	 * @param customerBargainService the customerBargainService to see
	 */
	public void setCustomerBargainService(ICustomerBargainService customerBargainService) {
		this.customerBargainService = customerBargainService;
	}

	/**
	 * @param sMSTempleteService the sMSTempleteService to see
	 */
	public void setsMSTempleteService(ISMSTempleteService sMSTempleteService) {
		this.sMSTempleteService = sMSTempleteService;
	}

	/**
	 * @param smsSendLogService the smsSendLogService to see
	 */
	public void setSmsSendLogService(ISMSSendLogService smsSendLogService) {
		this.smsSendLogService = smsSendLogService;
	}

	/**
	 * @param smsFailLogService the smsFailLogService to see
	 */
	public void setSmsFailLogService(ISMSFailLogService smsFailLogService) {
		this.smsFailLogService = smsFailLogService;
	}

	/**
	 * @param orgAdministrativeInfoService the orgAdministrativeInfoService to
	 *            see
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * @param sMSAdvertisingSloganService the sMSAdvertisingSloganService to see
	 */
	public void setsMSAdvertisingSloganService(ISMSAdvertisingSloganService sMSAdvertisingSloganService) {
		this.sMSAdvertisingSloganService = sMSAdvertisingSloganService;
	}

	/**
	 * @param handleQueryOutfieldService the handleQueryOutfieldService to see
	 */
	public void setHandleQueryOutfieldService(IHandleQueryOutfieldService handleQueryOutfieldService) {
		this.handleQueryOutfieldService = handleQueryOutfieldService;
	}

	/**
	 * @param configurationParamsService the configurationParamsService to see
	 */
	public void setBillReceivableService(IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	/**
	 * @param configurationParamsService the configurationParamsService to see
	 */
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setNotifyCustomerService(INotifyCustomerService notifyCustomerService) {
		this.notifyCustomerService = notifyCustomerService;
	}

	@Override
	public InputStream exportNotifyCustomerInfo(NotifyCustomerConditionDto conditionDto) {
		// 设置默认查询条件.
		initNotifyCustomerQuery(conditionDto);
		List<NotifyCustomerDto> dtos = this.queryWaybillInfoList(conditionDto, NumberConstants.ZERO, Integer.MAX_VALUE);
		if (CollectionUtils.isEmpty(dtos)) {
			dtos=new ArrayList<NotifyCustomerDto>();
			//return dtos;
		}
		List<List<String>> rowList = new ArrayList<List<String>>();
		for (NotifyCustomerDto dto : dtos) {
			List<String> columnList = new ArrayList<String>();
			if (StringUtils.isNotBlank(dto.getNoticeResult())) {
				//通知状态
				columnList.add(DictUtil.rendererSubmitToDisplay(dto.getNoticeResult(), DictionaryConstants.PKP_NOTIFY_CUSTOMER_STATUS));
			}
			// 运单号
			columnList.add(dto.getWaybillNo());
			// 收货人姓名
			columnList.add(dto.getReceiveCustomerContact());
			// 收货人电话
			columnList.add(dto.getReceiveCustomerPhone());
			// 收货人手机
			columnList.add(dto.getReceiveCustomerMobilephone());
			//派送方式
			columnList.add(DictUtil.rendererSubmitToDisplay(dto.getReceiveMethod(), DictionaryConstants.PICKUP_GOODS));
			//运输性质
			ProductEntity  pro =productService.getProductByCache(dto.getProductCode(),new Date());
			if(null != pro){
				columnList.add(pro.getName());//运输性质
			}else {
				columnList.add(dto.getProductCode());//运输性质
			}
			//支付方式
			columnList.add(DictUtil.rendererSubmitToDisplay(dto.getPaidMethod(), DictionaryConstants.SETTLEMENT__PAYMENT_TYPE));
			//送货地址
			columnList.add(dto.getReceiveCustomerAddress());
			if (StringUtil.equals(dto.getSelectType(), NotifyCustomerConstants.SELECT_TYPE_STOCK + "")) {
				//入库时间
				columnList.add(DateUtils.convert(dto.getInStockTime(), DateUtils.DATE_TIME_FORMAT));
				//开单件数
				columnList.add(dto.getGoodsQtyTotal() == null ? BigDecimal.ZERO.toString() : dto.getGoodsQtyTotal().toString());
				//到达件数
				columnList.add(dto.getArriveGoodsQty() == null ? BigDecimal.ZERO.toString() : dto.getArriveGoodsQty().toString());
				//库存件数
				columnList.add(dto.getStockQty() == null ? BigDecimal.ZERO.toString() : dto.getStockQty().toString());
				//库位件数
				columnList.add(dto.getPositionQty() == null ? BigDecimal.ZERO.toString() : dto.getPositionQty().toString());
				//在库天数
				columnList.add(dto.getStorageDay() == null ? BigDecimal.ZERO.toString() : dto.getStorageDay().toString());
			} else {
				//车辆到达时间
				columnList.add(DateUtils.convert(dto.getArriveTime(), DateUtils.DATE_TIME_FORMAT));
				//车辆预计到达时间
				columnList.add(DateUtils.convert(dto.getPlanArriveTime(), DateUtils.DATE_TIME_FORMAT));
				//开单件数
				columnList.add(dto.getGoodsQtyTotal() == null ? BigDecimal.ZERO.toString() : dto.getGoodsQtyTotal().toString());
				//到达件数
				columnList.add(dto.getArriveGoodsQty() == null ? BigDecimal.ZERO.toString() : dto.getArriveGoodsQty().toString());
				//交接单件数
				columnList.add(dto.getHandoverGoodsQty() == null ? BigDecimal.ZERO.toString() : dto.getHandoverGoodsQty().toString());
				//交接单号
				columnList.add(dto.getHandoverNo());
				//车牌号
				columnList.add(dto.getVehicleNo());
				//车次号
				columnList.add(dto.getVehicleAssembleNo());
			}
			//新增重量、体积、到付金额导出
			columnList.add(dto.getGoodsWeightTotal() == null ? BigDecimal.ZERO.toString() : dto.getGoodsWeightTotal().toString());
			columnList.add(dto.getGoodsVolumeTotal() == null ? BigDecimal.ZERO.toString() : dto.getGoodsVolumeTotal().toString());
			columnList.add(dto.getToPayAmount() == null ? BigDecimal.ZERO.toString() : dto.getToPayAmount().toString());
			
			//收货习惯
			columnList.add(dto.getReceivingHabits());
			rowList.add(columnList);
		}
		List<String> rowHeads = new ArrayList<String>();
		rowHeads.add("通知状态");
		// 运单号
		rowHeads.add("运单号");
		// 收货人姓名
		rowHeads.add("收货人姓名");
		// 收货人电话
		rowHeads.add("收货人电话");
		// 收货人手机
		rowHeads.add("收货人手机");
		//派送方式
		rowHeads.add("派送方式");
		//运输性质
		rowHeads.add("运输性质");
		//支付方式
		rowHeads.add("支付方式");
		// 送货地址
		rowHeads.add("送货地址");
		//update 
		
		if(CollectionUtils.isEmpty(dtos)){
			String selectType = conditionDto.getSelectType();
			if(StringUtils.isNotEmpty(selectType)&&selectType.equals(NotifyCustomerConstants.SELECT_TYPE_STOCK + "")){
				//入库时间
				rowHeads.add("入库时间");
				//开单件数
				rowHeads.add("开单件数");
				//到达件数
				rowHeads.add("到达件数");
				//库存件数
				rowHeads.add("库存件数");
				//库位件数
				rowHeads.add("库位件数");
				//在库天数
				rowHeads.add("在库天数");
			}else{
				//车辆到达时间
				rowHeads.add("车辆实际到达时间");
				//车辆预计到达时间
				rowHeads.add("车辆预计到达时间");
				//开单件数
				rowHeads.add("开单件数");
				//到达件数
				rowHeads.add("到达件数");
				//交接单件数
				rowHeads.add("交接单件数");
				//交接单号
				rowHeads.add("交接单号");
				//车牌号
				rowHeads.add("车牌号");
				//车次号
				rowHeads.add("车次号");
			}
		}else {
			if (StringUtil.equals(dtos.get(0).getSelectType(), NotifyCustomerConstants.SELECT_TYPE_STOCK + "")) {
				//入库时间
				rowHeads.add("入库时间");
				//开单件数
				rowHeads.add("开单件数");
				//到达件数
				rowHeads.add("到达件数");
				//库存件数
				rowHeads.add("库存件数");
				//库位件数
				rowHeads.add("库位件数");
				//在库天数
				rowHeads.add("在库天数");
			} else  {
				//车辆到达时间
				rowHeads.add("车辆实际到达时间");
				//车辆预计到达时间
				rowHeads.add("车辆预计到达时间");
				//开单件数
				rowHeads.add("开单件数");
				//到达件数
				rowHeads.add("到达件数");
				//交接单件数
				rowHeads.add("交接单件数");
				//交接单号
				rowHeads.add("交接单号");
				//车牌号
				rowHeads.add("车牌号");
				//车次号
				rowHeads.add("车次号");
			}
		}
		
		//new add
		rowHeads.add("开单总重量(千克)");
		rowHeads.add("开单总体积(立方米)");
		rowHeads.add("到付金额");
		
		//update
		//收货习惯
		rowHeads.add("收货习惯");
		ExportResource exportResource = new ExportResource();
		exportResource.setHeads(rowHeads.toArray(new String[rowHeads.size()]));
		exportResource.setRowList(rowList);
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName("通知客户");
		exportSetting.setSize(NotifyCustomerConstants.EXPORT_NUMBER);
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		return objExporterExecutor.exportSync(exportResource, exportSetting);
	}

	/***
	 * 去除参数空格  完成数据查询
	 * @author yuting
	 * @time 2014-07-29
	 * @param conditionDto NotifyCustomerConditionDto
	 * @return NotifyCustomerConditionDto
	 */
	private NotifyCustomerConditionDto getNotifyCustomerConditionDto(
			NotifyCustomerConditionDto conditionDto) {
		if(conditionDto!=null){
			String[] arrayWaybillNos = conditionDto.getArrayWaybillNos();
			if(ArrayUtils.isNotEmpty(arrayWaybillNos)){
				String[] rstArray=new String[arrayWaybillNos.length];
				for (int i = 0; i < arrayWaybillNos.length; i++) {
					rstArray[i]=arrayWaybillNos[i].trim();
				}
				conditionDto.setArrayWaybillNos(rstArray);
			}
		}
		return conditionDto;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	
	/**
	 * 
	 * @Title: getSelfSmsCode 
	 * @Description: 自提到付客户通知短信模板变更 
	 * @param @param entity
	 * @param @param dto
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
	private String getSelfSmsCode(NotificationEntity entity, NotifyCustomerDto dto){
		//针对“特殊增值服务”类标识的运单，使用新的短信通知模板   -- add by 243921
		if(StringUtils.isNotBlank(dto.getSpecialValueAddedService()) && dto.getSpecialValueAddedService().contains(NotifyCustomerConstants.DELIVER_EQUIP)){
			return NotifyCustomerConstants.SMS_CODE_JZ_WAYBILL_NOTICE;
		}
		//判断提货方式不为空，且是以DELIVER开头的（是送货方式）
		if (StringUtil.isNotEmpty(dto.getReceiveMethod()) && dto.getReceiveMethod().contains(WaybillConstants.DELIVER_FREE)) {
			// 送货
			return NotifyCustomerConstants.SMS_CODE_DELIVER;
		}else {
			// 自提，判断此单到付未核销金额是否大于0
			//根据运单号，取得此单对应的到付未核销金额
			List<String> sourceBillNos = new ArrayList<String>();
			sourceBillNos.add(dto.getWaybillNo());
			String sourceBillType = "W";
			String active = FossConstants.YES;
			BigDecimal zero = BigDecimal.valueOf(0);
			BigDecimal arrivePayTotal = zero;
			//根据运单号，获取应收单对应信息
			List<BillReceivableEntity> billReceivableEntitys = billReceivableService.queryBySourceBillNOs(sourceBillNos, sourceBillType, active);
			//判断对应信息是否为空
			if(!CollectionUtils.isEmpty(billReceivableEntitys)
					&& billReceivableEntitys.size() > 0){
				//循环获取总金额
				for(BillReceivableEntity billEntity : billReceivableEntitys){
					//如果未核销金额不为零，且放款方式为到付 (剔除始发应收、合伙人始发提成应收、合伙人委托派费应收)
					if(null != billEntity.getUnverifyAmount()
							&& StringUtils.equals(billEntity.getPaymentType(), "FC") 
							&& !StringUtils.equals(billEntity.getBillType(),SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__ORIGINAL_RECEIVABLE)
							&& !StringUtils.equals(billEntity.getBillType(),SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE)
							&& !StringUtils.equals(billEntity.getBillType(),SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_RECEIVABLE)){
						//将所有到付未核销金额相加
						arrivePayTotal = arrivePayTotal.add(billEntity.getUnverifyAmount());
					}
				}
			}
			//有未核销到付款
			if(arrivePayTotal.compareTo(zero) > 0){
				//设置未核销到付款
				dto.setToPayAmount(arrivePayTotal);
				//有征收
				if (StringUtil.equals(FossConstants.YES, entity.getIsStorageCharge())) {
					// 自提-有到付-产生保管费-征收(新)
					if (dto.getStorageCharge() != null && dto.getStorageCharge().compareTo(BigDecimal.valueOf(0)) > 0) {
						// 对有到付并征收保管费的进行到付金额累计
						dto.setTotalToPayAmount(arrivePayTotal.add(dto.getStorageCharge()));
						return NotifyCustomerConstants.SELF_FC_STORE_FEE_LEVY;
					}
					// 自提-有到付-预计提货-征收(新)
					if (StringUtil.isNotEmpty(entity.getEstimatedPickupTime())) {
						return NotifyCustomerConstants.SELF_FC_PREDELIVER_LEVY;
					}
					// 默认为自提-有到付-未产生保管费-征收(新)
					dto.setTotalToPayAmount(arrivePayTotal.add(dto.getStorageCharge()==null ? BigDecimal.ZERO:dto.getStorageCharge()));
					return NotifyCustomerConstants.SELF_FC_LEVY;
				} else {
					// 自提-有到付-预计提货-不征收(新)
					if (StringUtil.isNotEmpty(entity.getEstimatedPickupTime())) {
						//update
						//return NotifyCustomerConstants.SELF_FC_PREDELIVER;
						return NotifyCustomerConstants.PICKUP_FC_FORECAST_NOT_SC_WV;
						//update
						
					}
					return NotifyCustomerConstants.SELF_FC;
					//return NotifyCustomerConstants.SELF_FC_WV;
				}			
			}
			//没有未核销到付款
			else{
				 // 有征收
				if (StringUtil.equals(FossConstants.YES, entity.getIsStorageCharge())) {
					// 自提-无到付-已产生保管费-征收(新)
					if (dto.getStorageCharge() != null && dto.getStorageCharge().compareTo(BigDecimal.valueOf(0)) > 0) {
						return NotifyCustomerConstants.SELF_NOFC_STORE_FEE_LEVY;
					}
					// 自提-无到付-预计提货-征收(新)
					if (StringUtil.isNotEmpty(entity.getEstimatedPickupTime())) {
						return NotifyCustomerConstants.SELF_NOFC_PREDELIVER_LEVY;
					}
					// 自提-无到付-未产生保管费-征收(新)
					if (dto.getStorageDay() != null && dto.getStorageDay() >= 0) {
						return NotifyCustomerConstants.SELF_NOFC_LEVY;
					}
				} 
				//无征收
				else {
					// 自提-无到付-预计提货-不征收(新)
					if (StringUtil.isNotEmpty(entity.getEstimatedPickupTime())) {
						//update
						//return NotifyCustomerConstants.SELF_NOFC_PREDELIVER;
						return NotifyCustomerConstants.PICKUP_NO_FC_FORECAST_NOT_SC_WV;
						//update
					}
					// 自提-无到付-不征收(新)
					return NotifyCustomerConstants.SELF_NOFC ;
					//return NotifyCustomerConstants.SELF_NOFC_WV ;
				}
				return NotifyCustomerConstants.SELF_NOFC_LEVY;
			}
		}
	}
	/**
	 * 根据运单号查询通知明细信息
	 *  @author 159231-foss-meiying
	 * @date 2014-4-24 下午4:41:57
	 */
	public NotifyCustomerConditionDto queryNoticeList(String waybillNo){
		if(StringUtils.isBlank(waybillNo)){
			return null;
		}
		NotifyCustomerConditionDto conditionDto=new NotifyCustomerConditionDto();
		NotificationEntity notification = new NotificationEntity();
		notification.setWaybillNo(waybillNo);
		notification.setOrder("DESC");
		notification.setOperateOrgCode(FossUserContextHelper.getOrgCode());//操作部门编码
		notification.setModuleName(NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY);
		// 运单通知列表信息
		List<NotificationEntity> list = notifyCustomerDao.queryNotificationsByParam(notification);
		if(list!=null && list.size() >0){
			conditionDto.setNotificationEntityList(list);
			return conditionDto;
		}else {
			return null;
		}
	}

	public void setCustomerDao(ICustomerDao customerDao) {
		this.customerDao = customerDao;
	}


	public void setCustomerReceiptAddressService(
			ICustomerReceiptAddressService customerReceiptAddressService) {
		this.customerReceiptAddressService = customerReceiptAddressService;
	}

	/**
	 * 根据运单号查询最后一次通知的信息
	 * @author 159231 meiying
	 * 2015-4-29  下午8:09:12
	 * @param notify
	 * @return
	 */
	@Override
	public NotificationEntity queryLastNotifyByWaybillNo(
			NotificationEntity notify) {
		if(notify==null){
			return null;
		}
		if(StringUtils.isBlank(notify.getWaybillNo())){
			return null;
		}
		return notifyCustomerDao.queryLastNotifyByWaybillNo(notify);
	}
	/**
	 * 根据ID修改通知的部分信息
	 * @author 159231 meiying
	 * 2015-5-4  下午2:31:58
	 * @param notify
	 * @return
	 */
	@Override
	public int updatePartByPrimaryKeySelective(NotificationEntity notify) {
		return notifyCustomerDao.updatePartByPrimaryKeySelective(notify);
	}
	
	/**
	 *  比较开单地址与当前实际收货地址是否相等
	 * @param notificationEntity
	 */
	private boolean compareWayBill2ActualAddress(WaybillEntity  waybill, NotificationEntity notificationEntity) {
		//拼接开单地址信息
		String waybillAdress = toNull2Str(waybill.getReceiveCustomerProvCode())+ toNull2Str(waybill.getReceiveCustomerCityCode())+toNull2Str(waybill.getReceiveCustomerDistCode()) +
				toNull2Str(waybill.getReceiveCustomerAddress());

		ActualFreightEntity actualFreight = this.actualFreightDao.queryByWaybillNo(notificationEntity.getWaybillNo());
		if(null != actualFreight){
			waybillAdress  = waybillAdress + toNull2Str(actualFreight.getReceiveCustomerAddressNote());
		}
		
		//拼接通知界面实际地址
		String noticeAddress = toNull2Str(notificationEntity.getActualProvCode()) + toNull2Str(notificationEntity.getActualCityCode()) + toNull2Str(notificationEntity.getActualDistrictCode()) + 
				toNull2Str(notificationEntity.getActualAddressDetail()) + toNull2Str(notificationEntity.getActualStreetn());
		
		if (waybillAdress.equals(noticeAddress)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 设置实际收货地址为null
	 * @param notificationEntity
	 */
	private void modifyActualAddress2Null(NotificationEntity notificationEntity) {
		notificationEntity.setActualProvCode(null);
		notificationEntity.setActualCityCode(null);
		notificationEntity.setActualDistrictCode(null);
		notificationEntity.setActualAddressDetail(null);
		notificationEntity.setActualStreetn(null);
	}
	
	/**
	 * 判断是否停发收货人短信
	 */
	private boolean ifStopSmsToReciever(String waybillNo) {
		WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(waybillNo);
	    if(null!=waybill) {
	    	//零担不停发
	    	if(!WaybillConstants.directDetermineIsExpressByProductCode(waybill.getProductCode())) {
	    		return false;
	    	}
			if(StringUtils.isNotBlank(waybill.getDeliveryCustomerCode())) {
				CustomerEntity senderCustomerEntity = customerDao.queryCustStateByCode(waybill.getDeliveryCustomerCode());
				if(null!=senderCustomerEntity) {
					// 如果发货客户RECEIVER_SMS属性为"两者都停发"时,短信停发 219396-chengdaolin 20150812
					if(NotifyCustomerConstants.SMS_STOP_RECIEVER_BOTH.equals(senderCustomerEntity.getReceiverSms())) {
						return true;
					}else if (NotifyCustomerConstants.SMS_STOP_RECIEVER.equals(senderCustomerEntity.getReceiverSms())) {
						// 如果发货客户RECEIVER_SMS属性为"客户的收件人短信停发",短信停发 219396-chengdaolin 20150812
						return true; 
					}
				}
			}
			if(StringUtils.isNotBlank(waybill.getReceiveCustomerCode())) {
				CustomerEntity recieverCustomerEntity = customerDao.queryCustStateByCode(waybill.getReceiveCustomerCode());
				// 如果收货客户RECEIVER_SMS属性为"两者都停发"时,短信停发 219396-chengdaolin 20150812
				if(NotifyCustomerConstants.SMS_STOP_RECIEVER_BOTH.equals(recieverCustomerEntity.getReceiverSms())) {
					return true;
				}
				if (null != recieverCustomerEntity&& NotifyCustomerConstants.SMS_STOP_CUSTOMER_AS_RECIEVER.equals(recieverCustomerEntity.getReceiverSms())) {
					// 如果收货客户RECEIVER_SMS属性为"客户作为收件人短信停发"时,短信停发 219396-chengdaolin 20150812
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 自动推送到货信息
	 * @author 243921 zhangtingting
	 * @date 2015-12-25 下午5:30:13
	 */
	@Override
	@Transactional
	public void autoSendArrivalGoods(ArrivalGoodsWaybillDto dto) {
		LOGGER.info("开始封装运单通知信息内容，并推送到货信息。。。");
		NotificationEntity entity = new NotificationEntity();
		//设置基本信息
		entity.setNoticeType(NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE);
		// 运单号
		entity.setWaybillNo(dto.getWaybillNo());
		//发送部门编码-- 提货网点
		entity.setOperateOrgCode(dto.getCustomerPickUpOrgCode());
		// 根据登录的部门code获取
		OrgAdministrativeInfoEntity orgAdministrativeInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(dto.getCustomerPickUpOrgCode());
		if(null != orgAdministrativeInfo){
			entity.setOperateOrgName(orgAdministrativeInfo.getName());
		}
		//发送人员编码
		entity.setOperatorNo("000000");
		// 电话
		entity.setMobile(dto.getReceiveCustomerMobilephone());
		// 发送人
		entity.setOperator("--");
		// 业务类型
		entity.setModuleName(NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY);
		// 数量
		entity.setArriveGoodsQty(dto.getGoodsQtyTotal());
		// 接收人
		entity.setConsignee(dto.getReceiveCustomerContact());
		// 操作时间
		entity.setOperateTime(new Date());
		// 币种
		entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		//特殊增值服务类型
		entity.setSpecialValueAddedService(dto.getSpecialValueAddedService());
		// 获取短信内容
		entity.setNoticeContent(this.getSmsContentFoJz(entity));
		// 发送短信
		try {
			sendMessage(entity);
			// 更新运单关联表通知状态、最后通知时间、送货日期、付款方式
			updateActualFreightEntityByWaybillNo(entity);
		} catch (NotifyCustomerException e) {
			LOGGER.error(e.getMessage(), e);
		}
		LOGGER.info("推送家装运单到货信息结束。。。");
	}
	/**
	 * 自动推送到货信息 获取家装短信模板内容
	 * @author 243921 zhangtingting
	 * @date 2015-12-25 下午5:30:50
	 */
	private String getSmsContentFoJz(NotificationEntity entity) {
		String sms = ""; // 返回短信
		// 模版参数
		SmsParamDto smsParamDto = new SmsParamDto();
		// 短信编码
		smsParamDto.setSmsCode(NotifyCustomerConstants.SMS_CODE_JZ_WAYBILL_NOTICE);
		// 部门编码
		smsParamDto.setOrgCode(entity.getOperateOrgCode());
		Map<String, String> map = new HashMap<String, String>();
		// 货物单号
		map.put("waybillNo", entity.getWaybillNo());
		smsParamDto.setMap(map);
		try {
			sms = sMSTempleteService.querySmsByParam(smsParamDto);
		} catch (SMSTempleteException e) {//捕获异常
			LOGGER.error("短信内容为空", e);//记录日志
			throw new SignException(SignException.MESS_CONTENT_ISNULL, e);//短信内容为空
		}
		if (StringUtil.isBlank(sms)) {
			LOGGER.error("没有对应的短信模版");//记录日志
			throw new SignException(SignException.NO_SMS_TEMPLATES);//没有对应的短信模版
		}
		return sms;
	}
	
	/**
	 * 给dop推送到货信息   add by 243921
	 */
	private void arrivalGoodsToDop(String waybillNo,String specialValueAdded){
		if(StringUtils.isNotBlank(specialValueAdded) && specialValueAdded.contains(NotifyCustomerConstants.DELIVER_EQUIP)){
			ArrivalGoodsResponseDto resDto = null;
			int count = 1;
			//推送成功，则停止推送；推送失败，则继续推送，最多推送5次
			do{
				resDto = arrivalGoodsInformationDopService.arrivalGoodsInformation(waybillNo);
				count ++;
			}while((resDto == null || "0".equals(resDto.getHandleCode())) && count <= NumberConstants.NUMBER_5); 
		}
	}
	
	private String toNull2Str(String str) {
		if (null == str) {
			return "";
		}
		return str;
	}

	public void setActualFreightDao(IActualFreightDao actualFreightDao) {
		this.actualFreightDao = actualFreightDao;
	}
	
	
	public void setCalculateTransportPathService(
			ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}
	public void setLclDeliveryToCashManagementUnloadingService(
			ILclDeliveryToCashManagementUnloadingService lclDeliveryToCashManagementUnloadingService) {
		this.lclDeliveryToCashManagementUnloadingService = lclDeliveryToCashManagementUnloadingService;
	}
	public void setLclDeliveryToCashManagementDeliveryService(
			IlclDeliveryToCashManagementDeliveryService lclDeliveryToCashManagementDeliveryService) {
		this.lclDeliveryToCashManagementDeliveryService = lclDeliveryToCashManagementDeliveryService;
	}
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}

	
}