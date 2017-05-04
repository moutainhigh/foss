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
public class MvrLddEntity implements Serializable{
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1245465635045259890L;
	/**
	 * id
	 */
	private String id;
	
	/**
	 * 期间
	 */
	private String period;
	
	/**
	 * 产品类型
	 */
	private String productCode;
	
	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 出发部门编码
	 */
	private String origOrgCode;

	/**
	 * 出发部门名称
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
	 * 出发标杆编码
	 */
	private String origUnifiedCode;

	/**
	 * 到达标杆编码
	 */
	private String destUnifiedCode;

	/**
	 * 凭证开始日期
	 */
	private Date voucherBeginTime;

	/**
	 * 凭证结束日期
	 */
	private Date voucherEndTime;

	/**
	 * 客户类型
	 */
	private String customerType;

	/**
	 * 快递代理应付生成
	 */
	private BigDecimal landCostAgencyGen;

	/**
	 * 快递代理应付成本确认
	 */
	private BigDecimal landCostAgencyCfm;

	/**
	 * 快递代理应付成本反确认
	 */
	private BigDecimal landCostAgencyNcfm;

	/**
	 * 其它应付成本确认
	 */
	private BigDecimal landCostOtherConfirm;

	/**
	 * 快递代理成本付款申请
	 */
	private BigDecimal landCostPayApply;

	/**
	 * 快递代理其他应收录入
	 */
	private BigDecimal landOthEntry;

	/**
	 * 还款快递代理其他应收现金
	 */
	private BigDecimal landOthRcvCh;

	/**
	 * 还款快递代理其他应收银行
	 */
	private BigDecimal landOthRcvCd;

	/**
	 * 快递代理还款代收货款现金已签收
	 */
	private BigDecimal landCodChPod;

	/**
	 * 快递代理还款代收货款银行已签收
	 */
	private BigDecimal landCodCdPod;

	/**
	 * 快递代理签收时已核销代收货款
	 */
	private BigDecimal landCodPodWoCod;

	/**
	 * 快递代理反签收时已核销代收货款
	 */
	private BigDecimal landCodNpodWoCod;

	/**
	 * 快递代理还款代收货款现金未签收
	 */
	private BigDecimal landCodChNpod;

	/**
	 * 快递代理还款代收货款银行未签收
	 */
	private BigDecimal landCodCdNpod;

	/**
	 * 快递代理应付冲应收代收货款已签收
	 */
	private BigDecimal landCodWoAgencyPayPod;

	/**
	 * 快递代理其他应付冲应收代收货款已签收
	 */
	private BigDecimal landCodWoOthPayCod;

	/**
	 * 快递代理应付冲应收代收货款未签收
	 */
	private BigDecimal landCodWoAgencyPayNpod;

	/**
	 * 快递代理其他应付冲应收代收货款未签收
	 */
	private BigDecimal landCodWoOthNpod;

	/**
	 * 坏账冲其他应收
	 */
	private BigDecimal landBdrWoOthRcv;

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
	 * 应付代理成本冲应收到付运费已签收
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
	 * 其他应付冲其他应收
	 */
	private BigDecimal landPrOthWoOthRcv;

	/**
	 * 预收快递代理冲应收到付运费已签收
	 */
	private BigDecimal landDrDestRcvPod;

	/**
	 * 预收快递代理冲应收到付运费未签收
	 */
	private BigDecimal landDrDestRcvNpod;

	/**
	 * 预收快递代理现金
	 */
	private BigDecimal landDrCh;

	/**
	 * 预收快递代理银行
	 */
	private BigDecimal landDrCd;

	/**
	 * 预收快递代理冲其他应收
	 */
	private BigDecimal landDrWoOtherRcv;

	/**
	 * 预收快递代理冲应收代收货款已签收
	 */
	private BigDecimal landDrWoCodRcvPod;

	/**
	 * 预收快递代理冲应收代收货款未签收
	 */
	private BigDecimal landDrWoCodRcvNpod;

	/**
	 * 快递代理退预收付款申请
	 */
	private BigDecimal landDrPayApply;

	/**
	 * 预付快递代理公司
	 */
	private BigDecimal landAptCom;

	/**
	 * 预付冲应付快递代理公司
	 */
	private BigDecimal landAptWoComPay;

	/**
	 * 预付冲其他应付
	 */
	private BigDecimal landAptWoOthPay;
	
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
	 * @return origUnifiedCode
	 */
	public String getOrigUnifiedCode() {
		/*
		 *@get
		 *@ return origUnifiedCode
		 */
		return origUnifiedCode;
	}
	/**
	 * @SET
	 * @param origUnifiedCode
	 */
	public void setOrigUnifiedCode(String origUnifiedCode) {
		/*
		 *@set
		 *@this.origUnifiedCode = origUnifiedCode
		 */
		this.origUnifiedCode = origUnifiedCode;
	}
	/**
	 * @GET
	 * @return destUnifiedCode
	 */
	public String getDestUnifiedCode() {
		/*
		 *@get
		 *@ return destUnifiedCode
		 */
		return destUnifiedCode;
	}
	/**
	 * @SET
	 * @param destUnifiedCode
	 */
	public void setDestUnifiedCode(String destUnifiedCode) {
		/*
		 *@set
		 *@this.destUnifiedCode = destUnifiedCode
		 */
		this.destUnifiedCode = destUnifiedCode;
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
	 * @return landCostAgencyGen
	 */
	public BigDecimal getLandCostAgencyGen() {
		/*
		 *@get
		 *@ return landCostAgencyGen
		 */
		return landCostAgencyGen;
	}
	/**
	 * @SET
	 * @param landCostAgencyGen
	 */
	public void setLandCostAgencyGen(BigDecimal landCostAgencyGen) {
		/*
		 *@set
		 *@this.landCostAgencyGen = landCostAgencyGen
		 */
		this.landCostAgencyGen = landCostAgencyGen;
	}
	/**
	 * @GET
	 * @return landCostAgencyCfm
	 */
	public BigDecimal getLandCostAgencyCfm() {
		/*
		 *@get
		 *@ return landCostAgencyCfm
		 */
		return landCostAgencyCfm;
	}
	/**
	 * @SET
	 * @param landCostAgencyCfm
	 */
	public void setLandCostAgencyCfm(BigDecimal landCostAgencyCfm) {
		/*
		 *@set
		 *@this.landCostAgencyCfm = landCostAgencyCfm
		 */
		this.landCostAgencyCfm = landCostAgencyCfm;
	}
	/**
	 * @GET
	 * @return landCostAgencyNcfm
	 */
	public BigDecimal getLandCostAgencyNcfm() {
		/*
		 *@get
		 *@ return landCostAgencyNcfm
		 */
		return landCostAgencyNcfm;
	}
	/**
	 * @SET
	 * @param landCostAgencyNcfm
	 */
	public void setLandCostAgencyNcfm(BigDecimal landCostAgencyNcfm) {
		/*
		 *@set
		 *@this.landCostAgencyNcfm = landCostAgencyNcfm
		 */
		this.landCostAgencyNcfm = landCostAgencyNcfm;
	}
	/**
	 * @GET
	 * @return landCostOtherConfirm
	 */
	public BigDecimal getLandCostOtherConfirm() {
		/*
		 *@get
		 *@ return landCostOtherConfirm
		 */
		return landCostOtherConfirm;
	}
	/**
	 * @SET
	 * @param landCostOtherConfirm
	 */
	public void setLandCostOtherConfirm(BigDecimal landCostOtherConfirm) {
		/*
		 *@set
		 *@this.landCostOtherConfirm = landCostOtherConfirm
		 */
		this.landCostOtherConfirm = landCostOtherConfirm;
	}
	/**
	 * @GET
	 * @return landCostPayApply
	 */
	public BigDecimal getLandCostPayApply() {
		/*
		 *@get
		 *@ return landCostPayApply
		 */
		return landCostPayApply;
	}
	/**
	 * @SET
	 * @param landCostPayApply
	 */
	public void setLandCostPayApply(BigDecimal landCostPayApply) {
		/*
		 *@set
		 *@this.landCostPayApply = landCostPayApply
		 */
		this.landCostPayApply = landCostPayApply;
	}
	/**
	 * @GET
	 * @return landOthEntry
	 */
	public BigDecimal getLandOthEntry() {
		/*
		 *@get
		 *@ return landOthEntry
		 */
		return landOthEntry;
	}
	/**
	 * @SET
	 * @param landOthEntry
	 */
	public void setLandOthEntry(BigDecimal landOthEntry) {
		/*
		 *@set
		 *@this.landOthEntry = landOthEntry
		 */
		this.landOthEntry = landOthEntry;
	}
	/**
	 * @GET
	 * @return landOthRcvCh
	 */
	public BigDecimal getLandOthRcvCh() {
		/*
		 *@get
		 *@ return landOthRcvCh
		 */
		return landOthRcvCh;
	}
	/**
	 * @SET
	 * @param landOthRcvCh
	 */
	public void setLandOthRcvCh(BigDecimal landOthRcvCh) {
		/*
		 *@set
		 *@this.landOthRcvCh = landOthRcvCh
		 */
		this.landOthRcvCh = landOthRcvCh;
	}
	/**
	 * @GET
	 * @return landOthRcvCd
	 */
	public BigDecimal getLandOthRcvCd() {
		/*
		 *@get
		 *@ return landOthRcvCd
		 */
		return landOthRcvCd;
	}
	/**
	 * @SET
	 * @param landOthRcvCd
	 */
	public void setLandOthRcvCd(BigDecimal landOthRcvCd) {
		/*
		 *@set
		 *@this.landOthRcvCd = landOthRcvCd
		 */
		this.landOthRcvCd = landOthRcvCd;
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
	 * @return landCodChNpod
	 */
	public BigDecimal getLandCodChNpod() {
		/*
		 *@get
		 *@ return landCodChNpod
		 */
		return landCodChNpod;
	}
	/**
	 * @SET
	 * @param landCodChNpod
	 */
	public void setLandCodChNpod(BigDecimal landCodChNpod) {
		/*
		 *@set
		 *@this.landCodChNpod = landCodChNpod
		 */
		this.landCodChNpod = landCodChNpod;
	}
	/**
	 * @GET
	 * @return landCodCdNpod
	 */
	public BigDecimal getLandCodCdNpod() {
		/*
		 *@get
		 *@ return landCodCdNpod
		 */
		return landCodCdNpod;
	}
	/**
	 * @SET
	 * @param landCodCdNpod
	 */
	public void setLandCodCdNpod(BigDecimal landCodCdNpod) {
		/*
		 *@set
		 *@this.landCodCdNpod = landCodCdNpod
		 */
		this.landCodCdNpod = landCodCdNpod;
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
	 * @return landCodWoAgencyPayNpod
	 */
	public BigDecimal getLandCodWoAgencyPayNpod() {
		/*
		 *@get
		 *@ return landCodWoAgencyPayNpod
		 */
		return landCodWoAgencyPayNpod;
	}
	/**
	 * @SET
	 * @param landCodWoAgencyPayNpod
	 */
	public void setLandCodWoAgencyPayNpod(BigDecimal landCodWoAgencyPayNpod) {
		/*
		 *@set
		 *@this.landCodWoAgencyPayNpod = landCodWoAgencyPayNpod
		 */
		this.landCodWoAgencyPayNpod = landCodWoAgencyPayNpod;
	}
	/**
	 * @GET
	 * @return landCodWoOthNpod
	 */
	public BigDecimal getLandCodWoOthNpod() {
		/*
		 *@get
		 *@ return landCodWoOthNpod
		 */
		return landCodWoOthNpod;
	}
	/**
	 * @SET
	 * @param landCodWoOthNpod
	 */
	public void setLandCodWoOthNpod(BigDecimal landCodWoOthNpod) {
		/*
		 *@set
		 *@this.landCodWoOthNpod = landCodWoOthNpod
		 */
		this.landCodWoOthNpod = landCodWoOthNpod;
	}
	/**
	 * @GET
	 * @return landBdrWoOthRcv
	 */
	public BigDecimal getLandBdrWoOthRcv() {
		/*
		 *@get
		 *@ return landBdrWoOthRcv
		 */
		return landBdrWoOthRcv;
	}
	/**
	 * @SET
	 * @param landBdrWoOthRcv
	 */
	public void setLandBdrWoOthRcv(BigDecimal landBdrWoOthRcv) {
		/*
		 *@set
		 *@this.landBdrWoOthRcv = landBdrWoOthRcv
		 */
		this.landBdrWoOthRcv = landBdrWoOthRcv;
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
	 * @return landPrOthWoOthRcv
	 */
	public BigDecimal getLandPrOthWoOthRcv() {
		/*
		 *@get
		 *@ return landPrOthWoOthRcv
		 */
		return landPrOthWoOthRcv;
	}
	/**
	 * @SET
	 * @param landPrOthWoOthRcv
	 */
	public void setLandPrOthWoOthRcv(BigDecimal landPrOthWoOthRcv) {
		/*
		 *@set
		 *@this.landPrOthWoOthRcv = landPrOthWoOthRcv
		 */
		this.landPrOthWoOthRcv = landPrOthWoOthRcv;
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
	 * @return landDrCh
	 */
	public BigDecimal getLandDrCh() {
		/*
		 *@get
		 *@ return landDrCh
		 */
		return landDrCh;
	}
	/**
	 * @SET
	 * @param landDrCh
	 */
	public void setLandDrCh(BigDecimal landDrCh) {
		/*
		 *@set
		 *@this.landDrCh = landDrCh
		 */
		this.landDrCh = landDrCh;
	}
	/**
	 * @GET
	 * @return landDrCd
	 */
	public BigDecimal getLandDrCd() {
		/*
		 *@get
		 *@ return landDrCd
		 */
		return landDrCd;
	}
	/**
	 * @SET
	 * @param landDrCd
	 */
	public void setLandDrCd(BigDecimal landDrCd) {
		/*
		 *@set
		 *@this.landDrCd = landDrCd
		 */
		this.landDrCd = landDrCd;
	}
	/**
	 * @GET
	 * @return landDrWoOtherRcv
	 */
	public BigDecimal getLandDrWoOtherRcv() {
		/*
		 *@get
		 *@ return landDrWoOtherRcv
		 */
		return landDrWoOtherRcv;
	}
	/**
	 * @SET
	 * @param landDrWoOtherRcv
	 */
	public void setLandDrWoOtherRcv(BigDecimal landDrWoOtherRcv) {
		/*
		 *@set
		 *@this.landDrWoOtherRcv = landDrWoOtherRcv
		 */
		this.landDrWoOtherRcv = landDrWoOtherRcv;
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
	 * @return landDrWoCodRcvNpod
	 */
	public BigDecimal getLandDrWoCodRcvNpod() {
		/*
		 *@get
		 *@ return landDrWoCodRcvNpod
		 */
		return landDrWoCodRcvNpod;
	}
	/**
	 * @SET
	 * @param landDrWoCodRcvNpod
	 */
	public void setLandDrWoCodRcvNpod(BigDecimal landDrWoCodRcvNpod) {
		/*
		 *@set
		 *@this.landDrWoCodRcvNpod = landDrWoCodRcvNpod
		 */
		this.landDrWoCodRcvNpod = landDrWoCodRcvNpod;
	}
	/**
	 * @GET
	 * @return landDrPayApply
	 */
	public BigDecimal getLandDrPayApply() {
		/*
		 *@get
		 *@ return landDrPayApply
		 */
		return landDrPayApply;
	}
	/**
	 * @SET
	 * @param landDrPayApply
	 */
	public void setLandDrPayApply(BigDecimal landDrPayApply) {
		/*
		 *@set
		 *@this.landDrPayApply = landDrPayApply
		 */
		this.landDrPayApply = landDrPayApply;
	}
	/**
	 * @GET
	 * @return landAptCom
	 */
	public BigDecimal getLandAptCom() {
		/*
		 *@get
		 *@ return landAptCom
		 */
		return landAptCom;
	}
	/**
	 * @SET
	 * @param landAptCom
	 */
	public void setLandAptCom(BigDecimal landAptCom) {
		/*
		 *@set
		 *@this.landAptCom = landAptCom
		 */
		this.landAptCom = landAptCom;
	}
	/**
	 * @GET
	 * @return landAptWoComPay
	 */
	public BigDecimal getLandAptWoComPay() {
		/*
		 *@get
		 *@ return landAptWoComPay
		 */
		return landAptWoComPay;
	}
	/**
	 * @SET
	 * @param landAptWoComPay
	 */
	public void setLandAptWoComPay(BigDecimal landAptWoComPay) {
		/*
		 *@set
		 *@this.landAptWoComPay = landAptWoComPay
		 */
		this.landAptWoComPay = landAptWoComPay;
	}
	/**
	 * @GET
	 * @return landAptWoOthPay
	 */
	public BigDecimal getLandAptWoOthPay() {
		/*
		 *@get
		 *@ return landAptWoOthPay
		 */
		return landAptWoOthPay;
	}
	/**
	 * @SET
	 * @param landAptWoOthPay
	 */
	public void setLandAptWoOthPay(BigDecimal landAptWoOthPay) {
		/*
		 *@set
		 *@this.landAptWoOthPay = landAptWoOthPay
		 */
		this.landAptWoOthPay = landAptWoOthPay;
	}
	
	
	
}
