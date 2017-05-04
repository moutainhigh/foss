package com.deppon.foss.module.pickup.pricing.api.shared.vo;

import java.io.Serializable;

import java.util.List;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.InempDiscountPlanEntity;
/**
 * 内部员工折扣方案vo 用于数据交互
 * dp-foss-dongjialing
 * @author 225131
 *
 */
public class InempDiscountPlanVo implements Serializable {

	/**
	 *序列化ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 内部员工折扣方案实体集合
	 */
	private List<InempDiscountPlanEntity> inempDiscountPlanEntityList;
	
	
	/**
	 * 内部员工折扣方案实体
	 */
	private InempDiscountPlanEntity  inempDiscountPlanEntity;
	/**
	 * 内部员工折扣方案id集合
	 */
	private List<String> inempDiscountPlanIds;
	public List<InempDiscountPlanEntity> getInempDiscountPlanEntityList() {
		return inempDiscountPlanEntityList;
	}
	public void setInempDiscountPlanEntityList(
			List<InempDiscountPlanEntity> inempDiscountPlanEntityList) {
		this.inempDiscountPlanEntityList = inempDiscountPlanEntityList;
	}
	public InempDiscountPlanEntity getInempDiscountPlanEntity() {
		return inempDiscountPlanEntity;
	}
	public void setInempDiscountPlanEntity(
			InempDiscountPlanEntity inempDiscountPlanEntity) {
		this.inempDiscountPlanEntity = inempDiscountPlanEntity;
	}
	public List<String> getInempDiscountPlanIds() {
		return inempDiscountPlanIds;
	}
	public void setInempDiscountPlanIds(List<String> inempDiscountPlanIds) {
		this.inempDiscountPlanIds = inempDiscountPlanIds;
	}
}
