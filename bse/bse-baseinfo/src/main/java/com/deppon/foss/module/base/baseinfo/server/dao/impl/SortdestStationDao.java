package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISortdestStationDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SortdestStationEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SortdestStationDto;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

public class SortdestStationDao extends iBatis3DaoImpl implements ISortdestStationDao {

	private static final String NAMESPACE="foss.bse.bse-baseinfo.sortdestStation.";
	
	/**
	 * 
	* @Title: queryAll 
	* @Description: 
	* @param @param sortdestStationEntity
	* @param @return    设定文件 
	* @return List<SortdestStationEntity>    返回类型 
	* @throws
	 */
	@SuppressWarnings("unchecked") 
	@Override
	public List<SortdestStationEntity> queryAll(
			SortdestStationEntity sortdestStationEntity,int start,int limit) {
		RowBounds rowBounds = new RowBounds(limit,start);
		Set<String> roleids=FossUserContext.getCurrentInfo().getUser().getRoleids();
		//  分拣系统研究员
		//313353 sonar优化
//		if(roleids.contains("BSE_SORTING_SYSTEM_RESEARCHER")){
//			//nothing to do
//			;
//		}else 
		if(roleids.contains("BSE_EXPRESS_TRF_PERSON")){
			////快递中转场人员  
			sortdestStationEntity.setCreateUser(FossUserContext.getCurrentInfo().getEmpCode());
		}else{
			return null;
		}
		
		return this.getSqlSession().selectList(NAMESPACE+"queryAll", sortdestStationEntity,rowBounds);
	}
	

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SortdestStationEntity> queryAll(
			SortdestStationEntity sortdestStationEntity) {
		 
		  return this.getSqlSession().selectList(NAMESPACE+"queryAll",sortdestStationEntity);
	}

	
	
	/**
	 * 
	* @Title: sortDestStationInsert 
	* @Description: 
	* @param @param sortdestStationEntity    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@Override
	public void sortDestStationInsert(
			SortdestStationEntity sortdestStationEntity) {
		 
		this.getSqlSession().insert(NAMESPACE+"sortdestStationInsert", sortdestStationEntity);
	}
	

	/**
	 * 
	* @Title: sortDestStationUpdate 
	* @Description: 
	* @param @param sortdestStationEntity    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@Override
	public void sortDestStationUpdate(
			SortdestStationEntity sortdestStationEntity) {
		this.getSqlSession().update(NAMESPACE+"sortdestStationUpdate",sortdestStationEntity);
		
	}
	/**
	 * 
	 */
	@Override
	public Long countAll(SortdestStationEntity sortdestStationEntity) {
		 
		return (Long) this.getSqlSession().selectOne(NAMESPACE+"countAll", sortdestStationEntity);
	}
	/**
	 * 
	 */
	@Override
	public void sortDestStationDeleteByCode(Map<String, Object> map) {
		 
		this.getSqlSession().update(NAMESPACE+"deleteByCode", map);
	}

//----------------------------------------------------------------------------------
	/**
	 * 
	* @Title: sortDestStationInfoInsert 
	* @Description: 
	* @param @param sortdestStationDtos    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@Override
	public void sortDestStationInfoInsert(
			List<SortdestStationDto> sortdestStationDtos) {
		 
		this.getSqlSession().insert(NAMESPACE+"sortdestStationInfoInserts", sortdestStationDtos);
	}
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SortdestStationDto> querySortChildAll(
			SortdestStationDto sortdestStationDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(limit,start);
		return this.getSqlSession().selectList(NAMESPACE+"querySortChildAll", sortdestStationDto, rowBounds);
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SortdestStationDto> querySortChildAll(
			SortdestStationDto sortdestStationDto) {
		return this.getSqlSession().selectList(NAMESPACE+"querySortChildAll", sortdestStationDto);
	}
	@Override
	public Long countAllSortChild(SortdestStationDto sortdestStationDto) {
		 
		return (Long) this.getSqlSession().selectOne(NAMESPACE+"countAllSortChild", sortdestStationDto);
	}


	@Override
	public void sortDestStationInfoInsert(
			SortdestStationDto sortdestStationDto) {
		 
		this.getSqlSession().insert(NAMESPACE+"sortDestStationInfoInsert",sortdestStationDto);
	}


	@Override
	public void sortDestStationInfoDel(Map<String, Object> map) {
		 
		this.getSqlSession().update(NAMESPACE+"sortDestStationInfoDel", map);
	}
	/**
	 * 
	* @Title: sortDestStationInfoUpdate 
	* @Description: 
	* @param @param sortdestStationDto    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@Override
	public void sortDestStationInfoUpdate(SortdestStationDto sortdestStationDto) {
		 
		this.getSqlSession().update(NAMESPACE+"sortdestStationInfoUpdate", sortdestStationDto);
	}


	/**
	 * 
	 */
	@Override
	public void sortDestStationInfoByVirtualCodeDel(Map<String, Object> map) {
		 
		this.getSqlSession().update(NAMESPACE+"sortDestStationInfoByVirtualCodeDel", map);
	}
	
}
