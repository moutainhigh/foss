package com.deppon.foss.module.settlement.writeoff.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IStatementModifyDao;

public class StatementModifyDao extends iBatis3DaoImpl implements IStatementModifyDao {
	//命名空间
	private static final String NAMESPACE = "foss.stl.StatementModifyDao.";

	/**
	 * 查询对账单章
	 * @author ddw
	 * @date 2014-10-10
	 */
	@Override
	public String queryStatementChapter(String companyCode) {
		String chapterUrl = (String)getSqlSession().selectOne(NAMESPACE + "queryStatementChapter",companyCode);
		return chapterUrl;
	}

}
