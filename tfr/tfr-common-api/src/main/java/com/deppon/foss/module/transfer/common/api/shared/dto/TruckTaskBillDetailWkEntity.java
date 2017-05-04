package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class TruckTaskBillDetailWkEntity implements Serializable{
	
	/**
	* @fields serialVersionUID
	* @author 283250-foss-liuyi
	* @update 2016年4月27日 上午10:08:44
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 1L;

	private String id;// ID

	private String vehicleTaskDetailNo;// 车辆任务明细编号

	private String handoverBillNo;// 交接单号

	private String billLevel;// 单据级别

	private String assignmentStatus;// 分配状态

	private Date createTime;// 创建时间

	/**
	 * 部门名称
	 */
	private String operationOrgName;     
	/**
	 * 出发部门(操作部门操作人部门名称（顶级外场）)
	 */
	private String operationOrgCode;
	

	public String getOperationOrgName() {
		return operationOrgName;
	}

	public void setOperationOrgName(String operationOrgName) {
		this.operationOrgName = operationOrgName;
	}

	public String getOperationOrgCode() {
		return operationOrgCode;
	}

	public void setOperationOrgCode(String operationOrgCode) {
		this.operationOrgCode = operationOrgCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVehicleTaskDetailNo() {
		return vehicleTaskDetailNo;
	}

	public void setVehicleTaskDetailNo(String vehicleTaskDetailNo) {
		this.vehicleTaskDetailNo = vehicleTaskDetailNo;
	}

	public String getHandoverBillNo() {
		return handoverBillNo;
	}

	public void setHandoverBillNo(String handoverBillNo) {
		this.handoverBillNo = handoverBillNo;
	}

	public String getBillLevel() {
		return billLevel;
	}

	public void setBillLevel(String billLevel) {
		this.billLevel = billLevel;
	}

	public String getAssignmentStatus() {
		return assignmentStatus;
	}

	public void setAssignmentStatus(String assignmentStatus) {
		this.assignmentStatus = assignmentStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
