package com.deppon.foss.module.settlement.closing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.define.VDCombo;
import com.deppon.foss.module.settlement.closing.api.shared.domain.BigSubTypeEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.VoucherDetailResultDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.VoucherDetailsDto;

/**
 * 
 * 凭证报表明细前台显示DTO
 * 
 * @author 046644-foss-zengbinwen
 * @date 2013-4-1 下午3:15:06
 */
public class VoucherDetailVo implements Serializable {
	
	/**
	 * dto
	 */
	private VoucherDetailsDto dto;

	private List<VDCombo> comboList;

	private List<VoucherDetailResultDto> resultList;

	private List<BigSubTypeEntity>  bigSubTypeList;
	
	private BigSubTypeEntity  bigSubTypeEntity;
	

	public BigSubTypeEntity getBigSubTypeEntity() {
		return bigSubTypeEntity;
	}

	public void setBigSubTypeEntity(BigSubTypeEntity bigSubTypeEntity) {
		this.bigSubTypeEntity = bigSubTypeEntity;
	}

	public VoucherDetailsDto getDto() {
		return dto;
	}

	public void setDto(VoucherDetailsDto dto) {
		this.dto = dto;
	}

	public List<VDCombo> getComboList() {
		return comboList;
	}

	public void setComboList(List<VDCombo> comboList) {
		this.comboList = comboList;
	}

	public List<VoucherDetailResultDto> getResultList() {
		return resultList;
	}

	public void setResultList(List<VoucherDetailResultDto> resultList) {
		this.resultList = resultList;
	}
	
	public List<BigSubTypeEntity> getBigSubTypeList() {
		return bigSubTypeList;
	}

	public void setBigSubTypeList(List<BigSubTypeEntity> bigSubTypeList) {
		this.bigSubTypeList = bigSubTypeList;
	}

}
