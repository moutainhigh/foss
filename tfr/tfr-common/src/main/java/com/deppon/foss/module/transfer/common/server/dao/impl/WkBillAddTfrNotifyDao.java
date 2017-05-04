package com.deppon.foss.module.transfer.common.server.dao.impl;

import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.common.api.server.dao.IWkBillAddTfrNotifyDao;
import com.deppon.foss.module.transfer.common.api.shared.dto.WkHandOverBillEntity;

public class WkBillAddTfrNotifyDao extends iBatis3DaoImpl implements IWkBillAddTfrNotifyDao {
	private static final Logger log = LoggerFactory.getLogger(WkBillAddTfrNotifyDao.class);
	private static final String NAMESPACE = "foss.tfr.wkhandover.";
	
	/*
	 * 根据车辆任务明细ID查询悟空交接单
	 * @see com.deppon.foss.module.transfer.common.api.server.dao.IWkBillAddTfrNotifyDao#queryWkHandOverBillByTruckTaskDetailId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WkHandOverBillEntity> queryWkHandOverBillByTruckTaskDetailId(String truckTaskDetailId) throws SQLException {
		log.error("执行根据车辆任务明细ID查询悟空交接单DAO开始");
		return this.getSqlSession().selectList(NAMESPACE + "queryWkHandOverBillByTruckTaskDetailId", truckTaskDetailId);
	}
}
