package com.deppon.foss.module.pickup.pricing.api.shared.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.CarloadPriceDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CarloadPriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CarloadPriceOrgEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.CarloadPricePlanDto;

public class CarloadPriceManageMeantVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 整车价格参数方案
	 */
	private CarloadPriceEntity carloadPriceEntity;
	
	/**
	 * 整车价格参数方案明细
	 */
	private CarloadPriceDetailEntity carloadPriceDetailEntity;
	
	/**
	 * 整车价格参数方案与组织对应关系
	 */
	private CarloadPriceOrgEntity	carloadPriceOrgEntity;
	/**
	 * 整车价格查询条件
	 */
	private CarloadPricePlanDto	carloadPricePlanDto;
	
	/**
	 * 整车参数方案查询结果集
	 */
	private List<CarloadPricePlanDto>	carloadPricePlanDtos;
	
	/**
	 * 整车参数方案明细id列表
	 */
	private List<String> carPlanDetailIds;
	
	/**
	 * 整车参数方案id列表
	 */
	private List<String> carPlanIds;
	
	public List<String> getCarPlanIds() {
		return carPlanIds;
	}

	public void setCarPlanIds(List<String> carPlanIds) {
		this.carPlanIds = carPlanIds;
	}

	public List<String> getCarPlanDetailIds() {
		return carPlanDetailIds;
	}

	public void setCarPlanDetailIds(List<String> carPlanDetailIds) {
		this.carPlanDetailIds = carPlanDetailIds;
	}

	/**
	 * 价格方案返回结果集.
	 */
	private List<CarloadPriceEntity> carloadPriceEntityList=new ArrayList<CarloadPriceEntity>();
	
	/**
	 * 整车价格参数方案与组织对应关系结果集
	 */
	private List<CarloadPriceOrgEntity> carloadPriceOrgEntityList=new ArrayList<CarloadPriceOrgEntity>();
	
	/**
	 * 整车价格参数方案明细结果集
	 */
	private List<CarloadPriceDetailEntity> carloadPriceDetailEntityList=new ArrayList<CarloadPriceDetailEntity>();
	
	public CarloadPricePlanDto getCarloadPricePlanDto() {
		return carloadPricePlanDto;
	}

	public void setCarloadPricePlanDto(CarloadPricePlanDto carloadPricePlanDto) {
		this.carloadPricePlanDto = carloadPricePlanDto;
	}

	public List<CarloadPricePlanDto> getCarloadPricePlanDtos() {
		return carloadPricePlanDtos;
	}

	public void setCarloadPricePlanDtos(
			List<CarloadPricePlanDto> carloadPricePlanDtos) {
		this.carloadPricePlanDtos = carloadPricePlanDtos;
	}
	public List<CarloadPriceDetailEntity> getCarloadPriceDetailEntityList() {
		return carloadPriceDetailEntityList;
	}

	public void setCarloadPriceDetailEntityList(
			List<CarloadPriceDetailEntity> carloadPriceDetailEntityList) {
		this.carloadPriceDetailEntityList = carloadPriceDetailEntityList;
	}

	public CarloadPriceDetailEntity getCarloadPriceDetailEntity() {
		return carloadPriceDetailEntity;
	}

	public void setCarloadPriceDetailEntity(
			CarloadPriceDetailEntity carloadPriceDetailEntity) {
		this.carloadPriceDetailEntity = carloadPriceDetailEntity;
	}

	public CarloadPriceOrgEntity getCarloadPriceOrgEntity() {
		return carloadPriceOrgEntity;
	}

	public void setCarloadPriceOrgEntity(CarloadPriceOrgEntity carloadPriceOrgEntity) {
		this.carloadPriceOrgEntity = carloadPriceOrgEntity;
	}

	public CarloadPriceEntity getCarloadPriceEntity() {
		return carloadPriceEntity;
	}

	public void setCarloadPriceEntity(CarloadPriceEntity carloadPriceEntity) {
		this.carloadPriceEntity = carloadPriceEntity;
	}

	public List<CarloadPriceEntity> getCarloadPriceEntityList() {
		return carloadPriceEntityList;
	}

	public void setCarloadPriceEntityList(
			List<CarloadPriceEntity> carloadPriceEntityList) {
		this.carloadPriceEntityList = carloadPriceEntityList;
	}

	public List<CarloadPriceOrgEntity> getCarloadPriceOrgEntityList() {
		return carloadPriceOrgEntityList;
	}

	public void setCarloadPriceOrgEntityList(
			List<CarloadPriceOrgEntity> carloadPriceOrgEntityList) {
		this.carloadPriceOrgEntityList = carloadPriceOrgEntityList;
	}
	

}
