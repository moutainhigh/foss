package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillDepositReceivedService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService;
import com.deppon.foss.module.settlement.common.api.server.service.IClaimStatusMsgService;
import com.deppon.foss.module.settlement.common.api.server.service.IFossConfigEntityService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.ClaimStatusMsgEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.FossConfigEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VehicleFeeEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillInfoEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPaymentDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillPaymentManageService;
import com.deppon.foss.module.settlement.pay.api.server.service.IPaymentStatusToVTSClient;
import com.deppon.foss.module.settlement.pay.api.server.service.IReceiveHandleResult;
import com.deppon.foss.module.settlement.pay.api.shared.domain.PtpWithdrawResultReqEntity;
import com.deppon.foss.module.settlement.pay.api.shared.domain.PtpWithdrawResultRespEntity;
import com.deppon.foss.module.settlement.pay.api.shared.domain.ReceiveTPSResponseEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.CostcontrolToFossDto;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IDopStatementService;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IPartnerPayStatementManagerService;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IPartnerStatementOfAwardMService;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IWoodenStatementService;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.HomeStatementEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerStatementOfAwardEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.WoodenStatementEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 接受费控结果
 * 
 * @author 045738-foss-maojianqiang
 * @date 2012-12-5 下午5:52:57
 */
public class ReceiveHandleResult implements IReceiveHandleResult {
	/**
	 * 注入日志
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ReceiveHandleResult.class);
	/**
	 * 注入应付单公共服务接口
	 */
	private IBillPayableService billPayableService;
	/**
	 * 注入付款单公共服务接口
	 */
	private IBillPaymentService billPaymentService;

	/**
	 * 注入核销接口
	 */
	private IBillWriteoffService billWriteoffService;

	/**
	 * 注入结算单号生成类
	 */
	private ISettlementCommonService settlementCommonService;

	/**
	 * 注入预收单
	 */
	private IBillDepositReceivedService billDepositReceivedService;

	/**
	 * 注入付款单作废服务
	 */
	private IBillPaymentManageService billPaymentManageService;

	/**
	 * 注入坏账通知接口
	 */
	private IClaimStatusMsgService claimStatusMsgService;

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
	 * 家装对账单serivce
	 */
	private IDopStatementService dopStatementService;

	/**
	 * 注入接受财务共享处理结果接口
	 */
	private IReceiveHandleResult receiveHandleResult;
	/**
	 * 调用地址
	 */
	public static String ESB_CODE = "/ESB_FOSS2ESB_COMMAND_TPS";

	/**
	 * 查询esb地址信息的接口
	 */
	private IFossConfigEntityService fossConfigEntityService;
	
	/**
	 * 注入部门service
	 */
	private IOrgAdministrativeInfoService  orgAdministrativeInfoService;
	
	/**
	 * 合伙人-预收单退款回退接口编码
	 */
	private final static String PTP_WITHDRAW_ROLLBACK_SERVER_CODE = "/PTP_ESB2PTP_DEPOSIT_REFUND_ROLLBACK";
	/**
	 * 395982 VTS字段回传
	 */
    private IPaymentStatusToVTSClient paymentStatusToVTSClient;
	
	/**
	 * 处理付款工作流
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-5 下午5:56:49
	 */
	public void dealPaymentWorkFlow(CostcontrolToFossDto dto) {
		logger.info("处理汇款工作流start  工作流号：" + dto.getWorkflowNo());
		//待传输运单号集合
		List<String> billIds = new ArrayList<String>();
		//带传输实体
		BillInfoEntity billInfoEntity = new BillInfoEntity();
		// 获取费控用户
		CurrentInfo currentInfo = SettlementUtil.getFKCurrentInfo();
		// 获取核销批次号
		String writeoffBatchNo = settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.HX_BN);
		logger.info("处理汇款工作流start  工作流号：" + writeoffBatchNo);

		// 校验付款单金额和部门等
		List<BillPaymentEntity> paymentList = null;

		paymentList = validatePayment(dto.getPaymentBillNo(), dto.getAmount(),
				dto.getDepno());

		if (CollectionUtils.isEmpty(paymentList)) {
			throw new SettlementException("没有查询到对应的付款单");
		}

		// 获取付款单状态
		String payStatus = paymentList.get(0).getRemitStatus();
		// 声明成功转失败
		boolean isSuccessToFail = false;
		// 如果付款状态不为空，则需要判断是否为成功转失败
		if (StringUtils.isNotEmpty(payStatus)
				&& SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__TRANSFERRED
						.equals(payStatus)) {
			isSuccessToFail = true;
		}
		
		// 判断校验汇款类型
		if (SettlementESBDictionaryConstants.COST_CONTROL__RESULT__SUCESS
				.equals(dto.getExamineResult())) {
			// 进行审核成功 --1、进行付款冲应付或付款冲预收 2、更新付款单为“已汇款”
			payToSuccess(paymentList, dto.getPaymentBillNo(), writeoffBatchNo,
					currentInfo, dto.getWorkflowtype());
			// 汇款审核失败 --1、红冲付款单 2、释放应付单或预收单
		} else if (SettlementESBDictionaryConstants.COST_CONTROL__RESULT__FAIL
				.equals(dto.getExamineResult())) {
			// 成功转失败
			if (isSuccessToFail) {
				// 循环调用反核销接口进行反核销
				for (BillPaymentEntity en : paymentList) {
					// 反核销
					billWriteoffService.writeBackBillWriteoffByPayment(
							en.getPaymentNo(), currentInfo);
				}
				//电汇汇款失败
				billExtracted(dto, billIds, paymentList);
				billInfoEntity.setBillIds(billIds);
				//工作流号在这里传空值
				billInfoEntity.setPaymentNumber(null);
				//汇款状态 未汇款
				billInfoEntity.setPaymentStatus(SettlementDictionaryConstants
						.BILL_PAYMENT__REMIT_STATUS__NOT_TRANSFER);
				paymentStatusToVTSClient.sendPaymentStatusToVTS(billInfoEntity);
				logger.info("调用FOSS向VTS回传信息同步接口成功");
			}
			
			// 代打木架付款失败，反写对账单
			this.retrographyStatementOfSccountByPaymentsAndWooden(dto,
					paymentList, currentInfo);
			// 家装对账单付款失败，反写对账单
			this.retrographyStatementOfSccountByPaymentsAndHome(dto,
					paymentList, currentInfo);
			// 合伙人奖罚对账单付款失败，反写对账单
			this.retrographyStatementOfSccountByPaymentsAndPAward(dto,
					paymentList, currentInfo);
			// 合伙人付款对账单管理付款失败，反写对账单
			this.retrographyStatementOfSccountByPaymentsAndPm(dto,
					paymentList, currentInfo);
			List<BillPayableEntity> claimList = billPaymentManageService.dealInvalidPayment(paymentList, dto.getExamineConment()
							+ "汇款失败！", currentInfo);
			// 发送理赔信息
			// for(BillPayableEntity claim:claimList){
			// sendClaimMessageToCrm(claim);
			// }
			// 发送消息
			// 汇款成功转失败 --//调用作废接口，处理 -- 1、反核销 2、红冲付款单3、释放应付单或预收单
			
		} else {
			// 循环调用反核销接口进行反核销
			for (BillPaymentEntity en : paymentList) {
				// 反核销
				billWriteoffService.writeBackBillWriteoffByPayment(
						en.getPaymentNo(), currentInfo);
			}
			// 代打木架付款失败，反写对账单
			this.retrographyStatementOfSccountByPaymentsAndWooden(dto,
					paymentList, currentInfo);

			// 家装对账单付款失败，反写对账单
			this.retrographyStatementOfSccountByPaymentsAndHome(dto,
					paymentList, currentInfo);
			// 合伙人奖罚对账单付款失败，反写对账单
			this.retrographyStatementOfSccountByPaymentsAndPAward(dto,
					paymentList, currentInfo);
			// 合伙人付款对账单管理付款失败，反写对账单
			this.retrographyStatementOfSccountByPaymentsAndPm(dto,
					paymentList, currentInfo);
			// 调用作废接口，1、红冲付款单 2、释放应付单或预收单
			List<BillPayableEntity> claimList = billPaymentManageService.dealInvalidPayment(paymentList, dto.getExamineConment()
							+ "汇款失败！", currentInfo);

			// 发送理赔信息
			// for(BillPayableEntity claim:claimList){
			// sendClaimMessageToCrm(claim);
			// }
			
		}
		
		//如果是合伙人提现工作流，则将审批结果通知PTP
		if(SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PARTNER_WITHDRAW_PAY.equals(dto.getWorkflowtype())){
			notifyPtp(dto,paymentList);
		}
		
		logger.info("处理汇款工作流end");
	}

	private void billExtracted(CostcontrolToFossDto dto, List<String> billIds,
			List<BillPaymentEntity> paymentList) {
		for (BillPaymentEntity en : paymentList) {
			/**
			 * 	FOSS字段信息回传给VTS
			 * 
			 * @author 395982
			 * @date 2017-01-12 
			 */
			try {
				//先判断是否是FOSS外请车--付款类型，是的话调用FOSS向VTS传信息接口
				if(StringUtils.isNotEmpty(en.getPaymentNo()) && 
						SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_COST_DRIVER_PAY.equals(dto.getWorkflowtype())){
					// 根据付款单号查应付单，获取运单号集合，汇款状态是未汇款，工作流号空
					// 查询应付单
					BillPayableConditionDto conditionDto = new BillPayableConditionDto();
					conditionDto.setPaymentNo(en.getPaymentNo());
					// 获取应付单列表
					List<BillPayableEntity> payableEntityList = billPayableService
							.queryBillPayableByCondition(conditionDto);
					for(BillPayableEntity billPayable : payableEntityList ){
						logger.info("应付单号为：" + billPayable.getPayableNo());
						billIds.add(billPayable.getWaybillNo());
					}

				}
				//异常处理
			} catch (SettlementException e) {
				logger.error("调用FOSS向VTS回传信息同步接口异常" + e.getMessage());
			}
		}
	}
	
	/**
	 * 通知合伙人报账系统审批结果
	 * @author 302346-foss-jiangxun
	 * @date 2016-02-28 上午8:13:00
	 */
	public void notifyPtp(CostcontrolToFossDto dto,List<BillPaymentEntity> paymentList){
		//通知ptp生成提现明细
		logger.info("\n\n合伙人提现-接受报账系统审批结果通知合伙人-start....\n\n");
		String url = "";
		//校验地址是否在ESB注册
		FossConfigEntity configEntity = fossConfigEntityService.queryFossConfigEntityByServerCode(PTP_WITHDRAW_ROLLBACK_SERVER_CODE);
		if (null != configEntity && !StringUtil.isEmpty(configEntity.getEsbAddr())) {
			url = configEntity.getEsbAddr();
		} else {
			logger.error("\n\n合伙人提现-接受报账系统审批结果通知合伙人-读取esb地址有误:\n\n");
			throw new SettlementException("读取esb地址有误!");
		}
//		url = "http://10.224.165.113:8081/ptp-syncfoss-itf/v1/ptp/saleflow/fossDepositRefundService/refundRollback";
		
		//发送请求
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setContentCharset("UTF-8");
		PostMethod postMethod = new PostMethod(url);
		//请求参数
		PtpWithdrawResultReqEntity req = new PtpWithdrawResultReqEntity();
		//响应参数
		PtpWithdrawResultRespEntity result = new PtpWithdrawResultRespEntity();
		//组装请求实体
		req = buildPtpWithdrawResultReqEntity(dto,paymentList);
//				Object obj = JSONObject.toJSON(req);
		Object obj = JSONObject.fromObject(req);
		String json = (null == obj) ? "" : obj.toString();
		try {
			logger.info("\n\n合伙人提现-接受报账系统审批结果通知合伙人-请求参数:\n\n" + json);
			RequestEntity requestEntity = new StringRequestEntity(json, "application/json", "UTF-8");
			postMethod.setRequestEntity(requestEntity);
			postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
			String responseBody = "";
			// 执行postMethod
			httpClient.executeMethod(postMethod);
			// 获取返回值
			responseBody = postMethod.getResponseBodyAsString();
			if(responseBody==null||"".equals(responseBody)){
				logger.error("\n\n合伙人提现-接受报账系统审批结果通知合伙人-响应为空:\n\n");
				throw new Exception("\n\n合伙人提现-接受报账系统审批结果通知合伙人-响应为空:\n\n");
			}
			logger.info("\n\n合伙人提现-接受报账系统审批结果通知合伙人-响应信息:\n\n" + responseBody);
	
			// 将返回值转换成对象
//					JSONObject returnJSON = JSONObject.parseObject(responseBody);
//					result = JSONObject.toJavaObject(returnJSON, PtpWithdrawResultRespEntity.class);
			JSONObject returnJSON = JSONObject.fromObject(responseBody);
			result = (PtpWithdrawResultRespEntity)JSONObject.toBean(returnJSON, PtpWithdrawResultRespEntity.class);
			//异常信息
			if (!result.isResult()) {
				logger.error("\n\n合伙人提现-接受报账系统审批结果通知合伙人-合伙人系统校验失败:" +result.getMessage() );
				throw new Exception("合伙人系统校验失败，"+result.getMessage());
			}
		} catch (Exception e) {
			throw new SettlementException(e.getMessage(),e);
		} finally {
			if (null != postMethod) {
				postMethod.releaseConnection();
			}
		}
		logger.info("\n\n合伙人提现-合伙人校验并付款-end....\n\n");
	}
	
	/**
	 * 组装合伙人提现通知请求实体
	 * @author 302346-foss-蒋迅
	 * @param paymentEntity 
	 * @date 2016-02-26 下午4:59:00
	 */
	public PtpWithdrawResultReqEntity buildPtpWithdrawResultReqEntity(CostcontrolToFossDto  dto,
			List<BillPaymentEntity> paymentList){
		BillPaymentEntity billPaymentEntity = paymentList.get(0);
		
		PtpWithdrawResultReqEntity ptpWithdrawReqEntity = new PtpWithdrawResultReqEntity();
		
		ptpWithdrawReqEntity.setWorkFlowNo(dto.getWorkflowNo());//工作流号
		ptpWithdrawReqEntity.setPaymentBillNo(billPaymentEntity.getPaymentNo());//付款单单号
		ptpWithdrawReqEntity.setWorkFlowApplyStatus(dto.getExamineResult());//工作流审批状态
		
		ptpWithdrawReqEntity.setAmount(dto.getAmount());//提现或审批总金额
		ptpWithdrawReqEntity.setBizDate(new Date());//业务日期
//		ptpWithdrawReqEntity.setCreateUserCode(billPaymentEntity.getCreateUserCode());//当前提现人或申请审批人编码
//		ptpWithdrawReqEntity.setCreateUserName(billPaymentEntity.getCreateUserName());//当前提现人或申请审批人名称
		ptpWithdrawReqEntity.setModifyUserCode(billPaymentEntity.getCreateUserCode());//退款申请人编号
		ptpWithdrawReqEntity.setModifyUserName(billPaymentEntity.getCreateUserName());//退款申请人名称
		
		//根据合伙人部门编码，查询部门的标杆编码
		OrgAdministrativeInfoEntity origEntity = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(billPaymentEntity.getCustomerCode());//合伙人部门编码
		if(null == origEntity){
			logger.error("查询合伙人部门标杆编码失败，合伙人部门信息为空！");
			throw new SettlementException("查询合伙人部门标杆编码失败，合伙人部门信息为空！");
		}
		logger.info("合伙人部门标杆编码是："+origEntity.getUnifiedCode());
		ptpWithdrawReqEntity.setPartnerOrgCode(origEntity.getUnifiedCode());//合伙人部门编码（标杆编码）
		
		ptpWithdrawReqEntity.setRefundType(billPaymentEntity.getPaymentType());//退款类型
		//如果工作流号为空则返回作废意见，不为空返回备注
		if(StringUtil.isBlank(dto.getWorkflowNo())){
			ptpWithdrawReqEntity.setRemark(billPaymentEntity.getDisableOpinion());//作废意见
		}else{
			ptpWithdrawReqEntity.setRemark(billPaymentEntity.getNotes());//备注信息
		}
		ptpWithdrawReqEntity.setSourceBillNo(billPaymentEntity.getPaymentNo());//付款单号
		ptpWithdrawReqEntity.setSourceBillType(billPaymentEntity.getSourceBillType());//付款单来源类型
		ptpWithdrawReqEntity.setSourceSystem(SettlementESBDictionaryConstants.ESB_HEADER__SOURCE_SYSTEM);//来源系统FOSS
		return ptpWithdrawReqEntity;
	}

	/**
	 * 代打木架付款失败，反写对账单
	 * 
	 * @param paymentList
	 * @param currentInfo
	 */
	private void retrographyStatementOfSccountByPaymentsAndWooden(
			CostcontrolToFossDto dto, List<BillPaymentEntity> paymentList,
			CurrentInfo currentInfo) {

		// 不是代打木架方式，退出
		if (!SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PACK_WOODEN_PAY
				.equals(dto.getWorkflowtype()))
			return;

		// 付款失败，反写对账单信息
		BillPayableConditionDto payDto = new BillPayableConditionDto();
		for (BillPaymentEntity en : paymentList) {
			payDto.setPaymentNo(en.getPaymentNo());
			// 获取应付单列表
			List<BillPayableEntity> list = billPayableService
					.queryBillPayableByCondition(payDto);
			if (!CollectionUtils.isEmpty(list)) {
				String statementBillNo = list.get(0).getStatementBillNo();
				if (StringUtils.isNotEmpty(statementBillNo)
						&& !SettlementConstants.DEFAULT_BILL_NO
								.equals(statementBillNo)) {
					// 根据对账单号查询对账单
					WoodenStatementEntity stateEntity = woodenStatementService
							.queryByStateBillNoForInterface(statementBillNo);
					if (null != stateEntity) {
						stateEntity.setUnpaidAmount(stateEntity
								.getPeriodAmount());// 反写发生金额到未还款金额上
						// 修改对账单信息
						woodenStatementService.updateStatementForWriteOff(
								stateEntity, currentInfo);
					}
				}
			}
		}

	}

	/**
	 * 家装对账单失败，反写对账单
	 * 
	 * @param paymentList
	 * @param currentInfo
	 */
	private void retrographyStatementOfSccountByPaymentsAndHome(
			CostcontrolToFossDto dto, List<BillPaymentEntity> paymentList,
			CurrentInfo currentInfo) {

		// 不是家装，退出
		if (!SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PACK_HOME_PAY
				.equals(dto.getWorkflowtype()))
			return;

		// 付款失败，反写对账单信息
		BillPayableConditionDto payDto = new BillPayableConditionDto();
		for (BillPaymentEntity en : paymentList) {
			payDto.setPaymentNo(en.getPaymentNo());
			// 获取应付单列表
			List<BillPayableEntity> list = billPayableService
					.queryBillPayableByCondition(payDto);
			if (!CollectionUtils.isEmpty(list)) {
				String statementBillNo = list.get(0).getStatementBillNo();
				if (StringUtils.isNotEmpty(statementBillNo)
						&& !SettlementConstants.DEFAULT_BILL_NO
								.equals(statementBillNo)) {
					// 根据对账单号查询对账单
					HomeStatementEntity stateEntity = dopStatementService
							.queryByStatementNo(statementBillNo);
					if (null != stateEntity) {
						stateEntity.setUnverifyAmount(stateEntity
								.getPeriodAmount());// 反写发生金额到未还款金额上
						// 修改对账单信息
						dopStatementService.updateStatementForWriteOff(
								stateEntity, currentInfo);
					}
				}
			}
		}

	}
	
	/**
	 * 合伙人奖罚付款失败，反写对账单
	 * 
	 * @param paymentList
	 * @param currentInfo
	 */
	private void retrographyStatementOfSccountByPaymentsAndPAward(
			CostcontrolToFossDto dto, List<BillPaymentEntity> paymentList,
			CurrentInfo currentInfo) {

		// 不是代打木架方式，退出
		if (!SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PARTNER_AWARD_PAY
				.equals(dto.getWorkflowtype()))
			return;

		// 付款失败，反写对账单信息
		BillPayableConditionDto payDto = new BillPayableConditionDto();
		for (BillPaymentEntity en : paymentList) {
			payDto.setPaymentNo(en.getPaymentNo());
			// 获取应付单列表
			List<BillPayableEntity> list = billPayableService
					.queryBillPayableByCondition(payDto);
			if (!CollectionUtils.isEmpty(list)) {
				String statementBillNo = list.get(0).getStatementBillNo();
				if (StringUtils.isNotEmpty(statementBillNo)
						&& !SettlementConstants.DEFAULT_BILL_NO
								.equals(statementBillNo)) {
					// 根据对账单号查询对账单
					PartnerStatementOfAwardEntity stateEntity = partnerStatementOfAwardMService.queryPStatementsByStaNoNPage(statementBillNo);
					if (null != stateEntity) {
						stateEntity.setUnpaidAmount(stateEntity.getPeriodAmount());// 反写发生金额到未还款金额上
						stateEntity.setPaidAmount(BigDecimal.valueOf(0));
						// 修改对账单信息
						partnerStatementOfAwardMService.updateStatementForWriteOff(stateEntity,currentInfo);
					}
				}
			}
		}

	}
	

	/**
	 * 合伙人付款管理付款失败，反写对账单
	 * 
	 * @param paymentList
	 * @param currentInfo
	 */
	private void retrographyStatementOfSccountByPaymentsAndPm(
			CostcontrolToFossDto dto, List<BillPaymentEntity> paymentList,
			CurrentInfo currentInfo) {
		logger.info("处理合伙人付款管理失败回调开始 【{}】", new Date());
		// 不是合伙人付款方式，退出
		if (!SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PARTNER_PAY
				.equals(dto.getWorkflowtype()))
			return;
		
		// 付款失败，反写对账单信息
		BillPayableConditionDto payDto = new BillPayableConditionDto();
		for (BillPaymentEntity en : paymentList) {
			payDto.setPaymentNo(en.getPaymentNo());
			// 获取应付单列表
			List<BillPayableEntity> list = billPayableService
					.queryBillPayableByCondition(payDto);
			if(!CollectionUtils.isEmpty(list)){
				//校验付款单是否为合伙人付款对账单管理提交
				BillPayableEntity bpe = list.get(0);
				String billType = bpe.getBillType();
				//如果不为PDFP、PDDF 即到达提成、委托派费，则退出
				if(!(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_PAYABLE.equals(billType) 
							|| SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELEGATION_DELIVERY_FEE.equals(billType))){
					logger.info("非合伙人付款对账单提交工作流回馈，不做处理");
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
		logger.info("处理合伙人付款管理失败回调结束 【{}】", new Date());
	}

	/**
	 * 处理备用金工作流 只有审核通过和不通过，没有成功转失败
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-5 下午5:57:38
	 */
	public void dealPettyCashWorkFlow(CostcontrolToFossDto dto) {
		logger.info("处理汇款工作流start  工作流号：" + dto.getWorkflowNo());
		//待传输运单号集合
		List<String> billIds = new ArrayList<String>();
		//待传输实体
		BillInfoEntity billInfoEntity = new BillInfoEntity();
		// 获取费控用户
		CurrentInfo currentInfo = SettlementUtil.getFKCurrentInfo();
		// 获取核销批次号
		String writeoffBatchNo = settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.HX_BN);
		// 校验付款单金额和部门等
		List<BillPaymentEntity> paymentList = null;
		paymentList = validatePayment(dto.getPaymentBillNo(), dto.getAmount(),
				dto.getDepno());

		// 获取付款单状态
		String payStatus = paymentList.get(0).getRemitStatus();
		// 声明成功转失败
		boolean isSuccessToFail = false;
		// 如果付款状态不为空，则需要判断是否为成功转失败
		if (StringUtils.isNotEmpty(payStatus)
				&& SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__TRANSFERRED
						.equals(payStatus)) {
			isSuccessToFail = true;
		}

		// 判断校验汇款类型
		if (SettlementESBDictionaryConstants.COST_CONTROL__RESULT__SUCESS
				.equals(dto.getExamineResult())) {
			// 进行审核成功 -1、进行付款冲应付或付款冲预收 2、更新付款单为“已汇款”
			payToSuccess(paymentList, dto.getPaymentBillNo(), writeoffBatchNo,
					currentInfo, dto.getWorkflowtype());
		} else if (SettlementESBDictionaryConstants.COST_CONTROL__RESULT__FAIL
				.equals(dto.getExamineResult())) {
			// 如果为成功转失败
			if (isSuccessToFail) {
				// 循环调用反核销接口进行反核销
				for (BillPaymentEntity en : paymentList) {
					// 反核销
					billWriteoffService.writeBackBillWriteoffByPayment(
							en.getPaymentNo(), currentInfo);
				}
			}
			for (BillPaymentEntity en : paymentList) {
				/**
				 * 	FOSS字段信息回传给VTS
				 * 
				 * @author 395982 zhengyating
				 * @date 2017-01-12 
				 */
				try {
					//先判断是否是FOSS外请车--报销类型，是的话调用FOSS向VTS传信息接口
					if(StringUtils.isNotEmpty(en.getPaymentNo()) && 
							SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_COST_DRIVER_APPLY.equals(dto.getWorkflowtype())){
						BillPayableConditionDto conditionDto = new BillPayableConditionDto();
						conditionDto.setPaymentNo(en.getPaymentNo());
						// 获取应付单列表
						List<BillPayableEntity> payableEntityList = billPayableService
								.queryBillPayableByCondition(conditionDto);
						for(BillPayableEntity billPayable : payableEntityList ){
							logger.info("应付单号为：" + billPayable.getPayableNo());
							billIds.add(billPayable.getWaybillNo());
						}
					}
					//异常处理
				} catch (SettlementException e) {
					logger.error("调用FOSS向VTS回传信息同步接口异常" + e.getMessage());
				}
			}
			billInfoEntity.setBillIds(billIds);
			//工作流号在这里传空值
			billInfoEntity.setPaymentNumber(null);
			//汇款状态 未汇款
			billInfoEntity.setPaymentStatus(SettlementDictionaryConstants
					.BILL_PAYMENT__REMIT_STATUS__NOT_TRANSFER);
			paymentStatusToVTSClient.sendPaymentStatusToVTS(billInfoEntity);
			logger.info("调用FOSS向VTS回传信息同步接口成功");
			// 汇款审核不通过 --1、更新付款单付款状态为未汇款
			// 声明要更新的付款单dto
			BillPaymentDto billPaymentDto = new BillPaymentDto();
			billPaymentDto.setBillPayments(paymentList);// 设置更新付款单集合
			billPaymentDto.setBatchNo(dto.getPaymentBillNo());// 付款编号 --不会更新掉
			billPaymentDto.setDisableOpinion(dto.getExamineConment() + "汇款失败！");
			billPaymentDto
					.setRemitStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__NOT_TRANSFER);// 设置更新汇款状态为"未汇款"
			// 批量更新付款单为未汇款
			billPaymentService.batchReverseRemitStatusBillPayment(
					billPaymentDto, currentInfo);
		} else {
			// 循环调用反核销接口进行反核销
			for (BillPaymentEntity en : paymentList) {
				// 反核销
				billWriteoffService.writeBackBillWriteoffByPayment(
						en.getPaymentNo(), currentInfo);
			}
			// 汇款审核不通过 --1、更新付款单付款状态为未汇款
			// 声明要更新的付款单dto
			BillPaymentDto billPaymentDto = new BillPaymentDto();
			billPaymentDto.setBillPayments(paymentList);// 设置更新付款单集合
			billPaymentDto.setBatchNo(dto.getPaymentBillNo());// 付款编号 --不会更新掉
			billPaymentDto.setDisableOpinion(dto.getExamineConment() + "汇款失败！");
			billPaymentDto
					.setRemitStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__NOT_TRANSFER);// 设置更新汇款状态为"未汇款"
			// 批量更新付款单为未汇款
			billPaymentService.batchReverseRemitStatusBillPayment(
					billPaymentDto, currentInfo);
		}
		logger.info("处理汇款工作流end");
	}

	/**
	 * 汇款成功
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-5 下午9:09:46
	 */
	private void payToSuccess(List<BillPaymentEntity> paymentList,
			String payNo, String writeoffBatchNo, CurrentInfo currentInfo,
			String workflowType) {
		//待传输的运单集合
		List<String> billIds = new ArrayList<String>();
		//待传输实体
		BillInfoEntity billInfoEntity = new BillInfoEntity();
		// 循环核销
		for (BillPaymentEntity entity : paymentList) {
			// 如果来源单据为应付单，则只需要更新应付单数据
			if (StringUtils.isNotBlank(entity.getSourceBillType())
					&& SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__ADVANCED_PAYMENT
							.equals(entity.getSourceBillType())) {
				// 查询应付单
				BillPayableConditionDto conditionDto = new BillPayableConditionDto();
				conditionDto.setPaymentNo(entity.getPaymentNo());
				// 获取应付单列表
				List<BillPayableEntity> payableList = billPayableService
						.queryBillPayableByCondition(conditionDto);
				// 进行冲销
				writeoffPaymentToPayable(payableList, entity, writeoffBatchNo,
						currentInfo);
				// 判断是不是外请车类型的付款，是的话则调用TPS接口
				if (SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_COST_DRIVER_APPLY
						.equals(workflowType)
						|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_COST_DRIVER_PAY
								.equals(workflowType)) {
					try {
						receiveHandleResult.sendSourceBillsToTPS(payableList);
					} catch (SettlementException e) {
						logger.info("调用TPS接口异常" + e.getErrorCode());
					} catch (Exception e1) {
						logger.info("调用TPS接口异常" + e1.getMessage());
					}
					/**
					 * FOSS向VTS回传字段信息需求
					 * @author 395982
					 * @date 2017-01-12
					 */
					for(BillPayableEntity billPayable : payableList){
						//打印日志
						logger.info("应付单号为：" + billPayable.getPayableNo());
						//运单号
						billIds.add(billPayable.getWaybillNo());
					}
				}
				// 如果来源单据为预收单，则只需要更新预收单
			} else if (StringUtils.isNotBlank(entity.getSourceBillType())
					&& SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__DEPOSIT_RECEIVED
							.equals(entity.getSourceBillType())) {
				// 查询预收单
				List<BillDepositReceivedEntity> depositList = billDepositReceivedService
						.queryByPaymentNo(entity.getPaymentNo());
				// 进行冲销预收单
				writeoffPaymentToDepositReceived(depositList, entity,
						writeoffBatchNo, currentInfo);
			} else {
				// 查询应付单
				BillPayableConditionDto conditionDto = new BillPayableConditionDto();
				conditionDto.setPaymentNo(entity.getPaymentNo());
				// 获取应付单列表
				List<BillPayableEntity> payableList = billPayableService
						.queryBillPayableByCondition(conditionDto);
				// 如果存在应付单明细，则冲销
				if (payableList.size() > 0) {
					// 进行冲销
					writeoffPaymentToPayable(payableList, entity,
							writeoffBatchNo, currentInfo);
				}
				// 查询预收单
				List<BillDepositReceivedEntity> depositList = billDepositReceivedService
						.queryByPaymentNo(entity.getPaymentNo());
				// 如果存在预收单明细，则冲销
				if (depositList.size() > 0) {
					// 进行冲销预收单
					writeoffPaymentToDepositReceived(depositList, entity,
							writeoffBatchNo, currentInfo);
				}
			}
		}
		//运单号集合
		billInfoEntity.setBillIds(billIds);
		//工作流类型为FOSS外请车--报销
		if(SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_COST_DRIVER_APPLY.equals(workflowType)) {
			//付款工作流号
			billInfoEntity.setPaymentNumber(paymentList.get(0).getApplyWorkFlowNo());
		} else {
			//付款工作流号
			billInfoEntity.setPaymentNumber(paymentList.get(0).getWorkflowNo());
		}
		//已汇款状态
		billInfoEntity.setPaymentStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__TRANSFERRED);
		//调用VTS同步接口
		paymentStatusToVTSClient.sendPaymentStatusToVTS(billInfoEntity);
		logger.info("调用FOSS向VTS回传信息同步接口成功");
		// 声明要更新的付款单dto
		BillPaymentDto billPaymentDto = new BillPaymentDto();
		billPaymentDto.setBillPayments(paymentList);// 设置更新付款单集合
		billPaymentDto
				.setRemitStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__TRANSFERRED);// 设置更新汇款状态为"已汇款"
		billPaymentDto.setBatchNo(payNo);
		// 批量更新付款单为付款中
		billPaymentService.batchReverseRemitStatusBillPayment(billPaymentDto,
				currentInfo);

	}

	/**
	 * 调用TPS的接口，将应付单中的来源单号以及运费传过去
	 * 
	 * @author 269044
	 * @date 2015-10-27
	 */
	public void sendSourceBillsToTPS(List<BillPayableEntity> payableList) {	
		if (payableList != null && payableList.size() > 0) {
			logger.info("payableList的size:" + payableList.size());
			List<BillPayableEntity> payableBillList = billPayableService
					.queryPaymentListByPaymentNo(payableList);		
			Map param = new HashMap();
			if (payableBillList != null && payableBillList.size() > 0) {
				logger.info("根据来源单号查到的应收单集合：" + payableBillList.toString());
				for (BillPayableEntity payable : payableBillList) {
					logger.info("payable" + payable.toString());
					try {
						if (BigDecimal.valueOf(0).compareTo(payable.getUnverifyAmount()) == 0) {
							VehicleFeeEntity vehicleFee = new VehicleFeeEntity();
							vehicleFee
									.setTrainNumber(payable.getSourceBillNo());
							vehicleFee.setCarPrice(payable.getAmount());
							// 设置参数
							param.put("type", "settlementService");
							param.put("requestEntity", vehicleFee);
							// 将需要传输的实体转化成JSON格式
							JSONObject object = JSONObject.fromObject(param);
							String js = object.toString();
							RequestEntity entity = new StringRequestEntity(js,
									"application/json", "UTF-8");

							// 根据服务端的ESB_CODE查到esb地址
							FossConfigEntity fossConfig = fossConfigEntityService
									.queryFossConfigEntityByServerCode(ESB_CODE);
							String esbTPSAddr = fossConfig.getEsbAddr();
							logger.info(esbTPSAddr + ESB_CODE);
							// 构造PostMethod的实例
							PostMethod postMethod = new PostMethod(esbTPSAddr
									+ ESB_CODE);
							postMethod.setRequestEntity(entity);
							postMethod.addRequestHeader("Content-Type",
									"application/json;charset=UTF-8");

							HttpClient httpClient = new HttpClient();
							// 设置编码格式
							httpClient.getParams().setContentCharset("UTF-8");
							// 执行postMethod
							int statusCode = httpClient
									.executeMethod(postMethod);
							logger.info("客户端方法执行的结果值" + statusCode);
							String responseBody = "";
							// 获取返回值
							responseBody = postMethod.getResponseBodyAsString();
							logger.info(responseBody);

							// 将返回值转换成实体类ReceiveTPSResponseEntity
							JSONObject jo = JSONObject.fromObject(responseBody);
							if (jo != null) {
								Object obj = JSONObject.toBean(jo,
										ReceiveTPSResponseEntity.class);
								ReceiveTPSResponseEntity receiveTPSResponseEntity = (ReceiveTPSResponseEntity) obj;
								logger.info("调用TPS接口后相应的结果"
										+ receiveTPSResponseEntity.isResultFlag()
										+ "  错误记录"
										+ receiveTPSResponseEntity.getFailureReason());
							}
						} else {
							logger.info("车次号：" + payable.getSourceBillNo()
									+ "金额未核销！");
						}
					} catch (Exception e) {
						throw new SettlementException("发送车次号和车价到TPS失败:"
								+ e.getMessage());
					}
				}
			} else {
				logger.info("根据车次号没有查到数据");
			}
		}
	}

	/**
	 * 付款冲应付
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-5 下午8:58:42
	 */
	private void writeoffPaymentToPayable(List<BillPayableEntity> list,
			BillPaymentEntity entity, String writeoffBatchNo,
			CurrentInfo currentInfo) {
		// 判断应付单集合
		if (CollectionUtils.isEmpty(list)) {
			throw new SettlementException("付款单：" + entity.getPaymentNo()
					+ "没有查询到对应的应付单明细去核销！");
		}
		// 声明核销dto
		BillWriteoffOperationDto billWriteoffOperationDto = new BillWriteoffOperationDto();
		billWriteoffOperationDto.setWriteoffBatchNo(writeoffBatchNo);// 批次号
		billWriteoffOperationDto
				.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);// 系统生成
		billWriteoffOperationDto.setBillPaymentEntity(entity);// 付款单
		billWriteoffOperationDto.setBillPayableEntitys(list);// 应付单列表

		// 汇款成功
		billWriteoffService.writeoffCustPaymentAndPayable(
				billWriteoffOperationDto, currentInfo);
	}

	/**
	 * 付款冲预收
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-5 下午8:58:42
	 */
	private void writeoffPaymentToDepositReceived(
			List<BillDepositReceivedEntity> list, BillPaymentEntity entity,
			String writeoffBatchNo, CurrentInfo currentInfo) {
		// 判断应付单集合
		if (CollectionUtils.isEmpty(list)) {
			throw new SettlementException("付款单：" + entity.getPaymentNo()
					+ "没有查询到对应的应付单明细去核销！");
		}
		// 声明核销dto
		BillWriteoffOperationDto billWriteoffOperationDto = new BillWriteoffOperationDto();
		billWriteoffOperationDto.setWriteoffBatchNo(writeoffBatchNo);// 批次号
		billWriteoffOperationDto
				.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);// 系统生成
		billWriteoffOperationDto.setBillPaymentEntity(entity);// 付款单
		billWriteoffOperationDto.setBillDepositReceivedEntitys(list);// 预收单列表

		// 汇款成功
		billWriteoffService.writeoffCustPaymentAndDeposit(
				billWriteoffOperationDto, currentInfo);
	}

	/**
	 * 根据传入付款单号校验
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-5 下午8:07:51
	 * @return 付款单实体列表
	 */
	private List<BillPaymentEntity> validatePayment(String paymentNo,
			BigDecimal amount, String deptNo) {

		// 根据单号查询付款单列表
		List<BillPaymentEntity> paymentList = billPaymentService
				.queryPaymentByBatchNos(paymentNo, FossConstants.ACTIVE);

		if (CollectionUtils.isEmpty(paymentList)) {
			throw new SettlementException("付款单号：" + paymentNo + "查询"
					+ FossConstants.ACTIVE + "付款单列表未找到!");
		}

		// 定义总金额
		BigDecimal totalAmount = BigDecimal.ZERO;
		// 循环校验部门和累加金额
		for (BillPaymentEntity en : paymentList) {
			// //校验部门
			// if(deptNo!=null && !deptNo.equals(en.getPaymentOrgCode())){
			// throw new
			// SettlementException("付款单号："+en.getPaymentNo()+"的部门编码与费控返回部门编码不一致！");
			// }
			totalAmount = totalAmount.add(en.getAmount());
		}

		logger.info("费控传回的汇款金额:" + amount + "与付款单明细金额:" + totalAmount);

		// 校验金额
		if (amount != null && amount.compareTo(totalAmount) != 0) {
			logger.info("费控传回的汇款金额:" + amount + "与付款单明细金额:" + totalAmount
					+ "不一致！");
			throw new SettlementException("费控传回的汇款金额与付款单明细金额总和不一致！");
		}
		return paymentList;
	}

	/**
	 * 理赔付款失败给crm发送消息 --只是用汇款工作流，且应付单为理赔的
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-7 下午5:32:07
	 */
	private void sendClaimMessageToCrm(BillPayableEntity bill) {
		// 声明消息实体
		ClaimStatusMsgEntity claim = new ClaimStatusMsgEntity();
		claim.setId(UUIDUtils.getUUID());// 封装id
		claim.setWaybillNo(bill.getWaybillNo());// 运单号
		claim.setMsgAction(SettlementDictionaryConstants.CLAIM_STATUS_MSG__MSG_ACTION__FAIL);// 失败
		claim.setMsgDate(new Date());
		claim.setMsgStatus(SettlementDictionaryConstants.CLAIM_STATUS_MSG__MSG_STATUS__NEW);// 新增
		claim.setMsgContent("付理赔款失败！");
		claimStatusMsgService.addClaimStatusMsg(claim);
	}

	/**
	 * @GET
	 * @return billPayableService
	 */
	public IBillPayableService getBillPayableService() {
		/*
		 * @get
		 * 
		 * @ return billPayableService
		 */
		return billPayableService;
	}

	/**
	 * @SET
	 * @param billPayableService
	 */
	public void setBillPayableService(IBillPayableService billPayableService) {
		/*
		 * @set
		 * 
		 * @this.billPayableService = billPayableService
		 */
		this.billPayableService = billPayableService;
	}

	/**
	 * @GET
	 * @return billPaymentService
	 */
	public IBillPaymentService getBillPaymentService() {
		/*
		 * @get
		 * 
		 * @ return billPaymentService
		 */
		return billPaymentService;
	}

	/**
	 * @SET
	 * @param billPaymentService
	 */
	public void setBillPaymentService(IBillPaymentService billPaymentService) {
		/*
		 * @set
		 * 
		 * @this.billPaymentService = billPaymentService
		 */
		this.billPaymentService = billPaymentService;
	}

	/**
	 * @GET
	 * @return billWriteoffService
	 */
	public IBillWriteoffService getBillWriteoffService() {
		/*
		 * @get
		 * 
		 * @ return billWriteoffService
		 */
		return billWriteoffService;
	}

	/**
	 * @SET
	 * @param billWriteoffService
	 */
	public void setBillWriteoffService(IBillWriteoffService billWriteoffService) {
		/*
		 * @set
		 * 
		 * @this.billWriteoffService = billWriteoffService
		 */
		this.billWriteoffService = billWriteoffService;
	}

	/**
	 * @GET
	 * @return settlementCommonService
	 */
	public ISettlementCommonService getSettlementCommonService() {
		/*
		 * @get
		 * 
		 * @ return settlementCommonService
		 */
		return settlementCommonService;
	}

	/**
	 * @SET
	 * @param settlementCommonService
	 */
	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		/*
		 * @set
		 * 
		 * @this.settlementCommonService = settlementCommonService
		 */
		this.settlementCommonService = settlementCommonService;
	}

	/**
	 * @GET
	 * @return billDepositReceivedService
	 */
	public IBillDepositReceivedService getBillDepositReceivedService() {
		/*
		 * @get
		 * 
		 * @ return billDepositReceivedService
		 */
		return billDepositReceivedService;
	}

	/**
	 * @SET
	 * @param billDepositReceivedService
	 */
	public void setBillDepositReceivedService(
			IBillDepositReceivedService billDepositReceivedService) {
		/*
		 * @set
		 * 
		 * @this.billDepositReceivedService = billDepositReceivedService
		 */
		this.billDepositReceivedService = billDepositReceivedService;
	}

	/**
	 * @GET
	 * @return billPaymentManageService
	 */
	public IBillPaymentManageService getBillPaymentManageService() {
		/*
		 * @get
		 * 
		 * @ return billPaymentManageService
		 */
		return billPaymentManageService;
	}

	/**
	 * @SET
	 * @param billPaymentManageService
	 */
	public void setBillPaymentManageService(
			IBillPaymentManageService billPaymentManageService) {
		/*
		 * @set
		 * 
		 * @this.billPaymentManageService = billPaymentManageService
		 */
		this.billPaymentManageService = billPaymentManageService;
	}

	/**
	 * @GET
	 * @return claimStatusMsgService
	 */
	public IClaimStatusMsgService getClaimStatusMsgService() {
		/*
		 * @get
		 * 
		 * @ return claimStatusMsgService
		 */
		return claimStatusMsgService;
	}

	/**
	 * @SET
	 * @param claimStatusMsgService
	 */
	public void setClaimStatusMsgService(
			IClaimStatusMsgService claimStatusMsgService) {
		/*
		 * @set
		 * 
		 * @this.claimStatusMsgService = claimStatusMsgService
		 */
		this.claimStatusMsgService = claimStatusMsgService;
	}

	/**
	 * @return the woodenStatementService
	 */
	public IWoodenStatementService getWoodenStatementService() {
		return woodenStatementService;
	}

	/**
	 * @param woodenStatementService
	 *            the woodenStatementService to set
	 */
	public void setWoodenStatementService(
			IWoodenStatementService woodenStatementService) {
		this.woodenStatementService = woodenStatementService;
	}

	public void setReceiveHandleResult(IReceiveHandleResult receiveHandleResult) {
		this.receiveHandleResult = receiveHandleResult;
	}

	/**
	 * @param fossConfigEntityService
	 */
	public void setFossConfigEntityService(
			IFossConfigEntityService fossConfigEntityService) {
		this.fossConfigEntityService = fossConfigEntityService;
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

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public IPaymentStatusToVTSClient getPaymentStatusToVTSClient() {
		return paymentStatusToVTSClient;
	}

	public void setPaymentStatusToVTSClient(
			IPaymentStatusToVTSClient paymentStatusToVTSClient) {
		this.paymentStatusToVTSClient = paymentStatusToVTSClient;
	}

}
