package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.load.api.shared.domain.WKTfrBillEntity;

public interface IWKTfrBillDao {
	
	
	
	/**
	* @description 插入悟空交接单(表T_WK_TRF_BILL)信息
	* @param wKTfrBillEntity
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年4月22日 下午1:42:22
	*/
	public Map<String, String> insertBill(WKTfrBillEntity wKTfrBillEntity);

	
	
	/**
	* @description 更新悟空交接单(表T_WK_TRF_BILL)信息
	* @param wKTfrBillEntity
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年4月22日 下午1:42:09
	*/
	public Map<String, String> updateBill(WKTfrBillEntity wKTfrBillEntity); 
	
	
	/**
	* @description 获取交接单(表T_WK_TRF_BILL)信息
	* @param wktfrBillEntity
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月12日 下午3:54:08
	*/
	public WKTfrBillEntity getWKTfrBillEntity(WKTfrBillEntity wktfrBillEntity);
	
	/**
	* @description 根据车辆任务明细ID获取交接单号
	* @param wktfrBillEntity
	* @return
	* @version 1.0
	* @author 332209-foss-ruilibao
	* @update 2016年5月12日 下午3:54:08
	*/
	public List<String> getWkHandoverbillno(String truckTaskDetailId);
}
