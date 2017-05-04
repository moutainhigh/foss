package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.io.Serializable;
import java.util.List;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;

/**
 * 标签打印信息实体-图片开单推送app
 *<p>Title: LabelPrintEntity.java</p>
 * @author yuanjinbiao/ yuanjb@deppon.com
 * <p>Copyright: Copyright (c) 2014</p>
 * @version 1.0
 * 2014年7月28日
 */
public class LabelPrintEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -546231353136286890L;

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
	private List<GoodsAreaEntity>  goodsAreas;
	
	private String goodsType;
	/*zhangchengfu 20150516 FOSS展会货开单提示优化需求 begin*/
	/**
	 * 是否发货大客户
	 */
	private String deliveryBigCustomer;
	
	/**
	 * 是否收货大客户
	 */
	private String receiveBigCustomer;
	
	/**
	 * 是否展会货
	 */
	private String isExhibitCargo ;
	
	
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

	public List<GoodsAreaEntity> getGoodsAreas() {
		return goodsAreas;
	}

	public void setGoodsAreas(List<GoodsAreaEntity> goodsAreas) {
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
}
