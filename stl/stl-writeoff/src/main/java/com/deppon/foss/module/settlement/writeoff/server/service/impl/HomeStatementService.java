package com.deppon.foss.module.settlement.writeoff.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IHomeStatementDao;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IHomeStatementService;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.HomeStatementDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.HomeStatementDto;

/**
 * 家装对账单
 * 
 * @ClassName: HomeStatementService
 * @Description: (这里用一句话描述这个类的作用)
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2015-11-24 下午7:34:38
 * 
 */
public class HomeStatementService implements IHomeStatementService {
	private static Logger logger = LoggerFactory
			.getLogger(HomeStatementService.class);
	/**
	 * 注入Dao
	 */
	private IHomeStatementDao homeStatementDao;

	/**
	 * 结算通用服务
	 */
	private ISettlementCommonService settlementCommonService;

	/**
	 * 家装对账单查询应付单应收单
	 * 
	 * @author &269052 |zhouyuan008@deppon.com
	 */
	@Override
	public HomeStatementDto queryHomeStatementD(HomeStatementDto dto,
			int start, int limit) {
		logger.info("家装对账单查询应付单应收单开始………………");
		// 家装对账单明细
		List<HomeStatementDEntity> homeStatementDEntity = new ArrayList<HomeStatementDEntity>();
		// 家装DTO
		HomeStatementDto homeStatementDto = new HomeStatementDto();
		// 获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		// 设置当前用户的所在部门
		dto.setOrgCode(currentInfo.getCurrentDeptCode());
		// 总行数
		int count = 0;
		// 按时间查询
		if (SettlementConstants.TAB_QUERY_BY_DATE.equals(dto.getQueryTabType())) {
			/**
			 * 设置客户勾选那个按钮
			 */
			dto.setQueryReceive(dto.getBillTypes()[0]);// 查应收单
			dto.setQueryPayable(dto.getBillTypes()[1]);// 查应付单
			// 获取总行数
			count = homeStatementDao.getCountByDate(dto);
			if (count > 0) {
				homeStatementDEntity = homeStatementDao
						.queryHomeStatementByDate(dto, start, limit);
			}
		} else {
			// 按单号查询
			logger.info("按单号查询：------------------");
			homeStatementDEntity = homeStatementDao
					.queryHomeStatementByNumbers(dto, start, limit);
			logger.info("单号查询结果" + homeStatementDEntity);
			// 按单号查询，这总行数为单号个数
			count = dto.getNumbers().size();
		}
		/**
		 * 设置家装对账单详细
		 */
		homeStatementDto.setHomeStatementDList(homeStatementDEntity);
		// 设置总行数
		homeStatementDto.setCount(count);
		logger.info("家装对账单查询应付单应收单结束………………");
		return homeStatementDto;
	}
 
	/**
	 * 生成家装对账单
	 * 
	 * @ClassName: homeStatementSave
	 * @Description: (这里用一句话描述这个类的作用)
	 * @author &269052 |zhouyuan008@deppon.com
	 * @date 2015-11-24 下午7:34:38
	 */
	@Transactional
	@Override
	public HomeStatementDto homeStatementSave(HomeStatementDto dto) {
		logger.info("********生成家装对账单Service*********");
		// 获取对账单单号
		String statementNo = settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.DZ);
		// 设置对账单单号
		dto.setStatementBillNo(statementNo);
		// 获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		// 获取当前用户工号
		dto.setEmpCode(currentInfo.getEmpCode());
		// 获取当前用户姓名
		dto.setEmpName(currentInfo.getEmpName());
		// 获取当前用户所在部门
		dto.setOrgCode(currentInfo.getCurrentDeptCode());
		/**
		 * 判断用户点击了应收单还是应付单
		 */
		String queryReceive = "true";
		String queryPayable = "true";
		//
		dto.setQueryReceive(queryReceive);// 查应收单
		dto.setQueryPayable(queryPayable);// 查应付单
		// 插入行数
		int insertDRows = 0;
		logger.info("*************保存对账单明细开始*************");
		if (SettlementConstants.TAB_QUERY_BY_DATE.equals(dto.getQueryTabType())) {
			// 判断客户编码是否为空
			if (null == dto.getCustomerCode()|| "".equals(dto.getCustomerCode())) {
				throw new SettlementException("客户编码为空不可以生成对账单！");
			}

			/**
			 * 判断用户点击了应收单还是应付单
			 */
			queryReceive = dto.getBillTypes()[0];
			queryPayable = dto.getBillTypes()[1];
			//
			dto.setQueryReceive(queryReceive);// 查应收单
			dto.setQueryPayable(queryPayable);// 查应付单
			// 按时间客户生成对账单明细
			insertDRows = homeStatementDao.homeStatementDSaveByCustomer(dto);
		} else {
			// 按单号生成对账单明细
			insertDRows = homeStatementDao.homeStatementDSaveByNumber(dto);
		}
		logger.info("*************保存对账单明细结束*************");

		// 获取所有生成的对账单明细
		List<HomeStatementDEntity> list = homeStatementDao
				.queryHomeDByStatementBillNo(dto);
		logger.info("对账单明细实体:" + list);
		// 设置对账单金额
		sumRecAndPayAmount(dto, list);
		logger.info("---------------------");
		logger.info("计算之后的应付金额：" + dto.getPeriodPayAmount());
		logger.info("计算之后的应收金额：" + dto.getPeriodRecAmount());
		logger.info("计算之后的应付金额：" + dto.getPeriodAmount());
		logger.info("---------------------");

		// 生成对账单
		logger.info("生成对账单：");
		int insertRows = homeStatementDao.homeStatementSaveByStatementBillNo(dto); //
		logger.info("生成对账单：");
		// 判断是否生成一条对账单
		if (insertRows != 1) {
			throw new SettlementException("生成对账单失败，插入条数大于1，请确认应收单应付单客户是否一致");
		}
		/**
		 * 更新应付单或应收单
		 */
		int updatePayRows = 0;
		int updateRecRows = 0;
		
		if ("true".equals(queryReceive) && "true".equals(queryPayable)) {
			// 更新应付单
			updatePayRows = homeStatementDao.homePayUpdateByStatementBillNo(dto);
			// 更新应收单
			updateRecRows = homeStatementDao.homeRecUpdateByStatementBillNo(dto);
		} else if ("true".equals(queryReceive) && "false".equals(queryPayable)) {
			// 更新应收单
			updateRecRows = homeStatementDao.homeRecUpdateByStatementBillNo(dto);
		} else if ("false".equals(queryReceive) && "true".equals(queryPayable)) {
			// 更新应付单
			updatePayRows = homeStatementDao.homePayUpdateByStatementBillNo(dto);
		} else {
			throw new SettlementException("应收单和应付单至少要选择一个！");
		}

		// 判断更新的应收应付单据总条数是否和生成的对账单明细条数是否相等
		if ((updatePayRows + updateRecRows) != insertDRows) {
			throw new SettlementException("对账单明细生成条数和应付单应收单生成条数不一致，请重新查询！");
		}
		
		// 返回参数
		logger.info("********生成家装对账单Service*********");
		return dto;
	}

	/**
	 * 
	 * @Title: sumRecAndPayAmount
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	private void sumRecAndPayAmount(HomeStatementDto dto,
			List<HomeStatementDEntity> list) {
		// 本期发生金额
		BigDecimal periodAmount = BigDecimal.ZERO;
		// 本期应收金额
		BigDecimal periodRecAmount = BigDecimal.ZERO;
		// 本期应付金额
		BigDecimal periodPayAmount = BigDecimal.ZERO;

		// 总金额
		BigDecimal amount = BigDecimal.ZERO;
		// 总的已核销金额
		BigDecimal sumVerifyAmount = BigDecimal.ZERO;
		// 总的未核销金额
		//BigDecimal sumUnverifyAmount = BigDecimal.ZERO;
		// 循环对账单明细计算应收应付金额
		for (HomeStatementDEntity dEntity : list) {
			// 计算应付金额
			/**
			 * 判断是应收还是应付
			 */
			String str = dEntity.getPayableNo().substring(0, 2);
			if ("YF".equals(str)) {
				// 计算应付金额
				periodPayAmount = periodPayAmount.add(dEntity
						.getUnverifyAmount());
			}
			if ("YS".equals(str)) {
				// 计算应收金额
				periodRecAmount = periodRecAmount.add(dEntity
						.getUnverifyAmount());
			}
			// 计算总金额
			amount = amount.add(dEntity.getAmount());
			// 计算总的已核销金额
			sumVerifyAmount = sumVerifyAmount.add(dEntity.getVerifyAmount());
			// 计算总的未核销金额
			/*sumUnverifyAmount = sumUnverifyAmount.add(dEntity
					.getUnverifyAmount());*/
		}

		// 判断应收应付金额大小
		if (periodPayAmount.compareTo(periodRecAmount) > 0) {
			// 计算本期发生金额，应付金额减去应收金额
			periodAmount = periodPayAmount.subtract(periodRecAmount);
			// 应付大于应收，对账单为应付对账单
			// dto.setBillType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__BILL_TYPE__YF_WOODEN_ACCOUNT);
			dto.setBillType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__BILL_TYPE__YF_HOME_ACCOUNT);// "应付家装对账单"
		} else {
			// 计算本期发生金额，应收金额减去应付金额
			periodAmount = periodRecAmount.subtract(periodPayAmount);
			// 应收大于应付，对账单为应收对账单
			// dto.setBillType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__BILL_TYPE__YS_WOODEN_ACCOUNT);
			dto.setBillType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__BILL_TYPE__YS_HOME_ACCOUNT);// "应收家装对账单"
		}
		// 设置对账单应付金额
		dto.setPeriodPayAmount(periodPayAmount);
		// 设置对账单应收金额
		dto.setPeriodRecAmount(periodRecAmount);
		// 设置发生金额
		dto.setPeriodAmount(periodAmount);
		// 设置未核销金额
		dto.setUnpaidAmount(periodAmount);
		// 设置总金额
		dto.setAmount(periodAmount);
		// 设置总的未核销金额
		dto.setSumUnverifyAmount(periodAmount);
		// 设置总的已核销金额
		dto.setSumVerifyAmount(sumVerifyAmount);
		logger.info("应付金额：" + periodPayAmount);
		logger.info("应收金额：" + periodRecAmount);
		logger.info("本期发生金额：" + periodAmount);
	}

	/********** getter/setter*****************/
	public IHomeStatementDao getHomeStatementDao() {
		return homeStatementDao;
	}
	
	public void setHomeStatementDao(IHomeStatementDao homeStatementDao) {
		this.homeStatementDao = homeStatementDao;
	}

	public ISettlementCommonService getSettlementCommonService() {
		return settlementCommonService;
	}

	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

}
