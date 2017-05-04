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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/INonfixedCusAccountDao.java
 * 
 * FILE NAME        	: INonfixedCusAccountDao.java
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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCusAccountEntity;
/**
 * 临欠散客开户银行账户DAO接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-29 上午11:29:51 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-29 上午11:29:51
 * @since
 * @version
 */
public interface INonfixedCusAccountDao {
    
    /**
     * 新增临欠散客开户银行账户
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-11-29 上午11:29:51
     * @param entity
     *            临欠散客开户银行账户实体
     * @return 1：成功；-1：失败
     * @see
     */
    int addCusAccount(NonfixedCusAccountEntity entity);

    /**
     * 根据开户账号ID作废临欠散客开户银行账户
     * @author dp-xieyantao
     * @date 2012-11-20 上午9:33:05
     * @param crmId 
     * @param modifyUser
     * @return 1：成功；-1：失败
     * @see
     */
    int deleteCusAccountByCode(BigInteger crmId, String modifyUser);

    /**
     * 修改临欠散客开户银行账户
     * @author dp-xieyantao
     * @date 2012-11-29 上午11:29:51
     * @param entity
     *            临欠散客开户银行账户实体
     * @return 1：成功；-1：失败
     * @see
     */
    int updateCusAccount(NonfixedCusAccountEntity entity);
    
    /**
     * <p>根据临欠散客的客户编码查询散客的银行账号</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-21 上午9:17:18
     * @param custCode 临欠散客客户编码
     * @return
     * @see
     */
    List<NonfixedCusAccountEntity> queryCusAccountByCustCode(String custCode);
    
    /**
     * <p>根据客户编码查询时间最近的客户银行账户</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-5-2 下午5:02:13
     * @param custCode 客户编码
     * @return
     * @see
     */
    List<NonfixedCusAccountEntity> queryAccountInfoByCustCode(String custCode);
    
    /**
     * <p>根据crmId,最后一次修改时间查询临欠散客开户银行账户是否存在</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-26 上午10:15:29
     * @param crmId
     * @param lastupdatetime
     * @return
     * @see
     */
    boolean queryCusAccountByCrmId(BigDecimal crmId,Date lastupdatetime);
    
    /**
     * <p>根据crmId查询临欠散客银行账户信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-3-8 上午11:50:31
     * @param crmId 临欠散客银行账户信息ID
     * @return
     * @see
     */
    NonfixedCusAccountEntity queryCusAccountByCrmId(BigInteger crmId);

}
