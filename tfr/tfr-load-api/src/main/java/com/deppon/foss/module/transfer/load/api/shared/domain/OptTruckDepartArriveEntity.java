package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.io.Serializable;

/**
 * 
 * 类描述：	发车确认/到达确认/取消发车查询在PDA上显示,FOSS接收参数实体
 * 创建人：	106162-FOSS-LIPING
 * 创建时间：	2016-04-26 上午10:13:39
 * 
 */
public class OptTruckDepartArriveEntity implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 部门类别：出发部门为“0”,到达部门为“1”
	 */
	private String deptType;
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	/**
	 * 出发部门code
	 */
	private String origOrgCode;
	/**
	 * 到达部门Code
	 */
	private String destOrgCode;
	
	/**
	 * set...get...
	 * @return
	 */
	public String getDeptType() {
		return deptType;
	}
	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getOrigOrgCode() {
		return origOrgCode;
	}
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}
	public String getDestOrgCode() {
		return destOrgCode;
	}
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	/**
	 * toString();
	 */
	@Override
	public String toString() {
		return "OptTruckDepartArriveEntity [deptType=" + deptType
				+ ", vehicleNo=" + vehicleNo + ", origOrgCode=" + origOrgCode
				+ ", destOrgCode=" + destOrgCode + "]";
	}
	
	
	
	
}
