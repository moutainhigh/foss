package com.deppon.foss.module.settlement.agency.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.agency.api.shared.dto.AgencySystemReportQueryDto;
import com.deppon.foss.module.settlement.agency.api.shared.dto.AgencySystemReportResultDto;

/**
 * 偏线全盘报表Dao接口
 * 
 * @author foss-zhangxiaohui
 * @date Dec 25, 2012 3:38:35 PM
 */
public interface IAgencySystemReportQueryDao {

	/**
	 * 根据运单单号查询偏线全盘报表
	 * @author foss-zhangxiaohui
	 * @param 
	 * @date Dec 26, 2012 4:40:32 PM
	 * @return 
	 */
	List<AgencySystemReportResultDto> querAgencySystemReportByWayBillNo(AgencySystemReportQueryDto dto);
	
	/**
	 * 根据Dto查询偏线全盘报表
	 * 
	 * @author foss-zhangxiaohui
	 * @param 
	 * @date Dec 25, 2012 3:40:24 PM
	 * @return 
	 */
	List<AgencySystemReportResultDto> querAgencySystemReportByDto(int offset,int start,AgencySystemReportQueryDto dto);
	
	/**
	 * 根据Dto查询总记录条数
	 * 
	 * @author foss-zhangxiaohui
	 * @param 
	 * @date Dec 25, 2012 3:40:37 PM
	 * @return 
	 */
	AgencySystemReportResultDto queryTotalRecordsInDBByDto(AgencySystemReportQueryDto dto);
}
