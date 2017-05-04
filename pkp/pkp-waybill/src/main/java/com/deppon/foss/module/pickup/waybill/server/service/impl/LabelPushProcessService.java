package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.pickup.waybill.api.server.dao.ILabelPushProcessDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabelPushProcessService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabelPushProcessEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.eos.system.utility.Assert;

@Transactional(readOnly = true)
public class LabelPushProcessService implements ILabelPushProcessService{
	private ILabelPushProcessDao labelPushProcessDao;
	
	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.ILabelPushProcessService#insertLabelPushProcessEntity(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public void insertLabelPushProcessEntity(String orderNo, String waybillNo){
		Assert.notNull(orderNo, "订单号不可为空！");
		Assert.notNull(waybillNo, "运单号不可为空！");
		
		LabelPushProcessEntity existingEntity =  labelPushProcessDao.queryByWaibillNo(waybillNo);
		invalidateExistingEntity(existingEntity);
		
		LabelPushProcessEntity entity = new LabelPushProcessEntity();
		entity.setId(UUIDUtils.getUUID());
		entity.setOrderNo(orderNo);
		entity.setWaybillNo(waybillNo);
		entity.setCreateTime(new Date());
		entity.setModifyTime(entity.getCreateTime());
		entity.setJobId(WaybillConstants.UNKNOWN);
		entity.setOperateResult(WaybillConstants.LTL_EWAYBILL_LABELPUH_OPERATE_RESULT_UNPUSHED);
		entity.setProcessCount(0);
		entity.setActive(FossConstants.ACTIVE);
		
		labelPushProcessDao.insert(entity);
	}
	
	@Transactional
	public void invalidateExistingEntity(LabelPushProcessEntity entity){
		if(entity == null || StringUtils.isEmpty(entity.getWaybillNo()) || StringUtils.isEmpty(entity.getOrderNo())){
			return;
		}
		entity.setActive(FossConstants.INACTIVE);
		labelPushProcessDao.updateById(entity);
	}
	
	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.ILabelPushProcessService#updateAfterProcess(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public void updateAfterProcess(String waybillNo, String operateResult, String message){
		Assert.notNull(waybillNo, "运单号不可为空！");
		Assert.isTrue(WaybillConstants.LTL_EWAYBILL_LABELPUH_OPERATE_RESULT_FAIL.equals(operateResult)
					||WaybillConstants.LTL_EWAYBILL_LABELPUH_OPERATE_RESULT_SUCCESS.equals(operateResult)
					||WaybillConstants.LTL_EWAYBILL_LABELPUH_OPERATE_RESULT_PUSHED.equals(operateResult)
					, "执行结果参数不正确！值为："+operateResult);
		
		
		LabelPushProcessEntity entity =  labelPushProcessDao.queryByWaibillNo(waybillNo);
		
		entity.setModifyTime(new Date());
		entity.setOperateResult(operateResult);
		entity.setMessage(message);
		entity.setProcessCount(entity.getProcessCount()+1);

		labelPushProcessDao.updateById(entity);
	}
	
	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.ILabelPushProcessService#updateAfterProcess(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public void updateAfterJmsResponse(String waybillNo, String operateResult, String message){
		Assert.notNull(waybillNo, "运单号不可为空！");
		Assert.isTrue(WaybillConstants.LTL_EWAYBILL_LABELPUH_OPERATE_RESULT_FAIL.equals(operateResult)
					||WaybillConstants.LTL_EWAYBILL_LABELPUH_OPERATE_RESULT_SUCCESS.equals(operateResult)
					, "执行结果参数不正确！值为："+operateResult);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("waybillNo", waybillNo);
		map.put("modifyTime", new Date());
		map.put("operateResult", operateResult);
		map.put("message", message);
		
		labelPushProcessDao.updateSelective(map);
	}
	
	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.ILabelPushProcessService#queryWaybillNoForWaitingJobs()
	 */
	@Override
	@Transactional
	public int updateJobIdForWaitingJobs(String jobId, int maxCount){
		return labelPushProcessDao.updateJobIdForWaitingJobs(jobId, maxCount);
	}
	
	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.ILabelPushProcessService#rollbackWaitingJob()
	 */
	@Override
	@Transactional
	public void rollbackWaitingJob(String waybillNo) {
		Assert.isTrue(StringUtils.isNotEmpty(waybillNo), "waybillNo参数不可为空！");
		labelPushProcessDao.rollbackWaitingJob(waybillNo);
	}
	
	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.ILabelPushProcessService#retryFailedJob(java.lang.String)
	 */
	@Override
	@Transactional
	public void retryFailedJob(List<String> waybillNos){
		for(String waybillNo : waybillNos){
			if(StringUtils.isEmpty(waybillNo)){
				continue;
			}
			LabelPushProcessEntity entity =  labelPushProcessDao.queryByWaibillNo(waybillNo);
			if(WaybillConstants.LTL_EWAYBILL_LABELPUH_OPERATE_RESULT_SUCCESS.equals(entity.getOperateResult())){
				return;
			}
			
			invalidateExistingEntity(entity);
			
			this.insertLabelPushProcessEntity(entity.getOrderNo(), entity.getWaybillNo());
		}
	}
	
	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.ILabelPushProcessService#queryWaitingProcessWaybillNoByJobId(java.lang.String)
	 */
	@Override
	public List<LabelPushProcessEntity> queryWaitingProcessWaybillNoByJobId(String jobId){
		return labelPushProcessDao.queryWaitingProcessWaybillNoByJobId(jobId);
	}
	
	public void setLabelPushProcessDao(ILabelPushProcessDao labelPushProcessDao) {
		this.labelPushProcessDao = labelPushProcessDao;
	}
}

