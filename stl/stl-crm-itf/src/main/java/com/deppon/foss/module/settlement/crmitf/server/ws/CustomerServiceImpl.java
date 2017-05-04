/**
 * Copyright 2013 STL TEAM
 * PROJECT NAME	: stl-crm-itf
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/crmitf/server/ws/CustomerServiceImpl.java
 * FILE NAME        	: CustomerServiceImpl.java
 * AUTHOR			: FOSS结算系统开发组
 * HOME PAGE		: http://www.deppon.com
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 */

package com.deppon.foss.module.settlement.crmitf.server.ws;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import javax.jws.WebService;
import javax.xml.ws.Holder;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.esb.header.ESBHeader;
import com.deppon.foss.customerservice.CommonException;
import com.deppon.foss.customerservice.CustomerService;
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
import com.deppon.foss.esb.crm.server.order.IsCustomerBlankOutList;
import com.deppon.foss.esb.crm.server.order.IsCustomerBlankOutRequest;
import com.deppon.foss.esb.crm.server.order.IsCustomerBlankOutResponse;
import com.deppon.foss.esb.crm.server.order.QueryMoneyRequest;
import com.deppon.foss.esb.crm.server.order.QueryMoneyResponse;
import com.deppon.foss.esb.crm.server.order.ServiceChargeStatusUpdateRequest;
import com.deppon.foss.esb.crm.server.order.ServiceChargeStatusUpdateResponse;
import com.deppon.foss.esb.util.ESBHeaderUtil;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.closing.api.server.service.ICRMPayableBillService;
import com.deppon.foss.module.settlement.closing.api.shared.inteface.domain.ClaimsPayBillGenerateRes;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.ICreditCustomerService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CustomerInUseDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.esb.crm.server.order.SyncOrderLockInfoRequest;
import com.deppon.foss.esb.crm.server.order.SyncOrderLockInfoResponse;


/**
 * 
 * CRM服务实现：实现装卸费状态修改服务
 * 
 * @author 046644-foss-zengbinwen
 * @date 2012-12-1 下午2:23:47
 */
@WebService
public class CustomerServiceImpl implements CustomerService{

	/**
	 * 日志
	 */
	private final Logger logger = LogManager.getLogger(getClass());

	/**
	 * 应付单服务
	 */
	private IBillPayableService billPayableService;

	/**
	 * 客户信用额度服务
	 */
	private ICreditCustomerService creditCustomerService;

	/** CRM应付单服务. */
	private ICRMPayableBillService crmPayableBillService;
	
	
	/**
	 * @get
	 * @return crmPayableBillService
	 */
	public ICRMPayableBillService getCrmPayableBillService() {
		/*
		 * @get
		 * @return crmPayableBillService
		 */
		return crmPayableBillService;
	}

	
	/**
	 * @set
	 * @param crmPayableBillService
	 */
	public void setCrmPayableBillService(
			ICRMPayableBillService crmPayableBillService) {
		/*
		 *@set
		 *@this.crmPayableBillService = crmPayableBillService
		 */
		this.crmPayableBillService = crmPayableBillService;
	}

	/**
	 * @param billPayableService
	 */
	public void setBillPayableService(IBillPayableService billPayableService){
		this.billPayableService = billPayableService;
	}

	public void setCreditCustomerService(ICreditCustomerService creditCustomerService){
		this.creditCustomerService = creditCustomerService;
	}

	/**
	 * 
	 * 非结算系统实现
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-1 下午1:45:12
	 * @see com.deppon.foss.customerservice.CustomerService#queryMoney(javax.xml.ws.Holder,
	 *      com.deppon.esb.inteface.domain.crm.QueryMoneyRequest)
	 */
	@Deprecated
	@Override
	public QueryMoneyResponse queryMoney(Holder<ESBHeader> esbHeader, QueryMoneyRequest payload) throws CommonException{
		// 待实现
		return null;
	}

	/**
	 * 
	 * 非结算系统实现
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-1 下午1:45:45
	 * @see com.deppon.foss.customerservice.CustomerService#bindOrder(javax.xml.ws.Holder,
	 *      com.deppon.esb.inteface.domain.crm.BindOrderRequest)
	 */
	@Deprecated
	@Override
	public BindOrderResponse bindOrder(Holder<ESBHeader> esbHeader, BindOrderRequest payload) throws CommonException{
		// 待实现
		return null;
	}

	/**
	 * 
	 * 装卸费状态修改
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-1 下午1:46:02
	 * @see com.deppon.foss.customerservice.CustomerService#updateServiceChargeStatus(javax.xml.ws.Holder,
	 *      com.deppon.esb.inteface.domain.crm.ServiceChargeStatusUpdateRequest)
	 */
	@Transactional
	@Override
	public ServiceChargeStatusUpdateResponse updateServiceChargeStatus(Holder<ESBHeader> esbHeader,
			ServiceChargeStatusUpdateRequest payload) throws CommonException{
		// 记录日志
		logger.info(payload.getWaybillNumber());
		// 记录日志
		logger.info(payload.isStatus());

		// 设置ESBHeader和返回值
		ESBHeaderUtil.processEsbHeaderForResponse(esbHeader);
		// 实例化返回实体
		ServiceChargeStatusUpdateResponse response = new ServiceChargeStatusUpdateResponse();
		// 设置返回结果
		response.setResult(true);
		try{

			// 运单号
			String waybillNo = payload.getWaybillNumber();
			// 查询运单号对应的装卸费应付单
			List<BillPayableEntity> billPayables = billPayableService
					.queryByWaybillNosAndByBillTypes(
							Arrays.asList(waybillNo),
							Arrays.asList(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE));
			// 判空
			if (CollectionUtils.isNotEmpty(billPayables)){
				// 如果装卸费应付单不唯一，抛出异常
				if (billPayables.size() != 1){
					throw new SettlementException("存在多个有效的装卸费应付单");
				}
				// 获取应付单实体
				BillPayableEntity entity = billPayables.get(0);
				// 退运费申请被退回或审批不同意时,true代表可支付
				if (payload.isStatus()){
					// 装卸费生效由定时任务处理，此处不再做任何处理
				}

				// CRM退运费申请起草成功时
				else{ // false代表不可支付
					// 如果应付单已生效，则失效应付单
					String effectiveStatus = entity.getEffectiveStatus();
					// 生效状态处理
					if (SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES
							.equals(effectiveStatus)){
						// 失效应付单
						billPayableService.disableBillPayable(entity,
								SettlementUtil.getCRMCurrentInfo());
					}
				}
			}
			// 异常处理
		} catch (BusinessException e){
			// 日志处理
			logger.error(e.getMessage(), e);
			// 声明返回信息
			response.setResult(false);
			// 声明返回信息
			response.setReason(e.getMessage());
		}
		// 返回信息
		return response;
	}

	/**
	 * 
	 * 非结算系统实现
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-1 下午1:46:54
	 * @see com.deppon.foss.customerservice.CustomerService#sendGoodsBillReceive(javax.xml.ws.Holder,
	 *      com.deppon.esb.inteface.domain.crm.GoodsBillReceiveRequest)
	 */
	@Deprecated
	@Override
	public GoodsBillReceiveResponse sendGoodsBillReceive(
			Holder<ESBHeader> esbHeader, GoodsBillReceiveRequest payload)
			throws CommonException{
		// 异常处理
		return null;
	}

	/**
	 * 判断客户是否可作废（查询应收单、现金收款单、应付单业务是否完结）-ISSUE-2637
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2013-5-6 下午4:30:23
	 * @param arg0
	 * @param arg1
	 * @return
	 * @throws CommonException
	 * @see com.deppon.foss.customerservice.CustomerService#qureyIsCustomeBlankOut(com.deppon.foss.esb.crm.server.order.IsCustomerBlankOutRequest,
	 *      javax.xml.ws.Holder)
	 */
	@Override
	public IsCustomerBlankOutResponse qureyIsCustomeBlankOut(
			IsCustomerBlankOutRequest request, Holder<ESBHeader> esbHeader)
			throws CommonException{
		logger.info("进入查询客户是否可以作废服务...");

		IsCustomerBlankOutResponse response = null;

		try{
			// 设置ESBHeader和返回值
			ESBHeaderUtil.processEsbHeaderForResponse(esbHeader);
			response = new IsCustomerBlankOutResponse();

			if (request != null){
				// 查询客户是否在使用
				List<String> customerCodes = request.getCustomerCodeList();
				List<CustomerInUseDto> inUseList = this.creditCustomerService
						.isCustomerInUse(customerCodes);

				//设置结果
				IsCustomerBlankOutList outList = null;
				if (CollectionUtils.isNotEmpty(inUseList)){
					for (CustomerInUseDto dto : inUseList){
						outList = new IsCustomerBlankOutList();
						outList.setCustomerCode(dto.getCustomerCode());
						outList.setIsCustomerBlankOut(!dto.isInUse());
						response.getResultInfoList().add(outList);
					}
				}
			}

		} catch (BusinessException e){
			logger.error("查询客户是否可以作废服务异常", e);
			throw new CommonException("查询客户是否可以作废服务异常", e);
		}

		logger.info("结束查询客户是否可以作废服务");

		return response;
	}

	/** 
	 * 同步理赔方案
	 * @author foss-qiaolifeng
	 * @date 2013-6-4 下午3:59:30
	 * @param esbHeader
	 * @param payload
	 * @return
	 * @throws CommonException
	 * @see com.deppon.foss.customerservice.CustomerService#claimsPayBillGenerate(javax.xml.ws.Holder, com.deppon.foss.esb.crm.server.order.ClaimsPayBillGenerateRequest)
	 * update by 231434-foss 2015-12-03 该接口被改造成rest：GeneratePayableBillImpl.java
	 */
	@Transactional
	@Override
	public ClaimsPayBillGenerateResponse claimsPayBillGenerate(
			Holder<ESBHeader> esbHeader, ClaimsPayBillGenerateRequest payload)
			throws CommonException {
		
		//生成返回实体
		ClaimsPayBillGenerateResponse response = new ClaimsPayBillGenerateResponse();
		//获取crm用户
		CurrentInfo currentInfo = SettlementUtil.getCRMCurrentInfo();
	   
		ClaimsPayBillGenerateRes claimRes = new ClaimsPayBillGenerateRes();
		try{
		BeanUtils.copyProperties(claimRes, payload);//把payload的内容复制到claimRes
			// 记录日志
			logger.info("生成理赔应付单开始:" + payload.getBillNo());
			
			// 新增应付单
			crmPayableBillService.addCRMBillPayable(claimRes, currentInfo);
			
			//设置返回成功 1
			response.setResult(1);
			
			// 记录日志
			logger.info("生成理赔应付单结束:" + payload.getBillNo());
			
		} catch (BusinessException e){
			// 日志处理
			logger.error(e.getMessage(), e);
			
			//设置返回失败0
			response.setResult(0);
			// 声明返回异常信息
			response.setReason(e.getErrorCode());
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);//记录日志
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage(), e);//记录日志
		}
		return response;
	}

    /**
     * 添加空方法,防止打包报错
     * */
	@Override
	public SyncOrderLockInfoResponse syncOrderLockInfo(
			SyncOrderLockInfoRequest request, Holder<ESBHeader> esbHeader)
			throws CommonException {
		return null;
	}
	
	 /**
     * 添加空方法,防止打包报错
     * */
	@Override
	public CancelOrderResponse cancelOrder(Holder<ESBHeader> esbHeader, CancelOrderRequest payload) throws CommonException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
     * 添加空方法,防止打包报错
     * */
	@Override
	public FossQueryAcctinfoResponse fossQueryAcctinfo(Holder<ESBHeader> esbHeader, FossQueryAcctinfoRequest payload)
			throws CommonException {
		// TODO Auto-generated method stub
		return null;
	}

}
