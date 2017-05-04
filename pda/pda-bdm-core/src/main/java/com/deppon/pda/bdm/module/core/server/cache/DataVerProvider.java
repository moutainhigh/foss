package com.deppon.pda.bdm.module.core.server.cache;

//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;

//import com.deppon.foss.framework.cache.provider.ILazyCacheProvider;
import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.pda.bdm.module.core.server.dao.IBaseDao;
//import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.domain.BaseDataVerEntity;

public class DataVerProvider implements
		ITTLCacheProvider<BaseDataVerEntity> {

	private IBaseDao<BaseDataVerEntity> baseDao;

	@Override
	public BaseDataVerEntity get(String key) {
		return baseDao.getEntityById(key);
	}

	
	
//	@Override
//	public Date getLastModifyTime() {
//		// TODO Auto-generated method stub
//		return baseDao.getLastModifyTime();
//	}
//
//	@Override
//	public BaseDataVerEntity get(String key) {
//		// TODO Auto-generated method stub
//		return baseDao.getEntityById(key);
//	}
//
//	@Override
//	public Map<String, BaseDataVerEntity> getUpdateObjectMaps(Date time) {
//		Map<String, BaseDataVerEntity> dataMap = new HashMap<String, BaseDataVerEntity>();
//		List<BaseDataVerEntity> dataVers = baseDao
//				.getEntitiesByLastModifyTime(time);
//		for (BaseDataVerEntity dataVer : dataVers) {
//			dataMap.put(Constant.BASE_DATA_VERSION_KEY, dataVer);
//		}
//		return dataMap;
//	}
//
//	@Override
//	public Map<String, BaseDataVerEntity> getUpdateObjectMaps(String... keys) {
//		Map<String, BaseDataVerEntity> dataMap = new HashMap<String, BaseDataVerEntity>();
//		List<BaseDataVerEntity> dataVers = new ArrayList<BaseDataVerEntity>();
//		for(String key : keys){
//			BaseDataVerEntity baseDataVerEntity = baseDao.getEntityById(key);
//			if(baseDataVerEntity != null){
//				dataVers.add(baseDataVerEntity);
//			}
//		}
//		for (BaseDataVerEntity dataVer : dataVers) {
//			dataMap.put(Constant.BASE_DATA_VERSION_KEY, dataVer);
//		}
//		return dataMap;
//	}
//
	public void setBaseDao(IBaseDao<BaseDataVerEntity> baseDao) {
		this.baseDao = baseDao;
	}

}
