package com.deppon.foss.module.pickup.sign.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class DopSignEntity extends BaseEntity {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	//运单号
	private String waybillNo;
	//dop数据传来时间
	private Date signTime;
	//签收件数
	private int signGoodsQty;
	//签收备注
	private String signNote;
	//签收状态
	private String signStatus;
	//供应商名称
	private String supplierName;
	//供应商编码
	private String supplierCode;
	//付款方式
	private String payType;
	//收货部门名称
	private String receiveOrgName;
	//收货部门编码
	private String receiveOrgCode;
	//收货人
	private String receiverName;
	//收货人联系方式
	private String receiverPhone;
	//提货网店名称
	private String deliveryOrgName;
	//提货网店编码
	private String deliveryOrgCode;
	//创建时间
	private Date createTime;

	public String getDeliveryOrgName() {
		return deliveryOrgName;
	}
	public void setDeliveryOrgName(String deliveryOrgName) {
		this.deliveryOrgName = deliveryOrgName;
	}
	public String getDeliveryOrgCode() {
		return deliveryOrgCode;
	}
	public void setDeliveryOrgCode(String deliveryOrgCode) {
		this.deliveryOrgCode = deliveryOrgCode;
	}
	//特殊增值服务
//	private String specialAddValueService;
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
	public Date getSignTime() {
		return signTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	public int getSignGoodsQty() {
		return signGoodsQty;
	}
	public void setSignGoodsQty(int signGoodsQty) {
		this.signGoodsQty = signGoodsQty;
	}
	public String getSignNote() {
		return signNote;
	}
	public void setSignNote(String signNote) {
		this.signNote = signNote;
	}
	public String getSignStatus() {
		return signStatus;
	}
	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getReceiveOrgName() {
		return receiveOrgName;
	}
	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
