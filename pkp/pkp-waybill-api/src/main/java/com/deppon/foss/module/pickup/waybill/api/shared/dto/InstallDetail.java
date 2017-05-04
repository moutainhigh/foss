package com.deppon.foss.module.pickup.waybill.api.shared.dto;

/**
 * 安装物品详细列表
 * @author zengwen
 *
 */

public class InstallDetail {

	private String uniqueId; //对应WayBillInfo的ID
	
	private String mailNo; //运单号
	
	private String installCargoName; // 安装品名

	private Integer installNumber; // 安装件数
	
	private String specialFlag;

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getInstallCargoName() {
		return installCargoName;
	}

	public void setInstallCargoName(String installCargoName) {
		this.installCargoName = installCargoName;
	}

	public Integer getInstallNumber() {
		return installNumber;
	}

	public void setInstallNumber(Integer installNumber) {
		this.installNumber = installNumber;
	}

	public String getSpecialFlag() {
		return specialFlag;
	}

	public void setSpecialFlag(String specialFlag) {
		this.specialFlag = specialFlag;
	}

	public String getMailNo() {
		return mailNo;
	}

	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}

	
	
	
}
