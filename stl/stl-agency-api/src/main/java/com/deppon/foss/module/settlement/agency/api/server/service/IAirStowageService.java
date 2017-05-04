package com.deppon.foss.module.settlement.agency.api.server.service;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;


/**
 * 空运配载单服务
 * @author ibm-zhuwei
 * @date 2012-11-21 上午11:33:04
 */
public interface IAirStowageService {
	
	/**
	 * 新增空运配载单
	 * @author ibm-zhuwei
	 * @date 2012-11-21 上午11:36:04
	 */
	void addAirStowage(AirWaybillEntity airWaybill, CurrentInfo currentInfo);

	/**
	 * 作废空运配载单
	 * @author ibm-zhuwei
	 * @date 2012-11-22 下午3:25:05
	 */
	void cancelAirStowage(String airWaybillNo, CurrentInfo currentInfo);
	
	/**
	 * 修改空运配载单
	 * @author ibm-zhuwei
	 * @date 2012-11-21 上午11:36:06
	 */
	void modifyAirStowage(AirWaybillEntity oldAirWaybill, AirWaybillEntity newAirWaybill, CurrentInfo currentInfo);
	
}
