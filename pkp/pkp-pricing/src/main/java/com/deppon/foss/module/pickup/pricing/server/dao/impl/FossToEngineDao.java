package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IFossToEngineDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.FossToEngineLogEntity;


public class FossToEngineDao extends SqlSessionDaoSupport implements IFossToEngineDao{

	private static final String NAMESPACE="com.deppon.foss.module.pickup.pricing.FossToEngineLogMapper.";
	
	@Override
	public void insertLog(FossToEngineLogEntity fossToEngineLogEntity) {
		// TODO Auto-generated method stub
		this.getSqlSession().insert(NAMESPACE+"insertLog", fossToEngineLogEntity);
	}

}
