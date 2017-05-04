package com.deppon.foss.module.pickup.waybill.shared.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 快递签收状态详细信息
 * @author 272311
 *
 */
public class WaybillSignDetailVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 序号
	 */
	private String id;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 寄件时间
	 */
	private Date billTime;
	/**
	 * 开单时间
	 */
	private Date createTime;
	/**
	 * 原发货人
	 */
	private String deliveryCustomerName;
	/**
	 * 收货人
	 */
	private String receiveCustomerName;
	/**
	 * 目的地城市,提货网点
	 */
	private String customerPickupOrgCode;
	
	/**
	 * 承诺到达时间
	 */
	private Date preCustomerPickupTime ;
	
	/**
	 * 货物总件数
	 */
	private Integer goodsQtyTotal;
	/**
	 * 货物总重量
	 */
	private BigDecimal goodsWeightTotal;

	/**
	 * 运费
	 */
	private BigDecimal transportFee;
	/**
	 * 保价金额
	 */
	private BigDecimal codFee;

	/**
	 * 代收费用
	 */
	private BigDecimal insuranceFee;
	/**
	 * 其他费用
	 */
	private BigDecimal otherFee;
	/**
	 * 费用合计
	 */
	private BigDecimal totalFee;

	/**
	 * 快递运送状态
	 */
	private String signStatus;
	/**
	 * 签收时间
	 */
	private Date signTime;
	/**
	 * 签收人
	 */
	private String deliverymanName;
	/**
	 * 备注
	 */
	private String signNote;
	
	/**
	 * 外发单号
	 */
	private String externalBillNo;
	
	/**
	 * 落地配公司名称 / 外发公司名称
	 */
	private String agentCompanyName;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public Date getBillTime() {
		return billTime;
	}
	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getDeliveryCustomerName() {
		return deliveryCustomerName;
	}
	public void setDeliveryCustomerName(String deliveryCustomerName) {
		this.deliveryCustomerName = deliveryCustomerName;
	}
	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}
	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}
	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}
	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}
	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}
	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}
	public BigDecimal getTransportFee() {
		return transportFee;
	}
	public void setTransportFee(BigDecimal transportFee) {
		this.transportFee = transportFee;
	}
	public BigDecimal getCodFee() {
		return codFee;
	}
	public void setCodFee(BigDecimal codFee) {
		this.codFee = codFee;
	}
	public BigDecimal getInsuranceFee() {
		return insuranceFee;
	}
	public void setInsuranceFee(BigDecimal insuranceFee) {
		this.insuranceFee = insuranceFee;
	}
	public BigDecimal getOtherFee() {
		return otherFee;
	}
	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}
	public BigDecimal getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}
	public String getSignStatus() {
		return signStatus;
	}
	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}
	public Date getSignTime() {
		return signTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	public String getDeliverymanName() {
		return deliverymanName;
	}
	public void setDeliverymanName(String deliverymanName) {
		this.deliverymanName = deliverymanName;
	}
	public String getSignNote() {
		return signNote;
	}
	public void setSignNote(String signNote) {
		this.signNote = signNote;
	}
	public Date getPreCustomerPickupTime() {
		return preCustomerPickupTime;
	}
	public void setPreCustomerPickupTime(Date preCustomerPickupTime) {
		this.preCustomerPickupTime = preCustomerPickupTime;
	}
	public String getExternalBillNo() {
		return externalBillNo;
	}
	public void setExternalBillNo(String externalBillNo) {
		this.externalBillNo = externalBillNo;
	}
	public String getAgentCompanyName() {
		return agentCompanyName;
	}
	public void setAgentCompanyName(String agentCompanyName) {
		this.agentCompanyName = agentCompanyName;
	}
	
}
