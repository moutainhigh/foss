package com.deppon.foss.module.settlement.agency.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.agency.api.server.dao.IPackingRecAndPayDao;
import com.deppon.foss.module.settlement.agency.api.server.service.IPackingRecAndPayForTfrService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.PackingRecAndPayTfrDto;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountDService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.OperatingLogEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.service.impl.OperatingLogService;
import com.deppon.foss.module.settlement.common.server.service.impl.SettlementCommonService;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class PackingRecAndPayForTfrService implements IPackingRecAndPayForTfrService {

	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = Logger.getLogger(PackingRecAndPayForTfrService.class);

	/**
	 * dto
	 */
	private IPackingRecAndPayDao packingRecAndPayDao;

	/**
	 * 注入公共的应付单接口
	 */
	private IBillPayableService billPayableService;

	/**
	 * 部门信息service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * settlementCommonService
	 */
	private SettlementCommonService settlementCommonService;
	/**
	 * 操作日志
	 */
	private OperatingLogService operatingLogService;

	/**
	 * 运单service
	 */
	private IWaybillManagerService waybillManagerService;
	
	/**
	 * 对账单明细Service
	 */
	private IStatementOfAccountDService statementOfAccountDService;

	/**
	 * <p>新增主要包装 辅助包装</p> 
	 * @author 105762
	 * @date 2014-6-13 下午2:39:15
	 * @param packingRecAndPayTfrDtoList
	 * @param payableType
	 *  应付类别 两种
	 *  主要包装应付：
	 *  SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MAIN_PACKING ="MAP"
	 *  辅助包装应付
	 *  SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING = "MNP"
	 * @param currentInfo
	 */
	@Transactional
	@Override
	public void add(List<PackingRecAndPayTfrDto> packingRecAndPayTfrDtoList, String payableType, CurrentInfo currentInfo) {
		LOGGER.info("新增主要包装 辅助包装接口调用开始...");
		SettlementUtil.valideIsNull(packingRecAndPayTfrDtoList, "传入参数currentInfo实体为空");
		SettlementUtil.valideIsNull(currentInfo, "传入参数currentInfo实体为空");

		// 校验是否已经生成对应应付单，防止重复生成
		List<BillPayableEntity> list = packingRecAndPayDao.queryByDtosAndBillTypePayableType(packingRecAndPayTfrDtoList,
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__WOODEN_PAYABLE, payableType);
		if (CollectionUtils.isNotEmpty(list)) {
			StringBuffer payNos = new StringBuffer();
			for (BillPayableEntity en : list) {
				payNos.append(en.getPayableNo());
			}

			throw new SettlementException("新增失败，已经存在对应应付单:" + payNos.toString());
		}

		List<BillPayableEntity> payList = buildAddEntity(packingRecAndPayTfrDtoList, payableType, currentInfo);

		if (payList.size() > 0) {
			for (BillPayableEntity entity : payList) {
				// 保存应付单
				billPayableService.addBillPayable(entity, currentInfo);
			}
		}
		LOGGER.info("新增主要包装 辅助包装接口调用完成");
	}

	/**
	 * <p>创建新增实体</p> 
	 * @author 105762
	 * @date 2014-6-16 下午3:39:34
	 * @param packingRecAndPayTfrDtoList
	 * @param payableType
	 *  应付类别 两种
	 *  主要包装应付：
	 *  SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MAIN_PACKING ="MAP"
	 *  辅助包装应付
	 *  SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING = "MNP"
	 * @param currentInfo
	 */
	private List<BillPayableEntity> buildAddEntity(List<PackingRecAndPayTfrDto> list, String payableType, CurrentInfo currentInfo) {
		SettlementUtil.valideIsNull(list, "传入待创建实体的主要包装和辅助包装列表为空");
		List<BillPayableEntity> resultList = new ArrayList<BillPayableEntity>();
		for (PackingRecAndPayTfrDto en : list) {
			// 新建应付单实体
			BillPayableEntity payEn = new BillPayableEntity();

			// 客户信息
			payEn.setCustomerName(en.getPackageOrgName());
			payEn.setCustomerCode(en.getPackageOrgCode());

			// 查询运单
			WaybillEntity waybillentity = waybillManagerService.queryWaybillBasicByNo(en.getWaybillNo());
			SettlementUtil.valideIsNull(waybillentity, "未查询到对应运单");

			payEn.setWaybillNo(waybillentity.getWaybillNo());

			// 出发部门
			payEn.setOrigOrgCode(waybillentity.getReceiveOrgCode());
			payEn.setOrigOrgName(waybillentity.getReceiveOrgName());

			// 到达部门
			payEn.setDestOrgCode(en.getBillOrgCode());
			payEn.setDestOrgName(en.getBillOrgName());

			// 来源单号
			payEn.setSourceBillNo(waybillentity.getWaybillNo());
			// 来源单据类型
			payEn.setSourceBillType(SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__WAYBILL);
			// 产品类型
			payEn.setProductCode(waybillentity.getProductCode());

			// 主要包装
			if (SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MAIN_PACKING.equals(payableType)) {
				payEn.setPayableType(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MAIN_PACKING);


				// 辅助包装
			} else if (SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING.equals(payableType)) {
				payEn.setPayableType(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING);
			} else {
				throw new SettlementException("创建应付单时参数的类不合法");
			}

			// 判断金额的最大最小值
			BigDecimal packagePayableMoney = en.getPackagePayableMoney();

			// 金额
			payEn.setAmount(packagePayableMoney);
			// 未核销金额
			payEn.setUnverifyAmount(packagePayableMoney);
			// 核销金额
			payEn.setVerifyAmount(BigDecimal.ZERO);

			// 设置id
			payEn.setId(UUIDUtils.getUUID());

			// 系统生成方式
			payEn.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);

			// 单据子类型
			payEn.setBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__WOODEN_PAYABLE);

			// 设置审核状态为未审核
			payEn.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__NOT_AUDIT);

			// 获取当前选择的应付部门组织的的实体信息
			OrgAdministrativeInfoEntity payOrgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(en.getBillOrgCode());
			// 判断组织信息是否为空
			if (payOrgEntity == null) {
				// 当前选择的应付部门组织信息为空异常
				throw new SettlementException("当前选择的应付部门组织信息为空！");
			}

			// 应付部门
			payEn.setPayableOrgCode(payOrgEntity.getCode());
			payEn.setPayableOrgName(payOrgEntity.getName());

			// 设置应付部门子公司code
			payEn.setPayableComCode(payOrgEntity.getSubsidiaryCode());
			// 设置应付部门子公司name
			payEn.setPayableComName(payOrgEntity.getSubsidiaryName());

			// 生效状态不能为空
			payEn.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES);

			// 支付状态不能为空
			payEn.setPayStatus(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO);

			// 冻结状态不能为空
			payEn.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__NOT_FROZEN);

			// 设置应付管理的币种
			payEn.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);

			// 业务日期、记账日期为系统当前时间；创建时间默认为系统当前时间
			Date now = new Date();
			// 业务时间
			payEn.setBusinessDate(now);
			// 记账时间
			payEn.setAccountDate(now);

			// 应付单单据默认为有效的单据
			payEn.setActive(FossConstants.ACTIVE);
			// 应付单单据默认为非红单
			payEn.setIsRedBack(FossConstants.NO);
			// 应付单单据默认为非初始化
			payEn.setIsInit(FossConstants.NO);
			// 版本号
			payEn.setVersionNo(FossConstants.INIT_VERSION_NO);
			// 默认单号
			payEn.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);
			payEn.setPaymentNo(SettlementConstants.DEFAULT_BILL_NO);

			// 包装其他应付单号,应付单单号系统自动生成，生成规则“YF97+8位流水号”
			payEn.setPayableNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YF97));

			// 发票标记
			payEn.setInvoiceMark(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO);

			resultList.add(payEn);
		}
		return resultList;
	}

	/**
	 * <p>修改</p> 
	 * @author 105762
	 * @date 2014-6-17 下午5:01:13
	 * @param packingRecAndPayTfrDtoList
	 * @param payableType
	 *  应付类别 两种
	 *  主要包装应付：
	 *  SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MAIN_PACKING ="MAP"
	 *  辅助包装应付
	 *  SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING = "MNP"
	 * @param currentInfo
	 */
	@Transactional
	@Override
	public void update(List<PackingRecAndPayTfrDto> packingRecAndPayTfrDtoList, String payableType, CurrentInfo currentInfo) {
		LOGGER.info("修改包装应付开始");

		// 红冲原单
		invalid(packingRecAndPayTfrDtoList, payableType, currentInfo);

		// 新增应付单
		add(packingRecAndPayTfrDtoList, payableType, currentInfo);
		LOGGER.info("修改包装应付结束");
	}

	/**
	 * <p>作废</p> 
	 * @author 105762
	 * @date 2014-6-17 下午5:01:13
	 * @param packingRecAndPayTfrDtoList
	 * @param payableType
	 *  应付类别 两种
	 *  主要包装应付：
	 *  SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MAIN_PACKING ="MAP"
	 *  辅助包装应付
	 *  SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING = "MNP"
	 * @param currentInfo
	 */
	@Transactional
	@Override
	public void invalid(List<PackingRecAndPayTfrDto> packingRecAndPayTfrDtoList, String payableType, CurrentInfo currentInfo) {
		LOGGER.info("作废包装应付开始");
		// 非空校验
		SettlementUtil.valideIsNull(packingRecAndPayTfrDtoList, "传入参数currentInfo实体为空");
		SettlementUtil.valideIsNull(currentInfo, "传入参数currentInfo实体为空");

		// 获取应付单
		List<BillPayableEntity> payList = gainPayableList(packingRecAndPayTfrDtoList, payableType);

		// 校验对账单
		List<String> billNos = new ArrayList<String>();
		for (BillPayableEntity entity : payList) {
			if ((entity.getStatementBillNo() != null) && !SettlementConstants.DEFAULT_BILL_NO.equals(entity.getStatementBillNo())) {
				throw new SettlementException("应付单" + entity.getPayableNo() + "在对账单中不允许修改");
			}

			if (SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE.equals(entity.getApproveStatus())) {
				throw new SettlementException("应付单" + entity.getPayableNo() + "为“已审核”状态，不允许修改");
			}
			
			if ( SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__YES.equals(entity.getPayStatus())) {
				throw new SettlementException(entity.getPayableNo() + "为已支付状态,不能作废");
			}
			billNos.add(entity.getPayableNo());
		}
		/*
		 * 调用代打木架对账单接口 确认明细数据是否存在
		 */
		if (statementOfAccountDService.queryIfAtLeastOneBillExistsInStatement(billNos)) {
			throw new SettlementException("存在已进入代打木架对账单的单据,不能进行作废操作");
		}

		// 红冲原单
		for (BillPayableEntity entity : payList) {
			billPayableService.writeBackBillPayable(entity, currentInfo);
			// 操作日志
			doOperationLog(entity.getPayableNo(), entity.getBillType(), SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__RED_BACK,
					currentInfo);
		}
	}

	/**
	 * <p>审核</p> 
	 * @author 105762
	 * @date 2014-6-18 上午11:04:03
	 * @param packingRecAndPayTfrDtoList
	 * @param payableType
	 *  应付类别 两种
	 *  主要包装应付：
	 *  SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MAIN_PACKING ="MAP"
	 *  辅助包装应付
	 *  SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING = "MNP"
	 * @param currentInfo
	 */
	@Override
	public void audit(List<PackingRecAndPayTfrDto> packingRecAndPayTfrDtoList, String payableType, CurrentInfo currentInfo) {
		LOGGER.info("审核包装主要应付单和包装辅助应付单开始...");
		// 非空校验
		SettlementUtil.valideIsNull(packingRecAndPayTfrDtoList, "传入参数currentInfo实体为空");
		SettlementUtil.valideIsNull(currentInfo, "传入参数currentInfo实体为空");
		List<BillPayableEntity> payList = gainPayableList(packingRecAndPayTfrDtoList, payableType);
		// 校验对账单
		List<String> billNos = new ArrayList<String>();
		for (BillPayableEntity entity : payList) {
			if ((entity.getStatementBillNo() != null) && !SettlementConstants.DEFAULT_BILL_NO.equals(entity.getStatementBillNo())) {
				throw new SettlementException("应付单" + entity.getPayableNo() + "在对账单中不允许修改");
			}
			if ( SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__YES.equals(entity.getPayStatus())) {
				throw new SettlementException(entity.getPayableNo() + "为已支付状态,不能再次审核");
			}
			billNos.add(entity.getPayableNo());
		}
		/*
		 * 调用代打木架对账单接口 确认明细数据是否存在
		 */
		if (statementOfAccountDService.queryIfAtLeastOneBillExistsInStatement(billNos)) {
			throw new SettlementException("存在已进入代打木架对账单的单据,不能重复进行审核操作");
		}

		// 审核应付单
		Date now = new Date();
		if (payList.size() > 0) {
			BillPayableDto billPayableDto = new BillPayableDto();
			// 传list实体
			billPayableDto.setBillPayables(payList);

			// 审核时间
			billPayableDto.setAuditDate(now);
			// 修改审核状态为已审核
			billPayableDto.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE);
			// 其它应付单必须为有效、非红单的数据
			billPayableDto.setActive(FossConstants.YES);
			billPayableDto.setIsRedBack(FossConstants.NO);
			billPayableDto.setBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__WOODEN_OTEHR_PAYABLE);
			// 调用common下面的审核接口
			billPayableService.batchAuditBillPayable(billPayableDto, currentInfo);

			// 将单据信息、审核人、审核时间保存到操作日志表中
			// 循环插入日志
			for (BillPayableEntity billPayableEntity : payList) {
				doOperationLog(billPayableEntity.getPayableNo(), SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__PACKING_PAYABLE,
						SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__AUDIT, currentInfo);
			}
		}
		LOGGER.info("审核包装主要应付单和包装辅助应付单结束.");
	}

	/**
	 * <p>反审核</p> 
	 * @author 105762
	 * @date 2014-6-18 上午11:04:03
	 * @param packingRecAndPayTfrDtoList
	 * @param payableType
	 *  应付类别 两种
	 *  主要包装应付：
	 *  SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MAIN_PACKING ="MAP"
	 *  辅助包装应付
	 *  SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING = "MNP"
	 * @param currentInfo
	 */
	@Transactional
	@Override
	public void reverseAudit(List<PackingRecAndPayTfrDto> packingRecAndPayTfrDtoList, String payableType, CurrentInfo currentInfo) {
		LOGGER.info("反审核包装主要应付单和包装辅助应付单开始...");
		// 非空校验
		SettlementUtil.valideIsNull(packingRecAndPayTfrDtoList, "传入参数currentInfo实体为空");
		SettlementUtil.valideIsNull(currentInfo, "传入参数currentInfo实体为空");
		List<BillPayableEntity> payList = gainPayableList(packingRecAndPayTfrDtoList, payableType);
		// 校验对账单
		List<String> billNos = new ArrayList<String>();
		for (BillPayableEntity entity : payList) {
			if ((entity.getStatementBillNo() != null) && !SettlementConstants.DEFAULT_BILL_NO.equals(entity.getStatementBillNo())) {
				throw new SettlementException("应付单" + entity.getPayableNo() + "在对账单中不允许修改");
			}
			if ( SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__YES.equals(entity.getPayStatus())) {
				throw new SettlementException(entity.getPayableNo() + "为已支付状态,不能反审核");
			}
			billNos.add(entity.getPayableNo());
		}
		/*
		 * 调用代打木架对账单接口 确认明细数据是否存在
		 */
		if (statementOfAccountDService.queryIfAtLeastOneBillExistsInStatement(billNos)) {
			throw new SettlementException("存在已进入代打木架对账单的单据,不能进行反审核操作");
		}

		// 审核应付单
		Date now = new Date();
		if (payList.size() > 0) {
			BillPayableDto billPayableDto = new BillPayableDto();
			// 传list实体
			billPayableDto.setBillPayables(payList);

			// 审核时间
			billPayableDto.setAuditDate(now);
			// 修改审核状态为已审核
			billPayableDto.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__NOT_AUDIT);
			// 其它应付单必须为有效、非红单的数据
			billPayableDto.setActive(FossConstants.YES);
			billPayableDto.setIsRedBack(FossConstants.NO);
			billPayableDto.setBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__WOODEN_OTEHR_PAYABLE);
			// 调用common下面的审核接口
			billPayableService.batchReverseAuditBillPayable(billPayableDto, currentInfo);

			// 将单据信息、审核人、审核时间保存到操作日志表中
			// 循环插入日志
			for (BillPayableEntity billPayableEntity : payList) {
				doOperationLog(billPayableEntity.getPayableNo(), SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__PACKING_PAYABLE,
						SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__REVERSE_AUDIT, currentInfo);
			}
		}
		LOGGER.info("反审核包装主要应付单和包装辅助应付单结束.");
	}

	/**
	 * <p>获取对应应付单</p> 
	 * @author 105762
	 * @date 2014-6-19 上午8:45:51
	 * @param packingRecAndPayTfrDtoList
	 * @param payableType 
	 *  应付类别 两种
	 *  主要包装应付：
	 *  SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MAIN_PACKING ="MAP"
	 *  辅助包装应付
	 *  SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING = "MNP"
	 */
	private List<BillPayableEntity> gainPayableList(List<PackingRecAndPayTfrDto> packingRecAndPayTfrDtoList, String payableType) {
		LOGGER.info("获取对应应收单list");
		// 应付单list
		List<BillPayableEntity> payList = packingRecAndPayDao.queryByDtosAndBillTypePayableType(packingRecAndPayTfrDtoList,
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__WOODEN_PAYABLE, payableType);

		// 校验查询结果
		SettlementUtil.valideIsNull(payList, "未查询到运单对应应付单");

		// 校验查询到的应付单数目
		if (payList.size() != packingRecAndPayTfrDtoList.size()) {
			throw new SettlementException("查询到应付单数目与传入参数个数不一致");
		}

		return payList;
	}

	/**
	 * <p>添加操作日志</p> 
	 * @author 105762
	 * @date 2014-6-17 下午3:43:14
	 * @param billNo
	 * @param billType
	 * @param operationType
	 * @param currentInfo
	 * @see
	 */
	private void doOperationLog(String billNo, String billType, String operationType, CurrentInfo currentInfo) {
		OperatingLogEntity operatingLogEntity = new OperatingLogEntity();
		// 操作日志
		// 设置操作日志单号
		operatingLogEntity.setOperateBillNo(billNo);
		// 设置操作日志类型
		operatingLogEntity.setOperateBillType(billType);
		// 设置操作类型
		operatingLogEntity.setOperateType(operationType);
		// 调用插入日志接口
		operatingLogService.addOperatingLog(operatingLogEntity, currentInfo);
	}

	/**
	 * @param packingRecAndPayDao the packingRecAndPayDao to set
	 */
	public void setPackingRecAndPayDao(IPackingRecAndPayDao packingRecAndPayDao) {
		this.packingRecAndPayDao = packingRecAndPayDao;
	}

	/**
	 * @param billPayableService the billPayableService to set
	 */
	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	/**
	 * @param orgAdministrativeInfoService the orgAdministrativeInfoService to set
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * @param settlementCommonService the settlementCommonService to set
	 */
	public void setSettlementCommonService(SettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	/**
	 * @param operatingLogService the operatingLogService to set
	 */
	public void setOperatingLogService(OperatingLogService operatingLogService) {
		this.operatingLogService = operatingLogService;
	}

	/**
	 * @param waybillManagerService the waybillManagerService to set
	 */
	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	/**
	 * @param statementOfAccountDService the statementOfAccountDService to set
	 */
	public void setStatementOfAccountDService(IStatementOfAccountDService statementOfAccountDService) {
		this.statementOfAccountDService = statementOfAccountDService;
	}
}
