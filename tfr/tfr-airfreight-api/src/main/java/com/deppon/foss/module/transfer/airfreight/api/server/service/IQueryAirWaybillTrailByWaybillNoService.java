package com.deppon.foss.module.transfer.airfreight.api.server.service;

import com.deppon.foss.module.transfer.airfreight.api.shared.vo.AirWayBillQueryCreateDeptVO;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.StatusAndTrackResponse;

/**
* @description 配合(快递)根据运单号查询对应的航空运单轨迹
* (non-Javadoc)
* @see com.deppon.foss.service.IQueryAirWaybillTrailByWaybillNoService
* @author 106162-foss-liping
* @update 2016年5月16日 上午10:07:04
* @version V1.0
*/
public interface IQueryAirWaybillTrailByWaybillNoService {

	/**
	* @description 配合(快递)根据正单号查询运单号(配合快递张雨薇需求)
	* (non-Javadoc)
	* @see com.deppon.foss.service.IQueryAirWaybillTrailByWaybillNoService
	*                             #queryStatusAndTrackResponse(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年5月16日 上午10:07:04
	* @version V1.0
	*/
	StatusAndTrackResponse queryStatusAndTrackResponse(String waybillNo);
	
	/**
	* @description 配合(快递)根据正单号查询运单号(配合快递魏文文需求)
	* (non-Javadoc)
	* @see com.deppon.foss.service.IQueryAirWaybillTrailByWaybillNoService
	*                             #queryAirWaybillCreateDept(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年5月16日 上午10:07:04
	* @version V1.0
	*/
	AirWayBillQueryCreateDeptVO queryAirWaybillCreateDept(String waybillNo);
}
