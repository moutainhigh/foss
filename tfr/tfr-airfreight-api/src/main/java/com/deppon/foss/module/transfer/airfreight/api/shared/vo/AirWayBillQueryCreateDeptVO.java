package com.deppon.foss.module.transfer.airfreight.api.shared.vo;

import java.io.Serializable;

/**
 * @description 根据运单号查询航空制单的制单部门信息实体
 * @version 1.0
 * @author 106162
 * @date   2016-05-24
 * @see  com.deppon.foss.module.transfer.airfreight.api.shared.vo#AirWayBillQueryCreateDeptVO
 */
public class AirWayBillQueryCreateDeptVO implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 正单号
	 */
	private String airWaybillNo;  
	/**
	 * 制单部门编号
	 */
	private String createOrgCode;
	/**
	 * 制单部门名称
	 */
	private String createOrgName;
	/**
	 * 编号制单人编号
	 */
	private String createUserCode;
	/**
	 *  制单人名称
	 */
	private String createUserName;
	
	/**
	 * 目的站编码
	 */
	private String destOrgCode;  
	/**
	 * 目的站名称
	 */
	private String dedtOrgName;
	
	
	/**
	 * toString()
	 */
	@Override
	public String toString() {
		return "AirWayBillQueryCreateDeptVO [airWaybillNo=" + airWaybillNo
				+ ", createOrgCode=" + createOrgCode + ", createOrgName="
				+ createOrgName + ", createUserCode=" + createUserCode
				+ ", createUserName=" + createUserName + ", destOrgCode="
				+ destOrgCode + ", dedtOrgName=" + dedtOrgName + "]";
	}
	
	/**
	 * 无参构造函数
	 */
	public AirWayBillQueryCreateDeptVO() {
		super();
	}

	
	/**
	 * set/get...
	 * @return
	 */
	public String getAirWaybillNo() {
		return airWaybillNo;
	}

	public void setAirWaybillNo(String airWaybillNo) {
		this.airWaybillNo = airWaybillNo;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	public String getDestOrgCode() {
		return destOrgCode;
	}

	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	public String getDedtOrgName() {
		return dedtOrgName;
	}

	public void setDedtOrgName(String dedtOrgName) {
		this.dedtOrgName = dedtOrgName;
	}
	
}
