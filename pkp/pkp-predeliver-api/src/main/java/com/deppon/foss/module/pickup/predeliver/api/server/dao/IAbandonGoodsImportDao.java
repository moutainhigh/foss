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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/server/dao/IAbandonGoodsImportDao.java
 * 
 * FILE NAME        	: IAbandonGoodsImportDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.server.dao;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.AbandonGoodsImportEntity;

/**
 * 内部带货中间导入表 dao
 * @date 2012-11-27 上午11:02:07
 */
public interface IAbandonGoodsImportDao {
	
	/**
	 * 删除内部带货中间导入表 
	 * @date 2012-11-27 上午11:02:15
	 */
    int deleteByPrimaryKey(String id);

    /**
	 * 插入删除内部带货中间导入表 
	 * @date 2012-11-27 上午11:02:11
	 */
    int insert(AbandonGoodsImportEntity record);

    /**
	 * 插入内部带货中间导入表 
	 * @date 2012-11-27 上午11:02:10
	 */
    int insertSelective(AbandonGoodsImportEntity record);

    /**
	 * 选择内部带货中间导入表 纪录
	 * @date 2012-11-27 上午11:02:09
	 */
    AbandonGoodsImportEntity selectByPrimaryKey(String id);

    /**
	 * 更新内部带货中间导入表 
	 * @date 2012-11-27 上午11:02:08
	 */
    int updateByPrimaryKeySelective(AbandonGoodsImportEntity record);

    /**
	 * 更新内部带货中间导入表 
	 * @date 2012-11-27 上午11:02:08
	 */
    int updateByPrimaryKey(AbandonGoodsImportEntity record);
    
    /**
   	 * 根据WAYBILLNO运单号更新内部带货中间导入表 
   	 * @date 2012-11-27 上午11:02:08
   	 */
    int updateByWaybillNoSelective(AbandonGoodsImportEntity record);
}