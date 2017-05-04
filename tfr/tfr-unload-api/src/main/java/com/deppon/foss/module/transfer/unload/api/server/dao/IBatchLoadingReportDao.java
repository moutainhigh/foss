/**
 * @author foss 257200
 * 2015-6-18
 * 257220
 */
package com.deppon.foss.module.transfer.unload.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.unload.api.shared.domain.BatchLoadingReportDataEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.BatchLoadingReportLogEntity;

/**
 * @author 257220
 *
 */
public interface IBatchLoadingReportDao {
	/**
	 * 新增上报数据
	 * @param 
	 * @return
	 * @author 257220
	 * @date 2015-7-11上午9:54:31
	 */
	void addbatchLoadingReport(BatchLoadingReportDataEntity batchLoadingReportDataEntity);
	
	/**
	 * 添加上报日志
	 * @param batchLoadingReportLogEntity
	 * @return
	 * @author 257220
	 * @date 2015-6-24上午10:44:05
	 */
	int addBatchLoadingReportLog(BatchLoadingReportLogEntity batchLoadingReportLogEntity);
	/**
	 * 更新分批配载上报数据
	 * @param batchLoadingReportDetailEntity
	 * @author 257220
	 * @date 2015-6-24下午4:36:40
	 */
	int updateBatchLoadingReportData(BatchLoadingReportDataEntity batchLoadingReportDataEntity);

	/**
	 * 查询未上报的数据
	 * @return
	 * @author 257220
	 * @date 2015-6-24下午5:12:44
	 */
	List<BatchLoadingReportDataEntity> queryUnreportedBatchLoadingData();

	/**
	 * @param waybillNo
	 * @return 首次分批配载卸车任务id
	 * @author 257220
	 * @date 2015-6-30上午8:23:10
	 */
	String getFirstBatchLoadingTaskId(String waybillNo);

	/**
	 * 查询装车人
	 * @param unloadTaskId
	 * @param waybillNo
	 * @author 257220
	 * @date 2015-7-2下午1:59:16
	 */
	Map<String,String> getLoaderByUnloadTaskIdWaybillNo(String unloadTaskId, String waybillNo);
	
	/**
	 * 获取分批配载流水号
	 * @param unloadTaskId 卸车任务id
	 * @param waybillNo 运单号
	 * @return
	 * @author 257220
	 * @date 2015-7-17下午3:21:27
	 */
	List<String> getBatchLoadingSerials(String unloadTaskId,String waybillNo);
	
	/**
	 * 查询未上报分批配载的数据
	 */
	List<BatchLoadingReportDataEntity> queryUnreportedBatchLoadingData(Map<String, Object> map);
	/**
	 * 获取尚未生成分批配载差错的数据
	 * @param parameterMap
	 * @return
	 */
	List<BatchLoadingReportDataEntity> queryUnresolvedBatchLoadingData(Map<String,Object>parameterMap);
}
