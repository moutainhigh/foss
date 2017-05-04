package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;


public class UnloadbindTrayQueryConditionDto implements Serializable{

	private static final long serialVersionUID = 4035577588006216124L;
	
	/**
	 * 卸车任务号
	 * */
	 private String unloadTaskNo=null;
	
	/**
	 * 卸车任务绑定托盘创建人 
	 * */
	 private String createrCode=null;
	
	/**
	 * 卸车任务中的车牌
	 * */
	private String  vehicleNo=null;
	
	/**
	 * 创建任务开始时间
	 * */
	
	private String createStartDate=null;
	/**
	 * 创建任务结束时间
	 * */
	
   private String createEndDate=null;
	
   /**
	 * 外场编码
	 * */
   private String outfiledCode=null;
   
   /**
	 * 当前用户的部门编号
	 * */
   private String optionOrgCode=null;

   private int currentPageNo;
   
   private int pageSize;
   
   
	public String getUnloadTaskNo() {
		return unloadTaskNo;
	}
	public void setUnloadTaskNo(String unloadTaskNo) {
		this.unloadTaskNo = unloadTaskNo;
	}
	public String getCreaterCode() {
		return createrCode;
	}
	public void setCreaterCode(String createrCode) {
		this.createrCode = createrCode;
	}
	
	
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getCreateStartDate() {
		return createStartDate;
	}
	public void setCreateStartDate(String createStartDate) {
		this.createStartDate = createStartDate;
	}
	public String getCreateEndDate() {
		return createEndDate;
	}
	public void setCreateEndDate(String createEndDate) {
		this.createEndDate = createEndDate;
	}
	public String getOutfiledCode() {
		return outfiledCode;
	}
	public void setOutfiledCode(String outfiledCode) {
		this.outfiledCode = outfiledCode;
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
	public String getOptionOrgCode() {
		return optionOrgCode;
	}
	public void setOptionOrgCode(String optionOrgCode) {
		this.optionOrgCode = optionOrgCode;
	}
	@Override
	public String toString() {
		return "UnloadbindTrayQueryConditionDto [unloadTaskNo=" + unloadTaskNo
				+ ", createrCode=" + createrCode + ", vehicleNo=" + vehicleNo
				+ ", createStartDate=" + createStartDate + ", createEndDate="
				+ createEndDate + ", outfiledCode=" + outfiledCode
				+ ", optionOrgCode=" + optionOrgCode + ", currentPageNo="
				+ currentPageNo + ", pageSize=" + pageSize + "]";
	}
	
	
	
	
	
	
	
	
	
}
