package com.deppon.foss.module.settlement.consumer.api.server.dao;

import com.deppon.foss.module.settlement.consumer.api.shared.domain.OutStockExceptionEntity;

/**
 * 异常出库Dao
 * @author foss-qiaolifeng
 * @date 2012-12-17 下午4:19:20
 */
public interface IOutWarehouseExceptionDao {

	/**
	 * 新增异常出库信息
	 * @author foss-qiaolifeng
	 * @date 2012-12-17 下午4:18:58
	 */
	int addOutWarehouseException(OutStockExceptionEntity entity);

}
