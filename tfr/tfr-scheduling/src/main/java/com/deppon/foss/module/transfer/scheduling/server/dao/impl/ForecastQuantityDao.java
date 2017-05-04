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
 *  
 *  PROJECT NAME  : tfr-scheduling
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/dao/impl/ForecastQuantityDao.java
 * 
 *  FILE NAME     :ForecastQuantityDao.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.scheduling.server.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IForecastQuantityDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ForecastQuantityEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.StatisticalInquiriesEntity;


/**
 * 货量预测总表dao实现
 * @author huyue
 * @date 2012-10-11 下午9:13:22
 */
public class ForecastQuantityDao extends iBatis3DaoImpl implements IForecastQuantityDao{

	private static final String NAMESPACE = "Foss.forecastQuantity.";
	
	/** 
	 * 分页查询货量预测总表信息 返回list
	 * @author huyue
	 * @date 2012-11-22 上午10:53:05
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.IForecastQuantityDao#queryByPage(com.deppon.foss.module.transfer.scheduling.api.shared.domain.ForecastQuantityEntity, int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<ForecastQuantityEntity> queryByPage(ForecastQuantityEntity forecastQuantityEntity, int limit,
			int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAMESPACE + "queryByPage", forecastQuantityEntity, rowBounds);
	}
	
	/**
	 * 查询货量预测总表信息 根据最后更新时间
	 * @author huyue
	 * @date 2012-12-10 上午11:23:22
	 */
	@SuppressWarnings("unchecked")
	public List<ForecastQuantityEntity> queryByStatistics(ForecastQuantityEntity forecastQuantityEntity) {
		return this.getSqlSession().selectList(NAMESPACE + "queryByPage", forecastQuantityEntity);
	}
	
	/**
	 * 分页getCount
	 * @author huyue
	 * @date 2012-11-22 下午5:22:21
	 */
	public Long getCount(ForecastQuantityEntity forecastQuantityEntity) {
		
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "getCount", forecastQuantityEntity);
		
	}
	
	/**
	 * 查询外场货量预测货区的LIST
	 * @author huyue
	 * @date 2012-11-30 下午5:24:19
	 */
	@SuppressWarnings("unchecked")
	public List<ForecastQuantityEntity> queryByRegionList(ForecastQuantityEntity forecastQuantityEntity) {
		return getSqlSession().selectList(NAMESPACE + "queryByRegionList", forecastQuantityEntity);
	}

	/**
	 * 查询某外场最新一批货量预测的预测时间
	 * @author huyue
	 * @date 2012-12-14 下午9:47:36
	 */
	public Date selectMaxStatisticsTime(ForecastQuantityEntity forecastQuantityEntity) {
		return (Date) getSqlSession().selectOne(NAMESPACE + "selectMaxStatisticsTime", forecastQuantityEntity);
	}
	
	/**
	 * 查询某外场最新一批货量预测的预测天数LIST
	 * @author huyue
	 * @date 2012-11-30 下午2:39:55
	 */
	@SuppressWarnings("unchecked")
	public List<Date> queryForecastTimeList(ForecastQuantityEntity forecastQuantityEntity) {
		return getSqlSession().selectList(NAMESPACE + "selectTimeList", forecastQuantityEntity);
	}
	
	/**
	 * 根据不同参数查询货量预测总表信息 返回list
	 * @author huyue
	 * @date 2012-11-22 下午4:35:30
	 */
	@SuppressWarnings("unchecked")
	public List<ForecastQuantityEntity> queryForecastQuantityList(ForecastQuantityEntity forecastQuantityEntity, int limit,
			int start) {
		return getSqlSession().selectList(NAMESPACE + "select", forecastQuantityEntity);
	}
	
	/**
	 * 根据周期和其他参数查询总表信息 返回list
	 * @author huyue
	 * @date 2012-12-14 下午8:51:18
	 */
	@SuppressWarnings("unchecked")
	public List<ForecastQuantityEntity> selectByPeriod(ForecastQuantityEntity forecastQuantityEntity) {
		return getSqlSession().selectList(NAMESPACE + "selectByPeriod", forecastQuantityEntity);
	}
	
	/**
	 * 新增货量预测总表信息
	 * @author huyue
	 * @date 2012-11-14 下午8:31:57
	 */
	public int addforecastQuantity(ForecastQuantityEntity forecastQuantityEntity) {
		String statement = NAMESPACE + "insert";
		return getSqlSession().insert(statement, forecastQuantityEntity);
	}
	
	/**
	 * 新增货量预测总表信息 选择插入
	 * @author huyue
	 * @date 2012-11-14 下午8:33:09
	 */
	public int addforecastQuantitySelective(ForecastQuantityEntity forecastQuantityEntity) {
		String statement = NAMESPACE + "insertSelective";
		return getSqlSession().insert(statement, forecastQuantityEntity);
	}
	
	/**
	 * 更新货量预测总表信息
	 * @author huyue
	 * @date 2012-11-14 下午8:34:46
	 */
	public int updateforecastQuantity(ForecastQuantityEntity forecastQuantityEntity) {
		String statement = NAMESPACE + "updateByPrimaryKey";
		return getSqlSession().insert(statement, forecastQuantityEntity);
	}
	
	/**
	 * 更新货量预测总表信息 选择插入
	 * @author huyue
	 * @date 2012-11-14 下午8:34:09
	 */
	public int updateforecastQuantitySelective(ForecastQuantityEntity forecastQuantityEntity) {
		String statement = NAMESPACE + "updateByPrimaryKeySelective";
		return getSqlSession().insert(statement, forecastQuantityEntity);
	}
	
	/**
	 * 查询货量预测总表信息
	 * @author huyue
	 * @date 2012-11-14 下午8:44:32
	 */
	public ForecastQuantityEntity queryforecastQuantity(ForecastQuantityEntity forecastQuantityEntity) {
		String statement = NAMESPACE + "select";
		return (ForecastQuantityEntity) getSqlSession().selectOne(statement, forecastQuantityEntity);
	}
	
	/**
	 * 查询货量预测总表信息 批量
	 * @author huyue
	 * @date 2012-11-14 下午8:50:30
	 */
	@SuppressWarnings("unchecked")
	public List<ForecastQuantityEntity> queryforecastQuantityList(ForecastQuantityEntity forecastQuantityEntity) {
		String statement = NAMESPACE + "select";
		return getSqlSession().selectList(statement, forecastQuantityEntity);
	}

	/**
	 * 根据某一库存状态 查询下一个库存
	 * @author yuyongxiang
	 * @date 2013年6月25日 15:54:36
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryStockWhitInStock(Map<String, Object> map) {
		String statement = NAMESPACE + "queryStockWhitInStock";
		return getSqlSession().selectList(statement, map);
	}

	/**
	 * 更具运单号以及运输性质查询运单详细信息
	 * 
	 * @author yuyongxiang
	 * @date 2013年7月8日 20:57:17
	 *  (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.IForecastQuantityDao#queryStatisticalInquiriesWithWayBillNo(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StatisticalInquiriesEntity> queryStatisticalInquiriesWithWayBillNo(
			Map<String, Object> map) {
		String statement = NAMESPACE + "queryStatisticalInquiriesWithWayBillNo";
		RowBounds rowBounds = new RowBounds(Integer.valueOf(map.get("start").toString()), Integer.valueOf(map.get("limit").toString()));
		return getSqlSession().selectList(statement, map,rowBounds);
	}
	/**
	 * 更具运单号以及运输性质查询运单详细信息 count
	 * 
	 * @author yuyongxiang
	 * @date 2013年7月8日 20:57:17
	 *  (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.IForecastQuantityDao#queryStatisticalInquiriesWithWayBillNoCount(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryStatisticalInquiriesWithWayBillNoCount(
			Map<String, Object> map) {
		String statement = NAMESPACE + "queryStatisticalInquiriesWithWayBillNoCount";
		return (Map<String,Object>) getSqlSession().selectOne(statement, map);
	}
	
	/**
	 * 根据交接单号的状态 查出对应的交接单号里面的运单和(实际交接的重量和体积)
	 * @author yuyongxiang
	 * @date 2013年7月9日 15:28:53
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StatisticalInquiriesEntity> queryStatisticalInquiriesWithHandover(Map<String, Object> map) {
		String statement = NAMESPACE + "queryStatisticalInquiriesWithHandover";
		RowBounds rowBounds = new RowBounds(Integer.valueOf(map.get("start").toString()), Integer.valueOf(map.get("limit").toString()));
		return getSqlSession().selectList(statement, map,rowBounds);
	}
	/**
	 * 根据交接单号的状态 查出对应的交接单号里面的运单和(实际交接的重量和体积)
	 * @author yuyongxiang
	 * @date 2013年7月9日 15:28:53
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> queryStatisticalInquiriesWithHandoverCount(Map<String, Object> map) {
		String statement = NAMESPACE + "queryStatisticalInquiriesWithHandoverCount";
		return (Map<String,Object>) getSqlSession().selectOne(statement, map);
	}
	
	/**
	 * 根据当前部门和货物到达部门查询在库的运单
	 * @author yuyongxiang
	 * @date 2013年7月9日 15:28:53
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StatisticalInquiriesEntity> queryStatisticalInquiriesWithInLibrary(Map<String, Object> map) {
		String statement = NAMESPACE + "queryStatisticalInquiriesWithInLibrary";
		RowBounds rowBounds = new RowBounds(Integer.valueOf(map.get("start").toString()), Integer.valueOf(map.get("limit").toString()));
		return getSqlSession().selectList(statement, map,rowBounds);
	}
	/**
	 * 根据当前部门和货物到达部门查询在库的运单
	 * @author yuyongxiang
	 * @date 2013年7月9日 15:28:53
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> queryStatisticalInquiriesWithInLibraryCount(Map<String, Object> map) {
		String statement = NAMESPACE + "queryStatisticalInquiriesWithInLibraryCount";
		return (Map<String,Object>) getSqlSession().selectOne(statement, map);
	}
	
	/**
	 * 到达未卸车
	 * @author yuyongxiang
	 * @date 2013年7月9日 15:28:53
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StatisticalInquiriesEntity> queryStatisticalInquiriesWithUnloadingNotReach(Map<String, Object> map) {
		String statement = NAMESPACE + "queryStatisticalInquiriesWithUnloadingNotReach";
		RowBounds rowBounds = new RowBounds(Integer.valueOf(map.get("start").toString()), Integer.valueOf(map.get("limit").toString()));
		return getSqlSession().selectList(statement, map,rowBounds);
	}
	/**
	 * 到达未卸车
	 * @author yuyongxiang
	 * @date 2013年7月9日 15:28:53
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> queryStatisticalInquiriesWithUnloadingNotReachCount(Map<String, Object> map) {
		String statement = NAMESPACE + "queryStatisticalInquiriesWithUnloadingNotReachCount";
		return (Map<String,Object>) getSqlSession().selectOne(statement, map);
	}
}