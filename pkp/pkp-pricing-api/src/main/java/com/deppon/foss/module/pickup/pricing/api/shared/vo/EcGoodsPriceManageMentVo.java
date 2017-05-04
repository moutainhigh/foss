package com.deppon.foss.module.pickup.pricing.api.shared.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceCriteriaDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PricePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceValuationEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ExpressPricePlanDetailDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PricePlanDetailDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryPricePlanDetailBean;

/**
 * 首续重价格方案VO
 * @author 348757-cc
 * @date 2016-07-04
 * @version 1.0
 */
public class EcGoodsPriceManageMentVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 价格方案查询条件.
	 */
	private PricePlanEntity pricePlanEntity;

	/**
	 * 价格比对方案查询条件.
	 */
	private PricePlanEntity pricePlanEntityOld;

	/**
	 * 价格方案返回结果集.
	 */
	private List<PricePlanEntity> pricePlanEntityList;

	/**
	 * 价格计费规则(计费规则,一个价格方案至少对应1条计费规则或者N条计费规则).
	 */
	private PriceValuationEntity priceValuationEntity;

	/**
	 * 计价方式明细(计价明细,一个价格方案的计费规则分别对应重货，轻货处理计价方式明细).
	 */
	private PriceCriteriaDetailEntity priceCriteriaDetailEntity;

	/**
	 * 价格明细返回结果集.
	 */
	private List<PriceCriteriaDetailEntity> priceCriteriaDetailEntityList;

	/**
	 * 价格管理dto list.
	 */
	private List<PricePlanDetailDto> pricePlanDetailDtoList;

	/**
	 * 价格管理dto.
	 */
	private PricePlanDetailDto pricePlanDetailDto;

	/**
	 * 快递价格方案计价明细DTO List.
	 */
	private List<ExpressPricePlanDetailDto> detailDtoList;

	/**
	 * 快递价格方案计价明细DTO.
	 */
	private ExpressPricePlanDetailDto expressPricePlanDetailDto;

	/**
	 * 查询价格方案明细bean.
	 */
	private QueryPricePlanDetailBean queryPricePlanDetailBean;

	/**
	 * 价格主方案ID集合.
	 */
	private List<String> pricePlanIds;

	/**
	 * 单个价格方案id.
	 */
	private String pricePlanId;

	/**
	 * 中止时间.
	 */
	private Date stopEndTime;

	/**
	 * 价格计费规则ID集合.
	 */
	private List<String> pricePlanDetailIds;

	/**
	 * 获取 快递价格方案计价明细DTO List.
	 */
	public List<ExpressPricePlanDetailDto> getDetailDtoList() {
		return detailDtoList;
	}

	/**
	 * 设置 快递价格方案计价明细DTO List.
	 */
	public void setDetailDtoList(List<ExpressPricePlanDetailDto> detailDtoList) {
		this.detailDtoList = detailDtoList;
	}

	/**
	 * 获取 快递价格方案计价明细DTO.
	 */
	public ExpressPricePlanDetailDto getExpressPricePlanDetailDto() {
		return expressPricePlanDetailDto;
	}

	/**
	 * 设置 快递价格方案计价明细DTO.
	 */
	public void setExpressPricePlanDetailDto(ExpressPricePlanDetailDto expressPricePlanDetailDto) {
		this.expressPricePlanDetailDto = expressPricePlanDetailDto;
	}

	/**
	 * 获取 中止时间.
	 */
	public Date getStopEndTime() {
		return stopEndTime;
	}

	/**
	 * 设置 中止时间.
	 */
	public void setStopEndTime(Date stopEndTime) {
		this.stopEndTime = stopEndTime;
	}

	/**
	 * 获取 单个价格方案id.
	 */
	public String getPricePlanId() {
		return pricePlanId;
	}

	/**
	 * 设置 单个价格方案id.
	 */
	public void setPricePlanId(String pricePlanId) {
		this.pricePlanId = pricePlanId;
	}

	/**
	 * 获取 价格主方案ID集合.
	 */
	public List<String> getPricePlanIds() {
		return pricePlanIds;
	}

	/**
	 * 设置 价格主方案ID集合.
	 */
	public void setPricePlanIds(List<String> pricePlanIds) {
		this.pricePlanIds = pricePlanIds;
	}

	/**
	 * 获取 价格计费规则ID集合.
	 */
	public List<String> getPricePlanDetailIds() {
		return pricePlanDetailIds;
	}

	/**
	 * 设置 价格计费规则ID集合.
	 */
	public void setPricePlanDetailIds(List<String> pricePlanDetailIds) {
		this.pricePlanDetailIds = pricePlanDetailIds;
	}

	/**
	 * 获取 查询价格方案明细bean.
	 */
	public QueryPricePlanDetailBean getQueryPricePlanDetailBean() {
		return queryPricePlanDetailBean;
	}

	/**
	 * 获取 价格管理dto.
	 */
	public PricePlanDetailDto getPricePlanDetailDto() {
		return pricePlanDetailDto;
	}

	/**
	 * 设置 价格管理dto.
	 */
	public void setPricePlanDetailDto(PricePlanDetailDto pricePlanDetailDto) {
		this.pricePlanDetailDto = pricePlanDetailDto;
	}

	/**
	 * 设置 查询价格方案明细bean.
	 */
	public void setQueryPricePlanDetailBean(QueryPricePlanDetailBean queryPricePlanDetailBean) {
		this.queryPricePlanDetailBean = queryPricePlanDetailBean;
	}

	/**
	 * 获取 价格管理dto list.
	 */
	public List<PricePlanDetailDto> getPricePlanDetailDtoList() {
		return pricePlanDetailDtoList;
	}

	/**
	 * 设置 价格管理dto list.
	 */
	public void setPricePlanDetailDtoList(List<PricePlanDetailDto> pricePlanDetailDtoList) {
		this.pricePlanDetailDtoList = pricePlanDetailDtoList;
	}

	/**
	 * 获取 价格方案查询条件.
	 */
	public PricePlanEntity getPricePlanEntity() {
		return pricePlanEntity;
	}

	/**
	 * 获取 价格计费规则(计费规则,一个价格方案至少对应1条计费规则或者N条计费规则).
	 */
	public PriceValuationEntity getPriceValuationEntity() {
		return priceValuationEntity;
	}

	/**
	 * 设置 价格计费规则(计费规则,一个价格方案至少对应1条计费规则或者N条计费规则).
	 */
	public void setPriceValuationEntity(PriceValuationEntity priceValuationEntity) {
		this.priceValuationEntity = priceValuationEntity;
	}

	/**
	 * 获取 计价方式明细(计价明细,一个价格方案的计费规则分别对应重货，轻货处理计价方式明细).
	 */
	public PriceCriteriaDetailEntity getPriceCriteriaDetailEntity() {
		return priceCriteriaDetailEntity;
	}

	/**
	 * 设置 计价方式明细(计价明细,一个价格方案的计费规则分别对应重货，轻货处理计价方式明细).
	 */
	public void setPriceCriteriaDetailEntity(PriceCriteriaDetailEntity priceCriteriaDetailEntity) {
		this.priceCriteriaDetailEntity = priceCriteriaDetailEntity;
	}

	/**
	 * 获取 价格明细返回结果集.
	 */
	public List<PriceCriteriaDetailEntity> getPriceCriteriaDetailEntityList() {
		return priceCriteriaDetailEntityList;
	}

	/**
	 * 设置 价格明细返回结果集.
	 */
	public void setPriceCriteriaDetailEntityList(List<PriceCriteriaDetailEntity> priceCriteriaDetailEntityList) {
		this.priceCriteriaDetailEntityList = priceCriteriaDetailEntityList;
	}

	/**
	 * 设置 价格方案查询条件.
	 */
	public void setPricePlanEntity(PricePlanEntity pricePlanEntity) {
		this.pricePlanEntity = pricePlanEntity;
	}

	/**
	 * 获取 价格方案返回结果集.
	 */
	public List<PricePlanEntity> getPricePlanEntityList() {
		return pricePlanEntityList;
	}

	/**
	 * 设置 价格方案返回结果集.
	 */
	public void setPricePlanEntityList(List<PricePlanEntity> pricePlanEntityList) {
		this.pricePlanEntityList = pricePlanEntityList;
	}

	/**
	 * 获取 价格比对方案查询条件.
	 */
	public PricePlanEntity getPricePlanEntityOld() {
		return pricePlanEntityOld;
	}

	/**
	 * 设置 价格比对方案查询条件.
	 */
	public void setPricePlanEntityOld(PricePlanEntity pricePlanEntityOld) {
		this.pricePlanEntityOld = pricePlanEntityOld;
	}

}