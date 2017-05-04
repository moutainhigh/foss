/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-consumer
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/server/service/impl/LaborfeeTakeEffectService.java
 * 
 * FILE NAME        	: LaborfeeTakeEffectService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.ws.Holder;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.crm._interface.crmservice.CommException;
import com.deppon.crm._interface.crmservice.FossToCrmService;
import com.deppon.esb.header.ESBHeader;
import com.deppon.foss.esb.crm.client.BackFreightCheckRequest;
import com.deppon.foss.esb.crm.client.BackFreightCheckResponse;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.settlement.common.api.server.service.IBillBadAccountService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IBillPayableTakeEffectDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.ILaborfeeTakeEffectService;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 生效装卸费Service实现
 * 
 * @author foss-zhangxiaohui
 * @date Dec 4, 2012 4:07:06 PM
 */
public class LaborfeeTakeEffectService implements ILaborfeeTakeEffectService {

	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(LaborfeeTakeEffectService.class);
	/**
	 * 
	 */
	
	//private static final PricingConstants pricingConstants=null;
	/**
	 * 应付单实体Service
	 */
	private IBillPayableService billPayableService;

	/**
	 * 生效装卸费Dao
	 */
	private IBillPayableTakeEffectDao billPayableTakeEffectDao;

	/**
	 * 调用CRM接口Service
	 */
	private FossToCrmService fossToCrmService;

	/**
	 * 调用坏账单查询的服务
	 */
	private IBillBadAccountService billBadAccountService;

	/**
	 * 应收单服务
	 */
	private IBillReceivableService billReceivableService;

	/**
	 * 生效装卸费Service实现--先验证再生效装卸费
	 * 
	 * @author foss-zhangxiaohui
	 * @date Dec 14, 2012 1:38:17 PM
	 */
	@Override
	public void process() {

		// 获取符合条件的数据
		List<BillPayableEntity> list = this.queryBillPayableLaborFee();
		
		
		

		// 校验List非空
		if (CollectionUtils.isEmpty(list)) {
			return;
		}

		// 按十个一组来分割子List
		List<List<BillPayableEntity>> splitList = CollectionUtils
				.splitListBySize(list, SettlementReportNumber.TEN);

		// 遍历分割出来的List的集合
		for (List<BillPayableEntity> tempList : splitList) {
			try {
				// 声明需要生效装卸费的应付单实体
				List<BillPayableEntity> success = new ArrayList<BillPayableEntity>();

				// 校验坏账，查询坏账服务
				for (int i = 0; i < tempList.size(); i++) {

					String waybillNo = tempList.get(i).getWaybillNo();

					// 检查退运费
					// 根据接口需求说明书，CRM backFreightCheck为true代表已经有退运费申请装卸费不可以生效
					if (hasBackFreightApply(waybillNo)) {
						// 打印log
						LOGGER.debug(waybillNo + "已经有退运费申请不能生效装卸费");
						// 结束本次循环，继续下次循环
						continue;
					}
					// 如果坏账表里面有记录返回false，所有只有查询坏账表返回true才能继续添加
					if (hasBillBadAccount(waybillNo)) {
						// 打印log
						LOGGER.debug(waybillNo + "已经有坏账申请");
						// 结束本次循环，继续下次循环
						continue;
					}					
					
					if (hasUnVerifyAmount(waybillNo,tempList.get(i).getProductCode())) {
						// 打印log
						LOGGER.debug(waybillNo + "存在未核销的运费");
						// 结束本次循环，继续下次循环
						continue;
					}
					else {
						// 没有退运费也没有坏账则添加到success的List
						success.add(tempList.get(i));
					}
				}
				// Service执行开始打印日志
				LOGGER.debug("Entering service, 开始生效装卸费");
				// 非空判断，不为空再做更新
				if (CollectionUtils.isNotEmpty(success)) {
					// 生效符合条件的应付单实体
					this.updatePayableBillLaborFeeStatus(success);
					// Service执行开始打印日志
				}
				// Service执行开始打印日志
				LOGGER.debug("Exiting service, 结束生效装卸费服务");
			}
			// 捕获异常
			catch (Exception e) {
				// 打印Log信息
				LOGGER.error("生效装卸费" + e.getMessage(), e);
			}
		}
	}
		
	/**
	 * 
	 * 判断非空运、非偏线是否存在未核销的运费应收单
	 * 
	 * @Title: hasUnVerifyAmount
	 * @author 046644-foss-zengbinwen
	 * @date 2013-4-10 下午4:25:00
	 * @param @param waybillNo
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	private boolean hasUnVerifyAmount(String waybillNo,String  productCode) {

		// 查询出应收单
		BillReceivableConditionDto dto = new BillReceivableConditionDto();
		dto.setActive(FossConstants.YES);
		
		//如果是偏线或者是空运,则只统计始发，否则需要统计始发和到达		
		if(productCode.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE)||
		   productCode.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT)){
			dto.setBillTypes(new String[] {
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,// 始发
			});
		}
		else
		{
			dto.setBillTypes(new String[] {
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,// 始发
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,// 到达应收
			});
		}
				
		dto.setIsRedBack(FossConstants.NO);
		dto.setVerify(null);//???
		dto.setWaybillNo(waybillNo);

		// 调用接口查询
		List<BillReceivableEntity> queryList = billReceivableService
				.queryBillReceivableByCondition(dto);

		// 如果未核销金额等于0，返回FALSE
		for (BillReceivableEntity entity : queryList) {
			if (BigDecimal.ZERO.compareTo(entity.getUnverifyAmount()) < 0) {
				return true;
			}
		}
		// 返回FALSE
		return false;
	}

	/**
	 * @throws CommException
	 * 
	 *             判断是否有退运费申请
	 * @Title: isBackFreightApply
	 * @author 046644-foss-zengbinwen
	 * @date 2013-4-10 下午3:45:03
	 * @param @param waybillNo
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	private boolean hasBackFreightApply(String waybillNo) throws CommException {
		// webservice调用CRM系统检查退运费
		ESBHeader header = new ESBHeader();
		// 服务类型为校验退运费
		header.setEsbServiceCode(SettlementESBDictionaryConstants.ESB_FOSS2ESB_BACK_FREIGHT_CHECK);
		// 与业务相关的字段
		header.setBusinessId(UUIDUtils.getUUID());
		header.setExchangePattern(SettlementESBDictionaryConstants.ESB_HEADER__EXCHANGE_PATTERN);
		header.setVersion(SettlementESBDictionaryConstants.ESB_HEADER__VERSION);
		// 消息格式
		header.setMessageFormat(SettlementESBDictionaryConstants.ESB_HEADER__MESSAGE_FORMAT);
		header.setRequestId(UUIDUtils.getUUID());
		// 请求系统
		header.setSourceSystem(SettlementESBDictionaryConstants.ESB_HEADER__SOURCE_SYSTEM);

		Holder<ESBHeader> esbHeader = new Holder<ESBHeader>(header);
		BackFreightCheckRequest request = new BackFreightCheckRequest();

		request.setWaybillNum(waybillNo);
		BackFreightCheckResponse response = fossToCrmService.backFreightCheck(
				request, esbHeader);

		if (response != null) {
			return response.isCheckResult();
		}

		// 默认认为不能生效
		return true;
	}

	/**
	 * 通过Dto查询应付单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Nov 20, 2012 9:45:31 AM
	 */
	private List<BillPayableEntity> queryBillPayableLaborFee()
			throws SettlementException {
		// 声明日期格式转换类
		SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd");
		// 设置查询记账结束时间(默认是Service开始运行的当天)
		Date endAccountDate = DateUtils.truncate(Calendar.getInstance()
				.getTime(), Calendar.SECOND);
		// 设置查询记账开始时间(默认是元年1970年1月1日)
		Date startAccountDate = null;
		try {
			// 日期转换
			startAccountDate = sft.parse("1970-01-01");
		}
		// 捕获异常
		catch (ParseException e) {
			// 打印日志
			LOGGER.info("生效装卸费时间转换queryBillPayableLaborFee服务" + e.getMessage(),
					e);
		}
		// Service执行的Log
		LOGGER.info("进入service--通过Dto查询应付单");
		// 传单据类型 生效状态 记账日期
		List<BillPayableEntity> billPayableEntityList = billPayableTakeEffectDao
				.queryBillPayableLaborFee(
						SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE,
						startAccountDate, endAccountDate,
						FossConstants.INACTIVE, FossConstants.ACTIVE);
		// Service执行的Log
		LOGGER.info("退出service--通过Dto查询应付单");
		// 返回查询结果
		return billPayableEntityList;
	}

	/**
	 * 查询是否有坏账
	 * 
	 * @author foss-zhangxiaohui
	 * @date Dec 14, 2012 1:42:24 PM
	 */
	private boolean hasBillBadAccount(String waybillNo) {
		// 如果查询条件为空则返回false
		if (null == waybillNo) {
			// 打印日志
			LOGGER.debug("进入Service--查询是否有坏账");
			LOGGER.debug("运单号不能为空");
			// 返回false
			return false;
		}
		// 调用坏账查询的接口查询运单是否存在坏账
		int i = billBadAccountService.queryByWaybillNO(waybillNo);
		// 打印日志
		LOGGER.debug("退出Service--查询是否有坏账");
		// 如果坏账表里面有记录则返回false
		return i > 0;
	}

	/**
	 * 生效装卸费
	 * 
	 * @author foss-zhangxiaohui
	 * @date Dec 14, 2012 2:22:45 PM
	 */
	@Transactional
	private void updatePayableBillLaborFeeStatus(List<BillPayableEntity> list) {
		// 打印日志
		LOGGER.info("进入updatePayableBillLaborFeeStatus服务--开始生效装卸费");
		// 执行生效装卸费
		this.billPayableService.updatePayableBillLaborFeeStatus(list);
		// 打印日志
		LOGGER.info("退出updatePayableBillLaborFeeStatus服务--开始生效装卸费");
	}

	/**
	 * @param billPayableTakeEffectDao
	 */
	public void setBillPayableTakeEffectDao(
			IBillPayableTakeEffectDao billPayableTakeEffectDao) {
		this.billPayableTakeEffectDao = billPayableTakeEffectDao;
	}

	/**
	 * @param billPayableService
	 */
	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	/**
	 * @param fossToCrmService
	 */
	public void setFossToCrmService(FossToCrmService fossToCrmService) {
		this.fossToCrmService = fossToCrmService;
	}

	/**
	 * @param billBadAccountService
	 */
	public void setBillBadAccountService(
			IBillBadAccountService billBadAccountService) {
		this.billBadAccountService = billBadAccountService;
	}

	/**
	 * @param billReceivableService
	 */
	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

}
