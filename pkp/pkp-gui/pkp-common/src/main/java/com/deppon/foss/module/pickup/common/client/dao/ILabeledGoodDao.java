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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/ILabeledGoodDao.java
 * 
 * FILE NAME        	: ILabeledGoodDao.java
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

import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;

/**
 * 
 * 货签信息数据持久层接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-10-30 下午3:10:53, </p>
 * @author foss-sunrui
 * @date 2012-10-30 下午3:10:53
 * @since
 * @version
 */
public interface ILabeledGoodDao {

    /**
     * 
     * <p>按需插入一条数据</p> 
     * @author foss-sunrui
     * @date 2012-10-30 下午3:12:29
     * @param record
     * @return
     * @see
     */
     int insertSelective(LabeledGoodEntity record);

  
    /**
     * 
     * <p>批量插入</p> 
     * @author foss-sunrui
     * @date 2012-11-6 下午4:53:14
     * @param record
     * @return
     * @see
     */
     int insertBatch(List<LabeledGoodEntity> list);
    
    /**
     * 
     * <p>通过运单号查询最大的序列号</p> 
     * @author foss-sunrui
     * @date 2012-10-30 下午4:55:34
     * @param waybillNo
     * @return
     * @see
     */
     LabeledGoodEntity queryLastSerialByWaybillNo(String waybillNo);
    /**
     * 
     * <p>通过运单号查询所有流水号</p> 
     * @author foss-jiangfei
     * @date 2012-10-30 下午4:55:34
     * @param waybillNo
     * @return
     * @see
     */
     List<LabeledGoodEntity> queryAllSerialByWaybillNo(String waybillNo);


 
}