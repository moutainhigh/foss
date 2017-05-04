/**
 * @author foss 257200
 * 2015-6-24
 * 257220
 */
package com.deppon.foss.module.transfer.unload.api.shared.domain;

import java.util.Date;

/**
 * @author 257220
 *
 */
public class BatchLoadingReportLogEntity {
	private String id;
	private String reportDataId;
	private Date reportTime;
	private String reportStatus;
	private String exception;
	private String returnResult;
	private String returnMessage;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReportDataId() {
		return reportDataId;
	}
	public void setReportDataId(String reportDataId) {
		this.reportDataId = reportDataId;
	}
	
	public Date getReportTime() {
		return reportTime;
	}
	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
	public String getReportStatus() {
		return reportStatus;
	}
	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public String getReturnResult() {
		return returnResult;
	}
	public void setReturnResult(String returnResult) {
		this.returnResult = returnResult;
	}
	public String getReturnMessage() {
		return returnMessage;
	}
	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}
	
}
