package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.io.Serializable;
/**
 * 结清货款模块根据单号查询数据实体
 * @author 268974
 *
 */
public class SettlementOfPaymentEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	// 运单号
	private String waybillNo;
	// 部门编号
	private String deptCode;
	// 部门名称
	private String deptName;
	// 登录人编号
	private String userCode;
	// 登录人名称
	private String userName;
	
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
	
}
