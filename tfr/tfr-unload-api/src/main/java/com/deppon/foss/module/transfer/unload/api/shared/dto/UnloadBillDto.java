package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.util.List;

public class UnloadBillDto implements Serializable {

	private static final long serialVersionUID = 2455678448938749215L;

	/**
	 * 单据编号
	 */
	private String billNo;

	/**
	 * 单据类型
	 */
	private String billType;

	/**
	 * 出发部门编码
	 */
	private String origOrgCode;

	/**
	 * 到达部门编码
	 */
	private String destOrgCode;

	/**
	 * 运单集合
	 */
	private List<UnloadWaybillDto> waybillList;

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getOrigOrgCode() {
		return origOrgCode;
	}

	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	public String getDestOrgCode() {
		return destOrgCode;
	}

	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	public List<UnloadWaybillDto> getWaybillList() {
		return waybillList;
	}

	public void setWaybillList(List<UnloadWaybillDto> waybillList) {
		this.waybillList = waybillList;
	}

	@Override
	public String toString() {
		return "UnloadBillDto [billNo=" + billNo + ", billType=" + billType + ", origOrgCode=" + origOrgCode
				+ ", destOrgCode=" + destOrgCode + ", waybillList=" + waybillList + "]";
	}

}
