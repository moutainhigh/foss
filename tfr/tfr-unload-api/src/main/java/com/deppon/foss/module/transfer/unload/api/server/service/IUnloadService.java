package com.deppon.foss.module.transfer.unload.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.unload.api.shared.domain.LackGoodsFoundEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.ReportQMSDataEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadSerialDetaiDto;

/** 
 * @className: IUnloadService
 * @author: ShiWei shiwei@outlook.com
 * @description: 卸车service
 * @date: 2013-6-4 下午8:53:20
 * 
 */
public interface IUnloadService extends IService {
	
	/**
	 * 保存卸车差异报告
	 * @author 045923-foss-shiwei
	 * @date 2013-6-4 下午8:54:58
	 */
	int addUnloadDiffReportBasicInfoAndDetail(UnloadDiffReportEntity baseEntity,List<UnloadDiffReportDetailEntity> serialNoList);

	/**
	 * 上报卸车差异报告
	 * @author 045923-foss-shiwei
	 * @date 2013-6-5 下午3:14:30
	 */
	int executeUnloadDiffReportTask(String reportId);
	
	/**
	 * 新增一条卸车少货找到记录
	 * @author 045923-foss-shiwei
	 * @date 2013-7-2 下午2:59:40
	 */
	int addLackGoodsFoundInfo(LackGoodsFoundEntity entity);
	
	/**
	 * 更新少货差异报告中的少货报告，同时新增一条少货找到记录(待找到后更新)
	 * @author 045923-foss-shiwei
	 * @date 2013-7-2 下午2:57:31
	 */
	int updateOAErrorNoAndAddLackGoodsFoundInfo(List<UnloadDiffReportDetailEntity> lessGoodsList,UnloadDiffReportEntity diffEntity,String oaErrorNo,Date reportErrorTime);
	
	/**
	 * 获取少货未找到的，某类型(清仓、卸车)少货差错单编号
	 * @author 045923-foss-shiwei
	 * @date 2013-7-3 下午3:11:46
	 */
	List<String> queryNoFoundLackGoodsOAErrorNo(String reportType,Date reportOATime);
	
	/**
	 * 查询某少货差错中未找到的流水号
	 * @author 045923-foss-shiwei
	 * @date 2013-7-6 上午10:53:43
	 */
	List<LackGoodsFoundEntity> queryNoFoundLackGoodsDetailByOAErrorNo(String oaErrorNo);
	
	/**
	 * 少货找到后，更新相关字段，参数实体中需包含id
	 * @author 045923-foss-shiwei
	 * @date 2013-7-3 下午3:31:24
	 */
	int updateUnloadLackGoodsInfo(LackGoodsFoundEntity entity);

	/**
	 * @author niuly
	 * @date 2014-6-9下午4:39:39
	 * @function 保存上报OA差错失败日志
	 * @param reportCode
	 * @param waybillNo
	 * @param reportType
	 * @param differType
	 * @param message
	 */
	void addReportOaErrorLog(String reportCode, String waybillNo, String reportType, String differType, String message);

	/**
	 * <p>通过oa差错编号查询存在于少货找到上报记录表中的少货差错详情</p> 
	 * @author 148246-foss-sunjianbo
	 * @date 2014-10-21 下午7:58:01
	 * @param oaErrorNo
	 * @return
	 * @see
	 */
	List<LackGoodsFoundEntity> queryLackGoodsDetailByOAErrorNo(String oaErrorNo);
	
	/**
	 * 上报卸车少货 
	 * 为卸车丢货数据上报QMS做数据准备
	 * @author 283244
	 * **/
	 //int executeUnloadDiffReportToQMSTask(String reportId);
	 
	 int executeUnloadDiffReportToQMS(ReportQMSDataEntity reportQMSDataEntity);
	 /**通过运单号查询所有卸车流水信息(流水、时间)*/
	 List<UnloadSerialDetaiDto> queryUnloadSerialDetailByWaybillNo(String waybillNo);
}
