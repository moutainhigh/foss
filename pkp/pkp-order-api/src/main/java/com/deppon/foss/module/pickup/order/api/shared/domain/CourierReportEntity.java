package com.deppon.foss.module.pickup.order.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * @ClassName: OrderReportEntity
 * @Description: 快递订单处理每日统计报表记录
 * @author YANGBIN
 * @date 2014-5-9 下午2:01:48
 * 
 */
public class CourierReportEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 快递员工名称
	 */
	private String courierName;
	/**
	 * 快递员工编码
	 */
	private String courierCode;
	
	/**
	 * 快递员车牌号
	 */
	private String vehicleNo;
	
	/**
	 * 收派大区名称
	 */
	private String bigRegionName;
	/**
	 * 收派大区编码
	 */
	private String bigRegionCode;
	/**
	 * 收派小区名称
	 */
	private String smallRegionName;

	/**
	 * 收派小区编码
	 */
	private String smallRegionCode;
	/**
	 * 快递员属性
	 */
	private String courierType;

	/**
	 * 快递接收订单总数
	 */
	private Integer receiveOrderTotal;
	
	/**
	 * 快递接收订单并已开单总数
	 */
	private Integer receiveWaybillOrderTotal;
	
	/**
	 * 快递无订单开单总数
	 */
	private Integer noOrderWaybillTotal;

	/**
	 * 快递派送运单总数数
	 */
	private Integer deliverWaybillTotal; 
	
	/**
	 * 快递派送且签收运单总数数
	 */
	private Integer signWaybillTotal;
	
	/**
	 * 版本号
	 */
	private String dateVersion;
	 
	public String getCourierName() {
		return courierName;
	}

	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}

	public String getCourierCode() {
		return courierCode;
	}

	public void setCourierCode(String courierCode) {
		this.courierCode = courierCode;
	}

	public String getBigRegionName() {
		return bigRegionName;
	}

	public void setBigRegionName(String bigRegionName) {
		this.bigRegionName = bigRegionName;
	}

	public String getBigRegionCode() {
		return bigRegionCode;
	}

	public void setBigRegionCode(String bigRegionCode) {
		this.bigRegionCode = bigRegionCode;
	}

	public String getSmallRegionName() {
		return smallRegionName;
	}

	public void setSmallRegionName(String smallRegionName) {
		this.smallRegionName = smallRegionName;
	}

	public String getSmallRegionCode() {
		return smallRegionCode;
	}

	public void setSmallRegionCode(String smallRegionCode) {
		this.smallRegionCode = smallRegionCode;
	}

	public String getCourierType() {
		return courierType;
	}

	public void setCourierType(String courierType) {
		this.courierType = courierType;
	}

	public Integer getReceiveOrderTotal() {
		return receiveOrderTotal;
	}

	public void setReceiveOrderTotal(Integer receiveOrderTotal) {
		this.receiveOrderTotal = receiveOrderTotal;
	}

	public Integer getReceiveWaybillOrderTotal() {
		return receiveWaybillOrderTotal;
	}

	public void setReceiveWaybillOrderTotal(Integer receiveWaybillOrderTotal) {
		this.receiveWaybillOrderTotal = receiveWaybillOrderTotal;
	}

	public Integer getNoOrderWaybillTotal() {
		return noOrderWaybillTotal;
	}

	public void setNoOrderWaybillTotal(Integer noOrderWaybillTotal) {
		this.noOrderWaybillTotal = noOrderWaybillTotal;
	}

	public Integer getDeliverWaybillTotal() {
		return deliverWaybillTotal;
	}

	public void setDeliverWaybillTotal(Integer deliverWaybillTotal) {
		this.deliverWaybillTotal = deliverWaybillTotal;
	}

	public Integer getSignWaybillTotal() {
		return signWaybillTotal;
	}

	public void setSignWaybillTotal(Integer signWaybillTotal) {
		this.signWaybillTotal = signWaybillTotal;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getDateVersion() {
		return dateVersion;
	}

	public void setDateVersion(String dateVersion) {
		this.dateVersion = dateVersion;
	} 
	
	
}