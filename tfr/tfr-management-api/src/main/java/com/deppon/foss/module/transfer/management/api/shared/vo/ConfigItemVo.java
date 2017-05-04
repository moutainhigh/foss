/**   
 * File Name：ConfigItemVo.java   
 *   
 * Version:1.0
 * ：2013-4-16   
 * Copyright (c) 2013  Deppon All Rights Reserved. 2013    
 * @author: liangfuxiang liangfux@cn.ibm.com
 */

package com.deppon.foss.module.transfer.management.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.management.api.shared.domain.ConfigItemEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.ConfigTypeEntity;

/**
 * Class Description：配置项VO
 * 
 * @author: liangfuxiang liangfux@cn.ibm.com
 * @version: 2013-4-16 下午3:43:09
 */

public class ConfigItemVo implements Serializable {

	/**
	 * serialVersionUID:
	 * 
	 * @since Ver 1.0
	 */

	private static final long serialVersionUID = 1L;

	/**
	 * 配置项类型信息
	 */
	private ConfigTypeEntity configTypeEntity;

	/**
	 * 配置项实体类
	 */
	private ConfigItemEntity configItemEntity;
	/**
	 * 配置项类型信息集合
	 */
	private List<ConfigTypeEntity> configTypeEntityList;

	/**
	 * 配置项实体类集合
	 */
	private List<ConfigItemEntity> configItemEntityList;

	/**
	 * id集合
	 */
	private List<String> idList;

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
	 * @param idList the idList to set Date:2013-4-17上午10:43:14
	 */

	public void setIdList(List<String> idList) {
		this.idList = idList;
	}

	/**
	 * configTypeEntity
	 * 
	 * @return the configTypeEntity
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public ConfigTypeEntity getConfigTypeEntity() {
		return configTypeEntity;
	}

	/**
	 * @param configTypeEntity the configTypeEntity to set Date:2013-4-16下午3:47:11
	 */

	public void setConfigTypeEntity(ConfigTypeEntity configTypeEntity) {
		this.configTypeEntity = configTypeEntity;
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
	 * @param configItemEntity the configItemEntity to set Date:2013-4-16下午3:47:11
	 */

	public void setConfigItemEntity(ConfigItemEntity configItemEntity) {
		this.configItemEntity = configItemEntity;
	}

	/**
	 * configTypeEntityList
	 * 
	 * @return the configTypeEntityList
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public List<ConfigTypeEntity> getConfigTypeEntityList() {
		return configTypeEntityList;
	}

	/**
	 * @param configTypeEntityList the configTypeEntityList to set Date:2013-4-16下午3:47:11
	 */

	public void setConfigTypeEntityList(List<ConfigTypeEntity> configTypeEntityList) {
		this.configTypeEntityList = configTypeEntityList;
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
	 * @param configItemEntityList the configItemEntityList to set Date:2013-4-16下午3:47:11
	 */

	public void setConfigItemEntityList(List<ConfigItemEntity> configItemEntityList) {
		this.configItemEntityList = configItemEntityList;
	}

}
