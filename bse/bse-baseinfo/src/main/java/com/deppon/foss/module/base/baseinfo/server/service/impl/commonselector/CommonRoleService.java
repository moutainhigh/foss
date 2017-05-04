package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonRoleDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonRoleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonRoleEntity;


public class CommonRoleService implements ICommonRoleService {
	
	/**
     * 下面是dao对象的声明及get,set方法：
     */
    private ICommonRoleDao commonroleDao;
  
    /**
     * 模糊查询 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lifanghong
     * @date 2013-04-26
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleService#deleteRoleMore(java.lang.String[])
     */
    @Override
    public List<CommonRoleEntity> queryRoleByEntity(CommonRoleEntity commonentity, int start,
	    int limit) {
	List<CommonRoleEntity> resultEntity = commonroleDao.queryRoleByEntity(commonentity,start, limit);
	return resultEntity;
    }

    /**
     * 动态的查询条件-查询总条数。
     * 
     * 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lifanghong
     * @date 2013-04-26
     * @see com.deppon.foss.module.baseinfo.server.service.IRoleService#queryRoleCountByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.district.shared.domain.RoleEntity)
     */
    @Override
    public long queryRoleByEntityCount(CommonRoleEntity commonentity) {
	return commonroleDao.queryRoleByEntityCount(commonentity);
    }

	public ICommonRoleDao getCommonroleDao() {
		return commonroleDao;
	}

	public void setCommonroleDao(ICommonRoleDao commonroleDao) {
		this.commonroleDao = commonroleDao;
	}

    



}
