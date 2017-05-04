package com.deppon.pda.bdm.module.foss.load.shared.domain.derytranload;

/**
 * 获取派件指令
 * 
 * @author 245955
 * 
 */
public class GetLoadTask {
	/**
	 * 装车状态
	 */
	private String status;
	/**
	 * 任务号
	 */
	private String taskCode;
	/**
	 * 快递员编码
	 */
	private String tallyerCode;
	/**
	 * 总件数
	 */
	// private Integer picesTotal;
	/**
	 * 扫描件数
	 */
	private Integer pieceScan;

	public String getTallyerCode() {
		return tallyerCode;
	}

	public void setTallyerCode(String tallyerCode) {
		this.tallyerCode = tallyerCode;
	}

	public Integer getPieceScan() {
		return pieceScan;
	}

	public void setPieceScan(Integer pieceScan) {
		this.pieceScan = pieceScan;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
}
