/**  
 * Project Name:tfr-trackings-api  
 * File Name:IWaybillTrackingsJobService.java  
 * Package Name:com.deppon.foss.module.trackings.api.server.service  
 * Date:2016年2月19日下午7:52:58  
 *  
 */  
  
package com.deppon.foss.module.trackings.api.server.service;  
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.trackings.api.shared.dto.WaybillTrackingsDto;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobTodoEntity;

/**  
 * ClassName: IPushForWaybillToPTPService <br/>  
 * Function:. <br/>  
 * date: 2016年2月19日 下午7:52:58 <br/>  
 *  
 * @author 189284-foss-zhangxu
 * @version   
 * @since JDK 1.6  
 */
public interface IPushForWaybillToPTPService extends IService {
	
	/**
	 * 
	 * pushForWaybillTrackings:(车辆出发、取消出发、到达、取消到达后，将车载运单推送到临时表，该方法供Job调用). <br/>  
	 *  
	 * @author 189284-foss-zhangxu
	 * @return  
	 * @since JDK 1.6
	 */
	int pushForWaybillTrackings();
	
	/**
	 * 
	 * pushForWaybillTrackingsByTruckTaskDetailID:(根据任务车辆明细ID将车载运单存入临时表，控制事务，被pushForWaybillTrackings方法调用). <br/>  
	 *  
	 * @author 189284-foss-zhangxu
	 * @return  
	 * @since JDK 1.6
	 */

	void pushWaybillToPTP(List<WaybillTrackingsDto> waybillList,String operatorCode,String operatorName,String businessDesc2);

	}
  
