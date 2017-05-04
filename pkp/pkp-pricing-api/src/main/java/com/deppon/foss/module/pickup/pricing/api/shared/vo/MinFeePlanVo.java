package com.deppon.foss.module.pickup.pricing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.MinFeePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;

/**
 * 最低一票功能模块VO类
 * 
 * @author 026123-foss-lufeifei
 * @date 2013-8-13 上午8:34:48
 */
public class MinFeePlanVo implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -9052554515168586411L;

	/**
	 * 单个最低一票
	 */
	private MinFeePlanEntity minFeePlanEntity;

	/**
	 * 查询最低一票集合
	 */
	private List<MinFeePlanEntity> minFeePlanEntityList;

	/**
	 * 删除和激活用
	 */
	private List<String> minFeePlanIds;
	
	/**
	 * 3级产品信息集合
	 */
	private List<ProductEntity> productEntityList;

	public MinFeePlanEntity getMinFeePlanEntity() {
		return minFeePlanEntity;
	}

	public void setMinFeePlanEntity(MinFeePlanEntity minFeePlanEntity) {
		this.minFeePlanEntity = minFeePlanEntity;
	}

	public List<MinFeePlanEntity> getMinFeePlanEntityList() {
		return minFeePlanEntityList;
	}

	public void setMinFeePlanEntityList(List<MinFeePlanEntity> minFeePlanEntityList) {
		this.minFeePlanEntityList = minFeePlanEntityList;
	}

	public List<String> getMinFeePlanIds() {
		return minFeePlanIds;
	}

	public void setMinFeePlanIds(List<String> minFeePlanIds) {
		this.minFeePlanIds = minFeePlanIds;
	}

	public List<ProductEntity> getProductEntityList() {
		return productEntityList;
	}

	public void setProductEntityList(List<ProductEntity> productEntityList) {
		this.productEntityList = productEntityList;
	}

}
