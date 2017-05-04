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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/LoadAndUnloadEfficiencyManVo.java
 * 
 * FILE NAME        	: LoadAndUnloadEfficiencyManVo.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyManEntity;



/**
 * 装卸车人力效率标杆
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-29 下午4:10:55
 */
public class LoadAndUnloadEfficiencyManVo implements Serializable {
    private static final long serialVersionUID = 3620501034012743142L;
    
    // 装卸车标准-吨-时间列表
    private List<LoadAndUnloadEfficiencyManEntity> loadAndUnloadEfficiencyManList;

    // 装卸车标准-吨-时间详情
    private LoadAndUnloadEfficiencyManEntity loadAndUnloadEfficiencyManEntity;

    
    /**
     * 下面是get,set方法
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-16 下午2:20:42
     */
   

	public List<LoadAndUnloadEfficiencyManEntity> getLoadAndUnloadEfficiencyManList() {
		return loadAndUnloadEfficiencyManList;
	}

	public void setLoadAndUnloadEfficiencyManList(
			List<LoadAndUnloadEfficiencyManEntity> loadAndUnloadEfficiencyManList) {
		this.loadAndUnloadEfficiencyManList = loadAndUnloadEfficiencyManList;
	}

	public LoadAndUnloadEfficiencyManEntity getLoadAndUnloadEfficiencyManEntity() {
		return loadAndUnloadEfficiencyManEntity;
	}

	public void setLoadAndUnloadEfficiencyManEntity(
			LoadAndUnloadEfficiencyManEntity loadAndUnloadEfficiencyManEntity) {
		this.loadAndUnloadEfficiencyManEntity = loadAndUnloadEfficiencyManEntity;
	}


  



}
