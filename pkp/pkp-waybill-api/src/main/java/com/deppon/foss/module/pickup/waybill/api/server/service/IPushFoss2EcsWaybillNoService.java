package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.Date;


/**
 * FOSS将运单号传给ECS
 * @author 351326
 * 
 */
public interface IPushFoss2EcsWaybillNoService {
	/**
	 * 
	 * <p>推送运单号到ECS</p> 
	 * @author Foss-351326-xingjun 
	 * @date 2016-8-12 下午2:34:21
	 * @param waybillID
	 * @param waybillNo
	 * @param billCreateTime
	 * @param operator
	 * @see
	 */
	void pushFoss2EcsWaybillNo(String waybillID,String waybillNo,Date billCreateTime,String operator);
}
