package com.deppon.pda.bdm.module.core.server.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.cache.DefaultTTLRedisCache;
import com.deppon.pda.bdm.module.core.server.dao.IBaseDao;
import com.deppon.pda.bdm.module.core.shared.domain.UserEntity;

/**
 * 
  * @ClassName UserCache 
  * @Description UserEntity 缓存类 
  * @author xujun cometzb@126.com 
  * @date 2012-12-26
 */
public class UserCache extends DefaultTTLRedisCache<UserEntity> {
	private static final Log LOG = LogFactory.getLog(UserCache.class);
	private IBaseDao<UserEntity> baseDao;
	
	@Override
	public String getUUID() {
		return UserCache.class.getName();
	}

	public UserEntity getUser(String key){
		UserEntity userEntity = null;
		try {
			userEntity = get(key);
		} catch (Exception e) {
			LOG.error("缓存异常:"+e.getMessage());
			userEntity = baseDao.getEntityById(key); 
		}
		return userEntity;
	}
	
	public void setBaseDao(IBaseDao<UserEntity> baseDao) {
		this.baseDao = baseDao;
	}
	
}
