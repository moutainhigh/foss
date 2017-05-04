/**  
 * Project Name:tfr-trackings-api  
 * File Name:IWaybillTrackingsJobDao.java  
 * Package Name:com.deppon.foss.module.trackings.api.server.dao  
 * Date:2015年4月16日下午3:50:40  
 *  
 */  
  
package com.deppon.foss.module.trackings.api.server.dao;  

import java.util.List;

import com.deppon.foss.module.trackings.api.shared.dto.WaybillTrackingsDto;

/**  
 * ClassName: IWaybillTrackingsJobDao <br/>  
 * Function: 将车辆出发、到达、取消出发、取消到达之后的车载运单号存入轨迹临时表. <br/>  
 * date: 2015年4月16日 下午3:50:40 <br/>  
 *  
 * @author shiwei-045923 shiwei@outlook.com  
 * @version   
 * @since JDK 1.6  
 */
public interface IPushForWaybillTrackingsDao {
	
	/**
	 * 
	 * queryWaybillDetailByTruckTaskDetailID:(根据任务明细ID获取运单信息). <br/>  
	 *  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @param id
	 * @return  
	 * @since JDK 1.6
	 */
	List<WaybillTrackingsDto> queryWaybillDetailByTruckTaskDetailID(String truckTaskDetailID);
	/**
	 * 通过卸车任务ID获取卸车运单明细
	 * @param truckTaskDetailID
	 * @return
	 */
	List<WaybillTrackingsDto> queryWaybillDetailByUnloadTaskID(
			String truckTaskDetailID);

}
  
