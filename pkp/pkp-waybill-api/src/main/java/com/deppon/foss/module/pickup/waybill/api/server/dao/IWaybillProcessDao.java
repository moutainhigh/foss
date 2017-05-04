package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillProcessEntity;

/**
 * 零担电子面单激活处理DAO
 *
 * @author 325220-foss-liuhui
 * @date 2016年5月14日
 */
public interface IWaybillProcessDao {

	/**
	 * 通过设置的JOBID查询待激活运单数据
	 * 
	 * @param jobId
	 * @return
	 */
	List<WaybillProcessEntity> queryWaybillProcessListByJobId(String jobId);

	/**
	 * 更新一批待激活运单Job数据
	 * 
	 * @param map
	 * @return
	 */
	int updateForJob(Map<String, Object> map);

	/**
	 * 插入待激活运单Job数据
	 * @param waybillProcessEntity
	 * @return
	 */
	int addWaybillProcessEntity(WaybillProcessEntity waybillProcessEntity);
	
	/**
	 * 更新运单激活job表数据(同时更新SecondKey)
	 * @param waybillProcessEntity
	 * @param newSecondKey
	 * @param oldSecondKey
	 * @return
	 */
	int updateWaybillProcess(WaybillProcessEntity waybillProcessEntity,String newSecondKey,String oldSecondKey);
	/**
	 * @description 更新SecondKey为Y的记录
	 * @param waybillNo
	 * @param newSecondKey
	 * @return
	 */
	public int updateWaybillProcessSecondKey(String waybillNo,String newSecondKey);
	
	/**
	 * @description 查询
	 * @param waybillNo
	 * @return
	 */
	public WaybillProcessEntity queryNotFinishedByWaybillNo(String waybillNo);
	
	/**
	 * @description 强制解锁
	 */
	public int unlockedById(String id);
	
	public int updateWaybillProcessByJobId(Map<String, Object> map);
}
