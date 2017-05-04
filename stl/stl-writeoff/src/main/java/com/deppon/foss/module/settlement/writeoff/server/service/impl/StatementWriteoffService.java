package com.deppon.foss.module.settlement.writeoff.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillAdvancedPaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillDepositReceivedService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountDService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementMakeService;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementModifyService;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementWriteoffService;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementOfAccountMakeQueryResultDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 对账单核销接口实现类
 * 
 * @author foss-wangxuemin
 * @date Nov 7, 2012 5:05:16 PM
 */
public class StatementWriteoffService implements IStatementWriteoffService {

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(StatementWriteoffService.class);

	/**
	 * 注入对账单制作SERVICE
	 */
	private IStatementMakeService statementMakeService;

	/**
	 * 注入对账单修改SERVICE
	 */
	private IStatementModifyService statementModifyService;

	/**
	 * 注入对账单明细公共SERVICE
	 */
	private IStatementOfAccountDService statementOfAccountDService;

	/**
	 * 注入对账单公共SERVICE
	 */
	private IStatementOfAccountService statementOfAccountService;

	/**
	 * 注入应收单SERVICE
	 */
	private IBillReceivableService billReceivableService;

	/**
	 * 注入应付单SERVICE
	 */
	private IBillPayableService billPayableService;

	/**
	 * 注入预收单SERVICE
	 */
	private IBillDepositReceivedService billDepositReceivedService;

	/**
	 * 注入预付单SERVICE
	 */
	private IBillAdvancedPaymentService billAdvancedPaymentService;

	/**
	 * 注入核销单SERVICE
	 */
	private IBillWriteoffService billWriteoffService;

	/**
	 * 注入结算通用SERVICE
	 */
	private ISettlementCommonService settlementCommonService;

	/**
	 * 对账单核销
	 * 
	 * @author foss-wangxuemin
	 * @date Nov 7, 2012 5:04:36 PM
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementWriteoffService#writeoffStatement(com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementOfAccountWriteoffDto)
	 */
	@Transactional
	@Override
	public StatementOfAccountMakeQueryResultDto writeoffStatement(StatementOfAccountMakeQueryResultDto statementDto,CurrentInfo currentInfo) {
		//生成对账单制作返回dto
		StatementOfAccountMakeQueryResultDto statementResultDto = new StatementOfAccountMakeQueryResultDto();

		// 返回前台的未核销应付单和预收单里列表
		List<StatementOfAccountDEntity> unverifyDetailList = new ArrayList<StatementOfAccountDEntity>();

		/*
		 * 1 校验参数
		 */
		//如果对账单制作参数dto为空
		if (null == statementDto) {
			//提示传入参数为空，无法核销
			throw new SettlementException("传入参数为空，无法核销");
		}
		//获取对账单列表从传入的对账单制作参数dto上
		List<StatementOfAccountEntity> statementEntitys = statementDto.getStatementOfAccountList();
		//对账单列表不能为空
		if (CollectionUtils.isEmpty(statementDto.getStatementOfAccountList())) {
			//提示对账单记录为空，无法核销
			throw new SettlementException("对账单记录为空，无法核销");
		}

		//生成待确认对账单列表
		List<StatementOfAccountEntity> unconfirmEntityList = new ArrayList<StatementOfAccountEntity>();
		//循环处理对账单列表
		for (StatementOfAccountEntity statementEntity : statementEntitys) {
			// 校验对账单是否允许核销，已结清的对账单不允许核销
			if (StringUtils.isEmpty(statementEntity.getId())) {
				//提示对账单XXXX的ID为空，不能核销
				throw new SettlementException("对账单".concat(statementEntity.getStatementBillNo()).concat("的ID为空，不能核销"));
			}
			//根据对账单ID查询
			StatementOfAccountEntity sEntity = statementOfAccountService.queryByPrimaryKey(statementEntity.getId());
			//如果对账单未还金额大于0
			if (sEntity.getUnpaidAmount().compareTo(BigDecimal.ZERO) > 0) {
				////提示对账单XXXX未还金额大于零，不能核销
				throw new SettlementException("账单".concat(sEntity.getStatementBillNo()).concat("未还金额大于零，不能核销"));
			}
			//如果对账单的结账次数大于0
			if (sEntity.getSettleNum() > 0) {
			    //提示对账单XXXX已结清，不能核销
				throw new SettlementException("账单".concat(sEntity.getStatementBillNo()).concat("已结清，不能核销"));
			}
			//如果对账单为未确认
			if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM.equals(sEntity.getConfirmStatus())) {
				//将对账单加入到待确认
				unconfirmEntityList.add(sEntity);
			}
		}

		/*
		 * 2 未确认的系统自动确认
		 */
		//如果待确认对账单列表不为空
		if (CollectionUtils.isNotEmpty(unconfirmEntityList)) {
			//生成确认对账单参数dto
			StatementOfAccountMakeQueryResultDto unconfirmDto = new StatementOfAccountMakeQueryResultDto();
			//设置确认对账单参数dto的待确认数据
			unconfirmDto.setStatementOfAccountList(unconfirmEntityList);
			//设置确认对账单参数dto的用户
			unconfirmDto.setUser(statementDto.getUser());
			//确认对账单
			statementModifyService.confirmStatementForWriteOff(unconfirmDto,currentInfo);
		}

		// 获取核销批次号
		String writeoffBatchNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.HX_BN);

		/*
		 * 3 获取应收单、应付单、预收单、预付单列表记录
		 */
		for (StatementOfAccountEntity sEntity : statementEntitys) {
			//记录日志
			logger.debug("核销的对账单编号：".concat(sEntity.getStatementBillNo()));

			//根据对账单ID查询
			StatementOfAccountEntity sWriteoffEntity = statementOfAccountService.queryByPrimaryKey(sEntity.getId());

			/*
			 * 4 按照对账单明细核销
			 */
			BillWriteoffOperationDto writeoffResultDto = writeoffStatementDetail(sWriteoffEntity, writeoffBatchNo, currentInfo);

			/*
			 * 5 释放未完全核销的应付单、预收单、预付单，返回应付单和预收单列表
			 */
			List<StatementOfAccountDEntity> releaseDetailList = releaseFromStatement(writeoffResultDto, currentInfo);
			//释放
			unverifyDetailList.addAll(releaseDetailList);

			/*
			 * 6 更新对账单状态
			 */
			statementModifyService.releaseSOADUpdateStatement(sWriteoffEntity,currentInfo);
		}

		// 设置未完全核销的应付单和预付单列表
		statementResultDto.setStatementOfAccountDUnverifyList(unverifyDetailList);

		// 设置返回的账单和账单明细列表信息
		statementResultDto.setStatementOfAccountDBeginPeriodList(statementDto.getStatementOfAccountDBeginPeriodList());
		// 设置返回的账单和账单明细列表信息
		statementResultDto.setStatementOfAccountDPeriodList(statementDto.getStatementOfAccountDPeriodList());
		//生成对账单列表
		List<StatementOfAccountEntity> statementOfAccountList = new ArrayList<StatementOfAccountEntity>();
		//循环处理对账单
		for (StatementOfAccountEntity sEntity : statementEntitys) {
			//获取最新的对账单
			StatementOfAccountEntity sWriteoffEntity = statementOfAccountService.queryByPrimaryKey(sEntity.getId());
			//加入到对账单列表
			statementOfAccountList.add(sWriteoffEntity);
		}
		//设置对账单返回dto的对账单列表
		statementResultDto.setStatementOfAccountList(statementOfAccountList);
		//如果对账单列表大于1
		if (statementOfAccountList.size() == 1) {
			//获取第一个对账单设置到对账单返回dto
			statementResultDto.setStatementOfAccount(statementOfAccountList
					.get(0));
		}
		//返回对账单dto
		return statementResultDto;
	}

	/**
	 * 核销对账单明细
	 * 
	 * @author foss-wangxuemin
	 * @date Nov 7, 2012 5:04:36 PM
	 */
	private BillWriteoffOperationDto writeoffStatementDetail(StatementOfAccountEntity sWriteoffEntity, String writeoffBatchNo,CurrentInfo currentInfo) {

		//根据对账单号查询对账单明细信息
		List<StatementOfAccountDEntity> statementDetailEntitys = statementOfAccountDService.queryByStatementBillNo(sWriteoffEntity.getStatementBillNo());
		//如果对账单明细列表为空
		if (CollectionUtils.isEmpty(statementDetailEntitys)) {
			//提示 对账单明细记录为空，无法核销
			throw new SettlementException("对账单明细记录为空，无法核销");
		}

		// 核销操作Dto
		BillWriteoffOperationDto writeoffDto = new BillWriteoffOperationDto();

		// 从对账单明细中获取应收单号、应付单号、预收单号、预付单号
		List<String> recNos = new ArrayList<String>();//应收单号
		List<String> payNos = new ArrayList<String>();//应付单号
		List<String> depNos = new ArrayList<String>();//预收单号
		List<String> advNos = new ArrayList<String>();//预付单号
		//循环处理对账单明细
		for (StatementOfAccountDEntity statmentDetailEntity : statementDetailEntitys) {
			// 应收单号
			if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE.equals(statmentDetailEntity.getBillParentType())) {
				//加入到应收单号列表
				recNos.add(statmentDetailEntity.getSourceBillNo());
			}
			// 应付单号
			else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__PAYABLE.equals(statmentDetailEntity.getBillParentType())) {
				//加入到应付单号列表
				payNos.add(statmentDetailEntity.getSourceBillNo());
			}
			// 预收单号
			else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__DEPOSIT_RECEIVED.equals(statmentDetailEntity.getBillParentType())) {
				//加入到预收单号列表
				depNos.add(statmentDetailEntity.getSourceBillNo());
			}
			// 预付单号
			else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__ADVANCED_PAYMENT.equals(statmentDetailEntity.getBillParentType())) {
				//加入到预付单号列表
				advNos.add(statmentDetailEntity.getSourceBillNo());
			}
			// 其他
			else {
				//提示对账单明细单据大类型异常，无法核销
				throw new SettlementException("对账单明细单据大类型异常，无法核销");
			}
		}

		// 根据来源单号查询应收单
		//如果应收单号不为空
		if (CollectionUtils.isNotEmpty(recNos)) {
			//查询应收单
			List<BillReceivableEntity> recs = billReceivableService.queryByReceivableNOs(recNos, FossConstants.ACTIVE);
			// 校应收单原始单据数量和对账单明细数量是否匹配
			if ((CollectionUtils.isNotEmpty(recs) && recs.size() != recNos.size()) || CollectionUtils.isEmpty(recs)) {
				//提示对账单明细记录中的应收单和原始记录信息不一致，无法核销，请联系管理员
				throw new SettlementException("对账单明细记录中的应收单和原始记录信息不一致，无法核销，请联系管理员");
			}
			// 设置核销参数中的核销方列表：应收单列表、应付单列表、预收单列表、预付单列表
			writeoffDto.setBillReceivableEntitys(recs);
		}
		// 根据来源单号查询应付单
		//如果应付单号不为空
		if (CollectionUtils.isNotEmpty(payNos)) {
			//查询应付单
			List<BillPayableEntity> pays = billPayableService.queryByPayableNOs(payNos, FossConstants.ACTIVE);
			// 校应付单原始单据数量和对账单明细数量是否匹配
			if ((CollectionUtils.isNotEmpty(pays) && pays.size() != payNos.size()) || CollectionUtils.isEmpty(pays)) {
				//提示对账单明细记录中的应付单和原始记录信息不一致，无法核销，请联系管理员
				throw new SettlementException("对账单明细记录中的应付单和原始记录信息不一致，无法核销，请联系管理员");
			}
			// 设置核销参数中的核销方列表：应收单列表、应付单列表、预收单列表、预付单列表
			writeoffDto.setBillPayableEntitys(pays);
		}
		// 根据来源单号查询预收单
		//如果预收单号不为空
		if (CollectionUtils.isNotEmpty(depNos)) {
			//查询预收单
			List<BillDepositReceivedEntity> deps = billDepositReceivedService.queryByDepositReceivedNOs(depNos, FossConstants.ACTIVE);
			// 校预收单原始单据数量和对账单明细数量是否匹配
			if ((CollectionUtils.isNotEmpty(deps) && deps.size() != depNos.size()) || CollectionUtils.isEmpty(deps)) {
				//提示对账单明细记录中的预收单和原始记录信息不一致，无法核销，请联系管理员
				throw new SettlementException("对账单明细记录中的预收单和原始记录信息不一致，无法核销，请联系管理员");
			}
			// 设置核销参数中的核销方列表：应收单列表、应付单列表、预收单列表、预付单列表
			writeoffDto.setBillDepositReceivedEntitys(deps);
		}
		// 根据来源单号查询预付单
		//如果预付单号不为空
		if (CollectionUtils.isNotEmpty(advNos)) {
			//查询预付单
			List<BillAdvancedPaymentEntity> advs = billAdvancedPaymentService.queryBillAdvancedPaymentNos(advNos, FossConstants.ACTIVE);
			// 校预付单原始单据数量和对账单明细数量是否匹配
			if ((CollectionUtils.isNotEmpty(advs) && advs.size() != advNos.size()) || CollectionUtils.isEmpty(advs)) {
				//提示对账单明细记录中的预付单和原始记录信息不一致，无法核销，请联系管理员
				throw new SettlementException("对账单明细记录中的预付单和原始记录信息不一致，无法核销，请联系管理员");
			}
			// 设置核销参数中的核销方列表：应收单列表、应付单列表、预收单列表、预付单列表
			writeoffDto.setBillAdvancedPaymentEntitys(advs);
		}

		// 设置核销批次号
		writeoffDto.setWriteoffBatchNo(writeoffBatchNo);

		// 核销类型为手工核销
		writeoffDto.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);

		// 核销对账单编号取对账单上的单据编号
		writeoffDto.setStatementBillNo(sWriteoffEntity.getStatementBillNo());

		// 核销对账单明细
		BillWriteoffOperationDto writeoffResultDto = writeoffStatementDetailByBillList(writeoffDto, currentInfo);

		//返回核销结果dto
		return writeoffResultDto;

	}

	/**
	 * 对账单明细核销，包括：预收冲应收、应收冲应付、预付冲应付
	 * 
	 * @author foss-wangxuemin
	 * @date Nov 9, 2012 10:29:41 AM
	 */
	private BillWriteoffOperationDto writeoffStatementDetailByBillList(BillWriteoffOperationDto writeoffDto, CurrentInfo currentInfo) {
		// 初始化返回结果
		BillWriteoffOperationDto writeoffResultDto = new BillWriteoffOperationDto();
		//设置应付单
		writeoffResultDto.setBillPayableEntitys(writeoffDto.getBillPayableEntitys());
		//设置预收单
		writeoffResultDto.setBillDepositReceivedEntitys(writeoffDto.getBillDepositReceivedEntitys());
		//设置预付单
		writeoffResultDto.setBillAdvancedPaymentEntitys(writeoffDto.getBillAdvancedPaymentEntitys());

		// 预收冲应收
		//如果预收单和应收单不为空
		if (CollectionUtils.isNotEmpty(writeoffDto.getBillDepositReceivedEntitys())&& CollectionUtils.isNotEmpty(writeoffDto.getBillReceivableEntitys())) {
			//调用统一核销方法
			writeoffResultDto = billWriteoffService.writeoffDepositAndReceivable(writeoffDto, currentInfo);
			//设置预收单核销结果
			writeoffDto.setBillDepositReceivedEntitys(writeoffResultDto.getBillDepositReceivedEntitys());
			//设置应收单核销结果
			writeoffDto.setBillReceivableEntitys(writeoffResultDto.getBillReceivableEntitys());
		}

		// 应收冲应付
		//如果应收单和应付单不为空
		if (CollectionUtils.isNotEmpty(writeoffDto.getBillReceivableEntitys())&& CollectionUtils.isNotEmpty(writeoffDto.getBillPayableEntitys())) {
			//调用统一核销方法
			writeoffResultDto = billWriteoffService.writeoffReceibableAndPayable(writeoffDto, currentInfo);
			//设置应收单核销结果
			writeoffDto.setBillReceivableEntitys(writeoffResultDto.getBillReceivableEntitys());
			//设置应付单核销结果
			writeoffDto.setBillPayableEntitys(writeoffResultDto.getBillPayableEntitys());
		}

		// 预付冲应付
		//如果预付单和应付单不为空
		if (CollectionUtils.isNotEmpty(writeoffDto.getBillAdvancedPaymentEntitys())&& CollectionUtils.isNotEmpty(writeoffDto.getBillPayableEntitys())) {
			//调用统一核销方法
			writeoffResultDto = billWriteoffService.writeoffAdvancedAndPayable(writeoffDto, currentInfo);
			//设置预付单核销结果
			writeoffDto.setBillAdvancedPaymentEntitys(writeoffResultDto.getBillAdvancedPaymentEntitys());
			//设置应付单核销结果
			writeoffDto.setBillPayableEntitys(writeoffResultDto.getBillPayableEntitys());
		}
		//返回核销结果
		return writeoffResultDto;
	}

	/**
	 * 释放对账单明细中已核销的对账单明细
	 * 
	 * @author foss-wangxuemin
	 * @date Nov 9, 2012 10:29:41 AM
	 */
	private List<StatementOfAccountDEntity> releaseFromStatement(BillWriteoffOperationDto writeoffResultDto, CurrentInfo currentInfo) {
		// 返回前台的未核销应付单和预收单里列表
		List<StatementOfAccountDEntity> unverifyDetailList = new ArrayList<StatementOfAccountDEntity>();

		//生成释放单号集合
		List<String> releaseNosList = new ArrayList<String>();

		// 应付单未核销金额大于零时，从对账单中释放，并返回前台付款
		//如果应付单不为空
		if (CollectionUtils.isNotEmpty(writeoffResultDto.getBillPayableEntitys())) {
			//循环处理应付单
			for (BillPayableEntity pay : writeoffResultDto.getBillPayableEntitys()) {
				//如果应付单未核销金额大于零时
				if (pay.getUnverifyAmount().compareTo(BigDecimal.ZERO) > 0) {
					//生成对账单明细信息
					StatementOfAccountDEntity detailEntity = new StatementOfAccountDEntity();
					//拷贝数据到对账单明细数据
					BeanUtils.copyProperties(pay, detailEntity);
					//设置来源单号
					detailEntity.setSourceBillNo(pay.getPayableNo());
					//设置父类型
					detailEntity.setBillParentType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__PAYABLE);
					//设置网点编码
					detailEntity.setOrgCode(pay.getPayableOrgCode());
					//设置网点名称
					detailEntity.setOrgName(pay.getPayableOrgName());
					//设置审核状态
					detailEntity.setAuditStatus(pay.getApproveStatus());
					//加入到返回列表
					unverifyDetailList.add(detailEntity);
					//加入到释放列表
					releaseNosList.add(pay.getPayableNo());
				}
			}
		}

		// 预收单未核销金额大于零时，从对账单中释放，并返回前台付款
		//如果应收单不为空
		if (CollectionUtils.isNotEmpty(writeoffResultDto.getBillDepositReceivedEntitys())) {
			//循环处理应收单
			for (BillDepositReceivedEntity dep : writeoffResultDto.getBillDepositReceivedEntitys()) {
				//如果应收单未核销金额大于零时
				if (dep.getUnverifyAmount().compareTo(BigDecimal.ZERO) > 0) {
					//生成对账单明细信息
					StatementOfAccountDEntity detailEntity = new StatementOfAccountDEntity();
					//拷贝数据到对账单明细数据
					BeanUtils.copyProperties(dep, detailEntity);
					//设置来源单号
					detailEntity.setSourceBillNo(dep.getDepositReceivedNo());
					//设置父类型
					detailEntity.setBillParentType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__DEPOSIT_RECEIVED);
					//设置网点编码
					detailEntity.setOrgCode(dep.getGeneratingOrgCode());
					//设置网点名称
					detailEntity.setOrgName(dep.getGeneratingOrgName());
					//加入到返回列表
					unverifyDetailList.add(detailEntity);
					//加入到释放列表
					releaseNosList.add(dep.getDepositReceivedNo());
				}
			}
		}

		// 预付单未核销金额大于零时，从对账单中释放
		//如果预收单不为空
		if (CollectionUtils.isNotEmpty(writeoffResultDto.getBillAdvancedPaymentEntitys())) {
			//循环处理预付单
			for (BillAdvancedPaymentEntity adv : writeoffResultDto.getBillAdvancedPaymentEntitys()) {
				//如果预付单未核销金额大于零时
				if (adv.getUnverifyAmount().compareTo(BigDecimal.ZERO) > 0) {
					//加入到释放列表
					releaseNosList.add(adv.getAdvancesNo());
				}
			}
		}

		// 批量释放未核销完的单据
		statementMakeService.updateDetailBillNoForRelease(releaseNosList,SettlementConstants.DEFAULT_BILL_NO,currentInfo);

		//返回前台的未核销应付单和预收单里列表
		return unverifyDetailList;
	}

	/**
	 * @param statementMakeService
	 */
	public void setStatementMakeService(IStatementMakeService statementMakeService) {
		this.statementMakeService = statementMakeService;
	}

	/**
	 * @param statementModifyService
	 */
	public void setStatementModifyService(IStatementModifyService statementModifyService) {
		this.statementModifyService = statementModifyService;
	}

	/**
	 * @param statementOfAccountDService
	 */
	public void setStatementOfAccountDService(IStatementOfAccountDService statementOfAccountDService) {
		this.statementOfAccountDService = statementOfAccountDService;
	}

	/**
	 * @param statementOfAccountService
	 */
	public void setStatementOfAccountService(IStatementOfAccountService statementOfAccountService) {
		this.statementOfAccountService = statementOfAccountService;
	}

	/**
	 * @param billReceivableService
	 */
	public void setBillReceivableService(IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	/**
	 * @param billPayableService
	 */
	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	/**
	 * @param billDepositReceivedService
	 */
	public void setBillDepositReceivedService(IBillDepositReceivedService billDepositReceivedService) {
		this.billDepositReceivedService = billDepositReceivedService;
	}

	/**
	 * @param billAdvancedPaymentService
	 */
	public void setBillAdvancedPaymentService(IBillAdvancedPaymentService billAdvancedPaymentService) {
		this.billAdvancedPaymentService = billAdvancedPaymentService;
	}

	/**
	 * @param billWriteoffService
	 */
	public void setBillWriteoffService(IBillWriteoffService billWriteoffService) {
		this.billWriteoffService = billWriteoffService;
	}

	/**
	 * @param settlementCommonService
	 */
	public void setSettlementCommonService(ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

}
