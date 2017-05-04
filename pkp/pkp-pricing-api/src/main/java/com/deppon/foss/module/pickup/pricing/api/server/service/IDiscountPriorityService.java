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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/service/IDiscountPriorityService.java
 * 
 * FILE NAME        	: IDiscountPriorityService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.service;
import java.util.List;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.DiscountPriorityEntity;

public interface IDiscountPriorityService extends IService{
	/**
	 * @Description: 根据条件查询优先级集合(分页)
	 * Company:IBM
	 * @author IBMDP-sz
	 * @date 2012-12-17 上午11:33:37
	 * @param @param priorityEntity
	 * @param @param start
	 * @param @param limit
	 * @param @return
	 * @version V1.0
	 */
	 List<DiscountPriorityEntity> queryPagingByCodition(DiscountPriorityEntity priorityEntity, int start, int limit);
	/**
	 * @Description: 根据条件查询优先级总数（分页）
	 * Company:IBM
	 * @author IBMDP-sz
	 * @date 2012-12-17 上午11:37:28
	 * @param priorityEntity
	 * @return
	 * @version V1.0
	 */
	 Integer countPagingByCodition(DiscountPriorityEntity priorityEntity);
	/**
	 * @Description: 根据条件查询优先级集合
	 * Company:IBM
	 * @author IBMDP-sz
	 * @date 2012-12-17 上午11:33:37
	 * @param @param priorityEntity
	 * @param @param start
	 * @param @param limit
	 * @param @return
	 * @version V1.0
	 */
	 List<DiscountPriorityEntity> queryByCodition(DiscountPriorityEntity priorityEntity);
	
	/**
	 * @Description: 根据主键ID查询优先级记录
	 * Company:IBM
	 * @author IBMDP-sz
	 * @date 2012-12-17 上午11:40:29
	 * @param priorityId
	 * @version V1.0
	 */
	 DiscountPriorityEntity selectByPrimaryKey(String priorityId);
	/**
	 * @Description: 增加优先级记录
	 * Company:IBM
	 * @author IBMDP-sz
	 * @date 2012-12-17 上午11:36:18
	 * @param priorityEntity
	 * @return
	 * @version V1.0
	 */
	 void addDiscountPriority(DiscountPriorityEntity priorityEntity);
	/**
	 * @Description: 修改优先级记录
	 * Company:IBM
	 * @author IBMDP-sz
	 * @date 2012-12-17 上午11:39:03
	 * @param priorityEntity
	 * @return
	 * @version V1.0
	 */
	 void modifyDiscountPriority(DiscountPriorityEntity priorityEntity);
	
	/**
	 * @Description: 根据主键ID删除优先级记录
	 * Company:IBM
	 * @author IBMDP-sz
	 * @date 2012-12-17 上午11:40:29
	 * @param priorityId
	 * @version V1.0
	 */
	 void deleteDiscountPriority(String priorityId);
	/**
	 * @Description: 提升一级优先级
	 * Company:IBM
	 * @author IBMDP-sz
	 * @date 2012-12-17 下午7:27:48
	 * @param priorityId
	 * @version V1.0
	 */
	 void upPriority(String priorityId);
	/**
	 * @Description: 降低一级优先级
	 * Company:IBM
	 * @author IBMDP-sz
	 * @date 2012-12-17 下午7:28:29
	 * @param priorityId
	 * @version V1.0
	 */
	 void downPriority(String priorityId);
	/**
	 * 
	 * @Description: 从缓存中获取优先级
	 * @author FOSSDP-sz
	 * @date 2013-2-22 上午10:29:52
	 * @param dpcode
	 * @param billDate
	 * @return
	 * @version V1.0
	 */
	List <DiscountPriorityEntity> getDiscountPriorityByCache();
	/**
	 * 
	 * @Description: 刷新缓存
	 * @author FOSSDP-sz
	 * @date 2013-2-22 上午10:30:51
	 * @param dpcode
	 * @version V1.0
	 */
	void refreshDiscountPriorityCache();
}