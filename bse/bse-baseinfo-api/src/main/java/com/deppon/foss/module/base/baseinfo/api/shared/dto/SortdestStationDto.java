package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import com.deppon.foss.framework.entity.BaseEntity;

public class SortdestStationDto extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String active;

	private String parentVirtualCode;

	private String boxNo;

	private String cellNo;

	private String destOrgCode;

	private String destOrgName;

	private String packAgeType;

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getParentVirtualCode() {
		return parentVirtualCode;
	}

	public void setParentVirtualCode(String parentVirtualCode) {
		this.parentVirtualCode = parentVirtualCode;
	}

	public String getBoxNo() {
		return boxNo;
	}

	public void setBoxNo(String boxNo) {
		this.boxNo = boxNo;
	}

	public String getCellNo() {
		return cellNo;
	}

	public void setCellNo(String cellNo) {
		this.cellNo = cellNo;
	}

	public String getDestOrgCode() {
		return destOrgCode;
	}

	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	public String getDestOrgName() {
		return destOrgName;
	}

	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	public String getPackAgeType() {
		return packAgeType;
	}

	public void setPackAgeType(String packAgeType) {
		this.packAgeType = packAgeType;
	}

}
