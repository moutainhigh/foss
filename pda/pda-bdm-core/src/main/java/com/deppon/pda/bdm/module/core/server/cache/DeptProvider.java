package com.deppon.pda.bdm.module.core.server.cache;


import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.pda.bdm.module.core.server.dao.IBaseDao;
import com.deppon.pda.bdm.module.core.shared.domain.DeptEntity;

public class DeptProvider implements ITTLCacheProvider<DeptEntity> {

	private IBaseDao<DeptEntity> baseDao;

	
//	@Override
//	public Date getLastModifyTime() {
//		return baseDao.getLastModifyTime();
//	}
//
//	@Override
//	public DeptEntity get(String key) {
//		return baseDao.getEntityById(key);
//	}
//
//	@Override
//	public Map<String, DeptEntity> getUpdateObjectMaps(Date time) {
//		Map<String, DeptEntity> dataMap = new HashMap<String, DeptEntity>();
//		List<DeptEntity> depts = baseDao
//				.getEntitiesByLastModifyTime(time);
//		for (DeptEntity dept : depts) {
//			dataMap.put(dept.getId(), dept);
//		}
//		return dataMap;
//	}
//
//	@Override
//	public Map<String, DeptEntity> getUpdateObjectMaps(String... keys) {
//		Map<String, DeptEntity> dataMap = new HashMap<String, DeptEntity>();
//		List<DeptEntity> depts = new ArrayList<DeptEntity>();
//		for(String key :keys){
//			DeptEntity deptEntity = baseDao.getEntityById(key);
//			if(deptEntity != null){
//				depts.add(baseDao.getEntityById(key));
//			}
//		}
//		for (DeptEntity dept : depts) {
//			dataMap.put(dept.getId(), dept);
//		}
//		return dataMap;
//	}
//
	public void setBaseDao(IBaseDao<DeptEntity> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public DeptEntity get(String key) {
		return baseDao.getEntityById(key);
	}

}
