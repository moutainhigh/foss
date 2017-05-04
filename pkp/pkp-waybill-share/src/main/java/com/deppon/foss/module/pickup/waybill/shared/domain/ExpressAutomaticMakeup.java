package com.deppon.foss.module.pickup.waybill.shared.domain;
/**
 * 阿里巴巴快递自动补录对接信息封装类
 * @author:218371-foss-zhaoyanjun
 * @date:2015-05-14上午10:24
 */
import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="ExpressAutomaticMakeup")
public class ExpressAutomaticMakeup implements Serializable{
	private static final long serialVersionUID = 7650000987654327595L;
	
	// 运单号
	private String waybillNo;
	
	// 发货客户名称
	private String deliveryCustomerName;
	
	// 发货客户手机
	private String deliveryCustomerMobilePhone;
	
	// 发货客户电话
	private String deliveryCustomerPhone;
	
	// 收货客户名称
	private String receiveCustomerName;
	
	// 收货客户手机
	private String receiveCustomerMobilePhone;
	
	// 收货客户电话
	private String receiveCustomerPhone;
	
	// 收货客户-省
	private String receiveCustomerProvCode;
	
	// 收货客户-市
	private String receiveCustomerCityCode;
	
	// 收货客户-区
	private String receiveCustomerDistCode;

	// 收货具体地址（收件地址-详细）
	private String receiveCustomerAddress;
	
	// 货物名称（托寄物内容）
	private String goodsName;
	
	// 保价声明价值
	private Integer insuranceAmount;
	
	//代收货款退款类型
	private String refundType;
	
	//代收货款金额
	private Integer codAmount;
	
	//开户名
	private String accountName;
	
	//开户行
	private String accountBank;
	
	//收款帐号
	private String collectionAccount;
	
	// 包装费
	private Integer packageFeeCanvas;
	
	// 图片上传时间
	private Date uploadTime;
	
	// 发件人省
	private String deliveryCustomerProvCode;
	
	// 发件人 市
	private String deliveryCustomerCityCode;
	
	// 发件人 区
	private String deliveryCustomerDistCode;
	
	// 发货人具体地址
	private String deliveryCustomerAddress;

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

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getDeliveryCustomerName() {
		return deliveryCustomerName;
	}

	public void setDeliveryCustomerName(String deliveryCustomerName) {
		this.deliveryCustomerName = deliveryCustomerName;
	}

	public String getDeliveryCustomerPhone() {
		return deliveryCustomerPhone;
	}

	public void setDeliveryCustomerPhone(String deliveryCustomerPhone) {
		this.deliveryCustomerPhone = deliveryCustomerPhone;
	}

	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	public String getDeliveryCustomerMobilePhone() {
		return deliveryCustomerMobilePhone;
	}

	public void setDeliveryCustomerMobilePhone(String deliveryCustomerMobilePhone) {
		this.deliveryCustomerMobilePhone = deliveryCustomerMobilePhone;
	}

	public String getReceiveCustomerMobilePhone() {
		return receiveCustomerMobilePhone;
	}

	public void setReceiveCustomerMobilePhone(String receiveCustomerMobilePhone) {
		this.receiveCustomerMobilePhone = receiveCustomerMobilePhone;
	}

	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}

	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}

	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}

	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Integer getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(Integer insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public Integer getPackageFeeCanvas() {
		return packageFeeCanvas;
	}

	public void setPackageFeeCanvas(Integer packageFeeCanvas) {
		this.packageFeeCanvas = packageFeeCanvas;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public Integer getCodAmount() {
		return codAmount;
	}

	public void setCodAmount(Integer codAmount) {
		this.codAmount = codAmount;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountBank() {
		return accountBank;
	}

	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}

	public String getCollectionAccount() {
		return collectionAccount;
	}

	public void setCollectionAccount(String collectionAccount) {
		this.collectionAccount = collectionAccount;
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
}
