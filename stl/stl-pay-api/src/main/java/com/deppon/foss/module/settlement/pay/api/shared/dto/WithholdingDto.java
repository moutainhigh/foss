/*******************************************************************************
 * Copyright 2014 STL TEAM
 ******************************************************************************/

package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;

/**
 * 预提接口实体
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105762,date:2014-7-11 上午10:16:42,content:TODO </p>
 * @author 105762
 * @date 2014-7-11 上午10:16:42
 * @since 1.6
 * @version 1.0
 */
public class WithholdingDto implements Serializable {
	private String workflowNo;
	private boolean success;
	private String reason;

	/**
	  * @return  the workflowNo
	 */
	public String getWorkflowNo() {
		return workflowNo;
	}

	/**
	 * @param workflowNo the workflowNo to set
	 */
	public void setWorkflowNo(String workflowNo) {
		this.workflowNo = workflowNo;
	}

	/**
	  * @return  the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	  * @return  the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

}
