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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/cache/CustomerDtoCacheProvider.java
 * 
 * FILE NAME        	: CustomerDtoCacheProvider.java
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

import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;


/**
 * 客户主数据DTO Cache Provider类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-2-22 上午10:32:59 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-2-22 上午10:32:59
 * @since
 * @version
 */
public class CustomerDtoCacheProvider implements ITTLCacheProvider<CustomerDto>{
    
    /**
     * 客户信息Dao接口.
     */
    private ICustomerDao customerDao;
    
    /**
     * 设置 客户信息Dao接口.
     *
     * @param customerDao the customerDao to set
     */
    public void setCustomerDao(ICustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    /** 
     * <p>获取缓存数据</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-23 下午3:07:27
     * @param key
     * @return 
     * @see com.deppon.foss.framework.cache.provider.ITTLCacheProvider#get(java.lang.String)
     */
    @Override
    public CustomerDto get(String key) {
	if(StringUtil.isBlank(key)){
	    return null;
	}else {
	    return customerDao.queryCustInfoByCode(key);
	}
    }

}
