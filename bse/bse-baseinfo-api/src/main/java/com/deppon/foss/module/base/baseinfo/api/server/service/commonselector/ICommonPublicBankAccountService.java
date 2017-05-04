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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/commonselector/ICommonPublicBankAccountService.java
 * 
 * FILE NAME        	: ICommonPublicBankAccountService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PublicBankAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PublicBankAccountDto;
 
/**
 * 公共查询选择器--组织对公账号Service
 * @author 101911-foss-zhouChunlai
 * @date 2013-1-11 上午8:49:42
 */
public interface ICommonPublicBankAccountService  {
	 
    /**
     *根据dto模糊查询 对公银行账号
     * @author 101911-foss-zhouChunlai
     * @param 
     * @date 2013-1-11 上午8:54:04
     * @return 
     */
    List<PublicBankAccountEntity> queryPublicBankAccountByDto(PublicBankAccountDto dto,int start, int limit);
	
    /**
     * 根据dto模糊查询 对公银行账号 返回的记录总数,用于分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:19
     */
    long countQueryPublicBankAccountByDto(PublicBankAccountDto dto);

}
