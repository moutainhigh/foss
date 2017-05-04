package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SortdestStationEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SortdestStationDto;

public interface ISortdestStationService {

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
	public void sortDestStationInfoInserts(List<SortdestStationDto> sortdestStationDtos);
	/**
	 * 
	* @Title: sortDestStationUpdate 
	* @Description: 
	* @param @param sortdestStationEntity    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void sortDestStationUpdate(SortdestStationEntity sortdestStationEntity);

	/**
	 * 
	* @Title: sortDestStationDelByCode 
	* @Description:  
	* @param @param codeStr
	* @param @param active    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void sortDestStationDelByCode(String codeStr,String active);
	
	
	
	//-------------------------------------------------
	/**
	 * 
	* @Title: querySortChildAll 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
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
	* @Description: TODO(这里用一句话描述这个方法的作用) 
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
	public void sortDestStationInfoDel(String codeStr);
}
