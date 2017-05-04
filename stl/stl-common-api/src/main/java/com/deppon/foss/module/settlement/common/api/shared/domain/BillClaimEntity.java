package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 理赔、服务补救、退运费单据实体
 * 
 * @author foss-qiaolifeng
 * @date 2013-1-24 下午1:42:49
 */
public class BillClaimEntity {

	private String id;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 金额
	 */
	private BigDecimal amount;

	/**
	 * 支付类别
	 */
	private String paymentCategories;

	/**
	 * 理赔应付部门编码
	 */
	private String payableOrgCode;

	/**
	 * 理赔应付部门名称
	 */
	private String payableOrgName;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 创建人编码
	 */
	private String createUserCode;

	/**
	 * 创建人名称
	 */
	private String createUserName;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 修改人编码
	 */
	private String modifyUserCode;

	/**
	 * 修改人名称
	 */
	private String modifyUserName;

	/**
	 * 类型（理赔/服务补救/退运费）
	 */
	private String type;

	/**
	 * 发送状态（未发送、发送中、发送成功）
	 */
	private String status;
    /**
     * 是否有效
     */
    private String active;

    /**
     * 申请部门类型
     */
    private String applyOrgType;

	/**
	 * 产品类型   --数据库 不存，只是给前台显示用
	 */
	private String productCode;

    /**
     * 最晚付款时间（CRM传来  数据来源为理赔工作流审批24小时候后）
     */
    private Date paymentTimeLimit;
    
    /**
     * 理赔类型(1-异常签收理赔，2-丢货理赔,3-未签收理赔)
     * @author 268217
     */
    private String claimType;
    
    public Date getPaymentTimeLimit() {
        return paymentTimeLimit;
    }

    public void setPaymentTimeLimit(Date paymentTimeLimit) {
        this.paymentTimeLimit = paymentTimeLimit;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getPaymentCategories() {
		return paymentCategories;
	}

	public void setPaymentCategories(String paymentCategories) {
		this.paymentCategories = paymentCategories;
	}

	public String getPayableOrgCode() {
		return payableOrgCode;
	}

	public void setPayableOrgCode(String payableOrgCode) {
		this.payableOrgCode = payableOrgCode;
	}

	public String getPayableOrgName() {
		return payableOrgName;
	}

	public void setPayableOrgName(String payableOrgName) {
		this.payableOrgName = payableOrgName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

    public String getApplyOrgType() {
        return applyOrgType;
    }

    public void setApplyOrgType(String applyOrgType) {
        this.applyOrgType = applyOrgType;
    }

	public String getClaimType() {
		return claimType;
	}

	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}
}
