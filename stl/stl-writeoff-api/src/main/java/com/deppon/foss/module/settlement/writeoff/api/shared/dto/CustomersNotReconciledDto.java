package com.deppon.foss.module.settlement.writeoff.api.shared.dto;

import java.util.List;

import com.deppon.foss.module.settlement.writeoff.api.shared.domain.CustomersNotReconciledEntity;

public class CustomersNotReconciledDto {
	private List<CustomersNotReconciledEntity> customersNotReconciledList;
	
	private List<String> orgCodeList;
	
	private String largeRegionCode;
	
	private String smallRegionCode;
	
	private String statementOrgCode;
	
	private String customerCode;
	
	private String empCode;
	
	public List<CustomersNotReconciledEntity> getCustomersNotReconciledList() {
		return customersNotReconciledList;
	}

	public void setCustomersNotReconciledList(
			List<CustomersNotReconciledEntity> customersNotReconciledList) {
		this.customersNotReconciledList = customersNotReconciledList;
	}

	public List<String> getOrgCodeList() {
		return orgCodeList;
	}

	public void setOrgCodeList(List<String> orgCodeList) {
		this.orgCodeList = orgCodeList;
	}

	public String getLargeRegionCode() {
		return largeRegionCode;
	}

	public void setLargeRegionCode(String largeRegionCode) {
		this.largeRegionCode = largeRegionCode;
	}

	public String getSmallRegionCode() {
		return smallRegionCode;
	}

	public void setSmallRegionCode(String smallRegionCode) {
		this.smallRegionCode = smallRegionCode;
	}

	public String getStatementOrgCode() {
		return statementOrgCode;
	}

	public void setStatementOrgCode(String statementOrgCode) {
		this.statementOrgCode = statementOrgCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	
}
