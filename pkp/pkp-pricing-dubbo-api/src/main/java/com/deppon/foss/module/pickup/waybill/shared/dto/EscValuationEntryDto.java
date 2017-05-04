package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 计价条目
 *  
 * 葛亮亮 二〇一六年五月八日 12:22:23
 */
public class EscValuationEntryDto implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 8991819641145697529L;
	
	/**
     * 费用类型代码
     */
    private String  priceEntityCode;
    
    /**
     * 原始费用（增值服务具备）
     */
    private BigDecimal originnalCost;
	
    /**
     * 增值服务子类型（增值服务具备）
     * 子类型   对于代收货款：为退款类型(即日退，三日退，审核退) 对于签收回单：为返单类型（传真返单，原件返单）
	 * 对于其它费用：为费用类型名称（综合信息费，燃油附加费，中转费等）
     */
    private String subType;

	public String getPriceEntityCode() {
		return priceEntityCode;
	}

	public void setPriceEntityCode(String priceEntityCode) {
		this.priceEntityCode = priceEntityCode;
	}

	public BigDecimal getOriginnalCost() {
		return originnalCost;
	}

	public void setOriginnalCost(BigDecimal originnalCost) {
		this.originnalCost = originnalCost;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}
    
    
}
