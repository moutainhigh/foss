package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CargoArrivedDto implements Serializable {

	private static final long serialVersionUID = 7159615282771804874L;

	/**
	 * 到达部门名称
	 */
	private String destDeptName;

	/**
	 * 出发部门名称
	 */
	private String origDeptName;

	/**
	 * 总重量、体积、票数
	 */
	private BigDecimal weightTotal;

	private BigDecimal volumeTotal;

	private int voteTotal;

	/**
	 * 卡航重量、体积、票数
	 */
	private BigDecimal weightFlf;

	private BigDecimal volumeFlf;

	private int voteFlf;

	/**
	 * 卡航重量、体积、票数
	 */
	private BigDecimal weightFsf;

	private BigDecimal volumeFsf;

	private int voteFsf;

	/**
	 * 卡航重量、体积、票数
	 */
	private BigDecimal weightExp;

	private BigDecimal volumeExp;

	private int voteExp;

	/**** 下面的属性是用于长途出发货量查询明细用的 ****/

	private String status;

	/**
	 * 出发部门编码
	 */
	private String origDeptCode;

	/**
	 * 到达部门(当前外场)编码
	 */
	private String destDeptCode;

	/**
	 * 预计到达时间范围(开始时间)
	 */
	private Date beginTime;

	/**
	 * 预计到达时间范围(结束时间)
	 */
	private Date endTime;

	public String getDestDeptName() {
		return destDeptName;
	}

	public void setDestDeptName(String destDeptName) {
		this.destDeptName = destDeptName;
	}

	public String getOrigDeptName() {
		return origDeptName;
	}

	public void setOrigDeptName(String origDeptName) {
		this.origDeptName = origDeptName;
	}

	public BigDecimal getWeightTotal() {
		return weightTotal;
	}

	public void setWeightTotal(BigDecimal weightTotal) {
		this.weightTotal = weightTotal;
	}

	public BigDecimal getVolumeTotal() {
		return volumeTotal;
	}

	public void setVolumeTotal(BigDecimal volumeTotal) {
		this.volumeTotal = volumeTotal;
	}

	public int getVoteTotal() {
		return voteTotal;
	}

	public void setVoteTotal(int voteTotal) {
		this.voteTotal = voteTotal;
	}

	public BigDecimal getWeightFlf() {
		return weightFlf;
	}

	public void setWeightFlf(BigDecimal weightFlf) {
		this.weightFlf = weightFlf;
	}

	public BigDecimal getVolumeFlf() {
		return volumeFlf;
	}

	public void setVolumeFlf(BigDecimal volumeFlf) {
		this.volumeFlf = volumeFlf;
	}

	public int getVoteFlf() {
		return voteFlf;
	}

	public void setVoteFlf(int voteFlf) {
		this.voteFlf = voteFlf;
	}

	public BigDecimal getWeightFsf() {
		return weightFsf;
	}

	public void setWeightFsf(BigDecimal weightFsf) {
		this.weightFsf = weightFsf;
	}

	public BigDecimal getVolumeFsf() {
		return volumeFsf;
	}

	public void setVolumeFsf(BigDecimal volumeFsf) {
		this.volumeFsf = volumeFsf;
	}

	public int getVoteFsf() {
		return voteFsf;
	}

	public void setVoteFsf(int voteFsf) {
		this.voteFsf = voteFsf;
	}

	public BigDecimal getWeightExp() {
		return weightExp;
	}

	public void setWeightExp(BigDecimal weightExp) {
		this.weightExp = weightExp;
	}

	public BigDecimal getVolumeExp() {
		return volumeExp;
	}

	public void setVolumeExp(BigDecimal volumeExp) {
		this.volumeExp = volumeExp;
	}

	public int getVoteExp() {
		return voteExp;
	}

	public void setVoteExp(int voteExp) {
		this.voteExp = voteExp;
	}

	public String getOrigDeptCode() {
		return origDeptCode;
	}

	public void setOrigDeptCode(String origDeptCode) {
		this.origDeptCode = origDeptCode;
	}

	public String getDestDeptCode() {
		return destDeptCode;
	}

	public void setDestDeptCode(String destDeptCode) {
		this.destDeptCode = destDeptCode;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CargoArrivedDto [destDeptName=" + destDeptName
				+ ", origDeptName=" + origDeptName + ", weightTotal="
				+ weightTotal + ", volumeTotal=" + volumeTotal + ", voteTotal="
				+ voteTotal + ", weightFlf=" + weightFlf + ", volumeFlf="
				+ volumeFlf + ", voteFlf=" + voteFlf + ", weightFsf="
				+ weightFsf + ", volumeFsf=" + volumeFsf + ", voteFsf="
				+ voteFsf + ", weightExp=" + weightExp + ", volumeExp="
				+ volumeExp + ", voteExp=" + voteExp + ", status=" + status
				+ ", origDeptCode=" + origDeptCode + ", destDeptCode="
				+ destDeptCode + ", beginTime=" + beginTime + ", endTime="
				+ endTime + "]";
	}

	@Override
	public int hashCode() {
		return origDeptName.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CargoArrivedDto other = (CargoArrivedDto) obj;
		if (origDeptName == null) {
			if (other.origDeptName != null)
				return false;
		} else if (!origDeptName.equals(other.origDeptName))
			return false;
		return true;
	}

}
