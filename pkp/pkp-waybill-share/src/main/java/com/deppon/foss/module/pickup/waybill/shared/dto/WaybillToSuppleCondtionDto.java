package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * 作废待补录运单条件
 * @author Foss-105888-Zhangxingwang
 * @date 2014-8-11 09:48:45
 */
public class WaybillToSuppleCondtionDto implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 订单号
	 */
	private String orderNo;
	
	/**
	 * PDA开单时间-开始
	 */
	private Date beginBillTime;
	
	/**
	 * PDA开单时间-结束
	 */
	private Date endBillTime;
	
	/**
	 * 作废日期-开始
	 */
	private Date beginInvalidTime;
	
	/**
	 * 作废日期-结束
	 */
	private Date endInvalidTime;
	
	/**
	 * 作废类型
	 */
	private String invalidType;
	
	/**
	 * 工作流号
	 */
	private String workflowNo;
	
	/**
	 * 作废人所在组织
	 */
	private String invalidOrgCode;
	
	/**
	 * 流水号集合
	 */
	private List<String> serialNoList;
	
	/**
	 * 当前登陆部门
	 */
	private String currentDeptCode;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getBeginBillTime() {
		return beginBillTime;
	}

	public void setBeginBillTime(Date beginBillTime) {
		this.beginBillTime = beginBillTime;
	}

	public Date getEndBillTime() {
		return endBillTime;
	}

	public void setEndBillTime(Date endBillTime) {
		this.endBillTime = endBillTime;
	}

	public Date getBeginInvalidTime() {
		return beginInvalidTime;
	}

	public void setBeginInvalidTime(Date beginInvalidTime) {
		this.beginInvalidTime = beginInvalidTime;
	}

	public Date getEndInvalidTime() {
		return endInvalidTime;
	}

	public void setEndInvalidTime(Date endInvalidTime) {
		this.endInvalidTime = endInvalidTime;
	}

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

	public String getInvalidOrgCode() {
		return invalidOrgCode;
	}

	public void setInvalidOrgCode(String invalidOrgCode) {
		this.invalidOrgCode = invalidOrgCode;
	}

	public List<String> getSerialNoList() {
		return serialNoList;
	}

	public void setSerialNoList(List<String> serialNoList) {
		this.serialNoList = serialNoList;
	}

	public String getCurrentDeptCode() {
		return currentDeptCode;
	}

	public void setCurrentDeptCode(String currentDeptCode) {
		this.currentDeptCode = currentDeptCode;
	}
}