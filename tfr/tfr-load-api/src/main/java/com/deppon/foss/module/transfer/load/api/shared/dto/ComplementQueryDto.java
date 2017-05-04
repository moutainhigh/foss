package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.util.Date;
import java.util.List;

/** 
 * @className: ComplementQueryDto
 * @author: ShiWei shiwei@outlook.com
 * @description: 补码信息查询条件/结果
 * @date: 2013-7-23 上午10:33:50
 * 
 */
public class ComplementQueryDto {
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 出发城市code
	 */
	private String departCityCode;
	
	/**
	 * 出发城市name
	 */
	private String departCityName;
	
	/**
	 * 到达城市code
	 */
	private String arriveCityCode;
	
	/**
	 * 到达城市name
	 */
	private String arriveCityName;
	
	/**
	 * 到达区县code
	 */
	private String arriveDistCode;
	
	/**
	 * 到达区县name
	 */
	private String arriveDistName;
	
	/**
	 * 到达省份
	 */
	private String arriveProName;
	
	/**
	 * 提货网点code
	 */
	private String pkpOrgCode;
	
	/**
	 * 提货网点name
	 */
	private String pkpOrgName;
	
	/**
	 * 收货人地址
	 */
	private String address;
	
	/**
	 * 开单时间
	 */
	private Date billingTime;
	
	/**
	 * 退回时间
	 */
	private Date returnTime;
	
	/**
	 * 开始开单时间
	 */
	private Date beginBillingTime;
	
	/**
	 * 截止开单时间
	 */
	private Date endBillingTime;
	
	/**
	 * 开始退回时间
	 */
	private Date beginReturnTime;
	
	/**
	 * 截止退回时间
	 */
	private Date endReturnTime;
	
	/**
	 * 开单部门code
	 */
	private String billingOrgCode;
	
	/**
	 * 开单部门name
	 */
	private String billingOrgName;
	
	/**
	 * 提货方式
	 */
	private String pkpMethod;
	
	/**
	 * 收货人姓名
	 */
	private String consigneeName;

	/**
	 * 收货人电话
	 */
	private String consigneeTel;
	
	/**
	 * 是否已补码
	 */
	private String beDone;
	
	/**
	 * 补码时间
	 */
	private Date complementTime;
	
	/**
	 * 提货网点codeList
	 */
	private List<String> pkpOrgCodeList;
	
	/**
	 * 当前登录用户所在的快递外场
	 * @author 200978-foss-xiaobingcheng
	 * 2014-7-21
	 * @return
	 */
	private String currentTransferCenterCode;

	/**
	 * 到达区县
	 */
	private List<String> arriveDistCodeList;
	//历史补码部门
	private String historyComplementName;
	/**
	 * 返货类型
	 * @return
	 */
	
	
    private String returntype;
    /**
     * 是否电子运单Y是，N否
     */
    private String beEwaybill;
    
	////////////// 335284 start//////new properties/////////////
    /**
     * 具体收货地址
     */
    private String receiveCustomerAddress;
    /**
     * 收货人地址备注
     */
    private String receiveCustomerAddressNote;
    /**
     * 接货补码code
     */
	private String receiveOrgCode;
	/**
	 * 收货人省份code
	 */
	private String receiveCustomerProvCode;
	/**
	 * 收货人城市code
	 */
	private String receiveCustomerCityCode;
	/**
	 * 收货人行政区域code
	 */
	private String receiveCustomerDistCode;
	/**
	 * 收货人村code
	 */
	private String receiveCustomerVillageCode;
	/**
	 * 发货城市code
	 */
	private String deliveryCustomerCityCode;
	////////////// 335284 end///////////////////
    
    
	public String getReturntype() {
		return returntype;
	}
	public void setReturntype(String returntype) {
		this.returntype = returntype;
	}
	public List<String> getArriveDistCodeList() {
		return arriveDistCodeList;
	}
	public void setArriveDistCodeList(List<String> arriveDistCodeList) {
		this.arriveDistCodeList = arriveDistCodeList;
	}

	public List<String> getPkpOrgCodeList() {
		return pkpOrgCodeList;
	}

	public void setPkpOrgCodeList(List<String> pkpOrgCodeList) {
		this.pkpOrgCodeList = pkpOrgCodeList;
	}

	public Date getComplementTime() {
		return complementTime;
	}

	public void setComplementTime(Date complementTime) {
		this.complementTime = complementTime;
	}

	public String getBeDone() {
		return beDone;
	}

	public void setBeDone(String beDone) {
		this.beDone = beDone;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getDepartCityCode() {
		return departCityCode;
	}

	public void setDepartCityCode(String departCityCode) {
		this.departCityCode = departCityCode;
	}

	public String getDepartCityName() {
		return departCityName;
	}

	public void setDepartCityName(String departCityName) {
		this.departCityName = departCityName;
	}

	public String getArriveCityCode() {
		return arriveCityCode;
	}

	public void setArriveCityCode(String arriveCityCode) {
		this.arriveCityCode = arriveCityCode;
	}

	public String getArriveCityName() {
		return arriveCityName;
	}

	public void setArriveCityName(String arriveCityName) {
		this.arriveCityName = arriveCityName;
	}

	public String getPkpOrgCode() {
		return pkpOrgCode;
	}

	public void setPkpOrgCode(String pkpOrgCode) {
		this.pkpOrgCode = pkpOrgCode;
	}

	public String getPkpOrgName() {
		return pkpOrgName;
	}

	public void setPkpOrgName(String pkpOrgName) {
		this.pkpOrgName = pkpOrgName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBillingTime() {
		return billingTime;
	}

	public void setBillingTime(Date billingTime) {
		this.billingTime = billingTime;
	}

	public Date getReturnTime() {
		return returnTime;
	}
	
	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}
	
	public Date getBeginBillingTime() {
		return beginBillingTime;
	}

	public void setBeginBillingTime(Date beginBillingTime) {
		this.beginBillingTime = beginBillingTime;
	}

	public Date getEndBillingTime() {
		return endBillingTime;
	}
	
	public void setEndBillingTime(Date endBillingTime) {
		this.endBillingTime = endBillingTime;
	}

	public Date getBeginReturnTime() {
		return beginReturnTime;
	}
	public void setBeginReturnTime(Date beginReturnTime) {
		this.beginReturnTime = beginReturnTime;
	}
	public Date getEndReturnTime() {
		return endReturnTime;
	}
	public void setEndReturnTime(Date endReturnTime) {
		this.endReturnTime = endReturnTime;
	}
	
	public String getBillingOrgCode() {
		return billingOrgCode;
	}

	public void setBillingOrgCode(String billingOrgCode) {
		this.billingOrgCode = billingOrgCode;
	}

	public String getBillingOrgName() {
		return billingOrgName;
	}

	public void setBillingOrgName(String billingOrgName) {
		this.billingOrgName = billingOrgName;
	}

	public String getPkpMethod() {
		return pkpMethod;
	}

	public void setPkpMethod(String pkpMethod) {
		this.pkpMethod = pkpMethod;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneeTel() {
		return consigneeTel;
	}

	public void setConsigneeTel(String consigneeTel) {
		this.consigneeTel = consigneeTel;
	}

	public String getArriveProName() {
		return arriveProName;
	}

	public void setArriveProName(String arriveProName) {
		this.arriveProName = arriveProName;
	}

	public String getArriveDistCode() {
		return arriveDistCode;
	}

	public void setArriveDistCode(String arriveDistCode) {
		this.arriveDistCode = arriveDistCode;
	}
	
	public String getArriveDistName() {
		return arriveDistName;
	}

	public void setArriveDistName(String arriveDistName) {
		this.arriveDistName = arriveDistName;
	}

	public String getCurrentTransferCenterCode() {
		return currentTransferCenterCode;
	}

	public void setCurrentTransferCenterCode(String currentTransferCenterCode) {
		this.currentTransferCenterCode = currentTransferCenterCode;
	}
	public String getHistoryComplementName() {
		return historyComplementName;
	}
	public void setHistoryComplementName(String historyComplementName) {
		this.historyComplementName = historyComplementName;
	}
	/** 
	 *获取 是否电子运单Y是，N否
	 * @return  beEwaybill  
	 */
	public String getBeEwaybill() {
		return beEwaybill;
	}
	/**
	 *设置 是否电子运单Y是，N否
	 *setBeEwaybill
	 * @param beEwaybill the beEwaybill to set
	 * @return the beEwaybill
	 */
	public void setBeEwaybill(String beEwaybill) {
		this.beEwaybill = beEwaybill;
	}
	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}
	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}
	public String getReceiveCustomerAddressNote() {
		return receiveCustomerAddressNote;
	}
	public void setReceiveCustomerAddressNote(String receiveCustomerAddressNote) {
		this.receiveCustomerAddressNote = receiveCustomerAddressNote;
	}
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}
	public String getReceiveCustomerProvCode() {
		return receiveCustomerProvCode;
	}
	public void setReceiveCustomerProvCode(String receiveCustomerProvCode) {
		this.receiveCustomerProvCode = receiveCustomerProvCode;
	}
	public String getReceiveCustomerCityCode() {
		return receiveCustomerCityCode;
	}
	public void setReceiveCustomerCityCode(String receiveCustomerCityCode) {
		this.receiveCustomerCityCode = receiveCustomerCityCode;
	}
	public String getReceiveCustomerDistCode() {
		return receiveCustomerDistCode;
	}
	public void setReceiveCustomerDistCode(String receiveCustomerDistCode) {
		this.receiveCustomerDistCode = receiveCustomerDistCode;
	}
	public String getReceiveCustomerVillageCode() {
		return receiveCustomerVillageCode;
	}
	public void setReceiveCustomerVillageCode(String receiveCustomerVillageCode) {
		this.receiveCustomerVillageCode = receiveCustomerVillageCode;
	}
	public String getDeliveryCustomerCityCode() {
		return deliveryCustomerCityCode;
	}
	public void setDeliveryCustomerCityCode(String deliveryCustomerCityCode) {
		this.deliveryCustomerCityCode = deliveryCustomerCityCode;
	}
}

