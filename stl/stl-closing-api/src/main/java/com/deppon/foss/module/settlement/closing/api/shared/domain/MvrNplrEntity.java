package com.deppon.foss.module.settlement.closing.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 偏线月报表
 * 
 * @author 163576-foss-guxinhua
 * @date 2013-11-6 下午5:56:31
 */
public class MvrNplrEntity extends BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6854828070735965953L;

	private String id;

	private String period;

	private String productCode;

	private String customerCode;

	private String customerName;

	private String origOrgCode;

	private String origOrgName;

	private String destOrgCode;

	private String destOrgName;

	private String origUnifiedCode;

	private String destUnifiedCode;

	private String customerType;

	private BigDecimal uropDestChNpod;

	private BigDecimal uropDestCdNpod;

	private BigDecimal uropDestChPod;

	private BigDecimal uropDestCdPod;

	private BigDecimal urtpDestChNpod;

	private BigDecimal urtpDestCdNpod;

	private BigDecimal urtpDestChPod;

	private BigDecimal urtpDestCdPod;

	private BigDecimal plCostWoDestRcvoPod;

	private BigDecimal plCostWoDestRcvoNpod;

	private BigDecimal plCostWoDestRcvtPod;

	private BigDecimal plCostWoDestRcvtNpod;

	private BigDecimal plCostEntry;

	private BigDecimal plCostConfirm;

	private BigDecimal plCostNconfirm;

	private BigDecimal plCostPayApply;

	private BigDecimal plDrWoDestRcvtPod;

	private BigDecimal plDrCh;

	private BigDecimal plDrWoDestRcvoPod;

	private BigDecimal plDrWoDestRcvoNpod;

	private BigDecimal plDrWoDestRcvtNpod;

	private BigDecimal plDrCd;

	private BigDecimal plDrPayApply;

	/**
	 * 其它应付成本确认
	 */
	private BigDecimal plCostOpCrm;

	/**
	 * 预收偏线代理冲其他应收
	 */
	private BigDecimal plDrWoOr;

	/**
	 * 偏线其他应收录入
	 */
	private BigDecimal porEntry;

	/**
	 * 还款偏线其他应收现金
	 */
	private BigDecimal urPorCh;

	/**
	 * 还款偏线其他应收银行
	 */
	private BigDecimal urPorCd;

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

	/**
	 * 成本应付其他应付冲其他应收
	 */
	private BigDecimal pocpWoOr;

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

	public String getOrigUnifiedCode() {
		return origUnifiedCode;
	}

	public void setOrigUnifiedCode(String origUnifiedCode) {
		this.origUnifiedCode = origUnifiedCode;
	}

	public String getDestUnifiedCode() {
		return destUnifiedCode;
	}

	public void setDestUnifiedCode(String destUnifiedCode) {
		this.destUnifiedCode = destUnifiedCode;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public BigDecimal getUropDestChNpod() {
		return uropDestChNpod;
	}

	public void setUropDestChNpod(BigDecimal uropDestChNpod) {
		this.uropDestChNpod = uropDestChNpod;
	}

	public BigDecimal getUropDestCdNpod() {
		return uropDestCdNpod;
	}

	public void setUropDestCdNpod(BigDecimal uropDestCdNpod) {
		this.uropDestCdNpod = uropDestCdNpod;
	}

	public BigDecimal getUropDestChPod() {
		return uropDestChPod;
	}

	public void setUropDestChPod(BigDecimal uropDestChPod) {
		this.uropDestChPod = uropDestChPod;
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

	public BigDecimal getUrtpDestCdNpod() {
		return urtpDestCdNpod;
	}

	public void setUrtpDestCdNpod(BigDecimal urtpDestCdNpod) {
		this.urtpDestCdNpod = urtpDestCdNpod;
	}

	public BigDecimal getUrtpDestChPod() {
		return urtpDestChPod;
	}

	public void setUrtpDestChPod(BigDecimal urtpDestChPod) {
		this.urtpDestChPod = urtpDestChPod;
	}

	public BigDecimal getUrtpDestCdPod() {
		return urtpDestCdPod;
	}

	public void setUrtpDestCdPod(BigDecimal urtpDestCdPod) {
		this.urtpDestCdPod = urtpDestCdPod;
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

	public BigDecimal getPlCostWoDestRcvtNpod() {
		return plCostWoDestRcvtNpod;
	}

	public void setPlCostWoDestRcvtNpod(BigDecimal plCostWoDestRcvtNpod) {
		this.plCostWoDestRcvtNpod = plCostWoDestRcvtNpod;
	}

	public BigDecimal getPlCostEntry() {
		return plCostEntry;
	}

	public void setPlCostEntry(BigDecimal plCostEntry) {
		this.plCostEntry = plCostEntry;
	}

	public BigDecimal getPlCostConfirm() {
		return plCostConfirm;
	}

	public void setPlCostConfirm(BigDecimal plCostConfirm) {
		this.plCostConfirm = plCostConfirm;
	}

	public BigDecimal getPlCostNconfirm() {
		return plCostNconfirm;
	}

	public void setPlCostNconfirm(BigDecimal plCostNconfirm) {
		this.plCostNconfirm = plCostNconfirm;
	}

	public BigDecimal getPlCostPayApply() {
		return plCostPayApply;
	}

	public void setPlCostPayApply(BigDecimal plCostPayApply) {
		this.plCostPayApply = plCostPayApply;
	}

	public BigDecimal getPlDrWoDestRcvtPod() {
		return plDrWoDestRcvtPod;
	}

	public void setPlDrWoDestRcvtPod(BigDecimal plDrWoDestRcvtPod) {
		this.plDrWoDestRcvtPod = plDrWoDestRcvtPod;
	}

	public BigDecimal getPlDrCh() {
		return plDrCh;
	}

	public void setPlDrCh(BigDecimal plDrCh) {
		this.plDrCh = plDrCh;
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

	public BigDecimal getPlDrCd() {
		return plDrCd;
	}

	public void setPlDrCd(BigDecimal plDrCd) {
		this.plDrCd = plDrCd;
	}

	public BigDecimal getPlDrPayApply() {
		return plDrPayApply;
	}

	public void setPlDrPayApply(BigDecimal plDrPayApply) {
		this.plDrPayApply = plDrPayApply;
	}

	public BigDecimal getPlCostOpCrm() {
		return plCostOpCrm;
	}

	public void setPlCostOpCrm(BigDecimal plCostOpCrm) {
		this.plCostOpCrm = plCostOpCrm;
	}

	public BigDecimal getPlDrWoOr() {
		return plDrWoOr;
	}

	public void setPlDrWoOr(BigDecimal plDrWoOr) {
		this.plDrWoOr = plDrWoOr;
	}

	public BigDecimal getPorEntry() {
		return porEntry;
	}

	public void setPorEntry(BigDecimal porEntry) {
		this.porEntry = porEntry;
	}

	public BigDecimal getUrPorCh() {
		return urPorCh;
	}

	public void setUrPorCh(BigDecimal urPorCh) {
		this.urPorCh = urPorCh;
	}

	public BigDecimal getUrPorCd() {
		return urPorCd;
	}

	public void setUrPorCd(BigDecimal urPorCd) {
		this.urPorCd = urPorCd;
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

	public BigDecimal getPocpWoOr() {
		return pocpWoOr;
	}

	public void setPocpWoOr(BigDecimal pocpWoOr) {
		this.pocpWoOr = pocpWoOr;
	}

}