package com.deppon.foss.module.transfer.airfreight.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.airfreight.api.shared.vo.AirWayBillQueryCreateDeptVO;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.WaybillTrack;

/**
* @description 配合(快递)根据运单号查询对应的航空运单轨迹
* (non-Javadoc)
* @see com.deppon.foss.service.IQueryAirWaybillTrailByWaybillNoDao
* @author 106162-foss-liping
* @update 2016年5月16日 上午10:07:04
* @version V1.0
*/
public interface IQueryAirWaybillTrailByWaybillNoDao {

	/**
	 * 1.货物轨迹
	 * @param waybillNo
	 * @return
	 * @author 106162-foss-lishaoming
	 * @date   2016-05-23
	 */
    List<WaybillTrack> queryWaybillTracks(String waybillNo);
    
    /**
	* @description 配合(快递)根据正单号查询运单号(配合快递魏文文需求)
	* (non-Javadoc)
	* @see com.deppon.foss.service.IQueryAirWaybillTrailByWaybillNoService
	*                             #queryAirWaybillCreateDeptDao(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年5月16日 上午10:07:04
	* @version V1.0
	*/
    AirWayBillQueryCreateDeptVO queryAirWaybillCreateDeptDao(String waybillNo);
}
