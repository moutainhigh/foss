package com.deppon.pda.bdm.module.foss.load.shared.domain;

/** 
  * @ClassName KdScanBusinessErrorLog 快递装车扫描出现业务异常信息
  * @Description TODO 
  * @author cbb 
  * @date 2013-9-2 下午4:53:16 
*/ 
public class KdScanBusinessErrorLog {

	private String id;
	/**
	 * 任务号
	 */
	private String taskCode;
	/**
	 * 运单号
	 */
	private String waybillCode;
	/**
	 * 标签号（流水号）
	 */
	private String labelCode;
	/**
	 * 业务异常原因
	 */
	private String reason;
	/**
	 * 扫描状态 正扫 NORMAL、反扫CANCELED
	 */
	private String scanStatus;
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((labelCode == null) ? 0 : labelCode.hashCode());
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
		result = prime * result
				+ ((scanStatus == null) ? 0 : scanStatus.hashCode());
		result = prime * result
				+ ((taskCode == null) ? 0 : taskCode.hashCode());
		result = prime * result
				+ ((waybillCode == null) ? 0 : waybillCode.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KdScanBusinessErrorLog other = (KdScanBusinessErrorLog) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (labelCode == null) {
			if (other.labelCode != null)
				return false;
		} else if (!labelCode.equals(other.labelCode))
			return false;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		if (scanStatus == null) {
			if (other.scanStatus != null)
				return false;
		} else if (!scanStatus.equals(other.scanStatus))
			return false;
		if (taskCode == null) {
			if (other.taskCode != null)
				return false;
		} else if (!taskCode.equals(other.taskCode))
			return false;
		if (waybillCode == null) {
			if (other.waybillCode != null)
				return false;
		} else if (!waybillCode.equals(other.waybillCode))
			return false;
		return true;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getLabelCode() {
		return labelCode;
	}
	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getWaybillCode() {
		return waybillCode;
	}
	public void setWaybillCode(String waybillCode) {
		this.waybillCode = waybillCode;
	}
	public String getScanStatus() {
		return scanStatus;
	}
	public void setScanStatus(String scanStatus) {
		this.scanStatus = scanStatus;
	}
	
	
}
