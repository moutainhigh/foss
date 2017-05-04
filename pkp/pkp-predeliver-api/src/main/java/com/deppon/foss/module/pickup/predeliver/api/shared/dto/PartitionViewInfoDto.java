package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * @ClassName: PartitionViewInfoDto 
 * @Description: 分区查看相关信息 
 * @author 237982-foss-fangwenjun 
 * @date 2015-6-18 下午4:03:42 
 *  
 */
public class PartitionViewInfoDto implements Serializable{

	/**
	 * 类序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 区域名称
	 */
	private String regionalName;
	
	/**
	 * 区域编码
	 */
	private String regionalCode;
	
	/**
	 * 区域虚拟编码
	 */
	private String regionalVirtaulCode;
	
	/**
	 * 已交单总票数
	 */
	private Integer handoverBillCount;
	
	/**
	 * 重量总和
	 */
	private double goodsWeightSum;
	
	/**
	 * 体积总和
	 */
	private double goodsVolumeSum;
	
	/**
	 * 件数总和
	 */
	private Integer goodsQtyTotalSum;
	
	/**
	 * 定区车
	 */
	private String vehicleNo;
	
	/**
	 * 车型名
	 */
	private String truckModelValue;
	
	/**
	 * 班次
	 */
	private String frequencyNo;
	
	/**
	 * 预计带货方数（方/F）
	 */
	private BigDecimal expectedBringVolume;
	
	/**
	 * 送货日期
	 */
	private Date deliveryDate;

	/**
	 * 获取regionalName
	 * @return the regionalName
	 */
	public String getRegionalName() {
		return regionalName;
	}

	/**
	 * 设置regionalName
	 * @param regionalName 要设置的regionalName
	 */
	public void setRegionalName(String regionalName) {
		this.regionalName = regionalName;
	}

	/**
	 * 获取regionalCode
	 * @return the regionalCode
	 */
	public String getRegionalCode() {
		return regionalCode;
	}

	/**
	 * 设置regionalCode
	 * @param regionalCode 要设置的regionalCode
	 */
	public void setRegionalCode(String regionalCode) {
		this.regionalCode = regionalCode;
	}

	/**
	 * 获取regionalVirtaulCode
	 * @return the regionalVirtaulCode
	 */
	public String getRegionalVirtaulCode() {
		return regionalVirtaulCode;
	}

	/**
	 * 设置regionalVirtaulCode
	 * @param regionalVirtaulCode 要设置的regionalVirtaulCode
	 */
	public void setRegionalVirtaulCode(String regionalVirtaulCode) {
		this.regionalVirtaulCode = regionalVirtaulCode;
	}

	/**
	 * 获取handoverBillCount
	 * @return the handoverBillCount
	 */
	public Integer getHandoverBillCount() {
		return handoverBillCount;
	}

	/**
	 * 设置handoverBillCount
	 * @param handoverBillCount 要设置的handoverBillCount
	 */
	public void setHandoverBillCount(Integer handoverBillCount) {
		this.handoverBillCount = handoverBillCount;
	}

	/**
	 * 获取goodsWeightSum
	 * @return the goodsWeightSum
	 */
	public double getGoodsWeightSum() {
		return goodsWeightSum;
	}

	/**
	 * 设置goodsWeightSum
	 * @param goodsWeightSum 要设置的goodsWeightSum
	 */
	public void setGoodsWeightSum(double goodsWeightSum) {
		BigDecimal b = new BigDecimal(goodsWeightSum);
		this.goodsWeightSum = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 获取goodsVolumeSum
	 * @return the goodsVolumeSum
	 */
	public double getGoodsVolumeSum() {
		return goodsVolumeSum;
	}

	/**
	 * 设置goodsVolumeSum
	 * @param goodsVolumeSum 要设置的goodsVolumeSum
	 */
	public void setGoodsVolumeSum(double goodsVolumeSum) {
		BigDecimal b = new BigDecimal(goodsVolumeSum);
		this.goodsVolumeSum = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 获取goodsQtyTotalSum
	 * @return the goodsQtyTotalSum
	 */
	public Integer getGoodsQtyTotalSum() {
		return goodsQtyTotalSum;
	}

	/**
	 * 设置goodsQtyTotalSum
	 * @param goodsQtyTotalSum 要设置的goodsQtyTotalSum
	 */
	public void setGoodsQtyTotalSum(Integer goodsQtyTotalSum) {
		this.goodsQtyTotalSum = goodsQtyTotalSum;
	}

	/**
	 * 获取vehicleNo
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * 设置vehicleNo
	 * @param vehicleNo 要设置的vehicleNo
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获取truckModelValue
	 * @return the truckModelValue
	 */
	public String getTruckModelValue() {
		return truckModelValue;
	}

	/**
	 * 设置truckModelValue
	 * @param truckModelValue 要设置的truckModelValue
	 */
	public void setTruckModelValue(String truckModelValue) {
		this.truckModelValue = truckModelValue;
	}

	/**
	 * 获取frequencyNo
	 * @return the frequencyNo
	 */
	public String getFrequencyNo() {
		return frequencyNo;
	}

	/**
	 * 设置frequencyNo
	 * @param frequencyNo 要设置的frequencyNo
	 */
	public void setFrequencyNo(String frequencyNo) {
		this.frequencyNo = frequencyNo;
	}

	/**
	 * 获取expectedBringVolume
	 * @return the expectedBringVolume
	 */
	public BigDecimal getExpectedBringVolume() {
		return expectedBringVolume;
	}

	/**
	 * 设置expectedBringVolume
	 * @param expectedBringVolume 要设置的expectedBringVolume
	 */
	public void setExpectedBringVolume(BigDecimal expectedBringVolume) {
		this.expectedBringVolume = expectedBringVolume;
	}

	/**
	 * 获取deliveryDate
	 * @return the deliveryDate
	 */
	public Date getDeliveryDate() {
		return deliveryDate;
	}

	/**
	 * 设置deliveryDate
	 * @param deliveryDate 要设置的deliveryDate
	 */
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
}
