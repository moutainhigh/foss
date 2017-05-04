package com.deppon.foss.module.transfer.platform.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LineCargoDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LineCargoQcDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LineCargoSerialNoDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LineCargoTotalDto;

public class LineCargoVo implements Serializable {

	private static final long serialVersionUID = 1888935010382596891L;

	private String transferCenterCode;

	private String transferCenterName;

	private List<BaseDataDictDto> products;

	private LineCargoQcDto lineCargoQcDto;

	private List<LineCargoDto> lineCargoDtos;

	private LineCargoTotalDto lineCargoTotalDto;

	private List<LineCargoSerialNoDto> serialNoDtos;

	public List<BaseDataDictDto> getProducts() {
		return products;
	}

	public void setProducts(List<BaseDataDictDto> products) {
		this.products = products;
	}

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

	public LineCargoQcDto getLineCargoQcDto() {
		return lineCargoQcDto;
	}

	public void setLineCargoQcDto(LineCargoQcDto lineCargoQcDto) {
		this.lineCargoQcDto = lineCargoQcDto;
	}

	public List<LineCargoDto> getLineCargoDtos() {
		return lineCargoDtos;
	}

	public void setLineCargoDtos(List<LineCargoDto> lineCargoDtos) {
		this.lineCargoDtos = lineCargoDtos;
	}

	public LineCargoTotalDto getLineCargoTotalDto() {
		return lineCargoTotalDto;
	}

	public void setLineCargoTotalDto(LineCargoTotalDto lineCargoTotalDto) {
		this.lineCargoTotalDto = lineCargoTotalDto;
	}

	public List<LineCargoSerialNoDto> getSerialNoDtos() {
		return serialNoDtos;
	}

	public void setSerialNoDtos(List<LineCargoSerialNoDto> serialNoDtos) {
		this.serialNoDtos = serialNoDtos;
	}

}
