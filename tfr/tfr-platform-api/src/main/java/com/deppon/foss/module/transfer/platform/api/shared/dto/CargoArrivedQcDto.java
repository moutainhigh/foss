package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CargoArrivedQcDto implements Serializable {

	private static final long serialVersionUID = 1703741166231961970L;

	/**
	 * 货物状态
	 */
	private String status;

	/**
	 * 出发部门编码
	 */
	private String origDeptCode;

	/**
	 * 上一外场编码
	 */
	private String preDeptCode;

	/**
	 * 上一外场名称
	 */
	private String preDeptName;

	/**
	 * 到达部门编码
	 */
	private String destDeptCode;

	/**
	 * 到达部门名称
	 */
	private String destDeptName;

	/**
	 * 预计到达时间范围(开始时间)
	 */
	private Date beginTime;

	/**
	 * 预计到达时间范围(结束时间)
	 */
	private Date endTime;

	/**
	 * 集中接货重量配置
	 */
	private BigDecimal weightConfig;

	/**
	 * 集中接货体积配置
	 */
	private BigDecimal volumeConfig;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrigDeptCode() {
		return origDeptCode;
	}

	public void setOrigDeptCode(String origDeptCode) {
		this.origDeptCode = origDeptCode;
	}

	public String getPreDeptCode() {
		return preDeptCode;
	}

	public String getPreDeptName() {
		return preDeptName;
	}

	public void setPreDeptName(String preDeptName) {
		this.preDeptName = preDeptName;
	}

	public void setPreDeptCode(String preDeptCode) {
		this.preDeptCode = preDeptCode;
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

	public BigDecimal getWeightConfig() {
		return weightConfig;
	}

	public void setWeightConfig(BigDecimal weightConfig) {
		this.weightConfig = weightConfig;
	}

	public BigDecimal getVolumeConfig() {
		return volumeConfig;
	}

	public void setVolumeConfig(BigDecimal volumeConfig) {
		this.volumeConfig = volumeConfig;
	}

	public String getDestDeptName() {
		return destDeptName;
	}

	public void setDestDeptName(String destDeptName) {
		this.destDeptName = destDeptName;
	}

}
