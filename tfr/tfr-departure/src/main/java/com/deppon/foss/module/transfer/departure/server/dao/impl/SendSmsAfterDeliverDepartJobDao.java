/**  
 * Project Name:tfr-departure  
 * File Name:SendSmsAfterDeliverDepartJobDao.java  
 * Package Name:com.deppon.foss.module.transfer.departure.server.dao.impl  
 * Date:2015年5月24日下午3:04:44  
 * Copyright (c) 2015, shiwei@outlook.com All Rights Reserved.  
 *  
*/  
  
package com.deppon.foss.module.transfer.departure.server.dao.impl;  

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.departure.api.server.dao.ISendSmsAfterDeliverDepartJobDao;
import com.deppon.foss.module.transfer.departure.api.shared.dto.SendSmsAfterDeliverDepartDto;

/**  
 * ClassName:SendSmsAfterDeliverDepartJobDao <br/>  
 * Reason:   根据中转放行ID获取派送单中的运单明细. <br/>  
 * Date:     2015年5月24日 下午3:04:44 <br/>  
 * @author   shiwei-045923 shiwei@outlook.com  
 * @version    
 * @since    JDK 1.6  
 * @see        
 */
public class SendSmsAfterDeliverDepartJobDao extends iBatis3DaoImpl implements
		ISendSmsAfterDeliverDepartJobDao {
	
	private static final String NAMESPACE = "foss.tfr.departure.sendsmsafterdeliverdepart.";

	/**
	 * 根据车辆放行ID获取运单明细.  
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.ISendSmsAfterDeliverDepartJobDao#queryDeliverDetailByTruckDepartID(java.lang.String)
	 */
	@Override
	@SuppressWarnings(value = { "unchecked" })
	public List<SendSmsAfterDeliverDepartDto> queryDeliverDetailByTruckDepartID(
			String truckDepartID) {
		return getSqlSession().selectList(NAMESPACE + "queryDeliverDetailByTruckDepartID", truckDepartID);
	}

}
  
