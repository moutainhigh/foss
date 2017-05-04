package com.deppon.foss.module.transfer.stock.api.server.service;

import java.util.List;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.SalesDeptExpLostEntity;
import com.deppon.foss.module.transfer.stock.api.shared.dto.SalesDeptExpLostWaybillSerialNosDto;

public interface ISalesDeptExpLostService extends IService {

	/**
	 * @desc 保存0点时的库存快照，目前只保留营业部的快递货
	 * @date 2014年10月24日 上午10:49:04
	 * @author Ouyang
	 */
	void saveStock0amSnapshot();

	/**
	 * @desc 生成差异报告
	 * @param threadCount
	 * @param threadNo
	 * @date 2014年10月27日 上午10:04:38
	 * @author Ouyang
	 */
	void generateReport(int threadCount, int threadNo);

	/**
	 * @desc 查询差异报告
	 * @param info
	 * @param start
	 * @param limit
	 * @return
	 * @date 2014年10月27日 上午10:04:48
	 * @author Ouyang
	 */
	List<SalesDeptExpLostEntity> queryReportWaybillNo(
			SalesDeptExpLostEntity info);

	/**
	 * @desc 分页查询差异报告
	 * @param info
	 * @param start
	 * @param limit
	 * @return
	 * @date 2014年10月27日 上午10:04:48
	 * @author Ouyang
	 */
	List<SalesDeptExpLostEntity> queryReportWaybillNoPaging(
			SalesDeptExpLostEntity info, int start, int limit);

	/**
	 * @desc 查询报告数量
	 * @param info
	 * @return
	 * @date 2014年11月19日 上午10:27:50
	 * @author Ouyang
	 */
	Long queryReportWaybillNoCnt(SalesDeptExpLostEntity info);

	/**
	 * @desc 查询对应运单的流水号
	 * @param info
	 * @return
	 * @date 2014年10月27日 上午10:31:04
	 * @author Ouyang
	 */
	List<SalesDeptExpLostWaybillSerialNosDto> queryReportSerialNo(
			SalesDeptExpLostEntity info);

	/**
	 * @desc 导出差异报告
	 * @param info
	 * @return
	 * @date 2014年10月27日 上午10:04:56
	 * @author Ouyang
	 */
	ExportResource exportReport(SalesDeptExpLostEntity info);
}
