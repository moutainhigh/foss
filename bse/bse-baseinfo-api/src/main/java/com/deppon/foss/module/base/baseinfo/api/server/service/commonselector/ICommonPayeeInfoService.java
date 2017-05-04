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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/commonselector/ICommonPayeeInfoService.java
 * 
 * FILE NAME        	: ICommonPayeeInfoService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PayeeInfoEntity;


/**
 * 付款方信息Service接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-12-14 下午4:22:26 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-12-14 下午4:22:26
 * @since
 * @version
 */
public interface ICommonPayeeInfoService extends IService {
	/**
	 * 查询付款方信息
	 * 
	 * @author 078823-foss-panGuangjun
	 * @date 2012-12-15 下午2:44:17
	 * @param entity
	 *            付款方信息实体
	 * @return List<PayeeInfoEntity>:付款方集合
	 */
	 List<PayeeInfoEntity> searchPayeeInfoByCondition(
			PayeeInfoEntity payeeInfoEntity, int start, int limit);

	/**
	 * 查询总条数
	 * 
	 * @author 078823-foss-panGuangjun
	 * @date 2012-12-15 下午2:44:17
	 * @return long
	 */
	 long countPayeeInfoByCondition(PayeeInfoEntity payeeInfoEntity);
}
