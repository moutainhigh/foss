package com.deppon.foss.module.pickup.common.client.vo;


public class IdentityEffectivePlanVo {
	
	/**
	 * 是否存在时效
	 */
	private boolean isExist;
	
	/**
	 * 出发部门CODE
	 */
	private String departDeptCode;
	
	/**
	 * 出发部门名称
	 */
	private String departDeptName;
	
	/**
	 * 到达部门CODE
	 */
	private String arriveDetpCode;
	
	/**
	 * 到达部门名称
	 */
	private String arriveDetpName;
	/**
	 * 运输性质
	 */
	private String productCode;

	public String getProductCode() {
		return productCode;
	}


	
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}


	public boolean isExist() {
		return isExist;
	}

	
	public void setExist(boolean isExist) {
		this.isExist = isExist;
	}

	
	public String getDepartDeptCode() {
		return departDeptCode;
	}

	
	public void setDepartDeptCode(String departDeptCode) {
		this.departDeptCode = departDeptCode;
	}

	
	public String getDepartDeptName() {
		return departDeptName;
	}

	
	public void setDepartDeptName(String departDeptName) {
		this.departDeptName = departDeptName;
	}

	
	public String getArriveDetpCode() {
		return arriveDetpCode;
	}

	
	public void setArriveDetpCode(String arriveDetpCode) {
		this.arriveDetpCode = arriveDetpCode;
	}

	
	public String getArriveDetpName() {
		return arriveDetpName;
	}

	
	public void setArriveDetpName(String arriveDetpName) {
		this.arriveDetpName = arriveDetpName;
	}
	
	
}
