/**
 * 
 */
package com.deppon.foss.module.transfer.unload.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * @author niuly
 * @function 清仓上报OA少货或多货差错失败日志
 */
public class ReportOaErrorLogEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;
	//差错报告
	private String reportCode;
	//运单号
	private String waybillNo;
	//差异报告类型
	private String reportType;
	//差异类型
	private String differType;
	//失败原因
	private String remark;
	
	/**
	 * @return the reportCode
	 */
	public String getReportCode() {
		return reportCode;
	}
	/**
	 * @param reportCode the reportCode to set
	 */
	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}
	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	/**
	 * @return the reportType
	 */
	public String getReportType() {
		return reportType;
	}
	/**
	 * @param reportType the reportType to set
	 */
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	/**
	 * @return the differType
	 */
	public String getDifferType() {
		return differType;
	}
	/**
	 * @param differType the differType to set
	 */
	public void setDifferType(String differType) {
		this.differType = differType;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
