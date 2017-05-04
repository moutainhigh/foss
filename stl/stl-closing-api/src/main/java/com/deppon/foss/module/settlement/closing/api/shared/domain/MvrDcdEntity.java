package com.deppon.foss.module.settlement.closing.api.shared.domain;


import java.math.BigDecimal;

/**
 * 折扣到达
 *
 * @author 045738-foss-maojianqiang
 * @date 2013-11-15 下午3:59:04
 */
public class MvrDcdEntity {


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
     * 始发部门编码
     */
    private String origOrgCode;

    /**
     * 始发部门名称
     */
    private String origOrgName;

    /**
     * 到达部门编码
     */
    private String destOrgCode;

    /**
     * 到达部门名称
     */
    private String destOrgName;

    /**
     * 始发部门标杆编码
     */
    private String origUnifiedCode;

    /**
     * 到达部门标杆编码
     */
    private String destUnifiedCode;

    /**
     * 客户类型
     */
    private String customerType;

    /**
     * 统一结算类型
     */
    private String unifiedSettlementType;

    /**
     * 合同部门编码
     */
    private String contractOrgCode;

    /**
     * 合同部门名称
     */
    private String contractOrgName;

    /**
     * 理赔_到达部门申请_02理赔冲收入调整
     */
    private BigDecimal dcdDestApplyWoIncomet;

    /**
     * 理赔_到达部门申请02理赔入成本调整
     */
    private BigDecimal dcdDestApplyWoCostt;

    private String invoiceMark           ;// 发票标记
    private BigDecimal ldcdDestIncomeo     ;//	01理赔-到达部门申请-01理赔冲收入调整
    private BigDecimal ldcdDestCosto       ;//	01理赔-到达部门申请-01理赔入成本调整

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

    public String getOrigOrgCode() {
        return origOrgCode;
    }

    public void setOrigOrgCode(String origOrgCode) {
        this.origOrgCode = origOrgCode;
    }

    public String getOrigOrgName() {
        return origOrgName;
    }

    public void setOrigOrgName(String origOrgName) {
        this.origOrgName = origOrgName;
    }

    public String getDestOrgCode() {
        return destOrgCode;
    }

    public void setDestOrgCode(String destOrgCode) {
        this.destOrgCode = destOrgCode;
    }

    public String getDestOrgName() {
        return destOrgName;
    }

    public void setDestOrgName(String destOrgName) {
        this.destOrgName = destOrgName;
    }

    public String getOrigUnifiedCode() {
        return origUnifiedCode;
    }

    public void setOrigUnifiedCode(String origUnifiedCode) {
        this.origUnifiedCode = origUnifiedCode;
    }

    public String getDestUnifiedCode() {
        return destUnifiedCode;
    }

    public void setDestUnifiedCode(String destUnifiedCode) {
        this.destUnifiedCode = destUnifiedCode;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getUnifiedSettlementType() {
        return unifiedSettlementType;
    }

    public void setUnifiedSettlementType(String unifiedSettlementType) {
        this.unifiedSettlementType = unifiedSettlementType;
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

    public BigDecimal getDcdDestApplyWoIncomet() {
        return dcdDestApplyWoIncomet;
    }

    public void setDcdDestApplyWoIncomet(BigDecimal dcdDestApplyWoIncomet) {
        this.dcdDestApplyWoIncomet = dcdDestApplyWoIncomet;
    }

    public BigDecimal getDcdDestApplyWoCostt() {
        return dcdDestApplyWoCostt;
    }

    public void setDcdDestApplyWoCostt(BigDecimal dcdDestApplyWoCostt) {
        this.dcdDestApplyWoCostt = dcdDestApplyWoCostt;
    }

	public String getInvoiceMark() {
		return invoiceMark;
	}

	public void setInvoiceMark(String invoiceMark) {
		this.invoiceMark = invoiceMark;
	}

	public BigDecimal getLdcdDestIncomeo() {
		return ldcdDestIncomeo;
	}

	public void setLdcdDestIncomeo(BigDecimal ldcdDestIncomeo) {
		this.ldcdDestIncomeo = ldcdDestIncomeo;
	}

	public BigDecimal getLdcdDestCosto() {
		return ldcdDestCosto;
	}

	public void setLdcdDestCosto(BigDecimal ldcdDestCosto) {
		this.ldcdDestCosto = ldcdDestCosto;
	}
}