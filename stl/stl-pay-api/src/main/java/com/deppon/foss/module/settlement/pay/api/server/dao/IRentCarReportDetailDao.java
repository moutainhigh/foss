package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.pay.api.shared.dto.RentCarReportDetailDto;
/**
 * 租车明细查询接口
 * @author foss-maojianqiang
 * @date 2014-08-07 上午11:16:40
 */
public interface IRentCarReportDetailDao {

	/**
	 * 
	 * 按参数分页查询租车明细信息
     * @author foss-maojianqiang
     * @date 2014-08-07 上午11:16:40
	 */
	List<RentCarReportDetailDto> queryRentCarReportDetailDto(RentCarReportDetailDto dto,int start, int limit);
    
	/**
     * 导出所有查询出的租车明细Dto
     * @author foss-maojianqiang
     * @param rentCarReportDetailDto
     * @date 2014-08-07
     */
	List<RentCarReportDetailDto> exportQueryRentCarReportDetailDto(
			RentCarReportDetailDto rentCarReportDetailDto);

	/**
	 * 查询出租车明细数据的总条数
	 * @author foss-maojianqiang
	 * @date 2014-08-07
	 */
	int queryRentCarReportCount(RentCarReportDetailDto rentCarReportDetailDto); 
}
