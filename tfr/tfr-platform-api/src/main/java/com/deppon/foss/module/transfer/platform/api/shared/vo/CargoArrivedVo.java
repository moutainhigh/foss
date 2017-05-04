package com.deppon.foss.module.transfer.platform.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.dto.CargoArrivedDetailDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.CargoArrivedDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.CargoArrivedQcDto;

public class CargoArrivedVo implements Serializable {

	private static final long serialVersionUID = 686392852879852366L;

	private String transferCenterCode;

	private String transferCenterName;

	private CargoArrivedQcDto cargoArrivedQcDto;

	private List<CargoArrivedDto> cargoArrivedDtos;

	private List<CargoArrivedDetailDto> cargoArrivedDetailDtos;

	public String getTransferCenterCode() {
		return transferCenterCode;
	}

	public void setTransferCenterCode(String transferCenterCode) {
		this.transferCenterCode = transferCenterCode;
	}

	public String getTransferCenterName() {
		return transferCenterName;
	}

	public void setTransferCenterName(String transferCenterName) {
		this.transferCenterName = transferCenterName;
	}

	public CargoArrivedQcDto getCargoArrivedQcDto() {
		return cargoArrivedQcDto;
	}

	public void setCargoArrivedQcDto(CargoArrivedQcDto cargoArrivedQcDto) {
		this.cargoArrivedQcDto = cargoArrivedQcDto;
	}

	public List<CargoArrivedDto> getCargoArrivedDtos() {
		return cargoArrivedDtos;
	}

	public void setCargoArrivedDtos(List<CargoArrivedDto> cargoArrivedDtos) {
		this.cargoArrivedDtos = cargoArrivedDtos;
	}

	public List<CargoArrivedDetailDto> getCargoArrivedDetailDtos() {
		return cargoArrivedDetailDtos;
	}

	public void setCargoArrivedDetailDtos(
			List<CargoArrivedDetailDto> cargoArrivedDetailDtos) {
		this.cargoArrivedDetailDtos = cargoArrivedDetailDtos;
	}

}
