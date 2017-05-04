package com.deppon.pda.bdm.module.foss.accept.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.pda.bdm.module.foss.accept.server.dao.IPdamPositionDao;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.PositionEntity;

public class PdamPositionDao extends SqlSessionDaoSupport implements IPdamPositionDao {

	/**
	 * 保存经纬度数据
	 */
	@Override
	public void savePosition(PositionEntity position) {
		getSqlSession().insert(getClass().getName() + ".savePosition", position);
	}

}
