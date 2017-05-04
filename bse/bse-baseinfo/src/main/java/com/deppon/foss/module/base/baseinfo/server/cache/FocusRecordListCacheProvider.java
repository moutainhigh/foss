package com.deppon.foss.module.base.baseinfo.server.cache;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IFocusRecordManagementDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FocusRecordManagementEntity;
/**
 * 集中开单管理cache provider类
 * @author foss-wusuhua
 * @date 2015-6-24 10:26
 * @version 1.0
 */
public class FocusRecordListCacheProvider implements ITTLCacheProvider<List<FocusRecordManagementEntity>>{
	
	/**
	 * 集中开单管理的Dao
	 */
	private IFocusRecordManagementDao focusRecordManagementDao;
	
	
	public void setFocusRecordManagementDao(
			IFocusRecordManagementDao focusRecordManagementDao) {
		this.focusRecordManagementDao = focusRecordManagementDao;
	}

	/**
	 * 缓存没有就会走这个防辐射
	 */
	@Override
	public List<FocusRecordManagementEntity> get(String key) {
		// TODO Auto-generated method stub
		if(StringUtils.isBlank(key)){
			return null;
		}
		return focusRecordManagementDao.queryAllBillingGroup();
	}

}
