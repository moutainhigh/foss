package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.esb.inteface.domain.payment.ExpenseDetail;
import com.deppon.esb.inteface.domain.payment.StowageEntity;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptResultDto;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillDepositReceivedService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IClientSendVtsPayableCheck;
import com.deppon.foss.module.settlement.common.api.server.service.IOperatingLogService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.OperatingLogEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.RequestPayableCheckEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.ResponsePayableCheckEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCWayBillEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillInfoEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPaymentDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillPaymentManageService;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillPaymentPayService;
import com.deppon.foss.module.settlement.pay.api.server.service.IPayToCostcontrolSendService;
import com.deppon.foss.module.settlement.pay.api.server.service.IPayToFSSCSendService;
import com.deppon.foss.module.settlement.pay.api.server.service.IPaymentStatusToVTSClient;
import com.deppon.foss.module.settlement.pay.api.shared.dto.PayToCostcontrolDto;
import com.deppon.foss.module.transfer.load.api.server.service.IOutsideVehicleChargeService;
import com.deppon.foss.module.transfer.load.api.shared.vo.OutVehicleAssembleBillAndFeeVo;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.fssc.inteface.domain.payment.StowageDetail;

/**
 * 付款单服务
 * 
 * @author 045738-foss-maojianqiang
 * @date 2012-12-3 上午9:19:26
 */
public class BillPaymentManageService implements IBillPaymentManageService {

	/**
	 * 定义费用说明，给费控传递需要
	 */
	private static final String PAYMENTDESC = "申请备用金工作流";

	/**
	 * 声明反审核最大超期天数
	 */
	private static final int REVERSEAUDIT_MAX = 30;

	/**
	 * 获取日志
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(BillPaymentManageService.class);

	/**
	 * 注入付款单公共服务接口
	 */
	private IBillPaymentService billPaymentService;

	/**
	 * 注入费控接口
	 */
	private IPayToCostcontrolSendService payToCostcontrolSendService;

	/**
	 * 注入财务共享接口
	 */
	private IPayToFSSCSendService payToFSSCSendService;
	
	/**
	 * 注入应付单服务接口
	 */
	private IBillPayableService billPayableService;

	/**
	 * 注入预收单服务接口
	 */
	private IBillDepositReceivedService billDepositReceivedService;

	/**
	 * 注入操作日志服务
	 */
	private IOperatingLogService operatingLogService;
	
	/**
	 * 注入备用金额度判断
	 */
	private IConfigurationParamsService configurationParamsService;

	/**
	 * 注入结算编码生成接口
	 */
	private ISettlementCommonService settlementCommonService;
	
	/**
	 * 注入部门service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 注入配载单信息，在付款时如果为外请车，需要将明细传递过去
	 */
	private IOutsideVehicleChargeService outsideVehicleChargeService;
	
	/**
	 * 注入付款服务
	 */
	private IBillPaymentPayService billPaymentPayService;
	
	/**
	 * 快递代理点部映射
	 */
	private IExpressPartSalesDeptService expressPartSalesDeptService;
	
	/**
	 * 申请备用金服务
	 */
	private IBillPaymentManageService billPaymentManageService;
	
    /**
     * 注入校验VTS单号+合同编码
     * @218392 zhangyongxue
     * @date 2016-06-22 13:30:15
     */
    private IClientSendVtsPayableCheck clientSendVtsPayableCheck;
	/**
	 * 395982 zhengyating
	 */
    private IPaymentStatusToVTSClient paymentStatusToVTSClient;
	
//	/**
//	 * 
//	 */
//	private String expressOrgUniCode;
	
	/**
	 * 申请备用金工作流服务
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-1 下午3:06:54
	 */
	@Transactional
	public void applyPettyCash(PayToCostcontrolDto dto, CurrentInfo currentInfo) {
		logger.info("申请备用金工作start" + dto);
		// 校验传入付款单集合
		if (CollectionUtils.isEmpty(dto.getPaymentNos())) {
			throw new SettlementException("传入付款单集合为空,请至少勾选一条付款单！");
		}
		
		//付款对接系统  Y--财务共享，N--代表费控 
		String payToSystem = BillPaymentPayService.getPayToSystem();
		
		// 根据付款单号查询付款单
		List<BillPaymentEntity> list = billPaymentService.queryBillPaymentByPaymentNOs(dto.getPaymentNos(),FossConstants.ACTIVE);
		// 校验是否可以申请备用金,同时封装dto
		if (CollectionUtils.isNotEmpty(list)) {
			if(SettlementDictionaryConstants.SETTLEMENT__PAYTOSYSTEM_TYPE_CONSCONTROL.equals(payToSystem)){
				//校验备用金是否可以申请 --费控
				canApplyPettyCash(list,dto,currentInfo);
			}else if(SettlementDictionaryConstants.SETTLEMENT__PAYTOSYSTEM_TYPE_FSSC.equals(payToSystem)){
				//校验备用金是否可以申请 --财务共享
				canApplyPettyCashForFSSC(list,dto,currentInfo);
			}else{
				throw new SettlementException("付款工作流对接系统数据字典配置有误，请重新配置！");
			}
		} else {
			throw new SettlementException("没有查询到对应的付款单");
		}

		// 声明要更新的付款单dto
		BillPaymentDto billPaymentDto = new BillPaymentDto();
		billPaymentDto.setBillPayments(list);// 设置更新付款单集合
		//设置银行账号等相关信息
		billPaymentDto.setMobilePhone(dto.getPayBillCelephone());//手机号
		billPaymentDto.setAccountNo(dto.getPayBillBankNo());//账号
		billPaymentDto.setPayeeCode(dto.getPayBillPayeeCode());//收款人编码
		billPaymentDto.setPayeeName(dto.getPayBillPayeeName());//收款人名称
		billPaymentDto.setAccountType(dto.getAccountType());//账户类型
		billPaymentDto.setProvinceName(dto.getProvinceName());//省份名称
		billPaymentDto.setProvinceCode(dto.getProvinceCode());//省份编码
		billPaymentDto.setCityCode(dto.getCityCode());//城市编码
		billPaymentDto.setCityName(dto.getCityName());//城市名称
		billPaymentDto.setBankHqCode(dto.getBankHqCode());//开户行编码
		billPaymentDto.setBankHqName(dto.getBankHqName());//开户行名称
		billPaymentDto.setBankBranchCode(dto.getBankBranchCode());//支行编码
		billPaymentDto.setBankBranchName(dto.getBankBranchName());//支行名称
		billPaymentDto.setInvoiceHeadCode(dto.getPayBillComNo());//发票抬头编码
		billPaymentDto.setInvoiceHeadName(dto.getPayBillComName());//发票抬头名称
		
		//获取付款批次号
		String batchNo = null;
		if(null != dto.getBatchNo() && !"".equals(dto.getBatchNo())){
			batchNo = dto.getBatchNo();
		}else{
			batchNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.FK_BN);
		}
		//付款批次号不能为空
		if(StringUtils.isBlank(batchNo)){
			throw new SettlementException("付款批次号不能为空！");
		}
		billPaymentDto.setBatchNo(batchNo);//设置批次号
		billPaymentDto.setRemitStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__TRANSFERRING);// 设置更新汇款状态为"汇款中"

		// 批量更新付款单为付款中
		billPaymentManageService.updatePaymentBatchNo(billPaymentDto,currentInfo);
//		billPaymentService.batchReverseRemitStatusBillPayment(billPaymentDto,currentInfo);
		//设置付款批次号
		dto.setBatchNo(batchNo);
		//设置付款编号
		dto.setPaymentBillNo(batchNo);
		
		//对接费控
		if(SettlementDictionaryConstants.SETTLEMENT__PAYTOSYSTEM_TYPE_CONSCONTROL.equals(payToSystem)){
			try {
				// 申请备用金工作流
				payToCostcontrolSendService.payToCostcontrol(dto);
				logger.info("申请备用金工作end");
			} catch (Exception e) {
				billPaymentDto.setRemitStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__NOT_TRANSFER);// 设置更新汇款状态为"汇款中"
				billPaymentManageService.updatePaymentBatchNoBack(billPaymentDto,currentInfo);
				logger.error("申请备用金工作流失败！"+e.getMessage(), e);
				throw new SettlementException("申请备用金工作流失败！"+e.getMessage(), e.getMessage());
			}
		//对接财务共享	
		}else if(SettlementDictionaryConstants.SETTLEMENT__PAYTOSYSTEM_TYPE_FSSC.equals(payToSystem)){
			try {
				// 申请备用金工作流
				payToFSSCSendService.payToCostcontrol(dto);
				logger.info("申请备用金工作end");
			} catch (SettlementException e) {
				billPaymentDto.setRemitStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__NOT_TRANSFER);// 设置更新汇款状态为"汇款中"
				billPaymentManageService.updatePaymentBatchNoBack(billPaymentDto,currentInfo);
				logger.error("申请备用金工作流失败！"+e.getMessage(), e);
				throw new SettlementException(e.getErrorCode());
			}
		}else{
			throw new SettlementException("付款对接系统配置有误！");
		}
		
		
	}
	/**
	 * 批量更新付款单为付款中
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void updatePaymentBatchNo(BillPaymentDto billPaymentDto, CurrentInfo currentInfo) {
		// 批量更新付款单为付款中
		billPaymentService.batchReverseRemitStatusBillPayment(billPaymentDto,currentInfo);
	}
	
	/**
	 * 批量还原付款单为付款中
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void updatePaymentBatchNoBack(BillPaymentDto billPaymentDto, CurrentInfo currentInfo) {
		// 批量还原付款单为付款中
		billPaymentService.updatePaymentBatchNoBack(billPaymentDto,currentInfo);
	}
	
	/**
	 * 申请完后费控返回结果，更新备用金工作流号
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-1 上午8:55:27
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IBillPaymentPayService#updateWorkFlow(java.util.List,
	 *      java.lang.String)
	 */
	@Override
	@Transactional
	public void updateApplyWorkFlow(String paymentNos,
			String applyWorkflowNo) {
		logger.info("费控返回结果,更新备用金工作流号start: 付款编号" + paymentNos
				+ ",工作流号:" + applyWorkflowNo);
		// 校验传入更新付款单id集合
		if (StringUtils.isNotBlank(paymentNos)) {
			// 校验工作流号
			if (StringUtils.isNotBlank(applyWorkflowNo)) {
				// 声明dto
				BillPaymentDto dto = new BillPaymentDto();
				// 根据id查询对应的付款单实体
				List<BillPaymentEntity> billPayments = billPaymentService
						.queryPaymentByBatchNos(paymentNos,
								FossConstants.ACTIVE);
				dto.setBillPayments(billPayments);// 设置更新付款单集合
				dto.setApplyWorkFlowNo(applyWorkflowNo);// 设置工作流号
				// 反写备用金工作流号
				try {
					billPaymentService.batchReverseApplyWorkflowBillPayment(
							dto, SettlementUtil.getFKCurrentInfo());
					logger.info("费控返回结果,更新备用金工作流号end");
					extracted(applyWorkflowNo, billPayments);				
				    } catch (BusinessException e) {
				    	logger.error(e.getMessage(), e);
					    throw e;
					} catch (Exception e) {
					logger.error(e.getMessage(), e);
					throw new SettlementException("更新备用金工作流号失败！");
				}
			} else {
				logger.error("传入工作流号为空!");
				throw new SettlementException("传入工作流号为空！");
			}
		} else {
			logger.error("传入付款编号为空!");
			throw new SettlementException("传入付款编号为空！");
		}
		logger.info("费控返回结果,更新备用金工作流号结束: 付款编号" + paymentNos
				+ ",工作流号:" + applyWorkflowNo);
	}
	/**
	 * FOSS向VTS回传字段信息需求
	 * @author 395982 zhengyating
	 * @date 2017-01-12
	 */
	private void extracted(String applyWorkflowNo,
			List<BillPaymentEntity> billPayments) {
		//根据付款单号查询应付单集合
		BillPayableConditionDto payableDtos = new BillPayableConditionDto();
		//定义待传输的实体
		BillInfoEntity billInfoEntity = new BillInfoEntity();
		List<String> billIds = new ArrayList<String>();
		for(BillPaymentEntity paymentEntity : billPayments){
			logger.info("paymentEntity" + paymentEntity.toString());
			// 查询应付单
			payableDtos.setPaymentNo(paymentEntity.getPaymentNo());
			// 获取应付单列表
			List<BillPayableEntity> payableList = billPayableService
					.queryBillPayableByCondition(payableDtos);							
			//循环判断，将符合条件的应付单中的运单号放到一个list里面
			for(BillPayableEntity payable : payableList){
				logger.info("payable" + payable.toString());
				//判断应付单据的来源单据类型是否为TC
				if(null != payable && SettlementDictionaryConstants
						.BILL_PAYABLE__SOURCE_BILL_TYPE__TRANSPORT_CONTRACTS.equals(payable.getSourceBillType())){
					billIds.add(payable.getWaybillNo());
					}
				}
			}
		//设置运单号集合
		billInfoEntity.setBillIds(billIds);
		//工作流号
		billInfoEntity.setPaymentNumber(applyWorkflowNo);
		//汇款状态 汇款中
		billInfoEntity.setPaymentStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__TRANSFERRING);
		//调用VTS同步接口
		paymentStatusToVTSClient.sendPaymentStatusToVTS(billInfoEntity);
		logger.info("调用VTS同步接口成功");
	}

	/**
	 * 审核付款单
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-1 下午5:30:20
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IBillPaymentPayService#auditPaymentOrderBill(java.util.List,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	@Transactional
	public void auditPaymentOrderBill(List<String> paymentNos, String opinions,
			CurrentInfo currentInfo) {
		logger.info("审核付款单start: 付款单条数" + paymentNos.size());
		// 判断付款单号集合
		if (CollectionUtils.isEmpty(paymentNos)) {
			throw new SettlementException("传入付款单号集合为空！");
		}
		// 调用接口查询
		List<BillPaymentEntity> paymentList = billPaymentService
				.queryBillPaymentByPaymentNOs(paymentNos, FossConstants.ACTIVE);
		// 比较查询结果与查询条件是否条数一致
		compareResultToQueryParams(paymentNos, paymentList);
		// 校验要审核的付款单是否符合条件
		canAuditPayment(paymentList);
		// 声明接口传入参数
		BillPaymentDto dto = new BillPaymentDto();
		dto.setBillPayments(paymentList);// 审核付款单list
		dto.setAuditOpinion(opinions);// 审核意见
		// 更新审核状态
		billPaymentService.batchAuditBillPayment(dto, currentInfo);
		// 插入日志
		addOperateLog(
				paymentList,
				SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__AUDIT,
				opinions, currentInfo);
		logger.info("审核付款单end");
	}

	/**
	 * 反审核付款单
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-1 下午5:30:20
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IBillPaymentPayService#reverseAuditPaymentOrder(java.util.List,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	@Transactional
	public void reverseAuditPaymentOrder(List<String> paymentNos,
			String opinions, CurrentInfo currentInfo) {
		logger.info("反审核付款单start: 付款单条数" + paymentNos.size());
		// 判断付款单号集合
		if (CollectionUtils.isEmpty(paymentNos)) {
			throw new SettlementException("传入付款单号集合为空！");
		}
		// 调用接口查询
		List<BillPaymentEntity> paymentList = billPaymentService
				.queryBillPaymentByPaymentNOs(paymentNos, FossConstants.ACTIVE);
		// 比较查询结果与查询条件是否条数一致
		compareResultToQueryParams(paymentNos, paymentList);
		// 校验要审核的付款单是否符合条件
		canReverseAuditPayment(paymentList);
		// 声明接口传入参数
		BillPaymentDto dto = new BillPaymentDto();
		dto.setBillPayments(paymentList);
		dto.setAuditOpinion(opinions);// 审核意见
		// 更新审核状态
		billPaymentService.batchReverseAuditBillPayment(dto, currentInfo);
		// 插入日志
		addOperateLog(
				paymentList,
				SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__REVERSE_AUDIT,
				opinions, currentInfo);
		logger.info("反审核付款单end");
	}

	/**
	 * 作废
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-1 下午5:30:20
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IBillPaymentPayService#invalidPaymentOrder(java.util.List,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	@Transactional
	public void invalidPaymentOrder(List<String> paymentNos, String opinions,
			CurrentInfo currentInfo) {
		logger.info("作废付款单start: 付款单条数" + paymentNos.size());
		// 判断付款单号集合
		if (CollectionUtils.isEmpty(paymentNos)) {
			throw new SettlementException("传入付款单号集合为空！");
		}
		// 调用接口查询
		List<BillPaymentEntity> paymentList = billPaymentService
				.queryBillPaymentByPaymentNOs(paymentNos, FossConstants.ACTIVE);
		// 比较查询结果与查询条件是否条数一致
		compareResultToQueryParams(paymentNos, paymentList);
		// 校验要作废的付款单是否符合条件
		canInvalidPayment(paymentList, currentInfo);
		//处理作废
		dealInvalidPayment(paymentList,opinions,currentInfo);
		
		// 插入日志
		addOperateLog(
				paymentList,
				SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__DISABLE,
				opinions, currentInfo);
		logger.info("作废付款单end");
	}

	/**
	 * 处理付款单作废内容，反写付款单，反写对应应付和预收单
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-6 上午9:06:17
	 */
	public List<BillPayableEntity> dealInvalidPayment(List<BillPaymentEntity> paymentList, String opinions,
			CurrentInfo currentInfo){
		//声明理赔应付单list
		List<BillPayableEntity> claimList = new ArrayList<BillPayableEntity>();
		// 进行作废操作
		for (BillPaymentEntity entity : paymentList) {
			// 添加作废意见
			entity.setDisableOpinion(opinions);
			// 循环作废
			billPaymentService.writeBackBillPayment(entity, currentInfo);
			//反写付款单对应明细
			List<BillPayableEntity> list = writeBackPaymentEntryByPaymentNo(entity, currentInfo);
			claimList.addAll(list);
		}
		return claimList;
	}
	
	/**
	 * 作废付款单时，根据付款单反写其对应明细
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-6 下午4:37:59
	 */
	public List<BillPayableEntity> writeBackPaymentEntryByPaymentNo(BillPaymentEntity entity,CurrentInfo currentInfo){
		logger.info("作废-根据付款单反写其对应明细开始");
		List<BillPayableEntity> claimList = new ArrayList<BillPayableEntity>();
		// 如果来源单据为应付单，则只需要更新应付单数据
		if (StringUtils.isNotBlank(entity.getSourceBillType())
				&& SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__ADVANCED_PAYMENT
						.equals(entity.getSourceBillType())) {
			// 反写应付单
			claimList = writeBackBillPayable(entity, currentInfo);
			// 如果来源单据未预收单，则只需要更新预收单
		} else if (StringUtils.isNotBlank(entity.getSourceBillType())
				&& SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__DEPOSIT_RECEIVED
						.equals(entity.getSourceBillType())) {
			// 反写预收单
			writeBackBillDepositReceived(entity, currentInfo);
		} else {
			// 反写应付单
			claimList = writeBackBillPayable(entity, currentInfo);
			// 反写预收单
			writeBackBillDepositReceived(entity, currentInfo);
		}
		logger.info("作废-根据付款单反写其对应明细结束");
		return claimList;
	}
	
	
	/**
	 * 
	 * 作废-反写应付单
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-4 下午2:37:50
	 */
	private List<BillPayableEntity> writeBackBillPayable(BillPaymentEntity entity,
			CurrentInfo currentInfo) {
		logger.info("作废-根据付款单反写反写应付单开始");
		//声明理赔付款应付单 
		List<BillPayableEntity> claimList = new ArrayList<BillPayableEntity>();
		// 查询应付单
		BillPayableConditionDto dto = new BillPayableConditionDto();
		dto.setPaymentNo(entity.getPaymentNo());
		// 获取应付单列表
		List<BillPayableEntity> list = billPayableService
				.queryBillPayableByCondition(dto);
		// 循环释放应付单
		for (BillPayableEntity payable : list) {
			// 声明要更新的应付单dto
			BillPayableDto payableDto = new BillPayableDto();
			payableDto.setId(payable.getId());
			payableDto.setAccountDate(payable.getAccountDate());
			payableDto.setVersionNo(payable.getVersionNo());
			// 进行反写应付单支付状态、付款金额、付款单号等操作
			billPayableService.cancelPayForBillPayable(payableDto, currentInfo);
			//如果为理赔在放到该list中
			if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM.equals(payable.getBillType())){
				claimList.add(payable);
			}
		}
		logger.info("作废-根据付款单反写反写应付单结束");
		return claimList;
	}

	/**
	 * 
	 * 作废-反写预收单
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-4 下午2:37:50
	 */
	private void writeBackBillDepositReceived(BillPaymentEntity entity,
			CurrentInfo currentInfo) {
		logger.info("作废-根据付款单反写反写预收单开始");
		// 查询预付单
		List<BillDepositReceivedEntity> depositList = billDepositReceivedService
				.queryByPaymentNo(entity.getPaymentNo());
		// 循环反写预付单
		for (BillDepositReceivedEntity bill : depositList) {
			billDepositReceivedService.cancelPayForBillDepositReceived(bill,
					currentInfo);
		}
		logger.info("作废-根据付款单反写反写预收单结束");
	}

	/**
	 * 校验是否可以申请备用金
	 * @param  payToSystem --付款对接系统 Y为财务共享，N为费控
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-1 下午3:44:58
	 */
	@SuppressWarnings("unchecked")
	private void canApplyPettyCash(List<BillPaymentEntity> list,
			PayToCostcontrolDto dto, CurrentInfo currentInfo) {
		// 声明申请备用金总金额
		BigDecimal payAmount = BigDecimal.ZERO;
		//调用综合接口获取备用金最大最小值
		ConfigurationParamsEntity pettyCashMaxEntity = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__STL,ConfigurationParamsConstants.STL_PETTY_CASH_MAX_AMOUNT,currentInfo.getCurrentDeptCode());
		ConfigurationParamsEntity pettyCashMinEntity =  configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__STL,ConfigurationParamsConstants.STL_PETTY_CASH_MIN_AMOUNT,currentInfo.getCurrentDeptCode());
		//声明付款单的工作流类型
		String workFlowType = null;
		//声明押回单到达标志
		String returnBackBalance = null;
		
		//付款单来源单据为应付单集合  --只查询应付单
		List<String> paymentNosFromPayable = new ArrayList<String>();
		//付款单来源单据为预收单集合 -- 只查询预收单
		List<String> paymentNosFromDeopsit = new ArrayList<String>();
		//付款单来源单据为其它时付款单号集合 -- 两个都查询
		List<String> paymentNos = new ArrayList<String>();
		
		// 循环校验
		for (BillPaymentEntity en : list) {
			// 判断付款方式
			if (StringUtils.isBlank(en.getPaymentType())
					|| !SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH
							.equals(en.getPaymentType())) {
				throw new SettlementException("付款单:" + en.getPaymentNo()
						+ "的付款方式不是现金，不能申请备用金工作流");
			}
			// 校验汇款状态
			if (StringUtils.isBlank(en.getRemitStatus())
					|| !SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__NOT_TRANSFER
							.equals(en.getRemitStatus())) {
				throw new SettlementException("付款单:" + en.getPaymentNo()
						+ "的汇款状态不是未汇款，不能申请备用金工作流");
			}
			//判断工作流类型是否为空
			if(StringUtils.isBlank(en.getWorkFlowType())){
				throw new SettlementException("付款单:" + en.getPaymentNo()
						+ "的工作流类型不能为空！");
			}
			//判断工作流类型是否数据不同类型，如果为不同类型，则不能进行申请操作
			if(StringUtils.isBlank(workFlowType)){
				workFlowType = en.getWorkFlowType();
			}else{
				//如果不同类型，则不能进行申请操作
				if(!StringUtils.equals(workFlowType, en.getWorkFlowType())){
					throw new SettlementException("付款单:" + en.getPaymentNo()+"的工作流类型与之前付款单工作流类型不一致，不能同时申请备用金！");
				}
			}
			//判断付款单是否都为押回单到达类型或者全都不是才能一起申请
			if(StringUtils.isBlank(returnBackBalance)){
				if(StringUtils.isNotEmpty(en.getAuditOpinion()) 
						&& SettlementConstants.RETURN_BACK_BALANCE.equals(en.getAuditOpinion())){
					returnBackBalance = FossConstants.YES;
				}else{
					returnBackBalance = FossConstants.NO;
				}
			}else{
				String returnBackBalanceNew = null;
				if(StringUtils.isNotEmpty(en.getAuditOpinion()) 
						&& SettlementConstants.RETURN_BACK_BALANCE.equals(en.getAuditOpinion())){
					returnBackBalanceNew = FossConstants.YES;
				}else{
					returnBackBalanceNew = FossConstants.NO;
				}
				if(!returnBackBalance.equals(returnBackBalanceNew)){
					throw new SettlementException("付款单："+en.getPaymentNo()+"与前面所勾选的付款单不都为押回单到达付款单，不能一起付款！");
				}
			}
			// 算申请备用金总金额
			payAmount = payAmount.add(en.getAmount());
			
			// 如果来源单据为应付单，则只需要更新应付单数据
			if (StringUtils.isNotBlank(en.getSourceBillType())
					&& SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__ADVANCED_PAYMENT
							.equals(en.getSourceBillType())) {
				//添加单号到集合中
				paymentNosFromPayable.add(en.getPaymentNo());
				// 如果来源单据未预收单，则只需要更新预收单
			} else if (StringUtils.isNotBlank(en.getSourceBillType())
					&& SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__DEPOSIT_RECEIVED
							.equals(en.getSourceBillType())) {
				//添加单号到集合中
				paymentNosFromDeopsit.add(en.getPaymentNo());
			} else {
				paymentNos.add(en.getPaymentNo());
			}
		}
		
		//判断备用金最大额度	
		if(pettyCashMaxEntity==null || StringUtils.isBlank(pettyCashMaxEntity.getConfValue())){
			throw new SettlementException("本部门备用金最大额度没有录入，请去综合管理进行录入！");
		}
		//判断备用金最小额度
		if(pettyCashMinEntity==null || StringUtils.isBlank(pettyCashMinEntity.getConfValue())){
			throw new SettlementException("本部门备用金最小额度没有录入，请去综合管理进行录入！");
		}
		//获取最大额度
		BigDecimal pettyCashMax = new BigDecimal(pettyCashMaxEntity.getConfValue());
		//获取最小额度
		BigDecimal pettyCashMin = new BigDecimal(pettyCashMinEntity.getConfValue());
		//如果备用金大于部门最大额度，则提示
		if(payAmount.compareTo(pettyCashMax)>0){
			throw new SettlementException("申请备用金大于本部门最大额度！本部门最大备用金额度为"+pettyCashMax);
		}
		
		//如果备用金大于部门最大额度，则提示
		if(payAmount.compareTo(pettyCashMin)<0){
			throw new SettlementException("申请备用金小于本部门最小额度！本部门最小备用金额度为"+pettyCashMin);
		}
		
	
		
		// 获取第一条付款单
		BillPaymentEntity bill = list.get(0);
		/**
		 * 获取付款编号  在调用地方和批次号一块封装。 
		 * 银行账号、收款人姓名、联系人手机号、最迟汇款日期从界面申请地方传递过来了
		 */
		// 封装dto的其他属性
		//根据部门编码查询部门对象
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(bill.getPaymentOrgCode());
		//校验部门
		if(org!=null && StringUtils.isNotBlank(org.getUnifiedCode())){
			dto.setPayBillDeptNo(org.getUnifiedCode());// 获取部门标杆编码
		}else{
			throw new SettlementException("付款单"+bill.getPaymentNo()+"的付款部门标杆编码不存在！不能申请备用金");
		}
		dto.setPayBillDeptName(bill.getPaymentOrgName());// 获取部门名称
		//如果界面金额和后台计算金额不相等，则抛出异常
		if(payAmount.compareTo(dto.getPayBillAmount())!=0){
			throw new SettlementException("后台付款金额之和与界面付款金额之和不一致，请刷新界面重新进行申请！");
		}
		dto.setPayBillAmount(payAmount);// 获取总金额
		dto.setPayBillAppNo(currentInfo.getEmpCode());// 获取员工编号
		dto.setPayBillAppType(workFlowType);//工作流类型
		dto.setPayType(SettlementESBDictionaryConstants.COST_CONTROL_PAYMENT_APPLY);//支付类型  --报销
		/**
		 * 封装报销工作流的明细
		 */
		
		//封装报销工作流明细
		Map<String,Object> map= getPaymentDetails(paymentNosFromPayable,paymentNosFromDeopsit,paymentNos,org,currentInfo);
		List<ExpenseDetail> detail = (List<ExpenseDetail>) map.get("detailList");
		dto.setExpenseDetail(detail);//明细
		List<StowageEntity> stowageDetail = (List<StowageEntity>) map.get("stowageDetail");
		dto.setStowageEntityList(stowageDetail);//配载单信息
		
	}

	/**
	 * 判断是否可以审核
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-3 上午9:59:52
	 */
	private void canAuditPayment(List<BillPaymentEntity> list) {
		// 循环校验
		for (BillPaymentEntity en : list) {
			// 校验审核状态
			if (StringUtils.isBlank(en.getAuditStatus())
					|| !SettlementDictionaryConstants.BILL_PAYMENT__AUDIT_STATUS__NOT_AUDIT
							.equals(en.getAuditStatus())) {
				throw new SettlementException("付款单：" + en.getPaymentNo()
						+ "的审核状态不是未审核，不能进行审核操作！");
			}
			// 校验汇款状态
			if (StringUtils.isBlank(en.getRemitStatus())
					|| !SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__NOT_TRANSFER
							.equals(en.getRemitStatus())) {
				throw new SettlementException("付款单：" + en.getPaymentNo()
						+ "的汇款状态不是未汇款，不能进行审核操作！");
			}
		}
	}

	/**
	 * 判断是否可以反审核
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-3 上午9:59:52
	 */
	private void canReverseAuditPayment(List<BillPaymentEntity> list) {
		// 循环校验
		for (BillPaymentEntity en : list) {
			// 校验审核状态
			if (StringUtils.isBlank(en.getAuditStatus())
					|| !SettlementDictionaryConstants.BILL_PAYMENT__AUDIT_STATUS__AUDIT_AGREE
							.equals(en.getAuditStatus())) {
				throw new SettlementException("付款单：" + en.getPaymentNo()
						+ "的审核状态不是已审核，不能进行反审核操作！");
			}
			// 校验会计日期
			if (en.getAccountDate() != null) {
				if (DateUtils.getTimeDiff(en.getAccountDate(), new Date()) > REVERSEAUDIT_MAX) {
					throw new SettlementException("付款单：" + en.getPaymentNo()
							+ "的记账日期也当前时间超过30天，不能进行反审核操作！");
				}
			} else {
				throw new SettlementException("付款单：" + en.getPaymentNo()
						+ "的记账日期为空！");
			}

			// 校验汇款状态
			if (StringUtils.isBlank(en.getRemitStatus())
					|| !SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__NOT_TRANSFER
							.equals(en.getRemitStatus())) {
				throw new SettlementException("付款单：" + en.getPaymentNo()
						+ "的汇款状态不是未汇款，不能进行反审核操作！");
			}
		}
	}

	/**
	 * 判断是否可以作废
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-3 上午11:45:56
	 */
	private void canInvalidPayment(List<BillPaymentEntity> list,CurrentInfo currentInfo) {
		// 循环校验
		for (BillPaymentEntity en : list) {

			// 校验汇款状态
			if (StringUtils.isBlank(en.getRemitStatus())
					|| !SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__NOT_TRANSFER
							.equals(en.getRemitStatus())) {
				throw new SettlementException("付款单：" + en.getPaymentNo()
						+ "的汇款状态不是未汇款，不能进行作废操作！");
			}
			// 校验生成方式
			if (StringUtils.isBlank(en.getCreateType())
					|| !SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL
							.equals(en.getCreateType())) {
				throw new SettlementException("付款单：" + en.getPaymentNo()
						+ "的生成方式不是手动生成，不能进行作废操作！");
			}
			// 校验当前登录人与信息录入人
			if (StringUtils.isBlank(en.getCreateUserCode())
					|| currentInfo.getEmpCode().equals(en.getCreateUserCode())) {
				 throw new SettlementException("付款单："+en.getPaymentNo()+"的录入人是当前登录人，不能进行作废操作！");
			}
		}
	}

	/**
	 * 比较传入单号集合与查询结果条数是否一致
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-3 上午10:27:57
	 */
	private void compareResultToQueryParams(List<String> paymentNos,
			List<BillPaymentEntity> paymentList) {
		// 判断传入审核条数和查询条数是否一致
		if (paymentList.size() != paymentNos.size()) {
			// 声明查询结果的单号集合
			List<String> resultPaymentNos = new ArrayList<String>();
			for (BillPaymentEntity entity : paymentList) {
				resultPaymentNos.add(entity.getPaymentNo());
			}
			// 循环判断哪个单号没有查询到
			StringBuffer sb = new StringBuffer("付款单：");
			for (String s : paymentNos) {
				// 如果该单号没查询到，则将其返回给用户提示
				if (!resultPaymentNos.contains(s)) {
					sb.append(s);
					sb.append(" ");
				}
			}
			sb.append("根据单号，没有查询到对应的数据！");
			throw new SettlementException(sb.toString());
		}
	}

	/**
	 * 循环插入日志
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-3 上午11:41:02
	 */
	private void addOperateLog(List<BillPaymentEntity> paymentList,
			String operateType, String notes, CurrentInfo currentInfo) {
		// 循环插入日志
		for (BillPaymentEntity en : paymentList) {
			// 插入日志
			OperatingLogEntity log = new OperatingLogEntity();
			// 设置类型
			log.setOperateBillNo(en.getPaymentNo());
			// 设置操作单据类型--付款单
			log.setOperateBillType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__BILL_PAYMENT);
			// 设置操作类型--审核
			log.setOperateType(operateType);
			// 设置操作备注
			log.setNotes(notes);
			// 调用接口进行插入日志
			operatingLogService.addOperatingLog(log, currentInfo);
		}
	}
	
	/**
	 * 校验是否可以申请备用金
	 * @param  payToSystem --付款对接系统 Y为财务共享，N为费控
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-1 下午3:44:58
	 */
	@SuppressWarnings("unchecked")
	private void canApplyPettyCashForFSSC(List<BillPaymentEntity> list,
			PayToCostcontrolDto dto, CurrentInfo currentInfo) {
		// 声明申请备用金总金额
		BigDecimal payAmount = BigDecimal.ZERO;
		//调用综合接口获取备用金最大最小值
		ConfigurationParamsEntity pettyCashMaxEntity = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__STL,ConfigurationParamsConstants.STL_PETTY_CASH_MAX_AMOUNT,currentInfo.getCurrentDeptCode());
		ConfigurationParamsEntity pettyCashMinEntity =  configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__STL,ConfigurationParamsConstants.STL_PETTY_CASH_MIN_AMOUNT,currentInfo.getCurrentDeptCode());
		//声明付款单的工作流类型
		String workFlowType = null;
		Boolean isRentCarFlag = false;
		String paymentType = null;//后面要判断付款方式是否为临时租车
		
		//付款单来源单据为应付单集合  --只查询应付单
		List<String> paymentNosFromPayable = new ArrayList<String>();
		//付款单来源单据为预收单集合 -- 只查询预收单
		List<String> paymentNosFromDeopsit = new ArrayList<String>();
		//付款单来源单据为其它时付款单号集合 -- 两个都查询
		List<String> paymentNos = new ArrayList<String>();
		
		// 循环校验
		for (BillPaymentEntity en : list) {
			// 判断付款方式
			if (StringUtils.isBlank(en.getPaymentType())
					|| !SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH
							.equals(en.getPaymentType())) {
				throw new SettlementException("付款单:" + en.getPaymentNo()
						+ "的付款方式不是现金，不能申请备用金工作流");
			}
			// 校验汇款状态
			if (StringUtils.isBlank(en.getRemitStatus())
					|| !SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__NOT_TRANSFER
							.equals(en.getRemitStatus())) {
				throw new SettlementException("付款单:" + en.getPaymentNo()
						+ "的汇款状态不是未汇款，不能申请备用金工作流");
			}
			//判断工作流类型是否为空
			if(StringUtils.isBlank(en.getWorkFlowType())){
				throw new SettlementException("付款单:" + en.getPaymentNo()
						+ "的工作流类型不能为空！");
			}
			//判断工作流类型是否数据不同类型，如果为不同类型，则不能进行申请操作
			if(StringUtils.isBlank(workFlowType)){
				workFlowType = en.getWorkFlowType();
			}else{
				//如果不同类型，则不能进行申请操作
				if(!StringUtils.equals(workFlowType, en.getWorkFlowType())){
					if(SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_RENT_CAR_APPLY.equals(en.getWorkFlowType())
							||SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_RENT_CAR_TRAFFIC_APPLY.equals(en.getWorkFlowType())){
						throw new SettlementException("付款单:" + en.getPaymentNo()+"临时租车付款单不能与其他应付单类型生成的付款单合并补充备用金,且交通费不能与其他费用类型的临时租车应付单合并补充备用金");
					}
					throw new SettlementException("付款单:" + en.getPaymentNo()+"的工作流类型与之前付款单工作流类型不一致，不能同时申请备用金！");
				}
			}
			//如果为临时租车，则需要去判断是否有预提中数据
			if(SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_RENT_CAR_APPLY.equals(en.getWorkFlowType())
					||SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_RENT_CAR_TRAFFIC_APPLY.equals(en.getWorkFlowType())){
				isRentCarFlag = true;
				paymentType = SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__RENT_CAR;
			}
			// 算申请备用金总金额
			payAmount = payAmount.add(en.getAmount());
			
			// 如果来源单据为应付单，则只需要更新应付单数据
			if (StringUtils.isNotBlank(en.getSourceBillType())
					&& SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__ADVANCED_PAYMENT
							.equals(en.getSourceBillType())) {
				//添加单号到集合中
				paymentNosFromPayable.add(en.getPaymentNo());
				// 如果来源单据未预收单，则只需要更新预收单
			} else if (StringUtils.isNotBlank(en.getSourceBillType())
					&& SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__DEPOSIT_RECEIVED
							.equals(en.getSourceBillType())) {
				//添加单号到集合中
				paymentNosFromDeopsit.add(en.getPaymentNo());
			} else {
				paymentNos.add(en.getPaymentNo());
			}
		}
		
		//判断备用金最大额度	
		if(pettyCashMaxEntity==null || StringUtils.isBlank(pettyCashMaxEntity.getConfValue())){
			throw new SettlementException("本部门备用金最大额度没有录入，请去综合管理进行录入！");
		}
		//判断备用金最小额度
		if(pettyCashMinEntity==null || StringUtils.isBlank(pettyCashMinEntity.getConfValue())){
			throw new SettlementException("本部门备用金最小额度没有录入，请去综合管理进行录入！");
		}
		//获取最大额度
		BigDecimal pettyCashMax = new BigDecimal(pettyCashMaxEntity.getConfValue());
		//获取最小额度
		BigDecimal pettyCashMin = new BigDecimal(pettyCashMinEntity.getConfValue());
		//如果备用金大于部门最大额度，则提示
		if(payAmount.compareTo(pettyCashMax)>0){
			throw new SettlementException("申请备用金大于本部门最大额度！本部门最大备用金额度为"+pettyCashMax);
		}
		
		//如果备用金大于部门最大额度，则提示
		if(payAmount.compareTo(pettyCashMin)<0){
			throw new SettlementException("申请备用金小于本部门最小额度！本部门最小备用金额度为"+pettyCashMin);
		}
		
		// 获取第一条付款单
		BillPaymentEntity bill = list.get(0);
		/**
		 * 获取付款编号  在调用地方和批次号一块封装。 
		 * 银行账号、收款人姓名、联系人手机号、最迟汇款日期从界面申请地方传递过来了
		 */
		// 封装dto的其他属性
		//根据部门编码查询部门对象
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(bill.getPaymentOrgCode());
		//校验部门
		if(org!=null && StringUtils.isNotBlank(org.getUnifiedCode())){
			dto.setPayBillDeptNo(org.getUnifiedCode());// 获取部门标杆编码
		}else{
			throw new SettlementException("付款单"+bill.getPaymentNo()+"的付款部门标杆编码不存在！不能申请备用金");
		}
		dto.setPayBillDeptName(bill.getPaymentOrgName());// 获取部门名称
		//如果界面金额和后台计算金额不相等，则抛出异常
		if(payAmount.compareTo(dto.getPayBillAmount())!=0){
			throw new SettlementException("后台付款金额之和与界面付款金额之和不一致，请刷新界面重新进行申请！");
		}
		dto.setPayBillAmount(payAmount);// 获取总金额
		dto.setPayBillAppNo(currentInfo.getEmpCode());// 获取员工编号
		dto.setPayBillAppType(workFlowType);//工作流类型
		dto.setPayType(SettlementESBDictionaryConstants.COST_CONTROL_PAYMENT_APPLY);//支付类型  --报销
		dto.setCurrency(SettlementESBDictionaryConstants.CURRENCY_CODE_RMB);//币种--人民币
		dto.setExchangeRate(null);//汇率默认为空， foss暂时没有汇率
		dto.setExpenseDetailToFSSC(null);//明细	
		
		//封装报销工作流明细
		Map<String,Object> map= getFSSCPaymentDetails(paymentNosFromPayable,paymentNosFromDeopsit,paymentNos,org,paymentType);
		List<com.deppon.fssc.inteface.domain.payment.ExpenseDetail> detail = (List<com.deppon.fssc.inteface.domain.payment.ExpenseDetail>) map.get("detailList");
		dto.setExpenseDetailToFSSC(detail);//明细
		List<StowageDetail> stowageDetail = (List<StowageDetail>) map.get("stowageDetail");
		dto.setStowageDetail(stowageDetail);//配载单信息
	}
	/**
	 * 根据传入付款单号集合获取其对应明细
	 * @author 045738-foss-maojianqiang
	 * @date 2013-1-14 下午3:51:36
	 * @param paymentNosFromPayable  
	 * @param paymentNosFromDeopsit
	 * @param paymentNos
	 * @return
	 */
	private Map<String,Object> getPaymentDetails(List<String> paymentNosFromPayable,List<String> paymentNosFromDeopsit,
			List<String> paymentNos,OrgAdministrativeInfoEntity org,CurrentInfo currentInfo){
		//声明map
		Map<String,Object> map = new HashMap<String,Object>();
		//声明费控报销工作流明细
		List<ExpenseDetail> detailList = new ArrayList<ExpenseDetail>();
		//获取配载单detail
		List<StowageEntity> stowageEntityList = new ArrayList<StowageEntity>();
		//转化应付单明细 --付款工作流明细
		convertDetailFromPayable(detailList,paymentNosFromPayable,org,stowageEntityList,currentInfo);
		//转化预收单明细 --付款工作流明细
		convertDetailFromDeposit(detailList,paymentNosFromDeopsit,org);
		
		//paymentNos  --表示付款单可能同时存在应付单和预收单
		//转化应付单明细 --付款工作流明细
		convertDetailFromPayable(detailList,paymentNos,org,stowageEntityList,currentInfo);
		//转化预收单明细 --付款工作流明细
		convertDetailFromDeposit(detailList,paymentNos,org);
		map.put("detailList", detailList);
		map.put("stowageDetail", stowageEntityList);
		return map;
	}
	
	/**
	 * 根据传入付款单号集合获取其对应明细
	 * @author 045738-foss-maojianqiang
	 * @date 2013-1-14 下午3:51:36
	 * @param paymentNosFromPayable  
	 * @param paymentNosFromDeopsit
	 * @param paymentNos
	 * @return
	 */
	private Map<String,Object> getFSSCPaymentDetails(List<String> paymentNosFromPayable,List<String> paymentNosFromDeopsit,
			List<String> paymentNos,OrgAdministrativeInfoEntity org,String paymentType){
		//声明map
		Map<String,Object> map = new HashMap<String,Object>();
		//声明费控报销工作流明细
		List<com.deppon.fssc.inteface.domain.payment.ExpenseDetail> detailList = new ArrayList<com.deppon.fssc.inteface.domain.payment.ExpenseDetail>();
		//获取配载单detail
		List<StowageDetail> stowageDetail = new ArrayList<StowageDetail>();
		//转化应付单明细 --付款工作流明细
		convertDetailFromPayableToFSSC(detailList,paymentNosFromPayable,org,stowageDetail,paymentType);
		//转化预收单明细 --付款工作流明细
		convertDetailFromDepositToFSSC(detailList,paymentNosFromDeopsit,org);
		
		//paymentNos  --表示付款单可能同时存在应付单和预收单
		//转化应付单明细 --付款工作流明细
		convertDetailFromPayableToFSSC(detailList,paymentNos,org,stowageDetail,paymentType);
		//转化预收单明细 --付款工作流明细
		convertDetailFromDepositToFSSC(detailList,paymentNos,org);
		map.put("detailList", detailList);
		map.put("stowageDetail", stowageDetail);
		return map;
	}
	
	
	/**
	 * 根据应付单明细进行转化为工作流付款明细
	 * @author 045738-foss-maojianqiang
	 * @date 2013-1-14 下午4:19:13
	 * @param detailList
	 * @param paymentNos
	 */
	private void convertDetailFromPayable(List<ExpenseDetail> detailList,List<String> paymentNos,
											OrgAdministrativeInfoEntity org,List<StowageEntity> stowageEntityList,CurrentInfo currentInfo){
		//调用接口查询应付单明细
		if(CollectionUtils.isNotEmpty(paymentNos)){
			List<BillPayableEntity> payList = billPayableService.queryByPaymentNos(paymentNos, FossConstants.ACTIVE, FossConstants.NO);
			//获取外请车工作流类型  --财务共享用
			List<String> driverList = Arrays.asList(SettlementESBDictionaryConstants.WORKFLOW_DETAIL_DRIVER); 
			//声明外请车车次号集合
			List<String> driverNos = new ArrayList<String>();
			//如果存在应付单信息，则包装成费控要用的明细
			if(CollectionUtils.isNotEmpty(payList)){
				//循环封装
				for(BillPayableEntity en:payList){
					//如果是理赔则需要去分摊部门做点事情
					if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM.equals(en.getBillType())){
						//进行理赔应付单费用分摊
						payToCostcontrolSendService.sharePayableForBad(detailList,en, PAYMENTDESC);
						continue;
					//判断是否为外请车
					}else if(driverList.contains(en.getBillType())){
						//添加配载车次号 
						driverNos.add(en.getSourceBillNo());
					}
					ExpenseDetail d = new ExpenseDetail();
					d.setBillNumber(en.getPayableNo());//明细单号
					//ISSUE-3009 修改费用说明 对理赔、退运费、服务补救、装卸费传递运单号在费用说明中
					if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__REFUND.equals(en.getBillType())
							||SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION.equals(en.getBillType())
							||SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE.equals(en.getBillType())){
						d.setExpenseExplain(en.getWaybillNo());//费用说明
					}else{
						d.setExpenseExplain(PAYMENTDESC);//费用说明
					}
					d.setExpensesMoney(en.getPaymentAmount());//明细金额
					d.setExpensesType(BillPaymentPayService.convertBillType(en.getPayableNo(),en.getBillType()));//退款类型
					d.setPayBillExpDate(en.getBusinessDate());//业务发生日期
					d.setExpenseCostCenter(org.getUnifiedCode());//费用承担部门
					detailList.add(d);
				}
				//判断车次号如果不为空，则获取配置单信息
				if(CollectionUtils.isNotEmpty(driverNos)){
					//声明是否押回单到达
					boolean isReturnBackBalance = false;
					BillPayableEntity payable = payList.get(0);
					//判断是否为押回单到达付款
					if(StringUtils.isEmpty(payable.getPayableType())){
						throw new SettlementException("应付单："+payable.getPayableNo()+"的付款类型为空！没法判断是否为押回单到达！");
					//如果付款为押回单到达，则设置值
					}else if(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__LAST_BACK.equals(payable.getPayableType())){
						isReturnBackBalance= true;
					} 
					List<OutVehicleAssembleBillAndFeeVo> vehicleAssembleList = null;
					//调用中转接口获取配载单信息
					try{
						vehicleAssembleList = outsideVehicleChargeService.queryOutVehicleAssembleBillAndFeeVoList(driverNos);
					}catch(BusinessException e){
						throw new SettlementException("调用中转接口获取配载单信息异常："+e.getMessage());
					}
					//进行数据转化
					billPaymentPayService.getStowageEntityList(stowageEntityList,vehicleAssembleList,driverNos,isReturnBackBalance,currentInfo);
				}
			}
		}
	}
	
	/**
	 * 根据应付单明细进行转化为工作流付款明细
	 * @author 045738-foss-maojianqiang
	 * @date 2013-1-14 下午4:19:13
	 * @param detailList
	 * @param paymentNos
	 */
	private void convertDetailFromDeposit(List<ExpenseDetail> detailList,List<String> depositNos,OrgAdministrativeInfoEntity org){
		//调用接口查询预收单明细
		if(CollectionUtils.isNotEmpty(depositNos)){
			List<BillDepositReceivedEntity>  depositList = billDepositReceivedService.queryByPaymentNos(depositNos, FossConstants.ACTIVE, FossConstants.NO);
			//如果存在应付单信息，则包装成费控要用的明细
			if(CollectionUtils.isNotEmpty(depositList)){
				//循环封装
				for(BillDepositReceivedEntity en:depositList){
					ExpenseDetail d = new ExpenseDetail();
					d.setBillNumber(en.getDepositReceivedNo());//明细单号
					d.setExpenseExplain(en.getDepositReceivedNo());//费用说明
					d.setExpensesMoney(en.getPaymentAmount());//明细金额
					d.setExpensesType(SettlementESBDictionaryConstants.COST_CONTROL_PAY_DETAIL_DEPOSIT_RECEIVED);//退预收
					d.setPayBillExpDate(en.getBusinessDate());//业务发生日期
					d.setExpenseCostCenter(org.getUnifiedCode());//费用承担部门
					detailList.add(d);
				}
			}
		}
	}
	/**
	 * 根据应付单明细进行转化为工作流付款明细
	 * @author 045738-foss-maojianqiang
	 * @date 2013-1-14 下午4:19:13
	 * @param detailList
	 * @param paymentNos
	 * @param paymentType--此处为了判断是否为临时租车付款单，如果是则需要调用另外方法
	 */
	@SuppressWarnings("unchecked")
	private void convertDetailFromPayableToFSSC(List<com.deppon.fssc.inteface.domain.payment.ExpenseDetail> detailList,List<String> paymentNos,
													OrgAdministrativeInfoEntity org,List<StowageDetail> stowageDetail,String paymentType){
		boolean isRentCarFlag = false;
		//调用接口查询应付单明细
		if(CollectionUtils.isNotEmpty(paymentNos)){
			List<BillPayableEntity> payList = null;
			if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__RENT_CAR.equals(paymentType)){
				isRentCarFlag = true;
				payList = billPayableService.queryByPaymentNosForRentCar(paymentNos);
			}else{
				payList = billPayableService.queryByPaymentNos(paymentNos, FossConstants.ACTIVE, FossConstants.NO);
			}

			//获取外请车工作流类型  --财务共享用
			List<String> driverList = Arrays.asList(SettlementESBDictionaryConstants.WORKFLOW_DETAIL_DRIVER); 
			/**
			 * @218392 张永雪 VTS整车 批量付款的时候，去校验 VTS
			 * 1.单号是否作废
			 * 2.单号+合同编码是否唯一
			 * 3.合同编码是否存在
			 */
			List<RequestPayableCheckEntity> requestPayableCheckEntity = new ArrayList<RequestPayableCheckEntity>();
			RequestPayableCheckEntity checkEntity = new RequestPayableCheckEntity();
			//声明外请车车次号集合
			List<String> driverNos = new ArrayList<String>();
			List<WSCWayBillEntity> payableNos = new ArrayList<WSCWayBillEntity>();
			//如果存在应付单信息，则包装成费控要用的明细
			if(CollectionUtils.isNotEmpty(payList)){
				//声明预提状态
				String accruedState="init";
				String paymentNo = null;
				//循环封装
				for(BillPayableEntity en:payList){
					//如果是理赔则需要去分摊部门做点事情
					if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM.equals(en.getBillType())){
						//进行理赔应付单费用分摊
						payToFSSCSendService.sharePayableForBad(detailList,en, PAYMENTDESC);
						continue;
					//判断是否为外请车
					}else if(driverList.contains(en.getBillType())){
						//添加配载车次号 
						driverNos.add(en.getSourceBillNo());
						/**
						 * @author 218392 张永雪 FOSS结算开发组
						 * @date 2016-10-24 14:35:00
						 * 如果来源单据时整车TC开头的合同编码，那么设置值，去VTS系统校验下
						 */
						if("TC".equals(en.getSourceBillType())){
							checkEntity.setWaybillNo(en.getWaybillNo());//set运单号
							checkEntity.setContractCode(en.getSourceBillNo());//set合同编号
							requestPayableCheckEntity.add(checkEntity);
						}
					}
					com.deppon.fssc.inteface.domain.payment.ExpenseDetail d = new com.deppon.fssc.inteface.domain.payment.ExpenseDetail();
				    d.setBillNumber(en.getPayableNo());//明细单号
					//ISSUE-3009 修改费用说明 对理赔、退运费、服务补救、装卸费传递运单号在费用说明中
					if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__REFUND.equals(en.getBillType())
							||SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION.equals(en.getBillType())
							||SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE.equals(en.getBillType())){
						d.setExpenseExplain(en.getWaybillNo());//费用说明
					}else{
						d.setExpenseExplain(PAYMENTDESC);//费用说明
					}
					d.setExpensesMoney(en.getPaymentAmount());//明细金额
					d.setExpensesType(BillPaymentPayService.convertBillType(en.getPayableNo(),en.getBillType()));//退款类型
					d.setPayBillExpDate(en.getBusinessDate());//业务发生日期
					//如果为临时租车报销，则需要设置新加字段
					if(isRentCarFlag){
						//如果为临时租车，需要判断预提状态。预提中的不让报销， 已预提和未预提分开报销
						if(SettlementDictionaryConstants.WITHHOLDING_STATUS_TRANSFERRING.equals(en.getAccruedState())){
							throw new SettlementException("付款单："+en.getPaymentNo()+"中对应的应付单："+en.getPayableNo()+"预提工作流未审批完成，不允许报销!");
						}else{
							//初始化预提状态
							if("init".equals(accruedState)){
								accruedState = en.getAccruedState();
								paymentNo = en.getPaymentNo();
							}else{
								//已预预提和未预提分开报销
								if(!StringUtils.equals(accruedState, en.getAccruedState()) 
										&& !SettlementDictionaryConstants.WITHHOLDING_STATUS_TRANSFERED.equals(accruedState)
										&& !SettlementDictionaryConstants.WITHHOLDING_STATUS_TRANSFERED.equals(en.getAccruedState())){
									throw new SettlementException("付款单："+en.getPaymentNo()+"与"+paymentNo+"的预提状态不一致，未预提和已预提的临时租车费不能合并报销");
								}
							}
						}
						d.setInvatNum(en.getVatInvoice());//设置增值税发票号
						if(StringUtils.isBlank(en.getVatInvoice())){
							d.setIsvat(FossConstants.NO);//是否增值税专用发票
						}else{
							d.setIsvat(FossConstants.YES);//是否增值税专用发票
						}
						//ddw
						d.setTaxpayerId(en.getTaxpayerNumber());
						d.setTaxfreePrice(en.getTaxAmount());//不含税金额
						d.setTaxPrice(en.getTax());//税额
						d.setCarPurpose(BillPaymentPayService.getRentCarUseType(en.getRentCarUseType()));//租车用途
						d.setCarNumber(en.getVehicleNo());//车牌号
						d.setDriverName(en.getDriverName());//司机姓名
						//司机联系方式
						final int numberOfEleven = 11;
						if(StringUtils.isNotBlank(en.getDriverPhone()) && en.getDriverPhone().length()>numberOfEleven){
							d.setDriverTel(en.getDriverPhone().substring(0,numberOfEleven));//司机联系方式
						}else{
							d.setDriverTel(en.getDriverPhone());//司机联系方式
						}
						
						//重复标记
						if(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__RENTCAR_REPET.equals(en.getPayableType())){
							d.setRepeatTag(FossConstants.YES);
						}else{
							d.setRepeatTag(FossConstants.NO);
						}
						d.setWithHoldClaimNo(en.getWorkflowNo());//报销对应的预提工作流号
						d.setExpensesType(en.getCostType());//重置费用类型
						d.setPayBillExpDate(en.getBusinessOfDate());//重置业务发生日期为界面选取的日期
					}
					//如果为退运费或服务补救且产品类型为快递代理则获取快递代理点部
					if(SettlementUtil.isPackageProductCode(en.getProductCode()) 
						&& (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__REFUND.equals(en.getBillType())
						||SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION.equals(en.getBillType()))){
						//判断如果当前登录部门快递代理点部为空，则去获取
						//调用综合接口去查询快递代理点部
						ExpressPartSalesDeptResultDto shareExpressOrg = expressPartSalesDeptService.queryExpressPartSalesDeptBySalesCodeAndTime(org.getCode(),en.getAccountDate());
						//判断是否为空
						if(shareExpressOrg==null ){
							throw new SettlementException("应付单："+en.getPayableNo()+"产品类型为快递代理包裹，其应付部门对应的快递代理点部为空，不能进行付款操作！");
						}else if(StringUtils.isNotBlank(shareExpressOrg.getUnifiedCode())){
							//设置快递代理点部
							d.setExpenseCostCenter(shareExpressOrg.getUnifiedCode());
						}else{
							d.setExpenseCostCenter(org.getUnifiedCode());
						}
					}else{
						if(isRentCarFlag){
							d.setExpenseCostCenter(en.getCostDeptCode());//重置费用承担部门
						}else{
							d.setExpenseCostCenter(org.getUnifiedCode());//部门标杆编码
						}
					}
					detailList.add(d);
				}
				
				/**
				 * @218392 zhangyongxue
				 * @date 2016-06-22 11:03:10
				 * 调用VTS接口校验
				 */
				if(CollectionUtils.isNotEmpty(requestPayableCheckEntity)){
					//调用VTS服务端接口
					ResponsePayableCheckEntity responsePayableCheckEntity = clientSendVtsPayableCheck.sendVtsPayableCheck(requestPayableCheckEntity);
					if(responsePayableCheckEntity.isResultFlag()){
						logger.info("校验通过没问题！");
					}else{
						throw new SettlementException("付款失败：" + responsePayableCheckEntity.getFailureReason());
					}
				}
				
				//判断车次号如果不为空，则获取配置单信息
				if(CollectionUtils.isNotEmpty(driverNos)){
					List<OutVehicleAssembleBillAndFeeVo> vehicleAssembleList = null;
					List<OutVehicleAssembleBillAndFeeVo> vehicleList = new ArrayList<OutVehicleAssembleBillAndFeeVo>();
					List<String> list = new ArrayList<String>();
					//调用中转接口获取配载单信息
					try{
						OutVehicleAssembleBillAndFeeVo vo  = null;
						for(int i = 0; i < driverNos.size(); i++){
							/**
							 * @author 218392 张永雪 解决整车VTS合并付款报错问题
							 * @date 2016-10-24 13:53:00
							 */
							String sourceBillNo = driverNos.get(i);
							//list.add(driverNos.get(i));
							if(StringUtils.isEmpty(sourceBillNo)){
								throw new  SettlementException("整车外请车来源单据号为空！");
							}
							if("ZC".equals(sourceBillNo.substring(0, 2))){
								BillPayableEntity payableBillEntity = 
										billPayableService.queryByPayableNO(detailList.get(i).getBillNumber(), FossConstants.YES);
								OrgAdministrativeInfoEntity destOrg = 
										orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(payableBillEntity.getDestOrgCode());
								OrgAdministrativeInfoEntity origOrg = 
										orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(payableBillEntity.getOrigOrgCode());//始发部门=应付部门
								if(null==origOrg || null==destOrg){
									//只要有一个为空就会走进来
									throw new SettlementException("根据编码查询出发或到达部门信息为空！");
								}
								OutVehicleAssembleBillAndFeeVo vo1= new OutVehicleAssembleBillAndFeeVo();
								vo1.setOrigOrgCode(origOrg.getUnifiedCode());
								vo1.setDestOrgCode(destOrg.getUnifiedCode());
								vo1.setVehicleNo("VTS");
								vo1.setVehicleAssembleNo(sourceBillNo);
								vo1.setDriverName(payableBillEntity.getCustomerName());
								vo1.setFeeTotal(payableBillEntity.getAmount());
								vo1.setPrepaidFeeTotal(payableBillEntity.getAmount());
								vo1.setArriveFeeTotal(new BigDecimal(0));//到达金额
								vo1.setPaybillNo(detailList.get(i).getBillNumber());
								vo1.setAdjustFee(new BigDecimal(0));
								vo1.setAwardType("VTS");
								vo1.setBeMidwayLoad("N");
								vo1.setIsMonthPay("N");
								vo1.setPaymentType("CH");
								
								vehicleList.add(vo1);
								
							}else{
								list.add(driverNos.get(i));
								List<OutVehicleAssembleBillAndFeeVo> outList = outsideVehicleChargeService.queryOutVehicleAssembleBillAndFeeVoList(list);
								if(outList.size() > 0){
									vo = outList.get(0);
									OutVehicleAssembleBillAndFeeVo vo1= new OutVehicleAssembleBillAndFeeVo();
									vo.setPaybillNo(detailList.get(i).getBillNumber());
									//vo.setPaybillNo(payableNos.get(i).getWayBillNo());
									BeanUtils.copyProperties(vo, vo1);
									vehicleList.add(vo1);
								}
							}
							list.clear();
						}
						vehicleAssembleList = vehicleList;
//						vehicleAssembleList = outsideVehicleChargeService.queryOutVehicleAssembleBillAndFeeVoList(driverNos);
					}catch(BusinessException e){
						throw new SettlementException("调用中转接口获取配载单信息异常："+e.getMessage());
					}
					//进行数据转化
					BillPaymentPayService.getStowageDetailForFSSC(stowageDetail,vehicleAssembleList,driverNos);
				}
			}
		}
	}
	
	/**
	 * 根据应付单明细进行转化为工作流付款明细
	 * @author 045738-foss-maojianqiang
	 * @date 2013-1-14 下午4:19:13
	 * @param detailList
	 * @param paymentNos
	 */
	private void convertDetailFromDepositToFSSC(List<com.deppon.fssc.inteface.domain.payment.ExpenseDetail> detailList,List<String> depositNos,OrgAdministrativeInfoEntity org){
		//调用接口查询预收单明细
		if(CollectionUtils.isNotEmpty(depositNos)){
			List<BillDepositReceivedEntity>  depositList = billDepositReceivedService.queryByPaymentNos(depositNos, FossConstants.ACTIVE, FossConstants.NO);
			//如果存在应付单信息，则包装成费控要用的明细
			if(CollectionUtils.isNotEmpty(depositList)){
				//循环封装
				for(BillDepositReceivedEntity en:depositList){
					com.deppon.fssc.inteface.domain.payment.ExpenseDetail d = new com.deppon.fssc.inteface.domain.payment.ExpenseDetail();
					d.setBillNumber(en.getDepositReceivedNo());//明细单号
					d.setExpenseExplain(en.getDepositReceivedNo());//费用说明 ISSUE-3009
					d.setExpensesMoney(en.getPaymentAmount());//明细金额
					d.setExpensesType(SettlementESBDictionaryConstants.COST_CONTROL_PAY_DETAIL_DEPOSIT_RECEIVED);//退预收
					d.setPayBillExpDate(en.getBusinessDate());//业务发生日期
					d.setExpenseCostCenter(org.getUnifiedCode());//费用承担部门
					d.setRemittanceNumber(en.getRemitNo());//汇款编号
					detailList.add(d);
				}
			}
		}
	}
		
	public void setBillPaymentService(IBillPaymentService billPaymentService) {
		this.billPaymentService = billPaymentService;
	}

	public void setPayToCostcontrolSendService(
			IPayToCostcontrolSendService payToCostcontrolSendService) {
		this.payToCostcontrolSendService = payToCostcontrolSendService;
	}

	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	public void setBillDepositReceivedService(
			IBillDepositReceivedService billDepositReceivedService) {
		this.billDepositReceivedService = billDepositReceivedService;
	}

	public void setOperatingLogService(IOperatingLogService operatingLogService) {
		this.operatingLogService = operatingLogService;
	}

	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	/**
	 * @param settlementCommonService
	 */
	public void setSettlementCommonService(ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	/**
	 * @param orgAdministrativeInfoService
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * @GET
	 * @return payToFSSCSendService
	 */
	public IPayToFSSCSendService getPayToFSSCSendService() {
		/*
		 *@get
		 *@ return payToFSSCSendService
		 */
		return payToFSSCSendService;
	}

	/**
	 * @SET
	 * @param payToFSSCSendService
	 */
	public void setPayToFSSCSendService(IPayToFSSCSendService payToFSSCSendService) {
		/*
		 *@set
		 *@this.payToFSSCSendService = payToFSSCSendService
		 */
		this.payToFSSCSendService = payToFSSCSendService;
	}

	/**
	 * @SET
	 * @param outsideVehicleChargeService
	 */
	public void setOutsideVehicleChargeService(
			IOutsideVehicleChargeService outsideVehicleChargeService) {
		/*
		 *@set
		 *@this.outsideVehicleChargeService = outsideVehicleChargeService
		 */
		this.outsideVehicleChargeService = outsideVehicleChargeService;
	}

	/**
	 * @GET
	 * @return billPaymentPayService
	 */
	public IBillPaymentPayService getBillPaymentPayService() {
		/*
		 *@get
		 *@ return billPaymentPayService
		 */
		return billPaymentPayService;
	}

	/**
	 * @SET
	 * @param billPaymentPayService
	 */
	public void setBillPaymentPayService(IBillPaymentPayService billPaymentPayService) {
		/*
		 *@set
		 *@this.billPaymentPayService = billPaymentPayService
		 */
		this.billPaymentPayService = billPaymentPayService;
	}

	/**
	 * @GET
	 * @return expressPartSalesDeptService
	 */
	public IExpressPartSalesDeptService getExpressPartSalesDeptService() {
		/*
		 *@get
		 *@ return expressPartSalesDeptService
		 */
		return expressPartSalesDeptService;
	}

	/**
	 * @SET
	 * @param expressPartSalesDeptService
	 */
	public void setExpressPartSalesDeptService(
			IExpressPartSalesDeptService expressPartSalesDeptService) {
		/*
		 *@set
		 *@this.expressPartSalesDeptService = expressPartSalesDeptService
		 */
		this.expressPartSalesDeptService = expressPartSalesDeptService;
	}

	/**
	 * @GET
	 * @return expressOrgUniCode
	 */
//	public String getExpressOrgUniCode() {
//		/*
//		 *@get
//		 *@ return expressOrgUniCode
//		 */
//		return expressOrgUniCode;
//	}

	/**
	 * @SET
	 * @param expressOrgUniCode
	 */
//	public void setExpressOrgUniCode(String expressOrgUniCode) {
//		/*
//		 *@set
//		 *@this.expressOrgUniCode = expressOrgUniCode
//		 */
//		this.expressOrgUniCode = expressOrgUniCode;
//	}
	/**
	 * @SET
	 * @param billPaymentManageService
	 */
	public void setBillPaymentManageService(
			IBillPaymentManageService billPaymentManageService) {
		this.billPaymentManageService = billPaymentManageService;
	}
	
	public void setClientSendVtsPayableCheck(
			IClientSendVtsPayableCheck clientSendVtsPayableCheck) {
		this.clientSendVtsPayableCheck = clientSendVtsPayableCheck;
	}
	public void setPaymentStatusToVTSClient(
			IPaymentStatusToVTSClient paymentStatusToVTSClient) {
		this.paymentStatusToVTSClient = paymentStatusToVTSClient;
	}

}
