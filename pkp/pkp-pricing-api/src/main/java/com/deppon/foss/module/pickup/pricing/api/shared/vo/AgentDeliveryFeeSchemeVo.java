package com.deppon.foss.module.pickup.pricing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.AgentDeliveryFeeSchemeEntity;
/**
 * 偏线代理送货费方案VO
 */
public class AgentDeliveryFeeSchemeVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5156885323426359707L;
	/**
	 * 偏线代理送货费方案实体
	 */
	private AgentDeliveryFeeSchemeEntity schemeEntity;
	/**
	 * 偏线代理送货费方案实体List
	 */
	private List<AgentDeliveryFeeSchemeEntity> schemeEntityList;
	
	/**
	 * 偏线代理送货费方案ID
	 */
	private String id;
	/**
	 * 偏线代理送货费方案ID List
	 */
	private List<String> idList;

	public AgentDeliveryFeeSchemeEntity getSchemeEntity() {
		return schemeEntity;
	}

	public void setSchemeEntity(AgentDeliveryFeeSchemeEntity schemeEntity) {
		this.schemeEntity = schemeEntity;
	}

	public String getId() {
		return id;
	}

	public void setid(String id) {
		this.id = id;
	}
	public List<AgentDeliveryFeeSchemeEntity> getSchemeEntityList() {
		return schemeEntityList;
	}

	public void setSchemeEntityList(
			List<AgentDeliveryFeeSchemeEntity> schemeEntityList) {
		this.schemeEntityList = schemeEntityList;
	}
	public List<String> getIdList() {
		return idList;
	}

	public void setIdList(List<String> idList) {
		this.idList = idList;
	}
}
