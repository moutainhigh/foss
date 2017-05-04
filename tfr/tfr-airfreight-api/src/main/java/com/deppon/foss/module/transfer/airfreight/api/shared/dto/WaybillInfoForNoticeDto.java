package com.deppon.foss.module.transfer.airfreight.api.shared.dto;

import java.io.Serializable;

/**
 * 运单部分信息，单号、出发部门、到达部门dto
 * @author foss-liuxue(for IBM)
 * @date 2013-7-10 下午3:31:25
 */
public class WaybillInfoForNoticeDto implements Serializable {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -5366584915571531723L;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 出发部门code
	 */
	private String deptOrgCode;
	
	/**
	 * 出发部门名称
	 */
	private String deptOrgName;

	/**
	 * 到达部门code
	 */
	private String arriveOrgCode;
	
	/**
	 * 到达部门名称
	 */
	private String arriveOrgName;

	/**
	 * 获取 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * 设置 运单号.
	 *
	 * @param waybillNo the new 运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * 获取 出发部门code.
	 *
	 * @return the 出发部门code
	 */
	public String getDeptOrgCode() {
		return deptOrgCode;
	}

	/**
	 * 设置 出发部门code.
	 *
	 * @param deptOrgCode the new 出发部门code
	 */
	public void setDeptOrgCode(String deptOrgCode) {
		this.deptOrgCode = deptOrgCode;
	}

	/**
	 * 获取 出发部门名称.
	 *
	 * @return the 出发部门名称
	 */
	public String getDeptOrgName() {
		return deptOrgName;
	}

	/**
	 * 设置 出发部门名称.
	 *
	 * @param deptOrgName the new 出发部门名称
	 */
	public void setDeptOrgName(String deptOrgName) {
		this.deptOrgName = deptOrgName;
	}

	/**
	 * 获取 到达部门code.
	 *
	 * @return the 到达部门code
	 */
	public String getArriveOrgCode() {
		return arriveOrgCode;
	}

	/**
	 * 设置 到达部门code.
	 *
	 * @param arriveOrgCode the new 到达部门code
	 */
	public void setArriveOrgCode(String arriveOrgCode) {
		this.arriveOrgCode = arriveOrgCode;
	}

	/**
	 * 获取 到达部门名称.
	 *
	 * @return the 到达部门名称
	 */
	public String getArriveOrgName() {
		return arriveOrgName;
	}

	/**
	 * 设置 到达部门名称.
	 *
	 * @param arriveOrgName the new 到达部门名称
	 */
	public void setArriveOrgName(String arriveOrgName) {
		this.arriveOrgName = arriveOrgName;
	}
	
}
