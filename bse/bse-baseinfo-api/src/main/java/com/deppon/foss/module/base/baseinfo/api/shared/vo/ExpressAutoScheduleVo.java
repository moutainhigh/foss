package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressAutoScheduleEntity;

public class ExpressAutoScheduleVo {
	/**
	 * 快递自动调度管理信息实体
	 **/
  private ExpressAutoScheduleEntity entity;
	/**
	 * 快递自动调度管理信息实体链表
	 **/
  private List<ExpressAutoScheduleEntity>  entityList;
	/**
	 * 需要删除的自动调度管理信息id数组
	 **/
  private String[] codeList;
	public ExpressAutoScheduleEntity getEntity() {
		return entity;
	}
	public void setEntity(ExpressAutoScheduleEntity entity) {
		this.entity = entity;
	}
	public List<ExpressAutoScheduleEntity> getEntityList() {
		return entityList;
	}
	public void setEntityList(List<ExpressAutoScheduleEntity> entityList) {
		this.entityList = entityList;
	}
	public String[] getCodeList() {
		return codeList;
	}
	public void setCodeList(String[] codeList) {
		this.codeList = codeList;
	}

}
