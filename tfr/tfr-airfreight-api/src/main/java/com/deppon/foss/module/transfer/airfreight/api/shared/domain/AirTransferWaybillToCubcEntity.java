package com.deppon.foss.module.transfer.airfreight.api.shared.domain;

import java.util.List;

/**
 * 
 * @description FOSS推送中转提货清单至CUBC系统--FOSS推送实体
 * @author 316759-foss-RuipengWang
 * @date 2016-10-27 17:12:30 PM
 *
 */
public class AirTransferWaybillToCubcEntity {
	
	/**
	 * 中转提货清单
	 */
	private AirTransPickupbillEntity airTransPickupbillEntity;
	
	/**
	 * 合大票请单明细
	 */
	private List<AirTransPickupDetailEntity> airTransPickupDetailEntities;
	
	/**
	 * 删除列表
	 */
	private List<String> stlDeleteList;

	public AirTransPickupbillEntity getAirTransPickupbillEntity() {
		return airTransPickupbillEntity;
	}

	public void setAirTransPickupbillEntity(
			AirTransPickupbillEntity airTransPickupbillEntity) {
		this.airTransPickupbillEntity = airTransPickupbillEntity;
	}

	public List<AirTransPickupDetailEntity> getAirTransPickupDetailEntities() {
		return airTransPickupDetailEntities;
	}

	public void setAirTransPickupDetailEntities(
			List<AirTransPickupDetailEntity> airTransPickupDetailEntities) {
		this.airTransPickupDetailEntities = airTransPickupDetailEntities;
	}

	public List<String> getStlDeleteList() {
		return stlDeleteList;
	}

	public void setStlDeleteList(List<String> stlDeleteList) {
		this.stlDeleteList = stlDeleteList;
	}

}
