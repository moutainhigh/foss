package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;

/**
 * 预提接口实体
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-16 下午8:30:15
 */
public class SubmitRefundDto implements Serializable {
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 4176177050724444179L;
	
	// 预提工作流对应的付款工作流号
	private String workflowNo;
	// 预提是否成功
	private String isSuccess;
	

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

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

}
