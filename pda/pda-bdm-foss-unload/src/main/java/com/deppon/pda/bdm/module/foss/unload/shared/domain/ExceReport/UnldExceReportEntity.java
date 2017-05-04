package com.deppon.pda.bdm.module.foss.unload.shared.domain.ExceReport;

import java.util.Date;


/**
 * 查询差异报告明细
 * @author 092038
 *
 */
public class UnldExceReportEntity {
	 /**
	  * 差异报告编码
	  */
    private String reportCode;
    
    /**
     * 时间
     */
    private Date diffTime;
	/***
	   * 操作标识
	  */
	private String handInputFlg;
	
	public Date getDiffTime() {
		return diffTime;
	}
	public void setDiffTime(Date diffTime) {
		this.diffTime = diffTime;
	}
	public String getReportCode() {
		return reportCode;
	}
	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}
	public String getHandInputFlg() {
		return handInputFlg;
	}
	public void setHandInputFlg(String handInputFlg) {
		this.handInputFlg = handInputFlg;
	}
	
}
