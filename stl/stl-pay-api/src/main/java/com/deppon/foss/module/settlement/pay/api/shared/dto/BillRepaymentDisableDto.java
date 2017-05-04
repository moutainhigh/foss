package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.pay.api.shared.domain.RepaymentDisableApplicationEntity;
import com.deppon.foss.module.settlement.pay.api.shared.domain.RepaymentDisableDetailEntity;


/**
 * 
 *申请作废还款单dto
 *
 * @author 092036-foss-bochenlong
 * @date 2013-11-19 下午4:10:31
 */
public class BillRepaymentDisableDto extends RepaymentDisableApplicationEntity implements Serializable{

	private static final long serialVersionUID = 6277666619846783584L;
	
	/**还款单号*/
	private String repaymentNo;
	/**还款单付款方式*/
	private String paymentType;
	/**客户编码*/
	private String customerCode;
	/**客户名称*/
	private String customerName;
	/**还款单金额*/
	private BigDecimal repaymentAmount;
	/**作废原因*/
	private String disableReason;
	/**作废明细*/
	private List<RepaymentDisableDetailEntity> details;
	
	/**申请作废部门名称*/
	private String applyOrgName;

	/**权限部门*/
	private List<String> orgCodes;
	/**角色*/
	private String role;
	/**审批状态*/
	private String approveStatus;
	/**查询类型*/
	private String queryType;
	/**查询期间*/
	private Date applyDateStart;
	private Date applyDateEnd;
	/**还款单号集合*/
    private List<String> repaymentNos;
	
    /**申请作废小区编码*/
	private String applyParentOrgCode;
	/**申请作废大区编码*/
	private String applyAreaCode;
	/**申请作废部门编码*/
	private String applyOrgCode;
	/**审批作废的申请*/
	private List<String> applyIDs;
	
	/**操作類型*/
	private String operateType;
	/**当前登录人员*/
	private String empCode;
	
	/**是否整单作废*/
	private String isAllDisable;
	/**核销单ID*/
	private List<String> detailIds;
	/**作废金额*/
	private BigDecimal amount;
	/**作废备注*/
	private String disableNote;
	/**作废条数*/
	private int disableNum;
	
	/**明细ID*/
	private String detailId;
	
	/**applyId*/
	private String applyId;
	
	/**导出列头
	 */
	private String[] arrayColumns;

	/**导出列中文名称
	 */
	private String[] arrayColumnNames;

	/**
	 * @return the repaymentNo
	 */
	public String getRepaymentNo() {
		return repaymentNo;
	}

	/**
	 * @param repaymentNo the repaymentNo to set
	 */
	public void setRepaymentNo(String repaymentNo) {
		this.repaymentNo = repaymentNo;
	}

	/**
	 * @return the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType the paymentType to set
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * @return the customerCode
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
	 * @return the customerName
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
	 * @return the repaymentAmount
	 */
	public BigDecimal getRepaymentAmount() {
		return repaymentAmount;
	}

	/**
	 * @param repaymentAmount the repaymentAmount to set
	 */
	public void setRepaymentAmount(BigDecimal repaymentAmount) {
		this.repaymentAmount = repaymentAmount;
	}

	/**
	 * @return the disableReason
	 */
	public String getDisableReason() {
		return disableReason;
	}

	/**
	 * @param disableReason the disableReason to set
	 */
	public void setDisableReason(String disableReason) {
		this.disableReason = disableReason;
	}

	/**
	 * @return the details
	 */
	public List<RepaymentDisableDetailEntity> getDetails() {
		if(details == null) {
			details = new ArrayList<RepaymentDisableDetailEntity>();
		}
		return details;
	}

	/**
	 * @param details the details to set
	 */
	public void setDetails(List<RepaymentDisableDetailEntity> details) {
		this.details = details;
	}

	/**
	 * @return the applyOrgName
	 */
	public String getApplyOrgName() {
		return applyOrgName;
	}

	/**
	 * @param applyOrgName the applyOrgName to set
	 */
	public void setApplyOrgName(String applyOrgName) {
		this.applyOrgName = applyOrgName;
	}

	/**
	 * @return the orgCodes
	 */
	public List<String> getOrgCodes() {
		return orgCodes;
	}

	/**
	 * @param orgCodes the orgCodes to set
	 */
	public void setOrgCodes(List<String> orgCodes) {
		this.orgCodes = orgCodes;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the approveStatus
	 */
	public String getApproveStatus() {
		return approveStatus;
	}

	/**
	 * @param approveStatus the approveStatus to set
	 */
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	/**
	 * @return the queryType
	 */
	public String getQueryType() {
		return queryType;
	}

	/**
	 * @param queryType the queryType to set
	 */
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	/**
	 * @return the applyDateStart
	 */
	public Date getApplyDateStart() {
		return applyDateStart;
	}

	/**
	 * @param applyDateStart the applyDateStart to set
	 */
	public void setApplyDateStart(Date applyDateStart) {
		this.applyDateStart = applyDateStart;
	}

	/**
	 * @return the applyDateEnd
	 */
	public Date getApplyDateEnd() {
		return applyDateEnd;
	}

	/**
	 * @param applyDateEnd the applyDateEnd to set
	 */
	public void setApplyDateEnd(Date applyDateEnd) {
		this.applyDateEnd = applyDateEnd;
	}

	/**
	 * @return the repaymentNos
	 */
	public List<String> getRepaymentNos() {
		return repaymentNos;
	}

	/**
	 * @param repaymentNos the repaymentNos to set
	 */
	public void setRepaymentNos(List<String> repaymentNos) {
		this.repaymentNos = repaymentNos;
	}


	/**
	 * @return the applyParentOrgCode
	 */
	public String getApplyParentOrgCode() {
		return applyParentOrgCode;
	}

	/**
	 * @param applyParentOrgCode the applyParentOrgCode to set
	 */
	public void setApplyParentOrgCode(String applyParentOrgCode) {
		this.applyParentOrgCode = applyParentOrgCode;
	}

	/**
	 * @return the applyAreaCode
	 */
	public String getApplyAreaCode() {
		return applyAreaCode;
	}

	/**
	 * @param applyAreaCode the applyAreaCode to set
	 */
	public void setApplyAreaCode(String applyAreaCode) {
		this.applyAreaCode = applyAreaCode;
	}

	/**
	 * @return the applyOrgCode
	 */
	public String getApplyOrgCode() {
		return applyOrgCode;
	}

	/**
	 * @param applyOrgCode the applyOrgCode to set
	 */
	public void setApplyOrgCode(String applyOrgCode) {
		this.applyOrgCode = applyOrgCode;
	}

	/**
	 * @return the applyIDs
	 */
	public List<String> getApplyIDs() {
		return applyIDs;
	}

	/**
	 * @param applyIDs the applyIDs to set
	 */
	public void setApplyIDs(List<String> applyIDs) {
		this.applyIDs = applyIDs;
	}

	/**
	 * @return the operateType
	 */
	public String getOperateType() {
		return operateType;
	}

	/**
	 * @param operateType the operateType to set
	 */
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	/**
	 * @return the empCode
	 */
	public String getEmpCode() {
		return empCode;
	}

	/**
	 * @param empCode the empCode to set
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	/**
	 * @return the isAllDisable
	 */
	public String getIsAllDisable() {
		return isAllDisable;
	}

	/**
	 * @param isAllDisable the isAllDisable to set
	 */
	public void setIsAllDisable(String isAllDisable) {
		this.isAllDisable = isAllDisable;
	}

	/**
	 * @return the detailIds
	 */
	public List<String> getDetailIds() {
		return detailIds;
	}

	/**
	 * @param detailIds the detailIds to set
	 */
	public void setDetailIds(List<String> detailIds) {
		this.detailIds = detailIds;
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return the disableNote
	 */
	public String getDisableNote() {
		return disableNote;
	}

	/**
	 * @param disableNote the disableNote to set
	 */
	public void setDisableNote(String disableNote) {
		this.disableNote = disableNote;
	}

	/**
	 * @return the disableNum
	 */
	public int getDisableNum() {
		return disableNum;
	}

	/**
	 * @param disableNum the disableNum to set
	 */
	public void setDisableNum(int disableNum) {
		this.disableNum = disableNum;
	}

	/**
	 * @return the detailId
	 */
	public String getDetailId() {
		return detailId;
	}

	/**
	 * @param detailId the detailId to set
	 */
	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	/**
	 * @return the applyId
	 */
	public String getApplyId() {
		return applyId;
	}

	/**
	 * @param applyId the applyId to set
	 */
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	/**
	 * @return the arrayColumns
	 */
	public String[] getArrayColumns() {
		return arrayColumns;
	}

	/**
	 * @param arrayColumns the arrayColumns to set
	 */
	public void setArrayColumns(String[] arrayColumns) {
		this.arrayColumns = arrayColumns;
	}

	/**
	 * @return the arrayColumnNames
	 */
	public String[] getArrayColumnNames() {
		return arrayColumnNames;
	}

	/**
	 * @param arrayColumnNames the arrayColumnNames to set
	 */
	public void setArrayColumnNames(String[] arrayColumnNames) {
		this.arrayColumnNames = arrayColumnNames;
	}
}
