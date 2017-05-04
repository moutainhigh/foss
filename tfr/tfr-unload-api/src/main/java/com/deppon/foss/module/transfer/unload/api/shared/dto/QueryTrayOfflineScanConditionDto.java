package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

public class QueryTrayOfflineScanConditionDto implements Serializable{

	private static final long serialVersionUID = 4429688602704388160L;
	
	/**查询条件运单号*/
	private String waybillNo;
	
	/**叉车司机工号*/
	private String forkLiftDriverCode;
	
	/**叉车司机部门*/
	private String forkLiftDept;
	
	/**查询的起始时间*/
	private Date beginOfflineTrayScanTime;
	
	/**查询的结束时间*/
	private Date endOfflineTrayScanTime;
	
	/**叉车司机对应的外场*/
	private String outFieldCoade;
	
	/**查询托盘任务起始时间*/
	private Date beginTrayCtreateTime;
	/**查询托盘任务结束时间*/
	private Date endTrayCtreateTime;
	
	//当前用户的部门
	private String optionOrgCode;
	
	private int currentPageNo;
	
	private int pageSize;
	
	@DateFormat
	public Date getBeginTrayCtreateTime() {
		return beginTrayCtreateTime;
	}
	@DateFormat
	public void setBeginTrayCtreateTime(Date beginTrayCtreateTime) {
		this.beginTrayCtreateTime = beginTrayCtreateTime;
	}
	@DateFormat
	public Date getEndTrayCtreateTime() {
		return endTrayCtreateTime;
	}
	@DateFormat
	public void setEndTrayCtreateTime(Date endTrayCtreateTime) {
		this.endTrayCtreateTime = endTrayCtreateTime;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getForkLiftDriverCode() {
		return forkLiftDriverCode;
	}
	public void setForkLiftDriverCode(String forkLiftDriverCode) {
		this.forkLiftDriverCode = forkLiftDriverCode;
	}
	public String getForkLiftDept() {
		return forkLiftDept;
	}
	public void setForkLiftDept(String forkLiftDept) {
		this.forkLiftDept = forkLiftDept;
	}
	@DateFormat
	public Date getBeginOfflineTrayScanTime() {
		return beginOfflineTrayScanTime;
	}
	@DateFormat
	public void setBeginOfflineTrayScanTime(Date beginOfflineTrayScanTime) {
		this.beginOfflineTrayScanTime = beginOfflineTrayScanTime;
	}
	@DateFormat
	public Date getEndOfflineTrayScanTime() {
		return endOfflineTrayScanTime;
	}
	@DateFormat
	public void setEndOfflineTrayScanTime(Date endOfflineTrayScanTime) {
		this.endOfflineTrayScanTime = endOfflineTrayScanTime;
	}
	public String getOutFieldCoade() {
		return outFieldCoade;
	}
	public void setOutFieldCoade(String outFieldCoade) {
		this.outFieldCoade = outFieldCoade;
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
	@Override
	public String toString() {
		return "QueryTrayOfflineScanConditionDto [waybillNo=" + waybillNo
				+ ", forkLiftDriverCode=" + forkLiftDriverCode
				+ ", forkLiftDept=" + forkLiftDept
				+ ", beginOfflineTrayScanTime=" + beginOfflineTrayScanTime
				+ ", endOfflineTrayScanTime=" + endOfflineTrayScanTime
				+ ", outFieldCoade=" + outFieldCoade
				+ ", beginTrayCtreateTime=" + beginTrayCtreateTime
				+ ", endTrayCtreateTime=" + endTrayCtreateTime + "]";
	}
	
	

}
