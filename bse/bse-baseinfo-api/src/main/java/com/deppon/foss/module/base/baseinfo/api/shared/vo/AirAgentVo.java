package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirAgentEntity;
/**
 * 航空代理VO
 * 
 * @author 130346-foss-lifanghong
 * @date 2013-07-27 下午4:10:24
 */
public class AirAgentVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 航空代理实体
	 */
	private AirAgentEntity airAgentEntity;
	/**
	 * 航空代理实体集合
	 */
	private List<AirAgentEntity> airAgentEntityList;
	public AirAgentEntity getAirAgentEntity() {
		return airAgentEntity;
	}
	public void setAirAgentEntity(AirAgentEntity airAgentEntity) {
		this.airAgentEntity = airAgentEntity;
	}
	public List<AirAgentEntity> getAirAgentEntityList() {
		return airAgentEntityList;
	}
	public void setAirAgentEntityList(List<AirAgentEntity> airAgentEntityList) {
		this.airAgentEntityList = airAgentEntityList;
	}
	
	
}
