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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/LoadAndUnloadSquadVo.java
 * 
 * FILE NAME        	: LoadAndUnloadSquadVo.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadSquadEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PorterEntity;
/**
 * (装卸车小队VO)
 * @author 073586-FOSS-LIXUEXING
 * @date 2012-11-27 19:40:42
 * @since
 * @version
 */
public class LoadAndUnloadSquadVo implements Serializable {
	private static final long serialVersionUID = -4071850785593730357L;
	//禁运物品实体
	private LoadAndUnloadSquadEntity loadAndUnloadSquadEntity;
	//禁运物品实体LIST
	private List<LoadAndUnloadSquadEntity> loadAndUnloadSquadEntityList;
	//理货员实体
	private PorterEntity porterEntity;
	//理货员实体LIST[新增 理货员实体LIST]
	private List<PorterEntity> porterEntityList;
	//实体中对应关键字段[删除 理货员实体LIST]
	private String[] codeStr;
	//返回前台的INT类型属性
	private int returnInt;
	/**
	 * 下面是get,set方法
	 * 
	 * @author 073586-FOSS-LIXUEXING
	 * @date 2012-11-27  19:40:42
	 */
	public LoadAndUnloadSquadEntity getLoadAndUnloadSquadEntity() {
		return loadAndUnloadSquadEntity;
	}
	public void setLoadAndUnloadSquadEntity(
			LoadAndUnloadSquadEntity loadAndUnloadSquadEntity) {
		this.loadAndUnloadSquadEntity = loadAndUnloadSquadEntity;
	}
	public List<LoadAndUnloadSquadEntity> getLoadAndUnloadSquadEntityList() {
		return loadAndUnloadSquadEntityList;
	}
	public void setLoadAndUnloadSquadEntityList(
			List<LoadAndUnloadSquadEntity> loadAndUnloadSquadEntityList) {
		this.loadAndUnloadSquadEntityList = loadAndUnloadSquadEntityList;
	}
	public String[] getCodeStr() {
		return codeStr;
	}
	public void setCodeStr(String[] codeStr) {
		this.codeStr = codeStr;
	}
	public int getReturnInt() {
		return returnInt;
	}
	public void setReturnInt(int returnInt) {
		this.returnInt = returnInt;
	}
	public PorterEntity getPorterEntity() {
		return porterEntity;
	}
	public void setPorterEntity(PorterEntity porterEntity) {
		this.porterEntity = porterEntity;
	}
	public List<PorterEntity> getPorterEntityList() {
		return porterEntityList;
	}
	public void setPorterEntityList(List<PorterEntity> porterEntityList) {
		this.porterEntityList = porterEntityList;
	}

}
