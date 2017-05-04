package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionArriveEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionOrgArriveEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;

/**
 * 
 * FOSS同步到PTP信息的DTO
 * 
 * @author 265475	 DP-Foss-zoushengli
 * 
 * @date 2016-1-18 下午11:35:08
 * 
 */
public class WaybillRegionInfoDto implements Serializable {

	
	private List<ProductEntity>  productEntityList;
	/**
	 * 
	 */
	private static final long serialVersionUID = 5975303397283196505L;

	/** 
	 * 查询条件
	 */
	private ProductEntity productEntity;
	
	/** 
	 * 区域性质
	 */
	private String regionNature;
	
	/**
	 * 结束时间
	 */
	private Date endTime;
	
	/**
	 * 起始时间
	 */
	private Date beginTime;
	
	/**
	 * 判断操作
	 */
	private String judgmentOperation;
	
	/** 
	 * 激活区域
	 */
	private List<String> regionIds;
	
	/** 
	 * 区域关联部门信息(新增)
	 */
	private List<PriceRegioOrgnEntity> addPriceRegioOrgnEntityList;
	
	/** 
	 * 区域关联部门信息(修改)
	 */
	private List<PriceRegioOrgnEntity> updatePriceRegioOrgnEntityList;
	
	/**
	 * 获取 区域关联部门信息(查询).
	 *
	 * @return the 区域关联部门信息(查询)
	 */
	List<PriceRegioOrgnEntity> priceRegioOrgnEntityList;
	
	/** 
	 * 区域关联部门信息(查询)
	 */
	List<PriceRegionOrgArriveEntity> priceRegionOrgArriveEntityList;
	
	/** 
	 * 循环获取部门信息
	 */
	List<PriceRegionOrgArriveEntity> priceRegionOrgArriveEntityLists;
	
	/** 
	 * 查询条件
	 */
	private PriceRegionArriveEntity regionArriveEntity;
	
	/** 
	 * 区域关联部门信息(新增)
	 */
	private List<PriceRegionOrgArriveEntity> addPriceRegionOrgAirEntityList;
	
	/** 
	 * 区域关联部门信息(修改)
	 */
	private List<PriceRegionOrgArriveEntity> updatePriceRegionOrgAirEntityList;
	
	List<String> productIds;
	
	private String regionId;
	
	
    private PriceRegionEntity regionEntity;
	
    /** 
	 * 查询区域关联部门条件
	 */
	private PriceRegioOrgnEntity priceRegioOrgnEntity;
	
	/** 
	 * 查询区域关联部门条件
	 */
	private PriceRegionOrgArriveEntity priceRegionOrgArriveEntity;
	
	/** 
	 * 循环获取关联部门信息
	 */
	List<PriceRegioOrgnEntity> priceRegioOrgnEntityLists;
	
	public ProductEntity getProductEntity() {
		return productEntity;
	}

	public void setProductEntity(ProductEntity productEntity) {
		this.productEntity = productEntity;
	}
	public List<String> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<String> productIds) {
		this.productIds = productIds;
	}
	
	public PriceRegionOrgArriveEntity getPriceRegionOrgArriveEntity() {
		return priceRegionOrgArriveEntity;
	}

	public void setPriceRegionOrgArriveEntity(
			PriceRegionOrgArriveEntity priceRegionOrgArriveEntity) {
		this.priceRegionOrgArriveEntity = priceRegionOrgArriveEntity;
	}

	public PriceRegioOrgnEntity getPriceRegioOrgnEntity() {
		return priceRegioOrgnEntity;
	}

	public void setPriceRegioOrgnEntity(PriceRegioOrgnEntity priceRegioOrgnEntity) {
		this.priceRegioOrgnEntity = priceRegioOrgnEntity;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<PriceRegionOrgArriveEntity> getAddPriceRegionOrgAirEntityList() {
		return addPriceRegionOrgAirEntityList;
	}

	public void setAddPriceRegionOrgAirEntityList(
			List<PriceRegionOrgArriveEntity> addPriceRegionOrgAirEntityList) {
		this.addPriceRegionOrgAirEntityList = addPriceRegionOrgAirEntityList;
	}

	public List<PriceRegionOrgArriveEntity> getUpdatePriceRegionOrgAirEntityList() {
		return updatePriceRegionOrgAirEntityList;
	}

	public void setUpdatePriceRegionOrgAirEntityList(
			List<PriceRegionOrgArriveEntity> updatePriceRegionOrgAirEntityList) {
		this.updatePriceRegionOrgAirEntityList = updatePriceRegionOrgAirEntityList;
	}


	public PriceRegionArriveEntity getRegionArriveEntity() {
		return regionArriveEntity;
	}

	public void setRegionArriveEntity(PriceRegionArriveEntity regionArriveEntity) {
		this.regionArriveEntity = regionArriveEntity;
	}

	public List<PriceRegionOrgArriveEntity> getPriceRegionOrgArriveEntityLists() {
		return priceRegionOrgArriveEntityLists;
	}

	public void setPriceRegionOrgArriveEntityLists(
			List<PriceRegionOrgArriveEntity> priceRegionOrgArriveEntityLists) {
		this.priceRegionOrgArriveEntityLists = priceRegionOrgArriveEntityLists;
	}

	public List<PriceRegioOrgnEntity> getPriceRegioOrgnEntityLists() {
		return priceRegioOrgnEntityLists;
	}

	public void setPriceRegioOrgnEntityLists(
			List<PriceRegioOrgnEntity> priceRegioOrgnEntityLists) {
		this.priceRegioOrgnEntityLists = priceRegioOrgnEntityLists;
	}

	public List<PriceRegionOrgArriveEntity> getPriceRegionOrgArriveEntityList() {
		return priceRegionOrgArriveEntityList;
	}

	public void setPriceRegionOrgArriveEntityList(
			List<PriceRegionOrgArriveEntity> priceRegionOrgArriveEntityList) {
		this.priceRegionOrgArriveEntityList = priceRegionOrgArriveEntityList;
	}

	public List<PriceRegioOrgnEntity> getPriceRegioOrgnEntityList() {
		return priceRegioOrgnEntityList;
	}

	public void setPriceRegioOrgnEntityList(
			List<PriceRegioOrgnEntity> priceRegioOrgnEntityList) {
		this.priceRegioOrgnEntityList = priceRegioOrgnEntityList;
	}
	
	
	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}


	public PriceRegionEntity getRegionEntity() {
		return regionEntity;
	}

	public void setRegionEntity(PriceRegionEntity regionEntity) {
		this.regionEntity = regionEntity;
	}



	public List<PriceRegioOrgnEntity> getAddPriceRegioOrgnEntityList() {
		return addPriceRegioOrgnEntityList;
	}

	public void setAddPriceRegioOrgnEntityList(
			List<PriceRegioOrgnEntity> addPriceRegioOrgnEntityList) {
		this.addPriceRegioOrgnEntityList = addPriceRegioOrgnEntityList;
	}

	public List<PriceRegioOrgnEntity> getUpdatePriceRegioOrgnEntityList() {
		return updatePriceRegioOrgnEntityList;
	}

	public void setUpdatePriceRegioOrgnEntityList(
			List<PriceRegioOrgnEntity> updatePriceRegioOrgnEntityList) {
		this.updatePriceRegioOrgnEntityList = updatePriceRegioOrgnEntityList;
	}

	public List<String> getRegionIds() {
		return regionIds;
	}

	public void setRegionIds(List<String> regionIds) {
		this.regionIds = regionIds;
	}

	public String getRegionNature() {
		return regionNature;
	}

	public void setRegionNature(String regionNature) {
		this.regionNature = regionNature;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public String getJudgmentOperation() {
		return judgmentOperation;
	}

	public void setJudgmentOperation(String judgmentOperation) {
		this.judgmentOperation = judgmentOperation;
	}

	public List<ProductEntity> getProductEntityList() {
		return productEntityList;
	}

	public void setProductEntityList(List<ProductEntity> productEntityList) {
		this.productEntityList = productEntityList;
	}



}
