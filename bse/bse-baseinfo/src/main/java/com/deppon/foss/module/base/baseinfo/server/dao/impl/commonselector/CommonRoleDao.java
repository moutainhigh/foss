package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonRoleDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonRoleEntity;
import com.deppon.foss.util.define.FossConstants;

public class CommonRoleDao extends SqlSessionDaoSupport implements ICommonRoleDao {
	/**
     * 下面是基本的DAO操作
     */
    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".roleInfo.";

    /**
     * 模糊查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2013-04-26
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleDao#queryRoleMore(java.lang.String[])
     
     */
    @Override
    public List<CommonRoleEntity> queryRoleByEntity(
	    CommonRoleEntity commonentity, int start, int limit) {
    	CommonRoleEntity queryEntity;
	if (null == commonentity) {
	    queryEntity = new CommonRoleEntity();
	}else{
	    queryEntity = commonentity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	RowBounds rowBounds = new RowBounds(start, limit);

	
	return getSqlSession().selectList(NAMESPACE + "queryRoleByEntity", queryEntity,
			rowBounds);
    }

    /**
     * 模糊查询
     * 
     * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为���糊查询的查询条件
     * 
     * @author 087584-foss-lifanghong
     * @date 2013-04-26
     * @see com.deppon.foss.module.base.baseinfo.server.dao.IRoleDao#queryRoleByEntityCount
     * (com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleEntity)
     * 
     */
    @Override
    public long queryRoleByEntityCount(CommonRoleEntity commonentity) {
	CommonRoleEntity queryEntity;
	if (null == commonentity) {
	    queryEntity = new CommonRoleEntity();
	}else{
	    queryEntity = commonentity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	
	return (Long)getSqlSession().selectOne(NAMESPACE + "queryRoleByEntityCount", queryEntity);
    }

	
  

}
