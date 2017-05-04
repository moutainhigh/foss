package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 付款明细表
 * 
 * @author 099995-foss-wujiangtao
 * @date 2013-3-17 下午2:58:57
 * @since
 * @version
 */

public class BillPaymentDEntity extends BaseEntity{
	
    /**
	 *序列化ID
	 */
	private static final long serialVersionUID = -8853255634581981965L;

	/**
	 * ID主键
	 */
	private String id;

	/**
	 * 付款单号
	 */
    private String paymentNo;

    /**
     * 付款单的记账日期
     */
    private Date paymentAccountDate;

    /**
     * 来源单号  ---set
     */
    private String sourceBillNo;
    
    /**
     * 当来源单据为应付单时，保存应付单的来源单号
     */
    private String srcSourceBillNo;

    /**
     * 运单号 ---存在set
     */
    private String waybillNo;

    /**
     * 来源单据类型  (应付单/预售单)
     */
    private String sourceBillType;

    /**
     * 来源单据的记账日期 ---存在set
     */
    private Date sourceAccountDate;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 支付金额（每单支付金额）
     */
    private BigDecimal payAmount;
    
	/**
	 * 税金
	 */
	private BigDecimal taxAmount;
	
	/**
	 * 税后金额
	 */
	private BigDecimal tax;
	
	/**
	 * 临时租车业务发生日期
	 */
	private Date businessOfDate;

    
	
	/**
	 * @get
	 * @return payAmount
	 */
	public BigDecimal getPayAmount() {
		/*
		 * @get
		 * @return payAmount
		 */
		return payAmount;
	}


	
	/**
	 * @set
	 * @param payAmount
	 */
	public void setPayAmount(BigDecimal payAmount) {
		/*
		 *@set
		 *@this.payAmount = payAmount
		 */
		this.payAmount = payAmount;
	}


	/**
	 * @return  the id
	 */
	public String getId() {
		return id;
	}

	
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	
	/**
	 * @return  the paymentNo
	 */
	public String getPaymentNo() {
		return paymentNo;
	}

	
	/**
	 * @param paymentNo the paymentNo to set
	 */
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	
	/**
	 * @return  the paymentAccountDate
	 */
	public Date getPaymentAccountDate() {
		return paymentAccountDate;
	}

	
	/**
	 * @param paymentAccountDate the paymentAccountDate to set
	 */
	public void setPaymentAccountDate(Date paymentAccountDate) {
		this.paymentAccountDate = paymentAccountDate;
	}

	
	/**
	 * @return  the sourceBillNo
	 */
	public String getSourceBillNo() {
		return sourceBillNo;
	}

	
	/**
	 * @param sourceBillNo the sourceBillNo to set
	 */
	public void setSourceBillNo(String sourceBillNo) {
		this.sourceBillNo = sourceBillNo;
	}

	
	/**
	 * @return  the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	
	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	
	/**
	 * @return  the sourceBillType
	 */
	public String getSourceBillType() {
		return sourceBillType;
	}

	
	/**
	 * @param sourceBillType the sourceBillType to set
	 */
	public void setSourceBillType(String sourceBillType) {
		this.sourceBillType = sourceBillType;
	}

	
	/**
	 * @return  the sourceAccountDate
	 */
	public Date getSourceAccountDate() {
		return sourceAccountDate;
	}

	
	/**
	 * @param sourceAccountDate the sourceAccountDate to set
	 */
	public void setSourceAccountDate(Date sourceAccountDate) {
		this.sourceAccountDate = sourceAccountDate;
	}

	
	/**
	 * @return  the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	
	/**
	 * @return  the srcSourceBillNo
	 */
	public String getSrcSourceBillNo() {
		return srcSourceBillNo;
	}


	
	/**
	 * @param srcSourceBillNo the srcSourceBillNo to set
	 */
	public void setSrcSourceBillNo(String srcSourceBillNo) {
		this.srcSourceBillNo = srcSourceBillNo;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public BigDecimal getTax() {
		return tax;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	public Date getBusinessOfDate() {
		return businessOfDate;
	}

	public void setBusinessOfDate(Date businessOfDate) {
		this.businessOfDate = businessOfDate;
	}

   
}