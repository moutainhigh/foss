package com.deppon.foss.module.pickup.predeliver.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

public class DeliverHandoverbillEntity {
	/**
	 * id
	 */
    private String id;
    /**
     * 运单号
     */
    private String waybillNo;
    /**
     * 运输性质
     */
    private String productCode;
    /**
     * 通知情况
     */
    private String noticeContent;
    /**
     * 提货方式
     */
    private String receiveMethod;
    /**
     * 预计送货日期
     */
    private Date preDeliverDate;
    /**
     * 送货时间段
     */
    private String deliveryTimeInterval;
    /**
     * 开单件数
     */
    private Integer goodsQtyTotal;
    /**
     * 总重量
     */
    private BigDecimal goodsWeight;
    /**
     * 总体积
     */
    private BigDecimal goodsVolume;
    /**
     * 发票类型
     */
    private String invoiceType;
    /**
     * 是否有效
     */
    private String active;
    /**
     * 交单类型
     */
    private String billType;
    /**
     * 交单操作人名称
     */
    private String billOperateName;
    /**
     * 交单操作人
     */
    private String billOperateCode;
    /**
     * 交单时间
     */
    private Date billTime;
    /**
     * 车牌号
     */
    private String vehicleNo;
    /**
     * 交单件数
     */
    private Integer billQty;
    /**
     * 最终配载部门
     */
    private String lastLoadOrgCode;
    /**
     * 送货时间点(起)
     */
    private String deliveryTimeStart;
    /**
     * 送货时间点(止)
     */
    private String deliveryTimeOver;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 交单操作部门CODE
     */
    private String billOperateOrgCode;
    /**
     * 交单操作部门名称 
     */
    private String billOperateOrgName;
    /**
     * 理货员退回原因
     */
    private String tallymanReturnReason;
    /**
     * 晚交运单
     */
    private String lateNo;
    /**
     * 货物包装
     */
    private String goodsPackage;
    /**
     * 货物尺寸
     */
    private String goodsSize;
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
	 * 经度
	 */
	private String longitude;
	/**
	 * 纬度
	 */
	private String latitude;
	/**
	 *特殊地址类型
	 */
	private String specialAddressType;
	/**
	 * 匹配送货小区类型
	 */
	private String matchType;
	/**
	 * 收货人地址备注
	 */
	private String receiveCustomerAddressNote;
	
	/**
	 * 送货小区CODE
	 */
    private String smallzoneCode;
    /**
     * 到付金额
     */
    private BigDecimal toPayAmount;

    /**
     * 是否会展货
     */
    private String isExhibition;
    
    /**
     * 是否空车出
     */
    private String isEmptyCar;
    
    /**
     * 是否超远派送
     */
    private String uitraLongDelivery;
    /**
     * 是否Gis处理过
     */
    private String isGisDeal;
    /**
     * 是否使用(防止并发)
     */
    private String isUsed;
    /**
     * 送货小区名称
     */
    private String smallzoneName;
    private String oldActive;
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
     * 发票类型备注
     */
    private String invoiceDetail;
    public void setIsUsed(String isUsed) {
        this.isUsed = isUsed;
    }

    public String getIsUsed() {
        return isUsed;
    }
    /**
     * 送货要求
     */
    private String deliverRequire;
    /**
     * 车辆是否排班
     */
    private String isVehicleScheduling;
    public void setIsExhibition(String isExhibition) {
        this.isExhibition = isExhibition;
    }

    public void setUitraLongDelivery(String uitraLongDelivery) {
        this.uitraLongDelivery = uitraLongDelivery;
    }

    public String getIsExhibition() {

        return isExhibition;
    }

    public String getUitraLongDelivery() {
        return uitraLongDelivery;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo == null ? null : waybillNo.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent == null ? null : noticeContent.trim();
    }

    public String getReceiveMethod() {
        return receiveMethod;
    }

    public void setReceiveMethod(String receiveMethod) {
        this.receiveMethod = receiveMethod == null ? null : receiveMethod.trim();
    }

   

	/**
	 * 获取preDeliverDate  
	 * @return preDeliverDate preDeliverDate
	 */
	public Date getPreDeliverDate() {
		return preDeliverDate;
	}

	/**
	 * 设置preDeliverDate  
	 * @param preDeliverDate preDeliverDate 
	 */
	public void setPreDeliverDate(Date preDeliverDate) {
		this.preDeliverDate = preDeliverDate;
	}

	public String getDeliveryTimeInterval() {
        return deliveryTimeInterval;
    }

    public void setDeliveryTimeInterval(String deliveryTimeInterval) {
        this.deliveryTimeInterval = deliveryTimeInterval == null ? null : deliveryTimeInterval.trim();
    }

  

    public BigDecimal getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(BigDecimal goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public BigDecimal getGoodsVolume() {
        return goodsVolume;
    }

    public void setGoodsVolume(BigDecimal goodsVolume) {
        this.goodsVolume = goodsVolume;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType == null ? null : invoiceType.trim();
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active == null ? null : active.trim();
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType == null ? null : billType.trim();
    }

    public String getBillOperateName() {
        return billOperateName;
    }

    public void setBillOperateName(String billOperateName) {
        this.billOperateName = billOperateName == null ? null : billOperateName.trim();
    }

    public String getBillOperateCode() {
        return billOperateCode;
    }

    public void setBillOperateCode(String billOperateCode) {
        this.billOperateCode = billOperateCode == null ? null : billOperateCode.trim();
    }

    public Date getBillTime() {
        return billTime;
    }

    public void setBillTime(Date billTime) {
        this.billTime = billTime;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo == null ? null : vehicleNo.trim();
    }

   
    /**
	 * 获取goodsQtyTotal  
	 * @return goodsQtyTotal goodsQtyTotal
	 */
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	/**
	 * 设置goodsQtyTotal  
	 * @param goodsQtyTotal goodsQtyTotal 
	 */
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	/**
	 * 获取billQty  
	 * @return billQty billQty
	 */
	public Integer getBillQty() {
		return billQty;
	}

	/**
	 * 设置billQty  
	 * @param billQty billQty 
	 */
	public void setBillQty(Integer billQty) {
		this.billQty = billQty;
	}



    /**
	 * 获取lastLoadOrgCode  
	 * @return lastLoadOrgCode lastLoadOrgCode
	 */
	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}

	/**
	 * 设置lastLoadOrgCode  
	 * @param lastLoadOrgCode lastLoadOrgCode 
	 */
	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}

	public String getDeliveryTimeStart() {
        return deliveryTimeStart;
    }

    public void setDeliveryTimeStart(String deliveryTimeStart) {
        this.deliveryTimeStart = deliveryTimeStart == null ? null : deliveryTimeStart.trim();
    }

    public String getDeliveryTimeOver() {
        return deliveryTimeOver;
    }

    public void setDeliveryTimeOver(String deliveryTimeOver) {
        this.deliveryTimeOver = deliveryTimeOver == null ? null : deliveryTimeOver.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getBillOperateOrgCode() {
        return billOperateOrgCode;
    }

    public void setBillOperateOrgCode(String billOperateOrgCode) {
        this.billOperateOrgCode = billOperateOrgCode == null ? null : billOperateOrgCode.trim();
    }

    public String getBillOperateOrgName() {
        return billOperateOrgName;
    }

    public void setBillOperateOrgName(String billOperateOrgName) {
        this.billOperateOrgName = billOperateOrgName == null ? null : billOperateOrgName.trim();
    }

	
	

	/**
	 * 获取matchType  
	 * @return matchType matchType
	 */
	public String getMatchType() {
		return matchType;
	}

	/**
	 * 设置matchType  
	 * @param matchType matchType 
	 */
	public void setMatchType(String matchType) {
		this.matchType = matchType;
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
	 * 获取smallzoneCode  
	 * @return smallzoneCode smallzoneCode
	 */
	public String getSmallzoneCode() {
		return smallzoneCode;
	}

	/**
	 * 设置smallzoneCode  
	 * @param smallzoneCode smallzoneCode 
	 */
	public void setSmallzoneCode(String smallzoneCode) {
		this.smallzoneCode = smallzoneCode;
	}


	/**
	 * 获取specialAddressType  
	 * @return specialAddressType specialAddressType
	 */
	public String getSpecialAddressType() {
		return specialAddressType;
	}

	/**
	 * 设置specialAddressType  
	 * @param specialAddressType specialAddressType 
	 */
	public void setSpecialAddressType(String specialAddressType) {
		this.specialAddressType = specialAddressType;
	}

	/**
	 * 获取tallymanReturnReason  
	 * @return tallymanReturnReason tallymanReturnReason
	 */
	public String getTallymanReturnReason() {
		return tallymanReturnReason;
	}

	/**
	 * 设置tallymanReturnReason  
	 * @param tallymanReturnReason tallymanReturnReason 
	 */
	public void setTallymanReturnReason(String tallymanReturnReason) {
		this.tallymanReturnReason = tallymanReturnReason;
	}

	/**
	 * 获取lateNo  
	 * @return lateNo lateNo
	 */
	public String getLateNo() {
		return lateNo;
	}

	/**
	 * 设置lateNo  
	 * @param lateNo lateNo 
	 */
	public void setLateNo(String lateNo) {
		this.lateNo = lateNo;
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
	 * 获取goodsSize  
	 * @return goodsSize goodsSize
	 */
	public String getGoodsSize() {
		return goodsSize;
	}

	/**
	 * 设置goodsSize  
	 * @param goodsSize goodsSize 
	 */
	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
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
	 * 获取toPayAmount  
	 * @return toPayAmount toPayAmount
	 */
	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}

	/**
	 * 设置toPayAmount  
	 * @param toPayAmount toPayAmount 
	 */
	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
	}

	/**
	 * 获取isGisDeal  
	 * @return isGisDeal isGisDeal
	 */
	public String getIsGisDeal() {
		return isGisDeal;
	}

	/**
	 * 设置isGisDeal  
	 * @param isGisDeal isGisDeal 
	 */
	public void setIsGisDeal(String isGisDeal) {
		this.isGisDeal = isGisDeal;
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
	 * 获取smallzoneName  
	 * @return smallzoneName smallzoneName
	 */
	public String getSmallzoneName() {
		return smallzoneName;
	}

	/**
	 * 设置smallzoneName  
	 * @param smallzoneName smallzoneName 
	 */
	public void setSmallzoneName(String smallzoneName) {
		this.smallzoneName = smallzoneName;
	}

    public String getOldActive() {
        return oldActive;
    }

    public void setOldActive(String oldActive) {
        this.oldActive = oldActive;
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

	public String getIsEmptyCar() {
		return isEmptyCar;
	}

	public void setIsEmptyCar(String isEmptyCar) {
		this.isEmptyCar = isEmptyCar;
	}
	public String getInvoiceDetail() {
		return invoiceDetail;
	}

	public void setInvoiceDetail(String invoiceDetail) {
		this.invoiceDetail = invoiceDetail;
	}
    
}