package com.deppon.foss.module.pickup.predeliver.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.annotation.AllowBlank;

/** 
 * @ClassName: CustomerReceiptAddressEntity 
 * @Description: 客户历史收货地址类 
 * @author 237982-foss-fangwenjun 
 * @date 2015-4-1 下午3:08:07 
 *  
 */
public class CustomerReceiptAddressEntity extends BaseEntity {

	/** 
	 *  序列化版本号 
	 */ 
	private static final long serialVersionUID = 1L;
	
	/**
	 * 创建日期
	 */
	private Date createDate;
	
	/** 
	 *  创建部门编码
	 */ 
	private String createOrgCode;

	/** 
	 *  创建部门名称
	 */ 
	private String createOrgName;
	
	/**
	 * 创建人Code
	 */
	private String createrCode;
	
	/** 
	 *  创建人名称
	 */ 
	private String createrName;

	/** 
	 *  客户编码 
	 */ 
	private String customerCode;
	
	/** 
	 *  客户联系人 
	 */ 
	private String customerContactName;
	
	/** 
	 *  客户手机号码 
	 */ 
	private String customerMobilePhone;
	
	/** 
	 *  客户名称 
	 */ 
	private String customerName;
	
	/** 
	 *  客户固话 
	 */ 
	private String customerPhone;
	
	/**
	 * 客户收货地址id
	 */
	private String id;
	
	/**
	 * 修改日期
	 */
	private Date modifyDate;
	
	/**
	 * 修改人Code
	 */
	private String modifyUserCode;
	
	/** 
	 *  修改人名称 
	 */ 
	private String modifyUserName;
	
	/** 
	 *  收货地址详细 
	 */ 
	private String receiveAddressDetails;
	
	/** 
	 *  收货地址城市编码
	 */ 
	private String receiveCityCode;
	
	/** 
	 * 收货地址区域编码
	 */ 
	private String receiveDistCode;
	
	/** 
	 * 收货地址省份编码
	 */ 
	private String receiveProvCode;
	
	/** 
	 *  收货地址街道 
	 */ 
	private String receiveStreet;

	/**
	 * 获取createDate
	 * @return the createDate
	 */
	@AllowBlank
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * 获取createOrgCode
	 * @return the createOrgCode
	 */
	@AllowBlank
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * 获取createOrgName
	 * @return the createOrgName
	 */
	@AllowBlank
	public String getCreateOrgName() {
		return createOrgName;
	}

	/**
	 * 获取createrCode
	 * @return the createrCode
	 */
	@AllowBlank
	public String getCreaterCode() {
		return createrCode;
	}

	/**
	 * 获取createrName
	 * @return the createrName
	 */
	@AllowBlank
	public String getCreaterName() {
		return createrName;
	}

	/**
	 * 获取customerCode
	 * @return the customerCode
	 */
	@AllowBlank
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * 获取customerContactName
	 * @return the customerContactName
	 */
	@AllowBlank
	public String getCustomerContactName() {
		return customerContactName;
	}

	/**
	 * 获取customerMobilePhone
	 * @return the customerMobilePhone
	 */
	public String getCustomerMobilePhone() {
		return customerMobilePhone;
	}

	/**
	 * 获取customerName
	 * @return the customerName
	 */
	@AllowBlank
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * 获取customerPhone
	 * @return the customerPhone
	 */
	public String getCustomerPhone() {
		return customerPhone;
	}

	/**
	 * 获取id
	 * @return the id
	 */
	@AllowBlank
	public String getId() {
		return id;
	}

	/**
	 * 获取modifyDate
	 * @return the modifyDate
	 */
	@AllowBlank
	public Date getModifyDate() {
		return modifyDate;
	}

	/**
	 * 获取modifyUserCode
	 * @return the modifyUserCode
	 */
	@AllowBlank
	public String getModifyUserCode() {
		return modifyUserCode;
	}

	/**
	 * 获取modifyUserName
	 * @return the modifyUserName
	 */
	@AllowBlank
	public String getModifyUserName() {
		return modifyUserName;
	}

	/**
	 * 获取receiveAddressDetails
	 * @return the receiveAddressDetails
	 */
	public String getReceiveAddressDetails() {
		return receiveAddressDetails;
	}

	/**
	 * 获取receiveCityCode
	 * @return the receiveCityCode
	 */
	@AllowBlank
	public String getReceiveCityCode() {
		return receiveCityCode;
	}

	/**
	 * 获取receiveDistCode
	 * @return the receiveDistCode
	 */
	@AllowBlank
	public String getReceiveDistCode() {
		return receiveDistCode;
	}

	/**
	 * 获取receiveProvCode
	 * @return the receiveProvCode
	 */
	@AllowBlank
	public String getReceiveProvCode() {
		return receiveProvCode;
	}

	/**
	 * 获取receiveStreet
	 * @return the receiveStreet
	 */
	public String getReceiveStreet() {
		return receiveStreet;
	}

	/**
	 * 设置createDate
	 * @param createDate 要设置的createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 设置createOrgCode
	 * @param createOrgCode 要设置的createOrgCode
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * 设置createOrgName
	 * @param createOrgName 要设置的createOrgName
	 */
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	/**
	 * 设置createrCode
	 * @param createrCode 要设置的createrCode
	 */
	public void setCreaterCode(String createrCode) {
		this.createrCode = createrCode;
	}

	/**
	 * 设置createrName
	 * @param createrName 要设置的createrName
	 */
	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	/**
	 * 设置customerCode
	 * @param customerCode 要设置的customerCode
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * 设置customerContactName
	 * @param customerContactName 要设置的customerContactName
	 */
	public void setCustomerContactName(String customerContactName) {
		this.customerContactName = customerContactName;
	}

	/**
	 * 设置customerMobilePhone
	 * @param customerMobilePhone 要设置的customerMobilePhone
	 */
	public void setCustomerMobilePhone(String customerMobilePhone) {
		this.customerMobilePhone = customerMobilePhone;
	}

	/**
	 * 设置customerName
	 * @param customerName 要设置的customerName
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * 设置customerPhone
	 * @param customerPhone 要设置的customerPhone
	 */
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	/**
	 * 设置id
	 * @param id 要设置的id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 设置modifyDate
	 * @param modifyDate 要设置的modifyDate
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	/**
	 * 设置modifyUserCode
	 * @param modifyUserCode 要设置的modifyUserCode
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	/**
	 * 设置modifyUserName
	 * @param modifyUserName 要设置的modifyUserName
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	/**
	 * 设置receiveAddressDetails
	 * @param receiveAddressDetails 要设置的receiveAddressDetails
	 */
	public void setReceiveAddressDetails(String receiveAddressDetails) {
		this.receiveAddressDetails = receiveAddressDetails;
	}

	/**
	 * 设置receiveCityCode
	 * @param receiveCityCode 要设置的receiveCityCode
	 */
	public void setReceiveCityCode(String receiveCityCode) {
		this.receiveCityCode = receiveCityCode;
	}

	/**
	 * 设置receiveDistCode
	 * @param receiveDistCode 要设置的receiveDistCode
	 */
	public void setReceiveDistCode(String receiveDistCode) {
		this.receiveDistCode = receiveDistCode;
	}

	/**
	 * 设置receiveProvCode
	 * @param receiveProvCode 要设置的receiveProvCode
	 */
	public void setReceiveProvCode(String receiveProvCode) {
		this.receiveProvCode = receiveProvCode;
	}

	/**
	 * 设置receiveStreet
	 * @param receiveStreet 要设置的receiveStreet
	 */
	public void setReceiveStreet(String receiveStreet) {
		this.receiveStreet = receiveStreet;
	}
}
