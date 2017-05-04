package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AcceptPointSalesDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AccessPointEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;

/**
 * 接驳点
 * @author 198771
 *
 */
public class AccessPointVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1115938823168676450L;

	private AccessPointEntity accessPointEntity;

	private List<AccessPointEntity> accessPointEntityList;
	
	private AccessPointEntity accessPointEntityCondition;
	
	private List<String> accessPointCodes;
	
	private AdministrativeRegionsEntity administrativeRegionsEntity;
	
	private List<AdministrativeRegionsEntity> regionList;
	
	private List<AdministrativeRegionsEntity> provinceList;
	
	private List<AdministrativeRegionsEntity> cityList;
	
	private List<AdministrativeRegionsEntity> countyList;
	
	private List<String> regionCodes;
	
	private List<AcceptPointSalesDeptEntity> acceptPointSalesDeptEntitys;

	public AccessPointEntity getAccessPointEntity() {
		return accessPointEntity;
	}

	public void setAccessPointEntity(AccessPointEntity accessPointEntity) {
		this.accessPointEntity = accessPointEntity;
	}

	public List<AccessPointEntity> getAccessPointEntityList() {
		return accessPointEntityList;
	}

	public void setAccessPointEntityList(
			List<AccessPointEntity> accessPointEntityList) {
		this.accessPointEntityList = accessPointEntityList;
	}

	public AccessPointEntity getAccessPointEntityCondition() {
		return accessPointEntityCondition;
	}

	public void setAccessPointEntityCondition(
			AccessPointEntity accessPointEntityCondition) {
		this.accessPointEntityCondition = accessPointEntityCondition;
	}

	public List<String> getAccessPointCodes() {
		return accessPointCodes;
	}

	public void setAccessPointCodes(List<String> accessPointCodes) {
		this.accessPointCodes = accessPointCodes;
	}

	public AdministrativeRegionsEntity getAdministrativeRegionsEntity() {
		return administrativeRegionsEntity;
	}

	public void setAdministrativeRegionsEntity(
			AdministrativeRegionsEntity administrativeRegionsEntity) {
		this.administrativeRegionsEntity = administrativeRegionsEntity;
	}

	
	public List<AdministrativeRegionsEntity> getRegionList() {
		return regionList;
	}

	public void setRegionList(List<AdministrativeRegionsEntity> regionList) {
		this.regionList = regionList;
	}

	public List<String> getRegionCodes() {
		return regionCodes;
	}

	public void setRegionCodes(List<String> regionCodes) {
		this.regionCodes = regionCodes;
	}

	public List<AdministrativeRegionsEntity> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<AdministrativeRegionsEntity> provinceList) {
		this.provinceList = provinceList;
	}

	public List<AdministrativeRegionsEntity> getCityList() {
		return cityList;
	}

	public void setCityList(List<AdministrativeRegionsEntity> cityList) {
		this.cityList = cityList;
	}

	public List<AdministrativeRegionsEntity> getCountyList() {
		return countyList;
	}

	public void setCountyList(List<AdministrativeRegionsEntity> countyList) {
		this.countyList = countyList;
	}

	public List<AcceptPointSalesDeptEntity> getAcceptPointSalesDeptEntitys() {
		return acceptPointSalesDeptEntitys;
	}

	public void setAcceptPointSalesDeptEntitys(
			List<AcceptPointSalesDeptEntity> acceptPointSalesDeptEntitys) {
		this.acceptPointSalesDeptEntitys = acceptPointSalesDeptEntitys;
	}
}
