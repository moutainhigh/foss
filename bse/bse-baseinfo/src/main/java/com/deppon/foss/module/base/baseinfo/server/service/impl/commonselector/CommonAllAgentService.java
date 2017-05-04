package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;


import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonAllAgentDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonAllAgentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AllAgentEntity;




public class CommonAllAgentService implements ICommonAllAgentService {
/**
 * "航空代理"Dao.
 */	
private ICommonAllAgentDao commonAllAgentDao;
/**
 * 根据条件查询人员信息.
 *
 * @param vo :查询条件
 * @param start the start
 * @param limit the limit
 * @return 代理集合
 * @author lifanghong
 * @date 2013-05-16
 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonAllAgentService#queryAgentByCondtion(com.deppon.foss.module.base.baseinfo.api.shared.domain.AllAgentEntity)
 */
@Override
public List<AllAgentEntity> queryAgentByCondtion(AllAgentEntity allAgentEntity,
		int limit, int start) {
	if(("true").equals(allAgentEntity.getAll())){
		allAgentEntity.setActive(null);
	}
	List<AllAgentEntity> allAgentEntitys = commonAllAgentDao.queryAgentByCondtion(allAgentEntity, limit, start);
	// TODO Auto-generated method stub
	return allAgentEntitys;
}
/**
 * 查询的总条数.
 *
 * @param vo the vo
 * @return the long
 * @author lifanghong
 * @date 2013-05-16
 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.CommonAllAgentService#countRecordByCondtion(com.deppon.foss.module.base.baseinfo.api.shared.domain.AllAgentEntity)
 */
@Override
public Long countRecordByCondtion(AllAgentEntity allAgentEntity) {
	if(("true").equals(allAgentEntity.getAll())){
		allAgentEntity.setActive(null);
}
	return commonAllAgentDao.countRecordByCondtion(allAgentEntity);
}

public void setCommonAllAgentDao(ICommonAllAgentDao commonAllAgentDao) {
	this.commonAllAgentDao = commonAllAgentDao;
}
public ICommonAllAgentDao getCommonAllAgentDao() {
	return commonAllAgentDao;
}


	

}
