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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/cache/PreferentialInfoDtoListCacheProvider.java
 * 
 * FILE NAME        	: PreferentialInfoDtoListCacheProvider.java
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
package com.deppon.foss.module.base.baseinfo.server.cache;

import java.util.List;

import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPreferentialDao;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PreferentialInfoDto;


/**
 * 合同优惠信息cache provider类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-2-22 下午3:49:52 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-2-22 下午3:49:52
 * @since
 * @version
 */
public class PreferentialInfoDtoListCacheProvider implements ITTLCacheProvider<List<PreferentialInfoDto>>{
    
    /**
     * 客户优惠信息Dao接口.
     */
    private IPreferentialDao preferentialDao;
    
    /**
     * 设置 客户优惠信息Dao接口.
     *
     * @param preferentialDao the preferentialDao to set
     */
    public void setPreferentialDao(IPreferentialDao preferentialDao) {
        this.preferentialDao = preferentialDao;
    }

    /**
     * <p>根据传入的key查询合同优惠信息集合</p>.
     *
     * @param key 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-2-22 下午4:14:55
     * @see com.deppon.foss.framework.cache.provider.ITTLCacheProvider#get(java.lang.String)
     */
    @Override
    public List<PreferentialInfoDto> get(String key) {
	
	if(StringUtil.isBlank(key)){
	    return null;
	}else {
	    return preferentialDao.queryPreferentialInfoDtosByCustCode(key);
	}
    }

}
