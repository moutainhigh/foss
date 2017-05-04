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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/impl/LineItemService.java
 * 
 * FILE NAME        	: LineItemService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.service.impl;

import org.mybatis.guice.transactional.Transactional;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineItemEntity;
import com.deppon.foss.module.pickup.common.client.dao.ILineItemDao;
import com.deppon.foss.module.pickup.common.client.service.ILineItemService;
import com.google.inject.Inject;

/**
 * 发车线路实效服务实现
 * @author 105089-foss-yangtong
 * @date 2012-10-24 上午10:28:47
 */
public class LineItemService implements ILineItemService {
	
	@Inject
	ILineItemDao lineItemDao;
	
	
	/**
	 * @param lineItemDao the lineItemDao to set
	 */
	public void setLineItemDao(ILineItemDao lineItemDao) {
		this.lineItemDao = lineItemDao;
	}

	/**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	@Transactional
	@Override
	public void addLineItem(LineItemEntity lineItem) {
		lineItemDao.addLineItem(lineItem);

	}

	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Transactional
	@Override
	public void updateLineItem(LineItemEntity lineItem) {
		lineItemDao.updateLineItem(lineItem);

	}

	/**
	 * 
	 * 功能：新增或更新记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Override
	public void saveOrUpdate(LineItemEntity lineItem) {
		if(!lineItemDao.addLineItem(lineItem)){
			lineItemDao.updateLineItem(lineItem); 
		}

	}

	/**
	 * 删除
	 * @param lineItemEntity
	 */
	public void delete(LineItemEntity lineItemEntity) {
		lineItemDao.delete(lineItemEntity);
	}

}