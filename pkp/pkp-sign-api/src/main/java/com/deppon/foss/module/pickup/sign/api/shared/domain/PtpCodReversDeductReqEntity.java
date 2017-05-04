/**
 * 
 */
package com.deppon.foss.module.pickup.sign.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * PTP合伙人代收货款扣款-反结清(退款)-请求Request实体
 * @author 239284
 *
 */
public class PtpCodReversDeductReqEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8257764712589668321L;

	/**
	 * 合伙人部门编码
	 */
	private String partnerOrgCode;

	/**
	 * 合伙人部门名称
	 */
	private String partnerOrgName;
	

	/**
	 * 来源单号
	 */
	private String sourceBillNo;

	
	/**
	 * 业务类别
	 */
	private String sourceBillType;

	/**
	 * 来源系统
	 */
	private String sourceSystem;
	
	
	/**
	 * 业务日期
	 */
	private Date bizDate;
	
	
	/**
	 * 业务日期
	 */
	private Date accountDate;

	
	/**
	 * 扣款金额
	 */
	private BigDecimal amount;
	
	
	/**
	 * 操作人编码
	 */
	private String operatorCode;
	
	
	/**
	 * 操作人姓名
	 */
	private String operatorName;
	
	/**
	 * 备注信息
	 */
	private String remark;
	
	/**
	 * 流水类别-和业务类别传的值一致
	 */
	private String flowType;

	public String getPartnerOrgCode() {
		return partnerOrgCode;
	}

	public void setPartnerOrgCode(String partnerOrgCode) {
		this.partnerOrgCode = partnerOrgCode;
	}

	public String getPartnerOrgName() {
		return partnerOrgName;
	}

	public void setPartnerOrgName(String partnerOrgName) {
		this.partnerOrgName = partnerOrgName;
	}

	public String getSourceBillNo() {
		return sourceBillNo;
	}

	public void setSourceBillNo(String sourceBillNo) {
		this.sourceBillNo = sourceBillNo;
	}

	public String getSourceBillType() {
		return sourceBillType;
	}

	public void setSourceBillType(String sourceBillType) {
		this.sourceBillType = sourceBillType;
	}

	public String getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	public Date getBizDate() {
		return bizDate;
	}

	public void setBizDate(Date bizDate) {
		this.bizDate = bizDate;
	}

	public Date getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getFlowType() {
		return flowType;
	}

	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}
	
}
