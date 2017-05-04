/**   
 * File Name：ConfigOrgRelationVo.java   
 *   
 * Version:1.0
 * ：2013-3-28   
 * Copyright (c) 2013  Deppon All Rights Reserved. 2013    
 * @author: liangfuxiang liangfux@cn.ibm.com
 */

package com.deppon.foss.module.transfer.management.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.management.api.shared.domain.ConfigItemEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.ConfigOrgRelationEntity;

/**
 * Class Description： 配置项与组织对应关系VO
 * 
 * @author: liangfuxiang liangfux@cn.ibm.com
 * @version: 2013-3-28 下午5:52:00
 */

public class ConfigOrgRelationVo implements Serializable {

	private static final long serialVersionUID = 6012397836892288018L;

	/**
	 * 配置项与组织对应关系实体
	 */
	private ConfigOrgRelationEntity configOrgRelationEntity;

	/**
	 * 配置项与组织对应关系实体列表
	 */
	private List<ConfigOrgRelationEntity> configOrgRelationEntityList;

	/**
	 * 配置项信息列表（配置项类型唯一）
	 */
	private List<ConfigItemEntity> distinctConfigTypeEntityList;

	/**
	 * 配置项信息列表
	 */
	private List<ConfigItemEntity> configItemEntityList;

	/**
	 * 组织配置项ID
	 */
	private List<String> idList;

	/**
	 * 组织配置项基础信息
	 */
	private ConfigItemEntity configItemEntity;

	/**
	 * configOrgRelationEntityList
	 * 
	 * @return the configOrgRelationEntityList
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public List<ConfigOrgRelationEntity> getConfigOrgRelationEntityList() {
		return configOrgRelationEntityList;
	}

	/**
	 * @param configOrgRelationEntityList the configOrgRelationEntityList to set Date:2013-3-28下午5:55:25
	 */

	public void setConfigOrgRelationEntityList(List<ConfigOrgRelationEntity> configOrgRelationEntityList) {
		this.configOrgRelationEntityList = configOrgRelationEntityList;
	}

	/**
	 * configOrgRelationEntity
	 * 
	 * @return the configOrgRelationEntity
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public ConfigOrgRelationEntity getConfigOrgRelationEntity() {
		return configOrgRelationEntity;
	}

	/**
	 * @param configOrgRelationEntity the configOrgRelationEntity to set Date:2013-3-29下午1:45:36
	 */

	public void setConfigOrgRelationEntity(ConfigOrgRelationEntity configOrgRelationEntity) {
		this.configOrgRelationEntity = configOrgRelationEntity;
	}

	/**
	 * distinctConfigTypeEntityList
	 * 
	 * @return the distinctConfigTypeEntityList
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public List<ConfigItemEntity> getDistinctConfigTypeEntityList() {
		return distinctConfigTypeEntityList;
	}

	/**
	 * @param distinctConfigTypeEntityList the distinctConfigTypeEntityList to set Date:2013-4-1下午2:21:40
	 */

	public void setDistinctConfigTypeEntityList(List<ConfigItemEntity> distinctConfigTypeEntityList) {
		this.distinctConfigTypeEntityList = distinctConfigTypeEntityList;
	}

	/**
	 * configItemEntityList
	 * 
	 * @return the configItemEntityList
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public List<ConfigItemEntity> getConfigItemEntityList() {
		return configItemEntityList;
	}

	/**
	 * @param configItemEntityList the configItemEntityList to set Date:2013-4-1下午2:22:49
	 */

	public void setConfigItemEntityList(List<ConfigItemEntity> configItemEntityList) {
		this.configItemEntityList = configItemEntityList;
	}

	/**
	 * idList
	 * 
	 * @return the idList
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public List<String> getIdList() {
		return idList;
	}

	/**
	 * @param idList the idList to set Date:2013-4-3下午2:08:37
	 */

	public void setIdList(List<String> idList) {
		this.idList = idList;
	}

	/**
	 * configItemEntity
	 * 
	 * @return the configItemEntity
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public ConfigItemEntity getConfigItemEntity() {
		return configItemEntity;
	}

	/**
	 * @param configItemEntity the configItemEntity to set Date:2013-4-3下午7:11:36
	 */

	public void setConfigItemEntity(ConfigItemEntity configItemEntity) {
		this.configItemEntity = configItemEntity;
	}

}
