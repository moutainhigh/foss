package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillRepaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillRepaymentConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.dao.IOnlinePaymentStatementsDao;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillRepaymentManageService;
import com.deppon.foss.module.settlement.pay.api.server.service.IOnlinePaymentStatementsService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillSOAOnlineResultDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillSOAOnlineResultListDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillStatementToPaymentQueryDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.StatementOnlineQueryDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * service:网上支付SERVICE实现
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2012-11-30 上午8:42:17
 */
public class OnlinePaymentStatementsService implements
		IOnlinePaymentStatementsService {

	/**
	 * 注入onlinePaymentStatementsDao
	 */
	private IOnlinePaymentStatementsDao onlinePaymentStatementsDao;

	/**
	 * 注入statementOfAccountService
	 */
	private IStatementOfAccountService statementOfAccountService;

	/**
	 * 注入billRepaymentService
	 */
	private IBillRepaymentService billRepaymentService;

	/**
	 * IBillRepaymentManageService
	 */
	private IBillRepaymentManageService billRepaymentManageService;

	/**
	 * 系统配置参数 Service接口
	 */
	private IConfigurationParamsService configurationParamsService;
	
	private static final Logger logger = LogManager
			.getLogger(OnlinePaymentStatementsService.class);

	/**
	 * 
	 * 查询对账单
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-30 上午8:43:37
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.
	 * IOnlinePaymentStatementsService#queryStatementOnline
	 * (com.deppon.foss.module.settlement.pay.api.shared.dto.
	 * StatementOnlineQueryDto)
	 */
	@Override
	public BillSOAOnlineResultListDto queryStatementOnline(
			StatementOnlineQueryDto queryDto) {
		logger.info("网上支付查询对账单entering service");
		// 校验查询参数
		// 客户编码不能为空
		if (StringUtil.isEmpty(queryDto.getCustomerCode())) {
			logger.error("客户编码为空！");
			throw new SettlementException("客户编码为空！", "");
		}
		// 页面最大条数不能为0
		if (queryDto.getPageSize() == 0) {
			logger.error("页面最大条数不能为0");
			throw new SettlementException("页面最大条数不能为0", "");
		}
		// 设置常量
		// 对账单明细父类型（应收单）
		queryDto.setParentBillType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE);
		// 确认状态
		queryDto.setConfirmStatus(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM);
		// 应收单子类型(始发应收、到达应收)
		List<String> receiableTypeList = new ArrayList<String>();
		receiableTypeList
				.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE);
		receiableTypeList
				.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE);
		queryDto.setBillTypeList(receiableTypeList);
		// 明细删除标记
		queryDto.setDeleteFlag(FossConstants.NO);
		// 存放查询结果
		List<BillSOAOnlineResultDto> resultList = new ArrayList<BillSOAOnlineResultDto>();
		BillSOAOnlineResultListDto resultListDto = new BillSOAOnlineResultListDto();
		//BillSOAOnlineResultDto resultDto = new BillSOAOnlineResultDto();
		// 按对账单号查询
		if (StringUtil.isNotEmpty(queryDto.getStatementBillNo())) {
			logger.info("网上支付查询对账单,按对账单号查询，客户编码：" + queryDto.getCustomerCode()
					+ "对账单号：" + queryDto.getStatementBillNo());
			BillSOAOnlineResultDto resultDto = onlinePaymentStatementsDao.queryStatementByNo(queryDto);
			resultList.add(resultDto);
			if (resultList.size() > 0) {
				resultListDto.setBillSOAOnlineResultDtoList(resultList);
				resultListDto.setCountNum(resultList.size());
			}
		}
		// 按日期查询
		else {
			// 如果pageNo为0,则统计总条数
			logger.info("" + queryDto.getPageNo());
			if (queryDto.getPageNo() == 0) {
				resultListDto = (BillSOAOnlineResultListDto) onlinePaymentStatementsDao
						.queryCountStatementByCustomer(queryDto);
			}
			if (resultListDto.getCountNum() > 0 || queryDto.getPageNo() != 0) {
				// 分页查询对账单信息
				resultList = onlinePaymentStatementsDao
						.queryStatementByCustomer(queryDto,
								queryDto.getPageNo(), queryDto.getPageSize());
				resultListDto.setBillSOAOnlineResultDtoList(resultList);
			}
		}

		logger.info("successful:service 网上支付查询对账单,客户编码："
				+ queryDto.getCustomerCode());
		return resultListDto;
	}

	/**
	 * 
	 * 锁定对账单
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-30 上午8:43:41
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IOnlinePaymentStatementsService#lockStatementOnline(com.deppon.foss.module.settlement.pay.api.shared.dto.StatementOnlineQueryDto)
	 */
	@Transactional
	@Override
	public void lockStatementOnline(StatementOnlineQueryDto queryDto) {
		logger.info("网上支付锁定对账单entering service");
		// 校验传入参数
		// 对账单号不能为空
		if (StringUtil.isEmpty(queryDto.getStatementBillNo())) {
			throw new SettlementException("对账单号为空！", "");
		}
		logger.info("网上支付锁定对账单,对账单号：" + queryDto.getStatementBillNo());
		// 锁定时间
		String sysLockTimeStr = configurationParamsService
				.queryConfValueByCode(ConfigurationParamsConstants.STL_STATEMENT_LOCK_MINUTE);
		if (StringUtils.isEmpty(sysLockTimeStr)){
			throw new SettlementException("网上支付锁定对账单异常，锁定时间系统参数配置为空，无法锁定");
		}
		queryDto.setLockTime(Integer.parseInt(sysLockTimeStr));
		// 校验对账单信息
		StatementOfAccountEntity entity = validateStatementOfAccountEntity(queryDto
				.getStatementBillNo());
		// 锁定对账单
		queryDto.setVersionNo(entity.getVersionNo());
		int row = onlinePaymentStatementsDao
				.lockOnlinePaymentStatementBill(queryDto);
		if (row != 1) {
			throw new SettlementException("数据库更新记录发生异常！", "");
		}
		logger.info("网上支付锁定对账单successful service");
	}

	/**
	 * 按对账单还款
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-30 上午8:43:46
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IOnlinePaymentStatementsService#paymentStatementOnline(com.deppon.foss.module.settlement.pay.api.shared.dto.StatementOnlineQueryDto)
	 */
	@Transactional
	@Override
	public void paymentStatementOnline(StatementOnlineQueryDto queryDto) {
		// 校验输入参数
		logger.info("网上支付按对账单还款entering service");
		// 校验传入参数
		// 对账单号不能为空
		if (StringUtil.isEmpty(queryDto.getStatementBillNo())) {
			logger.error("对账单号为空");
			throw new SettlementException("对账单号为空！", "");
		}
		logger.info("网上支付按对账单还款,对账单号：" + queryDto.getStatementBillNo());
		if (StringUtil.isEmpty(queryDto.getOnlineNo())) {
			logger.error("在线支付编码为空！");
			throw new SettlementException("在线支付编码为空！", "");
		}
		if (queryDto.getAmount() == null) {
			logger.error("还款金额为空！");
			throw new SettlementException("还款金额为空！", "");
		}
		if (queryDto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
			logger.error("还款金额小于等于0！");
			throw new SettlementException("还款金额小于等于0！", "");
		}
		// 判断在线支付编号是否已经使用
		BillRepaymentConditionDto dto = new BillRepaymentConditionDto();
		dto.setOnlinePaymentNo(queryDto.getOnlineNo());
		List<BillRepaymentEntity> list = billRepaymentService
				.queryBillRepaymentByCondition(dto);
		if (list.size() > 0) {
			logger.error("在线支付编号已被使用过，请确认是否重复付款！");
			throw new SettlementException("在线支付编号已被使用过，请确认是否重复付款！", "");
		}
		// 校验对账单信息
		StatementOfAccountEntity entity = validateStatementOfAccountEntity(queryDto
				.getStatementBillNo());
		// 校验还款金额和对账单未还金额是否相等
		if (queryDto.getAmount().compareTo(entity.getUnpaidAmount()) != 0) {
			logger.error("还款金额与对账单未还金额不相等！");
			throw new SettlementException("还款金额与对账单未还金额不相等！", "");
		}
		logger.info("网上支付按对账单还款校验通过，开始还款");
		BillStatementToPaymentQueryDto repaymentQueryDto = new BillStatementToPaymentQueryDto();
		List<String> statementBillNos = new ArrayList<String>();
		statementBillNos.add(entity.getStatementBillNo());
		repaymentQueryDto.setStatementBillNos(statementBillNos
				.toArray(new String[statementBillNos.size()]));
		// 还款方式
		repaymentQueryDto
				.setRepaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE);
		// 还款金额
		repaymentQueryDto.setRepaymentAmount(queryDto.getAmount());
		// 在线支付编号
		repaymentQueryDto.setOnlinePaymentNo(queryDto.getOnlineNo());
		// 客户编码、名称
		repaymentQueryDto.setCustomerCode(entity.getCustomerCode());
		repaymentQueryDto.setCustomerName(entity.getCustomerName());
		// 创建当前登录信息
		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmpCode(SettlementConstants.BHO_CODE);
		employee.setEmpName(SettlementConstants.BHO_NAME);
		UserEntity user = new UserEntity();
		user.setEmployee(employee);
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode(entity.getCreateOrgCode());
		dept.setName(entity.getCreateOrgName());
		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		// 还款核销
		billRepaymentManageService.repayment(repaymentQueryDto, currentInfo);
		logger.info("网上支付按对账单还款successful service");
	}

	/**
	 * 
	 * 锁定对账单和按对账单还款时校验对账单信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-30 下午3:30:35
	 */
	private StatementOfAccountEntity validateStatementOfAccountEntity(
			String statementNo) {
		// 根据对账单号查询对账单
		StatementOfAccountEntity statementEntity = statementOfAccountService
				.queryByStatementNo(statementNo);
		// 校验对账单信息
		if (statementEntity == null) {
			throw new SettlementException("对账单号不存在！", "");
		}
		// 判断对账单的确认状态
		if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM
				.equals(statementEntity.getConfirmStatus())) {
			throw new SettlementException("对账单处于未确认状态，不能锁定", "");
		}
		// 本期剩余应收金额是否大于0
		if (statementEntity.getUnpaidAmount().compareTo(BigDecimal.ZERO) <= 0) {
			throw new SettlementException("对账单本期未还金额小于等于0", "");
		}
		return statementEntity;
	}

	public IOnlinePaymentStatementsDao getOnlinePaymentStatementsDao() {
		return onlinePaymentStatementsDao;
	}

	public void setOnlinePaymentStatementsDao(
			IOnlinePaymentStatementsDao onlinePaymentStatementsDao) {
		this.onlinePaymentStatementsDao = onlinePaymentStatementsDao;
	}

	public IStatementOfAccountService getStatementOfAccountService() {
		return statementOfAccountService;
	}

	public void setStatementOfAccountService(
			IStatementOfAccountService statementOfAccountService) {
		this.statementOfAccountService = statementOfAccountService;
	}

	public IBillRepaymentService getBillRepaymentService() {
		return billRepaymentService;
	}

	public void setBillRepaymentService(
			IBillRepaymentService billRepaymentService) {
		this.billRepaymentService = billRepaymentService;
	}

	public IBillRepaymentManageService getBillRepaymentManageService() {
		return billRepaymentManageService;
	}

	public void setBillRepaymentManageService(
			IBillRepaymentManageService billRepaymentManageService) {
		this.billRepaymentManageService = billRepaymentManageService;
	}

	
	public IConfigurationParamsService getConfigurationParamsService() {
		return configurationParamsService;
	}

	
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

}
