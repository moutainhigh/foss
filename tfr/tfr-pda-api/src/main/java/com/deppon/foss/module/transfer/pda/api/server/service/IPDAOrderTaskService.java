package com.deppon.foss.module.transfer.pda.api.server.service;

import java.util.List;

import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAOrderSerialNoDetailEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAOrderTaskDetailEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAOrderTaskEntity;

/**
 * PDA接口：点单任务service
 * @author foss-272681
 * @date 2016-01-28 上午10:10:11
 */
public interface IPDAOrderTaskService {
    //pda刷新点单任务,传入当前部门，获得当前部门的点单任务编号，状态，点单人
	public List<PDAOrderTaskEntity> refreshOrderTaskByPDA(String orgCode,String operator);
	
	//pda任务更新,根据点单任务编号 获得点单任务明细以及总重量，总体积，总票数，总件数
	public PDAOrderTaskDetailEntity queryOrderTaskDetailByOrderTaskNo(String orderTaskNo);
	
	//pda获取流水明细,根据点单任务明细id
	public List<PDAOrderSerialNoDetailEntity> queryPDAOrderTaskSerialNoListByBillNo(String id);
	
	//pda扫描上传
	public List<String> scanOrderTask(String orderTaskNo,String waybillNo,String serialNo,String operator,String id);
	
	//pda提交点单任务
	public String finishPdaOrderTask(String orderTaskNo,String operator);
}
