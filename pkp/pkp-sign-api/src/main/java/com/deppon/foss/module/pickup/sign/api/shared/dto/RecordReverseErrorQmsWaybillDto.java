/**
 * 快递反签收差错上报dto
 */
package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;

/**
 * @clasaName:com.deppon.foss.module.pickup.sign.api.shared.dto.RecordReverseErrorQmsWaybillDto
 * @author: foss-bieyexiong
 * @description: foss记录反签收差错 上报QMS
 * @date:2015年4月7日 下午16:11:26
 */
public class RecordReverseErrorQmsWaybillDto implements Serializable{
	
	/**
	 * 类的序列号
	 */
	private static final long serialVersionUID = -4192201682138526134L;

	/**
	 * 收货人电话
	 */
	private String receiverPhone;

	/**
	 * 快递差错-发货时间(开单时间)
	 */
	private String deliveryTime;
	
	/**
	 * 零担差错-发货时间(开单时间)
	 */
	private String sendGoodsTime;
	
	/**
	 * 到达部门(提货网点)
	 */
	private String arriveDeptCode;
	
	/**
	 * 到达部门名字
	 */
	private String arriveDeptName;
	
	/**
	 * 快递差错-收货人
	 */
	private String consignee;
	
	/**
	 * 零担差错-收货人
	 */
	private String receiverName;
	
	/**
	 * 开单部门(开单组织)
	 */
	private String billingDeptCode;
	
	/**
	 * 开单部门名字
	 */
	private String billingDeptName;

	/**
	 * 事件经过
	 */
	private String incident;
	
	/**
	 * 责任部门
	 */
	private String respDeptCode;
	
	/**
	 * 责任部门名字
	 */
	private String respDeptName;
	
	/**
	 * 责任人
	 */
	private String respEmpCode;
	
	/**
	 * 责任人姓名
	 */
	private String respEmpName;
	
	/**
	 * 操作员工号(起草反签收的人)
	 */
	private String operationsStaffNo;
	
	/**
	 * 操作员姓名
	 */
	private String operationsStaffName;
	
	/**
	 * 职位
	 */
	private String operationsStaffJobs;
	
	/**
	 * 短信通知对象(责任人和责任人上级)
	 */
	private String shortMessageCodes;
	
	/**
	 * 短信通知对象名称
	 */
	private String shortMessageNames;

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getArriveDeptCode() {
		return arriveDeptCode;
	}

	public void setArriveDeptCode(String arriveDeptCode) {
		this.arriveDeptCode = arriveDeptCode;
	}

	public String getArriveDeptName() {
		return arriveDeptName;
	}

	public void setArriveDeptName(String arriveDeptName) {
		this.arriveDeptName = arriveDeptName;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getBillingDeptCode() {
		return billingDeptCode;
	}

	public void setBillingDeptCode(String billingDeptCode) {
		this.billingDeptCode = billingDeptCode;
	}

	public String getBillingDeptName() {
		return billingDeptName;
	}

	public void setBillingDeptName(String billingDeptName) {
		this.billingDeptName = billingDeptName;
	}

	public String getIncident() {
		return incident;
	}

	public void setIncident(String incident) {
		this.incident = incident;
	}
	
	public String getRespDeptCode() {
		return respDeptCode;
	}

	public void setRespDeptCode(String respDeptCode) {
		this.respDeptCode = respDeptCode;
	}

	public String getRespDeptName() {
		return respDeptName;
	}

	public void setRespDeptName(String respDeptName) {
		this.respDeptName = respDeptName;
	}

	public String getOperationsStaffNo() {
		return operationsStaffNo;
	}

	public void setOperationsStaffNo(String operationsStaffNo) {
		this.operationsStaffNo = operationsStaffNo;
	}

	public String getOperationsStaffName() {
		return operationsStaffName;
	}

	public void setOperationsStaffName(String operationsStaffName) {
		this.operationsStaffName = operationsStaffName;
	}

	public String getOperationsStaffJobs() {
		return operationsStaffJobs;
	}

	public void setOperationsStaffJobs(String operationsStaffJobs) {
		this.operationsStaffJobs = operationsStaffJobs;
	}

	public String getRespEmpCode() {
		return respEmpCode;
	}

	public void setRespEmpCode(String respEmpCode) {
		this.respEmpCode = respEmpCode;
	}

	public String getRespEmpName() {
		return respEmpName;
	}

	public void setRespEmpName(String respEmpName) {
		this.respEmpName = respEmpName;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getShortMessageCodes() {
		return shortMessageCodes;
	}

	public void setShortMessageCodes(String shortMessageCodes) {
		this.shortMessageCodes = shortMessageCodes;
	}

	public String getShortMessageNames() {
		return shortMessageNames;
	}

	public void setShortMessageNames(String shortMessageNames) {
		this.shortMessageNames = shortMessageNames;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getSendGoodsTime() {
		return sendGoodsTime;
	}

	public void setSendGoodsTime(String sendGoodsTime) {
		this.sendGoodsTime = sendGoodsTime;
	}
	
}
