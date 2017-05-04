package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.pay.api.shared.dto.RentCarReportDto;

/**
 * @author 045738
 * 临时租车Dao
 */
public interface IRentCarReportDao {
	/**
	 * 功能：查询临时租车记录
	 * @author 045738-foss-maojianqiang
	 * @date 2014-6-17
	 * @return
	 */
	public List<RentCarReportDto> queryRentCarReport(RentCarReportDto dto ,int start, int limit);

	/**
	 * 功能：查询临时租车报表
	 * @author 045738-foss-maojianqiang
	 * @date 2014-6-27
	 * @return
	 */
	public RentCarReportDto queryRentCarReportCount(RentCarReportDto dto);
	
	/**
	 * 功能：查询临时租车记录
	 * @author 045738-foss-maojianqiang
	 * @date 2014-6-17
	 * @return
	 */
	public List<RentCarReportDto> queryRentCarReportForExport(RentCarReportDto dto );
	
	/**
	 * 功能：查询已经预提的租车记录
	 * 需求DN201704100011
	 * @author 045738-foss-maojianqiang
	 * @date 2014-7-23
	 * @return
	 */
	public List<RentCarReportDto> queryWithholdingedRentCar(RentCarReportDto dto);
	
	/**
	 * 功能：查询临时租车记录  提供给cubc使用
	 * @author 378375
	 * @date 2017年4月7日 16:12:26
	 * @return
	 */
	public List<RentCarReportDto> queryRentCarReportForCUBC(RentCarReportDto dto );
	
	
}
