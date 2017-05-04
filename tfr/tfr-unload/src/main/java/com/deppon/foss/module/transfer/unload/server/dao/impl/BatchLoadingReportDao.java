/**
 * @author foss 257200
 * 2015-6-18
 * 257220
 */
package com.deppon.foss.module.transfer.unload.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.common.api.shared.define.QmsErrorConstants;
import com.deppon.foss.module.transfer.unload.api.server.dao.IBatchLoadingReportDao;
import com.deppon.foss.module.transfer.unload.api.shared.domain.BatchLoadingReportDataEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.BatchLoadingReportLogEntity;

/**
 * @author 257220
 *
 */
public class BatchLoadingReportDao extends iBatis3DaoImpl implements IBatchLoadingReportDao{
	
	private static final String NAMESPACE = "tfr.unload.batchloading.";
	
	/**
	 * 获取尚未生成分批配载差错的数据
	 * @return
	 * @author 257220
	 * @date 2015-7-11上午9:54:31
	 */
	@SuppressWarnings("unchecked")
	public List<BatchLoadingReportDataEntity> queryUnresolvedBatchLoadingData(Map<String,Object>parameterMap) {
		return this.getSqlSession().selectList(NAMESPACE+"queryUnresolvedBatchLoading",parameterMap);
	}
	/**
	 *  添加分批配载上报数据
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IBatchLoadingReportDao#addbatchLoadingReport(com.deppon.foss.module.transfer.unload.api.shared.domain.BatchLoadingReportDetailEntity)
	 */
	public void addbatchLoadingReport(
			BatchLoadingReportDataEntity batchLoadingReportDataEntity) {
		this.getSqlSession().insert(NAMESPACE+"insertBatchLoadingReport",batchLoadingReportDataEntity);
	}

	/**
	 *  插入分批配载上报日志
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IBatchLoadingReportDao#addBatchLoadingReportLog(com.deppon.foss.module.transfer.unload.api.shared.domain.BatchLoadingReportLogEntity)
	 */
	@Override
	public int addBatchLoadingReportLog(
			BatchLoadingReportLogEntity batchLoadingReportLogEntity) {
		return this.getSqlSession().insert(NAMESPACE+"insertBatchLoadingReportLog",batchLoadingReportLogEntity);
	}

	/** 
	 * 更新分批配载上报数据
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IBatchLoadingReportDao#updateBatchLoadingReportData(com.deppon.foss.module.transfer.unload.api.shared.domain.BatchLoadingReportDetailEntity)
	 */
	@Override
	public int updateBatchLoadingReportData(
			BatchLoadingReportDataEntity batchLoadingReportDetailEntity) {
		return this.getSqlSession().update(NAMESPACE+"updateBatchLoadingReportData",batchLoadingReportDetailEntity);
	}

	/**
	 * 获取所有未上报的数据
	 * 时间约束：提交时间后8小时
	 * (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IBatchLoadingReportDao#queryUnreportedBatchLoadingData()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BatchLoadingReportDataEntity> queryUnreportedBatchLoadingData() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("hours", QmsErrorConstants.SUBMIT_HOURS);
		return this.getSqlSession().selectList(NAMESPACE+"queryUnreportedBatchLoadingData",map);
	}
	/**
	 * 获取所有未上报的数据
	 * (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IBatchLoadingReportDao#queryUnreportedBatchLoadingData()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BatchLoadingReportDataEntity> queryUnreportedBatchLoadingData(Map<String,Object>map) {
		return this.getSqlSession().selectList(NAMESPACE+"queryUnreportedBatchLoadingData",map);
	}
	/**
	 * 根据运单号获取首次分批配载卸车任务id
	 */
	@SuppressWarnings("unchecked")
	public String getFirstBatchLoadingTaskId(String waybillNo) {
		List<String> list =  this.getSqlSession().selectList(NAMESPACE+"getFirstBatchLoadingTaskId", waybillNo);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 根据卸车任务和运单获取装车人
	 */
	@SuppressWarnings("unchecked")
	public Map<String,String> getLoaderByUnloadTaskIdWaybillNo(String unloadTaskId, String waybillNo){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("unloadTaskId", unloadTaskId);
		map.put("waybillNo", waybillNo);
		List<Map<String,String>> loaderList = this.getSqlSession().selectList(NAMESPACE+"getLoaderByUnloadTaskIdWaybillNo", map);
		if(loaderList != null && loaderList.size() > 0){
			return loaderList.get(0);
		}
		return null;
	}
	/**
	 * <p>获取分批配载流水号</p>
	 * @param unloadTaskId 卸车任务id
	 * @param waybillNo 运单号
	 * @return
	 * @author pfzheng
	 * @date 2015-7-17下午3:21:27
	 */
	@SuppressWarnings("unchecked")
	public List<String> getBatchLoadingSerials(String unloadTaskId,String waybillNo){
		Map<String,String> map = new HashMap<String,String>();
		map.put("unloadTaskId", unloadTaskId);
		map.put("waybillNo", waybillNo);
		List<String> serials = this.getSqlSession().selectList(NAMESPACE+"getUnloadSerialsByUnloadTaskIDWaybillNo", map);
		return serials;
	}
}
