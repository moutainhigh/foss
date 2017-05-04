package com.deppon.pda.bdm.module.core.server.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.cache.DefaultTTLRedisCache;
import com.deppon.pda.bdm.module.core.server.dao.IBaseDao;
import com.deppon.pda.bdm.module.core.shared.domain.PdaDeviceEntity;

/**
 * 
  * @ClassName UserCache 
  * @Description UserEntity 缓存类 
  * @author xujun cometzb@126.com 
  * @date 2012-12-26
 */
public class DeviceCache extends DefaultTTLRedisCache<PdaDeviceEntity> {
	private static final Log LOG = LogFactory.getLog(DeviceCache.class);
	private IBaseDao<PdaDeviceEntity> baseDao;
	
	@Override
	public String getUUID() {
		return DeviceCache.class.getName();
	}

	/**
	 * 
	* @Title: getDevice
	* @Description: TODO 缓存服务器出现问题时，能主动从数据库去取资源
	* @param key
	* @return PdaDeviceEntity  返回类型 
	* @throws
	 */
	public PdaDeviceEntity getDevice(String key){
		PdaDeviceEntity deviceEntity = null;
		try {
			deviceEntity = get(key);
		} catch (Exception e) {
			LOG.error("缓存异常:"+e.getMessage());
			deviceEntity = baseDao.getEntityById(key); 
		}
		return deviceEntity;
	}
	
	public void setBaseDao(IBaseDao<PdaDeviceEntity> baseDao) {
		this.baseDao = baseDao;
	}
	
}
