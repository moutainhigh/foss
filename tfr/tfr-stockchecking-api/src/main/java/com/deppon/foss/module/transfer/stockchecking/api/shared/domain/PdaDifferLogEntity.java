/**
 * 
 */
package com.deppon.foss.module.transfer.stockchecking.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * @author niuly
 * @function PDA处理差异报告实体
 */
public class PdaDifferLogEntity extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	//差异报告编号
	private String reportCode;
	//PDA处理状态
	private String status;
	//PDANO
	private String pdaNo;
	//操作人工号
	private String operatorCode;
	//创建时间
	private Date createTime;
	
	/**
	 * @return the operatorCode
	 */
	public String getOperatorCode() {
		return operatorCode;
	}
	/**
	 * @param operatorCode the operatorCode to set
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the pdaNo
	 */
	public String getPdaNo() {
		return pdaNo;
	}
	/**
	 * @param pdaNo the pdaNo to set
	 */
	public void setPdaNo(String pdaNo) {
		this.pdaNo = pdaNo;
	}
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
