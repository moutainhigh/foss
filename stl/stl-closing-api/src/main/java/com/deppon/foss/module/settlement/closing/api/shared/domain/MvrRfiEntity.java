package com.deppon.foss.module.settlement.closing.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 始发到达往来.
 *
 * @author ibm-zhuwei
 * @date 2013-3-5 下午5:57:16
 */
public class MvrRfiEntity implements Serializable{
		
	/** 序列化id. */
	private static final long serialVersionUID = 2053359146359788949L;
	
	/** id. */
	 private String id;

	    private String period;

	    private String productCode;

	    private String customerCode;

	    private String customerName;

	    private String orgCode;

	    private String orgName;

	    private String orgType;

	    private String orgUnifiedCode;

	    private Date voucherBeginTime;

	    private Date voucherEndTime;

	    private String customerType;

	    private BigDecimal urDestChNpod;

	    private BigDecimal urDestCdNpod;

	    private BigDecimal urDestChPod;

	    private BigDecimal urDestCdPod;

	    private BigDecimal claimDestWoIncome;

	    private BigDecimal claimWoDestRcvPod;

	    private BigDecimal claimWoDestRcvNpod;

	    private BigDecimal codDestRcvPod;

	    private BigDecimal codDestRcvNpod;

	    private BigDecimal codOrigRcvPod;

	    private BigDecimal codOrigRcvNpod;

	    private BigDecimal codUrChNpod;

	    private BigDecimal codUrCdNpod;

	    private BigDecimal codWoOrRcv;

	    private BigDecimal rdDestWoIncome;

	    private int count;
	    
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
		 * @return  the orgCode
		 */
		public String getOrgCode() {
			return orgCode;
		}

		
		/**
		 * @param orgCode the orgCode to set
		 */
		public void setOrgCode(String orgCode) {
			this.orgCode = orgCode;
		}

		
		/**
		 * @return  the orgName
		 */
		public String getOrgName() {
			return orgName;
		}

		
		/**
		 * @param orgName the orgName to set
		 */
		public void setOrgName(String orgName) {
			this.orgName = orgName;
		}

		
		/**
		 * @return  the orgType
		 */
		public String getOrgType() {
			return orgType;
		}

		
		/**
		 * @param orgType the orgType to set
		 */
		public void setOrgType(String orgType) {
			this.orgType = orgType;
		}

		
		/**
		 * @return  the orgUnifiedCode
		 */
		public String getOrgUnifiedCode() {
			return orgUnifiedCode;
		}

		
		/**
		 * @param orgUnifiedCode the orgUnifiedCode to set
		 */
		public void setOrgUnifiedCode(String orgUnifiedCode) {
			this.orgUnifiedCode = orgUnifiedCode;
		}

		
		/**
		 * @return  the voucherBeginTime
		 */
		public Date getVoucherBeginTime() {
			return voucherBeginTime;
		}

		
		/**
		 * @param voucherBeginTime the voucherBeginTime to set
		 */
		public void setVoucherBeginTime(Date voucherBeginTime) {
			this.voucherBeginTime = voucherBeginTime;
		}

		
		/**
		 * @return  the voucherEndTime
		 */
		public Date getVoucherEndTime() {
			return voucherEndTime;
		}

		
		/**
		 * @param voucherEndTime the voucherEndTime to set
		 */
		public void setVoucherEndTime(Date voucherEndTime) {
			this.voucherEndTime = voucherEndTime;
		}

		
		/**
		 * @return  the customerType
		 */
		public String getCustomerType() {
			return customerType;
		}

		
		/**
		 * @param customerType the customerType to set
		 */
		public void setCustomerType(String customerType) {
			this.customerType = customerType;
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
		 * @return  the codDestRcvPod
		 */
		public BigDecimal getCodDestRcvPod() {
			return codDestRcvPod;
		}

		
		/**
		 * @param codDestRcvPod the codDestRcvPod to set
		 */
		public void setCodDestRcvPod(BigDecimal codDestRcvPod) {
			this.codDestRcvPod = codDestRcvPod;
		}

		
		/**
		 * @return  the codDestRcvNpod
		 */
		public BigDecimal getCodDestRcvNpod() {
			return codDestRcvNpod;
		}

		
		/**
		 * @param codDestRcvNpod the codDestRcvNpod to set
		 */
		public void setCodDestRcvNpod(BigDecimal codDestRcvNpod) {
			this.codDestRcvNpod = codDestRcvNpod;
		}

		
		/**
		 * @return  the codOrigRcvPod
		 */
		public BigDecimal getCodOrigRcvPod() {
			return codOrigRcvPod;
		}

		
		/**
		 * @param codOrigRcvPod the codOrigRcvPod to set
		 */
		public void setCodOrigRcvPod(BigDecimal codOrigRcvPod) {
			this.codOrigRcvPod = codOrigRcvPod;
		}

		
		/**
		 * @return  the codOrigRcvNpod
		 */
		public BigDecimal getCodOrigRcvNpod() {
			return codOrigRcvNpod;
		}

		
		/**
		 * @param codOrigRcvNpod the codOrigRcvNpod to set
		 */
		public void setCodOrigRcvNpod(BigDecimal codOrigRcvNpod) {
			this.codOrigRcvNpod = codOrigRcvNpod;
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
		 * @return  the codWoOrRcv
		 */
		public BigDecimal getCodWoOrRcv() {
			return codWoOrRcv;
		}

		
		/**
		 * @param codWoOrRcv the codWoOrRcv to set
		 */
		public void setCodWoOrRcv(BigDecimal codWoOrRcv) {
			this.codWoOrRcv = codWoOrRcv;
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
		 * @return  the count
		 */
		public int getCount() {
			return count;
		}


		
		/**
		 * @param count the count to set
		 */
		public void setCount(int count) {
			this.count = count;
		}


		public BigDecimal getRdWoDestRcvPod() {
			return rdWoDestRcvPod;
		}


		public void setRdWoDestRcvPod(BigDecimal rdWoDestRcvPod) {
			this.rdWoDestRcvPod = rdWoDestRcvPod;
		}


		public BigDecimal getRdWoDestRcvNpod() {
			return rdWoDestRcvNpod;
		}


		public void setRdWoDestRcvNpod(BigDecimal rdWoDestRcvNpod) {
			this.rdWoDestRcvNpod = rdWoDestRcvNpod;
		}


		public BigDecimal getCustDrDestRcvNpod() {
			return custDrDestRcvNpod;
		}


		public void setCustDrDestRcvNpod(BigDecimal custDrDestRcvNpod) {
			this.custDrDestRcvNpod = custDrDestRcvNpod;
		}


		public BigDecimal getCustDrDestRcvPod() {
			return custDrDestRcvPod;
		}


		public void setCustDrDestRcvPod(BigDecimal custDrDestRcvPod) {
			this.custDrDestRcvPod = custDrDestRcvPod;
		}


}
