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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/action/NotifyCustomerAction.java
 * 
 * FILE NAME        	: NotifyCustomerAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.action;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IBeforeNoticeService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.ICustomerReceiptHabitService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.CustomerReceiptHabitEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyMultibillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.NotifyCustomerException;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.NotifyCustomerVo;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;


/**
 * 
 * 派送提前通知
 * @author 159231-foss-meiying
 * @date 2013-12-27 上午11:01:28
 */
public class BeforeNoticeAction extends AbstractAction {
	
	
	/**
	 * serial version Id
	 */
	private static final long serialVersionUID = -1760685994019086295L;
	/**
	 * 客户通知VO
	 */
	private NotifyCustomerVo vo = new NotifyCustomerVo();
	/**
	 *派送提前通知Service
	 */
	private IBeforeNoticeService beforeNoticeService;
	
	private IBillReceivableService billReceivableService;
	
	private IWaybillRfcService  waybillRfcService;
	
	//客户收货习惯
	private ICustomerReceiptHabitService customerReceiptHabitService;
	
   /**
    * 根据查询条件查询通知信息
    *  @author 159231-foss-meiying
    * @date 2013-12-27 上午11:26:35
    */
	@JSON
	public String queryBeforeNotices() {
		try {
			// 查询符合条件的运单总记录数
			Long count= this.beforeNoticeService.queryWaybillInfoCount(vo.getConditionDto());
			// 根据运单总记录数
			if (count != null && count>0) {
				this.setTotalCount(count);
				// 查询符合条件的记录列表
				vo.setDtoList(this.beforeNoticeService.queryWaybillInfoList(vo.getConditionDto(), this.getStart(), this.getLimit()));
			} else {
				vo.setDtoList(null);
				// 设置页面显示的记录总数
				this.setTotalCount(Long.valueOf(0));
			}
		} catch (BusinessException e) {
			// 有异常时，返回异常信息
			return super.returnError(e);
		}
		// 返回成功信息
		return super.returnSuccess();
	}

	/**
	 * 查询运单信息.
	 * 
	 * 界面中“到达件数”为库存件数，
	 * 当库存件数和开单件数不一致时，
	 * 使用粉色标识
	 * 
	 * 未通知、
	 * 开单库存件数不一致、
	 * 通知三天未提货颜色冲突时，
	 * 按未通知、
	 * 开单库存件数不一致、
	 * 通知三天未提货顺序优先级显示颜色
	 * 
	 * @return 
	 * 			the string
	 * @author 
	 * 			ibm-wangfei
	 * @date 
	 * 			Oct 11, 2012 5:25:33 PM
	 */
	@JSON
	public String queryBeforeNoticeWaybillInfo() {
		try {
			// 判断vo属性及vo的waybillno属性
			if (vo != null && vo.getConditionDto().getWaybillNo() != null) {
				// waybill_no存在的时候，进行运单详细信息查询
				NotifyCustomerConditionDto conditionDto = this.beforeNoticeService.queryWaybillInfo(vo.getConditionDto());
				
				//判断开单为到付, 是否已经网上支付-239284
				String payType = vo.getConditionDto().getNotifyCustomerDto().getPaidMethodVir();
				if (StringUtils.isNotBlank(payType) 
						&& SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT.equals(payType)) {
					String[] billTypes = new String[]{SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE};
					String wayBillNo =  vo.getConditionDto().getWaybillNo(); //"654321122".equals(vo.getConditionDto().getWaybillNo()) ? "":"AAA"; //
					String actualType = SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE;; //
					int count = billReceivableService.queryIsOrPayByBillNo(billTypes, wayBillNo, null, null, actualType);
					if (count > 0) {
						conditionDto.getNotifyCustomerDto().setIsOrPayStatus("网上支付（已付）");
					}
				}
				
				//客户收货习惯历史信息  
				CustomerReceiptHabitEntity customerReceiptHabitEntity = new CustomerReceiptHabitEntity();
				//设置查询条件-客户代码
				customerReceiptHabitEntity.setCustomerCode(vo.getConditionDto().getNotifyCustomerDto().getReceiveCustomerCode());
				//设置查询条件-客户名称
				customerReceiptHabitEntity.setCustomerName(vo.getConditionDto().getNotifyCustomerDto().getReceiveCustomerName());
				//查询条件-手机
				customerReceiptHabitEntity.setCustomerMobilePhone(vo.getConditionDto().getNotifyCustomerDto().getReceiveCustomerMobilephone());
				//查询条件-座机
				customerReceiptHabitEntity.setCustomerPhone(vo.getConditionDto().getNotifyCustomerDto().getReceiveCustomerPhone());
				//查询条件-客户联系人
				customerReceiptHabitEntity.setCustomerContactName(vo.getConditionDto().getNotifyCustomerDto().getReceiveCustomerContact());
				//查询条件-当前操作人代码
				customerReceiptHabitEntity.setOperateOrgCode(FossUserContext.getCurrentDeptCode());
				
				CustomerReceiptHabitEntity newCustEntity = this.customerReceiptHabitService.selectReceiptHabitByParam(customerReceiptHabitEntity);
				if (null != newCustEntity) {
					conditionDto.getNotificationEntity().setDeliveryTimeInterval(newCustEntity.getDeliveryTimeInterval());
					conditionDto.getNotificationEntity().setDeliveryTimeStart(newCustEntity.getDeliveryTimeStart());
					conditionDto.getNotificationEntity().setDeliveryTimeOver(newCustEntity.getDeliveryTimeOver());
					conditionDto.getNotificationEntity().setInvoiceType(newCustEntity.getInvoiceType());
					conditionDto.getNotificationEntity().setInvoiceDetail(newCustEntity.getInvoiceDetail());
					conditionDto.getNotificationEntity().setReceiptHabitRemark(newCustEntity.getReceiptHabitRemark());
				}
				
				// 查出的dto对象设置到vo.conditionDto
				vo.setConditionDto(conditionDto);
			}
		} catch (BusinessException e) {
			// 有异常时，返回异常信息
			return super.returnError(e);
		}
		// 返回成功信息
		return super.returnSuccess();
	}

	/**
	 * 
	 * 保存派送提前通知信息
	 *  @author 159231-foss-meiying
	 * @date 2013-12-27 上午11:03:57
	 */
	@JSON
	public String addBeforeNotice() {
		try {
			// 判断vo属性
			if (vo != null) {
				// 新增通知相关信息
				this.beforeNoticeService.addNotificationInfo(vo.getConditionDto());
			}
		} catch (BusinessException e) {
			// 有异常时，返回异常信息
			return super.returnError(e);
		}
		// 返回成功信息
		return super.returnSuccess();
	}
	/**
	 * 
	 * 查询不在库存的运单号
	 *  @author 159231-foss-meiying
	 * @date 2013-12-27 上午11:03:57
	 */
	@JSON
	public String queryNotInStockWaybillNos() {
		try {
			// 判断vo属性
			if (vo != null) {
				// 新增通知相关信息
				this.beforeNoticeService.queryNotInStockWaybillNos(vo.getConditionDto());
			}
		} catch (BusinessException e) {
			// 有异常时，返回异常信息
			return super.returnError(e);
		}
		// 返回成功信息
		return super.returnSuccess();
	}
	@JSON
	public String queryMultibillListByCustomer() {
		try {
			if(null==vo || null==vo.getConditionDto()){
				// 如果没有值则抛出异常
				throw new NotifyCustomerException("传入参数不能为空！");
			}
			/**
			 * 根据运单的基本信息，判断加载多票信息
			 *要求运单时间在10天内，且存在客户编码
			 */
			if( null != vo.getConditionDto().getNotifyCustomerDto().getBillTime()&& !StringUtils.isBlank( vo.getConditionDto().getNotifyCustomerDto().getReceiveCustomerCode())
				&& com.deppon.foss.util.DateUtils.getTimeDiff(vo.getConditionDto().getNotifyCustomerDto().getBillTime(), new Date()) <= NumberConstants.NUMBER_10 ){
				//存在客户编码的才进行查询
				List<NotifyMultibillDto> multibillList  = beforeNoticeService.getMultibillListByCustomer(vo.getConditionDto());
				vo.setMultibillList(multibillList);
			}
		} catch (BusinessException e) {
			// 有异常时，返回异常信息
			return super.returnError(e);
		}
		// 返回成功信息
		return super.returnSuccess();
	}
	/**
	 * 
	 * @Description	添加合送 
	 * @return  		
	 * @author	mujun
	 * @date 	2014-4-10 上午9:24:48 
	 * @version	1.0
	 */
	@JSON
	public String mergeNoticeWaybill(){
		try {
			if(null != vo.getConditionDto().getArrayWaybillNos()){
				//判断是否存在更改单
				List<String > rfcBills = waybillRfcService.isExsitsWayBillRfcs(Arrays.asList(vo.getConditionDto().getArrayWaybillNos()));
				if(null != rfcBills && rfcBills.size() > 0 ){
				 
					vo.getConditionDto().setArrayWaybillNos(rfcBills.toArray(new String[]{})) ;
					return returnSuccess();
				}
					
				beforeNoticeService.mergeNoticeWaybill(vo.getConditionDto().getArrayWaybillNos());
				vo.getConditionDto().setArrayWaybillNos(null);
			}
		} catch (BusinessException e) {
			// 有异常时，返回异常信息
			return super.returnError(e);
		}
		
		return super.returnSuccess();
	}
		
	/**
	 * 
	 * @Description	解绑合送 
	 * @return  		
	 * @author	mujun
	 * @date 	2014-4-10 上午9:24:57 
	 * @version	1.0
	 */
	@JSON
	public String relieveNoticeWaybill(){
		try {
			if(vo.getConditionDto().getArrayWaybillNos()!= null && vo.getConditionDto().getArrayWaybillNos().length>0){
				beforeNoticeService.relieveNoticeWaybill(vo.getConditionDto().getArrayWaybillNos());
			}
		} catch (BusinessException e) {
			// 有异常时，返回异常信息
			return super.returnError(e);
		}
		return super.returnSuccess();
	}
	/**
	 * 
	 * 批量保存派送提前通知信息
	 *  @author 159231-foss-meiying
	 * @date 2014-5-26 上午11:03:57
	 */
	@JSON
	public String beforeNoticeBatchNotify() {
		try {
			// 判断vo属性
			if (vo != null) {
				// 新增通知相关信息
				this.beforeNoticeService.batchNotify(vo.getConditionDto());
			}
		} catch (BusinessException e) {
			// 有异常时，返回异常信息
			return super.returnError(e);
		}
		// 返回成功信息
		return super.returnSuccess();
	}
	/**
	 * @return 
	 * 		the vo
	 */
	public NotifyCustomerVo getVo() {
		return vo;
	}

	/**
	 * @param vo 
	 * 		the vo to see
	 */
	public void setVo(NotifyCustomerVo vo) {
		this.vo = vo;
	}

	public void setBeforeNoticeService(IBeforeNoticeService beforeNoticeService) {
		this.beforeNoticeService = beforeNoticeService;
	}


	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}

	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	public void setCustomerReceiptHabitService(
			ICustomerReceiptHabitService customerReceiptHabitService) {
		this.customerReceiptHabitService = customerReceiptHabitService;
	}
	
}