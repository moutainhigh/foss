package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;

//合伙人代收款上限请求实体
public class PartenerCodAmountUpperLimitDto implements Serializable {
	
	private static final long serialVersionUID = 5272943368985610568L;
	
	//开单部门编号
	private String partnerDeptCode;
	
	public String getPartnerDeptCode() {
		return partnerDeptCode;
	}
	public void setPartnerDeptCode(String partnerDeptCode) {
		this.partnerDeptCode = partnerDeptCode;
	}
}
