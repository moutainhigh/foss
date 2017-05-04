package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SourceCategoriesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.SourceCategoriesVo;
import com.deppon.foss.module.frameworkimpl.shared.domain.SourceCategoriesCondition;

/**
 * 行业货源基础资料Service
 * @author 198771
 *
 */
public interface ISourceCategoriesService extends IService{
	
	//点击按钮增加
	SourceCategoriesVo addSourceCategoriesEntity(
			SourceCategoriesVo sourceCategoriesVo);
	//导入增加
	List<SourceCategoriesEntity> insertSourceCategoriesEntity(
			List<SourceCategoriesEntity> sourceCategoriesEntitys);
	//修改
	SourceCategoriesVo updateSourceCategoriesEntity(
			SourceCategoriesEntity sourceCategoriesEntity);
	//根据条件查询
	SourceCategoriesVo querySourceCategoriesEntitys(
			SourceCategoriesCondition condition,int limit,int start);
	//根据条件获取总条数
	long getTotalRecord(SourceCategoriesCondition condition);
	//删除
	List<String> deleteSourceCategoriesEntitys(List<String> sourceCategoriesIds);
	//根据条件查询导出的数据
	ExportResource queryExportResource(SourceCategoriesVo sourceCategoriesVo);
	//导入
	void importSourceCategoriesEntitys(List<SourceCategoriesEntity> sourceCategoriesEntitys);
	//根据品名查询
	List<SourceCategoriesEntity> querySourceCategoriesEntitysByName(String name);
}
