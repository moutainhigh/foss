package com.deppon.foss.module.transfer.common.server.dao.impl;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.common.api.server.dao.ITfrNotifyDao;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrNotifyConfig;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrNotifyEntity;

public class TfrNotifyDao extends iBatis3DaoImpl implements ITfrNotifyDao {
	private static final String NAMESPACE = "foss.tfr.notify.";
	
	@Override
	public int addTfrNotifyEntity(TfrNotifyEntity e) {
		return this.getSqlSession().insert(NAMESPACE + "addTfrNotifyEntity", e);
	}

	@Override
	public List<TfrNotifyEntity> selectTfrNotifyList(Map<String,Object> map) {
		return this.getSqlSession().selectList(NAMESPACE + "selectTfrNotifyList", map);
	}

	@Override
	public int updateTfrNotifyIng(List<String> ids) {
		if(ids==null||ids.size()<=0)
			return -1;
		return this.getSqlSession().update(NAMESPACE+"updateTfrNotifyIng",ids);
	}

	@Override
	public int updateTfrNotifySuccess(String id) {
		if(id==null)
			return -1;
		return this.getSqlSession().delete(NAMESPACE + "updateTfrNotifySuccess", id);
		
	}

	@Override
	public int addTfrNotifyBeginEntity(TfrNotifyEntity e) {
		return this.getSqlSession().insert(NAMESPACE + "addTfrNotifyBeginEntity", e);
	}

	@Override
	public int updateTfrNotifyFail(TfrNotifyEntity e) {
		if(e==null||e.getId()==null)
			return -1;
		return this.getSqlSession().update(NAMESPACE+"updateTfrNotifyFail",e);
	}

	@Override
	public TfrNotifyConfig queryNotifyConfig(String className) {
		if(className==null)
			return null;
		return (TfrNotifyConfig)this.getSqlSession().selectOne(NAMESPACE+"queryNotifyConfig", className);
	}

	@Override
	public int updateTfrNotifyListJobId(Map<String, Object> map) {
		if(map == null) {
			return -1;
		} else {
			return this.getSqlSession().update(NAMESPACE+"updateTfrNotifyListJobId", map);
		}
	}
	
	
}
