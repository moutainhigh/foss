package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.TruckEfficiencyEntity;

/**
 * 装卸车 效率统计dao
 * @author 200978  xiaobingcheng
 * 2015-1-19
 */
public interface ITruckEfficiencyDao {

	/***
	 * 统计装卸车效率信息
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-19
	 * @param statisticDate
	 * @return
	 */
	List<TruckEfficiencyEntity> loadAndUnloadEfficiency(Date statisticDate);
	
	/**
	 * 保存装卸车效率信息
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-20
	 * @param truckEfficiencyEntity
	 */
	void saveTruckEfficiencyEntity(TruckEfficiencyEntity truckEfficiencyEntity);
	
	/**
	 * 按日期查询装卸车效率
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-23
	 * @param truckEfficiencyEntity
	 * @return
	 */
	List<TruckEfficiencyEntity> queryTruckEfficiencyByDay(TruckEfficiencyEntity truckEfficiencyEntity,int start,int totalCount);
	
	/**
	 * 按日期查询装卸车效率  总条数
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-24
	 * @param truckEfficiencyEntity
	 * @return
	 */
	Long queryTruckEfficiencyByDayCount(TruckEfficiencyEntity truckEfficiencyEntity);
	
	/**
	 * 按日期查询装卸车效率    对于有转运场的 部门查询结果显示  当月1号到当前查询日期的记录
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-23
	 * @param truckEfficiencyEntity
	 * @return
	 */
	List<TruckEfficiencyEntity> queryTruckEfficiencyByDayOfTransfer(TruckEfficiencyEntity truckEfficiencyEntity,int start,int totalCount);
	
	/**
	 * 按日期查询(有外场)   总条数
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-24
	 * @param truckEfficiencyEntity
	 * @return
	 */
	Long queryTruckEfficiencyByDayOfTransferCount(TruckEfficiencyEntity truckEfficiencyEntity);
	
	/**
	 * 按月份查询装卸车效率
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-23
	 * @param truckEfficiencyEntity
	 * @return
	 */
	List<TruckEfficiencyEntity> queryTruckEfficiencyByMonth(TruckEfficiencyEntity truckEfficiencyEntity,int start,int totalCount);
	
	/***
	 * 按月份查询装车效率  总条数
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-24
	 * @param truckEfficiencyEntity
	 * @return
	 */
	Long queryTruckEfficiencyByMonthCount(TruckEfficiencyEntity truckEfficiencyEntity);
	
	/**
	 * 根据当前部门查询 他对应的经营本部
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-24
	 * @param code
	 * @return
	 */
	TruckEfficiencyEntity queryOperationDeptCodeByCurrentCode(String code);
	
}
