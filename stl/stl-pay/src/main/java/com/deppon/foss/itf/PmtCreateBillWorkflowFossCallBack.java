package com.deppon.foss.itf;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

//import com.deppon.esb.core.exception.ESBException;
//import com.deppon.esb.core.process.ErrorResponse;
//import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.dpap.esb.mqc.core.exception.ESBException;
import com.deppon.dpap.esb.mqc.core.process.ErrorResponse;
import com.deppon.dpap.esb.mqc.core.process.ICallBackProcess;
import com.deppon.dpap.esb.mqc.exception.CommonExceptionInfo;
//import com.deppon.esb.exception.CommonExceptionInfo;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPaymentService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPaymentDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillAdvanceApplysManageService;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillPaymentManageService;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillPaymentPayService;
import com.deppon.foss.module.settlement.pay.api.server.service.IReceiveHandleResult;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillAdvanceDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.CostcontrolToFossDto;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IDopStatementService;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IPartnerPayStatementManagerService;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IPartnerStatementOfAwardMService;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IWoodenStatementService;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.HomeStatementEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerStatementOfAwardEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.WoodenStatementEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.fssc.inteface.domain.payment.CallCreatePmtBillWorkflowFossResponse;

/**
 * 返回预付单申请工作流
 * 
 * @author 095793-foss-LiQin
 * @date 2012-11-26 下午3:14:23
 */
public class PmtCreateBillWorkflowFossCallBack implements ICallBackProcess {

	/**
	 * 预付单管理服务
	 */
	private IBillAdvanceApplysManageService billAdvanceApplysManageService;

	/**
	 * 注入录入付款单服务
	 */
	private IBillPaymentPayService billPaymentPayService;

	/**
	 * 注入付款单管理服务
	 */
	private IBillPaymentManageService billPaymentManageService;

	/**
	 * 注入付款单公共服务接口
	 */
	private IBillPaymentService billPaymentService;
	
	/**
	 * 注入应付单公共服务接口
	 */
	private IBillPayableService billPayableService;
	
	/**
	 * 代打木架对账单service
	 */
	private IWoodenStatementService woodenStatementService;
	
	/**
	 * 合伙人奖罚对账单service
	 */
	private IPartnerStatementOfAwardMService partnerStatementOfAwardMService;
	
	/**
	 * 合伙人付款对账单管理service
	 */
	private IPartnerPayStatementManagerService partnerPayStatementManagerService;
	/**
	 * 家装对账单Service
	 */
	private IDopStatementService dopStatementService;
	
	/**
	 * 注入接受财务共享处理结果接口
	 */
	private IReceiveHandleResult receiveHandleResult;
	
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LogManager
			.getLogger(PmtCreateBillWorkflowFossCallBack.class);
	
	/**
	 * info的message的最大长度
	 */
	private static final int MESSAGE_MAX_LENGTH = 100;
	/**
	 * 返回付款生成的工作流（付款、备用金、预付款）
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-26 下午3:15:02
	 * @see com.deppon.esb.core.process.ICallBackProcess#callback(java.lang.Object)
	 */
	@Override
	public void callback(Object obj) throws ESBException {
		LOGGER.info("处理财务共享反写工作流号开始");
		// 返回工作流
		CallCreatePmtBillWorkflowFossResponse response = (CallCreatePmtBillWorkflowFossResponse) obj;
		// 工作流类型
		String appType = response.getPayBillAppType();
		// 工作流号
		String workflowNo = response.getPayBillWorkflowNo();
		// 预付单号集合
		String payNos = response.getPaymentBillNo();
		//记录日志
		LOGGER.info("工作流类型："+appType);
		//记录日志
		LOGGER.info("工作流号："+workflowNo);
		// 校验财务共享返回数据
		checkParmForWorkflow(workflowNo, appType, payNos);
		// 备用金工作流
		if (SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_DAY_APPLY.equals(appType)
				|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_COST_APPLY.equals(appType)
				|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_COST_DRIVER_APPLY.equals(appType)
				|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_RENT_CAR_APPLY.equals(appType)
				|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_RENT_CAR_TRAFFIC_APPLY.equals(appType)) {
			// 更新备用金工作流号
			billPaymentManageService.updateApplyWorkFlow(payNos, workflowNo);
		}
		// 付款工作流:新增家装付款工作流
		else if (SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_DAY_PAY.equals(appType)
				|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_COST_PAY.equals(appType)
				||SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_COST_DRIVER_PAY.equals(appType)
				||SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PACK_WOODEN_PAY.equals(appType)
				||SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PACK_HOME_PAY.equals(appType)
				|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_RENT_CAR_PAY.equals(appType)
				|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_RENT_CAR_TRAFFIC_PAY.equals(appType)
				|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PAY_APPLY_CLAIM.equals(appType)
				|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PARTNER_WITHDRAW_PAY.equals(appType)//合合伙人提现
				|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PARTNER_AWARD_PAY.equals(appType)
				|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PARTNER_PAY.equals(appType)) {
			// 更新付款单工作流号
			billPaymentPayService.updateWorkFlow(payNos, workflowNo);
		}
		// 更新财务共享，返回预付申请工作流
		else if (SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_COST_PAY_ADV
				.equals(appType)) {
			//声明dto
			BillAdvanceDto billAdDto = new BillAdvanceDto();
			//封装预付单dto
			billAdDto.setWorkflowNo(workflowNo);
			//封装预付单dto
			billAdDto.setAdvancesNo(payNos);
			//调用接口更新工作流号
			billAdvanceApplysManageService.updateBillPayWorkFlow(billAdDto);
		}
		//记录日志
		LOGGER.info("处理财务共享反写工作流号结束");
	}

	/**
	 * 校验，财务共享返回信息是否正确
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-1 下午2:35:02
	 */
	public void checkParmForWorkflow(String workflowNo, String appType,
			String payNos) {
		// 工作流号非空判断
		if (StringUtil.isEmpty(workflowNo)) {
			throw new SettlementException("工作流号，不能为空!");
		}
		// 付款编号
		if (StringUtil.isEmpty(payNos)) {
			throw new SettlementException("付款编号不能为空!");
		}
		// 工作流类型
		if (StringUtil.isEmpty(appType)) {
			throw new SettlementException("工作流类型不能为空!");
		}
	}

	/**
	 * 财务共享异常处理
	 * 
	 * @author 095793-foss-maojianqiang、LiQin
	 * @date 2012-11-26 下午3:14:52
	 * @see com .deppon.esb.core.process.ICallBackProcess#errorHandler(java.lang.Object)
	 */
	@Override
	@Transactional
	// 此处一定要打事物标记，因为下面service没有事物
	public void errorHandler(Object arg0) throws ESBException {
		//记录日志
		LOGGER.info("错误处理结果开始");
		//获取异常参数
		ErrorResponse response = (ErrorResponse) arg0;
		// 获取esb异常信息
		CommonExceptionInfo info = response.getCommonExceptionInfo();
		//记录日志
		LOGGER.info("业务编号:" + info.getExceptioncode());
		//记录日志
		LOGGER.info("错误信息：" + info.getMessage());
		//记录日志
		LOGGER.info("错误信息：" + info.getDetailedInfo());
		// 如果异常头信息存在，则进行回滚操作。 此处异常exceptionCode 为 付款编号,付款类型
		if (StringUtils.isNotEmpty(info.getExceptioncode())) {
			//分割异常头信息，获取付款编号和单据类型
			String[] s = info.getExceptioncode().split(",");
			// 获取付款编号
			String esbpaymentNo = s[0];
			
			/*
			 * 接口改成mqc之后，String com.deppon.dpap.esb.mqc.core.exception.ESBBusinessException.
			 * CODE_PERFIX = "ESB_BIZ_"ESB的默认前缀，用于表示业务异常.因此要去除前缀
			 */
			String paymentNo = esbpaymentNo.substring(8);
			
			// 获取单据类型
			String appType = s[1];
			// 进行回滚操作   判空
			if (StringUtils.isNotBlank(paymentNo)&& StringUtils.isNotBlank(appType)) {
				// 备用金工作流
				if (SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_DAY_APPLY.equals(appType)
						|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_COST_APPLY.equals(appType)
						||SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_COST_DRIVER_APPLY.equals(appType)
						|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_RENT_CAR_APPLY.equals(appType)
						|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_RENT_CAR_TRAFFIC_APPLY.equals(appType)) {
					// 汇款审核不通过 --1、更新付款单付款状态为未汇款
					// 获取财务共享用户
					CurrentInfo currentInfo = SettlementUtil.getFKCurrentInfo();
					// 根据单号查询付款单列表
					List<BillPaymentEntity> paymentList = billPaymentService.queryPaymentByBatchNos(paymentNo,FossConstants.ACTIVE);
					// 如果没有找到对应的单据，则不处理 ，记录日志
					if (CollectionUtils.isEmpty(paymentList)) {
						//记录日志
						LOGGER.error("根据支付编号没有找到对应的付款单" + paymentNo);
					} else {
						// 声明要更新的付款单dto
						BillPaymentDto billPaymentDto = new BillPaymentDto();
						billPaymentDto.setBillPayments(paymentList);// 设置更新付款单集合
						billPaymentDto.setBatchNo(paymentNo);// 付款编号 --不会更新掉
						if(StringUtils.isNotBlank(info.getMessage()) && info.getMessage().length() > MESSAGE_MAX_LENGTH){
							billPaymentDto.setDisableOpinion(info.getMessage().substring(0, MESSAGE_MAX_LENGTH)+"生成工作流失败！");
						}else{
							billPaymentDto.setDisableOpinion(info.getMessage()+"生成工作流失败！");
						}
						// 设置更新汇款状态为"未汇款"
						billPaymentDto.setRemitStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__NOT_TRANSFER);
						// 批量更新付款单为未汇款
						billPaymentService.batchReverseRemitStatusBillPayment(billPaymentDto, currentInfo);
					}
				}
				// 付款工作流
				else if (SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_DAY_PAY.equals(appType)
						|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_COST_PAY.equals(appType)
						||SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_COST_DRIVER_PAY.equals(appType)
						||SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PACK_WOODEN_PAY.equals(appType)
						||SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PACK_HOME_PAY.equals(appType)
						|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_RENT_CAR_PAY.equals(appType)
						|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PARTNER_WITHDRAW_PAY.equals(appType)//合合伙人提现
						|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PAY_APPLY_CLAIM.equals(appType)
						|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PARTNER_AWARD_PAY.equals(appType)
						|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_RENT_CAR_TRAFFIC_PAY.equals(appType)
						|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PARTNER_PAY.equals(appType)) {
					// 获取财务共享用户
					CurrentInfo currentInfo = SettlementUtil.getFKCurrentInfo();
					// 根据单号查询付款单列表
					List<BillPaymentEntity> paymentList = billPaymentService.queryPaymentByBatchNos(paymentNo,FossConstants.ACTIVE);
					// 如果没有找到对应的单据，则不处理 ，记录日志
					if (CollectionUtils.isEmpty(paymentList)) {
						//记录日志
						LOGGER.error("根据支付编号没有找到对应的付款单" + paymentNo);
					} else {
						// 代打木架付款失败，反写对账单
						if(SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PACK_WOODEN_PAY.equals(appType)){
							this.retrographyStatementOfSccountByWoodenPayments(paymentList, currentInfo);
						}
						
						// 合伙人奖罚付款失败，反写对账单
						if(SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PARTNER_AWARD_PAY.equals(appType)){
							this.retrographyStatementOfSccountByPAwardPayments(paymentList, currentInfo);
						}
						// 合伙人付款对账单管理付款失败，反写对账单
						if(SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PARTNER_PAY.equals(appType)){
							this.retrographyStatementOfSccountByPartnerPayments(paymentList, currentInfo);
						}
						
						/**
						 * 家装对账单付款失败，反写对账单
						 */
						if(SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PACK_HOME_PAY.equals(appType)){
							this.retrographyStatementOfSccountByHomePayments(paymentList, currentInfo);
						}
						// 更新付款单工作流号
						if(StringUtils.isNotBlank(info.getMessage()) && info.getMessage().length() > MESSAGE_MAX_LENGTH){
							billPaymentManageService.dealInvalidPayment(paymentList, info.getMessage().substring(0, MESSAGE_MAX_LENGTH)+"生成工作流失败！", currentInfo);
						}else{
							billPaymentManageService.dealInvalidPayment(paymentList, info.getMessage()+"生成工作流失败！", currentInfo);
						}
						
						try {
							// 如果是合伙人提现工作流，则将异常结果通知PTP（相当于审批不同意）
							if (SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PARTNER_WITHDRAW_PAY
									.equals(appType)) {
								CostcontrolToFossDto dto = new CostcontrolToFossDto();
								// 报账异常，没有生成工作流号
								dto.setWorkflowNo(null);
								// 审批结果（1表示通过，2表示不通过）
								dto.setExamineResult(SettlementESBDictionaryConstants.COST_CONTROL__RESULT__FAIL);
								// 付款金额
								dto.setAmount(paymentList.get(0).getAmount());
								// 通知PTP提现失败，红冲提现流水
								receiveHandleResult.notifyPtp(dto, paymentList);
							}
						} catch (SettlementException e) {
							LOGGER.error("通知合伙人红冲提现明细异常：" + e.getErrorCode(), e);
						}
					}
				}
				// 更新财务共享，返回预付申请工作流
				else if (SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_COST_PAY_ADV
						.equals(appType)) {
					//声明预付单
					BillAdvanceDto billAdDto = new BillAdvanceDto();
					//封装付款单号
					billAdDto.setAdvancesNo(paymentNo);
					//调用预付单服务进行查询
					billAdvanceApplysManageService.applyAdvancePayWorkflowFail(billAdDto);
				}
			}
		}
		//记录日志
		LOGGER.error("反写财务共享(付款)生成的工作流（付款、备用金、预付款）出错！" + arg0.toString());
	}
	
	/**
	 * 代打木架付款失败，反写对账单
	 * @param paymentList
	 * @param currentInfo
	 */
	private void retrographyStatementOfSccountByWoodenPayments(List<BillPaymentEntity> paymentList,CurrentInfo currentInfo){
		LOGGER.info("反写对账单开始");
		// 付款失败，反写对账单信息
		BillPayableConditionDto payDto = new BillPayableConditionDto();
		for(BillPaymentEntity en:paymentList){
			payDto.setPaymentNo(en.getPaymentNo());
			// 获取应付单列表
			List<BillPayableEntity> list = billPayableService.queryBillPayableByCondition(payDto);
			if(!CollectionUtils.isEmpty(list)){
				String statementBillNo = list.get(0).getStatementBillNo();
				if(StringUtils.isNotEmpty(statementBillNo) && !SettlementConstants.DEFAULT_BILL_NO.equals(statementBillNo)){
					// 根据对账单号查询对账单
					WoodenStatementEntity stateEntity = woodenStatementService.queryByStateBillNoForInterface(statementBillNo);
					if(null != stateEntity){
						stateEntity.setUnpaidAmount(stateEntity.getPeriodAmount());//反写发生金额到未还款金额上
						// 修改对账单信息
						woodenStatementService.updateStatementForWriteOff(stateEntity, currentInfo);	
					}
				}
			}
		}
	}
	
	/**
	 * 家装对账单失败，反写对账单
	 * @param paymentList
	 * @param currentInfo
	 */
	private void retrographyStatementOfSccountByHomePayments(List<BillPaymentEntity> paymentList,CurrentInfo currentInfo){
		LOGGER.info("反写对账单开始");
		// 付款失败，反写对账单信息
		BillPayableConditionDto payDto = new BillPayableConditionDto();
		for(BillPaymentEntity en:paymentList){
			payDto.setPaymentNo(en.getPaymentNo());
			// 获取应付单列表
			List<BillPayableEntity> list = billPayableService.queryBillPayableByCondition(payDto);
			if(!CollectionUtils.isEmpty(list)){
				String statementBillNo = list.get(0).getStatementBillNo();
				if(StringUtils.isNotEmpty(statementBillNo) && !SettlementConstants.DEFAULT_BILL_NO.equals(statementBillNo)){
					// 根据对账单号查询对账单
					HomeStatementEntity stateEntity = dopStatementService.queryByStatementNo(statementBillNo);
					if(null != stateEntity){
						stateEntity.setUnverifyAmount(stateEntity.getPeriodAmount());//反写发生金额到未还款金额上
						// 修改对账单信息
						dopStatementService.updateStatementForWriteOff(stateEntity, currentInfo);	
					}
				}
			}
		}
	}
	
	/**
	 * 合伙人奖罚付款失败，反写对账单
	 * @param paymentList
	 * @param currentInfo
	 */
	private void retrographyStatementOfSccountByPAwardPayments(List<BillPaymentEntity> paymentList,CurrentInfo currentInfo){
		LOGGER.info("反写对账单开始");
		// 付款失败，反写对账单信息
		BillPayableConditionDto payDto = new BillPayableConditionDto();
		for(BillPaymentEntity en:paymentList){
			payDto.setPaymentNo(en.getPaymentNo());
			// 获取应付单列表
			List<BillPayableEntity> list = billPayableService.queryBillPayableByCondition(payDto);
			if(!CollectionUtils.isEmpty(list)){
				String statementBillNo = list.get(0).getStatementBillNo();
				if(StringUtils.isNotEmpty(statementBillNo) && !SettlementConstants.DEFAULT_BILL_NO.equals(statementBillNo)){
					// 根据对账单号查询对账单
					PartnerStatementOfAwardEntity stateEntity = partnerStatementOfAwardMService.queryPStatementsByStaNoNPage(statementBillNo);
					if(null != stateEntity){
						stateEntity.setUnpaidAmount(stateEntity.getPeriodAmount());//反写发生金额到未还款金额上
						stateEntity.setPaidAmount(BigDecimal.valueOf(0));
						// 修改对账单信息
						partnerStatementOfAwardMService.updateStatementForWriteOff(stateEntity,currentInfo);
					}
				}
			}
		}
	}
	
	/**
	 * 合伙人付款对账单付款失败，反写对账单
	 * @param paymentList
	 * @param currentInfo
	 */
	private void retrographyStatementOfSccountByPartnerPayments(List<BillPaymentEntity> paymentList,CurrentInfo currentInfo){
		LOGGER.info("处理合伙人付款管理失败回调开始");
		// 付款失败，反写对账单信息
		BillPayableConditionDto payDto = new BillPayableConditionDto();
		for(BillPaymentEntity en:paymentList){
			payDto.setPaymentNo(en.getPaymentNo());
			// 获取应付单列表
			List<BillPayableEntity> list = billPayableService.queryBillPayableByCondition(payDto);
			if(!CollectionUtils.isEmpty(list)){
				//校验付款单是否为合伙人付款对账单管理提交
				BillPayableEntity bpe = list.get(0);
				String billType = bpe.getBillType();
				//如果不为PDFP、PDDF 即到达提成、委托派费，则退出
				if(!(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_PAYABLE.equals(billType) 
							|| SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELEGATION_DELIVERY_FEE.equals(billType))){
					LOGGER.info("非合伙人付款对账单提交工作流回馈，不做处理");
					return;
				}
				//将应付单中的对账单号添加到集合中
				List<String> statementBillNos = new ArrayList<String>();
				String statementBillNo = null; 
				for (BillPayableEntity billPayableEntity : list) {
					statementBillNo = billPayableEntity.getStatementBillNo();
					if(!SettlementConstants.DEFAULT_BILL_NO.equals(statementBillNo) && !statementBillNos.contains(statementBillNo)){
						//如果对账单号不是默认的N/A 则加入到集合中
						statementBillNos.add(statementBillNo);
					}
				}
				if(CollectionUtils.isNotEmpty(statementBillNos)){
					//回调更新
					partnerPayStatementManagerService.updateBillAfterFailPay(statementBillNos, currentInfo);
				}
			}
		}
		LOGGER.info("处理合伙人付款管理失败回调结束");
	}

	/**
	 * @param billAdvanceApplysManageService
	 */
	public void setBillAdvanceApplysManageService(
			IBillAdvanceApplysManageService billAdvanceApplysManageService) {
		this.billAdvanceApplysManageService = billAdvanceApplysManageService;
	}

	/**
	 * @param billPaymentPayService
	 */
	public void setBillPaymentPayService(
			IBillPaymentPayService billPaymentPayService) {
		this.billPaymentPayService = billPaymentPayService;
	}

	/**
	 * @param billPaymentManageService
	 */
	public void setBillPaymentManageService(
			IBillPaymentManageService billPaymentManageService) {
		this.billPaymentManageService = billPaymentManageService;
	}

	/**
	 * @param billPaymentService
	 */
	public void setBillPaymentService(IBillPaymentService billPaymentService) {
		this.billPaymentService = billPaymentService;
	}

	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	public void setWoodenStatementService(
			IWoodenStatementService woodenStatementService) {
		this.woodenStatementService = woodenStatementService;
	}

	public void setDopStatementService(IDopStatementService dopStatementService) {
		this.dopStatementService = dopStatementService;
	}

	public void setPartnerStatementOfAwardMService(
			IPartnerStatementOfAwardMService partnerStatementOfAwardMService) {
		this.partnerStatementOfAwardMService = partnerStatementOfAwardMService;
	}

	public void setPartnerPayStatementManagerService(
			IPartnerPayStatementManagerService partnerPayStatementManagerService) {
		this.partnerPayStatementManagerService = partnerPayStatementManagerService;
	}

	/**
	 * @param receiveHandleResult the receiveHandleResult to set
	 */
	public void setReceiveHandleResult(IReceiveHandleResult receiveHandleResult) {
		this.receiveHandleResult = receiveHandleResult;
	}
	
}
