package com.deppon.foss.module.pickup.waybill.server.utils.cubc.model;

import java.math.BigDecimal;

/**
 * WMS仓储费用项实体类
 * Created by 343451 on 2016/12/30.
 */
public class FeeType {

    /**
     * 费用项名称
     */
    private String name;
    /**
     * 费用金额
     */
    private BigDecimal money;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "FeeType [name=" + name + ", money=" + money + "]";
    }
    
}
