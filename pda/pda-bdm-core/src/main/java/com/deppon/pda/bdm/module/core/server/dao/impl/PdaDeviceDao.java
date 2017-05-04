package com.deppon.pda.bdm.module.core.server.dao.impl;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.pda.bdm.module.core.server.dao.IBaseDao;
import com.deppon.pda.bdm.module.core.shared.domain.PdaDeviceEntity;

public class PdaDeviceDao extends SqlSessionDaoSupport implements IBaseDao<PdaDeviceEntity> {

	@Override
	public Date getLastModifyTime() {
		return (Date)getSqlSession().selectOne(PdaDeviceDao.class.getName()+".getLastModifyTime");
	}

	@Override
	public PdaDeviceEntity getEntityById(String pdaCode) {
		return (PdaDeviceEntity)getSqlSession().selectOne(PdaDeviceDao.class.getName()+".getEntityById",pdaCode);
	}

	@Override
	public List<PdaDeviceEntity> getEntitiesByLastModifyTime(Date date) {
		return (List<PdaDeviceEntity>)getSqlSession().selectOne(PdaDeviceDao.class.getName()+".getEntityById",date);
	}

	@Override
	public List<PdaDeviceEntity> getEntityByIds(List<String> pdaCodes) {
		return (List<PdaDeviceEntity>)getSqlSession().selectOne(PdaDeviceDao.class.getName()+".getEntityById",pdaCodes);
	}

	

}
