package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;

//合伙人预存资金池校验传值实体
public class PartenerPrestoreDeductDto implements Serializable {
	
	private static final long serialVersionUID = 5272943963407410568L;
	
	//开单部门编号
	private String partnerOrgCode; 
	//总费用
	private String amount;
	
	
	public String getPartnerOrgCode() {
		return partnerOrgCode;
	}
	public void setPartnerOrgCode(String partnerOrgCode) {
		this.partnerOrgCode = partnerOrgCode;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
}
