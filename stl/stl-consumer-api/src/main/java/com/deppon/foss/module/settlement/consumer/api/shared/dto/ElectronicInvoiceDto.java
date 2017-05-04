package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 电子发票DTO
 * @author 163576
 *
 */
public class ElectronicInvoiceDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5362736495029508975L;
	/**
	 * 运单号-必填
	 */
	private String wayBillNo;
	/**
	 * 开单时间-必填
	 */
    private Date billingTime;
    /**
     * 开票子公司编码-必填
     */
    private String companyCode;
    /**
     * 开票部门标杆编码-必填
     */
    private String companyStandCode;
    /**
     * 购货方名称-必填
     */
    private String buyerName;
    /**
     * 购货方纳税人识别号
     */
    private String buyerTaxNo;
    /**
     * 购货方电话-必填
     */
    private String buyerPhone;
    /**
     * 购货方邮箱
     */
    private String buyerMail;
    /**
     * 购货方省份
     */
    private String buyerProvince;
    /**
     * 购货方地址
     */
    private String buyerAddress;
    /**
     * 收款员
     */
    private String receiver;
    /**
     * 开票金额合计-必填
     */
    private BigDecimal amountTotal;
    /**
     * 购方企业类型
     */
    private String buyerType;
    /**
     * 送货时间
     */
    private Date deliveryTime;
    /**
     * 业务类型-必填
     */
    private String businessType;
    /**
     * 是否重发
     */
    private String repeatFlag;
    /**
     * 货物编码
     */
    private String baleCode;
    /**
     * 货物名称
     */
    private String baleName;
    /**
     * 单位
     */
    private String unitPrice;
    /**
     * 规格型号
     */
    private String standardNo;
    /**
     * 数量
     */
    private Integer num;
    /**
     * 单价
     */
    private Integer unit;
	public String getWayBillNo() {
		return wayBillNo;
	}
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	public Date getBillingTime() {
		return billingTime;
	}
	public void setBillingTime(Date billingTime) {
		this.billingTime = billingTime;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getCompanyStandCode() {
		return companyStandCode;
	}
	public void setCompanyStandCode(String companyStandCode) {
		this.companyStandCode = companyStandCode;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getBuyerTaxNo() {
		return buyerTaxNo;
	}
	public void setBuyerTaxNo(String buyerTaxNo) {
		this.buyerTaxNo = buyerTaxNo;
	}
	public String getBuyerPhone() {
		return buyerPhone;
	}
	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}
	public String getBuyerMail() {
		return buyerMail;
	}
	public void setBuyerMail(String buyerMail) {
		this.buyerMail = buyerMail;
	}
	public String getBuyerProvince() {
		return buyerProvince;
	}
	public void setBuyerProvince(String buyerProvince) {
		this.buyerProvince = buyerProvince;
	}
	public String getBuyerAddress() {
		return buyerAddress;
	}
	public void setBuyerAddress(String buyerAddress) {
		this.buyerAddress = buyerAddress;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public BigDecimal getAmountTotal() {
		return amountTotal;
	}
	public void setAmountTotal(BigDecimal amountTotal) {
		this.amountTotal = amountTotal;
	}
	public String getBuyerType() {
		return buyerType;
	}
	public void setBuyerType(String buyerType) {
		this.buyerType = buyerType;
	}
	public Date getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getRepeatFlag() {
		return repeatFlag;
	}
	public void setRepeatFlag(String repeatFlag) {
		this.repeatFlag = repeatFlag;
	}
	public String getBaleCode() {
		return baleCode;
	}
	public void setBaleCode(String baleCode) {
		this.baleCode = baleCode;
	}
	public String getBaleName() {
		return baleName;
	}
	public void setBaleName(String baleName) {
		this.baleName = baleName;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getStandardNo() {
		return standardNo;
	}
	public void setStandardNo(String standardNo) {
		this.standardNo = standardNo;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getUnit() {
		return unit;
	}
	public void setUnit(Integer unit) {
		this.unit = unit;
	}
    
    
}
