package com.deppon.foss.module.transfer.airfreight.api.shared.dto;

import java.io.Serializable;

import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirCargoVolumeQueryEntity;

/**
 * 
 */
public class AirCargoVolumeForExportDto extends AirCargoVolumeQueryEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5004170064810424510L;
	
	/**
	 * 航空公司
	 */
	private String airName;
	
	/**
	 * 始发站
	 */
	private String deptRegionName;
	
	/**
	 * 配载类型
	 */
	private String airAssembleType;
	
	/**
	 * 合票号
	 */
	private String jointTicketNo;
	
	/**
	 * 航班日期
	 */
	private String flightDate;
	
	/**
	 * 起飞时间
	 */
	private String takeOffTime;
	
	/**
	 * 到达时间
	 */
	private String arriveTime;
	
	/**
	 * 件数
	 */
	private String goodsQty;
	
	/**
	 * 付款类型
	 */
	private String paymentType;
	
	/**
	 * 收货人名称
	 */
	private String receiverName;
	
	/**
	 * 外发代理
	 */
	private String agencyName;
	
	/**
	 * 制单人
	 */
	private String handoverCreateName;
	
	/**
	 * 制单部门
	 */
	private String handoverCreateOrgName;
	
	/**
	 * 总运费
	 */
	private String totalFee;
	
	/**
	 * 获取 航空公司.
	 *
	 * @return the 航空公司
	 */
	public String getAirName() {
		return airName;
	}

	/**
	 * 设置 航空公司.
	 *
	 * @param airName the new 航空公司
	 */
	public void setAirName(String airName) {
		this.airName = airName;
	}

	/**
	 * 获取 始发站.
	 *
	 * @return the 始发站
	 */
	public String getDeptRegionName() {
		return deptRegionName;
	}

	/**
	 * 设置 始发站.
	 *
	 * @param deptRegionName the new 始发站
	 */
	public void setDeptRegionName(String deptRegionName) {
		this.deptRegionName = deptRegionName;
	}

	/**
	 * 获取 配载类型.
	 *
	 * @return the 配载类型
	 */
	public String getAirAssembleType() {
		return airAssembleType;
	}

	/**
	 * 设置 配载类型.
	 *
	 * @param airAssembleType the new 配载类型
	 */
	public void setAirAssembleType(String airAssembleType) {
		this.airAssembleType = airAssembleType;
	}

	/**
	 * 获取 合票号.
	 *
	 * @return the 合票号
	 */
	public String getJointTicketNo() {
		return jointTicketNo;
	}

	/**
	 * 设置 合票号.
	 *
	 * @param jointTicketNo the new 合票号
	 */
	public void setJointTicketNo(String jointTicketNo) {
		this.jointTicketNo = jointTicketNo;
	}

	/**
	 * 获取 航班日期.
	 *
	 * @return the 航班日期
	 */
	public String getFlightDate() {
		return flightDate;
	}

	/**
	 * 设置 航班日期.
	 *
	 * @param flightDate the new 航班日期
	 */
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * 获取 起飞时间.
	 *
	 * @return the 起飞时间
	 */
	public String getTakeOffTime() {
		return takeOffTime;
	}

	/**
	 * 设置 起飞时间.
	 *
	 * @param takeOffTime the new 起飞时间
	 */
	public void setTakeOffTime(String takeOffTime) {
		this.takeOffTime = takeOffTime;
	}

	/**
	 * 获取 到达时间.
	 *
	 * @return the 到达时间
	 */
	public String getArriveTime() {
		return arriveTime;
	}

	/**
	 * 设置 到达时间.
	 *
	 * @param arriveTime the new 到达时间
	 */
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	/**
	 * 获取 件数.
	 *
	 * @return the 件数
	 */
	public String getGoodsQty() {
		return goodsQty;
	}

	/**
	 * 设置 件数.
	 *
	 * @param goodsQyt the new 件数
	 */
	public void setGoodsQty(String goodsQty) {
		this.goodsQty = goodsQty;
	}

	/**
	 * 获取 付款类型.
	 *
	 * @return the 付款类型
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * 设置 付款类型.
	 *
	 * @param paymentType the new 付款类型
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * 获取 收货人名称.
	 *
	 * @return the 收货人名称
	 */
	public String getReceiverName() {
		return receiverName;
	}

	/**
	 * 设置 收货人名称.
	 *
	 * @param receiverName the new 收货人名称
	 */
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	/**
	 * 获取 外发代理.
	 *
	 * @return the 外发代理
	 */
	public String getAgencyName() {
		return agencyName;
	}

	/**
	 * 设置 外发代理.
	 *
	 * @param agencyName the new 外发代理
	 */
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	/**
	 * 获取 制单人.
	 *
	 * @return the 制单人
	 */
	public String getHandoverCreateName() {
		return handoverCreateName;
	}

	/**
	 * 设置 制单人.
	 *
	 * @param handoverCreateName the new 制单人
	 */
	public void setHandoverCreateName(String handoverCreateName) {
		this.handoverCreateName = handoverCreateName;
	}

	/**
	 * 获取 制单部门.
	 *
	 * @return the 制单部门
	 */
	public String getHandoverCreateOrgName() {
		return handoverCreateOrgName;
	}

	/**
	 * 设置 制单部门.
	 *
	 * @param handoverCreateOrgName the new 制单部门
	 */
	public void setHandoverCreateOrgName(String handoverCreateOrgName) {
		this.handoverCreateOrgName = handoverCreateOrgName;
	}

	/**
	 * 获取 总运费.
	 *
	 * @return the 总运费
	 */
	public String getTotalFee() {
		return totalFee;
	}

	/**
	 * 设置 总运费.
	 *
	 * @param totalFee the new 总运费
	 */
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

}
