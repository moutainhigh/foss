package com.deppon.pda.bdm.module.core.server.dao.impl;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.pda.bdm.module.core.server.dao.IBaseDao;
import com.deppon.pda.bdm.module.core.shared.domain.BaseDataVerEntity;

/**
 * 
 * @ClassName BaseDataVerDao
 * @Description 查询基础数据版本
 * @author xujun cometzb@126.com
 * @date 2012-12-26
 */
public class BaseDataVerDao extends SqlSessionDaoSupport implements
		IBaseDao<BaseDataVerEntity> {

	@Override
	public Date getLastModifyTime() {
		
		BaseDataVerEntity baseDataVer = (BaseDataVerEntity) getSqlSession()
				.selectOne(getClass().getName() + ".getLastModifyTime");
		if(baseDataVer==null){
			return new Date();
		}
		return baseDataVer.getUpdDate();
	}

	@Override
	public BaseDataVerEntity getEntityById(String id) {
		
		return (BaseDataVerEntity) getSqlSession().selectOne(
				getClass().getName() + ".getEntityById", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BaseDataVerEntity> getEntitiesByLastModifyTime(Date date) {

		return (List<BaseDataVerEntity>)getSqlSession().selectList(
				getClass().getName() + ".getEntitiesByLastModifyTime", date);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BaseDataVerEntity> getEntityByIds(List<String> ids) {

		return (List<BaseDataVerEntity>)getSqlSession().selectList(
				getClass().getName() + ".getEntityByIds", ids);
	}

}
