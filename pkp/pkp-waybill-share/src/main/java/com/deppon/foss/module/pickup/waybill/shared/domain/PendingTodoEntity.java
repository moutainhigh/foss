/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.shared.domain;

/**
 * 异步产生代办entity
 * @author 026123-foss-lifengteng
 *
 */
public class PendingTodoEntity {

	/**
	 * 编号
	 */
	private String id;
	/**
	 * 任务编号
	 */
    private String jobId;
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 更改单id
	 */
	private String waybillRfcId;
	
	/**
	 * 自动生成代办如果失败，这里写失败原因
	 */
	private String failReason;
	

	/**
	 * @return the failReason
	 */
	public String getFailReason() {
		return failReason;
	}

	/**
	 * @param failReason the failReason to set
	 */
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the jobId
	 */
	public String getJobId() {
		return jobId;
	}

	/**
	 * @param jobId the jobId to set
	 */
	public void setJobId(String jobId) {
		this.jobId = jobId;
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
	 * @return the waybillRfcId
	 */
	public String getWaybillRfcId() {
		return waybillRfcId;
	}

	/**
	 * @param waybillRfcId the waybillRfcId to set
	 */
	public void setWaybillRfcId(String waybillRfcId) {
		this.waybillRfcId = waybillRfcId;
	}
	
	
}
