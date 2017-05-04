/**
 * 
 */
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonExpressSmallZoneEntity;

/**
 *<p>Title: CommonExpressSmallZoneVo</p>
 * <p>Description: 快递收派小区前后台交互实体</p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-5-4
 */
public class CommonExpressSmallZoneVo {
	/**
	 * 快递收派小区
	 */
	private CommonExpressSmallZoneEntity commonExpressSmallZoneEntity;
	/**
	 * 快递收派小区实体列表
	 */
	private List<CommonExpressSmallZoneEntity> commonExpressSmallZoneList;
	/**
	 * @return the commonExpressSmallZoneEntity
	 */
	public CommonExpressSmallZoneEntity getCommonExpressSmallZoneEntity() {
		return commonExpressSmallZoneEntity;
	}
	/**
	 * @param commonExpressSmallZoneEntity the commonExpressSmallZoneEntity to set
	 */
	public void setCommonExpressSmallZoneEntity(
			CommonExpressSmallZoneEntity commonExpressSmallZoneEntity) {
		this.commonExpressSmallZoneEntity = commonExpressSmallZoneEntity;
	}
	/**
	 * @return the commonExpressSmallZoneList
	 */
	public List<CommonExpressSmallZoneEntity> getCommonExpressSmallZoneList() {
		return commonExpressSmallZoneList;
	}
	/**
	 * @param commonExpressSmallZoneList the commonExpressSmallZoneList to set
	 */
	public void setCommonExpressSmallZoneList(
			List<CommonExpressSmallZoneEntity> commonExpressSmallZoneList) {
		this.commonExpressSmallZoneList = commonExpressSmallZoneList;
	}
	
	
}
