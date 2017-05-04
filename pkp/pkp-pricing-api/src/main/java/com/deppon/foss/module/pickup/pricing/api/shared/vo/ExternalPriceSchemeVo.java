package com.deppon.foss.module.pickup.pricing.api.shared.vo;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ExternalPriceSchemeEntity;

/**
 * @author 092020
 * @description 偏线外发价格方案VO
 */
public class ExternalPriceSchemeVo extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1155286675743654865L;
	
	/**
	 * 偏线外发价格方案
	 */
	private ExternalPriceSchemeEntity entity;
	/**
	 * 偏线外发价格方案ID
	 */
	private String id;
	/**
	 * 偏线外发价格方案IDLIST
	 */
	private List<String> idList;
	/**
	 * 偏线外发价格方案List
	 */
	private List<ExternalPriceSchemeEntity> entityList;
	/**
	 * 生效时间
	 */
	private Date beginTime;
	/**
	 * 截止时间
	 */
	private Date endTime;
	public ExternalPriceSchemeEntity getEntity() {
		return entity;
	}
	public void setEntity(ExternalPriceSchemeEntity entity) {
		this.entity = entity;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<String> getIdList() {
		return idList;
	}
	public void setIdList(List<String> idList) {
		this.idList = idList;
	}
	public List<ExternalPriceSchemeEntity> getEntityList() {
		return entityList;
	}
	public void setEntityList(List<ExternalPriceSchemeEntity> entityList) {
		this.entityList = entityList;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
