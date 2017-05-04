/*
 * PROJECT NAME: tfr-load
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server.dao.impl
 * FILE    NAME: TruckTaskCallESBDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskCallESBDao;
import com.deppon.foss.module.transfer.load.api.shared.dto.HandOverBillDetailDto;

/**
 * 
 * @author dp-duyi
 * @date 2013-5-27 上午11:25:38
 */
@SuppressWarnings("unchecked")
public class TruckTaskCallESBDao extends iBatis3DaoImpl implements ITruckTaskCallESBDao{
	private static final String NAMESPACE = "tfr-load.";
	/** 
	 * 查询未通知crm交接单
	 * @author dp-duyi
	 * @date 2013-5-27 上午11:26:32
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskCallESBDao#queryUnUpdateTransportPathHandOverBill(java.util.Date, java.util.Date, int, int)
	 */
	@Override
	public List<HandOverBillDetailDto> queryUnupdateCRMHandOverBill(
			Date bizJobStartTime, Date bizJobEndTime, int threadNo,
			int threadCount) {
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		//线程数
		paramsMap.put("threadCount", threadCount);
		//线程号
		paramsMap.put("threadNo", threadNo);
		//开始时间
		paramsMap.put("bizJobStartTime", bizJobStartTime);
		//结束时间
		paramsMap.put("bizJobEndTime", bizJobEndTime);
		return this.getSqlSession().selectList(NAMESPACE+"queryUnupdateCRMHandOverBill",paramsMap);
	}
	/** 
	 * @author dp-duyi
	 * @date 2013-5-27 上午11:46:56
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskCallESBDao#updateHandOverBillBeUpdateCRM(java.util.List)
	 */
	@Override
	public void updateHandOverBillBeUpdateCRM(String handBillNo) {
		 this.getSqlSession().update(NAMESPACE+"updateHandOverBillBeUpdateCRM",handBillNo);
	}

}
