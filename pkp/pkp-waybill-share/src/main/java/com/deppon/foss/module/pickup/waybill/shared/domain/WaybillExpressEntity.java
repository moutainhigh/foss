/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 运单快递冗余entity
 * @author 026123-foss-lifengteng
 *
 */
public class WaybillExpressEntity extends BaseEntity {
	/**
	 * 类的序列号
	 */
	private static final long serialVersionUID = 1;
	
	/**
	 * 运单id
	 */
	private String waybillId;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 发货省份
	 */
	private String deliveryCustomerProvCode;

	/**
	 * 发货市
	 */
	private String deliveryCustomerCityCode;

	/**
	 * 发货区
	 */
	private String deliveryCustomerDistCode;
	

	/**
	 * 收货客户编码
	 */
	private String receiveCustomerCode;

	/**
	 * 收货客户名称
	 */
	private String receiveCustomerName;

	/**
	 * 收货客户手机
	 */
	private String receiveCustomerMobilephone;

	/**
	 * 收货客户电话
	 */
	private String receiveCustomerPhone;

	/**
	 * 收货客户联系人
	 */
	private String receiveCustomerContact;
	
	/**
	 * 收货省份
	 */
	private String receiveCustomerProvCode;

	/**
	 * 收货市
	 */
	private String receiveCustomerCityCode;

	/**
	 * 收货区
	 */
	private String receiveCustomerDistCode;

	/**
	 * 收货具体地址
	 */
	private String receiveCustomerAddress;
	
	/**
	 * 提货方式
	 */
	private String receiveMethod;
	
	/**
	 * 目的站
	 */
	private String targetOrgCode;
	
	/**
	 * 开单时间
	 */
	private Date billTime;
	
	/**
	 * 开单组织
	 */
	private String createOrgCode;

	//---以下是新增字段 
	/**
	 * 是否补码
	 */
	private String isAddCode;
	
	/**
	 * 补码时间
	 */
	private Date addCodeTime;
	
	/**
	 * 返单/返货类型
	 */
	private String returnType;
	
	/**
	 * 返单/返货原运单号
	 */
	private String originalWaybillNo;
	
	/**
	 * 快递员code
	 */
	private String expressEmpCode;
	/**
	 * 快递员名称
	 */
	private String expressEmpName;

	/**
	 * 快递点部CODE
	 */
	private String expressOrgCode;

	/**
	 * 快递点部名称
	 */
	private String expressOrgName;
	
	/**
	 * PDA串号
	 */
	private String pdaSerial;
	
	/**
	 * 银行交易流水号
	 */
	private String bankTradeSerail;
	
	/**
	 * 提货网点
	 */
	private String customerPickupOrgCode;
	
	/**
	 * 提货网点名称
	 */
	private String customerPickupOrgName;
	
	
	/**
	 * 收货网点编码
	 */
	private String receiveOrgCode;
	
	/**
	 * 收货网点名称
	 */
	private String receiveOrgName;
	
	/**
	 * 是否可以做返货开单
	 */
	private String canReturnCargo;
	
	
	/**
	 * 最终外场编码
	 */
	private String lastOutFieldCode;
	
	
	/**
	 * 更改过体积
	 */
	private String changeVolume;
	
	/**
	 * 体积重  ISSUE-4523新加
	 */ 
	private BigDecimal volumeWeight;
	
	/**
	 * 内部带货费用承担部门
	 */
	private String innerPickupFeeBurdenDept;
	
	/**
	 * PDA运单补录插入时间
	 * @return
	 */
	private Date createTime;
	
	/**
	 * 内部带货发货人工号
	 */
	private String deliveryEmployeeCode;	
	
	/**
	 * 伙伴开单标识
	 * @return
	 */
	private String partnerBillingLogo;
	
	public String getPartnerBillingLogo() {
		return partnerBillingLogo;
	}

	public void setPartnerBillingLogo(String partnerBillingLogo) {
		this.partnerBillingLogo = partnerBillingLogo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getVolumeWeight() {
		return volumeWeight;
	}

	public void setVolumeWeight(BigDecimal volumeWeight) {
		this.volumeWeight = volumeWeight;
	}

	public String getChangeVolume() {
		return changeVolume;
	}

	public void setChangeVolume(String changeVolume) {
		this.changeVolume = changeVolume;
	}

	public String getLastOutFieldCode() {
		return lastOutFieldCode;
	}

	public void setLastOutFieldCode(String lastOutFieldCode) {
		this.lastOutFieldCode = lastOutFieldCode;
	}

	public String getCanReturnCargo() {
		return canReturnCargo;
	}

	public void setCanReturnCargo(String canReturnCargo) {
		this.canReturnCargo = canReturnCargo;
	}

	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	public String getReceiveOrgName() {
		return receiveOrgName;
	}

	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}

	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}

	public String getCustomerPickupOrgName() {
		return customerPickupOrgName;
	}

	public void setCustomerPickupOrgName(String customerPickupOrgName) {
		this.customerPickupOrgName = customerPickupOrgName;
	}

	public String getWaybillId() {
		return waybillId;
	}

	public void setWaybillId(String waybillId) {
		this.waybillId = waybillId;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

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

	public String getReceiveCustomerCode() {
		return receiveCustomerCode;
	}

	public void setReceiveCustomerCode(String receiveCustomerCode) {
		this.receiveCustomerCode = receiveCustomerCode;
	}

	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}

	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}

	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}

	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}

	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
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

	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}

	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}

	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	public String getTargetOrgCode() {
		return targetOrgCode;
	}

	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}

	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public String getIsAddCode() {
		return isAddCode;
	}

	public void setIsAddCode(String isAddCode) {
		this.isAddCode = isAddCode;
	}

	public Date getAddCodeTime() {
		return addCodeTime;
	}

	public void setAddCodeTime(Date addCodeTime) {
		this.addCodeTime = addCodeTime;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getOriginalWaybillNo() {
		return originalWaybillNo;
	}

	public void setOriginalWaybillNo(String originalWaybillNo) {
		this.originalWaybillNo = originalWaybillNo;
	}

	public String getExpressEmpCode() {
		return expressEmpCode;
	}

	public void setExpressEmpCode(String expressEmpCode) {
		this.expressEmpCode = expressEmpCode;
	}

	public String getExpressEmpName() {
		return expressEmpName;
	}

	public void setExpressEmpName(String expressEmpName) {
		this.expressEmpName = expressEmpName;
	}

	public String getExpressOrgCode() {
		return expressOrgCode;
	}

	public void setExpressOrgCode(String expressOrgCode) {
		this.expressOrgCode = expressOrgCode;
	}

	public String getExpressOrgName() {
		return expressOrgName;
	}

	public void setExpressOrgName(String expressOrgName) {
		this.expressOrgName = expressOrgName;
	}

	public String getPdaSerial() {
		return pdaSerial;
	}

	public void setPdaSerial(String pdaSerial) {
		this.pdaSerial = pdaSerial;
	}

	public String getBankTradeSerail() {
		return bankTradeSerail;
	}

	public void setBankTradeSerail(String bankTradeSerail) {
		this.bankTradeSerail = bankTradeSerail;
	}

	public String getInnerPickupFeeBurdenDept() {
		return innerPickupFeeBurdenDept;
	}

	public void setInnerPickupFeeBurdenDept(String innerPickupFeeBurdenDept) {
		this.innerPickupFeeBurdenDept = innerPickupFeeBurdenDept;
	}
	public String getDeliveryEmployeeCode() {
		return deliveryEmployeeCode;
	}

	public void setDeliveryEmployeeCode(String deliveryEmployeeCode) {
		this.deliveryEmployeeCode = deliveryEmployeeCode;
	}
	
}
