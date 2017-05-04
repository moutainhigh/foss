package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ILabelPushProcessDao;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabelPushProcessEntity;
import com.eos.system.utility.Assert;

public class LabelPushProcessDao extends iBatis3DaoImpl implements ILabelPushProcessDao{
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.waybill.shared.domain.LabelPushProcessEntity.";
	
	@Override
	public int insert(LabelPushProcessEntity entity) {
		return getSqlSession().insert(NAMESPACE+"insert", entity);
	}
	
	@Override
	public void updateById(LabelPushProcessEntity entity){
		getSqlSession().update(NAMESPACE+"updateById", entity);
	}
	
	public LabelPushProcessEntity queryByWaibillNo(String waybillNo){
		Assert.notNull(waybillNo,"运单号不可为空！");
		return (LabelPushProcessEntity)getSqlSession().selectOne(NAMESPACE+"queryByWaibillNo", waybillNo);
	}

	@Override
	public int updateJobIdForWaitingJobs(String jobId, int maxCount) {
		//待处理的任务为operateResult为unpushed的或者是operateResult为fail并且processCOUNT<5的，并且active为Y
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("jobId", jobId);
		map.put("maxCount", maxCount);
		return getSqlSession().update(NAMESPACE+"updateJobIdForWaitingJobs",map);
	}
	
	/**
	 * 回滚待执行的记录，让它变成未调度的任务（将对应的记录修改为processCount=0，message=null，operateResult=unpushed）
	 * @return
	 */
	@Override
	public void rollbackWaitingJob(String waybillNo) {
		Assert.isTrue(StringUtils.isNotEmpty(waybillNo), "waybillNo参数不可为空！");
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("waybillNo", waybillNo);
		map.put("operateResult", WaybillConstants.LTL_EWAYBILL_LABELPUH_OPERATE_RESULT_UNPUSHED);
		map.put("message", "线程池满，该任务记录被回滚为未分配状态。");
		getSqlSession().update(NAMESPACE+"rollbackWaitingJob",map);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<LabelPushProcessEntity> queryWaitingProcessWaybillNoByJobId(String jobId){
		return (List<LabelPushProcessEntity>)getSqlSession().selectList(NAMESPACE+"queryWaitingProcessWaybillNoByJobId",jobId);
	}

	@Override
	public void updateSelective(Map<String, Object> map) {
		Assert.isTrue(StringUtil.isNotEmpty((String)map.get("waybillNo")), "运单号不可为空！");
		getSqlSession().update(NAMESPACE+"updateSelective",map);
	}

	
}
