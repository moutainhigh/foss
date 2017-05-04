package com.deppon.foss.module.transfer.pda.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAUnloadDiffReportDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAUnloadDiffReportDto;

public interface IPDAUnloadDiffReportService {
	List<PDAUnloadDiffReportDto>  queryPDAUnloadDiffReportList(String deptCode,String operatorCode,String pdaNo);
	List<PDAUnloadDiffReportDetailDto> queryPDAUnloadDiffReportDetailList(String reportCode,String operatorCode,
			String pdaNo,String handInputFlg,String deptCode);
	int handleUnloadDiffReport(String deptCode,String reportCode,String waybillNo,String serialNo,
			Date scanTime,String operatorCode,String pdaNo);
	int commitUnloadDiffReport(String reportCode,String operatorCode,String pdaNo);
	
	/**
	 *查询司机当天二程接驳卸车差异报告
	 * @author 205109-foss-zenghaibin
	 * @date 2015-05-24 13:26:30
	 * @param deptCode部门编码，operatorCode操作人
	 ***/
	List<PDAUnloadDiffReportDto>  querySCPDAUnloadDiffReportList(String deptCode,String operatorCode);

	/**
	 *查询二程接驳卸车差异报告
	 * @author 205109-foss-zenghaibin
	 * @date 2015-05-24 13:26:30
	 * @param deptCode部门编码，operatorCode操作人,reportCode 差异报告编码,queryDate 查询日期
	 ***/
	List<PDAUnloadDiffReportDto>  querySCPDAUnloadDiffReportList(String deptCode,String operatorCode,
			String reportCode,Date queryDate);

	/**
	 *根据差异报告编号查询二程接驳卸车差异报告明细
	 * @author 205109-foss-zenghaibin
	 * @date 2015-05-24 13:26:30
	 * @param reportCode
	 ***/
	List<PDAUnloadDiffReportDetailDto> querySCPDAUnloadDiffReportDetailList(String reportCode,String operatorCode);
	
	/**
	 *二程接驳卸车差异明细扫描处理
	 *@date 2015-05-25 11:05:20	 
	 *@author 205109 foss zenghaibin
	 *@param deptCode 部门编码 
	 *@param reportCode 差异报告编码
	 *@param waybillno 运单好
	 *@param serialNo 流水号
	 *@param Scantim 扫描时间
	 *@param operatorCode 操作员工号
	 *@param pdaNo 设备编号
	 ***/
	int handleSCUnloadDiffReport(String deptCode,String reportCode,String waybillNo,String serialNo,
			Date scanTime,String operatorCode,String pdaNo);
}
