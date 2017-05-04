package com.deppon.foss.module.settlement.common.server.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.common.api.server.service.IMessageService;
import com.deppon.foss.module.base.common.api.shared.domain.InstationJobMsgEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.dao.IBillWriteoffEntityDao;
import com.deppon.foss.module.settlement.common.api.server.service.IBillAdvancedPaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillBadAccountService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillDepositReceivedService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillRepaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillBadAccountEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWritebackOperationDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffQueryParaDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.ListComparator;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 核销服务
 * 
 * @author foss-wangxuemin
 * @date 2012-10-10 下午3:30:25
 */
public class BillWriteoffService implements IBillWriteoffService {

	/**
	 * 日志
	 */
	private static final Logger logger = LogManager
			.getLogger(BillWriteoffService.class);

	/**
	 * 注入结算通用服务
	 */
	private ISettlementCommonService settlementCommonService;

	/**
	 * 注入应收单服务
	 */
	private IBillReceivableService billReceivableService;

	/**
	 * 注入应付单服务
	 */
	private IBillPayableService billPayableService;

	/**
	 * 注入预收单服务
	 */
	private IBillDepositReceivedService billDepositReceivedService;

	/**
	 * 注入预付单服务
	 */
	private IBillAdvancedPaymentService billAdvancedPaymentService;

	/**
	 * 注入还款单服务
	 */
	private IBillRepaymentService billRepaymentService;

	/**
	 * 注入坏账服务
	 */
	private IBillBadAccountService billBadAccountService;

	/**
	 * 注入核销单Dao
	 */
	private IBillWriteoffEntityDao billWriteoffEntityDao;
	
	/**
	 * 注入在线通知服务
	 */
	private IMessageService messageService;


	/**
	 * 新增核销单
	 * 
	 * @author foss-jiangxun
	 * @date 2016-06-02 11:04:00 AM
	 */
	public void addWriteoff(BillWriteoffEntity entity) {
		// FOSS生成的所有单据的初始化状态都为"N"
		entity.setIsInit(FossConstants.NO);
		int i = billWriteoffEntityDao.add(entity);
		//i不等于1提示异常信息
		if (i != 1) {
			throw new SettlementException("生成核销单失败");
		}
	}

	/**
	 * 新增核销单
	 * 
	 * @author foss-wangxuemin
	 * @date Apr 19, 2013 3:49:11 PM
	 */
	private void add(BillWriteoffEntity entity) {
		// FOSS生成的所有单据的初始化状态都为"N"
		entity.setIsInit(FossConstants.NO);
		int i = billWriteoffEntityDao.add(entity);
		//i不等于1提示异常信息
		if (i != 1) {
			throw new SettlementException("生成核销单失败");
		}
	}

	/**
	 * 根据核销单ID反核销/红冲
	 * 
	 * @author foss-wangxuemin
	 * @date 2012-10-22 下午2:04:01
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService#writeBackBillWriteoff(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public BillWritebackOperationDto  writeBackBillWriteoffById(String id,
			CurrentInfo currentInfo) {
		logger.info("反核销核销单ID :" + id);

		BillWriteoffEntity billWriteoffEntity = billWriteoffEntityDao
				.queryByPrimaryKey(id);
		return writeBackBillWriteoffByEntity(billWriteoffEntity, currentInfo);

	}

	/**
	 * 根据核销单实体反核销/红冲
	 * 
	 * @author foss-wangxuemin
	 * @date 2012-10-23 下午2:57:03
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService#writeBackBillWriteoffByEntity(com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public BillWritebackOperationDto writeBackBillWriteoffByEntity(
			BillWriteoffEntity billWriteoffEntity, CurrentInfo currentInfo) {
		/*
		 * 1 判断是否允许反核销
		 */
		// 核销单记录不能为空
		if (null == billWriteoffEntity) {
			throw new SettlementException("核销单记录不能为空");
		}
		// 输入参数中的红冲员工编号和名称不能为空
		if (null == currentInfo) {
			throw new SettlementException("当前登录用户信息不能为空");
		}
		if (StringUtils.isEmpty(currentInfo.getEmpCode())
				|| StringUtils.isEmpty(currentInfo.getEmpName())) {
			throw new SettlementException("红冲员工编号和名称不能为空");
		}
		// 已失效的核销单不允许反核销
		if (FossConstants.INACTIVE.equals(billWriteoffEntity.getActive())) {
			throw new SettlementException("已失效的核销单不允许反核销");
		}

		logger.info("反核销核销单ID :" + billWriteoffEntity.getId());

		// 获取系统当期日期
		Date now = new Date();

		/*
		 * 2 根据核销类型进行反核销
		 */
		BillWritebackOperationDto writebackResultDto = writebackBills(
				billWriteoffEntity, currentInfo);

		/*
		 * 3 红冲核销单
		 */
		writebackWriteoff(billWriteoffEntity, currentInfo, now);

		return writebackResultDto;
	}

	/**
	 * 根据应收单号和应付单号反核销/红冲(偏线外发反审核)
	 * 
	 * @author foss-wangxuemin
	 * @date Nov 27, 2012 3:19:58 PM
	 */
	public void writeBackByRecNoAndPayNo(String receivableNo, String payableNo,
			CurrentInfo currentInfo) {
		// 校验应收单号和应付单号
		if (StringUtils.isEmpty(receivableNo) || StringUtils.isEmpty(payableNo)) {
			throw new SettlementException("应收单号和应付单号不能为空");
		}

		logger.info("反核销的应收单编号:" + receivableNo + ", 应付单编号:" + payableNo);

		// 设置查询参数
		BillWriteoffQueryParaDto queryParaDto = new BillWriteoffQueryParaDto();
		queryParaDto.setBeginNo(receivableNo);
		queryParaDto.setEndNo(payableNo);
		// 查询有效的记录
		queryParaDto.setActive(FossConstants.ACTIVE);
		// 查询自动生成的记录
		queryParaDto
				.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);
		// 查询应收冲应付的记录
		queryParaDto
				.setWriteoffType(SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__RECEIVABLE_PAYABLE);

		List<BillWriteoffEntity> billWriteoffEntitys = billWriteoffEntityDao
				.queryByBeginNoAndEndNo(queryParaDto);
		if (CollectionUtils.isNotEmpty(billWriteoffEntitys)) {
			for (BillWriteoffEntity entity : billWriteoffEntitys) {
				// 调用按明细反核销方法进行反核销
				writeBackBillWriteoffByEntity(entity, currentInfo);
			}
		}
	}

	/**
	 * 红冲核销单记录
	 * 
	 * @author foss-wangxuemin
	 * @date Nov 9, 2012 10:14:40 AM
	 */
	private void writebackWriteoff(BillWriteoffEntity billWriteoffEntity,
			CurrentInfo currentInfo, Date now) {
		BillWriteoffEntity newBillWriteoffEntity = new BillWriteoffEntity();
		// 其他信息不变
		BeanUtils.copyProperties(billWriteoffEntity, newBillWriteoffEntity);
		// 获取ID
		newBillWriteoffEntity.setId(UUIDUtils.getUUID());
		// 记录红冲员工编号
		newBillWriteoffEntity.setRedImpactUserCode(currentInfo.getEmpCode());
		// 记录红冲员工名称
		newBillWriteoffEntity.setRedImpactUserName(currentInfo.getEmpName());
		// 记录为红单
		newBillWriteoffEntity.setIsRedBack(FossConstants.YES);
		// 记录为无效状态
		newBillWriteoffEntity.setActive(FossConstants.INACTIVE);
		// 金额和原金额相反
		newBillWriteoffEntity
				.setAmount(billWriteoffEntity.getAmount().negate());
		// 记账日期为当前系统日期
		newBillWriteoffEntity.setAccountDate(now);
		// 生成新红冲核销单
		this.add(newBillWriteoffEntity);

		// 更新原核销记录的生效标志为无效
		billWriteoffEntity.setActive(FossConstants.INACTIVE);
		int updateResult = billWriteoffEntityDao
				.updateActiveById(billWriteoffEntity);
		if (updateResult != 1) {
			throw new SettlementException("更新核销单失败");
		}
	}

	/**
	 * 根据核销单记录反核销相关单据
	 * 
	 * @author foss-wangxuemin
	 * @date Nov 14, 2012 10:48:54 AM
	 */
	private BillWritebackOperationDto writebackBills(
			BillWriteoffEntity billWriteoffEntity, CurrentInfo currentInfo) {
		// 反核销结果Dto
		BillWritebackOperationDto writebackResultDto = new BillWritebackOperationDto();

		/*
		 * 根据核销类型查询开始、目的单据记录，更新开始、目的单据的已核销金额、未核销金额
		 */
		// 应收冲应付反核销时
		if (SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__RECEIVABLE_PAYABLE
				.equals(billWriteoffEntity.getWriteoffType())) {
			// 反核销应收单
			BillReceivableEntity writeoffRecEntity = writebackBillReceivable(
					billWriteoffEntity.getBeginNo(),
					billWriteoffEntity.getAmount(), currentInfo);
			writebackResultDto.setBillReceivableEntity(writeoffRecEntity);

			// 反核销应付单
			BillPayableEntity writeoffPayEntity = writebackBillPayable(
					billWriteoffEntity.getEndNo(),
					billWriteoffEntity.getAmount(), currentInfo);
			writebackResultDto.setBillPayableEntity(writeoffPayEntity);
		}
		// 预收冲应收反核销时
		else if (SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__DEPOSIT_RECEIVABLE
				.equals(billWriteoffEntity.getWriteoffType())) {
			// 反核销预收单
			BillDepositReceivedEntity writeoffDeposit = writebackDeposit(
					billWriteoffEntity.getBeginNo(),
					billWriteoffEntity.getAmount(), currentInfo);
			writebackResultDto.setBillDepositReceivedEntity(writeoffDeposit);

			// 反核销应收单
			BillReceivableEntity writeoffRecEntity = writebackBillReceivable(
					billWriteoffEntity.getEndNo(),
					billWriteoffEntity.getAmount(), currentInfo);
			writebackResultDto.setBillReceivableEntity(writeoffRecEntity);
		}
		// 预付冲应付反核销时
		else if (SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__ADVANCED_PAYABLE
				.equals(billWriteoffEntity.getWriteoffType())) {
			// 反核销预付单
			BillAdvancedPaymentEntity writebackAdvanced = writebackAdvanced(
					billWriteoffEntity.getBeginNo(),
					billWriteoffEntity.getAmount(), currentInfo);
			writebackResultDto.setBillAdvancedPaymentEntity(writebackAdvanced);

			// 反核销应付单
			BillPayableEntity writeoffPayEntity = writebackBillPayable(
					billWriteoffEntity.getEndNo(),
					billWriteoffEntity.getAmount(), currentInfo);
			writebackResultDto.setBillPayableEntity(writeoffPayEntity);
		}
		// 还款冲应收反核销时
		else if (SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE
				.equals(billWriteoffEntity.getWriteoffType())) {
			// 查询并更新还款单核销金额
			BillRepaymentEntity billRepaymentEntity = billRepaymentService
					.queryByRepaymentNO(billWriteoffEntity.getBeginNo(),
							FossConstants.ACTIVE);
			if (null != billRepaymentEntity) {
				billRepaymentService.reverseWriteoffBillRepayment(
						billRepaymentEntity, billWriteoffEntity.getAmount(),
						currentInfo);
			}

			// 反核销应收单
			BillReceivableEntity writeoffRecEntity = writebackBillReceivable(
					billWriteoffEntity.getEndNo(),
					billWriteoffEntity.getAmount(), currentInfo);
			writebackResultDto.setBillReceivableEntity(writeoffRecEntity);
		}
		// 付款冲应付反核销时
		else if (SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__PAYMENT_PAYABLE
				.equals(billWriteoffEntity.getWriteoffType())) {
			// 付款单的红冲在调用此服务的服务中操作
			// 反核销应付单
			BillPayableEntity writeoffPayEntity = writebackBillPayable(
					billWriteoffEntity.getEndNo(),
					billWriteoffEntity.getAmount(), currentInfo);
			writebackResultDto.setBillPayableEntity(writeoffPayEntity);
		}
		// 付款冲预收反核销时
		else if(SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__PAYABLE_DEPOSIT
				.equals(billWriteoffEntity.getWriteoffType())){
			// 反核销预收单
			BillDepositReceivedEntity writeoffDeposit = writebackDeposit(
					billWriteoffEntity.getEndNo(),
					billWriteoffEntity.getAmount(), currentInfo);
			writebackResultDto.setBillDepositReceivedEntity(writeoffDeposit);
		}
		// 坏账核销、预付冲应付时，不允许反核销
		else if (SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__BAD_RECEIVABLE
				.equals(billWriteoffEntity.getWriteoffType())) {
			throw new SettlementException("付款冲应付、坏账核销、预付冲应付类型的核销单不允许反核销");
		}
		// 其他类型时，核销类型异常
		else {
			throw new SettlementException("核销类型异常");
		}

		return writebackResultDto;
	}

	/**
	 * 反核销应收单
	 * 
	 * @author foss-wangxuemin
	 * @date Nov 14, 2012 10:49:24 AM
	 */
	private BillReceivableEntity writebackBillReceivable(String receivableNo,
			BigDecimal writebackAmount, CurrentInfo currentInfo) {
		//
		BillReceivableEntity writebackRecEntity = new BillReceivableEntity();

		// 查询并更新应收核销金额
		BillReceivableEntity billReceivableEntity = billReceivableService.queryBillReceivableByReceivableNO(receivableNo, FossConstants.ACTIVE);
		if (null != billReceivableEntity) {
			// 为了财务完结，设置反核销后的应收单核销和未核销金额
			billReceivableEntity.setUnverifyAmount(billReceivableEntity
					.getUnverifyAmount().add(writebackAmount));
			billReceivableEntity.setVerifyAmount(billReceivableEntity
					.getVerifyAmount().subtract(writebackAmount));

			// 更新应收单核销金额
			billReceivableService.writeoffBillReceivable(billReceivableEntity,
					writebackAmount.negate(), currentInfo);

			// 生成返回的反核销后的应收单实体
			BeanUtils.copyProperties(billReceivableEntity, writebackRecEntity);
		} else {
			throw new SettlementException("核销单对应的应收单不存在");
		}

		return writebackRecEntity;
	}

	/**
	 * 反核销应付单
	 * 
	 * @author foss-wangxuemin
	 * @date Nov 14, 2012 10:49:37 AM
	 */
	private BillPayableEntity writebackBillPayable(String payableNo,
			BigDecimal writebackAmount, CurrentInfo currentInfo) {
		//
		BillPayableEntity writebackPayEntity = new BillPayableEntity();

		// 查询应付单
		BillPayableEntity billPayableEntity = billPayableService
				.queryByPayableNO(payableNo, FossConstants.ACTIVE);

		if (null != billPayableEntity) {
			// 为了财务完结，设置反核销后的应付单核销和未核销金额
			billPayableEntity.setUnverifyAmount(billPayableEntity
					.getUnverifyAmount().add(writebackAmount));
			billPayableEntity.setVerifyAmount(billPayableEntity
					.getVerifyAmount().subtract(writebackAmount));

			// 更新应付单核销金额
			billPayableService.writeoffBillPayable(billPayableEntity,
					writebackAmount.negate(), currentInfo);

			// 生成返回的反核销后的应付单实体
			BeanUtils.copyProperties(billPayableEntity, writebackPayEntity);
		} else {
			throw new SettlementException("核销单对应的应付单不存在");
		}

		return writebackPayEntity;
	}

	/**
	 * 反核销预收单
	 * 
	 * @author foss-wangxuemin
	 * @date Nov 14, 2012 11:02:32 AM
	 */
	private BillDepositReceivedEntity writebackDeposit(String depositNo,
			BigDecimal writebackAmount, CurrentInfo currentInfo) {
		//
		BillDepositReceivedEntity writebackDepEntity = new BillDepositReceivedEntity();

		// 查询预收单
		BillDepositReceivedEntity billDepositReceivedEntity = billDepositReceivedService
				.queryByDepositReceivedNo(depositNo, FossConstants.ACTIVE);

		if (null != billDepositReceivedEntity) {
			// 设置反核销后的预收单核销和未核销金额
			billDepositReceivedEntity
					.setUnverifyAmount(billDepositReceivedEntity
							.getUnverifyAmount().add(writebackAmount));
			billDepositReceivedEntity.setVerifyAmount(billDepositReceivedEntity
					.getVerifyAmount().subtract(writebackAmount));

			// 更新预收单核销金额
			billDepositReceivedService.writeoffBillDepositReceived(
					billDepositReceivedEntity, writebackAmount.negate(),
					currentInfo);

			// 生成返回的反核销后的预收单实体
			BeanUtils.copyProperties(billDepositReceivedEntity,
					writebackDepEntity);
		} else {
			throw new SettlementException("核销单对应的预收单不存在");
		}

		return writebackDepEntity;
	}

	/**
	 * 反核销预付单
	 * 
	 * @author foss-wangxuemin
	 * @date Nov 14, 2012 11:09:12 AM
	 */
	private BillAdvancedPaymentEntity writebackAdvanced(String advancedNo,
			BigDecimal writebackAmount, CurrentInfo currentInfo) {
		//
		BillAdvancedPaymentEntity writebackAdvEntity = new BillAdvancedPaymentEntity();

		// 查询并更新预付单核销金额
		BillAdvancedPaymentEntity billAdvancedPaymentEntity = billAdvancedPaymentService
				.queryBillAdvancedPaymentNo(advancedNo, FossConstants.ACTIVE);
		if (null != billAdvancedPaymentEntity) {
			// 设置反核销后的预付单核销和未核销金额
			billAdvancedPaymentEntity
					.setUnverifyAmount(billAdvancedPaymentEntity
							.getUnverifyAmount().add(writebackAmount));
			billAdvancedPaymentEntity.setVerifyAmount(billAdvancedPaymentEntity
					.getVerifyAmount().subtract(writebackAmount));

			// 更新预付单核销金额
			billAdvancedPaymentService.writeoffAdvancedPayment(
					billAdvancedPaymentEntity, writebackAmount.negate(),
					currentInfo);

			// 生成返回的反核销后的预付单实体
			BeanUtils.copyProperties(billAdvancedPaymentEntity,
					writebackAdvEntity);
		} else {
			throw new SettlementException("核销单对应的预付单不存在");
		}

		return writebackAdvEntity;
	}

	/**
	 * 根据还款单号红冲/反核销
	 * 
	 * @author foss-wangxuemin
	 * @date 2012-10-22 下午2:04:55
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService#writeBackBillWriteoffByRepayment(java.lang.String)
	 */
	public void writeBackBillWriteoffByRepayment(String repaymentNo,
			CurrentInfo currentInfo) {
		logger.info("还款单号:" + repaymentNo);

		/*
		 * 1 根据还款单号查询核销单记录
		 */
		List<BillWriteoffEntity> billWriteoffEntitys = billWriteoffEntityDao
				.queryByBeginNo(
						repaymentNo,
						FossConstants.ACTIVE,
						null,
						SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE);

		// 如果还款单编号对应核销记录为空，则无法反核销/红冲操作
		if (CollectionUtils.isEmpty(billWriteoffEntitys)) {
			throw new SettlementException("还款单编号对应核销记录为空，无法反核销/红冲");
		}

		/*
		 * 2 红冲核销单记录
		 */
		for (BillWriteoffEntity billWriteoffEntity : billWriteoffEntitys) {
			writeBackBillWriteoffByEntity(billWriteoffEntity, currentInfo);
		}
	}

	/**
	 * 根据付款单号红冲/反核销
	 * 
	 * @author foss-wangxuemin
	 * @date 2012-10-22 下午2:04:55
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService#writeBackBillWriteoffByPayment(java.lang.String)
	 */
	public void writeBackBillWriteoffByPayment(String paymentNo,
			CurrentInfo currentInfo) {
		logger.info("付款单号:" + paymentNo);

		/*
		 * 1 根据付款单号查询核销单记录
		 */
		List<BillWriteoffEntity> billWriteoffEntitys = billWriteoffEntityDao
				.queryByBeginNo(
						paymentNo,
						FossConstants.ACTIVE,
						null,
						null);//此处不能传类型，因为付款失败冲预收也要用

		// 如果付款单编号对应核销记录为空，则无法反核销/红冲操作
		if (CollectionUtils.isEmpty(billWriteoffEntitys)) {
			throw new SettlementException("付款单编号对应核销记录为空，无法反核销/红冲");
		}

		/*
		 * 2 红冲核销单记录
		 */
		for (BillWriteoffEntity billWriteoffEntity : billWriteoffEntitys) {
			writeBackBillWriteoffByEntity(billWriteoffEntity, currentInfo);
		}
	}

	/**
	 * 根据开始或者目的来源编号查询核销记录
	 * 
	 * @author foss-wangxuemin
	 * @date Nov 5, 2012 10:19:26 AM
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService#queryBillWriteoffByBeginOrEndNo(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillWriteoffEntity> queryBillWriteoffByBeginOrEndNo(
			String beginOrEndNo, String active, String createType) {
		logger.info("来源单号" + beginOrEndNo);

		// 根据开始来源编号查询
		List<BillWriteoffEntity> beginBillWriteoffEntitys = billWriteoffEntityDao
				.queryByBeginNo(beginOrEndNo, active, createType, null);

		// 根据目的来源编号查询
		List<BillWriteoffEntity> endBillWriteoffEntitys = billWriteoffEntityDao
				.queryByEndNo(beginOrEndNo, active, createType);

		// 合并查询出的两个列表返回
		return ListUtils
				.union(beginBillWriteoffEntitys, endBillWriteoffEntitys);
	}

	/**
	 * 
	 * 根据运单号查询是否存在手工核销的应收单
	 * 
	 * @Title: queryHandWriteoffReceivableByWaybillNo
	 * @author 046644-foss-zengbinwen
	 * @date 2013-4-12 上午11:34:36
	 * @param @param waybillNo
	 * @param @return 设定文件
	 * @return List<BillWriteoffEntity> 返回类型
	 * @throws
	 */
	public List<BillWriteoffEntity> queryHandWriteoffReceivableByWaybillNo(
			String waybillNo) {
		logger.info("运单号:" + waybillNo);

		if (StringUtils.isEmpty(waybillNo)) {
			throw new SettlementException("运单号不能为空");
		}

		return billWriteoffEntityDao
				.queryHandWriteoffReceivableByWaybillNo(waybillNo);
	}

	/**
	 * 根据还款单号查询核销记录
	 * 
	 * @auhor foss-wangxuemin
	 * @date Nov 12, 2012 3:35:25 PM
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService#queryByRepayment(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public List<BillWriteoffEntity> queryByRepayment(String repaymentNo,
			String active) {
		logger.info("根据还款单号查询核销单，还款单号：" + repaymentNo);

		return billWriteoffEntityDao
				.queryByBeginNo(
						repaymentNo,
						active,
						null,
						SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE);
	}

	/**
	 * 根据对账单好查询核销单记录
	 * 
	 * @author foss-wangxuemin
	 * @date Mar 20, 2013 3:57:09 PM
	 */
	@Override
	public List<BillWriteoffEntity> queryByStatementBillNo(
			String statementBillNo, String beginNo, String endNo,
			String writeoffType, String active) {
		logger.info("根据对账单好查询核销单，对账单号：" + statementBillNo);

		return billWriteoffEntityDao.queryByStatementBillNo(statementBillNo,
				beginNo, endNo, writeoffType, active);
	}

	/**
	 * 预收冲应收（按业务日期排序）
	 * 
	 * @author foss-wangxuemin
	 * @date Nov 6, 2012 3:41:29 PM
	 */
	@Override
	public BillWriteoffOperationDto writeoffDepositAndReceivable(
			BillWriteoffOperationDto billWriteoffOperationDto,
			CurrentInfo currentInfo) {
		// 按业务日期对Dto中的核销双方的列表进行排序
		sortWriteoffOperationList(billWriteoffOperationDto);

		// 核销
		return writeoffDepAndRecNoSort(billWriteoffOperationDto, currentInfo);
	}

	/**
	 * 预收冲应收（无序）
	 * 
	 * @author foss-wangxuemin
	 * @date 2012-10-18 下午2:23:55
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService#writeoffDepositAndReceivable(com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto)
	 */
	private BillWriteoffOperationDto writeoffDepAndRecNoSort(
			BillWriteoffOperationDto billWriteoffOperationDto,
			CurrentInfo currentInfo) {
		// 校验通用参数是否为空
		checkParameters(billWriteoffOperationDto, currentInfo);
		// 校验预收单列表是否为空
		if (CollectionUtils.isEmpty(billWriteoffOperationDto
				.getBillDepositReceivedEntitys())) {
			throw new SettlementException("预收单列表为空，无法核销");
		}
		// 校验应收单列表是否为空
		if (CollectionUtils.isEmpty(billWriteoffOperationDto
				.getBillReceivableEntitys())) {
			throw new SettlementException("应收单列表为空，无法核销");
		}

		// 取系统当前日期
		Date now = new Date();

		// 循环核销每笔预收单
		for (BillDepositReceivedEntity dep : billWriteoffOperationDto
				.getBillDepositReceivedEntitys()) {
			// 预收金额大于零时，才能用于核销
			if (dep.getUnverifyAmount().compareTo(BigDecimal.ZERO) > 0) {
				for (BillReceivableEntity rec : billWriteoffOperationDto
						.getBillReceivableEntitys()) {
					// 应收金额大于零时，才能用于核销
					if (rec.getUnverifyAmount().compareTo(BigDecimal.ZERO) > 0) {
						// 生成核销单记录
						BillWriteoffEntity billWriteoffEntity = new BillWriteoffEntity();

						// 设置核销单通用值
						setWriteoffEntityValuesForAdd(billWriteoffOperationDto,
								billWriteoffEntity, now, currentInfo);
						// 开始来源单号为预收单单号
						billWriteoffEntity.setBeginNo(dep
								.getDepositReceivedNo());
						//设置beginID
						billWriteoffEntity.setBeginId(dep.getId());
						//设置目的id
						billWriteoffEntity.setEndId(rec.getId());
						// 开始来源记账日期
						billWriteoffEntity.setBeginAccountDate(dep
								.getAccountDate());
						// 目的来源单号为应收单号
						billWriteoffEntity.setEndNo(rec.getReceivableNo());
						
						// 目的来源记账日期
						billWriteoffEntity.setEndAccountDate(rec
								.getAccountDate());
						// 核销部门编码为应收单催款部门编码
						billWriteoffEntity.setOrgCode(rec.getDunningOrgCode());
						// 核销部门名称为应收单催款部门名称
						billWriteoffEntity.setOrgName(rec.getDunningOrgName());
						// 核销类型应预收冲应收
						billWriteoffEntity
								.setWriteoffType(SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__DEPOSIT_RECEIVABLE);
						// 客户信息取应收单客户信息
						billWriteoffEntity.setCustomerCode(rec
								.getCustomerCode());
						billWriteoffEntity.setCustomerName(rec
								.getCustomerName());
						// 目的运单号为应收单运单号
						billWriteoffEntity.setEndWaybillNo(rec.getWaybillNo());

						// 当应收未核销金额大于等于预收未核销金额时，核销单金额为预收金额；当应收未核销金额小于预收未核销金额时，核销单金额为应收金额
						if (rec.getUnverifyAmount().compareTo(
								dep.getUnverifyAmount()) >= 0) {
							billWriteoffEntity.setAmount(dep
									.getUnverifyAmount());
						} else {
							billWriteoffEntity.setAmount(rec
									.getUnverifyAmount());
						}

						// 保存核销单记录
						this.add(billWriteoffEntity);

						// 更新预收单记录的已核销金额和未核销金额
						dep.setUnverifyAmount(dep.getUnverifyAmount().subtract(
								billWriteoffEntity.getAmount()));
						dep.setVerifyAmount(dep.getVerifyAmount().add(
								billWriteoffEntity.getAmount()));
						billDepositReceivedService.writeoffBillDepositReceived(
								dep, billWriteoffEntity.getAmount(),
								currentInfo);

						// 更新应收单记录的已核销金额和未核销金额
						rec.setUnverifyAmount(rec.getUnverifyAmount().subtract(
								billWriteoffEntity.getAmount()));
						rec.setVerifyAmount(rec.getVerifyAmount().add(
								billWriteoffEntity.getAmount()));
						billReceivableService.writeoffBillReceivable(rec,
								billWriteoffEntity.getAmount(), currentInfo);

						// 如果预收金额已经核销为零，则退出本次循环
						if (dep.getUnverifyAmount().compareTo(BigDecimal.ZERO) == 0) {
							break;
						}

					} else {
						continue;
					}
				}

				// 如果预收核销完所有的应收后，预收的未核销金额还大于零，则退出循环
				if (dep.getUnverifyAmount().compareTo(BigDecimal.ZERO) > 0) {
					break;
				}
			} else {
				continue;
			}

		}
		return billWriteoffOperationDto;
	}

	/**
	 * 应收冲应付（按业务日期排序）
	 * 
	 * @author foss-wangxuemin
	 * @date Nov 6, 2012 3:53:38 PM
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService#writeoffReceibableAndPayable(com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto)
	 */
	@Override
	public BillWriteoffOperationDto writeoffReceibableAndPayable(
			BillWriteoffOperationDto billWriteoffOperationDto,
			CurrentInfo currentInfo) {
		// 按业务日期对Dto中的核销双方的列表进行排序
		sortWriteoffOperationList(billWriteoffOperationDto);

		// 核销
		return writeoffRecAndPayNoSort(billWriteoffOperationDto, currentInfo);
	}

	/**
	 * 应收冲应付（无序）
	 * 
	 * @author foss-wangxuemin
	 * @date 2012-10-18 下午2:24:20
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService#writeoffReceibableAndPayable(com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto)
	 */
	private BillWriteoffOperationDto writeoffRecAndPayNoSort(
			BillWriteoffOperationDto billWriteoffOperationDto,
			CurrentInfo currentInfo) {
		// 校验通用参数是否为空
		checkParameters(billWriteoffOperationDto, currentInfo);
		// 校验应收单列表是否为空
		if (CollectionUtils.isEmpty(billWriteoffOperationDto
				.getBillReceivableEntitys())) {
			throw new SettlementException("应收单列表为空，无法核销");
		}
		// 校验应付单列表是否为空
		if (CollectionUtils.isEmpty(billWriteoffOperationDto
				.getBillPayableEntitys())) {
			throw new SettlementException("应付单列表为空，无法核销");
		}

		// 取系统当前日期
		Date now = new Date();

		// 循环核销每笔应收单
		for (BillReceivableEntity rec : billWriteoffOperationDto
				.getBillReceivableEntitys()) {
			// 应收金额大于零时，才能用于核销
			if (rec.getUnverifyAmount().compareTo(BigDecimal.ZERO) > 0) {
				for (BillPayableEntity pay : billWriteoffOperationDto
						.getBillPayableEntitys()) {
					// 应付金额大于零时，才能用于核销
					if (pay.getUnverifyAmount().compareTo(BigDecimal.ZERO) > 0) {
						// 生成核销单记录
						BillWriteoffEntity billWriteoffEntity = new BillWriteoffEntity();

						// 设置核销单通用值
						setWriteoffEntityValuesForAdd(billWriteoffOperationDto,
								billWriteoffEntity, now, currentInfo);
						// 开始来源单号为应收单号
						billWriteoffEntity.setBeginNo(rec.getReceivableNo());
						//设置开始和结束id
						billWriteoffEntity.setBeginId(rec.getId());
						billWriteoffEntity.setEndId(pay.getId());
						// 开始来源记账日期
						billWriteoffEntity.setBeginAccountDate(rec
								.getAccountDate());
						// 目的来源单号为应付单好
						billWriteoffEntity.setEndNo(pay.getPayableNo());
						// 目的来源记账日期
						billWriteoffEntity.setEndAccountDate(pay
								.getAccountDate());
						// 核销部门编码为应收单催款部门编码
						billWriteoffEntity.setOrgCode(rec.getDunningOrgCode());
						// 核销部门名称为应收单催款部门名称
						billWriteoffEntity.setOrgName(rec.getDunningOrgName());
						// 核销类型应应收冲应付
						billWriteoffEntity
								.setWriteoffType(SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__RECEIVABLE_PAYABLE);
						// 客户信息去应付单客户信息
						billWriteoffEntity.setCustomerCode(pay
								.getCustomerCode());
						billWriteoffEntity.setCustomerName(pay
								.getCustomerName());
						// 开始运单号为应收单运单号
						billWriteoffEntity
								.setBeginWaybillNo(rec.getWaybillNo());
						// 目的运单号为应付单运单号
						billWriteoffEntity.setEndWaybillNo(pay.getWaybillNo());

						// 当应付未核销金额大于等于应收未核销金额时，核销单金额为应收金额；当应付未核销金额小于应收未核销金额时，核销单金额为应付金额
						if (pay.getUnverifyAmount().compareTo(
								rec.getUnverifyAmount()) >= 0) {
							billWriteoffEntity.setAmount(rec
									.getUnverifyAmount());
						} else {
							billWriteoffEntity.setAmount(pay
									.getUnverifyAmount());
						}

						// 保存核销单记录
						this.add(billWriteoffEntity);

						// 更新应收单记录的已核销金额和未核销金额
						rec.setUnverifyAmount(rec.getUnverifyAmount().subtract(
								billWriteoffEntity.getAmount()));
						rec.setVerifyAmount(rec.getVerifyAmount().add(
								billWriteoffEntity.getAmount()));
						billReceivableService.writeoffBillReceivable(rec,
								billWriteoffEntity.getAmount(), currentInfo);

						// 更新应付单记录的已核销金额和未核销金额
						pay.setUnverifyAmount(pay.getUnverifyAmount().subtract(
								billWriteoffEntity.getAmount()));
						pay.setVerifyAmount(pay.getVerifyAmount().add(
								billWriteoffEntity.getAmount()));
						billPayableService.writeoffBillPayable(pay,
								billWriteoffEntity.getAmount(), currentInfo);

						// 如果应收金额已经核销为零，则退出本次循环
						if (rec.getUnverifyAmount().compareTo(BigDecimal.ZERO) == 0) {
							break;
						}

					} else {
						continue;
					}
				}

				// 如果应收核销完所有的应付后，应收的未核销金额还大于零，则退出循环
				if (rec.getUnverifyAmount().compareTo(BigDecimal.ZERO) > 0) {
					break;
				}
			} else {
				continue;
			}
		}

		return billWriteoffOperationDto;
	}

	/**
	 * 预付冲应付（按业务日期排序）
	 * 
	 * @author foss-wangxuemin
	 * @date Nov 6, 2012 3:55:56 PM
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService#writeoffAdvancedAndPayable(com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto)
	 */
	@Override
	public BillWriteoffOperationDto writeoffAdvancedAndPayable(
			BillWriteoffOperationDto billWriteoffOperationDto,
			CurrentInfo currentInfo) {
		// 按业务日期对Dto中的核销双方的列表进行排序
		sortWriteoffOperationList(billWriteoffOperationDto);

		// 核销
		return writeoffAdvAndPayNoSort(billWriteoffOperationDto, currentInfo);
	}

	/**
	 * 预付冲应付（无序）
	 * 
	 * @author foss-wangxuemin
	 * @date 2012-10-18 下午2:24:37
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService#writeoffAdvancedAndPayable(com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto)
	 */
	private BillWriteoffOperationDto writeoffAdvAndPayNoSort(
			BillWriteoffOperationDto billWriteoffOperationDto,
			CurrentInfo currentInfo) {
		// 校验通用参数是否为空
		checkParameters(billWriteoffOperationDto, currentInfo);
		// 校验预付单列表是否为空
		if (CollectionUtils.isEmpty(billWriteoffOperationDto
				.getBillAdvancedPaymentEntitys())) {
			throw new SettlementException("预付单列表为空，无法核销");
		}
		// 校验应付单列表是否为空
		if (CollectionUtils.isEmpty(billWriteoffOperationDto
				.getBillPayableEntitys())) {
			throw new SettlementException("应付单列表为空，无法核销");
		}

		// 取系统当前日期
		Date now = new Date();

		// 循环核销每笔应付单
		for (BillAdvancedPaymentEntity adv : billWriteoffOperationDto
				.getBillAdvancedPaymentEntitys()) {
			// 预付金额大于零时，才能用于核销
			if (adv.getUnverifyAmount().compareTo(BigDecimal.ZERO) > 0) {
				for (BillPayableEntity pay : billWriteoffOperationDto
						.getBillPayableEntitys()) {
					// 应付金额大于零时，才能用于核销
					if (pay.getUnverifyAmount().compareTo(BigDecimal.ZERO) > 0) {
						// 生成核销单记录
						BillWriteoffEntity billWriteoffEntity = new BillWriteoffEntity();

						// 设置核销单通用值
						setWriteoffEntityValuesForAdd(billWriteoffOperationDto,
								billWriteoffEntity, now, currentInfo);
						// 开始来源单号为预付单号
						billWriteoffEntity.setBeginNo(adv.getAdvancesNo());
						//设置开始和结束id
						billWriteoffEntity.setBeginId(adv.getId());
						billWriteoffEntity.setEndId(pay.getId());
						// 开始来源记账日期
						billWriteoffEntity.setBeginAccountDate(adv
								.getAccountDate());
						// 目的来源单号为应付单号
						billWriteoffEntity.setEndNo(pay.getPayableNo());
						// 目的来源记账日期
						billWriteoffEntity.setEndAccountDate(pay
								.getAccountDate());
						// 核销部门编码为应付单应付部门编码
						billWriteoffEntity.setOrgCode(pay.getPayableOrgCode());
						// 核销部门名称为应付单应付部门名称
						billWriteoffEntity.setOrgName(pay.getPayableOrgName());
						// 核销类型为预付冲应付
						billWriteoffEntity
								.setWriteoffType(SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__ADVANCED_PAYABLE);
						// 客户信息取应付客户信息
						billWriteoffEntity.setCustomerCode(pay
								.getCustomerCode());
						billWriteoffEntity.setCustomerName(pay
								.getCustomerName());
						// 目的运单号为应付单运单号
						billWriteoffEntity.setEndWaybillNo(pay.getWaybillNo());

						// 当应付未核销金额大于等于预付未核销金额时，核销单金额为预付金额；当应付未核销金额小于预付未核销金额时，核销单金额为应付金额
						if (pay.getUnverifyAmount().compareTo(
								adv.getUnverifyAmount()) >= 0) {
							billWriteoffEntity.setAmount(adv
									.getUnverifyAmount());
						} else {
							billWriteoffEntity.setAmount(pay
									.getUnverifyAmount());
						}

						// 保存核销单记录
						this.add(billWriteoffEntity);

						// 更新预付单记录的已核销金额和未核销金额
						adv.setUnverifyAmount(adv.getUnverifyAmount().subtract(
								billWriteoffEntity.getAmount()));
						adv.setVerifyAmount(adv.getVerifyAmount().add(
								billWriteoffEntity.getAmount()));
						billAdvancedPaymentService.writeoffAdvancedPayment(adv,
								billWriteoffEntity.getAmount(), currentInfo);

						// 更新应付单记录的已核销金额和未核销金额
						pay.setUnverifyAmount(pay.getUnverifyAmount().subtract(
								billWriteoffEntity.getAmount()));
						pay.setVerifyAmount(pay.getVerifyAmount().add(
								billWriteoffEntity.getAmount()));
						billPayableService.writeoffBillPayable(pay,
								billWriteoffEntity.getAmount(), currentInfo);

						// 如果预付金额已经核销为零，则退出本次循环
						if (adv.getUnverifyAmount().compareTo(BigDecimal.ZERO) == 0) {
							break;
						}
					} else {
						continue;
					}
				}

				// 如果预付核销完所有的应付后，预付的未核销金额还大于零，则退出循环
				if (adv.getUnverifyAmount().compareTo(BigDecimal.ZERO) > 0) {
					break;
				}
			} else {
				continue;
			}
		}
		return billWriteoffOperationDto;
	}

	/**
	 * 还款冲应收
	 * 
	 * @author foss-wangxuemin
	 * @date Nov 5, 2012 11:33:02 AM
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService#writeoffRepaymentAndReceibable(com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto)
	 */
	@Override
	public BillWriteoffOperationDto writeoffRepaymentAndReceibable(
			BillWriteoffOperationDto billWriteoffOperationDto,
			CurrentInfo currentInfo) {
		/*
		 * 1 参数校验
		 */
		// 校验通用参数是否为空
		checkParameters(billWriteoffOperationDto, currentInfo);
		// 校验还款单是否为空
		BillRepaymentEntity rep = billWriteoffOperationDto
				.getBillRepaymentEntity();
		if (null == rep) {
			throw new SettlementException("还款单为空，无法核销");
		}
		// 校验还款金额是否大于零
		BigDecimal repAmount = rep.getAmount();
		if (repAmount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new SettlementException("还款金额小于等于零，无法核销");
		}
		// 校验应收单列表是否为空
		if (CollectionUtils.isEmpty(billWriteoffOperationDto
				.getBillReceivableEntitys())) {
			throw new SettlementException("应收单列表为空，无法核销");
		}
		// 校验还款单和应收单金额是否相等
		validaOperation(billWriteoffOperationDto, currentInfo, rep, repAmount);
		
		return billWriteoffOperationDto;
	}

	private void validaOperation(
			BillWriteoffOperationDto billWriteoffOperationDto,
			CurrentInfo currentInfo, BillRepaymentEntity rep,
			BigDecimal repAmount) {
		BigDecimal recTotUnAmount = BigDecimal.ZERO;
		for (BillReceivableEntity rec : billWriteoffOperationDto
				.getBillReceivableEntitys()) {
			recTotUnAmount = recTotUnAmount.add(rec.getUnverifyAmount());
		}
		if (repAmount.compareTo(recTotUnAmount) > 0) {
			throw new SettlementException("还款单金额大于应收单未核销金额，无法核销");
		}

		logger.info("还款单号:" + rep.getRepaymentNo());

		/*
		 * 2 核销处理
		 */
		// 取系统当前日期
		validaBill(billWriteoffOperationDto, currentInfo, rep, repAmount);

		/* 网上支付成功后发送网上结清货款的在线通知给到达部门
		 * 
		 * 杨书硕 2013-8-30 下午3:22:00
		 */
		//检查是否网上支付
		if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(rep.getPaymentType())){
			
			// 创建当前登录信息
			EmployeeEntity employee = new EmployeeEntity();
			//设置属性值
			employee.setEmpCode(SettlementConstants.BHO_CODE);
			//设置属性值
			employee.setEmpName(SettlementConstants.BHO_NAME);
			//实例化
			UserEntity user = new UserEntity();
			//设置属性值
			user.setEmployee(employee);
			//设置属性值
			OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
			//设置属性值
			dept.setCode(SettlementConstants.BHO_CODE);
			//设置属性值
			dept.setName(SettlementConstants.BHO_NAME);
			//获取当前登录人
			//CurrentInfo bhoInfo = new CurrentInfo(user, dept);
			
			//循环应收单，取出应收单中的到达部门
			for (BillReceivableEntity rec : billWriteoffOperationDto.getBillReceivableEntitys()) {
				
				validaReceivable(rec);
			}
		}
	}

	private void validaReceivable(BillReceivableEntity rec) {
		//检查应收单是否到付应收
		if(SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL.equals(rec.getSourceBillType())
				&&(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE.equals(rec.getBillType())
				||SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE.equals(rec.getBillType())
				||SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY.equals(rec.getBillType()))){
		
			InstationJobMsgEntity msgEntity = new InstationJobMsgEntity();

			msgEntity.setSenderCode(SettlementConstants.BHO_CODE);
			msgEntity.setSenderName(SettlementConstants.BHO_NAME);
			msgEntity.setSenderOrgCode(SettlementConstants.BHO_CODE);
			msgEntity.setSenderOrgName(SettlementConstants.BHO_NAME);
			msgEntity.setContext("您好，单号：" + rec.getWaybillNo() + " 的应收单：" + rec.getReceivableNo() + "已经网上支付结清货款，请注意不要重复收取费用。");
			//设置为在线支付消息
			msgEntity.setMsgType(MessageConstants.MSG_TYPE__ONLINEPAYMENT);						
			//接受方式为组织
			msgEntity.setReceiveType(MessageConstants.MSG__RECEIVE_TYPE__ORG);
			//设置接收部门信息
			msgEntity.setReceiveOrgCode(rec.getDestOrgCode());
			msgEntity.setReceiveOrgName(rec.getDestOrgName());
			//设置

			//调用综合接口发送通知
			try{
				messageService.createBatchInstationMsg(msgEntity);
			}catch(BusinessException e){
				logger.error(e.getMessage(),e);
				throw new SettlementException("调用综合发送消息接口抛错："+e.getMessage());
			}
			
		}
	}

	private void validaBill(BillWriteoffOperationDto billWriteoffOperationDto,
			CurrentInfo currentInfo, BillRepaymentEntity rep,
			BigDecimal repAmount) {
		Date now = new Date();

		// 循环核销应收单
		for (BillReceivableEntity rec : billWriteoffOperationDto
				.getBillReceivableEntitys()) {
			// 应收金额大于零时，才能用于核销
			if (rec.getUnverifyAmount().compareTo(BigDecimal.ZERO) > 0) {
				// 生成核销单记录
				BillWriteoffEntity billWriteoffEntity = new BillWriteoffEntity();

				// 设置核销单通用值
				setWriteoffEntityValuesForAdd(billWriteoffOperationDto,
						billWriteoffEntity, now, currentInfo);
				// 开始来源单号为还款单号
				billWriteoffEntity.setBeginNo(rep.getRepaymentNo());
				//设置开始和结束id
				billWriteoffEntity.setBeginId(rep.getId());
				billWriteoffEntity.setEndId(rec.getId());
				// 开始来源记账日期
				billWriteoffEntity.setBeginAccountDate(rep.getAccountDate());
				// 目的来源单号为应收单号
				billWriteoffEntity.setEndNo(rec.getReceivableNo());
				// 目的来源记账日期
				billWriteoffEntity.setEndAccountDate(rec.getAccountDate());
				// 核销部门编码为应收单催款部门编码
				billWriteoffEntity.setOrgCode(rec.getDunningOrgCode());
				// 核销部门名称为应收单催款部门名称
				billWriteoffEntity.setOrgName(rec.getDunningOrgName()); // 核销类型为还款冲应收
				billWriteoffEntity
						.setWriteoffType(SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE);
				// 客户信息去应收单客户信息
				billWriteoffEntity.setCustomerCode(rec.getCustomerCode());
				billWriteoffEntity.setCustomerName(rec.getCustomerName());
				// 目的运单号为应收单运单号
				billWriteoffEntity.setEndWaybillNo(rec.getWaybillNo());

				// 当应收未核销金额大于等于还款金额时，核销单金额为还款金额；当应收未核销金额小于还款金额时，核销单金额为应收金额
				if (rec.getUnverifyAmount().compareTo(repAmount) >= 0) {
					billWriteoffEntity.setAmount(repAmount);
				} else {
					billWriteoffEntity.setAmount(rec.getUnverifyAmount());
				}

				// 保存核销单记录
				this.add(billWriteoffEntity);

				// 更新还款金额
				repAmount = repAmount.subtract(billWriteoffEntity.getAmount());

				// 更新应收单记录的已核销金额和未核销金额
				rec.setUnverifyAmount(rec.getUnverifyAmount().subtract(
						billWriteoffEntity.getAmount()));
				rec.setVerifyAmount(rec.getVerifyAmount().add(
						billWriteoffEntity.getAmount()));
				billReceivableService.writeoffBillReceivable(rec,
						billWriteoffEntity.getAmount(), currentInfo);

				// 如果还款金额已经核销为零，则退出循环
				if (repAmount.compareTo(BigDecimal.ZERO) == 0) {
					break;
				}

			} else {
				continue;
			}
		}
	}

	/**
	 * 付款冲应付（代收货款）
	 * 
	 * @author foss-wangxuemin
	 * @date Nov 27, 2012 3:05:11 PM
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService#writeoffPaymentAndPayable(com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void writeoffCODPaymentAndPayable(
			BillWriteoffOperationDto billWriteoffOperationDto,
			CurrentInfo currentInfo) {
		/*
		 * 1 参数校验
		 */
		// 校验通用参数是否为空
		checkParameters(billWriteoffOperationDto, currentInfo);

		// 校验参数中的付款单是否为空
		BillPaymentEntity payment = billWriteoffOperationDto
				.getBillPaymentEntity();
		if (null == payment) {
			throw new SettlementException("付款单为空，无法核销");
		}
		// 校验参数中付款单的金额是否大于零
		BigDecimal paymentAmount = payment.getAmount();
		if (paymentAmount.compareTo(BigDecimal.ZERO) < 1) {
			throw new SettlementException("付款金额小于等于零，无法核销");
		}
		// 校验应付单列表是否为空
		if (CollectionUtils.isEmpty(billWriteoffOperationDto
				.getBillPayableEntitys())) {
			throw new SettlementException("应付单列表为空，无法核销");
		}
		// 校验付款单和应付单金额是否相等
		BigDecimal payTotUnAmount = BigDecimal.ZERO;
		for (BillPayableEntity payable : billWriteoffOperationDto
				.getBillPayableEntitys()) {
			payTotUnAmount = payTotUnAmount.add(payable.getUnverifyAmount());
		}
		if (paymentAmount.compareTo(payTotUnAmount) != 0) {
			throw new SettlementException("付款单金额和应付单未核销金额不相等，无法核销");
		}

		logger.info("付款单号:" + payment.getPaymentNo());

		/*
		 * 2 核销处理
		 */
		// 取系统当前日期
		Date now = new Date();

		// 循环核销应付单
		for (BillPayableEntity payable : billWriteoffOperationDto
				.getBillPayableEntitys()) {
			// 应付金额大于零时，才能用于核销
			if (payable.getUnverifyAmount().compareTo(BigDecimal.ZERO) > 0) {
				// 生成核销单记录
				BillWriteoffEntity billWriteoffEntity = new BillWriteoffEntity();

				// 设置核销单通用值
				setWriteoffEntityValuesForAdd(billWriteoffOperationDto,
						billWriteoffEntity, now, currentInfo);
				// 开始来源单号为付款单号
				billWriteoffEntity.setBeginNo(payment.getPaymentNo());
				//设置开始和结束id
				billWriteoffEntity.setBeginId(payment.getId());
				billWriteoffEntity.setEndId(payable.getId());
				// 开始来源记账日期
				billWriteoffEntity
						.setBeginAccountDate(payment.getAccountDate());
				// 目的来源单号为应付单号
				billWriteoffEntity.setEndNo(payable.getPayableNo());
				// 目的来源记账日期
				billWriteoffEntity.setEndAccountDate(payable.getAccountDate());
				// 核销部门编码为应付单应付部门编码
				billWriteoffEntity.setOrgCode(payable.getPayableOrgCode());
				// 核销部门名称为应付单应付部门名称
				billWriteoffEntity.setOrgName(payable.getPayableOrgName());
				// 核销类型应付款冲应付
				billWriteoffEntity
						.setWriteoffType(SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__PAYMENT_PAYABLE);
				// 客户信息取应付单客户信息
				billWriteoffEntity.setCustomerCode(payable.getCustomerCode());
				billWriteoffEntity.setCustomerName(payable.getCustomerName());
				// 目的运单号为应付单运单号
				billWriteoffEntity.setEndWaybillNo(payable.getWaybillNo());
				// 网点信息取付款单上的付款部门
				billWriteoffEntity.setOrgCode(payment.getPaymentOrgCode());
				billWriteoffEntity.setOrgName(payment.getPaymentOrgName());

				// 当应付未核销金额大于等于预付未核销金额时，核销单金额为预付金额；当应付未核销金额小于预付未核销金额时，核销单金额为应付金额
				if (payable.getUnverifyAmount().compareTo(paymentAmount) >= 0) {
					billWriteoffEntity.setAmount(paymentAmount);
				} else {
					billWriteoffEntity.setAmount(payable.getUnverifyAmount());
				}

				// 保存核销单记录
				this.add(billWriteoffEntity);

				// 更新付款金额
				paymentAmount = paymentAmount.subtract(billWriteoffEntity
						.getAmount());

				// 更新应付单记录的已核销金额和未核销金额
				payable.setUnverifyAmount(payable.getUnverifyAmount().subtract(
						billWriteoffEntity.getAmount()));
				payable.setVerifyAmount(payable.getVerifyAmount().add(
						billWriteoffEntity.getAmount()));
				billPayableService.writeoffBillPayable(payable,
						billWriteoffEntity.getAmount(), currentInfo);

				// 如果付款金额已经核销为零，则退出循环
				if (paymentAmount.compareTo(BigDecimal.ZERO) == 0) {
					break;
				}
			} else {
				continue;
			}
		}
	}

	/**
	 * 付款冲应付（客户付款）
	 * 
	 * @author foss-wangxuemin
	 * @date Dec 4, 2012 2:01:16 PM
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService#writeoffCODPaymentAndPayable(com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void writeoffCustPaymentAndPayable(
			BillWriteoffOperationDto billWriteoffOperationDto,
			CurrentInfo currentInfo) {
		/*
		 * 1 参数校验
		 */
		// 校验通用参数是否为空
		checkParameters(billWriteoffOperationDto, currentInfo);

		// 校验参数中的付款单是否为空
		BillPaymentEntity payment = billWriteoffOperationDto
				.getBillPaymentEntity();
		if (null == payment) {
			throw new SettlementException("付款单为空，无法核销");
		}
		// 校验应付单列表是否为空
		if (CollectionUtils.isEmpty(billWriteoffOperationDto
				.getBillPayableEntitys())) {
			throw new SettlementException("应付单列表为空，无法核销");
		}

		logger.info("付款单号:" + payment.getPaymentNo());

		/*
		 * 2 核销处理
		 */
		// 取系统当前日期
		Date now = new Date();

		// 循环核销应付单
		for (BillPayableEntity payable : billWriteoffOperationDto
				.getBillPayableEntitys()) {
			// 应付单的付款金额大于未核销金额时，不能核销
			if (payable.getPaymentAmount().compareTo(
					payable.getUnverifyAmount()) > 0) {
				throw new SettlementException("应付单中的付款金额大于未核销金额，无法核销");
			}

			// 应付单未核销金额或者付款金额等于零时，不能用于核销
			if (payable.getUnverifyAmount().compareTo(BigDecimal.ZERO) == 0
					|| payable.getPaymentAmount().compareTo(BigDecimal.ZERO) == 0) {
				throw new SettlementException("应付单中的未核销金额或者付款金额为0，无法核销");
			}

			// 生成核销单记录
			BillWriteoffEntity billWriteoffEntity = new BillWriteoffEntity();

			// 设置核销单通用值
			setWriteoffEntityValuesForAdd(billWriteoffOperationDto,
					billWriteoffEntity, now, currentInfo);
			// 开始来源单号为付款单号
			billWriteoffEntity.setBeginNo(payment.getPaymentNo());
			//设置开始和结束id
			billWriteoffEntity.setBeginId(payment.getId());
			billWriteoffEntity.setEndId(payable.getId());
			// 开始来源记账日期
			billWriteoffEntity.setBeginAccountDate(payment.getAccountDate());
			// 目的来源单号为应付单号
			billWriteoffEntity.setEndNo(payable.getPayableNo());
			// 目的来源记账日期
			billWriteoffEntity.setEndAccountDate(payable.getAccountDate());
			// 核销部门编码为应付单应付部门编码
			billWriteoffEntity.setOrgCode(payable.getPayableOrgCode());
			// 核销部门名称为应付单应付部门名称
			billWriteoffEntity.setOrgName(payable.getPayableOrgName());
			// 核销类型应付款冲应付
			billWriteoffEntity
					.setWriteoffType(SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__PAYMENT_PAYABLE);
			// 客户信息去应付单客户信息
			billWriteoffEntity.setCustomerCode(payable.getCustomerCode());
			billWriteoffEntity.setCustomerName(payable.getCustomerName());
			// 目的运单号为应付单运单号
			billWriteoffEntity.setEndWaybillNo(payable.getWaybillNo());
			// 核销单金额为应付单的付款金额
			billWriteoffEntity.setAmount(payable.getPaymentAmount());
			// 网点信息取付款单上的付款部门
			billWriteoffEntity.setOrgCode(payment.getPaymentOrgCode());
			billWriteoffEntity.setOrgName(payment.getPaymentOrgName());

			// 保存核销单记录
			this.add(billWriteoffEntity);

			// 更新应付单记录的已核销金额和未核销金额
			payable.setUnverifyAmount(payable.getUnverifyAmount().subtract(
					billWriteoffEntity.getAmount()));
			payable.setVerifyAmount(payable.getVerifyAmount().add(
					billWriteoffEntity.getAmount()));
			billPayableService.writeoffBillPayable(payable,
					billWriteoffEntity.getAmount(), currentInfo);

		}
	}

	/**
	 * 付款冲预收
	 * 
	 * @author foss-wangxuemin
	 * @date Dec 6, 2012 10:36:27 AM
	 */
	@Override
	public void writeoffCustPaymentAndDeposit(
			BillWriteoffOperationDto billWriteoffOperationDto,
			CurrentInfo currentInfo) {
		/*
		 * 1 参数校验
		 */
		// 校验通用参数是否为空
		checkParameters(billWriteoffOperationDto, currentInfo);

		// 校验参数中的付款单是否为空
		BillPaymentEntity payment = billWriteoffOperationDto
				.getBillPaymentEntity();
		if (null == payment) {
			throw new SettlementException("付款单为空，无法核销");
		}
		// 校验应付单列表是否为空
		if (CollectionUtils.isEmpty(billWriteoffOperationDto
				.getBillDepositReceivedEntitys())) {
			throw new SettlementException("预收单列表为空，无法核销");
		}

		logger.info("付款单号:" + payment.getPaymentNo());

		/*
		 * 2 核销处理
		 */
		// 取系统当前日期
		Date now = new Date();

		// 循环核销预收单
		for (BillDepositReceivedEntity dep : billWriteoffOperationDto
				.getBillDepositReceivedEntitys()) {
			// 预收单的付款金额大于未核销金额时，不能核销
			if (dep.getPaymentAmount().compareTo(dep.getUnverifyAmount()) > 0) {
				throw new SettlementException("预收单中的付款金额大于未核销金额，无法核销");
			}

			// 预收单未核销金额或者付款金额等于零时，不能用于核销
			if (dep.getUnverifyAmount().compareTo(BigDecimal.ZERO) == 0
					|| dep.getPaymentAmount().compareTo(BigDecimal.ZERO) == 0) {
				throw new SettlementException("预收单中的未核销金额或者付款金额为0，无法核销");
			}

			// 生成核销单记录
			BillWriteoffEntity billWriteoffEntity = new BillWriteoffEntity();

			// 设置核销单通用值
			setWriteoffEntityValuesForAdd(billWriteoffOperationDto,
					billWriteoffEntity, now, currentInfo);
			// 开始来源单号为付款单号
			billWriteoffEntity.setBeginNo(payment.getPaymentNo());
			//设置开始和结束id
			billWriteoffEntity.setBeginId(payment.getId());
			billWriteoffEntity.setEndId(dep.getId());
			// 开始来源记账日期
			billWriteoffEntity.setBeginAccountDate(payment.getAccountDate());
			// 目的来源单号为预收单号
			billWriteoffEntity.setEndNo(dep.getDepositReceivedNo());
			// 目的来源记账日期
			billWriteoffEntity.setEndAccountDate(dep.getAccountDate());
			// 核销部门编码为应付单应付部门编码
			billWriteoffEntity.setOrgCode(dep.getGeneratingOrgCode());
			// 核销部门名称为应付单应付部门名称
			billWriteoffEntity.setOrgName(dep.getGeneratingOrgName());
			// 核销类型付款冲预收
			billWriteoffEntity
					.setWriteoffType(SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__PAYABLE_DEPOSIT);
			// 客户信息取预收单客户信息
			billWriteoffEntity.setCustomerCode(dep.getCustomerCode());
			billWriteoffEntity.setCustomerName(dep.getCustomerName());
			// 核销单金额为预收单的付款金额
			billWriteoffEntity.setAmount(dep.getPaymentAmount());

			// 保存核销单记录
			this.add(billWriteoffEntity);

			// 更新预收单记录的已核销金额和未核销金额
			dep.setUnverifyAmount(dep.getUnverifyAmount().subtract(
					billWriteoffEntity.getAmount()));
			dep.setVerifyAmount(dep.getVerifyAmount().add(
					billWriteoffEntity.getAmount()));
			billDepositReceivedService.writeoffBillDepositReceived(dep,
					billWriteoffEntity.getAmount(), currentInfo);

		}
	}

	/**
	 * 坏账冲应收
	 * 
	 * @author foss-wangxuemin
	 * @date Dec 6, 2012 10:36:27 AM
	 */
	@Override
	public void writeoffBadAccountAndReceivable(
			BillWriteoffOperationDto billWriteoffOperationDto, String billType,
			CurrentInfo currentInfo) {
		/*
		 * 1 参数校验
		 */
		// 校验通用参数是否为空
		checkParameters(billWriteoffOperationDto, currentInfo);
		// 校验还款金额是否大于零
		BigDecimal badAmount = billWriteoffOperationDto.getBadWriteoffAmount();
		if (badAmount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new SettlementException("坏账金额小于等于零，无法核销");
		}
		// 校验应收单列表是否为空
		if (CollectionUtils.isEmpty(billWriteoffOperationDto
				.getBillReceivableEntitys())) {
			throw new SettlementException("应收单列表为空，无法核销");
		}
		// 校验还款单和应收单金额是否相等
		BigDecimal recTotUnAmount = BigDecimal.ZERO;
		for (BillReceivableEntity rec : billWriteoffOperationDto
				.getBillReceivableEntitys()) {
			recTotUnAmount = recTotUnAmount.add(rec.getUnverifyAmount());
		}
		if (badAmount.compareTo(recTotUnAmount) > 0) {
			throw new SettlementException("坏账金额大于应收单未核销金额，无法核销");
		}

		/*
		 * 2 核销处理
		 */
		// 取系统当前日期
		Date now = new Date();

		// 循环核销应收单
		for (BillReceivableEntity rec : billWriteoffOperationDto
				.getBillReceivableEntitys()) {
			// 应收金额大于零时，才能用于核销
			if (rec.getUnverifyAmount().compareTo(BigDecimal.ZERO) > 0) {
				/*
				 * 生成坏账单记录
				 */
				BillBadAccountEntity billBadAccountEntity = new BillBadAccountEntity();
				billBadAccountEntity.setId(UUIDUtils.getUUID());
				// 生成新的坏账单号
				String badDebatBillNo = settlementCommonService
						.getSettlementNoRule(SettlementNoRuleEnum.HZ);
				billBadAccountEntity.setBadDebatBillNo(badDebatBillNo);
				billBadAccountEntity.setReceivableNo(rec.getReceivableNo());
				billBadAccountEntity.setWaybillNo(rec.getWaybillNo());
				billBadAccountEntity.setSourceBillType(rec.getSourceBillType());
				billBadAccountEntity.setCreateTime(now);
				// 当应收未核销金额大于等于坏账金额时，核销单金额为坏账金额；当应收未核销金额小于坏账金额时，核销单金额为应收金额
				BigDecimal writeAmount = BigDecimal.ZERO;
				if (rec.getUnverifyAmount().compareTo(badAmount) >= 0) {
					writeAmount = badAmount;
				} else {
					writeAmount = rec.getUnverifyAmount();
				}
				billBadAccountEntity.setBadAmount(writeAmount);
				billBadAccountEntity
						.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
				billBadAccountEntity.setBillType(billType);
				billBadAccountService.add(billBadAccountEntity);

				/*
				 * 生成核销单记录
				 */
				BillWriteoffEntity billWriteoffEntity = new BillWriteoffEntity();
				// 设置核销单通用值
				setWriteoffEntityValuesForAdd(billWriteoffOperationDto,
						billWriteoffEntity, now, currentInfo);
				// 开始来源单号为还坏账单
				billWriteoffEntity.setBeginNo(billBadAccountEntity
						.getBadDebatBillNo());
				//设置开始和结束id
				billWriteoffEntity.setBeginId(billBadAccountEntity.getId());
				billWriteoffEntity.setEndId(rec.getId());
				// 开始来源记账日期
				billWriteoffEntity.setBeginAccountDate(billBadAccountEntity
						.getCreateTime());
				// 目的来源单号为应收单号
				billWriteoffEntity.setEndNo(rec.getReceivableNo());
				// 目的来源记账日期
				billWriteoffEntity.setEndAccountDate(rec.getAccountDate());
				// 核销部门编码为应收单催款部门编码
				billWriteoffEntity.setOrgCode(rec.getDunningOrgCode());
				// 核销部门名称为应收单催款部门名称
				billWriteoffEntity.setOrgName(rec.getDunningOrgName());
				// 核销类型为坏账冲应收
				billWriteoffEntity
						.setWriteoffType(SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__BAD_RECEIVABLE);
				// 客户信息去应收单客户信息
				billWriteoffEntity.setCustomerCode(rec.getCustomerCode());
				billWriteoffEntity.setCustomerName(rec.getCustomerName());
				// 目的运单号为应收单运单号
				billWriteoffEntity.setEndWaybillNo(rec.getWaybillNo());
				// 核销金额
				billWriteoffEntity.setAmount(writeAmount);

				// 保存核销单记录
				this.add(billWriteoffEntity);

				// 更新还款金额
				badAmount = badAmount.subtract(billWriteoffEntity.getAmount());

				// 更新应收单记录的已核销金额和未核销金额
				rec.setUnverifyAmount(rec.getUnverifyAmount().subtract(
						billWriteoffEntity.getAmount()));
				rec.setVerifyAmount(rec.getVerifyAmount().add(
						billWriteoffEntity.getAmount()));
				billReceivableService.writeoffBillReceivable(rec,
						billWriteoffEntity.getAmount(), currentInfo);

				// 如果还款金额已经核销为零，则退出循环
				if (badAmount.compareTo(BigDecimal.ZERO) == 0) {
					break;
				}

			} else {
				continue;
			}
		}
	}

	/**
	 * 校验核销参数
	 * 
	 * @author foss-wangxuemin
	 * @date Nov 1, 2012 11:37:35 AM
	 */
	private void checkParameters(
			BillWriteoffOperationDto billWriteoffOperationDto,
			CurrentInfo currentInfo) {
		// 传入参数不能为空
		if (null == billWriteoffOperationDto) {
			throw new SettlementException("核销参数为空，无法核销");
		}

		// 核销批次号不能为空
		if (StringUtils.isEmpty(billWriteoffOperationDto.getWriteoffBatchNo())) {
			throw new SettlementException("核销批次号不能为空");
		}

		// 核销方式不能为空
		if (StringUtils.isEmpty(billWriteoffOperationDto.getCreateType())) {
			throw new SettlementException("核销方式不能为空");
		}

		// 登录员工信息不能为空
		if (null == currentInfo) {
			throw new SettlementException("登录员工信息为空");
		}

		// 核销员工编号不能为空
		if (StringUtils.isEmpty(currentInfo.getEmpCode())) {
			throw new SettlementException("核销员工编号不能为空");
		}

		// 核销员工名称不能为空
		if (StringUtils.isEmpty(currentInfo.getEmpName())) {
			throw new SettlementException("核销员工名称不能为空");
		}

		// 核销网点编号不能为空
		if (StringUtils.isEmpty(currentInfo.getCurrentDeptCode())) {
			throw new SettlementException("核销网点编号不能为空");
		}

		// 核销网点名称不能为空
		if (StringUtils.isEmpty(currentInfo.getCurrentDeptName())) {
			throw new SettlementException("核销网点名称不能为空");
		}
	}

	/**
	 * 新增核销单时，设置核销单的一些通用变量值
	 * 
	 * @author foss-wangxuemin
	 * @date Nov 5, 2012 6:46:48 PM
	 */
	private void setWriteoffEntityValuesForAdd(
			BillWriteoffOperationDto billWriteoffOperationDto,
			BillWriteoffEntity billWriteoffEntity, Date now,
			CurrentInfo currentInfo) {
		// 从参数中复制核销批次号、核销用户编号、核销用户名称、核销网点编号、核销网点名称、核销方式
		BeanUtils.copyProperties(billWriteoffOperationDto, billWriteoffEntity);

		// 设置主键
		billWriteoffEntity.setId(UUIDUtils.getUUID());

		// 生成新的核销单号
		String writeoffBillNo = settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.HX);
		billWriteoffEntity.setWriteoffBillNo(writeoffBillNo);

		// 设置核销员工信息
		billWriteoffEntity.setCreateUserCode(currentInfo.getEmpCode());
		billWriteoffEntity.setCreateUserName(currentInfo.getEmpName());

		// 设置为生效
		billWriteoffEntity.setActive(FossConstants.ACTIVE);

		// 设置为非红单
		billWriteoffEntity
				.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);

		// 设置核销时间
		billWriteoffEntity.setWriteoffTime(now);

		// 设置记账日期
		billWriteoffEntity.setAccountDate(now);

		// 设置初始版本号
		billWriteoffEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
	}

	/**
	 * 对核销参数中的核销方列表进行排序
	 * 
	 * @author foss-wangxuemin
	 * @date Nov 7, 2012 10:50:29 AM
	 */
	private void sortWriteoffOperationList(
			BillWriteoffOperationDto billWriteoffOperationDto) {
		if (null != billWriteoffOperationDto) {
			// 排序类，所有核销列表按照业务日期排序
			ListComparator orderComparator = new ListComparator("businessDate");

			// 按时间排序，预收收单核销时按业务时间先后排序
			List<BillDepositReceivedEntity> deps = billWriteoffOperationDto
					.getBillDepositReceivedEntitys();
			if (CollectionUtils.isNotEmpty(deps)) {
				Collections.sort(deps, orderComparator);
			}

			// 按时间排序，应收单核销时按业务时间先后排序
			List<BillReceivableEntity> recs = billWriteoffOperationDto
					.getBillReceivableEntitys();
			if (CollectionUtils.isNotEmpty(recs)) {
				Collections.sort(recs, orderComparator);
			}

			// 按时间排序，预付单核销时按业务时间先后排序
			List<BillAdvancedPaymentEntity> advs = billWriteoffOperationDto
					.getBillAdvancedPaymentEntitys();
			if (CollectionUtils.isNotEmpty(advs)) {
				Collections.sort(advs, orderComparator);
			}

			// 按时间排序，应付单核销时按业务时间先后排序
			List<BillPayableEntity> pays = billWriteoffOperationDto
					.getBillPayableEntitys();
			if (CollectionUtils.isNotEmpty(pays)) {
				Collections.sort(pays, orderComparator);
			}
		}
	}

	/**
	 * @return billWriteoffEntityDao
	 */
	public IBillWriteoffEntityDao getBillWriteoffEntityDao() {
		return billWriteoffEntityDao;
	}

	/**
	 * @param billWriteoffEntityDao
	 */
	public void setBillWriteoffEntityDao(
			IBillWriteoffEntityDao billWriteoffEntityDao) {
		this.billWriteoffEntityDao = billWriteoffEntityDao;
	}

	/**
	 * @param settlementCommonService
	 */
	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	/**
	 * @param billReceivableService
	 */
	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
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
	public void setBillDepositReceivedService(
			IBillDepositReceivedService billDepositReceivedService) {
		this.billDepositReceivedService = billDepositReceivedService;
	}

	/**
	 * @param billRepaymentService
	 */
	public void setBillRepaymentService(
			IBillRepaymentService billRepaymentService) {
		this.billRepaymentService = billRepaymentService;
	}

	/**
	 * @param billAdvancedPaymentService
	 */
	public void setBillAdvancedPaymentService(
			IBillAdvancedPaymentService billAdvancedPaymentService) {
		this.billAdvancedPaymentService = billAdvancedPaymentService;
	}

	/**
	 * @param billBadAccountService
	 */
	public void setBillBadAccountService(
			IBillBadAccountService billBadAccountService) {
		this.billBadAccountService = billBadAccountService;
	}

	/**
	 * @param messageService
	 */
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}
}
