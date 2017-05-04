package com.deppon.foss.module.transfer.partialline.api.shared.dto;

import java.util.Date;
import java.util.List;

/**
 * @author niuly
 * @function 打印代理面单Dto
 * @date 2014年9月5日上午10:59:52
 */
public class PrintAgentWaybillDto {

	//交接单号
	private List<String> handOverBillList;
	//运单号
	private List<String> waybillNoList;
	//代理单号
	//private List<String> agentWaybillNoList;
	//交接开始时间
	private Date beginHandOverTime;
	//交接结束时间
	private Date endHandOverTime;
	//入库开始时间
	private Date beginInStockTime;
	//入库结束时间
	private Date endInStockTime;
	//外场code
	private String deptCode;
	//运单号
	private String waybillNo;
	//快递代理单号
	private String agentWaybillNo;
	//绑定状态
	private String bundleState;
	//绑定开始时间
	private Date beginOperatTime;
	//绑定结束时间
	private Date endOperatTime;
	//代理单号
	private List<String> agentWaybillNoList;
	//代理公司
	private List<String> agentCompanyList;
	//外发费
	private List<String> frightFeeList;
	//当前部门编号
	private String currentDeptCode;
	
	public List<String> getHandOverBillList() {
		return handOverBillList;
	}
	public void setHandOverBillList(List<String> handOverBillList) {
		this.handOverBillList = handOverBillList;
	}
	public List<String> getWaybillNoList() {
		return waybillNoList;
	}
	public void setWaybillNoList(List<String> waybillNoList) {
		this.waybillNoList = waybillNoList;
	}
//	public List<String> getAgentWaybillNoList() {
//		return agentWaybillNoList;
//	}
//	public void setAgentWaybillNoList(List<String> agentWaybillNoList) {
//		this.agentWaybillNoList = agentWaybillNoList;
//	}
	public Date getBeginHandOverTime() {
		return beginHandOverTime;
	}
	public void setBeginHandOverTime(Date beginHandOverTime) {
		this.beginHandOverTime = beginHandOverTime;
	}
	public Date getEndHandOverTime() {
		return endHandOverTime;
	}
	public void setEndHandOverTime(Date endHandOverTime) {
		this.endHandOverTime = endHandOverTime;
	}
	public Date getBeginInStockTime() {
		return beginInStockTime;
	}
	public void setBeginInStockTime(Date beginInStockTime) {
		this.beginInStockTime = beginInStockTime;
	}
	public Date getEndInStockTime() {
		return endInStockTime;
	}
	public void setEndInStockTime(Date endInStockTime) {
		this.endInStockTime = endInStockTime;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getAgentWaybillNo() {
		return agentWaybillNo;
	}
	public void setAgentWaybillNo(String agentWaybillNo) {
		this.agentWaybillNo = agentWaybillNo;
	}
	public String getBundleState() {
		return bundleState;
	}
	public void setBundleState(String bundleState) {
		this.bundleState = bundleState;
	}
	public Date getBeginOperatTime() {
		return beginOperatTime;
	}
	public void setBeginOperatTime(Date beginOperatTime) {
		this.beginOperatTime = beginOperatTime;
	}
	public Date getEndOperatTime() {
		return endOperatTime;
	}
	public void setEndOperatTime(Date endOperatTime) {
		this.endOperatTime = endOperatTime;
	}
	public List<String> getAgentWaybillNoList() {
		return agentWaybillNoList;
	}
	public void setAgentWaybillNoList(List<String> agentWaybillNoList) {
		this.agentWaybillNoList = agentWaybillNoList;
	}
	public List<String> getAgentCompanyList() {
		return agentCompanyList;
	}
	public void setAgentCompanyList(List<String> agentCompanyList) {
		this.agentCompanyList = agentCompanyList;
	}
	public List<String> getFrightFeeList() {
		return frightFeeList;
	}
	public void setFrightFeeList(List<String> frightFeeList) {
		this.frightFeeList = frightFeeList;
	}
	public String getCurrentDeptCode() {
		return currentDeptCode;
	}
	public void setCurrentDeptCode(String currentDeptCode) {
		this.currentDeptCode = currentDeptCode;
	}
	
}
