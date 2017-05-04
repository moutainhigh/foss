package com.deppon.foss.module.transfer.partialline.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class AgentWaybillTrackEntity extends BaseEntity{

	private static final long serialVersionUID = 1L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 操作部门编码
	 */
	private String operateDeptCode;
	/**
	 * 操作部门名称
	 */
	private String operateDeptName;
	/**
	 * 操作城市编码
	 */
	private String operateCityCode;
	/**
	 * 操作城市名称
	 */
	private String operateCityName;
	/**
	 * 操作类型
	 */
	private String operateType;
	/**
	 * 操作内容
	 */
	private String operateContent;
	/**
	 * 操作时间
	 */
	private Date operateTime;
	/**
	 * 操作人名称
	 */
	private String operatorName;
	/**
	 * 派送员名称
	 */
	private String deliverName;
	/**
	 * 派送员电话
	 */
	private String deliverPhone;
	/**
	 * 签收人名称
	 */
	private String signerName;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 是否已推送
	 */
	private String isSEnd;
	
	
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getOperateDeptCode() {
		return operateDeptCode;
	}
	public void setOperateDeptCode(String operateDeptCode) {
		this.operateDeptCode = operateDeptCode;
	}
	public String getOperateDeptName() {
		return operateDeptName;
	}
	public void setOperateDeptName(String operateDeptName) {
		this.operateDeptName = operateDeptName;
	}
	public String getOperateCityCode() {
		return operateCityCode;
	}
	public void setOperateCityCode(String operateCityCode) {
		this.operateCityCode = operateCityCode;
	}
	public String getOperateCityName() {
		return operateCityName;
	}
	public void setOperateCityName(String operateCityName) {
		this.operateCityName = operateCityName;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public String getOperateContent() {
		return operateContent;
	}
	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getDeliverName() {
		return deliverName;
	}
	public void setDeliverName(String deliverName) {
		this.deliverName = deliverName;
	}
	public String getDeliverPhone() {
		return deliverPhone;
	}
	public void setDeliverPhone(String deliverPhone) {
		this.deliverPhone = deliverPhone;
	}
	public String getSignerName() {
		return signerName;
	}
	public void setSignerName(String signerName) {
		this.signerName = signerName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIsSEnd() {
		return isSEnd;
	}
	public void setIsSEnd(String isSEnd) {
		this.isSEnd = isSEnd;
	}
	
}
