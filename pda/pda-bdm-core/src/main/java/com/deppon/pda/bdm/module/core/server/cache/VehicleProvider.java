package com.deppon.pda.bdm.module.core.server.cache;

import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.pda.bdm.module.core.server.dao.IBaseDao;
import com.deppon.pda.bdm.module.core.shared.domain.VehicleEntity;
/**
 * 
  * @ClassName VehicleProvider 
  * @Description TODO 
  * @author mt hyssmt@vip.qq.com
  * @date 2013-9-7 下午4:14:13
 */
public class VehicleProvider implements ITTLCacheProvider<VehicleEntity> {

	private IBaseDao<VehicleEntity> baseDao;

	public void setBaseDao(IBaseDao<VehicleEntity> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public VehicleEntity get(String key) {
		return baseDao.getEntityById(key);
	}
}
