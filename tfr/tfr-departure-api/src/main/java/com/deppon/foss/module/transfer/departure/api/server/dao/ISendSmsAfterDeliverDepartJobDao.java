/**  
 * Project Name:tfr-departure-api  
 * File Name:ISendSmsAfterDeliverDepartJobDao.java  
 * Package Name:com.deppon.foss.module.transfer.departure.api.server.dao  
 * Date:2015年5月24日下午2:43:01  
 * Copyright (c) 2015, shiwei@outlook.com All Rights Reserved.  
 *  
*/  
  
package com.deppon.foss.module.transfer.departure.api.server.dao;  

import java.util.List;

import com.deppon.foss.module.transfer.departure.api.shared.dto.SendSmsAfterDeliverDepartDto;

/**  
 * ClassName:ISendSmsAfterDeliverDepartJobDao <br/>  
 * Reason:   根据中转放行ID获取派送单中的运单明细. <br/>  
 * Date:     2015年5月24日 下午2:43:01 <br/>  
 * @author   shiwei-045923 shiwei@outlook.com  
 * @version    
 * @since    JDK 1.6  
 * @see        
 */
public interface ISendSmsAfterDeliverDepartJobDao {
	
	/**
	 * 
	 * queryDeliverDetailByTruckDepartID:根据中转放行ID获取派送单中的运单明细. <br/>  
	 * 根据中转放行ID获取派送单中的运单明细.<br/>  
	 *  
	 *  Date:2015年5月24日下午3:03:09  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @param truckDepartID
	 * @return  
	 * @since JDK 1.6
	 */
	List<SendSmsAfterDeliverDepartDto> queryDeliverDetailByTruckDepartID(String truckDepartID);
	
	

}
  
