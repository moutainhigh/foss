package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * 专线到达DTO.
 *
 * @author guxinhua
 * @date 2013-3-6 上午11:11:36
 */
public class MvrRfdDto implements Serializable {
	/** serialVersionUID. */
	private static final long serialVersionUID = 6128359801326059811L;

	/** ID. */
	private String id;
	
	// 查询条件
	/** 期间. */
	private String period;
	
	/** 业务类型. */
	private String productCode;
	
	/** 业务类型集合. */
	private List<String> productCodeList;
	
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
	
	/** 用户编码-设置用户数据查询权限. */
	private String userCode;
	

	/** 还款运单总运费（到付）-还款现金未签收. */
	private BigDecimal urDestChNpod;
	
	/** 还款运单总运费（到付）-还款银行未签收. */
	private BigDecimal urDestCdNpod;
	
	/** 还款运单总运费（到付）-还款现金已签收. */
	private BigDecimal urDestChPod;
	
	/** 还款运单总运费（到付）-还款银行已签收. */
	private BigDecimal urDestCdPod;
	
	/** 理赔-理赔冲收入. */
	private BigDecimal claimDestWoIncome;
	
	/** 理赔-理赔入成本. */
	private BigDecimal claimDestCost;
	
	/** 理赔-理赔付款申请. */
	private BigDecimal claimDestPayApply;
	
	/** 理赔-理赔冲到达应收已签收. */
	private BigDecimal claimWoDestRcvPod;
	
	/** 理赔-理赔冲到达应收未签收. */
	private BigDecimal claimWoDestRcvNpod;
	
	/** 代收货款-还款代收货款现金未签收. */
	private BigDecimal codUrChNpod;
	
	/** 代收货款-还款代收货款银行未签收. */
	private BigDecimal codUrCdNpod;
	 
	/** 退运费-退运费冲收入. */
	private BigDecimal rdDestWoIncome;
	 
	
	/** 退运费-退运费入成本. */
	private BigDecimal rdDestCost;
	 
	
	/** 退运费-退运费付款申请. */
	private BigDecimal rdDestPayApply;

	/** 服务补救-到达服务补救付款申请. */
	private BigDecimal cnDestPayApply;
	
	/** 总条数. */
	private Long count;

	/**
	 * 退运费冲到达应收已签收
	 */
	private BigDecimal rdWoDestRcvPod;

	/**
	 * 退运费冲到达应收未签收
	 */
	private BigDecimal rdWoDestRcvNpod;

	/**
	 * 预收客户冲应收到付运费未签收
	 */
	private BigDecimal custDrDestRcvNpod;

	/**
	 * 预收客户冲应收到付运费已签收
	 */
	private BigDecimal custDrDestRcvPod;

	
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
	 * @return  the userCode
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * @param userCode the userCode to set
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
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
	 * @return  the claimDestWoIncome
	 */
	public BigDecimal getClaimDestWoIncome() {
		return claimDestWoIncome;
	}

	/**
	 * @param claimDestWoIncome the claimDestWoIncome to set
	 */
	public void setClaimDestWoIncome(BigDecimal claimDestWoIncome) {
		this.claimDestWoIncome = claimDestWoIncome;
	}

	/**
	 * @return  the claimDestCost
	 */
	public BigDecimal getClaimDestCost() {
		return claimDestCost;
	}

	/**
	 * @param claimDestCost the claimDestCost to set
	 */
	public void setClaimDestCost(BigDecimal claimDestCost) {
		this.claimDestCost = claimDestCost;
	}

	/**
	 * @return  the claimDestPayApply
	 */
	public BigDecimal getClaimDestPayApply() {
		return claimDestPayApply;
	}

	/**
	 * @param claimDestPayApply the claimDestPayApply to set
	 */
	public void setClaimDestPayApply(BigDecimal claimDestPayApply) {
		this.claimDestPayApply = claimDestPayApply;
	}

	/**
	 * @return  the claimWoDestRcvPod
	 */
	public BigDecimal getClaimWoDestRcvPod() {
		return claimWoDestRcvPod;
	}

	/**
	 * @param claimWoDestRcvPod the claimWoDestRcvPod to set
	 */
	public void setClaimWoDestRcvPod(BigDecimal claimWoDestRcvPod) {
		this.claimWoDestRcvPod = claimWoDestRcvPod;
	}

	/**
	 * @return  the claimWoDestRcvNpod
	 */
	public BigDecimal getClaimWoDestRcvNpod() {
		return claimWoDestRcvNpod;
	}

	/**
	 * @param claimWoDestRcvNpod the claimWoDestRcvNpod to set
	 */
	public void setClaimWoDestRcvNpod(BigDecimal claimWoDestRcvNpod) {
		this.claimWoDestRcvNpod = claimWoDestRcvNpod;
	}

	/**
	 * @return  the codUrChNpod
	 */
	public BigDecimal getCodUrChNpod() {
		return codUrChNpod;
	}

	/**
	 * @param codUrChNpod the codUrChNpod to set
	 */
	public void setCodUrChNpod(BigDecimal codUrChNpod) {
		this.codUrChNpod = codUrChNpod;
	}

	/**
	 * @return  the codUrCdNpod
	 */
	public BigDecimal getCodUrCdNpod() {
		return codUrCdNpod;
	}

	/**
	 * @param codUrCdNpod the codUrCdNpod to set
	 */
	public void setCodUrCdNpod(BigDecimal codUrCdNpod) {
		this.codUrCdNpod = codUrCdNpod;
	}

	/**
	 * @return  the rdDestWoIncome
	 */
	public BigDecimal getRdDestWoIncome() {
		return rdDestWoIncome;
	}

	/**
	 * @param rdDestWoIncome the rdDestWoIncome to set
	 */
	public void setRdDestWoIncome(BigDecimal rdDestWoIncome) {
		this.rdDestWoIncome = rdDestWoIncome;
	}

	/**
	 * @return  the rdDestCose
	 */
	public BigDecimal getRdDestCost() {
		return rdDestCost;
	}

	/**
	 * @param rdDestCose the rdDestCose to set
	 */
	public void setRdDestCost(BigDecimal rdDestCost) {
		this.rdDestCost = rdDestCost;
	}

	/**
	 * @return  the rdDestPayApply
	 */
	public BigDecimal getRdDestPayApply() {
		return rdDestPayApply;
	}

	/**
	 * @param rdDestPayApply the rdDestPayApply to set
	 */
	public void setRdDestPayApply(BigDecimal rdDestPayApply) {
		this.rdDestPayApply = rdDestPayApply;
	}

	/**
	 * @return  the cnDestPayApply
	 */
	public BigDecimal getCnDestPayApply() {
		return cnDestPayApply;
	}

	/**
	 * @param cnDestPayApply the cnDestPayApply to set
	 */
	public void setCnDestPayApply(BigDecimal cnDestPayApply) {
		this.cnDestPayApply = cnDestPayApply;
	}

	/**
	 * @return  the count
	 */
	public Long getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(Long count) {
		this.count = count;
	}

	/**
	 * @return  the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return productCodeList
	 */
	public List<String> getProductCodeList() {
		return productCodeList;
	}

	/**
	 * @param productCodeList
	 */
	public void setProductCodeList(List<String> productCodeList) {
		this.productCodeList = productCodeList;
	}

	/**
	 * @return the rdWoDestRcvPod
	 */
	public BigDecimal getRdWoDestRcvPod() {
		return rdWoDestRcvPod;
	}

	/**
	 * @param rdWoDestRcvPod the rdWoDestRcvPod to set
	 */
	public void setRdWoDestRcvPod(BigDecimal rdWoDestRcvPod) {
		this.rdWoDestRcvPod = rdWoDestRcvPod;
	}

	/**
	 * @return the rdWoDestRcvNpod
	 */
	public BigDecimal getRdWoDestRcvNpod() {
		return rdWoDestRcvNpod;
	}

	/**
	 * @param rdWoDestRcvNpod the rdWoDestRcvNpod to set
	 */
	public void setRdWoDestRcvNpod(BigDecimal rdWoDestRcvNpod) {
		this.rdWoDestRcvNpod = rdWoDestRcvNpod;
	}

	/**
	 * @return the custDrDestRcvNpod
	 */
	public BigDecimal getCustDrDestRcvNpod() {
		return custDrDestRcvNpod;
	}

	/**
	 * @param custDrDestRcvNpod the custDrDestRcvNpod to set
	 */
	public void setCustDrDestRcvNpod(BigDecimal custDrDestRcvNpod) {
		this.custDrDestRcvNpod = custDrDestRcvNpod;
	}

	/**
	 * @return the custDrDestRcvPod
	 */
	public BigDecimal getCustDrDestRcvPod() {
		return custDrDestRcvPod;
	}

	/**
	 * @param custDrDestRcvPod the custDrDestRcvPod to set
	 */
	public void setCustDrDestRcvPod(BigDecimal custDrDestRcvPod) {
		this.custDrDestRcvPod = custDrDestRcvPod;
	}

}
