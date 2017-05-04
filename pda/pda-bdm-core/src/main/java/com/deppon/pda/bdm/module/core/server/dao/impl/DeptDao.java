package com.deppon.pda.bdm.module.core.server.dao.impl;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.pda.bdm.module.core.server.dao.IBaseDao;
import com.deppon.pda.bdm.module.core.shared.domain.DeptEntity;

/**
 * 
  * @ClassName DeptDao 
  * @Description 部门dao 
  * @author xujun cometzb@126.com 
  * @date 2012-12-26
 */
public class DeptDao extends SqlSessionDaoSupport implements IBaseDao<DeptEntity>{

	@Override
	public Date getLastModifyTime() {
		return (Date)getSqlSession().selectOne(DeptDao.class.getName()+".getLastModifyTime");
	}

	@Override
	public DeptEntity getEntityById(String deptId) {
		return (DeptEntity)getSqlSession().selectOne(DeptDao.class.getName()+".getEntityById",deptId);
	}

	@Override
	public List<DeptEntity> getEntitiesByLastModifyTime(Date date) {
		return (List<DeptEntity>)getSqlSession().selectOne(DeptDao.class.getName()+".getEntityById",date);
	}

	@Override
	public List<DeptEntity> getEntityByIds(List<String> deptIds) {
		return (List<DeptEntity>)getSqlSession().selectOne(DeptDao.class.getName()+".getEntityById",deptIds);
	}


}
