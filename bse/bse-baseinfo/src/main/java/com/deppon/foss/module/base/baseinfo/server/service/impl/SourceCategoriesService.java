package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISourceCategoriesDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISourceCategoriesService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SourceCategoriesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SourceCategoriesDto;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.SourceCategoriesVo;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.SourceCategoriesCondition;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 行业货源品类service
 * @author 198771
 *
 */
public class SourceCategoriesService implements ISourceCategoriesService {
	                             
	private ISourceCategoriesDao sourceCategoriesDao;
	
	private IDataDictionaryService dataDictionaryService;
	

	public void setDataDictionaryService(
			IDataDictionaryService dataDictionaryService) {
		this.dataDictionaryService = dataDictionaryService;
	}

	public void setSourceCategoriesDao(ISourceCategoriesDao sourceCategoriesDao) {
		this.sourceCategoriesDao = sourceCategoriesDao;
	}

	//点击按钮增加
	@Override
	public SourceCategoriesVo addSourceCategoriesEntity(
			SourceCategoriesVo sourceCategoriesVo) {
		SourceCategoriesVo vo = new SourceCategoriesVo();
		SourceCategoriesEntity entity = sourceCategoriesVo.getSourceCategoriesEntity();
		//去掉前后空白
		if(null==entity){
			throw new BusinessException("行业货源类别实体");
		}
		entity.setName(entity.getName().trim());
		List<SourceCategoriesEntity> sourceCategoriesEntitys = sourceCategoriesDao.querySourceCategoriesEntitysByName(entity.getName());
		List<String> sourceCategoriesIds = null;
		//判断品名是否已经被占用
		if(sourceCategoriesEntitys!=null&&sourceCategoriesEntitys.size()>0){
			sourceCategoriesIds = new ArrayList<String>();
			for(SourceCategoriesEntity sourceCategoriesEntity:sourceCategoriesEntitys){
				sourceCategoriesIds.add(sourceCategoriesEntity.getId());
			}
			this.deleteSourceCategoriesEntitys(sourceCategoriesIds);
		}
		if(entity!=null){
			//创建时间为当前时间
			entity.setId(UUIDUtils.getUUID());
			entity.setCreateDate(new Date());
			entity.setActive(FossConstants.ACTIVE);
		}
		sourceCategoriesDao.addSourceCategoriesEntity(sourceCategoriesVo.getSourceCategoriesEntity());
		vo.setSourceCategoriesEntity(entity);
		vo.setCondition(sourceCategoriesVo.getCondition());
		return vo;
	}

	/**
	 * 修改
	 */
	@Override
	public SourceCategoriesVo updateSourceCategoriesEntity(
			SourceCategoriesEntity sourceCategoriesEntity) {
		SourceCategoriesVo vo = new SourceCategoriesVo();
		//根据品名去查找,若查到则删除
		if(sourceCategoriesEntity==null){
			throw new BusinessException("行业货源类别实体");	
		}
		List<SourceCategoriesEntity> sourceCategoriesEntitys = sourceCategoriesDao.querySourceCategoriesEntitysByName(
				sourceCategoriesEntity.getName().trim());
		List<String> sourceCategoriesIds = null;
		if(sourceCategoriesEntitys!=null&&sourceCategoriesEntitys.size()>0){
			sourceCategoriesIds = new ArrayList<String>();
			for(SourceCategoriesEntity entity:sourceCategoriesEntitys){
				sourceCategoriesIds.add(entity.getId());
			}
			this.deleteSourceCategoriesEntitys(sourceCategoriesIds);
		}
		if(sourceCategoriesEntity!=null){
			//设置为有效
			sourceCategoriesEntity.setActive(FossConstants.ACTIVE);
			//这里只是修改时间为当前时间,修改人、状态从页面传过来的
			sourceCategoriesEntity.setModifyDate(new Date());
			//设置去掉前后导空白
			sourceCategoriesEntity.setName(sourceCategoriesEntity.getName().trim());
			vo.setSourceCategoriesEntity(sourceCategoriesDao.updateSourceCategoriesEntity(sourceCategoriesEntity));
		}
		return vo;
	}

	public List<SourceCategoriesDto> querySourceCategoriesEntitysByCondition(SourceCategoriesCondition condition,
			int limit,int start){
		return sourceCategoriesDao.querySourceCategoriesEntitys(condition,limit,start);
	}
	
	@Override
	public SourceCategoriesVo querySourceCategoriesEntitys(
			SourceCategoriesCondition condition,int limit,int start) {
		SourceCategoriesVo vo = new SourceCategoriesVo();
		if(condition!=null){
			//品名通过模糊查询获得
			if(condition.getName()!=null){
				condition.setName("%"+condition.getName().trim()+"%");
			}
			List<SourceCategoriesDto> sourceCategoriesDtos = this.querySourceCategoriesEntitysByCondition(condition,limit,start);
			for(SourceCategoriesDto dto:sourceCategoriesDtos){
				//如果创建人离职就将创建人设置为系统
				if(dto.getCreateDate()!=null&&dto.getCreateUser()==null){
					dto.setCreateUser("系统");
				}
				//如果修改人离职就将修改人设置为系统
				if(dto.getModifyDate()!=null&&dto.getModifyUser()==null){
					dto.setModifyUser("系统");
				}
			}
			vo.setCondition(condition);
			vo.setSourceCategoriesDtos(sourceCategoriesDtos);
		}
		return vo;
	}

	@Override
	public long getTotalRecord(SourceCategoriesCondition condition) {
		return sourceCategoriesDao.getTotalRecord(condition);
	}


	//点击删除(是非物理删除,只是改ACTIVE为N)
	@Override
	public List<String> deleteSourceCategoriesEntitys(
			List<String> sourceCategoriesIds) {
		return sourceCategoriesDao.deleteSourceCategoriesEntitys(sourceCategoriesIds);
	}

	@Override
	public ExportResource queryExportResource(
			SourceCategoriesVo sourceCategoriesVo) {
		List<List<String>> results = new ArrayList<List<String>>();
		//根据词代码查询所有值代码和值名称
		List<DataDictionaryValueEntity> dataDictionaryValueEntityList = 
				dataDictionaryService.queryDataDictionaryByTermsCode(DictionaryConstants.BSE_SOURCE_CATEGORY)
				.getDataDictionaryValueEntityList(); 
		ExportResource sheet = new ExportResource();
		SourceCategoriesCondition condition = sourceCategoriesVo.getCondition();
		try {
			//页面通过get方式提交,转换编码,防止乱码查不到
			if(condition!=null&&condition.getName()!=null&&!"".equals(condition.getName())){
				condition.setName("%"+new String(condition.getName().getBytes("ISO8859-1"), "UTF-8").trim()+"%");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//如果导出模板则不用查询数据
		if(!sourceCategoriesVo.isTemplate()){
			List<SourceCategoriesEntity> sourceCategoriesEntitys  = 
					sourceCategoriesDao.exportSourceCategoriesEntitys(condition);
			List<String> result = null;
			int j=0;
			//遍历符合条件的行业货源信息
			if(sourceCategoriesEntitys!=null){
				for(int i=0;i<sourceCategoriesEntitys.size();i++){
					result = new ArrayList<String>();
					//将行业货源数据字典的值代码转换为值名称
					for(j=0;j<dataDictionaryValueEntityList.size();j++){
						if(sourceCategoriesEntitys.get(i).getCategory().
								equals(dataDictionaryValueEntityList.get(j).getValueCode())){
							result.add(dataDictionaryValueEntityList.get(j).getValueName());
							break;
						}
					}
					//如果没找到将值名称设置为数据库存储的类别
					if(j==dataDictionaryValueEntityList.size()){
						result.add(sourceCategoriesEntitys.get(i).getCategory());
					}
					result.add(sourceCategoriesEntitys.get(i).getName());
					results.add(result);
				}
			}
		}
		sheet.setHeads(ComnConst.SOURCE_CATEGORY);
		sheet.setRowList(results);
		return sheet;
	}

	@Override
	public void importSourceCategoriesEntitys(
			List<SourceCategoriesEntity> sourceCategoriesEntitys) {
		List<String> sourceCategoriesIds = null;
		List<SourceCategoriesEntity> scs = null;
		List<SourceCategoriesEntity> insertScs = new ArrayList<SourceCategoriesEntity>();
		List<String> deleteSourceCategoriesIds = new ArrayList<String>();
		for(SourceCategoriesEntity sourceCategoriesEntity:sourceCategoriesEntitys){
			scs = this.querySourceCategoriesEntitysByName(sourceCategoriesEntity.getName());
			//如果品名存在先作废再增加,不存在就新增
			if(scs!=null&&scs.size()>0){
				sourceCategoriesIds = new ArrayList<String>();
				for(SourceCategoriesEntity entity:scs){
					sourceCategoriesIds.add(entity.getId());
				}
				//this.deleteSourceCategoriesEntitys(sourceCategoriesIds);
				deleteSourceCategoriesIds.addAll(sourceCategoriesIds);
			}
			insertScs.add(sourceCategoriesEntity);
			//批量插入,达到40条插入一次
			if(insertScs.size()==NumberConstants.NUMBER_40){
				this.insertSourceCategoriesEntity(insertScs);
				//插入删除之后将集合里面的对象给移除掉
				insertScs.clear();
				if(deleteSourceCategoriesIds.size()>0){
					this.deleteSourceCategoriesEntitys(deleteSourceCategoriesIds);
					deleteSourceCategoriesIds.clear();
				}
			}
			
		}
		//如果最后一次没有40条数据，就做最后的插入和作废
		if(insertScs.size()>0){
			this.insertSourceCategoriesEntity(insertScs);
		}
		if(deleteSourceCategoriesIds.size()>0){
			this.deleteSourceCategoriesEntitys(deleteSourceCategoriesIds);
		}
	}
	

	//根据品名去查询Id
	@Override
	public List<SourceCategoriesEntity> querySourceCategoriesEntitysByName(
			String name) {
		return sourceCategoriesDao.querySourceCategoriesEntitysByName(name);
	}

	@Override
	public List<SourceCategoriesEntity> insertSourceCategoriesEntity(
			List<SourceCategoriesEntity> sourceCategoriesEntitys) {
		for(SourceCategoriesEntity sourceCategoriesEntity:sourceCategoriesEntitys){
			if(sourceCategoriesEntity!=null){
				//创建时间为当前时间
				sourceCategoriesEntity.setId(UUIDUtils.getUUID());
				sourceCategoriesEntity.setCreateDate(new Date());
				sourceCategoriesEntity.setActive(FossConstants.ACTIVE);
			}
		}
		sourceCategoriesDao.importSourceCategoriesEntitys(sourceCategoriesEntitys);
		return sourceCategoriesEntitys;
	}

}
