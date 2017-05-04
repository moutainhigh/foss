package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.ITruckEfficiencyDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TruckEfficiencyEntity;

/**
 * 装卸车 效率统计dao
 * @author 200978  xiaobingcheng
 * 2015-1-19
 */
public class TruckEfficiencyDao extends iBatis3DaoImpl implements ITruckEfficiencyDao {

	private static final String NAMESPACE = "Foss.platform.truckEfficiencyJob.";
	
	/**
	 * 装卸车效率统计方法
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TruckEfficiencyEntity> loadAndUnloadEfficiency(
			Date statisticDate) {
		Map<String,Date> map = new HashMap<String, Date>();
		map.put("statisticDate", statisticDate);
		return this.getSqlSession().selectList(NAMESPACE+"loadAndUnloadEfficiency", map);
	}

	/**
	 * 保存
	 */
	@Override
	public void saveTruckEfficiencyEntity(
			TruckEfficiencyEntity truckEfficiencyEntity) {
		this.getSqlSession().insert(NAMESPACE + "saveTruckEfficiencyEntity", truckEfficiencyEntity);
	}

	/**
	 * 按日期查询装卸车效率
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-23
	 * @param truckEfficiencyEntity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TruckEfficiencyEntity> queryTruckEfficiencyByDay(TruckEfficiencyEntity truckEfficiencyEntity,int start,int totalCount){
		RowBounds rb = new RowBounds(start, totalCount);
		return this.getSqlSession().selectList(NAMESPACE + "queryTruckEfficiencyByDay", truckEfficiencyEntity,rb);
	}
	
	/**
	 * 按日期查询装卸车效率  总条数
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-24
	 * @param truckEfficiencyEntity
	 * @return
	 */
	public Long queryTruckEfficiencyByDayCount(TruckEfficiencyEntity truckEfficiencyEntity){
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryTruckEfficiencyByDayCount", truckEfficiencyEntity);
	}
	
	/**
	 * 按日期查询装卸车效率    对于有转运场的 部门查询结果显示  当月1号到当前查询日期的记录
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-23
	 * @param truckEfficiencyEntity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TruckEfficiencyEntity> queryTruckEfficiencyByDayOfTransfer(TruckEfficiencyEntity truckEfficiencyEntity,int start,int totalCount){
		RowBounds rb = new RowBounds(start, totalCount);
		return this.getSqlSession().selectList(NAMESPACE + "queryTruckEfficiencyByDayOfTransfer", truckEfficiencyEntity,rb);
	}
	
	/**
	 * 按日期查询(有外场)   总条数
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-24
	 * @param truckEfficiencyEntity
	 * @return
	 */
	public Long queryTruckEfficiencyByDayOfTransferCount(TruckEfficiencyEntity truckEfficiencyEntity){
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryTruckEfficiencyByDayOfTransferCount", truckEfficiencyEntity);
	}
	
	/**
	 * 按月份查询装卸车效率
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-23
	 * @param truckEfficiencyEntity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TruckEfficiencyEntity> queryTruckEfficiencyByMonth(TruckEfficiencyEntity truckEfficiencyEntity,int start,int totalCount){
		RowBounds rb = new RowBounds(start, totalCount);
		return this.getSqlSession().selectList(NAMESPACE + "queryTruckEfficiencyByMonth", truckEfficiencyEntity,rb);
	}
	
	/***
	 * 按月份查询装车效率  总条数
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-24
	 * @param truckEfficiencyEntity
	 * @return
	 */
	public Long queryTruckEfficiencyByMonthCount(TruckEfficiencyEntity truckEfficiencyEntity){
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryTruckEfficiencyByMonthCount",truckEfficiencyEntity);
	}
	
	/**
	 * 根据当前部门查询 他对应的经营本部
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-24
	 * @param code
	 * @return
	 */
	public TruckEfficiencyEntity queryOperationDeptCodeByCurrentCode(String code){
		return (TruckEfficiencyEntity) this.getSqlSession().selectOne(NAMESPACE + "queryOperationDeptCodeByCurrentCode", code);
	}
	
	
}
