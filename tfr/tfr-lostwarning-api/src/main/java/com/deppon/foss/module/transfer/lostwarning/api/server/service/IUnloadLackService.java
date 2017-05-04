package com.deppon.foss.module.transfer.lostwarning.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.unload.api.shared.domain.LackGoodsFoundEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportEntity;

/** 
 * @className: IUnloadService
 * @author: WangWenbo 
 * @description: 卸车service
 * @date: 2016-6-4 下午8:53:20
 * 
 */
public interface IUnloadLackService extends IService {
	

	/**
	 * 上报卸车差异报告
	 * @author -foss-WangWenbo
	 * @date 2016-6-5 下午3:14:30
	 */
	int executeUnloadDiffReportTask(String reportId);
	
	/**
	 * 更新少货差异报告中的少货报告，同时新增一条少货找到记录(待找到后更新)
	 * @author -foss-WangWenbo
	 * @date 2013-7-2 下午2:57:31
	 */
	int updateOAErrorNoAndAddLackGoodsFoundInfo(List<UnloadDiffReportDetailEntity> lessGoodsList,UnloadDiffReportEntity diffEntity,String oaErrorNo,Date reportErrorTime);
	
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
	 * 新增一条卸车少货找到记录
	 * @author 045923-foss-shiwei
	 * @date 2013-7-2 下午2:59:40
	 */
	int addLackGoodsFoundInfo(LackGoodsFoundEntity entity);
}
