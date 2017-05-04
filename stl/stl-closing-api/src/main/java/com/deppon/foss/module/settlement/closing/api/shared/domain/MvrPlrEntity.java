package com.deppon.foss.module.settlement.closing.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;




/**
 * 偏线月报.
 *
 * @author ibm-zhuwei
 * @date 2013-3-5 下午5:57:28
 */
public class MvrPlrEntity extends BaseEntity{

	/** 序列化. */
	private static final long serialVersionUID = 6350362771612989118L;

	/** id. */
	private String id;

	/** 期间. */
	private String period;

	/** 业务类型. */
	private String productCode;
	
	/**
	 * 业务名称
	 */
	private String productName;

	/** 客户编码. */
	private String customerCode;

	/** 客户名称. */
	private String customerName;

	/** 始发部门编码. */
	private String origOrgCode;

	/** 始发部门名称. */
	private String origOrgName;

	/** 到达部门编码. */
	private String destOrgCode;

	/** 到达部门名称. */
	private String destOrgName;

	/** 统计时间. */
	private Date statisticalTime;
	
	
	/** 外发反馈录入. */
	private BigDecimal plCostVech;
	
	/** 偏线代理成本确认. */
	private BigDecimal plCostConfirm;
	
	/** 偏线代理成本反确认. */
	private BigDecimal plCostNotConfirm;
	
	/** 应付偏线代理成本冲应收到付运费已签收. */
	private BigDecimal plCostWoDestRcvPod;
	
	/** 应付偏线代理成本冲应收到付运费未签收. */
	private BigDecimal plCostWoDestRcvNpod;
	
	/** 偏线代理成本付款申请. */
	private BigDecimal plCostPayApply;
	
	/** 还款现金未签收. */
	private BigDecimal urDestChNpod;
	
	/** 还款银行未签收. */
	private BigDecimal urDestCdNpod;
	
	/** 还款现金已签收. */
	private BigDecimal urDestChPod;
	
	/** 还款银行已签收. */
	private BigDecimal urDestCdPod;
	
	/** 预收偏线代理冲应收到付运费已签收. */
	private BigDecimal plDrWoDestRcvPod;
	

	/** 预收偏线代理冲应收到付运费未签收. */
	private BigDecimal plDrWoDestRcvNpod;
	
	/** 预收偏线代理现金. */
	private BigDecimal plDrCh;
	
	/** 预收偏线代理银行. */
	private BigDecimal plDrCd;

	/** 偏线退预收付款申请. */
	private BigDecimal plDrPayApply;
	
	
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
	 * @return  the period
	 */
	public String getPeriod() {
		return period;
	}

	/**
	 * @param period the period to set
	 */
	public void setPeriod(String period) {
		this.period = period;
	}

	/**
	 * @return  the productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * @return  the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return  the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * @return  the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return  the origOrgCode
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}

	/**
	 * @param origOrgCode the origOrgCode to set
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	/**
	 * @return  the origOrgName
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}

	/**
	 * @param origOrgName the origOrgName to set
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	/**
	 * @return  the destOrgCode
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}

	/**
	 * @param destOrgCode the destOrgCode to set
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	/**
	 * @return  the destOrgName
	 */
	public String getDestOrgName() {
		return destOrgName;
	}

	/**
	 * @param destOrgName the destOrgName to set
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	/**
	 * @return  the statisticalTime
	 */
	public Date getStatisticalTime() {
		return statisticalTime;
	}

	/**
	 * @param statisticalTime the statisticalTime to set
	 */
	public void setStatisticalTime(Date statisticalTime) {
		this.statisticalTime = statisticalTime;
	}

	/**
	 * @return  the plCostVech
	 */
	public BigDecimal getPlCostVech() {
		return plCostVech;
	}

	/**
	 * @param plCostVech the plCostVech to set
	 */
	public void setPlCostVech(BigDecimal plCostVech) {
		this.plCostVech = plCostVech;
	}

	/**
	 * @return  the plCostConfirm
	 */
	public BigDecimal getPlCostConfirm() {
		return plCostConfirm;
	}

	/**
	 * @param plCostConfirm the plCostConfirm to set
	 */
	public void setPlCostConfirm(BigDecimal plCostConfirm) {
		this.plCostConfirm = plCostConfirm;
	}

	/**
	 * @return  the plCostNotConfirm
	 */
	public BigDecimal getPlCostNotConfirm() {
		return plCostNotConfirm;
	}

	/**
	 * @param plCostNotConfirm the plCostNotConfirm to set
	 */
	public void setPlCostNotConfirm(BigDecimal plCostNotConfirm) {
		this.plCostNotConfirm = plCostNotConfirm;
	}

	/**
	 * @return  the plCostWoDestRcvPod
	 */
	public BigDecimal getPlCostWoDestRcvPod() {
		return plCostWoDestRcvPod;
	}

	/**
	 * @param plCostWoDestRcvPod the plCostWoDestRcvPod to set
	 */
	public void setPlCostWoDestRcvPod(BigDecimal plCostWoDestRcvPod) {
		this.plCostWoDestRcvPod = plCostWoDestRcvPod;
	}

	/**
	 * @return  the plCostWoDestRcvNpod
	 */
	public BigDecimal getPlCostWoDestRcvNpod() {
		return plCostWoDestRcvNpod;
	}

	/**
	 * @param plCostWoDestRcvNpod the plCostWoDestRcvNpod to set
	 */
	public void setPlCostWoDestRcvNpod(BigDecimal plCostWoDestRcvNpod) {
		this.plCostWoDestRcvNpod = plCostWoDestRcvNpod;
	}

	/**
	 * @return  the plCostPayApply
	 */
	public BigDecimal getPlCostPayApply() {
		return plCostPayApply;
	}

	/**
	 * @param plCostPayApply the plCostPayApply to set
	 */
	public void setPlCostPayApply(BigDecimal plCostPayApply) {
		this.plCostPayApply = plCostPayApply;
	}

	/**
	 * @return  the urDestChNpod
	 */
	public BigDecimal getUrDestChNpod() {
		return urDestChNpod;
	}

	/**
	 * @param urDestChNpod the urDestChNpod to set
	 */
	public void setUrDestChNpod(BigDecimal urDestChNpod) {
		this.urDestChNpod = urDestChNpod;
	}

	/**
	 * @return  the urDestCdNpod
	 */
	public BigDecimal getUrDestCdNpod() {
		return urDestCdNpod;
	}

	/**
	 * @param urDestCdNpod the urDestCdNpod to set
	 */
	public void setUrDestCdNpod(BigDecimal urDestCdNpod) {
		this.urDestCdNpod = urDestCdNpod;
	}

	/**
	 * @return  the urDestChPod
	 */
	public BigDecimal getUrDestChPod() {
		return urDestChPod;
	}

	/**
	 * @param urDestChPod the urDestChPod to set
	 */
	public void setUrDestChPod(BigDecimal urDestChPod) {
		this.urDestChPod = urDestChPod;
	}

	
	/**
	 * @return  the plDrWoDestRcvPod
	 */
	public BigDecimal getPlDrWoDestRcvPod() {
		return plDrWoDestRcvPod;
	}

	/**
	 * @param plDrWoDestRcvPod the plDrWoDestRcvPod to set
	 */
	public void setPlDrWoDestRcvPod(BigDecimal plDrWoDestRcvPod) {
		this.plDrWoDestRcvPod = plDrWoDestRcvPod;
	}

	/**
	 * @return  the plDrWoDestRcvNpod
	 */
	public BigDecimal getPlDrWoDestRcvNpod() {
		return plDrWoDestRcvNpod;
	}

	/**
	 * @param plDrWoDestRcvNpod the plDrWoDestRcvNpod to set
	 */
	public void setPlDrWoDestRcvNpod(BigDecimal plDrWoDestRcvNpod) {
		this.plDrWoDestRcvNpod = plDrWoDestRcvNpod;
	}

	/**
	 * @return  the plDrCh
	 */
	public BigDecimal getPlDrCh() {
		return plDrCh;
	}

	/**
	 * @param plDrCh the plDrCh to set
	 */
	public void setPlDrCh(BigDecimal plDrCh) {
		this.plDrCh = plDrCh;
	}


	/**
	 * @return  the urDestCdPod
	 */
	public BigDecimal getUrDestCdPod() {
		return urDestCdPod;
	}

	/**
	 * @param urDestCdPod the urDestCdPod to set
	 */
	public void setUrDestCdPod(BigDecimal urDestCdPod) {
		this.urDestCdPod = urDestCdPod;
	}

	/**
	 * @return  the plDrCd
	 */
	public BigDecimal getPlDrCd() {
		return plDrCd;
	}

	/**
	 * @param plDrCd the plDrCd to set
	 */
	public void setPlDrCd(BigDecimal plDrCd) {
		this.plDrCd = plDrCd;
	}

	/**
	 * @return  the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return  the plDrPayApply
	 */
	public BigDecimal getPlDrPayApply() {
		return plDrPayApply;
	}

	/**
	 * @param plDrPayApply the plDrPayApply to set
	 */
	public void setPlDrPayApply(BigDecimal plDrPayApply) {
		this.plDrPayApply = plDrPayApply;
	}
	
	
	
	
	}
