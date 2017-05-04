package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;

//合伙人月结，资金池校验传值实体
public class PartenerDeductDto implements Serializable {
	
	private static final long serialVersionUID = 5272943368907410568L;
	
	//开单部门编号
	private String partnerDeptCode;
	//总费用
	private String limit;
	
	
	public String getPartnerDeptCode() {
		return partnerDeptCode;
	}
	public void setPartnerDeptCode(String partnerDeptCode) {
		this.partnerDeptCode = partnerDeptCode;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
}
