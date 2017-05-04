package com.deppon.foss.module.settlement.common.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.OrgMonthIncomeEntity;

/**
 * 部门收入记录表Dao接口
 * 
 * @author 099995-foss-wujiangtao
 * @date 2013-2-18 下午1:49:04
 * @since
 * @version
 */
public interface IOrgMonthIncomeEntityDao {
	
	/**
	 * 新增营业部收入记录
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-18 下午1:49:57
	 * @param entity
	 * @return
	 */
	int add(OrgMonthIncomeEntity entity);
	
	/**
	 * 新增批量营业部收入记录
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-18 下午1:51:45
	 * @param list
	 * @return
	 */
	int addBactch(List<OrgMonthIncomeEntity> list);
	
	/**
	 * 根据传入的营业部编码集合和日期，查询日期最近三个月内最大月收入金额
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-18 下午2:02:37
	 * @param orgCodes
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<OrgMonthIncomeEntity> queryMaxMonthIncomeAmountByOrgCodes(List<String> orgCodes,Date startDate,Date endDate);
	
	/**
	 * 查询（一定时间段内）现金收款单和应收单记录，根据部门编码进行分组统计部门的收入情况
	 * （应收单不包含代收货款应收单）
	 * 
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-18 下午4:50:25
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<OrgMonthIncomeEntity> queryCashAndReceivableByOrgCode(Date startDate,Date endDate);
}
