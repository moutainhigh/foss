package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.LabelPushProcessEntity;

/**
 * 
 * 标签推送job管理服务
 * @author 329834-zhangfan
 *
 */
public interface ILabelPushProcessService {

	/**
	 * 插入JOB
	 * @param orderNo
	 * @param waybillNo
	 */
	public void insertLabelPushProcessEntity(String orderNo, String waybillNo);

	/**
	 * 执行JOB之后更新
	 * @param waybillNo
	 * @param operateResult
	 * @param message
	 */
	public void updateAfterProcess(String waybillNo, String operateResult,
			String message);
	
	/**
	 * JMS响应之后更新
	 * @param waybillNo
	 * @param operateResult
	 * @param message
	 */
	public void updateAfterJmsResponse(String waybillNo, String operateResult,
			String message);

	/**
	 * 重试失败的任务
	 * @param waybillNo
	 */
	public void retryFailedJob(List<String> waybillNos);

	/**
	 * 更新待执行job的jobId
	 * @return
	 */
	public int updateJobIdForWaitingJobs(String jobId, int maxCount);
	
	/**
	 * 回滚待执行的记录，让它变成未调度的任务（将对应的记录修改为processCount=0，message=null，operateResult=unpushed）
	 * @return
	 */
	public void rollbackWaitingJob(String waybillNo);
	
	public List<LabelPushProcessEntity> queryWaitingProcessWaybillNoByJobId(String jobId);

}
