package com.deppon.foss.module.settlement.common.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.base.baseinfo.api.server.service.IOwedLimitRegionService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OwedLimitDto;
import com.deppon.foss.module.settlement.common.api.server.dao.IOrgMonthIncomeEntityDao;
import com.deppon.foss.module.settlement.common.api.server.service.IOrgMonthIncomeService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.OrgMonthIncomeEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;

/**
 * 部门收入记录Service
 * 
 * @author 099995-foss-wujiangtao
 * @date 2013-2-18 下午2:23:48
 * @since
 * @version
 */
public class OrgMonthIncomeService implements IOrgMonthIncomeService {

	private static final Logger LOGGER = LogManager.getLogger(OrgMonthIncomeService.class);

	/**
	 * 部门收入记录Dao
	 */
	private IOrgMonthIncomeEntityDao orgMonthIncomeEntityDao;

	/**
	 * 综合管理--临欠额度区间范围信息Service接口
	 */
	private IOwedLimitRegionService owedLimitRegionService;

	/**
	 * 新增营业部收入记录
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-18 下午2:25:52
	 * @param entity
	 */
	@Override
	public void addOrgMonthIncome(OrgMonthIncomeEntity entity) {
		if (entity == null) {
			throw new SettlementException("新增营业部收入记录参数不能为空");
		}
		LOGGER.info("Start 新增营业部收入记录");
		int i = orgMonthIncomeEntityDao.add(entity);
		if (i != 1) {
			throw new SettlementException("新增营业部收入记录失败！");
		}
		LOGGER.info("End 新增营业部收入记录");
	}

	/**
	 * 批量新增营业部收入记录
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-18 下午2:25:41
	 * @param list
	 */
	@Override
	public void batchAddOrgMonthIncome(List<OrgMonthIncomeEntity> list) {
		if (CollectionUtils.isEmpty(list)) {
			throw new SettlementException("批量新增营业部收入记录,参数不能为空");
		}
		LOGGER.info("Start 新增批量营业部收入记录");

		orgMonthIncomeEntityDao.addBactch(list);

		LOGGER.info("End 新增批量营业部收入记录");
	}

	/**
	 * 根据传入的营业部编码集合和日期，查询日期最近三个月内营业部的最大月收入金额
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-18 下午2:26:06
	 * @param orgCodes
	 * @param date
	 * @return
	 */
	@Override
	public List<OrgMonthIncomeEntity> queryMaxMonthIncomeAmountByOrgCodes(List<String> orgCodes,
			Date date) {
		if (CollectionUtils.isEmpty(orgCodes)) {
			throw new SettlementException("查询营业部收入记录，参数不能为空！");
		}
		Date dt = date != null ? date : new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)
				- SettlementConstants.ORG_INCOME_MONTH_COUNT_THREE);// 当前月份减去3，计算前三个月的第一个月份
		Date startDate = cal.getTime();

		// 日期格式化到月
		startDate = DateUtils.convert(
				DateUtils.convert(startDate, SettlementConstants.FORMATS_MONTH),
				SettlementConstants.FORMATS_MONTH);
		dt = DateUtils.convert(DateUtils.convert(dt, SettlementConstants.FORMATS_MONTH),
				SettlementConstants.FORMATS_MONTH);

		return orgMonthIncomeEntityDao.queryMaxMonthIncomeAmountByOrgCodes(orgCodes, startDate, dt);
	}

	/**
	 * 每个月初，定时统计上月营业部的收入情况，进行记录
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-18 下午4:59:38
	 */
	@Override
	public void sumStilBillToOrgMonthIncome() {
		// 根据当前时间获取上一个月的第一天和当月的第一天
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();

		cal.add(Calendar.MONTH, -1);// 当前月份减去1
		Date startDate = cal.getTime();

		// 格式化到日
		startDate = DateUtils.convert(
				DateUtils.convert(startDate, SettlementConstants.FORMATS_MONTH)
						+ SettlementConstants.MONTH_FIRST_DATE, DateUtils.DATE_FORMAT);

		// 格式化到日
		date = DateUtils.convert(DateUtils.convert(date, SettlementConstants.FORMATS_MONTH)
				+ SettlementConstants.MONTH_FIRST_DATE, DateUtils.DATE_FORMAT);
		/**
		 * 查询（一定时间段内）现金收款单和应收单记录，根据部门编码进行分组统计部门的收入情况 （应收单不包含代收货款应收单）
		 */
		List<OrgMonthIncomeEntity> list = this.orgMonthIncomeEntityDao
				.queryCashAndReceivableByOrgCode(startDate, date);
		if (CollectionUtils.isNotEmpty(list)) {
			List<OrgMonthIncomeEntity> listToAdd = new ArrayList<OrgMonthIncomeEntity>();
			// 需求变更，计算完部门收入之后，查询出部门三个月最大收入金额，调用综合接口修改部门的最大临欠额度
			List<String> orgCodes = new ArrayList<String>();
			Date dt = new Date();
			for (OrgMonthIncomeEntity entity : list) {
				// ID
				entity.setId(UUIDUtils.getUUID());

				// 统计月份-startDate
				entity.setCtMonth(startDate);

				// 创建时间
				entity.setCreateDate(dt);
				listToAdd.add(entity);

				// 部门编码集合
				orgCodes.add(entity.getOrgCode());
			}

			if (CollectionUtils.isNotEmpty(listToAdd) && CollectionUtils.isNotEmpty(orgCodes)) {

				// 调用批量新增部门收入记录方法
				this.batchAddOrgMonthIncome(listToAdd);

				// 获取部门三月内最大月收入，调用综合接口修改数据

				// 查询到部门编码集合大于1000
				if (orgCodes.size() > SettlementConstants.MAX_LIST_SIZE) {
					// 因为update用的是in,对超过1000条记录的进行特殊处理，分割后分批处理
					List<List<String>> splitList = com.deppon.foss.util.CollectionUtils
							.splitListBySize(orgCodes, SettlementConstants.MAX_LIST_SIZE);
					//循环
					for (List<String> orgCodesList : splitList) {
						batchUpdateAmountOweds(orgCodesList, date);
					}	
				} else {
					batchUpdateAmountOweds(orgCodes, date);
				}
			}
		}
	}

	/**
	 * 查询部门的三个月内最大月收入 调用综合接口，修改部门的最大临欠额度
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-28 下午5:33:30
	 * @param lists
	 */
	private void batchUpdateAmountOweds(List<String> orgCodes, Date date) {

		/**
		 * 查询部门三个月内最大收入信息
		 */
		List<OrgMonthIncomeEntity> lists = this.queryMaxMonthIncomeAmountByOrgCodes(orgCodes, date);

		List<OwedLimitDto> list = new ArrayList<OwedLimitDto>();
		for (OrgMonthIncomeEntity entity : lists) {
			OwedLimitDto dto = new OwedLimitDto();
			dto.setDeptCode(entity.getOrgCode());// 部门编码
			dto.setTaking(entity.getIncomeAmount());// 部门3月内最大收入额度
			list.add(dto);
		}
		this.owedLimitRegionService.batchUpdateAmountOweds(list);

	}

	/**
	 * @param orgMonthIncomeEntityDao
	 *            the orgMonthIncomeEntityDao to set
	 */
	public void setOrgMonthIncomeEntityDao(IOrgMonthIncomeEntityDao orgMonthIncomeEntityDao) {
		this.orgMonthIncomeEntityDao = orgMonthIncomeEntityDao;
	}

	/**
	 * @param owedLimitRegionService
	 *            the owedLimitRegionService to set
	 */
	public void setOwedLimitRegionService(IOwedLimitRegionService owedLimitRegionService) {
		this.owedLimitRegionService = owedLimitRegionService;
	}

}
