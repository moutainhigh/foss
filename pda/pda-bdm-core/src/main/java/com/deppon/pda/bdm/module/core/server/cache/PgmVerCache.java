package com.deppon.pda.bdm.module.core.server.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.cache.DefaultTTLRedisCache;
import com.deppon.pda.bdm.module.core.server.dao.IBaseDao;
import com.deppon.pda.bdm.module.core.shared.domain.PgmVerEntity;

/**
 * 
 * TODO(程序版本缓存类)
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
public class PgmVerCache extends DefaultTTLRedisCache<PgmVerEntity> {
	private static final Log LOG = LogFactory.getLog(PgmVerEntity.class);
	private IBaseDao<PgmVerEntity> baseDao;
	
	@Override
	public String getUUID() {
		return PgmVerCache.class.getName();
	}
	
	public PgmVerEntity getPgmVer(String key){
		PgmVerEntity pgmVerEntity = null;
		try {
			pgmVerEntity = get(key);
			if(pgmVerEntity == null) {
				pgmVerEntity = baseDao.getEntityById(key);
			}
		} catch (Exception e) {
			LOG.error("缓存异常:"+e.getMessage());
			pgmVerEntity = baseDao.getEntityById(key); 
		}
		return pgmVerEntity;
	}

	public void setBaseDao(IBaseDao<PgmVerEntity> baseDao) {
		this.baseDao = baseDao;
	}

}
