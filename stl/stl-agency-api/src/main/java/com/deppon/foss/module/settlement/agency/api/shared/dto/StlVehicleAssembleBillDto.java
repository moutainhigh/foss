package com.deppon.foss.module.settlement.agency.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 汽运配载单接口参数Dto 
 * @author 099995-foss-wujiangtao
 * @date 2012-12-17 下午8:47:18
 * @since
 * @version
 */
public class StlVehicleAssembleBillDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -4961191527697798883L;

	/**
	 * 配载车次号
	 */
	private String vehicleAssembleNo;
	

	/**
	 * 运单号
	 */
	private String waybillNo;


	/**
	 * 配载类型(区分整车/非整车的专线外请车) 
	 */
	private String assembleType;

	/**
	 * 出发部门编码---
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
	 * 车牌号
	 */
	private String vehicleNo;

	/**
	 * 车辆所有权类别 ，如果是公司车不允许调用此接口
	 *  --调整费用可不传 
	 */
	private String vehicleOwnerShip;

	/**
	 * 司机姓名
	 */
	private String driverName;

	/**
	 * 司机编码
	 */
	private String driverCode;

	
	/**
	 * 司机手机号码 @author 340403
	 */
	private String driverPhone;



	/**
	 * 付款方式 ---请参考结算的付款方式
	 */
	private String paymentType;

	/**
	 * 总运费
	 */
	private BigDecimal feeTotal;

	/**
	 * 预付运费总额
	 */
	private BigDecimal prePaidFeeTotal;

	/**
	 * 到付运费总额
	 */
	private BigDecimal arriveFeeTotal;


	/**
	 * 是否押回单
	 */
	private String beReturnReceipt;

	/**
	 * 币种
	 */
	private String currencyCode;
	
	/**
	 * 车辆出发日期
	 */
	private Date leaveTime;
    //车辆所在的机构

    /**
     *外请车组织编码
     */
    private String orgCode;
    /**
     *外请车组织名称
     */
    private String orgName;
/***************************************调整费用部分******************************/
	/**
	 * 奖罚类型---在配载单调整费用需要
	 */
	private String awardType;

	/**
	 * 调整费用---在配载单调整费用需要(在原应付的基础上要么增加的费用或减少的费用信息)
	 */
	private BigDecimal adjustFee;

	/**
	 * 部门经理审核状态---在配载单调整费用需要
	 */
	private String auditState;
    /**
     * 车辆是否到达 Y 到达  N 未到达
     */
	private String isArrive;
	/**
	 * 车辆到达时间
	 */
	private Date arriveTime;
	
	
	
	public String getIsArrive() {
		return isArrive;
	}

	public void setIsArrive(String isArrive) {
		this.isArrive = isArrive;
	}
	public Date getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	/**
	 * @return  the vehicleAssembleNo
	 */
	public String getVehicleAssembleNo() {
		return vehicleAssembleNo;
	}
    
	
	
	/**
	 * @param vehicleAssembleNo the vehicleAssembleNo to set
	 */
	public void setVehicleAssembleNo(String vehicleAssembleNo) {
		this.vehicleAssembleNo = vehicleAssembleNo;
	}

	
	/**
	 * @return  the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	
	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	
	/**
	 * @return  the assembleType
	 */
	public String getAssembleType() {
		return assembleType;
	}

	
	/**
	 * @param assembleType the assembleType to set
	 */
	public void setAssembleType(String assembleType) {
		this.assembleType = assembleType;
	}

	
	/**
	 * @return  the origOrgCode
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}

	
	/**
	 * @param origOrgCode the origOrgCode to set
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	
	/**
	 * @return  the origOrgName
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}

	
	/**
	 * @param origOrgName the origOrgName to set
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	
	/**
	 * @return  the destOrgCode
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}

	
	/**
	 * @param destOrgCode the destOrgCode to set
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	
	/**
	 * @return  the destOrgName
	 */
	public String getDestOrgName() {
		return destOrgName;
	}

	
	/**
	 * @param destOrgName the destOrgName to set
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	
	/**
	 * @return  the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	
	/**
	 * @param vehicleNo the vehicleNo to set
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	
	/**
	 * @return  the vehicleOwnerShip
	 */
	public String getVehicleOwnerShip() {
		return vehicleOwnerShip;
	}

	
	/**
	 * @param vehicleOwnerShip the vehicleOwnerShip to set
	 */
	public void setVehicleOwnerShip(String vehicleOwnerShip) {
		this.vehicleOwnerShip = vehicleOwnerShip;
	}

	
	/**
	 * @return  the driverName
	 */
	public String getDriverName() {
		return driverName;
	}

	
	/**
	 * @param driverName the driverName to set
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	
	/**
	 * @return  the driverCode
	 */
	public String getDriverCode() {
		return driverCode;
	}

	
	/**
	 * @param driverCode the driverCode to set
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	
	/**
	 * @return  the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	
	/**
	 * @param paymentType the paymentType to set
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	
	/**
	 * @return  the feeTotal
	 */
	public BigDecimal getFeeTotal() {
		return feeTotal;
	}

	
	/**
	 * @param feeTotal the feeTotal to set
	 */
	public void setFeeTotal(BigDecimal feeTotal) {
		this.feeTotal = feeTotal;
	}

	
	/**
	 * @return  the prePaidFeeTotal
	 */
	public BigDecimal getPrePaidFeeTotal() {
		return prePaidFeeTotal;
	}

	
	/**
	 * @param prePaidFeeTotal the prePaidFeeTotal to set
	 */
	public void setPrePaidFeeTotal(BigDecimal prePaidFeeTotal) {
		this.prePaidFeeTotal = prePaidFeeTotal;
	}

	
	/**
	 * @return  the arriveFeeTotal
	 */
	public BigDecimal getArriveFeeTotal() {
		return arriveFeeTotal;
	}

	
	/**
	 * @param arriveFeeTotal the arriveFeeTotal to set
	 */
	public void setArriveFeeTotal(BigDecimal arriveFeeTotal) {
		this.arriveFeeTotal = arriveFeeTotal;
	}

	
	/**
	 * @return  the beReturnReceipt
	 */
	public String getBeReturnReceipt() {
		return beReturnReceipt;
	}

	
	/**
	 * @param beReturnReceipt the beReturnReceipt to set
	 */
	public void setBeReturnReceipt(String beReturnReceipt) {
		this.beReturnReceipt = beReturnReceipt;
	}

	
	/**
	 * @return  the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	
	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	
	/**
	 * @return  the leaveTime
	 */
	public Date getLeaveTime() {
		return leaveTime;
	}

	
	/**
	 * @param leaveTime the leaveTime to set
	 */
	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}

	
	/**
	 * @return  the awardType
	 */
	public String getAwardType() {
		return awardType;
	}

	
	/**
	 * @param awardType the awardType to set
	 */
	public void setAwardType(String awardType) {
		this.awardType = awardType;
	}

	
	/**
	 * @return  the adjustFee
	 */
	public BigDecimal getAdjustFee() {
		return adjustFee;
	}

	
	/**
	 * @param adjustFee the adjustFee to set
	 */
	public void setAdjustFee(BigDecimal adjustFee) {
		this.adjustFee = adjustFee;
	}

	
	/**
	 * @return  the auditState
	 */
	public String getAuditState() {
		return auditState;
	}

	
	/**
	 * @param auditState the auditState to set
	 */
	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }


	public String getDriverPhone() {
		return driverPhone;
	}

	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}
    

}
