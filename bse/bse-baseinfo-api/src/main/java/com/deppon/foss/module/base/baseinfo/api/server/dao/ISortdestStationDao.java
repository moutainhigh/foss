package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SortdestStationEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SortdestStationDto;

public interface ISortdestStationDao {

	/**
	 * 
	* @Title: queryAll 
	* @Description: 
	* @param @param sortdestStationEntity
	* @param @return    设定文件 
	* @return List<SortdestStationEntity>    返回类型 
	* @throws
	 */
	public List<SortdestStationEntity> queryAll(SortdestStationEntity sortdestStationEntity,int start,int limit);
	
	public List<SortdestStationEntity> queryAll(SortdestStationEntity sortdestStationEntity);
	/**
	 * 
	* @Title: countAll 
	* @Description:  
	* @param @param sortdestStationEntity
	* @param @return    设定文件 
	* @return Long    返回类型 
	* @throws
	 */
	public Long countAll(SortdestStationEntity sortdestStationEntity);
	/**
	 * 
	* @Title: sortDestStationInsert 
	* @Description: 
	* @param @param sortdestStationEntity    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void sortDestStationInsert(SortdestStationEntity sortdestStationEntity);
	/**
	 * 
	* @Title: sortDestStationInfoInsert 
	* @Description: 
	* @param @param sortdestStationDtos    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void sortDestStationInfoInsert(List<SortdestStationDto> sortdestStationDtos);
	/**
	 * 
	* @Title: sortDestStationUpdate 
	* @Description: 
	* @param @param sortdestStationEntity    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void sortDestStationUpdate(SortdestStationEntity sortdestStationEntity);
	
	
	
	public void sortDestStationDeleteByCode(Map<String, Object> map);
	
	//----------------------------------------------------------------
	
	/**
	 * 
	* @Title: querySortChildAll 
	* @Description:  
	* @param @param sortdestStationDto
	* @param @param start
	* @param @param limit
	* @param @return    设定文件 
	* @return List<SortdestStationDto>    返回类型 
	* @throws
	 */
	public List<SortdestStationDto> querySortChildAll(SortdestStationDto sortdestStationDto,int start,int limit);
	/**
	 * 
	* @Title: countAllSortChild 
	* @Description:  
	* @param @param sortdestStationDto
	* @param @return    设定文件 
	* @return Long    返回类型 
	* @throws
	 */
	public Long countAllSortChild(SortdestStationDto sortdestStationDto);
	
	
	/**
	 * 
	* @Title: sortDestStationInfoInsert 
	* @Description:  
	* @param @param sortdestStationEntity    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void sortDestStationInfoInsert(SortdestStationDto sortdestStationDto);
	
	
	/**
	 * 
	* @Title: sortDestStationInfoUpdate 
	* @Description:  
	* @param @param sortdestStationDto    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void sortDestStationInfoUpdate(SortdestStationDto sortdestStationDto);
	
	
	/**
	 * 
	* @Title: sortDestStationInfoDel 
	* @Description:  
	* @param @param sortdestStationDto    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void sortDestStationInfoDel(Map<String, Object> map);
	
	public List<SortdestStationDto> querySortChildAll(
			SortdestStationDto sortdestStationDto);
	
	//
	
	public void sortDestStationInfoByVirtualCodeDel(Map<String, Object> map);

}
