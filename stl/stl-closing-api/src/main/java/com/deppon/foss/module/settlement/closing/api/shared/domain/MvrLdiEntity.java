package com.deppon.foss.module.settlement.closing.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 快递代理月报
 * @author 045738-foss-maojianqiang
 * @date 2013-8-1 下午3:59:04
 */
public class MvrLdiEntity implements Serializable{
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 3345839836007830578L;
	
	/**
	 * 期间
	 */
	private String id;
	
	/**
	 * 部门标杆编码
	 */
	private String orgUnifiedCode;
	
	/**
	 * 产品类型
	 */
	private String productCode;
	
	/**
	 * 期间
	 */
	private String period;
	
	/**
	 * 部门编码
	 */
	private String orgCode;
	
	/**
	 * 部门名称
	 */
	private String orgName;
	
	/**
	 * 客户名称
	 */
	private String customerName;
	
	/**
	 * 客户编码
	 */
	private String customerCode;
	
	/**
	 * 客户类型
	 */
	private String customerType;
	
	/**
	 * 类型 出发/到达
	 */
	private String orgType;
	
	/**
	 * 凭证开始时间
	 */
	private Date voucherBeginTime;
	
	/**
	 * 凭证结束时间
	 */
	private Date voucherEndTime;
	
	/**
	 * 快递代理签收时未核销代收货款
	 */
	private BigDecimal landCodPodNwoCod;
	
	/**
	 * 快递代理还款代收货款现金已签收
	 */
	private BigDecimal landCodChPod;
	
	/**
	 * 快递代理还款代收货款银行已签收
	 */
	private BigDecimal landCodCdPod;
	
	/**
	 * 快递代理反签收时未核销代收货款
	 */
	private BigDecimal landCodNpodNwoCod;
	
	/**
	 * 快递代理签收时已核销代收货款
	 */
	private BigDecimal landCodPodWoCod;
	
	/**
	 * 快递代理反签收时已核销代收货款
	 */
	private BigDecimal landCodNpodWoCod;
	
	/**
	 * 快递代理应付冲应收代收货款已签收
	 */
	private BigDecimal landCodWoAgencyPayPod;
	
	/**
	 * 快递代理其他应付冲应收代收货款已签收
	 */
	private BigDecimal landCodWoOthPayCod;
	
	/**
	 * 还款现金未签收
	 */
	private BigDecimal landUrDestChNpod;
	
	/**
	 * 还款银行未签收
	 */
	private BigDecimal landUrDestCdNpod;
	
	/**
	 * 还款现金已签收
	 */
	private BigDecimal landUrDestChPod;
	
	/**
	 * 还款银行已签收
	 */
	private BigDecimal landUrDestCdPod;
	
	/**
	 * 应付到达代理成本冲应收到付运费已签收	
	 */
	private BigDecimal landPrAgencyWoDestRcvPod;
	
	/**
	 * 应付到达代理成本冲应收到付运费未签收	
	 */
	private BigDecimal landPrAgencyWoDestRcvNp;
	
	/**
	 * 其他应付冲应收到付运费已签收	
	 */
	private BigDecimal landPrOtWoDestRcvPod;
	
	/**
	 * 其他应付冲应收到付运费未签收	
	 */
	private BigDecimal landPrOthWoDestRcvNpod;
	
	/**
	 * 预收快递代理冲应收到付运费已签收	
	 */
	private BigDecimal landDrDestRcvPod;
	
	/**
	 * 预收快递代理冲应收到付运费未签收	
	 */
	private BigDecimal landDrDestRcvNpod;
	
	/**
	 * 预收快递代理冲应收代收货款已签收
	 */
	private BigDecimal landDrWoCodRcvPod;
	

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
	 * @return orgUnifiedCode
	 */
	public String getOrgUnifiedCode() {
		/*
		 *@get
		 *@ return orgUnifiedCode
		 */
		return orgUnifiedCode;
	}
	/**
	 * @SET
	 * @param orgUnifiedCode
	 */
	public void setOrgUnifiedCode(String orgUnifiedCode) {
		/*
		 *@set
		 *@this.orgUnifiedCode = orgUnifiedCode
		 */
		this.orgUnifiedCode = orgUnifiedCode;
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
	 * @return orgCode
	 */
	public String getOrgCode() {
		/*
		 *@get
		 *@ return orgCode
		 */
		return orgCode;
	}
	/**
	 * @SET
	 * @param orgCode
	 */
	public void setOrgCode(String orgCode) {
		/*
		 *@set
		 *@this.orgCode = orgCode
		 */
		this.orgCode = orgCode;
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
	 * @return orgName
	 */
	public String getOrgName() {
		/*
		 *@get
		 *@ return orgName
		 */
		return orgName;
	}
	/**
	 * @SET
	 * @param orgName
	 */
	public void setOrgName(String orgName) {
		/*
		 *@set
		 *@this.orgName = orgName
		 */
		this.orgName = orgName;
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
	 * @return orgType
	 */
	public String getOrgType() {
		/*
		 *@get
		 *@ return orgType
		 */
		return orgType;
	}
	/**
	 * @SET
	 * @param orgType
	 */
	public void setOrgType(String orgType) {
		/*
		 *@set
		 *@this.orgType = orgType
		 */
		this.orgType = orgType;
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
	 * @return landCodNpodNwoCod
	 */
	public BigDecimal getLandCodNpodNwoCod() {
		/*
		 *@get
		 *@ return landCodNpodNwoCod
		 */
		return landCodNpodNwoCod;
	}
	/**
	 * @SET
	 * @param landCodNpodNwoCod
	 */
	public void setLandCodNpodNwoCod(BigDecimal landCodNpodNwoCod) {
		/*
		 *@set
		 *@this.landCodNpodNwoCod = landCodNpodNwoCod
		 */
		this.landCodNpodNwoCod = landCodNpodNwoCod;
	}
	/**
	 * @GET
	 * @return landCodWoAgencyPayPod
	 */
	public BigDecimal getLandCodWoAgencyPayPod() {
		/*
		 *@get
		 *@ return landCodWoAgencyPayPod
		 */
		return landCodWoAgencyPayPod;
	}
	/**
	 * @SET
	 * @param landCodWoAgencyPayPod
	 */
	public void setLandCodWoAgencyPayPod(BigDecimal landCodWoAgencyPayPod) {
		/*
		 *@set
		 *@this.landCodWoAgencyPayPod = landCodWoAgencyPayPod
		 */
		this.landCodWoAgencyPayPod = landCodWoAgencyPayPod;
	}
	/**
	 * @GET
	 * @return landCodWoOthPayCod
	 */
	public BigDecimal getLandCodWoOthPayCod() {
		/*
		 *@get
		 *@ return landCodWoOthPayCod
		 */
		return landCodWoOthPayCod;
	}
	/**
	 * @SET
	 * @param landCodWoOthPayCod
	 */
	public void setLandCodWoOthPayCod(BigDecimal landCodWoOthPayCod) {
		/*
		 *@set
		 *@this.landCodWoOthPayCod = landCodWoOthPayCod
		 */
		this.landCodWoOthPayCod = landCodWoOthPayCod;
	}
	/**
	 * @GET
	 * @return landUrDestChNpod
	 */
	public BigDecimal getLandUrDestChNpod() {
		/*
		 *@get
		 *@ return landUrDestChNpod
		 */
		return landUrDestChNpod;
	}
	/**
	 * @SET
	 * @param landUrDestChNpod
	 */
	public void setLandUrDestChNpod(BigDecimal landUrDestChNpod) {
		/*
		 *@set
		 *@this.landUrDestChNpod = landUrDestChNpod
		 */
		this.landUrDestChNpod = landUrDestChNpod;
	}
	/**
	 * @GET
	 * @return landUrDestCdNpod
	 */
	public BigDecimal getLandUrDestCdNpod() {
		/*
		 *@get
		 *@ return landUrDestCdNpod
		 */
		return landUrDestCdNpod;
	}
	/**
	 * @SET
	 * @param landUrDestCdNpod
	 */
	public void setLandUrDestCdNpod(BigDecimal landUrDestCdNpod) {
		/*
		 *@set
		 *@this.landUrDestCdNpod = landUrDestCdNpod
		 */
		this.landUrDestCdNpod = landUrDestCdNpod;
	}
	/**
	 * @GET
	 * @return landUrDestChPod
	 */
	public BigDecimal getLandUrDestChPod() {
		/*
		 *@get
		 *@ return landUrDestChPod
		 */
		return landUrDestChPod;
	}
	/**
	 * @SET
	 * @param landUrDestChPod
	 */
	public void setLandUrDestChPod(BigDecimal landUrDestChPod) {
		/*
		 *@set
		 *@this.landUrDestChPod = landUrDestChPod
		 */
		this.landUrDestChPod = landUrDestChPod;
	}
	/**
	 * @GET
	 * @return landUrDestCdPod
	 */
	public BigDecimal getLandUrDestCdPod() {
		/*
		 *@get
		 *@ return landUrDestCdPod
		 */
		return landUrDestCdPod;
	}
	/**
	 * @SET
	 * @param landUrDestCdPod
	 */
	public void setLandUrDestCdPod(BigDecimal landUrDestCdPod) {
		/*
		 *@set
		 *@this.landUrDestCdPod = landUrDestCdPod
		 */
		this.landUrDestCdPod = landUrDestCdPod;
	}
	/**
	 * @GET
	 * @return landPrAgencyWoDestRcvPod
	 */
	public BigDecimal getLandPrAgencyWoDestRcvPod() {
		/*
		 *@get
		 *@ return landPrAgencyWoDestRcvPod
		 */
		return landPrAgencyWoDestRcvPod;
	}
	/**
	 * @SET
	 * @param landPrAgencyWoDestRcvPod
	 */
	public void setLandPrAgencyWoDestRcvPod(BigDecimal landPrAgencyWoDestRcvPod) {
		/*
		 *@set
		 *@this.landPrAgencyWoDestRcvPod = landPrAgencyWoDestRcvPod
		 */
		this.landPrAgencyWoDestRcvPod = landPrAgencyWoDestRcvPod;
	}
	/**
	 * @GET
	 * @return landPrAgencyWoDestRcvNp
	 */
	public BigDecimal getLandPrAgencyWoDestRcvNp() {
		/*
		 *@get
		 *@ return landPrAgencyWoDestRcvNp
		 */
		return landPrAgencyWoDestRcvNp;
	}
	/**
	 * @SET
	 * @param landPrAgencyWoDestRcvNp
	 */
	public void setLandPrAgencyWoDestRcvNp(BigDecimal landPrAgencyWoDestRcvNp) {
		/*
		 *@set
		 *@this.landPrAgencyWoDestRcvNp = landPrAgencyWoDestRcvNp
		 */
		this.landPrAgencyWoDestRcvNp = landPrAgencyWoDestRcvNp;
	}
	/**
	 * @GET
	 * @return landPrOtWoDestRcvPod
	 */
	public BigDecimal getLandPrOtWoDestRcvPod() {
		/*
		 *@get
		 *@ return landPrOtWoDestRcvPod
		 */
		return landPrOtWoDestRcvPod;
	}
	/**
	 * @SET
	 * @param landPrOtWoDestRcvPod
	 */
	public void setLandPrOtWoDestRcvPod(BigDecimal landPrOtWoDestRcvPod) {
		/*
		 *@set
		 *@this.landPrOtWoDestRcvPod = landPrOtWoDestRcvPod
		 */
		this.landPrOtWoDestRcvPod = landPrOtWoDestRcvPod;
	}
	/**
	 * @GET
	 * @return landPrOthWoDestRcvNpod
	 */
	public BigDecimal getLandPrOthWoDestRcvNpod() {
		/*
		 *@get
		 *@ return landPrOthWoDestRcvNpod
		 */
		return landPrOthWoDestRcvNpod;
	}
	/**
	 * @SET
	 * @param landPrOthWoDestRcvNpod
	 */
	public void setLandPrOthWoDestRcvNpod(BigDecimal landPrOthWoDestRcvNpod) {
		/*
		 *@set
		 *@this.landPrOthWoDestRcvNpod = landPrOthWoDestRcvNpod
		 */
		this.landPrOthWoDestRcvNpod = landPrOthWoDestRcvNpod;
	}
	/**
	 * @GET
	 * @return landDrDestRcvPod
	 */
	public BigDecimal getLandDrDestRcvPod() {
		/*
		 *@get
		 *@ return landDrDestRcvPod
		 */
		return landDrDestRcvPod;
	}
	/**
	 * @SET
	 * @param landDrDestRcvPod
	 */
	public void setLandDrDestRcvPod(BigDecimal landDrDestRcvPod) {
		/*
		 *@set
		 *@this.landDrDestRcvPod = landDrDestRcvPod
		 */
		this.landDrDestRcvPod = landDrDestRcvPod;
	}
	/**
	 * @GET
	 * @return landDrDestRcvNpod
	 */
	public BigDecimal getLandDrDestRcvNpod() {
		/*
		 *@get
		 *@ return landDrDestRcvNpod
		 */
		return landDrDestRcvNpod;
	}
	/**
	 * @SET
	 * @param landDrDestRcvNpod
	 */
	public void setLandDrDestRcvNpod(BigDecimal landDrDestRcvNpod) {
		/*
		 *@set
		 *@this.landDrDestRcvNpod = landDrDestRcvNpod
		 */
		this.landDrDestRcvNpod = landDrDestRcvNpod;
	}
	/**
	 * @GET
	 * @return landDrWoCodRcvPod
	 */
	public BigDecimal getLandDrWoCodRcvPod() {
		/*
		 *@get
		 *@ return landDrWoCodRcvPod
		 */
		return landDrWoCodRcvPod;
	}
	/**
	 * @SET
	 * @param landDrWoCodRcvPod
	 */
	public void setLandDrWoCodRcvPod(BigDecimal landDrWoCodRcvPod) {
		/*
		 *@set
		 *@this.landDrWoCodRcvPod = landDrWoCodRcvPod
		 */
		this.landDrWoCodRcvPod = landDrWoCodRcvPod;
	}
	/**
	 * @GET
	 * @return landCodPodWoCod
	 */
	public BigDecimal getLandCodPodWoCod() {
		/*
		 *@get
		 *@ return landCodPodWoCod
		 */
		return landCodPodWoCod;
	}
	/**
	 * @SET
	 * @param landCodPodWoCod
	 */
	public void setLandCodPodWoCod(BigDecimal landCodPodWoCod) {
		/*
		 *@set
		 *@this.landCodPodWoCod = landCodPodWoCod
		 */
		this.landCodPodWoCod = landCodPodWoCod;
	}
	/**
	 * @GET
	 * @return landCodNpodWoCod
	 */
	public BigDecimal getLandCodNpodWoCod() {
		/*
		 *@get
		 *@ return landCodNpodWoCod
		 */
		return landCodNpodWoCod;
	}
	/**
	 * @SET
	 * @param landCodNpodWoCod
	 */
	public void setLandCodNpodWoCod(BigDecimal landCodNpodWoCod) {
		/*
		 *@set
		 *@this.landCodNpodWoCod = landCodNpodWoCod
		 */
		this.landCodNpodWoCod = landCodNpodWoCod;
	}
	/**
	 * @GET
	 * @return landCodCdPod
	 */
	public BigDecimal getLandCodCdPod() {
		/*
		 *@get
		 *@ return landCodCdPod
		 */
		return landCodCdPod;
	}
	/**
	 * @SET
	 * @param landCodCdPod
	 */
	public void setLandCodCdPod(BigDecimal landCodCdPod) {
		/*
		 *@set
		 *@this.landCodCdPod = landCodCdPod
		 */
		this.landCodCdPod = landCodCdPod;
	}
	/**
	 * @GET
	 * @return landCodPodNwoCod
	 */
	public BigDecimal getLandCodPodNwoCod() {
		/*
		 *@get
		 *@ return landCodPodNwoCod
		 */
		return landCodPodNwoCod;
	}
	/**
	 * @SET
	 * @param landCodPodNwoCod
	 */
	public void setLandCodPodNwoCod(BigDecimal landCodPodNwoCod) {
		/*
		 *@set
		 *@this.landCodPodNwoCod = landCodPodNwoCod
		 */
		this.landCodPodNwoCod = landCodPodNwoCod;
	}
	/**
	 * @GET
	 * @return landCodChPod
	 */
	public BigDecimal getLandCodChPod() {
		/*
		 *@get
		 *@ return landCodChPod
		 */
		return landCodChPod;
	}
	/**
	 * @SET
	 * @param landCodChPod
	 */
	public void setLandCodChPod(BigDecimal landCodChPod) {
		/*
		 *@set
		 *@this.landCodChPod = landCodChPod
		 */
		this.landCodChPod = landCodChPod;
	}
	
	
	
}
