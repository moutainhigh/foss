package com.deppon.pda.bdm.module.core.server.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.cache.DefaultTTLRedisCache;
import com.deppon.pda.bdm.module.core.server.dao.IBaseDao;
import com.deppon.pda.bdm.module.core.shared.domain.LoadLimitedWeightEntity;

/**
 * 
  * @ClassName LoadLimitedWeightCache 
  * @Description TODO 
  * @author cbb
  * @date 2013-12-3 下午4:13:58
 */
public class LoadLimitedWeightCache extends DefaultTTLRedisCache<LoadLimitedWeightEntity> {
	private static final Log LOG = LogFactory.getLog(LoadLimitedWeightCache.class);
	private IBaseDao<LoadLimitedWeightEntity> baseDao;
	
	@Override
	public String getUUID() {
		return LoadLimitedWeightCache.class.getName();
	}

	public LoadLimitedWeightEntity getLoadLimitedWeight(String key){
		LoadLimitedWeightEntity loadLimitedWeightEntity = null;
		try {
			loadLimitedWeightEntity = get(key);
		} catch (Exception e) {
			LOG.error("缓存异常:"+e.getMessage());
			loadLimitedWeightEntity = baseDao.getEntityById(key); 
		}
		return loadLimitedWeightEntity;
	}
	
	public void setBaseDao(IBaseDao<LoadLimitedWeightEntity> baseDao) {
		this.baseDao = baseDao;
	}

}
