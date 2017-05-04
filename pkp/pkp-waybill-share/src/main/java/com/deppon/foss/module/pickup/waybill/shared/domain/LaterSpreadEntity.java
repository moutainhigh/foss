package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * @Description: 晚到补差价
 * @author hbhk
 * @date 2015-6-30 上午11:26:24
 */
public class LaterSpreadEntity extends BaseEntity {

	private static final long serialVersionUID = -7603074547820138738L;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 发货客户的手机号码
	 */
	private String phone;

	/**
	 * 产品类型（三级产品）
	 */
	private String productCode;

	/**
	 * 任务编号
	 */
	private String jobId;

	/**
	 * 失败原因
	 */
	private String failReason;

	/**
	 * 开单金额
	 * */
	private BigDecimal billAmount;

	/**
	 * 短信内容
	 * */
	private String smsContent;

	/**
	 * 客户编码
	 */
	private String customerCode;
	/**
	 * 差价
	 */
	private BigDecimal spread;
	/**
	 * 优惠券面额多个以,分隔
	 */
	private String couponAmount;

	/**
	 * 有效天数
	 */
	private Integer activeDateNum;
	
	private Date billTime;

	/**
	 * 卡航/城运 费用
	 * 
	 */
	private BigDecimal cartageFee;
	/**
	 * 普运费用
	 */
	private BigDecimal ordinaryFee;
	/**
	 * Y N
	 */
	private String isSend;

	private String notSendReason;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public BigDecimal getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(BigDecimal billAmount) {
		this.billAmount = billAmount;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public BigDecimal getSpread() {
		return spread;
	}

	public void setSpread(BigDecimal spread) {
		this.spread = spread;
	}

	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}


	public BigDecimal getCartageFee() {
		return cartageFee;
	}

	public void setCartageFee(BigDecimal cartageFee) {
		this.cartageFee = cartageFee;
	}

	public BigDecimal getOrdinaryFee() {
		return ordinaryFee;
	}

	public void setOrdinaryFee(BigDecimal ordinaryFee) {
		this.ordinaryFee = ordinaryFee;
	}

	public String getIsSend() {
		return isSend;
	}

	public void setIsSend(String isSend) {
		this.isSend = isSend;
	}

	public String getNotSendReason() {
		return notSendReason;
	}

	public void setNotSendReason(String notSendReason) {
		this.notSendReason = notSendReason;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public String getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(String couponAmount) {
		this.couponAmount = couponAmount;
	}

	public Integer getActiveDateNum() {
		return activeDateNum;
	}

	public void setActiveDateNum(Integer activeDateNum) {
		this.activeDateNum = activeDateNum;
	}
	
}
