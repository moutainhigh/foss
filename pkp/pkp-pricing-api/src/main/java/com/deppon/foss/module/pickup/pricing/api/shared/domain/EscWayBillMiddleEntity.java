package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.WaybillDiscountVo;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EscValuationEntryDto;
/**
 * 中间转化实体类
 * @author Foss-308595-GELL
 *
 */
public class EscWayBillMiddleEntity implements Serializable {
	
	/**
	 * 代收货款金额
	 */
	private BigDecimal codAmount;

	/**
	 * 保价声明价值 
	 */
	private BigDecimal insuranceAmount;
	
    /**
     * 折扣信息
     */
	private List<WaybillDiscountVo> waybillDiscountVo;
    
	/**
	 * 公布价运费 
	 */
	private BigDecimal transportFee;
	
	/**
	 * 未冲减优惠券之前的运费
	 */
	private BigDecimal beforeProTranFee;
		
	/**
	 * 公布价最低一票
	 */
	private BigDecimal minTransportFee;
	
	/**
	 * 包装费
	 */
	private BigDecimal packageFee;
	
	/**
	 * 保价费
	 */
	private BigDecimal insuranceFee;
	
	/**
	 * 代收货款手续费
	 */
	private BigDecimal codFee;
	
	/**
	 * 送货费
	 */	
	private BigDecimal deliveryGoodsFee;  
	
	/**
	 * 到付金额
	 */
	private BigDecimal toPayFee;
	/**
	 *预付金额 
	 */
	private BigDecimal prePayFee;
	
	/**
	 * 公布价运费
	 */
	private BigDecimal TransportFee;
	
	/**
	 * 优惠总金额
	 */
	private BigDecimal promotionsFee;
	
	/**
	 * 增值费用
	 */
	private BigDecimal valueAddFee;
	
	/**
	 * 其他费用
	 */
	private BigDecimal otherFee;
	
	/**
	 * 其他费用明细
	 */
	private List<EscValuationEntryDto> otherChargeVos;
	
	/**
	 * 总金额
	 */
	private BigDecimal totalFee;
	
	/**
	 * 签收回单费（悟空要求单独出来不累加到公布价运费）
	 */
	private BigDecimal qsFee;
	
	/**
	 * 签收回单，反单类别
	 */
	private String qsSubType;
	
	/**
	 * 增值服务费
	 */	
	private BigDecimal incrementFee;
			
	/**
	 * 优惠卷费用
	 */
	private BigDecimal couponFree;
	
	/**
     * 优惠券优惠费用归集类型
     */
    private String couponRankType;
    
    /**
	 * 标示是否已经减去优惠券
	 */
	private String flagTakeCoupon;
	
	//优惠卷
	private CouponInfoDto  couponInfoDto;
	
	//从产品价格获取的价格信息
    List<GuiResultBillCalculateDto> guiResultBillCalculateDtos;
    
    //加收方式(前台页面显示)
  	private BigDecimal servicefee;
  	
    //折让方式-装卸费(数据库存储的费用)
  	private BigDecimal dcServicefee;
  	
  	//装卸费是否在前台页面展示（false：不在前台页面展示且不能编辑）
  	private boolean serviceChargeFlag;
  	
  	//计费重量 
  	private BigDecimal billWeight;
  	
  	//运费费率（运费费率需要单独重算，没有在计价方法中返回）
  	private BigDecimal feeRate;
  	
  	/**
  	 * 更改单
  	 */
  	//是否计算了公布价
	private String isCalTraFee;
	
	//公布价运费总优惠金额（因为公布价运费有情况下不会重新计算）
	private BigDecimal frtDisFee;
	
	public BigDecimal getCouponFree() {
		return couponFree;
	}

	public void setCouponFree(BigDecimal couponFree) {
		this.couponFree = couponFree;
	}

	public String getCouponRankType() {
		return couponRankType;
	}

	public void setCouponRankType(String couponRankType) {
		this.couponRankType = couponRankType;
	}

	public String getFlagTakeCoupon() {
		return flagTakeCoupon;
	}

	public void setFlagTakeCoupon(String flagTakeCoupon) {
		this.flagTakeCoupon = flagTakeCoupon;
	}

	public BigDecimal getPackageFee() {
		return packageFee;
	}

	public void setPackageFee(BigDecimal packageFee) {
		this.packageFee = packageFee;
	}

	public BigDecimal getInsuranceFee() {
		return insuranceFee;
	}

	public void setInsuranceFee(BigDecimal insuranceFee) {
		this.insuranceFee = insuranceFee;
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

	public BigDecimal getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}

	public BigDecimal getIncrementFee() {
		return incrementFee;
	}

	public void setIncrementFee(BigDecimal incrementFee) {
		this.incrementFee = incrementFee;
	}

	public BigDecimal getTransportFee() {
		return transportFee;
	}

	public void setTransportFee(BigDecimal transportFee) {
		this.transportFee = transportFee;
	}

	public CouponInfoDto getCouponInfoDto() {
		return couponInfoDto;
	}

	public void setCouponInfoDto(CouponInfoDto couponInfoDto) {
		this.couponInfoDto = couponInfoDto;
	}

	public List<WaybillDiscountVo> getWaybillDiscountVo() {
		return waybillDiscountVo;
	}

	public void setWaybillDiscountVo(List<WaybillDiscountVo> waybillDiscountVo) {
		this.waybillDiscountVo = waybillDiscountVo;
	}

	public BigDecimal getPromotionsFee() {
		return promotionsFee;
	}

	public void setPromotionsFee(BigDecimal promotionsFee) {
		this.promotionsFee = promotionsFee.setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入;
	}

	public BigDecimal getMinTransportFee() {
		return minTransportFee;
	}

	public void setMinTransportFee(BigDecimal minTransportFee) {
		this.minTransportFee = minTransportFee;
	}

	public BigDecimal getBeforeProTranFee() {
		return beforeProTranFee;
	}

	public void setBeforeProTranFee(BigDecimal beforeProTranFee) {
		this.beforeProTranFee = beforeProTranFee;
	}

	public List<GuiResultBillCalculateDto> getGuiResultBillCalculateDtos() {
		return guiResultBillCalculateDtos;
	}

	public void setGuiResultBillCalculateDtos(
			List<GuiResultBillCalculateDto> guiResultBillCalculateDtos) {
		this.guiResultBillCalculateDtos = guiResultBillCalculateDtos;
	}

	public BigDecimal getToPayFee() {
		return toPayFee;
	}

	public void setToPayFee(BigDecimal toPayFee) {
		this.toPayFee = toPayFee;
	}

	public BigDecimal getPrePayFee() {
		return prePayFee;
	}

	public void setPrePayFee(BigDecimal prePayFee) {
		this.prePayFee = prePayFee;
	}

	public BigDecimal getValueAddFee() {
		return valueAddFee;
	}

	public void setValueAddFee(BigDecimal valueAddFee) {
		this.valueAddFee = valueAddFee;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public BigDecimal getQsFee() {
		return qsFee;
	}

	public void setQsFee(BigDecimal qsFee) {
		this.qsFee = qsFee;
	}

	public BigDecimal getDcServicefee() {
		return dcServicefee;
	}

	public void setDcServicefee(BigDecimal dcServicefee) {
		this.dcServicefee = dcServicefee;
	}

	public BigDecimal getServicefee() {
		return servicefee;
	}

	public void setServicefee(BigDecimal servicefee) {
		this.servicefee = servicefee;
	}

	public boolean isServiceChargeFlag() {
		return serviceChargeFlag;
	}

	public void setServiceChargeFlag(boolean serviceChargeFlag) {
		this.serviceChargeFlag = serviceChargeFlag;
	}

	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public String getIsCalTraFee() {
		return isCalTraFee;
	}

	public void setIsCalTraFee(String isCalTraFee) {
		this.isCalTraFee = isCalTraFee;
	}

	public BigDecimal getCodAmount() {
		return codAmount;
	}

	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}

	public String getQsSubType() {
		return qsSubType;
	}

	public void setQsSubType(String qsSubType) {
		this.qsSubType = qsSubType;
	}

	public List<EscValuationEntryDto> getOtherChargeVos() {
		return otherChargeVos;
	}

	public void setOtherChargeVos(List<EscValuationEntryDto> otherChargeVos) {
		this.otherChargeVos = otherChargeVos;
	}

	public BigDecimal getBillWeight() {
		return billWeight;
	}

	public void setBillWeight(BigDecimal billWeight) {
		this.billWeight = billWeight;
	}

	public BigDecimal getFeeRate() {
		return feeRate;
	}

	public void setFeeRate(BigDecimal feeRate) {
		this.feeRate = feeRate;
	}

	public BigDecimal getFrtDisFee() {
		return frtDisFee;
	}

	public void setFrtDisFee(BigDecimal frtDisFee) {
		this.frtDisFee = frtDisFee;
	}
}
