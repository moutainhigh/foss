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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/server/dao/impl/StDifferReportDao.java
 *  
 *  FILE NAME          :StDifferReportDao.java
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStDifferReportDao;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StDifferReportEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StReportDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.vo.StReportVO;
import com.deppon.foss.util.DateUtils;

/**
 * 处理清仓差异报告数据
 * @author foss-wuyingjie
 * @date 2012-12-25 上午10:41:43
 */
public class StDifferReportDao extends iBatis3DaoImpl implements IStDifferReportDao{

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:41:50
	 * @function 查询差异报告列表
	 * @param  stReportDto
	 * @param start
	 * @param limit
	 * @return List<StReportDto>
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStDifferReportDao#queryStReportDtoList(com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StReportDto, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StReportDto> queryStReportDtoList(StReportDto stReportDto, int start, int limit) {

		return this.getSqlSession().selectList("foss.tfr.StDifferReportDao.queryStReportDtoList", stReportDto, new RowBounds(start, limit));
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:42:02
	 * @function 查询差异报告总数
	 * @param stReportDto
	 * @return long
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStDifferReportDao#queryStReportDtoListCount(com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StReportDto)
	 */
	@Override
	public Long queryStReportDtoListCount(StReportDto stReportDto) {

		return (Long) this.getSqlSession().selectOne("foss.tfr.StDifferReportDao.queryStReportDtoListCount", stReportDto);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:42:07
	 * @function 更新差异报告
	 * @param stDifferReportEntity
	 * @return
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStDifferReportDao#updateByPrimaryKeySelective(com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StDifferReportEntity)
	 */
	@Override
	public void updateByPrimaryKeySelective(StDifferReportEntity stDifferReportEntity) {
		this.getSqlSession().update("foss.tfr.StDifferReportDao.updateByPrimaryKeySelective", stDifferReportEntity);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:42:14
	 * @function 批量新增差异报告
	 * @param stDifferReportListForBatch
	 * @return
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStDifferReportDao#addBatchStDifferReport(java.util.List)
	 */
	@Override
	public void addBatchStDifferReport(List<StDifferReportEntity> stDifferReportListForBatch) {
		if(CollectionUtils.isNotEmpty(stDifferReportListForBatch)){
			this.getSqlSession().insert("foss.tfr.StDifferReportDao.addBatchStDifferReport", stDifferReportListForBatch);
		}
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:42:19
	 * @function 根据id查询差异报告
	 * @param id
	 * @return StDifferReportEntity
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStDifferReportDao#queryStReportEntityById(java.lang.String)
	 */
	@Override
	public StDifferReportEntity queryStReportEntityById(String id) {

		return (StDifferReportEntity) this.getSqlSession().selectOne("foss.tfr.StDifferReportDao.queryStReportEntityById", id);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:42:24
	 * @function 查询未处理的差异报告
	 * @param reportIdSet
	 * @return List<StDifferReportEntity>
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStDifferReportDao#queryStReportForDone(java.util.Set)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StDifferReportEntity> queryStReportForDone(Set<String> reportIdSet) {
		List<String> reportIdList = new ArrayList<String>();
		for(String id: reportIdSet){
			reportIdList.add(id);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("reportIdList", reportIdList);
		params.put("haventDone", TransferConstants.STOCK_CHECKING_REPORT_DOING);
		if(CollectionUtils.isEmpty(reportIdSet)){
			return new ArrayList<StDifferReportEntity>();
		}else{
			return this.getSqlSession().selectList("foss.tfr.StDifferReportDao.queryStReportForDone", params);
		}
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:42:28
	 * @function 批量更新差异报告
	 * @param reportList
	 * @return void 
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStDifferReportDao#updateBatchByPrimaryKeySelective(java.util.List)
	 */
	@Override
	public void updateBatchByPrimaryKeySelective(List<StDifferReportEntity> reportList) {
		if(CollectionUtils.isNotEmpty(reportList)){
			this.getSqlSession().update("foss.tfr.StDifferReportDao.updateBatchByPrimaryKeySelective", reportList);
		}
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:42:28
	 * @function 批量更新差异报告
	 * @param reportList
	 * @return void 
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStDifferReportDao#updateBatchByPrimaryKeySelective(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StDifferReportEntity> queryStReportForOAError(Date beginReportOATime,Date bizJobEndTime, int threadNo, int threadCount, int oaReportHourRule) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("beginReportOATime", beginReportOATime);
		params.put("oaReportHourRule", oaReportHourRule);
		Date bizJobStartTime = DateUtils.addDayToDate(Calendar.getInstance().getTime(), -TfrJobBusinessTypeEnum.ST_OA_ERROR.getDefaultBizDaySlice()); 
		params.put("bizJobStartTime", bizJobStartTime);
		params.put("bizJobEndTime", bizJobEndTime);
		params.put("goodsStatusLack", TransferConstants.GOODS_STATUS_LACK);
		params.put("oaNullValue", TransferConstants.NULL_VALUE);
		params.put("goodsStatusSurplusCarry", TransferConstants.GOODS_STATUS_SURPLUS_CARRY);
		params.put("goodsStatusSurplusCarryOthers", TransferConstants.GOODS_STATUS_SURPLUS_CARRY_OTHERS);
		params.put("threadNo", threadNo);
		params.put("threadCount", threadCount - 1);
		
		return this.getSqlSession().selectList("foss.tfr.StDifferReportDao.queryStReportForOAError", params);
	}

	@Override
	public void addStDifferReport(StDifferReportEntity stDiffer) {
		this.getSqlSession().insert("foss.tfr.StDifferReportDao.addStDifferReport", stDiffer);
	}

	@Override
	public int queryStReportCountByTaskId(String taskId) {
		return (Integer) this.getSqlSession().selectOne("foss.tfr.StDifferReportDao.queryStReportCountByTaskId", taskId);
	}
	/**
	 * 需上报OA少货差异报告
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStDifferReportDao#queryLackStReportForOAError(java.util.Date, java.util.Date, int, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StDifferReportEntity> queryLackStReportForOAError(
			Date beginReportOATime, Date bizJobEndTime, int threadNo,
			int threadCount, int oaReportHourRule, int bundleNum) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("beginReportOATime", beginReportOATime);
		params.put("oaReportHourRule", oaReportHourRule);
		Date bizJobStartTime = DateUtils.addDayToDate(Calendar.getInstance().getTime(), -TfrJobBusinessTypeEnum.ST_OA_ERROR.getDefaultBizDaySlice()); 
		params.put("bizJobStartTime", bizJobStartTime);
		params.put("bizJobEndTime", bizJobEndTime);
		params.put("goodsStatusLack", TransferConstants.GOODS_STATUS_LACK);
		params.put("oaNullValue", TransferConstants.NULL_VALUE);
		params.put("threadNo", threadNo);
		params.put("threadCount", threadCount - 1);
		params.put("bundleNum", bundleNum);
		params.put("detailStatus", TransferConstants.STOCK_CHECKING_REPORT_DOING);
		
		return this.getSqlSession().selectList("foss.tfr.StDifferReportDao.queryLackStReportForOAError", params);
	}
    /**
     * 需上报OA多货差异报告
     * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStDifferReportDao#queryMoreStReportForOAError(java.util.Date, java.util.Date, int, int, int)
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<StDifferReportEntity> queryMoreStReportForOAError(
			Date beginReportOATime, Date bizJobEndTime, int threadNo,
			int threadCount) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("beginReportOATime", beginReportOATime);
		Date bizJobStartTime = DateUtils.addDayToDate(Calendar.getInstance().getTime(), -TfrJobBusinessTypeEnum.ST_OA_ERROR.getDefaultBizDaySlice()); 
		params.put("bizJobStartTime", bizJobStartTime);
		params.put("bizJobEndTime", bizJobEndTime);
		params.put("oaNullValue", TransferConstants.NULL_VALUE);
		params.put("goodsStatusSurplusCarry", TransferConstants.GOODS_STATUS_SURPLUS_CARRY);
		params.put("goodsStatusSurplusCarryOthers", TransferConstants.GOODS_STATUS_SURPLUS_CARRY_OTHERS);
		params.put("threadNo", threadNo);
		params.put("threadCount", threadCount - 1);
		
		return this.getSqlSession().selectList("foss.tfr.StDifferReportDao.queryMoreStReportForOAError", params);
	}

	@Override
	public Long getStReportDetailDtoCount(String stReportId) {
		return (Long) this.getSqlSession().selectOne("foss.tfr.StDifferReportDao.queryStReportDetailDtoCount", stReportId);
	}
	/**
	 * @author niuly
	 * @date 2013-08-05 14:19:48
	 * @function 查询扫描时间
	 * @param taskId
	 * @param waybillNo
	 * @param serialNo
	 * @return
	 */
	@Override
	public Date queryOperateTime(String reportId, String waybillNo,	String serialNo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("reportId", reportId);
		params.put("waybillNo", waybillNo);
		params.put("serialNo", serialNo);
		return (Date)this.getSqlSession().selectOne("foss.tfr.StDifferReportDao.queryOperateTime", params);
	}
	/**
	 * 
	 * @author 097457-foss-zenghaibin
	 * @date 2014-10-20 下午2:39:38
	 * @param
	 * @return String
	 */
	public StReportVO goodsCountInfo(String orgCode){
		
		return (StReportVO)this.getSqlSession().selectList("foss.tfr.StDifferReportDao.goodsCountInfo",orgCode).get(0);
	}
	
}