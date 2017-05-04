package com.deppon.foss.module.transfer.scheduling.api.shared.domain;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @title: ExpressWaybillEntity
 * @description： 临时租车标记查询数据(快递运单Entity)
 * @author： gouyangyang
 * @date： 2016-5-24 上午10:48:41
 */
public class ExpressWaybillEntity implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 运单号
	private String wayBillNo;
	// 单据类型
	private String billType;
	// 重量
	private BigDecimal goodsWeight;
	// 体积
	private BigDecimal goodsVolume;
	// 货物名称
	private String goodsName;
	// 货物包装
	private String goodsPackage;
	// 寄件联系人
	private String shipperContactPerson;
	// 寄件地址备注
	private String shipperContactAddMemo;
	// 目的网点名称
	private String destinationPointName;
	// 收货联系人
	private String consigneeContactPerson;
	// 收件单位
	private String consigneeCompany;
	// 创建时间
	private Date createTime;
	public String getWayBillNo() {
		return wayBillNo;
	}
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
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
}
