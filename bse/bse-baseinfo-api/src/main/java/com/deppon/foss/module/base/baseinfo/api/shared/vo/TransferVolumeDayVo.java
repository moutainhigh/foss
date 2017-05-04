package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.TransferVolumeDayEntity;

public class TransferVolumeDayVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -409495507001659040L;

	private List<TransferVolumeDayEntity> transferVolumeDayEntitys;
	
	private TransferVolumeDayEntity transferVolumeDayEntity;
	
	/**
	 * code集合
	 */
	private List<String> codes;

	
	

	public List<String> getCodes() {
		return codes;
	}

	public void setCodes(List<String> codes) {
		this.codes = codes;
	}

	public TransferVolumeDayEntity getTransferVolumeDayEntity() {
		return transferVolumeDayEntity;
	}

	public void setTransferVolumeDayEntity(
			TransferVolumeDayEntity transferVolumeDayEntity) {
		this.transferVolumeDayEntity = transferVolumeDayEntity;
	}

	public List<TransferVolumeDayEntity> getTransferVolumeDayEntitys() {
		return transferVolumeDayEntitys;
	}

	public void setTransferVolumeDayEntitys(
			List<TransferVolumeDayEntity> transferVolumeDayEntitys) {
		this.transferVolumeDayEntitys = transferVolumeDayEntitys;
	}
	

}
