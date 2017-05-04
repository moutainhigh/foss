/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.settlement.pay.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.pay.api.server.dao.ISyncIncomeRptCHDao;
import com.deppon.foss.module.settlement.pay.api.shared.dto.FossCashDataDto;

/**
 *  FOSS现金缴款同步接口规格文档
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105762,date:2014-7-28 下午3:34:16,content:</p>
 * @author 105762
 * @date 2014-7-28 下午3:34:16
 * @since 1.6
 * @version 1.0
 */
public class SyncIncomeRptCHDao extends iBatis3DaoImpl implements ISyncIncomeRptCHDao {
	private static final String NAMESPACE = "foss.stl.SyncIncomeRptCHDao.";

	/** 
	 * <p>查询现金缴款每日统计数据</p> 
	 * @author 105762
	 * @date 2014-7-28 下午3:41:24
	 * @param rptDate
	 * @return 缴款数据列表
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.ISyncIncomeRptCHDao#queryIncomeRptCHByCreateTime(java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FossCashDataDto> queryIncomeRptCHByDateStr(String dateStr) {
		return (List<FossCashDataDto>) this.getSqlSession().selectList(NAMESPACE + "queryIncomeRptCHByDateStr", dateStr);
	}
}
