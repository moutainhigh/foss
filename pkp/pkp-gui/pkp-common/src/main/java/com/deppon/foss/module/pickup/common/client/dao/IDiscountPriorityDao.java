/**
 *  initial comments.
 */
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
 * PROJECT NAME	: pkp-pricing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/dao/IDiscountPriorityDao.java
 * 
 * FILE NAME        	: IDiscountPriorityDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.common.client.dao;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.DiscountPriorityEntity;

/**
 * 折扣优先级Dao

  * @ClassName: IDiscountPriorityDao

  * @Description: 20131012下载离线数据 BUG-55198

  * @author deppon-157229-zxy

  * @date 2013-10-12 
  
  *
 */
public interface IDiscountPriorityDao {
	
	/**
	  * Description: 根据主键ID删除数据
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-12
	  * @param id
	  * @return
	 */
    int deleteByPrimaryKey(String id);
    
    /**
	  * Description: 新增
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-12
	  * @param priceRegioOrgnEntity
	  * @return
	 */
    boolean insertSelective(DiscountPriorityEntity record);
    
    /**
	  * Description: 查询
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-12
	  * @param id
	  * @return
	 */
    DiscountPriorityEntity selectByPrimaryKey(String id);
    
    /**
     * @Description: 根据主键ID修改记录
     * @author deppon-157229-zxy
     * @version 1.0 2013-10-12
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(DiscountPriorityEntity record);
}