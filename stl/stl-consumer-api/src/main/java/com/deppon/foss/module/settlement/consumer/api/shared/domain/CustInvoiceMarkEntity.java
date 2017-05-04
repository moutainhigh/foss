package com.deppon.foss.module.settlement.consumer.api.shared.domain;

/**
 * 传递客户发票标记字段
 * <p style="display:none">
 * modifyRecord</p>
 * <p style="display:none">
 * version:V1.0,author:Adminstrator,date:2013-11-19 下午4:25:00</p>
 * @author 105762 YangShushuo
 * @date 2013-11-19 下午4:25:00
 */
public class CustInvoiceMarkEntity {
	/**
	 * 客户编码
	 */
	private String custNumber;
	/**
	 * 发票标记
	 */
	private String invoiceMark;
    /**
     *
     *是否统一结算
     */
	private String unifiedSettlement;
    /**
     * 合同部门编码
     */
    private String contractOrgCode;
    /**
     * 合同部门名称
     */
    private String contractOrgName;
    /**
     * 催款部门编码
     */
    private String dunningOrgCode;
    /**
     * 催款部门名称
     */
    private String dunningOrgName;

	public String getCustNumber() {
		return custNumber;
	}

	public void setCustNumber(String custNumber) {
		this.custNumber = custNumber;
	}

	public String getInvoiceMark() {
		return invoiceMark;
	}

	public void setInvoiceMark(String invoiceMark) {
		this.invoiceMark = invoiceMark;
	}

	public String getUnifiedSettlement() {
		return unifiedSettlement;
	}

	public void setUnifiedSettlement(String unifiedSettlement) {
		this.unifiedSettlement = unifiedSettlement;
	}

	public String getContractOrgCode() {
		return contractOrgCode;
	}

	public void setContractOrgCode(String contractOrgCode) {
		this.contractOrgCode = contractOrgCode;
	}

	public String getContractOrgName() {
		return contractOrgName;
	}

	public void setContractOrgName(String contractOrgName) {
		this.contractOrgName = contractOrgName;
	}

	public String getDunningOrgCode() {
		return dunningOrgCode;
	}

	public void setDunningOrgCode(String dunningOrgCode) {
		this.dunningOrgCode = dunningOrgCode;
	}

	public String getDunningOrgName() {
		return dunningOrgName;
	}

	public void setDunningOrgName(String dunningOrgName) {
		this.dunningOrgName = dunningOrgName;
	}

}
