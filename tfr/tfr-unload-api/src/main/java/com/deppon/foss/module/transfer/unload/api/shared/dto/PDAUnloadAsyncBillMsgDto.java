package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
/**
 * 卸车异步入库信息表
 */
public class PDAUnloadAsyncBillMsgDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**id*/
	private String id;
	/**任务编号*/
	private String taskNo;
	/**交接单or配载单*/
	private String billNo;
	/**运单号*/
	private String wayBillNo;
	/**流水号*/
	private String serialNo;
	/**类型*/
	private String inStockType;
	/**是否包扫描**/
	private String  bePackage;
	/**部门*/
	private String  orgCode;
	/**操作人*/
	private String  operatorCode;
	/**操作人姓名*/
	private String  operatorName;
	/**jobId 默认值：N/A */
	private String  jobId;
	
	/**状态*/
	private String  asyncstatus;
	/**创建时间*/
	private Date createTime;
	/**重量*/
	private double weight;
	/**体积*/
	private double volume;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTaskNo() {
		return taskNo;
	}
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getWayBillNo() {
		return wayBillNo;
	}
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getInStockType() {
		return inStockType;
	}
	public void setInStockType(String inStockType) {
		this.inStockType = inStockType;
	}
	public String getBePackage() {
		return bePackage;
	}
	public void setBePackage(String bePackage) {
		this.bePackage = bePackage;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOperatorCode() {
		return operatorCode;
	}
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getAsyncstatus() {
		return asyncstatus;
	}
	public void setAsyncstatus(String asyncstatus) {
		this.asyncstatus = asyncstatus;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	
}
