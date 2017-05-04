package com.deppon.foss.module.transfer.stockchecking.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StDifferDetailEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.LackGoodsFoundEntity;

public interface ITaskReportService extends IService{
	
	/**
	 * 增加清仓差异报告
	 * @date 2013-6-5 下午3:47:16
	 */
	void addStTaskReport(StTaskEntity stTask);

	/**
	 * <pre>
	 * 自动处理清仓差异报告明细中，未处理的数据且未做OA少货上报的数据 
	 * 1、已经签收出库的，标记为已处理
	 * 2、在明细的差异报告生成时间之后，存在有入库历史数据的
	 * </pre>
	 * @date 2013-7-5 下午3:47:16
	 * @param differEntity
	 * 
	 * @author foss-wuyingjie
	 */
	void stReportGapRepair(StDifferDetailEntity differEntity);
	
	/**
	 * <pre>
	 * 自动处理清仓差异报告明细中，未处理的数据且已做OA少货上报的数据
	 * 
	 * </pre>
	 * @param lackGoodsFoundEntity
	 * 
	 * @author foss-wuyingjie
	 */
	void stReportGapRepairNoStock(LackGoodsFoundEntity lackGoodsFoundEntity);

}
