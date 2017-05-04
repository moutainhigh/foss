package com.deppon.pda.bdm.module.foss.unload.shared.domain;
/**
 * 
 * TODO(统计叉车扫描返回实体)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2014-1-13 上午9:28:35,content:TODO </p>
 * @author Administrator
 * @date 2014-1-13 上午9:28:35
 * @since
 * @version
 */
public class CountPalletBoundResult {
	/**
	 * 任务号
	 */
	private String taskCode;
	/**
	 * 票数
	 */
	private int nVote;
	/**
	 * 扫描时间
	 */
	private String scanTime;
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public int getnVote() {
		return nVote;
	}
	public void setnVote(int nVote) {
		this.nVote = nVote;
	}
	public String getScanTime() {
		return scanTime;
	}
	public void setScanTime(String scanTime) {
		this.scanTime = scanTime;
	}
	
}
