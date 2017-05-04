package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;

public class BillAndPkgCountDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String empCode;
	private int wayBillCount;
	private int packageCount;
	private int goodsCount;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public int getWayBillCount() {
		return wayBillCount;
	}
	public void setWayBillCount(int wayBillCount) {
		this.wayBillCount = wayBillCount;
	}
	public int getPackageCount() {
		return packageCount;
	}
	public void setPackageCount(int packageCount) {
		this.packageCount = packageCount;
	}
	public int getGoodsCount() {
		return goodsCount;
	}
	public void setGoodsCount(int goodsCount) {
		this.goodsCount = goodsCount;
	}
	
}
