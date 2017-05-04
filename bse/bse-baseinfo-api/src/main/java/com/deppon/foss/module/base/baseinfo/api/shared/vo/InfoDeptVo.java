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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/InfoDeptVo.java
 * 
 * FILE NAME        	: InfoDeptVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptScoresEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptScoresStdEntity;

public class InfoDeptVo {
	// 信息部 集合
	private List<InfoDeptEntity> infoDeptEntityList;
	//信息部 实体
	private InfoDeptEntity infoDeptEntity = new InfoDeptEntity();
	//信息部 标准得分 实体
	private InfoDeptScoresEntity infoDeptScoresEntity = new InfoDeptScoresEntity();
	//信息部 标准得分 实体
	private List<InfoDeptScoresEntity> infoDeptScoresEntityList;
	//信息部得分 标准 
	private List<InfoDeptScoresStdEntity> infoDeptScoresStdList;
	// 返回前台的INT类型属性
	private int returnInt;
	//实体中对应ID字段，[支持批量删除]
    private List<String> codeStr;
	/**
	 * 下面是get,set方法
	 * 
	 * @author 073586-FOSS-LIXUEXING
	 * @date 2012-11-27 下午19:40:42
	 */

	public int getReturnInt() {
		return returnInt;
	}
	public void setReturnInt(int returnInt) {
		this.returnInt = returnInt;
	}
	public List<InfoDeptEntity> getInfoDeptEntityList() {
		return infoDeptEntityList;
	}
	public void setInfoDeptEntityList(List<InfoDeptEntity> infoDeptEntityList) {
		this.infoDeptEntityList = infoDeptEntityList;
	}
	public InfoDeptEntity getInfoDeptEntity() {
		return infoDeptEntity;
	}
	public void setInfoDeptEntity(InfoDeptEntity infoDeptEntity) {
		this.infoDeptEntity = infoDeptEntity;
	}
	public List<String> getCodeStr() {
		return codeStr;
	}
	public void setCodeStr(List<String> codeStr) {
		this.codeStr = codeStr;
	}
	public InfoDeptScoresEntity getInfoDeptScoresEntity() {
		return infoDeptScoresEntity;
	}
	public void setInfoDeptScoresEntity(InfoDeptScoresEntity infoDeptScoresEntity) {
		this.infoDeptScoresEntity = infoDeptScoresEntity;
	}
	public List<InfoDeptScoresEntity> getInfoDeptScoresEntityList() {
		return infoDeptScoresEntityList;
	}
	public void setInfoDeptScoresEntityList(
			List<InfoDeptScoresEntity> infoDeptScoresEntityList) {
		this.infoDeptScoresEntityList = infoDeptScoresEntityList;
	}
	public List<InfoDeptScoresStdEntity> getInfoDeptScoresStdList() {
		return infoDeptScoresStdList;
	}
	public void setInfoDeptScoresStdList(
			List<InfoDeptScoresStdEntity> infoDeptScoresStdList) {
		this.infoDeptScoresStdList = infoDeptScoresStdList;
	}
	
}
