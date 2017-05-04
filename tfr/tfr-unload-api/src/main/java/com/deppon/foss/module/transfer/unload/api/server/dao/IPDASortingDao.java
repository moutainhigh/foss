/*
 * PROJECT NAME: tfr-unload-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.api.server.dao
 * FILE    NAME: IPDASortingDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.unload.api.shared.domain.SortingScanEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.SortingScanCompareDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.SortingScanDto;
import com.deppon.foss.module.transfer.unload.api.shared.vo.SortingScanVo;

/**
 * IPDASortingDao
 * @author dp-duyi
 * @date 2013-7-26 上午11:42:26
 */
public interface IPDASortingDao {
	public void insertSortingScan(SortingScanEntity sortingScanEntity);
	public int insertSortingScans(List<SortingScanEntity> entitys);
	public List<SortingScanEntity> selectSortingScan(SortingScanVo vo);
	public List<SortingScanEntity> selectSortingScan(SortingScanVo vo, int limit , int start);
	
	/**
	 * 根据条件查询所有分拣扫描数据
	 * @Author: 200978  xiaobingcheng
	 * 2014-9-2
	 * @param sortingScanDto
	 * @return
	 */
	List<SortingScanEntity> querySortingScan(SortingScanDto sortingScanDto);
	
	/**
	 * 分拣扫描-查询库存数据
	 * @Author: 200978  xiaobingcheng
	 * 2014-9-2
	 * @param sortingScanDto
	 * @return
	 */
	List<SortingScanCompareDto> querySortingScanStock(SortingScanDto sortingScanDto);
	
	/**
	 * 分拣扫描-查询电子运单分拣记录
	 * @Author: 218427  hwy
	 * 2016-3-1
	 * @param dto
	 * @return
	 */
	List<SortingScanEntity> queryEwayBillRecords(SortingScanDto dto, int start, int limit);
	
	/**
	 * 分拣扫描-查询电子运单分拣记录数
	 * @Author: 218427  hwy
	 * 2016-3-2
	 * @param dto
	 * @return
	 */
	Long queryEwayBillRecordsCount(SortingScanDto dto);
	
	/**
	 * 分拣扫描 记录数
	 * @author 332209 ruilibao
	 * 2017-03-23
	 * 
	 */
	Long querySortingScanCount(SortingScanVo vo);
}
