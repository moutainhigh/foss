/*
 * PROJECT NAME: tfr-pda-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.pda.api.server.service
 * FILE    NAME: IPDAExpressDeliverLoadService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAAssignLoadTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressDeliverLoadTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressDeliverScanDto;

/**
 * 快递派送装车
 * @author dp-duyi
 * @date 2013-7-24 下午2:41:36
 */
public interface IPDAExpressDeliverLoadService {
	/**查询未完成派送装车任务*/
	public List<PDAAssignLoadTaskEntity> queryExpressDeliverLoadTask(String loaderCode,String loaderOrg,Date queryTimeBegin,Date queryTimeEnd);
	/**新建任务:返回任务号*/
	public String createTask(ExpressDeliverLoadTaskDto task);
	/**取消任务*/
	public String cancelLoadTask(String taskNo,String deviceNo,String operatorCode,Date cancelTime);
	/**提交任务*/
	public String submitLoadTask(String taskNo,Date loadEndTime,String deviceNo,String loaderCode);
	/**扫描*/
	public void scan(ExpressDeliverScanDto scanDto);
	/**
	 * 
	 * 根据装车任务表的taskNo查找装车运单明细表中已装车件数小于运单表的货物总件数的运单号信息 
	 * @param taskNo
	 * @return
		
	 */
      public List<String> queryWayBillNo(String taskNo);
	
}
