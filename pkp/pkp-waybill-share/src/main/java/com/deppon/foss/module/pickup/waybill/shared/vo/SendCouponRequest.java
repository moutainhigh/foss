package com.deppon.foss.module.pickup.waybill.shared.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="SendCouponRequest")
public class SendCouponRequest {
	//来源运单号
	protected String sourceWBNumber;
	//	渠道来源（FOSS）
	protected String request;
	//申请时间
	protected Long requestTime;
	//来源运单金额
	protected String sourceWBValue;
	//发送优惠劵手机号码
	protected String sendtelPhone;
	//短信内容
	protected String smsContent;
	//优惠券使用开始日期
	protected Long begintime;
	//优惠券使用结束日期
	protected Long endtime;
	//抵扣类型
	protected String discountType;
	//优惠券金额
	protected List<String> useCouponValue;
	//金额要求模式
	protected String costMode;
	//金额可选类型
	protected String costType;
	//金额要求1（每满X元减Y元）
	protected String value;
	//金额要求2（不低于X元）
	protected String discount;
	//增值费类型（包装费、保价费、代收费、送货费、接货费不低于多少）
	protected String costAddedType;
	//增值费抵扣金额
	protected String costAdded;
	//产品类型
	protected List<String> productTypes;
	//订单来源
	protected List<String> orderSources;
	//客户等级（普通客户、黄金、铂金、钻石）
	protected List<String> custLevels;
	//客户行业
	protected List<String> custTrades;
	//描述
	protected String describe;
	/**
	 * @return the sourceWBNumber
	 */
	public String getSourceWBNumber() {
		return sourceWBNumber;
	}
	/**
	 * @param sourceWBNumber the sourceWBNumber to set
	 */
	public void setSourceWBNumber(String sourceWBNumber) {
		this.sourceWBNumber = sourceWBNumber;
	}
	/**
	 * @return the request
	 */
	public String getRequest() {
		return request;
	}
	/**
	 * @param request the request to set
	 */
	public void setRequest(String request) {
		this.request = request;
	}
	/**
	 * @return the requestTime
	 */
	public Long getRequestTime() {
		return requestTime;
	}
	/**
	 * @param requestTime the requestTime to set
	 */
	public void setRequestTime(Long requestTime) {
		this.requestTime = requestTime;
	}
	/**
	 * @return the sourceWBValue
	 */
	public String getSourceWBValue() {
		return sourceWBValue;
	}
	/**
	 * @param sourceWBValue the sourceWBValue to set
	 */
	public void setSourceWBValue(String sourceWBValue) {
		this.sourceWBValue = sourceWBValue;
	}
	/**
	 * @return the sendtelPhone
	 */
	public String getSendtelPhone() {
		return sendtelPhone;
	}
	/**
	 * @param sendtelPhone the sendtelPhone to set
	 */
	public void setSendtelPhone(String sendtelPhone) {
		this.sendtelPhone = sendtelPhone;
	}
	/**
	 * @return the smsContent
	 */
	public String getSmsContent() {
		return smsContent;
	}
	/**
	 * @param smsContent the smsContent to set
	 */
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
	/**
	 * @return the begintime
	 */
	public Long getBegintime() {
		return begintime;
	}
	/**
	 * @param begintime the begintime to set
	 */
	public void setBegintime(Long begintime) {
		this.begintime = begintime;
	}
	/**
	 * @return the endtime
	 */
	public Long getEndtime() {
		return endtime;
	}
	/**
	 * @param endtime the endtime to set
	 */
	public void setEndtime(Long endtime) {
		this.endtime = endtime;
	}
	/**
	 * @return the discountType
	 */
	public String getDiscountType() {
		return discountType;
	}
	/**
	 * @param discountType the discountType to set
	 */
	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	/**
	 * @return the useCouponValue
	 */
	public List<String> getUseCouponValue() {
		return useCouponValue;
	}
	/**
	 * @param useCouponValue the useCouponValue to set
	 */
	public void setUseCouponValue(List<String> useCouponValue) {
		this.useCouponValue = useCouponValue;
	}
	/**
	 * @return the costMode
	 */
	public String getCostMode() {
		return costMode;
	}
	/**
	 * @param costMode the costMode to set
	 */
	public void setCostMode(String costMode) {
		this.costMode = costMode;
	}
	/**
	 * @return the costType
	 */
	public String getCostType() {
		return costType;
	}
	/**
	 * @param costType the costType to set
	 */
	public void setCostType(String costType) {
		this.costType = costType;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the discount
	 */
	public String getDiscount() {
		return discount;
	}
	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	/**
	 * @return the costAddedType
	 */
	public String getCostAddedType() {
		return costAddedType;
	}
	/**
	 * @param costAddedType the costAddedType to set
	 */
	public void setCostAddedType(String costAddedType) {
		this.costAddedType = costAddedType;
	}
	/**
	 * @return the costAdded
	 */
	public String getCostAdded() {
		return costAdded;
	}
	/**
	 * @param costAdded the costAdded to set
	 */
	public void setCostAdded(String costAdded) {
		this.costAdded = costAdded;
	}
	/**
	 * @return the productTypes
	 */
	public List<String> getProductTypes() {
		return productTypes;
	}
	/**
	 * @param productTypes the productTypes to set
	 */
	public void setProductTypes(List<String> productTypes) {
		this.productTypes = productTypes;
	}
	/**
	 * @return the orderSources
	 */
	public List<String> getOrderSources() {
		return orderSources;
	}
	/**
	 * @param orderSources the orderSources to set
	 */
	public void setOrderSources(List<String> orderSources) {
		this.orderSources = orderSources;
	}
	/**
	 * @return the custLevels
	 */
	public List<String> getCustLevels() {
		return custLevels;
	}
	/**
	 * @param custLevels the custLevels to set
	 */
	public void setCustLevels(List<String> custLevels) {
		this.custLevels = custLevels;
	}
	/**
	 * @return the custTrades
	 */
	public List<String> getCustTrades() {
		return custTrades;
	}
	/**
	 * @param custTrades the custTrades to set
	 */
	public void setCustTrades(List<String> custTrades) {
		this.custTrades = custTrades;
	}
	/**
	 * @return the describe
	 */
	public String getDescribe() {
		return describe;
	}
	/**
	 * @param describe the describe to set
	 */
	public void setDescribe(String describe) {
		this.describe = describe;
	}
}
