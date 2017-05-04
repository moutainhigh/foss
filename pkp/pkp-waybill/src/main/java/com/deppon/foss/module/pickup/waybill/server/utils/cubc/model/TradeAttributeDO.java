package com.deppon.foss.module.pickup.waybill.server.utils.cubc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * 交易属性对象
 *
 * @author <a href="mailto:yihui#pamirs.top">yihui</a>
 * @version 1.0
 * @since 2016/11/10 下午6:56
 */
public class TradeAttributeDO implements Serializable {

	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 */
	private static final long serialVersionUID = -774512159712583323L;

	// 始发部门名称
	private String origOrgName;

	// 始发部门编码
	private String origOrgCode;

	// 始发部门标杆编码
	private String origOrgUnifiedCode;

	// 到达部门名称
	private String descOrgName;

	// 到达部门编码
	private String descOrgCode;

	// 到达部门标杆编码
	private String descOrgUnifiedCode;

	// 应付子公司名称
	private String payableComName;

	// 应付子公司编码
	private String payableComCode;

	// 货物总件数
	private Long packageQuantity;

	// 货物总重量
	private BigDecimal weight = new BigDecimal(0);

	// 货物总体积
	private BigDecimal volume = new BigDecimal(0);

	// 计费重量
	private BigDecimal chargeWeight = new BigDecimal(0);

	// 计费体积
	private BigDecimal chargeVolume = new BigDecimal(0);

	// 打木架体积
	private BigDecimal woodenFrameVolume = new BigDecimal(0);

	// 打木箱体积
	private BigDecimal woodenCrateVolume = new BigDecimal(0);

	// 打木托个数
	private Long woodenStockNumbers;

	// 包带根数
	private Long packingBeltNumbers;

	// 木条长度
	private BigDecimal battenSize;

	// 气泡膜体积
	private BigDecimal bubbleFilmVolume = new BigDecimal(0);

	// 缠绕膜体积
	private BigDecimal wrappingFilmVolume = new BigDecimal(0);

	// 货物品名
	private String goodsName;

	// 公布价运费
	private BigDecimal publishFreinghtTax = new BigDecimal(0);

	// 代收货款手续费
	private BigDecimal collectionChargesPoundage = new BigDecimal(0);

	// 送货费
	private BigDecimal deliveryFee = new BigDecimal(0);

	// 接货费
	private BigDecimal doorServiceReceiveFee = new BigDecimal(0);

	// 保价
	private BigDecimal insuranceStatement = new BigDecimal(0);

	// 保价费
	private BigDecimal insuranceFee = new BigDecimal(0);

	// 包装费
	private BigDecimal packingFee = new BigDecimal(0);

	// 送货进仓费
	private BigDecimal enterWarehouseFee = new BigDecimal(0);

	// 其他费用
	private BigDecimal otherFee = new BigDecimal(0);

	// 费率 保价
	private BigDecimal rate = new BigDecimal(0);

	// 计价单位
	private String chargeUnit;

	// 航班号
	private String flightNumber;

	// 代理单号
	private String agentOrder;

	// 目的省-市-区/县-镇
	private String destination;

	// 提货方式
	private String pickupWay;

	// 折扣率 家装时取值
	private Long discountRate;

	// 理赔支付类别
	private String claimPaymentCategories;

	// 车牌号
	private String vehicleNo;

	// 是否押回单
	private String beReturnReceipt;
	// 车辆出发日期
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date leaveTime;
	// 司机手机号码
	private String driverPhone;

	// 租车用途
	private String rentCarUseType;
	// 租车日期
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date useCarDate;

	// 是否多次标记
	private String isRepeatemark;
	//用车原因
	private String userCarReason ;
	//公里数
	private BigDecimal kmsNum ;
	//应走货票数
	private BigDecimal shallTakeGoodsQyt;
	//实际走货票数
	private BigDecimal actualTakeGoodsQyt;
	//车型
	private String vehicleLenghtCode;
	
	// 调整费用---调整类型
	private String adjustType;
	// 理赔：
	private String claimType;
	// 理赔方式(1-正常理赔，2-快速理赔，3-在线理赔)
	private String claimWay;
	// 责任信息 理赔使用
	private String responsibilityInfoJson;
	// 银行支付信息 理赔使用
	private String bankPayInfo;
	// *****************1零担，2快递，3整车******************
	// 最迟汇款日期
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date payBillLastTime;
	// ************************4仓储**********************
	// 仓储差错类型 1变质，2货损，3丢货
	private String errorType;
	// 费用类型 存放费用项
	private String feeType;
	// 仓储费用类型 存放费用项列表
	private List<FeeType> feeTypeList;
	// 客户联系电话
	private String customerPhone;
	// 发货客户名称
	private String deliveryCustomerCode;
	// 发货客户编码
	private String deliveryCustomerName;
	// 收货客户编码
	private String receiveCustomerCode;
	// 收货客户名称
	private String receiveCustomerName;
	// 发货人手机号
	private String consignorPhone;
	// 收货人
	private String consignee;
	// 收货人手机号
	private String consigneePhone;
	// 小票收入类别
	private String incomeCategories;
	// 小票支付编码
	private String batchNo;
	// 工作流名称
	private String workflowName;
	// 冲销方式
	private String billType;
	// 差错处理编码
	private String mistakeNum;
	// 坏账原因
	private String badDebtReason;
	// 开单部门
	// private String ;
	 //应收部门对应的子公司名称
    private String receivableOrgComName;
    //子母件
    private String subWaybillNos;
	

	/**
	 * 返回 子母件
	 * 
	 * @return 子母件
	*/
	public String getSubWaybillNos() {
		return subWaybillNos;
	}
	

	/**  
	 * 设置  子母件
	 * @param 子母件
	 */
	public void setSubWaybillNos(String subWaybillNos) {
		this.subWaybillNos = subWaybillNos;
	}
	

	/**
	 * 返回 工作流名称
	 * 
	 * @return 工作流名称
	 */
	public String getWorkflowName() {
		return workflowName;
	}

	/**
	 * 设置 工作流名称
	 * 
	 * @param 工作流名称
	 */
	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	/**
	 * 返回 冲销方式
	 * 
	 * @return 冲销方式
	 */
	public String getBillType() {
		return billType;
	}

	/**
	 * 设置 冲销方式
	 * 
	 * @param 冲销方式
	 */
	public void setBillType(String billType) {
		this.billType = billType;
	}

	/**
	 * 返回 差错处理编码
	 * 
	 * @return 差错处理编码
	 */
	public String getMistakeNum() {
		return mistakeNum;
	}

	/**
	 * 设置 差错处理编码
	 * 
	 * @param 差错处理编码
	 */
	public void setMistakeNum(String mistakeNum) {
		this.mistakeNum = mistakeNum;
	}

	/**
	 * 返回 坏账原因
	 * 
	 * @return 坏账原因
	 */
	public String getBadDebtReason() {
		return badDebtReason;
	}

	/**
	 * 设置 坏账原因
	 * 
	 * @param 坏账原因
	 */
	public void setBadDebtReason(String badDebtReason) {
		this.badDebtReason = badDebtReason;
	}


	/**
	 * 返回 收货客户编码
	 * 
	 * @return 收货客户编码
	 */
	public String getReceiveCustomerCode() {
		return receiveCustomerCode;
	}

	/**
	 * 设置 收货客户编码
	 * 
	 * @param 收货客户编码
	 */
	public void setReceiveCustomerCode(String receiveCustomerCode) {
		this.receiveCustomerCode = receiveCustomerCode;
	}

	/**
	 * 返回 收货客户名称
	 * 
	 * @return 收货客户名称
	 */
	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	/**
	 * 设置 收货客户名称
	 * 
	 * @param 收货客户名称
	 */
	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	/**
	 * 返回 发货客户名称
	 * 
	 * @return 发货客户名称
	 */
	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}

	/**
	 * 设置 发货客户名称
	 * 
	 * @param 发货客户名称
	 */
	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}

	/**
	 * 返回 发货客户编码
	 * 
	 * @return 发货客户编码
	 */
	public String getDeliveryCustomerName() {
		return deliveryCustomerName;
	}

	/**
	 * 设置 发货客户编码
	 * 
	 * @param 发货客户编码
	 */
	public void setDeliveryCustomerName(String deliveryCustomerName) {
		this.deliveryCustomerName = deliveryCustomerName;
	}

	/**
	 * 返回 客户联系电话
	 * 
	 * @return 客户联系电话
	 */
	public String getCustomerPhone() {
		return customerPhone;
	}

	/**
	 * 设置 客户联系电话
	 * 
	 * @param 客户联系电话
	 */
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	/**
	 * 返回 始发部门名称
	 * 
	 * @return 始发部门名称
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}

	/**
	 * 设置 始发部门名称
	 * 
	 * @param 始发部门名称
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	/**
	 * 返回 始发部门编码
	 * 
	 * @return 始发部门编码
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}

	/**
	 * 设置 始发部门编码
	 * 
	 * @param 始发部门编码
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	/**
	 * 返回 始发部门标杆编码
	 * 
	 * @return 始发部门标杆编码
	 */
	public String getOrigOrgUnifiedCode() {
		return origOrgUnifiedCode;
	}

	/**
	 * 设置 始发部门标杆编码
	 * 
	 * @param 始发部门标杆编码
	 */
	public void setOrigOrgUnifiedCode(String origOrgUnifiedCode) {
		this.origOrgUnifiedCode = origOrgUnifiedCode;
	}

	/**
	 * 返回 到达部门名称
	 * 
	 * @return 到达部门名称
	 */
	public String getDescOrgName() {
		return descOrgName;
	}

	/**
	 * 设置 到达部门名称
	 * 
	 * @param 到达部门名称
	 */
	public void setDescOrgName(String descOrgName) {
		this.descOrgName = descOrgName;
	}

	/**
	 * 返回 到达部门编码
	 * 
	 * @return 到达部门编码
	 */
	public String getDescOrgCode() {
		return descOrgCode;
	}

	/**
	 * 设置 到达部门编码
	 * 
	 * @param 到达部门编码
	 */
	public void setDescOrgCode(String descOrgCode) {
		this.descOrgCode = descOrgCode;
	}

	/**
	 * 返回 到达部门标杆编码
	 * 
	 * @return 到达部门标杆编码
	 */
	public String getDescOrgUnifiedCode() {
		return descOrgUnifiedCode;
	}

	/**
	 * 设置 到达部门标杆编码
	 * 
	 * @param 到达部门标杆编码
	 */
	public void setDescOrgUnifiedCode(String descOrgUnifiedCode) {
		this.descOrgUnifiedCode = descOrgUnifiedCode;
	}

	/**
	 * 返回 应付子公司名称
	 * 
	 * @return 应付子公司名称
	 */
	public String getPayableComName() {
		return payableComName;
	}

	/**
	 * 设置 应付子公司名称
	 * 
	 * @param 应付子公司名称
	 */
	public void setPayableComName(String payableComName) {
		this.payableComName = payableComName;
	}

	/**
	 * 返回 应付子公司编码
	 * 
	 * @return 应付子公司编码
	 */
	public String getPayableComCode() {
		return payableComCode;
	}

	/**
	 * 设置 应付子公司编码
	 * 
	 * @param 应付子公司编码
	 */
	public void setPayableComCode(String payableComCode) {
		this.payableComCode = payableComCode;
	}

	/**
	 * 返回 货物总件数
	 * 
	 * @return 货物总件数
	 */
	public Long getPackageQuantity() {
		return packageQuantity;
	}

	/**
	 * 设置 货物总件数
	 * 
	 * @param 货物总件数
	 */
	public void setPackageQuantity(Long packageQuantity) {
		this.packageQuantity = packageQuantity;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public BigDecimal getChargeWeight() {
		return chargeWeight;
	}

	public void setChargeWeight(BigDecimal chargeWeight) {
		this.chargeWeight = chargeWeight;
	}

	public BigDecimal getChargeVolume() {
		return chargeVolume;
	}

	public void setChargeVolume(BigDecimal chargeVolume) {
		this.chargeVolume = chargeVolume;
	}

	public BigDecimal getWoodenFrameVolume() {
		return woodenFrameVolume;
	}

	public void setWoodenFrameVolume(BigDecimal woodenFrameVolume) {
		this.woodenFrameVolume = woodenFrameVolume;
	}

	public BigDecimal getWoodenCrateVolume() {
		return woodenCrateVolume;
	}

	public void setWoodenCrateVolume(BigDecimal woodenCrateVolume) {
		this.woodenCrateVolume = woodenCrateVolume;
	}

	public Long getWoodenStockNumbers() {
		return woodenStockNumbers;
	}

	public void setWoodenStockNumbers(Long woodenStockNumbers) {
		this.woodenStockNumbers = woodenStockNumbers;
	}

	public Long getPackingBeltNumbers() {
		return packingBeltNumbers;
	}

	public void setPackingBeltNumbers(Long packingBeltNumbers) {
		this.packingBeltNumbers = packingBeltNumbers;
	}

	public BigDecimal getBattenSize() {
		return battenSize;
	}

	public void setBattenSize(BigDecimal battenSize) {
		this.battenSize = battenSize;
	}

	public BigDecimal getBubbleFilmVolume() {
		return bubbleFilmVolume;
	}

	public void setBubbleFilmVolume(BigDecimal bubbleFilmVolume) {
		this.bubbleFilmVolume = bubbleFilmVolume;
	}

	public BigDecimal getWrappingFilmVolume() {
		return wrappingFilmVolume;
	}

	public void setWrappingFilmVolume(BigDecimal wrappingFilmVolume) {
		this.wrappingFilmVolume = wrappingFilmVolume;
	}

	/**
	 * 返回 货物品名
	 * 
	 * @return 货物品名
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * 设置 货物品名
	 * 
	 * @param 货物品名
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * 返回 公布价运费
	 * 
	 * @return 公布价运费
	 */
	public BigDecimal getPublishFreinghtTax() {
		return publishFreinghtTax;
	}

	/**
	 * 设置 公布价运费
	 * 
	 * @param 公布价运费
	 */
	public void setPublishFreinghtTax(BigDecimal publishFreinghtTax) {
		this.publishFreinghtTax = publishFreinghtTax;
	}

	/**
	 * 返回 代收货款手续费
	 * 
	 * @return 代收货款手续费
	 */
	public BigDecimal getCollectionChargesPoundage() {
		return collectionChargesPoundage;
	}

	/**
	 * 设置 代收货款手续费
	 * 
	 * @param 代收货款手续费
	 */
	public void setCollectionChargesPoundage(BigDecimal collectionChargesPoundage) {
		this.collectionChargesPoundage = collectionChargesPoundage;
	}

	/**
	 * 返回 送货费
	 * 
	 * @return 送货费
	 */
	public BigDecimal getDeliveryFee() {
		return deliveryFee;
	}

	/**
	 * 设置 送货费
	 * 
	 * @param 送货费
	 */
	public void setDeliveryFee(BigDecimal deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	/**
	 * 返回 接货费
	 * 
	 * @return 接货费
	 */
	public BigDecimal getDoorServiceReceiveFee() {
		return doorServiceReceiveFee;
	}

	/**
	 * 设置 接货费
	 * 
	 * @param 接货费
	 */
	public void setDoorServiceReceiveFee(BigDecimal doorServiceReceiveFee) {
		this.doorServiceReceiveFee = doorServiceReceiveFee;
	}

	/**
	 * 返回 保价
	 * 
	 * @return 保价
	 */
	public BigDecimal getInsuranceStatement() {
		return insuranceStatement;
	}

	/**
	 * 设置 保价
	 * 
	 * @param 保价
	 */
	public void setInsuranceStatement(BigDecimal insuranceStatement) {
		this.insuranceStatement = insuranceStatement;
	}

	/**
	 * 返回 保价费
	 * 
	 * @return 保价费
	 */
	public BigDecimal getInsuranceFee() {
		return insuranceFee;
	}

	/**
	 * 设置 保价费
	 * 
	 * @param 保价费
	 */
	public void setInsuranceFee(BigDecimal insuranceFee) {
		this.insuranceFee = insuranceFee;
	}

	/**
	 * 返回 包装费
	 * 
	 * @return 包装费
	 */
	public BigDecimal getPackingFee() {
		return packingFee;
	}

	/**
	 * 设置 包装费
	 * 
	 * @param 包装费
	 */
	public void setPackingFee(BigDecimal packingFee) {
		this.packingFee = packingFee;
	}

	/**
	 * 返回 送货进仓费
	 * 
	 * @return 送货进仓费
	 */
	public BigDecimal getEnterWarehouseFee() {
		return enterWarehouseFee;
	}

	/**
	 * 设置 送货进仓费
	 * 
	 * @param 送货进仓费
	 */
	public void setEnterWarehouseFee(BigDecimal enterWarehouseFee) {
		this.enterWarehouseFee = enterWarehouseFee;
	}

	/**
	 * 返回 其他费用
	 * 
	 * @return 其他费用
	 */
	public BigDecimal getOtherFee() {
		return otherFee;
	}

	/**
	 * 设置 其他费用
	 * 
	 * @param 其他费用
	 */
	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}

	/**
	 * 返回 费率 保价
	 * 
	 * @return 费率 保价
	 */
	public BigDecimal getRate() {
		return rate;
	}

	/**
	 * 设置 费率 保价
	 * 
	 * @param 费率
	 *            保价
	 */
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	/**
	 * 返回 计价单位
	 * 
	 * @return 计价单位
	 */
	public String getChargeUnit() {
		return chargeUnit;
	}

	/**
	 * 设置 计价单位
	 * 
	 * @param 计价单位
	 */
	public void setChargeUnit(String chargeUnit) {
		this.chargeUnit = chargeUnit;
	}

	/**
	 * 返回 航班号
	 * 
	 * @return 航班号
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * 设置 航班号
	 * 
	 * @param 航班号
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * 返回 代理单号
	 * 
	 * @return 代理单号
	 */
	public String getAgentOrder() {
		return agentOrder;
	}

	/**
	 * 设置 代理单号
	 * 
	 * @param 代理单号
	 */
	public void setAgentOrder(String agentOrder) {
		this.agentOrder = agentOrder;
	}

	

	/**
	 * 返回 发货人手机号
	 * 
	 * @return 发货人手机号
	 */
	public String getConsignorPhone() {
		return consignorPhone;
	}

	/**
	 * 设置 发货人手机号
	 * 
	 * @param 发货人手机号
	 */
	public void setConsignorPhone(String consignorPhone) {
		this.consignorPhone = consignorPhone;
	}

	/**
	 * 返回 收货人
	 * 
	 * @return 收货人
	 */
	public String getConsignee() {
		return consignee;
	}

	/**
	 * 设置 收货人
	 * 
	 * @param 收货人
	 */
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	/**
	 * 返回 收货人手机号
	 * 
	 * @return 收货人手机号
	 */
	public String getConsigneePhone() {
		return consigneePhone;
	}

	/**
	 * 设置 收货人手机号
	 * 
	 * @param 收货人手机号
	 */
	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}

	/**
	 * 返回 目的省-市-区/县-镇
	 * 
	 * @return 目的省-市-区/县-镇
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * 设置 目的省-市-区/县-镇
	 * 
	 * @param 目的省-市-区/县-镇
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * 返回 提货方式
	 * 
	 * @return 提货方式
	 */
	public String getPickupWay() {
		return pickupWay;
	}

	/**
	 * 设置 提货方式
	 * 
	 * @param 提货方式
	 */
	public void setPickupWay(String pickupWay) {
		this.pickupWay = pickupWay;
	}

	/**
	 * 返回 折扣率 家装时取值
	 * 
	 * @return 折扣率 家装时取值
	 */
	public Long getDiscountRate() {
		return discountRate;
	}

	/**
	 * 设置 折扣率 家装时取值
	 * 
	 * @param 折扣率
	 *            家装时取值
	 */
	public void setDiscountRate(Long discountRate) {
		this.discountRate = discountRate;
	}

	/**
	 * 返回 理赔支付类别
	 * 
	 * @return 理赔支付类别
	 */
	public String getClaimPaymentCategories() {
		return claimPaymentCategories;
	}

	/**
	 * 设置 理赔支付类别
	 * 
	 * @param 理赔支付类别
	 */
	public void setClaimPaymentCategories(String claimPaymentCategories) {
		this.claimPaymentCategories = claimPaymentCategories;
	}

	/**
	 * 返回 车牌号
	 * 
	 * @return 车牌号
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * 设置 车牌号
	 * 
	 * @param 车牌号
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 返回 是否押回单
	 * 
	 * @return 是否押回单
	 */
	public String getBeReturnReceipt() {
		return beReturnReceipt;
	}

	/**
	 * 设置 是否押回单
	 * 
	 * @param 是否押回单
	 */
	public void setBeReturnReceipt(String beReturnReceipt) {
		this.beReturnReceipt = beReturnReceipt;
	}

	/**
	 * 返回 车辆出发日期
	 * 
	 * @return 车辆出发日期
	 */
	public Date getLeaveTime() {
		return leaveTime;
	}

	/**
	 * 设置 车辆出发日期
	 * 
	 * @param 车辆出发日期
	 */
	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}

	/**
	 * 返回 司机手机号码
	 * 
	 * @return 司机手机号码
	 */
	public String getDriverPhone() {
		return driverPhone;
	}

	/**
	 * 设置 司机手机号码
	 * 
	 * @param 司机手机号码
	 */
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	/**
	 * 返回 租车用途
	 * 
	 * @return 租车用途
	 */
	public String getRentCarUseType() {
		return rentCarUseType;
	}

	/**
	 * 设置 租车用途
	 * 
	 * @param 租车用途
	 */
	public void setRentCarUseType(String rentCarUseType) {
		this.rentCarUseType = rentCarUseType;
	}

	/**
	 * 返回 租车日期
	 * 
	 * @return 租车日期
	 */
	public Date getUseCarDate() {
		return useCarDate;
	}

	/**
	 * 设置 租车日期
	 * 
	 * @param 租车日期
	 */
	public void setUseCarDate(Date useCarDate) {
		this.useCarDate = useCarDate;
	}

	/**
	 * 返回 是否多次标记
	 * 
	 * @return 是否多次标记
	 */
	public String getIsRepeatemark() {
		return isRepeatemark;
	}

	/**
	 * 设置 是否多次标记
	 * 
	 * @param 是否多次标记
	 */
	public void setIsRepeatemark(String isRepeatemark) {
		this.isRepeatemark = isRepeatemark;
	}

	/**
	 * 返回 调整费用---调整类型
	 * 
	 * @return 调整费用---调整类型
	 */
	public String getAdjustType() {
		return adjustType;
	}

	/**
	 * 设置 调整费用---调整类型
	 * 
	 * @param 调整费用---调整类型
	 */
	public void setAdjustType(String adjustType) {
		this.adjustType = adjustType;
	}

	/**
	 * 返回 理赔：
	 * 
	 * @return 理赔：
	 */
	public String getClaimType() {
		return claimType;
	}

	/**
	 * 设置 理赔：
	 * 
	 * @param 理赔：
	 */
	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}

	/**
	 * 返回 理赔方式(1-正常理赔，2-快速理赔，3-在线理赔)
	 * 
	 * @return 理赔方式(1-正常理赔，2-快速理赔，3-在线理赔)
	 */
	public String getClaimWay() {
		return claimWay;
	}

	/**
	 * 设置 理赔方式(1-正常理赔，2-快速理赔，3-在线理赔)
	 * 
	 * @param 理赔方式(1-正常理赔，2-快速理赔，3-在线理赔)
	 */
	public void setClaimWay(String claimWay) {
		this.claimWay = claimWay;
	}

	/**
	 * 返回 责任信息 理赔使用
	 * 
	 * @return 责任信息 理赔使用
	 */
	public String getResponsibilityInfoJson() {
		return responsibilityInfoJson;
	}

	/**
	 * 设置 责任信息 理赔使用
	 * 
	 * @param 责任信息
	 *            理赔使用
	 */
	public void setResponsibilityInfoJson(String responsibilityInfoJson) {
		this.responsibilityInfoJson = responsibilityInfoJson;
	}

	/**
	 * 返回 银行支付信息 理赔使用
	 * 
	 * @return 银行支付信息 理赔使用
	 */
	public String getBankPayInfo() {
		return bankPayInfo;
	}

	/**
	 * 设置 银行支付信息 理赔使用
	 * 
	 * @param 银行支付信息
	 *            理赔使用
	 */
	public void setBankPayInfo(String bankPayInfo) {
		this.bankPayInfo = bankPayInfo;
	}

	/**
	 * 返回 最迟汇款日期
	 * 
	 * @return 最迟汇款日期
	 */
	public Date getPayBillLastTime() {
		return payBillLastTime;
	}

	/**
	 * 设置 最迟汇款日期
	 * 
	 * @param 最迟汇款日期
	 */
	public void setPayBillLastTime(Date payBillLastTime) {
		this.payBillLastTime = payBillLastTime;
	}

	/**
	 * 返回 *******************
	 * 
	 * @return *******************
	 */
	public String getErrorType() {
		return errorType;
	}

	/**
	 * 设置 *******************
	 * 
	 * @param *******************
	 */
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	/**
	 * 返回 费用类型
	 * 
	 * @return 费用类型
	 */
	public String getFeeType() {
		return feeType;
	}

	/**
	 * 设置 费用类型
	 * 
	 * @param 费用类型
	 */
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	/**
	 * 返回 仓储费用项列表
	 * @return feeTypeList 仓储费用项列表
	 */
	public List<FeeType> getFeeTypeList() {
		return feeTypeList;
	}

	/**
	 * 设置  仓储费用项列表
	 * @param feeTypeList 仓储费用项列表
	 */
	public void setFeeTypeList(List<FeeType> feeTypeList) {
		this.feeTypeList = feeTypeList;
        // 设置attribute
        this.feeType =  GsonUtils.getInstance().toJson(feeTypeList);
	}

	/**
	 * 返回
	 * 
	 * @return 小票收入类别
	 */
	public String getIncomeCategories() {
		return incomeCategories;
	}

	/**
	 * 设置
	 * 
	 * @param incomeCategories
	 *            小票收入类别
	 */
	public void setIncomeCategories(String incomeCategories) {
		this.incomeCategories = incomeCategories;
	}

	/**
	 * 返回
	 * 
	 * @return 小票支付编码
	 */
	public String getBatchNo() {
		return batchNo;
	}

	/**
	 * 设置
	 * 
	 * @param batchNo
	 *            小票支付编码
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	

	public String getUserCarReason() {
		return userCarReason;
	}

	public void setUserCarReason(String userCarReason) {
		this.userCarReason = userCarReason;
	}

	public BigDecimal getKmsNum() {
		return kmsNum;
	}

	public void setKmsNum(BigDecimal kmsNum) {
		this.kmsNum = kmsNum;
	}

	public BigDecimal getShallTakeGoodsQyt() {
		return shallTakeGoodsQyt;
	}

	public void setShallTakeGoodsQyt(BigDecimal shallTakeGoodsQyt) {
		this.shallTakeGoodsQyt = shallTakeGoodsQyt;
	}

	public BigDecimal getActualTakeGoodsQyt() {
		return actualTakeGoodsQyt;
	}

	public void setActualTakeGoodsQyt(BigDecimal actualTakeGoodsQyt) {
		this.actualTakeGoodsQyt = actualTakeGoodsQyt;
	}

	public String getVehicleLenghtCode() {
		return vehicleLenghtCode;
	}

	public void setVehicleLenghtCode(String vehicleLenghtCode) {
		this.vehicleLenghtCode = vehicleLenghtCode;
	}

	public String getReceivableOrgComName() {
        return receivableOrgComName;
    }

    public void setReceivableOrgComName(String receivableOrgComName) {
        this.receivableOrgComName = receivableOrgComName;
    }

    /**
	 * 描述
	 * @author 148235
	 * 时间：2016年12月21日下午10:58:31
	 */
	@Override
	public String toString() {
		return "TradeAttributeDO [origOrgName=" + origOrgName + ", origOrgCode=" + origOrgCode + ", subWaybillNos=" + subWaybillNos+ ", origOrgUnifiedCode="
				+ origOrgUnifiedCode + ", descOrgName=" + descOrgName + ", descOrgCode=" + descOrgCode
				+ ", descOrgUnifiedCode=" + descOrgUnifiedCode + ", payableComName=" + payableComName
				+ ", payableComCode=" + payableComCode + ", packageQuantity=" + packageQuantity + ", weight=" + weight
				+ ", volume=" + volume + ", chargeWeight=" + chargeWeight + ", chargeVolume=" + chargeVolume
				+ ", woodenFrameVolume=" + woodenFrameVolume + ", woodenCrateVolume=" + woodenCrateVolume
				+ ", woodenStockNumbers=" + woodenStockNumbers + ", packingBeltNumbers=" + packingBeltNumbers
				+ ", battenSize=" + battenSize + ", bubbleFilmVolume=" + bubbleFilmVolume + ", wrappingFilmVolume="
				+ wrappingFilmVolume + ", goodsName=" + goodsName + ", publishFreinghtTax=" + publishFreinghtTax
				+ ", collectionChargesPoundage=" + collectionChargesPoundage + ", deliveryFee=" + deliveryFee
				+ ", doorServiceReceiveFee=" + doorServiceReceiveFee + ", insuranceStatement=" + insuranceStatement
				+ ", insuranceFee=" + insuranceFee + ", packingFee=" + packingFee + ", enterWarehouseFee="
				+ enterWarehouseFee + ", otherFee=" + otherFee + ", rate=" + rate + ", chargeUnit=" + chargeUnit
				+ ", flightNumber=" + flightNumber + ", agentOrder=" + agentOrder + ", destination=" + destination
				+ ", pickupWay=" + pickupWay + ", discountRate=" + discountRate + ", claimPaymentCategories="
				+ claimPaymentCategories + ", vehicleNo=" + vehicleNo + ", beReturnReceipt=" + beReturnReceipt
				+ ", leaveTime=" + leaveTime + ", driverPhone=" + driverPhone + ", rentCarUseType=" + rentCarUseType
				+ ", useCarDate=" + useCarDate + ", isRepeatemark=" + isRepeatemark + ", adjustType=" + adjustType
				+ ", claimType=" + claimType + ", claimWay=" + claimWay + ", responsibilityInfoJson="
				+ responsibilityInfoJson + ", bankPayInfo=" + bankPayInfo + ", payBillLastTime=" + payBillLastTime
				+ ", errorType=" + errorType + ", feeType=" + feeType + ", feeTypeList=" + feeTypeList + ", customerPhone=" + customerPhone
				+ ", deliveryCustomerCode=" + deliveryCustomerCode + ", deliveryCustomerName=" + deliveryCustomerName
				+ ", receiveCustomerCode=" + receiveCustomerCode + ", receiveCustomerName=" + receiveCustomerName
				+ ", consignorPhone=" + consignorPhone + ", consignee=" + consignee
				+ ", consigneePhone=" + consigneePhone + ", incomeCategories="
				+ incomeCategories + ", batchNo=" + batchNo + ", workflowName=" + workflowName + ", billType="
				+ billType + ", mistakeNum=" + mistakeNum + ", badDebtReason=" + badDebtReason + ", userCarReason =" + userCarReason 
				+ ",kmsNum =" + kmsNum  + ",shallTakeGoodsQyt =" + shallTakeGoodsQyt 
				+ ",actualTakeGoodsQyt =" + actualTakeGoodsQyt  + ",vehicleLenghtCode =" + vehicleLenghtCode  + "]";
	}
	
}
