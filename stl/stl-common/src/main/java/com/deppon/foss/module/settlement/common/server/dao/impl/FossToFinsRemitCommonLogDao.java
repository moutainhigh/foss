package com.deppon.foss.module.settlement.common.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.IFossToFinsRemitCommonLogDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.FossToFinsRemitCommonLogEntity;

/**
 * @Description:
 * @author 321603
 * 2016-10-14
 */
public class FossToFinsRemitCommonLogDao extends iBatis3DaoImpl implements
		IFossToFinsRemitCommonLogDao {
	
	private static final String NAME_SPACE = "foss.stl.FossToFinsRemitCommonLogDao.";
	
	@Override
	public void insertFossToFinsRemitCommonLog(
			FossToFinsRemitCommonLogEntity entity) {
		this.getSqlSession().insert(NAME_SPACE + "insertFossToFinsRemitCommonLog", entity);
	}


}
