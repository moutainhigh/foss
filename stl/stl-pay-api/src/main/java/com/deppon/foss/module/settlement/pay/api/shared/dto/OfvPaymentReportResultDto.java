package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 外请车付款报表dto
 * 
 * @author foss-zhangxiaohui
 * @date Dec 20, 2012 5:38:10 PM
 */
public class OfvPaymentReportResultDto implements Serializable {

	/**
	 * 外请车付款报表结果dto序列号
	 */
	private static final long serialVersionUID = 8425000791420345718L;

	/**
	 * 出发部门编码
	 */
	private String origOrgCode;

	/**
	 * 出发部门名称
	 */
	private String origOrgName;

	/**
	 * 到达部门编码
	 */
	private String destOrgCode;

	/**
	 * 到达部门名称
	 */
	private String destOrgName;

	/**
	 * 始发站编码
	 */
	private String origCityCode;

	/**
	 * 始发站名称
	 */
	private String origCityName;

	/**
	 * 到达站编码
	 */
	private String destCityCode;

	/**
	 * 到达站名称
	 */
	private String destCityName;

	/**
	 * 付款类型
	 */
	private String payableType;

	/**
	 * 车次号
	 */
	private String vehicleAssembleBillNo;

	/**
	 * 车牌号
	 */
	private String vehicleNo;

	/**
	 * 司机编码
	 */
	private String driverCode;

	/**
	 * 司机名称
	 */
	private String driverName;

	/**
	 * 配载日期
	 */
	private Date stowageDate;

	/**
	 * 总金额
	 */
	private BigDecimal feeTotal;

	/**
	 * 首款
	 */
	private BigDecimal prePaidFee;

	/**
	 * 尾款
	 */
	private BigDecimal arrivePaidFee;

	/**
	 * 调整费用
	 */
	private BigDecimal adjustPaidFee;

	/**
	 * 首款付款人
	 */
	private String prePaidPayer;

	/**
	 * 首款审核人
	 */
	private String prePaidAuditer;

	/**
	 * 尾款付款人
	 */
	private String arrivePaidPayer;

	/**
	 * 尾款审核人
	 */
	private String arrivePaidAuditer;

	/**
	 * 实际离港时间
	 */
	private Date actualDepartTime;

	/**
	 * 实际到港时间
	 */
	private Date actualArriveTime;

	/**
	 * 车辆所属类型
	 */
	private String vehicleOwnerShip;

	/**
	 * 是否押回单
	 */
	private String beReturnReceipt;

	/**
	 * 工作流号
	 */
	private String workFlowNo;

	/**
	 * 返回数据库总记录条数
	 */
	private int totalRecordsInDB;
	
	/**
	 * 总金额合计
	 */
	private BigDecimal totalFeeSum;
	
	/**
	 * 首款合计
	 */
	private BigDecimal prePaidFeeSum;
	
	/**
	 * 尾款合计
	 */
	private BigDecimal arrivePaidFeeSum;
	
	/**
	 * 调整费用合计
	 */
	private BigDecimal adjustPaidFeeSum;


	/**
	 * 车主姓名
	 */
	private String carOwnerName;
	
	/**
	 * 信息部名称
	 */
	private String informationName;
	
	
	/**
	 * @return the carOwnerName
	 */
	public String getCarOwnerName() {
		return carOwnerName;
	}

	/**
	 * @param carOwnerName the carOwnerName to set
	 */
	public void setCarOwnerName(String carOwnerName) {
		this.carOwnerName = carOwnerName;
	}

	/**
	 * @return the informationName
	 */
	public String getInformationName() {
		return informationName;
	}

	/**
	 * @param informationName the informationName to set
	 */
	public void setInformationName(String informationName) {
		this.informationName = informationName;
	}

	/**
	 * @get
	 * @return workFlowNo
	 */
	public String getWorkFlowNo() {
		/*
		 * @get
		 * 
		 * @return workFlowNo
		 */
		return workFlowNo;
	}

	/**
	 * @set
	 * @param workFlowNo
	 */
	public void setWorkFlowNo(String workFlowNo) {
		/*
		 * @set
		 * 
		 * @this.workFlowNo = workFlowNo
		 */
		this.workFlowNo = workFlowNo;
	}

	/**
	 * @return origOrgCode
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}

	/**
	 * @param origOrgCode
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	/**
	 * @return origOrgName
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}

	/**
	 * @param origOrgName
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	/**
	 * @return destOrgCode
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}

	/**
	 * @param destOrgCode
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	/**
	 * @return destOrgName
	 */
	public String getDestOrgName() {
		return destOrgName;
	}

	/**
	 * @param destOrgName
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	/**
	 * @return origCityCode
	 */
	public String getOrigCityCode() {
		return origCityCode;
	}

	/**
	 * @param origCityCode
	 */
	public void setOrigCityCode(String origCityCode) {
		this.origCityCode = origCityCode;
	}

	/**
	 * @return origCityName
	 */
	public String getOrigCityName() {
		return origCityName;
	}

	/**
	 * @param origCityName
	 */
	public void setOrigCityName(String origCityName) {
		this.origCityName = origCityName;
	}

	/**
	 * @return destCityCode
	 */
	public String getDestCityCode() {
		return destCityCode;
	}

	/**
	 * @param destCityCode
	 */
	public void setDestCityCode(String destCityCode) {
		this.destCityCode = destCityCode;
	}

	/**
	 * @return destCityName
	 */
	public String getDestCityName() {
		return destCityName;
	}

	/**
	 * @param destCityName
	 */
	public void setDestCityName(String destCityName) {
		this.destCityName = destCityName;
	}

	/**
	 * @return payableType
	 */
	public String getPayableType() {
		return payableType;
	}

	/**
	 * @param payableType
	 */
	public void setPayableType(String payableType) {
		this.payableType = payableType;
	}

	/**
	 * @return vehicleAssembleBillNo
	 */
	public String getVehicleAssembleBillNo() {
		return vehicleAssembleBillNo;
	}

	/**
	 * @param vehicleAssembleBillNo
	 */
	public void setVehicleAssembleBillNo(String vehicleAssembleBillNo) {
		this.vehicleAssembleBillNo = vehicleAssembleBillNo;
	}

	/**
	 * @return vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * @param vehicleNo
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * @return driverCode
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * @param driverCode
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	/**
	 * @return driverName
	 */
	public String getDriverName() {
		return driverName;
	}

	/**
	 * @param driverName
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * @return stowageDate
	 */
	public Date getStowageDate() {
		return stowageDate;
	}

	/**
	 * @param stowageDate
	 */
	public void setStowageDate(Date stowageDate) {
		this.stowageDate = stowageDate;
	}

	/**
	 * @return feeTotal
	 */
	public BigDecimal getFeeTotal() {
		return feeTotal;
	}

	/**
	 * @param feeTotal
	 */
	public void setFeeTotal(BigDecimal feeTotal) {
		this.feeTotal = feeTotal;
	}

	/**
	 * @return prePaidFee
	 */
	public BigDecimal getPrePaidFee() {
		return prePaidFee;
	}

	/**
	 * @param prePaidFee
	 */
	public void setPrePaidFee(BigDecimal prePaidFee) {
		this.prePaidFee = prePaidFee;
	}

	/**
	 * @return arrivePaidFee
	 */
	public BigDecimal getArrivePaidFee() {
		return arrivePaidFee;
	}

	/**
	 * @param arrivePaidFee
	 */
	public void setArrivePaidFee(BigDecimal arrivePaidFee) {
		this.arrivePaidFee = arrivePaidFee;
	}

	/**
	 * @return adjustPaidFee
	 */
	public BigDecimal getAdjustPaidFee() {
		return adjustPaidFee;
	}

	/**
	 * @param adjustPaidFee
	 */
	public void setAdjustPaidFee(BigDecimal adjustPaidFee) {
		this.adjustPaidFee = adjustPaidFee;
	}

	/**
	 * @return prePaidPayer
	 */
	public String getPrePaidPayer() {
		return prePaidPayer;
	}

	/**
	 * @param prePaidPayer
	 */
	public void setPrePaidPayer(String prePaidPayer) {
		this.prePaidPayer = prePaidPayer;
	}

	/**
	 * @return prePaidAuditer
	 */
	public String getPrePaidAuditer() {
		return prePaidAuditer;
	}

	/**
	 * @param prePaidAuditer
	 */
	public void setPrePaidAuditer(String prePaidAuditer) {
		this.prePaidAuditer = prePaidAuditer;
	}

	/**
	 * @return arrivePaidPayer
	 */
	public String getArrivePaidPayer() {
		return arrivePaidPayer;
	}

	/**
	 * @param arrivePaidPayer
	 */
	public void setArrivePaidPayer(String arrivePaidPayer) {
		this.arrivePaidPayer = arrivePaidPayer;
	}

	/**
	 * @return arrivePaidAuditer
	 */
	public String getArrivePaidAuditer() {
		return arrivePaidAuditer;
	}

	/**
	 * @param arrivePaidAuditer
	 */
	public void setArrivePaidAuditer(String arrivePaidAuditer) {
		this.arrivePaidAuditer = arrivePaidAuditer;
	}

	/**
	 * @return actualDepartTime
	 */
	public Date getActualDepartTime() {
		return actualDepartTime;
	}

	/**
	 * @param actualDepartTime
	 */
	public void setActualDepartTime(Date actualDepartTime) {
		this.actualDepartTime = actualDepartTime;
	}

	/**
	 * @return actualArriveTime
	 */
	public Date getActualArriveTime() {
		return actualArriveTime;
	}

	/**
	 * @param actualArriveTime
	 */
	public void setActualArriveTime(Date actualArriveTime) {
		this.actualArriveTime = actualArriveTime;
	}

	/**
	 * @return vehicleOwnerShip
	 */
	public String getVehicleOwnerShip() {
		return vehicleOwnerShip;
	}

	/**
	 * @param vehicleOwnerShip
	 */
	public void setVehicleOwnerShip(String vehicleOwnerShip) {
		this.vehicleOwnerShip = vehicleOwnerShip;
	}

	/**
	 * @return beReturnReceipt
	 */
	public String getBeReturnReceipt() {
		return beReturnReceipt;
	}

	/**
	 * @param beReturnReceipt
	 */
	public void setBeReturnReceipt(String beReturnReceipt) {
		this.beReturnReceipt = beReturnReceipt;
	}

	/**
	 * @return totalRecordsInDB
	 */
	public int getTotalRecordsInDB() {
		return totalRecordsInDB;
	}

	/**
	 * @param totalRecordsInDB
	 */
	public void setTotalRecordsInDB(int totalRecordsInDB) {
		this.totalRecordsInDB = totalRecordsInDB;
	}

	
	/**
	 * @get
	 * @return totalFeeSum
	 */
	public BigDecimal getTotalFeeSum() {
		/*
		 * @get
		 * @return totalFeeSum
		 */
		return totalFeeSum;
	}

	
	/**
	 * @set
	 * @param totalFeeSum
	 */
	public void setTotalFeeSum(BigDecimal totalFeeSum) {
		/*
		 *@set
		 *@this.totalFeeSum = totalFeeSum
		 */
		this.totalFeeSum = totalFeeSum;
	}

	
	/**
	 * @get
	 * @return prePaidFeeSum
	 */
	public BigDecimal getPrePaidFeeSum() {
		/*
		 * @get
		 * @return prePaidFeeSum
		 */
		return prePaidFeeSum;
	}

	
	/**
	 * @set
	 * @param prePaidFeeSum
	 */
	public void setPrePaidFeeSum(BigDecimal prePaidFeeSum) {
		/*
		 *@set
		 *@this.prePaidFeeSum = prePaidFeeSum
		 */
		this.prePaidFeeSum = prePaidFeeSum;
	}

	
	/**
	 * @get
	 * @return arrivePaidFeeSum
	 */
	public BigDecimal getArrivePaidFeeSum() {
		/*
		 * @get
		 * @return arrivePaidFeeSum
		 */
		return arrivePaidFeeSum;
	}

	
	/**
	 * @set
	 * @param arrivePaidFeeSum
	 */
	public void setArrivePaidFeeSum(BigDecimal arrivePaidFeeSum) {
		/*
		 *@set
		 *@this.arrivePaidFeeSum = arrivePaidFeeSum
		 */
		this.arrivePaidFeeSum = arrivePaidFeeSum;
	}

	
	/**
	 * @get
	 * @return adjustPaidFeeSum
	 */
	public BigDecimal getAdjustPaidFeeSum() {
		/*
		 * @get
		 * @return adjustPaidFeeSum
		 */
		return adjustPaidFeeSum;
	}

	
	/**
	 * @set
	 * @param adjustPaidFeeSum
	 */
	public void setAdjustPaidFeeSum(BigDecimal adjustPaidFeeSum) {
		/*
		 *@set
		 *@this.adjustPaidFeeSum = adjustPaidFeeSum
		 */
		this.adjustPaidFeeSum = adjustPaidFeeSum;
	}

}
