
package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.math.BigDecimal;


/**
 * 运单费用信息
 * 
 * 
 */
public class WaybillCostInfoForApp {
	private String costType;
	private String costName;
	private BigDecimal costMoney;

    public String getCostType() {
        return costType;
    }

    public void setCostType(String value) {
        this.costType = value;
    }

    public String getCostName() {
        return costName;
    }

    public void setCostName(String value) {
        this.costName = value;
    }

    public BigDecimal getCostMoney() {
        return costMoney;
    }

    public void setCostMoney(BigDecimal value) {
        this.costMoney = value;
    }

}
