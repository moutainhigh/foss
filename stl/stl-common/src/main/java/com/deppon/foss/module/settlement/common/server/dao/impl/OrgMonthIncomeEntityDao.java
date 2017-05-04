package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.IOrgMonthIncomeEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.OrgMonthIncomeEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 部门收入记录表Dao
 * 
 */
public class OrgMonthIncomeEntityDao extends iBatis3DaoImpl implements
		IOrgMonthIncomeEntityDao {

	private static final String NAMESPACE = "foss.stl.OrgMonthIncomeEntityDao.";

	/**
	 * 新增营业部收入记录
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-18 下午2:12:49
	 * @param entity
	 * @return
	 */
	@Override
	public int add(OrgMonthIncomeEntity entity) {

		return this.getSqlSession().insert(NAMESPACE + "insert", entity);
	}

	/**
	 * 新增批量营业部收入记录
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-18 下午2:13:12
	 * @param list
	 * @return
	 */
	@Override
	public int addBactch(List<OrgMonthIncomeEntity> list) {

		// 如果参数为空，则抛出异常
		if (CollectionUtils.isEmpty(list)) {
			throw new SettlementException("传入参数为空，新增批量营业部收入记录");
		}

		// 循环新增营业部收入记录
		for (OrgMonthIncomeEntity entity : list) {
			this.add(entity);
		}

		// 返回成功数
		return list.size();
	}

	/**
	 * 根据营业部编码集合，开始日期和结束日期，查询营业部收入记录表中营业部的最大月收入金额
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-3-14 上午11:20:01
	 * @param orgCodes
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrgMonthIncomeEntity> queryMaxMonthIncomeAmountByOrgCodes(
			List<String> orgCodes, Date startDate, Date endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgCodes", orgCodes);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		return this.getSqlSession().selectList(NAMESPACE + "selectByOrgCodes",
				map);
	}

	/**
	 * 查询（一定时间段内）现金收款单和应收单记录，根据部门编码进行分组统计部门的收入情况 （应收单不包含代收货款应收单）
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-18 下午4:53:44
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrgMonthIncomeEntity> queryCashAndReceivableByOrgCode(
			Date startDate, Date endDate) {
		Map<String, Object> map = new HashMap<String, Object>();

		// 有效
		map.put("active", FossConstants.ACTIVE);

		// 非红单
		map.put("isRedBack",
				SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		return this.getSqlSession().selectList(
				NAMESPACE + "selectCashAndReceivableByOrgCode", map);
	}

}
