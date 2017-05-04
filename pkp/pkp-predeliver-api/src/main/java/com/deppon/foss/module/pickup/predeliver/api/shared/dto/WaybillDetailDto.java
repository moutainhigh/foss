package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WaybillDetailDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;												      //ID
	private String waybillNo;                                             //运单号
	/**
	 * 送货地址 4级
	 */
	private String deliveryAddress;               						 
	private String consigneeAddress;//收货地址
	private int preArrangeGoodsQty;                                                //预排单件数
	private int arrangeGoodsQty;                                                //排单件数
	private int arrangableGoodsQty;//可排件数
	private String suggestionTreatment;									  //预处理建议（小区/车辆）
	private double arrangedWeight;                               			  //排单重量
	private double arrangedVolume;                                           //排单体积
	private String goodsSize;                                   		  //尺寸
	private String goodsPackage;                             			  //包装
	private String addressType;                                           //特殊地址类型
	private String goodsType;											  //特殊货物类型
	private String prouctCode;                                			  //运输性质
	private Date deliverDate;                           		          //预计送货日期
	private String deliveryTimeInterval;                                           //送货时间段
	private String deliverTimeStartAndOver;								  //送货时间点
	private int billQty;                                      			  //交单件数
	private int stockQty; 												  //库存件数
	private int goodsQtyTotal;                               				  //开单件数
	private BigDecimal payAmount;                           			          //到付金额
	private String ertuenReason;                    					  //理货员退回原因
	private String deliverRequire;                                        //送货要求
	private Date cashTime; //规定兑现时间
    /**
     * 实际送货小区CODE(人工在创建派送单(新)预处理建议里修改)
     */
    private String actualSmallzoneCode;
    /**
     * 实际送货小区名称(人工在创建派送单(新)预处理建议里修改)
     */
    private String actualSmallzoneName;
    /**
     * 实际车牌号(人工在创建派送单(新)预处理建议里修改)
     */
    private String actualVehicleNo;
    /**
	 * 合送编码
	 */
	private String togetherSendCode;
	/**
	 * 是否大客户
	 */
	private String receiveBigCustomer;
	private double goodsVolumeTotal;                               			  //开单重量
	private double weight;                                           //开单体积
	/** 提货方式. */
	private String receiveMethod;
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
	/** 收货地址.四级 */
	private String receiveCustomerAddress;	
	/** 收货地址 */
	private String receiveCustomerAddressNote;	
	private String longitude; //自动排序-经度
	private String latitude; //自动排序-纬度
	/**
	 * 交单id
	 */
	private String srvDeliverHandoverbillId;
	/** 尺寸. */
	private String dimension;
	/** 货物状态. */
	private String goodsStatus;
	/**
	 * 车辆是否排班
	 */
	private String isVehicleScheduling; 
    /**
     * 是否超远派送
     */
    private String uitraLongDelivery;
    private String actualProvN; //实际收货地址省
	private String actualCityN; //实际收货地址市
	private String actualDistrictN; //实际收货地址区域
	private String goodsName; //货物名称
	private Date arriveTime; //到达时间
	private Date inStockTime; //入库时间
	
	private Integer returnNumber; //退回次数

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
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	
	public String getSuggestionTreatment() {
		return suggestionTreatment;
	}
	public void setSuggestionTreatment(String suggestionTreatment) {
		this.suggestionTreatment = suggestionTreatment;
	}
	public String getGoodsSize() {
		return goodsSize;
	}
	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
	}
	
	public String getAddressType() {
		return addressType;
	}
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	public String getProuctCode() {
		return prouctCode;
	}
	public void setProuctCode(String prouctCode) {
		this.prouctCode = prouctCode;
	}
	
	/**
	 * 获取deliverDate  
	 * @return deliverDate deliverDate
	 */
	public Date getDeliverDate() {
		return deliverDate;
	}
	/**
	 * 设置deliverDate  
	 * @param deliverDate deliverDate 
	 */
	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}
	public String getDeliverTimeStartAndOver() {
		return deliverTimeStartAndOver;
	}
	public void setDeliverTimeStartAndOver(String deliverTimeStartAndOver) {
		this.deliverTimeStartAndOver = deliverTimeStartAndOver;
	}
	public int getBillQty() {
		return billQty;
	}
	public void setBillQty(int billQty) {
		this.billQty = billQty;
	}
	public int getStockQty() {
		return stockQty;
	}
	public void setStockQty(int stockQty) {
		this.stockQty = stockQty;
	}
	
	
	/**
	 * 获取actualSmallzoneCode  
	 * @return actualSmallzoneCode actualSmallzoneCode
	 */
	public String getActualSmallzoneCode() {
		return actualSmallzoneCode;
	}
	/**
	 * 设置actualSmallzoneCode  
	 * @param actualSmallzoneCode actualSmallzoneCode 
	 */
	public void setActualSmallzoneCode(String actualSmallzoneCode) {
		this.actualSmallzoneCode = actualSmallzoneCode;
	}
	/**
	 * 获取actualSmallzoneName  
	 * @return actualSmallzoneName actualSmallzoneName
	 */
	public String getActualSmallzoneName() {
		return actualSmallzoneName;
	}
	/**
	 * 设置actualSmallzoneName  
	 * @param actualSmallzoneName actualSmallzoneName 
	 */
	public void setActualSmallzoneName(String actualSmallzoneName) {
		this.actualSmallzoneName = actualSmallzoneName;
	}
	/**
	 * 获取actualVehicleNo  
	 * @return actualVehicleNo actualVehicleNo
	 */
	public String getActualVehicleNo() {
		return actualVehicleNo;
	}
	/**
	 * 设置actualVehicleNo  
	 * @param actualVehicleNo actualVehicleNo 
	 */
	public void setActualVehicleNo(String actualVehicleNo) {
		this.actualVehicleNo = actualVehicleNo;
	}
	/**
	 * 获取togetherSendCode  
	 * @return togetherSendCode togetherSendCode
	 */
	public String getTogetherSendCode() {
		return togetherSendCode;
	}
	/**
	 * 设置togetherSendCode  
	 * @param togetherSendCode togetherSendCode 
	 */
	public void setTogetherSendCode(String togetherSendCode) {
		this.togetherSendCode = togetherSendCode;
	}
	/**
	 * 获取preArrangeGoodsQty  
	 * @return preArrangeGoodsQty preArrangeGoodsQty
	 */
	public int getPreArrangeGoodsQty() {
		return preArrangeGoodsQty;
	}
	/**
	 * 设置preArrangeGoodsQty  
	 * @param preArrangeGoodsQty preArrangeGoodsQty 
	 */
	public void setPreArrangeGoodsQty(int preArrangeGoodsQty) {
		this.preArrangeGoodsQty = preArrangeGoodsQty;
	}
	/**
	 * 获取arrangeGoodsQty  
	 * @return arrangeGoodsQty arrangeGoodsQty
	 */
	public int getArrangeGoodsQty() {
		return arrangeGoodsQty;
	}
	/**
	 * 设置arrangeGoodsQty  
	 * @param arrangeGoodsQty arrangeGoodsQty 
	 */
	public void setArrangeGoodsQty(int arrangeGoodsQty) {
		this.arrangeGoodsQty = arrangeGoodsQty;
	}
	/**
	 * 获取arrangedWeight  
	 * @return arrangedWeight arrangedWeight
	 */
	public double getArrangedWeight() {
		return arrangedWeight;
	}
	/**
	 * 设置arrangedWeight  
	 * @param arrangedWeight arrangedWeight 
	 */
	public void setArrangedWeight(double arrangedWeight) {
		this.arrangedWeight = arrangedWeight;
	}
	/**
	 * 获取arrangedVolume  
	 * @return arrangedVolume arrangedVolume
	 */
	public double getArrangedVolume() {
		return arrangedVolume;
	}
	/**
	 * 设置arrangedVolume  
	 * @param arrangedVolume arrangedVolume 
	 */
	public void setArrangedVolume(double arrangedVolume) {
		this.arrangedVolume = arrangedVolume;
	}
	/**
	 * 获取goodsQtyTotal  
	 * @return goodsQtyTotal goodsQtyTotal
	 */
	public int getGoodsQtyTotal() {
		return goodsQtyTotal;
	}
	/**
	 * 设置goodsQtyTotal  
	 * @param goodsQtyTotal goodsQtyTotal 
	 */
	public void setGoodsQtyTotal(int goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}
	/**
	 * 获取ertuenReason  
	 * @return ertuenReason ertuenReason
	 */
	public String getErtuenReason() {
		return ertuenReason;
	}
	/**
	 * 设置ertuenReason  
	 * @param ertuenReason ertuenReason 
	 */
	public void setErtuenReason(String ertuenReason) {
		this.ertuenReason = ertuenReason;
	}
	/**
	 * 获取deliverRequire  
	 * @return deliverRequire deliverRequire
	 */
	public String getDeliverRequire() {
		return deliverRequire;
	}
	/**
	 * 设置deliverRequire  
	 * @param deliverRequire deliverRequire 
	 */
	public void setDeliverRequire(String deliverRequire) {
		this.deliverRequire = deliverRequire;
	}
	
	/**
	 * 获取receiveBigCustomer  
	 * @return receiveBigCustomer receiveBigCustomer
	 */
	public String getReceiveBigCustomer() {
		return receiveBigCustomer;
	}
	/**
	 * 设置receiveBigCustomer  
	 * @param receiveBigCustomer receiveBigCustomer 
	 */
	public void setReceiveBigCustomer(String receiveBigCustomer) {
		this.receiveBigCustomer = receiveBigCustomer;
	}
	/**
	 * 获取goodsVolumeTotal  
	 * @return goodsVolumeTotal goodsVolumeTotal
	 */
	public double getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}
	/**
	 * 设置goodsVolumeTotal  
	 * @param goodsVolumeTotal goodsVolumeTotal 
	 */
	public void setGoodsVolumeTotal(double goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}
	
	/**
	 * 获取weight  
	 * @return weight weight
	 */
	public double getWeight() {
		return weight;
	}
	/**
	 * 设置weight  
	 * @param weight weight 
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}
	/**
	 * 获取deliveryTimeInterval  
	 * @return deliveryTimeInterval deliveryTimeInterval
	 */
	public String getDeliveryTimeInterval() {
		return deliveryTimeInterval;
	}
	/**
	 * 设置deliveryTimeInterval  
	 * @param deliveryTimeInterval deliveryTimeInterval 
	 */
	public void setDeliveryTimeInterval(String deliveryTimeInterval) {
		this.deliveryTimeInterval = deliveryTimeInterval;
	}
	/**
	 * 获取arrangableGoodsQty  
	 * @return arrangableGoodsQty arrangableGoodsQty
	 */
	public int getArrangableGoodsQty() {
		return arrangableGoodsQty;
	}
	/**
	 * 设置arrangableGoodsQty  
	 * @param arrangableGoodsQty arrangableGoodsQty 
	 */
	public void setArrangableGoodsQty(int arrangableGoodsQty) {
		this.arrangableGoodsQty = arrangableGoodsQty;
	}
	/**
	 * 获取payAmount  
	 * @return payAmount payAmount
	 */
	public BigDecimal getPayAmount() {
		return payAmount;
	}
	/**
	 * 设置payAmount  
	 * @param payAmount payAmount 
	 */
	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}
	/**
	 * 获取receiveMethod  
	 * @return receiveMethod receiveMethod
	 */
	public String getReceiveMethod() {
		return receiveMethod;
	}
	/**
	 * 设置receiveMethod  
	 * @param receiveMethod receiveMethod 
	 */
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}
	/**
	 * 获取consigneeAddress  
	 * @return consigneeAddress consigneeAddress
	 */
	public String getConsigneeAddress() {
		return consigneeAddress;
	}
	/**
	 * 设置consigneeAddress  
	 * @param consigneeAddress consigneeAddress 
	 */
	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}
	/**
	 * 获取receiveCustomerProvCode  
	 * @return receiveCustomerProvCode receiveCustomerProvCode
	 */
	public String getReceiveCustomerProvCode() {
		return receiveCustomerProvCode;
	}
	/**
	 * 设置receiveCustomerProvCode  
	 * @param receiveCustomerProvCode receiveCustomerProvCode 
	 */
	public void setReceiveCustomerProvCode(String receiveCustomerProvCode) {
		this.receiveCustomerProvCode = receiveCustomerProvCode;
	}
	/**
	 * 获取receiveCustomerCityCode  
	 * @return receiveCustomerCityCode receiveCustomerCityCode
	 */
	public String getReceiveCustomerCityCode() {
		return receiveCustomerCityCode;
	}
	/**
	 * 设置receiveCustomerCityCode  
	 * @param receiveCustomerCityCode receiveCustomerCityCode 
	 */
	public void setReceiveCustomerCityCode(String receiveCustomerCityCode) {
		this.receiveCustomerCityCode = receiveCustomerCityCode;
	}
	/**
	 * 获取receiveCustomerDistCode  
	 * @return receiveCustomerDistCode receiveCustomerDistCode
	 */
	public String getReceiveCustomerDistCode() {
		return receiveCustomerDistCode;
	}
	/**
	 * 设置receiveCustomerDistCode  
	 * @param receiveCustomerDistCode receiveCustomerDistCode 
	 */
	public void setReceiveCustomerDistCode(String receiveCustomerDistCode) {
		this.receiveCustomerDistCode = receiveCustomerDistCode;
	}

	/**
	 * 获取receiveCustomerAddress  
	 * @return receiveCustomerAddress receiveCustomerAddress
	 */
	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}
	/**
	 * 设置receiveCustomerAddress  
	 * @param receiveCustomerAddress receiveCustomerAddress 
	 */
	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}

	/**
	 * 获取receiveCustomerAddressNote  
	 * @return receiveCustomerAddressNote receiveCustomerAddressNote
	 */
	public String getReceiveCustomerAddressNote() {
		return receiveCustomerAddressNote;
	}
	/**
	 * 设置receiveCustomerAddressNote  
	 * @param receiveCustomerAddressNote receiveCustomerAddressNote 
	 */
	public void setReceiveCustomerAddressNote(String receiveCustomerAddressNote) {
		this.receiveCustomerAddressNote = receiveCustomerAddressNote;
	}
	/**
	 * 获取goodsPackage  
	 * @return goodsPackage goodsPackage
	 */
	public String getGoodsPackage() {
		return goodsPackage;
	}
	/**
	 * 设置goodsPackage  
	 * @param goodsPackage goodsPackage 
	 */
	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}
	/**
	 * 获取longitude  
	 * @return longitude longitude
	 */
	public String getLongitude() {
		return longitude;
	}
	/**
	 * 设置longitude  
	 * @param longitude longitude 
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	/**
	 * 获取latitude  
	 * @return latitude latitude
	 */
	public String getLatitude() {
		return latitude;
	}
	/**
	 * 设置latitude  
	 * @param latitude latitude 
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	/**
	 * 获取srvDeliverHandoverbillId  
	 * @return srvDeliverHandoverbillId srvDeliverHandoverbillId
	 */
	public String getSrvDeliverHandoverbillId() {
		return srvDeliverHandoverbillId;
	}
	/**
	 * 设置srvDeliverHandoverbillId  
	 * @param srvDeliverHandoverbillId srvDeliverHandoverbillId 
	 */
	public void setSrvDeliverHandoverbillId(String srvDeliverHandoverbillId) {
		this.srvDeliverHandoverbillId = srvDeliverHandoverbillId;
	}
	/**
	 * 获取dimension  
	 * @return dimension dimension
	 */
	public String getDimension() {
		return dimension;
	}
	/**
	 * 设置dimension  
	 * @param dimension dimension 
	 */
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	/**
	 * 获取goodsStatus  
	 * @return goodsStatus goodsStatus
	 */
	public String getGoodsStatus() {
		return goodsStatus;
	}
	/**
	 * 设置goodsStatus  
	 * @param goodsStatus goodsStatus 
	 */
	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}
	/**
	 * 获取isVehicleScheduling  
	 * @return isVehicleScheduling isVehicleScheduling
	 */
	public String getIsVehicleScheduling() {
		return isVehicleScheduling;
	}
	/**
	 * 设置isVehicleScheduling  
	 * @param isVehicleScheduling isVehicleScheduling 
	 */
	public void setIsVehicleScheduling(String isVehicleScheduling) {
		this.isVehicleScheduling = isVehicleScheduling;
	}
	/**
	 * 获取uitraLongDelivery  
	 * @return uitraLongDelivery uitraLongDelivery
	 */
	public String getUitraLongDelivery() {
		return uitraLongDelivery;
	}
	/**
	 * 设置uitraLongDelivery  
	 * @param uitraLongDelivery uitraLongDelivery 
	 */
	public void setUitraLongDelivery(String uitraLongDelivery) {
		this.uitraLongDelivery = uitraLongDelivery;
	}
	/**
	 * 获取actualProvN  
	 * @return actualProvN actualProvN
	 */
	public String getActualProvN() {
		return actualProvN;
	}
	/**
	 * 设置actualProvN  
	 * @param actualProvN actualProvN 
	 */
	public void setActualProvN(String actualProvN) {
		this.actualProvN = actualProvN;
	}
	/**
	 * 获取actualCityN  
	 * @return actualCityN actualCityN
	 */
	public String getActualCityN() {
		return actualCityN;
	}
	/**
	 * 设置actualCityN  
	 * @param actualCityN actualCityN 
	 */
	public void setActualCityN(String actualCityN) {
		this.actualCityN = actualCityN;
	}
	/**
	 * 获取actualDistrictN  
	 * @return actualDistrictN actualDistrictN
	 */
	public String getActualDistrictN() {
		return actualDistrictN;
	}
	/**
	 * 设置actualDistrictN  
	 * @param actualDistrictN actualDistrictN 
	 */
	public void setActualDistrictN(String actualDistrictN) {
		this.actualDistrictN = actualDistrictN;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Date getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	public Date getInStockTime() {
		return inStockTime;
	}

	public void setInStockTime(Date inStockTime) {
		this.inStockTime = inStockTime;
	}
	public Integer getReturnNumber() {
		return returnNumber;
	}
	public void setReturnNumber(Integer returnNumber) {
		this.returnNumber = returnNumber;
	}
	public Date getCashTime() {
		return cashTime;
	}
	public void setCashTime(Date cashTime) {
		this.cashTime = cashTime;
	}

}
