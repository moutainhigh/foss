package com.deppon.foss.module.transfer.airfreight.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirNotifyCustomersSmsInfo;

public class AirNotifyCustomersDto implements Serializable{

	
	/**
	 * 空运通知客户
	 */
	private static final long serialVersionUID = -877845632598954306L;
	
	/**
	 * 运单集合 : 从前台查询条件获得传递到后台
	 */
	private String[] arrayWaybillNos;
	
	public String[] getArrayWaybillNos() {
		return arrayWaybillNos;
	}

	public void setArrayWaybillNos(String[] arrayWaybillNos) {
		this.arrayWaybillNos = arrayWaybillNos;
	}
	
	/**
	 * 正单号
	 */
	private String airWaybillNo;
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 选择的运单号列表
	 */
	private String waybillNos;
	
	public String getWaybillNos() {
		return waybillNos;
	}

	public void setWaybillNos(String waybillNos) {
		this.waybillNos = waybillNos;
	}
	/**
	 * 短息通知内容
	 */
	private AirNotifyCustomersSmsInfo airNotifyCustomersSmsInfo;
	/**
	 * 运单 批量通知 
	 */
	private List<AirNotifyCustomersSmsInfo> airNotifyCustomersSmsInfoList;
	
	public List<AirNotifyCustomersSmsInfo> getAirNotifyCustomersSmsInfoList() {
		return airNotifyCustomersSmsInfoList;
	}

	public void setAirNotifyCustomersSmsInfoList(
			List<AirNotifyCustomersSmsInfo> airNotifyCustomersSmsInfoList) {
		this.airNotifyCustomersSmsInfoList = airNotifyCustomersSmsInfoList;
	}

	/**
	 * 通知时间
	 */
	private Date notificationTime;
	
	
	public Date getNotificationTime() {
		return notificationTime;
	}

	public void setNotificationTime(Date notificationTime) {
		this.notificationTime = notificationTime;
	}

	/**
	 * 配载部门
	 */
	private String orgCode;
	/**
	 * 空运通知情况  :  全部   未通知  通知成功  通知失败  通知中
	 */
	private String airNotifyResult;
	/**
	 * 派送方式  : 全部  自提  派送
	 */
	private String deliverType;
	/**
	 * 到达代理处时间
	 */
	private Date arriveTime;

	/**
	 * 前台传来的开始时间 
	 */
	private Date arriveTimeFrom;
	/**
	 * 前台传来的截止时间 
	 */
	private Date arriveTimeTo;
	
	public AirNotifyCustomersSmsInfo getAirNotifyCustomersSmsInfo() {
		return airNotifyCustomersSmsInfo;
	}

	public void setAirNotifyCustomersSmsInfo(
			AirNotifyCustomersSmsInfo airNotifyCustomersSmsInfo) {
		this.airNotifyCustomersSmsInfo = airNotifyCustomersSmsInfo;
	}

	public Date getArriveTimeFrom() {
		return arriveTimeFrom;
	}

	public void setArriveTimeFrom(Date arriveTimeFrom) {
		this.arriveTimeFrom = arriveTimeFrom;
	}

	public Date getArriveTimeTo() {
		return arriveTimeTo;
	}

	public void setArriveTimeTo(Date arriveTimeTo) {
		this.arriveTimeTo = arriveTimeTo;
	}

	/**
	 * 状态
	 */
	private String noticeResult;
	/**
	 * 收货客户编码
	 */
	private String receiveCustomerCode;

	/**
	 * 收货客户名称
	 */
	private String receiveCustomerName;

	/**
	 * 收货客户手机
	 */
	private String receiveCustomerMobilephone;

	/**
	 * 收货客户电话
	 */
	private String receiveCustomerPhone;
	/**
	 * 通知方式:默认为短信,不可编辑
	 */
	private String noticeType;
	/**
	 * XX日内提货
	 */
	private String estimatedPickupTime;
	/**
	 * 通知内容 
	 */
	private String noticeContent;
	/**
	 * 发货客户编码
	 */
	private String deliveryCustomerCode;
	/**
	 * 发货客户名称
	 */
	private String deliveryCustomerName;

	/**
	 * 发货客户手机
	 */
	private String deliveryCustomerMobilephone;

	/**
	 * 发货客户电话
	 */
	private String deliveryCustomerPhone;
	
	/**
	 * 收货部门(出发部门)  --始发部门
	 */
	private String receiveOrgCode;
	/**
	 * 收货部门名称   --始发部门
	 */
	private String receiveOrgName;
	
	/**
	 * 货物名称
	 */
	private String goodsName;
	
	/**
	 * 合票方式
	 */
	private String freightMethod;
	/**
	 * 航班类型
	 */
	private String flightNumberType;
	/**
	 * 总体积
	 */
	private BigDecimal goodsVolumeTotal;

	/**
	 * 货物重量
	 */
	private BigDecimal goodsWeightTotal;
	
	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}

	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}

	/**
	 * 货物包装
	 */
	private String goodsPackage;

	/**
	 * 货物尺寸
	 */
	private String goodsSize;
	/**
	 * 储运事项
	 */
	private String transportationRemark;
	/**
	 * 运输性质
	 */
	private String productCode;
	
	/**
	 * 运输性质
	 */
	private String productName;
	
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * 提货方式
	 */
	private String receiveMethod;
	/**
	 * 提货网点
	 */
	private String customerPickupOrgCode;

	/**
	 * 提货网点名称
	 */
	private String customerPickupOrgName;
	
	/**
	 * 航空正单的到达网点编码
	 */
	private String destOrgCode;
	/**
	 * 航空正单的到达网点名称             
	 */
	private String dedtOrgName;
	/**
	 * 送货费
	 */
	private BigDecimal deliveryGoodsFee;
	/**
	 * 其他费用
	 */
	private BigDecimal otherFee;
	
	/**
	 * 保价费
	 */
	private BigDecimal insuranceFee;

	/**
	 * 代收货款
	 */
	private BigDecimal codAmount;
	/**
	 * 到付金额
	 */
	private BigDecimal toPayAmount;
	
	/**
	 * 开单付款方式
	 */
	private String paidMethod;
	
	/**
	 * 货物总件数
	 */
	private Integer goodsQtyTotal;
	
	/**
	 * 录入到达件数
	 */
	private Integer arriveGoodsQty;
	
	/**
	 * 收货具体地址- 省+市+区+收货具体地址(receiveCustomerAddress) = 收货人地址 = 送货地址
	 */
	private String receiveCustomerAddressSum;
	
	/**
	 * 公布价运费 --运费
	 */
	private BigDecimal transportFee;
	
	/**
	 * 保价声明价值 --货物价值
	 */
	private BigDecimal insuranceAmount;


	public String getAirWaybillNo() {
		return airWaybillNo;
	}

	public void setAirWaybillNo(String airWaybillNo) {
		this.airWaybillNo = airWaybillNo;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getAirNotifyResult() {
		return airNotifyResult;
	}

	public void setAirNotifyResult(String airNotifyResult) {
		this.airNotifyResult = airNotifyResult;
	}

	public String getDeliverType() {
		return deliverType;
	}

	public void setDeliverType(String deliverType) {
		this.deliverType = deliverType;
	}

	public Date getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getNoticeResult() {
		return noticeResult;
	}

	public void setNoticeResult(String noticeResult) {
		this.noticeResult = noticeResult;
	}

	public String getReceiveCustomerCode() {
		return receiveCustomerCode;
	}

	public void setReceiveCustomerCode(String receiveCustomerCode) {
		this.receiveCustomerCode = receiveCustomerCode;
	}

	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}

	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}

	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}

	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public String getEstimatedPickupTime() {
		return estimatedPickupTime;
	}

	public void setEstimatedPickupTime(String estimatedPickupTime) {
		this.estimatedPickupTime = estimatedPickupTime;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}

	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}

	public String getDeliveryCustomerName() {
		return deliveryCustomerName;
	}

	public void setDeliveryCustomerName(String deliveryCustomerName) {
		this.deliveryCustomerName = deliveryCustomerName;
	}

	public String getDeliveryCustomerMobilephone() {
		return deliveryCustomerMobilephone;
	}

	public void setDeliveryCustomerMobilephone(String deliveryCustomerMobilephone) {
		this.deliveryCustomerMobilephone = deliveryCustomerMobilephone;
	}

	public String getDeliveryCustomerPhone() {
		return deliveryCustomerPhone;
	}

	public void setDeliveryCustomerPhone(String deliveryCustomerPhone) {
		this.deliveryCustomerPhone = deliveryCustomerPhone;
	}

	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	public String getReceiveOrgName() {
		return receiveOrgName;
	}

	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getFreightMethod() {
		return freightMethod;
	}

	public void setFreightMethod(String freightMethod) {
		this.freightMethod = freightMethod;
	}

	public String getFlightNumberType() {
		return flightNumberType;
	}

	public void setFlightNumberType(String flightNumberType) {
		this.flightNumberType = flightNumberType;
	}

	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	public String getGoodsPackage() {
		return goodsPackage;
	}

	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	public String getGoodsSize() {
		return goodsSize;
	}

	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
	}

	public String getTransportationRemark() {
		return transportationRemark;
	}

	public void setTransportationRemark(String transportationRemark) {
		this.transportationRemark = transportationRemark;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}

	public String getCustomerPickupOrgName() {
		return customerPickupOrgName;
	}

	public void setCustomerPickupOrgName(String customerPickupOrgName) {
		this.customerPickupOrgName = customerPickupOrgName;
	}

	public String getDestOrgCode() {
		return destOrgCode;
	}

	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	public String getDedtOrgName() {
		return dedtOrgName;
	}

	public void setDedtOrgName(String dedtOrgName) {
		this.dedtOrgName = dedtOrgName;
	}

	public BigDecimal getDeliveryGoodsFee() {
		return deliveryGoodsFee;
	}

	public void setDeliveryGoodsFee(BigDecimal deliveryGoodsFee) {
		this.deliveryGoodsFee = deliveryGoodsFee;
	}

	public BigDecimal getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
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

	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}

	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
	}

	public String getPaidMethod() {
		return paidMethod;
	}

	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}

	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	public Integer getArriveGoodsQty() {
		return arriveGoodsQty;
	}

	public void setArriveGoodsQty(Integer arriveGoodsQty) {
		this.arriveGoodsQty = arriveGoodsQty;
	}

	public String getReceiveCustomerAddressSum() {
		return receiveCustomerAddressSum;
	}

	public void setReceiveCustomerAddressSum(String receiveCustomerAddressSum) {
		this.receiveCustomerAddressSum = receiveCustomerAddressSum;
	}

	public BigDecimal getTransportFee() {
		return transportFee;
	}

	public void setTransportFee(BigDecimal transportFee) {
		this.transportFee = transportFee;
	}

	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}
	
}
