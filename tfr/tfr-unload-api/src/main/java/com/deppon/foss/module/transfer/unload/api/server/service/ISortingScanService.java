/*
 * PROJECT NAME: tfr-unload-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.api.server.service
 * FILE    NAME: ISortingScanService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.api.server.service;

import java.util.List;

import com.deppon.foss.module.transfer.unload.api.shared.domain.SortingScanEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.SortingScanCompareDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.SortingScanDto;
import com.deppon.foss.module.transfer.unload.api.shared.vo.SortingScanVo;


/**
 * ISortingScanService
 * @author dp-duyi
 * @date 2013-7-26 下午2:43:48
 */
public interface ISortingScanService {
	public List<SortingScanEntity> selectSortingScan(SortingScanVo vo, int limit, int start);
	@SuppressWarnings("rawtypes")
	public List exportSortingScanToExcel(SortingScanVo vo, int limit, int start);
	
	/**
	 * 获取分拣扫描 记录数
	 * @author 332209 ruilibao
	 * 2017-03-23
	 * 
	 */
	Long sortingScanCount(SortingScanVo vo);
	
	/**
	 * 查询分拣扫描与库存数据的比对结果
	 * @Author: 200978  xiaobingcheng
	 * 2014-9-2
	 * @param sortingScanDto
	 * @param start
	 * @param limit
	 * @return
	 */
	List<SortingScanCompareDto> querySortingScanCompare(SortingScanDto sortingScanDto,int start,int limit);
	
	/**
	 * 获取比对结果总条数
	 * @Author: 200978  xiaobingcheng
	 * 2014-9-2
	 * @return
	 */
	Long querySortingScanCompareCount();
	
	/**
	 * 通过接送货传参查询出电子运单分拣记录
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 * @author 218427 foss-hongwy 
	 */
	List<SortingScanEntity> queryEwayBillRecords(SortingScanDto dto,int start,int limit);
	
	/**
	 * 查询出电子运单记录数
	 * @author 218427 foss-hongwy 
	 */
	Long queryEwayBillRecordsCount(SortingScanDto dto);
	
}
