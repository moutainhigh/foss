/**  
 * Project Name:tfr-trackings  
 * File Name:WaybillTrackingsJobDao.java  
 * Package Name:com.deppon.foss.module.trackings.server.dao.impl  
 * Date:2015年4月16日下午7:49:50  
 *  
 */  
  
package com.deppon.foss.module.trackings.server.dao.impl;  

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.trackings.api.server.dao.IPushForWaybillTrackingsDao;
import com.deppon.foss.module.trackings.api.shared.dto.WaybillTrackingsDto;

/**  
 * ClassName: WaybillTrackingsJobDao <br/>  
 * Function: 根据任务明细ID获取运单信息. <br/>   
 * date: 2015年4月16日 下午7:49:50 <br/>  
 *  
 * @author shiwei-045923 shiwei@outlook.com  
 * @version   
 * @since JDK 1.6  
 */
public class PushForWaybillTrackingsDao extends iBatis3DaoImpl implements
		IPushForWaybillTrackingsDao {
	
	private static final String NAMESPACE = "foss.trackings.waybillTrackingsJob.";

	/**  
	 * 根据任务明细ID获取运单信息.  
	 * @see com.deppon.foss.module.trackings.api.server.dao.IPushForWaybillTrackingsDao#queryWaybillDetailByTruckTaskDetailID(java.lang.String)  
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillTrackingsDto> queryWaybillDetailByTruckTaskDetailID(
			String truckTaskDetailID) {
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillDetailByTruckTaskDetailID",truckTaskDetailID);
	}
	/**
	 * 通过卸车任务ID获取卸车运单明细
	 * @param truckTaskDetailID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillTrackingsDto> queryWaybillDetailByUnloadTaskID(
			String truckTaskDetailID) {
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillDetailByUnloadTaskID",truckTaskDetailID);
	}
}
  
