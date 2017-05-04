package com.deppon.foss.module.settlement.closing.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;



/**
 * 01到达月报表
 * 
 * @author 163576-foss-guxinhua
 * @date 2013-11-6 下午5:56:31
 */
public class MvrNrfdoEntity extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2101000857857000190L;

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
    
    private BigDecimal custDroWoDestRcvoNpod;

    private BigDecimal custDroWoDestRcvoPod;

    private BigDecimal custDroWoDestRcvtNpod;

    private BigDecimal custDroWoDestRcvtPod;

    private BigDecimal claimDestoIncome;

    private BigDecimal claimDestoCost;

    private BigDecimal claimDestoWoDestRcvoPod;

    private BigDecimal claimDestoWoDestRcvoNpod;

    private BigDecimal claimDestoWoDestRcvtPod;

    private BigDecimal claimDestoWoDestRcvtNpod;

    private BigDecimal claimDestoPayApply;

    private BigDecimal rdDestoIncome;

    private BigDecimal rdDestoCost;

    private BigDecimal rdDestoPayApply;

    private BigDecimal rdDestoDestRcvoPod;

    private BigDecimal rdDestoWoDestRcvoNpod;

    private BigDecimal rdDestoDestRcvtPod;

    private BigDecimal rdDestoWoDestRcvtNpod;

    private BigDecimal cpoDestPayApply;

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

	public BigDecimal getCustDroWoDestRcvoNpod() {
        return custDroWoDestRcvoNpod;
    }

    public void setCustDroWoDestRcvoNpod(BigDecimal custDroWoDestRcvoNpod) {
        this.custDroWoDestRcvoNpod = custDroWoDestRcvoNpod;
    }

    public BigDecimal getCustDroWoDestRcvoPod() {
        return custDroWoDestRcvoPod;
    }

    public void setCustDroWoDestRcvoPod(BigDecimal custDroWoDestRcvoPod) {
        this.custDroWoDestRcvoPod = custDroWoDestRcvoPod;
    }

    public BigDecimal getCustDroWoDestRcvtNpod() {
        return custDroWoDestRcvtNpod;
    }

    public void setCustDroWoDestRcvtNpod(BigDecimal custDroWoDestRcvtNpod) {
        this.custDroWoDestRcvtNpod = custDroWoDestRcvtNpod;
    }

    public BigDecimal getCustDroWoDestRcvtPod() {
        return custDroWoDestRcvtPod;
    }

    public void setCustDroWoDestRcvtPod(BigDecimal custDroWoDestRcvtPod) {
        this.custDroWoDestRcvtPod = custDroWoDestRcvtPod;
    }

    public BigDecimal getClaimDestoIncome() {
        return claimDestoIncome;
    }

    public void setClaimDestoIncome(BigDecimal claimDestoIncome) {
        this.claimDestoIncome = claimDestoIncome;
    }

    public BigDecimal getClaimDestoCost() {
        return claimDestoCost;
    }

    public void setClaimDestoCost(BigDecimal claimDestoCost) {
        this.claimDestoCost = claimDestoCost;
    }

    public BigDecimal getClaimDestoWoDestRcvoPod() {
        return claimDestoWoDestRcvoPod;
    }

    public void setClaimDestoWoDestRcvoPod(BigDecimal claimDestoWoDestRcvoPod) {
        this.claimDestoWoDestRcvoPod = claimDestoWoDestRcvoPod;
    }

    public BigDecimal getClaimDestoWoDestRcvoNpod() {
        return claimDestoWoDestRcvoNpod;
    }

    public void setClaimDestoWoDestRcvoNpod(BigDecimal claimDestoWoDestRcvoNpod) {
        this.claimDestoWoDestRcvoNpod = claimDestoWoDestRcvoNpod;
    }

    public BigDecimal getClaimDestoWoDestRcvtPod() {
        return claimDestoWoDestRcvtPod;
    }

    public void setClaimDestoWoDestRcvtPod(BigDecimal claimDestoWoDestRcvtPod) {
        this.claimDestoWoDestRcvtPod = claimDestoWoDestRcvtPod;
    }

    public BigDecimal getClaimDestoWoDestRcvtNpod() {
        return claimDestoWoDestRcvtNpod;
    }

    public void setClaimDestoWoDestRcvtNpod(BigDecimal claimDestoWoDestRcvtNpod) {
        this.claimDestoWoDestRcvtNpod = claimDestoWoDestRcvtNpod;
    }

    public BigDecimal getClaimDestoPayApply() {
        return claimDestoPayApply;
    }

    public void setClaimDestoPayApply(BigDecimal claimDestoPayApply) {
        this.claimDestoPayApply = claimDestoPayApply;
    }

    public BigDecimal getRdDestoIncome() {
        return rdDestoIncome;
    }

    public void setRdDestoIncome(BigDecimal rdDestoIncome) {
        this.rdDestoIncome = rdDestoIncome;
    }

    public BigDecimal getRdDestoCost() {
        return rdDestoCost;
    }

    public void setRdDestoCost(BigDecimal rdDestoCost) {
        this.rdDestoCost = rdDestoCost;
    }

    public BigDecimal getRdDestoPayApply() {
        return rdDestoPayApply;
    }

    public void setRdDestoPayApply(BigDecimal rdDestoPayApply) {
        this.rdDestoPayApply = rdDestoPayApply;
    }

    public BigDecimal getRdDestoDestRcvoPod() {
        return rdDestoDestRcvoPod;
    }

    public void setRdDestoDestRcvoPod(BigDecimal rdDestoDestRcvoPod) {
        this.rdDestoDestRcvoPod = rdDestoDestRcvoPod;
    }

    public BigDecimal getRdDestoWoDestRcvoNpod() {
        return rdDestoWoDestRcvoNpod;
    }

    public void setRdDestoWoDestRcvoNpod(BigDecimal rdDestoWoDestRcvoNpod) {
        this.rdDestoWoDestRcvoNpod = rdDestoWoDestRcvoNpod;
    }

    public BigDecimal getRdDestoDestRcvtPod() {
        return rdDestoDestRcvtPod;
    }

    public void setRdDestoDestRcvtPod(BigDecimal rdDestoDestRcvtPod) {
        this.rdDestoDestRcvtPod = rdDestoDestRcvtPod;
    }

    public BigDecimal getRdDestoWoDestRcvtNpod() {
        return rdDestoWoDestRcvtNpod;
    }

    public void setRdDestoWoDestRcvtNpod(BigDecimal rdDestoWoDestRcvtNpod) {
        this.rdDestoWoDestRcvtNpod = rdDestoWoDestRcvtNpod;
    }

    public BigDecimal getCpoDestPayApply() {
        return cpoDestPayApply;
    }

    public void setCpoDestPayApply(BigDecimal cpoDestPayApply) {
        this.cpoDestPayApply = cpoDestPayApply;
    }
}