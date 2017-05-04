package com.deppon.foss.module.pickup.pricing.api.shared.vo;

import java.io.Serializable;

import java.util.List;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ExpressDiscountEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ExpressDiscountDto;
/**
 * 
 * Copyright (C) 2015 Asiainfo-Linkage
 *
 *
 * @className:com.deppon.foss.module.pickup.pricing.api.shared.vo.ExpressDiscountPlanVo
 * @description:快递折扣方案Vo  用于与前台数据的交互
 *
 * @version:v1.0.0
 * @author:DP-FOSS-YANGKANG
 *
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2015-1-9     DP-FOSS-YANGKANG       v1.0.0        create
 *
 *
 */
public class ExpressDiscountPlanVo implements Serializable {

	/**
	 *序列化ID
	 */
	private static final long serialVersionUID = 1L;
	//快递折扣方案实体集合
	private List<ExpressDiscountEntity> expressDiscountEntityList;
	
	//快递折扣方案实体
	private ExpressDiscountEntity  expressDiscountEntity;
	
	//快递折扣方案ID集合
	private List<String> expressDiscountPlanIds;
	
	//快递折扣方案明细查询实体集合
	private List<ExpressDiscountDto>  expressDiscountDtoList;
	
	//快递折扣方案明细查询实体
	private ExpressDiscountDto expressDiscountDto;
	
	private List<String> channelCodes;
	
	//快递折扣方案明细ID集合
	private List<String> expressDiscountDetailIds;
	
	//折扣方案原渠道信息
	private List<String> oldChannelCodes;
	private List<String> weeks;
	
	public List<String> getWeeks() {
		return weeks;
	}
	public void setWeeks(List<String> weeks) {
		this.weeks = weeks;
	}
	public List<ExpressDiscountEntity> getExpressDiscountEntityList() {
		return expressDiscountEntityList;
	}
	public void setExpressDiscountEntityList(
			List<ExpressDiscountEntity> expressDiscountEntityList) {
		this.expressDiscountEntityList = expressDiscountEntityList;
	}
	public ExpressDiscountEntity getExpressDiscountEntity() {
		return expressDiscountEntity;
	}
	public void setExpressDiscountEntity(ExpressDiscountEntity expressDiscountEntity) {
		this.expressDiscountEntity = expressDiscountEntity;
	}
	public List<String> getExpressDiscountPlanIds() {
		return expressDiscountPlanIds;
	}
	public void setExpressDiscountPlanIds(List<String> expressDiscountPlanIds) {
		this.expressDiscountPlanIds = expressDiscountPlanIds;
	}
	public List<ExpressDiscountDto> getExpressDiscountDtoList() {
		return expressDiscountDtoList;
	}
	public void setExpressDiscountDtoList(
			List<ExpressDiscountDto> expressDiscountDtoList) {
		this.expressDiscountDtoList = expressDiscountDtoList;
	}
	public ExpressDiscountDto getExpressDiscountDto() {
		return expressDiscountDto;
	}
	public void setExpressDiscountDto(ExpressDiscountDto expressDiscountDto) {
		this.expressDiscountDto = expressDiscountDto;
	}
	public List<String> getOldChannelCodes() {
		return oldChannelCodes;
	}
	public void setOldChannelCodes(List<String> oldChannelCodes) {
		this.oldChannelCodes = oldChannelCodes;
	}
	public List<String> getExpressDiscountDetailIds() {
		return expressDiscountDetailIds;
	}
	public void setExpressDiscountDetailIds(List<String> expressDiscountDetailIds) {
		this.expressDiscountDetailIds = expressDiscountDetailIds;
	}
	public List<String> getChannelCodes() {
		return channelCodes;
	}
	public void setChannelCodes(List<String> channelCodes) {
		this.channelCodes = channelCodes;
	}
	
}
