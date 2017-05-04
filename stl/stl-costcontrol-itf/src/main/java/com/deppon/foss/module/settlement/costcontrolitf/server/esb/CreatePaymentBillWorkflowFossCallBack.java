package com.deppon.foss.module.settlement.costcontrolitf.server.esb;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ErrorResponse;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.exception.CommonExceptionInfo;
import com.deppon.esb.inteface.domain.payment.CallCreatePaymentBillWorkflowFossResponse;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPaymentService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPaymentDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillAdvanceApplysManageService;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillPaymentManageService;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillPaymentPayService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillAdvanceDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 返回预付单申请工作流
 * 
 * @author 095793-foss-LiQin
 * @date 2012-11-26 下午3:14:23
 */
public class CreatePaymentBillWorkflowFossCallBack implements ICallBackProcess {

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
	 * 日志
	 */
	private static final Logger LOGGER = LogManager
			.getLogger(CreatePaymentBillWorkflowFossCallBack.class);

	/**
	 * 返回付款生成的工作流（付款、备用金、预付款）
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-26 下午3:15:02
	 * @see com.deppon.esb.core.process.ICallBackProcess#callback(java.lang.Object)
	 */
	@Override
	public void callback(Object obj) throws ESBException {
		LOGGER.info("处理费控反写工作流号开始");
		// 返回工作流
		CallCreatePaymentBillWorkflowFossResponse response = (CallCreatePaymentBillWorkflowFossResponse) obj;
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
		// 校验费控返回数据
		checkParmForWorkflow(workflowNo, appType, payNos);
		// 备用金工作流
		if (SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_DAY_APPLY.equals(appType)
				|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_COST_APPLY.equals(appType)
				||SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_COST_DRIVER_APPLY.equals(appType)) {
			// 更新备用金工作流号
			billPaymentManageService.updateApplyWorkFlow(payNos, workflowNo);
		}
		// 付款工作流
		else if (SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_DAY_PAY.equals(appType)
				|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_COST_PAY.equals(appType)
				||SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_COST_DRIVER_PAY.equals(appType)
				|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PAY_APPLY_CLAIM.equals(appType)) {
			// 更新付款单工作流号
			billPaymentPayService.updateWorkFlow(payNos, workflowNo);
		}
		// 更新费控，返回预付申请工作流
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
		LOGGER.info("处理费控反写工作流号结束");
	}

	/**
	 * 校验，费控返回信息是否正确
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
	 * 费控异常处理
	 * 
	 * @author 095793-foss-maojianqiang、LiQin
	 * @date 2012-11-26 下午3:14:52
	 * @see com.deppon.esb.core.process.ICallBackProcess#errorHandler(java.lang.Object)
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
			String paymentNo = s[0];
			// 获取单据类型
			String appType = s[1];
			// 进行回滚操作   判空
			if (StringUtils.isNotBlank(paymentNo)&& StringUtils.isNotBlank(appType)) {
				// 备用金工作流
				if (SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_DAY_APPLY.equals(appType)
						|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_COST_APPLY.equals(appType)
						||SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_COST_DRIVER_APPLY.equals(appType)) {
					// 汇款审核不通过 --1、更新付款单付款状态为未汇款
					// 获取费控用户
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
						billPaymentDto.setDisableOpinion(info.getMessage()+"生成工作流失败！");
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
						|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PAY_APPLY_CLAIM.equals(appType)) {
					// 获取费控用户
					CurrentInfo currentInfo = SettlementUtil.getFKCurrentInfo();
					// 根据单号查询付款单列表
					List<BillPaymentEntity> paymentList = billPaymentService.queryPaymentByBatchNos(paymentNo,FossConstants.ACTIVE);
					// 如果没有找到对应的单据，则不处理 ，记录日志
					if (CollectionUtils.isEmpty(paymentList)) {
						//记录日志
						LOGGER.error("根据支付编号没有找到对应的付款单" + paymentNo);
					} else {
						// 更新付款单工作流号
						billPaymentManageService.dealInvalidPayment(paymentList, info.getMessage()+"生成工作流失败！", currentInfo);
					}
				}
				// 更新费控，返回预付申请工作流
				else if (SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_COST_PAY_ADV
						.equals(appType)) {
					//声明预付单
					BillAdvanceDto billAdDto = new BillAdvanceDto();
					//封装付款单号
					billAdDto.setAdvancesNo(paymentNo);
					
					//预付款失败原因，记录失败					
					if(StringUtil.isNotEmpty(info.getMessage())){
						//错误原因					
						billAdDto.setNotes(info.getMessage());
					}
					
					//调用预付单服务进行查询
					billAdvanceApplysManageService.applyAdvancePayWorkflowFail(billAdDto);
				}
			}
		}
		//记录日志
		LOGGER.error("反写费控(付款)生成的工作流（付款、备用金、预付款）出错！" + arg0.toString());
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

	

}
