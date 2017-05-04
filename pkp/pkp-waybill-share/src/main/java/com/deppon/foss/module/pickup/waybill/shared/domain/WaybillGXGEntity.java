
package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 运单entity
 * 
 * @author dop-266396
 * @date 2015-09-14 上午10:36:10
 */
public class WaybillGXGEntity extends BaseEntity{

	private static final long serialVersionUID = 1L;
    //ID唯一性
	private String id;
	
	// 单号--客户自有ERP单号
	protected String erpLogisticID;

	// 客户到达门店编号
	protected String arrivedStoreNum;

	// 客户标签编号
	protected String customerLableNum;

	// 客户在CRM中的客户编码
	private String customerCode;

	// 发货人信息(内含新增五级地址与原始地址信息)
	private GXGPeopleInfoEntity sender;

	// 发货人是否上门接货
	private String vistReceive;

	// 始发网点名称
	private String businessNetworkName;
	// 始发网点编码
	private String businessNetworkNo;

	// 收货人信息(内含新增五级地址与原始地址信息)
	private GXGPeopleInfoEntity receiver;

	// 订单提交时间
	private Date gmtCommit;

	// 货物名称
	private String cargoName;

	// 总件数
	private int totalNumber;

	// 重量
	private double weight;

	// 体积
	private double volume;

	// 尺寸
	private String size;

	// 包装
	private String packageService;

	// 保价金额
	private BigDecimal insuranceValue;

	// 返单方式
	private String backSignBill;

	private String transportType;
	// 提货方式
	private String deliveryType;

	// 支付方式
	private String payType;

	// 短信通知，Y：需要 N: 不需要
	private String smsNotify;

	// 备注
	private String remark;

	// 订单来源 company-->companyCode 一定
	private String orderSource;
	
	private Date createDate;
	

	public String getErpLogisticID() {
		return erpLogisticID;
	}

	public void setErpLogisticID(String erpLogisticID) {
		this.erpLogisticID = erpLogisticID;
	}

	public String getArrivedStoreNum() {
		return arrivedStoreNum;
	}

	public void setArrivedStoreNum(String arrivedStoreNum) {
		this.arrivedStoreNum = arrivedStoreNum;
	}

	public String getCustomerLableNum() {
		return customerLableNum;
	}

	public void setCustomerLableNum(String customerLableNum) {
		this.customerLableNum = customerLableNum;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public GXGPeopleInfoEntity getSender() {
		return sender;
	}

	public void setSender(GXGPeopleInfoEntity sender) {
		this.sender = sender;
	}

	public String getVistReceive() {
		return vistReceive;
	}

	public void setVistReceive(String vistReceive) {
		this.vistReceive = vistReceive;
	}

	public String getBusinessNetworkName() {
		return businessNetworkName;
	}

	public void setBusinessNetworkName(String businessNetworkName) {
		this.businessNetworkName = businessNetworkName;
	}

	public String getBusinessNetworkNo() {
		return businessNetworkNo;
	}

	public void setBusinessNetworkNo(String businessNetworkNo) {
		this.businessNetworkNo = businessNetworkNo;
	}

	public GXGPeopleInfoEntity getReceiver() {
		return receiver;
	}

	public void setReceiver(GXGPeopleInfoEntity receiver) {
		this.receiver = receiver;
	}

	public Date getGmtCommit() {
		return gmtCommit;
	}

	public void setGmtCommit(Date gmtCommit) {
		this.gmtCommit = gmtCommit;
	}

	public String getCargoName() {
		return cargoName;
	}

	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
	}

	public int getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getPackageService() {
		return packageService;
	}

	public void setPackageService(String packageService) {
		this.packageService = packageService;
	}

	public BigDecimal getInsuranceValue() {
		return insuranceValue;
	}

	public void setInsuranceValue(BigDecimal insuranceValue) {
		this.insuranceValue = insuranceValue;
	}

	public String getBackSignBill() {
		return backSignBill;
	}

	public void setBackSignBill(String backSignBill) {
		this.backSignBill = backSignBill;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getSmsNotify() {
		return smsNotify;
	}

	public void setSmsNotify(String smsNotify) {
		this.smsNotify = smsNotify;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

}
