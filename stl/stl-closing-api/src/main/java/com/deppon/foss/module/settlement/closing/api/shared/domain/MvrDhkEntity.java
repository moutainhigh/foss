package com.deppon.foss.module.settlement.closing.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 代汇款报表
 * @author 163576-foss-guxinhua
 * @date 2013-12-05 下午4:38:04
 */
public class MvrDhkEntity implements Serializable{

	private static final long serialVersionUID = 1440262686117156949L;

	private String id;

    private String period;

    private String remitOrgCode;

    private String remitOrgName;

    private String byremitOrgCode;

    private String byremitOrgName;

    private BigDecimal amount;

    private String collectionType;

    private Date voucherBeginTime;

    private Date voucherEndTime;

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

    public String getRemitOrgCode() {
        return remitOrgCode;
    }

    public void setRemitOrgCode(String remitOrgCode) {
        this.remitOrgCode = remitOrgCode;
    }

    public String getRemitOrgName() {
        return remitOrgName;
    }

    public void setRemitOrgName(String remitOrgName) {
        this.remitOrgName = remitOrgName;
    }

    public String getByremitOrgCode() {
        return byremitOrgCode;
    }

    public void setByremitOrgCode(String byremitOrgCode) {
        this.byremitOrgCode = byremitOrgCode;
    }

    public String getByremitOrgName() {
        return byremitOrgName;
    }

    public void setByremitOrgName(String byremitOrgName) {
        this.byremitOrgName = byremitOrgName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
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
}