package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.server.dao.IWKTfrBillDao;
import com.deppon.foss.module.transfer.load.api.shared.domain.WKTfrBillEntity;

public class WKTfrBillDao extends iBatis3DaoImpl implements IWKTfrBillDao {
	
	private static final String NAMESPACE = "wk-tfr-bill.";
	
	private static final Logger LOGGER = Logger.getLogger(WKTfrBillDao.class);

	
	/**
	* @description 插入悟空交接单(表T_WK_TRF_BILL)信息
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.load.api.server.dao.IWKTfrBillDao#insertBill(com.deppon.foss.module.transfer.load.api.shared.domain.WKTfrBillEntity)
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午4:44:21
	* @version V1.0
	*/
	public Map<String, String> insertBill(WKTfrBillEntity wKTfrBillEntity) {
		String sql = NAMESPACE + "insertBill";
		
		Map<String, String> resMap = new HashMap<String, String>();
		String errMsg = "";
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("悟空交接单数据插入开始");
		}
		
		int result = 0;
		
		try {
			// 插入交接单
			result = this.getSqlSession().insert(sql, wKTfrBillEntity);
		} catch(Exception e) {
			LOGGER.error(e);
			errMsg = e.getMessage();
		}
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("悟空交接单数据插入结束");
		}
		
		resMap.put("result", result + "");
		resMap.put("errMsg", errMsg);
		
		return resMap;
	}

	
	
	/**
	* @description 更新悟空交接单(表T_WK_TRF_BILL)信息
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.load.api.server.dao.IWKTfrBillDao#updateBill(com.deppon.foss.module.transfer.load.api.shared.domain.WKTfrBillEntity)
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午4:44:07
	* @version V1.0
	*/
	public Map<String, String> updateBill(WKTfrBillEntity wKTfrBillEntity) {
		String sql = NAMESPACE + "updateBill";
		
		Map<String, String> resMap = new HashMap<String, String>();
		String errMsg = "";
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("悟空交接单数据更新开始");
		}
		
		int result = 0;
		
		try {
			// 更新悟空交接单
			result = this.getSqlSession().update(sql, wKTfrBillEntity);
		} catch(Exception e) {
			LOGGER.error(e);
			errMsg = e.getMessage();
		}
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("悟空交接单数据更新结束");
		}
		
		resMap.put("result", result + "");
		resMap.put("errMsg", errMsg);
		
		return resMap;
	}



	@Override
	public WKTfrBillEntity getWKTfrBillEntity(WKTfrBillEntity wktfrBillEntity) {
	String sql = NAMESPACE + "getWKTfrBillEntity";
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("获取悟空交接单数据开始");
		}
		
		WKTfrBillEntity result = null;
		
		try {
			//获取悟空交接单
			result = (WKTfrBillEntity) this.getSqlSession().selectOne(sql, wktfrBillEntity);
		} catch(Exception e) {
			LOGGER.error(e);
		}
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("获取悟空交接单数据结束");
		}
		
		
		return result;
	}


	/**
	* @description 根据车辆任务明细ID获取交接单号
	* @param wktfrBillEntity
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月12日 下午3:54:08
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getWkHandoverbillno(String truckTaskDetailId) {
		String sql = NAMESPACE + "getWkHandoverbillno";

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("获取悟空交接单数据开始");
		}
		List<String> result = null;
		try {
			// 获取悟空交接单
			result =  this.getSqlSession().selectList(sql, truckTaskDetailId);
		} catch (Exception e) {
			LOGGER.error(e);
		}
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("获取悟空交接单数据结束");
		}
		return result;
	}
}
