package com.deppon.foss.module.transfer.unload.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.unload.api.shared.domain.ManualInputForkVoteEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.ManualInputGoodsQtyEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ManualInputQcDto;

public class ManualInputVo implements Serializable {

	private static final long serialVersionUID = -369611151211682118L;

	private ManualInputQcDto manualInputQcDto;

	private ManualInputGoodsQtyEntity manualInputGoodsQtyEntity;

	private List<ManualInputGoodsQtyEntity> manualInputGoodsQtyEntities;

	private ManualInputForkVoteEntity manualInputForkVoteEntity;

	private List<ManualInputForkVoteEntity> manualInputForkVoteEntities;
	
	private String parentTfrCtrCode;
	
	private String parentTfrCtrName;

	public ManualInputQcDto getManualInputQcDto() {
		return manualInputQcDto;
	}

	public void setManualInputQcDto(ManualInputQcDto manualInputQcDto) {
		this.manualInputQcDto = manualInputQcDto;
	}

	public ManualInputGoodsQtyEntity getManualInputGoodsQtyEntity() {
		return manualInputGoodsQtyEntity;
	}

	public void setManualInputGoodsQtyEntity(
			ManualInputGoodsQtyEntity manualInputGoodsQtyEntity) {
		this.manualInputGoodsQtyEntity = manualInputGoodsQtyEntity;
	}

	public List<ManualInputGoodsQtyEntity> getManualInputGoodsQtyEntities() {
		return manualInputGoodsQtyEntities;
	}

	public void setManualInputGoodsQtyEntities(
			List<ManualInputGoodsQtyEntity> manualInputGoodsQtyEntities) {
		this.manualInputGoodsQtyEntities = manualInputGoodsQtyEntities;
	}

	public ManualInputForkVoteEntity getManualInputForkVoteEntity() {
		return manualInputForkVoteEntity;
	}

	public void setManualInputForkVoteEntity(
			ManualInputForkVoteEntity manualInputForkVoteEntity) {
		this.manualInputForkVoteEntity = manualInputForkVoteEntity;
	}

	public List<ManualInputForkVoteEntity> getManualInputForkVoteEntities() {
		return manualInputForkVoteEntities;
	}

	public void setManualInputForkVoteEntities(
			List<ManualInputForkVoteEntity> manualInputForkVoteEntities) {
		this.manualInputForkVoteEntities = manualInputForkVoteEntities;
	}

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

}
