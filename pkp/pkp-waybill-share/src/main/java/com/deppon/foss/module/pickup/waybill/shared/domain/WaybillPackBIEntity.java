package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 用于BI抽取打木架后数据
 * @author 043258-foss-zhaobin
 * @date 2014-7-9 下午5:02:31
 */
public class WaybillPackBIEntity extends BaseEntity{

	    private static final long serialVersionUID = 1L;

		/**
		 * 运单号
		 */
		private String waybillNo;

		/**
		 * 订单号
		 */
		private String orderNo;
		
		/**
		 * 订单来源
		 */
		private String orderChannel;
		
		/**
		 * 订单付款方式
		 */
		private String orderPaidMethod;

		/**
		 * 发货客户ID
		 */
		private String deliveryCustomerId;

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
		 * 发货客户联系人
		 */
		private String deliveryCustomerContact;

		/**
		 * 发货国家
		 */
		private String deliveryCustomerNationCode;

		/**
		 * 发货省份
		 */
		private String deliveryCustomerProvCode;

		/**
		 * 发货市
		 */
		private String deliveryCustomerCityCode;

		/**
		 * 发货区
		 */
		private String deliveryCustomerDistCode;

		/**
		 * 发货具体地址
		 */
		private String deliveryCustomerAddress;

		/**
		 * 收货客户ID
		 */
		private String receiveCustomerId;

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
		 * 收货客户联系人
		 */
		private String receiveCustomerContact;

		/**
		 * 收货国家
		 */
		private String receiveCustomerNationCode;

		/**
		 * 收货省份
		 */
		private String receiveCustomerProvCode;

		/**
		 * 收货市
		 */
		private String receiveCustomerCityCode;

		/**
		 * 收货区
		 */
		private String receiveCustomerDistCode;

		/**
		 * 收货具体地址
		 */
		private String receiveCustomerAddress;

		/**
		 * 收货部门(出发部门)
		 */
		private String receiveOrgCode;

		/**
		 * 产品ID
		 */
		private String productId;

		/**
		 * 运输性质
		 */
		private String productCode;

		/**
		 * 提货方式
		 */
		private String receiveMethod;

		/**
		 * 提货网点
		 */
		private String customerPickupOrgCode;

		/**
		 * 配载类型
		 */
		private String loadMethod;

		/**
		 * 目的站
		 */
		private String targetOrgCode;

		/**
		 * 是否上门接货
		 */
		private String pickupToDoor;

		/**
		 * 司机
		 */
		private String driverCode;

		/**
		 * 是否集中接货
		 */
		private String pickupCentralized;

		/**
		 * 配载线路
		 */
		private String loadLineCode;

		/**
		 * 配载部门
		 */
		private String loadOrgCode;

		/**
		 * 最终配载部门
		 */
		private String lastLoadOrgCode;

		/**
		 * 预计出发时间
		 */
		private Date preDepartureTime;

		/**
		 * 预计派送/提货时间
		 */
		private Date preCustomerPickupTime;

		/**
		 * 是否大车直送
		 */
		private String carDirectDelivery;

		/**
		 * 货物名称
		 */
		private String goodsName;

		/**
		 * 货物总件数
		 */
		private Integer goodsQtyTotal;

		/**
		 * 货物总重量
		 */
		private BigDecimal goodsWeightTotal;

		/**
		 * 货物总体积
		 */
		private BigDecimal goodsVolumeTotal;

		/**
		 * 货物尺寸
		 */
		private String goodsSize;

		/**
		 * 货物类型
		 */
		private String goodsTypeCode;

		/**
		 * 是否贵重物品
		 */
		private String preciousGoods;

		/**
		 * 是否异形物品
		 */
		private String specialShapedGoods;

		/**
		 * 对外备注
		 */
		private String outerNotes;

		/**
		 * 对内备注
		 */
		private String innerNotes;

		/**
		 * 货物包装
		 */
		private String goodsPackage;

		/**
		 * 保价声明价值
		 */
		private BigDecimal insuranceAmount;

		/**
		 * 保价费率
		 */
		private BigDecimal insuranceRate;

		/**
		 * 保价费
		 */
		private BigDecimal insuranceFee;

		/**
		 * 代收货款
		 */
		private BigDecimal codAmount;

		/**
		 * 代收费率
		 */
		private BigDecimal codRate;

		/**
		 * 代收货款手续费
		 */
		private BigDecimal codFee;

		/**
		 * 退款类型
		 */
		private String refundType;

		/**
		 * 返单类别
		 */
		private String returnBillType;

		/**
		 * 预付费保密
		 */
		private String secretPrepaid;

		/**
		 * 到付金额
		 */
		private BigDecimal toPayAmount;

		/**
		 * 预付金额
		 */
		private BigDecimal prePayAmount;

		/**
		 * 送货费
		 */
		private BigDecimal deliveryGoodsFee;

		/**
		 * 其他费用
		 */
		private BigDecimal otherFee;

		/**
		 * 包装手续费
		 */
		private BigDecimal packageFee;

		/**
		 * 优惠费用
		 */
		private BigDecimal promotionsFee;

		/**
		 * 运费计费类型
		 */
		private String billingType;

		/**
		 * 运费计费费率
		 */
		private BigDecimal unitPrice;

		/**
		 * 公布价运费
		 */
		private BigDecimal transportFee;

		/**
		 * 增值费用
		 */
		private BigDecimal valueAddFee;

		/**
		 * 开单付款方式
		 */
		private String paidMethod;

		/**
		 * 到达类型
		 */
		private String arriveType;

		/**
		 * 禁行
		 */
		private String forbiddenLine;

		/**
		 * 合票方式
		 */
		private String freightMethod;

		/**
		 * 航班时间
		 */
		private String flightShift;

		/**
		 * 总费用
		 */
		private BigDecimal totalFee;

		/**
		 * 优惠编码
		 */
		private String promotionsCode;

		/**
		 * 创建时间
		 */
		private Date createTime;

		/**
		 * 更新时间（业务时间）
		 */
		private Date modifyTime;

		/**
		 * 开单时间
		 */
		private Date billTime;

		/**
		 * 开单人
		 */
		private String createUserCode;

		/**
		 * 更新人
		 */
		private String modifyUserCode;

		/**
		 * 开单组织
		 */
		private String createOrgCode;
		
		/**
		 * 管理营业部出发运单，制单部门列表。当登录部门为集中开单区域营业部时，添加所属集中开单组作为制单部门查询条件
		 */
		private List<String> createOrgCodeList;
		
		/**
		 * 管理营业部出发运单，收货部门列表。当登录部门为集中开单区域营业部时，添加所有所属集中开单组的部门 作为收货部门查询条件
		 */
		private List<String> receiveOrgCodeList;

		/**
		 * 更新组织
		 */
		private String modifyOrgCode;

		/**
		 * 原运单ID
		 */
		private String refId;

		/**
		 * 原运单号
		 */
		private String refCode;

		/**
		 * 币种
		 */
		private String currencyCode;

		/**
		 * 是否整车运单
		 */
		private String isWholeVehicle;

		/**
		 * 整车约车报价
		 */
		private BigDecimal wholeVehicleAppfee;

		/**
		 * 整车开单报价
		 */
		private BigDecimal wholeVehicleActualfee;

		/**
		 * 返款帐户开户名称
		 */
		private String accountName;

		/**
		 * 返款帐户开户账户
		 */
		private String accountCode;

		/**
		 * 返款帐户开户银行
		 */
		private String accountBank;

		/**
		 * 计费重量
		 */
		private BigDecimal billWeight;

		/**
		 * 接货费
		 */
		private BigDecimal pickupFee;

		/**
		 * 装卸费
		 */
		private BigDecimal serviceFee;

		/**
		 * 预计到达时间
		 */
		private Date preArriveTime;

		/**
		 * 运输类型
		 */
		private String transportType;

		/**
		 * 打印次数
		 */
		private Integer printTimes;

		/**
		 * 新增时间
		 */
		private Date addTime;

		/**
		 * 联系人地址ID
		 */
		private String contactAddressId;

		/**
		 * 航班类型
		 */
		private String flightNumberType;

		/**
		 * 收款部门
		 */
		private String collectionDepartment;

		/**
		 * 储运事项
		 */
		private String transportationRemark;

		/**
		 * 运单状态
		 */
		private String active;

		/**
		 * 是否经过营业部
		 */
		private String isPassOwnDepartment;

		/**
		 * 其他包装
		 */
		private String otherPackage;

		/**
		 * 纸包装
		 */
		private Integer paperNum;

		/**
		 * 木包装
		 */
		private Integer woodNum;

		/**
		 * 纤包装
		 */
		private Integer fibreNum;

		/**
		 * 托包装
		 */
		private Integer salverNum;

		/**
		 * 膜包装
		 */
		private Integer membraneNum;

		/**
		 * 发货联系人Id
		 */
		private String deliverCustContactId;

		/**
		 * 收获联系人ID
		 */
		private String receiverCustContactId;

		/**
		 * 处理类型:"PDA_ACTIVE"--PDA已补录 ，"PC_ACTIVE"--暂存已开单
		 */
		private String pendingType;

		/**
		 * 车牌号
		 */
		private String licensePlateNum;

		/**
		 * 约车编号
		 */
		private String orderVehicleNum;
		
		/**
		 * 运单提交人姓名
		 */
		private String createUserName;
		
		/**
		 * 运单提交人所在部门名称
		 */
		private String createUserDeptName;
		
		/**
		 * 收货部门名称
		 */
		private String receiveOrgName;
		/**
		 * 提货网点名称
		 */
		private String customerPickupOrgName;
		
		/**
		 * 配载部门名称
		 */
		private String loadOrgName;
		
		/**
		 * 最终配置部门名称
		 */
		private String lastLoadOrgName;
		
		/**
		 * 中转 返货的时候的中转起点外场
		 */
		private String transferStartOrgCode;
		
		/**
		 * 中转 返货的时候的中转起点外场名称
		 */
		private String transferStartOrgName;
		/**
		 * 公里数
		 */
		private BigDecimal kilometer;
		
		/**
		 * 是否迁移数据
		 */
		private String isInit;
		
		/**
		 * 是否经济自提件
		 */
		private String isEconomyGoods;
		/**
		 * 经济自提件类型
		 */
		private String economyGoodsType;

	    private String deliveryBigCustomer;

	    private String receiveBigCustomer;

	    private String deliveryCustomerContactId;
	    
	    private String receiveCustomerContactId;
	    
	    private String isExpress;
	    
		public String getWaybillNo() {
			return waybillNo;
		}

		public void setWaybillNo(String waybillNo) {
			this.waybillNo = waybillNo;
		}

		public String getOrderNo() {
			return orderNo;
		}

		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}

		public String getOrderChannel() {
			return orderChannel;
		}

		public void setOrderChannel(String orderChannel) {
			this.orderChannel = orderChannel;
		}

		public String getOrderPaidMethod() {
			return orderPaidMethod;
		}

		public void setOrderPaidMethod(String orderPaidMethod) {
			this.orderPaidMethod = orderPaidMethod;
		}

		public String getDeliveryCustomerId() {
			return deliveryCustomerId;
		}

		public void setDeliveryCustomerId(String deliveryCustomerId) {
			this.deliveryCustomerId = deliveryCustomerId;
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

		public String getDeliveryCustomerContact() {
			return deliveryCustomerContact;
		}

		public void setDeliveryCustomerContact(String deliveryCustomerContact) {
			this.deliveryCustomerContact = deliveryCustomerContact;
		}

		public String getDeliveryCustomerNationCode() {
			return deliveryCustomerNationCode;
		}

		public void setDeliveryCustomerNationCode(String deliveryCustomerNationCode) {
			this.deliveryCustomerNationCode = deliveryCustomerNationCode;
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

		public String getDeliveryCustomerAddress() {
			return deliveryCustomerAddress;
		}

		public void setDeliveryCustomerAddress(String deliveryCustomerAddress) {
			this.deliveryCustomerAddress = deliveryCustomerAddress;
		}

		public String getReceiveCustomerId() {
			return receiveCustomerId;
		}

		public void setReceiveCustomerId(String receiveCustomerId) {
			this.receiveCustomerId = receiveCustomerId;
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

		public String getReceiveCustomerContact() {
			return receiveCustomerContact;
		}

		public void setReceiveCustomerContact(String receiveCustomerContact) {
			this.receiveCustomerContact = receiveCustomerContact;
		}

		public String getReceiveCustomerNationCode() {
			return receiveCustomerNationCode;
		}

		public void setReceiveCustomerNationCode(String receiveCustomerNationCode) {
			this.receiveCustomerNationCode = receiveCustomerNationCode;
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

		public String getReceiveCustomerAddress() {
			return receiveCustomerAddress;
		}

		public void setReceiveCustomerAddress(String receiveCustomerAddress) {
			this.receiveCustomerAddress = receiveCustomerAddress;
		}

		public String getReceiveOrgCode() {
			return receiveOrgCode;
		}

		public void setReceiveOrgCode(String receiveOrgCode) {
			this.receiveOrgCode = receiveOrgCode;
		}

		public String getProductId() {
			return productId;
		}

		public void setProductId(String productId) {
			this.productId = productId;
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

		public String getLoadMethod() {
			return loadMethod;
		}

		public void setLoadMethod(String loadMethod) {
			this.loadMethod = loadMethod;
		}

		public String getTargetOrgCode() {
			return targetOrgCode;
		}

		public void setTargetOrgCode(String targetOrgCode) {
			this.targetOrgCode = targetOrgCode;
		}

		public String getPickupToDoor() {
			return pickupToDoor;
		}

		public void setPickupToDoor(String pickupToDoor) {
			this.pickupToDoor = pickupToDoor;
		}

		public String getDriverCode() {
			return driverCode;
		}

		public void setDriverCode(String driverCode) {
			this.driverCode = driverCode;
		}

		public String getPickupCentralized() {
			return pickupCentralized;
		}

		public void setPickupCentralized(String pickupCentralized) {
			this.pickupCentralized = pickupCentralized;
		}

		public String getLoadLineCode() {
			return loadLineCode;
		}

		public void setLoadLineCode(String loadLineCode) {
			this.loadLineCode = loadLineCode;
		}

		public String getLoadOrgCode() {
			return loadOrgCode;
		}

		public void setLoadOrgCode(String loadOrgCode) {
			this.loadOrgCode = loadOrgCode;
		}

		public String getLastLoadOrgCode() {
			return lastLoadOrgCode;
		}

		public void setLastLoadOrgCode(String lastLoadOrgCode) {
			this.lastLoadOrgCode = lastLoadOrgCode;
		}

		public Date getPreDepartureTime() {
			return preDepartureTime;
		}

		public void setPreDepartureTime(Date preDepartureTime) {
			this.preDepartureTime = preDepartureTime;
		}

		public Date getPreCustomerPickupTime() {
			return preCustomerPickupTime;
		}

		public void setPreCustomerPickupTime(Date preCustomerPickupTime) {
			this.preCustomerPickupTime = preCustomerPickupTime;
		}

		public String getCarDirectDelivery() {
			return carDirectDelivery;
		}

		public void setCarDirectDelivery(String carDirectDelivery) {
			this.carDirectDelivery = carDirectDelivery;
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

		public String getGoodsSize() {
			return goodsSize;
		}

		public void setGoodsSize(String goodsSize) {
			this.goodsSize = goodsSize;
		}

		public String getGoodsTypeCode() {
			return goodsTypeCode;
		}

		public void setGoodsTypeCode(String goodsTypeCode) {
			this.goodsTypeCode = goodsTypeCode;
		}

		public String getPreciousGoods() {
			return preciousGoods;
		}

		public void setPreciousGoods(String preciousGoods) {
			this.preciousGoods = preciousGoods;
		}

		public String getSpecialShapedGoods() {
			return specialShapedGoods;
		}

		public void setSpecialShapedGoods(String specialShapedGoods) {
			this.specialShapedGoods = specialShapedGoods;
		}

		public String getOuterNotes() {
			return outerNotes;
		}

		public void setOuterNotes(String outerNotes) {
			this.outerNotes = outerNotes;
		}

		public String getInnerNotes() {
			return innerNotes;
		}

		public void setInnerNotes(String innerNotes) {
			this.innerNotes = innerNotes;
		}

		public String getGoodsPackage() {
			return goodsPackage;
		}

		public void setGoodsPackage(String goodsPackage) {
			this.goodsPackage = goodsPackage;
		}

		public BigDecimal getInsuranceAmount() {
			return insuranceAmount;
		}

		public void setInsuranceAmount(BigDecimal insuranceAmount) {
			this.insuranceAmount = insuranceAmount;
		}

		public BigDecimal getInsuranceRate() {
			return insuranceRate;
		}

		public void setInsuranceRate(BigDecimal insuranceRate) {
			this.insuranceRate = insuranceRate;
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

		public BigDecimal getCodRate() {
			return codRate;
		}

		public void setCodRate(BigDecimal codRate) {
			this.codRate = codRate;
		}

		public BigDecimal getCodFee() {
			return codFee;
		}

		public void setCodFee(BigDecimal codFee) {
			this.codFee = codFee;
		}

		public String getRefundType() {
			return refundType;
		}

		public void setRefundType(String refundType) {
			this.refundType = refundType;
		}

		public String getReturnBillType() {
			return returnBillType;
		}

		public void setReturnBillType(String returnBillType) {
			this.returnBillType = returnBillType;
		}

		public String getSecretPrepaid() {
			return secretPrepaid;
		}

		public void setSecretPrepaid(String secretPrepaid) {
			this.secretPrepaid = secretPrepaid;
		}

		public BigDecimal getToPayAmount() {
			return toPayAmount;
		}

		public void setToPayAmount(BigDecimal toPayAmount) {
			this.toPayAmount = toPayAmount;
		}

		public BigDecimal getPrePayAmount() {
			return prePayAmount;
		}

		public void setPrePayAmount(BigDecimal prePayAmount) {
			this.prePayAmount = prePayAmount;
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

		public BigDecimal getPackageFee() {
			return packageFee;
		}

		public void setPackageFee(BigDecimal packageFee) {
			this.packageFee = packageFee;
		}

		public BigDecimal getPromotionsFee() {
			return promotionsFee;
		}

		public void setPromotionsFee(BigDecimal promotionsFee) {
			this.promotionsFee = promotionsFee;
		}

		public String getBillingType() {
			return billingType;
		}

		public void setBillingType(String billingType) {
			this.billingType = billingType;
		}

		public BigDecimal getUnitPrice() {
			return unitPrice;
		}

		public void setUnitPrice(BigDecimal unitPrice) {
			this.unitPrice = unitPrice;
		}

		public BigDecimal getTransportFee() {
			return transportFee;
		}

		public void setTransportFee(BigDecimal transportFee) {
			this.transportFee = transportFee;
		}

		public BigDecimal getValueAddFee() {
			return valueAddFee;
		}

		public void setValueAddFee(BigDecimal valueAddFee) {
			this.valueAddFee = valueAddFee;
		}

		public String getPaidMethod() {
			return paidMethod;
		}

		public void setPaidMethod(String paidMethod) {
			this.paidMethod = paidMethod;
		}

		public String getArriveType() {
			return arriveType;
		}

		public void setArriveType(String arriveType) {
			this.arriveType = arriveType;
		}

		public String getForbiddenLine() {
			return forbiddenLine;
		}

		public void setForbiddenLine(String forbiddenLine) {
			this.forbiddenLine = forbiddenLine;
		}

		public String getFreightMethod() {
			return freightMethod;
		}

		public void setFreightMethod(String freightMethod) {
			this.freightMethod = freightMethod;
		}

		public String getFlightShift() {
			return flightShift;
		}

		public void setFlightShift(String flightShift) {
			this.flightShift = flightShift;
		}

		public BigDecimal getTotalFee() {
			return totalFee;
		}

		public void setTotalFee(BigDecimal totalFee) {
			this.totalFee = totalFee;
		}

		public String getPromotionsCode() {
			return promotionsCode;
		}

		public void setPromotionsCode(String promotionsCode) {
			this.promotionsCode = promotionsCode;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

		public Date getModifyTime() {
			return modifyTime;
		}

		public void setModifyTime(Date modifyTime) {
			this.modifyTime = modifyTime;
		}

		public Date getBillTime() {
			return billTime;
		}

		public void setBillTime(Date billTime) {
			this.billTime = billTime;
		}

		public String getCreateUserCode() {
			return createUserCode;
		}

		public void setCreateUserCode(String createUserCode) {
			this.createUserCode = createUserCode;
		}

		public String getModifyUserCode() {
			return modifyUserCode;
		}

		public void setModifyUserCode(String modifyUserCode) {
			this.modifyUserCode = modifyUserCode;
		}

		public String getCreateOrgCode() {
			return createOrgCode;
		}

		public void setCreateOrgCode(String createOrgCode) {
			this.createOrgCode = createOrgCode;
		}

		public List<String> getCreateOrgCodeList() {
			return createOrgCodeList;
		}

		public void setCreateOrgCodeList(List<String> createOrgCodeList) {
			this.createOrgCodeList = createOrgCodeList;
		}

		public List<String> getReceiveOrgCodeList() {
			return receiveOrgCodeList;
		}

		public void setReceiveOrgCodeList(List<String> receiveOrgCodeList) {
			this.receiveOrgCodeList = receiveOrgCodeList;
		}

		public String getModifyOrgCode() {
			return modifyOrgCode;
		}

		public void setModifyOrgCode(String modifyOrgCode) {
			this.modifyOrgCode = modifyOrgCode;
		}

		public String getRefId() {
			return refId;
		}

		public void setRefId(String refId) {
			this.refId = refId;
		}

		public String getRefCode() {
			return refCode;
		}

		public void setRefCode(String refCode) {
			this.refCode = refCode;
		}

		public String getCurrencyCode() {
			return currencyCode;
		}

		public void setCurrencyCode(String currencyCode) {
			this.currencyCode = currencyCode;
		}

		public String getIsWholeVehicle() {
			return isWholeVehicle;
		}

		public void setIsWholeVehicle(String isWholeVehicle) {
			this.isWholeVehicle = isWholeVehicle;
		}

		public BigDecimal getWholeVehicleAppfee() {
			return wholeVehicleAppfee;
		}

		public void setWholeVehicleAppfee(BigDecimal wholeVehicleAppfee) {
			this.wholeVehicleAppfee = wholeVehicleAppfee;
		}

		public BigDecimal getWholeVehicleActualfee() {
			return wholeVehicleActualfee;
		}

		public void setWholeVehicleActualfee(BigDecimal wholeVehicleActualfee) {
			this.wholeVehicleActualfee = wholeVehicleActualfee;
		}

		public String getAccountName() {
			return accountName;
		}

		public void setAccountName(String accountName) {
			this.accountName = accountName;
		}

		public String getAccountCode() {
			return accountCode;
		}

		public void setAccountCode(String accountCode) {
			this.accountCode = accountCode;
		}

		public String getAccountBank() {
			return accountBank;
		}

		public void setAccountBank(String accountBank) {
			this.accountBank = accountBank;
		}

		public BigDecimal getBillWeight() {
			return billWeight;
		}

		public void setBillWeight(BigDecimal billWeight) {
			this.billWeight = billWeight;
		}

		public BigDecimal getPickupFee() {
			return pickupFee;
		}

		public void setPickupFee(BigDecimal pickupFee) {
			this.pickupFee = pickupFee;
		}

		public BigDecimal getServiceFee() {
			return serviceFee;
		}

		public void setServiceFee(BigDecimal serviceFee) {
			this.serviceFee = serviceFee;
		}

		public Date getPreArriveTime() {
			return preArriveTime;
		}

		public void setPreArriveTime(Date preArriveTime) {
			this.preArriveTime = preArriveTime;
		}

		public String getTransportType() {
			return transportType;
		}

		public void setTransportType(String transportType) {
			this.transportType = transportType;
		}

		public Integer getPrintTimes() {
			return printTimes;
		}

		public void setPrintTimes(Integer printTimes) {
			this.printTimes = printTimes;
		}

		public Date getAddTime() {
			return addTime;
		}

		public void setAddTime(Date addTime) {
			this.addTime = addTime;
		}

		public String getContactAddressId() {
			return contactAddressId;
		}

		public void setContactAddressId(String contactAddressId) {
			this.contactAddressId = contactAddressId;
		}

		public String getFlightNumberType() {
			return flightNumberType;
		}

		public void setFlightNumberType(String flightNumberType) {
			this.flightNumberType = flightNumberType;
		}

		public String getCollectionDepartment() {
			return collectionDepartment;
		}

		public void setCollectionDepartment(String collectionDepartment) {
			this.collectionDepartment = collectionDepartment;
		}

		public String getTransportationRemark() {
			return transportationRemark;
		}

		public void setTransportationRemark(String transportationRemark) {
			this.transportationRemark = transportationRemark;
		}

		public String getActive() {
			return active;
		}

		public void setActive(String active) {
			this.active = active;
		}

		public String getIsPassOwnDepartment() {
			return isPassOwnDepartment;
		}

		public void setIsPassOwnDepartment(String isPassOwnDepartment) {
			this.isPassOwnDepartment = isPassOwnDepartment;
		}

		public String getOtherPackage() {
			return otherPackage;
		}

		public void setOtherPackage(String otherPackage) {
			this.otherPackage = otherPackage;
		}

		public Integer getPaperNum() {
			return paperNum;
		}

		public void setPaperNum(Integer paperNum) {
			this.paperNum = paperNum;
		}

		public Integer getWoodNum() {
			return woodNum;
		}

		public void setWoodNum(Integer woodNum) {
			this.woodNum = woodNum;
		}

		public Integer getFibreNum() {
			return fibreNum;
		}

		public void setFibreNum(Integer fibreNum) {
			this.fibreNum = fibreNum;
		}

		public Integer getSalverNum() {
			return salverNum;
		}

		public void setSalverNum(Integer salverNum) {
			this.salverNum = salverNum;
		}

		public Integer getMembraneNum() {
			return membraneNum;
		}

		public void setMembraneNum(Integer membraneNum) {
			this.membraneNum = membraneNum;
		}

		public String getDeliverCustContactId() {
			return deliverCustContactId;
		}

		public void setDeliverCustContactId(String deliverCustContactId) {
			this.deliverCustContactId = deliverCustContactId;
		}

		public String getReceiverCustContactId() {
			return receiverCustContactId;
		}

		public void setReceiverCustContactId(String receiverCustContactId) {
			this.receiverCustContactId = receiverCustContactId;
		}

		public String getPendingType() {
			return pendingType;
		}

		public void setPendingType(String pendingType) {
			this.pendingType = pendingType;
		}

		public String getLicensePlateNum() {
			return licensePlateNum;
		}

		public void setLicensePlateNum(String licensePlateNum) {
			this.licensePlateNum = licensePlateNum;
		}

		public String getOrderVehicleNum() {
			return orderVehicleNum;
		}

		public void setOrderVehicleNum(String orderVehicleNum) {
			this.orderVehicleNum = orderVehicleNum;
		}

		public String getCreateUserName() {
			return createUserName;
		}

		public void setCreateUserName(String createUserName) {
			this.createUserName = createUserName;
		}

		public String getCreateUserDeptName() {
			return createUserDeptName;
		}

		public void setCreateUserDeptName(String createUserDeptName) {
			this.createUserDeptName = createUserDeptName;
		}

		public String getReceiveOrgName() {
			return receiveOrgName;
		}

		public void setReceiveOrgName(String receiveOrgName) {
			this.receiveOrgName = receiveOrgName;
		}

		public String getCustomerPickupOrgName() {
			return customerPickupOrgName;
		}

		public void setCustomerPickupOrgName(String customerPickupOrgName) {
			this.customerPickupOrgName = customerPickupOrgName;
		}

		public String getLoadOrgName() {
			return loadOrgName;
		}

		public void setLoadOrgName(String loadOrgName) {
			this.loadOrgName = loadOrgName;
		}

		public String getLastLoadOrgName() {
			return lastLoadOrgName;
		}

		public void setLastLoadOrgName(String lastLoadOrgName) {
			this.lastLoadOrgName = lastLoadOrgName;
		}

		public String getTransferStartOrgCode() {
			return transferStartOrgCode;
		}

		public void setTransferStartOrgCode(String transferStartOrgCode) {
			this.transferStartOrgCode = transferStartOrgCode;
		}

		public String getTransferStartOrgName() {
			return transferStartOrgName;
		}

		public void setTransferStartOrgName(String transferStartOrgName) {
			this.transferStartOrgName = transferStartOrgName;
		}

		public BigDecimal getKilometer() {
			return kilometer;
		}

		public void setKilometer(BigDecimal kilometer) {
			this.kilometer = kilometer;
		}

		public String getIsInit() {
			return isInit;
		}

		public void setIsInit(String isInit) {
			this.isInit = isInit;
		}

		public String getIsEconomyGoods() {
			return isEconomyGoods;
		}

		public void setIsEconomyGoods(String isEconomyGoods) {
			this.isEconomyGoods = isEconomyGoods;
		}

		public String getEconomyGoodsType() {
			return economyGoodsType;
		}

		public void setEconomyGoodsType(String economyGoodsType) {
			this.economyGoodsType = economyGoodsType;
		}

		public String getDeliveryBigCustomer() {
			return deliveryBigCustomer;
		}

		public void setDeliveryBigCustomer(String deliveryBigCustomer) {
			this.deliveryBigCustomer = deliveryBigCustomer;
		}

		public String getReceiveBigCustomer() {
			return receiveBigCustomer;
		}

		public void setReceiveBigCustomer(String receiveBigCustomer) {
			this.receiveBigCustomer = receiveBigCustomer;
		}

		public String getDeliveryCustomerContactId() {
			return deliveryCustomerContactId;
		}

		public void setDeliveryCustomerContactId(String deliveryCustomerContactId) {
			this.deliveryCustomerContactId = deliveryCustomerContactId;
		}

		public String getReceiveCustomerContactId() {
			return receiveCustomerContactId;
		}

		public void setReceiveCustomerContactId(String receiveCustomerContactId) {
			this.receiveCustomerContactId = receiveCustomerContactId;
		}

		public String getIsExpress() {
			return isExpress;
		}

		public void setIsExpress(String isExpress) {
			this.isExpress = isExpress;
		}
		
}
