package com.deppon.foss.module.settlement.closing.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 功能：代打木架
 * @author 045738
 */
public class MvrWoodenEntity implements Serializable{
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -8086698582451093214L;

	/**
	 * id
	 */
	private String id;
	
	/**
	 * 期间
	 */
    private String period;

    /**
     * 客户编码
     */
    private String customerCode;
    
    /**
     * 客户名称
     */
    private String customerName;
    
    /**
     * 部门编码
     */
    private String origOrgCode;

    /**
     * 部门名称
     */
    private String origOrgName;

    /**
     * 包装入成本
     */
    private BigDecimal woodenCost;
    
    /**
     * 包装其他应收录入
     */
    private BigDecimal wrEntry;
    
    /**
     * 包装其他应付录入
     */
    private BigDecimal wpEntry;
    
    /**
     * 应付冲其他应收
     */
    private BigDecimal wothPayWoOthRc;
    
    /**
     * 打木架付款申请
     */
    private BigDecimal woodenOrigPayTail;
    
    /**
     * 部门编码
     */
    private String destOrgCode;

    /**
     * 部门名称
     */
    private String destOrgName;

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

	public String getOrigOrgCode() {
		return origOrgCode;
	}

	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	public String getOrigOrgName() {
		return origOrgName;
	}

	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	public BigDecimal getWoodenCost() {
		return woodenCost;
	}

	public void setWoodenCost(BigDecimal woodenCost) {
		this.woodenCost = woodenCost;
	}

	public BigDecimal getWrEntry() {
		return wrEntry;
	}

	public void setWrEntry(BigDecimal wrEntry) {
		this.wrEntry = wrEntry;
	}

	public BigDecimal getWpEntry() {
		return wpEntry;
	}

	public void setWpEntry(BigDecimal wpEntry) {
		this.wpEntry = wpEntry;
	}

	public BigDecimal getWoodenOrigPayTail() {
		return woodenOrigPayTail;
	}

	public void setWoodenOrigPayTail(BigDecimal woodenOrigPayTail) {
		this.woodenOrigPayTail = woodenOrigPayTail;
	}

	public String getDestOrgCode() {
		return destOrgCode;
	}

	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	public String getDestOrgName() {
		return destOrgName;
	}

	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	public BigDecimal getWothPayWoOthRc() {
		return wothPayWoOthRc;
	}

	public void setWothPayWoOthRc(BigDecimal wothPayWoOthRc) {
		this.wothPayWoOthRc = wothPayWoOthRc;
	}

    
}
