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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/ProhibitedArticlesVo.java
 * 
 * FILE NAME        	: ProhibitedArticlesVo.java
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
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProhibitedArticlesEntity;
/**
 * (禁运物品VO)
 * @author 073586-FOSS-LIXUEXING
 * @date 2012-11-27 19:40:42
 * @since
 * @version
 */
public class ProhibitedArticlesVo implements Serializable {

	private static final long serialVersionUID = -6709471332955961785L;
	//禁运物品实体
	private ProhibitedArticlesEntity prohibitedArticlesEntity;
	//禁运物品实体LIST
	private List<ProhibitedArticlesEntity> prohibitedArticlesEntityList;
	//实体中对应virtualCode字段，字段间以逗号分隔[支持批量删除]
	private String[] codeStr;
	//返回前台的INT类型属性
	private int returnInt;

	/**
	 * 下面是get,set方法
	 * 
	 * @author 073586-FOSS-LIXUEXING
	 * @date 2012-11-27  19:40:42
	 */
	public ProhibitedArticlesEntity getProhibitedArticlesEntity() {
		return prohibitedArticlesEntity;
	}
	public void setProhibitedArticlesEntity(
			ProhibitedArticlesEntity prohibitedArticlesEntity) {
		this.prohibitedArticlesEntity = prohibitedArticlesEntity;
	}
	public List<ProhibitedArticlesEntity> getProhibitedArticlesEntityList() {
		return prohibitedArticlesEntityList;
	}
	public void setProhibitedArticlesEntityList(
			List<ProhibitedArticlesEntity> prohibitedArticlesEntityList) {
		this.prohibitedArticlesEntityList = prohibitedArticlesEntityList;
	}

	public int getReturnInt() {
		return returnInt;
	}

	public void setReturnInt(int returnInt) {
		this.returnInt = returnInt;
	}
	public String[] getCodeStr() {
		return codeStr;
	}
	public void setCodeStr(String[] codeStr) {
		this.codeStr = codeStr;
	}
}
