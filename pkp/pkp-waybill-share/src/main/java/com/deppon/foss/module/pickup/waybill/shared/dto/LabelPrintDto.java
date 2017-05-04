package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.Date;
/**
 * pda接受
 * fanbangyu
 */

public class LabelPrintDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id ;
	/**
	 * 运单号
	 */
	private String wblCode;
	/**
	 * 条码
	 */
	private String barcode;
	
	/**
	 * 送标记
	 */
	private String send;

	/**
	 * 件数
	 */
	private String pieces;
	
	/**
	 * 运输性质
	 */
	private String transType;
	
	/**
	 * 包装类型
	 */
	private String wrapType;
	
	/**
	 * 目的地名称
	 */
	private String destinationName;
	
	
	private String userCode;
	/**
	 * 到达外场名称
	 */
	private String destTransCenterName;
	/**
	 * 始发城市名称
	 */
	private String departmentCityName;
	/**
	 * 目的站提货网点编码
	 */
	private String destStationNumber;
	
	/**
	 * 路由库位信息
	 */
	private String  goodsAreas;
	
	/**
	 * 货物类型
	 */
	private String goodsType;
	
	/**
	 * 是否发货大客户
	 */
	private String deliveryBigCustomer;
	
	/**
	 * 是否收货大客户
	 */
	private String receiveBigCustomer;
	
	/**
	 * 到达门店编码
	 */
	private String arriveStoreNUM;
	/**
	 * 是否有效
	 */
	private String active;
	 /**
     * FOSS获得数据时系统时间
     */
    private Date foss_systime;

	/**
	 * 创建时间
	 * @return
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date  modifyTime;
	
	/**
	 * 客户编码
	 * @author 245120 | fanbangyu
	 * @date 2015-09-17 10:33
	 */
	private String customerLableNums;
	/**
	 * 流水号
	 */
	private String seriCode;
	
	/**
	 * 是否展会货
	 */
	private String isExhibitCargo ;
	
	
	public Date getFoss_systime() {
		return foss_systime;
	}

	public void setFoss_systime(Date fossSystime) {
		this.foss_systime = fossSystime;
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

	public String getArriveStoreNUM() {
		return arriveStoreNUM;
	}
	
	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public void setArriveStoreNUM(String arriveStoreNUM) {
		this.arriveStoreNUM = arriveStoreNUM;
	}

	
	public String getSeriCode() {
		return seriCode;
	}

	public void setSeriCode(String seriCode) {
		this.seriCode = seriCode;
	}

	
	
	public String getDeliveryBigCustomer() {
		return deliveryBigCustomer;
	}

	public void setDeliveryBigCustomer(String deliveryBigCustomer) {
		this.deliveryBigCustomer = deliveryBigCustomer;
	}

	public String getReceiveBigCustomer() {
		return receiveBigCustomer;
	}

	public void setReceiveBigCustomer(String receiveBigCustomer) {
		this.receiveBigCustomer = receiveBigCustomer;
	}

	public String getIsExhibitCargo() {
		return isExhibitCargo;
	}

	public void setIsExhibitCargo(String isExhibitCargo) {
		this.isExhibitCargo = isExhibitCargo;
	}
	/*zhangchengfu 20150516 FOSS展会货开单提示优化需求 end*/
	public String getWblCode() {
		return wblCode;
	}

	public void setWblCode(String wblCode) {
		this.wblCode = wblCode;
	}

	public String getPieces() {
		return pieces;
	}

	public void setPieces(String pieces) {
		this.pieces = pieces;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getWrapType() {
		return wrapType;
	}

	public void setWrapType(String wrapType) {
		this.wrapType = wrapType;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getDestTransCenterName() {
		return destTransCenterName;
	}

	public void setDestTransCenterName(String destTransCenterName) {
		this.destTransCenterName = destTransCenterName;
	}

	public String getDepartmentCityName() {
		return departmentCityName;
	}

	public void setDepartmentCityName(String departmentCityName) {
		this.departmentCityName = departmentCityName;
	}

	public String getDestStationNumber() {
		return destStationNumber;
	}

	public void setDestStationNumber(String destStationNumber) {
		this.destStationNumber = destStationNumber;
	}

	public String getGoodsAreas() {
		return goodsAreas;
	}

	public void setGoodsAreas(String goodsAreas) {
		this.goodsAreas = goodsAreas;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	public String getSend() {
		return send;
	}

	public void setSend(String send) {
		this.send = send;
	}
	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getCustomerLableNums() {
		return customerLableNums;
	}

	public void setCustomerLableNums(String customerLableNums) {
		this.customerLableNums = customerLableNums;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
