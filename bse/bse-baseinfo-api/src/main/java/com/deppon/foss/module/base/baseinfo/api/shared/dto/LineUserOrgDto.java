package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.util.List;

/**
 * 
 *<p>Title: LineUserOrgDto</p>
 * <p>Description: 线路数据权限Dto</p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-5-23
 */
public class LineUserOrgDto {
	//营业部
	private String SalesOrgs;
	/**
	 * 营业部名称
	 */
	private String SalesOrgsName;
	//外场
	private String transferOrg;
	/**
	 * 外场名称
	 */
	private String transferOrgName;
	//数据权限集合
	private List<String> orgList;
	
	public String getSalesOrgs() {
		return SalesOrgs;
	}
	public void setSalesOrgs(String salesOrgs) {
		SalesOrgs = salesOrgs;
	}
	public String getTransferOrg() {
		return transferOrg;
	}
	public void setTransferOrg(String transferOrg) {
		this.transferOrg = transferOrg;
	}
	public List<String> getOrgList() {
		return orgList;
	}
	public void setOrgList(List<String> orgList) {
		this.orgList = orgList;
	}
	public String getSalesOrgsName() {
		return SalesOrgsName;
	}
	public void setSalesOrgsName(String salesOrgsName) {
		SalesOrgsName = salesOrgsName;
	}
	public String getTransferOrgName() {
		return transferOrgName;
	}
	public void setTransferOrgName(String transferOrgName) {
		this.transferOrgName = transferOrgName;
	}
	
	
}
