package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 网上支付汇款信息
 * Created by 105762 on 2014/9/24.
 */
public class OnlinePayInfoDto {

    /**
     * 汇款人
     */
    private String remitterName;

    /**
     * 汇款时间
     */
    private Date remittanceTime;
    /**
     * 未使用金额
     */
    private BigDecimal unuseredAmounts;

    public void setRemitterName(String remitterName) {
        this.remitterName = remitterName;
    }

    public void setRemittanceTime(Date remittanceTime) {
        this.remittanceTime = remittanceTime;
    }

    public void setUnuseredAmounts(BigDecimal unuseredAmounts) {
        this.unuseredAmounts = unuseredAmounts;
    }

    public String getRemitterName() {
        return remitterName;
    }

    public Date getRemittanceTime() {
        return remittanceTime;
    }

    public BigDecimal getUnuseredAmounts() {
        return unuseredAmounts;
    }
}
