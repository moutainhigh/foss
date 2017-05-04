package com.deppon.foss.module.transfer.pda.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAAssignLoadTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.QueryAssignedLoadTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressDeliverLoadTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressDeliverScanDto;

/**
 * 
 * <p>派件交接装车任务</p> 
 * @author alfred
 * @date 2015-5-13 上午10:30:25
 * @see
 */
public interface IPDAExpressSendPieceService {
	
	/**创建任务*/
	public String createTask(ExpressDeliverLoadTaskDto task);
	
	/**扫描*/
	public void scan(ExpressDeliverScanDto scanDto);
	
	/**提交任务*/
	public String submitLoadTask(String taskNo,Date loadEndTime,String deviceNo,String loaderCode);
	
	/**取消任务*/
	public String cancelLoadTask(String taskNo);
	/**获取未完成的派件装车指令**/
	public List<PDAAssignLoadTaskEntity> queryUnfinishLoadTask(
			QueryAssignedLoadTaskEntity condition);
}
