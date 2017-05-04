package com.deppon.foss.module.pickup.order.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 电子运单订单实体
 * 
 * @author 136334-foss-白磊
 * 
 */
public class EWaybillOrderEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3029832608154646068L;
	
	/**
	 * ID
	 */
	private String ID;

	/**
	 * 订单号
	 */
	private String orderNO;
	
	/**
	 * 运单号
	 */
	private String waybillNO;
	
	/**
	 * 收货客户ID
	 */
	private String receiveCustomerID;
	
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
	 * 收货国家
	 */
	private String receiveCustomerNationCode;
	
	/**
	 * 收货省份
	 */
	private String receiveCustomerProvCode;
	
	/**
	 * 收货市
	 */
	private String receiveCustomerCityCode;
	
	/**
	 * 发货联系人
	 */
	private String deliveryCustomerContact;
	
	/**
	 * 发货联系人ID
	 */
	private String deliveryCustomerContactId;
	
	
	
	public String getDeliveryCustomerContactId() {
		return deliveryCustomerContactId;
	}

	public void setDeliveryCustomerContactId(String deliveryCustomerContactId) {
		this.deliveryCustomerContactId = deliveryCustomerContactId;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	/**
	 * 收货区
	 */
	private String receiveCustomerDistCode;
		
	/**
	 * 收货具体地址
	 */
	private String receiveCustomerAddress;
	
	/**
	 * 渠道客户Id:官网发货人德邦用户名
	 */
	private String channelCustID;
	
	/**
	 * 货物尺寸
	 */
	private String goodsSize;
	
	/**
	 * 返单类型
	 */
	private String returnBillType;
	
	/**
	 * 创建时间
	 */
	 private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;
	
	/**
	 * 定时任务编号
	 */
	private String jobID;
	
	/**
	 * 操作结果
	 */
	private String operateResult;
	
	/**
	 * 失败原因
	 */
	private String failReason;
	
	
	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
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
	
	public String getReceiveCustomerID() {
		return receiveCustomerID;
	}

	public void setReceiveCustomerID(String receiveCustomerID) {
		this.receiveCustomerID = receiveCustomerID;
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

	public String getReceiveCustomerNationCode() {
		return receiveCustomerNationCode;
	}

	public void setReceiveCustomerNationCode(String receiveCustomerNationCode) {
		this.receiveCustomerNationCode = receiveCustomerNationCode;
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

	public String getGoodsSize() {
		return goodsSize;
	}

	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
	}

	public String getChannelCustID() {
		return channelCustID;
	}

	public void setChannelCustID(String channelCustID) {
		this.channelCustID = channelCustID;
	}

	public String getReturnBillType() {
		return returnBillType;
	}

	public void setReturnBillType(String returnBillType) {
		this.returnBillType = returnBillType;
	}

	public String getJobID() {
		return jobID;
	}

	public void setJobID(String jobID) {
		this.jobID = jobID;
	}

	public String getOperateResult() {
		return operateResult;
	}

	public void setOperateResult(String operateResult) {
		this.operateResult = operateResult;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}

	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
	}

	public String getWaybillNO() {
		return waybillNO;
	}

	public void setWaybillNO(String waybillNO) {
		this.waybillNO = waybillNO;
	}

	
}