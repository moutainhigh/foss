/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/PlatformStorageDistanceEntity.java
 * 
 * FILE NAME        	: PlatformStorageDistanceEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/**
 * 
 */
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 月台和库位间的距离
 * 
 * @author zhujunyong
 * Create on Oct 10, 2012
 * 
 */
public class PlatformStorageDistanceEntity extends BaseEntity{

	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = -230236430711888104L;

	/**
	 * 组织
	 */
	private OrgAdministrativeInfoEntity organization;
	
	/**
	 * 月台
	 */
	private PlatformEntity platform;

	/**
	 * 库位
	 */
	private StorageEntity storage;
	
	/**
	 * 距离 单位米
	 */
	private int distance;

	/**
	 * 是否有效
	 */
	private boolean active;

	/**
	 * @return the platform
	 */
	public PlatformEntity getPlatform() {
		return platform;
	}

	/**
	 * @param platform the platform to set
	 */
	public void setPlatform(PlatformEntity platform) {
		this.platform = platform;
	}

	/**
	 * @return the storage
	 */
	public StorageEntity getStorage() {
		return storage;
	}

	/**
	 * @param storage the storage to set
	 */
	public void setStorage(StorageEntity storage) {
		this.storage = storage;
	}

	/**
	 * @return the distance
	 */
	public int getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	/**
	 * @return  the organization
	 */
	public OrgAdministrativeInfoEntity getOrganization() {
	    return organization;
	}
	
	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(OrgAdministrativeInfoEntity organization) {
	    this.organization = organization;
	}
}