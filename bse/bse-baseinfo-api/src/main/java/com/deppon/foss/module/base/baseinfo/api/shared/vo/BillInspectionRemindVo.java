package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillInspectionRemindEntity;

public class BillInspectionRemindVo {

	
	private BillInspectionRemindEntity billInspectionRemindEntity;
	
	private List<BillInspectionRemindEntity> billInspectionRemindEntitys;
	
	private String regionType;
	
	private String regionTypeCode;
	
	private String regionLev;
	
	private String regionLevCode;
	
	private String regionName;
	
	private String[] ids;

	public String getRegionType() {
		return regionType;
	}

	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}

	public String getRegionLev() {
		return regionLev;
	}

	public void setRegionLev(String regionLev) {
		this.regionLev = regionLev;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getRegionTypeCode() {
		return regionTypeCode;
	}

	public void setRegionTypeCode(String regionTypeCode) {
		this.regionTypeCode = regionTypeCode;
	}

	public String getRegionLevCode() {
		return regionLevCode;
	}

	public void setRegionLevCode(String regionLevCode) {
		this.regionLevCode = regionLevCode;
	}

	public BillInspectionRemindEntity getBillInspectionRemindEntity() {
		return billInspectionRemindEntity;
	}

	public void setBillInspectionRemindEntity(
			BillInspectionRemindEntity billInspectionRemindEntity) {
		this.billInspectionRemindEntity = billInspectionRemindEntity;
	}

	public List<BillInspectionRemindEntity> getBillInspectionRemindEntitys() {
		return billInspectionRemindEntitys;
	}

	public void setBillInspectionRemindEntitys(
			List<BillInspectionRemindEntity> billInspectionRemindEntitys) {
		this.billInspectionRemindEntitys = billInspectionRemindEntitys;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}
	
	
}
