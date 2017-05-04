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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/server/dao/impl/StTaskDao.java
 *  
 *  FILE NAME          :StTaskDao.java
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskDao;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.GoodsStockDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StTaskDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StTaskExportDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StWaybillInfoDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.vo.StockcheckingVO;
import com.deppon.foss.util.UUIDUtils;

/**
 * 处理清仓任务实体相关的Crud
 * @author foss-wuyingjie
 * @date 2012-10-16 下午4:56:09
 */
public class StTaskDao extends iBatis3DaoImpl implements IStTaskDao {

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:45:02
	 * @function 分页查询任务列表
	 * @param stTaskDto
	 * @param start
	 * @param limit
	 * @return List<StTaskDto>                                                              
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskDao#queryStTaskDtoList(com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StTaskDto, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StTaskDto> queryStTaskDtoList(StTaskDto stTaskDto, int start, int limit) {
		
		return this.getSqlSession().selectList("foss.tfr.StTaskDao.queryStTaskDtoList", stTaskDto, new RowBounds(start, limit));
	}
	
	/** 
	 * @author foss-wuyingjie
	 * @date 2013-1-22 下午3:46:59
	 * @function 分页查询外场任务列表
	 * @param stTaskDto
	 * @param start
	 * @param limit
	 * @return List<StTaskDto>
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskDao#queryTransferCenterStTaskDtoList(com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StTaskDto, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StTaskDto> queryTransferCenterStTaskDtoList(StTaskDto stTaskDto, int start, int limit) {

		return this.getSqlSession().selectList("foss.tfr.StTaskDao.queryTransferCenterStTaskDtoList", stTaskDto, new RowBounds(start, limit));
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:45:06
	 * @function 查询任务总数
	 * @param stTaskDto
	 * @return long
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskDao#queryStTaskDtoListCount(com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StTaskDto)
	 */
	@Override
	public Long queryStTaskDtoListCount(StTaskDto stTaskDto) {
		
		return (Long) this.getSqlSession().selectOne("foss.tfr.StTaskDao.queryStTaskDtoListCount", stTaskDto);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2013-1-22 下午3:47:14
	 * @function 查询外场任务总数
	 * @param stTaskDto
	 * @return long
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskDao#queryTransferCenterStTaskDtoListCount(com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StTaskDto)
	 */
	@Override
	public Long queryTransferCenterStTaskDtoListCount(StTaskDto stTaskDto) {

		return (Long) this.getSqlSession().selectOne("foss.tfr.StTaskDao.queryTransferCenterStTaskDtoListCount", stTaskDto);
	}
	
	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:45:11
	 * @function 营业部建立清仓任务时查询库存信息
	 * @param goodsStockDto
	 * @param beforeTime
	 * @return List<GoodsStockDto>
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskDao#queryGoodsStockDtoList(com.deppon.foss.module.transfer.stockchecking.api.shared.dto.GoodsStockDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsStockDto> queryGoodsStockDtoList(GoodsStockDto goodsStockDto,int beforeTime) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("goodsStockDto", goodsStockDto);
		map.put("beforeTime", beforeTime);
		return this.getSqlSession().selectList("foss.tfr.StTaskDao.queryGoodsStockDtoList", map);
	}
	
	/** 
	 * @author 268084
	 * @date 
	 * @function 营业部建立清仓任务时查询库存信息(快递)
	 * @param goodsStockDto
	 * @param beforeTime
	 * @return List<GoodsStockDto>
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskDao#queryGoodsStockDtoList(com.deppon.foss.module.transfer.stockchecking.api.shared.dto.GoodsStockDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsStockDto> queryExpressGoodsStockDtoList(GoodsStockDto goodsStockDto,int beforeTime) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("goodsStockDto", goodsStockDto);
		map.put("beforeTime", beforeTime);
		return this.getSqlSession().selectList("foss.tfr.StTaskDao.queryExpressGoodsStockDtoList", map);
	}

	/** 
	 * @author 268084
	 * @date 
	 * @function 营业部建立清仓任务时查询库存信息(零担)
	 * @param goodsStockDto
	 * @param beforeTime
	 * @return List<GoodsStockDto>
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskDao#queryGoodsStockDtoList(com.deppon.foss.module.transfer.stockchecking.api.shared.dto.GoodsStockDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsStockDto> queryNoExpressGoodsStockDtoList(GoodsStockDto goodsStockDto,int beforeTime) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("goodsStockDto", goodsStockDto);
		map.put("beforeTime", beforeTime);
		return this.getSqlSession().selectList("foss.tfr.StTaskDao.queryNoExpressGoodsStockDtoList", map);
	}
	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:45:16
	 * @function 新增任务
	 * @param stTaskEntity
	 * @return
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskDao#addStTaskEntity(com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskEntity)
	 */
	@Override
	public void addStTaskEntity(StTaskEntity stTaskEntity) {
		stTaskEntity.setId(UUIDUtils.getUUID());
		
		this.getSqlSession().insert("foss.tfr.StTaskDao.addStTaskEntity", stTaskEntity);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:45:23
	 * @function 查询该部门货区的任务
	 * @param goodsAreaCode
	 * @param deptNo
	 * @return List<StTaskEntity>
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskDao#queryStTaskStatus(java.util.Date, java.lang.String, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StTaskEntity> queryStTaskStatus(String goodsAreaCode, String deptNo) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("goodsAreaCode", goodsAreaCode);
		paramsMap.put("deptNo", deptNo);
		
		return this.getSqlSession().selectList("foss.tfr.StTaskDao.queryStTaskStatus", paramsMap);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:45:33
	 * @function 更新任务状态
	 * @param stTaskEntity
	 * @return 
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskDao#updateStTaskStatus(com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskEntity)
	 */
	@Override
	public void updateStTaskStatus(StTaskEntity stTaskEntity) {
		this.getSqlSession().update("foss.tfr.StTaskDao.updateStTaskStatus", stTaskEntity);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:45:38
	 * @function 查询任务
	 * @param stTaskId
	 * @return StTaskEntity
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskDao#queryStTaskById(java.lang.String)
	 */
	@Override
	public StTaskEntity queryStTaskById(String stTaskId) {
		
		return (StTaskEntity) this.getSqlSession().selectOne("foss.tfr.StTaskDao.queryStTaskById", stTaskId);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:45:42
	 * @function 查询任务内运单信息
	 * @param stTaskId
	 * @return List<StWaybillInfoDto>
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskDao#queryStWaybillInfoDtoByStTaskId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StWaybillInfoDto> queryStWaybillInfoDtoByStTaskId(String stTaskId) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("stTaskId", stTaskId);
		
		return this.getSqlSession().selectList("foss.tfr.StTaskDao.queryStWaybillInfoDtoByStTaskId", paramsMap);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:45:47
	 * @function 导出任务时查询任务相关数据
	 * @param stTaskId
	 * @return List<StTaskExportDto>
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskDao#queryStTaskExportInfoById(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StTaskExportDto> queryStTaskExportInfoById(String stTaskId) {
		
		return this.getSqlSession().selectList("foss.tfr.StTaskDao.queryStTaskExportInfoById", stTaskId);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:45:53
	 * @function 更新任务
	 * @param stTaskEntity
	 * @return
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskDao#updateStTaskEntity(com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskEntity)
	 */
	@Override
	public void updateStTaskEntity(StTaskEntity stTaskEntity) {
		this.getSqlSession().update("foss.tfr.StTaskDao.updateStTaskEntity", stTaskEntity);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:45:59
	 * @function 批量查询任务
	 * @param bizJobStartTime
	 * @param bizJobEndTime
	 * @param threadNo
	 * @param threadCount
	 * @return List<StTaskEntity>
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskDao#queryStTaskByBatch(java.util.Date, java.util.Date, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StTaskEntity> queryStTaskByBatch(Date bizJobStartTime,	Date bizJobEndTime, int threadNo, int threadCount) {
		Map<String, Object> map = new HashMap<String, Object>();
		//BUG-53701 因时间差问题，查询开始时间提前15分钟
		map.put("bizJobStartTime", new Date(bizJobStartTime.getTime() - 1000l * ConstantsNumberSonar.SONAR_NUMBER_60 * ConstantsNumberSonar.SONAR_NUMBER_15));
		map.put("bizJobEndTime", bizJobEndTime);
		map.put("threadNo", threadNo);
		map.put("threadCount", threadCount - 1);
		map.put("stTaskStatus", TransferConstants.STOCK_CHECKING_DONE);
		
		List<StTaskEntity> list = this.getSqlSession().selectList("foss.tfr.StTaskDao.queryStTaskByBatch", map);
		
		if(CollectionUtils.isEmpty(list)){
			return new ArrayList<StTaskEntity>();
		}else{
			return list;
		}
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:46:05
	 * @function 根据任务号查询任务
	 * @param stTaskNo
	 * @return stTaskEntity
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskDao#queryStTaskByNo(java.lang.String)
	 */
	@Override
	public StTaskEntity queryStTaskByNo(String stTaskNo) {
		StTaskEntity stTaskEntity = (StTaskEntity) this.getSqlSession().selectOne("foss.tfr.StTaskDao.queryStTaskByNo", stTaskNo);
		if(null == stTaskEntity){
			return new StTaskEntity();
		}else{
			return stTaskEntity;
		}
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:46:11
	 * @functon 更新任务
	 * @param stTaskEntity
	 * @return
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskDao#updateByPrimaryKeySelective(com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskEntity)
	 */
	@Override
	public void updateByPrimaryKeySelective(StTaskEntity stTaskEntity) {
		this.getSqlSession().update("foss.tfr.StTaskDao.updateByPrimaryKeySelective", stTaskEntity);
	}
	
	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:46:11
	 * @functon 查询外场任务列表
	 * @param goodsStockDto
	 * @param beforeTime
	 * @return List<GoodsStockDto>
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskDao#updateByPrimaryKeySelective(com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsStockDto> queryTransferCenterGoodsStockDtoList(GoodsStockDto goodsStockDto,int beforeTime) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("goodsStockDto", goodsStockDto);
		map.put("beforeTime", beforeTime);
		return this.getSqlSession().selectList("foss.tfr.StTaskDao.queryTransferCenterGoodsStockDtoList", map);
	}
	/** 
	 * @author 268084
	 * @date 
	 * @functon 查询外场任务列表(快递)
	 * @param goodsStockDto
	 * @param beforeTime
	 * @return List<GoodsStockDto>
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskDao#updateByPrimaryKeySelective(com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsStockDto> queryTransferCenterGoodsStockDtoListExpress(GoodsStockDto goodsStockDto,int beforeTime){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("goodsStockDto", goodsStockDto);
		map.put("beforeTime", beforeTime);
		return this.getSqlSession().selectList("foss.tfr.StTaskDao.queryTransferCenterGoodsStockDtoListExpress", map);
	}
	/** 
	 * @author 268084
	 * @date 
	 * @functon 查询外场任务列表(零担)
	 * @param goodsStockDto
	 * @param beforeTime
	 * @return List<GoodsStockDto>
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskDao#updateByPrimaryKeySelective(com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsStockDto> queryTransferCenterGoodsStockDtoListNoExpress(GoodsStockDto goodsStockDto,int beforeTime){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("goodsStockDto", goodsStockDto);
		map.put("beforeTime", beforeTime);
		return this.getSqlSession().selectList("foss.tfr.StTaskDao.queryTransferCenterGoodsStockDtoListNoExpress", map);
	}
	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:46:11
	 * @functon 查询外场任务列表
	 * @param goodsStockDto
	 * @param beforeTime
	 * @return List<GoodsStockDto>
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskDao#updateByPrimaryKeySelective(com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskEntity)
	 */
	@Override
	public StTaskEntity queryStTaskByReportId(String reportId) {
		StTaskEntity stTaskEntity = (StTaskEntity) this.getSqlSession().selectOne("foss.tfr.StTaskDao.queryStTaskByReportId", reportId);
		if(null == stTaskEntity){
			return new StTaskEntity();
		}else{
			return stTaskEntity;
		}
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:46:11
	 * @functon 查询清仓中的任务
	 * @param deptCode
	 * @param goodsAreaCode
	 * @return List<StTaskDto>
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskDao#updateByPrimaryKeySelective(com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StTaskDto> queryTaskInProcess(String deptCode, String goodsAreaCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deptCode", deptCode);
		map.put("goodsAreaCode", goodsAreaCode);
		map.put("status", TransferConstants.STOCK_CHECKING_DOING);
		
		List<StTaskDto> list = this.getSqlSession().selectList("foss.tfr.StTaskDao.queryTaskInProcess", map);
		
		if(CollectionUtils.isEmpty(list)){
			return new ArrayList<StTaskDto>();
		}else{
			return list;
		}
		
	}
	
	/**
	   * @author niuly
	   * @date 2013-7-17 14:40:20
	   * @function 根据件数查询库存信息
	   * @param goodsStockDto
	   * @param beforeTime
	   * @return
	   */
	  /*@SuppressWarnings("unchecked")
	  @Override
	  public List<GoodsStockDto> queryTransferStockDtoListByQty(GoodsStockDto goodsStockDto,int beforeTime) {
		  Map<String,Object> map = new HashMap<String,Object>();
		  map.put("goodsStockDto", goodsStockDto);
		  map.put("beforeTime", beforeTime);
	    return this.getSqlSession().selectList("foss.tfr.StTaskDao.queryTransferStockDtoListByQty", map);
	  }*/

	  /**
	   * @author niuly
	   * @date 2013-7-17 14:53:20
	   * @function 按件新增清仓任务
	   * @param goodsStockDto
	   * @return
	   */
	 /* @Override
	  public void addStTaskEntityByQty(StTaskEntity stTaskEntity) {
	    
	    stTaskEntity.setId(UUIDUtils.getUUID());
	    this.getSqlSession().insert("foss.tfr.StTaskDao.addStTaskEntityByQty", stTaskEntity);
	  }*/

	  /**
	   * @author niuly
	   * @date 2013-07-18 14:25:32
	   * @function 按件建立清仓任务时查询任务列表
	   * @param goodsAreaCode
	   * @param deptNo
	   * @return
	   */
	/*  @SuppressWarnings("unchecked")
	  @Override
	  public List<StTaskEntity> queryStTaskStatusByQty(String goodsAreaCode, String deptNo) {
	    Map<String, Object> paramsMap = new HashMap<String, Object>();
	    paramsMap.put("goodsAreaCode", goodsAreaCode);
	    paramsMap.put("deptNo", deptNo);
	    
	    return this.getSqlSession().selectList("foss.tfr.StTaskDao.queryStTaskStatusByQty", paramsMap);
	  }*/

	  /**
		 * @author niuly
		 * @date 2014-6-16下午4:37:14
		 * @function 更新清仓任务 
		 */
	@Override
	public int updateTask(int hours) {
		return this.getSqlSession().update("foss.tfr.StTaskDao.updateTask", hours);
	}
	/**
	 * @author zenghaibin
	 * @date 2014-9-19下午2:37:14
	 * @function 统计转运场数据
	 */
	@Override
	public StockcheckingVO statistics(String orgCode) {
		return (StockcheckingVO)this.getSqlSession().selectList("foss.tfr.StTaskDao.statistics",orgCode).get(0);
	}
	
	/**
	 *@author 205109 zenghaibin
	 *@date 2014-11-01
	 *@function 查询清仓的状态为DOING的清仓任务的部门编码
	 *@param configHours 配置时间
	 *@param deptCodeList 配置的部门编码
	 ***/
	@Override
	
	public void updateTaskByDeptCode(String configHours,List<String> deptCodeList){
		Map<String,Object> paramsMap=new HashMap<String,Object>();
		int hours=Integer.valueOf(configHours).intValue();
		paramsMap.put("hours",hours);
		paramsMap.put("deptCodeList",deptCodeList);
		 this.getSqlSession().update("foss.tfr.StTaskDao.updateTaskByDeptCode",paramsMap);
	}
	/** 
	 * @author 332153
	 * @date 
	 * @function 根据清仓任务号获取未提交包数量
	 * @param stTaskNo
	 */
	@Override
	public int getNoSubmitPackageNumByStTaskNo(String stTaskNo) {
		return (Integer) this.getSqlSession().selectOne("getNoSubmitPackageNumByStTaskNo", stTaskNo);
	}
	/** 
	 * @author 332153
	 * @date 
	 * @function 根据清仓任务号获取未提交运单数量
	 * @param stTaskNo
	 */
	@Override
	public int getNoSubmitWaybillNumByStTaskNo(String stTaskNo) {
		return (Integer) this.getSqlSession().selectOne("getNoSubmitWaybillNumByStTaskNo", stTaskNo);
	}
	/** 
	 * @author 332153
	 * @date 
	 * @function 根据清仓任务号获取未提交流水数量
	 * @param stTaskNo
	 */
	@Override
	public int getNoSubmitSerialNumByStTaskNo(String stTaskNo) {
		return (Integer) this.getSqlSession().selectOne("getNoSubmitSerialNumByStTaskNo", stTaskNo);
	}
	
}