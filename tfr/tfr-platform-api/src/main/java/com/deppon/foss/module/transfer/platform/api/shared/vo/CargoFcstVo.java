package com.deppon.foss.module.transfer.platform.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.dto.CargoFcstDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.CargoFcstResultDto;

public class CargoFcstVo implements Serializable {

	private static final long serialVersionUID = 1203429670313431736L;

	/**
	 * 外场货量预测配置
	 */
	private String config;

	/**
	 * 外场编码
	 */
	private String tfrCtrCode;

	/**
	 * 外场名称
	 */
	private String tfrCtrName;

	/**
	 * 查询条件
	 */
	private CargoFcstDto cargoFcstDto;

	/**
	 * 返回结果
	 */
	private List<CargoFcstResultDto> cargoFcstResultDtos;

	public String getTfrCtrCode() {
		return tfrCtrCode;
	}

	public void setTfrCtrCode(String tfrCtrCode) {
		this.tfrCtrCode = tfrCtrCode;
	}

	public String getTfrCtrName() {
		return tfrCtrName;
	}

	public void setTfrCtrName(String tfrCtrName) {
		this.tfrCtrName = tfrCtrName;
	}

	public CargoFcstDto getCargoFcstDto() {
		return cargoFcstDto;
	}

	public void setCargoFcstDto(CargoFcstDto cargoFcstDto) {
		this.cargoFcstDto = cargoFcstDto;
	}

	public List<CargoFcstResultDto> getCargoFcstResultDtos() {
		return cargoFcstResultDtos;
	}

	public void setCargoFcstResultDtos(
			List<CargoFcstResultDto> cargoFcstResultDtos) {
		this.cargoFcstResultDtos = cargoFcstResultDtos;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

}
