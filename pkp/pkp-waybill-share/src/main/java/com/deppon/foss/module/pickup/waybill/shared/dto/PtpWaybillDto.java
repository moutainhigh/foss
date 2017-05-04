package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PtpWaybillDto  implements Serializable{

	private static final long serialVersionUID = -2138607407266924405L;
	
	//费用明细
//	private List<PtpWaybillFeeDetailDto> ptpWaybillFeeDetailList;

	/**
	 * 运单号
	 */
	private String waybillNo;
	
	//运单表中运单ID
	private String waybillNOId ;
	
	// 收货客户编码
	private String receiveCustomerCode;
	
	// 收货客户名称
	private String receiveCustomerName;
	
	// 发货客户编码
	private String deliveryCustomerCode;

	// 发货客户名称
	private String deliveryCustomerName;
	
	// 货物总重量
	private BigDecimal goodsWeightTotal;

	// 货物总体积
	private BigDecimal goodsVolumeTotal;
	
	// 货物总件数
	private Integer goodsQtyTotal;
	
	// 目的站
	private String targetOrgCode;
	
	// 开单人
	private String createUserCode;
	
	// 开单组织
	private String createOrgCode;
	
	// 发货客户联系人
	private String deliveryCustomerContact;
	
	// 收货客户联系人
	private String receiveCustomerContact;
	
	//发票标记
	private String invoice;
	
	/**
	 * 到达客户是否统一结算
	 */
	private String arriveCentralizedSettlement;
	
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
	
	// 创建时间
	private Date createTime;
	
	// 货物名称
	private String goodsName;

	/**
	 * 提货网点
	 */
	private String customerPickupOrgCode;
	
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
	
	//基础送货费
	private BigDecimal baseDeliveryGoodsFee ;
	
	//上楼费
	private BigDecimal upFloorFee ;

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
	 * 总费用
	 */
	private BigDecimal totalFee;
	
	/**
	 * 开单时间
	 */
	private Date billTime;
	

	/**
	 * 原运单号
	 */
	private String refCode;
	
	/**
	 * 原运单ID
	 */
	private String refId;
	
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
	 * 运输类型
	 */
	private String transportType;
	
	
	/**
	 * 运单状态
	 */
	private String active;
	
	
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
	 * 收货部门名称
	 */
	private String receiveOrgName;
	
	/**
	 * 提货网点名称
	 */
	private String customerPickupOrgName;
	
	/**
	 * 公里数
	 */
	private BigDecimal kilometer;
	
	/**
	 * 是否经济自提件
	 */
	private String isEconomyGoods;
	
	/**
	 * 是否快递
	 * @return
	 */
	private String isExpress;
	
	
	//0:成本  1：提成  2：成本和提成
	private String feeType ;
	
	// 公布价运费
	private BigDecimal transportFeeOrg;

	/**
	 * 保价费 （修改前的值）
	 */
	private BigDecimal insuranceFeeOrg;

	// 代收货款手续费
	private BigDecimal codFeeOrg;
	
	// 接货费
	private String pickUpFeeOrg;

	// 送货费+送货上楼费
	private String deliveryGoodsFeeOrg;
	
	//基础送货费
	private String baseDeliveryGoodsFeeOrg;
	
	//上楼费
	private String upFloorFeeOrg;
	
	// 包装费
	private String packageFeeOrg;

	// 其他费用
	private BigDecimal otherFeeOrg;

	// 总费用
	private BigDecimal totalFeeOrg;
	
	// 打木箱货物费用
	private BigDecimal boxChargeOrg;
	
	// 打木托货物费用
	private BigDecimal salverGoodsChargeOrg;
	
	// 打木架货物费用
	private BigDecimal standChargeOrg;
	
	/**
	 * 优惠费用
	 */
	private BigDecimal promotionsFeeOrg;
	
	/**
	 * 装卸费
	 */
	private BigDecimal serviceFeeOrg;
	
	/**
	 * 增值费用
	 */
	private BigDecimal valueAddFeeOrg;

	/**
	 * 是否更改单 Y：更改单；N:开单
	 */
	private String isChanged ;
	
	//送货进仓费
	private BigDecimal deliveryWareHouseFeeOrg;
	
	//大件上楼费
	private BigDecimal bigGoodsUpFloorFeeOrg ;
	
	//是否更改标签 Y:是；N:否
	private Boolean isChangeLabel = false;
	
	//更改 开单件数
	private Integer countBills ;
	
	//(更改类型):转运、返货、其他项
	private String changeType ;
	
	//到达部门 
	private String  arriveOrgCode ;
	
	// 是否上门接货
	private Boolean pickupToDoor;
	
	//送货上楼安装（家装）
	private BigDecimal pickupToDoorJZFeeOrg ;
	
	//异常操作服务费 ZZ
	private BigDecimal exceptionOpreationFeeOrg ;
	
	//超远派送费 CY
	private BigDecimal overDistanceFeeOrg ;
	
	//签收单
	private BigDecimal signBillFeeOrg ;
	
	//判断到达部门是否为合伙人
	private Boolean isArriveDepPartner ;

	//判断出发部门是否为合伙人
	private Boolean isReceiveDepPartner ;
	
	//更改费
	private BigDecimal changeFee ;
	
	/**
     * 职员编号
     */
    private String userCode;

    /**
     * 用户登录名
     */
    private String userName;
	
	//当前登录部门编码
	private String currentDeptCode;
		
	//当前登录部门名称
	private String currentDeptName;
	
	//币种
	private String currencyCode ;
	
	//当前登录部门标杆编码
	private String currentDeptUnifieldCode ;
	
	//出发部门标杆编码
	private String receiveDeptUnifieldCode ;
    
	//到达部门标杆编码
	private String arriveDeptUnifieldCode ;
	
	//2016年3月8日 09:53:49 葛亮亮
	/**
	 * 开户行编码
	 */
	private String bankHQCode;

	/**
	 * 开户行名称
	 */
	private String bankHQName;

	/**
	 * 支行编码（行号）
	 */
	private String bankBranchCode;

	/**
	 * 支行名称
	 */
	private String bankBranchName;

	/**
	 * 省份编码
	 */
	private String provinceCode;

	/**
	 * 省份名称
	 */
	private String provinceName;

	/**
	 * 城市编码
	 */
	private String cityCode;

	/**
	 * 城市名称
	 */
	private String cityName;

	/**
	 * 对公对私标志
	 */
	private String publicPrivateFlag;

	/**
	 * 收款人与发货人关系
	 */
	private String payeeRelationship;

	/**
	 * 收款人手机号码
	 */
	private String payeePhone;
	
	/**
	 * 收款人
	 */
	private String payeeName;
	
	/**
	 * 收款人账号
	 */
	private String accountNo;
	
	/**
	 * 库存状态
	 */
	private String waybillStock;
	
	//是否需要查询推送到PTP的历史数据
	private String isSearchHistory;
	
	//运单上一次的操作创建时间
	private Date lastCreateTime;
	
	//转运返货是否重新计算公布价运费
	private String isCalTraFee;
	//重新计算公布价运费出发部门
	private String startOrgCodeCal;
	//重新计算公布价运费到达部门
	private String destinationOrgCodeCal;
	
	//合伙人受理更改单提成 标识
	private String isParnterAcceptStatus;
	
	//合伙人目的站更改状态
	private String toPartnerBranchStat;
	
	//合伙人到达加收金额 2016年9月1日 09:47:19 葛亮亮
	private BigDecimal overTransportFeeOrg;
	//合伙人到达加收费率
	private BigDecimal overTransportRateOrg;
	
	//添加优惠券code和优惠劵金额--合伙人第二批需求--zoushengli
    //优惠券code
	private String couponCode;
	//优惠券金额
	private BigDecimal couponFeeOrg;
	
	public BigDecimal getCouponFeeOrg() {
		return couponFeeOrg;
	}

	public void setCouponFeeOrg(BigDecimal couponFeeOrg) {
		this.couponFeeOrg = couponFeeOrg;
	}

	public String getToPartnerBranchStat() {
		return toPartnerBranchStat;
	}

	public void setToPartnerBranchStat(String toPartnerBranchStat) {
		this.toPartnerBranchStat = toPartnerBranchStat;
	}

	public String getWaybillStock() {
		return waybillStock;
	}

	public void setWaybillStock(String waybillStock) {
		this.waybillStock = waybillStock;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
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

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	public String getRefCode() {
		return refCode;
	}

	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
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

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
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

	public BigDecimal getKilometer() {
		return kilometer;
	}

	public void setKilometer(BigDecimal kilometer) {
		this.kilometer = kilometer;
	}

	public String getIsEconomyGoods() {
		return isEconomyGoods;
	}

	public void setIsEconomyGoods(String isEconomyGoods) {
		this.isEconomyGoods = isEconomyGoods;
	}

	public String getIsExpress() {
		return isExpress;
	}

	public void setIsExpress(String isExpress) {
		this.isExpress = isExpress;
	}

	public BigDecimal getTransportFeeOrg() {
		return transportFeeOrg;
	}

	public void setTransportFeeOrg(BigDecimal transportFeeOrg) {
		this.transportFeeOrg = transportFeeOrg;
	}

	public BigDecimal getCodFeeOrg() {
		return codFeeOrg;
	}

	public void setCodFeeOrg(BigDecimal codFeeOrg) {
		this.codFeeOrg = codFeeOrg;
	}

	public String getPickUpFeeOrg() {
		return pickUpFeeOrg;
	}

	public void setPickUpFeeOrg(String pickUpFeeOrg) {
		this.pickUpFeeOrg = pickUpFeeOrg;
	}

	public String getDeliveryGoodsFeeOrg() {
		return deliveryGoodsFeeOrg;
	}

	public void setDeliveryGoodsFeeOrg(String deliveryGoodsFeeOrg) {
		this.deliveryGoodsFeeOrg = deliveryGoodsFeeOrg;
	}

	public String getBaseDeliveryGoodsFeeOrg() {
		return baseDeliveryGoodsFeeOrg;
	}

	public void setBaseDeliveryGoodsFeeOrg(String baseDeliveryGoodsFeeOrg) {
		this.baseDeliveryGoodsFeeOrg = baseDeliveryGoodsFeeOrg;
	}

	public String getUpFloorFeeOrg() {
		return upFloorFeeOrg;
	}

	public void setUpFloorFeeOrg(String upFloorFeeOrg) {
		this.upFloorFeeOrg = upFloorFeeOrg;
	}

	public String getPackageFeeOrg() {
		return packageFeeOrg;
	}

	public void setPackageFeeOrg(String packageFeeOrg) {
		this.packageFeeOrg = packageFeeOrg;
	}

	public BigDecimal getInsuranceFeeOrg() {
		return insuranceFeeOrg;
	}

	public void setInsuranceFeeOrg(BigDecimal insuranceFeeOrg) {
		this.insuranceFeeOrg = insuranceFeeOrg;
	}

	public BigDecimal getBoxChargeOrg() {
		return boxChargeOrg;
	}

	public void setBoxChargeOrg(BigDecimal boxChargeOrg) {
		this.boxChargeOrg = boxChargeOrg;
	}

	public BigDecimal getSalverGoodsChargeOrg() {
		return salverGoodsChargeOrg;
	}

	public void setSalverGoodsChargeOrg(BigDecimal salverGoodsChargeOrg) {
		this.salverGoodsChargeOrg = salverGoodsChargeOrg;
	}

	public BigDecimal getStandChargeOrg() {
		return standChargeOrg;
	}

	public void setStandChargeOrg(BigDecimal standChargeOrg) {
		this.standChargeOrg = standChargeOrg;
	}

	public BigDecimal getPromotionsFeeOrg() {
		return promotionsFeeOrg;
	}

	public void setPromotionsFeeOrg(BigDecimal promotionsFeeOrg) {
		this.promotionsFeeOrg = promotionsFeeOrg;
	}

	public BigDecimal getServiceFeeOrg() {
		return serviceFeeOrg;
	}

	public void setServiceFeeOrg(BigDecimal serviceFeeOrg) {
		this.serviceFeeOrg = serviceFeeOrg;
	}

	public BigDecimal getValueAddFeeOrg() {
		return valueAddFeeOrg;
	}

	public void setValueAddFeeOrg(BigDecimal valueAddFeeOrg) {
		this.valueAddFeeOrg = valueAddFeeOrg;
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

	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	public String getTargetOrgCode() {
		return targetOrgCode;
	}

	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}

	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
	}

	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public String getArriveCentralizedSettlement() {
		return arriveCentralizedSettlement;
	}

	public void setArriveCentralizedSettlement(String arriveCentralizedSettlement) {
		this.arriveCentralizedSettlement = arriveCentralizedSettlement;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getIsChanged() {
		return isChanged;
	}

	public void setIsChanged(String isChanged) {
		this.isChanged = isChanged;
	}

	public BigDecimal getBaseDeliveryGoodsFee() {
		return baseDeliveryGoodsFee;
	}

	public void setBaseDeliveryGoodsFee(BigDecimal baseDeliveryGoodsFee) {
		this.baseDeliveryGoodsFee = baseDeliveryGoodsFee;
	}

	public BigDecimal getUpFloorFee() {
		return upFloorFee;
	}

	public void setUpFloorFee(BigDecimal upFloorFee) {
		this.upFloorFee = upFloorFee;
	}

	public Boolean getIsChangeLabel() {
		return isChangeLabel;
	}

	public void setIsChangeLabel(Boolean isChangeLabel) {
		this.isChangeLabel = isChangeLabel;
	}

	public Integer getCountBills() {
		return countBills;
	}

	public void setCountBills(Integer countBills) {
		this.countBills = countBills;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public String getArriveOrgCode() {
		return arriveOrgCode;
	}

	public void setArriveOrgCode(String arriveOrgCode) {
		this.arriveOrgCode = arriveOrgCode;
	}

	public Boolean getPickupToDoor() {
		return pickupToDoor;
	}

	public void setPickupToDoor(Boolean pickupToDoor) {
		this.pickupToDoor = pickupToDoor;
	}

	public BigDecimal getDeliveryWareHouseFeeOrg() {
		return deliveryWareHouseFeeOrg;
	}

	public void setDeliveryWareHouseFeeOrg(BigDecimal deliveryWareHouseFeeOrg) {
		this.deliveryWareHouseFeeOrg = deliveryWareHouseFeeOrg;
	}

	public BigDecimal getBigGoodsUpFloorFeeOrg() {
		return bigGoodsUpFloorFeeOrg;
	}

	public void setBigGoodsUpFloorFeeOrg(BigDecimal bigGoodsUpFloorFeeOrg) {
		this.bigGoodsUpFloorFeeOrg = bigGoodsUpFloorFeeOrg;
	}

	public BigDecimal getPickupToDoorJZFeeOrg() {
		return pickupToDoorJZFeeOrg;
	}

	public void setPickupToDoorJZFeeOrg(BigDecimal pickupToDoorJZFeeOrg) {
		this.pickupToDoorJZFeeOrg = pickupToDoorJZFeeOrg;
	}

	public BigDecimal getExceptionOpreationFeeOrg() {
		return exceptionOpreationFeeOrg;
	}

	public void setExceptionOpreationFeeOrg(BigDecimal exceptionOpreationFeeOrg) {
		this.exceptionOpreationFeeOrg = exceptionOpreationFeeOrg;
	}

	public BigDecimal getOverDistanceFeeOrg() {
		return overDistanceFeeOrg;
	}

	public void setOverDistanceFeeOrg(BigDecimal overDistanceFeeOrg) {
		this.overDistanceFeeOrg = overDistanceFeeOrg;
	}

	public BigDecimal getSignBillFeeOrg() {
		return signBillFeeOrg;
	}

	public void setSignBillFeeOrg(BigDecimal signBillFeeOrg) {
		this.signBillFeeOrg = signBillFeeOrg;
	}

	public BigDecimal getOtherFeeOrg() {
		return otherFeeOrg;
	}

	public void setOtherFeeOrg(BigDecimal otherFeeOrg) {
		this.otherFeeOrg = otherFeeOrg;
	}

	public BigDecimal getTotalFeeOrg() {
		return totalFeeOrg;
	}

	public void setTotalFeeOrg(BigDecimal totalFeeOrg) {
		this.totalFeeOrg = totalFeeOrg;
	}

	public Boolean getIsArriveDepPartner() {
		return isArriveDepPartner;
	}

	public void setIsArriveDepPartner(Boolean isArriveDepPartner) {
		this.isArriveDepPartner = isArriveDepPartner;
	}

	public BigDecimal getChangeFee() {
		return changeFee;
	}

	public void setChangeFee(BigDecimal changeFee) {
		this.changeFee = changeFee;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCurrentDeptCode() {
		return currentDeptCode;
	}

	public void setCurrentDeptCode(String currentDeptCode) {
		this.currentDeptCode = currentDeptCode;
	}

	public String getCurrentDeptName() {
		return currentDeptName;
	}

	public void setCurrentDeptName(String currentDeptName) {
		this.currentDeptName = currentDeptName;
	}

	public String getWaybillNOId() {
		return waybillNOId;
	}

	public void setWaybillNOId(String waybillNOId) {
		this.waybillNOId = waybillNOId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrentDeptUnifieldCode() {
		return currentDeptUnifieldCode;
	}

	public void setCurrentDeptUnifieldCode(String currentDeptUnifieldCode) {
		this.currentDeptUnifieldCode = currentDeptUnifieldCode;
	}

	public String getReceiveDeptUnifieldCode() {
		return receiveDeptUnifieldCode;
	}

	public void setReceiveDeptUnifieldCode(String receiveDeptUnifieldCode) {
		this.receiveDeptUnifieldCode = receiveDeptUnifieldCode;
	}

	public String getArriveDeptUnifieldCode() {
		return arriveDeptUnifieldCode;
	}

	public void setArriveDeptUnifieldCode(String arriveDeptUnifieldCode) {
		this.arriveDeptUnifieldCode = arriveDeptUnifieldCode;
	}

	public String getBankHQCode() {
		return bankHQCode;
	}

	public String getBankHQName() {
		return bankHQName;
	}

	public String getBankBranchCode() {
		return bankBranchCode;
	}

	public String getBankBranchName() {
		return bankBranchName;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public String getPublicPrivateFlag() {
		return publicPrivateFlag;
	}

	public String getPayeeRelationship() {
		return payeeRelationship;
	}

	public String getPayeePhone() {
		return payeePhone;
	}

	public void setBankHQCode(String bankHQCode) {
		this.bankHQCode = bankHQCode;
	}

	public void setBankHQName(String bankHQName) {
		this.bankHQName = bankHQName;
	}

	public void setBankBranchCode(String bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}

	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public void setPublicPrivateFlag(String publicPrivateFlag) {
		this.publicPrivateFlag = publicPrivateFlag;
	}

	public void setPayeeRelationship(String payeeRelationship) {
		this.payeeRelationship = payeeRelationship;
	}

	public void setPayeePhone(String payeePhone) {
		this.payeePhone = payeePhone;
	}

	public Boolean getIsReceiveDepPartner() {
		return isReceiveDepPartner;
	}

	public void setIsReceiveDepPartner(Boolean isReceiveDepPartner) {
		this.isReceiveDepPartner = isReceiveDepPartner;
	}

	public String getIsSearchHistory() {
		return isSearchHistory;
	}

	public Date getLastCreateTime() {
		return lastCreateTime;
	}

	public void setIsSearchHistory(String isSearchHistory) {
		this.isSearchHistory = isSearchHistory;
	}

	public void setLastCreateTime(Date lastCreateTime) {
		this.lastCreateTime = lastCreateTime;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getIsCalTraFee() {
		return isCalTraFee;
	}

	public String getStartOrgCodeCal() {
		return startOrgCodeCal;
	}

	public String getDestinationOrgCodeCal() {
		return destinationOrgCodeCal;
	}

	public void setIsCalTraFee(String isCalTraFee) {
		this.isCalTraFee = isCalTraFee;
	}

	public void setStartOrgCodeCal(String startOrgCodeCal) {
		this.startOrgCodeCal = startOrgCodeCal;
	}

	public void setDestinationOrgCodeCal(String destinationOrgCodeCal) {
		this.destinationOrgCodeCal = destinationOrgCodeCal;
	}

	public BigDecimal getOverTransportFeeOrg() {
		return overTransportFeeOrg;
	}

	public void setOverTransportFeeOrg(BigDecimal overTransportFeeOrg) {
		this.overTransportFeeOrg = overTransportFeeOrg;
	}

	public BigDecimal getOverTransportRateOrg() {
		return overTransportRateOrg;
	}

	public void setOverTransportRateOrg(BigDecimal overTransportRateOrg) {
		this.overTransportRateOrg = overTransportRateOrg;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getIsParnterAcceptStatus() {
		return isParnterAcceptStatus;
	}

	public void setIsParnterAcceptStatus(String isParnterAcceptStatus) {
		this.isParnterAcceptStatus = isParnterAcceptStatus;
	}

	//偏线费用   2016年11月4日18:25:04  by:352676
	private BigDecimal partialTransportFeeOrg;
	public BigDecimal getPartialTransportFeeOrg() {
		return partialTransportFeeOrg;
	}

	public void setPartialTransportFeeOrg(BigDecimal partialTransportFeeOrg) {
		this.partialTransportFeeOrg = partialTransportFeeOrg;
	}

	
}
