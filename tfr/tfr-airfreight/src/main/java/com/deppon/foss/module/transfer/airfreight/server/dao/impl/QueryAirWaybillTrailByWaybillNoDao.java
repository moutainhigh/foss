package com.deppon.foss.module.transfer.airfreight.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IQueryAirWaybillTrailByWaybillNoDao;
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
public class QueryAirWaybillTrailByWaybillNoDao extends iBatis3DaoImpl implements
		IQueryAirWaybillTrailByWaybillNoDao{

	private String NAMESPACE="foss.airfreight.";

	/**
	 * 1.货物轨迹(配合快递张雨薇需求)[此代码可以注释掉,后续删掉了引用]
	 * @param waybillNo
	 * @return 
	 * @author 106162-foss-lishaoming
	 * @date   2016-05-23
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillTrack> queryWaybillTracks(String waybillNo) {
		List<WaybillTrack> list = this.getSqlSession().selectList(NAMESPACE+"queryWaybillTracks", waybillNo);
		return list;
	}

	 /**
		* @description 2. 配合(快递)根据正单号查询运单号(配合快递魏文文需求)
		* (non-Javadoc)
		* @see com.deppon.foss.service.IQueryAirWaybillTrailByWaybillNoService
		*                             #queryAirWaybillCreateDeptDao(java.lang.String)
		* @author 106162-foss-liping
		* @update 2016年5月16日 上午10:07:04
		* @version V1.0
		*/
	@Override
	public AirWayBillQueryCreateDeptVO queryAirWaybillCreateDeptDao(
			String waybillNo) {
		AirWayBillQueryCreateDeptVO airWayBillQueryCreateDeptVO =(AirWayBillQueryCreateDeptVO)this.getSqlSession().selectOne(NAMESPACE+"queryAirWayBillCreateDept", waybillNo);
		return airWayBillQueryCreateDeptVO;
	}

}
