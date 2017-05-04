package com.deppon.foss.module.transfer.load.api.server.service;

import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.load.api.shared.domain.WKBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.WKTfrBillEntity;

/**
 *	悟空交接单service 
 * @author xieyang
 * @date 2016-4-21 下午2:22:01
 * @since
 * @version
 */
public interface IWKTfrBillService extends IService {
	
	
	/**
	* @description 插入悟空交接单(表T_WK_TRF_BILL)信息
	* @param wKTfrBillEntity
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年4月22日 下午1:43:12
	*/
	public Map<String, String> insertBill(WKTfrBillEntity wKTfrBillEntity);

	
	
	/**
	* @description 更新悟空交接单(表T_WK_TRF_BILL)信息
	* @param wKTfrBillEntity
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年4月22日 下午1:43:23
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
	* @description 插入交接单和装配单
	* @param wkBillEntity
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年6月6日 下午5:10:55
	*/
	public Map<String, String> insertWKBill(WKBillEntity wkBillEntity);
	
	
	
	/**
	* @description 插入交接单和装配单
	* @param wkBillEntity
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年6月6日 下午5:11:19
	*/
	public Map<String, String> updateWKBill(WKBillEntity wkBillEntity);
	
}
