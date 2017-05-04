package com.deppon.foss.module.transfer.platform.api.shared.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrStaffNoDutyEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrStaffDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrStaffQcDto;

public class TfrCtrStaffVo implements Serializable {

	private static final long serialVersionUID = -8811155997084882756L;

	private String parentTfrCtrCode;

	private String parentTfrCtrName;

	private TfrCtrStaffQcDto tfrCtrStaffQcDto;

	private List<TfrCtrStaffDto> tfrCtrStaffDtos;

	private String transferCenterCode;

	private Date queryDate;

	private List<TfrCtrStaffNoDutyEntity> tfrCtrStaffNoDutyEntities;

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

	public TfrCtrStaffQcDto getTfrCtrStaffQcDto() {
		return tfrCtrStaffQcDto;
	}

	public void setTfrCtrStaffQcDto(TfrCtrStaffQcDto tfrCtrStaffQcDto) {
		this.tfrCtrStaffQcDto = tfrCtrStaffQcDto;
	}

	public List<TfrCtrStaffDto> getTfrCtrStaffDtos() {
		return tfrCtrStaffDtos;
	}

	public void setTfrCtrStaffDtos(List<TfrCtrStaffDto> tfrCtrStaffDtos) {
		this.tfrCtrStaffDtos = tfrCtrStaffDtos;
	}

	public String getTransferCenterCode() {
		return transferCenterCode;
	}

	public void setTransferCenterCode(String transferCenterCode) {
		this.transferCenterCode = transferCenterCode;
	}

	public Date getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}

	public List<TfrCtrStaffNoDutyEntity> getTfrCtrStaffNoDutyEntities() {
		return tfrCtrStaffNoDutyEntities;
	}

	public void setTfrCtrStaffNoDutyEntities(
			List<TfrCtrStaffNoDutyEntity> tfrCtrStaffNoDutyEntities) {
		this.tfrCtrStaffNoDutyEntities = tfrCtrStaffNoDutyEntities;
	}

}
