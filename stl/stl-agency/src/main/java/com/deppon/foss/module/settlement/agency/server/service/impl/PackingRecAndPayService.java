package com.deppon.foss.module.settlement.agency.server.service.impl;

import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.server.service.impl.complex.OrgAdministrativeInfoComplexService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.agency.api.server.dao.IPackingRecAndPayDao;
import com.deppon.foss.module.settlement.agency.api.server.service.IPackingRecAndPayService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.PackingRecAndPayDto;
import com.deppon.foss.module.settlement.agency.api.shared.dto.PackingRecAndPayQueryDto;
import com.deppon.foss.module.settlement.agency.api.shared.dto.PackingRecAndPayResultDto;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountDService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.OperatingLogEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.service.impl.OperatingLogService;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

public class PackingRecAndPayService implements IPackingRecAndPayService {

    private static final String COUNT = "COUNT";
    private static final String REC_TOTAL_AMOUNT = "REC_TOTAL_AMOUNT";
    private static final String REC_TOTAL_UNVERIFY_AMOUNT = "REC_TOTAL_UNVERIFY_AMOUNT";
    private static final String REC_TOTAL_VERIFY_AMOUNT = "REC_TOTAL_VERIFY_AMOUNT";
    private static final String PAY_TOTAL_AMOUNT = "PAY_TOTAL_AMOUNT";
    private static final String PAY_TOTAL_UNVERIFY_AMOUNT = "PAY_TOTAL_UNVERIFY_AMOUNT";
    private static final String PAY_TOTAL_VERIFY_AMOUNT = "PAY_TOTAL_VERIFY_AMOUNT";

	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = Logger.getLogger(PackingRecAndPayService.class);
	/**
	 * 查询部门大区小区信息service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	/**
	 * dto
	 */
	private IPackingRecAndPayDao packingRecAndPayDao;

	/**
	 * 注入公共的应收单接口
	 */
	private IBillReceivableService billReceivableService;

	/**
	 * 注入公共的应付单接口
	 */
	private IBillPayableService billPayableService;

	/**
	 * 操作日志
	 */
	private OperatingLogService operatingLogService;

	/**
	 * 对账单明细Service
	 */
	private IStatementOfAccountDService statementOfAccountDService;

	/**
	 * <p>查询</p> 
	 * @author 105762
	 * @date 2014-6-7 下午4:41:09
	 * @param dto
	 * @param currentInfo
	 * @return 
	 * @see
	 */
	@Override
	public PackingRecAndPayResultDto query(PackingRecAndPayQueryDto dto, CurrentInfo currentInfo) {
		LOGGER.info("查询开始...");
		// 非空校验
		valideQueryDto(dto, currentInfo);
		preprocessQueryDto(dto, currentInfo);

        // 返回值
        PackingRecAndPayResultDto resultDto = new PackingRecAndPayResultDto();
		// 查询结果list
		List<PackingRecAndPayDto> list = new ArrayList<PackingRecAndPayDto>();

		// 按日期查询
		if (SettlementConstants.TAB_QUERY_BY_ACCOUNT_DATE.endsWith(dto.getQueryType())) {
            Map map = packingRecAndPayDao.queryTotalCountByAccountDate(dto);
			resultDto.setTotalCount(((BigDecimal)map.get(COUNT)).intValue());
            resultDto.setRecTotalAmount((BigDecimal)map.get(REC_TOTAL_AMOUNT));
            resultDto.setRecTotalUnverifyAmount((BigDecimal)map.get(REC_TOTAL_UNVERIFY_AMOUNT));
            resultDto.setRecTotalVerifyAmount((BigDecimal) map.get(REC_TOTAL_VERIFY_AMOUNT));
            resultDto.setPayTotalAmount((BigDecimal) map.get(PAY_TOTAL_AMOUNT));
            resultDto.setPayTotalUnverifyAmount((BigDecimal) map.get(PAY_TOTAL_UNVERIFY_AMOUNT));
            resultDto.setPayTotalVerifyAmount((BigDecimal) map.get(PAY_TOTAL_VERIFY_AMOUNT));

            if (resultDto.getTotalCount() > 0) {
                resultDto.setPackingRecAndPayDtos(packingRecAndPayDao.queryByAccountDate(dto));
            }
			// 按单号查询
		} else if (SettlementConstants.TAB_QUERY_BY_BILL_NO.endsWith(dto.getQueryType())) {
			list = packingRecAndPayDao.queryByBillNo(dto);
			int total = list.size();

            BigDecimal recTotalAmount = new BigDecimal(0);
            BigDecimal recUnverifyAmount = new BigDecimal(0);
            BigDecimal payTotalAmount = new BigDecimal(0);
            BigDecimal payUnverifyAmount = new BigDecimal(0);

            /*
             * 统计总数
             */
            for(PackingRecAndPayDto oneDto:list){
                if(oneDto.getBillNo().startsWith("YS")){
                    recTotalAmount = recTotalAmount.add(oneDto.getAmount());
                    recUnverifyAmount = recUnverifyAmount.add(oneDto.getUnverifyAmount());
                } else {
                    payTotalAmount = payTotalAmount.add(oneDto.getAmount());
                    payUnverifyAmount = payUnverifyAmount.add(oneDto.getUnverifyAmount());
                }
            }
            /*
             * 设置总数
             */
            resultDto.setTotalCount(total);
            resultDto.setRecTotalAmount(recTotalAmount);
            resultDto.setRecTotalUnverifyAmount(recUnverifyAmount);
            resultDto.setRecTotalVerifyAmount(recTotalAmount.subtract(recUnverifyAmount));
            resultDto.setPayTotalAmount(payTotalAmount);
            resultDto.setPayTotalUnverifyAmount(payUnverifyAmount);
            resultDto.setPayTotalVerifyAmount(payTotalAmount.subtract(payUnverifyAmount));

            /*
             * 设置 list
             */
            resultDto.setPackingRecAndPayDtos(list);

		} else {
			throw new SettlementException("查询类别错误");
		}
		SettlementUtil.valideIsNull(resultDto.getPackingRecAndPayDtos(), "未查询到数据");
		LOGGER.info("查询结束");
		return resultDto;
	}

	/**
	 * <p>预处理QueryDto</p> 
	 * @author 105762
	 * @date 2014-6-7 下午5:01:01
	 * @param dto
	 * @param currentInfo
	 * @see
	 */
	private void preprocessQueryDto(PackingRecAndPayQueryDto dto, CurrentInfo currentInfo) {
		LOGGER.info("预处理查询参数开始...");
		// 默认查询有效单据
		dto.setActive(FossConstants.YES);
		// 设置当前登录人
		dto.setEmpCode(currentInfo.getEmpCode());

		if (SettlementConstants.TAB_QUERY_BY_ACCOUNT_DATE.endsWith(dto.getQueryType())) {
			// 结束日期 +1
			dto.setEndAccountDate(DateUtils.addDayToDate(dto.getEndAccountDate(), 1));
			// 大区小区营业部查询条件处理
			List<String> depts = new ArrayList<String>();
			// 如果部门存在，则获取当前部门与用户所管辖部门的交集
			if (StringUtils.isNotBlank(dto.getDepartment())) {
				depts.add(dto.getDepartment());
				// 如果部门不存在，小区存在，则获取小区地下所有部门与该用户所管辖部门交集
			} else if (StringUtils.isNotBlank(dto.getSmallArea())) {
				// 调用综合方法查询
				List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(dto
						.getSmallArea());
				// 循环部门，获取用户所管辖部门与查询到部门的交集
				for (OrgAdministrativeInfoEntity en : orgList) {
					depts.add(en.getCode());
				}
				// 如果部门、小区都不存在，而大区存在， 则获取大区底下所有部门与该用户所管辖部门交集
			} else if (StringUtils.isNotBlank(dto.getBigArea())) {
				// 调用综合方法查询
				List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(dto
						.getBigArea());
				// 循环部门，获取用户所管辖部门与查询到部门的交集
				for (OrgAdministrativeInfoEntity en : orgList) {
					depts.add(en.getCode());
				}
				// 如果都不存在，则获取默认用户所管辖部门集合
			}
			// 设置可查询部门结果集
			dto.setDepts(depts);
		}
		LOGGER.info("预处理查询参数结束");
	}

	/**
	 * <p>校验查询参数</p> 
	 * @author 105762
	 * @date 2014-6-7 下午4:47:47
	 * @param dto
	 * @param currentInfo
	 * @see
	 */
	private void valideQueryDto(PackingRecAndPayQueryDto dto, CurrentInfo currentInfo) {
		LOGGER.info("校验查询参数开始...");
		// 非空校验
		SettlementUtil.valideIsNull(dto, "packingRecAndPayQueryDto为空");
		SettlementUtil.valideIsNull(currentInfo, "currentInfo为空,当前用户未登录");
		SettlementUtil.valideIsNull(dto.getQueryType(), "查询类别为空");

		// 查询条件校验
		if (SettlementConstants.TAB_QUERY_BY_ACCOUNT_DATE.endsWith(dto.getQueryType())) {
			SettlementUtil.valideIsNull(dto.getBeginAccountDate(), "开始时间为空");
			SettlementUtil.valideIsNull(dto.getEndAccountDate(), "结束时间为空");
		} else if (SettlementConstants.TAB_QUERY_BY_BILL_NO.endsWith(dto.getQueryType())) {
			SettlementUtil.valideIsNull(dto.getBillNos(), "待查询单号为空.");
		} else {
			throw new SettlementException("查询类别错误");
		}
		LOGGER.info("校验查询参数结束");
	}

	/** 
	 * <p>作废/红冲</p> 
	 * @author 105762
	 * @date 2014-6-11 上午8:15:28
	 * @param packingRecAndPayQueryDto
	 * @param currentInfo
	 * @return 
	 * @see com.deppon.foss.module.settlement.agency.api.server.service.IPackingRecAndPayService#invalid(com.deppon.foss.module.settlement.agency.api.shared.dto.PackingRecAndPayQueryDto, com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void invalid(PackingRecAndPayQueryDto packingRecAndPayQueryDto, CurrentInfo currentInfo) {
		LOGGER.info("作废开始...");
		/* 非空校验 */
		SettlementUtil.valideIsNull(packingRecAndPayQueryDto, "dto 为空");
		SettlementUtil.valideIsNull(packingRecAndPayQueryDto.getBillNos(), "单号为空");
		SettlementUtil.valideIsNull(currentInfo, "用户信息为空");

		/* 查询对应应收应付单据 */
		List<String> billNos = null;
		if (packingRecAndPayQueryDto.getBillNos().length == 0) {
			throw new SettlementException("传入待作废单号为空");
		} else {
			billNos = Arrays.asList(packingRecAndPayQueryDto.getBillNos());
		}
		List<BillReceivableEntity> recList = billReceivableService.queryByReceivableNOs(billNos, FossConstants.YES);
		List<BillPayableEntity> payList = billPayableService.queryByPayableNOs(billNos, FossConstants.YES);

		if (billNos.size() != recList.size() + payList.size()) {
			throw new SettlementException("传入待作废单号数据已经被修改,请刷新界面重新操作");
		}

		/*
		 * 对单据做作废前的校验 已审核或已经做进代打木架对账单的不能做废
		 */
		validaBillReceivableEntity(currentInfo, billNos, recList, payList);
		LOGGER.info("作废结束...");
	}

	private void validaBillReceivableEntity(CurrentInfo currentInfo,
			List<String> billNos, List<BillReceivableEntity> recList,
			List<BillPayableEntity> payList) {
		for (BillReceivableEntity rec : recList) {
			if (SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__AUDIT_AGREE.equals(rec.getApproveStatus())) {
				throw new SettlementException(rec.getReceivableNo() + "审核状态为“已审核”，不能作废。");
			}
			if (!rec.getStatementBillNo().isEmpty() && !SettlementConstants.DEFAULT_BILL_NO.equals(rec.getStatementBillNo())) {
				throw new SettlementException(rec.getReceivableNo() + "已进入代打木架对账单，不能作废。");
			}
		}
		for (BillPayableEntity pay : payList) {
			if (SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__AUDIT_AGREE.equals(pay.getApproveStatus())) {
				throw new SettlementException(pay.getPayableNo() + "审核状态为“已审核”，不能作废。");
			}
			if (!pay.getStatementBillNo().isEmpty() && !SettlementConstants.DEFAULT_BILL_NO.equals(pay.getStatementBillNo())) {
				throw new SettlementException(pay.getPayableNo() + "已进入代打木架对账单，不能作废。");
			}
		}
		/*
		 * 调用代打木架对账单接口 确认明细数据是否存在
		 */
		if (statementOfAccountDService.queryIfAtLeastOneBillExistsInStatement(billNos)) {
			throw new SettlementException("存在已进入代打木架对账单的单据,不能进行作废操作");
		}

		/*
		 * 循环作废应收应付单据
		 */
		for (BillReceivableEntity rec : recList) {
			billReceivableService.writeBackBillReceivable(rec, currentInfo);
		}
		for (BillPayableEntity pay : payList) {
			billPayableService.writeBackBillPayable(pay, currentInfo);
		}
	}

	/** 
	 * <p>审核</p> 
	 * @author 105762
	 * @date 2014-6-11 上午8:15:28
	 * @param packingRecAndPayQueryDto
	 * @param currentInfo
     * @see com.deppon.foss.module.settlement.agency.api.server.service.IPackingRecAndPayService#approve(com.deppon.foss.module.settlement.agency.api.shared.dto.PackingRecAndPayQueryDto, com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void approve(PackingRecAndPayQueryDto packingRecAndPayQueryDto, CurrentInfo currentInfo) {
		LOGGER.info("审核开始...");
		/* 非空校验 */
		SettlementUtil.valideIsNull(packingRecAndPayQueryDto, "dto 为空");
		SettlementUtil.valideIsNull(packingRecAndPayQueryDto.getBillNos(), "单号为空");
		SettlementUtil.valideIsNull(currentInfo, "用户信息为空");

		/* 查询对应应收应付单据 */
		List<String> billNos = null;
		if (packingRecAndPayQueryDto.getBillNos().length == 0) {
			throw new SettlementException("传入待审核单号为空");
		} else {
			billNos = Arrays.asList(packingRecAndPayQueryDto.getBillNos());
		}
		List<BillReceivableEntity> recList = billReceivableService.queryByReceivableNOs(billNos, FossConstants.YES);
		List<BillPayableEntity> payList = billPayableService.queryByPayableNOs(billNos, FossConstants.YES);

		if (billNos.size() != recList.size() + payList.size()) {
			throw new SettlementException("传入待审核单号数据已经被修改,请刷新界面重新操作");
		}

		/*
		 * 对单据做审核前的校验 已审核的单据不能再次审核
		 */
		validaEntity(recList, payList);

		/*
		 * 调用代打木架对账单接口 确认明细数据是否存在
		 */
		if (statementOfAccountDService.queryIfAtLeastOneBillExistsInStatement(billNos)) {
			throw new SettlementException("存在已进入代打木架对账单的单据,不能重复进行审核操作");
		}

		/*
		 * 循环审核应收应付单
		 */
		// 审核时间
		validaBillPayableAndReceivable(currentInfo, recList, payList);
		LOGGER.info("审核结束...");
	}

	private void validaEntity(List<BillReceivableEntity> recList,
			List<BillPayableEntity> payList) {
		for (BillReceivableEntity rec : recList) {
			if (StringUtils.isEmpty(rec.getCustomerCode())) {
				throw new SettlementException(rec.getReceivableNo() + "的客户编码为空!");
			}
			if (SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__AUDIT_AGREE.equals(rec.getApproveStatus())) {
				throw new SettlementException(rec.getReceivableNo() + "审核状态为“已审核”，不能再次审核。");
			}
			if (rec.getVerifyAmount().compareTo(BigDecimal.ZERO) > 0) {
				throw new SettlementException(rec.getReceivableNo() + "已核销，不能再次审核。");
			}
		}
		for (BillPayableEntity pay : payList) {
			if (StringUtils.isEmpty(pay.getCustomerCode())) {
				throw new SettlementException(pay.getPayableNo() + "的客户编码为空!");
			}
			if (SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE.equals(pay.getApproveStatus())) {
				throw new SettlementException(pay.getPayableNo() + "审核状态为“已审核”，不能审核。");
			}
			if (pay.getVerifyAmount().compareTo(BigDecimal.ZERO) > 0) {
				throw new SettlementException(pay.getPayableNo() + "已核销，不能再次审核。");
			}
			if ( SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__YES.equals(pay.getPayStatus())) {
				throw new SettlementException(pay.getPayableNo() + "为已支付状态,不能再次审核");
			}
		}
	}

	private void validaBillPayableAndReceivable(CurrentInfo currentInfo,
			List<BillReceivableEntity> recList, List<BillPayableEntity> payList) {
		Date now = new Date();
		// 声明实例日志对象
		OperatingLogEntity operatingLogEntity = new OperatingLogEntity();
		// 应收单
		if (recList.size() > 0) {
			BillReceivableDto billReceivableDto = new BillReceivableDto();

			// 批量传入list实体
			billReceivableDto.setBillReceivables(recList);

			billReceivableDto.setAuditDate(now);

			// 修改审核状态为已审核
			billReceivableDto.setApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__AUDIT_AGREE);

			// 设置审核人code
			billReceivableDto.setAuditUserCode(currentInfo.getEmpCode());
			// 设置审核人name
			billReceivableDto.setAuditUserName(currentInfo.getEmpName());

			// 必须为有效的数据
			billReceivableDto.setActive(FossConstants.YES);
			// 必须为非红单的数据
			billReceivableDto.setIsRedBack(FossConstants.NO);
			// 应收单类型
			billReceivableDto.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__WOODEN_OTEHR_RECEIVABLE);

			// 调用common下面的审核接口
			billReceivableService.auditPackingOtherBillReceivable(billReceivableDto, currentInfo);

			// 将单据信息、反审核人、反审核时间保存到操作日志表中
			// 循环插入日志
			for (BillReceivableEntity billReceivableEntity : recList) {
				// 设置操作日志单号
				operatingLogEntity.setOperateBillNo(billReceivableEntity.getReceivableNo());
				// 设置操作日志类型
				operatingLogEntity.setOperateBillType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__PACKING_OTHER_RECEIVABLE);
				// 设置操作类型
				operatingLogEntity.setOperateType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__AUDIT);
				// 调用插入日志接口
				operatingLogService.addOperatingLog(operatingLogEntity, currentInfo);
			}
		}

		// 审核应付单
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
				// 插入日志编号
				operatingLogEntity.setOperateBillNo(billPayableEntity.getPayableNo());
				// 插入日志类型
				operatingLogEntity.setOperateBillType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__PACKING_OTHER_PAYABLE);
				// 插入日志类型
				operatingLogEntity.setOperateType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__AUDIT);
				// 插入日志
				operatingLogService.addOperatingLog(operatingLogEntity, currentInfo);
			}
		}
	}

	/** 
	 * <p>反审核</p> 
	 * @author 105762
	 * @date 2014-6-11 上午8:15:28
	 * @param packingRecAndPayQueryDto
	 * @see com.deppon.foss.module.settlement.agency.api.server.service.IPackingRecAndPayService#reverseApprove(com.deppon.foss.module.settlement.agency.api.shared.dto.PackingRecAndPayQueryDto, com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void reverseApprove(PackingRecAndPayQueryDto packingRecAndPayQueryDto, CurrentInfo currentInfo) {
		LOGGER.info("反审核开始...");
		/* 非空校验 */
		SettlementUtil.valideIsNull(packingRecAndPayQueryDto, "dto 为空");
		SettlementUtil.valideIsNull(packingRecAndPayQueryDto.getBillNos(), "单号为空");
		SettlementUtil.valideIsNull(currentInfo, "用户信息为空");

		/* 查询对应应收应付单据 */
		List<String> billNos = null;
		if (packingRecAndPayQueryDto.getBillNos().length == 0) {
			throw new SettlementException("传入待反审核单号为空");
		} else {
			billNos = Arrays.asList(packingRecAndPayQueryDto.getBillNos());
		}
		List<BillReceivableEntity> recList = billReceivableService.queryByReceivableNOs(billNos, FossConstants.YES);
		List<BillPayableEntity> payList = billPayableService.queryByPayableNOs(billNos, FossConstants.YES);

		if (billNos.size() != recList.size() + payList.size()) {
			throw new SettlementException("传入待反审核单号数据已经被修改,请刷新界面重新操作");
		}

		/*
		 * 对单据做审核前的校验 1.审核状态为“未审核”，不能反审核 2.已进入对账单的不允许反审核
		 */
		validaBill(recList, payList);
		/*
		 * 调用代打木架对账单接口 确认明细数据是否存在
		 */
		if (statementOfAccountDService.queryIfAtLeastOneBillExistsInStatement(billNos)) {
			throw new SettlementException("存在已进入代打木架对账单的单据,不能进行反审核操作");
		}

		/*
		 * 循环反审核应收应付单
		 */
		// 审核时间
		Date now = new Date();
		// 声明实例日志对象
		OperatingLogEntity operatingLogEntity = new OperatingLogEntity();
		// 循环应收单list
		if (recList.size() > 0) {
			validaCurrent(currentInfo, recList, now, operatingLogEntity);
		}

		// 循环应付单list
		if (payList.size() > 0) {
			validaInfo(currentInfo, payList, now, operatingLogEntity);
		}
		LOGGER.info("反审核结束...");
	}

	private void validaBill(List<BillReceivableEntity> recList,
			List<BillPayableEntity> payList) {
		for (BillReceivableEntity rec : recList) {
			if (SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__NOT_AUDIT.equals(rec.getApproveStatus())) {
				throw new SettlementException(rec.getReceivableNo() + "审核状态为“未审核”，不能反审核。");
			}
			if (!rec.getStatementBillNo().isEmpty() && !SettlementConstants.DEFAULT_BILL_NO.equals(rec.getStatementBillNo())) {
				throw new SettlementException(rec.getReceivableNo() + "已进入代打木架对账单，不能反审核。");
			}
			if (rec.getVerifyAmount().compareTo(BigDecimal.ZERO) > 0) {
				throw new SettlementException(rec.getReceivableNo() + "已核销，不能反审核。");
			}
		}
		for (BillPayableEntity pay : payList) {
			if (StringUtils.isEmpty(pay.getCustomerCode())) {
				throw new SettlementException("待反审核数据的客户编码为空!");
			}
			if (SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__NOT_AUDIT.equals(pay.getApproveStatus())) {
				throw new SettlementException(pay.getPayableNo() + "审核状态为“未审核”，不能反审核。");
			}
			if (!pay.getStatementBillNo().isEmpty() && !SettlementConstants.DEFAULT_BILL_NO.equals(pay.getStatementBillNo())) {
				throw new SettlementException(pay.getPayableNo() + "已进入代打木架对账单，不能反审核。");
			}
			if (pay.getVerifyAmount().compareTo(BigDecimal.ZERO) > 0) {
				throw new SettlementException(pay.getPayableNo() + "已核销，不能反审核。");
			}
			if ( SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__YES.equals(pay.getPayStatus())) {
				throw new SettlementException(pay.getPayableNo() + "为已支付状态,不能反审核");
			}
		}
	}

	private void validaInfo(CurrentInfo currentInfo,
			List<BillPayableEntity> payList, Date now,
			OperatingLogEntity operatingLogEntity) {
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
			// 插入日志编号
			operatingLogEntity.setOperateBillNo(billPayableEntity.getPayableNo());
			// 插入日志类型
			operatingLogEntity.setOperateBillType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__PACKING_OTHER_PAYABLE);
			// 插入日志类型
			operatingLogEntity.setOperateType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__REVERSE_AUDIT);
			// 插入日志
			operatingLogService.addOperatingLog(operatingLogEntity, currentInfo);
		}
	}

	private void validaCurrent(CurrentInfo currentInfo,
			List<BillReceivableEntity> recList, Date now,
			OperatingLogEntity operatingLogEntity) {
		BillReceivableDto billReceivableDto = new BillReceivableDto();

		// 批量传入list实体
		billReceivableDto.setBillReceivables(recList);

		billReceivableDto.setAuditDate(now);

		// 修改审核状态为未审核
		billReceivableDto.setApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__NOT_AUDIT);

		// 设置审核人code
		billReceivableDto.setAuditUserCode(currentInfo.getEmpCode());
		// 设置审核人name
		billReceivableDto.setAuditUserName(currentInfo.getEmpName());

		// 必须为有效的数据
		billReceivableDto.setActive(FossConstants.YES);
		// 必须为非红单的数据
		billReceivableDto.setIsRedBack(FossConstants.NO);
		// 应收单类型
		billReceivableDto.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__WOODEN_OTEHR_RECEIVABLE);

		// 调用common下面的审核接口
		billReceivableService.reverseAuditPackingOtherBillReceivable(billReceivableDto, currentInfo);

		// 将单据信息、反审核人、反审核时间保存到操作日志表中
		// 循环插入日志
		for (BillReceivableEntity billReceivableEntity : recList) {
			// 设置操作日志单号
			operatingLogEntity.setOperateBillNo(billReceivableEntity.getReceivableNo());
			// 设置操作日志类型
			operatingLogEntity.setOperateBillType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__PACKING_OTHER_RECEIVABLE);
			// 设置操作类型
			operatingLogEntity.setOperateType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__REVERSE_AUDIT);
			// 调用插入日志接口
			operatingLogService.addOperatingLog(operatingLogEntity, currentInfo);
		}
	}

	/**
	 * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set
	 */
	public void setOrgAdministrativeInfoComplexService(OrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * @param packingRecAndPayDao the packingRecAndPayDao to set
	 */
	public void setPackingRecAndPayDao(IPackingRecAndPayDao packingRecAndPayDao) {
		this.packingRecAndPayDao = packingRecAndPayDao;
	}

	/**
	 * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set
	 */
	public void setOrgAdministrativeInfoComplexService(IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * @param billReceivableService the billReceivableService to set
	 */
	public void setBillReceivableService(IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	/**
	 * @param billPayableService the billPayableService to set
	 */
	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	/**
	 * @param operatingLogService the operatingLogService to set
	 */
	public void setOperatingLogService(OperatingLogService operatingLogService) {
		this.operatingLogService = operatingLogService;
	}

	/**
	 * @param statementOfAccountDService the statementOfAccountDService to set
	 */
	public void setStatementOfAccountDService(IStatementOfAccountDService statementOfAccountDService) {
		this.statementOfAccountDService = statementOfAccountDService;
	}

}
