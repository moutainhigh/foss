package com.deppon.foss.module.transfer.platform.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.GoodsAreaDensityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.GoodsAreaDensity4SumDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.GoodsAreaDensityQcDto;

public class GoodsAreaDensityVo implements Serializable{

	private static final long serialVersionUID = -6111825094501994112L;

	/**
	 * 货区密度查询条件
	 */
	private GoodsAreaDensityQcDto goodsAreaDensityQcDto;
	
	/**
	 * 各种货区密度查询返回结果
	 */
	private List<GoodsAreaDensityEntity> goodsAreaDensityEntities;
	
	/**
	 * 用于统计的货区密度返回结果
	 */
	private List<GoodsAreaDensity4SumDto> goodsAreaDensity4SumDtos;
	
	/**
	 * 部门所在外场编码
	 */
	private String parentTfrCtrCode;
	
	/**
	 * 部门所在外场名称
	 */
	private String parentTfrCtrName;

	public GoodsAreaDensityQcDto getGoodsAreaDensityQcDto() {
		return goodsAreaDensityQcDto;
	}

	public void setGoodsAreaDensityQcDto(GoodsAreaDensityQcDto goodsAreaDensityQcDto) {
		this.goodsAreaDensityQcDto = goodsAreaDensityQcDto;
	}

	public List<GoodsAreaDensityEntity> getGoodsAreaDensityEntities() {
		return goodsAreaDensityEntities;
	}

	public void setGoodsAreaDensityEntities(
			List<GoodsAreaDensityEntity> goodsAreaDensityEntities) {
		this.goodsAreaDensityEntities = goodsAreaDensityEntities;
	}

	public List<GoodsAreaDensity4SumDto> getGoodsAreaDensity4SumDtos() {
		return goodsAreaDensity4SumDtos;
	}

	public void setGoodsAreaDensity4SumDtos(
			List<GoodsAreaDensity4SumDto> goodsAreaDensity4SumDtos) {
		this.goodsAreaDensity4SumDtos = goodsAreaDensity4SumDtos;
	}

	public String getParentTfrCtrCode() {
		return parentTfrCtrCode;
	}

	public void setParentTfrCtrCode(String parentTfrCtrCode) {
		this.parentTfrCtrCode = parentTfrCtrCode;
	}

	public String getParentTfrCtrName() {
		return parentTfrCtrName;
	}

	public void setParentTfrCtrName(String parentTfrCtrName) {
		this.parentTfrCtrName = parentTfrCtrName;
	}

}
