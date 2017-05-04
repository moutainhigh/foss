package com.deppon.pda.bdm.module.core.server.dao.impl;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.pda.bdm.module.core.server.dao.IBaseDao;
import com.deppon.pda.bdm.module.core.shared.domain.UserEntity;

public class UserDao extends SqlSessionDaoSupport implements IBaseDao<UserEntity> {

	@Override
	public Date getLastModifyTime() {
		//System.out.println(getSqlSession().getConnection().get);
		return (Date)getSqlSession().selectOne(UserDao.class.getName()+".getLastModifyTime");
	}

	@Override
	public UserEntity getEntityById(String userCode) {
		return (UserEntity)getSqlSession().selectOne(UserDao.class.getName()+".getEntityById",userCode);
	}

	@Override
	public List<UserEntity> getEntitiesByLastModifyTime(Date date) {
		return (List<UserEntity>)getSqlSession().selectOne(UserDao.class.getName()+".getEntityById",date);
	}

	@Override
	public List<UserEntity> getEntityByIds(List<String> userCodes) {
		return (List<UserEntity>)getSqlSession().selectOne(UserDao.class.getName()+".getEntityById",userCodes);
	}
	
}
