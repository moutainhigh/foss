package com.deppon.pda.bdm.module.foss.load.shared.domain;
/**
 * 
 * TODO(检验强装请求实体)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2013-3-20 下午6:22:05,content:TODO </p>
 * @author Administrator
 * @date 2013-3-20 下午6:22:05
 * @since
 * @version
 */
public class CheckForceLoad {
	/**
	 * 任务号
	 */
	private String taskCode;
	/**
	 * 运单号
	 */
	private String wblCode;
	/**
	 * 流水号
	 */
	private String serialNo;
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getWblCode() {
		return wblCode;
	}
	public void setWblCode(String wblCode) {
		this.wblCode = wblCode;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
}
