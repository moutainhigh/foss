package com.deppon.foss.module.pickup.waybill.shared.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class EscWayBillValueAddDetaillVo implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 费用编码
	 */
	private String  subType;
	
	/**
	 * 费用名称
	 */
	private String subTypeName;
	/**
	 * 归集类别 code
	 */
	private String belongToPriceEntityCode;
	/**
	 * 归集类型名称
	 */
	private String belongToPriceEntityName;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 金额
	 */
	private BigDecimal fee;
	/**
	 * 上限
	 */
	private BigDecimal maxFee;
	/**
	 * 下限
	 */
	private BigDecimal minFee;
	/**
	 * 是否可修改
	 */
	private String canmodify;
	/**
	 * 是否可删除
	 */
	private String candelete;
	public String getBelongToPriceEntityCode() {
		return belongToPriceEntityCode;
	}
	public void setBelongToPriceEntityCode(String belongToPriceEntityCode) {
		this.belongToPriceEntityCode = belongToPriceEntityCode;
	}
	public String getBelongToPriceEntityName() {
		return belongToPriceEntityName;
	}
	public void setBelongToPriceEntityName(String belongToPriceEntityName) {
		this.belongToPriceEntityName = belongToPriceEntityName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	public BigDecimal getMaxFee() {
		return maxFee;
	}
	public void setMaxFee(BigDecimal maxFee) {
		this.maxFee = maxFee;
	}
	public BigDecimal getMinFee() {
		return minFee;
	}
	public void setMinFee(BigDecimal minFee) {
		this.minFee = minFee;
	}
	public String getCanmodify() {
		return canmodify;
	}
	public void setCanmodify(String canmodify) {
		this.canmodify = canmodify;
	}
	public String getCandelete() {
		return candelete;
	}
	public void setCandelete(String candelete) {
		this.candelete = candelete;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public String getSubTypeName() {
		return subTypeName;
	}
	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}
	
}
