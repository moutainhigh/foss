package com.deppon.foss.module.transfer.common.server.service.impl;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.common.api.server.dao.ITfrNotifyDao;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrNotifyService;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrNotifyConfig;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrNotifyEntity;

public class TfrNotifyService implements ITfrNotifyService {
	
	private ITfrNotifyDao tfrNotifyDao;

		
	

	public ITfrNotifyDao getTfrNotifyDao() {
		return tfrNotifyDao;
	}

	public void setTfrNotifyDao(ITfrNotifyDao tfrNotifyDao) {
		this.tfrNotifyDao = tfrNotifyDao;
	}

	@Override
	public int addTfrNotifyEntity(TfrNotifyEntity e) {
		return tfrNotifyDao.addTfrNotifyEntity(e);
	}

	@Override
	public int addTfrNotifyBeginEntity(TfrNotifyEntity e) {
		return tfrNotifyDao.addTfrNotifyBeginEntity(e);
	}

	@Override
	public List<TfrNotifyEntity> selectTfrNotifyList(Map<String, Object> map) {
		return tfrNotifyDao.selectTfrNotifyList(map);
	}

	@Override
	public int updateTfrNotifyIng(List<String> ids) {
		return tfrNotifyDao.updateTfrNotifyIng(ids);
	}

	@Override
	public int updateTfrNotifySuccess(String id) {
		return tfrNotifyDao.updateTfrNotifySuccess(id);
	}

	@Override
	public int updateTfrNotifyFail(TfrNotifyEntity e) {
		return tfrNotifyDao.updateTfrNotifyFail(e);
	}

	@Override
	public TfrNotifyConfig queryNotifyConfig(String className) {
		return tfrNotifyDao.queryNotifyConfig(className);
	}
	
	@Override
	public int updateTfrNotifyListJobId(Map<String, Object> map) {
		return tfrNotifyDao.updateTfrNotifyListJobId(map);
	}
}
