 
package com.deppon.foss.module.base.querying.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * ESB理赔单返回数据实体
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2013-4-24 下午4:19:29
 */
public class QueryClaimbillResultDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 运单号
	 */
	private String waybillNum;

	/**
	 * 客户编码
	 */
	private String custNum;

	/**
	 * 客户名称
	 */
	private String custName;

	/**
	 * 出险类型：BREAKED 破损，DAMP 潮湿，POLLUTE 污染，GOODS_LESS 内物短少，PIECE_LOST
	 * 整件丢失，TICKET_LOST 整票丢失，FALSELY_CLAIM 冒领，OTHER 其它
	 */
	private String riskType;
	 
	/**
	 * 出险时间
	 */
	private Date riskTime;

	/**
	 * 报案时间
	 */
	private Date reportCaseTime;

	/**
	 * 索赔金额
	 */
	private BigDecimal claimSum;

	/**
	 * 实际理赔金额
	 */
	private BigDecimal actualPaySum;

	/**
	 * 收货部门名称
	 */
	private String receivingDeptName;

	/**
	 * 出险信息
	 */
	private String riskInfo;

	/**
	 * 处理状态
	 */
	private String status;

	/**
	 * 处理人
	 */
	private String dealingPeople;

	/**
	 * 理赔专员处理时间
	 */
	private Date dealingTime;

	/**
	 * 最终审批人（OA审批）
	 */
	private String finalApproval;

	/**
	 * 最终审批意见（OA中审批）
	 */
	private String finalApprovalOpinion;

	/**
	 * 入部门费用（入部门费用如果是多个需要拼接成文本：如A部门：xx；B部门:xx;）
	 */
	private String indeptCharge;

	/**
	 * 入公司费用
	 */
	private BigDecimal inCompanyCharge;

	/**
	 * 其他费用
	 */
	private BigDecimal otherCharge;
	/**
	 * 出险原因
	 */
	private String dangerCause;
	/**
	 * 经手是否有责任
	 */
	private String passIsDuty;
	/**
	 * 责任部门名称
	 */
	private String responsibilityOrgName;
	/**
	 * 责任部门编码
	 */
	private String responsibilityOrgCode;
	
	public String getDangerCause() {
		return dangerCause;
	}

	public void setDangerCause(String dangerCause) {
		this.dangerCause = dangerCause;
	}

	public String getPassIsDuty() {
		return passIsDuty;
	}

	public void setPassIsDuty(String passIsDuty) {
		this.passIsDuty = passIsDuty;
	}

	public String getResponsibilityOrgName() {
		return responsibilityOrgName;
	}

	public void setResponsibilityOrgName(String responsibilityOrgName) {
		this.responsibilityOrgName = responsibilityOrgName;
	}

	public String getResponsibilityOrgCode() {
		return responsibilityOrgCode;
	}

	public void setResponsibilityOrgCode(String responsibilityOrgCode) {
		this.responsibilityOrgCode = responsibilityOrgCode;
	}

	public String getCustNum() {
		return custNum;
	}

	public void setCustNum(String custNum) {
		this.custNum = custNum;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getWaybillNum() {
		return waybillNum;
	}

	public void setWaybillNum(String waybillNum) {
		this.waybillNum = waybillNum;
	}

	public String getRiskType() {
		return riskType;
	}

	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}

	public Date getRiskTime() {
		return riskTime;
	}

	public void setRiskTime(Date riskTime) {
		this.riskTime = riskTime;
	}

	public Date getReportCaseTime() {
		return reportCaseTime;
	}

	public void setReportCaseTime(Date reportCaseTime) {
		this.reportCaseTime = reportCaseTime;
	}

	public BigDecimal getClaimSum() {
		return claimSum;
	}

	public void setClaimSum(BigDecimal claimSum) {
		this.claimSum = claimSum;
	}

	public BigDecimal getActualPaySum() {
		return actualPaySum;
	}

	public void setActualPaySum(BigDecimal actualPaySum) {
		this.actualPaySum = actualPaySum;
	}

	public String getReceivingDeptName() {
		return receivingDeptName;
	}

	public void setReceivingDeptName(String receivingDeptName) {
		this.receivingDeptName = receivingDeptName;
	}

	public String getRiskInfo() {
		return riskInfo;
	}

	public void setRiskInfo(String riskInfo) {
		this.riskInfo = riskInfo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDealingPeople() {
		return dealingPeople;
	}

	public void setDealingPeople(String dealingPeople) {
		this.dealingPeople = dealingPeople;
	}

	public Date getDealingTime() {
		return dealingTime;
	}

	public void setDealingTime(Date dealingTime) {
		this.dealingTime = dealingTime;
	}

	public String getFinalApproval() {
		return finalApproval;
	}

	public void setFinalApproval(String finalApproval) {
		this.finalApproval = finalApproval;
	}

	public String getFinalApprovalOpinion() {
		return finalApprovalOpinion;
	}

	public void setFinalApprovalOpinion(String finalApprovalOpinion) {
		this.finalApprovalOpinion = finalApprovalOpinion;
	}
	
	public String getIndeptCharge() {
		return indeptCharge;
	}

	
	public void setIndeptCharge(String indeptCharge) {
		this.indeptCharge = indeptCharge;
	}

	public BigDecimal getInCompanyCharge() {
		return inCompanyCharge;
	}

	public void setInCompanyCharge(BigDecimal inCompanyCharge) {
		this.inCompanyCharge = inCompanyCharge;
	}

	public BigDecimal getOtherCharge() {
		return otherCharge;
	}

	public void setOtherCharge(BigDecimal otherCharge) {
		this.otherCharge = otherCharge;
	} 
}
