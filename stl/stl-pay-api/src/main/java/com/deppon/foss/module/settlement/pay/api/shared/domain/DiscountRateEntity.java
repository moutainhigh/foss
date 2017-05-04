package com.deppon.foss.module.settlement.pay.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

public class DiscountRateEntity extends BaseEntity {
    private String id;

    private String customerCode;

    private String customerName;

    private BigDecimal codAmount;

    private BigDecimal insuranceAmount;

    private BigDecimal transportAmount;

    private BigDecimal codRate;

    private BigDecimal insuranceRate;

    private BigDecimal transportRate;

    private Date createTime;

    private Long codCrmId;

    private Long insuranceCrmId;

    private Long transportCrmId;

    private String createType;

    private String active;

    private Date modifyTime;

    private String modifyUserCode;

    private String modifyUserName;

    private String createUserCode;

    private String createUserName;

    private Date effectMonth;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public BigDecimal getCodAmount() {
        return codAmount;
    }

    public void setCodAmount(BigDecimal codAmount) {
        this.codAmount = codAmount;
    }

    public BigDecimal getInsuranceAmount() {
        return insuranceAmount;
    }

    public void setInsuranceAmount(BigDecimal insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    public BigDecimal getTransportAmount() {
        return transportAmount;
    }

    public void setTransportAmount(BigDecimal transportAmount) {
        this.transportAmount = transportAmount;
    }

    public BigDecimal getCodRate() {
        return codRate;
    }

    public void setCodRate(BigDecimal codRate) {
        this.codRate = codRate;
    }

    public BigDecimal getInsuranceRate() {
        return insuranceRate;
    }

    public void setInsuranceRate(BigDecimal insuranceRate) {
        this.insuranceRate = insuranceRate;
    }

    public BigDecimal getTransportRate() {
        return transportRate;
    }

    public void setTransportRate(BigDecimal transportRate) {
        this.transportRate = transportRate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCodCrmId() {
        return codCrmId;
    }

    public void setCodCrmId(Long codCrmId) {
        this.codCrmId = codCrmId;
    }

    public Long getInsuranceCrmId() {
        return insuranceCrmId;
    }

    public void setInsuranceCrmId(Long insuranceCrmId) {
        this.insuranceCrmId = insuranceCrmId;
    }

    public Long getTransportCrmId() {
        return transportCrmId;
    }

    public void setTransportCrmId(Long transportCrmId) {
        this.transportCrmId = transportCrmId;
    }

    public String getCreateType() {
        return createType;
    }

    public void setCreateType(String createType) {
        this.createType = createType;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUserCode() {
        return modifyUserCode;
    }

    public void setModifyUserCode(String modifyUserCode) {
        this.modifyUserCode = modifyUserCode;
    }

    public String getModifyUserName() {
        return modifyUserName;
    }

    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName;
    }

    public String getCreateUserCode() {
        return createUserCode;
    }

    public void setCreateUserCode(String createUserCode) {
        this.createUserCode = createUserCode;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Date getEffectMonth() {
        return effectMonth;
    }

    public void setEffectMonth(Date effectMonth) {
        this.effectMonth = effectMonth;
    }
}