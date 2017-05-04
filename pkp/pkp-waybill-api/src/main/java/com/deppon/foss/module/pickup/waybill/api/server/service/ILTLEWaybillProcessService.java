package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillProcessEntity;

/**
 * 零担电子面单线程处理相关Service
 *
 * @author 325220-foss-liuhui
 * @date 2016年5月10日
 */
public interface ILTLEWaybillProcessService extends IService {

	/**
	 * 根据jobId查询待激活处理运单的任务记录
	 * 
	 * @param jobId
	 * @return
	 */
	List<WaybillProcessEntity> queryWaybillProcessListByJobId(String jobId);

	/**
	 * 更新一批待激活处理运单的任务数据
	 * 
	 * @param map
	 * @return
	 */
	int updateForJob(Map<String, Object> map);
	
	/**
	 * @description 保存激活线程,
	 * @param WaybillProcessEntity
	 * @return
	 */
	public int addWaybillProcessEntity(String waybillNo,String orderNo,String processType);

	/**
	 * 当激活失败时候调用此方法去掉激活线程的唯一性
	 * @param waybillNo 运单号
	 * @param newSecondKey 和运单号为组合主键，可以将JOBID作为新的secondKey（添加激活线程时secondKey默认值为Y）
	 */
	public int updateWaybillProcessSecondKey(String waybillNo,String newSecondKey);

	public int updateWaybillProcessByJobId(Map<String, Object> map);
}
