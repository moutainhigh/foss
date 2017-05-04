package com.deppon.foss.module.transfer.unload.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.pda.api.shared.domain.SCPDAAssignUnloadTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadGoodsDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadScanDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadTaskResultDto;

public interface ISCPDAUnloadTaskService {
	
	//查询分配卸车任务
	List<SCPDAAssignUnloadTaskEntity> pdaQueryAssignedSCUnloadTask(
			String vehicleNo);
	//建立卸车任务
	public UnloadTaskResultDto createLoadTask(UnloadTaskDto unloadTask);
	
	//刷新卸车任务明细
	public List<UnloadGoodsDetailDto> refrushUnloadTaskDetail(String taskNo);
	
	//卸车扫描
	public String unLoadWayBillScan(UnloadScanDetailDto scanRecord);
	
	//提交任务
	String finishSCUnloadTask(String taskNo, Date loadEndTime, String deviceNo,
			String loaderCode, String beException, String notes);
	//扫描后  状态改变
    public int scUnloadState(String taskNo,String billNo);
	
}
