package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.IPlatformCommonDao;

public class PlatformCommonDao extends iBatis3DaoImpl implements
		IPlatformCommonDao {

	private final String NAMESPACE = "com.deppon.foss.module.transfer.platform.api.server.dao.IPlatformCommonDao.";

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> findSupHq(String orgCode) {
		return (Map<String, String>) super.getSqlSession().selectOne(
				NAMESPACE + "findSupHq", orgCode);
	}

}
