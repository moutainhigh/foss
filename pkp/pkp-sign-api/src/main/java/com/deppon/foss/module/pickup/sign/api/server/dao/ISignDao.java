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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/server/dao/ISignDao.java
 * 
 * FILE NAME        	: ISignDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.StockDto;



/**
 * 
 * 签收出库Dao
 * <p style="display:none">
 * @author foss-meiying
 * @date 2012-10-17 上午10:58:30
 * @since
 */
public interface ISignDao {
	
	/**
	 * 
	 * 查询签收出库流水号  通过运单编号,运单的最终配载部门=当前登录部门
	 * @author foss-meiying
	 * @date 2012-10-19 下午6:45:19
	 * @param dto (运单号,当前登录人的部门编码)
	 * @return
	 */
	 List<StockDto> queryStock(SignDto dto);
	 /**
	 * 
	 * 根据运单号查询到达联信息（整车）
	 * @author foss-meiying
	 * @date 2013-9-10 下午3:45:19
	 * @param dto 
	 * @return
	 */
	 List<SignArriveSheetDto> queryArrivesheetListByWaybillWVH(SignDto dto);
	 /**
	  * 根据运单号集合查询在库子母件信息
	  * @author 231438-chenjunying
	  * @param dto
	  * @return
	  */
	 List<StockDto> queryCzmInStock(SignDto dto);
}