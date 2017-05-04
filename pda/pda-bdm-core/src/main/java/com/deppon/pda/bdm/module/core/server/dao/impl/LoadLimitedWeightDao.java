package com.deppon.pda.bdm.module.core.server.dao.impl;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.pda.bdm.module.core.server.dao.IBaseDao;
import com.deppon.pda.bdm.module.core.shared.domain.LoadLimitedWeightEntity;

/**
 * 
  * @ClassName LoadLimitedWeightDao 
  * @Description TODO 装车限重dao
  * @author cbb 
  * @date 2013-12-3 下午3:33:33
 */
public class LoadLimitedWeightDao extends SqlSessionDaoSupport implements IBaseDao<LoadLimitedWeightEntity>{
	@Override
	public LoadLimitedWeightEntity getEntityById(String models) {
		return (LoadLimitedWeightEntity)getSqlSession().selectOne(LoadLimitedWeightDao.class.getName()+".getEntityById",models);
	}

	@Override
	public Date getLastModifyTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LoadLimitedWeightEntity> getEntitiesByLastModifyTime(Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LoadLimitedWeightEntity> getEntityByIds(List<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}
}
