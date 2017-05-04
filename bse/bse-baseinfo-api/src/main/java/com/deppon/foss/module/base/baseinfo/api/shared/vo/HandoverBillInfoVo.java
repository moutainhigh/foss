package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.HandoverBillInfoEntity;

public class HandoverBillInfoVo implements Serializable{

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 交接单管理 基础信息实体
	 */
	private HandoverBillInfoEntity handoverBillInfoEntity;
	/**
	 * 交接单管理 基础信息list
	 */
	private List<HandoverBillInfoEntity> handoverBillInfoEntitys;
	/**
	 * 作废 交单管理 ID集合
	 */
	private List<String> ids;	
	
	
	/**
	 * 获取 作废 交单管理 ID集合
	 * @return  the ids
	 */
	public List<String> getIds() {
		return ids;
	}
	/**
	 * 设置 作废 交单管理 ID集合
	 * @param ids the ids to set
	 */
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	/**
	 * 获取 交接单管理 基础信息实体
	 * @return  the handoverBillInfoEntiy
	 */
	
	public HandoverBillInfoEntity getHandoverBillInfoEntity() {
		return handoverBillInfoEntity;
	}
	/**
	 * 设置 交接单管理 基础信息实体
	 * @param handoverBillInfoEntity the handoverBillInfoEntity to set
	 */
	public void setHandoverBillInfoEntity(
			HandoverBillInfoEntity handoverBillInfoEntity) {
		this.handoverBillInfoEntity = handoverBillInfoEntity;
	}
	/**
	 * 获取 交接单管理 基础信息list
	 * @return  the handoverBillInfoEntitys
	 */
	public List<HandoverBillInfoEntity> getHandoverBillInfoEntitys() {
		return handoverBillInfoEntitys;
	}
	/**
	 * 设置 交接单管理 基础信息list
	 * @param handoverBillInfoEntitys the handoverBillInfoEntitys to set
	 */
	public void setHandoverBillInfoEntitys(
			List<HandoverBillInfoEntity> handoverBillInfoEntitys) {
		this.handoverBillInfoEntitys = handoverBillInfoEntitys;
	}
	
}
