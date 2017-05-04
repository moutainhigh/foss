package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.server.service.impl.complex.OrgAdministrativeInfoComplexService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.service.impl.BillPayableService;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IOverdueSFPaymentApplyDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.IOverdueSFPaymentApplyService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.OverdueSFPaymentApplyEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OverdueSFPaymentApplyDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OverdueSFPaymentApplyQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OverdueSFPaymentApplyResultDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

public class OverdueSFPaymentApplyService implements IOverdueSFPaymentApplyService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OverdueSFPaymentApplyService.class);
	// dao
	private IOverdueSFPaymentApplyDao overdueSFPaymentApplyDao;
	// 部门service
	private OrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	// 应付单servide
	private BillPayableService billPayableService;

	/**
	 * <p>
	 * 提交超时装卸费付款申请
	 * </p>
	 * 
	 * @author 105762
	 * @date 2014-3-28 下午3:54:57
	 * @param dto
	 * @see
	 */
	@Override
	public void applyOrAudit(OverdueSFPaymentApplyQueryDto dto, CurrentInfo currentInfo) {
		LOGGER.info("service 处理申请or审核 开始");
		// 校验
		validateApplyDto(dto, currentInfo);

		// 创建实体
		List<OverdueSFPaymentApplyEntity> entityList = buildEntityList(dto, currentInfo);

		// 判断申请还是审核
		if (dto.getStatus().equals(SettlementDictionaryConstants.OVERDUE_SERVICE_FEE_AUDIT_STATUS_PROCESSING)) {
			// 申请记录
			for (OverdueSFPaymentApplyEntity en : entityList) {
				overdueSFPaymentApplyDao.insertOrUpdate(en);
			}
		} else {
			// 审核记录
			for (OverdueSFPaymentApplyEntity en : entityList) {
				overdueSFPaymentApplyDao.updateForAudit(en);
			}
		}
		LOGGER.info("service 处理申请or审核 结束");
	}

	/**
	 * <p>
	 * 建立申请实体 包括申请和审核两个操作
	 * </p>
	 * 
	 * @author 105762
	 * @date 2014-5-8 上午11:08:33
	 * @param dto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	private List<OverdueSFPaymentApplyEntity> buildEntityList(OverdueSFPaymentApplyQueryDto dto, CurrentInfo currentInfo) {
		LOGGER.info("创建实体 开始");
		// 获取对应应付单实体
		List<BillPayableEntity> payableList = billPayableService.queryByPayableNOs(Arrays.asList(dto.getPayableNos()), FossConstants.ACTIVE);

		// 校验下应付单
		if (payableList == null || CollectionUtils.isEmpty(payableList)) {
			throw new SettlementException("未查询到对应应付单");
		}

		// 批量操作校验
		BillPayableEntity firstEntity = payableList.get(0);
		for (BillPayableEntity e : payableList) {

			// 客户编码要一致
			if (!StringUtils.equals(firstEntity.getCustomerCode(), e.getCustomerCode())) {
				throw new SettlementException("应付单客户编码不一致，不能进行批量操作");
			} else if (StringUtils.isEmpty(firstEntity.getCustomerCode())) {
				// 客户编码为空则联系人名称和手机号要一致
				if (!StringUtils.equals(firstEntity.getCustomerContactName(), e.getCustomerContactName())
						|| !StringUtils.equals(firstEntity.getCustomerPhone(), e.getCustomerPhone())) {
					throw new SettlementException("应付单联系人编码和电话不一致，不能进行批量操作");
				}
			}
			// 装卸费应付单要生效
			if (!FossConstants.ACTIVE.equals(e.getEffectiveStatus())) {
				throw new SettlementException("应付单" + e.getPayableNo() + "未生效,不能进行后续操作");
			}
		}

		// 批量操作应付单号必须存在对应有效数据
		for (String payableNo : dto.getPayableNos()) {
			Boolean isExsit = false;
			for (BillPayableEntity e : payableList) {
				if (e.getPayableNo().equals(payableNo)) {
					isExsit = true;
					break;
				}
			}
			if (!isExsit) {
				throw new SettlementException("参数应付单号" + payableNo + "不存在对应有效应付单");
			}
		}
		// 应付单状态若为"已付款"则禁止进行任何操作
		for (BillPayableEntity e : payableList) {
			if (e.getPayStatus().equals(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__YES)) {
				throw new SettlementException("装卸费应付单号" + e.getPayableNo() + "付款状态为‘已付款’，不能进行后续操作");
			}
		}

		// 建造的实体
		List<OverdueSFPaymentApplyEntity> entityList = new ArrayList<OverdueSFPaymentApplyEntity>();
		for (BillPayableEntity payableEntity : payableList) {
			OverdueSFPaymentApplyEntity en = new OverdueSFPaymentApplyEntity();
			// 有效
			en.setActive(FossConstants.ACTIVE);
			// 申请处理
			if (dto.getStatus().equals(SettlementDictionaryConstants.OVERDUE_SERVICE_FEE_AUDIT_STATUS_PROCESSING)) {
				// 申请状态更新
				en.setAuditStatus(SettlementDictionaryConstants.OVERDUE_SERVICE_FEE_AUDIT_STATUS_PROCESSING);
				// 申请人信息
				en.setApplyOrgCode(currentInfo.getCurrentDeptCode());
				en.setApplyOrgName(currentInfo.getCurrentDeptName());
				en.setApplyUserName(currentInfo.getEmpName());
				en.setApplyUserCode(currentInfo.getEmpCode());
				// 申请原因
				en.setReason(dto.getReason());

				// 审核处理
			} else if (dto.getStatus().equals(SettlementDictionaryConstants.OVERDUE_SERVICE_FEE_AUDIT_STATUS_PASSED)
					|| dto.getStatus().equals(SettlementDictionaryConstants.OVERDUE_SERVICE_FEE_AUDIT_STATUS_REJECTED)) {
				// 审核人信息
				en.setAuditOrgCode(currentInfo.getCurrentDeptCode());
				en.setAuditOrgName(currentInfo.getCurrentDeptName());
				en.setAuditUserName(currentInfo.getEmpName());
				en.setAuditUserCode(currentInfo.getEmpCode());

				en.setAuditStatus(dto.getStatus());
				// 审核备注
				en.setNotes(dto.getNotes());
			}
			Date now = new Date();
			// 对应运单号 应付单号 应付单ID
			en.setWaybillNo(payableEntity.getWaybillNo());
			en.setPayableNo(payableEntity.getPayableNo());
			en.setPayableId(payableEntity.getId());
			// 时间
			en.setModifyTime(now);
			en.setCreateTime(now);

			// 加入List
			entityList.add(en);
		}
		LOGGER.info("创建实体 结束");
		return entityList;
	}

	/**
	 * <p>
	 * 校验申请dto
	 * </p>
	 * 
	 * @author 105762
	 * @date 2014-5-7 下午5:56:07
	 * @see
	 */
	private void validateApplyDto(OverdueSFPaymentApplyQueryDto dto, CurrentInfo currentInfo) {
		LOGGER.info("校验申请参数开始");
		// 校验dto
		SettlementUtil.valideIsNull(dto, "参数 OverdueSFPaymentApplyDto 为空");

		// 校验currentInfo
		SettlementUtil.valideIsNull(currentInfo, "参数 currentInfo 为空");

		// 校验 应付单号
		SettlementUtil.valideIsNull(dto.getPayableNos(), "申请付款的应付单号为空");

		if (dto.getPayableNos().length < 1) {
			new SettlementException("申请付款的应付单号为空");
		}

		SettlementUtil.valideIsNull(dto.getStatus(), "申请状态为空");

		// 申请
		if (dto.getStatus().equals(SettlementDictionaryConstants.OVERDUE_SERVICE_FEE_AUDIT_STATUS_PROCESSING)) {
			// 校验申请原因
			SettlementUtil.valideIsNull(dto.getReason(), "申请原因为空");
		} else if (dto.getStatus().equals(SettlementDictionaryConstants.OVERDUE_SERVICE_FEE_AUDIT_STATUS_REJECTED)) {
			SettlementUtil.valideIsNull(dto.getNotes(), "审批为“不同意”必须填写审批备注");
		} else if (dto.getStatus().equals(SettlementDictionaryConstants.OVERDUE_SERVICE_FEE_AUDIT_STATUS_PASSED)) {
			// 同意不做其他校验
		} else {
			// 其他状态报错
			new SettlementException("申请状态不合法");
		}

		LOGGER.info("校验申请参数结束");
	}

	/**
	 * <p>
	 * 查询超时装卸费付款申请单
	 * </p>
	 * 
	 * @author 105762
	 * @date 2014-4-29 下午3:24:39
	 * @param dto
	 * @param currentInfo
	 * @return
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.IOverdueSFPaymentApplyService#query(com.deppon.foss.module.settlement.consumer.api.shared.dto.OverdueSFPaymentApplyQueryDto,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public OverdueSFPaymentApplyResultDto query(OverdueSFPaymentApplyQueryDto dto, CurrentInfo currentInfo) {
		LOGGER.info("查询开始");
		// 非空校验
		if (dto == null) {
			throw new SettlementException("查询参数为空");
		}
		if (currentInfo == null) {
			throw new SettlementException("用户未登录");
		}
		// 检查参数
		validateQueryDto(dto);

		// 预处理下dto
		preprocessQueryDto(dto, currentInfo);
		// resultDto
		OverdueSFPaymentApplyResultDto resultDto = new OverdueSFPaymentApplyResultDto();
		/* 按记账日期查询 */
		if (SettlementConstants.TAB_QUERY_BY_ACCOUNT_DATE.equals(dto.getQueryType())) {

			// 查询总条数
			int totalCount = 0;
			totalCount = overdueSFPaymentApplyDao.queryCountByAccountDate(dto);

			// 总条数为零
			if (totalCount == 0) {
				new SettlementException("按记账日期未查询到数据");
			}
			// 设置总条数
			resultDto.setTotalCount(totalCount);
			// 按记账日期查询
			resultDto.setOverdueSFPaymentApplyDtos(overdueSFPaymentApplyDao.queryByAccountDate(dto));
		} else if (SettlementConstants.TAB_QUERY_BY_WAYBILL_NO.equals(dto.getQueryType())) {
			// 按运单号查询
			resultDto.setOverdueSFPaymentApplyDtos(overdueSFPaymentApplyDao.queryBySourceBillNo(dto));
			// 设置总条数
			if (resultDto.getOverdueSFPaymentApplyDtos().size() == 0) {
				throw new SettlementException("按运单号未查询到数据");
			}
			resultDto.setTotalCount(resultDto.getOverdueSFPaymentApplyDtos().size());
		} else if (SettlementConstants.TAB_QUERY_BY_PAYABLE_NO.equals(dto.getQueryType())) {
			// 按应付单号查询
			resultDto.setOverdueSFPaymentApplyDtos(overdueSFPaymentApplyDao.queryByPayableNo(dto));
			if (resultDto.getOverdueSFPaymentApplyDtos().size() == 0) {
				throw new SettlementException("按应付单号未查询到数据");
			}
			// 设置总条数
			resultDto.setTotalCount(resultDto.getOverdueSFPaymentApplyDtos().size());
		}
		LOGGER.info("查询完成");
		return resultDto;
	}

	/**
	 * <p>
	 * 预处理查询dto
	 * </p>
	 * 
	 * @author 105762
	 * @date 2014-4-28 下午3:40:29
	 * @param dto
	 * @param currentInfo
	 * @see
	 */
	private void preprocessQueryDto(OverdueSFPaymentApplyQueryDto dto, CurrentInfo currentInfo) {
		LOGGER.info("预处理查询dto 开始");
		// 默认查询有效单据
		dto.setActive(FossConstants.YES);
		// 设置当前登录人
		dto.setEmpCode(currentInfo.getEmpCode());
		// 按记账日期查询
		if (SettlementConstants.TAB_QUERY_BY_ACCOUNT_DATE.equals(dto.getQueryType())) {
			// 结束时间+1天
			dto.setEndAccountDate(DateUtils.addDays(dto.getEndAccountDate(), 1));

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
		} // 按运单号查询
		else if (SettlementConstants.TAB_QUERY_BY_WAYBILL_NO.equals(dto.getQueryType())) {
			// dto.setWaybillNoList(Arrays.asList(dto.getWaybillNos()));
			// 按应付单号查询
		} else if (SettlementConstants.TAB_QUERY_BY_PAYABLE_NO.equals(dto.getQueryType())) {
			// dto.setPayableNoList(Arrays.asList(dto.getPayableNos()));
		}
		LOGGER.info("预处理查询dto 结束");
	}

	/**
	 * <p>
	 * 校验参数是否合法
	 * </p>
	 * 
	 * @author 105762
	 * @date 2014-4-28 下午3:40:14
	 * @param dto
	 * @see
	 */
	private void validateQueryDto(OverdueSFPaymentApplyQueryDto dto) {
		LOGGER.info("校验参数是否合法 开始");
		// 查询类型
		if (StringUtil.isBlank(dto.getQueryType())) {
			throw new SettlementException("查询参数中查询类型为空");
		}
		// 按记账日期查询
		if (SettlementConstants.TAB_QUERY_BY_ACCOUNT_DATE.equals(dto.getQueryType())) {
			// 记账日期不为空
			if (dto.getBeginAccountDate() == null || dto.getEndAccountDate() == null) {
				throw new SettlementException("查询参数中记账日期为空");
			}
			// 按运单号查询
		} else if (SettlementConstants.TAB_QUERY_BY_WAYBILL_NO.equals(dto.getQueryType())) {
			if (dto.getWaybillNos() == null || dto.getWaybillNos().length == 0) {
				throw new SettlementException("查询参数中运单号为空");
			}
			// 按应付单号查询
		} else if (SettlementConstants.TAB_QUERY_BY_PAYABLE_NO.equals(dto.getQueryType())) {
			if (dto.getPayableNos() == null || dto.getPayableNos().length == 0) {
				throw new SettlementException("查询参数中应付单号为空");
			}
		}
		LOGGER.info("校验参数是否合法 结束");
	}

	/**
	 * <p>
	 * 按应付单号查询付款申请对应的有效单据
	 * </p>
	 * 
	 * @author 105762
	 * @date 2014-5-13 下午4:47:31
	 * @param payableNo
	 * @return
	 * @see
	 */
	public OverdueSFPaymentApplyDto queryOneByPayableNo(String payableNo) {
		OverdueSFPaymentApplyQueryDto dto = new OverdueSFPaymentApplyQueryDto();
		String[] payableNos = { "" };
		payableNos[0] = payableNo;
		dto.setPayableNos(payableNos);
		dto.setActive(FossConstants.ACTIVE);
		return overdueSFPaymentApplyDao.queryByPayableNo(dto).get(0);
	}

	/**
	 * @param overdueSFPaymentApplyDao
	 *            the overdueSFPaymentApplyDao to set
	 */
	public void setOverdueSFPaymentApplyDao(IOverdueSFPaymentApplyDao overdueSFPaymentApplyDao) {
		this.overdueSFPaymentApplyDao = overdueSFPaymentApplyDao;
	}

	/**
	 * @param orgAdministrativeInfoComplexService
	 *            the orgAdministrativeInfoComplexService to set
	 */
	public void setOrgAdministrativeInfoComplexService(OrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * @param billPayableService
	 *            the billPayableService to set
	 */
	public void setBillPayableService(BillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}
}
