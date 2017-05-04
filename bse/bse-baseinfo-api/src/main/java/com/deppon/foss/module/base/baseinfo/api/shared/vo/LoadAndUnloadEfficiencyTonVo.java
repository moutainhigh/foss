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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/LoadAndUnloadEfficiencyTonVo.java
 * 
 * FILE NAME        	: LoadAndUnloadEfficiencyTonVo.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyTonEntity;


/**
 * 装卸车效率标准VO
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-29 下午4:11:16
 */
public class LoadAndUnloadEfficiencyTonVo implements Serializable {
    private static final long serialVersionUID = 3620501034012743142L;
    
    // 装卸车标准-吨-时间列表
    private List<LoadAndUnloadEfficiencyTonEntity> loadAndUnloadEfficiencyTonList;

    // 装卸车标准-吨-时间详情
    private LoadAndUnloadEfficiencyTonEntity loadAndUnloadEfficiencyTonEntity;


    /**
     * 下面是get,set方法
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-16 下午2:20:42
     */
   
    public List<LoadAndUnloadEfficiencyTonEntity> getLoadAndUnloadEfficiencyTonList() {
		return loadAndUnloadEfficiencyTonList;
	}

	public void setLoadAndUnloadEfficiencyTonList(
			List<LoadAndUnloadEfficiencyTonEntity> loadAndUnloadEfficiencyTonList) {
		this.loadAndUnloadEfficiencyTonList = loadAndUnloadEfficiencyTonList;
	}

	public LoadAndUnloadEfficiencyTonEntity getLoadAndUnloadEfficiencyTonEntity() {
		return loadAndUnloadEfficiencyTonEntity;
	}

	public void setLoadAndUnloadEfficiencyTonEntity(
			LoadAndUnloadEfficiencyTonEntity loadAndUnloadEfficiencyTonEntity) {
		this.loadAndUnloadEfficiencyTonEntity = loadAndUnloadEfficiencyTonEntity;
	}



}
