package com.deppon.foss.module.pickup.waybill.shared.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 发货清单，返回结果VO
 * 
 * @author 272311
 * 
 */
public class DeliverGoodsListVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 寄件日期（开单时间）, 精确到日
	 */
	private Date createTime ;
	/**
	 * 运单号
	 */
	private String waybillNo ;
	/**
	 * 对方城市,收货人所在城市名称 ,收货地址 --省市区+详细地址
	 */
	private String ReceiveCustomerDetailAddress ;
	/**
	 * 对方公司名称,收货人所在公司名称 ,对应字段：收货客户名称
	 */
	private String receiveCustomerName ;
	/**
	 * 重量
	 */
	private BigDecimal goodsWeightTotal ;
	/**
	 * 体积
	 */
	private BigDecimal goodsVolumeTotal ;
	/**
	 * 运费
	 */
	private BigDecimal transportFee ;
	/**
	 * 保价，保价产生的手续费（元）
	 */
	private BigDecimal insuranceFee ;
	/**
	 * 代收货款,代收货款的金额
	 */
	private BigDecimal codAmount ;
	/**
	 * 代收手续费
	 */
	private BigDecimal codFee ;
	/**
	 * 合计费用(不包含代收货款),= 总费用-代收货款-代收手续费
	 */
	private BigDecimal totalCost ;
	/**
	 * 发货人,开单时发货人的名字,对应字段：发货联系人
	 */
	private String deliveryCustomerName ;
	
	/**
	 * 货物状态,运输中、签收等状态,对应运单表中的货物状态字段。从 中转 那边取值
	 */
	 private String goodsStatus ;
	 
	/**
	 * 签收人、从 结算 那边取值
	 */
	 private String deliverymanName ;
	 
	/**
	 * 签收时间、取最新的签收时间。从结算那边取值
	 */
	 private Date signTime ;
	 
	/**
	 * 货物当前所在城市,在途中上取一个城市。从 中转 那边取值
	 */
	 private String goodsCurrentCity ;
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getReceiveCustomerDetailAddress() {
		return ReceiveCustomerDetailAddress;
	}

	public void setReceiveCustomerDetailAddress(
			String receiveCustomerDetailAddress) {
		ReceiveCustomerDetailAddress = receiveCustomerDetailAddress;
	}

	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	public String getDeliveryCustomerName() {
		return deliveryCustomerName;
	}

	public void setDeliveryCustomerName(String deliveryCustomerName) {
		this.deliveryCustomerName = deliveryCustomerName;
	}

	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}

	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}

	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	public BigDecimal getTransportFee() {
		return transportFee;
	}

	public void setTransportFee(BigDecimal transportFee) {
		this.transportFee = transportFee;
	}

	public BigDecimal getInsuranceFee() {
		return insuranceFee;
	}

	public void setInsuranceFee(BigDecimal insuranceFee) {
		this.insuranceFee = insuranceFee;
	}

	public BigDecimal getCodAmount() {
		return codAmount;
	}

	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}

	public BigDecimal getCodFee() {
		return codFee;
	}

	public void setCodFee(BigDecimal codFee) {
		this.codFee = codFee;
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	public String getDeliverymanName() {
		return deliverymanName;
	}

	public void setDeliverymanName(String deliverymanName) {
		this.deliverymanName = deliverymanName;
	}

	public String getGoodsCurrentCity() {
		return goodsCurrentCity;
	}

	public void setGoodsCurrentCity(String goodsCurrentCity) {
		this.goodsCurrentCity = goodsCurrentCity;
	}

	public String getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}
	
	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	@Override
	public String toString() {
		return "DeliverGoodsListVo [waybillNo=" + waybillNo
				+ ", ReceiveCustomerDetailAddress="
				+ ReceiveCustomerDetailAddress + ", receiveCustomerName="
				+ receiveCustomerName + ", deliveryCustomerName="
				+ deliveryCustomerName + ", deliverymanName=" + deliverymanName
				+ ", goodsStatus=" + goodsStatus + ", goodsCurrentCity="
				+ goodsCurrentCity + ", goodsWeightTotal=" + goodsWeightTotal
				+ ", goodsVolumeTotal=" + goodsVolumeTotal + ", transportFee="
				+ transportFee + ", insuranceFee=" + insuranceFee
				+ ", codAmount=" + codAmount + ", codFee=" + codFee
				+ ", totalCost=" + totalCost + ", signTime=" + signTime
				+ ", createTime=" + createTime + "]";
	}

}
