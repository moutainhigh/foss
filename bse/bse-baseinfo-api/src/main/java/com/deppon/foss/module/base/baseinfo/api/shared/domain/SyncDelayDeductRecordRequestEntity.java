package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;
/**
 * 
 * 同步延时扣款查询列表请求参数
 * @author 308861 
 * @date 2016-10-31 下午2:45:32
 * @since
 * @version
 */
public class SyncDelayDeductRecordRequestEntity{
	/**
	 * 申请部门编码
	 */
	private String applyOrgCode;
	/**
	 * 审核部门编码
	 */
	private String checkOrgCode;
	/**
	 * 申请开始时间
	 */
	private Date applyStartTime;
	/**
	 * 申请结束时间
	 */
	private Date  applyEndTime;
	
	/**
	 * 申请开始时间long
	 */
	private Long applyStartTimeLong;
	/**
	 * 申请结束时间long
	 */
	private Long  applyEndTimeLong;
	
	/**
	 * 审核状态
	 */
	private String checkStatus;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 分页--开始
	 */
	private  int start;
	/**
	 * 分页--每页条数
	 */
	private int limit;
	
	public String getApplyOrgCode() {
		return applyOrgCode;
	}
	public void setApplyOrgCode(String applyOrgCode) {
		this.applyOrgCode = applyOrgCode;
	}
	public String getCheckOrgCode() {
		return checkOrgCode;
	}
	public void setCheckOrgCode(String checkOrgCode) {
		this.checkOrgCode = checkOrgCode;
	}
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
	public Long getApplyStartTimeLong() {
		return applyStartTimeLong;
	}
	public void setApplyStartTimeLong(Long applyStartTimeLong) {
		this.applyStartTimeLong = applyStartTimeLong;
	}
	public Long getApplyEndTimeLong() {
		return applyEndTimeLong;
	}
	public void setApplyEndTimeLong(Long applyEndTimeLong) {
		this.applyEndTimeLong = applyEndTimeLong;
	}
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
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
