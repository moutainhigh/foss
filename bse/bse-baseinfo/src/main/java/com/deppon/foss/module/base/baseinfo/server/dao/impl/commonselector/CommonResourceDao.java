package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonResourceDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity;
import com.deppon.foss.util.define.FossConstants;

public class CommonResourceDao extends SqlSessionDaoSupport implements
ICommonResourceDao {
	/**
	 * 087584-foss-lijun 权限的数据库访问常用方法
	 */

	private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX
			+ ".CommonResourceEntity.";

	@Override
	public long queryResourceExactByEntityCount(ResourceEntity entity) {
		ResourceEntity queryEntity;
		if (null == entity) {
			queryEntity = new ResourceEntity();
		} else {
			queryEntity = entity;
		}
		queryEntity.setActive(FossConstants.ACTIVE);
		return (Long) getSqlSession().selectOne(
				NAMESPACE + "queryResourceExactByEntityCount", queryEntity);
	}

	/**
	 * 精确查询 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * 
	 * @author 087584-foss-lifanghong
	 * @date 2013-05-29
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICommonResourceDao#queryResourceExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity,
	 *      int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ResourceEntity> queryResourceExactByEntity(
			ResourceEntity entity, int start, int limit) {
		ResourceEntity queryEntity;
		if (null == entity) {
			queryEntity = new ResourceEntity();
		} else {
			queryEntity = entity;
		}
		queryEntity.setActive(FossConstants.ACTIVE);
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(
				NAMESPACE + "queryResourceExactByEntity", queryEntity,
				rowBounds);
	}

}
