package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:托盘任务查询条件
 * @author 105869-foss-heyongdong
 * @date 2013-07-25 
 */
public class QueryTrayScanConditionDto implements Serializable {

	private static final long serialVersionUID = 392010737728911225L;
	//绑定任务部门
	private String bindingDept;
	//绑定任务的人
	private String bindingName;
	//绑定任务的人的编号
	private String bindingCode;
	//绑定任务的编号
	private String trayBindingTaskNo;
	//叉车部门
	private String forkLiftDept;
	//叉车司机姓名
	private String forkLiftDriverName;
	//叉车司机编号
	private String forkLiftDriverCode;
	//运单号
	private String billNo;
	//叉车扫描起始时间
	private Date beginTrayScanTime;
	//叉车扫描结束时间
	private Date endTrayScanTime;
	//托盘创建开始时间
	private Date beginTrayCreateTime;
	//托盘创建结束时间
	private Date endTrayCreateTime;
	//当前部门
	private String currentDept;
	//状态
	private String trayScanStatus;
	
	//当前用户的部门编号
	private String optionOrgCode;
	
	private int currentPageNo;
	
	private int pageSize;
	
	
	
	public Date getBeginTrayCreateTime() {
		return beginTrayCreateTime;
	}
	public void setBeginTrayCreateTime(Date beginTrayCreateTime) {
		this.beginTrayCreateTime = beginTrayCreateTime;
	}
	public Date getEndTrayCreateTime() {
		return endTrayCreateTime;
	}
	public void setEndTrayCreateTime(Date endTrayCreateTime) {
		this.endTrayCreateTime = endTrayCreateTime;
	}
	public String getTrayScanStatus() {
		return trayScanStatus;
	}
	public void setTrayScanStatus(String trayScanStatus) {
		this.trayScanStatus = trayScanStatus;
	}
	public String getBindingDept() {
		return bindingDept;
	}
	public void setBindingDept(String bindingDept) {
		this.bindingDept = bindingDept;
	}
	public String getBindingName() {
		return bindingName;
	}
	public void setBindingName(String bindingName) {
		this.bindingName = bindingName;
	}
	public String getForkLiftDept() {
		return forkLiftDept;
	}
	public void setForkLiftDept(String forkLiftDept) {
		this.forkLiftDept = forkLiftDept;
	}
	public String getForkLiftDriverName() {
		return forkLiftDriverName;
	}
	public void setForkLiftDriverName(String forkLiftDriverName) {
		this.forkLiftDriverName = forkLiftDriverName;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public Date getBeginTrayScanTime() {
		return beginTrayScanTime;
	}
	public void setBeginTrayScanTime(Date beginTrayScanTime) {
		this.beginTrayScanTime = beginTrayScanTime;
	}
	public Date getEndTrayScanTime() {
		return endTrayScanTime;
	}
	public void setEndTrayScanTime(Date endTrayScanTime) {
		this.endTrayScanTime = endTrayScanTime;
	}
	public String getCurrentDept() {
		return currentDept;
	}
	public void setCurrentDept(String currentDept) {
		this.currentDept = currentDept;
	}
	public String getOptionOrgCode() {
		return optionOrgCode;
	}
	public void setOptionOrgCode(String optionOrgCode) {
		this.optionOrgCode = optionOrgCode;
	}
	public int getCurrentPageNo() {
		return currentPageNo;
	}
	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getTrayBindingTaskNo() {
		return trayBindingTaskNo;
	}
	public void setTrayBindingTaskNo(String trayBindingTaskNo) {
		this.trayBindingTaskNo = trayBindingTaskNo;
	}
	public String getForkLiftDriverCode() {
		return forkLiftDriverCode;
	}
	public void setForkLiftDriverCode(String forkLiftDriverCode) {
		this.forkLiftDriverCode = forkLiftDriverCode;
	}
	public String getBindingCode() {
		return bindingCode;
	}
	public void setBindingCode(String bindingCode) {
		this.bindingCode = bindingCode;
	}
	@Override
	public String toString() {
		return "QueryTrayScanConditionDto [bindingDept=" + bindingDept
				+ ", bindingName=" + bindingName + ", forkLiftDept="
				+ forkLiftDept + ", forkLiftDriverName=" + forkLiftDriverName
				+ ", billNo=" + billNo + ", beginTrayScanTime="
				+ beginTrayScanTime + ", endTrayScanTime=" + endTrayScanTime
				+ ", beginTrayCreateTime=" + beginTrayCreateTime
				+ ", endTrayCreateTime=" + endTrayCreateTime + ", currentDept="
				+ currentDept + ", trayScanStatus=" + trayScanStatus + "]";
	}
	
	
}
