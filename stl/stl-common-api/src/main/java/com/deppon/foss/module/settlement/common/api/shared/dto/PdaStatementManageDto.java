package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.settlement.common.api.shared.domain.PdaStatementManageEntity;


/**
 * 对账单管理DTO
 * 
 * @ClassName: PdaStatementManageDto
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2016-1-18 下午4:27:51
 */
public class PdaStatementManageDto {
	/**
	 * FOSS返回数据到POS实体类
	 */
	private List<PdaStatementManageEntity> statementEntitys;

	/********* 还款需要封装的参数 *********/
	/* 还款数据 */
	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 对账单编号数组
	 */
	private String[] statementBillNos;

	/**
	 * 对账单版本号数组
	 * 对账单单号和版本号一一对应
	 */
	private String[] versionNos;

	/**
	 * 还款方式:字段值为"CD"
	 */
	private String repaymentType;

	/**
	 * 汇款编号：银联的交易流水号
	 */
	private String remittanceNumber;

	/**
	 * 还款金额：刷卡金额
	 */
	private BigDecimal repaymentAmount;

	/**
	 * 汇款人
	 */
	private String remittanceName;

	/**
	 * 还款单备注：可为空
	 */
	private String repaymentNotes;
	
	/**
	 * 还款描述:可为空
	 */
	private String description;
	
	/**
	 * 备注
	 */
	private String notes;
	
	/**
	 * 这里要封装FossUserCurrentInfo :必须
	 */
	/**
	 * 员工编码
	 */
	private String empCode;
	
	/**
	 * 员工名称
	 */
	private String empName;
	
	/**
	 * 部门名称
	 */
	private String currentDeptName;
	
	/**
	 * 部门编码
	 */
	private String currentDeptCode;
	
	/**
	 * 是否成功 "Y"成功   "N"失败
	 */
	private String isSuccess;
	
	/**
	 * 	失败 的对账单 List
	 * 
	 */
	private List<String> statementNos;
	
	/**
	 * 失败的信息Map以及成功后因有保管费未能结清的信息
	 * 
	 */
	private Map<String,String> msgMap;
	
	
	/****** getter/setter ********/
	public List<PdaStatementManageEntity> getStatementEntitys() {
		return statementEntitys;
	}

	public void setStatementEntitys(
			List<PdaStatementManageEntity> statementEntitys) {
		this.statementEntitys = statementEntitys;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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

	public String[] getStatementBillNos() {
		return statementBillNos;
	}

	public void setStatementBillNos(String[] statementBillNos) {
		this.statementBillNos = statementBillNos;
	}

	public String[] getVersionNos() {
		return versionNos;
	}

	public void setVersionNos(String[] versionNos) {
		this.versionNos = versionNos;
	}

	public String getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}

	public String getRemittanceNumber() {
		return remittanceNumber;
	}

	public void setRemittanceNumber(String remittanceNumber) {
		this.remittanceNumber = remittanceNumber;
	}

	public BigDecimal getRepaymentAmount() {
		return repaymentAmount;
	}

	public void setRepaymentAmount(BigDecimal repaymentAmount) {
		this.repaymentAmount = repaymentAmount;
	}

	public String getRemittanceName() {
		return remittanceName;
	}

	public void setRemittanceName(String remittanceName) {
		this.remittanceName = remittanceName;
	}

	public String getRepaymentNotes() {
		return repaymentNotes;
	}

	public void setRepaymentNotes(String repaymentNotes) {
		this.repaymentNotes = repaymentNotes;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getCurrentDeptName() {
		return currentDeptName;
	}

	public void setCurrentDeptName(String currentDeptName) {
		this.currentDeptName = currentDeptName;
	}
	
	public String getCurrentDeptCode() {
		return currentDeptCode;
	}

	public void setCurrentDeptCode(String currentDeptCode) {
		this.currentDeptCode = currentDeptCode;
	}

	public List<String> getStatementNos() {
		return statementNos;
	}

	public void setStatementNos(List<String> statementNos) {
		this.statementNos = statementNos;
	}

	public Map<String, String> getMsgMap() {
		return msgMap;
	}

	public void setMsgMap(Map<String, String> msgMap) {
		this.msgMap = msgMap;
	}

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
	
}
