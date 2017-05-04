/**
 * 
 */
package com.deppon.foss.module.pickup.sign.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 *  PTP合伙人代收货款扣款-请求Request实体
 * @author 239284
 * 
 *
 */
public class PtpCodDeductReqEntity {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -4803689293496583439L;

	/**
	 * 合伙人部门编码
	 */
	private String partnerOrgCode;

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
	 * 签收时间
	 */
	private Date signDate;
	
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

	public String getPartnerOrgCode() {
		return partnerOrgCode;
	}

	public void setPartnerOrgCode(String partnerOrgCode) {
		this.partnerOrgCode = partnerOrgCode;
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

	/**
	 * @return the bizDate
	 */
	public Date getBizDate() {
		return bizDate;
	}

	/**
	 * @param bizDate the bizDate to set
	 */
	public void setBizDate(Date bizDate) {
		this.bizDate = bizDate;
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

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the signDate
	 */
	public Date getSignDate() {
		return signDate;
	}

	/**
	 * @param signDate the signDate to set
	 */
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
}
