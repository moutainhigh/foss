package com.deppon.foss.module.pickup.order.api.shared.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.order.api.shared.domain.CourierReportEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.ExpressWorkerCompleteDto;

public class ExpressWorerStatusConditionVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * 收派大区查询条件
	 * */
	private List<String> bigRegionCode;
	/*
	 * 收派小区查询条件
	 * */
	private List<String> smallRegionCode;
	/*
	 *公共选择器中快递员的工号
	 * 
	 */
	private String expressWorkerCode;
	/*
	 * 快递员工号查询条件
	 * */
	private List<String> queryEmployeeCodes;
	/*
	 * 查询实时状态结果返回值
	 * */
	private List<ExpressWorkerCompleteDto> ExpressWorkerStatusTatal;
	
	/*
	 * 查询实时状态结果返回值
	 * */
	private List<CourierReportEntity> courierReportList;
	
	/*
	 * 开始时间
	 * */
	private Date startDate;
	/*
	 * 结束时间
	 * */
	private Date endDate;
	/*
	 * 选择人员工号 
	 * */
	private List<String> changeStatusEmpCodes;
	private List<String> operateBigRegionCode;//业务收派大区 9.9
	private String recieveOrderStatus; //14.10.8接收状态
	
	
	public String getRecieveOrderStatus() {
		return recieveOrderStatus;
	}
	public void setRecieveOrderStatus(String recieveOrderStatus) {
		this.recieveOrderStatus = recieveOrderStatus;
	}
	public List<String> getOperateBigRegionCode() {
		return operateBigRegionCode;
	}
	public void setOperateBigRegionCode(List<String> operateBigRegionCode) {
		this.operateBigRegionCode = operateBigRegionCode;
	}
	public List<String> getChangeStatusEmpCodes() {
		return changeStatusEmpCodes;
	}
	public void setChangeStatusEmpCodes(List<String> changeStatusEmpCodes) {
		this.changeStatusEmpCodes = changeStatusEmpCodes;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public List<String> getBigRegionCode() {
		return bigRegionCode;
	}
	public void setBigRegionCode(List<String> bigRegionCode) {
		this.bigRegionCode = bigRegionCode;
	}
	public List<String> getSmallRegionCode() {
		return smallRegionCode;
	}
	public void setSmallRegionCode(List<String> smallRegionCode) {
		this.smallRegionCode = smallRegionCode;
	}	
	
    
	public String getExpressWorkerCode() {
		return expressWorkerCode;
	}
	public void setExpressWorkerCode(String expressWorkerCode) {
		this.expressWorkerCode = expressWorkerCode;
	}
	public List<String> getQueryEmployeeCodes() {
		return queryEmployeeCodes;
	}
	public void setQueryEmployeeCodes(List<String> queryEmployeeCodes) {
		this.queryEmployeeCodes = queryEmployeeCodes;
	}
	public List<ExpressWorkerCompleteDto> getExpressWorkerStatusTatal() {
		return ExpressWorkerStatusTatal;
	}
	public void setExpressWorkerStatusTatal(
			List<ExpressWorkerCompleteDto> expressWorkerStatusTatal) {
		ExpressWorkerStatusTatal = expressWorkerStatusTatal;
	}
	public List<CourierReportEntity> getCourierReportList() {
		return courierReportList;
	}
	public void setCourierReportList(List<CourierReportEntity> courierReportList) {
		this.courierReportList = courierReportList;
	}
	
}
