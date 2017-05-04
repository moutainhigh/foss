package com.deppon.foss.module.settlement.agency.api.server.service;

import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.agency.api.shared.dto.AgencySystemReportQueryDto;
import com.deppon.foss.module.settlement.agency.api.shared.dto.AgencySystemReportResultDto;

/**
 * 偏线全盘报表Service接口
 * 
 * @author foss-zhangxiaohui
 * @date Dec 25, 2012 3:31:48 PM
 */
public interface IAgencySystemReportQueryService {

	/**
	 * 根据运单单号查询偏线全盘报表
	 * 
	 * @author foss-zhangxiaohui
	 * @param 
	 * @date Dec 26, 2012 4:39:43 PM
	 * @return 
	 */
	List<AgencySystemReportResultDto> querAgencySystemReportByWayBillNo(AgencySystemReportQueryDto dto,CurrentInfo cInfo);
	
	/**
	 * 根据Dto查询偏线全盘报表
	 * 
	 * @author foss-zhangxiaohui
	 * @param 
	 * @date Dec 25, 2012 3:33:34 PM
	 * @return 
	 */
	List<AgencySystemReportResultDto> querAgencySystemReportByDto(int offset,int start,AgencySystemReportQueryDto dto,CurrentInfo cInfo);
	
	/**
	 * 根据Dto查询总记录条数
	 * 
	 * @author foss-zhangxiaohui
	 * @param 
	 * @date Dec 25, 2012 3:37:34 PM
	 * @return 
	 */
	AgencySystemReportResultDto queryTotalRecordsInDBByDto(AgencySystemReportQueryDto dto,CurrentInfo cInfo);
}
