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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonSubSidiaryService.java
 * 
 * FILE NAME        	: CommonSubSidiaryService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonSubSidiaryDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonSubSidiaryService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonSubSidiaryDto;

 
/**
 * 公共选择器--子公司信息service.
 * @author 101911-foss-zhouChunlai
 * @date 2013-2-2 上午9:29:31
 */
public class CommonSubSidiaryService implements ICommonSubSidiaryService {
	
	/**
	 * 子公司信息Dao
	 */
	private ICommonSubSidiaryDao commonSubSidiaryDao;
	
	/** 
	 *  根据条件查询子公司信息
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2013-2-2 上午9:37:12
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonSubSidiaryService#querySubSidiaryByCondition(com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonSubSidiaryDto, int, int)
	 */
	@Override
	public List<CommonSubSidiaryDto> querySubSidiaryByCondition(
			CommonSubSidiaryDto dto, int limit, int start) {
		return commonSubSidiaryDao.querySubSidiaryByCondition(dto, limit, start);
	}

	/** 
	 * 根据条件查询子公司信息总条数
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2013-2-2 上午9:37:15
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonSubSidiaryService#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonSubSidiaryDto)
	 */
	@Override
	public Long queryRecordCount(CommonSubSidiaryDto dto) {
		return commonSubSidiaryDao.queryRecordCount(dto);
	}

	
	public ICommonSubSidiaryDao getCommonSubSidiaryDao() {
		return commonSubSidiaryDao;
	}

	
	public void setCommonSubSidiaryDao(ICommonSubSidiaryDao commonSubSidiaryDao) {
		this.commonSubSidiaryDao = commonSubSidiaryDao;
	}
	
}
