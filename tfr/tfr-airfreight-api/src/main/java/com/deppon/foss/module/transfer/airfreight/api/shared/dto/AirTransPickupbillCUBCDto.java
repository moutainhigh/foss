package com.deppon.foss.module.transfer.airfreight.api.shared.dto;

import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTransPickupDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTransPickupbillEntity;

public class AirTransPickupbillCUBCDto {
	//中转提货清单
	private AirTransPickupbillEntity airTransPickupbillEntity;
	//新增列表,用于调用结算接口
	List<AirTransPickupDetailEntity> stlAddList;
	//修改列表,用于调用结算接口
	List<AirTransPickupDetailEntity> airTransPickupDetailEntities;
	//删除列表,用于调用结算接口
	List<String> stlDeleteList;
	//当前登录人
//	private CurrentInfo currentInfo;
	
	public List<AirTransPickupDetailEntity> getStlAddList() {
		return stlAddList;
	}
	public void setStlAddList(List<AirTransPickupDetailEntity> stlAddList) {
		this.stlAddList = stlAddList;
	}
	
	/**
	 * @return the airTransPickupbillEntity
	 */
	public AirTransPickupbillEntity getAirTransPickupbillEntity() {
		return airTransPickupbillEntity;
	}
	/**
	 * @param airTransPickupbillEntity the airTransPickupbillEntity to set
	 */
	public void setAirTransPickupbillEntity(AirTransPickupbillEntity airTransPickupbillEntity) {
		this.airTransPickupbillEntity = airTransPickupbillEntity;
	}
	/**
	 * @return the airTransPickupDetailEntities
	 */
	public List<AirTransPickupDetailEntity> getAirTransPickupDetailEntities() {
		return airTransPickupDetailEntities;
	}
	/**
	 * @param airTransPickupDetailEntities the airTransPickupDetailEntities to set
	 */
	public void setAirTransPickupDetailEntities(List<AirTransPickupDetailEntity> airTransPickupDetailEntities) {
		this.airTransPickupDetailEntities = airTransPickupDetailEntities;
	}

	
	public List<String> getStlDeleteList() {
		return stlDeleteList;
	}
	public void setStlDeleteList(List<String> stlDeleteList) {
		this.stlDeleteList = stlDeleteList;
	}
//	public CurrentInfo getCurrentInfo() {
//		return currentInfo;
//	}
//	public void setCurrentInfo(CurrentInfo currentInfo) {
//		this.currentInfo = currentInfo;
//	}
	
	
}
