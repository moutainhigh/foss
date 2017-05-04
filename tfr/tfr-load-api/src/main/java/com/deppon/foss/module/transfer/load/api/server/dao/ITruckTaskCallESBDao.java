/*
 * PROJECT NAME: tfr-load-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.server.dao
 * FILE    NAME: ITruckTaskCallESBDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.dto.HandOverBillDetailDto;

/**
 * TODO（描述类的职责）
 * @author dp-duyi
 * @date 2013-5-27 上午11:23:47
 */
public interface ITruckTaskCallESBDao {
	//查询未更新crm状态交接单
	public List<HandOverBillDetailDto> queryUnupdateCRMHandOverBill(Date bizJobStartTime,	Date bizJobEndTime,int threadNo, int threadCount);
	public void updateHandOverBillBeUpdateCRM(String handBillNo);
}
