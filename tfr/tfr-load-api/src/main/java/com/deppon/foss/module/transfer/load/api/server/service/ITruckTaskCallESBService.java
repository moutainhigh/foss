/*
 * PROJECT NAME: tfr-load-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.server.service
 * FILE    NAME: ITruckTaskCallESBService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.server.service;

import java.util.Date;




/**
 *任务车辆通知CRM，GPS
 * @author dp-duyi
 * @date 2013-5-27 上午11:02:17
 */
public interface ITruckTaskCallESBService {
	public void callEsb(Date bizJobStartTime,Date bizJobEndTime,int threadNo, int threadCount);
	
}
