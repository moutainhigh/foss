package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.util.Date;


/**
 * 
 * 延迟扣款申请审核查询Dto
 * @author 308861 
 * @date 2016-10-29 上午10:09:52
 * @since
 * @version
 */
public class DelayDeductRecordQueryDto {
	/**
	 * 申请开始时间
	 */
	private Date applyStartTime;
	/**
	 * 申请截止时间
	 */
	private Date applyEndTime;
	/**
	 * 申请人编码/创建人编码
	 */
	private String applicantCode;
	/**
	 * 审核人编码/修改人编码
	 */
	private String checkPersonCode;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 审核部门编码
	 */
	private String checkOrgCode;
	/**
	 * 申请部门编码
	 */
	private String applyOrgCode;
	/**
	 * 当前登录人工号
	 */
	private String empCode;
	/**
	 * 审核状态'0':'待审核','1':'已审核','2':'已拒绝','3':'已失效'
	 */
	private String checkStatus;
	/**
	 * 分页--开始
	 */
	private  int start;
	/**
	 * 分页--每页条数
	 */
	private int limit;
	public Date getApplyStartTime() {
		return applyStartTime;
	}
	public void setApplyStartTime(Date applyStartTime) {
		this.applyStartTime = applyStartTime;
	}
	public Date getApplyEndTime() {
		return applyEndTime;
	}
	public void setApplyEndTime(Date applyEndTime) {
		this.applyEndTime = applyEndTime;
	}
	public String getApplicantCode() {
		return applicantCode;
	}
	public void setApplicantCode(String applicantCode) {
		this.applicantCode = applicantCode;
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
	public String getCheckOrgCode() {
		return checkOrgCode;
	}
	public void setCheckOrgCode(String checkOrgCode) {
		this.checkOrgCode = checkOrgCode;
	}
	public String getApplyOrgCode() {
		return applyOrgCode;
	}
	public void setApplyOrgCode(String applyOrgCode) {
		this.applyOrgCode = applyOrgCode;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
}
