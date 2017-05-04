package com.deppon.foss.module.settlement.common.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.OrgMonthIncomeEntity;

/**
 * 部门收入记录Service
 * 
 * @author 099995-foss-wujiangtao
 * @date 2013-2-18 下午2:14:25
 * @since
 * @version
 */
public interface IOrgMonthIncomeService {
	
	/**
	 * 新增营业部收入记录
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-18 下午2:15:14
	 * @param entity
	 */
	void addOrgMonthIncome(OrgMonthIncomeEntity entity);
	
	/**
	 * 新增批量营业部收入记录
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-18 下午2:15:59
	 * @param list
	 */
	void batchAddOrgMonthIncome(List<OrgMonthIncomeEntity> list);
	
	/**
	 * 根据传入的营业部编码集合和日期，查询日期最近三个月内营业部的最大月收入金额
	 * 
	 * 比如（传入日期为：2013.2.18 ,那么就是查询2013.1 和2012.12、2012.11三个月的中最大的收入金额）
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-18 下午2:16:59
	 * @param orgCodes
	 * @param date
	 * @return
	 */
	List<OrgMonthIncomeEntity> queryMaxMonthIncomeAmountByOrgCodes(List<String> orgCodes,Date date);
	
	/**
	 * 每个月初，定时统计上月营业部的收入情况，进行记录
	 *  
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-18 下午4:58:03
	 */
	void sumStilBillToOrgMonthIncome();
}
