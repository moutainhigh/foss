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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/commonselector/ICommonPriceRegionDao.java
 * 
 * FILE NAME        	: ICommonPriceRegionDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo-api
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.api.server.dao
 * FILE    NAME: ICommonPerscriptionRegionDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity;

/**
 * 公共查询组件--快递价格区域DAO接口
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2013-8-12 下午6:58:10
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2013-8-12 下午6:58:10
 * @since
 * @version
 */
public interface ICommonExpressPriceRegionDao {

    /**
     * 
     * <p>
     * 分页查询快递价格区域
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-8-12 下午6:58:46
     * @param regionEntity
     *            查询条件
     * @param start
     *            其实查询位置
     * @param limit
     *            每页几条
     * @return
     * @see
     */
    List<PriceRegionEntity> searchRegionByCondition(
	    PriceRegionEntity regionEntity, int start, int limit);

    /**
     * <p>统计总记录数</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-8-12 下午6:59:49
     * @param regionEntity
     * @return
     * @see
     */
    Long countRegionByCondition(PriceRegionEntity regionEntity);
}
