/**
 * 
 */
package com.deppon.foss.module.transfer.stockchecking.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * @author niuly
 * @function Pda处理清仓差异操作人
 */
public class PdaDifferOperatorEntity extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	//差异报告编号
	private String reportCode;
	//操作人工号
	private String operatorCode;
	//操作人姓名
	private String operatorName;
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
	 * @return the operatorName
	 */
	public String getOperatorName() {
		return operatorName;
	}
	/**
	 * @param operatorName the operatorName to set
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	
}
