package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;



/**
* @description 同步给快递系统车辆任务对象
* @version 1.0
* @author 283250-foss-liuyi
* @update 2016年4月26日 下午4:12:22
*/
public class TruckTaskInfoDto implements Serializable {
	
	
	
	private static final long serialVersionUID = 1L;

	/**
	 * 操作类型(1、发车确认；2、取消发车；3、到达确认；4、取消到达；5、分配卸车任务；6、提交卸车任务）
	 */
	private String operationType;// 操作类型

	private String truckTaskNo; // 车辆任务编号

	private String truckTaskType;// 车型

	private String vehicleNo;// 车牌号

	private String lineNo;// 路径编号

	private String businessType;// 车辆业务类型

	private String masterDriverCode;// 主驾司机编号

	private String masterDriverName;// 主驾司机姓名

	private String masterDriverPhone;// 主驾联系方式

	private String slaveDriverCode;// 副驾司机编号

	private String slaveDriverName;// 副驾司机姓名

	private String slaveDriverPhone;// 副驾联系方式

	private String truckStatus;// 车辆状态

	private String truckType;// 整车类型

	private Date createTime;// 创建时间

	private Date updateTime;// 更新时间
	
	//车辆任务明细基本信息
	private TruckTaskDetailWkEntity truckTaskDetail;
	
	//单据明细列表
	private List<TruckTaskBillDetailWkEntity> truckTaskBillDetailList;

	/**
	 * 出发部门
	 */
	private String operationOrgCode;
	
	private String operationOrgName;

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
	
	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getTruckTaskNo() {
		return truckTaskNo;
	}

	public void setTruckTaskNo(String truckTaskNo) {
		this.truckTaskNo = truckTaskNo;
	}

	public String getTruckTaskType() {
		return truckTaskType;
	}

	public void setTruckTaskType(String truckTaskType) {
		this.truckTaskType = truckTaskType;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getMasterDriverCode() {
		return masterDriverCode;
	}

	public void setMasterDriverCode(String masterDriverCode) {
		this.masterDriverCode = masterDriverCode;
	}

	public String getMasterDriverName() {
		return masterDriverName;
	}

	public void setMasterDriverName(String masterDriverName) {
		this.masterDriverName = masterDriverName;
	}

	public String getMasterDriverPhone() {
		return masterDriverPhone;
	}

	public void setMasterDriverPhone(String masterDriverPhone) {
		this.masterDriverPhone = masterDriverPhone;
	}

	public String getSlaveDriverCode() {
		return slaveDriverCode;
	}

	public void setSlaveDriverCode(String slaveDriverCode) {
		this.slaveDriverCode = slaveDriverCode;
	}

	public String getSlaveDriverName() {
		return slaveDriverName;
	}

	public void setSlaveDriverName(String slaveDriverName) {
		this.slaveDriverName = slaveDriverName;
	}

	public String getSlaveDriverPhone() {
		return slaveDriverPhone;
	}

	public void setSlaveDriverPhone(String slaveDriverPhone) {
		this.slaveDriverPhone = slaveDriverPhone;
	}

	public String getTruckStatus() {
		return truckStatus;
	}

	public void setTruckStatus(String truckStatus) {
		this.truckStatus = truckStatus;
	}

	

	public String getTruckType() {
		return truckType;
	}

	public void setTruckType(String truckType) {
		this.truckType = truckType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	

	public TruckTaskDetailWkEntity getTruckTaskDetail() {
		return truckTaskDetail;
	}

	public void setTruckTaskDetail(TruckTaskDetailWkEntity truckTaskDetail) {
		this.truckTaskDetail = truckTaskDetail;
	}

	public List<TruckTaskBillDetailWkEntity> getTruckTaskBillDetailList() {
		return truckTaskBillDetailList;
	}

	public void setTruckTaskBillDetailList(
			List<TruckTaskBillDetailWkEntity> truckTaskBillDetailList) {
		this.truckTaskBillDetailList = truckTaskBillDetailList;
	}

	
	
	
	
}
