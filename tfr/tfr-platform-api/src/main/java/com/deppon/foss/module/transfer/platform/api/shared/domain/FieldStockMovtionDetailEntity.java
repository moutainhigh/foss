/**
 * 
 */
package com.deppon.foss.module.transfer.platform.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * @author 105795
 * @desc 场内库存流动明细
 */
public class FieldStockMovtionDetailEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5410165841829363887L;
	//运单号
	private String waybillNo;
	//运输类型
	private String transportType;
	//提货方式
	private String pickupMethod;
	//开单件数
	private int goodsQtyTotal; 
	//开单重量(千克)
	private BigDecimal goodsWeightTotal;  
	//开单体积(方)
	private BigDecimal goodsVolumeTotal; 
	//当前件数
	private int currentGoodsQty;
	//当前重量(千克)
	private BigDecimal currentWeight;
	//当前体积(方)
	private BigDecimal currentVolume;
	//外场名称
	private String transferCenterName;
	//外场编码
	private String transferCenterCode;
	//当前位置
	private String currentLocaton;
	//车牌号
	private String vehicleNo;
	//上一部门
	private String lastOrgName;
	private String lastOrgCode;
	//下一部门
	private String nextOrgName;
	private String nextOrgCode;
	
	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	/**
	 * @return the transportType
	 */
	public String getTransportType() {
		return transportType;
	}
	/**
	 * @param transportType the transportType to set
	 */
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	/**
	 * @return the pickupMethod
	 */
	public String getPickupMethod() {
		return pickupMethod;
	}
	/**
	 * @param pickupMethod the pickupMethod to set
	 */
	public void setPickupMethod(String pickupMethod) {
		this.pickupMethod = pickupMethod;
	}
	/**
	 * @return the goodsQtyTotal
	 */
	public int getGoodsQtyTotal() {
		return goodsQtyTotal;
	}
	/**
	 * @param goodsQtyTotal the goodsQtyTotal to set
	 */
	public void setGoodsQtyTotal(int goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}
	/**
	 * @return the goodsWeightTotal
	 */
	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}
	/**
	 * @param goodsWeightTotal the goodsWeightTotal to set
	 */
	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}
	/**
	 * @return the goodsVolumeTotal
	 */
	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}
	/**
	 * @param goodsVolumeTotal the goodsVolumeTotal to set
	 */
	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}
	/**
	 * @return the currentWeight
	 */
	public BigDecimal getCurrentWeight() {
		return currentWeight;
	}
	/**
	 * @param currentWeight the currentWeight to set
	 */
	public void setCurrentWeight(BigDecimal currentWeight) {
		this.currentWeight = currentWeight;
	}
	/**
	 * @return the currentVolume
	 */
	public BigDecimal getCurrentVolume() {
		return currentVolume;
	}
	/**
	 * @param currentVolume the currentVolume to set
	 */
	public void setCurrentVolume(BigDecimal currentVolume) {
		this.currentVolume = currentVolume;
	}
	/**
	 * @return the transferCenterName
	 */
	public String getTransferCenterName() {
		return transferCenterName;
	}
	/**
	 * @param transferCenterName the transferCenterName to set
	 */
	public void setTransferCenterName(String transferCenterName) {
		this.transferCenterName = transferCenterName;
	}
	/**
	 * @return the transferCenterCode
	 */
	public String getTransferCenterCode() {
		return transferCenterCode;
	}
	/**
	 * @param transferCenterCode the transferCenterCode to set
	 */
	public void setTransferCenterCode(String transferCenterCode) {
		this.transferCenterCode = transferCenterCode;
	}
	
	
	
	/**
	 * @return the currentLocaton
	 */
	public String getCurrentLocaton() {
		return currentLocaton;
	}
	/**
	 * @param currentLocaton the currentLocaton to set
	 */
	public void setCurrentLocaton(String currentLocaton) {
		this.currentLocaton = currentLocaton;
	}
	/**
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	/**
	 * @param vehicleNo the vehicleNo to set
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	/**
	 * @return the lastOrgName
	 */
	public String getLastOrgName() {
		return lastOrgName;
	}
	/**
	 * @param lastOrgName the lastOrgName to set
	 */
	public void setLastOrgName(String lastOrgName) {
		this.lastOrgName = lastOrgName;
	}
	/**
	 * @return the lastOrgCode
	 */
	public String getLastOrgCode() {
		return lastOrgCode;
	}
	/**
	 * @param lastOrgCode the lastOrgCode to set
	 */
	public void setLastOrgCode(String lastOrgCode) {
		this.lastOrgCode = lastOrgCode;
	}
	/**
	 * @return the nextOrgName
	 */
	public String getNextOrgName() {
		return nextOrgName;
	}
	/**
	 * @param nextOrgName the nextOrgName to set
	 */
	public void setNextOrgName(String nextOrgName) {
		this.nextOrgName = nextOrgName;
	}
	/**
	 * @return the nextOrgCode
	 */
	public String getNextOrgCode() {
		return nextOrgCode;
	}
	/**
	 * @param nextOrgCode the nextOrgCode to set
	 */
	public void setNextOrgCode(String nextOrgCode) {
		this.nextOrgCode = nextOrgCode;
	}
	/**
	 * @return the currentGoodsQty
	 */
	public int getCurrentGoodsQty() {
		return currentGoodsQty;
	}
	/**
	 * @param currentGoodsQty the currentGoodsQty to set
	 */
	public void setCurrentGoodsQty(int currentGoodsQty) {
		this.currentGoodsQty = currentGoodsQty;
	}
	
}
