package com.deppon.foss.module.settlement.common.server.service.impl;

import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.settlement.common.api.server.dao.ICreditOrgEntityDao;
import com.deppon.foss.module.settlement.common.api.server.service.ICreditOrgService;
import com.deppon.foss.module.settlement.common.api.shared.domain.CreditOrgEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CreditOrgAlarmDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CreditOrgDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jgroups.util.UUID;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 组织临欠额度管理
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-11-17 上午8:36:19
 */
public class CreditOrgService implements ICreditOrgService {

	private static final Logger LOGGER = LogManager.getLogger(CreditOrgService.class);

	/**
	 * 部门收支平衡表Dao
	 */
	private ICreditOrgEntityDao creditOrgEntityDao;

	/**
	 * 查询配置参数表
	 */
	private IConfigurationParamsService configurationParamsService;

	/**
	 * 部门服务
	 */
	private ISaleDepartmentService saleDepartmentService;

	/**
	 * 新加信息组织信息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-17 上午8:39:13
	 * @see com.deppon.foss.module.settlement.common.api.server.service.ICreditOrgService#addCreditOrg(com.deppon.foss.module.settlement.common.api.shared.domain.CreditOrgEntity)
	 */
	@Override
	public void addCreditOrg(CreditOrgEntity item) {

		// 检查待添加的
		if (null == item) {
			throw new SettlementException("传入参数为空!");
		}
		// 检查客户编码
		if (StringUtils.isEmpty(item.getOrgCode())) {
			throw new SettlementException("传入组织编码为空!");
		}
		// 检查已用额度
		if (null == item.getUsedAmount()) {
			throw new SettlementException("传入已用额度为空!");
		} else if (item.getUsedAmount().compareTo(BigDecimal.ZERO) < 0) {
			throw new SettlementException("传入已用额度小于0!");
		}
		// 检查是否超期
		if (StringUtils.isEmpty(item.getIsOverdue())) {
			throw new SettlementException("是否超期欠款标记为空!");
		} else if (!item.getIsOverdue().equals(FossConstants.YES)
				&& !item.getIsOverdue().equals(FossConstants.NO)) {
			throw new SettlementException("是否超期欠款标记为空只能为Y或N!");
		}

		// 如果Id为空，初始化Id
		if (StringUtils.isEmpty(item.getId())) {
			item.setId(UUID.randomUUID().toString());
		}
		// 初始化
		Date sysDate = new Date();
		item.setCreateTime(sysDate);
		item.setBusinessDate(sysDate);

		LOGGER.info("开始保存部门收支平衡表，部门编码：" + item.getOrgCode() + ",组织名称:" + item.getOrgName());

		// 执行新加
		int result = this.creditOrgEntityDao.addCreditOrg(item);
		// 判断返回行数
		if (result != 1) {

			LOGGER.info("部门平衡表信息保存失败!");

			throw new SettlementException("部门平衡表信息保存失败!");
		}

		LOGGER.info("保存部门收支平衡表结束");

	}

	/**
	 * 更新部门收支平衡信息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-17 上午8:39:46
	 * @see com.deppon.foss.module.settlement.common.api.server.service.ICreditOrgService#updateOrgCredit(com.deppon.foss.module.settlement.common.api.shared.domain.CreditOrgEntity)
	 */
	@Override
	public void updateOrgCredit(CreditOrgEntity item) {
		if (item != null) {

			LOGGER.info("开始更新部门收支平衡信息，部门编码:" + item.getOrgCode() + ",部门名称" + item.getOrgName());

			int updateRows = this.creditOrgEntityDao.updateCreditOrg(item);
			if (updateRows != 1) {

				LOGGER.info("更新部门收支平衡信息出错！");

				throw new SettlementException("更新结果不唯一，没有找到该部门的临欠额度！");
			}

			LOGGER.info("更新部门收支平衡信息结束！");
		} else {
			throw new SettlementException("参数为空！");
		}

	}

	/**
	 * 根据组织编码查询基本临欠额度信息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-17 上午11:07:06
	 * @see com.deppon.foss.module.settlement.common.api.server.service.ICreditOrgService#queryByOrgCode(java.lang.String)
	 */
	@Override
	public CreditOrgEntity queryByOrgCode(String orgCode) {
		if (StringUtils.isNotEmpty(orgCode)) {
			return this.creditOrgEntityDao.queryByOrgCode(orgCode);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 
	 * 按组织查询可用额度信息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-17 上午10:50:43
	 * @see com.deppon.foss.module.settlement.common.api.server.service.ICreditOrgService#queryDebitByOrgCode(java.lang.String)
	 */
	@Override
	public CreditOrgDto queryDebitByOrgCode(String orgCode) {
		if (StringUtils.isNotEmpty(orgCode)) {
			return this.creditOrgEntityDao.queryDebitByOrgCode(orgCode);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 更新已用额度
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-17 上午10:49:36
	 * @see com.deppon.foss.module.settlement.common.api.server.service.ICreditOrgService#updateUsedAmount(com.deppon.foss.module.settlement.common.api.shared.domain.CreditOrgEntity)
	 */
	@Override
	public void updateUsedAmount(String orgCode, BigDecimal amount) {

		if (StringUtils.isNotEmpty(orgCode) && amount != null) {

			LOGGER.info("更新部门收支平衡表的已用额度信息,部门编码:" + orgCode + ",金额：" + amount);

			// 生成一个组织欠款额度信息
			CreditOrgEntity entity = new CreditOrgEntity();
			entity.setOrgCode(orgCode);
			entity.setUsedAmount(amount);
			Date date = new Date();
			entity.setBusinessDate(date);
			entity.setModifyTime(date);

			// 更新失败不错强制要求
			this.creditOrgEntityDao.updateUsedAmount(entity);

			LOGGER.info("更新部门收支平衡表的已用额度信息结束");

		} else {
			throw new SettlementException("内部错误，参数不正确！");
		}
	}

	/**
	 * 更新超期欠款标志
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-17 上午10:47:32
	 * @see com.deppon.foss.module.settlement.common.api.server.service.ICreditOrgService#updateOverdueState(com.deppon.foss.module.settlement.common.api.shared.domain.CreditOrgEntity)
	 */
	@Override
	public void updateOverdueState(String orgCode, String isOverdue, int maxAccountDays,
			String notes) {
		if (StringUtils.isNotEmpty(orgCode) && StringUtils.isNotEmpty(isOverdue)) {
			// 实现化组织对象
			CreditOrgEntity entity = new CreditOrgEntity();
			// 组织编码
			entity.setOrgCode(orgCode);
			// 是否超期
			entity.setIsOverdue(isOverdue);

			// 超期欠款最大天数
			entity.setMaxAccountDays(maxAccountDays);

			// 如果组织的超期欠款标记为空时，清空先前的备注信息
			if (isOverdue.equals(FossConstants.NO)) {
				entity.setNotes(null);
			} else {
				entity.setNotes(notes);
			}

			LOGGER.info("更新组织超期欠款标志,组织编码: " + orgCode + ",超期欠款标记:" + isOverdue);

			// 执行更新
			int updateRows = this.creditOrgEntityDao.updateOverdueState(entity);
			// 判断返回行数,不唯一时通过异常抛出
			if (updateRows != 1) {

				LOGGER.info("更新组织超期欠款标志发生错误，更新行数不唯一！");

				throw new SettlementException("内部异常，更新行数不唯一,可能没有找到该记录！");
			}

			LOGGER.info("更新组织超期欠款标志结束！");

		} else {
			throw new SettlementException("内部错误，参数不正确");
		}

	}

	/**
	 * 
	 * 查找部门收支平衡总行数
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-17 上午10:46:57
	 * @see com.deppon.foss.module.settlement.common.api.server.service.ICreditOrgService#queryTotalRows()
	 */
	@Override
	public int queryTotalRows() {
		return this.creditOrgEntityDao.queryTotalRows();
	}

	/**
	 * 
	 * 通过分页的形式查询欠款组织信息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-17 上午10:46:12
	 * @see com.deppon.foss.module.settlement.common.api.server.service.ICreditOrgService#queryByPage(int,
	 *      int)
	 */
	@Override
	public List<CreditOrgDto> queryByPage(int offset, int limit) {
		return this.creditOrgEntityDao.queryOrgCodeByPage(offset, limit);
	}

	/**
	 * 
	 * 查询部门临欠额度、最大账期预警
	 * 
	 * @Title: queryOrgAlarm
	 * @author 046644-foss-zengbinwen
	 * @date 2013-4-8 下午2:00:18
	 * @param @param orgCode
	 * @param @return 设定文件
	 * @return CreditOrgAlarmDto 返回类型
	 * @throws
	 */
	public String queryOrgAlarm(String orgCode) {

		// 部门编码为空，则抛异常
		if (StringUtils.isEmpty(orgCode)) {
			throw new SettlementException("部门编码为空");
		}

		// 按部门编码查询部门临欠、账期信息
		CreditOrgDto dto = creditOrgEntityDao.queryDebitByOrgCode(orgCode);
		if (dto != null) {

			// 构造DTO
			CreditOrgAlarmDto alarm = new CreditOrgAlarmDto();

			// 部门编码、已用额度、最大账期
			alarm.setOrgCode(orgCode);
			alarm.setUsedAmount(dto.getUsedAmount());
			alarm.setMaxAccountDays(dto.getMaxAccountDays());

			// 调用综合管理接口，查询部門最大临欠天数
			String confValue = configurationParamsService
					.queryConfValueByCode(ConfigurationParamsConstants.STL_DEBT_LIMIT_DAY);

			// 校验参数
			if (StringUtils.isNotEmpty(confValue) && StringUtils.isNumeric(confValue)) {

				// 设置账期预警值
				alarm.setDebitLimitDays(Integer.parseInt(confValue));

			} else {
				throw new SettlementException("请在综合配置临欠款过期天数!");
			}

			// 调用综合管理接口，查询超期欠款预警值
			confValue = configurationParamsService
					.queryConfValueByCode(ConfigurationParamsConstants.STL_DEBT_LIMIT_ALARM_DAY);

			// 校验参数
			if (StringUtils.isNotEmpty(confValue) && StringUtils.isNumeric(confValue)) {

				// 设置账期预警值
				alarm.setDebitLimitAlarmDays(Integer.parseInt(confValue));

			} else {
				throw new SettlementException("请在综合配置临欠款过期预警值!");
			}

			// 调用综合管理接口，查询信用额度预警值
			confValue = configurationParamsService
					.queryConfValueByCode(ConfigurationParamsConstants.STL_CREDIT_LIMT_ALRAM_AMOUNT);

			// 校验参数
			if (StringUtils.isNotEmpty(confValue) && StringUtils.isNumeric(confValue)) {

				// 设置账期预警值
				alarm.setDebitAlaramAmount(new BigDecimal(confValue));

			} else {
				throw new SettlementException("请在综合配置信用额度预警值!");
			}

			// 查询部门最大临欠额度
			SaleDepartmentEntity entity = saleDepartmentService.querySaleDepartmentByCode(orgCode);
			if (entity == null || entity.getMaxTempArrears() == null) {
				throw new SettlementException("请在综合管理系统维护营业部的最大临欠额度");
			}

			// 设置最大临欠额度
			alarm.setMaxDebitAmount(entity.getMaxTempArrears());

			return getAlaramString(alarm);
		}

		return null;
	}

    /**
     * 根据部门编码查询部门动态已经使用额度
     * @param orgCode
     * @return
     */
    @Override
    public CreditOrgDto queryOrgCreditInfo(String orgCode) {
        List<CreditOrgDto> creditOrgDtos = creditOrgEntityDao.queryOrgDebitInfo(orgCode);
        CreditOrgDto dto = null;
        if(CollectionUtils.isNotEmpty(creditOrgDtos)&&
                creditOrgDtos.size()>0){
            dto = creditOrgDtos.get(0);
        }
        return dto;
    }

    /**
	 * 获取预警字符串
	 * 
	 * @param dto
	 * @return
	 */
	private String getAlaramString(CreditOrgAlarmDto dto) {

		// 信用额度是否到达最大值
		boolean isCreditLimit = dto.getUsedAmount().compareTo(dto.getMaxDebitAmount()) >= 0;

		// 信用额度是否达到预警值
		boolean isCreditAlarm = (dto.getUsedAmount().compareTo(
				dto.getMaxDebitAmount().subtract(dto.getDebitAlaramAmount())) >= 0)
				&& !isCreditLimit;

		// 账期是否达到最大值
		boolean isDebitDaysLimt = dto.getMaxAccountDays() >= dto.getDebitLimitDays();

		// 账期是否达到预警值
		boolean isDebitDaysAlarm = (dto.getMaxAccountDays() >= (dto.getDebitLimitDays() - dto
				.getDebitLimitAlarmDays())) && !isDebitDaysLimt;

		/**
		 * case1:信用额度未达到预警值，账期达到预警值
		 */
		if (!isCreditAlarm && !isCreditLimit && isDebitDaysAlarm) {
			return String.format("您部门的临时欠款最长欠款期限是%d天，距离最迟还款期限还有%d天，请及时联系客户还款，以免部门无法开单临时欠款。",
					dto.getDebitLimitDays(), (dto.getDebitLimitDays() - dto.getMaxAccountDays()));
		}

		/**
		 * case2:信用额度未达到预警值，账期达到最大值
		 */
		else if (!isCreditAlarm && !isCreditLimit && isDebitDaysLimt) {
			return String.format("您部门的临时欠款最长欠款期限是%d天，可用欠款期限已经为0天，请及时联系客户还款，部门已经无法开单临时欠款。",
					dto.getDebitLimitDays());
		}

		/**
		 * case3:信用额度达到预警值，账期未达到预警值
		 */
		else if (isCreditAlarm && !isDebitDaysAlarm && !isDebitDaysLimt) {
			return String.format("您部门的临时欠款总信用额度是%s，已经用了%s，还剩%s，请及时联系客户还款，以免部门无法开单临时欠款。",
					dto.getMaxDebitAmount(), dto.getUsedAmount(),
					dto.getMaxDebitAmount().subtract(dto.getUsedAmount()));
		}

		/**
		 * case4:信用额度达到最大值，账期未达到预警值
		 */
		else if (isCreditLimit && !isDebitDaysAlarm && !isDebitDaysLimt) {
			return String.format("您部门的临时欠款总信用额度是%s，已经全部使用完毕，请及时联系客户还款，部门已经无法开单临时欠款。",
					dto.getMaxDebitAmount());
		}

		/**
		 * case5:信用额度达到预警值，账期达到预警值
		 */
		else if (isCreditAlarm && isDebitDaysAlarm) {
			return String
					.format("您部门的临时欠款总信用额度是%s，已经用了%s，还剩%s，您部门的临时欠款最长欠款期限是%d天，距离最迟还款期限还有%d天，请及时联系客户还款，以免部门无法开单临时欠款。",
							dto.getMaxDebitAmount(), dto.getUsedAmount(), dto.getMaxDebitAmount()
									.subtract(dto.getUsedAmount()), dto.getDebitLimitDays(),
							dto.getDebitLimitDays() - dto.getMaxAccountDays());
		}

		/**
		 * case6:信用额度达到预警值，账期达到最大值
		 */
		else if (isCreditAlarm && isDebitDaysLimt) {
			return String
					.format("您部门的临时欠款总信用额度是%s，已经用了%s，还剩%s，您部门的临时欠款最长欠款期限是%d天，可用欠款期限已经为0天，请及时联系客户还款，部门已经无法开单临时欠款。",
							dto.getMaxDebitAmount(), dto.getUsedAmount(), dto.getMaxDebitAmount()
									.subtract(dto.getUsedAmount()), dto.getDebitLimitDays());
		}

		/**
		 * case7:信用额度达到最大值，账期达到预警值
		 */
		else if (isCreditLimit && isDebitDaysAlarm) {
			return String
					.format("您部门的临时欠款总信用额度是%s，已经全部使用完毕，您部门的临时欠款最长欠款期限是%d天，距离最迟还款期限还有%d天，请及时联系客户还款，部门已经无法开单临时欠款。",
							dto.getMaxDebitAmount(), dto.getDebitLimitDays(),
							dto.getDebitLimitDays() - dto.getMaxAccountDays());
		}

		/**
		 * case8:信用额度达到最大值，账期达到最大值
		 */
		else if (isCreditLimit && isDebitDaysLimt) {
			return String
					.format("您部门的临时欠款总信用额度是%s，已经全部使用完毕，您部门的临时欠款最长欠款期限是%d天，可用欠款期限已经为0天，请及时联系客户还款，部门已经无法开单临时欠款。",
							dto.getMaxDebitAmount(), dto.getDebitLimitDays());
		}

		/**
		 * 其它情况
		 */
		else {
			return null;
		}
	}

	public void setCreditOrgEntityDao(ICreditOrgEntityDao creditOrgEntityDao) {
		this.creditOrgEntityDao = creditOrgEntityDao;
	}

	/**
	 * @param configurationParamsService
	 */
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	/**
	 * @return saleDepartmentService
	 */
	public ISaleDepartmentService getSaleDepartmentService() {
		return saleDepartmentService;
	}

	/**
	 * @param saleDepartmentService
	 */
	public void setSaleDepartmentService(ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

}
