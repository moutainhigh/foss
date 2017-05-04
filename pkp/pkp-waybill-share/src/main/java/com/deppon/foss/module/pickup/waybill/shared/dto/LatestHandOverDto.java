package com.deppon.foss.module.pickup.waybill.shared.dto;

/**
 * 通过单号获取最新单号走货信息
 * @author Foss-105888-Zhangxingwang
 * @date 2013-11-14 10:19:55
 *
 */
public class LatestHandOverDto {

	private String waybillNo;
	
	private String serialNo;
	
	private String productName;
	
	private String receiveCustomerName;
	
	private String customerPickUpOrgName;
	
	private String handoverBillNo;
	
	private String vehicleAssembleNo;
	
	private String isInstock;
	
	private String exceptMsg;
	
	private String isPrinted;

	public String getIsPrinted() {
		return isPrinted;
	}

	public void setIsPrinted(String isPrinted) {
		this.isPrinted = isPrinted;
	}

	public String getIsInstock() {
		return isInstock;
	}

	public void setIsInstock(String isInstock) {
		this.isInstock = isInstock;
	}

	public String getExceptMsg() {
		return exceptMsg;
	}

	public void setExceptMsg(String exceptMsg) {
		this.exceptMsg = exceptMsg;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	public String getCustomerPickUpOrgName() {
		return customerPickUpOrgName;
	}

	public void setCustomerPickUpOrgName(String customerPickUpOrgName) {
		this.customerPickUpOrgName = customerPickUpOrgName;
	}

	public String getHandoverBillNo() {
		return handoverBillNo;
	}

	public void setHandoverBillNo(String handoverBillNo) {
		this.handoverBillNo = handoverBillNo;
	}

	public String getVehicleAssembleNo() {
		return vehicleAssembleNo;
	}

	public void setVehicleAssembleNo(String vehicleAssembleNo) {
		this.vehicleAssembleNo = vehicleAssembleNo;
	}
	
}
