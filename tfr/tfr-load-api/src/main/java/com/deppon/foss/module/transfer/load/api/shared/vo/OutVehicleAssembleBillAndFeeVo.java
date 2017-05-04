/**   
 * File Name：OutVehicleAssembleBillAndFeeVo.java   
 *   
 * Version:1.0
 * ：2013-4-19   
 * Copyright (c) 2013  Deppon All Rights Reserved. 2013    
 * @author: liangfuxiang liangfux@cn.ibm.com
 */

package com.deppon.foss.module.transfer.load.api.shared.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Class Description： 配 载单及调整金额信息VO
 * 
 * @author: liangfuxiang liangfux@cn.ibm.com
 * @version: 2013-4-19 下午1:14:08
 */

public class OutVehicleAssembleBillAndFeeVo implements Serializable {

	/**
	 * serialVersionUID:
	 * 
	 * @since Ver 1.0
	 */

	private static final long serialVersionUID = 932678337146724350L;

	/**
	 * 车牌号
	 */
	private String vehicleNo;

	/**
	 * 配载单车次号
	 */
	private String vehicleAssembleNo;

	/**
	 * 司机编号
	 */
	private String driverCode;
	/**
	 * 司机姓名
	 */
	private String driverName;

	/**
	 * 出发部门名称
	 */
	private String origOrgCode;
	/**
	 * 出发部门编号
	 */
	private String origOrgName;
	/**
	 * 到达部门编号
	 */
	private String destOrgCode;
	/**
	 * 到达部门编号
	 */
	private String destOrgName;

	/**
	 * 总运费(元)
	 */
	private BigDecimal feeTotal;

	/**
	 * 预付运费总额(元)
	 */
	private BigDecimal prepaidFeeTotal;

	/**
	 * 到付运费总额(元)
	 */
	private BigDecimal arriveFeeTotal;

	/**
	 * 调整费用(元)
	 */
	private BigDecimal adjustFee;

	/**
	 * 奖罚类型（REWARD：奖励 FINE：罚款）
	 */
	private String awardType;

	/**
	 * 押回单是否到达：(Y:是,N：否)
	 */
	private String beMidwayLoad;

	/**
	 * 支付方式(CT:月结 CH:现金 CD:银行卡 TT：电汇 NT:支票 OL:网上支付 DT:临时欠款 FC:到付-----------详情查看数据字典)
	 */
	private String paymentType;

	/**
	 * 是否月结(1:是 0：否)
	 */
	private String isMonthPay;
	
	/**
	 * 应付单单号
	 */
	private String paybillNo;
	
	public String getPaybillNo() {
		return paybillNo;
	}

	public void setPaybillNo(String paybillNo) {
		this.paybillNo = paybillNo;
	}

	/**
	 * paymentType
	 * 
	 * @return the paymentType
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * vehicleNo
	 * 
	 * @return the vehicleNo
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * @param vehicleNo the vehicleNo to set Date:2013-4-19下午1:39:20
	 */

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * @param paymentType the paymentType to set Date:2013-4-22下午5:40:54
	 */

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * vehicleAssembleNo
	 * 
	 * @return the vehicleAssembleNo
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public String getVehicleAssembleNo() {
		return vehicleAssembleNo;
	}

	/**
	 * @param vehicleAssembleNo the vehicleAssembleNo to set Date:2013-4-19下午1:39:20
	 */

	public void setVehicleAssembleNo(String vehicleAssembleNo) {
		this.vehicleAssembleNo = vehicleAssembleNo;
	}

	/**
	 * driverCode
	 * 
	 * @return the driverCode
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * @param driverCode the driverCode to set Date:2013-4-19下午1:39:20
	 */

	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	/**
	 * driverName
	 * 
	 * @return the driverName
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public String getDriverName() {
		return driverName;
	}

	/**
	 * @param driverName the driverName to set Date:2013-4-19下午1:39:20
	 */

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * origOrgCode
	 * 
	 * @return the origOrgCode
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public String getOrigOrgCode() {
		return origOrgCode;
	}

	/**
	 * @param origOrgCode the origOrgCode to set Date:2013-4-19下午1:39:20
	 */

	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	/**
	 * origOrgName
	 * 
	 * @return the origOrgName
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public String getOrigOrgName() {
		return origOrgName;
	}

	/**
	 * @param origOrgName the origOrgName to set Date:2013-4-19下午1:39:20
	 */

	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	/**
	 * destOrgCode
	 * 
	 * @return the destOrgCode
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public String getDestOrgCode() {
		return destOrgCode;
	}

	/**
	 * @param destOrgCode the destOrgCode to set Date:2013-4-19下午1:39:20
	 */

	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	/**
	 * destOrgName
	 * 
	 * @return the destOrgName
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public String getDestOrgName() {
		return destOrgName;
	}

	/**
	 * @param destOrgName the destOrgName to set Date:2013-4-19下午1:39:20
	 */

	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	/**
	 * feeTotal
	 * 
	 * @return the feeTotal
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public BigDecimal getFeeTotal() {
		return feeTotal;
	}

	/**
	 * @param feeTotal the feeTotal to set Date:2013-4-19下午1:39:20
	 */

	public void setFeeTotal(BigDecimal feeTotal) {
		this.feeTotal = feeTotal;
	}

	/**
	 * prepaidFeeTotal
	 * 
	 * @return the prepaidFeeTotal
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public BigDecimal getPrepaidFeeTotal() {
		return prepaidFeeTotal;
	}

	/**
	 * @param prepaidFeeTotal the prepaidFeeTotal to set Date:2013-4-19下午1:39:20
	 */

	public void setPrepaidFeeTotal(BigDecimal prepaidFeeTotal) {
		this.prepaidFeeTotal = prepaidFeeTotal;
	}

	/**
	 * arriveFeeTotal
	 * 
	 * @return the arriveFeeTotal
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public BigDecimal getArriveFeeTotal() {
		return arriveFeeTotal;
	}

	/**
	 * @param arriveFeeTotal the arriveFeeTotal to set Date:2013-4-19下午1:39:20
	 */

	public void setArriveFeeTotal(BigDecimal arriveFeeTotal) {
		this.arriveFeeTotal = arriveFeeTotal;
	}

	/**
	 * adjustFee
	 * 
	 * @return the adjustFee
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public BigDecimal getAdjustFee() {
		return adjustFee;
	}

	/**
	 * @param adjustFee the adjustFee to set Date:2013-4-19下午1:39:20
	 */

	public void setAdjustFee(BigDecimal adjustFee) {
		this.adjustFee = adjustFee;
	}

	/**
	 * awardType
	 * 
	 * @return the awardType
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public String getAwardType() {
		return awardType;
	}

	/**
	 * @param awardType the awardType to set Date:2013-4-19下午1:39:20
	 */

	public void setAwardType(String awardType) {
		this.awardType = awardType;
	}

	/**
	 * beMidwayLoad
	 * 
	 * @return the beMidwayLoad
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public String getBeMidwayLoad() {
		return beMidwayLoad;
	}

	/**
	 * @param beMidwayLoad the beMidwayLoad to set Date:2013-4-19下午1:39:20
	 */

	public void setBeMidwayLoad(String beMidwayLoad) {
		this.beMidwayLoad = beMidwayLoad;
	}

	/**
	 * isMonthPay
	 * 
	 * @return the isMonthPay
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public String getIsMonthPay() {
		return isMonthPay;
	}

	/**
	 * @param isMonthPay the isMonthPay to set Date:2013-4-19下午1:39:20
	 */

	public void setIsMonthPay(String isMonthPay) {
		this.isMonthPay = isMonthPay;
	}

}
