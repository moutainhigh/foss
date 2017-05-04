package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;
import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
 * 延时扣款申请审核
 * @author 308861 
 * @date 2016-10-29 上午10:05:13
 * @since
 * @version
 */
public class DelayDeductRecordEntity extends BaseEntity {
	/** 
	*serialVersionUID
	*/ 
	private static final long serialVersionUID = -2888590166521842517L;
	/**
	 * 表ID
	 */
	private String id;
	/**
	 * 申请部门名称
	 */
	private String applyOrgName;
	/**
	 * 申请部门编码
	 */
	private String applyOrgCode;
	/**
	 * 申请人名称/创建人名称
	 */
	private String applicantName;
	/**
	 * 申请人编码/创建人编码
	 */
	private String applicantCode;
	/**
	 * 审核部门名称
	 */
	private String checkOrgName;
	/**
	 * 审核部门编码
	 */
	private String checkOrgCode;
	/**
	 * 审核人名称/修改人名称
	 */
	private String checkPersonName;
	/**
	 * 审核人编码/修改人编码
	 */
	private String checkPersonCode;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 审核状态,'0':'待审核','1':'已审核','2':'已拒绝','3':'已失效'
	 */
	private String checkStatus;
	/**
	 * 扣款状态 WHF：扣款失败，WHS：扣款成功，UWH：未扣款
	 */
	private String flowStatus;
	/**
	 * 审核意见
	 */
	private String checkOpinion;
	/**
	 * 申请扣款时间
	 */
	private Date applyDeductTime;
	/**
	 * 申请时间/创建时间
	 */
	private Date applyTime;
	/**
	 * 审核时间/修改时间
	 */
	private Date checkTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getApplyOrgName() {
		return applyOrgName;
	}
	public void setApplyOrgName(String applyOrgName) {
		this.applyOrgName = applyOrgName;
	}
	public String getApplyOrgCode() {
		return applyOrgCode;
	}
	public void setApplyOrgCode(String applyOrgCode) {
		this.applyOrgCode = applyOrgCode;
	}
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public String getApplicantCode() {
		return applicantCode;
	}
	public void setApplicantCode(String applicantCode) {
		this.applicantCode = applicantCode;
	}
	public String getCheckOrgName() {
		return checkOrgName;
	}
	public void setCheckOrgName(String checkOrgName) {
		this.checkOrgName = checkOrgName;
	}
	public String getCheckOrgCode() {
		return checkOrgCode;
	}
	public void setCheckOrgCode(String checkOrgCode) {
		this.checkOrgCode = checkOrgCode;
	}
	public String getCheckPersonName() {
		return checkPersonName;
	}
	public void setCheckPersonName(String checkPersonName) {
		this.checkPersonName = checkPersonName;
	}
	public String getCheckPersonCode() {
		return checkPersonCode;
	}
	public void setCheckPersonCode(String checkPersonCode) {
		this.checkPersonCode = checkPersonCode;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	public String getFlowStatus() {
		return flowStatus;
	}
	public void setFlowStatus(String flowStatus) {
		this.flowStatus = flowStatus;
	}
	public String getCheckOpinion() {
		return checkOpinion;
	}
	public void setCheckOpinion(String checkOpinion) {
		this.checkOpinion = checkOpinion;
	}
	public Date getApplyDeductTime() {
		return applyDeductTime;
	}
	public void setApplyDeductTime(Date applyDeductTime) {
		this.applyDeductTime = applyDeductTime;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	
	public DelayDeductRecordEntity() {
		super();
	}
	public DelayDeductRecordEntity(String id, String applyOrgName,
			String applyOrgCode, String applicantName, String applicantCode,
			String checkOrgName, String checkOrgCode, String checkPersonName,
			String checkPersonCode, String waybillNo, String checkStatus,
			String flowStatus, String checkOpinion, Date applyDeductTime,
			Date applyTime, Date checkTime) {
		super();
		this.id = id;
		this.applyOrgName = applyOrgName;
		this.applyOrgCode = applyOrgCode;
		this.applicantName = applicantName;
		this.applicantCode = applicantCode;
		this.checkOrgName = checkOrgName;
		this.checkOrgCode = checkOrgCode;
		this.checkPersonName = checkPersonName;
		this.checkPersonCode = checkPersonCode;
		this.waybillNo = waybillNo;
		this.checkStatus = checkStatus;
		this.flowStatus = flowStatus;
		this.checkOpinion = checkOpinion;
		this.applyDeductTime = applyDeductTime;
		this.applyTime = applyTime;
		this.checkTime = checkTime;
	}
	@Override
	public String toString() {
		return "DelayDeductRecordEntity [id=" + id + ", applyOrgName="
				+ applyOrgName + ", applyOrgCode=" + applyOrgCode
				+ ", applicantName=" + applicantName + ", applicantCode="
				+ applicantCode + ", checkOrgName=" + checkOrgName
				+ ", checkOrgCode=" + checkOrgCode + ", checkPersonName="
				+ checkPersonName + ", checkPersonCode=" + checkPersonCode
				+ ", waybillNo=" + waybillNo + ", checkStatus=" + checkStatus
				+ ", flowStatus=" + flowStatus + ", checkOpinion="
				+ checkOpinion + ", applyDeductTime=" + applyDeductTime
				+ ", applyTime=" + applyTime + ", checkTime=" + checkTime + "]";
	}
	
}
