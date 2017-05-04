package com.deppon.foss.module.transfer.common.api.server.dao;

import java.sql.SQLException;
import java.util.List;

import com.deppon.foss.module.transfer.common.api.shared.dto.WkHandOverBillEntity;

/**
* @description 查询WK交接单和插入临时表用Dao
* @version 1.0
* @author 332209-FOSS  ruilibao
* @update 2016年4月26日 上午9:13:13
*/
public interface IWkBillAddTfrNotifyDao {
	
	
	/**
	 * 根据车辆任务明细ID查询WK交接单
	 */
	List<WkHandOverBillEntity> queryWkHandOverBillByTruckTaskDetailId(String truckTaskDetailId) throws SQLException;
	
}
