package com.deppon.foss.module.transfer.partialline.api.shared.domain;


/**
 * FOSS审核结果推送给DOP
 * 
 * @author 269701--LLN
 * @date 2015-12-16
 *
 */
public class FossToDopCheckResultResponse {
	/**
	 * 唯一请求Id：该请求Id为DOP推送给FOSS中转待审核信息时带的字段值，FOSS中转需将其返回！
	 */
	private String uniqueRequestId;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 审核状态（同意：Y;不同意：N）
	 */
	private String auditStatus;

	/**
	 * 审核意见
	 */
	private String auditOpinion;

	/**
	 * 审核操作时间
	 */
	private String auditTime;

	/**
	 * 审核操作人
	 */
	private String auditBy;

	/**
	 * @return the uniqueRequestId
	 */
	public String getUniqueRequestId() {
		return uniqueRequestId;
	}

	/**
	 * @param uniqueRequestId the uniqueRequestId to set
	 */
	public void setUniqueRequestId(String uniqueRequestId) {
		this.uniqueRequestId = uniqueRequestId;
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
	 * @return the auditStatus
	 */
	public String getAuditStatus() {
		return auditStatus;
	}

	/**
	 * @param auditStatus the auditStatus to set
	 */
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	/**
	 * @return the auditOpinion
	 */
	public String getAuditOpinion() {
		return auditOpinion;
	}

	/**
	 * @param auditOpinion the auditOpinion to set
	 */
	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}



	/**
	 * @return the auditTime
	 */
	public String getAuditTime() {
		return auditTime;
	}

	/**
	 * @param auditTime the auditTime to set
	 */
	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	/**
	 * @return the auditBy
	 */
	public String getAuditBy() {
		return auditBy;
	}

	/**
	 * @param auditBy the auditBy to set
	 */
	public void setAuditBy(String auditBy) {
		this.auditBy = auditBy;
	}
	
}
