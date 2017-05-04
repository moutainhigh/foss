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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/commonselector/ICommonSaleDeptAndOuterBranchService.java
 * 
 * FILE NAME        	: ICommonSaleDeptAndOuterBranchService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonSaleDeptAndOuterBranchDto;


/**
 * 公共查询选择器--营业部和偏线代理网点信息查询.
 * @author 101911-foss-zhouChunlai
 * @date 2013-1-28 上午10:27:14
 */
public interface ICommonSaleDeptAndOuterBranchService extends IService {
	/**
	 * 查询营业部和偏线代理网点信息
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2013-1-28 上午10:51:59
	 * @return 
	 */
	List<CommonSaleDeptAndOuterBranchDto> searchSaleDeptAndOuterBranchByCondition(CommonSaleDeptAndOuterBranchDto dto, int start, int limit); 

 
	/**
	 * 查询总条数
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2013-1-28 上午11:08:51
	 * @return 
	 */
	long countReocrd(CommonSaleDeptAndOuterBranchDto dto);
}
