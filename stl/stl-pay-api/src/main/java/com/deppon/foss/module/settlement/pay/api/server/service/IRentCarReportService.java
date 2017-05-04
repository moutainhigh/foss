package com.deppon.foss.module.settlement.pay.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.RentCarReportDto;
import com.deppon.foss.module.settlement.pay.api.shared.vo.RentCarReportVo;

/**
 * @author 045738
 * 临时租车service
 */
public interface IRentCarReportService extends IService {
	
	/**
	 * 功能：查询临时租车记录
	 * @author 045738-foss-maojianqiang
	 * @date 2014-6-17
	 * @return
	 */
	public List<RentCarReportDto> queryRentCarReport(RentCarReportDto dto,CurrentInfo info ,int start, int limit);
	
	/**
	 * 功能：查询临时租车报表汇总记录
	 * @author 045738-foss-maojianqiang
	 * @date 2014-6-27
	 * @return
	 */
	public RentCarReportDto queryRentCarReportCount(RentCarReportDto dto,CurrentInfo cInfo) ;
	
	/**
	 * 功能：取消租车标记
	 * @author 045738-foss-maojianqiang
	 * @date 2014-7-14
	 * @return
	 */
	public void disableRentCar(RentCarReportDto dto,CurrentInfo cInfo);
	
	/**
	 * 功能：导出临时租车
	 * @author 045738-foss-maojianqiang
	 * @date 2014-7-15
	 * @return
	 */
	public List<RentCarReportDto> queryRentCarReportForExport(RentCarReportDto dto,CurrentInfo info);

	/**
	 * 功能：预提
	 * @author 045738-foss-maojianqiang
	 * @date 2014-7-17
	 * @return
	 */
	public RentCarReportVo withholding(RentCarReportDto dto,CurrentInfo cInfo);
	
	/**
	 * 功能：保存预提
	 * @author 045738-foss-maojianqiang
	 * @date 2014-7-17
	 * @return
	 */
	public RentCarReportVo saveWithholding(RentCarReportVo vo,CurrentInfo cInfo);
	
	/**
	 * 功能：更新应付单版本号--控制并发操作
	 * @author 045738-foss-maojianqiang
	 * @date 2014-11-4
	 * @return
	 */
	public void updateWorkFlowNoByPayNo(List<BillPayableEntity> list, String workFolwNo,CurrentInfo cInfo);

	/**
	 * 功能：当更新报账预提不成功时，版本号-1
	 * @author 045738-foss-maojianqiang
	 * @date 2014-11-4
	 * @return
	*/
	public void updatePayableVersion(List<BillPayableEntity> list, CurrentInfo cInfo);
	
	/**
	 * 功能：查询临时租车记录
	 * 需求DN201704100011
	 * @author 378375
	 * @date 2017年4月7日 16:54:48
	 * @param dto
	 * @return
	 */
	public List<RentCarReportDto> queryRentCarReportForCUBC(RentCarReportDto dto);
}
