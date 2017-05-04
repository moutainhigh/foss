package com.deppon.foss.module.transfer.airfreight.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirLockWaybillDao;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirLockWaybillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillDetailEntity;

/**
 *  空运锁票Dao
 * 
 * @author 105795-wqh
 * @date 2014-03-17
 */
public class AirLockWaybillDao extends iBatis3DaoImpl implements IAirLockWaybillDao {

	private static final String NAMESPACE = "foss.airfreight.";

	@Override
	public int addAirLockWaybillDetail(
			AirLockWaybillDetailEntity airLockWaybillDetailEntity) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert(NAMESPACE+"addAirLockWaybillDetail", airLockWaybillDetailEntity);
	}

	/**
	 * @desc 解除空运锁票
	 * @param airLockWaybillDetailEntity 锁票实体
	 * @author 105795-wqh
	 * @date 2014-03-17
	 */ 
	@Override
	public int unlockAirWaybill(
			AirLockWaybillDetailEntity airLockWaybillDetailEntity) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update(NAMESPACE+"unlockAirWaybill", airLockWaybillDetailEntity);
	}

	
	/**
	 * @desc 查询空运锁票运单信息
	 * @param airLockWaybillDetailEntity 锁票实体
	 * @author 105795-wqh
	 * @date 2014-03-17
	 */
	@Override
	public AirLockWaybillDetailEntity queryLockAirWaybill(
			AirLockWaybillDetailEntity airLockWaybillDetailEntity) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<AirLockWaybillDetailEntity> list= 
				this.getSqlSession().selectList(NAMESPACE+"queryLockAirWaybill", airLockWaybillDetailEntity);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	
	/**
	 * @desc 新增空运锁票
	 * @param airLockWaybillDetailEntity 锁票实体
	 * @author 105795-wqh
	 * @date 2014-03-17
	 */ 
	@SuppressWarnings("unchecked")
	@Override
	public List<AirLockWaybillDetailEntity> queryLockAirWaybillList(
			List<String> waybillList, String orgCode) {
		// TODO Auto-generated method stub
		Map<String,Object> dataMap = new HashMap<String, Object>();
		dataMap.put("waybillNoList", waybillList);
		dataMap.put("orgCode", orgCode);
		return this.getSqlSession().selectList(NAMESPACE+"queryLockAirWaybillList", dataMap);
	}

	/**
	 * @desc 查询当前空运总调中运单件数
	 * @param orgCode 
	 * @param goodAreaCode
	 * @param waybillNoList
	 * @author 105795-wqh
	 * @date 2014-04-08
	 */ 
  @SuppressWarnings("unchecked")
public List<AirWaybillDetailEntity> queryWaybillStockQty(List<String> waybillNoList,
		  String orgCode,String goodAreaCode){
	  
	    Map<String,Object> dataMap = new HashMap<String, Object>();
	    dataMap.put("waybillNoList", waybillNoList);
	    dataMap.put("orgCode", orgCode);
	    dataMap.put("goodAreaCode", goodAreaCode);
	  return this.getSqlSession().selectList(NAMESPACE+"queryWaybillStockQty", dataMap);
  }
}
