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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IPayeeInfoDao.java
 * 
 * FILE NAME        	: IPayeeInfoDao.java
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
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PayeeInfoEntity;


/**
 * 付款方信息DAO接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-12-14 下午2:44:17 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-12-14 下午2:44:17
 * @since
 * @version
 */
public interface IPayeeInfoDao {
    
    /**
     * 新增付款方信息
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-12-14 下午2:44:17
     * @param entity
     *            付款方信息实体
     * @return 1：成功；-1：失败
     * @see
     */
    int addPayeeInfo(PayeeInfoEntity entity);
    
    /**
     * <p>根据收款方编码查询收款方信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-5-16 下午6:03:59
     * @param payeeNo 收款方编码
     * @return
     * @see
     */
    PayeeInfoEntity queryPayeeInfoByPayeeNo(String payeeNo);

    /**
     * 根据收款方编码作废付款方信息
     * @author dp-xieyantao
     * @date 2012-12-14 下午2:44:17
     * @param payeeNo
     * @param modifyUser
     * @return 1：成功；-1：失败
     * @see
     */
    int deletePayeeInfoByPayeeNo(String payeeNo, String modifyUser);

}
