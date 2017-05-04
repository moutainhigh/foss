package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonTransTeamEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;

/**
 * action和前台传递值实体
 * 
 * @author dujunhui-187862
 * @date 2014-08-13 下午9:12:58
 */
public class CommonTransTeamVo implements Serializable {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 2629160509193950089L;

	/**
	 * 传递到前台的组织集合
	 */
	private List<OrgAdministrativeInfoEntity> entityList;
	
	/**
	 * 组织公共选择器实体
	 */
	private CommonTransTeamEntity entity;

	/**
	 * @return  the entityList
	 */
	public List<OrgAdministrativeInfoEntity> getEntityList() {
		return entityList;
	}

	/**
	 * @param entityList the entityList to set
	 */
	public void setEntityList(List<OrgAdministrativeInfoEntity> entityList) {
		this.entityList = entityList;
	}

	/**
	 * @return  the entity
	 */
	public CommonTransTeamEntity getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(CommonTransTeamEntity entity) {
		this.entity = entity;
	}

}
