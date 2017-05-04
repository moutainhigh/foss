package com.deppon.foss.module.settlement.closing.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 凭证报表明细实体
 * 
 * @author 046644-foss-zengbinwen
 * @date 2013-4-1 下午2:15:19
 */
public class VoucherDetailEntity {

	/**
	 * 业务场景
	 */
	private String businessCase;
	
	/**
	 *发票标记
	 */
	private String voucherMark;
	

	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 客户类型
	 */
	private String customerType;

	/**
	 * 始发部门编码
	 */
	private String origOrgCode;

	/**
	 * 始发部门名称
	 */
	private String origOrgName;

	/**
	 * 到达部门编码
	 */
	private String destOrgCode;

	/**
	 * 到达部门名称
	 */
	private String destOrgName;
	
	/**
	 * 借方始发部门编码
	 */
	private String  creditOrgCode;
	
	/**
	 * 借方始发部门名称
	 */
	private String creditOrgName;

	/**
	 * 借方始发部门发票标记
	 */
	private String creditInvoiceMark;

    /**
     * 统一结算类型
     */
    private String unifiedSettlementType;


    /**
     * 合同部门编码
     */
    private String contractOrgCode;

    /**
     * 合同部门名称
     */
    private String contractOrgName;

    public String getContractOrgCode() {
        return contractOrgCode;
    }

    public void setContractOrgCode(String contractOrgCode) {
        this.contractOrgCode = contractOrgCode;
    }

    public String getContractOrgName() {
        return contractOrgName;
    }

    public void setContractOrgName(String contractOrgName) {
        this.contractOrgName = contractOrgName;
    }

    public String getUnifiedSettlementType() {
        return unifiedSettlementType;
    }

    public void setUnifiedSettlementType(String unifiedSettlementType) {
        this.unifiedSettlementType = unifiedSettlementType;
    }

    public String getCreditInvoiceMark() {
		return creditInvoiceMark;
	}

	public void setCreditInvoiceMark(String creditInvoiceMark) {
		this.creditInvoiceMark = creditInvoiceMark;
	}

    /**
	 * 借方部门类型
	 */
	private String creditOrgType;
	
	public String getCreditOrgType() {
		return creditOrgType;
	}

	public void setCreditOrgType(String creditOrgType) {
		this.creditOrgType = creditOrgType;
	}

	public String getDebitOrgType() {
		return debitOrgType;
	}

	public void setDebitOrgType(String debitOrgType) {
		this.debitOrgType = debitOrgType;
	}

	/**
	 * 贷方部门类型
	 */
	private String debitOrgType;
	
	/**
	 * 贷方始发部门编码
	 */
	private String  debitOrgCode;
	
	/**
	 * 贷方始发部门名称
	 */
	private String debitOrgName;
	
	/**
	 * 贷方始发部门发票标记
	 */
	private String debitInvoiceMark;
	
	public String getDebitInvoiceMark() {
		return debitInvoiceMark;
	}

	public void setDebitInvoiceMark(String debitInvoiceMark) {
		this.debitInvoiceMark = debitInvoiceMark;
	}

	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 单据编号
	 */
	private String billNo;
	
	/**
	 * 会计日期
	 */
	private Date accountDate;

	/**
	 * 业务日期
	 */
	private Date businessDate;

	/**
	 * 单据类型
	 */
	private String billParentType;

	/**
	 * 单据子类型
	 */
	private String billType;

	/**
	 * 金额
	 */
	private BigDecimal amount;

	/**
	 * 运费
	 */
	private BigDecimal amountFrt;

	/**
	 * 接货费
	 */
	private BigDecimal amountPup;

	/**
	 * 送货费
	 */
	private BigDecimal amountDel;

	/**
	 * 包装费
	 */
	private BigDecimal amountPkg;

	/**
	 * 保价费
	 */
	private BigDecimal amountDv;

	/**
	 * 代收货款手续费
	 */
	private BigDecimal amountCod;

	/**
	 * 其它费
	 */
	private BigDecimal amountOt;

	/**
	 * 业务类型
	 */
	private String productCode;

	public String getBusinessCase() {
		return businessCase;
	}

	public void setBusinessCase(String businessCase) {
		this.businessCase = businessCase;
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

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
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

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public Date getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}

	public Date getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	public String getBillParentType() {
		return billParentType;
	}

	public void setBillParentType(String billParentType) {
		this.billParentType = billParentType;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAmountFrt() {
		return amountFrt;
	}

	public void setAmountFrt(BigDecimal amountFrt) {
		this.amountFrt = amountFrt;
	}

	public BigDecimal getAmountPup() {
		return amountPup;
	}

	public void setAmountPup(BigDecimal amountPup) {
		this.amountPup = amountPup;
	}

	public BigDecimal getAmountDel() {
		return amountDel;
	}

	public void setAmountDel(BigDecimal amountDel) {
		this.amountDel = amountDel;
	}

	public BigDecimal getAmountPkg() {
		return amountPkg;
	}

	public void setAmountPkg(BigDecimal amountPkg) {
		this.amountPkg = amountPkg;
	}

	public BigDecimal getAmountDv() {
		return amountDv;
	}

	public void setAmountDv(BigDecimal amountDv) {
		this.amountDv = amountDv;
	}

	public BigDecimal getAmountCod() {
		return amountCod;
	}

	public void setAmountCod(BigDecimal amountCod) {
		this.amountCod = amountCod;
	}

	public BigDecimal getAmountOt() {
		return amountOt;
	}

	public void setAmountOt(BigDecimal amountOt) {
		this.amountOt = amountOt;
	}

	/**
	 * @GET
	 * @return productCode
	 */
	public String getProductCode() {
		/*
		 * @get
		 * 
		 * @ return productCode
		 */
		return productCode;
	}

	/**
	 * @SET
	 * @param productCode
	 */
	public void setProductCode(String productCode) {
		/*
		 * @set
		 * 
		 * @this.productCode = productCode
		 */
		this.productCode = productCode;
	}
	
	public String getVoucherMark() {
		return voucherMark;
	}

	public void setVoucherMark(String voucherMark) {
		this.voucherMark = voucherMark;
	}
	
	public String getCreditOrgCode() {
		return creditOrgCode;
	}

	public void setCreditOrgCode(String creditOrgCode) {
		this.creditOrgCode = creditOrgCode;
	}

	public String getCreditOrgName() {
		return creditOrgName;
	}

	public void setCreditOrgName(String creditOrgName) {
		this.creditOrgName = creditOrgName;
	}

	public String getDebitOrgCode() {
		return debitOrgCode;
	}

	public void setDebitOrgCode(String debitOrgCode) {
		this.debitOrgCode = debitOrgCode;
	}

	public String getDebitOrgName() {
		return debitOrgName;
	}

	public void setDebitOrgName(String debitOrgName) {
		this.debitOrgName = debitOrgName;
	}
	
	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
}