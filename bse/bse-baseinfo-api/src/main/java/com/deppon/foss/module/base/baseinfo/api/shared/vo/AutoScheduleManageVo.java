package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AutoScheduleManageEntity;

public class AutoScheduleManageVo {
	/**
	 * 自动调度管理信息实体
	 **/
  private AutoScheduleManageEntity entity;
	/**
	 * 自动调度管理信息实体链表
	 **/
  private List<AutoScheduleManageEntity>  entityList;
	/**
	 * 需要删除的自动调度管理信息id数组
	 **/
  private String[] codeList;
	public AutoScheduleManageEntity getEntity() {
		return entity;
	}
	public void setEntity(AutoScheduleManageEntity entity) {
		this.entity = entity;
	}
	public List<AutoScheduleManageEntity> getEntityList() {
		return entityList;
	}
	public void setEntityList(List<AutoScheduleManageEntity> entityList) {
		this.entityList = entityList;
	}
	public String[] getCodeList() {
		return codeList;
	}
	public void setCodeList(String[] codeList) {
		this.codeList = codeList;
	}


 
  
}
