/**
 * 
 */
package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;

/**
 * @desc 查询全国外场(剔除分部)
 * @author 105795
 * @date 2015/01/28
 */
public class TransferCenterDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2053099931470381062L;
	// 外场名称
	private String TransferCenterName;
	// 外场code
	private String TransferCenterCode;
	
	//部门名称
	private String orgName;
	//部门code
	private String orgCode;
	//父部门code
	private String parentCode;
	
	/**
	 * @return the transferCenterName
	 */
	public String getTransferCenterName() {
		return TransferCenterName;
	}
	/**
	 * @param transferCenterName the transferCenterName to set
	 */
	public void setTransferCenterName(String transferCenterName) {
		TransferCenterName = transferCenterName;
	}
	/**
	 * @return the transferCenterCode
	 */
	public String getTransferCenterCode() {
		return TransferCenterCode;
	}
	/**
	 * @param transferCenterCode the transferCenterCode to set
	 */
	public void setTransferCenterCode(String transferCenterCode) {
		TransferCenterCode = transferCenterCode;
	}
	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}
	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}
	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	/**
	 * @return the parentCode
	 */
	public String getParentCode() {
		return parentCode;
	}
	/**
	 * @param parentCode the parentCode to set
	 */
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	

	
}
