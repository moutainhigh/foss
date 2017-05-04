package com.deppon.foss.itf;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.dpap.esb.mqc.core.exception.ESBBusinessException;
import com.deppon.dpap.esb.mqc.core.process.IProcess;
import com.deppon.foss.esb.inteface.domain.payment.PmtResultNotificationRequest;
import com.deppon.foss.esb.inteface.domain.payment.PmtResultNotificationResponse;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillAdvanceApplysManageService;
import com.deppon.foss.module.settlement.pay.api.server.service.IReceiveHandleResult;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillAdvanceDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.CostcontrolToFossDto;

/**
 * 
 * 接受财务共享处理结果服务
 * @author 045738-foss-maojianqiang
 * @date 2012-12-24 下午8:40:06
 */
public class PmtBillPaymentOnlineResultProcessor implements IProcess {

	/**
	 * 日志
	 */
	private static final Logger logger = LogManager
			.getLogger(PmtBillPaymentOnlineResultProcessor.class);

	/**
	 * 预付单service
	 */
	private IBillAdvanceApplysManageService billAdvanceApplysManageService;
	
	/**
	 * 注入接受财务共享处理结果接口
	 */
	private IReceiveHandleResult receiveHandleResult;
	
	/**
	 * 注入部门service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * 付款处理（付款单、备用金、预付单审批结果）
	 * @author 095793-foss-LiQin、maojianqiang
	 * @date 2012-11-28 下午6:46:37
	 * @see com.deppon.esb.core.process.IProcess#process(java.lang.Object)
	 */
	@Transactional
	@Override
	public Object process(Object obj) throws ESBBusinessException {
		//记录日志
		logger.info("开始接收付款、备用金报销、预付结果.");
		//获取返回信息
		PmtResultNotificationRequest request = (PmtResultNotificationRequest) obj;
		// 返回Foss审批处理状态到财务共享
		PmtResultNotificationResponse response = new PmtResultNotificationResponse();
		// 对结果循环做处理,将结果放入DTO中
		if (request != null) {
			//日志记录接口参数信息
			logger.info(request.getWorkflowNo());
			//日志记录接口参数信息
			logger.info(request.getWorkflowType());
			//日志记录接口参数信息
			logger.info(request.getPayBillDeptNo());
			//日志记录接口参数信息
			logger.info(request.getExamineResult());
			//日志记录接口参数信息
			logger.info(request.getPayBillAmount());
			//日志记录接口参数信息
			logger.info(request.getExamineComment());
			//日志记录接口参数信息
			logger.info(request.getPaymentBillNo());
			// 付款单或预付单号集合
			String paymentBillNos = request.getPaymentBillNo();
			// 审批金额
			BigDecimal amount = request.getPayBillAmount();
			// 工作流号
			String workflowNo = request.getWorkflowNo();
			// 工作流类型
			String workflowtype = request.getWorkflowType();
			// 付款部门编码
			String depno = request.getPayBillDeptNo();
			// 审批意见
			String examineConment = request.getExamineComment();
			// 返回审批结果
			String examineResult = request.getExamineResult();
			//校验传入的财务共享参数
			try{
				//校验传入参数
				valiadteParams(request);
			//异常处理
			}catch(BusinessException be){
				//声明返回结果
				response.setResult(false);
				//声明返回结果
				response.setWorkflowNo(workflowNo);
				//声明返回结果
				response.setWorkflowType(workflowtype);
				//声明返回结果
				response.setReason(be.getMessage());
				return response;
			}
			//声明回传dto
			CostcontrolToFossDto dto = new CostcontrolToFossDto();
			//设置属性值
			dto.setPaymentBillNo(paymentBillNos);
			//设置属性值
			dto.setAmount(amount);
			//设置属性值
			dto.setWorkflowNo(workflowNo);
			//设置属性值
			dto.setWorkflowtype(workflowtype);
			//设置属性值
			dto.setDepno(depno);
			//设置属性值
			dto.setExamineConment(examineConment);
			//设置属性值
			dto.setExamineResult(examineResult);
			//标杆编码转化成部门编码
			OrgAdministrativeInfoEntity deptEntity= orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCode(depno);
			//非空判断
			if(deptEntity==null){
				//记录日志
				logger.error("传入部门标杆: "+depno+"编码未找到对应的部门");
				//记录日志
				response.setResult(false);
				//记录日志
				response.setWorkflowNo(workflowNo);
				//记录日志
				response.setWorkflowType(workflowtype);
				//记录日志
				response.setReason("传入部门标杆: "+depno+"编码未找到对应的部门");
			}else{
				//重置部门编码
				dto.setDepno(deptEntity.getCode());
			}
			
			// 备用金(报销/现金)工作流返回结果
			if (SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_DAY_APPLY.equals(workflowtype)
					||SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_COST_APPLY.equals(workflowtype)
					||SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_COST_DRIVER_APPLY.equals(workflowtype)
					|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_RENT_CAR_APPLY.equals(workflowtype)
					|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_RENT_CAR_TRAFFIC_APPLY.equals(workflowtype)) {
				try{
					//进行处理备用金工作流
					receiveHandleResult.dealPettyCashWorkFlow(dto);	
					//封装返回结果
					response.setResult(true);
					//封装返回结果
					response.setWorkflowNo(workflowNo);
					//封装返回结果
					response.setWorkflowType(workflowtype);	
				}catch(BusinessException e){
					//记录日志
					logger.error(e.getErrorArguments(),e);
					//封装返回结果
					response.setResult(false);
					//封装返回结果
					response.setWorkflowNo(workflowNo);
					//封装返回结果
					response.setWorkflowType(workflowtype);
					//封装返回结果
					response.setReason(e.getErrorCode());
				}
			}
			// 付款（电汇）工作流返回结果
			else if (SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_DAY_PAY.equals(workflowtype)
					||SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_COST_PAY.equals(workflowtype)
					||SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_COST_DRIVER_PAY.equals(workflowtype)
					||SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PACK_WOODEN_PAY.equals(workflowtype)
					||SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PACK_HOME_PAY.equals(workflowtype)
					|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_RENT_CAR_PAY.equals(workflowtype)
					|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_RENT_CAR_TRAFFIC_PAY.equals(workflowtype)
					|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PAY_APPLY_CLAIM.equals(workflowtype)
					|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PARTNER_AWARD_PAY.equals(workflowtype)
					|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PARTNER_PAY.equals(workflowtype)
					|| SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PARTNER_WITHDRAW_PAY.equals(workflowtype)) {//合伙人提现（电汇）
				try{
					//进行处理汇款工作流
					receiveHandleResult.dealPaymentWorkFlow(dto);
					//封装返回结果
					response.setResult(true);
					//封装返回结果
					response.setWorkflowNo(workflowNo);
					//封装返回结果
					response.setWorkflowType(workflowtype);
				}catch(BusinessException e){
					//记录日志
					logger.error(e.getErrorArguments(),e);
					//封装返回结果
					response.setResult(false);
					//封装返回结果
					response.setWorkflowNo(workflowNo);
					//封装返回结果
					response.setWorkflowType(workflowtype);
					//封装返回结果
					response.setReason(e.getErrorCode());
				}
			}
			// 预付款申请工作流返回结果
			else if (SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_COST_PAY_ADV
					.equals(workflowtype)) {
				// 调用预付款返回服务处理
				BillAdvanceDto billAdDto = new BillAdvanceDto();
				// 设置工作流号
				billAdDto.setWorkflowNo(workflowNo);
				// 预付款申请金额
				billAdDto.setAmount(amount);
				// 付款部门标杆编码
				billAdDto.setPaymentOrgCode(depno);
				// 备注
				billAdDto.setNotes(examineConment);
				// 审批结果0:批准 1：拒绝 2：批准转拒绝
				billAdDto.setAuditStatus(String.valueOf(examineResult));
				//预付单号
				List<String> advanPayNolist = new ArrayList<String>();
				//将预付单号放到集合中
				advanPayNolist.add(paymentBillNos);
				//设置预付单号列表
				billAdDto.setAdvanPayNoList(advanPayNolist);
				// 设置返回财务共享工作流号
				response.setWorkflowNo(request.getWorkflowNo());
				// Foss更新结果
				try {
					//调用接口处理
					billAdvanceApplysManageService.dealBillAdvancePayResultProcessor(billAdDto);
					//封装返回结果
					response.setResult(true);
					//封装返回结果
					response.setWorkflowNo(workflowNo);
					//封装返回结果
					response.setWorkflowType(workflowtype);
					//异常处理
				} catch (BusinessException be) {
					//记录日志
					logger.error(be.getErrorArguments(),be);
					//封装返回结果
					response.setResult(false);
					//封装返回结果
					response.setWorkflowNo(workflowNo);
					//封装返回结果
					response.setWorkflowType(workflowtype);
					//封装返回结果
					response.setReason(be.getErrorCode());
				}
			}
		}
		//记录日志
		logger.info("结束接收付款、备用金报销、预付结果.");
		//返回
		return response;
	}
	
	/**
	 * 校验传入参数
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-5 下午6:32:59
	 */
	private void valiadteParams(PmtResultNotificationRequest request){
		//校验金额必须大于0
		if(request.getPayBillAmount()==null || request.getPayBillAmount().compareTo(BigDecimal.ZERO)<1){
			throw new SettlementException("传入审核金额不能为空且必须大于0！");
		}
		//校验工作流号
		if(StringUtils.isEmpty(request.getWorkflowNo())){
			throw new SettlementException("传入工作流号不能为空！");
		}
		//校验工作流号
		if(StringUtils.isEmpty(request.getWorkflowType())){
			throw new SettlementException("传入工作流类型不能为空！");
		}
		//校验部门编码
		if(StringUtils.isEmpty(request.getPayBillDeptNo())){
			throw new SettlementException("传入付款部门编码不能为空！");
		}
	}
	
	/**
	 * 
	 * 异常处理
	 * @author 045738-foss-maojianqiang
	 * @date 2013-3-14 上午10:39:38
	 * @param req
	 * @return
	 * @throws ESBBusinessException
	 * @see com.deppon.esb.core.process.IProcess#errorHandler(java.lang.Object)
	 */
	public Object errorHandler(Object req) throws ESBBusinessException {
		//记录异常
		logger.error("处理工作流结果报错！");
		return null;
	}
	/**
	 * @param billAdvanceApplysManageService
	 */
	public void setBillAdvanceApplysManageService(
			IBillAdvanceApplysManageService billAdvanceApplysManageService) {
		this.billAdvanceApplysManageService = billAdvanceApplysManageService;
	}

	/**
	 * @param receiveHandleResult
	 */
	public void setReceiveHandleResult(IReceiveHandleResult receiveHandleResult) {
		this.receiveHandleResult = receiveHandleResult;
	}

	/**
	 * @param orgAdministrativeInfoService
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	
}
