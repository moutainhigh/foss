/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-stockchecking
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/server/dao/impl/StDifferDetailDao.java
 *  
 *  FILE NAME          :StDifferDetailDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stockchecking.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStDifferDetailDao;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StDifferDetailEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StDifferDetailDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StReportDetailDto;
import com.deppon.foss.util.UUIDUtils;

/**
 * 处理清仓差异报告明细数据
 * @author foss-wuyingjie
 * @date 2012-12-25 上午10:40:58
 */
public class StDifferDetailDao extends iBatis3DaoImpl implements IStDifferDetailDao {

	
	/** 
	 * 通过清仓差异报告ID获取清仓差异报告明细列表
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:37:50
	 * @param stReportId
	 * @return List<StReportDetailDto>
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStDifferDetailDao#queryStReportDetailDtoListById(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StReportDetailDto> queryStReportDetailDtoListById(String stReportId) {
		
		return this.getSqlSession().selectList("foss.tfr.StDifferDetailDao.queryStReportDetailDtoListById", stReportId);
	}

	/** 
	 * 通过查询"少货"、"放错货区"状态的明细，查找出一个月内的相匹配数据
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:38:05
	 * @param stReportId
	 * @param limitedDay
	 * @param scanResultLack
	 * @param scanResultSurplusErrorGoodsarea
	 * @return List<StReportDetailDto>
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStDifferDetailDao#queryStReportRelativeDetailDtoList(java.lang.String, java.util.Date, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StReportDetailDto> queryStReportRelativeDetailDtoList(String stReportId, Date limitedDay, String scanResultLack, String scanResultSurplusErrorGoodsarea) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stReportId", stReportId);
		map.put("limitedDay", limitedDay);
		map.put("scanResultLack", scanResultLack);
		map.put("scanResultSurplusErrorGoodsarea", scanResultSurplusErrorGoodsarea);
		
		return this.getSqlSession().selectList("foss.tfr.StDifferDetailDao.queryStReportRelativeDetailDtoList", map);
	}

	/** 
	 * 更新差异明细的非空项字段
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:38:23
	 * @param stDifferDetail
	 * @return void
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStDifferDetailDao#updateByPrimaryKeySelective(com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StDifferDetailEntity)
	 */
	@Override
	public void updateByPrimaryKeySelective(StDifferDetailEntity stDifferDetail) {
		this.getSqlSession().update("foss.tfr.StDifferDetailDao.updateByPrimaryKeySelective", stDifferDetail);
	}

	/** 
	 * 查询某一清仓差异报告下未处理任务明细的条数
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:38:42
	 * @param id
	 * @param stDifferReportId
	 * @param stockCheckingReportDoing
	 * @return long
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStDifferDetailDao#queryStReportDetailCountByReportId(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public long queryStReportDetailCountByReportId(String id, String stDifferReportId, String stockCheckingReportDoing) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("stDifferReportId", stDifferReportId);
		map.put("stockCheckingReportDoing", stockCheckingReportDoing);
		
		return (Long) this.getSqlSession().selectOne("foss.tfr.StDifferDetailDao.queryStReportDetailCountByReportId", map);
	}

	/** 
	 * 批量新增清仓差异报告明细
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:39:07
	 * @param stDifferDetailListForBatch
	 * @return void 
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStDifferDetailDao#addBatchStDifferReportDetail(java.util.List)
	 */
	@Override
	public void addBatchStDifferReportDetail(List<StDifferDetailEntity> stDifferDetailListForBatch) {
		if(CollectionUtils.isNotEmpty(stDifferDetailListForBatch)){
			this.getSqlSession().insert("foss.tfr.StDifferDetailDao.addBatchStDifferReportDetail", stDifferDetailListForBatch);
		}
	}

	/** 
	 * 按业务起止时间查询需上报的OA差错
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:39:20
	 * @param stReportId
	 * @return List<StDifferDetailDto>
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStDifferDetailDao#queryStOAErrorByBatch(java.util.Date, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StDifferDetailDto> queryStOAErrorByBatch(String stReportId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("stReportId", stReportId);
		params.put("goodsStatusLack", TransferConstants.GOODS_STATUS_LACK);
		params.put("oaNullValue", TransferConstants.NULL_VALUE);
		params.put("goodsStatusSurplusCarry", TransferConstants.GOODS_STATUS_SURPLUS_CARRY);
		params.put("goodsStatusSurplusCarryOthers", TransferConstants.GOODS_STATUS_SURPLUS_CARRY_OTHERS);
		
		return this.getSqlSession().selectList("foss.tfr.StDifferDetailDao.queryStOAErrorByBatch", params);
	}

	/** 
	 * 批量更新清仓差异明细的非空项字段
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:40:04
	 * @param stDifferDetailListForBatch
	 * @return void
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStDifferDetailDao#updateBatchByPrimaryKeySelective(java.util.List)
	 */
	@Override
	public void updateBatchByPrimaryKeySelective(List<StDifferDetailEntity> stDifferDetailListForBatch) {
		if(CollectionUtils.isNotEmpty(stDifferDetailListForBatch)){
			this.getSqlSession().update("foss.tfr.StDifferDetailDao.updateBatchByPrimaryKeySelective", stDifferDetailListForBatch);
		}
	}

	/** 
	 * 通过清仓差异明细获取清仓差异实体信息
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:40:21
	 * @param id
	 * @return StDifferDetailEntity
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStDifferDetailDao#queryStDifferDetailEntityById(java.lang.String)
	 */
	@Override
	public StDifferDetailEntity queryStDifferDetailEntityById(String id) {

		return (StDifferDetailEntity) this.getSqlSession().selectOne("foss.tfr.StDifferDetailDao.queryStDifferDetailEntityById", id);
	}

	/** 
	 * 通过运单号、流水号、部门编号获取清仓差异明细列表
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:40:35
	 * @param waybillNo
	 * @param serialNo
	 * @param deptCode
	 * @return List<StDifferDetailEntity>
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStDifferDetailDao#queryStDifferDetailEntityList(java.lang.String, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StDifferDetailEntity> queryStDifferDetailEntityList(String waybillNo, String serialNo, String deptCode) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("waybillNo", waybillNo);
		params.put("serialNo", serialNo);
		params.put("deptCode", deptCode);
		
		return this.getSqlSession().selectList("foss.tfr.StDifferDetailDao.queryStDifferDetailEntityList", params);
	}
	/**
	 *  添加清仓差异报告
	 * @author foss-wuyingjie
	 * @date 2012-12-25 
	 * @param entity
	 * @return 
	 */
	@Override
	public void addStDifferEntity(StDifferDetailEntity entity) {
		entity.setId(UUIDUtils.getUUID());
		this.getSqlSession().insert("foss.tfr.StDifferDetailDao.addStDifferEntity", entity);
	}

	/**
	 * @author foss-wuyingjie
	 * @date 2012-12-25 
	 * @function 根据差异报告ID查询差异报告明细
	 * @param stReportId
	 * @param differenceType
	 * @param handleStatus
	 * @return List<StDifferDetailEntity>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StDifferDetailEntity> queryStReportDetail(String stReportId, String differenceType, String handleStatus) {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("stReportId", stReportId);
		params.put("differenceType", differenceType);
		params.put("handleStatus", handleStatus);
		
		return this.getSqlSession().selectList("foss.tfr.StDifferDetailDao.queryStReportDetail", params);
	}

	/**
	 * @author foss-wuyingjie
	 * @date 2012-12-25 
	 * @function 根据运单号查询差异报告明细
	 * @param waybillNo
	 * @param differenceType
	 * @param handleStatus
	 * @return List<StDifferDetailEntity>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StDifferDetailEntity> queryStDifferDetailListByWaybillNo(String waybillNo, String differenceType, String handleStatus) {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("waybillNo", waybillNo);
		params.put("differenceType", differenceType);
		params.put("handleStatus", handleStatus);
		
		return this.getSqlSession().selectList("foss.tfr.StDifferDetailDao.queryStDifferDetailListByWaybillNo", params);
	}
	/**
	 * @author foss-wuyingjie
	 * @date 2012-12-25 
	 * @function 根据id分页查询差异报告明细
	 * @param stReportId
	 * @param start
	 * @param limit
	 * @return List<StReportDetailDto>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StReportDetailDto> queryStReportDetailDtoListsById(String stReportId, int start, int limit) {
		
		return this.getSqlSession().selectList("foss.tfr.StDifferDetailDao.queryStReportDetailDtoListsById", stReportId, new RowBounds(start, limit));
	}

	/**
	 * @author niuly
	 * @function 根据差异报告id、运单号、流水号查询两天内的关联差异报告
	 * @param stReportId
	 * @param limitedDay
	 * @param goodsStatusLack
	 * @param goodsStatusSurplusErrorGoodsarea
	 * @param waybillNo
	 * @param serialNosList
	 * @return List<StReportDetailDto>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StReportDetailDto> queryReportRelativeDetail(String stReportId,
			Date limitedDay, String goodsStatusLack,
			String goodsStatusSurplusErrorGoodsarea, String waybillNo,List<String> serialNosList) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stReportId", stReportId);
		map.put("limitedDay", limitedDay);
		map.put("scanResultLack", goodsStatusLack);
		map.put("scanResultSurplusErrorGoodsarea", goodsStatusSurplusErrorGoodsarea);
		map.put("waybillNo", waybillNo);
		map.put("serialNosList", serialNosList);
		
		return this.getSqlSession().selectList("foss.tfr.StDifferDetailDao.queryReportRelativeDetail", map);
	}
	/**
	 * @author niuly
	 * @function 查询一个差异报告中一个单doing状态的少货件数
	 * @param differReportId
	 * @param waybillNo
	 * @return int
	 */
	@Override
	public int queryCountLastLackOfWaybill(String differReportId, String waybillNo) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("differReportId", differReportId);
		map.put("waybillNo", waybillNo);
		return (Integer) this.getSqlSession().selectOne("foss.tfr.StDifferDetailDao.queryCountLastLackOfWaybill", map);
	}
	/**
	 * @author foss-wuyingjie
	 * @date 2012-12-25 
	 * @function 查询未上报OA少货且状态为DOING的任务明细
	 * @param beginTime
	 * @param scheduledFireTime
	 * @param threadNo
	 * @param threadCount
	 * @return List<StDifferDetailEntity>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StDifferDetailEntity> queryDetailForGapRepairList(Date beginTime, Date scheduledFireTime, int threadNo, int threadCount) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("threadNo", threadNo);
		map.put("threadCount", threadCount - 1);
		map.put("handleStatus", TransferConstants.STOCK_CHECKING_REPORT_DOING);
		map.put("diffType", TransferConstants.GOODS_STATUS_LACK);
		map.put("oaErrorNo", TransferConstants.NULL_VALUE);
		map.put("beginTime", beginTime);
		map.put("endTime", scheduledFireTime);
		
		return this.getSqlSession().selectList("foss.tfr.StDifferDetailDao.queryDetailForGapRepairList", map);
	}

	/**
	 * @author foss-wuyingjie
	 * @date 2012-12-25 
	 * @function 查询任务明细
	 * @param entity
	 * @return List<StDifferDetailEntity>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StDifferDetailEntity> queryDifferDetailListForGapRepair(StDifferDetailEntity entity) {
		return this.getSqlSession().selectList("foss.tfr.StDifferDetailDao.queryDifferDetailListForGapRepair", entity);
	}
	/**
	 * @author niuly
	 * @date 2013-7-24 15:19:25
	 * @param stDifferReportId
	 * @param waybillNo
	 * @function 根据差异报告id和运单号查询差异明细信息
	 * @return List<StDifferDetailEntity>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StDifferDetailEntity> queryStDifferDetailsByStIdAndWaybillNo(String stDifferReportId, String waybillNo) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("stDifferReportId", stDifferReportId);
		params.put("waybillNo", waybillNo);
		
		return this.getSqlSession().selectList("foss.tfr.StDifferDetailDao.queryStDifferDetailsByStIdAndWaybillNo", params);
	}
	/**
	 * @author niuly
	 * @date 2013-07-26 15:08:25
	 * @function 根据单号、差异报告id查询差异报告明细
	 * @param stReportId
	 * @param waybillNo
	 * @return List<StReportDetailDto>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StReportDetailDto> queryReportDetailByWaybillNo(String stReportId, String waybillNo) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("stReportId", stReportId);
		params.put("waybillNo", waybillNo);
		return this.getSqlSession().selectList("foss.tfr.StDifferDetailDao.queryReportDetailByWaybillNo", params);
	}
	/**
	 * @author niuly
	 * @date 2014-6-17上午11:21:45
	 * @function 根据运单号查询少货且成功上报OA差错的数量
	 * @param waybillNo
	 * @return
	 */
	@Override
	public Long queryLackCountByWaybillNo(String waybillNo) {
		return (Long)this.getSqlSession().selectOne("foss.tfr.StDifferDetailDao.queryLackCountByWaybillNo", waybillNo);
	}
}