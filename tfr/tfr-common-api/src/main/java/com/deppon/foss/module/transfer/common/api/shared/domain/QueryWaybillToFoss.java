package com.deppon.foss.module.transfer.common.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 
 * 快递运单
 * @author 313352   gouyangyang
 *
 */

public class QueryWaybillToFoss implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 单据类型
	 */
	private String billType;
	/**
	 * 重量
	 */
	private BigDecimal goodsWeight;
	/**
	 * 体积
	 */
	private BigDecimal goodsVolume;
	/**
	 * 货物名称
	 */
	private String goodsName;
	/**
	 * 货物包装
	 */
    private String goodsPackage;
    /**
     * 寄件联系人
     */
    private String shipperContactPerson;
    /**
     * 寄件地址备注
     */
    private String shipperContactAddMemo;
    /**
     * 目的网点名称
     */
    private String destinationPointName;
    
    /**
     * 收货联系人
     */
    private String consigneeContactPerson;
    
    /**
     * 收件单位
     */
    private String consigneeCompany;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 提货方式 
     */
    private String receiveMethod;
    
    /**
     *发货人地址 
     */
    private String consignerArea;
    
    /**
     * 收货人地址 
     */
    private String consigneeArea;

    
	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public BigDecimal getGoodsWeight() {
		return goodsWeight;
	}

	public void setGoodsWeight(BigDecimal goodsWeight) {
		this.goodsWeight = goodsWeight;
	}

	public BigDecimal getGoodsVolume() {
		return goodsVolume;
	}

	public void setGoodsVolume(BigDecimal goodsVolume) {
		this.goodsVolume = goodsVolume;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsPackage() {
		return goodsPackage;
	}

	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	public String getShipperContactPerson() {
		return shipperContactPerson;
	}

	public void setShipperContactPerson(String shipperContactPerson) {
		this.shipperContactPerson = shipperContactPerson;
	}

	public String getShipperContactAddMemo() {
		return shipperContactAddMemo;
	}

	public void setShipperContactAddMemo(String shipperContactAddMemo) {
		this.shipperContactAddMemo = shipperContactAddMemo;
	}

	public String getDestinationPointName() {
		return destinationPointName;
	}

	public void setDestinationPointName(String destinationPointName) {
		this.destinationPointName = destinationPointName;
	}

	public String getConsigneeContactPerson() {
		return consigneeContactPerson;
	}

	public void setConsigneeContactPerson(String consigneeContactPerson) {
		this.consigneeContactPerson = consigneeContactPerson;
	}

	public String getConsigneeCompany() {
		return consigneeCompany;
	}

	public void setConsigneeCompany(String consigneeCompany) {
		this.consigneeCompany = consigneeCompany;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	public String getConsignerArea() {
		return consignerArea;
	}

	public void setConsignerArea(String consignerArea) {
		this.consignerArea = consignerArea;
	}

	public String getConsigneeArea() {
		return consigneeArea;
	}

	public void setConsigneeArea(String consigneeArea) {
		this.consigneeArea = consigneeArea;
	}
	

}
