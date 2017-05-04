package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.server.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.ListComparator;
import com.deppon.foss.module.settlement.pay.api.server.service.IFossToFinanceRemittanceService;
import com.deppon.foss.module.settlement.pay.api.server.service.ISoaRepaymentManageService;
import com.deppon.foss.module.settlement.pay.api.server.service.IWoodenStatementRepaymentService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.SoaRepaymentEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillStatementToPaymentQueryDto;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IWoodenStatementService;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.WoodenStatementDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.WoodenStatementEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class WoodenStatementRepaymentService implements IWoodenStatementRepaymentService{
	
	private static final Logger LOGGER = LogManager.getLogger(WoodenStatementRepaymentService.class);
	
	/**
	 * 还款单service
	 */
	private IBillRepaymentService billRepaymentService;
	
	/**
	 * 结算通用服务service
	 */
	private ISettlementCommonService settlementCommonService;
	
	/**
	 * 汇款服务接口service
	 */
	private IFossToFinanceRemittanceService fossToFinanceRemittanceService;
	
	/**
	 * 核销/反核销公共service
	 */
	private IBillWriteoffService billWriteoffService;
	
	/**
	 * 应收单service
	 */
	private IBillReceivableService billReceivableService;

	/**
	 * 应付单service
	 */
	private IBillPayableService billPayableService;
	
	/**
	 * 对账单service
	 */
	private IWoodenStatementService woodenStatementService;

	/**
	 * 对账单还款单关系service
	 */
	private ISoaRepaymentManageService soaRepaymentManageService;
	/**
	 * 网上支付记录接口调用代理
	 */
	private IFinanceOnlinePayWSProxy financeOnlinePayWSProxy;

	/**
	 * 还款
	 * @author ddw
	 * @date 2014-06-17
	 */
	@Override
	@Transactional
	public String woodenToRepayment(BillStatementToPaymentQueryDto billStatementToPaymentQueryDto,CurrentInfo cInfo) {

		LOGGER.info("还款/批量还款单开始...");

		//验证还款dto
		if (billStatementToPaymentQueryDto == null||billStatementToPaymentQueryDto.getRepaymentAmount()==null) {
			LOGGER.info("还款/批量还款单时,传入的还款信息为空");
			throw new SettlementException("传入的还款信息为空!");
		}

		//还款金额必须>0,否则提示还款金额小于0异常
		if(billStatementToPaymentQueryDto.getRepaymentAmount().compareTo(BigDecimal.ZERO)!=1){
			throw new SettlementException("还款/批量还款单时还款金额必须大于0!");
		}
		
		// 获取对账单号集合
		List<String> statementBillNoList = Arrays.asList(billStatementToPaymentQueryDto.getStatementBillNos());

		// 对账单号不能为空
		if (CollectionUtils.isEmpty(statementBillNoList)) {
			LOGGER.info("还款/批量还款单时,传入的对账单号为空不能正常还款");
			throw new SettlementException("传入的对账单号为空不能正常还款!");
		}

		// 根据对账单号集合获取对账单列表
		List<WoodenStatementEntity> statementList = new ArrayList<WoodenStatementEntity>();

		WoodenStatementEntity woodenStatementEntity = woodenStatementService.queryByStatementNo(statementBillNoList.get(0));
		statementList.add(woodenStatementEntity);
		//对账单确认状态判断
		if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM.equals(
				woodenStatementEntity.getConfirmStatus())) {
			throw new SettlementException("对账单确认状态为未确认不可以进行还款!");
		}
		// 1、新增还款单实体，单位保存
		BillRepaymentEntity billRepaymentEntity = addBillRepayment(billStatementToPaymentQueryDto, cInfo);
		//还款单单号
		String repaymentNo = billRepaymentEntity.getRepaymentNo();
		// 排序类，所有对账单按照业务日期排序
		ListComparator orderComparator = new ListComparator("businessDate");
		// 按时间排序，预收收单核销时按业务时间先后排序
		Collections.sort(statementList, orderComparator);

		// 2、调用核销操作
		// 还款单执行每次按对账单还款后剩余金额
		BigDecimal unpaidAmountLeft = billRepaymentEntity.getAmount();

		// 获取核销批次号
		String writeoffBatchNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.HX_BN);

		//生成其中待核销的一个应收单，永远设置还款单的收入部门
		BillReceivableEntity rtnBillReceivableEntity = null;
		
		for (WoodenStatementEntity entity : statementList) {

			LOGGER.info("还款/批量还款单时,循环核销对账单开始,对账单号：" + entity.getStatementBillNo());

			// 如果还款单金额小于等于单个对账单的未还款金额，按还款单金额核销还款，并break
			if (unpaidAmountLeft.compareTo(entity.getUnpaidAmount()) <= 0) {
				billRepaymentEntity.setAmount(unpaidAmountLeft);
				
				// 调用核销操作
				if(rtnBillReceivableEntity==null){
					rtnBillReceivableEntity = billRepayWriteoff(entity.getStatementBillNo(),billRepaymentEntity, cInfo, writeoffBatchNo);
				}else{
					billRepayWriteoff(entity.getStatementBillNo(),billRepaymentEntity, cInfo, writeoffBatchNo);
				}
				
				

				// 修改对账单未还款金额
				entity.setUnpaidAmount(entity.getUnpaidAmount().subtract(unpaidAmountLeft));
				woodenStatementService.updateStatementForWriteOff(entity,cInfo);
				
				// 新增对账单还款数据(账单号、还款单号、还款金额)
				addSoaRepayment(entity.getStatementBillNo(),
						billRepaymentEntity.getRepaymentNo(),
						billRepaymentEntity.getAmount());
				break;

				// 反之按对账单的未还款金额去核销
			} else if (unpaidAmountLeft.compareTo(entity.getUnpaidAmount()) > 0) {
				billRepaymentEntity.setAmount(entity.getUnpaidAmount());

				// 调用核销操作
				rtnBillReceivableEntity = billRepayWriteoff(entity.getStatementBillNo(),billRepaymentEntity, cInfo, writeoffBatchNo);
				unpaidAmountLeft = unpaidAmountLeft.subtract(entity.getUnpaidAmount());

				// 修改对账单未还款金额
				entity.setUnpaidAmount(BigDecimal.ZERO);
				woodenStatementService.updateStatementForWriteOff(entity,cInfo);
				
				// 新增对账单还款数据(账单号、还款单号、还款金额)
				addSoaRepayment(entity.getStatementBillNo(),
						billRepaymentEntity.getRepaymentNo(),
						billRepaymentEntity.getAmount());
			}
		}
		//根据核销的应收单修改收款、收入部门，且重设全额还款金额
		billRepaymentEntity.setAmount(billStatementToPaymentQueryDto.getRepaymentAmount());//重设还款金额
		if(null != rtnBillReceivableEntity){
			billRepaymentEntity.setCollectionOrgCode(rtnBillReceivableEntity.getDunningOrgCode());// 收款部门编码
			billRepaymentEntity.setCollectionOrgName(rtnBillReceivableEntity.getDunningOrgName());// 收款部门名称
		}
		// 保存还款单
		billRepaymentEntity.setCreateOrgCode(cInfo.getCurrentDeptCode());
		billRepaymentEntity.setCreateOrgName(cInfo.getCurrentDeptName());
		billRepaymentService.addBillRepayment(billRepaymentEntity, cInfo);
		LOGGER.info("还款/批量还款单时,新增还款单成功,还款单号：" + billRepaymentEntity.getRepaymentNo());

		// 4、调用费控接口处理数据
		// 如果是电汇或支票还款，且校验通过，则调用费控占用汇款接口
		if (StringUtils.isNotEmpty(billStatementToPaymentQueryDto.getRemittanceNumber())
			&& (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER.equals(billStatementToPaymentQueryDto.getRepaymentType())
			||SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__NOTE.equals(billStatementToPaymentQueryDto.getRepaymentType()))) {

			LOGGER.info("还款/批量还款单时,调用费控接口占用汇款开始,汇款单号："
					+ billStatementToPaymentQueryDto.getRemittanceNumber());
			fossToFinanceRemittanceService.obtainClaim(
					billStatementToPaymentQueryDto.getRepaymentAmount(),
					billStatementToPaymentQueryDto.getRemittanceNumber(),
					cInfo.getCurrentDeptCode(),billRepaymentEntity.getRepaymentNo());
		}

		//5.网上支付则调用财务接口占用
		//DMANA-6876 FOSS-网上支付发更改重新还款
		if (StringUtils.isNotEmpty(billStatementToPaymentQueryDto.getOnlinePaymentNo())
				&& SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER.equals(
					billStatementToPaymentQueryDto.getRepaymentType())) {

			LOGGER.info("还款/批量还款单时,调用费控接口占用网上支付款开始,网上支付编号："
					+ billStatementToPaymentQueryDto.getOnlinePaymentNo());
			financeOnlinePayWSProxy.obtain(billStatementToPaymentQueryDto.getOnlinePaymentNo()
					, billStatementToPaymentQueryDto.getRepaymentAmount());

		}
		LOGGER.debug("还款/批量还款单结束...");
		//返回还款单号
		return repaymentNo;
	}
	
	/**
	 * 新增还款单
	 * @author ddw
	 * @date 2014-06-17
	 */
	private BillRepaymentEntity addBillRepayment(BillStatementToPaymentQueryDto dto, CurrentInfo cInfo) {

		LOGGER.info("还款/批量还款单时,新增还款单开始...");
		// 生成还款单
		BillRepaymentEntity entity = new BillRepaymentEntity();
		Date now = new Date();

		entity.setId(UUIDUtils.getUUID());// ID
		entity.setRepaymentNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.HK1));// 还款单号
		entity.setCustomerCode(dto.getCustomerCode());// 客户编码
		entity.setCustomerName(dto.getCustomerName());// 客户名称
		entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);// 币种
		entity.setAmount(dto.getRepaymentAmount());// 金额
		entity.setTrueAmount(dto.getRepaymentAmount());// 实际还款金额
		entity.setBverifyAmount(BigDecimal.ZERO);// 反核销金额
		entity.setAuditStatus(SettlementDictionaryConstants.BILL_REPAYMENT__AUDIT_STATUS__NOT_AUDIT);// 审核状态
		entity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);// 生成方式
		entity.setOaPaymentNo(dto.getRemittanceNumber());// OA汇款编号
		entity.setCreateOrgCode(cInfo.getCurrentDeptCode());// 录入部门编码
		entity.setCreateOrgName(cInfo.getCurrentDeptCode());// 录入部门名称
		entity.setBillType(SettlementDictionaryConstants.BILL_REPAYMENT__BILL_TYPE__REPAYMENT);// 还款单类型
		entity.setStatus(SettlementDictionaryConstants.BILL_REPAYMENT__STATUS__SUBMIT);// 状态默认为提交
		entity.setActive(FossConstants.ACTIVE);// 有效
		entity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);// 非红单
		entity.setPaymentType(dto.getRepaymentType());// 支付方式
		entity.setCreateUserCode(cInfo.getCurrentDeptCode());// 制单人编码
		entity.setCreateUserName(cInfo.getCurrentDeptName());// 制单人名称
		entity.setBusinessDate(now);// 业务时间
		entity.setAccountDate(now);// 记账时间
		entity.setCreateTime(now);// 创建时间
		entity.setIsInit(FossConstants.NO);// 是否初始化
		entity.setNotes(dto.getDescription());// 备注
		entity.setVersionNo(FossConstants.INIT_VERSION_NO);// 版本号
		entity.setOnlinePaymentNo(dto.getOnlinePaymentNo());// 在线支付编号
		entity.setOaPayee(dto.getRemittanceName());//OA汇款人
		
		// 保存还款单
		return entity;
	}
	
	/**
	 * 按对账单进行核销
	 * @author ddw
	 * @date 2014-06-17
	 */
	private BillReceivableEntity billRepayWriteoff(String statementNo,
			BillRepaymentEntity entity, CurrentInfo cInfo,
			String writeoffBatchNo) {

		// 循环按每个对账单处理

		// 根据对账单号查询对账单明细
		List<WoodenStatementDEntity> statementDetailEntitys = woodenStatementService.queryByStatementBillNo(statementNo);
		if (CollectionUtils.isEmpty(statementDetailEntitys)) {

			LOGGER.info("还款/批量还款按对账单核销时,对账单明细记录为空，无法核销");
			throw new SettlementException("对账单明细记录为空，无法核销");
		}

		// 从对账单明细中获取应收单号、应付单号、预收单号、预付单号
		List<String> recNos = new ArrayList<String>();
		List<String> payNos = new ArrayList<String>();
		for (WoodenStatementDEntity statmentDetailEntity : statementDetailEntitys) {
			// 应收单号
			if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE
					.equals(statmentDetailEntity.getBillParentType())) {
				recNos.add(statmentDetailEntity.getPayableNo());
			}
			// 应付单号
			else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__PAYABLE
					.equals(statmentDetailEntity.getBillParentType())) {
				payNos.add(statmentDetailEntity.getPayableNo());
			}
			else {
				LOGGER.info("还款/批量还款按对账单核销时,对账单明细单据大类型异常，无法核销");
				throw new SettlementException("对账单明细单据大类型异常，无法核销");
			}
		}

		List<BillReceivableEntity> recs = new ArrayList<BillReceivableEntity>();
		List<BillPayableEntity> pays = new ArrayList<BillPayableEntity>();

		// 根据来源单号查询应收单
		if (CollectionUtils.isNotEmpty(recNos)) {
			recs = billReceivableService.queryByReceivableNOs(recNos,FossConstants.ACTIVE);
			// 校应收单原始单据数量和对账单明细数量是否匹配
			if ((CollectionUtils.isNotEmpty(recs) && recs.size() != recNos.size())
					|| (CollectionUtils.isEmpty(recs) && recNos.size() > 0)) {
				LOGGER.info("还款/批量还款按对账单核销时,对账单明细数量和应收单原始单据数量不相等，无法核销");
				throw new SettlementException("对账单明细数量和应收单原始单据数量不相等，无法核销");
			}
		}

		// 根据来源单号查询应付单
		if (CollectionUtils.isNotEmpty(payNos)) {
			pays = billPayableService.queryByPayableNOs(payNos,FossConstants.ACTIVE);
			// 校应付单原始单据数量和对账单明细数量是否匹配
			if ((CollectionUtils.isNotEmpty(pays) && pays.size() != payNos.size())
					|| (CollectionUtils.isEmpty(pays) && payNos.size() > 0)) {
				LOGGER.info("还款/批量还款按对账单核销时,对账单明细数量和应付单原始单据数量不相等，无法核销");
				throw new SettlementException("对账单明细数量和应付单原始单据数量不相等，无法核销");
			}
		}

		BillWriteoffOperationDto writeoffDto = new BillWriteoffOperationDto();

		//获取其中待核销的一个应收单
		BillReceivableEntity rtnBillReceivableEntity = recs.get(0);
		
		// 设置核销参数中的核销方列表：应收单列表、应付单列表、预收单列表、预付单列表
		writeoffDto.setBillReceivableEntitys(recs);
		writeoffDto.setBillPayableEntitys(pays);

		// 设置还款单
		writeoffDto.setBillRepaymentEntity(entity);

		writeoffDto.setWriteoffBatchNo(writeoffBatchNo);

		// 核销类型为手工核销
		writeoffDto.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);

		// 核销对账单编号取对账单上的单据编号
		writeoffDto.setStatementBillNo(statementNo);

		// 核销对账单明细
		writeoffStatementDetailForRepayment(writeoffDto, cInfo);

		return rtnBillReceivableEntity;
	}
	
	/**
	 * 对账单明细核销，包括：应收冲应付、还款冲应收 
	 * @author ddw
	 * @date 2014-06-17
	 */
	private BillWriteoffOperationDto writeoffStatementDetailForRepayment(
			BillWriteoffOperationDto writeoffDto, CurrentInfo currentInfo) {
		// 初始化返回结果
		BillWriteoffOperationDto writeoffResultDto = new BillWriteoffOperationDto();
		writeoffResultDto.setBillPayableEntitys(writeoffDto.getBillPayableEntitys());

		// 应收冲应付
		if (CollectionUtils.isNotEmpty(writeoffDto.getBillReceivableEntitys())
				&& CollectionUtils.isNotEmpty(writeoffDto.getBillPayableEntitys())) {
			LOGGER.info("还款/批量还款按对账单核销时,核销应收冲应付...");
			writeoffResultDto = billWriteoffService.writeoffReceibableAndPayable(writeoffDto, currentInfo);
			writeoffDto.setBillReceivableEntitys(writeoffResultDto.getBillReceivableEntitys());
			writeoffDto.setBillPayableEntitys(writeoffResultDto.getBillPayableEntitys());
		}

		// 还款冲应收
		if (CollectionUtils.isNotEmpty(writeoffDto.getBillReceivableEntitys())
				&& writeoffDto.getBillRepaymentEntity() != null) {
			LOGGER.info("还款/批量还款按对账单核销时,核销还款冲应收...");
			billWriteoffService.writeoffRepaymentAndReceibable(writeoffDto,currentInfo);
			writeoffDto.setBillReceivableEntitys(writeoffResultDto.getBillReceivableEntitys());
			writeoffDto.setBillPayableEntitys(writeoffResultDto.getBillPayableEntitys());
		}

		return writeoffResultDto;
	}

	/**
	 * 生成对账单还款单关系数据
	 * @author ddw
	 * @date 2014-06-17
	 */
	private void addSoaRepayment(String statementNo, String repaymentNo,BigDecimal repaymentAmount) {
		// 生成对账单还款单关系数据
		SoaRepaymentEntity entity = new SoaRepaymentEntity();
		entity.setId(UUIDUtils.getUUID());
		entity.setStatementBillNo(statementNo);
		entity.setRepaymentNo(repaymentNo);
		entity.setRepaymentAmount(repaymentAmount);
		entity.setPaymentDate(new Date());

		// 保存对账单还款单关系数据
		soaRepaymentManageService.add(entity);

		LOGGER.info("还款/批量还款单时,生成对账单还款单关系数据成功,对账单号："
				+ entity.getStatementBillNo() + ",还款单号："
				+ entity.getRepaymentNo());
	}
	
	public void setBillRepaymentService(IBillRepaymentService billRepaymentService) {
		this.billRepaymentService = billRepaymentService;
	}

	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	public void setFossToFinanceRemittanceService(
			IFossToFinanceRemittanceService fossToFinanceRemittanceService) {
		this.fossToFinanceRemittanceService = fossToFinanceRemittanceService;
	}

	public void setBillWriteoffService(IBillWriteoffService billWriteoffService) {
		this.billWriteoffService = billWriteoffService;
	}

	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	public void setWoodenStatementService(
			IWoodenStatementService woodenStatementService) {
		this.woodenStatementService = woodenStatementService;
	}

	public void setSoaRepaymentManageService(
			ISoaRepaymentManageService soaRepaymentManageService) {
		this.soaRepaymentManageService = soaRepaymentManageService;
	}

	public void setFinanceOnlinePayWSProxy(IFinanceOnlinePayWSProxy financeOnlinePayWSProxy) {
		this.financeOnlinePayWSProxy = financeOnlinePayWSProxy;
	}
}
