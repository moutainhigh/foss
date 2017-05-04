
package com.deppon.foss.shared.response;

import java.math.BigDecimal;


/**
 * 运单费用信息
 */
public class WaybillCostInfo {

    protected String costType;
    protected String costName;
    protected BigDecimal costMoney;

    /**
     * Gets the value of the costType property.
     */
    public String getCostType() {
        return costType;
    }

    /**
     * Sets the value of the costType property.
     */
    public void setCostType(String value) {
        this.costType = value;
    }

    /**
     * Gets the value of the costName property.
     * 
     *     
     */
    public String getCostName() {
        return costName;
    }

    /**
     * Sets the value of the costName property.
     * 
     */
    public void setCostName(String value) {
        this.costName = value;
    }

    /**
     * Gets the value of the costMoney property.
     * 
     *     
     */
    public BigDecimal getCostMoney() {
        return costMoney;
    }

    /**
     * Sets the value of the costMoney property.
     * 
     */
    public void setCostMoney(BigDecimal value) {
        this.costMoney = value;
    }

}
