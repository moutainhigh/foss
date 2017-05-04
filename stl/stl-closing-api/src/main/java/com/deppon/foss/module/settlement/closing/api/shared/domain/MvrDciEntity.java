package com.deppon.foss.module.settlement.closing.api.shared.domain;


import java.math.BigDecimal;

/**
 * 折扣往来
 *
 * @author 045738-foss-maojianqiang
 * @date 2013-11-15 下午3:59:04
 */
public class MvrDciEntity {

    /**
     * Id
     */
    private String id;

    /**
     * 期间
     */
    private String period;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 客户编码
     */
    private String customerCode;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 部门编码
     */
    private String orgCode;

    /**
     * 部门名称
     */
    private String orgName;

    /**
     * 部门类型
     */
    private String orgType;

    /**
     * 发票标记
     */
    private String invoiceMark;

    /**
     * 统一结算类型
     */
    private String unifiedSettlementType;

    /**
     * 理赔-到达部门申请_02理赔冲收入调整（往来，非统一）
     */
    private BigDecimal dciDestApplyWoIncomeNus;
    /**
     * 理赔-到达部门申请_02理赔冲收入调整（往来，统一）
     */
    private BigDecimal dciDestApplyWoIncomeUs;
    
    private BigDecimal ldciDestIncomeoNus   ;//	理赔-到达部门申请-01理赔冲收入调整（往来，非统一）
    private BigDecimal ldciDestIncomeoUs    ;//	理赔-到达部门申请-01理赔冲收入调整（往来，统一）


    /*setters & getters*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getInvoiceMark() {
        return invoiceMark;
    }

    public void setInvoiceMark(String invoiceMark) {
        this.invoiceMark = invoiceMark;
    }

    public String getUnifiedSettlementType() {
        return unifiedSettlementType;
    }

    public void setUnifiedSettlementType(String unifiedSettlementType) {
        this.unifiedSettlementType = unifiedSettlementType;
    }

    public BigDecimal getDciDestApplyWoIncomeNus() {
        return dciDestApplyWoIncomeNus;
    }

    public void setDciDestApplyWoIncomeNus(BigDecimal dciDestApplyWoIncomeNus) {
        this.dciDestApplyWoIncomeNus = dciDestApplyWoIncomeNus;
    }

    public BigDecimal getDciDestApplyWoIncomeUs() {
        return dciDestApplyWoIncomeUs;
    }

    public void setDciDestApplyWoIncomeUs(BigDecimal dciDestApplyWoIncomeUs) {
        this.dciDestApplyWoIncomeUs = dciDestApplyWoIncomeUs;
    }

	public BigDecimal getLdciDestIncomeoNus() {
		return ldciDestIncomeoNus;
	}

	public void setLdciDestIncomeoNus(BigDecimal ldciDestIncomeoNus) {
		this.ldciDestIncomeoNus = ldciDestIncomeoNus;
	}

	public BigDecimal getLdciDestIncomeoUs() {
		return ldciDestIncomeoUs;
	}

	public void setLdciDestIncomeoUs(BigDecimal ldciDestIncomeoUs) {
		this.ldciDestIncomeoUs = ldciDestIncomeoUs;
	}
}