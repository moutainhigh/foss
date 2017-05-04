package com.deppon.pda.bdm.module.core.server.cache;

import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.pda.bdm.module.core.server.dao.IBaseDao;
import com.deppon.pda.bdm.module.core.shared.domain.LoadLimitedWeightEntity;
/**
 * 
  * @ClassName LoadLimitedWeightProvider 
  * @Description TODO 
  * @author cbb
  * @date 2013-12-3 下午4:14:13
 */
public class LoadLimitedWeightProvider implements ITTLCacheProvider<LoadLimitedWeightEntity> {

	private IBaseDao<LoadLimitedWeightEntity> baseDao;

	public void setBaseDao(IBaseDao<LoadLimitedWeightEntity> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public LoadLimitedWeightEntity get(String key) {
		return baseDao.getEntityById(key);
	}
}
