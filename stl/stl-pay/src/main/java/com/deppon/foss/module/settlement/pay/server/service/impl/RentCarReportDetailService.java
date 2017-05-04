package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.util.List;

import com.deppon.foss.module.settlement.pay.api.server.dao.IRentCarReportDetailDao;
import com.deppon.foss.module.settlement.pay.api.server.service.IRentCarReportDetailService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.RentCarReportDetailDto;


/**
 * 租车明细查询service接口实现
 * 
 * @author foss-maojianqiang
 * @date 2014-07-29 12:37:21
 */
public class RentCarReportDetailService implements IRentCarReportDetailService {
	
	/**
	 * 租车明细查询dao的注入
	 */
	private IRentCarReportDetailDao rentCarReportDetailDao;
	
	/**
	 * 分页查询租车明细数据
	 * 
	 * @author foss-maojianqiang
	 * @date 2014-07-29 12:37:21
	 */
	@Override
	public List<RentCarReportDetailDto> queryRentCarReportDetailDto(
			RentCarReportDetailDto rentCarReportDetailDto, int start, int limit) {
		//返回查询的租车明细数据集合     
		return rentCarReportDetailDao.queryRentCarReportDetailDto(rentCarReportDetailDto, start, limit);
	}
	
	/**
	 * 导出查询出的租车明细数据
	 * 
	 * @author foss-maojianqiang
	 * @date 2014-07-29 12:37:21
	 */
	@Override
	public List<RentCarReportDetailDto> exportQueryRentCarReportDetailDto(
			RentCarReportDetailDto rentCarReportDetailDto) {
		//返回查询的租车明细数据集合  
		return rentCarReportDetailDao.exportQueryRentCarReportDetailDto(rentCarReportDetailDto);
	}
	
	/**
	 * 查询租车明细数据的总条数
	 * 
	 * @author foss-maojianqiang
	 * @date 2014-07-29 12:37:21
	 */
	@Override
	public int queryRentCarReportCount(
			RentCarReportDetailDto rentCarReportDetailDto) {
		//返回查询的租车明细数据集合的总条数  
		return rentCarReportDetailDao.queryRentCarReportCount(rentCarReportDetailDto);
	}

	public void setRentCarReportDetailDao(
			IRentCarReportDetailDao rentCarReportDetailDao) {
		this.rentCarReportDetailDao = rentCarReportDetailDao;
	}
}
