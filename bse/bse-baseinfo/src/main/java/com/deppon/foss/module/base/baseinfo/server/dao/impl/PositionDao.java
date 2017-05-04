package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPositionDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PositionEntity;
import com.deppon.foss.util.define.FossConstants;

public class PositionDao extends SqlSessionDaoSupport implements IPositionDao{
	 private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX
			    + ".position.";
	
	/**
     * 模糊查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * @author 130346-foss-lifanghong
     * @date 2014-4-29 下午4:10:24
     */
	@Override
	public List<PositionEntity> queryPositionByEntitySelector(
			PositionEntity entity, int start, int limit) {
		PositionEntity queryEntity;
    	if (null == entity) {
    		queryEntity = new PositionEntity();
    	}else{
    		queryEntity = entity;
    	}
    	queryEntity.setActive(FossConstants.ACTIVE);
    	RowBounds rowBounds = new RowBounds(start, limit);
    	return getSqlSession()
    			.selectList(NAMESPACE + "queryEmployeeExactByEntity4Selector",
    					queryEntity,
    					rowBounds);
	}
	 /**
     * 模糊查询--查询符合条件的总数
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * @author 130346-foss-lifanghong
     * @date 2014-4-29 下午4:10:24
     */
	@Override
	public long countPositionByEntitySelector(PositionEntity entity) {
		PositionEntity queryEntity;
    	if (null == entity) {
    		queryEntity = new PositionEntity();
    	}else{
    		queryEntity = entity;
    	}
    	queryEntity.setActive(FossConstants.ACTIVE);
    	return  (Long)getSqlSession()
    			.selectOne(NAMESPACE + "countEmployeeExactByEntity4Selector",
    					queryEntity);
	}

}
