package com.deppon.foss.module.transfer.airfreight.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirLockWaybillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillDetailEntity;

/**
 *  空运锁票Dao接口
 * 
 * @author 105795-wqh
 * @date 2014-03-17
 */
public interface IAirLockWaybillDao {

	/**
	 * @desc 查询空运锁票运单信息
	 * @param airLockWaybillDetailEntity 锁票实体
	 * @author 105795-wqh
	 * @date 2014-03-17
	 */
      AirLockWaybillDetailEntity queryLockAirWaybill(AirLockWaybillDetailEntity airLockWaybillDetailEntity);
	
  	/**
  	 * @desc 查询空运锁票运单信息List
  	 * @param airLockWaybillDetailEntity 锁票实体
  	 * @author 105795-wqh
  	 * @date 2014-03-17
  	 */
      List<AirLockWaybillDetailEntity> queryLockAirWaybillList(List<String> waybillList,String orgCode);
	
	
	/**
	 * @desc 新增空运锁票
	 * @param airLockWaybillDetailEntity 锁票实体
	 * @author 105795-wqh
	 * @date 2014-03-17
	 */ 
    int addAirLockWaybillDetail(AirLockWaybillDetailEntity airLockWaybillDetailEntity);
    
	/**
	 * @desc 解除空运锁票
	 * @param airLockWaybillDetailEntity 锁票实体
	 * @author 105795-wqh
	 * @date 2014-03-17
	 */ 
    int unlockAirWaybill(AirLockWaybillDetailEntity airLockWaybillDetailEntity);
    
    /**
	 * @desc 查询当前空运总调中运单件数
	 * @param orgCode 
	 * @param goodAreaCode
	 * @param waybillNoList
	 * @author 105795-wqh
	 * @date 2014-04-08
	 */ 
   List<AirWaybillDetailEntity> queryWaybillStockQty(List<String> waybillNoList,String orgCode,String goodAreaCode);
    
}
