package com.deppon.foss.module.transfer.arrival.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobTodoEntity;

/**
 * 
* @description 长途车辆出发到达时将车辆状态发送给TPS系统
* @version 1.0
* @author 105869-foss-heyongdong
* @update 2015年4月20日 下午5:43:23
 */
public interface ISendVehicleStatus2TPSJobService extends IService {
	
	/**
	 * 
	* @description 长途车辆出发到达时将车辆状态发送给TPS系统
	* @return
	* @version 1.0
	* @author 105869-foss-heyongdong
	* @update 2015年4月20日 下午5:44:35
	 */
	int sendVehicleStatus2TPS();
	
	/**
	 * 
	* @description 用于控制事务，单个车辆提交一次
	* @return
	* @version 1.0
	* @author 105869-foss-heyongdong
	* @update 2015年4月20日 下午5:47:06
	 */
	int sendOneVehicleStatus2TPS(TfrJobTodoEntity entity);

}
