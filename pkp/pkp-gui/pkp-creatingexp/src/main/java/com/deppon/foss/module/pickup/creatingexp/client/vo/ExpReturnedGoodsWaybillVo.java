package com.deppon.foss.module.pickup.creatingexp.client.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;

public class ExpReturnedGoodsWaybillVo implements Serializable {

	private static final long serialVersionUID = 1L;

	// 返单号
	private String returnWaybillNo;
	// 原单号
	private String waybillNo;
	// 受理状态
	private DataDictionaryValueVo acceptanceStatus;
	// 返货类型
	private DataDictionaryValueVo returnGoodsType;
	// 任务部门
	private DataDictionaryValueVo taskDepartment;
	// 创建开始时间
	private Date createStartTime;
	// 创建结束时间
	private Date createEndTime;
	// 工单号
	private String workOrder;
	// 代收货款
	private BigDecimal goodsPayment;
	// 收货地址
	private String address;
	// 创建人工号
	private String creatorCode;
	// 创建人姓名
	private String creatorName;
	
	private String isHandle;
	
	public String getIsHandle() {
		return isHandle;
	}

	public void setIsHandle(String isHandle) {
		this.isHandle = isHandle;
	}

	public String getCreatorCode() {
		return creatorCode;
	}

	public void setCreatorCode(String creatorCode) {
		this.creatorCode = creatorCode;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	// 创建时间
	private Date createTime;
	// 受理时间
	private Date handleTime;
	// 受理人工号
	private String handlerCode;
	// 受理人姓名
	private String handlerName;
	//返货方式
	private DataDictionaryValueVo returnMode;
	//返货原因(我司原因、客户原因)
	private String returnReason;
	
	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public DataDictionaryValueVo getReturnMode() {
		return returnMode;
	}

	public void setReturnMode(DataDictionaryValueVo returnMode) {
		this.returnMode = returnMode;
	}

	public String getHandlerCode() {
		return handlerCode;
	}

	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}

	public String getHandlerName() {
		return handlerName;
	}

	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	public String getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(String workOrder) {
		this.workOrder = workOrder;
	}

	public BigDecimal getGoodsPayment() {
		return goodsPayment;
	}

	public void setGoodsPayment(BigDecimal goodsPayment) {
		this.goodsPayment = goodsPayment;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}

	public String getReturnWaybillNo() {
		return returnWaybillNo;
	}

	public void setReturnWaybillNo(String returnWaybillNo) {
		this.returnWaybillNo = returnWaybillNo;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public DataDictionaryValueVo getAcceptanceStatus() {
		return acceptanceStatus;
	}

	public void setAcceptanceStatus(DataDictionaryValueVo acceptanceStatus) {
		this.acceptanceStatus = acceptanceStatus;
	}

	public DataDictionaryValueVo getReturnGoodsType() {
		return returnGoodsType;
	}

	public void setReturnGoodsType(DataDictionaryValueVo returnGoodsType) {
		this.returnGoodsType = returnGoodsType;
	}

	public Date getCreateStartTime() {
		return createStartTime;
	}

	public void setCreateStartTime(Date createStartTime) {
		this.createStartTime = createStartTime;
	}

	public Date getCreateEndTime() {
		return createEndTime;
	}

	public void setCreateEndTime(Date createEndTime) {
		this.createEndTime = createEndTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public DataDictionaryValueVo getTaskDepartment() {
		return taskDepartment;
	}

	public void setTaskDepartment(DataDictionaryValueVo taskDepartment) {
		this.taskDepartment = taskDepartment;
	}
	
	//返货方式-string类型
	private String returnModeString;
	public String getReturnModeString() {
		return returnModeString;
	}

	public void setReturnModeString(String returnModeString) {
		this.returnModeString = returnModeString;
	}
}
