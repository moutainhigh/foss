package com.deppon.esb.pojo.domain.oms2foss;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @discription Oms同步订单Request
 * @author 297064
 *
 */
public class AsynOmsOrderRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1497101444852581229L;
	
	/**
	 * OMS_ORDER_NO
	 */
    private String orderNo;
   
    /**
     * 订单类型
     */
    private String orderType;
  
    /**
     * 订单号
     */
    private String waybillNo;
    
    /**
     * 总重量
     */
    private BigDecimal goodsWeightTotal;
    
    /**
     * 总体积
     */
    private BigDecimal goodsVolumeTotal;
    
    /**
     * 发货客户省份编码
     */
    private String deliveryCustomerProvCode;
    
    /**
     * 发货客户城市编码
     */
    private String deliveryCustomerCityCode;
    
    /**
     * 发货客户区县编码
     */
    private String deliveryCustomerDistCode;

    /**
     * 发货客户乡镇编码
     */
    private String deliveryCustomerTownCode;

    /**
     * 发货客户详细地址
     */
    private String deliveryCustomerAddress;

    /**
     * 发货客户详细地址备注
     */
    private String deliveryCustomerAddressCmt;

    /**
     * 发货客户名字
     */
    private String deliveryCustomerName;

    /**
     * 发货客户编码
     */
    private String deliveryCustomerCode;

    /**
     * 发货客户联系人
     */
    private String deliveryCustomerContact;

    /**
     * 发货客户联系电话
     */
    private String deliveryCustomerPhone;

    /**
     * 发货客户联系手机
     */
    private String deliveryCustomerMobilephone;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品件数
     */
    private Integer goodsQtyTotal;

    /**
     * 商品类型编码
     */
    private String goodsTypeCode;

    /**
     * 其它包装
     */
    private String otherPackage;

    /**
     * 储运事项备注
     */
    private String otherComment;

    /**
     * 运输性质（产品编码）
     */
    private String productCode;

    /**
     * OMS订单下单时间
     */
    private Date orderTime;

    /**
     * 提货方式
     */
    private String receiveMethod;

    /**
     * 订单来源
     */
    private String orderChannel;

    /**
     * 渠道代号
     */
    private String channelCode;
    
    /**
     * 渠道号
     */
    private String channelNo;

    /**
     * 支付方式
     */
    private String paidMethod;

    /**
     * 代收货款类型
     */
    private String codType;

    /**
     * 代收货款金额
     */
    private BigDecimal codAmount;

    /**
     * 代收货款账户(返款账户)
     */
    private String accountCode;

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 账户银行
     */
    private String accountBank;

    /**
     * 保价声明价值
     */
    private BigDecimal insuranceAmount;

    /**
     * 货物尺寸
     */
    private String dimension;
    
    /**
     * 收货客户省份编码
     */
    private String receiveCustomerProvCode;

    /**
     * 收货客户城市编码
     */
    private String receiveCustomerCityCode;

    /**
     * 收货客户区县编码
     */
    private String receiveCustomerDictCode;

    /**
     * 收货客户乡镇编码（FOSS暂无）
     */
    private String receiveCustomerTownCode;

    /**
     * 收货客户详细地址
     */
    private String receiveCustomerAddress;

    /**
     * 收货客户地址详细地址备注
     */
    private String receiveCustomerAddressCmt;

    /**
     * 收货客户名字
     */
    private String receiveCustomerName;

    /**
     * 收货客户联系电话
     */
    private String receiveCustomerPhone;

    /**
     * 收货客户联系手机
     */
    private String receiveCustomerMobilephone;

    /**
     * 收货客户编码
     */
    private String receiveCustomerCode;
    
    /**
     * 收货客户联系人 
     */
    private String receiveCustomerContact;

    /**
     * 其它费用
     */
    private BigDecimal otherFee;
    
    /**
     * 订单受理名称
     */
    private String orderAcceptOrgName;
    
    /**
     * 订单受理部门编码
     */
    private String orderAcceptOrgCode;
    
    /**
     * 提货网点
     */
    private String customerPickupOrgCode;

    /**
     * 是否上面提货
     */
    private String pickupToDoor;

    /**
     * 是否免费接货
     */
    private String freePickupGoods;

    /**
     * 收入部门编码
     */
    private String incomeOrgCode;

    /**
     * 收入部门名称
     */
    private String incomeOrgName;

    /**
     * 是否是零担电子运单大客户
     */
    private String ltlewBigCustomer;

    /**
     * 是否是集中接货区域
     */
    private String pickupCentralized;

    /**
     * 推送标签信息的目标系统
     */
    private String targetSystem;

	/**
	 * 操作类型（下单和撤销）
	 */
    private String operationType;
    
    /**
     * 官网登录用户名
     */
    private String owsLoginName;
    
    /**
     * 联系人ID
     */
    private String linkmanId;
    
    /**
     * 是否超远派送
     */
    private String isMuchHigherDelivery;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
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

	public String getDeliveryCustomerProvCode() {
		return deliveryCustomerProvCode;
	}

	public void setDeliveryCustomerProvCode(String deliveryCustomerProvCode) {
		this.deliveryCustomerProvCode = deliveryCustomerProvCode;
	}

	public String getDeliveryCustomerCityCode() {
		return deliveryCustomerCityCode;
	}

	public void setDeliveryCustomerCityCode(String deliveryCustomerCityCode) {
		this.deliveryCustomerCityCode = deliveryCustomerCityCode;
	}

	public String getDeliveryCustomerDistCode() {
		return deliveryCustomerDistCode;
	}

	public void setDeliveryCustomerDistCode(String deliveryCustomerDistCode) {
		this.deliveryCustomerDistCode = deliveryCustomerDistCode;
	}

	public String getDeliveryCustomerTownCode() {
		return deliveryCustomerTownCode;
	}

	public void setDeliveryCustomerTownCode(String deliveryCustomerTownCode) {
		this.deliveryCustomerTownCode = deliveryCustomerTownCode;
	}

	public String getDeliveryCustomerAddress() {
		return deliveryCustomerAddress;
	}

	public void setDeliveryCustomerAddress(String deliveryCustomerAddress) {
		this.deliveryCustomerAddress = deliveryCustomerAddress;
	}

	public String getDeliveryCustomerAddressCmt() {
		return deliveryCustomerAddressCmt;
	}

	public void setDeliveryCustomerAddressCmt(String deliveryCustomerAddressCmt) {
		this.deliveryCustomerAddressCmt = deliveryCustomerAddressCmt;
	}

	public String getDeliveryCustomerName() {
		return deliveryCustomerName;
	}

	public void setDeliveryCustomerName(String deliveryCustomerName) {
		this.deliveryCustomerName = deliveryCustomerName;
	}

	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}

	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}

	public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}

	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
	}

	public String getDeliveryCustomerPhone() {
		return deliveryCustomerPhone;
	}

	public void setDeliveryCustomerPhone(String deliveryCustomerPhone) {
		this.deliveryCustomerPhone = deliveryCustomerPhone;
	}

	public String getDeliveryCustomerMobilephone() {
		return deliveryCustomerMobilephone;
	}

	public void setDeliveryCustomerMobilephone(String deliveryCustomerMobilephone) {
		this.deliveryCustomerMobilephone = deliveryCustomerMobilephone;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	public String getGoodsTypeCode() {
		return goodsTypeCode;
	}

	public void setGoodsTypeCode(String goodsTypeCode) {
		this.goodsTypeCode = goodsTypeCode;
	}

	public String getOtherPackage() {
		return otherPackage;
	}

	public void setOtherPackage(String otherPackage) {
		this.otherPackage = otherPackage;
	}

	public String getOtherComment() {
		return otherComment;
	}

	public void setOtherComment(String otherComment) {
		this.otherComment = otherComment;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	public String getOrderChannel() {
		return orderChannel;
	}

	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	public String getPaidMethod() {
		return paidMethod;
	}

	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}

	public String getCodType() {
		return codType;
	}

	public void setCodType(String codType) {
		this.codType = codType;
	}

	public BigDecimal getCodAmount() {
		return codAmount;
	}

	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountBank() {
		return accountBank;
	}

	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}

	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
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

	public String getReceiveCustomerDictCode() {
		return receiveCustomerDictCode;
	}

	public void setReceiveCustomerDictCode(String receiveCustomerDictCode) {
		this.receiveCustomerDictCode = receiveCustomerDictCode;
	}

	public String getReceiveCustomerTownCode() {
		return receiveCustomerTownCode;
	}

	public void setReceiveCustomerTownCode(String receiveCustomerTownCode) {
		this.receiveCustomerTownCode = receiveCustomerTownCode;
	}

	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}

	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}

	public String getReceiveCustomerAddressCmt() {
		return receiveCustomerAddressCmt;
	}

	public void setReceiveCustomerAddressCmt(String receiveCustomerAddressCmt) {
		this.receiveCustomerAddressCmt = receiveCustomerAddressCmt;
	}

	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}

	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}

	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}

	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}

	public String getReceiveCustomerCode() {
		return receiveCustomerCode;
	}

	public void setReceiveCustomerCode(String receiveCustomerCode) {
		this.receiveCustomerCode = receiveCustomerCode;
	}

	public BigDecimal getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}

	public String getOrderAcceptOrgName() {
		return orderAcceptOrgName;
	}

	public void setOrderAcceptOrgName(String orderAcceptOrgName) {
		this.orderAcceptOrgName = orderAcceptOrgName;
	}

	public String getOrderAcceptOrgCode() {
		return orderAcceptOrgCode;
	}

	public void setOrderAcceptOrgCode(String orderAcceptOrgCode) {
		this.orderAcceptOrgCode = orderAcceptOrgCode;
	}

	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}

	public String getPickupToDoor() {
		return pickupToDoor;
	}

	public void setPickupToDoor(String pickupToDoor) {
		this.pickupToDoor = pickupToDoor;
	}

	public String getFreePickupGoods() {
		return freePickupGoods;
	}

	public void setFreePickupGoods(String freePickupGoods) {
		this.freePickupGoods = freePickupGoods;
	}

	public String getIncomeOrgCode() {
		return incomeOrgCode;
	}

	public void setIncomeOrgCode(String incomeOrgCode) {
		this.incomeOrgCode = incomeOrgCode;
	}

	public String getIncomeOrgName() {
		return incomeOrgName;
	}

	public void setIncomeOrgName(String incomeOrgName) {
		this.incomeOrgName = incomeOrgName;
	}

	public String getLtlewBigCustomer() {
		return ltlewBigCustomer;
	}

	public void setLtlewBigCustomer(String ltlewBigCustomer) {
		this.ltlewBigCustomer = ltlewBigCustomer;
	}

	public String getPickupCentralized() {
		return pickupCentralized;
	}

	public void setPickupCentralized(String pickupCentralized) {
		this.pickupCentralized = pickupCentralized;
	}

	public String getTargetSystem() {
		return targetSystem;
	}

	public void setTargetSystem(String targetSystem) {
		this.targetSystem = targetSystem;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}

	public String getOwsLoginName() {
		return owsLoginName;
	}

	public void setOwsLoginName(String owsLoginName) {
		this.owsLoginName = owsLoginName;
	}

	public String getLinkmanId() {
		return linkmanId;
	}

	public void setLinkmanId(String linkmanId) {
		this.linkmanId = linkmanId;
	}

	public String getIsMuchHigherDelivery() {
		return isMuchHigherDelivery;
	}

	public void setIsMuchHigherDelivery(String isMuchHigherDelivery) {
		this.isMuchHigherDelivery = isMuchHigherDelivery;
	}
    
	
}
