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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/commonselector/ICommonSubSidiaryService.java
 * 
 * FILE NAME        	: ICommonSubSidiaryService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonSubSidiaryDto;
 
/**
 * 公共选择器--子公司信息Service
 * @author 101911-foss-zhouChunlai
 * @date 2013-2-2 上午9:13:46
 */
public interface ICommonSubSidiaryService {
 
	/**
	 * 根据条件查询子公司信息
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2013-2-2 上午9:20:45
	 * @return 
	 */
	List<CommonSubSidiaryDto> querySubSidiaryByCondition(CommonSubSidiaryDto dto, int limit,int start);
	
	/**
	 * 根据条件查询子公司信息总条数.
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2013-1-12 上午10:09:23
	 * @return 
	 */
	Long queryRecordCount(CommonSubSidiaryDto dto);

}
