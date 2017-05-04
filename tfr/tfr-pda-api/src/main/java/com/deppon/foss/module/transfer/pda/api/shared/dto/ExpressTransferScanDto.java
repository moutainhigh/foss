package com.deppon.foss.module.transfer.pda.api.shared.dto;

import java.io.Serializable;

/**
 * 快递转货扫描Dto
 * @author zhuyunrong
 * @date 2014-12-22 下午4:00:08
 */

public class ExpressTransferScanDto implements Serializable{
	
	private static final long serialVersionUID = 2075791909076280396L;
	/**运单号*/
	private String wayBillNo;
	/**流水号*/
	private String serialNo;
	/**包号*/
	private String packageNo;
	/**部门编号*/
	private String orgCode;
	
	/**
	 * 获取 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWayBillNo() {
		return wayBillNo;
	}
	
	/**
	 * 设置 运单号.
	 *
	 * @param wayBillNo the new 运单号
	 */
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	
	/**
	 * 获取 流水号.
	 *
	 * @return the 流水号
	 */
	public String getSerialNo() {
		return serialNo;
	}
	
	/**
	 * 设置 流水号.
	 *
	 * @param serialNo the new 流水号
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
	/**
	 * 获取 包号.
	 *
	 * @return the 包号
	 */
	public String getPackageNo() {
		return packageNo;
	}
	
	/**
	 * 设置 包号.
	 *
	 * @param packageNo the new 包号
	 */
	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}
	
	/**
	 * 获取 部门编号.
	 *
	 * @return the 部门编号
	 */
	public String getOrgCode() {
		return orgCode;
	}
	
	/**
	 * 设置 部门编号.
	 *
	 * @param orgCode the new 部门编号
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
}
