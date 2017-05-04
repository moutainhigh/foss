package com.deppon.foss.module.pickup.pricing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.CarloadLinePricePlanDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CarloadLinePricePlanEntity;

public class CarloadLinePricePlanVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 整车线路价格方案
	 */
	private CarloadLinePricePlanEntity carloadLinePricePlanEntity;

	/**
	 * 价格方案返回结果集.
	 */
	private List<CarloadLinePricePlanEntity> carloadLinePricePlanEntityList;
	
	/**
	 * 整车线路明细价格方案
	 */
	private CarloadLinePricePlanDetailEntity carloadLinePricePlanDetailEntity;

	/**
	 * 价格方案明细返回结果集.
	 */
	private List<CarloadLinePricePlanDetailEntity> carloadLinePricePlanDetailEntityList;

	public CarloadLinePricePlanEntity getCarloadLinePricePlanEntity() {
		return carloadLinePricePlanEntity;
	}

	public void setCarloadLinePricePlanEntity(
			CarloadLinePricePlanEntity carloadLinePricePlanEntity) {
		this.carloadLinePricePlanEntity = carloadLinePricePlanEntity;
	}

	public List<CarloadLinePricePlanEntity> getCarloadLinePricePlanEntityList() {
		return carloadLinePricePlanEntityList;
	}

	public void setCarloadLinePricePlanEntityList(
			List<CarloadLinePricePlanEntity> carloadLinePricePlanEntityList) {
		this.carloadLinePricePlanEntityList = carloadLinePricePlanEntityList;
	}
	
	public CarloadLinePricePlanDetailEntity getCarloadLinePricePlanDetailEntity() {
		return carloadLinePricePlanDetailEntity;
	}

	public void setCarloadLinePricePlanDetailEntity(
			CarloadLinePricePlanDetailEntity carloadLinePricePlanDetailEntity) {
		this.carloadLinePricePlanDetailEntity = carloadLinePricePlanDetailEntity;
	}

	public List<CarloadLinePricePlanDetailEntity> getCarloadLinePricePlanDetailEntityList() {
		return carloadLinePricePlanDetailEntityList;
	}

	public void setCarloadLinePricePlanDetailEntityList(
			List<CarloadLinePricePlanDetailEntity> carloadLinePricePlanDetailEntityList) {
		this.carloadLinePricePlanDetailEntityList = carloadLinePricePlanDetailEntityList;
	}

}
