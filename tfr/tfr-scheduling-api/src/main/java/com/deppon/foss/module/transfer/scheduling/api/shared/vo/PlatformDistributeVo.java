package com.deppon.foss.module.transfer.scheduling.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PlatformPreAssignEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PlatformDistributeQcDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PlatformDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PlatformQcDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PlatformVehicleInfoDto;

public class PlatformDistributeVo implements Serializable {

	private static final long serialVersionUID = -8700326045658673929L;

	/**
	 * 分配月台界面 查询车辆信息的查询条件
	 */
	private PlatformDistributeQcDto platformDistributeQcDto;

	/**
	 * 在途公司车
	 */
	private List<PlatformVehicleInfoDto> vehicleInfosCompanyOnTheWay;

	/**
	 * 在途外请车
	 */
	private List<PlatformVehicleInfoDto> vehicleInfosLeasedOnTheWay;

	/**
	 * 到达未分配
	 */
	private List<PlatformVehicleInfoDto> vehicleInfosArrived;

	/**
	 * 获取最优月台时的查询条件
	 */
	private PlatformQcDto platformQcDto;

	/**
	 * 系统给出的最优月台建议
	 */
	private List<PlatformDto> platformDtos;

	/**
	 * 预分配记录
	 */
	private PlatformPreAssignEntity platformPreAssignEntity;

	private String parentTfrCtrCode;

	private String parentTfrCtrName;

	public String getParentTfrCtrCode() {
		return parentTfrCtrCode;
	}

	public void setParentTfrCtrCode(String parentTfrCtrCode) {
		this.parentTfrCtrCode = parentTfrCtrCode;
	}

	public String getParentTfrCtrName() {
		return parentTfrCtrName;
	}

	public void setParentTfrCtrName(String parentTfrCtrName) {
		this.parentTfrCtrName = parentTfrCtrName;
	}

	public PlatformDistributeQcDto getPlatformDistributeQcDto() {
		return platformDistributeQcDto;
	}

	public void setPlatformDistributeQcDto(
			PlatformDistributeQcDto platformDistributeQcDto) {
		this.platformDistributeQcDto = platformDistributeQcDto;
	}

	public List<PlatformVehicleInfoDto> getVehicleInfosCompanyOnTheWay() {
		return vehicleInfosCompanyOnTheWay;
	}

	public void setVehicleInfosCompanyOnTheWay(
			List<PlatformVehicleInfoDto> vehicleInfosCompanyOnTheWay) {
		this.vehicleInfosCompanyOnTheWay = vehicleInfosCompanyOnTheWay;
	}

	public List<PlatformVehicleInfoDto> getVehicleInfosLeasedOnTheWay() {
		return vehicleInfosLeasedOnTheWay;
	}

	public void setVehicleInfosLeasedOnTheWay(
			List<PlatformVehicleInfoDto> vehicleInfosLeasedOnTheWay) {
		this.vehicleInfosLeasedOnTheWay = vehicleInfosLeasedOnTheWay;
	}

	public List<PlatformVehicleInfoDto> getVehicleInfosArrived() {
		return vehicleInfosArrived;
	}

	public void setVehicleInfosArrived(
			List<PlatformVehicleInfoDto> vehicleInfosArrived) {
		this.vehicleInfosArrived = vehicleInfosArrived;
	}

	public PlatformQcDto getPlatformQcDto() {
		return platformQcDto;
	}

	public void setPlatformQcDto(PlatformQcDto platformQcDto) {
		this.platformQcDto = platformQcDto;
	}

	public List<PlatformDto> getPlatformDtos() {
		return platformDtos;
	}

	public void setPlatformDtos(List<PlatformDto> platformDtos) {
		this.platformDtos = platformDtos;
	}

	public PlatformPreAssignEntity getPlatformPreAssignEntity() {
		return platformPreAssignEntity;
	}

	public void setPlatformPreAssignEntity(
			PlatformPreAssignEntity platformPreAssignEntity) {
		this.platformPreAssignEntity = platformPreAssignEntity;
	}

}
