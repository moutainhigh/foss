package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.TemporaryRentalCarMarkTimeManagementEntity;

/**
 * 
 * @author 218392  张永雪
 * @date 创建时间：2014-12-18 下午5:12:18
 *
 */
public class TemporaryRentalCarMarkTimeManagementVo implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 临时租车标记时间管理 实体类
	 */
	private TemporaryRentalCarMarkTimeManagementEntity entity;
	
	/**
	 * 临时租车标记时间管理信息集合
	 */
	private List<TemporaryRentalCarMarkTimeManagementEntity> entityList;
	
	/**
	 * 临时租车标记时间管理信息id
	 */
	private String id;
	
	/**
	 * 临时租车标记时间管理信息id集合
	 */
	private List<String> idList;

	public TemporaryRentalCarMarkTimeManagementEntity getEntity() {
		return entity;
	}

	public void setEntity(TemporaryRentalCarMarkTimeManagementEntity entity) {
		this.entity = entity;
	}

	public List<TemporaryRentalCarMarkTimeManagementEntity> getEntityList() {
		return entityList;
	}

	public void setEntityList(
			List<TemporaryRentalCarMarkTimeManagementEntity> entityList) {
		this.entityList = entityList;
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

	
}
