package com.deppon.foss.module.settlement.closing.api.shared.domain;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 
 * 营业部核销月报表
 * @author 045738-foss-maojianqiang
 * @date 2013-8-1 下午3:59:04
 */
public class MvrLwoEntity implements Serializable{
	
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 4185239465832289951L;
	/** ID. */
	private String id;

	/** 期间. */
	private String period;

	/** 业务类型. */
	private String productCode;

	/** 客户编码. */
	private String customerCode;

	/** 客户名称. */
	private String customerName;

	/**
	 * 客户类型
	 */
	private String customerType;
	
	/** 始发部门标杆编码. */
	private String origUnifiedCode;
	
	/** 到达部门标杆编码. */
	private String destUnifiedCode;

	/** 始发部门编码. */
	private String origOrgCode;

	/** 始发部门名称. */
	private String origOrgName;

	/** 到达部门编码. */
	private String destOrgCode;

	/** 到达部门名称. */
	private String destOrgName;
	
	/**
	 * 凭证开始时间
	 */
	private Date voucherBeginTime;
	
	/**
	 * 凭证结束时间
	 */
	private Date voucherEndTime;

	/**
	 * 理赔冲始发应收已签收
	 */
    private BigDecimal claimWoOrigLandRcvPod;

    /**
	 * 理赔冲始发应收未签收
	 */
    private BigDecimal claimWoOrigLandRcvNpod;
    
    /**
     * 预收客户冲应收始发运费未签收
     */
    private BigDecimal custDrOrigLandRcvNpod;
    /**
     * 预收客户冲应收始发运费已签收
     */
    private BigDecimal custDrOrigLandRcvPod;
    
    /**
     * 应付理赔冲小票应收
     */
    private BigDecimal orLandRcvWoClaimPay;

    /**
     * 预收客户冲小票应收
     */
    private BigDecimal orLandRcvWoCustDr;

    /**
     * 退运费冲始发应收已签收
     */
    private BigDecimal refundWoOrigLandRcvPod;
    
    /**
     * 退运费冲始发应收未签收
     */
    private BigDecimal refundWoOrigLandRcvNpod;

    /**
     * 理赔冲始发应收已签收
     */
    private BigDecimal landClaimWoOrigRcvPod;

    /**
     * 理赔冲始发应收未签收
     */
    private BigDecimal landClaimWoOrigRcvNpod;

    /**
     * 退运费冲始发应收已签收
     */
    private BigDecimal landRefundWoOrigRcvPod;

    /**
     * 退运费冲始发应收未签收
     */
    private BigDecimal landRefundWoOrigRcvNpod;

    /**
     * 应付理赔冲小票应收
     */
    private BigDecimal orRcvWoLandClaimPay;
    
    /**
     * 出发部门付款申请 
     */
    private BigDecimal origOrgPayApply;
    
    /**
     * 到达部门付款申请
     */
    private BigDecimal destOrgPayApply;
    
	/**
	 * @GET
	 * @return id
	 */
	public String getId() {
		/*
		 *@get
		 *@ return id
		 */
		return id;
	}

	/**
	 * @SET
	 * @param id
	 */
	public void setId(String id) {
		/*
		 *@set
		 *@this.id = id
		 */
		this.id = id;
	}

	/**
	 * @GET
	 * @return period
	 */
	public String getPeriod() {
		/*
		 *@get
		 *@ return period
		 */
		return period;
	}

	/**
	 * @SET
	 * @param period
	 */
	public void setPeriod(String period) {
		/*
		 *@set
		 *@this.period = period
		 */
		this.period = period;
	}

	/**
	 * @GET
	 * @return productCode
	 */
	public String getProductCode() {
		/*
		 *@get
		 *@ return productCode
		 */
		return productCode;
	}

	/**
	 * @SET
	 * @param productCode
	 */
	public void setProductCode(String productCode) {
		/*
		 *@set
		 *@this.productCode = productCode
		 */
		this.productCode = productCode;
	}

	/**
	 * @GET
	 * @return customerCode
	 */
	public String getCustomerCode() {
		/*
		 *@get
		 *@ return customerCode
		 */
		return customerCode;
	}

	/**
	 * @SET
	 * @param customerCode
	 */
	public void setCustomerCode(String customerCode) {
		/*
		 *@set
		 *@this.customerCode = customerCode
		 */
		this.customerCode = customerCode;
	}

	/**
	 * @GET
	 * @return customerName
	 */
	public String getCustomerName() {
		/*
		 *@get
		 *@ return customerName
		 */
		return customerName;
	}

	/**
	 * @SET
	 * @param customerName
	 */
	public void setCustomerName(String customerName) {
		/*
		 *@set
		 *@this.customerName = customerName
		 */
		this.customerName = customerName;
	}

	/**
	 * @GET
	 * @return customerType
	 */
	public String getCustomerType() {
		/*
		 *@get
		 *@ return customerType
		 */
		return customerType;
	}

	/**
	 * @SET
	 * @param customerType
	 */
	public void setCustomerType(String customerType) {
		/*
		 *@set
		 *@this.customerType = customerType
		 */
		this.customerType = customerType;
	}

	/**
	 * @GET
	 * @return origOrgCode
	 */
	public String getOrigOrgCode() {
		/*
		 *@get
		 *@ return origOrgCode
		 */
		return origOrgCode;
	}

	/**
	 * @SET
	 * @param origOrgCode
	 */
	public void setOrigOrgCode(String origOrgCode) {
		/*
		 *@set
		 *@this.origOrgCode = origOrgCode
		 */
		this.origOrgCode = origOrgCode;
	}

	/**
	 * @GET
	 * @return origOrgName
	 */
	public String getOrigOrgName() {
		/*
		 *@get
		 *@ return origOrgName
		 */
		return origOrgName;
	}

	/**
	 * @SET
	 * @param origOrgName
	 */
	public void setOrigOrgName(String origOrgName) {
		/*
		 *@set
		 *@this.origOrgName = origOrgName
		 */
		this.origOrgName = origOrgName;
	}

	/**
	 * @GET
	 * @return destOrgCode
	 */
	public String getDestOrgCode() {
		/*
		 *@get
		 *@ return destOrgCode
		 */
		return destOrgCode;
	}

	/**
	 * @SET
	 * @param destOrgCode
	 */
	public void setDestOrgCode(String destOrgCode) {
		/*
		 *@set
		 *@this.destOrgCode = destOrgCode
		 */
		this.destOrgCode = destOrgCode;
	}

	/**
	 * @GET
	 * @return destOrgName
	 */
	public String getDestOrgName() {
		/*
		 *@get
		 *@ return destOrgName
		 */
		return destOrgName;
	}

	/**
	 * @SET
	 * @param destOrgName
	 */
	public void setDestOrgName(String destOrgName) {
		/*
		 *@set
		 *@this.destOrgName = destOrgName
		 */
		this.destOrgName = destOrgName;
	}

	/**
	 * @GET
	 * @return voucherBeginTime
	 */
	public Date getVoucherBeginTime() {
		/*
		 *@get
		 *@ return voucherBeginTime
		 */
		return voucherBeginTime;
	}

	/**
	 * @SET
	 * @param voucherBeginTime
	 */
	public void setVoucherBeginTime(Date voucherBeginTime) {
		/*
		 *@set
		 *@this.voucherBeginTime = voucherBeginTime
		 */
		this.voucherBeginTime = voucherBeginTime;
	}

	/**
	 * @GET
	 * @return voucherEndTime
	 */
	public Date getVoucherEndTime() {
		/*
		 *@get
		 *@ return voucherEndTime
		 */
		return voucherEndTime;
	}

	/**
	 * @SET
	 * @param voucherEndTime
	 */
	public void setVoucherEndTime(Date voucherEndTime) {
		/*
		 *@set
		 *@this.voucherEndTime = voucherEndTime
		 */
		this.voucherEndTime = voucherEndTime;
	}

	/**
	 * @GET
	 * @return claimWoOrigLandRcvPod
	 */
	public BigDecimal getClaimWoOrigLandRcvPod() {
		/*
		 *@get
		 *@ return claimWoOrigLandRcvPod
		 */
		return claimWoOrigLandRcvPod;
	}

	/**
	 * @SET
	 * @param claimWoOrigLandRcvPod
	 */
	public void setClaimWoOrigLandRcvPod(BigDecimal claimWoOrigLandRcvPod) {
		/*
		 *@set
		 *@this.claimWoOrigLandRcvPod = claimWoOrigLandRcvPod
		 */
		this.claimWoOrigLandRcvPod = claimWoOrigLandRcvPod;
	}

	/**
	 * @GET
	 * @return claimWoOrigLandRcvNpod
	 */
	public BigDecimal getClaimWoOrigLandRcvNpod() {
		/*
		 *@get
		 *@ return claimWoOrigLandRcvNpod
		 */
		return claimWoOrigLandRcvNpod;
	}

	/**
	 * @SET
	 * @param claimWoOrigLandRcvNpod
	 */
	public void setClaimWoOrigLandRcvNpod(BigDecimal claimWoOrigLandRcvNpod) {
		/*
		 *@set
		 *@this.claimWoOrigLandRcvNpod = claimWoOrigLandRcvNpod
		 */
		this.claimWoOrigLandRcvNpod = claimWoOrigLandRcvNpod;
	}

	/**
	 * @GET
	 * @return custDrOrigLandRcvNpod
	 */
	public BigDecimal getCustDrOrigLandRcvNpod() {
		/*
		 *@get
		 *@ return custDrOrigLandRcvNpod
		 */
		return custDrOrigLandRcvNpod;
	}

	/**
	 * @SET
	 * @param custDrOrigLandRcvNpod
	 */
	public void setCustDrOrigLandRcvNpod(BigDecimal custDrOrigLandRcvNpod) {
		/*
		 *@set
		 *@this.custDrOrigLandRcvNpod = custDrOrigLandRcvNpod
		 */
		this.custDrOrigLandRcvNpod = custDrOrigLandRcvNpod;
	}

	/**
	 * @GET
	 * @return custDrOrigLandRcvPod
	 */
	public BigDecimal getCustDrOrigLandRcvPod() {
		/*
		 *@get
		 *@ return custDrOrigLandRcvPod
		 */
		return custDrOrigLandRcvPod;
	}

	/**
	 * @SET
	 * @param custDrOrigLandRcvPod
	 */
	public void setCustDrOrigLandRcvPod(BigDecimal custDrOrigLandRcvPod) {
		/*
		 *@set
		 *@this.custDrOrigLandRcvPod = custDrOrigLandRcvPod
		 */
		this.custDrOrigLandRcvPod = custDrOrigLandRcvPod;
	}

	/**
	 * @GET
	 * @return orLandRcvWoClaimPay
	 */
	public BigDecimal getOrLandRcvWoClaimPay() {
		/*
		 *@get
		 *@ return orLandRcvWoClaimPay
		 */
		return orLandRcvWoClaimPay;
	}

	/**
	 * @SET
	 * @param orLandRcvWoClaimPay
	 */
	public void setOrLandRcvWoClaimPay(BigDecimal orLandRcvWoClaimPay) {
		/*
		 *@set
		 *@this.orLandRcvWoClaimPay = orLandRcvWoClaimPay
		 */
		this.orLandRcvWoClaimPay = orLandRcvWoClaimPay;
	}

	/**
	 * @GET
	 * @return orLandRcvWoCustDr
	 */
	public BigDecimal getOrLandRcvWoCustDr() {
		/*
		 *@get
		 *@ return orLandRcvWoCustDr
		 */
		return orLandRcvWoCustDr;
	}

	/**
	 * @SET
	 * @param orLandRcvWoCustDr
	 */
	public void setOrLandRcvWoCustDr(BigDecimal orLandRcvWoCustDr) {
		/*
		 *@set
		 *@this.orLandRcvWoCustDr = orLandRcvWoCustDr
		 */
		this.orLandRcvWoCustDr = orLandRcvWoCustDr;
	}

	/**
	 * @GET
	 * @return refundWoOrigLandRcvPod
	 */
	public BigDecimal getRefundWoOrigLandRcvPod() {
		/*
		 *@get
		 *@ return refundWoOrigLandRcvPod
		 */
		return refundWoOrigLandRcvPod;
	}

	/**
	 * @SET
	 * @param refundWoOrigLandRcvPod
	 */
	public void setRefundWoOrigLandRcvPod(BigDecimal refundWoOrigLandRcvPod) {
		/*
		 *@set
		 *@this.refundWoOrigLandRcvPod = refundWoOrigLandRcvPod
		 */
		this.refundWoOrigLandRcvPod = refundWoOrigLandRcvPod;
	}

	/**
	 * @GET
	 * @return refundWoOrigLandRcvNpod
	 */
	public BigDecimal getRefundWoOrigLandRcvNpod() {
		/*
		 *@get
		 *@ return refundWoOrigLandRcvNpod
		 */
		return refundWoOrigLandRcvNpod;
	}

	/**
	 * @SET
	 * @param refundWoOrigLandRcvNpod
	 */
	public void setRefundWoOrigLandRcvNpod(BigDecimal refundWoOrigLandRcvNpod) {
		/*
		 *@set
		 *@this.refundWoOrigLandRcvNpod = refundWoOrigLandRcvNpod
		 */
		this.refundWoOrigLandRcvNpod = refundWoOrigLandRcvNpod;
	}

	/**
	 * @GET
	 * @return landClaimWoOrigRcvPod
	 */
	public BigDecimal getLandClaimWoOrigRcvPod() {
		/*
		 *@get
		 *@ return landClaimWoOrigRcvPod
		 */
		return landClaimWoOrigRcvPod;
	}

	/**
	 * @SET
	 * @param landClaimWoOrigRcvPod
	 */
	public void setLandClaimWoOrigRcvPod(BigDecimal landClaimWoOrigRcvPod) {
		/*
		 *@set
		 *@this.landClaimWoOrigRcvPod = landClaimWoOrigRcvPod
		 */
		this.landClaimWoOrigRcvPod = landClaimWoOrigRcvPod;
	}

	/**
	 * @GET
	 * @return landClaimWoOrigRcvNpod
	 */
	public BigDecimal getLandClaimWoOrigRcvNpod() {
		/*
		 *@get
		 *@ return landClaimWoOrigRcvNpod
		 */
		return landClaimWoOrigRcvNpod;
	}

	/**
	 * @SET
	 * @param landClaimWoOrigRcvNpod
	 */
	public void setLandClaimWoOrigRcvNpod(BigDecimal landClaimWoOrigRcvNpod) {
		/*
		 *@set
		 *@this.landClaimWoOrigRcvNpod = landClaimWoOrigRcvNpod
		 */
		this.landClaimWoOrigRcvNpod = landClaimWoOrigRcvNpod;
	}

	/**
	 * @GET
	 * @return landRefundWoOrigRcvPod
	 */
	public BigDecimal getLandRefundWoOrigRcvPod() {
		/*
		 *@get
		 *@ return landRefundWoOrigRcvPod
		 */
		return landRefundWoOrigRcvPod;
	}

	/**
	 * @SET
	 * @param landRefundWoOrigRcvPod
	 */
	public void setLandRefundWoOrigRcvPod(BigDecimal landRefundWoOrigRcvPod) {
		/*
		 *@set
		 *@this.landRefundWoOrigRcvPod = landRefundWoOrigRcvPod
		 */
		this.landRefundWoOrigRcvPod = landRefundWoOrigRcvPod;
	}

	/**
	 * @GET
	 * @return landRefundWoOrigRcvNpod
	 */
	public BigDecimal getLandRefundWoOrigRcvNpod() {
		/*
		 *@get
		 *@ return landRefundWoOrigRcvNpod
		 */
		return landRefundWoOrigRcvNpod;
	}

	/**
	 * @SET
	 * @param landRefundWoOrigRcvNpod
	 */
	public void setLandRefundWoOrigRcvNpod(BigDecimal landRefundWoOrigRcvNpod) {
		/*
		 *@set
		 *@this.landRefundWoOrigRcvNpod = landRefundWoOrigRcvNpod
		 */
		this.landRefundWoOrigRcvNpod = landRefundWoOrigRcvNpod;
	}

	/**
	 * @GET
	 * @return orRcvWoLandClaimPay
	 */
	public BigDecimal getOrRcvWoLandClaimPay() {
		/*
		 *@get
		 *@ return orRcvWoLandClaimPay
		 */
		return orRcvWoLandClaimPay;
	}

	/**
	 * @SET
	 * @param orRcvWoLandClaimPay
	 */
	public void setOrRcvWoLandClaimPay(BigDecimal orRcvWoLandClaimPay) {
		/*
		 *@set
		 *@this.orRcvWoLandClaimPay = orRcvWoLandClaimPay
		 */
		this.orRcvWoLandClaimPay = orRcvWoLandClaimPay;
	}

	/**
	 * @return the origUnifiedCode
	 */
	public String getOrigUnifiedCode() {
		return origUnifiedCode;
	}

	/**
	 * @param origUnifiedCode the origUnifiedCode to set
	 */
	public void setOrigUnifiedCode(String origUnifiedCode) {
		this.origUnifiedCode = origUnifiedCode;
	}

	/**
	 * @return the destUnifiedCode
	 */
	public String getDestUnifiedCode() {
		return destUnifiedCode;
	}

	/**
	 * @param destUnifiedCode the destUnifiedCode to set
	 */
	public void setDestUnifiedCode(String destUnifiedCode) {
		this.destUnifiedCode = destUnifiedCode;
	}

	public BigDecimal getOrigOrgPayApply() {
		return origOrgPayApply;
	}

	public void setOrigOrgPayApply(BigDecimal origOrgPayApply) {
		this.origOrgPayApply = origOrgPayApply;
	}

	public BigDecimal getDestOrgPayApply() {
		return destOrgPayApply;
	}

	public void setDestOrgPayApply(BigDecimal destOrgPayApply) {
		this.destOrgPayApply = destOrgPayApply;
	}
	
}