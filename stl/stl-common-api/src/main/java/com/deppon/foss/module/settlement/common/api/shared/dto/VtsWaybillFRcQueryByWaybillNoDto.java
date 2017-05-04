package com.deppon.foss.module.settlement.common.api.shared.dto;

public class VtsWaybillFRcQueryByWaybillNoDto {


	// 运单号
	String waybillNo;

	// 更改单状态:待审核
	String preAudit;
	// 更改单状态
	String preAccecpt;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getPreAudit() {
		return preAudit;
	}

	public void setPreAudit(String preAudit) {
		this.preAudit = preAudit;
	}

	public String getPreAccecpt() {
		return preAccecpt;
	}

	public void setPreAccecpt(String preAccecpt) {
		this.preAccecpt = preAccecpt;
	}


}
