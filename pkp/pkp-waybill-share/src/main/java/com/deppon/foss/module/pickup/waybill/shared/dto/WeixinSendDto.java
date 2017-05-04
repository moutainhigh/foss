package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 微信消息推送Dto
 * </p>
 * 
 * @author Foss-105888-Zhangxingwang
 * @date 2014-2-11 10:54:31
 */
public class WeixinSendDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 运单来源 
	 */
	private String orderChannel;
	
	/**
	 * 订单号
	 */
	private String orderNo;
	
	/**
	 * 货物名称
	 */
	private String goodsName;
	
	/**
	 * 到付金额
	 */
	private BigDecimal arrivePayAmount;
	
	/**
	 * 发货人姓名
	 */
	private String shippCustomerName;
	
	/**
	 * 发货人手机号
	 */
	private String shippMobile;
	
	/**
	 * 发货客户ID
	 */
	private String shippCustomerId;
	
	/**
	 * 发货客户编码
	 */
	private String shippCustomerCode;
	
	/**
	 * 收货人姓名
	 */
	private String consigneeName;
	
	/**
	 * 收货人手机号
	 */
	private String consigneeMobile;
	
	/**
	 * 状态类型
	 */
	private String stateType;
	
	/**状态发生时间
	 * 
	 */
	private Date createTime;
	
	/**
	 * 发件城市
	 */
	private String shippCity;
	
	/**
	 * 收件城市
	 */
	private String congneeCity;
	
	/**
	 * 当前处理件数
	 */
	private Integer currentPieces;
	
	/**
	 * 已经处理件数
	 */
	private Integer processedPieces;
	
	/**
	 * 总件数
	 */
	private Integer goodsTotal;
	
	/**
	 * 到达网点编码
	 */
	private String customerPickUpOrgCode;
	
	/**
	 * 到达网点名称
	 */
	private String customerPickUpOrgName;
	
	/**
	 * 到达网点电话
	 */
	private String customerPickUpOrgTelephone;
	
	/**
	 * 到达网点地址
	 */
	private String customerPickUpOrgAddress;
	
	/**
	 * 派送人电话
	 */
	private String deliverManMobile;
	
	/**
	 * 派送人姓名
	 */
	private String deliverManName;
	
	/**
	 * 派送失败原因
	 */
	private String deliverFailReason;
	
	/**
	 * 派送网点编码
	 */
	private String deliverOrgCode;
	
	/**
	 * 派送网点名称
	 */
	private String deliverOrgName;
	
	/**
	 * 派送网点电话
	 */
	private String deliverOrgTelephone;
	
	/**
	 * 派送网点地址
	 */
	private String deliverOrgAddress;
	
	/**
	 * 签收人
	 */
	private String signName;
	
	/**
	 * 异常签收原因
	 */
	private String signNote;
	
	/**
	 * 产品类型
	 */
	private String productCode;
	
	/**
	 * 派送单ID
	 */
	private String deliverBillId;
	/**
	 * 派送单编码
	 */
	private String deliverBillNo;
	
	/**
	 * 开单组织编码
	 */
	private String createOrgCode;
	
	/**
	 * 开单总金额
	 * @return
	 */
	private BigDecimal totalAmount;
	
	/**
	 * 代收货款金额
	 * @return
	 */
	private BigDecimal refundAmount;
	
	/**
	 * 未核销金额
	 * @return
	 */
	private BigDecimal repayAmount;
	
	public String getCreateOrgCode() {
		return createOrgCode;
	}
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public String getDeliverBillId() {
		return deliverBillId;
	}

	public void setDeliverBillId(String deliverBillId) {
		this.deliverBillId = deliverBillId;
	}

	public String getDeliverBillNo() {
		return deliverBillNo;
	}

	public void setDeliverBillNo(String deliverBillNo) {
		this.deliverBillNo = deliverBillNo;
	}

	/**
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * @return the waybillNo
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
	 * @return the orderChannel
	 */
	public String getOrderChannel() {
		return orderChannel;
	}

	/**
	 * @param orderChannel the orderChannel to set
	 */
	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}

	/**
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return the goodsName
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * @param goodsName the goodsName to set
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public BigDecimal getArrivePayAmount() {
		return arrivePayAmount;
	}

	public void setArrivePayAmount(BigDecimal arrivePayAmount) {
		this.arrivePayAmount = arrivePayAmount;
	}

	/**
	 * @return the shippCustomerName
	 */
	public String getShippCustomerName() {
		return shippCustomerName;
	}

	/**
	 * @param shippCustomerName the shippCustomerName to set
	 */
	public void setShippCustomerName(String shippCustomerName) {
		this.shippCustomerName = shippCustomerName;
	}

	/**
	 * @return the shippMobile
	 */
	public String getShippMobile() {
		return shippMobile;
	}

	/**
	 * @param shippMobile the shippMobile to set
	 */
	public void setShippMobile(String shippMobile) {
		this.shippMobile = shippMobile;
	}

	/**
	 * @return the shippCustomerId
	 */
	public String getShippCustomerId() {
		return shippCustomerId;
	}

	/**
	 * @param shippCustomerId the shippCustomerId to set
	 */
	public void setShippCustomerId(String shippCustomerId) {
		this.shippCustomerId = shippCustomerId;
	}

	/**
	 * @return the shippCustomerCode
	 */
	public String getShippCustomerCode() {
		return shippCustomerCode;
	}

	/**
	 * @param shippCustomerCode the shippCustomerCode to set
	 */
	public void setShippCustomerCode(String shippCustomerCode) {
		this.shippCustomerCode = shippCustomerCode;
	}

	/**
	 * @return the consigneeName
	 */
	public String getConsigneeName() {
		return consigneeName;
	}

	/**
	 * @param consigneeName the consigneeName to set
	 */
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	/**
	 * @return the consigneeMobile
	 */
	public String getConsigneeMobile() {
		return consigneeMobile;
	}

	/**
	 * @param consigneeMobile the consigneeMobile to set
	 */
	public void setConsigneeMobile(String consigneeMobile) {
		this.consigneeMobile = consigneeMobile;
	}

	/**
	 * @return the stateType
	 */
	public String getStateType() {
		return stateType;
	}

	/**
	 * @param stateType the stateType to set
	 */
	public void setStateType(String stateType) {
		this.stateType = stateType;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the shippCity
	 */
	public String getShippCity() {
		return shippCity;
	}

	/**
	 * @param shippCity the shippCity to set
	 */
	public void setShippCity(String shippCity) {
		this.shippCity = shippCity;
	}

	/**
	 * @return the congneeCity
	 */
	public String getCongneeCity() {
		return congneeCity;
	}

	/**
	 * @param congneeCity the congneeCity to set
	 */
	public void setCongneeCity(String congneeCity) {
		this.congneeCity = congneeCity;
	}

	/**
	 * @return the goodsTotal
	 */
	public Integer getGoodsTotal() {
		return goodsTotal;
	}

	/**
	 * @param goodsTotal the goodsTotal to set
	 */
	public void setGoodsTotal(Integer goodsTotal) {
		this.goodsTotal = goodsTotal;
	}

	/**
	 * @return the customerPickUpOrgCode
	 */
	public String getCustomerPickUpOrgCode() {
		return customerPickUpOrgCode;
	}

	/**
	 * @param customerPickUpOrgCode the customerPickUpOrgCode to set
	 */
	public void setCustomerPickUpOrgCode(String customerPickUpOrgCode) {
		this.customerPickUpOrgCode = customerPickUpOrgCode;
	}

	/**
	 * @return the customerPickUpOrgName
	 */
	public String getCustomerPickUpOrgName() {
		return customerPickUpOrgName;
	}

	/**
	 * @param customerPickUpOrgName the customerPickUpOrgName to set
	 */
	public void setCustomerPickUpOrgName(String customerPickUpOrgName) {
		this.customerPickUpOrgName = customerPickUpOrgName;
	}

	/**
	 * @return the customerPickUpOrgTelephone
	 */
	public String getCustomerPickUpOrgTelephone() {
		return customerPickUpOrgTelephone;
	}

	/**
	 * @param customerPickUpOrgTelephone the customerPickUpOrgTelephone to set
	 */
	public void setCustomerPickUpOrgTelephone(String customerPickUpOrgTelephone) {
		this.customerPickUpOrgTelephone = customerPickUpOrgTelephone;
	}

	/**
	 * @return the customerPickUpOrgAddress
	 */
	public String getCustomerPickUpOrgAddress() {
		return customerPickUpOrgAddress;
	}

	/**
	 * @param customerPickUpOrgAddress the customerPickUpOrgAddress to set
	 */
	public void setCustomerPickUpOrgAddress(String customerPickUpOrgAddress) {
		this.customerPickUpOrgAddress = customerPickUpOrgAddress;
	}

	/**
	 * @return the deliverManMobile
	 */
	public String getDeliverManMobile() {
		return deliverManMobile;
	}

	/**
	 * @param deliverManMobile the deliverManMobile to set
	 */
	public void setDeliverManMobile(String deliverManMobile) {
		this.deliverManMobile = deliverManMobile;
	}

	/**
	 * @return the deliverManName
	 */
	public String getDeliverManName() {
		return deliverManName;
	}

	/**
	 * @param deliverManName the deliverManName to set
	 */
	public void setDeliverManName(String deliverManName) {
		this.deliverManName = deliverManName;
	}

	/**
	 * @return the deliverOrgCode
	 */
	public String getDeliverOrgCode() {
		return deliverOrgCode;
	}

	/**
	 * @param deliverOrgCode the deliverOrgCode to set
	 */
	public void setDeliverOrgCode(String deliverOrgCode) {
		this.deliverOrgCode = deliverOrgCode;
	}

	/**
	 * @return the deliverOrgName
	 */
	public String getDeliverOrgName() {
		return deliverOrgName;
	}

	/**
	 * @param deliverOrgName the deliverOrgName to set
	 */
	public void setDeliverOrgName(String deliverOrgName) {
		this.deliverOrgName = deliverOrgName;
	}

	/**
	 * @return the deliverOrgTelephone
	 */
	public String getDeliverOrgTelephone() {
		return deliverOrgTelephone;
	}

	/**
	 * @param deliverOrgTelephone the deliverOrgTelephone to set
	 */
	public void setDeliverOrgTelephone(String deliverOrgTelephone) {
		this.deliverOrgTelephone = deliverOrgTelephone;
	}

	/**
	 * @return the deliverOrgAddress
	 */
	public String getDeliverOrgAddress() {
		return deliverOrgAddress;
	}

	/**
	 * @param deliverOrgAddress the deliverOrgAddress to set
	 */
	public void setDeliverOrgAddress(String deliverOrgAddress) {
		this.deliverOrgAddress = deliverOrgAddress;
	}

	/**
	 * @return the signName
	 */
	public String getSignName() {
		return signName;
	}

	/**
	 * @param signName the signName to set
	 */
	public void setSignName(String signName) {
		this.signName = signName;
	}

	/**
	 * @return the signNote
	 */
	public String getSignNote() {
		return signNote;
	}

	/**
	 * @param signNote the signNote to set
	 */
	public void setSignNote(String signNote) {
		this.signNote = signNote;
	}
	/**
	 * @return the deliverFailReason
	 */
	public String getDeliverFailReason() {
		return deliverFailReason;
	}

	/**
	 * @param deliverFailReason the deliverFailReason to set
	 */
	public void setDeliverFailReason(String deliverFailReason) {
		this.deliverFailReason = deliverFailReason;
	}

	public Integer getCurrentPieces() {
		return currentPieces;
	}

	public void setCurrentPieces(Integer currentPieces) {
		this.currentPieces = currentPieces;
	}

	public Integer getProcessedPieces() {
		return processedPieces;
	}

	public void setProcessedPieces(Integer processedPieces) {
		this.processedPieces = processedPieces;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BigDecimal getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}
	public BigDecimal getRepayAmount() {
		return repayAmount;
	}
	public void setRepayAmount(BigDecimal repayAmount) {
		this.repayAmount = repayAmount;
	}
}
