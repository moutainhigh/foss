package com.deppon.pda.bdm.module.core.server.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.cache.DefaultTTLRedisCache;
import com.deppon.pda.bdm.module.core.server.dao.IBaseDao;
import com.deppon.pda.bdm.module.core.shared.domain.BaseDataVerEntity;

/**
 * 
 * TODO(数据版本缓存类)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:chengang,date:2012-12-28 上午10:23:11,content:TODO
 * </p>
 * 
 * @author chengang
 * @date 2012-12-28 上午10:23:11
 * @since
 * @version
 */
public class DataVerCache extends
		DefaultTTLRedisCache<BaseDataVerEntity> {
	private static final Log LOG = LogFactory.getLog(DataVerCache.class);
	private IBaseDao<BaseDataVerEntity> baseDao;
	
	@Override
	public String getUUID() {
		return DataVerCache.class.getName();
	}

	public BaseDataVerEntity getBaseDataVer(String key){
		BaseDataVerEntity baseDataVerEntity = null;
		try {
			baseDataVerEntity = get(key);
		} catch (Exception e) {
			LOG.error("缓存异常:"+e.getMessage());
			baseDataVerEntity = baseDao.getEntityById(key); 
		}
		return baseDataVerEntity;
	}

	public void setBaseDao(IBaseDao<BaseDataVerEntity> baseDao) {
		this.baseDao = baseDao;
	}
	
}
