package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CenterAndDeliveryAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CenterAndDeliveryAreaDto;

public class CenterAndDeliveryAreaVo {
	/**
	 * 
	 */
	private CenterAndDeliveryAreaDto centerAndDeliveryAreaDto;
	/**
	 * 									
	 */
	private CenterAndDeliveryAreaEntity centerAndDeliveryAreaEntity;

	
	
	private String status;
	/**
	 * 
	 * @return
	 */
	public CenterAndDeliveryAreaDto getCenterAndDeliveryAreaDto() {
		return centerAndDeliveryAreaDto;
	}
	/**
	 * 
	 * @param centerAndDeliveryAreaDto
	 */
	public void setCenterAndDeliveryAreaDto(
			CenterAndDeliveryAreaDto centerAndDeliveryAreaDto) {
		this.centerAndDeliveryAreaDto = centerAndDeliveryAreaDto;
	}
	/**
	 * 
	 * @return
	 */
	public CenterAndDeliveryAreaEntity getCenterAndDeliveryAreaEntity() {
		return centerAndDeliveryAreaEntity;
	}
	/**
	 * 
	 * @param centerAndDeliveryAreaEntity
	 */
	public void setCenterAndDeliveryAreaEntity(
			CenterAndDeliveryAreaEntity centerAndDeliveryAreaEntity) {
		this.centerAndDeliveryAreaEntity = centerAndDeliveryAreaEntity;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	

}
