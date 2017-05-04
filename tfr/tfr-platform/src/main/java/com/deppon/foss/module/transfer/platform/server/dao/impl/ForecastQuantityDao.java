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
package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.NetGroupSiteDto;
import com.deppon.foss.module.transfer.platform.api.server.dao.IForecastQuantityDao;
import com.deppon.foss.module.transfer.platform.api.shared.define.ForecastConstants;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ForecastQuantityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StatisticalInquiriesEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.SerialnoDto;


/**
 * 货量预测总表dao实现
 * @author huyue
 * @date 2012-10-11 下午9:13:22
 */
public class ForecastQuantityDao extends iBatis3DaoImpl implements IForecastQuantityDao{

	private static final String NAMESPACE = "Foss.platform.forecastQuantity.";
	
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
		String statement = NAMESPACE + "queryforecastQuantityList";
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
	
	/**
	 * 计算四周均量
	 * （1）预测营业部未来货量需要根据历史数据预测，
	 * 		例如预测周一某营业部的开单货量，需要计算其前四周的周一平均货量来预测，并按照目的站显示
	 * （2）预测集中接货的未来货量需要根据历史数据预测，
	 * 		例如预测周一的集中接货量，需要计算其前四周的周一平均货量来预测，并按照目的站显示
	 * （3）预测未来货量的时间范围同转运场所配置查询的货量统计保持一致
	 * @author 163580
	 * @date 2014-3-25 下午7:42:06
	 * @return
	 * @see
	 */
	@Override
	public ForecastQuantityEntity countForecastQty(
			ForecastQuantityEntity forecastQuantityParam) {
		String statement = NAMESPACE + "countForecastQty";
		return (ForecastQuantityEntity) getSqlSession().selectOne(statement, forecastQuantityParam);
	}

	/**
	 * 提供给派送率的接口
	 * 查询预计某日派送到达货量（票数、重量、体积）
	 * @author 163580
	 * @date 2014-3-26 下午3:14:10
	 * @param deptCodes 部门编号
	 * @param forecastDate 预测日期
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ForecastQuantityEntity> queryForecastQtyByRelevantOrgCode(List<String> deptCodes,
			Date forecastDate) {
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("deptCodes", deptCodes);
		map.put("forecastDate", forecastDate);
		String statement = NAMESPACE + "queryForecastQtyByRelevantOrgCode";
		return getSqlSession().selectList(statement, map);
	}

	/**
	 * 根据车次号获取当前运单的所有流水
	 * @author 163580
	 * @date 2014-4-3 上午10:35:32
	 * @param deptCode
	 * @param vehicleassembleNo
	 * @param wayBillNo
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SerialnoDto> querySerialnoByVehicleassembleNo(String deptCode,
			String vehicleassembleNo, String wayBillNo) {
		Map<String, String> map = new HashMap<String, String>(PlatformConstants.SONAR_NUMBER_3);
		map.put("deptCode", deptCode);
		map.put("vehicleassembleNo", vehicleassembleNo);
		map.put("wayBillNo", wayBillNo);
		String statement = NAMESPACE + "querySerialnoByVehicleassembleNo";
		return getSqlSession().selectList(statement, map);
	}

	/**
	 * 根据运单号获取当前运单在库存中的所有流水
	 * (在库存表中查询) 
	 * @author 163580
	 * @date 2014-4-3 下午3:42:49
	 * @param deptCode
	 * @param wayBillNo
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SerialnoDto> querySerialnoByStock(String deptCode,
			String wayBillNo) {
		Map<String, String> map = new HashMap<String, String>(PlatformConstants.SONAR_NUMBER_3);
		map.put("deptCode", deptCode);
		map.put("wayBillNo", wayBillNo);
		String statement = NAMESPACE + "querySerialnoByStock";
		return getSqlSession().selectList(statement, map);
	}

	/**
	 * 根据下一到达部门, 运单号在走货路径中查流水号
	 * @author 163580
	 * @date 2014-4-3 下午4:37:26
	 * @param nextLine
	 * @param wayBillNo
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SerialnoDto> querySerialnoByPathDetail(String nextLine,
			String wayBillNo) {
		Map<String, String> map = new HashMap<String, String>(PlatformConstants.SONAR_NUMBER_3);
		map.put("nextLine", nextLine);
		map.put("wayBillNo", wayBillNo);
		String statement = NAMESPACE + "querySerialnoByPathDetail";
		return getSqlSession().selectList(statement, map);
	}

	/**
	 * 根据运单号在运单货签中查询流水号
	 * @author 163580
	 * @date 2014-4-3 下午4:39:45
	 * @param wayBillNo
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SerialnoDto> querySerialnoByLabeledGoods(String wayBillNo) {
		Map<String, String> map = new HashMap<String, String>(PlatformConstants.SONAR_NUMBER_3);
		map.put("wayBillNo", wayBillNo);
		String statement = NAMESPACE + "querySerialnoByLabeledGoods";
		return getSqlSession().selectList(statement, map);
	}

	/**
	 * 提供给仓库饱和度的接口
	 * 查询预计某日的操作货量(重量)
	 * 未来一天操作货量 = 未来一天到达该外场货量+未来一天该外场出发货量+该外场辐射营业部过去一个月内对应星期X的出发货量平均值
	 * @author 163580
	 * @date 2014-4-4 上午9:55:56
	 * @param deptCodes 外场code
	 * @param forecastDate
	 * @return
	 * @see
	 */
	@Override
	public ForecastQuantityEntity queryForecastWeightByRelevantOrgCode(
			String deptCode, List<NetGroupSiteDto> netGroupSites,
			Date forecastDate) {
		Map<String, Object> map = new HashMap<String, Object>(PlatformConstants.SONAR_NUMBER_2);
		map.put("deptCode", deptCode);
		map.put("netGroupSites", netGroupSites);
		map.put("forecastTime", forecastDate);
		String statement = NAMESPACE + "queryForecastWeightByRelevantOrgCode";
		return (ForecastQuantityEntity) getSqlSession().selectOne(statement, map);
	}

	/**
	 * 查询未开单货物(data_type = 1)
	 * @author 163580
	 * @date 2014-4-11 下午3:24:56
	 * @param forecastQuantityEntity
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ForecastQuantityEntity> queryUnbillingQuantity(
			ForecastQuantityEntity forecastQuantityEntity) {
		forecastQuantityEntity.setDataType(ForecastConstants.DATA_TYPE_ONE);
		String statement = NAMESPACE + "queryforecastQuantityList";
		return getSqlSession().selectList(statement, forecastQuantityEntity);
	}

	/**
	 * 统计某日货量
	 * @author 163580
	 * @date 2014-4-12 上午10:53:18
	 * @param forecastQuantityParam
	 * @return
	 * @see
	 */
	@Override
	public ForecastQuantityEntity countForecastQtyOneDay(
			ForecastQuantityEntity forecastQuantityParam) {
		String statement = NAMESPACE + "countForecastQtyOneDay";
		return (ForecastQuantityEntity) getSqlSession().selectOne(statement, forecastQuantityParam);
	}

	/**
	 * 统计某周货量
	 * 日期必须从周期开始
	 * @author 163580
	 * @date 2014-4-16 下午2:35:39
	 * @param forecastQuantityParam
	 * @return
	 * @see
	 */
	@Override
	public ForecastQuantityEntity countForecastQtyOneWeek(
			ForecastQuantityEntity forecastQuantityParam) {
		String statement = NAMESPACE + "countForecastQtyOneWeek";
		return (ForecastQuantityEntity) getSqlSession().selectOne(statement, forecastQuantityParam);
	}
}