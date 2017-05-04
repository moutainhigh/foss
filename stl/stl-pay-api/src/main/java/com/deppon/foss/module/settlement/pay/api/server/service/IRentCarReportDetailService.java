package com.deppon.foss.module.settlement.pay.api.server.service;

import java.util.List;

import com.deppon.foss.module.settlement.pay.api.shared.dto.RentCarReportDetailDto;

/**
 * 租车明细
 * @author foss-maojianqiang
 * @date 2014-08-07 上午11:16:40
 */
public interface IRentCarReportDetailService {

	/**
	 * 处理租车明细数据
	 * @author foss-maojianqiang
	 * @data 2014-08-07 下午5:56:49
	 */
	List<RentCarReportDetailDto> queryRentCarReportDetailDto(
			RentCarReportDetailDto rentCarReportDetailDto,int start,int limit);
	
	/**
	 * 处理租车明细数据的导出
	 * @author foss-maojianqiang
	 * @data 2014-08-07 下午5:57:49
	 */
	List<RentCarReportDetailDto> exportQueryRentCarReportDetailDto(
			RentCarReportDetailDto rentCarReportDetailDto);
	
	/**
	 * 处理租车明细数据的总数据
	 * @author foss-maojianqiang
	 * @data 2014-08-07 下午5:56:49
	 */
	int queryRentCarReportCount(RentCarReportDetailDto rentCarReportDetailDto);
}
