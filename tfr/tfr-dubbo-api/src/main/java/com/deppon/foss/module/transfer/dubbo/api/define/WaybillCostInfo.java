package com.deppon.foss.module.transfer.dubbo.api.define;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * <p>
 * Crm系统查询运单信息之费用信息
 * </p>
 * @title WaybillCostInfo.java
 * @package com.deppon.foss.module.pickup.waybill.shared.domain 
 * @author suyujun
 * @version 0.1 2012-12-18
 */
public class WaybillCostInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 费用类型
	 */
    private String costType;
    /**
     * 费用名称
     */
    private String costName;
    /**
     * 金额
     */
    private BigDecimal costMoney;
	/**
	 * @return the costType
	 */
	public String getCostType() {
		return costType;
	}
	/**
	 * @param costType the costType to set
	 */
	public void setCostType(String costType) {
		this.costType = costType;
	}
	/**
	 * @return the costName
	 */
	public String getCostName() {
		return costName;
	}
	/**
	 * @param costName the costName to set
	 */
	public void setCostName(String costName) {
		this.costName = costName;
	}
	/**
	 * @return the costMoney
	 */
	public BigDecimal getCostMoney() {
		return costMoney;
	}
	/**
	 * @param costMoney the costMoney to set
	 */
	public void setCostMoney(BigDecimal costMoney) {
		this.costMoney = costMoney;
	}
	
}