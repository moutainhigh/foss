package com.deppon.foss.module.pickup.common.client.vo;

import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;

/**
 * 
 * 流水号VO
 * zxy 20131118 ISSUE-4391
 * @author 157229-zxy
 * @date 2013-10-28 
 */
public class LabeledGoodEntityVo {
	/**
	 * 流水号实体对象
	 */
	private LabeledGoodEntity entity;
	
	/**
	 * 
	 * 实例化流水号VO对象
	 */
	public LabeledGoodEntityVo(LabeledGoodEntity entity){
		this.entity = entity;
	}
	
	/**
	 * 获取流水号实体对象
	 */
	public LabeledGoodEntity getEntity() {
		return entity;
	}


	@Override
	public String toString() {
		if(entity != null)
			return entity.getSerialNo();
		return super.toString();
	}
}