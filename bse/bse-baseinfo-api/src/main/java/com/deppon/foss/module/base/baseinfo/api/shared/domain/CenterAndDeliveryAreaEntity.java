package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CenterAndDeliveryBigRegionAreaDto;

public class CenterAndDeliveryAreaEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6324556036119898801L;
	
//	车队名称
	private String manageMentName;
//	车队编码
	private String manageMentCode;
//	画图类型	
	private String type;
//	gid关联的ID
	private String gisId;
	
	private String gisArea;
	
	private String active;
	
	private String rolePermissions;
	
	private String drawMap;
	
	private List<CenterAndDeliveryBigRegionAreaDto> BigRegions;

	public String getManageMentName() {
		return manageMentName;
	}

	public void setManageMentName(String manageMentName) {
		this.manageMentName = manageMentName;
	}

	public String getManageMentCode() {
		return manageMentCode;
	}

	public void setManageMentCode(String manageMentCode) {
		this.manageMentCode = manageMentCode;
	}

	public List<CenterAndDeliveryBigRegionAreaDto> getBigRegions() {
		return BigRegions;
	}

	public void setBigRegions(List<CenterAndDeliveryBigRegionAreaDto> bigRegions) {
		BigRegions = bigRegions;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGisId() {
		return gisId;
	}

	public void setGisId(String gisId) {
		this.gisId = gisId;
	}

	public String getGisArea() {
		return gisArea;
	}

	public void setGisArea(String gisArea) {
		this.gisArea = gisArea;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getRolePermissions() {
		return rolePermissions;
	}

	public void setRolePermissions(String rolePermissions) {
		this.rolePermissions = rolePermissions;
	}

	public String getDrawMap() {
		return drawMap;
	}

	public void setDrawMap(String drawMap) {
		this.drawMap = drawMap;
	}
	
	
	

}
