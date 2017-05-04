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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactAddressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.CustomerException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyCustomerDao;
import com.deppon.foss.module.pickup.predeliver.api.server.process.IHandleQueryOutfieldService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IBeforeNoticeService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.ICustomerReceiptAddressService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IExceptionProcessService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ExceptionProcessConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.CustomerReceiptAddressEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionProcessDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.InvoiceInfomationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionProcessConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionProcessDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyMultibillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.ExceptionProcessException;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.NotifyCustomerException;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.sign.api.server.service.ISendWaybillTrackService;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillTrackDto;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.ICustomerBargainService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.dto.DebitDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.WaybillReceivableDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.trackings.api.shared.define.WaybillTrackingsConstants;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 
 * 派送提前通知Service
 * @author 159231-foss-meiying
 * @date 2014-1-1 上午10:53:30
 */
public class BeforeNoticeService implements IBeforeNoticeService {
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
	 * 组织信息 Service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 外场相关共通接口
	 */
	private IHandleQueryOutfieldService handleQueryOutfieldService;

	/**
	 * 应收单服务
	 */
	private IBillReceivableService billReceivableService;
	/**
	 * 业务互斥锁服务
	 */
	private IBusinessLockService businessLockService;
	/**
	 * 运单管理服务
	 */
	private IWaybillManagerService waybillManagerService;
	/**
	 * 合送
	 */
	private static final String TOGETHER = "合送";
	private IActualFreightDao actualFreightDao;
	
	/**
	 * 轨迹推送接口 
	 */
	private ISendWaybillTrackService sendWaybillTrackService;
	/**
	 * @param sendWaybillTrackService the sendWaybillTrackService to set
	 */
	public void setSendWaybillTrackService(
			ISendWaybillTrackService sendWaybillTrackService) {
		this.sendWaybillTrackService = sendWaybillTrackService;
	}
	/**
	 * 实际收货地址服务
	 */
	private ICustomerReceiptAddressService customerReceiptAddressService;
	
	private INotifyCustomerService notifyCustomerService;

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
		// 查询列表.
		List<NotifyCustomerDto> dtos = getNotifyCustomerDto(conditionDto, start, limit);
		// 判断查询结果是否存在
		if (CollectionUtils.isEmpty(dtos)) {
			// 不存在，返回null值
			return null;
		}
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
			// 按运单方式查询时，不修改到达件数
			if (String.valueOf(NotifyCustomerConstants.SELECT_TYPE_WAYBILLNO).equals(conditionDto.getSelectType())) {
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
		}
		// 返回查询结果
		return dtos;
	}
	/**
	 * 
	 * 查询通知信息
	 *  @author 159231-foss-meiying
	 * @date 2014-1-1 上午10:56:51
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService#queryWaybillInfoCount(com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto)
	 */
	@Override
	public Long queryWaybillInfoCount(NotifyCustomerConditionDto conditionDto) {
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
		// 对页面查询类型进行switch判断
		switch (queryType) {
		case NotifyCustomerConstants.SELECT_TYPE_WAYBILLNO:
			// 4.按运单 号查询
			return this.notifyCustomerDao.queryBeforeNoticeCountForWaybillNos(conditionDto);
		case NotifyCustomerConstants.SELECT_TYPE_HANDOVER:
			// 1.页面录入交接单号
			return this.notifyCustomerDao.queryBeforeNoticeCountForHandoverNo(conditionDto);
		case NotifyCustomerConstants.SELECT_TYPE_VEHICLEASSEMBLENO:
			// 2.页面录入车次、预计到达时间
			// 按车次、预计到达时间查询
			return this.notifyCustomerDao.queryBeforeNoticeCountForVehicleAssembleNo(conditionDto);
		case NotifyCustomerConstants.SELECT_TYPE_STOCK_ERROR:
			// 3.页面应该录入运单号，但没有录入，直接返回null
			return null;
		default:
			// 默认为库存查询
			return null;
		}
	}

	/**
	 * 新增通知相关信息.
	 *  @author 159231-foss-meiying
	 * @date 2014-3-5 下午5:17:14
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IBeforeNoticeService#addNotificationInfo(com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto)
	 */
	@Override
	public void addNotificationInfo(NotifyCustomerConditionDto conditionDto) {
		// 判断传入参数
		if (conditionDto == null ||conditionDto.getNotificationEntity()==null|| StringUtil.isBlank(conditionDto.getNotificationEntity().getWaybillNo())) {
			// 打印异常信息
			LOGGER.error("通知信息异常，传入参数为NULL");
			// 抛出异常
			throw new NotifyCustomerException("通知信息异常，传入参数为NULL");
		}
		
		//保存页面实际收货地址到数据库实际收货地址表
		CustomerReceiptAddressEntity addressEntity = new CustomerReceiptAddressEntity();
		if (null != conditionDto.getNotifyCustomerDto()) {
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
		// 添加运单通知信息
		this.addNotifyCustomer(conditionDto.getNotificationEntity(), conditionDto.getInvoiceInfomationEntity());
	}
	
	/**
	 * 比较开单地址与当前实际收货地址是否相等
	 * @param notificationEntity
	 */
	private boolean compareWayBill2ActualAddress(WaybillEntity  waybill, NotificationEntity notificationEntity) {
		//拼接开单地址信息
		String waybillAdress = toNull2Str(waybill.getReceiveCustomerProvCode())+ toNull2Str(waybill.getReceiveCustomerCityCode())+toNull2Str(waybill.getReceiveCustomerDistCode()) +
				toNull2Str(waybill.getReceiveCustomerAddress());

		ActualFreightEntity actualFreight = this.actualFreightDao.queryByWaybillNo(notificationEntity.getWaybillNo());
		waybillAdress  = waybillAdress + toNull2Str(actualFreight.getReceiveCustomerAddressNote());
		
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
	private String toNull2Str(String str) {
		if (null == str) {
			return "";
		}
		return str;
	}
	
	/**
	 * 批量通知.
	 * @param conditionDto
	 */
	@Transactional
	@Override
	public void batchNotify(NotifyCustomerConditionDto conditionDto) {
		// 判断传入参数
		if (conditionDto == null || CollectionUtils.isEmpty(conditionDto.getNotificationEntityList())) {
			LOGGER.info("传入参数为NULL");
			return;
		}
		// 对传入的运单列表进行批量通知
		for (NotificationEntity notificationEntity : conditionDto.getNotificationEntityList()) {
			
			CustomerReceiptAddressEntity addressEntity = new CustomerReceiptAddressEntity();
			if (null != notificationEntity && null != conditionDto.getNotifyCustomerDto()) {
				//保存页面实际收货地址到数据库实际收货地址表
				if (StringUtil.isNotBlank(notificationEntity.getReceiveCustomerCode())  && StringUtil.isNotBlank(conditionDto.getNotifyCustomerDto().getReceiveCustomerName())) {
					addressEntity.setCustomerCode(notificationEntity.getReceiveCustomerCode());
					addressEntity.setCustomerName(conditionDto.getNotifyCustomerDto().getReceiveCustomerName());
					addressEntity.setCustomerContactName(notificationEntity.getReceiveCustomerContact());
					addressEntity.setReceiveProvCode(notificationEntity.getActualProvCode());
					addressEntity.setReceiveCityCode(notificationEntity.getActualCityCode());
					addressEntity.setReceiveDistCode(notificationEntity.getActualDistrictCode());
					String addressNotes = (notificationEntity.getActualStreetn() == null ? "" : notificationEntity.getActualStreetn());
					addressEntity.setReceiveStreet(addressNotes);
					addressEntity.setReceiveAddressDetails(notificationEntity.getActualAddressDetail() );
					addressEntity.setCustomerMobilePhone(notificationEntity.getReceiveCustomerMobilephone());
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
				
				//运单当前页面的实际收货地址(可能是通知记录最新的实际收货地址， 也可能是开单地址)
				//如果当前页面的实际送货地址和开单地址一样，则通知保存实际送货地址为null； 否则保存新的实际收货地址(包括null)
				WaybillEntity  waybill =waybillManagerService.queryWaybillBasicByNo(notificationEntity.getWaybillNo());
				if (null != waybill) {
					boolean flag = compareWayBill2ActualAddress(waybill, notificationEntity);
					if (flag) {
						modifyActualAddress2Null(notificationEntity);
					} 
				}
				
			} else {
				//合送-批量通知, 无法带修改非当前通知运单的实际送货地址，因此保存为null
				modifyActualAddress2Null(notificationEntity);
			}
			
			// 添加运单通知信息.
			this.addBeforeNotifyCustomer(notificationEntity, conditionDto.getIsTogetherSend());
		}
	}
	private void addBeforeNotifyCustomer(NotificationEntity notificationEntity,String isTogetherSend) {
		
		if (notificationEntity == null) {
			throw new NotifyCustomerException("通知信息异常");
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
			WaybillEntity  waybill =waybillManagerService.queryWaybillBasicByNo(notificationEntity.getWaybillNo());
			if(waybill!=  null){
				if(waybill.getLastLoadOrgCode()!=null && waybill.getLastLoadOrgCode().equals(FossUserContextHelper.getOrgCode())){
					//不作处理
				}else {
					throw new NotifyCustomerException("目的站不是当前营业部，不能保存!");
				}
			}else {
				throw new NotifyCustomerException("查询运单信息失败");
			}
			notificationEntity.setReceiveCustomerCode(waybill.getReceiveCustomerCode());
			notificationEntity.setReceiveCustomerContact(waybill.getReceiveCustomerContact());
			notificationEntity.setReceiveMethod(DictUtil.rendererSubmitToDisplay(waybill.getReceiveMethod(), DictionaryConstants.PICKUP_GOODS));
			notificationEntity.setToPayAmount(waybill.getToPayAmount());
			// 对传入的通知信息实体进行实例化处理
			initNotificationEntity(notificationEntity);
			// 新增运单通知明细信息
			addNotificationEntity(notificationEntity);
			ActualFreightEntity actualFreight = this.actualFreightDao.queryByWaybillNo(notificationEntity.getWaybillNo());
			if(actualFreight != null){
				if(StringUtils.isNotBlank(actualFreight.getTogetherSendCode())&&FossConstants.NO.equals(isTogetherSend)){
					throw new NotifyCustomerException("保存失败，当前运单已经合送，请重新加载！");
				}else if(StringUtils.isBlank(actualFreight.getTogetherSendCode())&&FossConstants.YES.equals(isTogetherSend)){
					throw new NotifyCustomerException("保存失败，运单号："+notificationEntity.getWaybillNo()+"未合送，请重新加载！");
				}
			}
			// 通知成功
			if (NotifyCustomerConstants.SUCCESS.equals(notificationEntity.getNoticeResult())) {
				// 判断能否月结/欠款
				checkIsBeBebt(notificationEntity);
				// 电话通知并且通知成功时,新增到达联信息
				ArriveSheetEntity entity = initArriveSheetEntity(notificationEntity);
				// 根据运单号校验生成到达联
				arriveSheetManngerService.checkGenerateArriveSheet(entity);
				if(FossConstants.YES.equals(isTogetherSend)){
					entity.setTogetherSendCode(actualFreight.getTogetherSendCode());
				}
				// 更新到达联的通知客户录入信息
				arriveSheetManngerService.updateArriveSheetEntityByWaybillNo(entity);
			}else {
				if(FossConstants.YES.equals(isTogetherSend)){
					actualFreight.setTogetherSendCode(NotifyCustomerConstants.DEFAULT_GOODS_AREA_CODE);
					actualFreight.setTogetherSendMark(NotifyCustomerConstants.DEFAULT_GOODS_AREA_CODE);
					// 更新到达联的通知客户录入信息
					ArriveSheetDto dto =new ArriveSheetDto();
					String [] waybillNos={notificationEntity.getWaybillNo()};
					dto.setWaybillNos(waybillNos);
					dto.setTogetherSendCode(NotifyCustomerConstants.DEFAULT_GOODS_AREA_CODE);
					// 更新到达联的通知客户录入信息
					arriveSheetManngerService.updateTogetherSendCodeByWaybillNos(dto);
				}
			}
			//客户通知,电话通知成功 提前通知都是电话通知 --231438，快递100，轨迹推送需求RFOSS2015031706 
			WaybillTrackDto trackDto = new WaybillTrackDto();
			//当前用户登录信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			if(null == currentInfo){
				throw new NotifyCustomerException("登录信息为空！");
			}//运单号
			trackDto.setWaybillNo(notificationEntity.getWaybillNo());
			//登录信息
			trackDto.setCurrentInfo(currentInfo);
			//操作类型
			trackDto.setOperateType(WaybillTrackingsConstants.OPERATE_TYPE_NOTIFY);
			// 通知成功
			if(NotifyCustomerConstants.SUCCESS.equals(notificationEntity.getNoticeResult())) {
				//通知客户，操作结果描述
				trackDto.setOperateDesc("客户通知，电话通知成功");
			}else{//通知失败
				//通知客户，操作结果描述
				trackDto.setOperateDesc("客户通知，电话通知失败");
			}
			//调用轨迹接口，推送轨迹
//			sendWaybillTrackService.sendTrackings(trackDto);// --add by 231438
			//推送菜鸟轨迹--add by 339073
			sendWaybillTrackService.rookieTrackingByNotification(notificationEntity);
			// 根据通知结果，进行异常流程处理
			updateExceptionProcess(notificationEntity);
			//计算时间
			notificationEntity.setCashTime(notifyCustomerService.executeCashTime(notificationEntity.getWaybillNo()));
			// 更新运单关联表通知状态、最后通知时间、送货日期、付款方式,合送标识、合送编码
			updateActualFreightByWaybillNo(notificationEntity,actualFreight.getTogetherSendCode(),actualFreight.getTogetherSendMark());
			
		} catch (NotifyCustomerException e) {
			businessLockService.unlock(mutexElement);
			throw new NotifyCustomerException(e.getErrorCode());
		}
		businessLockService.unlock(mutexElement);
	}
	private void updateActualFreightByWaybillNo(NotificationEntity notificationEntity,String togetherSendCode,String togetherSendMark){
		ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
		// 运单号
		actualFreightEntity.setWaybillNo(notificationEntity.getWaybillNo());
		// 通知日期
		actualFreightEntity.setNotificationTime(notificationEntity.getOperateTime());
		// 要求送货日期
		actualFreightEntity.setDeliverDate(DateUtils.convert(notificationEntity.getDeliverDate(), DateUtils.DATE_FORMAT));

		//送货时间段，送货时间范围，发票类型
		actualFreightEntity.setDeliveryTimeInterval(notificationEntity.getDeliveryTimeInterval());
		actualFreightEntity.setDeliveryTimeStart(notificationEntity.getDeliveryTimeStart());
		actualFreightEntity.setDeliveryTimeOver(notificationEntity.getDeliveryTimeOver());
		actualFreightEntity.setInvoiceType(notificationEntity.getInvoiceType());
		
		// 通知结果
		actualFreightEntity.setNotificationResult(notificationEntity.getNoticeResult());
		// 付款方式
		actualFreightEntity.setPaymentType(notificationEntity.getPaymentType());
		// 如果是电话通知且通知成功，则设置曾经电话通知成功过
		if (NotifyCustomerConstants.NOTIFY_TYPE_TEL_NOTICE.equals(notificationEntity.getNoticeType()) && NotifyCustomerConstants.SUCCESS.equals(notificationEntity.getNoticeResult())) {
			actualFreightEntity.setEverTelnoticeSuccess(FossConstants.YES);
		}
		// 设置最后通知部门code
		actualFreightEntity.setNotificationOrgCode(notificationEntity.getOperateOrgCode());
		// 设置规定兑现时间
		actualFreightEntity.setCashTime(notificationEntity.getCashTime());
		actualFreightEntity.setTogetherSendCode(togetherSendCode);
		actualFreightEntity.setTogetherSendMark(togetherSendMark);
		// 根据运单编号，更新运单附属表的通知信息.
		this.updateActualFreightEntityByWaybillNo(actualFreightEntity);
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
	private void notifyCustomerDtoRelate(NotifyCustomerDto pageDto, NotifyCustomerDto dbDto) {
		if (pageDto != null && dbDto != null) {
			// 到达件数
			dbDto.setArriveGoodsQty(pageDto.getArriveGoodsQty());
			// 页面传入的查询方式
			dbDto.setSelectType(pageDto.getSelectType());
			// 车辆状态
			dbDto.setTaskStatus(pageDto.getTaskStatus());
		}
	}
	/**
	 *  获取客户相关信息.
	 *  @author 159231-foss-meiying
	 * @date 2014-2-18 上午11:17:48
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
	 * 通过运单编号查询运单通知记录.
	 *  @author 159231-foss-meiying
	 * @date 2014-2-18 上午11:19:11
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IBeforeNoticeService#queryWaybillInfo(com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto)
	 */
	@Override
	@Transactional(readOnly = true)
	public NotifyCustomerConditionDto queryWaybillInfo(NotifyCustomerConditionDto conditionDto) {
		// 打印查询参数
		LOGGER.info("通过运单编号查询运单通知记录" + ReflectionToStringBuilder.toString(conditionDto));
		// 添加库存外场、库区默认查询条件
		List<String> list = handleQueryOutfieldService.getEndStockCodeAndAreaCode(FossUserContextHelper.getOrgCode());
		if (CollectionUtils.isNotEmpty(list)) {
			conditionDto.setEndStockOrgCode(list.get(0));
			conditionDto.setGoodsAreaCode(list.get(1));
		}
		// 获取运单明细信息
		NotifyCustomerDto notifyCustomerDto = this.queryBeforeNoticeDetailByWaybillNo(conditionDto);
		// 将列表中的值直接设置	到查询出来出来的dto上
		// 已减少数据库多重关联查询.
		notifyCustomerDtoRelate(conditionDto.getNotifyCustomerDto(), notifyCustomerDto);
		if(null != notifyCustomerDto){
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
					notifyCustomerDto.getReceiveCustomerAddress(), notifyCustomerDto.getReceiveCustomerAddressNote()));
			
			//取最新一条通知记录的实际收货地址，如果通知记录没有则读取开单地址
			NotificationEntity notificationEntity = new NotificationEntity();
			notificationEntity.setWaybillNo(conditionDto.getWaybillNo());
			notificationEntity.setModuleName(NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY);
			notificationEntity.setOperateOrgCode(FossUserContextHelper.getOrgCode());
			NotificationEntity notify1 =notifyCustomerService.queryLastNotifyByWaybillNo(notificationEntity);
			//设置根据运单带出显示在通知界面的实际收货地址省-市-区， 街道(备注)， 详细地址
			NotificationEntity noEntity = new NotificationEntity();
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
					noEntity.setActualProvN(actualProvN);
					String actualCityN = handleQueryOutfieldService.getCompleteAddress(null, notify1.getActualCityCode(), null, "");
					noEntity.setActualCityN(actualCityN);
					String actualDistN = handleQueryOutfieldService.getCompleteAddress(null, null, notify1.getActualDistrictCode(), "");
					noEntity.setActualDistrictN(actualDistN);
					
					noEntity.setActualStreetn(notify1.getActualStreetn()==null?null:notify1.getActualStreetn());
					noEntity.setActualAddressDetail(notify1.getActualAddressDetail()==null?null:notify1.getActualAddressDetail());
				} else {
					String actualProvN = handleQueryOutfieldService.getCompleteAddress(notifyCustomerDto.getReceiveCustomerProvCode(), null, null, "");
					noEntity.setActualProvN(actualProvN);
					String actualCityN = handleQueryOutfieldService.getCompleteAddress(null, notifyCustomerDto.getReceiveCustomerCityCode(), null, "");
					noEntity.setActualCityN(actualCityN);
					String actualDistN = handleQueryOutfieldService.getCompleteAddress(null, null, notifyCustomerDto.getReceiveCustomerDistCode(), "");
					noEntity.setActualDistrictN(actualDistN);
					//设置收货地址详细信息
					noEntity.setActualStreetn(notifyCustomerDto.getReceiveCustomerAddressNote());
					//设置收货地址
					noEntity.setActualAddressDetail(acutalAdressVirtual);
				}
				//设置会展货
				noEntity.setIsExhibition(notify1.getIsExhibition());
				//设置空车出
				noEntity.setIsEmptyCar(notify1.getIsEmptyCar());
				//设置送货时间
				noEntity.setDeliverDate(notify1.getDeliverDate());
			} else {
				String actualProvN = handleQueryOutfieldService.getCompleteAddress(notifyCustomerDto.getReceiveCustomerProvCode(), null, null, "");
				noEntity.setActualProvN(actualProvN);
				String actualCityN = handleQueryOutfieldService.getCompleteAddress(null, notifyCustomerDto.getReceiveCustomerCityCode(), null, "");
				noEntity.setActualCityN(actualCityN);
				String actualDistN = handleQueryOutfieldService.getCompleteAddress(null, null, notifyCustomerDto.getReceiveCustomerDistCode(), "");
				noEntity.setActualDistrictN(actualDistN);
				//设置收货地址详细信息
				noEntity.setActualStreetn(notifyCustomerDto.getReceiveCustomerAddressNote());
				//设置收货地址
				noEntity.setActualAddressDetail(acutalAdressVirtual);
			}
			conditionDto.setNotificationEntity(noEntity);
		}		
		
		// 设置 运单通知信息.
		conditionDto.setNotifyCustomerDto(notifyCustomerDto);
		// 设置默认的要求送货日期 送货时间默认当前时间；16：00以通知的，默认第二天早上凌晨0点 BUG-31972
		setDefaultDeliverDate(conditionDto);
		
		//取最新的送货要求
		String deliverRequire = notifyCustomerDao.queryDeliverRequire(conditionDto.getWaybillNo());
		conditionDto.getNotifyCustomerDto().setDeliverRequire(deliverRequire);
		 
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
		MutexElement mutexElement = new MutexElement(notificationEntity.getWaybillNo(), "运单号", MutexElementType.CUSTOMER_NOTIFY);
		//互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement, NumberConstants.ZERO);
		//若未上锁
		if (!isLocked) {
			//抛出派送单异常
			throw new NotifyCustomerException(NotifyCustomerException.NOTIFY_UNDERWAY);
		}
		try {
			WaybillEntity  waybill =waybillManagerService.queryWaybillBasicByNo(notificationEntity.getWaybillNo());
			if(waybill!=  null){
				if(waybill.getLastLoadOrgCode()!=null && waybill.getLastLoadOrgCode().equals(FossUserContextHelper.getOrgCode())){
					//不作处理
				}else {
					throw new NotifyCustomerException("目的站不是当前营业部，不能保存!");
				}
			}else {
				throw new NotifyCustomerException("查询运单信息失败");
			}
			notificationEntity.setReceiveCustomerCode(waybill.getReceiveCustomerCode());
			// 对传入的通知信息实体进行实例化处理
			initNotificationEntity(notificationEntity);
			// 新增运单通知明细信息
			addNotificationEntity(notificationEntity);
			
			//客户通知,电话通知成功 提前通知都是电话通知 --231438，快递100，轨迹推送需求RFOSS2015031706
			WaybillTrackDto trackDto = new WaybillTrackDto();
			//当前用户登录信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			if(null == currentInfo){
				throw new NotifyCustomerException("登录信息为空！");
			}//运单号
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
				
				//通知客户，操作结果描述
				trackDto.setOperateDesc("客户通知，电话通知成功");
			} else {//客户通知,短信通知失败 --add by 231438
				//通知客户，操作结果描述
				trackDto.setOperateDesc("客户通知，电话通知失败");
			}
			//调用轨迹接口，推送轨迹 --add by 231438
			sendWaybillTrackService.sendTrackings(trackDto); 
			
			// 根据通知结果，进行异常流程处理
			updateExceptionProcess(notificationEntity);
			//DP-FOSS-零担-派送兑现时效显示功能优化需求DN201606230002  by370196
			notificationEntity.setCashTime(notifyCustomerService.executeCashTime(notificationEntity.getWaybillNo()));
			LOGGER.info("规定兑现时间和运单号：" + notificationEntity.getCashTime()+":"+notificationEntity.getWaybillNo());
			// 更新运单关联表通知状态、最后通知时间、送货日期、付款方式
			updateActualFreightEntityByWaybillNo(notificationEntity);
		} catch (NotifyCustomerException e) {
			businessLockService.unlock(mutexElement);
			throw new NotifyCustomerException(e.getErrorCode());
		}
		businessLockService.unlock(mutexElement);
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

		//送货时间段，送货时间范围，发票类型
		actualFreightEntity.setDeliveryTimeInterval(notificationEntity.getDeliveryTimeInterval());
		actualFreightEntity.setDeliveryTimeStart(notificationEntity.getDeliveryTimeStart());
		actualFreightEntity.setDeliveryTimeOver(notificationEntity.getDeliveryTimeOver());
		actualFreightEntity.setInvoiceType(notificationEntity.getInvoiceType());
		
		// 通知结果
		actualFreightEntity.setNotificationResult(notificationEntity.getNoticeResult());
		// 付款方式
		actualFreightEntity.setPaymentType(notificationEntity.getPaymentType());
		// 如果是电话通知且通知成功，则设置曾经电话通知成功过
		if (NotifyCustomerConstants.NOTIFY_TYPE_TEL_NOTICE.equals(notificationEntity.getNoticeType()) && NotifyCustomerConstants.SUCCESS.equals(notificationEntity.getNoticeResult())) {
			actualFreightEntity.setEverTelnoticeSuccess(FossConstants.YES);
		}
		// 设置最后通知部门code
		actualFreightEntity.setNotificationOrgCode(notificationEntity.getOperateOrgCode());
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
			//exceptionProcessConditionDto.setNoticeContext(notificationEntity.getNoticeContent());
			
			LOGGER.info("派送提前通知--送货要求"+notificationEntity.getDeliverRequire());
			// modify by foss-sunyanfei 2015-7-30 
			//将“送货要求”放入“通知内容”字段
			exceptionProcessConditionDto.setNoticeContext(notificationEntity.getDeliverRequire());
			// modify by foss-sunyanfei 2015-7-30
			
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
						
						//通知成功且异常-异常详细里的异常原因下拉框异常原因
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
				//只有通知成功无异常
				if (NotifyCustomerConstants.SUCCESS.equals(notificationEntity.getNoticeResult()) && FossConstants.NO.equals(notificationEntity.getIsException())) {
					// 如果通知成功,更新异常主表状态为已处理
					exceptionEntity.setStatus(ExceptionProcessConstants.HANDLED);
					exceptionEntity.setExceptionTime(new Date());
					exceptionProcessService.updateExceptionProcessInfo(exceptionEntity);
					
				} else if (NotifyCustomerConstants.FAILURE.equals(notificationEntity.getNoticeResult()) ||(
						NotifyCustomerConstants.SUCCESS.equals(notificationEntity.getNoticeResult()) && FossConstants.YES.equals(notificationEntity.getIsException())  )) {
					
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
					
					LOGGER.info("派送提前通知--送货要求--更新"+exceptionProcessConditionDto.getNoticeContext());
					// add by foss-sunyanfei 2015-7-30
					exceptionEntity.setNoticeContext(exceptionProcessConditionDto.getNoticeContext());//通知内容
					// add by foss-sunyanfei 2015-7-30
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
			}  else {
				// 这里不做处理
				LOGGER.info("不做处理");
			}
		} catch (ExceptionProcessException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	/**
	 * 查询客户通知运单明细信息.
	 *  @author 159231-foss-meiying
	 * @date 2014-2-20 下午2:21:07
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IBeforeNoticeService#queryBeforeNoticeDetailByWaybillNo(com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto)
	 */
	@Override
	public NotifyCustomerDto queryBeforeNoticeDetailByWaybillNo(NotifyCustomerConditionDto conditionDto) {
		// 设置active状态
		conditionDto.setActive(FossConstants.ACTIVE);
		// 查询运单通知明细
		return notifyCustomerDao.queryBeforeNoticeDetailByWaybillNo(conditionDto);
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
		// 付款方式
		if (inputEntity.getPaymentType() == null) {
			inputEntity.setPaymentType("");
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
		inputEntity.setNoticeType(NotifyCustomerConstants.NOTIFY_TYPE_TEL_NOTICE);
		// 收货人
		inputEntity.setConsignee(inputEntity.getReceiveCustomerContact());
		// 联系方式
		inputEntity.setMobile(inputEntity.getReceiveCustomerMobilephone());
		if (NotifyCustomerConstants.FAILURE.equals(inputEntity.getNoticeResult())) {
			inputEntity.setNoticeContent(NotifyCustomerConstants.NOTICE_CONTENT_FAILURE);
			// 错误信息    modify by foss-sunyanfei 2015-8-6
			inputEntity.setExceptionNotes(inputEntity.getDeliverRequire());
			// 通知失败时，付款方式设置为空，即数据库不更新
			inputEntity.setPaymentType("");
		} else if (NotifyCustomerConstants.SUCCESS.equals(inputEntity.getNoticeResult()) && FossConstants.YES.equals(inputEntity.getIsException())) {
			inputEntity.setNoticeContent(NotifyCustomerConstants.NOTICE_CONTENT_SUCCESS);
			// 错误信息   modify by foss-sunyanfei 2015-8-6
			inputEntity.setExceptionNotes(inputEntity.getDeliverRequire());
		} else {
			inputEntity.setNoticeContent(NotifyCustomerConstants.NOTICE_CONTENT_SUCCESS);//通知成功
			inputEntity.setExceptionNotes("");
		}
		// 提货方式
		inputEntity.setDeliverType(inputEntity.getReceiveMethod());
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
		switch (queryType) {
		case NotifyCustomerConstants.SELECT_TYPE_WAYBILLNO:
			// 0. 默认查询到达本地以及本地库存的运单
			return (List<NotifyCustomerDto>) this.notifyCustomerDao.queryBeforeNoticeListForWaybillNos(conditionDto, start, limit);
		case NotifyCustomerConstants.SELECT_TYPE_HANDOVER:
			// 1.按交接单号查询
			return (List<NotifyCustomerDto>) this.notifyCustomerDao.queryBeforeNoticeListForHandoverNo(conditionDto, start, limit);
		case NotifyCustomerConstants.SELECT_TYPE_VEHICLEASSEMBLENO:
			// 2.按车次、预计到达时间查询
			return (List<NotifyCustomerDto>) this.notifyCustomerDao.queryBeforeNoticeListForVehicleAssembleNo(conditionDto, start, limit);
		default:
			return null;
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
		notifyCustomerConditionDto.setReceiveMethod(ArriveSheetConstants.SIGN_RECEIVE_METHOD_DELIVER);
		List<String> afStatus = new ArrayList<String>();  //运单的状态--已中止已作废
		afStatus.add(WaybillConstants.ABORTED);
		afStatus.add(WaybillConstants.OBSOLETE); 
		// 关联已生成、派送中的到达联
		List<String> arriveSheetStatus = new ArrayList<String>();
		arriveSheetStatus.add(ArriveSheetConstants.STATUS_GENERATE);
		arriveSheetStatus.add(ArriveSheetConstants.STATUS_DELIVER);
		notifyCustomerConditionDto.setArrStatus(arriveSheetStatus);
		notifyCustomerConditionDto.setAfStatus(afStatus);
		// 查询中变更状态 -- 已受理
		List<String> wbrStatus = new ArrayList<String>();
		// 起草
		wbrStatus.add(WaybillRfcConstants.PRE_AUDIT);
		// 待受理、审核同意
		wbrStatus.add(WaybillRfcConstants.PRE_ACCECPT);
		notifyCustomerConditionDto.setWbrStatus(wbrStatus);
		// 当前日期
		notifyCustomerConditionDto.setDeliverDateTmp(new Date());
		// 默认日期
		notifyCustomerConditionDto.setDeliverDateDef(DateUtils.getEndDatetime(new Date()));
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
		// 默认运单不等于空运、偏线、快递   
		String productCodes[] = { ProductEntityConstants.PRICING_PRODUCT_ROUND_COUPON_PACKAGE,ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT, ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE,ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE,ProductEntityConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE,ProductEntityConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT};
		
		notifyCustomerConditionDto.setProductCodes(productCodes);
		//使用是否快递字段来过滤快递的运单  排除后续快递产品增加的影响  add  by  yangkang
		//notifyCustomerConditionDto.setIsExpress(FossConstants.YES);
		
		// 默认运单版本
		notifyCustomerConditionDto.setActive(FossConstants.ACTIVE);
		// 添加库存外场、库区默认查询条件
		List<String> list = handleQueryOutfieldService.getEndStockCodeAndAreaCode(currOrgCode);
		if (CollectionUtils.isNotEmpty(list)) {
			notifyCustomerConditionDto.setEndStockOrgCode(list.get(0));
			notifyCustomerConditionDto.setGoodsAreaCode(list.get(1));
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
		//收集行政区域列表-239284
		if (StringUtil.isNotBlank(conditionDto.getDeliverDistCode())) {
			conditionDto.setDeliverDistCodes(conditionDto.getDeliverDistCode().replace(" ", "").split(","));
		}
		//收集运输性质列表-239284
		if (StringUtil.isNotBlank(conditionDto.getProductCode())) {
			conditionDto.setProductCodeCons(conditionDto.getProductCode().replace(" ", "").split(","));
		}
		//收集派送方式列表-239284
		if (StringUtil.isNotBlank(conditionDto.getReceiveMethodCon())) {
			conditionDto.setReceiveMethodCons(conditionDto.getReceiveMethodCon().replace(" ", "").split(","));
		} else {
			//默认的提货方式为送货
			conditionDto.setReceiveMethodTmp(ArriveSheetConstants.SIGN_RECEIVE_METHOD_DELIVER);
		}
		//收集车次号列表-239284
		if (StringUtil.isNotBlank(conditionDto.getVehicleAssembleNo())) {
			conditionDto.setVehicleAssembleNos(conditionDto.getVehicleAssembleNo().split("\\n"));
		}
		if (StringUtil.isNotBlank(conditionDto.getWaybillNo())) {
			// 根据运单编号查询 忽略其他全部
			conditionDto.setProductCode(null);
			conditionDto.setProductCodeCons(null);
			conditionDto.setDeliverProv(null);
			conditionDto.setDeliverCity(null);
			conditionDto.setDeliverDistCodes(null);
			conditionDto.setReceiveMethodCons(null);
			conditionDto.setReceiveMethodTmp(null);
			conditionDto.setSelectType(String.valueOf(NotifyCustomerConstants.SELECT_TYPE_WAYBILLNO));
			// 解析运单号为列表
			conditionDto.setArrayWaybillNos(conditionDto.getWaybillNo().split("\\n"));
			if (conditionDto.getArrayWaybillNos().length == 0) {
				return NotifyCustomerConstants.SELECT_TYPE_STOCK_ERROR;
			}
			conditionDto.setIsQueryDeliveyDate(FossConstants.YES);
			return NotifyCustomerConstants.SELECT_TYPE_WAYBILLNO;
		}
		if (StringUtil.isNotBlank(conditionDto.getHandoverNo())) {
			// 根据交接单编号查询 
			conditionDto.setSelectType(String.valueOf(NotifyCustomerConstants.SELECT_TYPE_HANDOVER));
			conditionDto.setPlanArriveTimeFrom(null);
			conditionDto.setPlanArriveTimeTo(null);
			conditionDto.setProductCodeCons(null);
			conditionDto.setReceiveMethodCons(null);
			conditionDto.setVehicleAssembleNos(null);
			conditionDto.setIsQueryDeliveyDate(FossConstants.YES);
			return NotifyCustomerConstants.SELECT_TYPE_HANDOVER;
		}
		if (StringUtil.isNotBlank(conditionDto.getVehicleAssembleNo())) {
			// 根据车次号查询
			conditionDto.setSelectType(String.valueOf(NotifyCustomerConstants.SELECT_TYPE_HANDOVER));
			return NotifyCustomerConstants.SELECT_TYPE_VEHICLEASSEMBLENO;
		}
		if (StringUtil.isNotBlank(conditionDto.getPlanArriveTimeFrom()) || StringUtil.isNotEmpty(conditionDto.getPlanArriveTimeTo())) {
			// 预计到达时间查询，同交接单查询
			conditionDto.setSelectType(String.valueOf(NotifyCustomerConstants.SELECT_TYPE_HANDOVER));
			return NotifyCustomerConstants.SELECT_TYPE_HANDOVER;
		}
		//需求要求  车次号可跟预计到达时间可以组合查询  
		conditionDto.setSelectType(String.valueOf(NotifyCustomerConstants.SELECT_TYPE_HANDOVER));
		return NotifyCustomerConstants.SELECT_TYPE_VEHICLEASSEMBLENO;
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
	 * 查询不在库存的运单号
	 *  @author 159231-foss-meiying
	 * @date 2014-2-27 上午9:16:23
	 */
	public NotifyCustomerConditionDto queryNotInStockWaybillNos(NotifyCustomerConditionDto dto){
		NotifyCustomerConditionDto  conditionDto = new NotifyCustomerConditionDto();
		if(dto!=null &&dto.getArrayWaybillNos().length>0){
			// 添加库存外场、库区默认查询条件
			List<String> list = handleQueryOutfieldService.getEndStockCodeAndAreaCode(FossUserContextHelper.getOrgCode());
			if (CollectionUtils.isNotEmpty(list)) {
				dto.setEndStockOrgCode(list.get(0));
				dto.setGoodsAreaCode(list.get(1));
			}
			List<String> waybillNos =notifyCustomerDao.queryNoWaybillStockByNos(dto);
			if(CollectionUtils.isNotEmpty(waybillNos) && waybillNos.size()>0){
				String[] w = new String[waybillNos.size()];
				int i =0;
				for (String string : waybillNos) {
					w[i]=string;
					i++;
				}
				conditionDto.setArrayWaybillNos(w);
			} 
			return conditionDto;
		}else {
			throw new NotifyCustomerException("传入的运单号为空！");
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
	 * @param orgAdministrativeInfoService the orgAdministrativeInfoService to
	 *            see
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
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
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	/** 
	 * @Description		 
	 * @param bills
	 * @return			
	 * @author			mujun	
	 * @date 			2014-4-10 上午9:45:44 
	 * @version			1.0
	 */
	
	@Override
	@Transactional
	public int mergeNoticeWaybill(String[] bills) {
		Map<String,String> mp=null;
		String mark = (int)(Math.random() * NumberConstants.NUMBER_100)+""+System.currentTimeMillis();
		int result = 0;
		for(int i=0;i<bills.length;i++){
			mp = new HashMap<String,String>();
			mp.put("bill", bills[i]);
			mp.put("mark",mark);
			mp.put("code", TOGETHER+bills.length+"-"+(i+1));
			if(notifyCustomerDao.mergeNoticeWaybill( mp)<=0){
				throw new NotifyCustomerException("运单号:"+bills[i]+"已经合送!不能重复合送,请重新加载一下多票信息");
			}
		}
		return result;
	}
	/** 
	 * @Description		
	 * @param code
	 * @return			
	 * @author			mujun	
	 * @date 			2014-4-10 上午9:45:44 
	 * @version			1.0
	 */
	
	@Override
	@Transactional
	public int relieveNoticeWaybill(String[] bills) {
		int result =notifyCustomerDao.relieveNoticeWaybill(bills);
		if(result==bills.length){
			ArriveSheetDto dto =new ArriveSheetDto();
			dto.setWaybillNos(bills);
			dto.setTogetherSendCode(NotifyCustomerConstants.DEFAULT_GOODS_AREA_CODE);
			// 更新到达联的通知客户录入信息
			arriveSheetManngerService.updateTogetherSendCodeByWaybillNos(dto);
			return result;
		}else {
			throw new NotifyCustomerException("解绑失败,存在运单已经解绑,请重新加载一下多票信息");
		}
		
	}
	/** 
	 * @param CustomerCode
	 * @return			
	 * @author			mujun	
	 * @date 			2014-4-10 下午3:24:20 
	 * @version			1.0
	 */
	@Override
	public List<NotifyMultibillDto> getMultibillListByCustomer(
			NotifyCustomerConditionDto notifyCustomerConditionDto) {
		//没有客户编码，直接返回null;
		if(StringUtils.isBlank( notifyCustomerConditionDto.getNotifyCustomerDto().getReceiveCustomerCode())){
			return null;
		}
		
		initNotifyCustomerQuery(notifyCustomerConditionDto);  //设置查询条件
		notifyCustomerConditionDto.setProductCodes(null);
		// 默认运单等于整车、精准汽运(短途)、精准城运、精准卡航、精准汽运(长途)
		String productCodes[] = { 
				ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE, 
				ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT,
				ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT,
				ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT,
				ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT };
		notifyCustomerConditionDto.setProductCodesContain(productCodes);
		List<NotifyMultibillDto> r =  notifyCustomerDao.getMultibillListByCustomer(notifyCustomerConditionDto);
		if(CollectionUtils.isNotEmpty(r) &&r.size() >1){
			for(NotifyMultibillDto dto :r){
				dto.setReceiveCustomerAddress(
						handleQueryOutfieldService.getCompleteAddressAttachAddrNote(dto.getReceiveCustomerProvCode(), dto.getReceiveCustomerCityCode(), dto.getReceiveCustomerDistCode(),
					dto.getReceiveCustomerAddress(), dto.getReceiveCustomerAddressNote()));
			}
		}else {
			return null;
		}
		return r;
	}
	
	public void setActualFreightDao(IActualFreightDao actualFreightDao) {
		this.actualFreightDao = actualFreightDao;
	}
	public void setCustomerReceiptAddressService(
			ICustomerReceiptAddressService customerReceiptAddressService) {
		this.customerReceiptAddressService = customerReceiptAddressService;
	}
	public void setNotifyCustomerService(
			INotifyCustomerService notifyCustomerService) {
		this.notifyCustomerService = notifyCustomerService;
	}
	
}