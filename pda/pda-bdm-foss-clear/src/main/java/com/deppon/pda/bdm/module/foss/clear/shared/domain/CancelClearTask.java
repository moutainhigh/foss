package com.deppon.pda.bdm.module.foss.clear.shared.domain;

import java.util.Date;

/**
 * 撤销清仓任务
 * 
 * @author 徐俊
 * @date Sep 6,2012 14:33:30 PM
 * @version 1.0
 * @since
 */
public class CancelClearTask {

	/**
	 * 取消时间
	 */
	private Date cancelTime;
	
	/**
	 * 任务编号
	 */
	private String taskCode;

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

}