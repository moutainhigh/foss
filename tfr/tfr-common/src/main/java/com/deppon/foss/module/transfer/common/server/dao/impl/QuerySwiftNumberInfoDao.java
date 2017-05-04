package com.deppon.foss.module.transfer.common.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.common.api.server.dao.IQuerySwiftNumberInfoDao;
import com.deppon.foss.module.transfer.common.api.shared.dto.SwiftNumberInfoDto;

public class QuerySwiftNumberInfoDao extends iBatis3DaoImpl implements
		IQuerySwiftNumberInfoDao {
	private static final String NAMESPACE = "foss.tfr.SerialNumberRuleDao.";

	/**
	 * @description 查询流水号信息
	 * @see com.deppon.foss.module.transfer.common.api.server.dao.IQuerySwiftNumberInfoDao#querySwiftNumberInfo(java.lang.String)
	 * @author 316759-foss-wangruipeng
	 * @update 2016-09-02 上午09:24:06
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SwiftNumberInfoDto> querySwiftNumberInfo(String wayBillNo) {
		return this.getSqlSession().selectList(NAMESPACE + "querySwiftNumberInfo", wayBillNo);
	}

}
