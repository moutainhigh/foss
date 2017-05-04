/**  
 * Project Name:tfr-departure-api  
 * File Name:ISendSmsAfterDeliverDepartJobService.java  
 * Package Name:com.deppon.foss.module.transfer.departure.api.server.service  
 * Date:2015年5月24日下午3:41:27  
 * Copyright (c) 2015, shiwei@outlook.com All Rights Reserved.  
 *  
*/  
  
package com.deppon.foss.module.transfer.departure.api.server.service;  

import com.deppon.foss.framework.service.IService;

/**  
 * ClassName:ISendSmsAfterDeliverDepartJobService <br/>  
 * Reason:   派送放行时，给收货客户发短信，供job调用. <br/>  
 * Date:     2015年5月24日 下午3:41:27 <br/>  
 * @author   shiwei-045923 shiwei@outlook.com  
 * @version    
 * @since    JDK 1.6  
 * @see        
 */
public interface ISendSmsAfterDeliverDepartJobService extends IService{
	
	/**
	 * 
	 * SendSmsAfterDeliverDepart:job调用入口方法. <br/>  
	 * job调用入口方法.<br/>  
	 *  
	 *  Date:2015年5月24日下午3:43:35  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @return  
	 * @since JDK 1.6
	 */
	int SendSmsAfterDeliverDepart();
	
	/**
	 * 
	 * SendSmsAfterDeliverDepartByTruckDepartID:按车辆放行ID来处理，用于事务控制. <br/>  
	 * 按车辆放行ID来处理，用于事务控制.<br/>  
	 *  
	 *  Date:2015年5月24日下午3:44:09  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @param truckDepartID
	 * @param jobTodoID
	 * @return  
	 * @since JDK 1.6
	 */
	int SendSmsAfterDeliverDepartByTruckDepartID(String truckDepartID,String jobTodoID);
}
  
