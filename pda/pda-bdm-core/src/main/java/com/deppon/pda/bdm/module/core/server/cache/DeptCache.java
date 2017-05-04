package com.deppon.pda.bdm.module.core.server.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.cache.DefaultTTLRedisCache;
import com.deppon.pda.bdm.module.core.server.dao.IBaseDao;
import com.deppon.pda.bdm.module.core.shared.domain.DeptEntity;

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
public class DeptCache extends DefaultTTLRedisCache<DeptEntity> {
	private static final Log LOG = LogFactory.getLog(DeptCache.class);
	private IBaseDao<DeptEntity> baseDao;
	
	@Override
	public String getUUID() {
		return DeptCache.class.getName();
	}

	public DeptEntity getDept(String key){
		DeptEntity deptEntity = null;
		try {
			deptEntity = get(key);
		} catch (Exception e) {
			LOG.error("缓存异常:"+e.getMessage());
			deptEntity = baseDao.getEntityById(key); 
		}
		return deptEntity;
	}
	
	public void setBaseDao(IBaseDao<DeptEntity> baseDao) {
		this.baseDao = baseDao;
	}

}
