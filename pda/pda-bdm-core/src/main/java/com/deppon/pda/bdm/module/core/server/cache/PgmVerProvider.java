package com.deppon.pda.bdm.module.core.server.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.pda.bdm.module.core.server.dao.IBaseDao;
import com.deppon.pda.bdm.module.core.shared.domain.PgmVerEntity;

public class PgmVerProvider implements ITTLCacheProvider<PgmVerEntity> {

	private IBaseDao<PgmVerEntity> baseDao;

//	@Override
//	public Date getLastModifyTime() {
//		// TODO Auto-generated method stub
//		return baseDao.getLastModifyTime();
//	}
//
//	@Override
//	public PgmVerEntity get(String key) {
//		// TODO Auto-generated method stub
//		return baseDao.getEntityById(key);
//	}
//
//	@Override
//	public Map<String, PgmVerEntity> getUpdateObjectMaps(Date time) {
//		Map<String, PgmVerEntity> pgmMap = new HashMap<String, PgmVerEntity>();
//		List<PgmVerEntity> pgmVers = baseDao.getEntitiesByLastModifyTime(time);
//		for (PgmVerEntity pgmVer : pgmVers) {
//			pgmMap.put(pgmVer.getId(), pgmVer);
//		}
//		return pgmMap;
//	}
//
//	@Override
//	public Map<String, PgmVerEntity> getUpdateObjectMaps(String... keys) {
//		Map<String, PgmVerEntity> pgmMap = new HashMap<String, PgmVerEntity>();
//		List<PgmVerEntity> pgmVers = new ArrayList<PgmVerEntity>();
//		for(String key : keys){
//			PgmVerEntity pgmVerEntity = baseDao.getEntityById(key);
//			if(pgmVerEntity != null){
//				pgmVers.add(pgmVerEntity);
//			}
//		}
//		for (PgmVerEntity pgmVer : pgmVers) {
//			pgmMap.put(pgmVer.getId(), pgmVer);
//		}
//		return pgmMap;
//	}

	public void setBaseDao(IBaseDao<PgmVerEntity> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public PgmVerEntity get(String key) {
		return baseDao.getEntityById(key);
	}

}
