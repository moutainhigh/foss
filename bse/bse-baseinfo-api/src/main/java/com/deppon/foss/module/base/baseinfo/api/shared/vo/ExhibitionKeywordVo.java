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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/SpecialAddressVo.java
 * 
 * FILE NAME        	: SpecialAddressVo.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExhibitionKeywordEntity;
/**
 * 
 * 特殊地址VO
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:何波,date:2013-2-20 下午2:27:47</p>
 * @author 何波
 * @date 2013-2-20 下午2:27:47
 * @since
 * @version
 */
public class ExhibitionKeywordVo implements Serializable {

	private static final long serialVersionUID = 4951105994423149518L;

	// 特殊地址信息
	private ExhibitionKeywordEntity ExhibitionKeyword;
	// 特殊地址集合
	private List<ExhibitionKeywordEntity> ExhibitionKeywordList;
	public ExhibitionKeywordEntity getExhibitionKeyword() {
		return ExhibitionKeyword;
	}

	public void setExhibitionKeyword(ExhibitionKeywordEntity exhibitionKeyword) {
		this.ExhibitionKeyword = exhibitionKeyword;
	}

	public List<ExhibitionKeywordEntity> getExhibitionKeywordList() {
		return ExhibitionKeywordList;
	}

	public void setExhibitionKeywordList(
			List<ExhibitionKeywordEntity> exhibitionKeywordList) {
		this.ExhibitionKeywordList = exhibitionKeywordList;
	}

	// 批量操作ID集合
	private List<String> batchIds;

	public List<String> getBatchIds() {
		return batchIds;
	}

	public void setBatchIds(List<String> batchIds) {
		this.batchIds = batchIds;
	}

}
