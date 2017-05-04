package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.waybill.shared.domain.LabelPushProcessEntity;


/**
 * 
 * DOP/OWS标签信息推送线程实体DAO访问类接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-zhangfan,date:20160528,= </p>
 * @author dp-zhangfan
 * @date 20160528
 * @since
 * @version
 */
public interface ILabelPushProcessDao {
	public int insert(LabelPushProcessEntity entity);

	public LabelPushProcessEntity queryByWaibillNo(String waybillNo);

	public void updateById(LabelPushProcessEntity entity);
	
	public void updateSelective(Map<String, Object> map);

	public int updateJobIdForWaitingJobs(String jobId, int maxCount);

	public List<LabelPushProcessEntity> queryWaitingProcessWaybillNoByJobId(String jobId);
	
	/**
	 * 回滚待执行的记录，让它变成未调度的任务（将对应的记录修改为processCount=0，message=null，operateResult=unpushed）
	 * @return
	 */
	public void rollbackWaitingJob(String waybillNo);

}
