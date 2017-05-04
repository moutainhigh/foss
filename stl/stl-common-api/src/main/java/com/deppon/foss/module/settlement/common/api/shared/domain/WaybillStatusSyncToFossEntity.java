package com.deppon.foss.module.settlement.common.api.shared.domain;

public class WaybillStatusSyncToFossEntity {
	
	// 原运单ID
	private String oldWaybillId;
	// 原运单状态
	private String oldWaybillActive;
	// 新运单ID
	private String newWaybillId;
	// 新运单状态
	private String newWaybillActive;
	
	/**
	 * 是否成功
	 */
	private String isSuccess;
	
	/**
	 * 返回消息
	 */
	private String message;
	
	
	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the oldWaybillId
	 */
	public String getOldWaybillId() {
		return oldWaybillId;
	}

	/**
	 * @param oldWaybillId
	 *            the oldWaybillId to set
	 */
	public void setOldWaybillId(String oldWaybillId) {
		this.oldWaybillId = oldWaybillId;
	}

	/**
	 * @return the oldWaybillActive
	 */
	public String getOldWaybillActive() {
		return oldWaybillActive;
	}

	/**
	 * @param oldWaybillActive
	 *            the oldWaybillActive to set
	 */
	public void setOldWaybillActive(String oldWaybillActive) {
		this.oldWaybillActive = oldWaybillActive;
	}

	/**
	 * @return the newWaybillId
	 */
	public String getNewWaybillId() {
		return newWaybillId;
	}

	/**
	 * @param newWaybillId
	 *            the newWaybillId to set
	 */
	public void setNewWaybillId(String newWaybillId) {
		this.newWaybillId = newWaybillId;
	}

	/**
	 * @return the newWaybillActive
	 */
	public String getNewWaybillActive() {
		return newWaybillActive;
	}

	/**
	 * @param newWaybillActive
	 *            the newWaybillActive to set
	 */
	public void setNewWaybillActive(String newWaybillActive) {
		this.newWaybillActive = newWaybillActive;
	}

	@Override
	public String toString() {
		return "WaybillStatusSyncToFossEntity [oldWaybillId=" + oldWaybillId
				+ ", oldWaybillActive=" + oldWaybillActive + ", newWaybillId="
				+ newWaybillId + ", newWaybillActive=" + newWaybillActive
				+ ", isSuccess=" + isSuccess + ", message=" + message + "]";
	}
	

}
