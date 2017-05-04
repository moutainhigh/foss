/*
 * PROJECT NAME: tfr-unload
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.server.dao.impl
 * FILE    NAME: PDASortingDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.unload.api.server.dao.IBatchSaveProcessDao;
import com.deppon.foss.module.transfer.unload.api.server.dao.IPDASortingDao;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.module.transfer.unload.api.shared.domain.SortingScanEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.SortingScanCompareDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.SortingScanDto;
import com.deppon.foss.module.transfer.unload.api.shared.vo.SortingScanVo;

/**
 * PDASortingDao
 * @author dp-duyi
 * @date 2013-7-26 下午1:49:05
 */
@SuppressWarnings("unchecked")
public class PDASortingDao extends iBatis3DaoImpl implements IPDASortingDao{
	private static final String NAMESPACE = "tfr.unload.sorting.";
	private IBatchSaveProcessDao batchSaveProcessDao;
	public void setBatchSaveProcessDao(IBatchSaveProcessDao batchSaveProcessDao) {
		this.batchSaveProcessDao = batchSaveProcessDao;
	}
	/** 
	 * insertSortingScan
	 * @author dp-duyi
	 * @date 2013-7-26 下午1:49:36
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDASortingDao#insertSortingScan(com.deppon.foss.module.transfer.unload.api.shared.domain.SortingScanEntity)
	 */
	@Override
	public void insertSortingScan(SortingScanEntity sortingScanEntity) {
		this.getSqlSession().insert(NAMESPACE+"insertSortingScan", sortingScanEntity);
	}
	/** 
	 * 查询分拣扫描
	 * @author dp-duyi
	 * @date 2013-7-26 下午2:38:58
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDASortingDao#selectSortingScan(com.deppon.foss.module.transfer.unload.api.shared.vo.SortingScanVo)
	 */
	@Override
	public List<SortingScanEntity> selectSortingScan(SortingScanVo vo, int limit , int start) {
		//分页
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE+"selectSortingScan", vo, rowBounds);
	}
	
	/**
	 * 
	 * <p>根据包号批量插入分拣信息</p> 
	 * @author alfred
	 * @date 2014-10-28 上午10:08:36
	 * @param entitys
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDASortingDao#insertSortingScans(java.util.List)
	 */
	@Override
	public int insertSortingScans(List<SortingScanEntity> entitys) {
		return batchSaveProcessDao.batchSaveProcess(UnloadConstants.BATCH_SAVE_TYPE_INSERT, NAMESPACE+"insertSortingScans", entitys);
	
	}
	
	/** 
	 * 查询分拣扫描
	 * @author dp-duyi
	 * @date 2013-7-26 下午2:38:58
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDASortingDao#selectSortingScan(com.deppon.foss.module.transfer.unload.api.shared.vo.SortingScanVo)
	 */
	@Override
	public List<SortingScanEntity> selectSortingScan(SortingScanVo vo) {
		//分页
		return this.getSqlSession().selectList(NAMESPACE+"selectSortingScan", vo);
	}

	/**
	 * 根据条件查询所有分拣扫描数据
	 * @Author: 200978  xiaobingcheng
	 * 2014-9-2
	 * @param sortingScanDto
	 * @return
	 */
	public List<SortingScanEntity> querySortingScan(SortingScanDto sortingScanDto){
		return this.getSqlSession().selectList(NAMESPACE+"querySortingScan", sortingScanDto);
	}
	
	/**
	 * 分拣扫描-查询库存数据
	 * @Author: 200978  xiaobingcheng
	 * 2014-9-2
	 * @param sortingScanDto
	 * @return
	 */
	public List<SortingScanCompareDto> querySortingScanStock(SortingScanDto sortingScanDto){
		return this.getSqlSession().selectList(NAMESPACE+"querySortingScanStock", sortingScanDto);
	}
	
	/**
	 * 分拣扫描 -查询电子运单分拣记录
	 * @author 218427 hwy
	 * 2016-03-01
	 * 
	 */
	@Override
	public List<SortingScanEntity> queryEwayBillRecords(SortingScanDto dto,int start, int limit){
		return this.getSqlSession().selectList(NAMESPACE+"queryEwayBillRecords",dto);
	}
	
	/**
	 * 分拣扫描 -查询电子运单分拣记录数
	 * @author 218427 hwy
	 * 2016-03-02
	 * 
	 */
	@Override
	public Long queryEwayBillRecordsCount(SortingScanDto dto){
		return (Long) this.getSqlSession().selectOne(NAMESPACE+"queryEwayBillRecordsCount",dto);
	}
	
	/**
	 * 分拣扫描 记录数
	 * @author 332209 ruilibao
	 * 2017-03-23
	 * 
	 */
	@Override
	public Long querySortingScanCount(SortingScanVo vo) {
		Integer count = (Integer) this.getSqlSession().selectOne(NAMESPACE+"querySortingScanCount", vo);
		return new Long(count);
	}

}
