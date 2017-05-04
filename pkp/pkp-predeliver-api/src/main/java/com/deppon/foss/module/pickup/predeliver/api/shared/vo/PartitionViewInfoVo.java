package com.deppon.foss.module.pickup.predeliver.api.shared.vo;

import java.util.Date;

/** 
 * @ClassName: PartitionViewInfoVo 
 * @Description: 分区查看Vo 
 * @author 237982-foss-fangwenjun 
 * @date 2015-6-19 上午9:43:42 
 *  
 */
public class PartitionViewInfoVo {

	/**
	 * 送货日期
	 */
	private Date deliveryDate;
	
	/**
	 * 运输性质
	 */
	private String productCode;
	
	/**
	 * 车队组
	 */
	private String fleetGroup;
	
	/**
	 * 货物状态
	 */
	private String goodStatus;
	
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	
	/**
	 * 小区编码数组
	 */
	private String[] smallZoneCodes;
	
	/**
	 * 大区编码数组
	 */
	private String[] bigZoneCodes;
	
	/**
	 * 虚拟编码
	 */
	private String  regionalVirtaulCode;
	
	/**
	 * 操作部门编码
	 */
	private String operateOrgCode;
	
	/**
     * 库区
     */
	private String goodsAreaCode;
	
	/**
	 * 最后库存code
	 */
	private String endStockOrgCode;
	
	/**
	 * 最终配载部门
	 */
	private String lastLoadOrgCode;

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

	/**
	 * 获取productCode
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * 设置productCode
	 * @param productCode 要设置的productCode
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * 获取fleetGroup
	 * @return the fleetGroup
	 */
	public String getFleetGroup() {
		return fleetGroup;
	}

	/**
	 * 设置fleetGroup
	 * @param fleetGroup 要设置的fleetGroup
	 */
	public void setFleetGroup(String fleetGroup) {
		this.fleetGroup = fleetGroup;
	}

	/**
	 * 获取goodStatus
	 * @return the goodStatus
	 */
	public String getGoodStatus() {
		return goodStatus;
	}

	/**
	 * 设置goodStatus
	 * @param goodStatus 要设置的goodStatus
	 */
	public void setGoodStatus(String goodStatus) {
		this.goodStatus = goodStatus;
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
	 * 获取smallZoneCodes
	 * @return the smallZoneCodes
	 */
	public String[] getSmallZoneCodes() {
		return smallZoneCodes;
	}

	/**
	 * 设置smallZoneCodes
	 * @param smallZoneCodes 要设置的smallZoneCodes
	 */
	public void setSmallZoneCodes(String[] smallZoneCodes) {
		this.smallZoneCodes = smallZoneCodes;
	}

	/**
	 * 获取bigZoneCodes
	 * @return the bigZoneCodes
	 */
	public String[] getBigZoneCodes() {
		return bigZoneCodes;
	}

	/**
	 * 设置bigZoneCodes
	 * @param bigZoneCodes 要设置的bigZoneCodes
	 */
	public void setBigZoneCodes(String[] bigZoneCodes) {
		this.bigZoneCodes = bigZoneCodes;
	}

	/**
	 * 获取operateOrgCode
	 * @return the operateOrgCode
	 */
	public String getOperateOrgCode() {
		return operateOrgCode;
	}

	/**
	 * 设置operateOrgCode
	 * @param operateOrgCode 要设置的operateOrgCode
	 */
	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
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
	 * 获取goodsAreaCode
	 * @return the goodsAreaCode
	 */
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}

	/**
	 * 设置goodsAreaCode
	 * @param goodsAreaCode 要设置的goodsAreaCode
	 */
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}

	/**
	 * 获取endStockOrgCode
	 * @return the endStockOrgCode
	 */
	public String getEndStockOrgCode() {
		return endStockOrgCode;
	}

	/**
	 * 设置endStockOrgCode
	 * @param endStockOrgCode 要设置的endStockOrgCode
	 */
	public void setEndStockOrgCode(String endStockOrgCode) {
		this.endStockOrgCode = endStockOrgCode;
	}

	/**
	 * 获取lastLoadOrgCode
	 * @return the lastLoadOrgCode
	 */
	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}

	/**
	 * 设置lastLoadOrgCode
	 * @param lastLoadOrgCode 要设置的lastLoadOrgCode
	 */
	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}
	
}
