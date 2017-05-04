/**
 *  initial comments.
 */
package com.deppon.foss.module.transfer.dubbo.api.define;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 运单费用dto
 * @author 043258-foss-zhaobin
 * @date 2013-1-6 下午9:11:54
 */
public class WaybillChargeCostDto implements Serializable
{

	private static final long serialVersionUID = 1L;
	/**
	 * 费用代码
	 */
    protected String costType;
    /**
     * 费用名称
     */
    protected String costName;
    /**
     * 金额
     */
    protected BigDecimal costMoney;
    
    
	public String getCostType()
	{
		return costType;
	}
	public void setCostType(String costType)
	{
		this.costType = costType;
	}
	public String getCostName()
	{
		return costName;
	}
	public void setCostName(String costName)
	{
		this.costName = costName;
	}
	public BigDecimal getCostMoney()
	{
		return costMoney;
	}
	public void setCostMoney(BigDecimal costMoney)
	{
		this.costMoney = costMoney;
	}
	
}