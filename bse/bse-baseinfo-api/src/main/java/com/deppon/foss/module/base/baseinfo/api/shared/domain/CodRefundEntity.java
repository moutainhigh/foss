package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * (代收货款打包退款CodRefund的实体类)
 * @author 187862-dujunhui
 * @date 2014-7-15 下午6:50:52
 * @since
 * @version v1.0
 */
public class CodRefundEntity extends BaseEntity {

	private static final long serialVersionUID = -575837681062590687L;
	
	//部门名称
	private String deptName;
	//部门编码
	private String deptCode;
	//用户名称
	private String customerName;
	//用户编码
	private String customerCode;
	//操作人名称
	private String operatorName;
	//操作人编码
	private String operatorCode;
	//期限
	private String timeLimit;
	//期限开始时间
	private Date timeLimitStart;
	//期限结束时间
	private Date timeLimitEnd;
	//录入时间
	private Date enteringTime;
	//录入时间开始
	private Date enteringTimeStart;
	//录入时间结束
	private Date enteringTimeEnd;
	//附件
	private String additional;
	//备注
	private String remark;
	//是否有效
	private String active;
	//版本号
	private Long version;
	/**
	 * @return  the deptName
	 */
	public String getDeptName() {
		return deptName;
	}
	/**
	 * @param deptName the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	/**
	 * @return  the deptCode
	 */
	public String getDeptCode() {
		return deptCode;
	}
	/**
	 * @param deptCode the deptCode to set
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
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
	 * @return  the operatorName
	 */
	public String getOperatorName() {
		return operatorName;
	}
	/**
	 * @param operatorName the operatorName to set
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	/**
	 * @return  the operatorCode
	 */
	public String getOperatorCode() {
		return operatorCode;
	}
	/**
	 * @param operatorCode the operatorCode to set
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	/**
	 * @return  the timeLimit
	 */
	public String getTimeLimit() {
		return timeLimit;
	}
	/**
	 * @param timeLimit the timeLimit to set
	 */
	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}
	/**
	 * @return  the enteringTimeStart
	 */
	public Date getEnteringTimeStart() {
		return enteringTimeStart;
	}
	/**
	 * @param enteringTimeStart the enteringTimeStart to set
	 */
	public void setEnteringTimeStart(Date enteringTimeStart) {
		this.enteringTimeStart = enteringTimeStart;
	}
	/**
	 * @return  the enteringTimeEnd
	 */
	public Date getEnteringTimeEnd() {
		return enteringTimeEnd;
	}
	/**
	 * @param enteringTimeEnd the enteringTimeEnd to set
	 */
	public void setEnteringTimeEnd(Date enteringTimeEnd) {
		this.enteringTimeEnd = enteringTimeEnd;
	}
	/**
	 * @return  the timeLimitStart
	 */
	public Date getTimeLimitStart() {
		return timeLimitStart;
	}
	/**
	 * @param timeLimitStart the timeLimitStart to set
	 */
	public void setTimeLimitStart(Date timeLimitStart) {
		this.timeLimitStart = timeLimitStart;
	}
	/**
	 * @return  the timeLimitEnd
	 */
	public Date getTimeLimitEnd() {
		return timeLimitEnd;
	}
	/**
	 * @param timeLimitEnd the timeLimitEnd to set
	 */
	public void setTimeLimitEnd(Date timeLimitEnd) {
		this.timeLimitEnd = timeLimitEnd;
	}
	/**
	 * @return  the enteringTime
	 */
	public Date getEnteringTime() {
		return enteringTime;
	}
	/**
	 * @param enteringTime the enteringTime to set
	 */
	public void setEnteringTime(Date enteringTime) {
		this.enteringTime = enteringTime;
	}
	/**
	 * @return  the additional
	 */
	public String getAdditional() {
		return additional;
	}
	/**
	 * @param additional the additional to set
	 */
	public void setAdditional(String additional) {
		this.additional = additional;
	}
	/**
	 * @return  the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return  the active
	 */
	public String getActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}
	/**
	 * @return  the version
	 */
	public Long getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(Long version) {
		this.version = version;
	}
	
	
	
	

}
