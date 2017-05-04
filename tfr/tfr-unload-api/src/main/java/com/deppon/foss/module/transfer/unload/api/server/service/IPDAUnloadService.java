/*
 * PROJECT NAME: tfr-unload-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.api.server.service
 * FILE    NAME: IPDAUnloadService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.api.server.service;

import java.util.Date;

import com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadScanDetailDto;

/**
 * 没有锁的卸车扫描
 * 为了解锁前提交扫描事务
 * @author dp-duyi
 * @date 2013-3-20 上午9:14:23
 */
public interface IPDAUnloadService {
	public String unlockunLoadScan(UnloadScanDetailDto scanRecord);
	/**zwd 200968  快递集中卸车*/
	public String unlockunLoadScanWaybillStatus(UnloadScanDetailDto scanRecord);
	public String finishUnloadTaskAndSoOn(String taskNo, Date loadEndTime,
			String deviceNo, String loaderCode,String beException,String notes);
	public String unlockunLoadPackageScan(UnloadScanDetailDto scanRecord);
	//卸车运单扫描
	public String unLoadWayBillScan(UnloadScanDetailDto scanRecord);
	//卸车包扫描
	public String unLoadPackageScan(UnloadScanDetailDto scanRecord);
}
