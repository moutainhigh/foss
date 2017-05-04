package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.foss.module.transfer.load.api.shared.define.OutsideVehicleChargeConstants;

/**
 * 外请车付款报表参数Dto
 * 
 * @author foss-zhangxiaohui
 * @date Dec 20, 2012 5:39:33 PM
 */
public class OfvPaymentReportQueryDto implements Serializable {

	/**
	 * 外请车付款报表参数Dto序列号
	 */
	private static final long serialVersionUID = 3755520385072805721L;

	/**
	 * 确认已签收常量
	 */
	@SuppressWarnings("unused")
	private static final String OFV_PAYMENT_REPORT_YES = FossConstants.YES;

	/**
	 * 确认未签收常量
	 */
	@SuppressWarnings("unused")
	private static final String OFV_PAYMENT_REPORT_NO = FossConstants.NO;

	/**
	 * 外请车首款
	 */
	@SuppressWarnings("unused")
	private static final String PAYABLE_TYPE_F = SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK1_FIRST;

	/**
	 * 外请车尾款
	 */
	@SuppressWarnings("unused")
	private static final String PAYABLE_TYPE_L = SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK1_LAST;

	/**
	 * 整车首款
	 */
	@SuppressWarnings("unused")
	private static final String PAYABLE_TYPE_TF = SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_FIRST;

	/**
	 * 整车尾款
	 */
	@SuppressWarnings("unused")
	private static final String PAYABLE_TYPE_TL = SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST;

	/**
	 * 是否签回单：是
	 */
	@SuppressWarnings("unused")
	private static final String BE_RETURN_RECEIPT_Y = FossConstants.YES;

	/**
	 * 是否签回单：是
	 */
	@SuppressWarnings("unused")
	private static final String BE_RETURN_RECEIPT_N = FossConstants.NO;

	/**
	 * 调整费用：  奖励
	 */
	@SuppressWarnings("unused")
	private static final String ADJUST_AWARD_TYPE_FINE = OutsideVehicleChargeConstants.FINE;

	/**
	 * 调整费用：  费用减少
	 */
	@SuppressWarnings("unused")
	private static final String ADJUST_TYPE_FEE_REDUCE = OutsideVehicleChargeConstants.ADJUST_TYPE_FEE_REDUCE;

	/**
	 * 调整费用：  已审核
	 */
	@SuppressWarnings("unused")
	private static final String ADJUST_AUDIT_STATUS_PASS = OutsideVehicleChargeConstants.AUDITPASS;

	/**
	 * 车辆归属：公司车 
	 */
	@SuppressWarnings("unused")
	private static final String ASSETS_OWNERSHIP_TYPE_COMPANY = ComnConst.ASSETS_OWNERSHIP_TYPE_COMPANY;

	/**
	 * 车辆归属：外请车
	 */
	@SuppressWarnings("unused")
	private static final String ASSETS_OWNERSHIP_TYPE_LEASED = ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED;

	/**
	 * 付款类型（开始、到达、全部）
	 */
	private String payableType;

	/**
	 * 单据类型集合（首款集合、尾款集合）
	 */
	private List<String> payableTypeList;

	/**
	 * 审核状态（付款单）
	 */
	private String auditStatus;

	/**
	 * 付款确认状态（付款单）
	 */
	private String confirmStatus;

	/**
	 * 付款日期（起）
	 */
	private Date startPaymentDate;

	/**
	 * 付款日期（止）
	 */
	private Date endPaymentDate;

	/**
	 * 当前登录用户员工编码
	 */
	private String empCode;

	/**
	 * 工作流号
	 */
	private String workFlowNo;

	/**
	 * 工作流号数据
	 */
	private String[] workFlowNosArray;

	/**
	 * 工作流号集合
	 */
	private List<String> workFlowNos;

	/**
	 * 车次号数组
	 */
	private String[] vchAbillNosArray;

	/**
	 * 车次号集合
	 */
	private List<String> vchAbillNos;
	
	/**
	 * 合同编码--340403
	 */
	private String contractCode;
	
	/**
	 * 合同编码集合--340403
	 */
	private List<String> contractCodesNos;
	
	/**
	 * 合同编码数组--340403
	 */
	private String[] contractCodesNosArray;
	/**
	 * 出发部门
	 */
	private String origDept;

	/**
	 * 到达部门
	 */
	private String destDept;
	
	/**
	 * 增减类型常亮
	 */
	@SuppressWarnings("unused")
	private static final String ADJUST_TYPE_INCREASE = "increase";
	@SuppressWarnings("unused")
	private static final String ADJUST_TYPE_DECREASE = "decrease";
	public String getOrigDept() {
		return origDept;
	}

	public void setOrigDept(String origDept) {
		this.origDept = origDept;
	}

	public String getDestDept() {
		return destDept;
	}

	public void setDestDept(String destDept) {
		this.destDept = destDept;
	}

	/**
	 * @get
	 * @return workFlowNosArray
	 */
	public String[] getWorkFlowNosArray() {
		/*
		 * @get
		 * @return workFlowNosArray
		 */
		return workFlowNosArray;
	}

	/**
	 * @set
	 * @param workFlowNosArray
	 */
	public void setWorkFlowNosArray(String[] workFlowNosArray) {
		/*
		 *@set
		 *@this.workFlowNosArray = workFlowNosArray
		 */
		this.workFlowNosArray = workFlowNosArray;
	}

	/**
	 * @get
	 * @return workFlowNos
	 */
	public List<String> getWorkFlowNos() {
		/*
		 * @get
		 * @return workFlowNos
		 */
		return workFlowNos;
	}

	/**
	 * @set
	 * @param workFlowNos
	 */
	public void setWorkFlowNos(List<String> workFlowNos) {
		/*
		 *@set
		 *@this.workFlowNos = workFlowNos
		 */
		this.workFlowNos = workFlowNos;
	}

	/**
	 * @get
	 * @return vchAbillNosArray
	 */
	public String[] getVchAbillNosArray() {
		/*
		 * @get
		 * @return vchAbillNosArray
		 */
		return vchAbillNosArray;
	}

	/**
	 * @set
	 * @param vchAbillNosArray
	 */
	public void setVchAbillNosArray(String[] vchAbillNosArray) {
		/*
		 *@set
		 *@this.vchAbillNosArray = vchAbillNosArray
		 */
		this.vchAbillNosArray = vchAbillNosArray;
	}

	/**
	 * @get
	 * @return vchAbillNos
	 */
	public List<String> getVchAbillNos() {
		/*
		 * @get
		 * @return vchAbillNos
		 */
		return vchAbillNos;
	}

	/**
	 * @set
	 * @param vchAbillNos
	 */
	public void setVchAbillNos(List<String> vchAbillNos) {
		/*
		 *@set
		 *@this.vchAbillNos = vchAbillNos
		 */
		this.vchAbillNos = vchAbillNos;
	}

	/**
	 * @get
	 * @return workFlowNo
	 */
	public String getWorkFlowNo() {
		/*
		 * @get
		 * @return workFlowNo
		 */
		return workFlowNo;
	}

	/**
	 * @set
	 * @param workFlowNo
	 */
	public void setWorkFlowNo(String workFlowNo) {
		/*
		 *@set
		 *@this.workFlowNo = workFlowNo
		 */
		this.workFlowNo = workFlowNo;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	/**
	 * @return paymentType
	 */
	public String getPayableType() {
		return payableType;
	}

	/**
	 * @param paymentType
	 */
	public void setPayableType(String payableType) {
		this.payableType = payableType;
	}

	/**
	 * @return auditStatus
	 */
	public String getAuditStatus() {
		return auditStatus;
	}

	/**
	 * @param auditStatus
	 */
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	/**
	 * @return confirmStatus
	 */
	public String getConfirmStatus() {
		return confirmStatus;
	}

	/**
	 * @param confirmStatus
	 */
	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	/**
	 * @return startPaymentDate
	 */
	public Date getStartPaymentDate() {
		return startPaymentDate;
	}

	/**
	 * @param startPaymentDate
	 */
	public void setStartPaymentDate(Date startPaymentDate) {
		this.startPaymentDate = startPaymentDate;
	}

	/**
	 * @return endPaymentDate
	 */
	public Date getEndPaymentDate() {
		return endPaymentDate;
	}

	/**
	 * @param endPaymentDate
	 */
	public void setEndPaymentDate(Date endPaymentDate) {
		this.endPaymentDate = endPaymentDate;
	}

	/**
	 * @get
	 * @return payableTypeList
	 */
	public List<String> getPayableTypeList() {
		/*
		 * @get
		 * @return payableTypeList
		 */
		return payableTypeList;
	}

	/**
	 * @set
	 * @param payableTypeList
	 */
	public void setPayableTypeList(List<String> payableTypeList) {
		/*
		 *@set
		 *@this.payableTypeList = payableTypeList
		 */
		this.payableTypeList = payableTypeList;
	}

	public List<String> getContractCodesNos() {
		return contractCodesNos;
	}

	public void setContractCodesNos(List<String> contractCodesNos) {
		this.contractCodesNos = contractCodesNos;
	}

	public String[] getContractCodesNosArray() {
		return contractCodesNosArray;
	}

	public void setContractCodesNosArray(String[] contractCodesNosArray) {
		this.contractCodesNosArray = contractCodesNosArray;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	

}
