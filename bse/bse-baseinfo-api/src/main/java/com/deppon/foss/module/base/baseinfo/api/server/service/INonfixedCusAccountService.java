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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/INonfixedCusAccountService.java
 * 
 * FILE NAME        	: INonfixedCusAccountService.java
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
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCusAccountEntity;

/**
 * 临欠散客开户银行账户Service接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-29 上午11:33:31 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-29 上午11:33:31
 * @since
 * @version
 */
public interface INonfixedCusAccountService extends IService {
    
    /**
     * 新增临欠散客开户银行账户
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-11-29 上午11:33:31
     * @param entity
     *            临欠散客开户银行账户实体
     * @return 1：成功；-1：失败
     * @see
     */
     int addCusAccount(NonfixedCusAccountEntity entity);

    /**
     * 根据开户账号作废临欠散客开户银行账户
     * @author dp-xieyantao
     * @date 2012-11-29 上午11:33:31
     * @param code code
     * @param modifyUser
     * @return 1：成功；-1：失败
     * @see
     */
     int deleteCusAccountByCode(BigInteger crmId, String modifyUser);

    /**
     * 修改临欠散客开户银行账户
     * 对于有版本控制的编辑，先逻辑删除该记录，再新增一条记录
     * @author dp-xieyantao
     * @date 2012-11-29 上午11:33:31
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
     * @date 2012-11-29 上午11:33:31
     * @param crmId
     * @param lastupdatetime
     * @return
     * @see
     */
     boolean queryCusAccountByCrmId(BigDecimal crmId,Date lastupdatetime);

}
