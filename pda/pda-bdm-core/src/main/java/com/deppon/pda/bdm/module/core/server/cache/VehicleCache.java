package com.deppon.pda.bdm.module.core.server.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.cache.DefaultTTLRedisCache;
import com.deppon.pda.bdm.module.core.server.dao.IBaseDao;
import com.deppon.pda.bdm.module.core.shared.domain.VehicleEntity;

/**
 * 
  * @ClassName VehicleCache 
  * @Description TODO 
  * @author mt hyssmt@vip.qq.com
  * @date 2013-9-7 下午4:13:58
 */
public class VehicleCache extends DefaultTTLRedisCache<VehicleEntity> {
	private static final Log LOG = LogFactory.getLog(VehicleCache.class);
	private IBaseDao<VehicleEntity> baseDao;
	
	@Override
	public String getUUID() {
		return VehicleCache.class.getName();
	}

	public VehicleEntity getDept(String key){
		VehicleEntity vehicleEntity = null;
		try {
			vehicleEntity = get(key);
		} catch (Exception e) {
			LOG.error("缓存异常:"+e.getMessage());
			vehicleEntity = baseDao.getEntityById(key); 
		}
		return vehicleEntity;
	}
	
	public void setBaseDao(IBaseDao<VehicleEntity> baseDao) {
		this.baseDao = baseDao;
	}

}
