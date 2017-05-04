package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.util.Date;

/*
 * FOSS将运单号传给ECS日志实体
 * 2016年7月23日 08:07:12 葛亮亮
 */
public class PushFoss2EcsWaybillNoLogEntity {
	
	//id
	private String id;
	
	//运单id
	private String waybillID;
	
	//运单号
	private String waybillNo;
	
	//运单创建时间
	private Date billCreateTime;
	
	//传送的内容
	private String sendMsg;
	
	//是否成功标识 Y:正常，N:异常
	private String successFlag; 
	
	//创建时间
	private Date createTime;
	
	//返回内容
	private String responeMsg;
	
	//响应时间
	private long responseTime;
	
	//异常信息
	private String exceptionInfo;
	
	//系统调度关系
	private String sysRelation;
	
	//操作人
	private String operator;
	
	//预留字段1
	private String column1;
	
	//预留字段2
	private String column2;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWaybillID() {
		return waybillID;
	}

	public void setWaybillID(String waybillID) {
		this.waybillID = waybillID;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getSendMsg() {
		return sendMsg;
	}

	public void setSendMsg(String sendMsg) {
		this.sendMsg = sendMsg;
	}

	public String getSuccessFlag() {
		return successFlag;
	}

	public void setSuccessFlag(String successFlag) {
		this.successFlag = successFlag;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getBillCreateTime() {
		return billCreateTime;
	}

	public void setBillCreateTime(Date billCreateTime) {
		this.billCreateTime = billCreateTime;
	}

	public String getResponeMsg() {
		return responeMsg;
	}

	public void setResponeMsg(String responeMsg) {
		this.responeMsg = responeMsg;
	}

	public long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}

	public String getExceptionInfo() {
		return exceptionInfo;
	}

	public void setExceptionInfo(String exceptionInfo) {
		this.exceptionInfo = exceptionInfo;
	}

	public String getSysRelation() {
		return sysRelation;
	}

	public void setSysRelation(String sysRelation) {
		this.sysRelation = sysRelation;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getColumn1() {
		return column1;
	}

	public void setColumn1(String column1) {
		this.column1 = column1;
	}

	public String getColumn2() {
		return column2;
	}

	public void setColumn2(String column2) {
		this.column2 = column2;
	}

	
}
