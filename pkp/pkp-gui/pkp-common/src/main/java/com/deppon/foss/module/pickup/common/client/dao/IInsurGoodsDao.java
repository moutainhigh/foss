/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/IInsurGoodsDao.java
 * 
 * FILE NAME        	: IInsurGoodsDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
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
package com.deppon.foss.module.pickup.common.client.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LimitedWarrantyItemsEntity;

/**
 * 
 * 限保物品数据持久层接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-10-16 下午5:35:16, </p>
 * @author foss-sunrui
 * @date 2012-10-16 下午5:35:16
 * @since
 * @version
 */
public interface IInsurGoodsDao {

    /**
     * 
     * <p>通过主键获取限保物品</p> 
     * @author foss-sunrui
     * @date 2012-10-16 下午5:35:39
     * @param id
     * @return
     * @see
     */
     LimitedWarrantyItemsEntity queryByPrimaryKey(String id);
    
    /**
     * 
     * <p>获取所有的限保物品</p> 
     * @author foss-sunrui
     * @date 2012-10-16 下午5:35:43
     * @return
     * @see
     */
     List<LimitedWarrantyItemsEntity> queryAllActive();
    
    /**
     * 
     * <p>判断是否是限保物品</p> 
     * @author foss-sunrui
     * @date 2012-10-16 下午5:35:50
     * @param goodsName
     * @return
     * @see
     */
     LimitedWarrantyItemsEntity isInsurGoods(String goodsName);
    
    /** 
     * 
     * <p>判断是否是限保物品并且带出限保金额</p> 
     * @author foss-sunrui
     * @date 2012-10-15 下午1:00:41
     * @param goodsName
     * @return 
     * @see
     */
     String queryLimitAmount(String goodsName);

}