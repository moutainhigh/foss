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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/PlatformVo.java
 * 
 * FILE NAME        	: PlatformVo.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity;

/**
 * (月台VO)
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
public class PlatformVo implements Serializable {

	/**
     *
     */
	private static final long serialVersionUID = 3875916350859197349L;
	//月台实体
    private PlatformEntity platformEntity;
    //月台list实体
    private List<PlatformEntity> platformEntityList;
    //批量作废月台虚拟编码
    private List<String> platformVirtualCodes;
    //导入出现的为导入成功的数据
    private List<Integer> numList;
    
    
	public List<Integer> getNumList() {
		return numList;
	}
	public void setNumList(List<Integer> numList) {
		this.numList = numList;
	}
	public List<String> getPlatformVirtualCodes() {
		return platformVirtualCodes;
	}
	public void setPlatformVirtualCodes(List<String> platformVirtualCodes) {
		this.platformVirtualCodes = platformVirtualCodes;
	}
	public PlatformEntity getPlatformEntity() {
		return platformEntity;
	}
	public void setPlatformEntity(PlatformEntity platformEntity) {
		this.platformEntity = platformEntity;
	}
	public List<PlatformEntity> getPlatformEntityList() {
		return platformEntityList;
	}
	public void setPlatformEntityList(List<PlatformEntity> platformEntityList) {
		this.platformEntityList = platformEntityList;
	}
	
	
}
