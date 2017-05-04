package com.deppon.foss.module.settlement.closing.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

public class UnifiedSettlementEntity {
    private String id;

    private String period;

    private String productCode;

    private String customerCode;

    private String customerName;

    private String orgCode;

    private String orgName;

    private String orgType;

    private String orgUnifiedCode;

    private Date voucherBeginTime;

    private Date voucherEndTime;

    private String invoiceMark;

    private String unifiedSettlementType;

    private BigDecimal uroDestCdNpod;

    private BigDecimal uroDestCdPod;

    private BigDecimal urtDestCdNpod;

    private BigDecimal urtDestCdPod;

    private BigDecimal custDroWoOrigRcvtNpod;

    private BigDecimal custDroWoOrigRcvtPod;

    private BigDecimal codPayWoOrigRcvtPod;

    private BigDecimal codPayWoOrigRcvtNpod;

    private BigDecimal codPayWoOthRcvt;

    private BigDecimal claimOrigoWoOrigRcvtPod;

    private BigDecimal claimOrigtOrigRcvoPod;

    private BigDecimal claimOrigoWoOrigRcvtNpod;

    private BigDecimal claimOrigtWoOrigRcvoNpod;

    private BigDecimal claimDesttIncome;

    private BigDecimal claimDestoWoDestRcvoPod;

    private BigDecimal claimDesttWoDestRcvoPod;

    private BigDecimal claimDestoWoDestRcvoNpod;

    private BigDecimal claimDesttWoDestRcvoNpod;

    private BigDecimal claimDestoWoDestRcvtPod;

    private BigDecimal claimDesttWoDestRcvtPod;

    private BigDecimal claimDestoWoDestRcvtNpod;

    private BigDecimal claimDesttWoDestRcvtNpod;

    private BigDecimal claimDestoPayApply;

    private BigDecimal orClaimPayoWoRcvt;

    private BigDecimal orClaimPaytWoRcvo;

    private BigDecimal orCustDroWoRcvt;

    private BigDecimal rdOrigoWoOrigRcvtPod;

    private BigDecimal rdOrigtWoOrigRcvoPod;

    private BigDecimal rdOrigoWoOrigRcvtNpod;

    private BigDecimal rdOrigtWoOrigRcvoNpod;

    private BigDecimal rdDestoPayApply;

    private BigDecimal rdDestoDestRcvoPod;

    private BigDecimal rdDesttWoDestRcvoPod;

    private BigDecimal rdDestoWoDestRcvoNpod;

    private BigDecimal rdDesttWoDestRcvoNpod;

    private BigDecimal rdDestoDestRcvtPod;

    private BigDecimal rdDesttWoDestRcvtPod;

    private BigDecimal rdDestoWoDestRcvtNpod;

    private BigDecimal rdDesttWoDestRcvtNpod;

    private BigDecimal claimDestoIncome;

    private BigDecimal rdDestoIncome;

    private BigDecimal rdDesttIncome;

    private BigDecimal custDrtWoDestRcvoNpod;

    private BigDecimal custDroWoDestRcvoNpod;

    private BigDecimal custDroWoDestRcvoPod;

    private BigDecimal custDrtWoDestRcvoPod;

    private BigDecimal custDroWoDestRcvtNpod;

    private BigDecimal custDrtWoDestRcvtNpod;

    private BigDecimal custDroWoDestRcvtPod;

    private BigDecimal custDrtWoDestRcvtPod;

    private BigDecimal codPayWoDestRcvoPod;

    private BigDecimal codPayWoDestRcvoNpod;

    private BigDecimal codPayWoDestRcvtPod;

    private BigDecimal codPayWoDestRcvtNpod;

    public BigDecimal getCodPayWoDestRcvtNpod() {
        return codPayWoDestRcvtNpod;
    }

    public void setCodPayWoDestRcvtNpod(BigDecimal codPayWoDestRcvtNpod) {
        this.codPayWoDestRcvtNpod = codPayWoDestRcvtNpod;
    }

    public BigDecimal getCustDrtWoDestRcvoNpod() {
        return custDrtWoDestRcvoNpod;
    }

    public void setCustDrtWoDestRcvoNpod(BigDecimal custDrtWoDestRcvoNpod) {
        this.custDrtWoDestRcvoNpod = custDrtWoDestRcvoNpod;
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

    public BigDecimal getCustDrtWoDestRcvoPod() {
        return custDrtWoDestRcvoPod;
    }

    public void setCustDrtWoDestRcvoPod(BigDecimal custDrtWoDestRcvoPod) {
        this.custDrtWoDestRcvoPod = custDrtWoDestRcvoPod;
    }

    public BigDecimal getCustDroWoDestRcvtNpod() {
        return custDroWoDestRcvtNpod;
    }

    public void setCustDroWoDestRcvtNpod(BigDecimal custDroWoDestRcvtNpod) {
        this.custDroWoDestRcvtNpod = custDroWoDestRcvtNpod;
    }

    public BigDecimal getCustDrtWoDestRcvtNpod() {
        return custDrtWoDestRcvtNpod;
    }

    public void setCustDrtWoDestRcvtNpod(BigDecimal custDrtWoDestRcvtNpod) {
        this.custDrtWoDestRcvtNpod = custDrtWoDestRcvtNpod;
    }

    public BigDecimal getCustDroWoDestRcvtPod() {
        return custDroWoDestRcvtPod;
    }

    public void setCustDroWoDestRcvtPod(BigDecimal custDroWoDestRcvtPod) {
        this.custDroWoDestRcvtPod = custDroWoDestRcvtPod;
    }

    public BigDecimal getCustDrtWoDestRcvtPod() {
        return custDrtWoDestRcvtPod;
    }

    public void setCustDrtWoDestRcvtPod(BigDecimal custDrtWoDestRcvtPod) {
        this.custDrtWoDestRcvtPod = custDrtWoDestRcvtPod;
    }

    public BigDecimal getCodPayWoDestRcvoPod() {
        return codPayWoDestRcvoPod;
    }

    public void setCodPayWoDestRcvoPod(BigDecimal codPayWoDestRcvoPod) {
        this.codPayWoDestRcvoPod = codPayWoDestRcvoPod;
    }

    public BigDecimal getCodPayWoDestRcvoNpod() {
        return codPayWoDestRcvoNpod;
    }

    public void setCodPayWoDestRcvoNpod(BigDecimal codPayWoDestRcvoNpod) {
        this.codPayWoDestRcvoNpod = codPayWoDestRcvoNpod;
    }

    public BigDecimal getCodPayWoDestRcvtPod() {
        return codPayWoDestRcvtPod;
    }

    public void setCodPayWoDestRcvtPod(BigDecimal codPayWoDestRcvtPod) {
        this.codPayWoDestRcvtPod = codPayWoDestRcvtPod;
    }

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

    public String getOrgUnifiedCode() {
        return orgUnifiedCode;
    }

    public void setOrgUnifiedCode(String orgUnifiedCode) {
        this.orgUnifiedCode = orgUnifiedCode;
    }

    public Date getVoucherBeginTime() {
        return voucherBeginTime;
    }

    public void setVoucherBeginTime(Date voucherBeginTime) {
        this.voucherBeginTime = voucherBeginTime;
    }

    public Date getVoucherEndTime() {
        return voucherEndTime;
    }

    public void setVoucherEndTime(Date voucherEndTime) {
        this.voucherEndTime = voucherEndTime;
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

    public BigDecimal getUroDestCdNpod() {
        return uroDestCdNpod;
    }

    public void setUroDestCdNpod(BigDecimal uroDestCdNpod) {
        this.uroDestCdNpod = uroDestCdNpod;
    }

    public BigDecimal getUroDestCdPod() {
        return uroDestCdPod;
    }

    public void setUroDestCdPod(BigDecimal uroDestCdPod) {
        this.uroDestCdPod = uroDestCdPod;
    }

    public BigDecimal getUrtDestCdNpod() {
        return urtDestCdNpod;
    }

    public void setUrtDestCdNpod(BigDecimal urtDestCdNpod) {
        this.urtDestCdNpod = urtDestCdNpod;
    }

    public BigDecimal getUrtDestCdPod() {
        return urtDestCdPod;
    }

    public void setUrtDestCdPod(BigDecimal urtDestCdPod) {
        this.urtDestCdPod = urtDestCdPod;
    }

    public BigDecimal getCustDroWoOrigRcvtNpod() {
        return custDroWoOrigRcvtNpod;
    }

    public void setCustDroWoOrigRcvtNpod(BigDecimal custDroWoOrigRcvtNpod) {
        this.custDroWoOrigRcvtNpod = custDroWoOrigRcvtNpod;
    }

    public BigDecimal getCustDroWoOrigRcvtPod() {
        return custDroWoOrigRcvtPod;
    }

    public void setCustDroWoOrigRcvtPod(BigDecimal custDroWoOrigRcvtPod) {
        this.custDroWoOrigRcvtPod = custDroWoOrigRcvtPod;
    }

    public BigDecimal getCodPayWoOrigRcvtPod() {
        return codPayWoOrigRcvtPod;
    }

    public void setCodPayWoOrigRcvtPod(BigDecimal codPayWoOrigRcvtPod) {
        this.codPayWoOrigRcvtPod = codPayWoOrigRcvtPod;
    }

    public BigDecimal getCodPayWoOrigRcvtNpod() {
        return codPayWoOrigRcvtNpod;
    }

    public void setCodPayWoOrigRcvtNpod(BigDecimal codPayWoOrigRcvtNpod) {
        this.codPayWoOrigRcvtNpod = codPayWoOrigRcvtNpod;
    }

    public BigDecimal getCodPayWoOthRcvt() {
        return codPayWoOthRcvt;
    }

    public void setCodPayWoOthRcvt(BigDecimal codPayWoOthRcvt) {
        this.codPayWoOthRcvt = codPayWoOthRcvt;
    }

    public BigDecimal getClaimOrigoWoOrigRcvtPod() {
        return claimOrigoWoOrigRcvtPod;
    }

    public void setClaimOrigoWoOrigRcvtPod(BigDecimal claimOrigoWoOrigRcvtPod) {
        this.claimOrigoWoOrigRcvtPod = claimOrigoWoOrigRcvtPod;
    }

    public BigDecimal getClaimOrigtOrigRcvoPod() {
        return claimOrigtOrigRcvoPod;
    }

    public void setClaimOrigtOrigRcvoPod(BigDecimal claimOrigtOrigRcvoPod) {
        this.claimOrigtOrigRcvoPod = claimOrigtOrigRcvoPod;
    }

    public BigDecimal getClaimOrigoWoOrigRcvtNpod() {
        return claimOrigoWoOrigRcvtNpod;
    }

    public void setClaimOrigoWoOrigRcvtNpod(BigDecimal claimOrigoWoOrigRcvtNpod) {
        this.claimOrigoWoOrigRcvtNpod = claimOrigoWoOrigRcvtNpod;
    }

    public BigDecimal getClaimOrigtWoOrigRcvoNpod() {
        return claimOrigtWoOrigRcvoNpod;
    }

    public void setClaimOrigtWoOrigRcvoNpod(BigDecimal claimOrigtWoOrigRcvoNpod) {
        this.claimOrigtWoOrigRcvoNpod = claimOrigtWoOrigRcvoNpod;
    }

    public BigDecimal getClaimDesttIncome() {
        return claimDesttIncome;
    }

    public void setClaimDesttIncome(BigDecimal claimDesttIncome) {
        this.claimDesttIncome = claimDesttIncome;
    }

    public BigDecimal getClaimDestoWoDestRcvoPod() {
        return claimDestoWoDestRcvoPod;
    }

    public void setClaimDestoWoDestRcvoPod(BigDecimal claimDestoWoDestRcvoPod) {
        this.claimDestoWoDestRcvoPod = claimDestoWoDestRcvoPod;
    }

    public BigDecimal getClaimDesttWoDestRcvoPod() {
        return claimDesttWoDestRcvoPod;
    }

    public void setClaimDesttWoDestRcvoPod(BigDecimal claimDesttWoDestRcvoPod) {
        this.claimDesttWoDestRcvoPod = claimDesttWoDestRcvoPod;
    }

    public BigDecimal getClaimDestoWoDestRcvoNpod() {
        return claimDestoWoDestRcvoNpod;
    }

    public void setClaimDestoWoDestRcvoNpod(BigDecimal claimDestoWoDestRcvoNpod) {
        this.claimDestoWoDestRcvoNpod = claimDestoWoDestRcvoNpod;
    }

    public BigDecimal getClaimDesttWoDestRcvoNpod() {
        return claimDesttWoDestRcvoNpod;
    }

    public void setClaimDesttWoDestRcvoNpod(BigDecimal claimDesttWoDestRcvoNpod) {
        this.claimDesttWoDestRcvoNpod = claimDesttWoDestRcvoNpod;
    }

    public BigDecimal getClaimDestoWoDestRcvtPod() {
        return claimDestoWoDestRcvtPod;
    }

    public void setClaimDestoWoDestRcvtPod(BigDecimal claimDestoWoDestRcvtPod) {
        this.claimDestoWoDestRcvtPod = claimDestoWoDestRcvtPod;
    }

    public BigDecimal getClaimDesttWoDestRcvtPod() {
        return claimDesttWoDestRcvtPod;
    }

    public void setClaimDesttWoDestRcvtPod(BigDecimal claimDesttWoDestRcvtPod) {
        this.claimDesttWoDestRcvtPod = claimDesttWoDestRcvtPod;
    }

    public BigDecimal getClaimDestoWoDestRcvtNpod() {
        return claimDestoWoDestRcvtNpod;
    }

    public void setClaimDestoWoDestRcvtNpod(BigDecimal claimDestoWoDestRcvtNpod) {
        this.claimDestoWoDestRcvtNpod = claimDestoWoDestRcvtNpod;
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

    public BigDecimal getOrClaimPayoWoRcvt() {
        return orClaimPayoWoRcvt;
    }

    public void setOrClaimPayoWoRcvt(BigDecimal orClaimPayoWoRcvt) {
        this.orClaimPayoWoRcvt = orClaimPayoWoRcvt;
    }

    public BigDecimal getOrClaimPaytWoRcvo() {
        return orClaimPaytWoRcvo;
    }

    public void setOrClaimPaytWoRcvo(BigDecimal orClaimPaytWoRcvo) {
        this.orClaimPaytWoRcvo = orClaimPaytWoRcvo;
    }

    public BigDecimal getOrCustDroWoRcvt() {
        return orCustDroWoRcvt;
    }

    public void setOrCustDroWoRcvt(BigDecimal orCustDroWoRcvt) {
        this.orCustDroWoRcvt = orCustDroWoRcvt;
    }

    public BigDecimal getRdOrigoWoOrigRcvtPod() {
        return rdOrigoWoOrigRcvtPod;
    }

    public void setRdOrigoWoOrigRcvtPod(BigDecimal rdOrigoWoOrigRcvtPod) {
        this.rdOrigoWoOrigRcvtPod = rdOrigoWoOrigRcvtPod;
    }

    public BigDecimal getRdOrigtWoOrigRcvoPod() {
        return rdOrigtWoOrigRcvoPod;
    }

    public void setRdOrigtWoOrigRcvoPod(BigDecimal rdOrigtWoOrigRcvoPod) {
        this.rdOrigtWoOrigRcvoPod = rdOrigtWoOrigRcvoPod;
    }

    public BigDecimal getRdOrigoWoOrigRcvtNpod() {
        return rdOrigoWoOrigRcvtNpod;
    }

    public void setRdOrigoWoOrigRcvtNpod(BigDecimal rdOrigoWoOrigRcvtNpod) {
        this.rdOrigoWoOrigRcvtNpod = rdOrigoWoOrigRcvtNpod;
    }

    public BigDecimal getRdOrigtWoOrigRcvoNpod() {
        return rdOrigtWoOrigRcvoNpod;
    }

    public void setRdOrigtWoOrigRcvoNpod(BigDecimal rdOrigtWoOrigRcvoNpod) {
        this.rdOrigtWoOrigRcvoNpod = rdOrigtWoOrigRcvoNpod;
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

    public BigDecimal getRdDesttWoDestRcvoPod() {
        return rdDesttWoDestRcvoPod;
    }

    public void setRdDesttWoDestRcvoPod(BigDecimal rdDesttWoDestRcvoPod) {
        this.rdDesttWoDestRcvoPod = rdDesttWoDestRcvoPod;
    }

    public BigDecimal getRdDestoWoDestRcvoNpod() {
        return rdDestoWoDestRcvoNpod;
    }

    public void setRdDestoWoDestRcvoNpod(BigDecimal rdDestoWoDestRcvoNpod) {
        this.rdDestoWoDestRcvoNpod = rdDestoWoDestRcvoNpod;
    }

    public BigDecimal getRdDesttWoDestRcvoNpod() {
        return rdDesttWoDestRcvoNpod;
    }

    public void setRdDesttWoDestRcvoNpod(BigDecimal rdDesttWoDestRcvoNpod) {
        this.rdDesttWoDestRcvoNpod = rdDesttWoDestRcvoNpod;
    }

    public BigDecimal getRdDestoDestRcvtPod() {
        return rdDestoDestRcvtPod;
    }

    public void setRdDestoDestRcvtPod(BigDecimal rdDestoDestRcvtPod) {
        this.rdDestoDestRcvtPod = rdDestoDestRcvtPod;
    }

    public BigDecimal getRdDesttWoDestRcvtPod() {
        return rdDesttWoDestRcvtPod;
    }

    public void setRdDesttWoDestRcvtPod(BigDecimal rdDesttWoDestRcvtPod) {
        this.rdDesttWoDestRcvtPod = rdDesttWoDestRcvtPod;
    }

    public BigDecimal getRdDestoWoDestRcvtNpod() {
        return rdDestoWoDestRcvtNpod;
    }

    public void setRdDestoWoDestRcvtNpod(BigDecimal rdDestoWoDestRcvtNpod) {
        this.rdDestoWoDestRcvtNpod = rdDestoWoDestRcvtNpod;
    }

    public BigDecimal getRdDesttWoDestRcvtNpod() {
        return rdDesttWoDestRcvtNpod;
    }

    public void setRdDesttWoDestRcvtNpod(BigDecimal rdDesttWoDestRcvtNpod) {
        this.rdDesttWoDestRcvtNpod = rdDesttWoDestRcvtNpod;
    }

    public BigDecimal getClaimDestoIncome() {
        return claimDestoIncome;
    }

    public void setClaimDestoIncome(BigDecimal claimDestoIncome) {
        this.claimDestoIncome = claimDestoIncome;
    }

    public BigDecimal getRdDestoIncome() {
        return rdDestoIncome;
    }

    public void setRdDestoIncome(BigDecimal rdDestoIncome) {
        this.rdDestoIncome = rdDestoIncome;
    }

    public BigDecimal getRdDesttIncome() {
        return rdDesttIncome;
    }

    public void setRdDesttIncome(BigDecimal rdDesttIncome) {
        this.rdDesttIncome = rdDesttIncome;
    }
}