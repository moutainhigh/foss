package com.deppon.pda.bdm.module.foss.clear.shared.domain;

/**
 * 提交找货任务
 * @author 245955
 *
 */
public class FindGoodsAdminSubmitEntity {

	/**
	 * 任务号
	 */
	private String taskCode;
	/**
	 * 操作人
	 */
	private String user;
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
}
