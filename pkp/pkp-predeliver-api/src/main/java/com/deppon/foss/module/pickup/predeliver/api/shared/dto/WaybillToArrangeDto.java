/**
 *  initial comments.
 */
/*
 * 
 */
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

// TODO: Auto-generated Javadoc
/**
 * 待排单运单Dto.
 *
 * @author ibm-wangxiexu
 * @date 2012-11-2 下午5:25:35
 */
public class WaybillToArrangeDto implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1093848282361653470L;
	/**
	 * 收货省份
	 */
	private String receiveCustomerProvCode;
	/**
	 * 收货市
	 */
	private String receiveCustomerCityCode;

	/** 收货区(行政区域). */
	private String receiveCustomerDistCode;
	
	/** 收货区(行政区域). */
	private List<String> districtList;

	/** 送货小区编码. */
	private String deliverRegionCode;
	
	/** 送货小区编码. */
	private List<String> deliverRegionList;

	/** 运单号. */
	private String waybillNo;
	/**
	 * 规定兑现时间
	 */
	private Date cashTime;
	
	/**
	 * 选择的运单号列表
	 */
	private String[] arrayWaybillNos;

	/** 提货方式. */
	private String receiveMethod;

	/** 收货客户名称. */
	private String receiveCustomerName;

	/** 收货客户电话. */
	private String receiveCustomerPhone;

	/** 精准卡航产品编码. */
	private String fastWaybillCode;

	/** 运单有效状态. */
	private String waybillActive;

	/** 更改单受理状态. */
	private String rfcStatus;
	
	/** 更改单审核拒绝. */
	private String rfcStatusAuditDeny;
	
	/** 更改单受理拒绝. */
	private String rfcStatusAcceptDeny;

	/** 到达联状态. */
	private String arrivesheetStatus;

	/** 到达联有效状态. */
	private String arrivesheetActive;

	/** 到达联删除状态. */
	private String arrivesheetDestroyed;

	/** 异常类型. */
	private String exceptionType;

	/** 异常处理状态. */
	private String exceptionStatus;

	/** 卡货标志. */
	private Short fastWaybillFlag;

	/** 通知客户成功标志. */
	private String notifySuccessFlag;

	/** The id. */
	private String id;

	/** 派送单ID. */
	private String tSrvDeliverbillId;

	/** The serial no. */
	private Integer serialNo;

	/** The arrivesheet no. */
	private String arrivesheetNo;

	/** The arrange status. */
	private String arrangeStatus;

	/** 可排单件数. */
	private Integer arrangableGoodsQty;

	/** 预排单件数. */
	private Integer preArrangeGoodsQty;

	/** 排单件数. */
	private Integer arrangeGoodsQty;

	/** 重量. */
	private BigDecimal weight;

	/** 货物尺寸(长*宽*高). */
	private String dimension;

	/** 货物名称. */
	private String goodsName;

	/** 运单件数. */
	private Integer waybillGoodsQty;

	/** 运输方式. */
	private String transportType;

	/** 到达时间. */
	private Date arriveTime;

	/** 收货人. */
	private String consignee;

	/** 联系方式. */
	private String consigneeContact;

	/** 收货地址. */
	private String consigneeAddress;
	
	/** 收货地址. */
	private String consigneeAddressNote;

	/** 送货要求. */
	private String deliverRequire;

	/** 货物状态. */
	private String goodsStatus;

	/** 是否异常. */
	private String isException;

	/** 是否已联系客户. */
	private String isAlreadyContact;

	/** 预计到达时间. */
	private Date estimatedDeliverTime;

	/** 是否需要发票. */
	private String isNeedInvoice;

	/** 付款方式. */
	private String paymentType;

	/** 备注. */
	private String notes;

	/** 送货方式. */
	private String deliverType;

	/** 是否必送货. */
	private String isSentRequired;

	/** 体积. */
	private BigDecimal goodsVolumeTotal;

	/** 到付款. */
	private BigDecimal payAmount;

	/** 无法排单的原因. */
	private String failedToArrangeReason;
	
	/**
	 * 是否通知
	 */
	private String isNotification;

	/** 货币类型. */
	private String currencyCode;

	/** 返单类型. */
	private String returnBillType;

	/** 包装. */
	private String goodsPackage;

	/** 部门Code. */
	private String orgCode;
	
	/** 库存件数. */
	private String stockGoodQty;
	
	/** 最后库存code. */
	private String endStockOrgCode;	
	
	/** 库区. */
	private String goodsAreaCode;
	/**
	 * 到达时间（起）
	 */
	private Date arriveTimeBegin;
	/**
	 * 到达时间（止）
	 */
	private Date arriveTimeEnd;
	/**
	 * 预计送货时间
	 */
	private Date deliverTime;
	/**
	 * 送货日期
	 */
	private Date deliverDate;
	/**
	 * 是否已到库位
	 */
	private String isStoring;
	/**
	 *送货小区 
	 */
	private String deliverRegionName;
	/**
	 * 带人建议
	 */
	private String deliverSuggest;
	/**
	 * 运输性质 
	 */
	private String productCode;
	/**
	 * 选择过滤的运输性质
	 * 
	 */
	private String[] productCodes;
	
	/**
	 * 是否快递
	 * add by yangkang
	 */
	private String isExpress;
	/**
	 * 合送编码
	 */
	private String togetherSendCode;

	/**

	 *  收货人是否大客户.
	 */
	private String receiveBigCustomer; 
	
	private Integer billQty; //交单件数
	
	/**
	 * 交单id
	 */
	private String srvDeliverHandoverbillId;
	
	/**
	 *  过滤不期望的家装提货方式
	 */
	private List<String> notReceviedMethods;
	
	/**
	 * Gets the 收货人是否大客户.
	 *
	 * @return the 收货人是否大客户.
	 */
	public String getReceiveBigCustomer() {
		return receiveBigCustomer;
	}

	/**
	 * Sets the 收货人是否大客户.
	 *
	 * @param receiveBigCustomer the 收货人是否大客户.
	 */
	public void setReceiveBigCustomer(String receiveBigCustomer) {
		this.receiveBigCustomer = receiveBigCustomer;
	}

	/**
	 * Gets the receive customer dist code.
	 * 
	 * @return the receiveCustomerDistCode
	 */
	public String getReceiveCustomerDistCode() {
		return receiveCustomerDistCode;
	}

	/**
	 * Sets the receive customer dist code.
	 * 
	 * @param receiveCustomerDistCode
	 *            the receiveCustomerDistCode to see
	 */
	public void setReceiveCustomerDistCode(String receiveCustomerDistCode) {
		this.receiveCustomerDistCode = receiveCustomerDistCode;
	}

	
	public List<String> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List<String> districtList) {
		this.districtList = districtList;
	}

	/**
	 * Gets the waybill no.
	 * 
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * Sets the waybill no.
	 * 
	 * @param waybillNo
	 *            the waybillNo to see
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * Gets the receive method.
	 * 
	 * @return the receiveMethod
	 */
	public String getReceiveMethod() {
		return receiveMethod;
	}

	/**
	 * Sets the receive method.
	 * 
	 * @param receiveMethod
	 *            the receiveMethod to see
	 */
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	/**
	 * Gets the receive customer name.
	 * 
	 * @return the receiveCustomerName
	 */
	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	/**
	 * Sets the receive customer name.
	 * 
	 * @param receiveCustomerName
	 *            the receiveCustomerName to see
	 */
	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	/**
	 * Gets the receive customer phone.
	 * 
	 * @return the receiveCustomerPhone
	 */
	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}

	/**
	 * Sets the receive customer phone.
	 * 
	 * @param receiveCustomerPhone
	 *            the receiveCustomerPhone to see
	 */
	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the id to see
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the t srv deliverbill id.
	 * 
	 * @return the tSrvDeliverbillId
	 */
	public String gettSrvDeliverbillId() {
		return tSrvDeliverbillId;
	}

	/**
	 * Sets the t srv deliverbill id.
	 * 
	 * @param tSrvDeliverbillId
	 *            the tSrvDeliverbillId to see
	 */
	public void settSrvDeliverbillId(String tSrvDeliverbillId) {
		this.tSrvDeliverbillId = tSrvDeliverbillId;
	}

	/**
	 * Gets the serial no.
	 * 
	 * @return the serialNo
	 */
	public Integer getSerialNo() {
		return serialNo;
	}

	/**
	 * Sets the serial no.
	 * 
	 * @param serialNo
	 *            the serialNo to see
	 */
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * Gets the arrivesheet no.
	 * 
	 * @return the arrivesheetNo
	 */
	public String getArrivesheetNo() {
		return arrivesheetNo;
	}

	/**
	 * Sets the arrivesheet no.
	 * 
	 * @param arrivesheetNo
	 *            the arrivesheetNo to see
	 */
	public void setArrivesheetNo(String arrivesheetNo) {
		this.arrivesheetNo = arrivesheetNo;
	}

	/**
	 * Gets the arrange status.
	 * 
	 * @return the arrangeStatus
	 */
	public String getArrangeStatus() {
		return arrangeStatus;
	}

	/**
	 * Sets the arrange status.
	 * 
	 * @param arrangeStatus
	 *            the arrangeStatus to see
	 */
	public void setArrangeStatus(String arrangeStatus) {
		this.arrangeStatus = arrangeStatus;
	}

	/**
	 * Gets the pre arrange goods qty.
	 * 
	 * @return the preArrangeGoodsQty
	 */
	public Integer getPreArrangeGoodsQty() {
		return preArrangeGoodsQty;
	}

	/**
	 * Sets the pre arrange goods qty.
	 * 
	 * @param preArrangeGoodsQty
	 *            the preArrangeGoodsQty to see
	 */
	public void setPreArrangeGoodsQty(Integer preArrangeGoodsQty) {
		this.preArrangeGoodsQty = preArrangeGoodsQty;
	}

	/**
	 * Gets the arrange goods qty.
	 * 
	 * @return the arrangeGoodsQty
	 */
	public Integer getArrangeGoodsQty() {
		return arrangeGoodsQty;
	}

	/**
	 * Sets the arrange goods qty.
	 * 
	 * @param arrangeGoodsQty
	 *            the arrangeGoodsQty to see
	 */
	public void setArrangeGoodsQty(Integer arrangeGoodsQty) {
		this.arrangeGoodsQty = arrangeGoodsQty;
	}

	/**
	 * Gets the weight.
	 * 
	 * @return the weight
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * Sets the weight.
	 * 
	 * @param weight
	 *            the weight to see
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * Gets the dimension.
	 * 
	 * @return the dimension
	 */
	public String getDimension() {
		return dimension;
	}

	/**
	 * Sets the dimension.
	 * 
	 * @param dimension
	 *            the dimension to see
	 */
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	/**
	 * Gets the goods name.
	 * 
	 * @return the goodsName
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * Sets the goods name.
	 * 
	 * @param goodsName
	 *            the goodsName to see
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * Gets the waybill goods qty.
	 * 
	 * @return the waybillGoodsQty
	 */
	public Integer getWaybillGoodsQty() {
		return waybillGoodsQty;
	}

	/**
	 * Sets the waybill goods qty.
	 * 
	 * @param waybillGoodsQty
	 *            the waybillGoodsQty to see
	 */
	public void setWaybillGoodsQty(Integer waybillGoodsQty) {
		this.waybillGoodsQty = waybillGoodsQty;
	}

	/**
	 * Gets the transport type.
	 * 
	 * @return the transportType
	 */
	public String getTransportType() {
		return transportType;
	}

	/**
	 * Sets the transport type.
	 * 
	 * @param transportType
	 *            the transportType to see
	 */
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	/**
	 * Gets the arrive time.
	 * 
	 * @return the arriveTime
	 */
	public Date getArriveTime() {
		return arriveTime;
	}

	/**
	 * Sets the arrive time.
	 * 
	 * @param arriveTime
	 *            the arriveTime to see
	 */
	@DateFormat
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	/**
	 * Gets the consignee.
	 * 
	 * @return the consignee
	 */
	public String getConsignee() {
		return consignee;
	}

	/**
	 * Sets the consignee.
	 * 
	 * @param consignee
	 *            the consignee to see
	 */
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	/**
	 * Gets the consignee contact.
	 * 
	 * @return the consigneeContact
	 */
	public String getConsigneeContact() {
		return consigneeContact;
	}

	/**
	 * Sets the consignee contact.
	 * 
	 * @param consigneeContact
	 *            the consigneeContact to see
	 */
	public void setConsigneeContact(String consigneeContact) {
		this.consigneeContact = consigneeContact;
	}

	/**
	 * Gets the consignee address.
	 * 
	 * @return the consigneeAddress
	 */
	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	/**
	 * Sets the consignee address.
	 * 
	 * @param consigneeAddress
	 *            the consigneeAddress to see
	 */
	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	/**
	 * Gets the deliver require.
	 * 
	 * @return the deliverRequire
	 */
	public String getDeliverRequire() {
		return deliverRequire;
	}

	/**
	 * Sets the deliver require.
	 * 
	 * @param deliverRequire
	 *            the deliverRequire to see
	 */
	public void setDeliverRequire(String deliverRequire) {
		this.deliverRequire = deliverRequire;
	}

	/**
	 * Gets the goods status.
	 * 
	 * @return the goodsStatus
	 */
	public String getGoodsStatus() {
		return goodsStatus;
	}

	/**
	 * Sets the goods status.
	 * 
	 * @param goodsStatus
	 *            the goodsStatus to see
	 */
	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	/**
	 * Gets the checks if is exception.
	 * 
	 * @return the isException
	 */
	public String getIsException() {
		return isException;
	}

	/**
	 * Sets the checks if is exception.
	 * 
	 * @param isException
	 *            the isException to see
	 */
	public void setIsException(String isException) {
		this.isException = isException;
	}

	/**
	 * Gets the checks if is already contact.
	 * 
	 * @return the isAlreadyContact
	 */
	public String getIsAlreadyContact() {
		return isAlreadyContact;
	}

	/**
	 * Sets the checks if is already contact.
	 * 
	 * @param isAlreadyContact
	 *            the isAlreadyContact to see
	 */
	public void setIsAlreadyContact(String isAlreadyContact) {
		this.isAlreadyContact = isAlreadyContact;
	}

	/**
	 * Gets the estimated deliver time.
	 * 
	 * @return the estimatedDeliverTime
	 */
	public Date getEstimatedDeliverTime() {
		return estimatedDeliverTime;
	}

	/**
	 * Sets the estimated deliver time.
	 * 
	 * @param estimatedDeliverTime
	 *            the estimatedDeliverTime to see
	 */
	@DateFormat
	public void setEstimatedDeliverTime(Date estimatedDeliverTime) {
		this.estimatedDeliverTime = estimatedDeliverTime;
	}

	/**
	 * Gets the checks if is need invoice.
	 * 
	 * @return the isNeedInvoice
	 */
	public String getIsNeedInvoice() {
		return isNeedInvoice;
	}

	/**
	 * Sets the checks if is need invoice.
	 * 
	 * @param isNeedInvoice
	 *            the isNeedInvoice to see
	 */
	public void setIsNeedInvoice(String isNeedInvoice) {
		this.isNeedInvoice = isNeedInvoice;
	}

	/**
	 * Gets the payment type.
	 * 
	 * @return the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * Sets the payment type.
	 * 
	 * @param paymentType
	 *            the paymentType to see
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * Gets the notes.
	 * 
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * Sets the notes.
	 * 
	 * @param notes
	 *            the notes to see
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * Gets the deliver type.
	 * 
	 * @return the deliverType
	 */
	public String getDeliverType() {
		return deliverType;
	}

	/**
	 * Sets the deliver type.
	 * 
	 * @param deliverType
	 *            the deliverType to see
	 */
	public void setDeliverType(String deliverType) {
		this.deliverType = deliverType;
	}

	/**
	 * Gets the rfc status.
	 * 
	 * @return the rfcStatus
	 */
	public String getRfcStatus() {
		return rfcStatus;
	}

	/**
	 * Sets the rfc status.
	 * 
	 * @param rfcStatus
	 *            the rfcStatus to see
	 */
	public void setRfcStatus(String rfcStatus) {
		this.rfcStatus = rfcStatus;
	}

	/**
	 * Gets the waybill active.
	 * 
	 * @return the waybillActive
	 */
	public String getWaybillActive() {
		return waybillActive;
	}

	/**
	 * Sets the waybill active.
	 * 
	 * @param waybillActive
	 *            the waybillActive to see
	 */
	public void setWaybillActive(String waybillActive) {
		this.waybillActive = waybillActive;
	}

	/**
	 * Gets the exception type.
	 * 
	 * @return the exceptionType
	 */
	public String getExceptionType() {
		return exceptionType;
	}

	/**
	 * Sets the exception type.
	 * 
	 * @param exceptionType
	 *            the exceptionType to see
	 */
	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	/**
	 * Gets the exception status.
	 * 
	 * @return the exceptionStatus
	 */
	public String getExceptionStatus() {
		return exceptionStatus;
	}

	/**
	 * Sets the exception status.
	 * 
	 * @param exceptionStatus
	 *            the exceptionStatus to see
	 */
	public void setExceptionStatus(String exceptionStatus) {
		this.exceptionStatus = exceptionStatus;
	}

	/**
	 * Gets the deliver region code.
	 * 
	 * @return the deliverRegionCode
	 */
	public String getDeliverRegionCode() {
		return deliverRegionCode;
	}

	/**
	 * Sets the deliver region code.
	 * 
	 * @param deliverRegionCode
	 *            the deliverRegionCode to see
	 */
	public void setDeliverRegionCode(String deliverRegionCode) {
		this.deliverRegionCode = deliverRegionCode;
	}

	/**
	 * Gets the checks if is sent required.
	 * 
	 * @return the isSentRequired
	 */
	public String getIsSentRequired() {
		return isSentRequired;
	}

	/**
	 * Sets the checks if is sent required.
	 * 
	 * @param isSentRequired
	 *            the isSentRequired to see
	 */
	public void setIsSentRequired(String isSentRequired) {
		this.isSentRequired = isSentRequired;
	}

	/**
	 * Gets the arrivesheet status.
	 * 
	 * @return the arrivesheetStatus
	 */
	public String getArrivesheetStatus() {
		return arrivesheetStatus;
	}

	/**
	 * Sets the arrivesheet status.
	 * 
	 * @param arrivesheetStatus
	 *            the arrivesheetStatus to see
	 */
	public void setArrivesheetStatus(String arrivesheetStatus) {
		this.arrivesheetStatus = arrivesheetStatus;
	}

	/**
	 * Gets the arrivesheet active.
	 * 
	 * @return the arrivesheetActive
	 */
	public String getArrivesheetActive() {
		return arrivesheetActive;
	}

	/**
	 * Sets the arrivesheet active.
	 * 
	 * @param arrivesheetActive
	 *            the arrivesheetActive to see
	 */
	public void setArrivesheetActive(String arrivesheetActive) {
		this.arrivesheetActive = arrivesheetActive;
	}

	/**
	 * Gets the goods volume total.
	 * 
	 * @return the goodsVolumeTotal
	 */
	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	/**
	 * Sets the goods volume total.
	 * 
	 * @param goodsVolumeTotal
	 *            the goodsVolumeTotal to see
	 */
	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	/**
	 * Gets the pay amount.
	 * 
	 * @return the payAmount
	 */
	public BigDecimal getPayAmount() {
		return payAmount;
	}

	/**
	 * Sets the pay amount.
	 * 
	 * @param payAmount
	 *            the payAmount to see
	 */
	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	/**
	 * Gets the fast waybill flag.
	 * 
	 * @return the fastWaybillFlag
	 */
	public Short getFastWaybillFlag() {
		return fastWaybillFlag;
	}

	/**
	 * Sets the fast waybill flag.
	 * 
	 * @param fastWaybillFlag
	 *            the fastWaybillFlag to see
	 */
	public void setFastWaybillFlag(Short fastWaybillFlag) {
		this.fastWaybillFlag = fastWaybillFlag;
	}

	/**
	 * Gets the fast waybill code.
	 * 
	 * @return the fastWaybillCode
	 */
	public String getFastWaybillCode() {
		return fastWaybillCode;
	}

	/**
	 * Sets the fast waybill code.
	 * 
	 * @param fastWaybillCode
	 *            the fastWaybillCode to see
	 */
	public void setFastWaybillCode(String fastWaybillCode) {
		this.fastWaybillCode = fastWaybillCode;
	}

	/**
	 * Gets the notify success flag.
	 * 
	 * @return the notifySuccessFlag
	 */
	public String getNotifySuccessFlag() {
		return notifySuccessFlag;
	}

	/**
	 * Sets the notify success flag.
	 * 
	 * @param notifySuccessFlag
	 *            the notifySuccessFlag to see
	 */
	public void setNotifySuccessFlag(String notifySuccessFlag) {
		this.notifySuccessFlag = notifySuccessFlag;
	}

	/**
	 * Gets the arrangable goods qty.
	 * 
	 * @return the arrangableGoodsQty
	 */
	public Integer getArrangableGoodsQty() {
		return arrangableGoodsQty;
	}

	/**
	 * Sets the arrangable goods qty.
	 * 
	 * @param arrangableGoodsQty
	 *            the arrangableGoodsQty to see
	 */
	public void setArrangableGoodsQty(Integer arrangableGoodsQty) {
		this.arrangableGoodsQty = arrangableGoodsQty;
	}

	/**
	 * Gets the failed to arrange reason.
	 * 
	 * @return the failedToArrangeReason
	 */
	public String getFailedToArrangeReason() {
		return failedToArrangeReason;
	}

	/**
	 * Sets the failed to arrange reason.
	 * 
	 * @param failedToArrangeReason
	 *            the failedToArrangeReason to see
	 */
	public void setFailedToArrangeReason(String failedToArrangeReason) {
		this.failedToArrangeReason = failedToArrangeReason;
	}

	/**
	 * Gets the currency code.
	 * 
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * Sets the currency code.
	 * 
	 * @param currencyCode
	 *            the currencyCode to see
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * Gets the return bill type.
	 * 
	 * @return the returnBillType
	 */
	public String getReturnBillType() {
		return returnBillType;
	}

	/**
	 * Sets the return bill type.
	 * 
	 * @param returnBillType
	 *            the returnBillType to see
	 */
	public void setReturnBillType(String returnBillType) {
		this.returnBillType = returnBillType;
	}

	/**
	 * Gets the goods package.
	 * 
	 * @return the goodsPackage
	 */
	public String getGoodsPackage() {
		return goodsPackage;
	}

	/**
	 * Sets the goods package.
	 * 
	 * @param goodsPackage
	 *            the goodsPackage to see
	 */
	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	/**
	 * Gets the org code.
	 * 
	 * @return the org code
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * Sets the org code.
	 * 
	 * @param orgCode
	 *            the new org code
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * Gets the arrivesheet destroyed.
	 *
	 * @return the arrivesheet destroyed
	 */
	public String getArrivesheetDestroyed() {
		return arrivesheetDestroyed;
	}

	/**
	 * Sets the arrivesheet destroyed.
	 *
	 * @param arrivesheetDestroyed the new arrivesheet destroyed
	 */
	public void setArrivesheetDestroyed(String arrivesheetDestroyed) {
		this.arrivesheetDestroyed = arrivesheetDestroyed;
	}

	/**
	 * Gets the stock good qty.
	 *
	 * @return the stock good qty
	 */
	public String getStockGoodQty()
	{
		return stockGoodQty;
	}

	/**
	 * Sets the stock good qty.
	 *
	 * @param stockGoodQty the new stock good qty
	 */
	public void setStockGoodQty(String stockGoodQty)
	{
		this.stockGoodQty = stockGoodQty;
	}

	/**
	 * Gets the end stock org code.
	 *
	 * @return the end stock org code
	 */
	public String getEndStockOrgCode() {
		return endStockOrgCode;
	}

	/**
	 * Sets the end stock org code.
	 *
	 * @param endStockOrgCode the new end stock org code
	 */
	public void setEndStockOrgCode(String endStockOrgCode) {
		this.endStockOrgCode = endStockOrgCode;
	}

	/**
	 * Gets the goods area code.
	 *
	 * @return the goods area code
	 */
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}

	/**
	 * Sets the goods area code.
	 *
	 * @param goodsAreaCode the new goods area code
	 */
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}

	public Date getArriveTimeBegin() {
		return arriveTimeBegin;
	}

	public void setArriveTimeBegin(Date arriveTimeBegin) {
		this.arriveTimeBegin = arriveTimeBegin;
	}

	public Date getArriveTimeEnd() {
		return arriveTimeEnd;
	}

	public void setArriveTimeEnd(Date arriveTimeEnd) {
		this.arriveTimeEnd = arriveTimeEnd;
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

	public String getRfcStatusAuditDeny() {
		return rfcStatusAuditDeny;
	}

	public void setRfcStatusAuditDeny(String rfcStatusAuditDeny) {
		this.rfcStatusAuditDeny = rfcStatusAuditDeny;
	}

	public String getRfcStatusAcceptDeny() {
		return rfcStatusAcceptDeny;
	}

	public void setRfcStatusAcceptDeny(String rfcStatusAcceptDeny) {
		this.rfcStatusAcceptDeny = rfcStatusAcceptDeny;
	}

	public Date getDeliverTime() {
		return deliverTime;
	}

	public void setDeliverTime(Date deliverTime) {
		this.deliverTime = deliverTime;
	}

	public String[] getArrayWaybillNos() {
		return arrayWaybillNos;
	}

	public void setArrayWaybillNos(String[] arrayWaybillNos) {
		this.arrayWaybillNos = arrayWaybillNos;
	}

	public String getIsNotification() {
		return isNotification;
	}

	public void setIsNotification(String isNotification) {
		this.isNotification = isNotification;
	}

	public Date getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}

	public List<String> getDeliverRegionList() {
		return deliverRegionList;
	}

	public void setDeliverRegionList(List<String> deliverRegionList) {
		this.deliverRegionList = deliverRegionList;
	}

	public String getIsStoring() {
		return isStoring;
	}

	public void setIsStoring(String isStoring) {
		this.isStoring = isStoring;
	}

	public String getDeliverRegionName() {
		return deliverRegionName;
	}

	public void setDeliverRegionName(String deliverRegionName) {
		this.deliverRegionName = deliverRegionName;
	}

	public String getDeliverSuggest() {
		return deliverSuggest;
	}

	public void setDeliverSuggest(String deliverSuggest) {
		this.deliverSuggest = deliverSuggest;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getTogetherSendCode() {
		return togetherSendCode;
	}

	public void setTogetherSendCode(String togetherSendCode) {
		this.togetherSendCode = togetherSendCode;
	}

	public String[] getProductCodes() {
		return productCodes;
	}

	public void setProductCodes(String[] productCodes) {
		this.productCodes = productCodes;
	}

	public String getIsExpress() {
		return isExpress;
	}

	public void setIsExpress(String isExpress) {
		this.isExpress = isExpress;
	}

	public String getConsigneeAddressNote() {
		return consigneeAddressNote;
	}

	public void setConsigneeAddressNote(String consigneeAddressNote) {
		this.consigneeAddressNote = consigneeAddressNote;
	}

	public Integer getBillQty() {
		return billQty;
	}

	public void setBillQty(Integer billQty) {
		this.billQty = billQty;
	}

	public String getSrvDeliverHandoverbillId() {
		return srvDeliverHandoverbillId;
	}

	public void setSrvDeliverHandoverbillId(String srvDeliverHandoverbillId) {
		this.srvDeliverHandoverbillId = srvDeliverHandoverbillId;
	}

	public List<String> getNotReceviedMethods() {
		return notReceviedMethods;
	}

	public void setNotReceviedMethods(List<String> notReceviedMethods) {
		this.notReceviedMethods = notReceviedMethods;
	}

	public Date getCashTime() {
		return cashTime;
	}

	public void setCashTime(Date cashTime) {
		this.cashTime = cashTime;
	}
	
}