package com.deppon.pda.bdm.module.foss.packagemanager.shared.domain;

/**
 * 建包增加扫描生成目的站
 * @author 245955
 *
 */
public class CreatePackageSiteEntity {

	/**
	 * 运单号
	 */
  private 	String wayBillNo;
	/**
	 * 当前所在转运场
	 */
   public String deptCode;
   
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getWayBillNo() {
		return wayBillNo;
	}

	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}  
}
