package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.dto.MarkActivitiesQueryConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EscValuationEntryDto;


/**
 * 价格计算参数请求实体
 * 葛亮亮 2016年5月8日 12:20:46 
 *
 */
public class EscWayBillRequestEntity implements Serializable{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 6454201944890522227L;
	
	/**
   	 * 伙伴开单
   	 */
   	private Boolean  partnerBilling;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 出发部门
	 */
    private String originalOrgCode;
    
    /**
     * 到达部门
     */
    private String destinationOrgCode;
    
    /**
     * 产品类型
     */
    private String productCode;

    /**
     * 开单日期
     */
    private Date billDate;
    
    /**
     * 重量
     */
    private BigDecimal weight;
    
    /**
     * 体积
     */
    private BigDecimal volume;
    
    /**
     * 币种
     */
    private String currencyCdoe;
    
    /**
     * 客户编码
     */
    private String customerCode;
    
    /**
     * 计价条目
     */
    private String pricingEntryCode;
    
    /**
     * 子类型   对于代收货款：为退款类型(即日退，三日退，审核退) 对于签收回单：为返单类型（传真返单，原件返单）
	 * 对于其它费用：为费用类型名称（综合信息费，燃油附加费，中转费等）
     */
    private String subType;
    
    /**
     * 付款方式
     */
    private String paidMethod;
    
    /**
     * 计价条目列表
     */
	private List<EscValuationEntryDto> priceEntities;
	
   //菜鸟返货折扣
    private Boolean isCainiao;//是否满足条件
    private String returnWaybillNo;//原始单号
    private String oldreceiveCustomerCode;//原收货人的客户编码
    private Date returnbilltime;//原始开单时间
    private BigDecimal returnTransportFee;//原单公布价费
    private BigDecimal returnInsuranceFee;//原单保价费
    private String OriginalReceiveOrgCode;//原单号收货部门编码
	
    /**
     * 开单类型
     */
    private String returnType;
    
    /**
     * 原单号
     */
    private String oriWaybill;
    
   /**
    * 活动编码
    */
    private String activeCode;
    /**
     * 货物名称
     */
    private String goodsName;
    /**
     * 订单来源
     */
    private String orderChannel;
    
    /**
     * 优惠券编码
     */
    private String promotionsCode;
    
    /**
     * 内部发货类型	
     */
  	private String  internalDeliveryType;
  	/**
  	 * 员工号
  	 */
  	private String  employeeNo;
  	
  	/** 渠道code. */
    private String channelCode;
    
    //营销活动DTO
    private MarkActivitiesQueryConditionDto activeDto;
  	
    /**
     * 返货原因
     */
	private String returnBillReason;
	
	// 最终配载部门编号
	private String lastLoadOrgCode;
	
	// 订单号
	private String orderNo;
	
	// 配载部门编号
	private String loadOrgCode;
	
	// 最终外场编码
	private String lastOutLoadOrgCode;
	
	//加收方式(前台页面显示)
  	private BigDecimal servicefee;
  	
  	//是否需要计算装卸费
  	private boolean isCalculateServicefee;
	
	//是否改单 N:开单，Y：改单
	private String isChange;
	
	//更改类型
	private String rfcType;
	
	//货物当前库存状态（例如收货部门库存，出收货部门）
	private String inventory;
	
	//公布价运费总优惠金额（因为公布价运费有情况下不会重新计算）
	private BigDecimal frtDisFee;
	
	//优惠券金额（改单时需要把开单时优惠券的金额传过来直接用，因为优惠券的金额不一定就是CRM中设置的金额）
	private BigDecimal couponFree;
	
	//优惠券类型（快递优惠券类型只有 运费FRT和代收货款HK两种）
	private String couponRankType;
	
	//内部员工当月优惠金额（由悟空算好当月总优惠金额接口直接使用）
	private BigDecimal employeeAmount;
	
	//改单时运单元来内部员工工号
	private String originalEmployeeNo;
	
	//更改运单时内部员工原来优惠金额
	private BigDecimal originalFee;
	
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getOriginalOrgCode() {
		return originalOrgCode;
	}
	public void setOriginalOrgCode(String originalOrgCode) {
		this.originalOrgCode = originalOrgCode;
	}
	public String getDestinationOrgCode() {
		return destinationOrgCode;
	}
	public void setDestinationOrgCode(String destinationOrgCode) {
		this.destinationOrgCode = destinationOrgCode;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
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
	public String getCurrencyCdoe() {
		return currencyCdoe;
	}
	public void setCurrencyCdoe(String currencyCdoe) {
		this.currencyCdoe = currencyCdoe;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getPricingEntryCode() {
		return pricingEntryCode;
	}
	public void setPricingEntryCode(String pricingEntryCode) {
		this.pricingEntryCode = pricingEntryCode;
	}
	public String getPaidMethod() {
		return paidMethod;
	}
	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}
	public List<EscValuationEntryDto> getPriceEntities() {
		return priceEntities;
	}
	public void setPriceEntities(List<EscValuationEntryDto> priceEntities) {
		this.priceEntities = priceEntities;
	}
	public Boolean getIsCainiao() {
		return isCainiao;
	}
	public void setIsCainiao(Boolean isCainiao) {
		this.isCainiao = isCainiao;
	}
	public String getReturnWaybillNo() {
		return returnWaybillNo;
	}
	public void setReturnWaybillNo(String returnWaybillNo) {
		this.returnWaybillNo = returnWaybillNo;
	}
	public String getOldreceiveCustomerCode() {
		return oldreceiveCustomerCode;
	}
	public void setOldreceiveCustomerCode(String oldreceiveCustomerCode) {
		this.oldreceiveCustomerCode = oldreceiveCustomerCode;
	}
	public Date getReturnbilltime() {
		return returnbilltime;
	}
	public void setReturnbilltime(Date returnbilltime) {
		this.returnbilltime = returnbilltime;
	}
	public BigDecimal getReturnTransportFee() {
		return returnTransportFee;
	}
	public void setReturnTransportFee(BigDecimal returnTransportFee) {
		this.returnTransportFee = returnTransportFee;
	}
	public BigDecimal getReturnInsuranceFee() {
		return returnInsuranceFee;
	}
	public void setReturnInsuranceFee(BigDecimal returnInsuranceFee) {
		this.returnInsuranceFee = returnInsuranceFee;
	}
	public String getOriginalReceiveOrgCode() {
		return OriginalReceiveOrgCode;
	}
	public void setOriginalReceiveOrgCode(String originalReceiveOrgCode) {
		OriginalReceiveOrgCode = originalReceiveOrgCode;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public String getOriWaybill() {
		return oriWaybill;
	}
	public void setOriWaybill(String oriWaybill) {
		this.oriWaybill = oriWaybill;
	}
	public String getActiveCode() {
		return activeCode;
	}
	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getOrderChannel() {
		return orderChannel;
	}
	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}
	public String getPromotionsCode() {
		return promotionsCode;
	}
	public void setPromotionsCode(String promotionsCode) {
		this.promotionsCode = promotionsCode;
	}
	public String getInternalDeliveryType() {
		return internalDeliveryType;
	}
	public void setInternalDeliveryType(String internalDeliveryType) {
		this.internalDeliveryType = internalDeliveryType;
	}
	public String getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public MarkActivitiesQueryConditionDto getActiveDto() {
		return activeDto;
	}
	public void setActiveDto(MarkActivitiesQueryConditionDto activeDto) {
		this.activeDto = activeDto;
	}
	public String getReturnBillReason() {
		return returnBillReason;
	}
	public void setReturnBillReason(String returnBillReason) {
		this.returnBillReason = returnBillReason;
	}
	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}
	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getLoadOrgCode() {
		return loadOrgCode;
	}
	public void setLoadOrgCode(String loadOrgCode) {
		this.loadOrgCode = loadOrgCode;
	}
	public String getLastOutLoadOrgCode() {
		return lastOutLoadOrgCode;
	}
	public void setLastOutLoadOrgCode(String lastOutLoadOrgCode) {
		this.lastOutLoadOrgCode = lastOutLoadOrgCode;
	}
	public String getInventory() {
		return inventory;
	}
	public void setInventory(String inventory) {
		this.inventory = inventory;
	}
	public String getIsChange() {
		return isChange;
	}
	public void setIsChange(String isChange) {
		this.isChange = isChange;
	}
	public String getRfcType() {
		return rfcType;
	}
	public void setRfcType(String rfcType) {
		this.rfcType = rfcType;
	}
	public Boolean getPartnerBilling() {
		return partnerBilling;
	}
	public void setPartnerBilling(Boolean partnerBilling) {
		this.partnerBilling = partnerBilling;
	}
	public BigDecimal getServicefee() {
		return servicefee;
	}
	public void setServicefee(BigDecimal servicefee) {
		this.servicefee = servicefee;
	}	
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
	public BigDecimal getFrtDisFee() {
		return frtDisFee;
	}
	public void setFrtDisFee(BigDecimal frtDisFee) {
		this.frtDisFee = frtDisFee;
	}
	public boolean getIsCalculateServicefee() {
		return isCalculateServicefee;
	}
	public void setIsCalculateServicefee(boolean isCalculateServicefee) {
		this.isCalculateServicefee = isCalculateServicefee;
	}
	public BigDecimal getEmployeeAmount() {
		return employeeAmount;
	}
	public void setEmployeeAmount(BigDecimal employeeAmount) {
		this.employeeAmount = employeeAmount;
	}
	public String getOriginalEmployeeNo() {
		return originalEmployeeNo;
	}
	public void setOriginalEmployeeNo(String originalEmployeeNo) {
		this.originalEmployeeNo = originalEmployeeNo;
	}
	public BigDecimal getOriginalFee() {
		return originalFee;
	}
	public void setOriginalFee(BigDecimal originalFee) {
		this.originalFee = originalFee;
	}
}
