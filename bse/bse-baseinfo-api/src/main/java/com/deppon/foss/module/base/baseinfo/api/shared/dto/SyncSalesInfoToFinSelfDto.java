package com.deppon.foss.module.base.baseinfo.api.shared.dto;
/**
 * 同步营业部部分信息给发票项目
 * @author 130566
 *
 */
public class SyncSalesInfoToFinSelfDto {
	/**
	 * 编码
	 */
	private String orgCode;
	/**
	 * 部门名称
	 */
	private String orgName;
	/**
	 * 部门地址
	 */
	private String orgAddress;
	/**
	 * 部门状态
	 */
	private String active;
	
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgAddress() {
		return orgAddress;
	}
	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	
}
