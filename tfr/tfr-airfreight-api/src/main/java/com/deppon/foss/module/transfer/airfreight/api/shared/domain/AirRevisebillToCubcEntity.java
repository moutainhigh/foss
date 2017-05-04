package com.deppon.foss.module.transfer.airfreight.api.shared.domain;

import java.util.List;

/**
 * 
 * @description FOSS推送合大票信息至CUBC系统--FOSS推送实体
 * @author 316759-foss-RuipengWang
 * @date 2016-10-21 15:21:11 PM
 *
 */
public class AirRevisebillToCubcEntity {

	/**
	 * 合大票清单
	 */
	private AirPickupbillEntity airPickupbillEntity;
	
	/**
	 * 合大票清单明细
	 */
	private List<AirPickupbillDetailEntity> stlModifyList;
	
	/**
	 * 删除列表,用于调用结算接口
	 */
	List<String> stlDeleteList;

	public AirPickupbillEntity getAirPickupbillEntity() {
		return airPickupbillEntity;
	}

	public void setAirPickupbillEntity(AirPickupbillEntity airPickupbillEntity) {
		this.airPickupbillEntity = airPickupbillEntity;
	}

	public List<AirPickupbillDetailEntity> getStlModifyList() {
		return stlModifyList;
	}

	public void setStlModifyList(List<AirPickupbillDetailEntity> stlModifyList) {
		this.stlModifyList = stlModifyList;
	}

	public List<String> getStlDeleteList() {
		return stlDeleteList;
	}

	public void setStlDeleteList(List<String> stlDeleteList) {
		this.stlDeleteList = stlDeleteList;
	}

}
