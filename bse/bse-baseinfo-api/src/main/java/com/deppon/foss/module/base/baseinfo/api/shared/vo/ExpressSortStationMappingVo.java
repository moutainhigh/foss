package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressSortStationMappingEntity;

public class ExpressSortStationMappingVo {
	//映射信息
	public ExpressSortStationMappingEntity entity;
	//映射信息集合
	public List<ExpressSortStationMappingEntity> mappingEntityList;
	//虚拟编码集合
	public List<String> virtualCodeList;

	public ExpressSortStationMappingEntity getEntity() {
		return entity;
	}

	public void setEntity(ExpressSortStationMappingEntity entity) {
		this.entity = entity;
	}

	public List<ExpressSortStationMappingEntity> getMappingEntityList() {
		return mappingEntityList;
	}

	public void setMappingEntityList(
			List<ExpressSortStationMappingEntity> mappingEntityList) {
		this.mappingEntityList = mappingEntityList;
	}

	public List<String> getVirtualCodeList() {
		return virtualCodeList;
	}
	public void setVirtualCodeList(List<String> virtualCodeList) {
		this.virtualCodeList = virtualCodeList;
	}
	
}
