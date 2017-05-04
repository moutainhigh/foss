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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/server/service/IShareJobService.java
 * 
 * FILE NAME        	: IShareJobService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.StorageExecdateDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.StorageExecdateEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.StorageJobDto;

/**
 * 
 * 仓储费计算service接口
 * 
 * @author ibm-wangfei
 * @date Feb 27, 2013 5:14:09 PM
 */
public interface IShareJobService extends IService{
	
	/**
	 * 
	 * 仓储费日期执行表记录操作入口
	 * 
	 * @author ibm-wangfei
	 * @date Feb 27, 2013 5:19:15 PM
	 */
	void executeStorageExecdate();

	/**
	 * 
	 * 批量新增待执行明细表记录入口
	 * 
	 * @author ibm-wangfei
	 * @date Feb 27, 2013 5:21:01 PM
	 */
	void batchAddStorageExecdateDetail();

	/**
	 * 
	 * 计算仓储费--定时任务<br>
	 * 
	 * Job说明：<br>
	 * 1：查询出符合到达件数 ≥ 开单件数 AND 库存件数 > 0 AND 运输性质  空运、偏线的运单信息<br>
	 * 2：【到达时间】：检索出来的库存表最后入库时间<br>
	 * 3：【入库日期】：到达时间<12:00，则【入库日期】=当天日期；到达时间≥12:00，则【入库日期】=（当天日期+1） <br>
	 * 4：【库存天数】=当前日期-入库日期，当前日期和入库日期的运算按工作日的日期进行计算；（当前日期≥入库日期）
	 * 5：【超期天数】=（库存天数-X），超期天数＞0，则计算仓储费；否则，仓储费=0<br>
	 * 6：【仓储费】=货物体积*超期天数*10元/方/天，仓储费收取标准为10元/方/天，最低5元/票，最高1000元/票；<br>
	 * 7：停止计算仓储费规则：若当前日期≥出库日期，则不再计算仓储费，仓储费金额显示最后一次计算的数值不变；<br>
	 * 	    若库存天数≥90天，则不再计算仓储费，并且仓储费=0<br>
	 * 8：空运、偏线、中转下线的不计算仓储费 <br>
	 * 
	 * 9：本JOB每日一次，进行仓储费的累加计算，节假日不执行
	 * 
	 * @author ibm-wangfei
	 * @date Feb 27, 2013 10:30:13 AM
	 */
	void computeStorage();

	/**
	 * 新增可执行明细数据
	 * @param entity
	 * @param productCodes
	 * @author ibm-wangfei
	 * @date Apr 23, 2013 3:33:11 PM
	 */
	void exeBatchAddStorageExecdateDetail(StorageExecdateEntity entity, String[] productCodes);

	/**
	 * 具体仓储费计算逻辑
	 * @param storageExecdateDetailEntity
	 * @param storageExecdateEntity
	 * @param storageJobDto
	 * @author ibm-wangfei
	 * @date Apr 23, 2013 3:35:47 PM
	 */
	void execute(StorageExecdateDetailEntity storageExecdateDetailEntity, StorageExecdateEntity storageExecdateEntity, StorageJobDto storageJobDto);
}