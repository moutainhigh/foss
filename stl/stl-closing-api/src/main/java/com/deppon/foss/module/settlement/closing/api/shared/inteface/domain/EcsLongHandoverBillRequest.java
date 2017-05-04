package com.deppon.foss.module.settlement.closing.api.shared.inteface.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 快递交接单请求实体
 * @author foss-231434-bieyexiong 
 * @date 2016-4-20 16:28
 */
public class EcsLongHandoverBillRequest implements Serializable{

	private static final long serialVersionUID = 1L;

	//交接员工号
    private String empCode;
    
    //交接员姓名
    private String empName;
    
    //交接员部门编码
    private String currentDeptCode;
    
    //交接员部门名称
    private String currentDeptName;
    
    //交接单号
    private String vehicleAssembleNo;
    
    //车辆所有权类别 ，如果是公司车不允许调用此接口
    private String vehicleOwnerShip;
    
    //车牌号
    private String vehicleNo;
	
    //预付运费总额
    private BigDecimal prePaidFeeTotal;
    
    //付款方式
    private String paymentType;
    
    //出发部门编码
    private String origOrgCode;
    
    //出发部门名称
    private String origOrgName;
    
    //车辆出发日期
    private Date leaveTime;
    
    //总运费
    private BigDecimal feeTotal;
    
    //司机编码
    private String driverCode;
    
    //司机姓名
    private String driverName;
    
    //到达部门编码
    private String destOrgCode;
    
    //到达部门名称
    private String destOrgName;
    
    //币种
    private String currencyCode;
    
    //是否押回单（否）
    private String beReturnReceipt;
    
    //到付运费总额
    private BigDecimal arriveFeeTotal;
    
    //配载类型（专线）
    private String assembleType;

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getCurrentDeptCode() {
		return currentDeptCode;
	}

	public void setCurrentDeptCode(String currentDeptCode) {
		this.currentDeptCode = currentDeptCode;
	}

	public String getCurrentDeptName() {
		return currentDeptName;
	}

	public void setCurrentDeptName(String currentDeptName) {
		this.currentDeptName = currentDeptName;
	}

	public String getVehicleAssembleNo() {
		return vehicleAssembleNo;
	}

	public void setVehicleAssembleNo(String vehicleAssembleNo) {
		this.vehicleAssembleNo = vehicleAssembleNo;
	}

	public String getVehicleOwnerShip() {
		return vehicleOwnerShip;
	}

	public void setVehicleOwnerShip(String vehicleOwnerShip) {
		this.vehicleOwnerShip = vehicleOwnerShip;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public BigDecimal getPrePaidFeeTotal() {
		return prePaidFeeTotal;
	}

	public void setPrePaidFeeTotal(BigDecimal prePaidFeeTotal) {
		this.prePaidFeeTotal = prePaidFeeTotal;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getOrigOrgCode() {
		return origOrgCode;
	}

	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	public String getOrigOrgName() {
		return origOrgName;
	}

	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	public Date getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}

	public BigDecimal getFeeTotal() {
		return feeTotal;
	}

	public void setFeeTotal(BigDecimal feeTotal) {
		this.feeTotal = feeTotal;
	}

	public String getDriverCode() {
		return driverCode;
	}

	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDestOrgCode() {
		return destOrgCode;
	}

	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	public String getDestOrgName() {
		return destOrgName;
	}

	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getBeReturnReceipt() {
		return beReturnReceipt;
	}

	public void setBeReturnReceipt(String beReturnReceipt) {
		this.beReturnReceipt = beReturnReceipt;
	}

	public BigDecimal getArriveFeeTotal() {
		return arriveFeeTotal;
	}

	public void setArriveFeeTotal(BigDecimal arriveFeeTotal) {
		this.arriveFeeTotal = arriveFeeTotal;
	}

	public String getAssembleType() {
		return assembleType;
	}

	public void setAssembleType(String assembleType) {
		this.assembleType = assembleType;
	}
    
}
