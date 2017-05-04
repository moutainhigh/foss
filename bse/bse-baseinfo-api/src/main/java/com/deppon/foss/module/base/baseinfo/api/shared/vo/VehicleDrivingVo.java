package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleDrivingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleDrivingQueryDto;
/**
 * 长途车队信息
 * @author 323136
 *
 */
public class VehicleDrivingVo {
	/**
	 * 长途车队信息  查询参数Dto
	 *  @author 307196
	 */

	private VehicleDrivingQueryDto vehicleDrivingQueryDto ;
	/**
	 * 长途车队信息
	 */
	private VehicleDrivingEntity vehicleDrivingEntity;
	
	/**
	 * 长途车队信息返回结果集
	 */
	private List<VehicleDrivingEntity> vehicleDrivingEntityList;

	public VehicleDrivingQueryDto getVehicleDrivingQueryDto() {
		return vehicleDrivingQueryDto;
	}

	public void setVehicleDrivingQueryDto(
			VehicleDrivingQueryDto vehicleDrivingQueryDto) {
		this.vehicleDrivingQueryDto = vehicleDrivingQueryDto;
	}

	public VehicleDrivingEntity getVehicleDrivingEntity() {
		return vehicleDrivingEntity;
	}

	public void setVehicleDrivingEntity(VehicleDrivingEntity vehicleDrivingEntity) {
		this.vehicleDrivingEntity = vehicleDrivingEntity;
	}

	public List<VehicleDrivingEntity> getVehicleDrivingEntityList() {
		return vehicleDrivingEntityList;
	}

	public void setVehicleDrivingEntityList(
			List<VehicleDrivingEntity> vehicleDrivingEntityList) {
		this.vehicleDrivingEntityList = vehicleDrivingEntityList;
	}
	


}

