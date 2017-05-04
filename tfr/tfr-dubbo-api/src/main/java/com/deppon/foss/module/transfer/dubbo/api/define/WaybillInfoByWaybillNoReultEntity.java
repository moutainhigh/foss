package com.deppon.foss.module.transfer.dubbo.api.define;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 根据运单号查询运单详情:1、收货部门： 运单开单部门名称 2、收货部门电话： 运单开单部门电话 3、运输性质： 运单开单产品 4、配载类型： 运单配载类型
 * 5、配载部门： 运单始发配载部门 6、预配线路/航班： 如果是汽运单，则返回预配线路，如果是空运单则返回预配航班 7、提货方式： 运单开单提货方式
 * 8、填开人： 运单开单人姓名 9、目的站： 运单开单目的站 10、提货网点： 运单开单到达部门名称 11、提货网点地址： 运单开单到达部门地址
 * 12、提货网点电话： 运单开单到达部门电话 13、发货客户名称： 发货客户名称 14、发货客户编码： 发货客户编码 15、发货客户手机号： 发货客户手机号
 * 16、发货客户电话： 发货客户电话号码 17、发货联系人： 发货联系人姓名 18、发货人地址： 发货客户地址 19、收货客户名称： 发货客户名称
 * 20、收货客户编码： 发货客户编码 21、收货客户手机号： 发货客户手机号 22、收货客户电话： 发货客户电话号码 23、收货联系人： 发货联系人姓名
 * 24、收货人地址： 发货客户地址 25、货物名称： 26、货物类型：A货，B货 27、包装： 28、运单总件数： 29、运单总重量： 30、运单总体积：
 * 31、运单尺寸： 32、对内备注： 33、对外备注： 34、储运事项 35、计费类型 36、运价 37、运费 38、付款方式 39、预付金额
 * 40、到付金额 41、返单类别 42、保险价值 43、保价费 44、代收货款 45、代收货款费率 46、退款类型 47、接货费 48、送货费 49、包装费
 * 50、装卸费 51、其他费用 52、是否集中接货 Y/N 53、是否大车直送 Y/N 54、是否整车到达客户处 Y/N 55、是否整车到达营业部 Y/N
 * 56、是否贵重物品 Y/N 57、是否特殊物品 Y/N 58、收货部门所属城市：始发站 59、预计到达时间 60、航班类型 61、航班时间 62、运单状态63、预付费保密
 * 64、是否展会货
 * @author 026113-foss-linwensheng
 * 
 */
public class WaybillInfoByWaybillNoReultEntity {
	
	/**
	 * 始发部门(集中开单的始发部门是当前部门所属外场的驻地营业部)
	 */
	private String startOrgCode;
	public String getStartOrgCode() {
		return startOrgCode;
	}
	public void setStartOrgCode(String startOrgCode) {
		this.startOrgCode = startOrgCode;
	}

	/**
	 * 收货部门： 运单开单部门编码
	 */
	private String receiveOrgCode;
	
	/**
	 * 收货部门： 运单开单部门名称
	 */
	private String receiveOrgName;
	
	/**
	 * 收货部门电话： 运单开单部门电话
	 */
	private String receiveOrgPhone;
	
	/**
	 * 运输性质： 运单开单产品编码
	 */
	private String productCode;
	
	/**
	 * 运输性质： 运单开单产品名称
	 */
	private String productName;
	
	/**
	 *预配线路/航班： 如果是汽运单，则返回预配线路，如果是空运单则返回预配航班
	 */
	private String loadMethod;
	
	/**
	 * 配载部门
	 */
	private String loadOrgCode;
	
	/**
	 * 配载部门名称
	 */
	private String loadOrgName;
	
	/**
	 * 配载线路
	 */
	private String loadLineCode;
	
	/**
	 * 提货方式
	 */
	private String receiveMethod;

	/**
	 * 开单人编码
	 */
	private String createUserCode;
	
	/**
	 * 开单人姓名
	 */
	private String createUserName;
	
	/**
	 * 目的站
	 */
	private String targetOrgCode;
	
	/**
	 * 提货网点编码
	 */
	private String customerPickupOrgCode;
	
	/**
	 * 提货网点名称
	 */
	private String customerPickupOrgName;
	
	/**
	 * 提货网点地址
	 */
	private String customerPickupOrgAddress;
	
	
	/**
	 * 提货网点电话： 运单开单到达部门电话
	 */
	private String customerPickupOrgPhone;

	/**
	 * 发货客户名称： 发货客户名称
	 */
	private String deliveryCustomerName;
	
	
	
	/**
	 * 发货客户编码
	 */
	private String deliveryCustomerCode;
	
	
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
	 * 发货具体地址
	 */
	private String deliveryCustomerAddress;
	
	/**
	 * 发货具体地址
	 */
	private String deliveryCustomerAddressNote;
	

	/**
	 * 收货客户名称
	 */
	private String receiveCustomerName;
	

	/**
	 * 收货客户编码
	 */
	private String receiveCustomerCode;
	
	
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
	 * 收货具体地址
	 */
	private String receiveCustomerAddress;
	
	/**
	 * 收货具体地址
	 */
	private String receiveCustomerAddressNote;
	
	
	/**
	 * 货物名称
	 */
	private String goodsName;
	
	/**
	 * DMANA-8928 FOSS开单品名自动匹配货源品类需求
	 * 货源品类
	 * @author Foss-206860
	 */
	private String category;
	
	/**
	 * 货物类型
	 */
	private String goodsTypeCode;
	
	
	
	/**
	 * 货物包装
	 */
	private String goodsPackage;
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
	 * 子母件总重量
	 */
	private BigDecimal czmGoodsWeightTotal;
	
	/**
	 * 子母件总体积
	 */
	private BigDecimal czmGoodsVolumeTotal;
	
	/**
	 * 货物尺寸
	 */
	private String goodsSize;
	
	/**
	 * 对外备注
	 */
	private String outerNotes;

	/**
	 * 对内备注
	 */
	private String innerNotes;
	/**
	 * 储运事项
	 */
	private String transportationRemark;
	
	/**
	 * 运费计费类型
	 */
	private String billingType;
	
	/**
	 * 公布价运费
	 */
	private BigDecimal transportFee;
	/**
	 * 运费
	 */
	private BigDecimal totalFee;
	
	
	/**
	 * 开单付款方式
	 */
	private String paidMethod;
	

	/**
	 * 预付金额
	 */
	private BigDecimal prePayAmount;

	/**
	 * 到付金额
	 */
	private BigDecimal toPayAmount;
	
	/**
	 * 返单类型
	 */
	private String returnBillType;
	
	
	/**
	 * 保价声明价值
	 */
	private BigDecimal insuranceAmount;
	

	/**
	 * 代收货款
	 */
	private BigDecimal codAmount;
	
	/**
	 * 代收货款费率
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
	 * 送货费
	 */
	private BigDecimal deliveryGoodsFee;
	
	/**
	 * 包装手续费
	 */
	private BigDecimal packageFee;
	
	
	/**
	 * 装卸费
	 */
	private BigDecimal serviceFee;
	
	
	
	/**
	 * 其他费用
	 */
	private BigDecimal otherFee;
	
	
	/**
	 * 是否集中接货
	 */
	private String pickupCentralized;
	
	
	/**
	 * 是否展会货
	 * @date 2015-06-25 配合综合查询显示展会货的需求
	 */
	private String isExhibitCargo;
	/**
	 * 订单来源
	 */
	private String orderChannel;
	
	/**
	 * 开单快递员 
	 */
	private String billingcourier;

	/**
	 * 签收快递员
	 */
	private String signcourier;
	
	/**
	 * 返签收单号
	 * @return
	 */
	private String returnSignWaybillNo;
	
	/**
	 * 返签收单原单号
	 * @return
	 */
	private String originalSignWaybillNo;
	
	/**
	 * 客户分群
	 */
	private String flabelleavemonth;
	
	public String getFlabelleavemonth() {
		return flabelleavemonth;
	}
	public void setFlabelleavemonth(String flabelleavemonth) {
		this.flabelleavemonth = flabelleavemonth;
	}
	public String getReturnSignWaybillNo() {
		return returnSignWaybillNo;
	}
	public void setReturnSignWaybillNo(String returnSignWaybillNo) {
		this.returnSignWaybillNo = returnSignWaybillNo;
	}
	public String getOriginalSignWaybillNo() {
		return originalSignWaybillNo;
	}
	public void setOriginalSignWaybillNo(String originalSignWaybillNo) {
		this.originalSignWaybillNo = originalSignWaybillNo;
	}
	public String getBillingcourier() {
		return billingcourier;
	}
	
	public void setBillingcourier(String billingcourier) {
		this.billingcourier = billingcourier;
	}
	
	public String getSigncourier() {
		return signcourier;
	}
	
	public void setSigncourier(String signcourier) {
		this.signcourier = signcourier;
	}
	
	/**
	 * 是否大车直送
	 */
	private String carDirectDelivery;
	
	/**
	 * 是否整车运单
	 */
	private String isWholeVehicle;
	
	
	/**
	 * 是否经过客户处
	 */
	private String isNotPassOwnDepartment;
	
	/**
	 * 是否经过营业部
	 */
	private String isPassOwnDepartment;
	
	
	/**
	 * 是否贵重物品
	 */
	private String preciousGoods;

	/**
	 * 是否异形物品
	 */
	private String specialShapedGoods;
	/**
	 * 接货费
	 */
	private BigDecimal pickUpFee;
	/**
	 * 保价费
	 */
	private BigDecimal insuranceFee;
	/**
	 * 开单时间
	 */
	private Date billTime;
	/**
	 * 收货部门所属城市：始发站
	 */
	private String receiveOrgCity;
	/**
	 * 预计到达时间
	 */
	private Date preCustomerPickupTime;
	/**
	 * 航班时间
	 */
	private Date flightShift;
	/**
	 * 航班类型
	 */
	private String flightNumberType;
	/**
	 * 运单状态
	 */
	private String waybillStatus;
	/**
	 * 预付费保密
	 */
	private String secretPrepaid;
	/**
	 * 合票方式
	 */
	private String freightMethod;
	/**
	 * 收货客户是否大客户：Y,是;N,不是;空,未标记
	 */
	private String receiveBigCustomer;//
	/**
	 * 发货客户是否大客户：Y,是;N,不是;空,未标记
	 */
	private String  deliveryBigCustomer;//
	
	/**
	 * 约车编号
	 */
	private String orderVehicleNum;
	
	/**
	 * 运单类型：EWAYBILL为电子运单，空为传统运单
	 */
	private String waybillType;
	
	/**
	 * 返货单号
	 */
	private String returnWaybillNo;
	
	/**
	 * 原单单号
	 */
	private String originalWaybillNo;
	
	
	 /**
	 * 到付折扣金额
	 */
	private BigDecimal toPayAmountDiscount;

	/**
	 * 预付折扣金额
	 */
	private BigDecimal prePayAmountDiscount;
	
	//注意一下两个(原单号总金额,原单号和返单号的总金额)字段，当查询的是返单号是才有用.....
	/**
	 * 原单号总金额
	 */
	private BigDecimal originalWaybillNoTotalFee;
	
	/**
	 * 原单号和返单号的总金额
	 */
	private BigDecimal oriAndReturntotalFee;

	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getOrderVehicleNum() {
		return orderVehicleNum;
	}
	public void setOrderVehicleNum(String orderVehicleNum) {
		this.orderVehicleNum = orderVehicleNum;
	}
	public String getFreightMethod() {
		return freightMethod;
	}

	public void setFreightMethod(String freightMethod) {
		this.freightMethod = freightMethod;
	}

	/**
	 * @return billTime : set the property billTime.
	 */
	public Date getBillTime() {
		return billTime;
	}

	/**
	 * @param billTime : return the property billTime.
	 */
	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	/**
	 * @return insuranceFee : set the property insuranceFee.
	 */
	public BigDecimal getInsuranceFee() {
		return insuranceFee;
	}

	/**
	 * @param insuranceFee : return the property insuranceFee.
	 */
	public void setInsuranceFee(BigDecimal insuranceFee) {
		this.insuranceFee = insuranceFee;
	}

	/**
	 * @return pickUpFee : set the property pickUpFee.
	 */
	public BigDecimal getPickUpFee() {
		return pickUpFee;
	}

	/**
	 * @param pickUpFee : return the property pickUpFee.
	 */
	public void setPickUpFee(BigDecimal pickUpFee) {
		this.pickUpFee = pickUpFee;
	}

	

	/**
	 * @return receiveOrgCode : set the property receiveOrgCode.
	 */
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	/**
	 * @param receiveOrgCode : return the property receiveOrgCode.
	 */
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	/**
	 * Gets the receive org phone.
	 *
	 * @return the receive org phone
	 */
	public String getReceiveOrgPhone() {
		return receiveOrgPhone;
	}

	/**
	 * Sets the receive org phone.
	 *
	 * @param receiveOrgPhone the new receive org phone
	 */
	public void setReceiveOrgPhone(String receiveOrgPhone) {
		this.receiveOrgPhone = receiveOrgPhone;
	}

	/**
	 * Gets the product code.
	 *
	 * @return the product code
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * Sets the product code.
	 *
	 * @param productCode the new product code
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * Gets the load method.
	 *
	 * @return the load method
	 */
	public String getLoadMethod() {
		return loadMethod;
	}

	/**
	 * Sets the load method.
	 *
	 * @param loadMethod the new load method
	 */
	public void setLoadMethod(String loadMethod) {
		this.loadMethod = loadMethod;
	}

	/**
	 * Gets the load org code.
	 *
	 * @return the load org code
	 */
	public String getLoadOrgCode() {
		return loadOrgCode;
	}

	/**
	 * Sets the load org code.
	 *
	 * @param loadOrgCode the new load org code
	 */
	public void setLoadOrgCode(String loadOrgCode) {
		this.loadOrgCode = loadOrgCode;
	}

	/**
	 * Gets the load line code.
	 *
	 * @return the load line code
	 */
	public String getLoadLineCode() {
		return loadLineCode;
	}

	/**
	 * Sets the load line code.
	 *
	 * @param loadLineCode the new load line code
	 */
	public void setLoadLineCode(String loadLineCode) {
		this.loadLineCode = loadLineCode;
	}

	/**
	 * Gets the receive method.
	 *
	 * @return the receive method
	 */
	public String getReceiveMethod() {
		return receiveMethod;
	}

	/**
	 * Sets the receive method.
	 *
	 * @param receiveMethod the new receive method
	 */
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	/**
	 * Gets the creates the user code.
	 *
	 * @return the creates the user code
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * Sets the creates the user code.
	 *
	 * @param createUserCode the new creates the user code
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * Gets the target org code.
	 *
	 * @return the target org code
	 */
	public String getTargetOrgCode() {
		return targetOrgCode;
	}

	/**
	 * Sets the target org code.
	 *
	 * @param targetOrgCode the new target org code
	 */
	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}

	/**
	 * Gets the customer pickup org name.
	 *
	 * @return the customer pickup org name
	 */
	public String getCustomerPickupOrgName() {
		return customerPickupOrgName;
	}

	/**
	 * Sets the customer pickup org name.
	 *
	 * @param customerPickupOrgName the new customer pickup org name
	 */
	public void setCustomerPickupOrgName(String customerPickupOrgName) {
		this.customerPickupOrgName = customerPickupOrgName;
	}

	/**
	 * Gets the customer pickup org address.
	 *
	 * @return the customer pickup org address
	 */
	public String getCustomerPickupOrgAddress() {
		return customerPickupOrgAddress;
	}

	/**
	 * Sets the customer pickup org address.
	 *
	 * @param customerPickupOrgAddress the new customer pickup org address
	 */
	public void setCustomerPickupOrgAddress(String customerPickupOrgAddress) {
		this.customerPickupOrgAddress = customerPickupOrgAddress;
	}

	/**
	 * Gets the customer pickup org phone.
	 *
	 * @return the customer pickup org phone
	 */
	public String getCustomerPickupOrgPhone() {
		return customerPickupOrgPhone;
	}

	/**
	 * Sets the customer pickup org phone.
	 *
	 * @param customerPickupOrgPhone the new customer pickup org phone
	 */
	public void setCustomerPickupOrgPhone(String customerPickupOrgPhone) {
		this.customerPickupOrgPhone = customerPickupOrgPhone;
	}

	/**
	 * Gets the delivery customer name.
	 *
	 * @return the delivery customer name
	 */
	public String getDeliveryCustomerName() {
		return deliveryCustomerName;
	}

	/**
	 * Sets the delivery customer name.
	 *
	 * @param deliveryCustomerName the new delivery customer name
	 */
	public void setDeliveryCustomerName(String deliveryCustomerName) {
		this.deliveryCustomerName = deliveryCustomerName;
	}

	/**
	 * Gets the delivery customer code.
	 *
	 * @return the delivery customer code
	 */
	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}

	/**
	 * Sets the delivery customer code.
	 *
	 * @param deliveryCustomerCode the new delivery customer code
	 */
	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}

	/**
	 * Gets the delivery customer mobilephone.
	 *
	 * @return the delivery customer mobilephone
	 */
	public String getDeliveryCustomerMobilephone() {
		return deliveryCustomerMobilephone;
	}

	/**
	 * Sets the delivery customer mobilephone.
	 *
	 * @param deliveryCustomerMobilephone the new delivery customer mobilephone
	 */
	public void setDeliveryCustomerMobilephone(String deliveryCustomerMobilephone) {
		this.deliveryCustomerMobilephone = deliveryCustomerMobilephone;
	}

	/**
	 * Gets the delivery customer phone.
	 *
	 * @return the delivery customer phone
	 */
	public String getDeliveryCustomerPhone() {
		return deliveryCustomerPhone;
	}

	/**
	 * Sets the delivery customer phone.
	 *
	 * @param deliveryCustomerPhone the new delivery customer phone
	 */
	public void setDeliveryCustomerPhone(String deliveryCustomerPhone) {
		this.deliveryCustomerPhone = deliveryCustomerPhone;
	}

	/**
	 * Gets the delivery customer contact.
	 *
	 * @return the delivery customer contact
	 */
	public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}

	/**
	 * Sets the delivery customer contact.
	 *
	 * @param deliveryCustomerContact the new delivery customer contact
	 */
	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
	}

	/**
	 * Gets the delivery customer address.
	 *
	 * @return the delivery customer address
	 */
	public String getDeliveryCustomerAddress() {
		return deliveryCustomerAddress;
	}

	/**
	 * Sets the delivery customer address.
	 *
	 * @param deliveryCustomerAddress the new delivery customer address
	 */
	public void setDeliveryCustomerAddress(String deliveryCustomerAddress) {
		this.deliveryCustomerAddress = deliveryCustomerAddress;
	}

	/**
	 * Gets the receive customer name.
	 *
	 * @return the receive customer name
	 */
	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	/**
	 * Sets the receive customer name.
	 *
	 * @param receiveCustomerName the new receive customer name
	 */
	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	/**
	 * Gets the receive customer code.
	 *
	 * @return the receive customer code
	 */
	public String getReceiveCustomerCode() {
		return receiveCustomerCode;
	}

	/**
	 * Sets the receive customer code.
	 *
	 * @param receiveCustomerCode the new receive customer code
	 */
	public void setReceiveCustomerCode(String receiveCustomerCode) {
		this.receiveCustomerCode = receiveCustomerCode;
	}

	/**
	 * Gets the receive customer mobilephone.
	 *
	 * @return the receive customer mobilephone
	 */
	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}

	/**
	 * Sets the receive customer mobilephone.
	 *
	 * @param receiveCustomerMobilephone the new receive customer mobilephone
	 */
	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}

	/**
	 * Gets the receive customer phone.
	 *
	 * @return the receive customer phone
	 */
	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}

	/**
	 * Sets the receive customer phone.
	 *
	 * @param receiveCustomerPhone the new receive customer phone
	 */
	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}

	/**
	 * Gets the receive customer contact.
	 *
	 * @return the receive customer contact
	 */
	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	/**
	 * Sets the receive customer contact.
	 *
	 * @param receiveCustomerContact the new receive customer contact
	 */
	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}

	/**
	 * Gets the receive customer address.
	 *
	 * @return the receive customer address
	 */
	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}

	/**
	 * Sets the receive customer address.
	 *
	 * @param receiveCustomerAddress the new receive customer address
	 */
	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}

	/**
	 * Gets the goods name.
	 *
	 * @return the goods name
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * Sets the goods name.
	 *
	 * @param goodsName the new goods name
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * Gets the goods type code.
	 *
	 * @return the goods type code
	 */
	public String getGoodsTypeCode() {
		return goodsTypeCode;
	}

	/**
	 * Sets the goods type code.
	 *
	 * @param goodsTypeCode the new goods type code
	 */
	public void setGoodsTypeCode(String goodsTypeCode) {
		this.goodsTypeCode = goodsTypeCode;
	}

	/**
	 * Gets the goods package.
	 *
	 * @return the goods package
	 */
	public String getGoodsPackage() {
		return goodsPackage;
	}

	/**
	 * Sets the goods package.
	 *
	 * @param goodsPackage the new goods package
	 */
	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	/**
	 * Gets the goods qty total.
	 *
	 * @return the goods qty total
	 */
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	/**
	 * Sets the goods qty total.
	 *
	 * @param goodsQtyTotal the new goods qty total
	 */
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	/**
	 * Gets the goods weight total.
	 *
	 * @return the goods weight total
	 */
	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}

	/**
	 * Sets the goods weight total.
	 *
	 * @param goodsWeightTotal the new goods weight total
	 */
	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}

	/**
	 * Gets the goods volume total.
	 *
	 * @return the goods volume total
	 */
	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	/**
	 * Sets the goods volume total.
	 *
	 * @param goodsVolumeTotal the new goods volume total
	 */
	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	/**
	 * Gets the goods size.
	 *
	 * @return the goods size
	 */
	public String getGoodsSize() {
		return goodsSize;
	}

	/**
	 * Sets the goods size.
	 *
	 * @param goodsSize the new goods size
	 */
	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
	}

	/**
	 * Gets the outer notes.
	 *
	 * @return the outer notes
	 */
	public String getOuterNotes() {
		return outerNotes;
	}

	/**
	 * Sets the outer notes.
	 *
	 * @param outerNotes the new outer notes
	 */
	public void setOuterNotes(String outerNotes) {
		this.outerNotes = outerNotes;
	}

	/**
	 * Gets the inner notes.
	 *
	 * @return the inner notes
	 */
	public String getInnerNotes() {
		return innerNotes;
	}

	/**
	 * Sets the inner notes.
	 *
	 * @param innerNotes the new inner notes
	 */
	public void setInnerNotes(String innerNotes) {
		this.innerNotes = innerNotes;
	}

	/**
	 * Gets the transportation remark.
	 *
	 * @return the transportation remark
	 */
	public String getTransportationRemark() {
		return transportationRemark;
	}

	/**
	 * Sets the transportation remark.
	 *
	 * @param transportationRemark the new transportation remark
	 */
	public void setTransportationRemark(String transportationRemark) {
		this.transportationRemark = transportationRemark;
	}

	/**
	 * Gets the billing type.
	 *
	 * @return the billing type
	 */
	public String getBillingType() {
		return billingType;
	}

	/**
	 * Sets the billing type.
	 *
	 * @param billingType the new billing type
	 */
	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}

	/**
	 * Gets the transport fee.
	 *
	 * @return the transport fee
	 */
	public BigDecimal getTransportFee() {
		return transportFee;
	}

	/**
	 * Sets the transport fee.
	 *
	 * @param transportFee the new transport fee
	 */
	public void setTransportFee(BigDecimal transportFee) {
		this.transportFee = transportFee;
	}

	/**
	 * Gets the total fee.
	 *
	 * @return the total fee
	 */
	public BigDecimal getTotalFee() {
		return totalFee;
	}

	/**
	 * Sets the total fee.
	 *
	 * @param totalFee the new total fee
	 */
	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	/**
	 * Gets the paid method.
	 *
	 * @return the paid method
	 */
	public String getPaidMethod() {
		return paidMethod;
	}

	/**
	 * Sets the paid method.
	 *
	 * @param paidMethod the new paid method
	 */
	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}

	/**
	 * Gets the pre pay amount.
	 *
	 * @return the pre pay amount
	 */
	public BigDecimal getPrePayAmount() {
		return prePayAmount;
	}

	/**
	 * Sets the pre pay amount.
	 *
	 * @param prePayAmount the new pre pay amount
	 */
	public void setPrePayAmount(BigDecimal prePayAmount) {
		this.prePayAmount = prePayAmount;
	}

	/**
	 * Gets the to pay amount.
	 *
	 * @return the to pay amount
	 */
	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}

	/**
	 * Sets the to pay amount.
	 *
	 * @param toPayAmount the new to pay amount
	 */
	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
	}

	/**
	 * Gets the return bill type.
	 *
	 * @return the return bill type
	 */
	public String getReturnBillType() {
		return returnBillType;
	}

	/**
	 * Sets the return bill type.
	 *
	 * @param returnBillType the new return bill type
	 */
	public void setReturnBillType(String returnBillType) {
		this.returnBillType = returnBillType;
	}

	/**
	 * Gets the insurance amount.
	 *
	 * @return the insurance amount
	 */
	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}

	/**
	 * Sets the insurance amount.
	 *
	 * @param insuranceAmount the new insurance amount
	 */
	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	/**
	 * Gets the cod amount.
	 *
	 * @return the cod amount
	 */
	public BigDecimal getCodAmount() {
		return codAmount;
	}

	/**
	 * Sets the cod amount.
	 *
	 * @param codAmount the new cod amount
	 */
	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}

	/**
	 * Gets the cod fee.
	 *
	 * @return the cod fee
	 */
	public BigDecimal getCodFee() {
		return codFee;
	}

	/**
	 * Sets the cod fee.
	 *
	 * @param codFee the new cod fee
	 */
	public void setCodFee(BigDecimal codFee) {
		this.codFee = codFee;
	}

	/**
	 * Gets the refund type.
	 *
	 * @return the refund type
	 */
	public String getRefundType() {
		return refundType;
	}

	/**
	 * Sets the refund type.
	 *
	 * @param refundType the new refund type
	 */
	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	/**
	 * Gets the delivery goods fee.
	 *
	 * @return the delivery goods fee
	 */
	public BigDecimal getDeliveryGoodsFee() {
		return deliveryGoodsFee;
	}

	/**
	 * Sets the delivery goods fee.
	 *
	 * @param deliveryGoodsFee the new delivery goods fee
	 */
	public void setDeliveryGoodsFee(BigDecimal deliveryGoodsFee) {
		this.deliveryGoodsFee = deliveryGoodsFee;
	}

	/**
	 * Gets the package fee.
	 *
	 * @return the package fee
	 */
	public BigDecimal getPackageFee() {
		return packageFee;
	}

	/**
	 * Sets the package fee.
	 *
	 * @param packageFee the new package fee
	 */
	public void setPackageFee(BigDecimal packageFee) {
		this.packageFee = packageFee;
	}

	/**
	 * Gets the service fee.
	 *
	 * @return the service fee
	 */
	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	/**
	 * Sets the service fee.
	 *
	 * @param serviceFee the new service fee
	 */
	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}

	/**
	 * Gets the other fee.
	 *
	 * @return the other fee
	 */
	public BigDecimal getOtherFee() {
		return otherFee;
	}

	/**
	 * Sets the other fee.
	 *
	 * @param otherFee the new other fee
	 */
	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}

	/**
	 * Gets the pickup centralized.
	 *
	 * @return the pickup centralized
	 */
	public String getPickupCentralized() {
		return pickupCentralized;
	}

	/**
	 * Sets the pickup centralized.
	 *
	 * @param pickupCentralized the new pickup centralized
	 */
	public void setPickupCentralized(String pickupCentralized) {
		this.pickupCentralized = pickupCentralized;
	}

	/**
	 * Gets the car direct delivery.
	 *
	 * @return the car direct delivery
	 */
	public String getCarDirectDelivery() {
		return carDirectDelivery;
	}

	/**
	 * Sets the car direct delivery.
	 *
	 * @param carDirectDelivery the new car direct delivery
	 */
	public void setCarDirectDelivery(String carDirectDelivery) {
		this.carDirectDelivery = carDirectDelivery;
	}

	/**
	 * Gets the is whole vehicle.
	 *
	 * @return the is whole vehicle
	 */
	public String getIsWholeVehicle() {
		return isWholeVehicle;
	}

	/**
	 * Sets the is whole vehicle.
	 *
	 * @param isWholeVehicle the new is whole vehicle
	 */
	public void setIsWholeVehicle(String isWholeVehicle) {
		this.isWholeVehicle = isWholeVehicle;
	}

	/**
	 * Gets the is not pass own department.
	 *
	 * @return the is not pass own department
	 */
	public String getIsNotPassOwnDepartment() {
		return isNotPassOwnDepartment;
	}

	/**
	 * Sets the is not pass own department.
	 *
	 * @param isNotPassOwnDepartment the new is not pass own department
	 */
	public void setIsNotPassOwnDepartment(String isNotPassOwnDepartment) {
		this.isNotPassOwnDepartment = isNotPassOwnDepartment;
	}

	/**
	 * Gets the is pass own department.
	 *
	 * @return the is pass own department
	 */
	public String getIsPassOwnDepartment() {
		return isPassOwnDepartment;
	}

	/**
	 * Sets the is pass own department.
	 *
	 * @param isPassOwnDepartment the new is pass own department
	 */
	public void setIsPassOwnDepartment(String isPassOwnDepartment) {
		this.isPassOwnDepartment = isPassOwnDepartment;
	}

	/**
	 * Gets the precious goods.
	 *
	 * @return the precious goods
	 */
	public String getPreciousGoods() {
		return preciousGoods;
	}

	/**
	 * Sets the precious goods.
	 *
	 * @param preciousGoods the new precious goods
	 */
	public void setPreciousGoods(String preciousGoods) {
		this.preciousGoods = preciousGoods;
	}

	/**
	 * Gets the special shaped goods.
	 *
	 * @return the special shaped goods
	 */
	public String getSpecialShapedGoods() {
		return specialShapedGoods;
	}

	/**
	 * Sets the special shaped goods.
	 *
	 * @param specialShapedGoods the new special shaped goods
	 */
	public void setSpecialShapedGoods(String specialShapedGoods) {
		this.specialShapedGoods = specialShapedGoods;
	}

	/**
	  * @return  the createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * @param createUserName the createUserName to set
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	  * @return  the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	  * @return  the receiveOrgName
	 */
	public String getReceiveOrgName() {
		return receiveOrgName;
	}

	/**
	 * @param receiveOrgName the receiveOrgName to set
	 */
	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}

	/**
	  * @return  the customerPickupOrgCode
	 */
	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	/**
	 * @param customerPickupOrgCode the customerPickupOrgCode to set
	 */
	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}

	/**
	  * @return  the loadOrgName
	 */
	public String getLoadOrgName() {
		return loadOrgName;
	}

	/**
	 * @param loadOrgName the loadOrgName to set
	 */
	public void setLoadOrgName(String loadOrgName) {
		this.loadOrgName = loadOrgName;
	}


	/**
	 * @return  the ReceiveOrgCity
	 */
	public String getReceiveOrgCity() {
		return receiveOrgCity;
	}
	
	public void setReceiveOrgCity(String receiveOrgCity) {
		this.receiveOrgCity = receiveOrgCity;
	}

	public Date getPreCustomerPickupTime() {
		return preCustomerPickupTime;
	}

	public void setPreCustomerPickupTime(Date preCustomerPickupTime) {
		this.preCustomerPickupTime = preCustomerPickupTime;
	}

	public Date getFlightShift() {
		return flightShift;
	}

	public void setFlightShift(Date flightShift) {
		this.flightShift = flightShift;
	}

	public String getFlightNumberType() {
		return flightNumberType;
	}

	public void setFlightNumberType(String flightNumberType) {
		this.flightNumberType = flightNumberType;
	}

	public BigDecimal getCodRate() {
		return codRate;
	}

	public void setCodRate(BigDecimal codRate) {
		this.codRate = codRate;
	}

	public String getWaybillStatus() {
		return waybillStatus;
	}

	public void setWaybillStatus(String waybillStatus) {
		this.waybillStatus = waybillStatus;
	}

	public String getSecretPrepaid() {
		return secretPrepaid;
	}

	public void setSecretPrepaid(String secretPrepaid) {
		this.secretPrepaid = secretPrepaid;
	}
	public String getReceiveBigCustomer() {
		return receiveBigCustomer;
	}
	public void setReceiveBigCustomer(String receiveBigCustomer) {
		this.receiveBigCustomer = receiveBigCustomer;
	}
	public String getDeliveryBigCustomer() {
		return deliveryBigCustomer;
	}
	public void setDeliveryBigCustomer(String deliveryBigCustomer) {
		this.deliveryBigCustomer = deliveryBigCustomer;
	}
	public String getWaybillType() {
		return waybillType;
	}
	public void setWaybillType(String waybillType) {
		this.waybillType = waybillType;
	}
	public String getDeliveryCustomerAddressNote() {
		return deliveryCustomerAddressNote;
	}
	public void setDeliveryCustomerAddressNote(String deliveryCustomerAddressNote) {
		this.deliveryCustomerAddressNote = deliveryCustomerAddressNote;
	}
	public String getReceiveCustomerAddressNote() {
		return receiveCustomerAddressNote;
	}
	public void setReceiveCustomerAddressNote(String receiveCustomerAddressNote) {
		this.receiveCustomerAddressNote = receiveCustomerAddressNote;
	}
	public String getReturnWaybillNo() {
		return returnWaybillNo;
	}
	public void setReturnWaybillNo(String returnWaybillNo) {
		this.returnWaybillNo = returnWaybillNo;
	}
	public String getOriginalWaybillNo() {
		return originalWaybillNo;
	}
	public void setOriginalWaybillNo(String originalWaybillNo) {
		this.originalWaybillNo = originalWaybillNo;
	}
	public BigDecimal getToPayAmountDiscount() {
		return toPayAmountDiscount;
	}
	public void setToPayAmountDiscount(BigDecimal toPayAmountDiscount) {
		this.toPayAmountDiscount = toPayAmountDiscount;
	}
	public BigDecimal getPrePayAmountDiscount() {
		return prePayAmountDiscount;
	}
	public void setPrePayAmountDiscount(BigDecimal prePayAmountDiscount) {
		this.prePayAmountDiscount = prePayAmountDiscount;
	}
	public BigDecimal getOriAndReturntotalFee() {
		return oriAndReturntotalFee;
	}
	public void setOriAndReturntotalFee(BigDecimal oriAndReturntotalFee) {
		this.oriAndReturntotalFee = oriAndReturntotalFee;
	}
	public BigDecimal getOriginalWaybillNoTotalFee() {
		return originalWaybillNoTotalFee;
	}
	public void setOriginalWaybillNoTotalFee(BigDecimal originalWaybillNoTotalFee) {
		this.originalWaybillNoTotalFee = originalWaybillNoTotalFee;
	}
	public String getIsExhibitCargo() {
		return isExhibitCargo;
	}
	public void setIsExhibitCargo(String isExhibitCargo) {
		this.isExhibitCargo = isExhibitCargo;
	}

	public String getOrderChannel() {
		return orderChannel;
	}
	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}
	public BigDecimal getCzmGoodsWeightTotal() {
		return czmGoodsWeightTotal;
	}
	public void setCzmGoodsWeightTotal(BigDecimal czmGoodsWeightTotal) {
		this.czmGoodsWeightTotal = czmGoodsWeightTotal;
	}
	public BigDecimal getCzmGoodsVolumeTotal() {
		return czmGoodsVolumeTotal;
	}
	public void setCzmGoodsVolumeTotal(BigDecimal czmGoodsVolumeTotal) {
		this.czmGoodsVolumeTotal = czmGoodsVolumeTotal;
	}
	

}