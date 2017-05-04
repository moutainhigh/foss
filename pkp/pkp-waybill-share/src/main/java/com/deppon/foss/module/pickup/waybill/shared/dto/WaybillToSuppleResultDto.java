package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.Date;
/**
 * 作废运单记录
 * @author Foss-105888-Zhangxingwang
 * @date 2014-8-11 10:12:13
 */
public class WaybillToSuppleResultDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String waybillSuppleId;
	/**
	 * 新运单号
	 */
	private String newWaybillNo;
	
	/**
	 * 旧运单号
	 */
	private String oldWaybillNo;
	
	/**
	 * 订单号
	 */
	private String orderNo;
	
	/**
	 * 开单部门
	 */
	private String createOrgName;
	
	/**
	 * 收货部门
	 */
	private String receiveOrgName;
	
	/**
	 * 开单业务时间
	 */
	private Date billTime;
	
	/**
	 * 作废原因
	 */
	private String invalidReason;
	
	/**
	 * 作废人
	 */
	private String invalidor;
	
	/**
	 * 作废人所在组织
	 */
	private String invalidOrgName;
	
	/**
	 * 作废时间
	 */
	private Date invalidTime;
	
	/**
	 * 工作流号
	 */
	private String workflowNo;
	
	/**
	 * 作废类型
	 */
	private String invalidType;
	
	public String getInvalidType() {
		return invalidType;
	}
	
	public void setInvalidType(String invalidType) {
		this.invalidType = invalidType;
	}
	
	public String getWorkflowNo() {
		return workflowNo;
	}
	
	public void setWorkflowNo(String workflowNo) {
		this.workflowNo = workflowNo;
	}
	
	public String getWaybillSuppleId() {
		return waybillSuppleId;
	}
	
	public void setWaybillSuppleId(String waybillSuppleId) {
		this.waybillSuppleId = waybillSuppleId;
	}
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public String getReceiveOrgName() {
		return receiveOrgName;
	}

	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}

	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	public String getInvalidReason() {
		return invalidReason;
	}

	public void setInvalidReason(String invalidReason) {
		this.invalidReason = invalidReason;
	}

	public String getInvalidor() {
		return invalidor;
	}

	public void setInvalidor(String invalidor) {
		this.invalidor = invalidor;
	}

	public String getInvalidOrgName() {
		return invalidOrgName;
	}

	public void setInvalidOrgName(String invalidOrgName) {
		this.invalidOrgName = invalidOrgName;
	}

	public Date getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
	}

	public String getNewWaybillNo() {
		return newWaybillNo;
	}

	public void setNewWaybillNo(String newWaybillNo) {
		this.newWaybillNo = newWaybillNo;
	}

	public String getOldWaybillNo() {
		return oldWaybillNo;
	}

	public void setOldWaybillNo(String oldWaybillNo) {
		this.oldWaybillNo = oldWaybillNo;
	}
}