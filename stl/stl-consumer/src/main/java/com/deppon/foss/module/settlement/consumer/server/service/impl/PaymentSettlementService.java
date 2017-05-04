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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/server/service/impl/PaymentSettlementService.java
 * 
 * FILE NAME        	: PaymentSettlementService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 * 
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.INonfixedCustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillRepaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService;
import com.deppon.foss.module.settlement.common.api.server.service.ICustomerBargainService;
import com.deppon.foss.module.settlement.common.api.server.service.IFinanceOnlinePayWSProxy;
import com.deppon.foss.module.settlement.common.api.server.service.IFossConfigEntityService;
import com.deppon.foss.module.settlement.common.api.server.service.IPODService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService;
import com.deppon.foss.module.settlement.common.api.server.service.IWSCManageService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillClaimEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.FossConfigEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PODEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.DebitDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.PaymentSettlementDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.service.impl.HttpClientUtil;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IPaymentSettlementDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillClaimService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IGrayCustomerService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IPaymentSettlementService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.GrayCustomerEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.ArriveOnlineDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.PtpToPayDeductReqDto;
import com.deppon.foss.module.settlement.pay.api.server.service.IFossToFinanceRemittanceService;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IBillDepWriteoffBillRecService;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillDepWriteoffBillRecDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillDepositReceivedDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 
 * 到付运费结转临欠/月结
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-10-16 上午8:44:59
 * @since
 * @version 
 */
public class PaymentSettlementService implements IPaymentSettlementService {

	private static final Logger LOGGER = LogManager.getLogger(PaymentSettlementService.class);

	/**
	 * 结算公用Service
	 */
	private ISettlementCommonService settlementCommonService;

	/**
	 * 应收单公用Service
	 */
	private IBillReceivableService billReceivableService;

	/**
	 * 还款单公用 Service
	 */
	private IBillRepaymentService billRepaymentService;

	/**
	 * 应付单公用Service
	 */
	private IBillPayableService billPayableService;

	/**
	 * 扣减客户信用额度Service
	 */
	private ICustomerBargainService customerBargainService;

	/**
	 * 核销service
	 */
	private IBillWriteoffService billWriteoffService;

	/**
	 * 对账单Service
	 */
	private IStatementOfAccountService statementOfAccountService;

	/**
	 * 接送货更改单Service
	 */
	private IWaybillRfcService waybillRfcService;

	/**
	 * 接送货运单Service
	 */
	private IWaybillManagerService waybillManagerService;

	/**
	 * 综合管理-客户信息Service
	 */
	private ICustomerService customerService;
	
	/**
	 * 综合管理-散客信息服务
	 */
	private INonfixedCustomerService nonfixedCustomerService;

	/**
	 * 占用汇款编号/释放汇款编号 Service
	 */
	private IFossToFinanceRemittanceService fossToFinanceRemittanceService;

	/**
	 * 组织信息服务
	 */
	//private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * POD服务
	 */
	private IPODService podService;

	/**
	 * 网上支付的款项处理
	 */
	private IFinanceOnlinePayWSProxy financeOnlinePayWSProxy;
	
	/**
	 * 灰名单服务
	 */
	private IGrayCustomerService grayCustomerService;
	
	/**
	 * 待核销预收单、应收单进行查询、核销等操作service
	 */
	private IBillDepWriteoffBillRecService billDepWriteoffBillRecService;
	
	private ISaleDepartmentService saleDepartmentService;
	
	private IConfigurationParamsService configurationParamsService;
	
	/**
	 * 付款结算dao
	 */
	private IPaymentSettlementDao paymentSettlementDao;
	
	/**
	 * @author 268217
	 * 注入理赔单服务Service
	 */
	private IBillClaimService billClaimService;

    private IWSCManageService wscManageService;
    
    /**
	 * esb地址配置Service
	 */
	private IFossConfigEntityService fossConfigEntityService;
	
	/**
	 * 综合服务接口 组织信息 Service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * 调用接送货的一些外围验证
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-17 上午11:40:00
	 * @param dto
	 */
	private void validateWaybill(PaymentSettlementDto dto) {
		LOGGER.info(" 开始调用接送货接口验证规则 ");
		// 调用接送货接口，判断运单信息是否存在 2012-11-16
		// 需要验证运单信息是否存在开关
		if (SettlementConstants.EXTEND_SYSTEM_WAYBILL_IS_EXISTST) {
			boolean bl = waybillManagerService.isWayBillExsits(dto.getWaybillNo());
			if (!bl) {
				LOGGER.error("调用接送货接口验证规则,运单信息不存在。");
				throw new SettlementException("运单信息不存在。");
			}
		}

		// 接送货判断是否存在未受理的更改单【接送货接口 】
		boolean b = waybillRfcService.isExsitsWayBillRfc(dto.getWaybillNo());
		if (b) {
			LOGGER.error("运单" + dto.getWaybillNo() + "存在未受理的更改单");
			throw new SettlementException("运单" + dto.getWaybillNo() + "存在未受理的更改单");
		}
		LOGGER.info(" 调用接送货校验规则成功！ ");
	}

	/**
	 * 验证到付转临欠/月结参数
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-20 下午2:37:10
	 * @param dto
	 */
	private void validateConfirmToBillReceivableParams(PaymentSettlementDto dto) {
		if (StringUtils.isEmpty(dto.getWaybillNo())) {
			throw new SettlementException("运单号不能为空");
		}
		
		// ISSUE-3281 查询最新的签收记录，如果签收类型为POD(已签收)，则不允许继续操作
		PODEntity podEntity = podService.queryNewestPOD(dto.getWaybillNo());
		if (podEntity != null
				&& podEntity.getPodType()
						.equals(SettlementDictionaryConstants.POD_ENTITY__POD_TYPE__PROOF_OF_DELIVERY)) {
			throw new SettlementException("运单已经签收，不能结清货款");
		}

		if (StringUtils.isEmpty(dto.getPaymentType())) {
			throw new SettlementException("付款方式不能为空");
		} else {
			// 判断付款方式不为临欠或月结
			if (!(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT.equals(dto
					.getPaymentType()))
					&& !(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(dto
							.getPaymentType()))) {
				throw new SettlementException("付款方式不为临欠或月结");
			}
		}

		String custName = null;

		// 付款方式为月结的，客户编码不能为空（付款方式为临欠的客户编码可以为空）
		if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(dto
				.getPaymentType())
				||SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT.equals(dto.getPaymentType())) {

			if (StringUtils.isEmpty(dto.getCustomerCode())) {
				throw new SettlementException("转临时欠款/月结时客户编码不能为空");
			}

			// 根据客户编码，判断客户信息是否存在 ISSUE-3271
			// 查询客户信息
			CustomerEntity custEntity = customerService.queryNoDeletedCustInfoByCode(dto
					.getCustomerCode());
			if (custEntity == null) {
				throw new SettlementException(String.format("客户编码为：%s的客户信息不存在！", dto.getCustomerCode()));
			} else {
				custName = custEntity.getName();
			}
		}
		else{
			
			if(StringUtils.isNotEmpty(dto.getCustomerCode())){
				
				//查询固定客户
				CustomerEntity custEntity = customerService.queryNoDeletedCustInfoByCode(dto
						.getCustomerCode());
				
				if(custEntity == null){
					
					//查询散客
					//BUG-42022
					NonfixedCustomerEntity nonfixedCustEntity = nonfixedCustomerService.queryNoDeletedCustInfoByCode(dto.getCustomerCode());
					if(nonfixedCustEntity != null){
						custName = nonfixedCustEntity.getCustName();
					}
					else{
						throw new SettlementException(String.format("客户编码为：%s的客户信息不存在！", dto.getCustomerCode()));
					}
				}
				else{
					custName = custEntity.getName();
				}
			}
		}
		
		//设置客户名称
		if(StringUtils.isNotEmpty(custName)){
			dto.setCustomerName(custName);
		}

		/**
		 * 统一结算需求
		 * 运单到达客户为统一结算客户则：
		 * 1.结清货款客户必须为到达客户
		 * 2.付款方式只能为电汇
		 *
		 * @Author 105762
		 */
		ActualFreightEntity actualFreightEntity;
		WaybillDto waybill;
		try{
			//获取运单信息
			waybill =  waybillManagerService.queryWaybillByNo(dto.getWaybillNo());
			actualFreightEntity = waybill.getActualFreightEntity();
			if(null == waybill || null == actualFreightEntity){
				throw new SettlementException("获取运单信息异常！");
			}
		} catch(Exception e){
			throw new SettlementException("查询运单发票标记接口（接送货模块）发生异常，信息："+e.getMessage());
		}

		//判断是否到达统一结算
		if(StringUtil.equals(actualFreightEntity.getArriveCentralizedSettlement(), FossConstants.YES)){
			//结清货款客户不与运单上的到达客户相同
			if(!StringUtil.equals(dto.getCustomerCode(),waybill.getWaybillEntity().getReceiveCustomerCode())){
				throw new SettlementException(String.format("运单：%s 到达客户为【统一结算】，则结清货款时客户只能选择编号为【%s】的" +
						"客户，且付款方式只能选择为【临欠】【月结】或者【电汇】。",
						dto.getWaybillNo(),waybill.getWaybillEntity().getReceiveCustomerCode()));
			}
		}

		if (StringUtils.isEmpty(dto.getDestOrgCode())) {
			throw new SettlementException("到达部门编码不能为空");
		}

		// 验证运单是否存在和是否存在未受理的更改单
		this.validateWaybill(dto);
		
		/**
		 * 268217
		 * 快递 理赔出库的查询应付单
		 */
		if(StringUtils.isNotEmpty(dto.getIsExpress()) && 
				SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__YES.equals(dto.getIsExpress()) &&
				SettlementDictionaryConstants.CLAIMSWAY_CLAIMS_OUT.equals(dto.getPaymentType())){
			List<String> waybillNos = new ArrayList<String>();
			List<String> billTypes = new ArrayList<String>();
			waybillNos.add(dto.getWaybillNo());
			billTypes.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM);
			//查询理赔单中是否有数据
			BillClaimEntity claim=billClaimService.queryBillClaimEntity(dto.getWaybillNo());
			if(claim ==null || !SettlementDictionaryConstants.CLAIMSWAY_ONLINE.equals(claim.getClaimType())){
				throw new SettlementException("此单号未生成理赔应付单，无法理赔出库 ，请先申请理赔，生成理赔应付单！");
			}
		}
		//合伙人开单校验财务单据金额与运单表中的金额是否一致  by 243921 
		this.validateReceivableAmount(waybill.getWaybillEntity());
	}

	/**
	 * 到付运费结转临欠/月结
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-16 上午8:45:34
	 * @param dto
	 * @param currentInfo
	 * @return
	 */
	@Override
	public void confirmToBillReceivable(PaymentSettlementDto dto, CurrentInfo currentInfo) {
		if (dto == null) {
			throw new SettlementException("接口传入的参数不能为空");
		}
		
		LOGGER.info("开始到付运费结转临欠/月结操作，运单号为：" + dto.getWaybillNo());

		// 验证接送货传入的参数
		validateConfirmToBillReceivableParams(dto);

		// 查询应收单信息
		BillReceivableConditionDto receivableDto = new BillReceivableConditionDto(
				dto.getWaybillNo());
		receivableDto
				.setBillTypes(new String[] { SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,
						SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE});
		List<BillReceivableEntity> list = billReceivableService
				.queryBillReceivableByCondition(receivableDto);
		if (CollectionUtils.isEmpty(list)) {
			throw new SettlementException("不存在到付运费应收单");
		}

		// 系统中存在多个到付运费应收单，不能进行到付运费转临欠月结操作
		this.billReceivableService.validateWaybillForBillReceivable(list);
		BillReceivableEntity entity = list.get(0);
		Date date = dto.getBusinessDate() != null ? dto.getBusinessDate() : new Date();

		
		/**
		 * @author 218392 zhangyongxue
		 * @date 2016-06-27 20:26:30
		 * VTS整车项目的临欠月结是在VTS结清货款界面进行，所以后台这部分校验，如果是VTS的结清货款就不用校验
		 * 如果不是VTS整车：那么businessId为空，那么就走括号里面的
		 */
		if(StringUtils.isEmpty(dto.getBusinessId())){
			// 根据应收单的未核销金额和客户信息，判断客户是否能够欠款
			if (entity.getUnverifyAmount() != null
					&& entity.getUnverifyAmount().compareTo(BigDecimal.ZERO) > 0) {
				DebitDto deDto = customerBargainService.isBeBebt(dto.getCustomerCode(),
						dto.getDestOrgCode(), dto.getPaymentType(), entity.getUnverifyAmount());

				if (deDto == null) {
					throw new SettlementException("找不到客户/部门的额度配置信息!");
				}
				if (!deDto.isBeBebt()) {
					throw new SettlementException(deDto.getMessage());
				}
			}
		}

		// 业务时间或当前时间在应收单的解锁时间之前，不能进行到付运费转临欠月结操作
		if (entity.getUnlockDateTime() != null && date.before(entity.getUnlockDateTime())) {
			throw new SettlementException("应收单已经被网上支付锁定，不能进行到付运费转临欠月结操作");
		}

		// 应收单进入对账单，且被确认了，不能进行到付运费转临欠月结操作
		if (StringUtils.isNotEmpty(entity.getStatementBillNo())
				&& !SettlementConstants.DEFAULT_BILL_NO.equals(entity.getStatementBillNo())) {
			validateStatementBillNo(entity.getStatementBillNo());
		}

		// 应收单的签收状态为已签收和已确认收入，不能进行到付运费转临欠月结操作
		if (entity.getConrevenDate() != null) {
			throw new SettlementException("运单已签收，不能进行到付运费转临欠月结操作");
		}

		// 当查询到的到付运费应收的付款方式已经为临欠或月结时，返回提示信息
		if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT.equals(entity
				.getPaymentType())) {
			throw new SettlementException("付款方式已更改为临欠");
		} else if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(entity
				.getPaymentType())) {
			throw new SettlementException("付款方式已更改为月结");
		}

		// 判断已核销金额是否等于0，大于零时提示不能进行操作
		if (entity.getVerifyAmount() != null
				&& entity.getVerifyAmount().compareTo(BigDecimal.ZERO) > 0) {
			throw new SettlementException("应收单已经被核销，不能进行到付运费转临欠/月结操作");
		}

		// 查询到的应收单的应收部门，和传入的到达部门不一致的情况下,不能进行到付运费转临欠月结操作
		//剔除统一结算的到付运费
		if (StringUtils.isNotEmpty(dto.getDestOrgCode()) && !FossConstants.YES.equals(entity.getUnifiedSettlement()) 
				&& !dto.getDestOrgCode().equals(entity.getReceivableOrgCode())) {
			throw new SettlementException("当前部门与应收单的应收部门不一致");
		}

		/**
		 * @author 218392 zhangyongxue
		 * @date 2016-06-27 20:26:30
		 * VTS整车项目的临欠月结是在VTS结清货款界面进行，所以后台这部分校验，如果是VTS的结清货款就不用校验
		 * 如果不是VTS整车：那么businessId为空，那么就走括号里面的
		 */
		if(StringUtils.isEmpty(dto.getBusinessId())){
			
			// 查询的到付运费付款方式为月结的，客户为 具体客户时，返回错误信息
			if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(dto
					.getPaymentType()) && StringUtils.isNotEmpty(entity.getCustomerCode())) {
				// 调用综合接口进行查询
				CustomerDto custDto = customerService.queryCustInfoByCode(entity.getCustomerCode());
				if (custDto != null) {
					throw new SettlementException("到付运费客户已为 具体客户");
				}
			}
			
		}

		dto.setRevers(false);// 设置正向操作

		// 调用红冲应收单方法
		writeBackBillReceivableByPayable(entity, dto, currentInfo);
		
		//start add by 269044-zhurongrong 2016-05-09 15:22
		//到付转月结时，先判断该客户是否在灰名单中，不在的话需判断是否添加进去，在的话，需要更新最久欠款日期
		//存放待处理客户编码集合
		List<String> customerCodeList = new ArrayList<String>();
		//添加客户编码
		customerCodeList.add(dto.getCustomerCode());
		try{
			//调用判断时候修改灰名单接口
			grayCustomerService.updateGrayCustomerToECS(customerCodeList);
		} catch (Exception e) {
			//打印异常
			LOGGER.info("调用悟空更改灰名单接口异常" + e.getMessage());
		}
		//end
		LOGGER.info(" 到付转临欠/月结操作成功");
	}

	/**
	 * 验证对账单方法
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-30 下午6:30:34
	 * @param statementBillNo
	 * @see
	 */
	private void validateStatementBillNo(String statementBillNo) {
		List<String> souIds = statementOfAccountService.queryConfirmStatmentOfAccount(Arrays
				.asList(statementBillNo));
		if (CollectionUtils.isNotEmpty(souIds)) {
			throw new SettlementException("到达应收单对应的对账单已被客户/代理确认，不能进行反签收操作");
		}
	}

	/**
	 * (反签收) 反到付运费结转临欠/月结
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-16 上午8:45:43
	 * @param dto
	 * @param currentInfo
	 * @return
	 */
	@Override
	public void reversConfirmToBillReceiveable(PaymentSettlementDto dto, CurrentInfo currentInfo) {
		if (dto == null) {
			throw new SettlementException("接口传入的参数不能为空");
		}
		if (StringUtils.isEmpty(dto.getWaybillNo())) {
			throw new SettlementException("运单号为空，不能进行后续操作！");
		}
		
		// ISSUE-3281 查询最新的签收记录，如果签收类型为POD(已签收)，则不允许继续操作
		PODEntity podEntity = podService.queryNewestPOD(dto.getWaybillNo());
		if (podEntity != null
				&& podEntity.getPodType()
						.equals(SettlementDictionaryConstants.POD_ENTITY__POD_TYPE__PROOF_OF_DELIVERY)) {
			throw new SettlementException("运单已经签收，不能结清货款");
		}
		
		LOGGER.info(" 开始反到付转临欠/月结操作，运单号为：" + dto.getWaybillNo());
		if (StringUtils.isEmpty(dto.getDestOrgCode())) {
			throw new SettlementException("到达部门编码不能为空！");
		}

		// 验证运单是否存在和是否存在未受理的更改单
		this.validateWaybill(dto);

		// 根据传入的运单号和单据类型等参数，查询[到付运费]有效应收单信息
		BillReceivableConditionDto receivableDto = new BillReceivableConditionDto(
				dto.getWaybillNo());
		receivableDto
				.setBillTypes(new String[] { SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,
						SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE });// 到达应收单
		List<BillReceivableEntity> list = billReceivableService
				.queryBillReceivableByCondition(receivableDto);

		// 不存在到付运费应收单，不能进行反到付运费转临欠月结操作
		if (CollectionUtils.isEmpty(list)) {
			throw new SettlementException("不存在到达运费应收单，不能进行反到付运费转临欠月结操作");
		}
		
		// 系统中存在多个到达运费应收单，不能进行反到付运费转临欠月结操作
		this.billReceivableService.validateWaybillForBillReceivable(list);

		BillReceivableEntity entity = (BillReceivableEntity) list.get(0);

		// 当前操作时间
		Date date = dto.getBusinessDate() != null ? dto.getBusinessDate() : new Date();
		if (entity.getUnlockDateTime() != null && date.before(entity.getUnlockDateTime())) {
			throw new SettlementException("应收单已经被锁定，不能进行反到付运费转临欠月结操作");
		}
		if (entity.getVerifyAmount() != null
				&& entity.getVerifyAmount().compareTo(BigDecimal.ZERO) > 0) {
			throw new SettlementException("应收单已核销，不能反到付运费结转临欠月结操作");
		}

		// 对账单号，不为空且不为N/A，验证对账单是否已经被确认
		if (StringUtils.isNotEmpty(entity.getStatementBillNo())
				&& !SettlementConstants.DEFAULT_BILL_NO.equals(entity.getStatementBillNo())) {
			validateStatementBillNo(entity.getStatementBillNo());
		}

		// 应收单已确认收入，付款方式没有发生改变，不能进行反到付运费结转临欠月结操作
		if (entity.getConrevenDate() != null
				&& !SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT
						.equals(dto.getPaymentType())) {
			// 签收以后，如果接口传入的付款方式不为到付，不能进行反到付运费结转临欠月结操作

			throw new SettlementException("应收单已确认收入，不能进行反到付运费转临欠月结操作");
		}

		// 查询到的应收单的应收部门，和传入的到达部门不一致的情况下
		if (StringUtils.isNotEmpty(dto.getDestOrgCode()) && !FossConstants.YES.equals(entity.getUnifiedSettlement())
				&& !dto.getDestOrgCode().equals(entity.getReceivableOrgCode()) ) {
			throw new SettlementException("传入的到达部门和和应收单的应收部门不一致");
		}

		// 判断应收单的付款方式为到付时，返回提示信息
		if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT.equals(entity
				.getPaymentType())) {
			throw new SettlementException("已存在付款方式为到付的应收单");
		}
		
		//BUG-45564 取消对客户的校验，临时欠款客户可以是散客信息，并且需要将原有客户清空		
		/*if (StringUtils.isNotEmpty(entity.getCustomerCode())) {
			// 校验到达运费应收单的 客户信息是否存在，不存在提示客户信息为空
			CustomerDto custDto = customerService.queryCustInfoByCode(entity.getCustomerCode());
			if (custDto == null || StringUtils.isEmpty(custDto.getCusCode())) {
				throw new SettlementException("原到达运费应收单的客户信息为空");
			}
		} else {
			throw new SettlementException("到达运费应收单的客户信息为空");
		}*/
		
		dto.setRevers(true);// 反操作方法

		// 调用红冲应收单方法，生成蓝单
		writeBackBillReceivableByPayable(entity, dto, currentInfo);

		//start add by 269044-zhurongrong 2016-05-09 15:23
		//反结清时，先判断该客户是否在灰名单中，在的话需判断是否拉出来，不在的话，直接pass
		GrayCustomerEntity grayCustomer = grayCustomerService.queryGrayCustomerByCustomerCode(entity.getCustomerCode());
		//存放待处理客户编码集合
		List<String> customerCodeList = new ArrayList<String>();
		//添加客户编码
		customerCodeList.add(entity.getCustomerCode());
		if(grayCustomer!=null) {
			try{
				//调用判断时候修改灰名单接口
				grayCustomerService.updateGrayCustomerToECS(customerCodeList);
			} catch (Exception e) {
				//打印异常
				LOGGER.info("调用悟空更改灰名单接口异常" + e.getMessage());
			}
		}	
		//end
		LOGGER.info(" 反到付转临欠/月结操作成功");
	}

	/**
	 * (到付运费结转临欠/月结)根据红应收单动作，作废原应收单信息 生成新红单应收单信息、生成蓝单应收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-23 下午2:31:46
	 * @param entity
	 * @param dto
	 * @param currentInfo
	 * @see
	 */
	private void writeBackBillReceivableByPayable(BillReceivableEntity entity,
			PaymentSettlementDto dto, CurrentInfo currentInfo) {

		// 输入参数校验
		if (entity == null || dto == null) {
			throw new SettlementException("到付运费结转临欠/月结参数不能为空！");
		}

		LOGGER.info("entering service, sourceBillNo: " + entity.getSourceBillNo());

		// 调用公共 红冲应收单方法
		this.billReceivableService.writeBackBillReceivable(entity, currentInfo);

		// 红冲应收单成功以后
		BillReceivableEntity blueEntity = new BillReceivableEntity();
		BeanUtils.copyProperties(entity, blueEntity);

		// ID
		blueEntity.setId(UUIDUtils.getUUID());

		// 生成新单据
		if (SettlementConstants.BLUE_NEW_BILL_NO) {
			SettlementNoRuleEnum rule = SettlementNoRuleEnum.getByCode(entity.getReceivableNo());
			blueEntity.setReceivableNo(settlementCommonService.getSettlementNoRule(rule));
		}

		// 设置付款方式
		blueEntity.setPaymentType(dto.getPaymentType());

		// 非反操作时，到付转临欠/月结时，设置客户编码信息
		if (!dto.isRevers()) {
			// 设置客户编码
			blueEntity.setCustomerCode(dto.getCustomerCode());

			// 设置客户名称
			blueEntity.setCustomerName(dto.getCustomerName());

			// 设置到达客户编码
			//blueEntity.setReceiveCustomerCode(dto.getCustomerCode());

			// 设置到达客户名称
			//blueEntity.setReceiveCustomerName(dto.getCustomerName());

			// BUG-23123：转临欠/月结时，催款部门需改为客户合同上的催款部门
			/*CustomerDto custDto = customerService.queryCustInfoByCode(dto.getCustomerCode());
			if (custDto != null) {
				// 获取合同信息
				List<CusBargainEntity> bargains = custDto.getBargainList();

				if (CollectionUtils.isNotEmpty(bargains) && bargains.get(0) != null) {
					// 根据综合反映，只取第一个合同
					CusBargainEntity bargainEntity = bargains.get(0);

					// 判断为异地调货
					if (FossConstants.YES.equals(bargainEntity.getAsyntakegoodsCode())) {
						OrgAdministrativeInfoEntity asynOrgEntity = orgAdministrativeInfoService
								.queryOrgAdministrativeInfoByUnifiedCode(bargainEntity
										.getHastenfunddeptCode());

						// 设置催款部门
						if (asynOrgEntity != null) {
							blueEntity.setDunningOrgCode(asynOrgEntity.getCode());
							blueEntity.setDunningOrgName(asynOrgEntity.getName());
						}
					}
				}

			}*/

		} else {
			// 反到付转临欠/月结时，设置客户编码信息为空

			// 设置客户编码为空
			blueEntity.setCustomerCode("");

			// 设置客户名称为空
			blueEntity.setCustomerName("");

			// 设置到达客户编码为空
			//blueEntity.setReceiveCustomerCode("");

			// 设置到达客户名称为空
			//blueEntity.setReceiveCustomerName("");

			// 反之后，设置付款方式为到付
			blueEntity
					.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT);

			// BUG-23123：反转临欠/月结时，催款部门运单上的到达部门
			//blueEntity.setDunningOrgCode(blueEntity.getDestOrgCode());
			//blueEntity.setDunningOrgName(blueEntity.getDestOrgName());
		}

		// 日期初始化到秒
		Date now = Calendar.getInstance().getTime();

		// 设置记账日期
		blueEntity.setAccountDate(now);

		// ISSUE-2397:结转时，设置业务日期为当前日期
		blueEntity.setBusinessDate(now);

		// 是否有效
		blueEntity.setActive(FossConstants.ACTIVE);

		// 是否红单--非红单
		blueEntity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);

		// 版本号
		blueEntity.setVersionNo(FossConstants.INIT_VERSION_NO);

		// 红冲生成蓝单后设置对账单号，为默认单号
		blueEntity.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);

		// 保存应收单信息
		this.billReceivableService.addBillReceivable(blueEntity, currentInfo);
		if (!dto.isRevers()) {
			// 挂具体客户，生成蓝单以后，调用结算内部接口，增加客户的已用信用额度信息
			customerBargainService.updateUsedAmount(
					// 此方法不报错误，表示修改成功
					blueEntity.getCustomerCode(), blueEntity.getReceivableOrgCode(),
					blueEntity.getPaymentType(), blueEntity.getUnverifyAmount(), currentInfo);
		}
	}

	/**
	 * 验证实收货款参数
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-20 下午3:08:06
	 * @param dto
	 */
	private void validateConfirmToPaymentParams(PaymentSettlementDto dto) {
		if (StringUtils.isEmpty(dto.getWaybillNo())) {
			throw new SettlementException("运单号不能为空");
		}
		// ISSUE-3281 查询最新的签收记录，如果签收类型为POD(已签收)，则不允许继续操作
		PODEntity podEntity = podService.queryNewestPOD(dto.getWaybillNo());
		if (podEntity != null
				&& podEntity.getPodType()
						.equals(SettlementDictionaryConstants.POD_ENTITY__POD_TYPE__PROOF_OF_DELIVERY)) {
			throw new SettlementException("运单已经签收，不能结清货款");
		}

		if (StringUtils.isEmpty(dto.getSourceBillNo())) {
			throw new SettlementException("实收货款批次号不能为空");
		}

		// 到达部门编码
		if (StringUtils.isEmpty(dto.getDestOrgCode())) {
			throw new SettlementException("到达部门编码不能为空");
		}
		if (StringUtils.isEmpty(dto.getPaymentType())) {
			throw new SettlementException("付款方式不能为空");
		}

		// 判断付款方式不能为空，并且不能为临欠或月结
		if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT.equals(dto
				.getPaymentType())
				|| SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(dto
						.getPaymentType())) {
			throw new SettlementException("传入的付款方式不能为临欠或月结");
		}

		// 付款方式为：电汇或支票时，汇款编号不能为空
        if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER.equals(dto
                .getPaymentType())
                || SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__NOTE.equals(dto
                .getPaymentType())) {
            if (StringUtils.isEmpty(dto.getPaymentNo())) {
                // 汇款编号为空
                throw new SettlementException("付款方式为电汇或支票时，汇款编号不能为空");
            }
        }

        /**
         * 付款方式为银行卡时，汇款编号不能为空，且均由数字组成。
         *
         * @author 105762
         */
        /**
         * 涉及PDA结清，因项目组要求，去掉纯数字校验后续要加 再提需求 2015-04-22
         */
        if(StringUtils.isBlank(dto.getPaymentNo())){
        	dto.setPaymentNo(dto.getBatchNo());
        }
        
        if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(dto.getPaymentType())) {
            if (StringUtils.isBlank(dto.getPaymentNo()) ) {
                throw new SettlementException("付款方式为银行卡时，汇款编号不能为空。");
            }
        }

		// 两种实收货款同时为空时，返回不能进行实收货款操作
		if (dto.getCodFee() == null && dto.getToPayFee() == null) {
			throw new SettlementException("实收货款金额不能为空");
		}
		if (dto.getCodFee() != null && (BigDecimal.ZERO.compareTo(dto.getCodFee()) >= 0)
				&& dto.getToPayFee() != null && (BigDecimal.ZERO.compareTo(dto.getToPayFee()) >= 0)) {
			throw new SettlementException("实收代收货款费用和实收到达运费金额必须大于0");
		}
		if (dto.getCodFee() != null && (BigDecimal.ZERO.compareTo(dto.getCodFee()) >= 0)
				&& dto.getToPayFee() == null) {
			throw new SettlementException(" 实收代收货款金额必须大于0");
		}
		if (dto.getToPayFee() != null && (BigDecimal.ZERO.compareTo(dto.getToPayFee()) >= 0)
				&& dto.getCodFee() == null) {
			throw new SettlementException("实收到达运费金额必须大于0");
		}
		
		//BUG-45666 验证先结清为现金后半个小时内转为临时欠款与月结但是异步生成了还款单
		BillReceivableConditionDto receivDto = new BillReceivableConditionDto(dto.getWaybillNo());
		receivDto.setBillTypes(new String[]{
			SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,
			SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE
		});
		receivDto.setActive(FossConstants.ACTIVE);
		List<BillReceivableEntity> receivables = 
				billReceivableService.queryBillReceivableByCondition(receivDto);
		if(CollectionUtils.isNotEmpty(receivables)){
			if(receivables.size()>1){
				throw new SettlementException("存在多条有效的应收到付运费！");	
			}
			for(BillReceivableEntity billReceivable:receivables){
				//判断是否存在月结或者临时欠款金额
				if(SettlementDictionaryConstants.
						SETTLEMENT__PAYMENT_TYPE__DEBT.equals(billReceivable.getPaymentType())
				  ||SettlementDictionaryConstants.
				  SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(billReceivable.getPaymentType())){
			      throw new SettlementException("有效版本的应收单目前已经是临时欠款或者月结,实收货款不正确！");
				}
			}
		}

		/**
		 * 统一结算需求
		 * 运单到达客户为统一结算客户则：
		 * 1.结清货款客户必须为到达客户
		 * 2.付款方式只能为电汇
		 *
		 * @Author 105762
		 */ 
		ActualFreightEntity actualFreightEntity;
		WaybillDto waybill;
		try{
			//获取运单信息
			waybill =  waybillManagerService.queryWaybillByNo(dto.getWaybillNo());
			actualFreightEntity = waybill.getActualFreightEntity();
			if(null == waybill || null == actualFreightEntity){
				throw new SettlementException("获取运单信息异常！");
			}
		} catch(Exception e){
			throw new SettlementException("查询运单发票标记接口（接送货模块）发生异常，信息："+e.getMessage());
		}

		//到付运费大于零 且 判断是否到达统一结算
		if(dto.getToPayFee() != null && dto.getToPayFee().compareTo(BigDecimal.ZERO) > 0
				&& StringUtil.equals(actualFreightEntity.getArriveCentralizedSettlement(), FossConstants.YES)){
			//结清货款客户不与运单上的到达客户相同
			if(!StringUtil.equals(dto.getCustomerCode(),waybill.getWaybillEntity().getReceiveCustomerCode())){
				throw new SettlementException(String.format("运单：%s 到达客户为统一结算，则结清货款时客户只能选择编号为【%s】的" +
						"客户，且付款方式只能选择为【临欠】【月结】或者【电汇】。",
						dto.getWaybillNo(),waybill.getWaybillEntity().getReceiveCustomerCode()));
			}
			//付款方式只能为电汇
			if(!StringUtil.equals(dto.getPaymentType(),SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER)){
				throw new SettlementException(String.format("运单：%s 到达客户为统一结算，则结清货款时客户只能选择编号为【%s】的" +
						"客户，且付款方式只能选择为【临欠】【月结】或者【电汇】。" ,
						dto.getWaybillNo(), waybill.getWaybillEntity().getReceiveCustomerCode()));
			}
		}
		
		/**
		 * 268217
		 * 快递 理赔出库的查询应付单
		 */
		if(StringUtils.isNotEmpty(dto.getIsExpress()) && 
				SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__YES.equals(dto.getIsExpress()) &&
				SettlementDictionaryConstants.CLAIMSWAY_CLAIMS_OUT.equals(dto.getPaymentType())){
			List<String> waybillNos = new ArrayList<String>();
			List<String> billTypes = new ArrayList<String>();
			waybillNos.add(dto.getWaybillNo());
			billTypes.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM);
			//查询理赔单中是否有数据
			BillClaimEntity claim=billClaimService.queryBillClaimEntity(dto.getWaybillNo());
			if(claim ==null || !SettlementDictionaryConstants.CLAIMSWAY_ONLINE.equals(claim.getClaimType())){
				throw new SettlementException("此单号未生成理赔应付单，无法理赔出库 ，请先申请理赔，生成理赔应付单！");
			}
		}
		//合伙人开单校验财务单据金额与运单表中的金额是否一致  by 243921 
		this.validateReceivableAmount(waybill.getWaybillEntity());
		
	}
	
	/**
	 * 确认付款 (客户付给公司的费用) 实收货款
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-16 上午8:46:00
	 * @param dto
	 * @param currentInfo
	 * @return
	 */
	@Override
	public String confirmToPayment(PaymentSettlementDto dto, CurrentInfo currentInfo) {
		if (dto == null) {
			throw new SettlementException("接口传入参数不能为空");
		}
		
		// 验证传入参数的合法性
		validateConfirmToPaymentParams(dto);

		LOGGER.info("开始确认付款，实收货款操作，运单号为：" + dto.getWaybillNo() + " 实收货款批次号码： "
				+ dto.getSourceBillNo());

		// 验证运单是否存在和是否存在未受理的更改单
		this.validateWaybill(dto);

        /*
        * 添加结清方式的标记。默认为PC端结清
        * @author 105762
        */
        if(StringUtil.isBlank(dto.getSettleApproach())){
            dto.setSettleApproach(SettlementDictionaryConstants.SETTLE_APPROACH_PC);
        }

        //付款方式为‘理赔出库’时，红冲应收单然后将客户，变更为到达客户268217
        if(SettlementDictionaryConstants.CLAIMSWAY_CLAIMS_OUT.equals(dto.getPaymentType())){
        	BillReceivableConditionDto receivDto = new BillReceivableConditionDto(dto.getWaybillNo());
    		receivDto.setBillTypes(new String[]{
    			SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,
    			SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE
    		});
    		receivDto.setActive(FossConstants.ACTIVE);
    		List<BillReceivableEntity> receivables = 
    				billReceivableService.queryBillReceivableByCondition(receivDto);
    		//268217 如果有代收货款应收单，提示不能理赔出库
    		if(CollectionUtils.isNotEmpty(receivables)){
    			for (BillReceivableEntity ent : receivables){
    				if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE.equals(ent.getBillType())){
    					throw new SettlementException("此运单存在代收货款应收单，不能进行理赔出库，请先将代收货款应收单作废！");
    				}
    				billReceivableService.writeBackBillReceivable(ent, currentInfo);
    				this.buildBillReceivableOrig(ent,currentInfo);
    			}
    			//BillReceivableEntity ent=Receivables.get(0);
    		}
		}else{
			// 确认付款,生成还款单，后进行还款冲应收操作
			confirmToPaymentAddRepaymentAndWriteOff(dto, currentInfo);
		}

		/*
		 * SUC变更调用结算付款成功后，需要判断 对应的到付运费应收单和 代收货款应收单是否已完成核销或到付已转临欠/月结
		 */
		BillReceivableConditionDto receDto = new BillReceivableConditionDto(dto.getWaybillNo());
		receDto.setBillTypes(new String[] {
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,// 到达应收单
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE,// 代收货款应收单
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE
		});
		List<BillReceivableEntity> lists = this.billReceivableService
				.queryBillReceivableByCondition(receDto);
		if (CollectionUtils.isNotEmpty(lists)) {
			return this.decideWaybillIsSettle(lists);
		}

		LOGGER.info(" 确认付款操作成功");
		return SettlementConstants.CONFIRM_PAYMENT_SUCCESS;// 调用实收货款成功
	}

	/**
	 * 判断运费是否已经货款结清 经过和接送货沟通，只需要判断到付客户的应收单是否货款结清
	 * （到达应收付款方式为到付，而代收货款应收单付款方式原本就为：到付）进入，
	 *          
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-4 上午10:10:07
	 * @param list
	 * @return
	 * 修改于2016-12-20(将private改为public)为了走foss原来的逻辑
	 */
	public String decideWaybillIsSettle(List<BillReceivableEntity> list) {
		for (BillReceivableEntity entity : list) {
			// 应收单的付款方式：到付，且未核销金额大于0,返回没有结清货款
			if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT
					.equals(entity.getPaymentType())
					&& entity.getUnverifyAmount() != null
					&& entity.getUnverifyAmount().compareTo(BigDecimal.ZERO) > 0) {
				return SettlementConstants.CONFIRM_PAYMENT_SUCCESS_NOT_SETTLE;
			}
		}
		return SettlementConstants.CONFIRM_PAYMENT_SUCCESS_AND_PAYMENT_SETTLE;
	}

	/**
	 * 1、有实收代收货款金额和代收货款应收单时，进行核销操作 2、有是有到付实收运费和到达运费应收单时，进行核销操作
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-17 下午8:57:09
	 * @param dto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	private void confirmToPaymentAddRepaymentAndWriteOff(PaymentSettlementDto dto,
			CurrentInfo currentInfo) {
		if (dto == null) {
			throw new SettlementException("接口传入参数不能为空");
		}
		LOGGER.info("确认付款，开始执行生成还款单-------");

		// 查询应收单信息
		List<BillReceivableEntity> billReceivableLists = null;

		// 当实收货款和实收代收货款金额同时大于0时，一次性把代收货款应收单和到达运费应收单都查询出来
		if (dto.getCodFee() != null && dto.getCodFee().compareTo(BigDecimal.ZERO) > 0
				&& dto.getToPayFee() != null && dto.getToPayFee().compareTo(BigDecimal.ZERO) > 0) {
			BillReceivableConditionDto receivableDto = new BillReceivableConditionDto();
			receivableDto
					.setBillTypes(new String[] {
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE });
			receivableDto.setWaybillNo(dto.getWaybillNo());
			billReceivableLists = billReceivableService
					.queryBillReceivableByCondition(receivableDto);
			if (CollectionUtils.isNotEmpty(billReceivableLists)) {

				// 验证一个运单是否存在相同类型的多条应收单
				this.billReceivableService.validateWaybillForBillReceivable(billReceivableLists);
			}
		}
	   //到付运费和代收货款金额之和
	   BigDecimal repaymentAmount = BigDecimal.ZERO;
		// 当传入的实收代收货款金额大于0时，查询代收货款应收单,验证通过进行核销操作
		if (dto.getCodFee() != null && dto.getCodFee().compareTo(BigDecimal.ZERO) > 0) {
			confirmToPaymentAddRepaymentWriteOff(dto,
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE,// 应收单单据类型：代收货款
					SettlementDictionaryConstants.BILL_REPAYMENT__SOURCE_BILL_TYPE__COD,// 还款单来源单据类型：代收货款
					billReceivableLists, currentInfo);
			        repaymentAmount = repaymentAmount.add(dto.getCodFee());
		}

		// 当实收到达运费金额大于0时，查询到达运费应收单
		if (dto.getToPayFee() != null && dto.getToPayFee().compareTo(BigDecimal.ZERO) > 0) {
			List<BillReceivableEntity> receivableLists = new ArrayList<BillReceivableEntity> ();
			BillReceivableConditionDto receivableDto = new BillReceivableConditionDto();
			receivableDto.setBillTypes(new String[] {
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE });
			receivableDto.setWaybillNo(dto.getWaybillNo());
			receivableLists = billReceivableService.queryBillReceivableByCondition(receivableDto);
			for(BillReceivableEntity receEntity : receivableLists){
				if(receEntity.getBillType().equals(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE)){
					confirmToPaymentAddRepaymentWriteOff(
							dto,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,// 应收单单据类型：到达运费应收单
							SettlementDictionaryConstants.BILL_REPAYMENT__SOURCE_BILL_TYPE__FREIGHT_COLLECT,// 还款单来源单据类型：到付费
							billReceivableLists, currentInfo);
				}else if(receEntity.getBillType().equals(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE)){
					//DDW
					confirmToPaymentAddRepaymentWriteOff(
							dto,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE,// 应收单单据类型：到达运费应收单
							SettlementDictionaryConstants.BILL_REPAYMENT__SOURCE_BILL_TYPE__FREIGHT_COLLECT,// 还款单来源单据类型：到付费
							billReceivableLists, currentInfo);
				}
				repaymentAmount = repaymentAmount.add(dto.getToPayFee());
			}
		}	
		if ("PDA".equals(dto.getRepaymentType())) {
            // 支付宝条码支付不做处理
        } else {
        	// BUG-20628 生成还款单时，调用财物自助接口放置到最后操作
    		// 如果是电汇或支票的，调用财务自助系统接口，根据汇款编号，修改使用金额		
    		if (StringUtils.isNotEmpty(dto.getPaymentNo()) ) {

    			//网上支付占用财务自助记录
    			if(StringUtils.equals(dto.getPaymentType(),SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE)){
    				financeOnlinePayWSProxy.obtain(dto.getPaymentNo(),repaymentAmount);

    			} else if (StringUtils.equals(dto.getPaymentType(),SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER) ||
    					StringUtils.equals(dto.getPaymentType(),SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__NOTE)){
    				// 占用电汇汇款编号
    				fossToFinanceRemittanceService.obtainClaim(repaymentAmount,// 还款金额
    						dto.getPaymentNo(), // 汇款编号
    						currentInfo.getCurrentDeptCode(),// 操作部门编码
    						dto.getWaybillNo()
    						// repaymentEntity.getRepaymentNo()// 还款单号
    					);
    			}
    		}
        }
		

		LOGGER.info("确认付款，成功生成还款单-----");
	}

	/**
	 * 验证应收单信息，验证通过生成还款单，进行核销操作
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-18 上午9:03:14
	 * @param dto
	 * @param receivableType
	 *            应收单类型
	 * @param repaymentSourceBillType
	 *            还款单来源单据类型
	 * @param billReceivableLists
	 *            应收单集合
	 * @param currentInfo
	 */
	private void confirmToPaymentAddRepaymentWriteOff(PaymentSettlementDto dto,
			String receivableType, String repaymentSourceBillType,
			List<BillReceivableEntity> billReceivableLists, CurrentInfo currentInfo) {

		// 校验应收单信息，看应收单是否能被操作
		BillReceivableEntity receivableEntity = checkReceivable(dto, receivableType,
				billReceivableLists, repaymentSourceBillType);

		if (receivableEntity != null) {
			// 设置还款单实体属性
			BillRepaymentEntity repaymentEntity = getBillRepaymentEntity(dto,
					repaymentSourceBillType, receivableEntity.getOrigOrgCode(),
					receivableEntity.getOrigOrgName());

			// 生成还款单
			repaymentEntity.setCreateOrgCode(currentInfo.getCurrentDeptCode());
			repaymentEntity.setCreateOrgName(currentInfo.getCurrentDeptName());
			this.billRepaymentService.addBillRepayment(repaymentEntity, currentInfo);

			// 保存还款单成功后，进行还款冲应收操作，调用核销接口
			writeoffRepaymentAndReceibable(repaymentEntity, receivableEntity, currentInfo);		
		}
	}

	/**
	 * 实收货款校验应收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-18 下午5:05:04
	 * @param dto
	 * @param receivableType
	 *            应收单类型
	 * @param list
	 * @return
	 * @see
	 */
	private BillReceivableEntity checkReceivable(PaymentSettlementDto dto, String receivableType,
			List<BillReceivableEntity> list, String repaymentSourceBillType) {
		LOGGER.debug("确认付款，开始校验应收单据");
		if (dto == null) {
			throw new SettlementException("接口参数不能为空");
		}
		BillReceivableEntity entity = null;

		// 传入的List集合为空时，再进行单独的查询
		if (CollectionUtils.isEmpty(list)) {

			// 根据传入的运单号和单据类型等参数，查询应收单信息
			BillReceivableConditionDto receivableDto = new BillReceivableConditionDto(
					dto.getWaybillNo());
			receivableDto.setBillTypes(new String[] { receivableType });
			List<BillReceivableEntity> listReces = billReceivableService
					.queryBillReceivableByCondition(receivableDto);

			if (CollectionUtils.isNotEmpty(listReces)) {
				// 验证一个运单是否存在相同类型的多条应收单
				this.billReceivableService.validateWaybillForBillReceivable(listReces);
				entity = (BillReceivableEntity) listReces.get(0);
			}
		} else {
			// 传入参数list不为空时，
			entity = this.getBillReceivableEntityByBillTypeForList(list, receivableType);
		}

		// 根据传入 receivableType 应收单类型获取应收单名称,方便后续抛出异常使用
		String receivableName = getBillNameByType(receivableType);
		if (entity == null || StringUtils.isEmpty(entity.getId())) {
			throw new SettlementException("不存在应收单信息", receivableName);
		}

		// 未核销金额等于0时
		if (entity.getUnverifyAmount() != null 
				&& entity.getUnverifyAmount().compareTo(BigDecimal.ZERO) == 0) {
			throw new SettlementException("应收单已完成核销，不能进行实收货款操作", receivableName);
		} 
		if (StringUtils.isNotEmpty(dto.getDestOrgCode()) && !FossConstants.YES.equals(entity.getUnifiedSettlement())
				&& !dto.getDestOrgCode().equals(entity.getReceivableOrgCode())) {
			throw new SettlementException("应收单的应收部门与传入的到达部门编码不一致");
		}

		// 单据类型：到付运费应收单
		if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE
				.equals(receivableType)
				&& dto.getToPayFee() != null
				&& dto.getToPayFee().compareTo(entity.getUnverifyAmount()) > 0) {
			throw new SettlementException("实收货款金额大于应收单的未核销金额");
		}
		// 单据类型：代收货款应收单
		else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE
				.equals(receivableType)
				&& dto.getCodFee() != null
				&& dto.getCodFee().compareTo(entity.getUnverifyAmount()) > 0) {
			throw new SettlementException("实收代收货款金额大于应收单的未核销金额");
		}
		
		// 判断【实收单号】是否已经做过还款,防止并发情况还是加上
		String repayResult = this.billRepaymentService.queryBySourceBillNO(dto.getSourceBillNo(),
				repaymentSourceBillType);
		LOGGER.error("查询还款单返回数据：repayResult"+repayResult);
		LOGGER.error("PDA结清问题打印日志处理："+dto.getSourceBillNo());
		LOGGER.error("还款来源：repaymentSourceBillType"+repaymentSourceBillType);
		LOGGER.error("判断返回信息："+StringUtils.isNotEmpty(repayResult));
		if (StringUtils.isNotEmpty(repayResult)) {
			throw new SettlementException("该实收单已存在还款记录，不能重复进行还款操作");
		}
		LOGGER.debug("确认付款，校验应收数据成功！");
		return entity;
	}

	/**
	 * 根据单据类型从应收单集合中获取一条应收单数据
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-6 上午11:18:50
	 * @param list
	 * @param billType
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	private BillReceivableEntity getBillReceivableEntityByBillTypeForList(
			List<BillReceivableEntity> list, String billType) {
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		Predicate predicate = new BeanPropertyValueEqualsPredicate(SettlementConstants.BILL_TYPE,
				billType);
		List<BillReceivableEntity> lists = (List<BillReceivableEntity>) CollectionUtils.select(
				list, predicate);
		if (CollectionUtils.isNotEmpty(lists)) {
			return lists.get(0);
		}
		return null;
	}

	/**
	 * 设置还款单的属性值
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-18 上午11:12:44
	 * @param dto
	 * @param sourceBillType
	 *            还款单来源单据类型
	 * @param origOrgCode
	 *            出发部门编码
	 * @param origOrgName
	 *            出发部门名称
	 * @return
	 */
	private BillRepaymentEntity getBillRepaymentEntity(PaymentSettlementDto dto,
			String sourceBillType, String origOrgCode, String origOrgName) {
		if (dto == null) {
			throw new SettlementException("接口参数不能为空");
		}
		BillRepaymentEntity entity = new BillRepaymentEntity();

		// ID
		entity.setId(UUIDUtils.getUUID());

		// 还款单号---系统录入生成
		entity.setRepaymentNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.HK2));

		// 来源单号
		entity.setSourceBillNo(dto.getSourceBillNo());

		// 客户编码
		entity.setCustomerCode(dto.getCustomerCode());
		if (StringUtils.isNotEmpty(dto.getCustomerCode())) {
			CustomerDto custDto = this.customerService.queryCustInfoByCode(dto.getCustomerCode());
			if (null != custDto) {
				entity.setCustomerName(custDto.getName());// 客户名称
			}
		}

		// 币种 为空默认为：人民币
		entity.setCurrencyCode(dto.getCurrencyCode() != null ? dto.getCurrencyCode()
				: FossConstants.CURRENCY_CODE_RMB);

		// 设置还款金额
		if (SettlementDictionaryConstants.BILL_REPAYMENT__SOURCE_BILL_TYPE__COD
				.equals(sourceBillType)) {
			entity.setAmount(dto.getCodFee());
		} else if (SettlementDictionaryConstants.BILL_REPAYMENT__SOURCE_BILL_TYPE__FREIGHT_COLLECT
				.equals(sourceBillType)) {
			entity.setAmount(dto.getToPayFee());
		}

		// 实际还款金额
		entity.setTrueAmount(entity.getAmount());

		// 反核销金额 默认为0
		entity.setBverifyAmount(BigDecimal.ZERO);

		// 审核状态，默认未审核
		entity.setAuditStatus(SettlementDictionaryConstants.BILL_REPAYMENT__AUDIT_STATUS__NOT_AUDIT);

		// 单据状态
		entity.setStatus(SettlementDictionaryConstants.BILL_REPAYMENT__STATUS__SUBMIT);

		// 生成方式
		entity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);

		// 支付方式
		entity.setPaymentType(dto.getPaymentType());

		// 汇款编号 or 编号 or 银联交易流水号
		if(StringUtil.equals(dto.getPaymentType(),SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER)
				|| StringUtil.equals(dto.getPaymentType(),SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__NOTE)){
			entity.setOaPaymentNo(dto.getPaymentNo());
		}
		if (StringUtil.equals(dto.getPaymentType(),SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE)){
			entity.setOnlinePaymentNo(dto.getPaymentNo());
		}
		if (StringUtil.equals(dto.getPaymentType(),SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD)
				&& StringUtil.isNotBlank(dto.getPaymentNo())){
				entity.setBatchNo(dto.getPaymentNo());
		} else {
			entity.setBatchNo(dto.getBatchNo());//银联交易流水号
		}

		// 创建部门编码
		entity.setCreateOrgCode(dto.getDestOrgCode());

		// 创建部门名称
		entity.setCreateOrgName(dto.getDestOrgName());

		// 收款部门编码为：到达部门编码
		entity.setCollectionOrgCode(dto.getDestOrgCode());

		// 收款部门名称
		entity.setCollectionOrgName(dto.getDestOrgName());

		// BUG-20628 还款单不需要收入部门及收入部门公司
		// 收入部门编码
		// entity.setGeneratingOrgCode(origOrgCode);//应收单的始发部门编码

		// 收入部门名称
		// entity.setGeneratingOrgName(origOrgName);//应收单的始发部门名称

		// 来源单据类型
		entity.setSourceBillType(sourceBillType);

		// 单据状态
		entity.setStatus(SettlementDictionaryConstants.BILL_REPAYMENT__STATUS__SUBMIT);

		// 是否有效 -有效
		entity.setActive(FossConstants.ACTIVE);

		// 是否红单
		entity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);

		// 是否初始化-默认为“N”
		entity.setIsInit(FossConstants.NO);

		// 版本号
		entity.setVersionNo(FossConstants.INIT_VERSION_NO);

		Date date = new Date();

		// 记账日期
		entity.setAccountDate(date);

		// 业务日期
		entity.setBusinessDate(date);

		// 单据类型
		entity.setBillType(SettlementDictionaryConstants.BILL_REPAYMENT__BILL_TYPE__REPAYMENT);

		// 运单号 为了方便后续查询，实收货款时，在还款单记录中设置运单号属性
		entity.setWaybillNo(dto.getWaybillNo());
		
		entity.setPosSerialNum(dto.getPosSerialNum());//POS串号

        /**
         * 结清方式
         */
        entity.setSettleApproach(dto.getSettleApproach());
		return entity;
	}

	/**
	 * 处理之后的数据，统一调用核销方法接口
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-18 上午11:12:44
	 * @param repaymentEntity
	 * @param receivableEntity
	 * @param currentInfo
	 */
	private void writeoffRepaymentAndReceibable(BillRepaymentEntity repaymentEntity,
			BillReceivableEntity receivableEntity, CurrentInfo currentInfo) {
		if (repaymentEntity != null && receivableEntity != null) {
			BillWriteoffOperationDto dto = new BillWriteoffOperationDto();
			dto.setBillRepaymentEntity(repaymentEntity);

			// 设置应收单信息
			dto.setBillReceivableEntitys(Arrays.asList(receivableEntity));

			// 设置核销批次号
			dto.setWriteoffBatchNo(this.settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.HX_BN));

			// 生成方式-自动生成
			dto.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);

			// 调用还款冲应收 核销接口
			this.billWriteoffService.writeoffRepaymentAndReceibable(dto, currentInfo);
		}
	}
	
	/**
	 * 243921
	 * H-D 校验到付运费应收单的总金额与运单的到付金额-代收金额是否相等，代收货款应收单的总金额与运单的代收货款金额是否相等
	 */
	private void validateReceivableAmount(WaybillEntity waybillEntity){
		if(null == waybillEntity){
			throw new SettlementException("获取运单信息异常！");
		}
		if(isPartnerDept(waybillEntity.getReceiveOrgCode())){
			//出发部门是合伙人部门--校验CR 和 PFCR			
			BillReceivableConditionDto recDto = new BillReceivableConditionDto(waybillEntity.getWaybillNo());
			recDto.setBillTypes(new String[]{
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE
			});
			recDto.setActive(FossConstants.ACTIVE);
			List<BillReceivableEntity> recList = 
					billReceivableService.queryBillReceivableByCondition(recDto);
			if(CollectionUtils.isNotEmpty(recList)){
				for(BillReceivableEntity receivableEntity:recList){
					//代收货款校验
					if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE.equals(receivableEntity.getBillType())){
						if(waybillEntity.getCodAmount().compareTo(receivableEntity.getAmount()) != 0){
							throw new SettlementException("应收代收货款金额和运单表中代收货款金额不一致，请于十分钟后进行结清操作!");
						}
						if(!waybillEntity.getCustomerPickupOrgCode().equals(receivableEntity.getDestOrgCode())){
							throw new SettlementException("运单的提货网点和代收货款应收单中到达部门不一致，请于十分钟后进行结清操作!");					
						}
					}
					//PFCR校验
					if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE.equals(receivableEntity.getBillType())){
						if(receivableEntity.getAmount().compareTo(waybillEntity.getToPayAmount().subtract(waybillEntity.getCodAmount())) != 0){
							throw new SettlementException("应收到付金额和运单表到付运费金额不一致，请于十分钟后进行结清操作!");
						}
						if(!waybillEntity.getCustomerPickupOrgCode().equals(receivableEntity.getDestOrgCode())){
							throw new SettlementException("运单的提货网点和到付运费应收单中到达部门不一致，请于十分钟后进行结清操作!");					
						}
					}
				}
			}
		}
	}

	/**
	 * 反确认付款 (客户、代理付给公司的费用) 反实收货款
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-16 上午8:46:20
	 * @param dto
	 * @param currentInfo
	 */
	@Override
	public void reversConfirmPayment(PaymentSettlementDto dto, CurrentInfo currentInfo) {
		if (dto == null) {
			throw new SettlementException("接口传入参数不能为空");
		}
		if (StringUtils.isEmpty(dto.getWaybillNo())) {
			throw new SettlementException("运单号不能为空");
		}
		if (StringUtils.isEmpty(dto.getSourceBillNo())) {
			throw new SettlementException("实收单号不能为空");
		}
		if (StringUtils.isEmpty(dto.getDestOrgCode())) {
			throw new SettlementException("到达部门编码不能为空");
		}
		
		LOGGER.error("开始反确认付款操作，运单号为：" + dto.getWaybillNo() + " 实收单号为： " + dto.getSourceBillNo());

		// 验证运单是否存在、验证是否存在未受理的更改单
		this.validateWaybill(dto);

		/*
		 * 关联的应付单没有支付，或已核销金额等于0 跟运单关联： 1、代收货款应付单 2、装卸费应付单
		 */
		BillPayableConditionDto payableConditionDto = new BillPayableConditionDto();
		payableConditionDto.setWaybillNo(dto.getWaybillNo());
		payableConditionDto.setBillTypes(new String[] {
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD,
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE });
		List<BillPayableEntity> payables = billPayableService
				.queryBillPayableByCondition(payableConditionDto);
		if (CollectionUtils.isNotEmpty(payables)) {
			for (int i = 0; i < payables.size(); i++) {
				BillPayableEntity entity = payables.get(i);
				if (entity.getVerifyAmount() != null
						&& entity.getVerifyAmount().compareTo(BigDecimal.ZERO) > 0) {
					throw new SettlementException("运单对应应付单已核销，不能进行反实收货款操作");
				}
				if (SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__YES.equals(entity
						.getPayStatus())) {
					throw new SettlementException("运单对应的应付单正在支付中或已支付，不能进行反实收货款操作");
				}
			}
		}
		
//		SaleDepartmentEntity entity = saleDepartmentService.querySaleDepartmentInfoByCode(FossUserContext.getCurrentDept().getCode());
		String configString = configurationParamsService.queryConfValueByCode(ConfigurationParamsConstants.PKP_PTP_SIGN_INIT_410);
		WaybillEntity waybillEntity = waybillManagerService.queryPartWaybillByNo(dto.getWaybillNo());
		SaleDepartmentEntity entity = saleDepartmentService.querySaleDepartmentInfoByCode(waybillEntity.getLastLoadOrgCode());
		long intTime = 0;
		if (StringUtils.isNotBlank(configString)) {
			intTime = DateUtils.convert(configString.trim(), DateUtils.DATE_TIME_FORMAT).getTime();
		}
        long destTime = waybillEntity.getBillTime().getTime();
      //354658-合伙人结清货款后，反结清异常bug修复
        boolean isPtp = false;
        if(StringUtils.isNotBlank(dto.getIsPtp())){
            isPtp = FossConstants.YES.equals(dto.getIsPtp());
        }else if(entity != null){
            //如果entity == null,那么肯定就不是合伙人
            isPtp = FossConstants.YES.equals(entity.getIsLeagueSaleDept());
        }

		if((entity != null && !isPtp) || destTime < intTime){
			LOGGER.error("开始反确认直营收款操作，运单号为：" + dto.getWaybillNo() + "部门编码为：" + entity.getCode());
			// ISSUE-3281 查询最新的签收记录，如果签收类型为POD(已签收)，则不允许继续操作
			PODEntity podEntity = podService.queryNewestPOD(dto.getWaybillNo());
			if (podEntity != null
					&& podEntity.getPodType()
							.equals(SettlementDictionaryConstants.POD_ENTITY__POD_TYPE__PROOF_OF_DELIVERY)) {
				throw new SettlementException("运单已经签收，不能结清货款");
			}
			// 根据传入的来源单号【到达实收单号】，查询是否存在代收货款还款单或到达运费还款单
			List<String> sourceBillNos = new ArrayList<String>();
			sourceBillNos.add(dto.getSourceBillNo());
			List<BillRepaymentEntity> repayments = this.billRepaymentService.queryBySourceBillNOs(
					sourceBillNos, FossConstants.ACTIVE);
			if (CollectionUtils.isEmpty(repayments)) {
				throw new SettlementException("不存在还款单记录，不能进行反实收货款操作");
			}
	
			// 根据到达实收单号，查询到还款单时，需要根据还款单信息查询对应运单的到达运费应收单和代收货款应收单信息 查询所有的应收单信息
			BillReceivableConditionDto reDto = new BillReceivableConditionDto(dto.getWaybillNo());
			reDto.setBillTypes(new String[] {
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE,
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE});
			List<BillReceivableEntity> billReceivables = billReceivableService
					.queryBillReceivableByCondition(reDto);
			if (CollectionUtils.isEmpty(billReceivables)) {
				throw new SettlementException("不存在应收单信息，不能进行反实收货款操作");
			} else {
				// 验证同一个运单号是否存在相同类型的多条应收单
				this.billReceivableService.validateWaybillForBillReceivable(billReceivables);
			}
	
			// 代收货款还款单
			BillRepaymentEntity codBillRepayment = null;// 默认为空
	
			// 到达运费还款单
			BillRepaymentEntity destBillRepayment = null;// 默认为空
			for (int i = 0; i < repayments.size(); i++) {
				BillRepaymentEntity repaymentEntity = repayments.get(i);
	
				// 来源单据类型：代收货款还款单时，需要判断是否存在代收货款应收单 不存在时，抛出异常
				if (SettlementDictionaryConstants.BILL_REPAYMENT__SOURCE_BILL_TYPE__COD
						.equals(repaymentEntity.getSourceBillType())) {
	
					// 验证代收货款应收单
					validateBillReceivableByReversConfirmPayment(billReceivables,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE);
					codBillRepayment = repaymentEntity;
				}
	
				// 来源单据类型：到达运费还款单时，需要判断是否存在到达运费应收单 不存在时，抛出异常
				else if (SettlementDictionaryConstants.BILL_REPAYMENT__SOURCE_BILL_TYPE__FREIGHT_COLLECT
						.equals(repaymentEntity.getSourceBillType())) {
					for(BillReceivableEntity receEntity : billReceivables){
						if(receEntity.getBillType().equals(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE)){
							// 验证到达运费应收单信息
							validateBillReceivableByReversConfirmPayment(
									billReceivables,
									SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE);
						}else if(receEntity.getBillType().equals(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE)){
							// DDW
							validateBillReceivableByReversConfirmPayment(
									billReceivables,
									SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE);
						}
					}
					destBillRepayment = repaymentEntity;
				}
			}
	
			// 代收货款还款单
			this.writeBackBillRepayment(codBillRepayment, currentInfo);
	
			// 到付运费还款单
			this.writeBackBillRepayment(destBillRepayment, currentInfo);
		}else if(entity != null && isPtp && destTime > intTime){
			LOGGER.error("开始反确认合伙人收款操作，运单号为：" + dto.getWaybillNo() + "部门编码为：" + entity.getCode());
			BillReceivableConditionDto reDto = new BillReceivableConditionDto(dto.getWaybillNo());
			reDto.setBillTypes(new String[] {
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE,
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__ORIGINAL_RECEIVABLE,
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE,
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_RECEIVABLE});
			List<BillReceivableEntity> billReceivables = billReceivableService.queryBillReceivableByCondition(reDto);
			//ddw
			String receivableNo = "";
			String active = FossConstants.ACTIVE;
			String createType = SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO;
			for(int i = 0; i < billReceivables.size(); i++){
				BillReceivableEntity recRntity = billReceivables.get(i);
				if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(recRntity.getPaymentType())){
					if(!SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__ORIGINAL_RECEIVABLE.equals(recRntity.getBillType())
							&& !SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_RECEIVABLE.equals(recRntity.getBillType())){
						receivableNo = recRntity.getReceivableNo();
						List<BillWriteoffEntity> list = billWriteoffService.queryBillWriteoffByBeginOrEndNo(receivableNo, active, createType);
						for(BillWriteoffEntity writeoffEntity : list){
							billWriteoffService.writeBackBillWriteoffById(writeoffEntity.getId(),currentInfo);
						}
					}
				}else if(!SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(recRntity.getPaymentType())){
					receivableNo = recRntity.getReceivableNo();
					List<BillWriteoffEntity> list = billWriteoffService.queryBillWriteoffByBeginOrEndNo(receivableNo, active, createType);
					for(BillWriteoffEntity writeoffEntity : list){
						billWriteoffService.writeBackBillWriteoffById(writeoffEntity.getId(),currentInfo);
					}
				}
			}
		}else{
			throw new SettlementException("单据错误，运单到达部门既不是合伙人也不是直营！");
		}

		LOGGER.info(" 反实收货款成功 ");
	}

	/**
	 * 红冲还款单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-10 上午10:53:48
	 * @param entity
	 * @param currentInfo
	 */
	private void writeBackBillRepayment(BillRepaymentEntity entity, CurrentInfo currentInfo) {
		if (entity != null) {
			// 红冲代收货款还款单
			this.billRepaymentService.writeBackBillRepayment(entity, currentInfo);

			// 释放财务自助汇款编号
			if (StringUtils.isNotEmpty(entity.getOaPaymentNo())
					&& FossConstants.NO.equals(entity.getIsInit())// 是否初始化为否，是foss生成的还款单，为“是”是原来ERP导入的不释放已占用的汇款编号
			// 2013、03、25日需求变更 ERP迁入FOSS的电汇和支票类还款单/预收单，作废时不要找财务自助释放已占用的汇款
			) {
				this.fossToFinanceRemittanceService.releaseClaim(entity.getAmount(),
						entity.getOaPaymentNo(), currentInfo.getCurrentDeptCode(),
						entity.getRepaymentNo());
			}

			/**
			 * 释放网上支付金额
			 */
			if (StringUtils.isNotEmpty(entity.getOnlinePaymentNo()) && FossConstants.NO.equals(entity.getIsInit())
					&& SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(entity.getPaymentType())) {
				financeOnlinePayWSProxy.release(entity.getOnlinePaymentNo(),entity.getAmount());
			}

			// 反核销（还款冲应收操作）
			this.billWriteoffService.writeBackBillWriteoffByRepayment(entity.getRepaymentNo(),
					currentInfo);
		}
	}

	/**
	 * 反实收货款时，验证还款单对应的应收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-20 下午1:49:33
	 * @param list
	 * @param receivableType
	 * @param receivableType
	 * @return
	 * @see
	 */
	private void validateBillReceivableByReversConfirmPayment(List<BillReceivableEntity> list,
			String receivableType) {
		String receivableName = this.getBillNameByType(receivableType);

		// 根据应收单类型获取应收单实体信息
		BillReceivableEntity billReceivableEntity = this.getBillReceivableEntityByBillTypeForList(
				list, receivableType);
		if (billReceivableEntity == null) {
			throw new SettlementException("不存在" + receivableName + "应收单，不能进行反实收货款操作！");
		}

		// 应收单类型为到付运费应收单，并且运单已签收（运单签收后，应收单的确认收入日期不为空）
		if (billReceivableEntity.getConrevenDate() != null
				&& SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE
						.equals(billReceivableEntity.getBillType())
				&&
				// 到付运费应收单的付款方式为：临欠或月结，时抛出异常信息
				(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT// 临欠
						.equals(billReceivableEntity.getPaymentType()) || SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT// 月结
						.equals(billReceivableEntity.getPaymentType()))) {
			throw new SettlementException("运单已签收，且该运单的付款方式非到付，不能调用此接口！");
		}
	}

	/**
	 * 
	 * ptp合伙人冲销
	 * @author 043258-foss-zhaobin
	 * @date 2016-1-11 下午2:31:59
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.IPaymentSettlementService#confirmPTPPayment(com.deppon.foss.module.settlement.common.api.shared.dto.PaymentSettlementDto, com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void confirmPTPPaymentAndWriteOff(PaymentSettlementDto dto,CurrentInfo currentInfo) {
		if (dto == null) {
			throw new SettlementException("接口传入参数不能为空");
		}
		
		// 验证传入参数的合法性, 合伙人的暂时去掉校验
//		validateConfirmToPaymentParams(dto);

		LOGGER.info("开始合伙人冲销，运单号为：" + dto.getWaybillNo());

		// 验证运单是否存在和是否存在未受理的更改单
		this.validateWaybill(dto);
		//存放应收单查询过滤条件 dto
		BillReceivableConditionDto receivableDto = new BillReceivableConditionDto();
		//预收单参数dto
		BillDepositReceivedDto billDepositReceivedDto = new BillDepositReceivedDto();
		//核销操作Dto
		BillWriteoffOperationDto writeoffDto = new BillWriteoffOperationDto();
		receivableDto.setWaybillNo(dto.getWaybillNo());
		//查询出以下四类应收单
		receivableDto.setBillTypes(new String[]{SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,
																	SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE,
																	SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__ORIGINAL_RECEIVABLE,
																	SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE,
																	SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_RECEIVABLE});
		// 查询合伙人应收单信息
		List<BillReceivableEntity> billReceivableLists = billReceivableService.queryBillReceivableByCondition(receivableDto);
		//如果应收单不为空
		if (CollectionUtils.isNotEmpty(billReceivableLists)) {
			//循环遍历应收单
			for (BillReceivableEntity billReceivableEntity : billReceivableLists) {
				LOGGER.info("签收ptp合伙人冲销，查询应收单。运单号:" +billReceivableEntity.getWaybillNo() + 
						",应收单类型:"+billReceivableEntity.getBillType()+
						"扣款状态:"+billReceivableEntity.getWithholdStatus()+
						"支付方式:"+billReceivableEntity.getPaymentType());
				//D-H，如果到达是网上已支付，则不进行校验扣款状态
				boolean isOnlinePay = arriveOnlinePay(billReceivableEntity.getWaybillNo(), billReceivableEntity.getBillType(), SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE);
				
				//如果是到达应收单(DR)或者到付运费应收单(PFCR)未扣款，则调用ptp接口进行扣款
				if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE
						.equals(billReceivableEntity.getBillType())
						|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE
								.equals(billReceivableEntity.getBillType())) {
					if(!SettlementDictionaryConstants.STL__WITHHOLD_STATUS__WITHHOLD_SUCCESS.equals(billReceivableEntity.getWithholdStatus())){
						//调用ptp接口扣款
						this.deductPtpToPayReceivable(billReceivableEntity,currentInfo);
					}
				}
				
				//扣款状态不为空并且不等于扣款成功
				if(StringUtil.isNotEmpty(billReceivableEntity.getWithholdStatus()) 
						&& !SettlementDictionaryConstants.STL__WITHHOLD_STATUS__WITHHOLD_SUCCESS.equals(billReceivableEntity.getWithholdStatus())
						&& !SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(billReceivableEntity.getPaymentType()) && !isOnlinePay){

					// 扣款不成功，不能签收。
					LOGGER.error("运单" + dto.getWaybillNo()
							+ "。该运单未扣款或扣款失败，无法签收！");
					throw new SettlementException("该运单未扣款或扣款失败，无法签收！");
					
				}else{
					//判断是否为月结，如果为月结判断是否为始发提成和委托派费，如果是则不自动核销
					if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(billReceivableEntity.getPaymentType())){
						if(!SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__ORIGINAL_RECEIVABLE.equals(billReceivableEntity.getBillType())
								&& !SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_RECEIVABLE.equals(billReceivableEntity.getBillType())){
							//设置客户编码
							billDepositReceivedDto.setCustomerCode(billReceivableEntity.getCustomerCode());
							//设置部门编码
							billDepositReceivedDto.setCollectionOrgCode(billReceivableEntity.getReceivableOrgCode());
							billDepositReceivedDto.setQueryType(SettlementConstants.TAB_QUERY_BY_DATE);
							//按照客户编码和部门编码查询预收单
							BillDepWriteoffBillRecDto billDepWriteoffBillRecDto = billDepWriteoffBillRecService.queryBillDep(billDepositReceivedDto);
							//ddw
							if(CollectionUtils.isEmpty(billDepWriteoffBillRecDto.getBillDepositreceivedEntityList())){
								LOGGER.error("合伙人没有预存款，不能签收。");
								throw new SettlementException("合伙人没有预存款，不能签收，合伙人编码：" + billReceivableEntity.getCustomerCode() + 
										"，应收部门：" + billReceivableEntity.getReceivableOrgCode());
							}
							LOGGER.info("签收ptp合伙人冲销，查询预收单。预收单数量:"+billDepWriteoffBillRecDto.getBillDepositreceivedEntityList().size());
							//核销操作Dto加入预收单List
							writeoffDto.setBillDepositReceivedEntitys(billDepWriteoffBillRecDto.getBillDepositreceivedEntityList());
							//插入应收单
							List<BillReceivableEntity> billReceivableEntitys = new ArrayList<BillReceivableEntity>();
							billReceivableEntitys.add(billReceivableEntity);
							//核销操作Dto加入应收单List
							writeoffDto.setBillReceivableEntitys(billReceivableEntitys);
							
							// 获取核销批次号
							String writeoffBillBatchNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.HX_BN);
							writeoffDto.setWriteoffBatchNo(writeoffBillBatchNo);
							writeoffDto.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);
							
							//如果预收单和应收单不为空-预收冲应收
							if (CollectionUtils.isNotEmpty(writeoffDto.getBillDepositReceivedEntitys())&& CollectionUtils.isNotEmpty(writeoffDto.getBillReceivableEntitys())) {
								//调用统一核销方法
								billWriteoffService.writeoffDepositAndReceivable(writeoffDto, currentInfo);
							}
						}
					}else if(!SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(billReceivableEntity.getPaymentType())){
						//设置客户编码
						billDepositReceivedDto.setCustomerCode(billReceivableEntity.getCustomerCode());
						//设置部门编码
						billDepositReceivedDto.setCollectionOrgCode(billReceivableEntity.getReceivableOrgCode());
						billDepositReceivedDto.setQueryType(SettlementConstants.TAB_QUERY_BY_DATE);
						//按照客户编码和部门编码查询预收单
						BillDepWriteoffBillRecDto billDepWriteoffBillRecDto = billDepWriteoffBillRecService.queryBillDep(billDepositReceivedDto);
						//ddw
						if(CollectionUtils.isEmpty(billDepWriteoffBillRecDto.getBillDepositreceivedEntityList())){
							throw new SettlementException("合伙人没有预存款，不能签收，合伙人编码：" + billReceivableEntity.getCustomerCode() + 
									"，应收部门：" + billReceivableEntity.getReceivableOrgCode());
						}
						LOGGER.info("签收ptp合伙人冲销，查询预收单。预收单数量:"+billDepWriteoffBillRecDto.getBillDepositreceivedEntityList().size());
						//核销操作Dto加入预收单List
						writeoffDto.setBillDepositReceivedEntitys(billDepWriteoffBillRecDto.getBillDepositreceivedEntityList());
						//插入应收单
						List<BillReceivableEntity> billReceivableEntitys = new ArrayList<BillReceivableEntity>();
						billReceivableEntitys.add(billReceivableEntity);
						//核销操作Dto加入应收单List
						writeoffDto.setBillReceivableEntitys(billReceivableEntitys);
						
						// 获取核销批次号
						String writeoffBillBatchNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.HX_BN);
						writeoffDto.setWriteoffBatchNo(writeoffBillBatchNo);
						writeoffDto.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);
						
						//如果预收单和应收单不为空-预收冲应收
						if (CollectionUtils.isNotEmpty(writeoffDto.getBillDepositReceivedEntitys())&& CollectionUtils.isNotEmpty(writeoffDto.getBillReceivableEntitys())) {
							//调用统一核销方法
							billWriteoffService.writeoffDepositAndReceivable(writeoffDto, currentInfo);
						}
					}
				}	
			}
		}
	}
	
	
	
	/**
	 * 到达应收单(DR)或者到付运费应收单(PFCR)，调用ptp接口进行扣款
	 * @author gpz
	 * @date 2016年9月21日
	 * @param billReceivableEntity 应收单
	 * @param currentInfo 当前登录用户信息
	 */
	private void deductPtpToPayReceivable(
			BillReceivableEntity billReceivableEntity, CurrentInfo currentInfo) {
		//运单号
		String waybillNo = billReceivableEntity.getWaybillNo();
		//应收单金额未核销
		if (BigDecimal.ZERO.compareTo(billReceivableEntity.getUnverifyAmount()) < 0) {
			
			// ptp接口地址URL
			FossConfigEntity configEntity = fossConfigEntityService
					.queryFossConfigEntityByServerCode(SignConstants.PTP_PFCR_ESB_SYN_SERVER_CODE);
			if (null == configEntity
					|| StringUtil.isEmpty(configEntity.getEsbAddr())) {
				LOGGER.error("调用PTP到付金额扣款。读取esb配置地址有误!接口编码:"
						+ SignConstants.PTP_PFCR_ESB_SYN_SERVER_CODE);
				throw new SettlementException("调用PTP到付金额扣款接口 ：读取esb配置地址有误!");
			} 
			//ptp接口地址url
			String url = configEntity.getEsbAddr();
			//开发环境地址
			//url = "http://10.224.70.58:8081/ptp-syncfoss-itf/v1/ptp/saleflow/saleFlowDeductService/deductDRBySourceBill";
			LOGGER.info("查询到的PTP到付金额扣款的接口地址为：" + url);
			
			//调用ptp接口传递的参数
			PtpToPayDeductReqDto request = new PtpToPayDeductReqDto();
			//运单号
			request.setWaybillNo(billReceivableEntity.getWaybillNo());
			//当前操作人工号
			request.setOperatorCode(currentInfo.getEmpCode());
			//当前操作人名称
			request.setOperatorName(currentInfo.getEmpName());
			//通过运单号获取运单信息 
			WaybillEntity wayBillEntity = waybillManagerService
					.queryWaybillBasicByNo(waybillNo);
			if(null == wayBillEntity){
				LOGGER.error("调用PTP到付金额扣款。" + "该运单号：" + waybillNo
						+ ", 不存在!");
				throw new SettlementException("该运单号：" + waybillNo + " 不存在!");
			}
			// 判断合伙人信息-不能为空-传标杆编码
			if (StringUtil.isNotEmpty(wayBillEntity.getLastLoadOrgCode())) {
				OrgAdministrativeInfoEntity orgAdminEntity = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCode(wayBillEntity
								.getLastLoadOrgCode());
				if (null != orgAdminEntity
						&& StringUtil.isNotEmpty(orgAdminEntity.getUnifiedCode())) {
					request.setPartnerOrgCode(orgAdminEntity.getUnifiedCode());
				} else {
					LOGGER.error("调用PTP到付金额扣款失败。合伙人到达部门标杆编码为空!");
					throw new SettlementException("合伙人到达部门标杆编码为空!");
				}
			} else {
				LOGGER.error("调用PTP到付金额扣款。" + "该运单号：" + waybillNo
						+ ", 的到达部门不存在!");
				throw new SettlementException("该运单号：" + waybillNo + " 的到达部门不存在!");
			}
			
			
			try {
				// 请求参数
				RequestEntity requestEntity = new StringRequestEntity(
						JSON.toJSONString(request), "application/json", "UTF-8");
				LOGGER.info("--调用PTP到付金额扣款接口请求参数：" + JSON.toJSONString(request));
				// 调用接口获取返回的执行结果
				String responseBody = HttpClientUtil.postRequest(url,
						requestEntity);
				LOGGER.info("--调用PTP到付金额扣款接口响应信息: " + responseBody);
				
				// 将返回的json字符串转成JSONObject对象
				JSONObject response = JSON.parseObject(responseBody);
				if (!response.getBooleanValue("result")) {
					LOGGER.error("调用PTP到付金额扣款接口异常。"
							+ response.getString("message"));
					throw new SettlementException(response.getString("message"));
				}
				
			} catch (UnsupportedEncodingException e) {
				LOGGER.error("调用PTP到付金额扣款接口异常。数据错误", e);
				throw new SettlementException(
						"调用PTP到付金额扣款。数据错误！");
			} catch (SettlementException e) {
				throw new SettlementException("到付金额扣款失败！" + e.getErrorCode());
			} catch (Exception e) {
				LOGGER.error("调用PTP到付金额扣款接口异常", e);
				throw new SettlementException("调用PTP到付金额扣款接口异常!"
						+ e.getMessage());
			}
			
			//扣款状态为扣款成功
			billReceivableEntity.setWithholdStatus(SettlementDictionaryConstants.STL__WITHHOLD_STATUS__WITHHOLD_SUCCESS);
			//更新(DR或者PFCR)应收单扣款状态为扣款成功
			billReceivableService.updateBillReceivableWithholdStatus(billReceivableEntity);
		}
	}

	/**
	 * 到达是否网上已支付校验
	 * @param waybillNo 运单号
	 * @param billType 应收单单据类型
	 * @param payType 还款单付款方式
	 * @return
	 */
	public boolean arriveOnlinePay(String waybillNo, String billType, String payType) {
		//判断传入的是否为空
		if(StringUtils.isEmpty(waybillNo) && StringUtils.isEmpty(billType)  && StringUtils.isEmpty(payType) ){
			LOGGER.error("运单号或单据类型或付款方式错误，不能进行到达网上支付校验!");
			throw new SettlementException("运单号或单据类型或付款方式错误，不能进行到达网上支付校验!");
		}
		
		List<ArriveOnlineDto>  list = paymentSettlementDao.arriveOnlinePay(waybillNo, billType, payType);
		if (null == list || list.size() == 0) {
			return false;
		}
		for (ArriveOnlineDto arriveOnlineDto : list) {
			if (null != arriveOnlineDto) {
				BigDecimal account = (null == arriveOnlineDto.getAmount() ? BigDecimal.ZERO : arriveOnlineDto.getAmount());
				if (arriveOnlineDto.getAmount().equals(arriveOnlineDto.getVerifyAmount()) && !BigDecimal.ZERO.equals(account)) {
					return true;
				}
			}
		}
		return false;
	}

    /**
     * NCI----释放相应的银行卡金额
     * @param dto    存放运单信息和到达部门信息Dto
     * @param info   当前用户
     */
    @Override
    public void reversPosCard(PaymentSettlementDto dto, CurrentInfo info) {
        // 根据传入的来源单号【到达实收单号】，查询是否存在代收货款还款单或到达运费还款单
        List<String> sourceBillNos = new ArrayList<String>();
        sourceBillNos.add(dto.getSourceBillNo());
        List<BillRepaymentEntity> repayments = this.billRepaymentService.queryBySourceBillNOs(sourceBillNos, FossConstants.ACTIVE);
        for (BillRepaymentEntity repayment : repayments) {
			if (SettlementDictionaryConstants.BILL_REPAYMENT__SOURCE_BILL_TYPE__COD.equals(repayment.getSourceBillType()) || SettlementDictionaryConstants.BILL_REPAYMENT__SOURCE_BILL_TYPE__FREIGHT_COLLECT.equals(repayment.getSourceBillType())) {
				if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(repayment.getPaymentType())) {
					// 释放指定金额 实际还款金额
                BigDecimal trueAmount = repayment.getTrueAmount();
					// 交易流水号
                String batchNo = repayment.getBatchNo();
					// 运单号
                String waybillNo = repayment.getWaybillNo();
                    //释放指定类型的金额  W2 结清货款
					wscManageService.reversPosCardPosCard(trueAmount, batchNo, waybillNo,"W2");
            }
        }
    }
    }

    /**
	 * 根据单据类型获取单据名称
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-7 上午11:30:33
	 * @param billType
	 * @return
	 */
	private String getBillNameByType(String billType) {
		// 调用综合管理DictUtil工具类接口
		return DictUtil.rendererSubmitToDisplay(billType,
				DictionaryConstants.BILL_RECEIVABLE__BILL_TYPE);
	}
	
	/**
	 * 
	 * @param orgCode
	 * @return
	 */
	private boolean isPartnerDept(String orgCode){
		//DDW
		SaleDepartmentEntity entity = saleDepartmentService.querySaleDepartmentInfoByCode(orgCode);
		//判断到达部门是否为合伙人，如果是合伙人则在PTP系统生成应收单
		if(entity != null && FossConstants.YES.equals(entity.getIsLeagueSaleDept())){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * @author 268217
	 * @param ent
	 * @param currentInfo
	 */
	public void buildBillReceivableOrig(BillReceivableEntity ent,CurrentInfo currentInfo){
		BillReceivableEntity entity=new BillReceivableEntity();
		BeanUtils.copyProperties(ent, entity);
		// 日期初始化到秒
		Date date = Calendar.getInstance().getTime();
		entity.setId(UUIDUtils.getUUID());
		entity.setAccountDate(date); // 记账日期
		// 结算客户：到付客户
		entity.setCustomerCode(ent.getReceiveCustomerCode()); 
		entity.setCustomerName(ent.getReceiveCustomerName());
		// 生成新单据
		entity.setReceivableNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YS2)); // 应收单号
		entity.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE); // 单据子类型
		//设置付款方式为理赔出库
		entity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT);
		// 是否红单--非红单
		entity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		// 是否有效
		entity.setActive(FossConstants.ACTIVE);
		// 红冲生成蓝单后设置对账单号，为默认单号
		entity.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);
		//收银确认时间
		//entity.setConrevenDate(date);
		entity.setCreateDate(date);
		entity.setModifyDate(date);
		billReceivableService.addBillReceivable(entity, currentInfo);
	}
	/**
	 * @param settlementCommonService
	 *            the settlementCommonService to set
	 */
	public void setSettlementCommonService(ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	/**
	 * @param billReceivableService
	 *            the billReceivableService to set
	 */
	public void setBillReceivableService(IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	/**
	 * @param billRepaymentService
	 *            the billRepaymentService to set
	 */
	public void setBillRepaymentService(IBillRepaymentService billRepaymentService) {
		this.billRepaymentService = billRepaymentService;
	}

	/**
	 * @param billPayableService
	 *            the billPayableService to set
	 */
	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	/**
	 * @param customerBargainService
	 *            the customerBargainService to set
	 */
	public void setCustomerBargainService(ICustomerBargainService customerBargainService) {
		this.customerBargainService = customerBargainService;
	}

	/**
	 * @param billWriteoffService
	 *            the billWriteoffService to set
	 */
	public void setBillWriteoffService(IBillWriteoffService billWriteoffService) {
		this.billWriteoffService = billWriteoffService;
	}

	/**
	 * @param statementOfAccountService
	 *            the statementOfAccountService to set
	 */
	public void setStatementOfAccountService(IStatementOfAccountService statementOfAccountService) {
		this.statementOfAccountService = statementOfAccountService;
	}

	/**
	 * @param waybillRfcService
	 *            the waybillRfcService to set
	 */
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}

	/**
	 * @param waybillManagerService
	 *            the waybillManagerService to set
	 */
	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	/**
	 * @param customerService
	 *            the customerService to set
	 */
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	/**
	 * @param fossToFinanceRemittanceService
	 *            the fossToFinanceRemittanceService to set
	 */
	public void setFossToFinanceRemittanceService(
			IFossToFinanceRemittanceService fossToFinanceRemittanceService) {
		this.fossToFinanceRemittanceService = fossToFinanceRemittanceService;
	}

	/*public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}*/
	
	public void setNonfixedCustomerService(INonfixedCustomerService nonfixedCustomerService) {
		this.nonfixedCustomerService = nonfixedCustomerService;
	}

	/**
	 * @param podService the podService to set
	 */
	public void setPodService(IPODService podService) {
		this.podService = podService;
	}

	public void setFinanceOnlinePayWSProxy(IFinanceOnlinePayWSProxy financeOnlinePayWSProxy) {
		this.financeOnlinePayWSProxy = financeOnlinePayWSProxy;
	}
	
	/**
	 * @SET
	 * @param grayCustomerService
	 */
	public void setGrayCustomerService(IGrayCustomerService grayCustomerService) {
		this.grayCustomerService = grayCustomerService;
	}

	public void setBillDepWriteoffBillRecService(
			IBillDepWriteoffBillRecService billDepWriteoffBillRecService) {
		this.billDepWriteoffBillRecService = billDepWriteoffBillRecService;
	}

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setPaymentSettlementDao(IPaymentSettlementDao paymentSettlementDao) {
		this.paymentSettlementDao = paymentSettlementDao;
	}
	
	public void setBillClaimService(IBillClaimService billClaimService) {
		this.billClaimService = billClaimService;
	}

    public void setWscManageService(IWSCManageService wscManageService) {
        this.wscManageService = wscManageService;
    }

	/**
	 * @return the fossConfigEntityService
	 */
	public IFossConfigEntityService getFossConfigEntityService() {
		return fossConfigEntityService;
	}

	/**
	 * @param fossConfigEntityService the fossConfigEntityService to set
	 */
	public void setFossConfigEntityService(
			IFossConfigEntityService fossConfigEntityService) {
		this.fossConfigEntityService = fossConfigEntityService;
	}

	/**
	 * @return the orgAdministrativeInfoService
	 */
	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}

	/**
	 * @param orgAdministrativeInfoService the orgAdministrativeInfoService to set
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
    
}
