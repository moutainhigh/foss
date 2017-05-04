package com.deppon.foss.module.transfer.load.api.server.service;


import com.deppon.foss.module.transfer.common.api.shared.domain.TfrNotifyEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.TruckTaskHandOverDto;


/**
* @description 车辆任务service(快递拆分,快递对接FOSS车辆任务service)
* @version 1.0
* @author 283250-foss-liuyi
* @update 2016年4月26日 下午7:55:26
*/
public interface ITruckTaskForWkService {
	
	
	/**
	* @description 根据快递系统同步过来的交接单信息，生成车辆任务(针对快递系统)
	* @param handOverBillNo
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年4月27日 下午6:43:42
	*/
	public void createTruckTaskByHandOverBillFromWk(TfrNotifyEntity entity);

	
	/**
	* @description 异步方式 同步车辆任务到快递系统
	* @param entity
	* @return
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年4月28日 上午11:39:34
	*/
	public void pushTruckTaskToWk(TfrNotifyEntity entity);
	
	
	/**
	* @description 更新车辆任务到快递系统
	* @param entity
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年4月30日 上午11:39:04
	*/
	public void updateTruckTaskToWk(TfrNotifyEntity entity);
	
	
	
	/**
	* @description 快递系统交接单作废，触发车辆任务作废
	* @param handOverBillNo
	* @return
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年5月12日 下午8:11:36
	*/
	public int deleteTruckTaskByWkHandOverBill(String handOverBillNo);
	
	
	/**
	* @description 快递系统交接单更新,触发车辆任务更新
	* @param handOverBillNo
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年5月12日 下午8:12:34
	*/
	public void updateTruckTaskByWkHandOverBill(String handOverBillNo, TruckTaskHandOverDto truckTask);
	
}
