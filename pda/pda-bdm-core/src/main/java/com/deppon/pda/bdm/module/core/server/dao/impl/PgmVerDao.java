package com.deppon.pda.bdm.module.core.server.dao.impl;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.pda.bdm.module.core.server.dao.IBaseDao;
import com.deppon.pda.bdm.module.core.shared.domain.PgmVerEntity;
/**
 * 
  * @ClassName PgmVerDao 
  * @Description PDA程序版本Dao 
  * @author xujun cometzb@126.com 
  * @date 2012-12-26
 */
public class PgmVerDao extends SqlSessionDaoSupport implements IBaseDao<PgmVerEntity> {

	@Override
	public Date getLastModifyTime() {
		return (Date)getSqlSession().selectOne(PgmVerDao.class.getName()+".getLastModifyTime");
	}

	@Override
	public PgmVerEntity getEntityById(String pdaModel) {
		return (PgmVerEntity)getSqlSession().selectOne(PgmVerDao.class.getName()+".getEntityById",pdaModel);
	}

	@Override
	public List<PgmVerEntity> getEntitiesByLastModifyTime(Date date) {
		return (List<PgmVerEntity>)getSqlSession().selectOne(PgmVerDao.class.getName()+".getEntityById",date);
	}

	@Override
	public List<PgmVerEntity> getEntityByIds(List<String> pdaModels) {
		return (List<PgmVerEntity>)getSqlSession().selectOne(PgmVerDao.class.getName()+".getEntityById",pdaModels);
	}
	
}
