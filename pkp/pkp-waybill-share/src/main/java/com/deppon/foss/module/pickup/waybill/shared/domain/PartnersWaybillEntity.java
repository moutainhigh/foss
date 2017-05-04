package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 合伙人运单信息表(用于存放折前费用和其他一些信息)
 * 
 * @author 272311-sangwenhao
 * @date 2016-1-16
 */
public class PartnersWaybillEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3213673151289093101L;
	/**
	 * id
	 */
	private String id;
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	//运单id
	private String waybillId ; 
	
	/**
	 * 代收货款手续费
	 */
	private BigDecimal codFee;
	/**
	 * 送货费
	 */
	private BigDecimal deliveryGoodsFee;
	/**
	 * 包装手续费
	 */
	private BigDecimal packageFee;
	/**
	 * 优惠费用
	 */
	private BigDecimal promotionsFee;
	/**
	 * 公布价运费
	 */
	private BigDecimal transportFee;
	/**
	 * 增值费用
	 */
	private BigDecimal valueAddFee;
	/**
	 * 接货费
	 */
	private BigDecimal pickupFee;
	/**
	 * 劳务费
	 */
	private BigDecimal serviceFee;
	/**
	 * 保价费
	 */
	private BigDecimal insuranceFee;
	/**
	 * 总费用
	 */
	private BigDecimal totalFee;

	// 上楼费
	private String upFloorFee;

	// 打木架货物费用
	private BigDecimal standCharge;

	// 打木箱货物费用
	private BigDecimal boxCharge;

	// 打木托货物费用
	private BigDecimal salverGoodsCharge;
	
	private BigDecimal otherFee ;
	
	private String active ;
	
	//创建时间
	private Date createTime ;
	
	//修改时间
	private Date modifyTime ;
	//备注
	private String remark ;
	
	//是否快递
	private String isExpress ;
	//更改单类型
	private String changeType ;
	//超远派送费
	private BigDecimal overDistanceFeeOrg;
	//基础送货费
	private BigDecimal baseDeliveryGoodsFeeOrg;
	//签收单
	private BigDecimal signBillFeeOrg;
	//异常操作服务费
	private BigDecimal exceptionOpreationFee;
	//大件上楼费
	private BigDecimal bigGoodsUpFloorFee;
	//送货进仓费
	private BigDecimal deliveryWareHouseFee;
	//送货安装费
	private BigDecimal pickupToDoorJZFee;
	//转运返货是否重新计算公布价运费
	private String isCalTraFee;
	//重新计算公布价运费出发部门
	private String startOrgCodeCal;
	//重新计算公布价运费到达部门
	private String destinationOrgCodeCal;
	//合伙人到达加收金额 2016年9月1日 09:47:19 葛亮亮
	private BigDecimal overTransportFee;
	//合伙人到达加收费率 2016年9月13日14:23:57 葛亮亮
	private BigDecimal overTransportRate;
	
	//添加优惠劵金额--合伙人第二批需求--zoushengli
	//优惠券金额
	private BigDecimal couponFee;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public BigDecimal getCodFee() {
		return codFee;
	}

	public void setCodFee(BigDecimal codFee) {
		this.codFee = codFee;
	}

	public BigDecimal getDeliveryGoodsFee() {
		return deliveryGoodsFee;
	}

	public void setDeliveryGoodsFee(BigDecimal deliveryGoodsFee) {
		this.deliveryGoodsFee = deliveryGoodsFee;
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

	public BigDecimal getInsuranceFee() {
		return insuranceFee;
	}

	public void setInsuranceFee(BigDecimal insuranceFee) {
		this.insuranceFee = insuranceFee;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public String getUpFloorFee() {
		return upFloorFee;
	}

	public void setUpFloorFee(String upFloorFee) {
		this.upFloorFee = upFloorFee;
	}

	public BigDecimal getStandCharge() {
		return standCharge;
	}

	public void setStandCharge(BigDecimal standCharge) {
		this.standCharge = standCharge;
	}

	public BigDecimal getBoxCharge() {
		return boxCharge;
	}

	public void setBoxCharge(BigDecimal boxCharge) {
		this.boxCharge = boxCharge;
	}

	public BigDecimal getSalverGoodsCharge() {
		return salverGoodsCharge;
	}

	public void setSalverGoodsCharge(BigDecimal salverGoodsCharge) {
		this.salverGoodsCharge = salverGoodsCharge;
	}

	public BigDecimal getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsExpress() {
		return isExpress;
	}

	public void setIsExpress(String isExpress) {
		this.isExpress = isExpress;
	}

	public String getWaybillId() {
		return waybillId;
	}

	public void setWaybillId(String waybillId) {
		this.waybillId = waybillId;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public BigDecimal getOverDistanceFeeOrg() {
		return overDistanceFeeOrg;
	}

	public BigDecimal getBaseDeliveryGoodsFeeOrg() {
		return baseDeliveryGoodsFeeOrg;
	}

	public BigDecimal getSignBillFeeOrg() {
		return signBillFeeOrg;
	}

	public void setOverDistanceFeeOrg(BigDecimal overDistanceFeeOrg) {
		this.overDistanceFeeOrg = overDistanceFeeOrg;
	}

	public void setBaseDeliveryGoodsFeeOrg(BigDecimal baseDeliveryGoodsFeeOrg) {
		this.baseDeliveryGoodsFeeOrg = baseDeliveryGoodsFeeOrg;
	}

	public void setSignBillFeeOrg(BigDecimal signBillFeeOrg) {
		this.signBillFeeOrg = signBillFeeOrg;
	}

	public BigDecimal getExceptionOpreationFee() {
		return exceptionOpreationFee;
	}

	public void setExceptionOpreationFee(BigDecimal exceptionOpreationFee) {
		this.exceptionOpreationFee = exceptionOpreationFee;
	}

	public BigDecimal getBigGoodsUpFloorFee() {
		return bigGoodsUpFloorFee;
	}

	public BigDecimal getDeliveryWareHouseFee() {
		return deliveryWareHouseFee;
	}

	public BigDecimal getPickupToDoorJZFee() {
		return pickupToDoorJZFee;
	}

	public void setBigGoodsUpFloorFee(BigDecimal bigGoodsUpFloorFee) {
		this.bigGoodsUpFloorFee = bigGoodsUpFloorFee;
	}

	public void setDeliveryWareHouseFee(BigDecimal deliveryWareHouseFee) {
		this.deliveryWareHouseFee = deliveryWareHouseFee;
	}

	public void setPickupToDoorJZFee(BigDecimal pickupToDoorJZFee) {
		this.pickupToDoorJZFee = pickupToDoorJZFee;
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

	public BigDecimal getOverTransportFee() {
		return overTransportFee;
	}

	public void setOverTransportFee(BigDecimal overTransportFee) {
		this.overTransportFee = overTransportFee;
	}

	public BigDecimal getOverTransportRate() {
		return overTransportRate;
	}

	public void setOverTransportRate(BigDecimal overTransportRate) {
		this.overTransportRate = overTransportRate;
	}

	public BigDecimal getCouponFee() {
		return couponFee;
	}

	public void setCouponFee(BigDecimal couponFee) {
		this.couponFee = couponFee;
	}
	
	 /**
     * 偏线费  352676
     */
    private BigDecimal partialTransportFee;

	public BigDecimal getPartialTransportFee() {
		return partialTransportFee;
	}

	public void setPartialTransportFee(BigDecimal partialTransportFee) {
		this.partialTransportFee = partialTransportFee;
	}


}
