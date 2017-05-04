package com.deppon.foss.module.settlement.agency.server.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPackagingSupplierService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.agency.api.server.dao.IPackingRecAndPayInputDao;
import com.deppon.foss.module.settlement.agency.api.server.service.IPackingRecAndPayInputService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.PackingRecAndPayInputDto;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.service.impl.SettlementCommonService;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 包装其他应收应付录入 service
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:105762,date:2014-6-6 上午9:06:56,content:
 * </p>
 * @author 105762
 * @date 2014-6-6 上午9:06:56
 * @since 1.6
 */
public class PackingRecAndPayInputService implements IPackingRecAndPayInputService, Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 配置参数service
	 */
	private IConfigurationParamsService configurationParamsService;
	/**
	 * 应付单service
	 */
	private IBillPayableService billPayableService;
	/**
	 * 应收单service
	 */
	private IBillReceivableService billReceivableService;

	/**
	 * 部门信息service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * settlementCommonService
	 */
	private SettlementCommonService settlementCommonService;

	/**
	 * dao
	 */
	private IPackingRecAndPayInputDao packingRecAndPayInputDao;

	/**
	 * 查询包装供应商
	 */
	private IPackagingSupplierService packagingSupplierService;

	/**
	 * 运单基本信息
	 */
	private IWaybillManagerService waybillManagerService;

	/**
	 * <p>
	 * 包装其它应收应付录入
	 * </p>
	 * 
	 * @author 105762
	 * @date 2014-5-16 下午5:23:49
	 * @return
	 * @see com.deppon.foss.module.settlement.agency.api.server.service.IPackingRecAndPayInputService#input()
	 */
	@Override
	@Transactional
	public void input(PackingRecAndPayInputDto dto, CurrentInfo currentInfo) {
		// 校验
		validate(dto, currentInfo);
		// 预处理
		preprocess(dto);

		// 存储单号对应的生成年月记录 map
		Map<String, String> map = new HashMap<String, String>();
		map.put("occurMonth", dto.getBillTime());

		// 创建实体 保存
		if (dto.getAmount().compareTo(BigDecimal.ZERO) > 0) {
			BillReceivableEntity recEntity = buildRecEntity(dto, currentInfo);
			billReceivableService.addBillReceivable(recEntity, currentInfo);
			// 单号放入map
			map.put("billNo", recEntity.getReceivableNo());
		} else {
			BillPayableEntity payEntity = buildPayEntity(dto, currentInfo);
			billPayableService.addBillPayable(payEntity, currentInfo);
			// 单号放入map
			map.put("billNo", payEntity.getPayableNo());
		}
		// 存储单号对应的生成年月记录
		packingRecAndPayInputDao.addPackingBillTime(map);
	}

	/**
	 * <p>
	 * 创建应付单实体
	 * </p>
	 * 
	 * @author 105762
	 * @date 2014-5-29 上午10:05:45
	 * @param dto
	 * @param currentInfo
	 * @return payEntity
	 */
	private BillPayableEntity buildPayEntity(PackingRecAndPayInputDto dto, CurrentInfo currentInfo) {
		// 非空校验
		SettlementUtil.valideIsNull(dto, "预处理时dto为空");
		SettlementUtil.valideIsNull(currentInfo, "预处理时currentInfo为空");

		// 应付单实体
		BillPayableEntity billPayableEntity = new BillPayableEntity();

		// 包装其他应付单号,应付单单号系统自动生成，生成规则“YF96+8位流水号”
		billPayableEntity.setPayableNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YF96));
		// 运单号
        billPayableEntity.setWaybillNo(StringUtils.isBlank(dto.getWaybillNo()) ? SettlementConstants.DEFAULT_BILL_NO : dto.getWaybillNo());
        // 来源单号
		billPayableEntity.setSourceBillNo(billPayableEntity.getPayableNo().substring(2));
		// 来源单据类型
		billPayableEntity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__WAYBILL);

		// 破损奖励
		if (SettlementConstants.PACKING_REC_AND_PAY_INPUT_TYPE__DAMAGERATE.equals(dto.getInputType())) {
			billPayableEntity.setPayableType(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__DAMAGE_PAKING_REWARD);
			// 时效奖励
		} else if (SettlementConstants.PACKING_REC_AND_PAY_INPUT_TYPE__AGING.equals(dto.getInputType())) {
			billPayableEntity.setPayableType(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__AGING_REWARD);
			// 面板
		} else if (SettlementConstants.PACKING_REC_AND_PAY_INPUT_TYPE__BOARD.equals(dto.getInputType())) {
			billPayableEntity.setPayableType(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__BOARD);
			// 木方
		} else if (SettlementConstants.PACKING_REC_AND_PAY_INPUT_TYPE__WOODENSTAND.equals(dto.getInputType())) {
			billPayableEntity.setPayableType(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__WOODEN_STAND);
			// 托盘
		} else if (SettlementConstants.PACKING_REC_AND_PAY_INPUT_TYPE__TRAY.equals(dto.getInputType())) {
			billPayableEntity.setPayableType(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__TRAY);
			// 包装托盘
		} else if (SettlementConstants.PACKING_REC_AND_PAY_INPUT_TYPE__PACKINGTRAY.equals(dto.getInputType())) {
			billPayableEntity.setPayableType(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__PACKING_TRAY);
			// 系统与实际差异单号
		} else if (SettlementConstants.PACKING_REC_AND_PAY_INPUT_TYPE__INCONSISTENTWAYBILL.equals(dto.getInputType())) {
			billPayableEntity.setPayableType(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__INCONSISTENT_WAYBILL);
		} else {
			throw new SettlementException("生成应付单时输入类型错误");
		}

		// 获取dto中的金额
		BigDecimal amount = dto.getAmount();
		// 判断金额是否为空
		if (amount == null) {
			throw new SettlementException("内部异常，传入应付金额参数为空，不能新增快递代理其他应付信息!");
		}
		// 处理amount 原为负值 转换成其绝对值
		amount = amount.abs();

		// 判断金额的最大最小值
		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new SettlementException("包装其他应收应付录入金额不能小于零");
		}
		if (amount.compareTo(SettlementConstants.BILL_MAX_AMOUNT) > 0) {
			throw new SettlementException("包装其他应收应付录入金额超过结算最大允许金额");
		}
		// 金额
		billPayableEntity.setAmount(amount);
		// 未核销金额
		billPayableEntity.setUnverifyAmount(amount);
		// 核销金额
		billPayableEntity.setVerifyAmount(BigDecimal.ZERO);

		// 客户信息
		billPayableEntity.setCustomerName(dto.getCustomerName());
		billPayableEntity.setCustomerCode(dto.getCustomerCode());

		// 设置id
		billPayableEntity.setId(UUIDUtils.getUUID());

		// 系统生成方式
		billPayableEntity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);

		// 单据子类型
		billPayableEntity.setBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__WOODEN_OTEHR_PAYABLE);

		// 设置审核状态为未审核
		billPayableEntity.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__NOT_AUDIT);

		// 获取当前选择的应付部门组织的的实体信息
		OrgAdministrativeInfoEntity payOrgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(dto.getDeptCode());
		// 判断组织信息是否为空
		if (payOrgEntity == null) {
			// 当前选择的应付部门组织信息为空异常
			throw new SettlementException("当前选择的应付部门组织信息为空！");
		}

		// 应付部门
		billPayableEntity.setPayableOrgCode(payOrgEntity.getCode());
		billPayableEntity.setPayableOrgName(payOrgEntity.getName());

		// 出发到达部门
		billPayableEntity.setOrigOrgCode(payOrgEntity.getCode());
		billPayableEntity.setOrigOrgName(payOrgEntity.getName());
		billPayableEntity.setDestOrgCode(payOrgEntity.getCode());
		billPayableEntity.setDestOrgName(payOrgEntity.getName());

		// 设置应付部门子公司code
		billPayableEntity.setPayableComCode(payOrgEntity.getSubsidiaryCode());
		// 设置应付部门子公司name
		billPayableEntity.setPayableComName(payOrgEntity.getSubsidiaryName());

		// 生效状态不能为空
		billPayableEntity.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES);

		// 支付状态不能为空
		billPayableEntity.setPayStatus(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO);

		// 冻结状态不能为空
		billPayableEntity.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__NOT_FROZEN);

		// 设置快递代理其他应付管理的币种
		billPayableEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);

		// 业务日期、记账日期为系统当前时间；创建时间默认为系统当前时间
		Date now = new Date();
		// 业务时间
		billPayableEntity.setBusinessDate(now);
		// 记账时间
		billPayableEntity.setAccountDate(now);

		// 应付单单据默认为有效的单据
		billPayableEntity.setActive(FossConstants.ACTIVE);
		// 应付单单据默认为非红单
		billPayableEntity.setIsRedBack(FossConstants.NO);
		// 应付单单据默认为非初始化
		billPayableEntity.setIsInit(FossConstants.NO);
		// 版本号
		billPayableEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
		// 默认单号
		billPayableEntity.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);
		billPayableEntity.setPaymentNo(SettlementConstants.DEFAULT_BILL_NO);

		// 运输性质
		billPayableEntity.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);

		// 发票标记
		billPayableEntity.setInvoiceMark(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO);

		return billPayableEntity;
	}

	/**
	 * <p>
	 * 创建应收单实体
	 * </p>
	 * 
	 * @author 105762
	 * @date 2014-5-29 上午10:05:38
	 * @param dto
	 * @param currentInfo
	 * @return recEntity
	 */
	private BillReceivableEntity buildRecEntity(PackingRecAndPayInputDto dto, CurrentInfo currentInfo) {
		SettlementUtil.valideIsNull(dto, "预处理时dto为空");
		BillReceivableEntity billReceivableEntity = new BillReceivableEntity();

		// 获取当前选择的应付部门组织的的实体信息
		OrgAdministrativeInfoEntity recOrgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(dto.getDeptCode());

		// 设置id
		billReceivableEntity.setId(UUIDUtils.getUUID());
		// 设置应收单号,应收单单号系统自动生成，生成规则“YS96+8位流水号”
		billReceivableEntity.setReceivableNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YS96));

		// 来源单据编号
		billReceivableEntity.setSourceBillNo(billReceivableEntity.getReceivableNo().substring(2));
		// 来源类型
		billReceivableEntity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__WAYBILL);

		// 来源单据类型 手工输入
		billReceivableEntity.setSourceBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__MANUAL);

		// 客户信息
		billReceivableEntity.setCustomerName(dto.getCustomerName());
		billReceivableEntity.setCustomerCode(dto.getCustomerCode());

		// 运单号 破损理赔可能有运单号
		billReceivableEntity.setWaybillNo(StringUtils.isBlank(dto.getWaybillNo()) ? SettlementConstants.DEFAULT_BILL_NO : dto.getWaybillNo());

		// 支付方式 电汇
		billReceivableEntity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER);

		// 设置系统生成方式
		billReceivableEntity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);

		// 设置单据子类型 代打木架爱其它应收
		billReceivableEntity.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__WOODEN_OTEHR_RECEIVABLE);

		// 包装其它应收收款类别
		if (SettlementConstants.PACKING_REC_AND_PAY_INPUT_TYPE__DAMAGERATE.equals(dto.getInputType())) {
			// 破损率奖罚
			billReceivableEntity.setCollectionType(SettlementDictionaryConstants.BILL_RECEIVABLE__COLLECTION_TYPE__DAMAGE_PAKING_FORFEIT);
		} else if (SettlementConstants.PACKING_REC_AND_PAY_INPUT_TYPE__AGING.equals(dto.getInputType())) {
			// 时效奖罚金额
			billReceivableEntity.setCollectionType(SettlementDictionaryConstants.BILL_RECEIVABLE__COLLECTION_TYPE__AGING_FORFEIT);
		} else if (SettlementConstants.PACKING_REC_AND_PAY_INPUT_TYPE__DAMAGECLAIM.equals(dto.getInputType())) {
			// 破损理赔处罚
			billReceivableEntity.setCollectionType(SettlementDictionaryConstants.BILL_RECEIVABLE__COLLECTION_TYPE__DAMAGE_CLAIM_FORFEIT);
		} else if (SettlementConstants.PACKING_REC_AND_PAY_INPUT_TYPE__FORKLIFTTICKET.equals(dto.getInputType())) {
			// 叉车票金额
			billReceivableEntity.setCollectionType(SettlementDictionaryConstants.BILL_RECEIVABLE__COLLECTION_TYPE__FORKLIFT_TICKET);
		} else if (SettlementConstants.PACKING_REC_AND_PAY_INPUT_TYPE__MIXING.equals(dto.getInputType())) {
			// 混打处罚金额
			billReceivableEntity.setCollectionType(SettlementDictionaryConstants.BILL_RECEIVABLE__COLLECTION_TYPE__MIXING_FORFEIT);
		} else if (SettlementConstants.PACKING_REC_AND_PAY_INPUT_TYPE__LOSING.equals(dto.getInputType())) {
			// 丢货处罚金额
			billReceivableEntity.setCollectionType(SettlementDictionaryConstants.BILL_RECEIVABLE__COLLECTION_TYPE__LOSING_FORFEIT);
		} else if (SettlementConstants.PACKING_REC_AND_PAY_INPUT_TYPE__COMPLAINT.equals(dto.getInputType())) {
			// 投诉处罚金额
			billReceivableEntity.setCollectionType(SettlementDictionaryConstants.BILL_RECEIVABLE__COLLECTION_TYPE__COMPLAINT_FORFEIT);
		} else if (SettlementConstants.PACKING_REC_AND_PAY_INPUT_TYPE__BATTENCHECK.equals(dto.getInputType())) {
			// 木条验收处罚金额
			billReceivableEntity.setCollectionType(SettlementDictionaryConstants.BILL_RECEIVABLE__COLLECTION_TYPE__BATTEN_CHECK_FORFEIT);
		} else if (SettlementConstants.PACKING_REC_AND_PAY_INPUT_TYPE__RECOTHER.equals(dto.getInputType())) {
			// 应收其他金额
			billReceivableEntity.setCollectionType(SettlementDictionaryConstants.BILL_RECEIVABLE__COLLECTION_TYPE__RECEIVE_OTHER);
		} else if (SettlementConstants.PACKING_REC_AND_PAY_INPUT_TYPE__INCONSISTENTWAYBILL.equals(dto.getInputType())) {
			// 系统与实际差异单号
			billReceivableEntity.setCollectionType(SettlementDictionaryConstants.BILL_RECEIVABLE__COLLECTION_TYPE__INCONSISTENTWAYBILL);
		} else {
			throw new SettlementException("生成包装其他应收单时收款类别不存在");
		}

		// 催款部门
		billReceivableEntity.setDunningOrgCode(recOrgEntity.getCode());
		billReceivableEntity.setDunningOrgName(recOrgEntity.getName());

		// 设置应收部门编码
		billReceivableEntity.setReceivableOrgCode(recOrgEntity.getCode());
		// 设置应收部门名称
		billReceivableEntity.setReceivableOrgName(recOrgEntity.getName());

		// 出发到达部门
		billReceivableEntity.setOrigOrgCode(recOrgEntity.getCode());
		billReceivableEntity.setOrigOrgName(recOrgEntity.getName());
		billReceivableEntity.setDestOrgCode(recOrgEntity.getCode());
		billReceivableEntity.setDestOrgName(recOrgEntity.getName());

		// 设置部门收入部门编码
		billReceivableEntity.setGeneratingOrgCode(recOrgEntity.getCode());
		// 设置部门收入部门名称
		billReceivableEntity.setGeneratingOrgName(recOrgEntity.getName());
		// 当前登录用户操作公司code
		billReceivableEntity.setGeneratingComCode(recOrgEntity.getSubsidiaryCode());
		// 当前登录用户操作公司name
		billReceivableEntity.setGeneratingComName(recOrgEntity.getSubsidiaryName());

		// 判断应收的最大最小值
		if (dto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
			throw new SettlementException("包装其他应收应付录入金额不能小于零");
		}
		if (dto.getAmount().compareTo(SettlementConstants.BILL_MAX_AMOUNT) > 0) {
			throw new SettlementException("包装其他应收应付录入金额超过结算最大允许金额");
		}
		// 金额
		billReceivableEntity.setAmount(dto.getAmount());
		// 未核销金额
		billReceivableEntity.setUnverifyAmount(dto.getAmount());
		// 核销金额
		billReceivableEntity.setVerifyAmount(BigDecimal.ZERO);

		// 设置审核状态为未审核
		billReceivableEntity.setApproveStatus(SettlementDictionaryConstants.NOTE_APPLICATION__APPROVE_STATUS__NOT_AUDIT);

		// 设置币种
		billReceivableEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);

		// 业务日期、记账日期为系统当前时间；创建时间默认为系统当前时间
		Date now = new Date();
		// 设置业务时间
		billReceivableEntity.setBusinessDate(now);

		// 设置记账时间
		billReceivableEntity.setAccountDate(now);

		// 默认为有效的单据
		billReceivableEntity.setActive(FossConstants.ACTIVE);
		// 默认为非红单的单据
		billReceivableEntity.setIsRedBack(FossConstants.NO);
		// 非初始化的单据
		billReceivableEntity.setIsInit(FossConstants.NO);
		// 版本号
		billReceivableEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
		// 默认单号
		billReceivableEntity.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);

		// 产品类型
		billReceivableEntity.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);
		// 发票标记
		billReceivableEntity.setInvoiceMark(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO);

		return billReceivableEntity;
	}

	/**
	 * <p>
	 * 预处理dto
	 * </p>
	 * 
	 * @author 105762
	 * @date 2014-5-29 上午9:00:38
	 * @param dto
	 */
	private void preprocess(PackingRecAndPayInputDto dto) {

		// 破损率奖罚
		if (SettlementConstants.PACKING_REC_AND_PAY_INPUT_TYPE__DAMAGERATE.equals(dto.getInputType())) {
			// {(破损率—破损率参数)/0.01%}*1000
			BigDecimal amount = new BigDecimal((dto.getDamageRate() - dto.getStandardDamageRate()) * 10000000d);
			if (amount.compareTo(dto.getPackingDamageMaxAmount()) > 0) {
				amount = dto.getPackingDamageMaxAmount();
			}
			if (amount.compareTo(dto.getPackingDamageMaxAmount().negate()) < 0) {
				amount = dto.getPackingDamageMaxAmount().negate();
			}
			dto.setAmount(amount);
		}
		// 破损投诉处罚
		if (SettlementConstants.PACKING_REC_AND_PAY_INPUT_TYPE__DAMAGECLAIM.equals(dto.getInputType())) {
			dto.setWaybillNo(dto.getWaybillNo().trim());
		}
	}

	/**
	 * <p>
	 * 校验参数
	 * </p>
	 * 
	 * @author 105762
	 * @date 2014-5-22 下午4:37:30
	 * @param dto
	 * @param currentInfo
	 */
	private void validate(PackingRecAndPayInputDto dto, CurrentInfo currentInfo) {
		validaPacking(dto, currentInfo);

		// 破损率奖罚
		if (SettlementConstants.PACKING_REC_AND_PAY_INPUT_TYPE__DAMAGERATE.equals(dto.getInputType())) {
			validaPack(dto);
		} else if (SettlementConstants.PACKING_REC_AND_PAY_INPUT_TYPE__INCONSISTENTWAYBILL.equals(dto.getInputType())) {
			validaInput(dto);
		} else if (SettlementConstants.PACKING_REC_AND_PAY_INPUT_TYPE__DAMAGECLAIM.equals(dto.getInputType())) {
			validaPay(dto);
		} else if (validaRec(dto)) { // 应收其他

			BigDecimal amount = dto.getAmount();
			SettlementUtil.valideIsNull(amount, "包装其他应收应付录入金额不存在");
			if (amount.compareTo(BigDecimal.ZERO) <= 0) {
				throw new SettlementException("包装其他应收应付录入金额不能小于零");
			}
			// 对生成应付单的类别做校验
		} else if (validaType(dto)) {// 包装托盘

			BigDecimal amount = dto.getAmount();
			SettlementUtil.valideIsNull(amount, "包装其他应收应付录入金额不存在");
			if (amount.compareTo(BigDecimal.ZERO) >= 0) {
				throw new SettlementException("包装其他应收应付录入金额不能大于零");
			}
		} else {
			throw new SettlementException("包装其他应收应付录入类别错误");
		}
	}

	private boolean validaType(PackingRecAndPayInputDto dto) {
		return SettlementConstants.PACKING_REC_AND_PAY_INPUT_TYPE__BOARD.equals(dto.getInputType()) || // 面板
				SettlementConstants.PACKING_REC_AND_PAY_INPUT_TYPE__WOODENSTAND.equals(dto.getInputType()) || // 木方
				SettlementConstants.PACKING_REC_AND_PAY_INPUT_TYPE__TRAY.equals(dto.getInputType()) || // 托盘
				SettlementConstants.PACKING_REC_AND_PAY_INPUT_TYPE__PACKINGTRAY.equals(dto.getInputType());
	}

	private void validaPay(PackingRecAndPayInputDto dto) {
		BigDecimal amount = dto.getAmount();
		SettlementUtil.valideIsNull(amount, "包装其他应收应付录入金额不存在");
		SettlementUtil.valideIsNull(dto.getWaybillNo(), "破损理赔处罚录入需填写运单号");

		// 根据运单号查询运单
		if (StringUtils.isNotBlank(dto.getWaybillNo())) {
			WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(dto.getWaybillNo());
			if (waybill == null) {
				throw new SettlementException("运单号为:" + dto.getWaybillNo() + "的运单在系统中不存在");
			}
		}

		if (amount.compareTo(BigDecimal.ZERO) == 0) {
			throw new SettlementException("包装其他应收应付录入金额为零");
		}
		if (amount.compareTo(SettlementConstants.BILL_MAX_AMOUNT) > 0) {
			throw new SettlementException("包装其他应收应付录入金额超过最大允许金额");
		}

		// 对生成应收单的类别做校验
	}

	private void validaPack(PackingRecAndPayInputDto dto) {
		// 破损率小于零
		if (dto.getDamageRate() < 0) {
			throw new SettlementException("录入的破损率小于 0%");
		}
		// 破损率大于1
		if (dto.getDamageRate() > 1) {
			throw new SettlementException("录入的破损率大于 100%");
		}
		// 破损率等于破损率参数
		if (dto.getDamageRate().equals(dto.getStandardDamageRate())) {
			throw new SettlementException("录入的破损率与破损率参数相等,不生成相关包装费其他应收应付单据");
		}
		// 系统与实际差异单号
	}

	private void validaInput(PackingRecAndPayInputDto dto) {
		BigDecimal amount = dto.getAmount();
		SettlementUtil.valideIsNull(amount, "包装其他应收应付录入金额不存在");
		if (amount.compareTo(BigDecimal.ZERO) == 0) {
			throw new SettlementException("包装其他应收应付录入金额不能等于零");
		}

		// 根据运单号查询运单
		if (StringUtils.isNotBlank(dto.getWaybillNo())) {
			WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(dto.getWaybillNo());
			if (waybill == null) {
				throw new SettlementException("运单号为:" + dto.getWaybillNo() + "的运单在系统中不存在");
			}
		} else {
		    throw new SettlementException("请输入运单号");
		}

		// 破损理赔处罚
	}

	private boolean validaRec(PackingRecAndPayInputDto dto) {
		return SettlementConstants.PACKING_REC_AND_PAY_INPUT_TYPE__FORKLIFTTICKET.equals(dto.getInputType()) || // 叉车票金额
				SettlementConstants.PACKING_REC_AND_PAY_INPUT_TYPE__MIXING.equals(dto.getInputType()) || // 混打处罚金额
				SettlementConstants.PACKING_REC_AND_PAY_INPUT_TYPE__LOSING.equals(dto.getInputType()) || // 丢货处罚金额
				SettlementConstants.PACKING_REC_AND_PAY_INPUT_TYPE__COMPLAINT.equals(dto.getInputType()) || // 投诉处罚金额
				SettlementConstants.PACKING_REC_AND_PAY_INPUT_TYPE__BATTENCHECK.equals(dto.getInputType()) || // 木条验收处罚金额
				SettlementConstants.PACKING_REC_AND_PAY_INPUT_TYPE__AGING.equals(dto.getInputType()) || // 时效奖罚金额
				SettlementConstants.PACKING_REC_AND_PAY_INPUT_TYPE__RECOTHER.equals(dto.getInputType());
	}

	private void validaPacking(PackingRecAndPayInputDto dto,
			CurrentInfo currentInfo) {
		SettlementUtil.valideIsNull(dto, "参数Dto为空");
		SettlementUtil.valideIsNull(currentInfo, "用户信息currentInfo为空");
		SettlementUtil.valideIsNull(dto.getCustomerCode(), "客户编码为空");
		SettlementUtil.valideIsNull(dto.getCustomerName(), "客户名称为空");
		SettlementUtil.valideIsNull(dto.getDeptCode(), "部门编码为空");
		SettlementUtil.valideIsNull(dto.getBillTime(), "录入时间为空");
		SettlementUtil.valideIsNull(dto.getAmount(), "录入金额为空");

		// 初始化破损最大奖罚金额
		String confValue = configurationParamsService.queryConfValueByCode(ConfigurationParamsConstants.STL_PACKING_DAMAGE_MAX_AMOUNT);
		if (StringUtils.isBlank(confValue)) {
			throw new SettlementException("获取破损率参数失败,请联系管理员配置系统参数");
		} else {
			dto.setPackingDamageMaxAmount(new BigDecimal(confValue));
		}
		// 获取破损率参数
		PackagingSupplierEntity en = packagingSupplierService.queryPackagingSupplierByEntity(dto.getDeptCode(), dto.getCustomerCode());
		SettlementUtil.valideIsNull(en, "未查询到当前部门对应包装供应商基础资料");
		if (StringUtils.isNotEmpty(en.getBreakageRate())) {
			dto.setStandardDamageRate(Double.parseDouble(en.getBreakageRate()));
		}

		if (BigDecimal.ZERO.equals(dto.getAmount())) {
			throw new SettlementException("录入金额为零,不生成相关包装费其他应收应付单据");
		}

		if (SettlementConstants.BILL_MAX_AMOUNT.compareTo(dto.getAmount().abs()) < 0) {
			throw new SettlementException("包装其他应收应付录入金额超过最大允许金额范围");
		}
	}

	/**
	 * @param configurationParamsService
	 *            the configurationParamsService to set
	 */
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	/**
	 * @param billPayableService
	 *            the billPayableService to set
	 */
	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	/**
	 * @param billReceivableService
	 *            the billReceivableService to set
	 */
	public void setBillReceivableService(IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	/**
	 * @param orgAdministrativeInfoService
	 *            the orgAdministrativeInfoService to set
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * @param settlementCommonService
	 *            the settlementCommonService to set
	 */
	public void setSettlementCommonService(SettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	/**
	 * @param packingRecAndPayInputDao
	 *            the packingRecAndPayInputDao to set
	 */
	public void setPackingRecAndPayInputDao(IPackingRecAndPayInputDao packingRecAndPayInputDao) {
		this.packingRecAndPayInputDao = packingRecAndPayInputDao;
	}

	/**
	 * @param packagingSupplierService
	 *            the packagingSupplierService to set
	 */
	public void setPackagingSupplierService(IPackagingSupplierService packagingSupplierService) {
		this.packagingSupplierService = packagingSupplierService;
	}

	/**
	 * @param waybillManagerService
	 *            the waybillManagerService to set
	 */
	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

}
