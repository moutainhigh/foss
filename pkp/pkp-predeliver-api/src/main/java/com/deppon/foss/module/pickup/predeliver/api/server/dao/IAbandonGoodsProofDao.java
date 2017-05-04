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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/server/dao/IAbandonGoodsProofDao.java
 * 
 * FILE NAME        	: IAbandonGoodsProofDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.server.dao;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.AbandonGoodsProofEntity;


/**
 * 弃货凭证dao
 * @date 2012-11-27 上午11:02:07
 */
public interface IAbandonGoodsProofDao {
	
	/**
	 * 删除弃货凭证
	 * @date 2012-11-27 上午11:02:15
	 */
	int deleteByPrimaryKey(String id);

	
	 /**
	  * 插入弃货凭证
	  * @date 2012-11-27 上午11:02:11
	  */
    int insert(AbandonGoodsProofEntity record);

    /**
	  * 选择插入弃货凭证
	  * @date 2012-11-27 上午11:02:11
	  */
    int insertSelective(AbandonGoodsProofEntity record);

    /**
   	 * 选择弃货凭证 纪录
   	 * @date 2012-11-27 上午11:02:09
   	 */
    AbandonGoodsProofEntity selectByPrimaryKey(String id);

    /**
	 * 更新弃货凭证 纪录
	 * @date 2012-11-27 上午11:02:08
	 */
    int updateByPrimaryKeySelective(AbandonGoodsProofEntity record);

    /**
   	 * 更新弃货凭证 纪录
   	 * @date 2012-11-27 上午11:02:08
   	 */
    int updateByPrimaryKey(AbandonGoodsProofEntity record);
}