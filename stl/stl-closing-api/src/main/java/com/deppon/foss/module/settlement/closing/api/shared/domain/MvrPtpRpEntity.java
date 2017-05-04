/**
 * 
 */
package com.deppon.foss.module.settlement.closing.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 231438
 *
 */
public class MvrPtpRpEntity implements Serializable{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6264807713854753335L;

	/**
	 * id
	 */
	public String id;
	/**
	 * 期间
	 */
	public String period;
	/**
	 * 业务类型（运输性质）
	 */
	public String productCode;
	/**
	 * 客户编码
	 */
	public String customerCode;
	/**
	 * 客户名称
	 */
	public String customerName;
	/**
	 * 收入部门编码
	 */
	public String generatingOrgCode;
	/**
	 * 收入部门名称
	 */
	public String generatingOrgName;
	/**
	 * 费用承担部门编码
	 */
	public String expenseBearCode;
	/**
	 * 费用承担部门名称
	 */
	public String expenseBearName;
	/**
	 * 应付部门编码
	 */
	public String payOrgCode;
	/**
	 * 应付部门编码
	 */
	public String payOrgName;
	/**
	 * 应收部门编码
	 */
	public String recOrgCode;
	/**
	 * 应收部门名称
	 */
	public String recOrgName;
	/**
	 * 合伙人罚款应收
	 */
	public BigDecimal ptpPen;
	/**
	 * 快递差错应收
	 */
	public BigDecimal ptpErrEer;
	/**
	 * 抢货应收
	 */
	public BigDecimal ptpErrBgr;
	/**
	 * 网上支付开单金额应收
	 */
	public BigDecimal ptpErrOpbar;
	/**
	 * 异常签收应收
	 */
	public BigDecimal ptpErrEsr;
	/**
	 * 派送时效应收
	 */
	public BigDecimal ptpErrSlr;
	/**
	 * 超重超方成本提取应收
	 */
	public BigDecimal ptpErrOwoscer;
	/**
	 * 投诉应收
	 */
	public BigDecimal ptpErrRcr;
	/**
	 * 网上支付差错应收
	 */
	public BigDecimal ptpErrOper;
	/**
	 * 抢货差错应收
	 */
	public BigDecimal ptpErrBer;
	/**
	 * 理赔冲成本
	 */
	public BigDecimal ptpErrSpc;
	/**
	 * 理赔入收入
	 */
	public BigDecimal ptpErrSei;
	/**
	 * 代打木架应收
	 */
	public BigDecimal ptpErrHwfr;
	/**
	 * 培训费收款
	 */
	public BigDecimal ptpTrTcc;
	/**
	 * 会务费收款
	 */
	public BigDecimal ptpTrRfc;
	/**
	 * 上报快递差错应付
	 */
	public BigDecimal ptpLtlrUltlp;
	/**
	 * 抢货应付
	 */
	public BigDecimal ptpLtlrBgp;
	/**
	 * 合伙人奖励应付
	 */
	public BigDecimal ptpLtlrPtprp;
	/**
	 * 其他应收冲其他应付
	 */
	public BigDecimal ptpOtherRtp;
	/**
	 * 奖励付款申请
	 */
	public BigDecimal ptpLpaRpa;
	
	/**
     * 凭证报表优化新增 2017-02-28
     * 奖励应付付款申请  奖励自动返需求 355019
     */
    private BigDecimal ptpLpaRppa;
    
    /**
     * 凭证报表优化新增 2017-02-28
     * 快递差错应付付款申请 奖励自动返需求 355019
     */
    private BigDecimal ptpLpaEeppa;
    
	/**
	 * 开始时间
	 */
	public Date voucherBeginTime;
	/**
	 * 结束时间
	 */
	public Date voucherEndTime;


	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getPeriod() {
		return period;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setGeneratingOrgCode(String generatingOrgCode) {
		this.generatingOrgCode = generatingOrgCode;
	}

	public String getGeneratingOrgCode() {
		return generatingOrgCode;
	}

	public void setGeneratingOrgName(String generatingOrgName) {
		this.generatingOrgName = generatingOrgName;
	}

	public String getGeneratingOrgName() {
		return generatingOrgName;
	}

	public void setExpenseBearCode(String expenseBearCode) {
		this.expenseBearCode = expenseBearCode;
	}

	public String getExpenseBearCode() {
		return expenseBearCode;
	}

	public void setExpenseBearName(String expenseBearName) {
		this.expenseBearName = expenseBearName;
	}

	public String getExpenseBearName() {
		return expenseBearName;
	}

	public void setPayOrgCode(String payOrgCode) {
		this.payOrgCode = payOrgCode;
	}

	public String getPayOrgCode() {
		return payOrgCode;
	}

	public void setPayOrgName(String payOrgName) {
		this.payOrgName = payOrgName;
	}

	public String getPayOrgName() {
		return payOrgName;
	}

	public void setRecOrgCode(String recOrgCode) {
		this.recOrgCode = recOrgCode;
	}

	public String getRecOrgCode() {
		return recOrgCode;
	}

	public void setRecOrgName(String recOrgName) {
		this.recOrgName = recOrgName;
	}

	public String getRecOrgName() {
		return recOrgName;
	}

	public void setPtpPen(BigDecimal ptpPen) {
		this.ptpPen = ptpPen;
	}

	public BigDecimal getPtpPen() {
		return ptpPen;
	}

	public void setPtpErrEer(BigDecimal ptpErrEer) {
		this.ptpErrEer = ptpErrEer;
	}

	public BigDecimal getPtpErrEer() {
		return ptpErrEer;
	}

	public void setPtpErrBgr(BigDecimal ptpErrBgr) {
		this.ptpErrBgr = ptpErrBgr;
	}

	public BigDecimal getPtpErrBgr() {
		return ptpErrBgr;
	}

	public void setPtpErrOpbar(BigDecimal ptpErrOpbar) {
		this.ptpErrOpbar = ptpErrOpbar;
	}

	public BigDecimal getPtpErrOpbar() {
		return ptpErrOpbar;
	}

	public void setPtpErrEsr(BigDecimal ptpErrEsr) {
		this.ptpErrEsr = ptpErrEsr;
	}

	public BigDecimal getPtpErrEsr() {
		return ptpErrEsr;
	}

	public void setPtpErrSlr(BigDecimal ptpErrSlr) {
		this.ptpErrSlr = ptpErrSlr;
	}

	public BigDecimal getPtpErrSlr() {
		return ptpErrSlr;
	}

	public void setPtpErrOwoscer(BigDecimal ptpErrOwoscer) {
		this.ptpErrOwoscer = ptpErrOwoscer;
	}

	public BigDecimal getPtpErrOwoscer() {
		return ptpErrOwoscer;
	}

	public void setPtpErrRcr(BigDecimal ptpErrRcr) {
		this.ptpErrRcr = ptpErrRcr;
	}

	public BigDecimal getPtpErrRcr() {
		return ptpErrRcr;
	}

	public void setPtpErrOper(BigDecimal ptpErrOper) {
		this.ptpErrOper = ptpErrOper;
	}

	public BigDecimal getPtpErrOper() {
		return ptpErrOper;
	}

	public void setPtpErrBer(BigDecimal ptpErrBer) {
		this.ptpErrBer = ptpErrBer;
	}

	public BigDecimal getPtpErrBer() {
		return ptpErrBer;
	}

	public void setPtpErrSpc(BigDecimal ptpErrSpc) {
		this.ptpErrSpc = ptpErrSpc;
	}

	public BigDecimal getPtpErrSpc() {
		return ptpErrSpc;
	}

	public void setPtpErrSei(BigDecimal ptpErrSei) {
		this.ptpErrSei = ptpErrSei;
	}

	public BigDecimal getPtpErrSei() {
		return ptpErrSei;
	}

	public void setPtpErrHwfr(BigDecimal ptpErrHwfr) {
		this.ptpErrHwfr = ptpErrHwfr;
	}

	public BigDecimal getPtpErrHwfr() {
		return ptpErrHwfr;
	}

	public void setPtpTrTcc(BigDecimal ptpTrTcc) {
		this.ptpTrTcc = ptpTrTcc;
	}

	public BigDecimal getPtpTrTcc() {
		return ptpTrTcc;
	}

	public void setPtpTrRfc(BigDecimal ptpTrRfc) {
		this.ptpTrRfc = ptpTrRfc;
	}

	public BigDecimal getPtpTrRfc() {
		return ptpTrRfc;
	}

	public void setPtpLtlrUltlp(BigDecimal ptpLtlrUltlp) {
		this.ptpLtlrUltlp = ptpLtlrUltlp;
	}

	public BigDecimal getPtpLtlrUltlp() {
		return ptpLtlrUltlp;
	}

	public void setPtpLtlrBgp(BigDecimal ptpLtlrBgp) {
		this.ptpLtlrBgp = ptpLtlrBgp;
	}

	public BigDecimal getPtpLtlrBgp() {
		return ptpLtlrBgp;
	}

	public void setPtpLtlrPtprp(BigDecimal ptpLtlrPtprp) {
		this.ptpLtlrPtprp = ptpLtlrPtprp;
	}

	public BigDecimal getPtpLtlrPtprp() {
		return ptpLtlrPtprp;
	}

	public void setPtpOtherRtp(BigDecimal ptpOtherRtp) {
		this.ptpOtherRtp = ptpOtherRtp;
	}

	public BigDecimal getPtpOtherRtp() {
		return ptpOtherRtp;
	}

	public void setPtpLpaRpa(BigDecimal ptpLpaRpa) {
		this.ptpLpaRpa = ptpLpaRpa;
	}

	public BigDecimal getPtpLpaRpa() {
		return ptpLpaRpa;
	}

	public void setVoucherBeginTime(Date voucherBeginTime) {
		this.voucherBeginTime = voucherBeginTime;
	}

	public Date getVoucherBeginTime() {
		return voucherBeginTime;
	}

	public void setVoucherEndTime(Date voucherEndTime) {
		this.voucherEndTime = voucherEndTime;
	}

	public Date getVoucherEndTime() {
		return voucherEndTime;
	}

	public BigDecimal getPtpLpaRppa() {
		return ptpLpaRppa;
	}

	public void setPtpLpaRppa(BigDecimal ptpLpaRppa) {
		this.ptpLpaRppa = ptpLpaRppa;
	}

	public BigDecimal getPtpLpaEeppa() {
		return ptpLpaEeppa;
	}

	public void setPtpLpaEeppa(BigDecimal ptpLpaEeppa) {
		this.ptpLpaEeppa = ptpLpaEeppa;
	}
}