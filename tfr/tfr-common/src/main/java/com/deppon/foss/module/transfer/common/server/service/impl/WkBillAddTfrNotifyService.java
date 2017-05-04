package com.deppon.foss.module.transfer.common.server.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.transfer.common.api.server.dao.IWkBillAddTfrNotifyDao;
import com.deppon.foss.module.transfer.common.api.server.service.IWkBillAddTfrNotifyService;
import com.deppon.foss.module.transfer.common.api.shared.dto.WkHandOverBillEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;

public class WkBillAddTfrNotifyService implements IWkBillAddTfrNotifyService {

	private static final Logger log = LoggerFactory.getLogger(WkBillAddTfrNotifyService.class);
	/**
	 * DAO
	 */
	private IWkBillAddTfrNotifyDao wkBillAddTfrNotifyDao;
	
	
	/**
	 * @param wkBillAddTfrNotifyDao the wkBillAddTfrNotifyDao to set
	 */
	public void setWkBillAddTfrNotifyDao(IWkBillAddTfrNotifyDao wkBillAddTfrNotifyDao) {
		this.wkBillAddTfrNotifyDao = wkBillAddTfrNotifyDao;
	}

	/**
	 * 根据车辆任务明细ID查询WK交接单
	 */
	@Override
	public List<WkHandOverBillEntity> queryWkHandOverBillByTruckTaskDetailId(String truckTaskDetailId) {
		
		log.error("根据车辆任务明细ID查询WK交接单开始，车辆任务明细ID = " + truckTaskDetailId);
		List<WkHandOverBillEntity> list = new ArrayList<WkHandOverBillEntity>();
		try {
			list = wkBillAddTfrNotifyDao.queryWkHandOverBillByTruckTaskDetailId(truckTaskDetailId);
		} catch (SQLException e) {
			log.error("数据库异常了" + e);
			throw new TfrBusinessException("数据库异常了，请稍后操作", e);
		}
		log.error("根据车辆任务明细ID查询WK交接单结束，取到了" + list.size() + "交接单");
		return list;
	}
}
