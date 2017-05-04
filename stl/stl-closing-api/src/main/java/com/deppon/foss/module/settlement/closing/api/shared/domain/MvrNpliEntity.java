package com.deppon.foss.module.settlement.closing.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 始发偏线往来月报表
 * 
 * @author 163576-foss-guxinhua
 * @date 2013-11-6 下午5:56:31
 */
public class MvrNpliEntity extends BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5425120526349733732L;

	private String id;

	private String period;

	private String productCode;

	private String customerCode;

	private String customerName;

	private String orgCode;

	private String orgName;

	private String orgType;

	private String orgUnifiedCode;

	private String customerType;

	private String invoiceMark;

	private BigDecimal uropDestChNpod;

	private BigDecimal uropDestChPod;

	private BigDecimal urtpDestCdNpod;

	private BigDecimal urtpDestCdPod;

	private BigDecimal plCostWoDestRcvtNpod;

	private BigDecimal plDrWoDestRcvtPod;

	private BigDecimal uropDestCdNpod;

	private BigDecimal uropDestCdPod;

	private BigDecimal urtpDestChNpod;

	private BigDecimal urtpDestChPod;

	private BigDecimal plCostWoDestRcvoPod;

	private BigDecimal plCostWoDestRcvoNpod;

	private BigDecimal plCostWoDestRcvtPod;

	private BigDecimal plDrWoDestRcvoPod;

	private BigDecimal plDrWoDestRcvoNpod;

	private BigDecimal plDrWoDestRcvtNpod;

	/**
	 * 其他应付冲01应收到付运费已签收
	 */
	private BigDecimal popWoDroPod;

	/**
	 * 其他应付冲01应收到付运费未签收
	 */
	private BigDecimal popWoDroNpod;

	/**
	 * 其他应付冲02应收到付运费已签收
	 */
	private BigDecimal popWoDrtPod;

	/**
	 * 其他应付冲02应收到付运费未签收
	 */
	private BigDecimal popWoDrtNpod;

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

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
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

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getOrgUnifiedCode() {
		return orgUnifiedCode;
	}

	public void setOrgUnifiedCode(String orgUnifiedCode) {
		this.orgUnifiedCode = orgUnifiedCode;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getInvoiceMark() {
		return invoiceMark;
	}

	public void setInvoiceMark(String invoiceMark) {
		this.invoiceMark = invoiceMark;
	}

	public BigDecimal getUropDestChNpod() {
		return uropDestChNpod;
	}

	public void setUropDestChNpod(BigDecimal uropDestChNpod) {
		this.uropDestChNpod = uropDestChNpod;
	}

	public BigDecimal getUropDestChPod() {
		return uropDestChPod;
	}

	public void setUropDestChPod(BigDecimal uropDestChPod) {
		this.uropDestChPod = uropDestChPod;
	}

	public BigDecimal getUrtpDestCdNpod() {
		return urtpDestCdNpod;
	}

	public void setUrtpDestCdNpod(BigDecimal urtpDestCdNpod) {
		this.urtpDestCdNpod = urtpDestCdNpod;
	}

	public BigDecimal getUrtpDestCdPod() {
		return urtpDestCdPod;
	}

	public void setUrtpDestCdPod(BigDecimal urtpDestCdPod) {
		this.urtpDestCdPod = urtpDestCdPod;
	}

	public BigDecimal getPlCostWoDestRcvtNpod() {
		return plCostWoDestRcvtNpod;
	}

	public void setPlCostWoDestRcvtNpod(BigDecimal plCostWoDestRcvtNpod) {
		this.plCostWoDestRcvtNpod = plCostWoDestRcvtNpod;
	}

	public BigDecimal getPlDrWoDestRcvtPod() {
		return plDrWoDestRcvtPod;
	}

	public void setPlDrWoDestRcvtPod(BigDecimal plDrWoDestRcvtPod) {
		this.plDrWoDestRcvtPod = plDrWoDestRcvtPod;
	}

	public BigDecimal getUropDestCdNpod() {
		return uropDestCdNpod;
	}

	public void setUropDestCdNpod(BigDecimal uropDestCdNpod) {
		this.uropDestCdNpod = uropDestCdNpod;
	}

	public BigDecimal getUropDestCdPod() {
		return uropDestCdPod;
	}

	public void setUropDestCdPod(BigDecimal uropDestCdPod) {
		this.uropDestCdPod = uropDestCdPod;
	}

	public BigDecimal getUrtpDestChNpod() {
		return urtpDestChNpod;
	}

	public void setUrtpDestChNpod(BigDecimal urtpDestChNpod) {
		this.urtpDestChNpod = urtpDestChNpod;
	}

	public BigDecimal getUrtpDestChPod() {
		return urtpDestChPod;
	}

	public void setUrtpDestChPod(BigDecimal urtpDestChPod) {
		this.urtpDestChPod = urtpDestChPod;
	}

	public BigDecimal getPlCostWoDestRcvoPod() {
		return plCostWoDestRcvoPod;
	}

	public void setPlCostWoDestRcvoPod(BigDecimal plCostWoDestRcvoPod) {
		this.plCostWoDestRcvoPod = plCostWoDestRcvoPod;
	}

	public BigDecimal getPlCostWoDestRcvoNpod() {
		return plCostWoDestRcvoNpod;
	}

	public void setPlCostWoDestRcvoNpod(BigDecimal plCostWoDestRcvoNpod) {
		this.plCostWoDestRcvoNpod = plCostWoDestRcvoNpod;
	}

	public BigDecimal getPlCostWoDestRcvtPod() {
		return plCostWoDestRcvtPod;
	}

	public void setPlCostWoDestRcvtPod(BigDecimal plCostWoDestRcvtPod) {
		this.plCostWoDestRcvtPod = plCostWoDestRcvtPod;
	}

	public BigDecimal getPlDrWoDestRcvoPod() {
		return plDrWoDestRcvoPod;
	}

	public void setPlDrWoDestRcvoPod(BigDecimal plDrWoDestRcvoPod) {
		this.plDrWoDestRcvoPod = plDrWoDestRcvoPod;
	}

	public BigDecimal getPlDrWoDestRcvoNpod() {
		return plDrWoDestRcvoNpod;
	}

	public void setPlDrWoDestRcvoNpod(BigDecimal plDrWoDestRcvoNpod) {
		this.plDrWoDestRcvoNpod = plDrWoDestRcvoNpod;
	}

	public BigDecimal getPlDrWoDestRcvtNpod() {
		return plDrWoDestRcvtNpod;
	}

	public void setPlDrWoDestRcvtNpod(BigDecimal plDrWoDestRcvtNpod) {
		this.plDrWoDestRcvtNpod = plDrWoDestRcvtNpod;
	}

	public BigDecimal getPopWoDroPod() {
		return popWoDroPod;
	}

	public void setPopWoDroPod(BigDecimal popWoDroPod) {
		this.popWoDroPod = popWoDroPod;
	}

	public BigDecimal getPopWoDroNpod() {
		return popWoDroNpod;
	}

	public void setPopWoDroNpod(BigDecimal popWoDroNpod) {
		this.popWoDroNpod = popWoDroNpod;
	}

	public BigDecimal getPopWoDrtPod() {
		return popWoDrtPod;
	}

	public void setPopWoDrtPod(BigDecimal popWoDrtPod) {
		this.popWoDrtPod = popWoDrtPod;
	}

	public BigDecimal getPopWoDrtNpod() {
		return popWoDrtNpod;
	}

	public void setPopWoDrtNpod(BigDecimal popWoDrtNpod) {
		this.popWoDrtNpod = popWoDrtNpod;
	}

}