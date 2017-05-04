/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-writeoff-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/writeoff/api/server/dao/IBillPayWriteoffAdvPayQueryDao.java
 * 
 * FILE NAME        	: IBillPayWriteoffAdvPayQueryDao.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.writeoff.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.AdvPayWriteoffBillPayDto;

/**
 * 预付冲应付dao
 * 
 * @author foss-pengzhen
 * @date 2012-12-24 下午8:50:55
 * @since
 * @version
 */
public interface IBillPayWriteoffAdvPayQueryDao {

	/**
	 * 
	 * 根据传入的一到多个应收单号，获取一到多条应收单信息
	 * 
	 * @author foss-pengzhen
	 * @date 2012-10-17 上午11:58:36
	 * @param advPayWriteoffBillPayDto
	 *            预付冲应付Dto参数
	 * @return List<BillPayableEntity> 
	 * 				应付单集合
	 * @see
	 */
	List<BillPayableEntity> queryPayableNOs(
			AdvPayWriteoffBillPayDto advPayWriteoffBillPayDto);

	/**
	 * 根据传入的参数获取一到多条应收单信息
	 * 
	 * @author foss-pengzhen
	 * @date 2012-10-17 下午4:44:23
	 * @param advPayWriteoffBillPayDto
	 *            预付冲应付Dto参数
	 * @return List<BillPayableEntity> 
	 * 				应付单集合
	 * @see
	 */
	List<BillPayableEntity> queryPayableParams(
			AdvPayWriteoffBillPayDto advPayWriteoffBillPayDto);
}
