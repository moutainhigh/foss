package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISourceCategoriesDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SourceCategoriesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SourceCategoriesDto;
import com.deppon.foss.module.frameworkimpl.shared.domain.SourceCategoriesCondition;

/**
 * 行业货源基础资料
 * @author 198771
 *
 */

public class SourceCategoriesDao extends SqlSessionDaoSupport implements ISourceCategoriesDao {

	private final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX+".sourcecategories.";
	
	//点击按钮增加
	@Override
	public SourceCategoriesEntity addSourceCategoriesEntity(
			SourceCategoriesEntity sourceCategoriesEntity) {
		if(sourceCategoriesEntity==null){
			return sourceCategoriesEntity;
		}
		int result = this.getSqlSession().insert(NAMESPACE+"insertSourceCategoriesEntity", sourceCategoriesEntity);
		return result > NumberConstants.ZERO ? sourceCategoriesEntity : null;
	}

	//修改
	@Override
	public SourceCategoriesEntity updateSourceCategoriesEntity(
			SourceCategoriesEntity sourceCategoriesEntity) {
		if(sourceCategoriesEntity==null){
			return sourceCategoriesEntity;
		}
		this.getSqlSession().update(NAMESPACE+"updateSourceCategoriesEntity", sourceCategoriesEntity);
		return sourceCategoriesEntity;
	}

	//分页查询
	@SuppressWarnings({"unchecked"})
	@Override
	public List<SourceCategoriesDto> querySourceCategoriesEntitys(
			SourceCategoriesCondition condition,int limit,int start) {
		if(condition==null){
			return null;
		}
		RowBounds rowBounds = new RowBounds(start, limit);
		List<SourceCategoriesDto> sourceCategoriesEntitys = this.getSqlSession().selectList(NAMESPACE+"querySourceCategoriesEntitys", condition,rowBounds);
		return sourceCategoriesEntitys;
	}

	//根据条件获取总数
	@Override
	public long getTotalRecord(SourceCategoriesCondition condition) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"getToTalRecord", condition);
	}

	//作废
	@Override
	public List<String> deleteSourceCategoriesEntitys(
			List<String> sourceCategoriesIds) {
		this.getSqlSession().update(NAMESPACE+"deleteSourceCategoriesEntity", sourceCategoriesIds);
		return sourceCategoriesIds;
	}

	//根据品名查找
	@SuppressWarnings("unchecked")
	@Override
	public List<SourceCategoriesEntity> querySourceCategoriesEntitysByName(
			String name) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("name", name);
		return (List<SourceCategoriesEntity>)this.getSqlSession().selectList(NAMESPACE+"querySourceCategoriesEntitysByName", map);
	}

	//导出
	@SuppressWarnings("unchecked")
	@Override
	public List<SourceCategoriesEntity> exportSourceCategoriesEntitys(
			SourceCategoriesCondition condition) {
		return this.getSqlSession().selectList(NAMESPACE+"exportSourceCategoriesEntitys", condition);
	}

    //批量导入
	@Override
	public List<SourceCategoriesEntity> importSourceCategoriesEntitys(
			List<SourceCategoriesEntity> sourceCategoriesEntitys) {
		this.getSqlSession().insert(NAMESPACE+"importSourceCategoriesEntitys", sourceCategoriesEntitys);
		return sourceCategoriesEntitys;
	}
	
}
