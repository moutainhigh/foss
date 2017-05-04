package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.NewbirdinfoEntity;

public class NewbirdinfoVO  implements Serializable{

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = -8372339694836134644L;
	/**
	 * 交接单管理 基础信息实体
	 */
	private NewbirdinfoEntity newbirdinfoEntity;
	/**
	 * 交接单管理 基础信息list
	 */
	private List<NewbirdinfoEntity> newbirdinfoEntitys;
	/**
	 * 作废 交单管理 ID集合
	 */
	private List<String> ids;
	public NewbirdinfoEntity getNewbirdinfoEntity() {
		return newbirdinfoEntity;
	}
	public void setNewbirdinfoEntity(NewbirdinfoEntity newbirdinfoEntity) {
		this.newbirdinfoEntity = newbirdinfoEntity;
	}
	public List<NewbirdinfoEntity> getNewbirdinfoEntitys() {
		return newbirdinfoEntitys;
	}
	public void setNewbirdinfoEntitys(List<NewbirdinfoEntity> newbirdinfoEntitys) {
		this.newbirdinfoEntitys = newbirdinfoEntitys;
	}
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}	
	
	
	
	
}