package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 待处理返券日志类
 * */
public class PendingSendCouponLogEntity extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9206888686935694146L;

	/**
	 * 编号
	 */
	private String id;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 开单时间
	 */
	private Date billTime;
	
	/**
	 * 发货客户的手机号码
	 */
	private String deliveryCustomerMobilephone;
	
	/**
	 * 优惠券金额
	 */
	private BigDecimal promotionsFee;
	
	
	/**
	 * 产品类型（三级产品）
	 */
	private String productCode;
	
	/**
	 * 订单来源
	 */
	private String orderChannel;
	
	/**
	 * 客户等级
	 */
	private String customerDegree;
	
	/**
	 * 客户行业
	 */
	private String customerIndustry;
	
	/**
	 * 优惠券起始时间
	 */
	private Date couponBeginTime;
	
	/**
	 * 优惠券终止时间
	 */
	private Date couponEndTime;
	
	/**
	 * 线路区域要求
	 */
	private String lineArea;
	
	
	/**
	 * 返券时间（相对于开单时间而言）
	 */
	private BigDecimal sendCouponTime;
	
	/**
	 * 任务编号
	 */
	private String jobId;
	
	/**
	 * 失败原因
	 */
	private String faileReason;
	
	/**
	 * 操作类型
	 */
	private String operateType;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	
	/**
	 * 是否返券
	 */
	private String sendCoupon;
	
	/**
	 * 开单金额
	 * */
	private BigDecimal billAmount;
	/**
	 * 出发行政区域编码
	 * */
	private String receiveCustomerCityCode;
	/**
	 * 出发行政区域名称
	 * */
	private String receiveCustomerCityName;
	/**
	 * 到达行政区域编码
	 * */
	private String deliveryCustomerCityCode;
	/**
	 * 到达行政区域名称
	 * */
	private String deliveryCustomerCityName;
	/**
	 * 短信内容
	 * */
	private String sendCouponContent;
	
	/**
	 * 优惠券编码
	 * */
	private String couponNo;
	/**
	 * crm优惠券异常
	 * */
	private String couponExcep;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	public String getDeliveryCustomerMobilephone() {
		return deliveryCustomerMobilephone;
	}

	public void setDeliveryCustomerMobilephone(String deliveryCustomerMobilephone) {
		this.deliveryCustomerMobilephone = deliveryCustomerMobilephone;
	}

	public BigDecimal getPromotionsFee() {
		return promotionsFee;
	}

	public void setPromotionsFee(BigDecimal promotionsFee) {
		this.promotionsFee = promotionsFee;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getOrderChannel() {
		return orderChannel;
	}

	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}

	public String getCustomerDegree() {
		return customerDegree;
	}

	public void setCustomerDegree(String customerDegree) {
		this.customerDegree = customerDegree;
	}

	public String getCustomerIndustry() {
		return customerIndustry;
	}

	public void setCustomerIndustry(String customerIndustry) {
		this.customerIndustry = customerIndustry;
	}

	public Date getCouponBeginTime() {
		return couponBeginTime;
	}

	public void setCouponBeginTime(Date couponBeginTime) {
		this.couponBeginTime = couponBeginTime;
	}

	public Date getCouponEndTime() {
		return couponEndTime;
	}

	public void setCouponEndTime(Date couponEndTime) {
		this.couponEndTime = couponEndTime;
	}

	public String getLineArea() {
		return lineArea;
	}

	public void setLineArea(String lineArea) {
		this.lineArea = lineArea;
	}


	public BigDecimal getSendCouponTime() {
		return sendCouponTime;
	}

	public void setSendCouponTime(BigDecimal sendCouponTime) {
		this.sendCouponTime = sendCouponTime;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getFaileReason() {
		return faileReason;
	}

	public void setFaileReason(String faileReason) {
		this.faileReason = faileReason;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
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

	public String getSendCoupon() {
		return sendCoupon;
	}

	public void setSendCoupon(String sendCoupon) {
		this.sendCoupon = sendCoupon;
	}

	public BigDecimal getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(BigDecimal billAmount) {
		this.billAmount = billAmount;
	}

	public String getReceiveCustomerCityCode() {
		return receiveCustomerCityCode;
	}

	public void setReceiveCustomerCityCode(String receiveCustomerCityCode) {
		this.receiveCustomerCityCode = receiveCustomerCityCode;
	}

	public String getReceiveCustomerCityName() {
		return receiveCustomerCityName;
	}

	public void setReceiveCustomerCityName(String receiveCustomerCityName) {
		this.receiveCustomerCityName = receiveCustomerCityName;
	}

	public String getDeliveryCustomerCityCode() {
		return deliveryCustomerCityCode;
	}

	public void setDeliveryCustomerCityCode(String deliveryCustomerCityCode) {
		this.deliveryCustomerCityCode = deliveryCustomerCityCode;
	}

	public String getDeliveryCustomerCityName() {
		return deliveryCustomerCityName;
	}

	public void setDeliveryCustomerCityName(String deliveryCustomerCityName) {
		this.deliveryCustomerCityName = deliveryCustomerCityName;
	}

	public String getSendCouponContent() {
		return sendCouponContent;
	}

	public void setSendCouponContent(String sendCouponContent) {
		this.sendCouponContent = sendCouponContent;
	}

	public String getCouponNo() {
		return couponNo;
	}

	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}

	public String getCouponExcep() {
		return couponExcep;
	}

	public void setCouponExcep(String couponExcep) {
		this.couponExcep = couponExcep;
	}

	
	
}
