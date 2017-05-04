package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryRegionsEntity;

/**
 * 快递派送区域的Vo
 * @author 130566
 *
 */
public class ExpressDeliveryRegionsVo {
	/**
	 * 派送区域详情实体
	 */
	private ExpressDeliveryRegionsEntity expressDeliveryRegionsEntity;
	/**
	 * 派送区域实体列表
	 */
	private List<ExpressDeliveryRegionsEntity> expressDeliveryRegionsList;
	/**
	 * 用来批量作废的code数组
	 */
	private String[] codes;
	
	/**
	 * 上级区域编码
	 */
	private String parentDistrictCode;
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 以下为set get 方法
	 */
	public ExpressDeliveryRegionsEntity getExpressDeliveryRegionsEntity() {
		return expressDeliveryRegionsEntity;
	}
	public void setExpressDeliveryRegionsEntity(
			ExpressDeliveryRegionsEntity expressDeliveryRegionsEntity) {
		this.expressDeliveryRegionsEntity = expressDeliveryRegionsEntity;
	}
	public List<ExpressDeliveryRegionsEntity> getExpressDeliveryRegionsList() {
		return expressDeliveryRegionsList;
	}
	public void setExpressDeliveryRegionsList(
			List<ExpressDeliveryRegionsEntity> expressDeliveryRegionsList) {
		this.expressDeliveryRegionsList = expressDeliveryRegionsList;
	}
	public String[] getCodes() {
		return codes;
	}
	public void setCodes(String[] codes) {
		this.codes = codes;
	}
	public String getParentDistrictCode() {
		return parentDistrictCode;
	}
	public void setParentDistrictCode(String parentDistrictCode) {
		this.parentDistrictCode = parentDistrictCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
