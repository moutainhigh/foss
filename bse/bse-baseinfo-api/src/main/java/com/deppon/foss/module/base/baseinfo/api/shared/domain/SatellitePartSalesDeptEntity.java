package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 卫星点部营业部对应关系
 * @author 130566
 *
 */
public class SatellitePartSalesDeptEntity extends BaseEntity{

	private static final long serialVersionUID = 1L;
	//卫星点部
	private String satelliteDeptCode;
	
	//卫星点部名称
	private String satelliteDeptName;
	
	//营业部
	private String salesDeptCode;
	
	//营业部名称
	private String salesDeptName;
	
	//是否启用
	private String active;
	
	//版本号
	private long versionNo;
	/**
	 * satelliteDeptCode 的get()方法
	 */
	public String getSatelliteDeptCode() {
		return satelliteDeptCode;
	}
	/**
	 * satelliteDeptCode 的set()方法
	 */
	public void setSatelliteDeptCode(String satelliteDeptCode) {
		this.satelliteDeptCode = satelliteDeptCode;
	}
	/**
	 * satelliteDeptName 的get()方法
	 */
	public String getSatelliteDeptName() {
		return satelliteDeptName;
	}
	/**
	 * satelliteDeptName 的set()方法
	 */
	public void setSatelliteDeptName(String satelliteDeptName) {
		this.satelliteDeptName = satelliteDeptName;
	}
	/**
	 * salesDeptcode 的get()方法
	 */
	public String getSalesDeptCode() {
		return salesDeptCode;
	}
	/**
	 * salesDeptcode 的set()方法
	 */
	public void setSalesDeptCode(String salesDeptCode) {
		this.salesDeptCode = salesDeptCode;
	}
	/**
	 * salesDeptName 的get()方法
	 */
	public String getSalesDeptName() {
		return salesDeptName;
	}
	/**
	 * salesDeptName 的set()方法
	 */
	public void setSalesDeptName(String salesDeptName) {
		this.salesDeptName = salesDeptName;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public long getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(long versionNo) {
		this.versionNo = versionNo;
	}
}
