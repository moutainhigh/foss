package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonResourceDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonResourceService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity;

public class CommonResourceService implements ICommonResourceService{
	private ICommonResourceDao commonResourceDao;
	@Override
	public List<ResourceEntity> queryResourceExactByEntity(
			ResourceEntity entity, int start, int limit) {
		List<ResourceEntity> entityResults = commonResourceDao
				.queryResourceExactByEntity(entity, start, limit);
		
		return entityResults;
	}

	@Override
	public long queryResourceExactByEntityCount(ResourceEntity entity) {
		return commonResourceDao.queryResourceExactByEntityCount(entity);
	
	}

	public ICommonResourceDao getCommonResourceDao() {
		return commonResourceDao;
	}

	public void setCommonResourceDao(ICommonResourceDao commonResourceDao) {
		this.commonResourceDao = commonResourceDao;
	}
	

}
