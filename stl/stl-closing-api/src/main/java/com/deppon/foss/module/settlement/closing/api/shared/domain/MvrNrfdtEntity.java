package com.deppon.foss.module.settlement.closing.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 02到达月报表
 * 
 * @author 163576-foss-guxinhua
 * @date 2013-11-6 下午5:56:31
 */
public class MvrNrfdtEntity extends BaseEntity implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2169602082225451850L;

	private String id;

    private String period;

    private String productCode;

    private String customerCode;

    private String customerName;

    private String origOrgCode;

    private String origOrgName;

    private String destOrgCode;

    private String destOrgName;

    private String origUnifiedCode;

    private String destUnifiedCode;

    private String customerType;
    
	private String unifiedSettlementType;
	
	private String contractOrgCode;
	
	private String contractOrgName;

    private BigDecimal uroDestChNpod;

    private BigDecimal uroDestCdNpod;

    private BigDecimal uroDestChPod;

    private BigDecimal uroDestCdPod;

    private BigDecimal urtDestChNpod;

    private BigDecimal urtDestCdNpod;

    private BigDecimal urtDestChPod;

    private BigDecimal urtDestCdPod;

    private BigDecimal custDrtWoDestRcvtNpod;

    private BigDecimal codUrCdNpod;

    private BigDecimal claimDesttCost;

    private BigDecimal claimDesttWoDestRcvtPod;

    private BigDecimal claimDesttWoDestRcvtNpod;

    private BigDecimal claimDestoPayApply;

    private BigDecimal claimDesttPayApply;

    private BigDecimal rdDestoPayApply;

    private BigDecimal rdDesttIncome;

    private BigDecimal rdDesttCost;

    private BigDecimal rdDesttPayApply;

    private BigDecimal rdDesttWoDestRcvoPod;

    private BigDecimal rdDesttWoDestRcvoNpod;

    private BigDecimal rdDesttWoDestRcvtPod;

    private BigDecimal rdDesttWoDestRcvtNpod;

    private BigDecimal cpoDestPayApply;

    private BigDecimal cptDestPayApply;

    private BigDecimal custDrtWoDestRcvoNpod;

    private BigDecimal custDrtWoDestRcvoPod;

    private BigDecimal custDrtWoDestRcvtPod;

    private BigDecimal codUrChNpod;

    private BigDecimal claimDesttIncome;

    private BigDecimal claimDesttWoDestRcvoPod;

    private BigDecimal claimDesttWoDestRcvoNpod;

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

	public BigDecimal getUroDestChNpod() {
        return uroDestChNpod;
    }

    public void setUroDestChNpod(BigDecimal uroDestChNpod) {
        this.uroDestChNpod = uroDestChNpod;
    }

    public BigDecimal getUroDestCdNpod() {
        return uroDestCdNpod;
    }

    public void setUroDestCdNpod(BigDecimal uroDestCdNpod) {
        this.uroDestCdNpod = uroDestCdNpod;
    }

    public BigDecimal getUroDestChPod() {
        return uroDestChPod;
    }

    public void setUroDestChPod(BigDecimal uroDestChPod) {
        this.uroDestChPod = uroDestChPod;
    }

    public BigDecimal getUroDestCdPod() {
        return uroDestCdPod;
    }

    public void setUroDestCdPod(BigDecimal uroDestCdPod) {
        this.uroDestCdPod = uroDestCdPod;
    }

    public BigDecimal getUrtDestChNpod() {
        return urtDestChNpod;
    }

    public void setUrtDestChNpod(BigDecimal urtDestChNpod) {
        this.urtDestChNpod = urtDestChNpod;
    }

    public BigDecimal getUrtDestCdNpod() {
        return urtDestCdNpod;
    }

    public void setUrtDestCdNpod(BigDecimal urtDestCdNpod) {
        this.urtDestCdNpod = urtDestCdNpod;
    }

    public BigDecimal getUrtDestChPod() {
        return urtDestChPod;
    }

    public void setUrtDestChPod(BigDecimal urtDestChPod) {
        this.urtDestChPod = urtDestChPod;
    }

    public BigDecimal getUrtDestCdPod() {
        return urtDestCdPod;
    }

    public void setUrtDestCdPod(BigDecimal urtDestCdPod) {
        this.urtDestCdPod = urtDestCdPod;
    }

    public BigDecimal getCustDrtWoDestRcvtNpod() {
        return custDrtWoDestRcvtNpod;
    }

    public void setCustDrtWoDestRcvtNpod(BigDecimal custDrtWoDestRcvtNpod) {
        this.custDrtWoDestRcvtNpod = custDrtWoDestRcvtNpod;
    }

    public BigDecimal getCodUrCdNpod() {
        return codUrCdNpod;
    }

    public void setCodUrCdNpod(BigDecimal codUrCdNpod) {
        this.codUrCdNpod = codUrCdNpod;
    }

    public BigDecimal getClaimDesttCost() {
        return claimDesttCost;
    }

    public void setClaimDesttCost(BigDecimal claimDesttCost) {
        this.claimDesttCost = claimDesttCost;
    }

    public BigDecimal getClaimDesttWoDestRcvtPod() {
        return claimDesttWoDestRcvtPod;
    }

    public void setClaimDesttWoDestRcvtPod(BigDecimal claimDesttWoDestRcvtPod) {
        this.claimDesttWoDestRcvtPod = claimDesttWoDestRcvtPod;
    }

    public BigDecimal getClaimDesttWoDestRcvtNpod() {
        return claimDesttWoDestRcvtNpod;
    }

    public void setClaimDesttWoDestRcvtNpod(BigDecimal claimDesttWoDestRcvtNpod) {
        this.claimDesttWoDestRcvtNpod = claimDesttWoDestRcvtNpod;
    }

    public BigDecimal getClaimDestoPayApply() {
        return claimDestoPayApply;
    }

    public void setClaimDestoPayApply(BigDecimal claimDestoPayApply) {
        this.claimDestoPayApply = claimDestoPayApply;
    }

    public BigDecimal getClaimDesttPayApply() {
        return claimDesttPayApply;
    }

    public void setClaimDesttPayApply(BigDecimal claimDesttPayApply) {
        this.claimDesttPayApply = claimDesttPayApply;
    }

    public BigDecimal getRdDestoPayApply() {
        return rdDestoPayApply;
    }

    public void setRdDestoPayApply(BigDecimal rdDestoPayApply) {
        this.rdDestoPayApply = rdDestoPayApply;
    }

    public BigDecimal getRdDesttIncome() {
        return rdDesttIncome;
    }

    public void setRdDesttIncome(BigDecimal rdDesttIncome) {
        this.rdDesttIncome = rdDesttIncome;
    }

    public BigDecimal getRdDesttCost() {
        return rdDesttCost;
    }

    public void setRdDesttCost(BigDecimal rdDesttCost) {
        this.rdDesttCost = rdDesttCost;
    }

    public BigDecimal getRdDesttPayApply() {
        return rdDesttPayApply;
    }

    public void setRdDesttPayApply(BigDecimal rdDesttPayApply) {
        this.rdDesttPayApply = rdDesttPayApply;
    }

    public BigDecimal getRdDesttWoDestRcvoPod() {
        return rdDesttWoDestRcvoPod;
    }

    public void setRdDesttWoDestRcvoPod(BigDecimal rdDesttWoDestRcvoPod) {
        this.rdDesttWoDestRcvoPod = rdDesttWoDestRcvoPod;
    }

    public BigDecimal getRdDesttWoDestRcvoNpod() {
        return rdDesttWoDestRcvoNpod;
    }

    public void setRdDesttWoDestRcvoNpod(BigDecimal rdDesttWoDestRcvoNpod) {
        this.rdDesttWoDestRcvoNpod = rdDesttWoDestRcvoNpod;
    }

    public BigDecimal getRdDesttWoDestRcvtPod() {
        return rdDesttWoDestRcvtPod;
    }

    public void setRdDesttWoDestRcvtPod(BigDecimal rdDesttWoDestRcvtPod) {
        this.rdDesttWoDestRcvtPod = rdDesttWoDestRcvtPod;
    }

    public BigDecimal getRdDesttWoDestRcvtNpod() {
        return rdDesttWoDestRcvtNpod;
    }

    public void setRdDesttWoDestRcvtNpod(BigDecimal rdDesttWoDestRcvtNpod) {
        this.rdDesttWoDestRcvtNpod = rdDesttWoDestRcvtNpod;
    }

    public BigDecimal getCpoDestPayApply() {
        return cpoDestPayApply;
    }

    public void setCpoDestPayApply(BigDecimal cpoDestPayApply) {
        this.cpoDestPayApply = cpoDestPayApply;
    }

    public BigDecimal getCptDestPayApply() {
        return cptDestPayApply;
    }

    public void setCptDestPayApply(BigDecimal cptDestPayApply) {
        this.cptDestPayApply = cptDestPayApply;
    }

    public BigDecimal getCustDrtWoDestRcvoNpod() {
        return custDrtWoDestRcvoNpod;
    }

    public void setCustDrtWoDestRcvoNpod(BigDecimal custDrtWoDestRcvoNpod) {
        this.custDrtWoDestRcvoNpod = custDrtWoDestRcvoNpod;
    }

    public BigDecimal getCustDrtWoDestRcvoPod() {
        return custDrtWoDestRcvoPod;
    }

    public void setCustDrtWoDestRcvoPod(BigDecimal custDrtWoDestRcvoPod) {
        this.custDrtWoDestRcvoPod = custDrtWoDestRcvoPod;
    }

    public BigDecimal getCustDrtWoDestRcvtPod() {
        return custDrtWoDestRcvtPod;
    }

    public void setCustDrtWoDestRcvtPod(BigDecimal custDrtWoDestRcvtPod) {
        this.custDrtWoDestRcvtPod = custDrtWoDestRcvtPod;
    }

    public BigDecimal getCodUrChNpod() {
        return codUrChNpod;
    }

    public void setCodUrChNpod(BigDecimal codUrChNpod) {
        this.codUrChNpod = codUrChNpod;
    }

    public BigDecimal getClaimDesttIncome() {
        return claimDesttIncome;
    }

    public void setClaimDesttIncome(BigDecimal claimDesttIncome) {
        this.claimDesttIncome = claimDesttIncome;
    }

    public BigDecimal getClaimDesttWoDestRcvoPod() {
        return claimDesttWoDestRcvoPod;
    }

    public void setClaimDesttWoDestRcvoPod(BigDecimal claimDesttWoDestRcvoPod) {
        this.claimDesttWoDestRcvoPod = claimDesttWoDestRcvoPod;
    }

    public BigDecimal getClaimDesttWoDestRcvoNpod() {
        return claimDesttWoDestRcvoNpod;
    }

    public void setClaimDesttWoDestRcvoNpod(BigDecimal claimDesttWoDestRcvoNpod) {
        this.claimDesttWoDestRcvoNpod = claimDesttWoDestRcvoNpod;
    }
}