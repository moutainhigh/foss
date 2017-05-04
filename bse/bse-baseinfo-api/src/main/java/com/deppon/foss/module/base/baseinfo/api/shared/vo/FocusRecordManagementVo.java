package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.FocusRecordManagementEntity;

public class FocusRecordManagementVo implements Serializable{

	private static final long serialVersionUID = 7946775205146654321L;
	
	private FocusRecordManagementEntity entity;
	
	private List<FocusRecordManagementEntity> entityList;
	
	private String ids;

	public FocusRecordManagementEntity getEntity() {
		return entity;
	}

	public void setEntity(FocusRecordManagementEntity entity) {
		this.entity = entity;
	}

	public List<FocusRecordManagementEntity> getEntityList() {
		return entityList;
	}

	public void setEntityList(List<FocusRecordManagementEntity> entityList) {
		this.entityList = entityList;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	
	
	
}
