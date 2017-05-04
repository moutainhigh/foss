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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/StorageVo.java
 * 
 * FILE NAME        	: StorageVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.StorageEntity;

/**
 * (库位VO)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:078838-foss-zhangbin,date:2012-11-28 下午6:15:07
 * </p>
 * 
 * @author 078838-foss-zhangbin
 * @date 2012-11-28 下午6:15:07
 * @since
 * @version
 */
public class StorageVo implements Serializable {

	/**
     *
     */
	private static final long serialVersionUID = 3875916350859197349L;
	
    //库位实体
	private StorageEntity storageEntity;
	//库位实体LIST
	private List<StorageEntity> storageEntityList;
	//批量作废库位虚拟编码
	private List<String> storageVirtualCodes;
	//批量作废库位id
	private List<String>  ids;
	
	/** 
	 *获取
	 * @return  ids  
	 */
	public List<String> getIds() {
		return ids;
	}
	/**
	 *设置
	 *setIds
	 * @param ids the ids to set
	 * @return the ids
	 */
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	public List<String> getStorageVirtualCodes() {
		return storageVirtualCodes;
	}
	public void setStorageVirtualCodes(List<String> storageVirtualCodes) {
		this.storageVirtualCodes = storageVirtualCodes;
	}
	public StorageEntity getStorageEntity() {
		return storageEntity;
	}
	public void setStorageEntity(StorageEntity storageEntity) {
		this.storageEntity = storageEntity;
	}
	public List<StorageEntity> getStorageEntityList() {
		return storageEntityList;
	}
	public void setStorageEntityList(List<StorageEntity> storageEntityList) {
		this.storageEntityList = storageEntityList;
	}
	
}
