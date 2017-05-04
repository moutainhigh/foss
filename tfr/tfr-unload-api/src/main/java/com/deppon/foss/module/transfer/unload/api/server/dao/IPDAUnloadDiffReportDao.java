package com.deppon.foss.module.transfer.unload.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAUnloadDiffReportDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAUnloadDiffReportDto;
import com.deppon.foss.module.transfer.unload.api.shared.domain.PDAUnloadDiffReportDetailLogEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.PDAUnloaddiffReportLogEntity;

/**
 * 
 * <p>处理PDA卸车差异报告</p> 
 * @author alfred
 * @date 2014-6-11 下午4:20:07
 * @see
 */
public interface IPDAUnloadDiffReportDao {
	/**
	 * 
	 * <p>PDA根据部门查询卸车差异</p> 
	 * @author alfred
	 * @date 2014-6-11 上午11:00:31
	 * @param queryCondition
	 * @param limit
	 * @param start
	 * @return
	 * @see
	 */
	List<PDAUnloadDiffReportDto> queryPDAUnloadDiffReportList(List<String> deptCodes,int hours);
	
	/**
	 * 
	 * <p>PDA根据卸车差异编号查询卸车差异明细</p> 
	 * @author alfred
	 * @date 2014-6-13 上午11:02:29
	 * @param reportCode
	 * @return
	 * @see
	 */
	List<PDAUnloadDiffReportDetailDto> queryPDAUnloadDiffDetailList(String reportCode);
	
	/**
	 * 
	 * <p>插入PDA处理卸车差异报告明细日志</p> 
	 * @author alfred
	 * @date 2014-6-13 下午3:10:54
	 * @param detailLogEntity
	 * @return
	 * @see
	 */
	int addPDAScanDetailLog(PDAUnloadDiffReportDetailLogEntity detailLogEntity);
	
	/**
	 * 
	 * <p>插入PDA处理卸车报告日志</p> 
	 * @author alfred
	 * @date 2014-6-13 下午3:12:04
	 * @param reportLogEntity
	 * @return
	 * @see
	 */
	int addPDAReportLog(PDAUnloaddiffReportLogEntity reportLogEntity);
	
	/**
	 * 
	 * <p>更新PDA处理状态</p> 
	 * @author alfred
	 * @date 2014-6-13 下午3:33:19
	 * @param reportCode
	 * @return
	 * @see
	 */
	int updateUnloadDiffReportHandleState(String reportCode);
	
	/**
	 *查询二程接驳卸车差异报告
	 * @author 205109-foss-zenghaibin
	 * @date 2015-05-24 13:26:30
	 * @param deptCode部门编码，operatorCode操作人
	 ***/
	List<PDAUnloadDiffReportDto>  querySCPDAUnloadDiffReportList(String deptCode,String operatorCode);
	
	/**
	 *查询二程接驳卸车差异报告
	 * @author 205109-foss-zenghaibin
	 * @date 2015-05-24 13:26:30
	 * @param deptCode部门编码，operatorCode操作人
	 ***/
	public List<PDAUnloadDiffReportDto>  querySCPDAUnloadDiffReportList(String deptCode,String operatorCode,
			String reportCode,Date queryDate);
	
	
	/**
	 *根据差异报告编号查询二程接驳卸车差异报告明细
	 * @author 205109-foss-zenghaibin
	 * @date 2015-05-24 13:26:30
	 * @param reportCode
	 ***/
	List<PDAUnloadDiffReportDetailDto> querySCPDAUnloadDiffReportDetailList(String reportCode,String operatorCode);

}
