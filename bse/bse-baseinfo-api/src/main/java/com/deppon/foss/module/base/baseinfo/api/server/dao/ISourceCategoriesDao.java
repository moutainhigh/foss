package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SourceCategoriesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SourceCategoriesDto;
import com.deppon.foss.module.frameworkimpl.shared.domain.SourceCategoriesCondition;

/**
 * 行业货源类别基础资料
 * @author 198771
 *
 */
public interface ISourceCategoriesDao {
	
	//增加
	SourceCategoriesEntity addSourceCategoriesEntity(SourceCategoriesEntity sourceCategoriesEntity);
	//修改
	SourceCategoriesEntity updateSourceCategoriesEntity(SourceCategoriesEntity sourceCategoriesEntity);
	//根据条件查询
	List<SourceCategoriesDto> querySourceCategoriesEntitys(SourceCategoriesCondition condition,int limit,int start);
	//根据条件获取总条数
	long getTotalRecord(SourceCategoriesCondition condition);
	//删除
	List<String> deleteSourceCategoriesEntitys(List<String> sourceCategoriesIds);
	//根据品名查询
	List<SourceCategoriesEntity> querySourceCategoriesEntitysByName(String name);
	//根据条件查询导出的数据
	List<SourceCategoriesEntity> exportSourceCategoriesEntitys(SourceCategoriesCondition condition);
	//批量导入
	List<SourceCategoriesEntity> importSourceCategoriesEntitys(List<SourceCategoriesEntity> sourceCategoriesEntitys);
}
